package be.ucll.java.gip5;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OverviewRecycleViewAdapter extends RecyclerView.Adapter<OverviewRecycleViewAdapter.OverviewHolder> {
    Context context;
    List<Pair<String, String>> repo;
    Drawable background;

    public OverviewRecycleViewAdapter(Context ct, List<Pair<String, String>> repoL){
        this.context = ct;
        this.repo = repoL;
        background = ResourcesCompat.getDrawable(ct.getResources(), R.drawable.overview_recycler_background, null);
    }

    @NonNull
    @Override
    public OverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.overview_row, parent, false);
        return new OverviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewHolder holder, int position) {
        if(repo.size()>0){
            holder.time_txt.setText(repo.get(position).first);
            holder.review_txt.setText(repo.get(position).second);

            holder.itemView.setBackground(background);
        }
    }

    @Override
    public int getItemCount() {
        return repo.size();
    }

    public class OverviewHolder extends RecyclerView.ViewHolder {
        TextView time_txt, review_txt;

        public OverviewHolder(@NonNull View itemView) {
            super(itemView);
            time_txt = itemView.findViewById(R.id.txt_time);
            review_txt = itemView.findViewById(R.id.txt_singleReview);
        }
    }
}
