package be.ucll.java.gip5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText username_field, apiKey_field;
    Button buttonSave;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_APIKEY = "apikey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        username_field = findViewById(R.id.input_username);
        apiKey_field = findViewById(R.id.input_apiKey);

        buttonSave = findViewById(R.id.btn_saveProfile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String apikey = sharedPreferences.getString(KEY_APIKEY, null);

        if(username != null){
            username_field.setText(username);
        }
        if(apikey != null){
            apiKey_field.setText(apikey);
        }

        buttonSave.setOnClickListener(v -> {
            //when clicked on save, put data on sharedpreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username_field.getText().toString());
            editor.putString(KEY_APIKEY, apiKey_field.getText().toString());
            editor.apply();

            finish();
        });
    }
}