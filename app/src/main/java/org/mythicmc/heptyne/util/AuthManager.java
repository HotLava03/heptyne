package org.mythicmc.heptyne.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mythicmc.heptyne.MainActivity;

import java.util.HashMap;
import java.util.Map;

/*
 * Responsible to request servers to Octyne.
 * Mainly so authentication with the token
 * stored in SharedPreferences is checked before
 * accessing any activity that requires authentication.
 */
public class AuthManager implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String TAG = "AuthManager";
    private final Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // Looks like the token is invalid. Login time.
        Intent intent = new Intent(context, MainActivity.class);
        // Start it.
        context.startActivity(intent);
    }

    @Override
    public void onResponse(JSONObject response) {
        // Sounds good. Now create a map with the servers.
        Map<String, Boolean> servers = new HashMap<>();
        try {
            // Get the servers JSON object.
            JSONObject obj = response.getJSONObject("servers");
            // Get the names as a JSON array.
            JSONArray names = obj.names();
            if (names == null) {
                Log.e(TAG, "names is null for some reason.");
                return;
            }
            // Populate servers' map.
            for (int i = 0; i < names.length(); i++) {
                // obj.getInt() == 1 basically transforms a 1/0 boolean to a true/false boolean.
                servers.put(names.get(i).toString(), obj.getInt(names.get(i).toString()) == 1);
            }
            // Ready to call setServers.
            setServers(servers);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get JSON object from response.", e);
            e.printStackTrace();
        }
    }

    // Called when a response is received with all servers. If needed, implement this method.
    public void setServers(Map<String, Boolean> servers) {}
}
