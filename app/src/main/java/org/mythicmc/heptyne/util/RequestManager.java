package org.mythicmc.heptyne.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.mythicmc.heptyne.R;

/*
 * Creates requests to Octyne only.
 * The Octyne URL is set in the config.xml file under
 * /app/src/main/res/values.
 */
public class RequestManager implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String TAG = "RequestMaker";

    private final Context context;
  
    public RequestManager(Context context) {
        this.context = context;
    }

    public void createRequest(int method, String endpoint) {
        // Create a new request queue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Create a new JSON request to enqueue.
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                context.getResources().getString(R.string.octyne_url) + endpoint,
                null,
                this,
                this
        );
        // Add it to the queue.
        queue.add(req);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Error while making a request to Octyne.", error);
    }

    @Override
    public void onResponse(JSONObject response) {
        // TODO: Fire event.
    }


}
