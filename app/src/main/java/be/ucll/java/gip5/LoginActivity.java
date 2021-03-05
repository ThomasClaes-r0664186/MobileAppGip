package be.ucll.java.gip5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import be.ucll.java.gip5.model.Apikey;

public class LoginActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    Toolbar toolbar;

    private RequestQueue queue;

    EditText username_field, apiKey_field;
    Button buttonSave;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_APIKEY = "apikey";
    private static final String URL = "http://ucll-team5-gip5-web.eu-west-1.elasticbeanstalk.com/person/apikey";

    private String usedUsername, usedPassword;

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
        String pw = sharedPreferences.getString(KEY_PASSWORD, null);
        String apiK = sharedPreferences.getString(KEY_APIKEY, null);

        if(username != null){
            username_field.setText(username);
        }
        if(pw != null){
            apiKey_field.setText(pw);
        }
        if(username != null && pw != null && apiK != null && !apiK.trim().isEmpty() && !username.trim().isEmpty() && !pw.trim().isEmpty()){
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back);
        }

        buttonSave.setOnClickListener(v -> {
            //when clicked on save, put data on sharedpreferences
            if(username_field.getText() == null
                    || username_field.getText().toString().isEmpty()
                    || apiKey_field.getText() == null
                || apiKey_field.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getText(R.string.error_login_page), Toast.LENGTH_LONG).show();
            }
            else{
                usedUsername = username_field.getText().toString();
                usedPassword = apiKey_field.getText().toString();

                confirmIdentity(usedUsername, usedPassword);
            }
        });
    }

    public void confirmIdentity(String username, String password){
        queue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {
            json.put(KEY_USERNAME, username);
            json.put(KEY_PASSWORD, password);
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), getString(R.string.error_sending_login), Toast.LENGTH_LONG).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, json, this, this);

        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.error_sending_login), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {

        // Cast into Gson JSONObject
        JSONObject jsono = (JSONObject) response;

        Apikey apiKey = new Gson().fromJson(jsono.toString(), Apikey.class);

        if(jsono.has("apikey")){
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Log.d("Requested_api_key", apiKey.getApikey());

            editor.putString(KEY_USERNAME, usedUsername);
            editor.putString(KEY_PASSWORD, usedPassword);
            editor.putString(KEY_APIKEY, apiKey.getApikey());
            editor.apply();

            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_sending_login), Toast.LENGTH_LONG).show();
        }
    }
}