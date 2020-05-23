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
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.models.Friends;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Friend2Adapter extends RecyclerView.Adapter<Friend2Adapter.Viewholder> {
    private Context context;
    private List<Friends> list;

    public Friend2Adapter(Context context, List<Friends> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newfriend_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.nameFriend2.setText(list.get(position).getNameFriend());
        Glide.with(context)
                .load(list.get(position).getAvtFirend())
                .error(R.mipmap.logoavatar)
                .into(holder.avtFriend1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView nameFriend2;
        private CircleImageView avtFriend1;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameFriend2 = itemView.findViewById(R.id.nameFriend2);
            avtFriend1 = itemView.findViewById(R.id.avtFriend1);
        }
    }
}
