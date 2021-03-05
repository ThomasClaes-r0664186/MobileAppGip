package be.ucll.java.gip5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.Participant;

public class DetailsFragment extends Fragment {

    Game game;
    Participant participant;

    //todo: make call to api using game id

    //todo: check if player -> else set part invisible

    //todo: handle onclick save radiobutton & inputbox

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Game game) {
        this.game = game;
    }

    public DetailsFragment(Participant participant) {
        this.participant = participant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        //Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), gameId + "", Toast.LENGTH_LONG).show();

        return view;
    }
}