package example.todolist.additem;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import example.todolist.models.Task;

public class AddTaskInteractor implements AddTaskContract.Interactor {

    private final static String TAG = "AddTaskInteractor";
    private DatabaseReference database;

    @Override
    public void saveTaskToDB(final onFinishedListener listener, Task task) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Tasks").child(task.getId()).setValue(task, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                listener.onFinished(true);
                if(databaseError != null){
                    Log.e(TAG, "onComplete: ", databaseError.toException());
                }
            }
        });
    }
}
