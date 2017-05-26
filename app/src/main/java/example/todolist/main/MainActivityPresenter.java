package example.todolist.main;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import example.todolist.models.Task;

public class MainActivityPresenter implements MainActivityContract.Presenter, MainActivityContract.Interactor.OnFinishedListener {

    private MainActivityContract.View mainView;
    private MainActivityInteractor mainActivityInteractor;
    private DatabaseReference database;

    public MainActivityPresenter(MainActivityContract.View mainView, MainActivityInteractor mainActivityInteractor) {
        this.mainView = mainView;
        this.mainActivityInteractor = mainActivityInteractor;
    }

    @Override
    public void onResume() {
        if(mainView != null){
            mainView.showProgressBar(true);
        }

        mainActivityInteractor.getTasks(this);
    }

    @Override
    public void onFinished(List<Task> tasks) {
        if(mainView != null){
            mainView.showTasks(tasks);
            mainView.showProgressBar(false);
        }
    }

    @Override
    public void onCompleteTaskClick(Task task) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Tasks").child(task.getId()).removeValue();
    }

    public MainActivityContract.View getMainView() { return mainView; }
}
