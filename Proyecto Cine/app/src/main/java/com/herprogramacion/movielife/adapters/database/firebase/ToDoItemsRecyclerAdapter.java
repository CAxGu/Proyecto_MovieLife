package com.herprogramacion.movielife.adapters.database.firebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.models.ToDoItem;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoItemsRecyclerAdapter extends FirebaseRecyclerAdapter<ToDoItem, ToDoItemsRecyclerAdapter.ToDoItemViewHolder> {

    public ToDoItemsRecyclerAdapter(int modelLayout, DatabaseReference ref) {
        super(ToDoItem.class, modelLayout, ToDoItemsRecyclerAdapter.ToDoItemViewHolder.class, ref);
    }

    @Override
    public ToDoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
        return new ToDoItemViewHolder(view);
    }

    @Override
    protected void populateViewHolder(ToDoItemViewHolder holder, ToDoItem item, int position) {
        String itemDescription = item.getItem();
        String username = item.getUsername();

        holder.txtItem.setText(itemDescription);
        holder.txtUser.setText(username);
        if (item.isCompleted()) {
            holder.imgDone.setVisibility(View.VISIBLE);
        } else {
            holder.imgDone.setVisibility(View.INVISIBLE);
        }
    }

    class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.txtItem) TextView txtItem;
        @Bind(R.id.txtUser) TextView txtUser;
        @Bind(R.id.imgDone) ImageView imgDone;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(final View view) { //reference.updateChildren(updates);
            int position = getAdapterPosition();
            ToDoItem currentItem = (ToDoItem)getItem(position);
            DatabaseReference reference = getRef(position);
            boolean completed = !currentItem.isCompleted();

            currentItem.setCompleted(completed);
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("completed", completed);
            //reference.updateChildren(updates);
            reference.updateChildren(updates, new DatabaseReference.CompletionListener(){
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if(error == null)
                        Toast.makeText(view.getContext(), "Update OK", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public boolean onLongClick(final View view) { //reference.removeValue();
            int position = getAdapterPosition();
            DatabaseReference reference = getRef(position);
            //reference.removeValue();
            reference.removeValue(new DatabaseReference.CompletionListener() {
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if(error == null)
                        Toast.makeText(view.getContext(), "Delete OK", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
    }
}

