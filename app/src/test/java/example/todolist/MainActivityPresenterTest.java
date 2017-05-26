package example.todolist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import example.todolist.main.MainActivityContract;
import example.todolist.main.MainActivityInteractor;
import example.todolist.main.MainActivityPresenter;
import example.todolist.models.Task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    MainActivityContract.View mainView;
    @Mock
    MainActivityInteractor interactor;

    private MainActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainActivityPresenter(mainView, interactor);
    }

    @Test
    public void checkIfShowProgressBar(){
        presenter.onResume();
        verify(mainView, times(1)).showProgressBar(true);
    }

    @Test
    public void checkIfItemsArePassedToView(){
        List<Task> tasks = new ArrayList<Task>() {{
            new Task("1", "test 1", "26.05.2017");
            new Task("2", "test 2", "26.05.2017");
        }};

        presenter.onFinished(tasks);
        verify(mainView, times(1)).showTasks(tasks);
        verify(mainView, times(1)).showProgressBar(false);
    }
}
