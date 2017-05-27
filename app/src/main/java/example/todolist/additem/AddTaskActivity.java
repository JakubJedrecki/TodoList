package example.todolist.additem;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.todolist.R;
import example.todolist.util.Utils;

public class AddTaskActivity extends AppCompatActivity implements AddTaskContract.View {

    @BindView(R.id.taskDescription)
    TextView taskDescription;
    AddTaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        presenter = new AddTaskPresenter(this, new AddTaskInteractor());
    }

    @OnClick(R.id.CancelButton)
    public void cancel() {
        finish();
    }

    @OnClick(R.id.SaveButton)
    public void save() {
        if (Utils.checkInternetConnection((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE))) {
            presenter.saveTask(taskDescription.getText().toString());
        } else {
            showMessage(R.string.check_internet_con);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMessage(int id) {
        Snackbar.make(findViewById(android.R.id.content), getString(id), Snackbar.LENGTH_LONG).show();
    }
}
