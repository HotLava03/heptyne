package org.mythicmc.heptyne.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return getViewHolder(v);
    }

    // Return the item's layout ID.
    public abstract int getItemLayout();

    // Return the view holder instance.
    public abstract RecyclerViewHolder getViewHolder(View view);
}
