package be.ucll.java.gip5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class GameDetailActivity extends AppCompatActivity {

    int gameId;
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

        gameId = getIntent().getIntExtra("gameid", -1);
        if(gameId < 0){
            Toast.makeText(getApplicationContext(), getString(R.string.faulty_gameid), Toast.LENGTH_LONG).show();
            finish();
        }

        DetailsFragment detailsFrag = new DetailsFragment(gameId);
        OverviewFragment overviewFrag = new OverviewFragment(gameId);

        getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, detailsFrag).commit();

        detailsBtn.setOnClickListener(v -> {
            //todo: change style of button
            getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, detailsFrag).commit();
        });

        overviewBtn.setOnClickListener(v -> {
            //todo: change style of button
            getSupportFragmentManager().beginTransaction().replace(R.id.gameDetailFragmentHolder, overviewFrag).commit();
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
}