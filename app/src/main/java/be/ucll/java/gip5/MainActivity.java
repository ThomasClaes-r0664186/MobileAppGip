package be.ucll.java.gip5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.GamesReturn;
import be.ucll.java.gip5.model.Participant;


public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    Toolbar toolbar;
    TextView txt_countdown;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    private RequestQueue queue;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_APIKEY = "apikey";
    private static final String URL = "http://ucll-team5-gip5-web.eu-west-1.elasticbeanstalk.com/allgames/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        txt_countdown = findViewById(R.id.txt_timerTxt);
        recyclerView = findViewById(R.id.gameRecyclerView);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String pw = sharedPreferences.getString(KEY_PASSWORD, null);
        String apiK = sharedPreferences.getString(KEY_APIKEY, null);

        if(apiK == null || username == null || pw == null || apiK.trim().isEmpty() || username.trim().isEmpty() || pw.trim().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            getGames();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getGames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getGames(){
        try {
            sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String apiK = sharedPreferences.getString(KEY_APIKEY, null);

            queue = Volley.newRequestQueue(getApplicationContext());

            String url = URL + URLEncoder.encode(apiK, "UTF-8"); //Change this to try out another URL

            Log.i("URL used: ", url);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            queue.add(req);
        }
        catch (UnsupportedEncodingException e) {

            Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong_with_request), Toast.LENGTH_LONG).show();

            GameRecycleViewAdapter adapter = new GameRecycleViewAdapter(this, new GamesReturn());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //todo handle invalid call -> login
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

        GameRecycleViewAdapter adapter = new GameRecycleViewAdapter(this, new GamesReturn());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResponse(Object response) {
        //todo handle invalid call -> login
        JSONObject jsono = (JSONObject) response;

        Log.i("URL used: ", jsono.toString());

        if(jsono.has("Participants") || jsono.has("Games")){
            handleGames(jsono);
        }
        else{
            Toast.makeText(getApplicationContext(),
                    getText(R.string.something_went_wrong) + " " + getText(R.string.something_went_wrong_with_request),
                    Toast.LENGTH_LONG).show();

            GameRecycleViewAdapter adapter = new GameRecycleViewAdapter(this, new GamesReturn());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void handleGames(JSONObject jsono){
        GamesReturn repo = new Gson().fromJson(jsono.toString(), GamesReturn.class);

        if(repo != null &&
                ((repo.getParticipants() != null && repo.getParticipants().size() > 0)
                        || (repo.getGames() != null && repo.getGames().size() > 0))){

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime time;
                if(repo.getParticipants() != null && repo.getParticipants().size() > 0){
                    for (Participant par:repo.getParticipants()) {
                        String timeTxt = par.getGame().getStartTime();
                        time = LocalDateTime.parse(timeTxt);

                        if(LocalDateTime.now().isBefore(time)){
                            txt_countdown.setText(calculateDateDiff(time));
                            break;
                        }
                    }
                }
                else{
                    for (Game game:repo.getGames()) {
                        String timeTxt = game.getStartTime();
                        time = LocalDateTime.parse(timeTxt);

                        if(LocalDateTime.now().isBefore(time)){
                            txt_countdown.setText(calculateDateDiff(time));
                            break;
                        }
                    }
                }
            }
            else{
                String errorTxt = getString(R.string.insufficient_api_lvl) + " " + getString(R.string.timer_error);
                Toast.makeText(getApplicationContext(), errorTxt, Toast.LENGTH_LONG).show();
            }

            GameRecycleViewAdapter adapter = new GameRecycleViewAdapter(this, repo);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.txt_game_repo_empty), Toast.LENGTH_LONG).show();

            GameRecycleViewAdapter adapter = new GameRecycleViewAdapter(this, new GamesReturn());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public String calculateDateDiff(LocalDateTime dateToCalc){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            LocalDateTime dateNow = LocalDateTime.now();

            LocalDateTime fromTemp = LocalDateTime.from(dateNow);

            long days = fromTemp.until(dateToCalc, ChronoUnit.DAYS);
            fromTemp = fromTemp.plusDays(days);

            long hours = fromTemp.until(dateToCalc, ChronoUnit.HOURS);
            fromTemp = fromTemp.plusHours(hours);

            long minutes = fromTemp.until(dateToCalc, ChronoUnit.MINUTES);

            return days + ":" + hours + ":" + minutes;
        }
        else{
            String errorTxt = getString(R.string.insufficient_api_lvl) + " " + getString(R.string.timer_error);
            Toast.makeText(getApplicationContext(), errorTxt, Toast.LENGTH_LONG).show();
            return "00:00:00";
        }
    }
}