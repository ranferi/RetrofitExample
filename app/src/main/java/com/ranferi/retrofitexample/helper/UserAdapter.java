package com.ranferi.retrofitexample.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context sContext;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.sContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageButton imageButtonMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageButtonMessage = (ImageButton)  itemView.findViewById(R.id.imageButtonMessage);
        }
    }
}
