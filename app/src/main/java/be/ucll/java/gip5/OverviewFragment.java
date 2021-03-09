package be.ucll.java.gip5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.ucll.java.gip5.model.GameReportReturn;
import be.ucll.java.gip5.model.GamesReturn;
import be.ucll.java.gip5.model.Report;

public class OverviewFragment extends Fragment {

    RecyclerView recyclerView;
    int gameId;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_APIKEY = "apikey";

    private RequestQueue queue;

    private static final String URL = "http://ucll-team5-gip5-web.eu-west-1.elasticbeanstalk.com/gamereports/";

    LinearLayout editTextLin;
    LinearLayout sendBtnLin;
    EditText reportInput;

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

        Button sendBtn = view.findViewById(R.id.send_button);
        ImageView refresh = view.findViewById(R.id.refresh);
        refresh.bringToFront();

        editTextLin = view.findViewById(R.id.layout_edittext);
        sendBtnLin = view.findViewById(R.id.layout_button);
        reportInput = view.findViewById(R.id.editTextReport);

        makeCall();

        sendBtn.setOnClickListener(v -> {
            if(!reportInput.getText().toString().trim().isEmpty()){
                sendReport();
            } else {
                StyleableToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.error_empty_report_field), R.style.mainToast).show();
            }
        });

        refresh.setOnClickListener(v -> {
            makeCall();
        });

        return view;
    }

    private void makeCall(){
        try {
            List<Pair<String, String>> reports = new ArrayList<Pair<String, String>>();

            sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String apiK = sharedPreferences.getString(KEY_APIKEY, null);

            String url = URL
                    + gameId
                    + "/"
                    + URLEncoder.encode(apiK, "UTF-8");
            Log.i("URL used: ", url);

            queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        //handle response
                        JSONObject jsono = (JSONObject) response;

                        Log.i("Return api: ", jsono.toString());

                        GameReportReturn repRet = new Gson().fromJson(jsono.toString(), GameReportReturn.class);

                        if(repRet != null && repRet.getRole() != null && repRet.getRole().getRoleType().equals("REPORTER")){
                            editTextLin.setVisibility(View.VISIBLE);
                            sendBtnLin.setVisibility(View.VISIBLE);
                        }

                        if(repRet != null && repRet.getError() == null){
                            if(repRet.getReports() != null && repRet.getReports().size() > 0){
                                for (Report rep:repRet.getReports()) {
                                    reports.add(new Pair<>(rep.getTime().substring(0,5), rep.getText()));
                                }
                            }
                            else {
                                reports.add(new Pair<>("", "There were no reports."));
                            }
                        }
                        else if(repRet != null && !repRet.getError().isEmpty()){
                            reports.add(new Pair<>("", repRet.getError()));
                        }
                        else {
                            reports.add(new Pair<>("", "Something went wrong while retreiving the reports."));
                        }

                        OverviewRecycleViewAdapter adapter = new OverviewRecycleViewAdapter(Objects.requireNonNull(getContext()), reports);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    },
                    error -> {
                        reports.add(new Pair<>("", "The game id was not found."));

                        OverviewRecycleViewAdapter adapter = new OverviewRecycleViewAdapter(Objects.requireNonNull(getContext()), reports);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                });

            queue.add(req);
        }
        catch (UnsupportedEncodingException e){
            StyleableToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.something_went_wrong), R.style.mainToast).show();
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    private void sendReport(){
        //todo: handle input box & onclick handler send btn
        try {
            sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String apiK = sharedPreferences.getString(KEY_APIKEY, null);

            String url = URL
                    + gameId
                    + "/"
                    + URLEncoder.encode(apiK, "UTF-8");
            Log.i("URL used: ", url);

            queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());

            JSONObject postData = new JSONObject();
            try{
                postData.put("txt", reportInput.getText().toString().trim());
            }
            catch (JSONException e){
                StyleableToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.unsuccessfully_send_data), R.style.mainToast).show();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, postData,
                    response -> {
                        makeCall();
                    },
                    error -> {
                        StyleableToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.unsuccessfully_send_data), R.style.mainToast).show();
                    });

            queue.add(req);
        }catch (UnsupportedEncodingException e){
            StyleableToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.something_went_wrong), R.style.mainToast).show();
        }
    }
}