package example.todolist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.todolist.R;
import example.todolist.adapters.TaskListAdapter;
import example.todolist.additem.AddTaskActivity;
import example.todolist.models.Task;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, TaskListAdapter.ClickListener{

    @BindView(R.id.fabAddTask) FloatingActionButton addTask;
    @BindView(R.id.tasksRecyclerView) RecyclerView tasksRecyclerView;
    @BindView(R.id.progressBarLayout) RelativeLayout loadingProgressBar;
    private MainActivityPresenter presenter;
    private TaskListAdapter taskListAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this, new MainActivityInteractor());

        LinearLayoutManager llm = new LinearLayoutManager(this);
        tasks = new ArrayList<>();
        taskListAdapter = new TaskListAdapter(tasks, this, this);
        tasksRecyclerView.setLayoutManager(llm);
        tasksRecyclerView.setAdapter(taskListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        taskListAdapter.replaceData(tasks);
    }

    @Override
    public void showProgressBar(boolean show) {
        if(show){
            loadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.fabAddTask)
    public void goToAddTask(){
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    @Override
    public void itemClicked(Task task) {
        presenter.onCompleteTaskClick(task);
        taskListAdapter.updateLastPosition();
    }
}