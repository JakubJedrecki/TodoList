package example.todolist.additem;

import java.util.Date;
import java.util.UUID;

import example.todolist.R;
import example.todolist.models.Task;

public class AddTaskPresenter implements AddTaskContract.Presenter, AddTaskContract.Interactor.onFinishedListener{

    private AddTaskContract.View addTaskView;
    private AddTaskContract.Interactor addTaskInteractor;

    public AddTaskPresenter(AddTaskContract.View addTaskView, AddTaskContract.Interactor addTaskInteractor) {
        this.addTaskView = addTaskView;
        this.addTaskInteractor = addTaskInteractor;
    }

    @Override
    public void saveTask(String description) {
        String taskUUID = UUID.randomUUID().toString();
        if(!description.equals("") && !description.isEmpty()) {
            Task task = new Task(taskUUID, description, new Date().toString());
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
