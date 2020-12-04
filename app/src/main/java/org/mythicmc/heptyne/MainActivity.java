package org.mythicmc.heptyne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.mythicmc.heptyne.util.RequestManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private AppCompatEditText username, password;
    private TextView alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there's a token yet.
        // Get the shared preferences to hold local data.
        SharedPreferences pref = getSharedPreferences("org.mythicmc.preferences", Context.MODE_PRIVATE);
        // If it exists or not.
        if (pref.getString("token", null) != null) {
            // Looks like it exists. Create an intent and go to the servers activity.
            moveToServers();
            return;
        }
        // It's safe to show content now.
        setContentView(R.layout.activity_main);
        // Get the login button in order to register the click listener.
        Button login = (Button) findViewById(R.id.login);
        // Store the edit texts as they are used across methods.
        username = (AppCompatEditText) findViewById(R.id.username);
        password = (AppCompatEditText) findViewById(R.id.password);
        // Same for the alert TextView.
        alerts = (TextView) findViewById(R.id.login_alerts);
        // Register the login button's listener.
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Listen to the login button.
        // Reset old alerts and colors.
        setRed(username, false);
        setRed(password, false);
        // Clear the text.
        alerts.setText("");
        // Check if the EditTexts meet the requirements.
        if (username.getText().toString().isEmpty()) { // #getText isn't null, as the EditText is always set.
            setRed(username, true);
            alerts.setText(R.string.username_required);
        } else if (password.getText().toString().isEmpty()) { // #getText isn't null, as the EditText is always set.
            setRed(password, true);
            alerts.setText(R.string.password_required);
        } else {
            // They meet the requirements.
            // Create a new request manager for Octyne requests.
            RequestManager manager = new RequestManager(this);
            // Ready the header map.
            Map<String, String> headers = new HashMap<>();
            headers.put("Username", username.getText().toString());
            headers.put("Password", password.getText().toString());
            // Create a new request, in this case, to login.
            manager.createRequest(Request.Method.GET, "login", headers, this, this);
        }
    }

    // The warnings aren't to worry about.
    @SuppressLint("RestrictedApi")
    private void setRed(AppCompatEditText editText, boolean red) {
        // Shorter way to quickly set the tint of the EditTexts.
        if (red)
            editText.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        else
            editText.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
    }

    // Listen to the login request.
    @Override
    public void onResponse(JSONObject response) {
        // Verify if Octyne responds with a token.
        if (!response.has("token")) {
            // Looks like it didn't, so the credentials are wrong.
            setRed(username, true);
            setRed(password, true);
            alerts.setText(R.string.invalid_username_password);
            return;
        }

        try {
            // Get the shared preferences to hold local data.
            SharedPreferences pref = getSharedPreferences("org.mythicmc.preferences", Context.MODE_PRIVATE);
            // Get the editor.
            SharedPreferences.Editor editor = pref.edit();
            // Make the necessary changes.
            editor.putString("token", response.getString("token"));
            Toast.makeText(this, response.getString("token"), Toast.LENGTH_SHORT).show();
            // Apply the change async.
            editor.apply();
        } catch (JSONException e) {
            // This happens when Octyne responds with malformed JSON.
            Toast.makeText(this, getResources().getString(R.string.invalid_json),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.networkResponse.statusCode == 401) {
            // Looks like it went wrong, so there's invalid credentials.
            setRed(username, true);
            setRed(password, true);
            alerts.setText(R.string.invalid_username_password);
        } else {
            // Don't really control anything else...
            alerts.setText(R.string.unknown_error);
        }
    }

    private void moveToServers() {
        // Create an intent to the servers activity.
        Intent intent = new Intent(this, ServersActivity.class);
        // Start it.
        startActivity(intent);
        // Finish this one.
        finish();
    }
}