package example.todolist.main;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import example.todolist.adapters.TaskListAdapter;
import example.todolist.models.Task;

public class MainActivityPresenter implements MainActivityContract.Presenter, MainActivityContract.Interactor.OnFinishedListener {

    private MainActivityContract.View mainView;
    private MainActivityInteractor mainActivityInteractor;
    private DatabaseReference database;
    private TaskListAdapter taskListAdapter;

    public MainActivityPresenter(MainActivityContract.View mainView, MainActivityInteractor mainActivityInteractor) {
        this.mainView = mainView;
        this.mainActivityInteractor = mainActivityInteractor;
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getData(TaskListAdapter taskListAdapter) {
        this.taskListAdapter = taskListAdapter;
        if (mainView != null) {
            mainView.showProgressBar(true);
        }

        mainActivityInteractor.getTasks(this);
    }

    public void registerChildEventListener(List<Task> tasks, TaskListAdapter adapter){
        database.child("Tasks").addChildEventListener(mainActivityInteractor.setChildEventListener(tasks, adapter));
    }

    @Override
    public void onFinished(List<Task> tasks) {
        if (mainView != null) {
            mainView.showTasks(tasks);
            mainView.showProgressBar(false);
            //TODO: dopiero tutaj zarejestrowac listener
            registerChildEventListener(tasks, taskListAdapter);
        }
    }

    @Override
    public void onCompleteTaskClick(Task task) {
        database.child("Tasks").child(task.getId()).removeValue();
    }

    public MainActivityContract.View getMainView() {
        return mainView;
    }
}
