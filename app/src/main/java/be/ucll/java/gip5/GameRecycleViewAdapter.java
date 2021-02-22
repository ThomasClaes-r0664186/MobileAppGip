package be.ucll.java.gip5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.GamesReturn;

public class GameRecycleViewAdapter extends RecyclerView.Adapter<GameRecycleViewAdapter.ViewHolder> {

    Context context;
    GamesReturn repo;

    public GameRecycleViewAdapter(Context ct, GamesReturn repo){
        this.context = ct;
        this.repo = repo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game;
        if(repo.getParticipants().size()>0){
            game = repo.getParticipants().get(position).getGame();
            switch (repo.getParticipants().get(position).getAvailability()){
                case "yes": //green
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_green) );
                    break;
                case "probably": //green
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_green) );
                    break;
                case "backup": //orange
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_orange) );
                    break;
                case "probablyNot": //orange
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_orange) );
                    break;
                case "sick": //red
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_red) );
                    break;
                case "unavailable": //red
                    holder.cardviewBg.setBackground(context.getResources().getDrawable (R.drawable.game_row_background_red) );
                    break;
                case "unchecked": //do nothing
                    break;
                case "invited": //blue (new feature, back end probably won't be able to implement) => do nothing for now
                    break;
            }
        }
        else{
            game = repo.getGames().get(position);
        }

        if(game.getTeam1().getHome()){
            holder.opp_txt.setText(game.getTeam2().getTitle());
        }
        else{
            holder.opp_txt.setText(game.getTeam1().getTitle());
        }

        //todo: check at what place the time exaclty is.
        holder.time_txt.setText(game.getStartTime().substring(11, 16));
        holder.date_txt.setText(formatDateString(game.getStartTime()));
    }

    @Override
    public int getItemCount() {
        if(repo.getParticipants().size()>0){
            return repo.getParticipants().size();
        }
        else{
            return repo.getGames().size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView opp_txt, time_txt, date_txt;
        ConstraintLayout cardviewBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            opp_txt = itemView.findViewById(R.id.opponent_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            cardviewBg = itemView.findViewById(R.id.cardview_background);
        }
    }

    public String formatDateString(String date){
        String[] months = new DateFormatSymbols().getMonths();

        //todo: fix month name: months[Integer.parseInt(date.substring(5, 6))-1]
        int month = Integer.parseInt(date.substring(5, 7)) - 1;
        return date.substring(8, 10) + " " +  months[month].substring(0,3) + ". " + date.substring(0, 4);
    }
}
