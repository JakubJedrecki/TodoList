package example.todolist.main;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.todolist.R;
import example.todolist.adapters.TaskListAdapter;
import example.todolist.additem.AddTaskActivity;
import example.todolist.models.Task;
import example.todolist.models.viewholders.TaskHolder;
import example.todolist.util.Utils;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, TaskListAdapter.ClickListener {

    @BindView(R.id.fabAddTask) FloatingActionButton addTask;
    @BindView(R.id.tasksRecyclerView) RecyclerView tasksRecyclerView;
    @BindView(R.id.progressBarLayout) RelativeLayout loadingProgressBar;
    private MainActivityPresenter presenter;
    private TaskListAdapter taskListAdapter;
    private List<Task> tasks;
    private FirebaseRecyclerAdapter<Task, TaskHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this, new MainActivityInteractor());

        LinearLayoutManager llm = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(llm);
        tasks = new ArrayList<>();

        taskListAdapter = new TaskListAdapter(tasks, this, this);
        //tasksRecyclerView.setAdapter(taskListAdapter);
        mAdapter = presenter.createFirebaseAdapter();
        tasksRecyclerView.setAdapter(mAdapter);
        if (Utils.checkInternetConnection((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE))) {
            presenter.getData(taskListAdapter);
        } else {
            showMessage(R.string.check_internet_con);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        taskListAdapter.replaceData(tasks);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.fabAddTask)
    public void goToAddTask() {
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    @Override
    public void itemClicked(Task task) {
        if(Utils.checkInternetConnection((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE))) {
            presenter.onCompleteTaskClick(task);
            taskListAdapter.updateLastPosition();
        } else {
            showMessage(R.string.check_internet_con);
        }
    }

    @Override
    public void showMessage(int id) {
        Snackbar.make(findViewById(android.R.id.content), getString(id), Snackbar.LENGTH_LONG).show();
    }

}
