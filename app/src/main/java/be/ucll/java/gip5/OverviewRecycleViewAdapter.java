package be.ucll.java.gip5;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OverviewRecycleViewAdapter extends RecyclerView.Adapter<OverviewRecycleViewAdapter.OverviewHolder> {

    @NonNull
    @Override
    public OverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OverviewHolder extends RecyclerView.ViewHolder {
        //todo: put all elements from the row here
        TextView time_txt, review_txt;

        public OverviewHolder(@NonNull View itemView) {
            super(itemView);
            time_txt = itemView.findViewById(R.id.txt_time);
            review_txt = itemView.findViewById(R.id.txt_singleReview);
        }
    }
}
