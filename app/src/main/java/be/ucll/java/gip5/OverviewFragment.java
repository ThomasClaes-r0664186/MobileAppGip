package be.ucll.java.gip5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OverviewFragment extends Fragment {

    int gameId;

    //todo: make call to mock api using game id
    public OverviewFragment() {
        // Required empty public constructor
    }

    public OverviewFragment(int gameid) {
        this.gameId = gameid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        return view;
    }
}