package org.mythicmc.heptyne;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;
import org.mythicmc.heptyne.recyclerview.RecyclerAdapter;
import org.mythicmc.heptyne.util.RequestManager;

import java.util.HashMap;
import java.util.Map;

public class ServersActivity extends AuthenticatedRecyclerActivity
        implements Response.ErrorListener, Response.Listener<JSONObject> {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.servers, menu);
        return true;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            // Quit and delete the token.
            SharedPreferences pref = getSharedPreferences("org.mythicmc.preferences", Context.MODE_PRIVATE);
            // Send the logout request to Octyne.
            RequestManager manager = new RequestManager(this);
            Map<String, String> headers = new HashMap<>();
            // Authorization header.
            headers.put("Authorization", pref.getString("token", null));
            // Create the request.
            manager.createRequest(Request.Method.POST, "logout", headers, this, this);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("token");
            /*
             * In this case, we must do this sync.
             * Otherwise, we'd still have a token when
             * going to the login activity most likely.
             */
            editor.commit();
            // Move to the login activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.about) {
            // Open the about page.
            Intent intent = new Intent(this, AboutActivity.class);
            // Move to the about activity.
            startActivity(intent);
            // Don't finish.
            return true;
        }
        return false;
    }

    @Override
    protected void onServersReceived(Map<String, Boolean> servers) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public RecyclerAdapter getAdapter() {
        return null;
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public int getRecyclerViewId() {
        return 0;
    }
}
