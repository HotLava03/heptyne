package org.mythicmc.heptyne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;

import org.mythicmc.heptyne.util.AuthManager;
import org.mythicmc.heptyne.util.RequestManager;

import java.util.HashMap;
import java.util.Map;

/*
 * If an activity requires a token in order
 * to be accessed, inherit from this activity
 * instead. Remember to always call super.onCreate()
 * in onCreate()'s inheritance.
 */
public class AuthenticatedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if the token is valid.
        // Get the shared preferences to hold local data.
        SharedPreferences pref = getSharedPreferences("org.mythicmc.preferences", Context.MODE_PRIVATE);
        // If it exists or not.
        if (pref.getString("token", null) == null) {
            // Looks like it doesn't exist. Easy to understand we must login first.
            // Create an intent to the main activity (login).
            Intent intent = new Intent(this, MainActivity.class);
            // Start it.
            startActivity(intent);
            // Finish this one and return.
            finish();
            return;
        }

        // So, the token exists, now we check if it's valid.
        // This class handles it all for us. All we must do is use it to listen to requests.
        AuthManager manager = new AuthManager(this) {
            @Override
            public void setServers(Map<String, Boolean> servers) {
                // Call our own setServers, which holds the same purpose.
                AuthenticatedActivity.this.setServers(servers);
            }
        };

        // Create our request manager.
        RequestManager req = new RequestManager(this);
        // Ready the header map.
        Map<String, String> headers = new HashMap<>();
        // Add the authorization header.
        headers.put("Authorization", pref.getString("token", null));
        req.createRequest(Request.Method.GET, "servers", headers, manager, manager);
    }

    // Called when a response is received with all servers. Inherit from this method when needed.
    protected void setServers(Map<String, Boolean> servers) {}
}
