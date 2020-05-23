package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.ModelChat;
import com.bumptech.glide.Glide;

    import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Viewholder> {
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right,parent,false);
            return new Viewholder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left,parent,false);
            return new Viewholder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String message = chatList.get(position).getMessage();
        String timeStamp = chatList.get(position).getTimestamp();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("hh:mm aa",cal).toString();


        holder.messageTv.setText(message);
        holder.timeTv.setText(dateTime);

        Glide.with(context)
                .load(imageUrl)
                .error(R.mipmap.backgroud2)
                .into(holder.profileTv);

        if (position==chatList.size()-1){
            if (chatList.get(position).isSeen()){
                holder.isSeenTv.setText("đã xem.");
            }else {
                holder.isSeenTv.setText("đã gửi.");
            }
        }else {
            holder.isSeenTv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getSender().equals(HomeFragment.mID)){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }

    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private TextView messageTv,timeTv,isSeenTv;
        private CircleImageView profileTv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            profileTv = itemView.findViewById(R.id.avtProfileTv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);
        }
    }
}
