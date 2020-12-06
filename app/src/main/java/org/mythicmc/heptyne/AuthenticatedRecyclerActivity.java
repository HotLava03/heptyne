package org.mythicmc.heptyne;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.mythicmc.heptyne.recyclerview.RecyclerAdapter;

public abstract class AuthenticatedRecyclerActivity extends AuthenticatedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        RecyclerView recycler = (RecyclerView) findViewById(getRecyclerViewId());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(getAdapter());
    }

    // Return the adapter for this RecyclerView.
    public abstract RecyclerAdapter getAdapter();

    // Return the layout ID holding this activity.
    public abstract int getLayout();

    // Return the RecyclerView's ID.
    public abstract int getRecyclerViewId();
}
