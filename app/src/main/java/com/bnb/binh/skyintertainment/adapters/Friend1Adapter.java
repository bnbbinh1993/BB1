package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.Friends;

import java.util.List;

public class Friend1Adapter extends RecyclerView.Adapter<Friend1Adapter.Viewholder> {
    private Context context;
    private List<Friends> list;

    public Friend1Adapter(Context context, List<Friends> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addfriend_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Friends friends = list.get(position);
        holder.nameFriend1.setText(friends.getNameFriend());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView nameFriend1;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameFriend1 = itemView.findViewById(R.id.nameFriend1);
        }
    }
}
