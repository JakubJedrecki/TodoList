package example.todolist.additem;

import android.icu.text.SimpleDateFormat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import example.todolist.R;
import example.todolist.models.Task;

public class AddTaskPresenter implements AddTaskContract.Presenter, AddTaskContract.Interactor.onFinishedListener {

    private AddTaskContract.View addTaskView;
    private AddTaskContract.Interactor addTaskInteractor;

    public AddTaskPresenter(AddTaskContract.View addTaskView, AddTaskContract.Interactor addTaskInteractor) {
        this.addTaskView = addTaskView;
        this.addTaskInteractor = addTaskInteractor;
    }

    @Override
    public void saveTask(String description) {
        if (!description.equals("") && !description.isEmpty()) {

            DateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
            String date = sdf.format(Calendar.getInstance().getTime());

            Task task = new Task(description, date);
            addTaskInteractor.saveTaskToDB(this, task);
        } else {
            addTaskView.showMessage(R.string.empty_task_error);
        }
    }

    @Override
    public void onFinished(boolean succeeded) {
        addTaskView.finishActivity();
    }
}
