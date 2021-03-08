package be.ucll.java.gip5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.Participant;

public class DetailsFragment extends Fragment {

    Game game;
    Participant participant;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_APIKEY = "apikey";

    private RequestQueue queue;

    private static final String URL = "http://ucll-team5-gip5-web.eu-west-1.elasticbeanstalk.com/participant/";

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

        TextView location_txt = view.findViewById(R.id.location_txt);

        EditText comment_et = view.findViewById(R.id.editTextComment);

        //things to set invisible if not a player. & to send data when player
        TextView availabilityHeader = view.findViewById(R.id.txtAvailability);
        LinearLayout availabilityLayout = view.findViewById(R.id.radiogroupLayout);
        LinearLayout commentInput = view.findViewById(R.id.layout_edittext_comment);
        LinearLayout saveButtonLayout = view.findViewById(R.id.layout_button_send);

        Button saveButton = view.findViewById(R.id.send_button);
        RadioGroup radioGroup = view.findViewById(R.id.radiogroup);

        //all radio buttons
        RadioButton rb1 = view.findViewById(R.id.radioButton1);
        RadioButton rb2 = view.findViewById(R.id.radioButton2);
        RadioButton rb3 = view.findViewById(R.id.radioButton3);
        RadioButton rb4 = view.findViewById(R.id.radioButton4);
        RadioButton rb5 = view.findViewById(R.id.radioButton5);
        RadioButton rb6 = view.findViewById(R.id.radioButton6);
        if(participant != null && participant.getGame() != null){
            Game game = participant.getGame();

            if(game.getTeam1().getHome()){
                team1_txt.setText(game.getTeam1().getTitle());
                team2_txt.setText(game.getTeam2().getTitle());
            }else{
                team1_txt.setText(game.getTeam2().getTitle());
                team2_txt.setText(game.getTeam1().getTitle());
            }

            try{
                date_txt.setText(formatDateString(game.getStartTime()));
            }
            catch (ParseException e){
                date_txt.setText(simpleFormatDateString(game.getStartTime()));
            }

            time_txt.setText(game.getStartTime().substring(11, 16));
            location_txt.setText(game.getLocation());

            if(participant.getAvailability() == null){
                availabilityHeader.setVisibility(View.GONE);
                availabilityLayout.setVisibility(View.GONE);
                commentInput.setVisibility(View.GONE);
                saveButtonLayout.setVisibility(View.GONE);
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
                    case "invited":
                        break;
                    default: //do nothing, the background will stay gray.
                        break;
                }

                saveButton.setOnClickListener(v -> {
                    String comment = comment_et.getText().toString().trim();
                    int index = (radioGroup.indexOfChild(view.findViewById(radioGroup.getCheckedRadioButtonId()))) + 1; //returns -1 if none selected

                    if(index < 1 || index > 6){
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.error_availability_selector), Toast.LENGTH_LONG).show();
                    }
                    else {
                        sendData(index, comment);
                    }
                });
            }
        }
        else{
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.error_getting_game), Toast.LENGTH_LONG).show();
            Objects.requireNonNull(getActivity()).finish();
        }

        return view;
    }

    public void sendData(int choice, String comment){
        try {
            sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String apiK = sharedPreferences.getString(KEY_APIKEY, null);

            String url = URL
                    + URLEncoder.encode(participant.getGame().getId().toString(), "UTF-8")
                    + "/"
                    + URLEncoder.encode(apiK, "UTF-8");
            Log.i("URL used: ", url);

            queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());

            JSONObject postData = new JSONObject();
            try{
                postData.put("choice", choice+"");
                postData.put("comment", comment);
            }
            catch (JSONException e){
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.unsuccessfully_send_data), Toast.LENGTH_LONG).show();
                Objects.requireNonNull(getActivity()).finish();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, postData,
                    response -> {
                        //Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.successfully_send_data), Toast.LENGTH_LONG).show();
                        Objects.requireNonNull(getActivity()).finish();
                    },
                    error -> {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.unsuccessfully_send_data), Toast.LENGTH_LONG).show();
                        Objects.requireNonNull(getActivity()).finish();
                });

            queue.add(req);

        }catch (UnsupportedEncodingException e){
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    public String formatDateString(String dateStr) throws ParseException {
        String correctDateFormat = dateStr.substring(8, 10) + "/" + dateStr.substring(5, 7) + "/" + dateStr.substring(0, 4);

        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(correctDateFormat);
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault());

        return dateFormat.format(Objects.requireNonNull(date));
    }

    public String simpleFormatDateString(String dateStr){
        return dateStr.substring(8, 10) + "/" + dateStr.substring(5, 7) + "/" + dateStr.substring(0, 4);
    }
}