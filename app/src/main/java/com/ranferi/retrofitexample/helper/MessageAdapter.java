package com.ranferi.retrofitexample.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;
    private Context sContext;

    public MessageAdapter(List<Message> messages, Context sContext) {
        this.messages = messages;
        this.sContext = sContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_messages, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message message = messages.get(position);
        holder.textViewName.setText(message.getFrom());
        holder.textViewTitle.setText(message.getTitle());
        holder.textViewMessage.setText(message.getMessage());
        holder.textViewTime.setText(message.getSent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewTitle;
        public TextView textViewMessage;
        public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewNameMessage);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitleMessage);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }
}
