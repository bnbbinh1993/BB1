package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.ChatActivity;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.models.ModelUser;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {
    private Context context;
    private List<ModelUser> users;

    public AdapterUser(Context context, List<ModelUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_newfriend_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userImage =users.get(position).getAvt();
        String userName =users.get(position).getName();
        final String hisuid =users.get(position).getId();

        holder.nameUser.setText(userName);
        Glide.with(context).load(userImage).error(R.mipmap.logoavatar).into(holder.mAvatar);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent  intent = new Intent(context, ChatActivity.class);
//                intent.putExtra("hisuid",hisuid);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("id",hisuid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mAvatar;
        private TextView nameUser;
        private TextView emailUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatar =itemView.findViewById(R.id.avtFriend1);
            nameUser =itemView.findViewById(R.id.nameFriend2);

        }
    }
}
