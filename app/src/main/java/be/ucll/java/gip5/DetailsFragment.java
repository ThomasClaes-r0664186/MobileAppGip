package be.ucll.java.gip5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.Participant;

public class DetailsFragment extends Fragment {

    Game game;
    Participant participant;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Participant participant) {
        this.participant = participant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView date_txt = view.findViewById(R.id.date_txt);
        TextView time_txt = view.findViewById(R.id.time_txt);

        TextView team1_txt = view.findViewById(R.id.team1);
        TextView team2_txt = view.findViewById(R.id.team2);

        EditText comment_et = view.findViewById(R.id.editTextComment);

        //things to set invisible if not a player. & to send data when player
        TextView availabilityHeader = view.findViewById(R.id.txtAvailability);
        LinearLayout availabilityLayout = view.findViewById(R.id.radiogroup);
        LinearLayout commentInput = view.findViewById(R.id.layout_edittext_comment);
        LinearLayout saveButton = view.findViewById(R.id.layout_button_send);

        //all radio buttons
        RadioButton rb1 = view.findViewById(R.id.radioButton1);
        RadioButton rb2 = view.findViewById(R.id.radioButton2);
        RadioButton rb3 = view.findViewById(R.id.radioButton3);
        RadioButton rb4 = view.findViewById(R.id.radioButton4);
        RadioButton rb5 = view.findViewById(R.id.radioButton5);
        RadioButton rb6 = view.findViewById(R.id.radioButton6);

        if(participant != null && participant.getAvailability() == null){
            availabilityHeader.setVisibility(View.GONE);
            availabilityLayout.setVisibility(View.GONE);
            commentInput.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
        else {
            switch (participant.getAvailability().toLowerCase()){
                case "yes": //green
                    rb1.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "probably": //green
                    rb2.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "backup": //orange
                    rb3.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "probablynot": //orange
                    rb4.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "sick": //red
                    rb5.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "unavailable": //red
                    rb6.toggle();
                    comment_et.setText(participant.getComment().trim());
                    break;
                case "unchecked": //do nothing
                    break;
                case "invited": //do nothing
                    break;
                default: //do nothing, the background will stay gray.
                    break;
            }

            saveButton.setOnClickListener(v -> {
                //todo: send data from radiobutton & commentbox to api
            });
        }

        return view;
    }
}