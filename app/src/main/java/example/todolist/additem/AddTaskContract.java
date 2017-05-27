package example.todolist.additem;

import example.todolist.models.Task;

public interface AddTaskContract {

    interface View {
        void finishActivity();
        void showMessage(int id);
    }

    interface Presenter {
        void saveTask(String description);
    }

    interface Interactor {

        interface onFinishedListener {
            void onFinished(boolean succeeded);
        }

        void saveTaskToDB(onFinishedListener listener, Task task);
    }
}
