package example.todolist.main;

import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import example.todolist.R;
import example.todolist.adapters.TaskListAdapter;
import example.todolist.models.Task;
import example.todolist.models.viewholders.TaskHolder;

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

    //TODO: adapter do firebaseui, trzeba dokonczyc onclick i returna
    public FirebaseRecyclerAdapter<Task, TaskHolder> createFirebaseAdapter(){
        FirebaseRecyclerAdapter<Task, TaskHolder> mAdapter = new FirebaseRecyclerAdapter<Task, TaskHolder>(
                Task.class,
                R.layout.item_card_view,
                TaskHolder.class,
                mainActivityInteractor.getDBReference()) {

            @Override
            protected void populateViewHolder(TaskHolder viewHolder, Task model, int position) {
                viewHolder.setTaskDescription(model.getDescription());
            }

            @Override
            public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TaskHolder taskHolder = super.onCreateViewHolder(parent, viewType);
                taskHolder.setOnClickListener(new TaskHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
            }
        };
    }
}
