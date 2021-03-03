package be.ucll.java.gip5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    int gameId;

    //todo: make call to api using game id

    //todo: check if player -> else set part invisible

    //todo: handle onclick save radiobutton & inputbox

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(int gameid) {
        this.gameId = gameid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        return view;
    }
}