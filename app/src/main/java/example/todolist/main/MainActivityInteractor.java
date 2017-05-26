package example.todolist.main;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import example.todolist.models.Task;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private DatabaseReference database;

    @Override
    public void getTasks(final OnFinishedListener listener) {

        database = FirebaseDatabase.getInstance().getReference();
        database.child("Tasks").orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<Task> tasks = new ArrayList<>();

                for (DataSnapshot child : children) {
                    Task task = child.getValue(Task.class);
                    tasks.add(task);
                }
                listener.onFinished(tasks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainInteractor", "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}
