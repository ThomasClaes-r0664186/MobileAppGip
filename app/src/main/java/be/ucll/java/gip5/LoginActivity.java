package be.ucll.java.gip5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText username_field, apiKey_field;
    Button buttonSave;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        username_field = findViewById(R.id.input_username);
        apiKey_field = findViewById(R.id.input_apiKey);

        buttonSave = findViewById(R.id.btn_saveProfile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String apikey = sharedPreferences.getString(KEY_PASSWORD, null);

        if(username != null){
            username_field.setText(username);
        }
        if(apikey != null){
            apiKey_field.setText(apikey);
        }
        if(username != null && apikey != null && !username.trim().isEmpty() && !apikey.trim().isEmpty()){
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back);
        }

        buttonSave.setOnClickListener(v -> {
            //when clicked on save, put data on sharedpreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(username_field.getText() == null
                    || username_field.getText().toString().isEmpty()
                    || apiKey_field.getText() == null
                || apiKey_field.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getText(R.string.error_login_page), Toast.LENGTH_LONG).show();
            }
            else{
                //todo: make call here and save api key, if not correct -> toast incorrect & stay on page
                editor.putString(KEY_USERNAME, username_field.getText().toString());
                editor.putString(KEY_PASSWORD, apiKey_field.getText().toString());
                editor.apply();
                finish();
            }
        });
    }
    //todo: make call to api to confirm identity and receice api key.
}