package example.todolist.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.todolist.R;
import example.todolist.models.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> tasks;
    private final Context context;
    private ClickListener clickListener;
    private int lastPosition = -1;

    public TaskListAdapter(List<Task> tasks, Context context, ClickListener clickListener) {
        this.tasks = tasks;
        this.context = context;
        this.clickListener = clickListener;
    }


    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.ViewHolder holder, final int position) {
        if(tasks != null){
            holder.taskDescription.setText(tasks.get(position).getDescription());
            holder.taskComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.taskComplete.setEnabled(false);
                        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
                        holder.taskCardView.startAnimation(animation);
                        holder.taskComplete.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                clickListener.itemClicked(tasks.get(holder.getLayoutPosition()));
                                holder.taskComplete.toggle();
                                holder.taskComplete.setEnabled(true);
                            }
                        }, 300);
                }
            });
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public interface ClickListener{
        void itemClicked(Task task);
    }

    public void replaceData(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.taskCardView) CardView taskCardView;
        @BindView(R.id.taskDescription) TextView taskDescription;
        @BindView(R.id.taskCompleteCB) CheckBox taskComplete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null){
            }
        }

        public void clearAnimation()
        {
            taskCardView.clearAnimation();
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.setStartOffset(500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void updateLastPosition(){
        lastPosition --;
    }
}
