package example.todolist.main;

import java.util.List;

import example.todolist.adapters.TaskListAdapter;
import example.todolist.models.Task;

public interface MainActivityContract {

    interface View {
        void showTasks(List<Task> tasks);
        void showProgressBar(boolean show);
        void showMessage(int id);
    }

    interface Presenter {
        void getData(TaskListAdapter taskListAdapter);

        void onCompleteTaskClick(Task task);
    }

    interface Interactor {

        interface OnFinishedListener {
            void onFinished(List<Task> tasks);
        }

        void getTasks(OnFinishedListener listener);
        void removeTask(Task task);
    }
}
