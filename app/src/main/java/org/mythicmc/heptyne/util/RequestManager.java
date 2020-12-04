package org.mythicmc.heptyne.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.mythicmc.heptyne.R;

import java.util.HashMap;
import java.util.Map;

/*
 * Creates requests to Octyne only.
 * The Octyne URL is set in the config.xml file under
 * /app/src/main/res/values.
 */
public class RequestManager {
    private static final String TAG = "RequestMaker";

    private final Context context;

    public RequestManager(Context context) {
        this.context = context;
    }

    public void createRequest(
            int method,
            String endpoint,
            Map<String, String> headers,
            Response.Listener<JSONObject> listener,
            Response.ErrorListener errorListener
    ) {
        // Create a new request queue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Create a new JSON request to enqueue.
        JsonObjectRequest req = new JsonObjectRequest(
                method,
                context.getResources().getString(R.string.octyne_url) + endpoint,
                null,
                listener,
                errorListener
        ) {
            // JsonObjectRequest anonymous class for headers.
            @Override
            public Map<String, String> getHeaders() {
                return headers; // Return the headers passed in the createRequest method.
            }
        };
        // Add it to the queue.
        queue.add(req);
    }

}
