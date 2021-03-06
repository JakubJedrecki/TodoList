package example.todolist.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {

    private String id;
    private String description;
    private String date;

    public Task() {
    }

    public Task(String description, String date) {
        this.description = description;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
