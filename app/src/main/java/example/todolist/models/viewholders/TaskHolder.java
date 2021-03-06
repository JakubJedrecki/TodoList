package example.todolist.models.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.todolist.R;

/**
 * Created by Jakub on 24/08/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder {

    //TODO: firebase UI adapter

    @BindView(R.id.taskCardView) CardView taskCardView;
    @BindView(R.id.taskDescription) TextView taskDescription;
    @BindView(R.id.taskCompleteCB) CheckBox taskComplete;

    public TaskHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    private TaskHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(TaskHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setTaskDescription(String description){
        taskDescription.setText(description);
    }
}
