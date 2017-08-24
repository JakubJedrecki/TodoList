package example.todolist.main;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.todolist.R;
import example.todolist.adapters.TaskListAdapter;
import example.todolist.models.Task;
import example.todolist.models.viewholders.TaskHolder;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference getDBReference(){
        return database;
    }

    @Override
    public void getTasks(final OnFinishedListener listener) {

        database.child("Tasks").orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<Task> tasks = new ArrayList<>();

                for (DataSnapshot child : children) {
                    Task task = child.getValue(Task.class);
                    tasks.add(task);
                }
                Collections.reverse(tasks);
                listener.onFinished(tasks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainInteractor", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public ChildEventListener setChildEventListener(final List<Task> tasks, final TaskListAdapter adapter){

        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                tasks.add(dataSnapshot.getValue(Task.class));
                adapter.replaceData(tasks);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task toRemove = dataSnapshot.getValue(Task.class);
                for(int i=0; i<tasks.size(); i++){
                    if(tasks.get(i).getId().equals(toRemove.getId())){
                        tasks.remove(tasks.get(i));
                    }
                }
                adapter.replaceData(tasks);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainInteractor", "onCancelled: " + databaseError.getMessage());
            }
        };
    }

    public void removeTask(Task task) {
        database.child("Tasks").child(task.getId()).removeValue();
    }
}
