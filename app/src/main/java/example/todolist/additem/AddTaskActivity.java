package example.todolist.additem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.todolist.R;

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
        presenter.saveTask(taskDescription.getText().toString());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMessage(int id) {
        Toast.makeText(this, getResources().getString(id), Toast.LENGTH_LONG).show();
    }
}
