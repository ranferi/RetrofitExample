package com.ranferi.retrofitexample.helper;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context sContext;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.sContext = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
