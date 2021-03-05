package be.ucll.java.gip5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import java.util.Objects;

import be.ucll.java.gip5.model.Game;
import be.ucll.java.gip5.model.Participant;

public class GameDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button detailsBtn, overviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        detailsBtn = findViewById(R.id.buttonDetails);
        overviewBtn = findViewById(R.id.buttonOverview);

        String partJson = getIntent().getStringExtra("partString");
        String gameJson = getIntent().getStringExtra("gameString");

        Participant participant = new Participant();

        if(partJson != null){
            participant = new Gson().fromJson(partJson, Participant.class);
        }else if(gameJson != null){
            participant = new Participant(new Gson().fromJson(gameJson, Game.class));
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.faulty_gameid), Toast.LENGTH_LONG).show();
            finish();
        }

        DetailsFragment detailsFragment = new DetailsFragment(participant);
        OverviewFragment overviewFragment = new OverviewFragment(participant.getGame().getId());

        setDetailsClicked();
        getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, detailsFragment).commit();

        detailsBtn.setOnClickListener(v -> {
            setDetailsClicked();
            getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, detailsFragment).commit();
        });

        overviewBtn.setOnClickListener(v -> {
            setOverviewClicked();
            getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, overviewFragment).commit();
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back);
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

    public void setDetailsClicked(){
        detailsBtn.setBackground(getResources().getDrawable(R.drawable.btn_selected_left));
        detailsBtn.setTextColor(getResources().getColor(R.color.main_color_red));

        overviewBtn.setBackground(getResources().getDrawable(R.drawable.btn_notselected_right));
        overviewBtn.setTextColor(getResources().getColor(R.color.white));
    }

    public void setOverviewClicked(){
        detailsBtn.setBackground(getResources().getDrawable(R.drawable.btn_notselected_left));
        detailsBtn.setTextColor(getResources().getColor(R.color.white));

        overviewBtn.setBackground(getResources().getDrawable(R.drawable.btn_selected_right));
        overviewBtn.setTextColor(getResources().getColor(R.color.main_color_red));
    }

}