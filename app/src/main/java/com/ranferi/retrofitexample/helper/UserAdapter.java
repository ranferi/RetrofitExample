package com.ranferi.retrofitexample.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.api.APIService;
import com.ranferi.retrofitexample.api.APIUrl;
import com.ranferi.retrofitexample.model.Message;
import com.ranferi.retrofitexample.model.MessageResponse;
import com.ranferi.retrofitexample.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        final User user = users.get(position);
        holder.textViewName.setText(user.getName());

        holder.imageButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(sContext);
                View promptsView = layoutInflater.inflate(R.layout.dialog_send_message, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(sContext);
                alertDialogBuilder.setView(promptsView);

                final EditText editTextTitle = (EditText) promptsView.findViewById(R.id.editTextTitle);
                final EditText editTextMessage = (EditText) promptsView.findViewById(R.id.editTextMessage);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Enviar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // obteniendo los valores
                                        String title = editTextTitle.getText().toString().trim();
                                        String message = editTextMessage.getText().toString().trim();

                                        // enviar el mensaje
                                        sendMessage(user.getId(), title, message);
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
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

    private void sendMessage(int id, String title, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(sContext);
        progressDialog.setMessage("Envando mensaje...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<MessageResponse> call = service.sendMessage(
                SharedPrefManager.getInstance(sContext).getUser().getId(),
                id, title, message
        );

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                progressDialog.dismiss();
                Toast.makeText(sContext, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(sContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
