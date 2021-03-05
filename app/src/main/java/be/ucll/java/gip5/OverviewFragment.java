package be.ucll.java.gip5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OverviewFragment extends Fragment {

    RecyclerView recyclerView;
    int gameId;

    //todo: make call to api using game id

    //todo: check if reporter -> else set part invisible

    //todo: handle refresh button -> make call to api

    //todo: handle input box & onclick handler send btn

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
        recyclerView = view.findViewById(R.id.overviewRecyclerView);

        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), gameId + "", Toast.LENGTH_LONG).show();

        //this list is to staticly test the view.
        List<Pair<String, String>> repoL = new ArrayList<Pair<String, String>>();
        repoL.add(new Pair<>("15:45", "De wedstrijd is afgelopen."));
        repoL.add(new Pair<>("15:06", "Goal!! 1-0 voor FcBinkom!"));
        repoL.add(new Pair<>("14:45", "Rust"));
        repoL.add(new Pair<>("14:16", "Er zijn nog niet zo heel veel grote kansen geweest. Als één van beide ploegen wilt winnen, gaan ze toch meer druk moeten zetten en hun kleine kansen afmaken."));
        repoL.add(new Pair<>("14:01", "En we zijn van start gegaan, Binkom begint alvast heel aanvallend."));

        //todo: put adapter in new function, so you can call from onclicklistener
        OverviewRecycleViewAdapter adapter = new OverviewRecycleViewAdapter(getContext(), repoL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}