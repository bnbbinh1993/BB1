package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.ChatActivity;
import com.bnb.binh.skyintertainment.models.MSG2;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MSG2Adapter extends RecyclerView.Adapter<MSG2Adapter.ViewHolder> {
    private Context context;
    private List<MSG2> msg2List;


    public MSG2Adapter(Context context, List<MSG2> msg2List) {
        this.context = context;
        this.msg2List = msg2List;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg2_recyclerview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MSG2 chatLists = msg2List.get(i);
        String name = chatLists.getNameSender();
        String url = chatLists.getAvtSender();
        String time = chatLists.getTimestamp();
        String message = chatLists.getMessage();
        final String hisuid = chatLists.getReceiver();
        boolean isSeen  = chatLists.isSeen();


        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(time));
        String dateTime = DateFormat.format("hh:mm aa", calendar).toString();

        if (isSeen == true){
            viewHolder.messageLate.setText(message);
            viewHolder.messageLate.setTextColor(Color.BLACK);
        }else {
            viewHolder.messageLate.setText(message);
        }

        viewHolder.userNameMSG2.setText(name);
        viewHolder.time.setText(dateTime);

        Glide.with(context).load(url).error(R.mipmap.logoavatar).into(viewHolder.avtUserMSG2);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("hisuid",hisuid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return msg2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userNameMSG2;
        private TextView messageLate;
        private TextView time;
        private CircleImageView avtUserMSG2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameMSG2 = itemView.findViewById(R.id.userNameMSG2);
            messageLate = itemView.findViewById(R.id.messageLate);
            time = itemView.findViewById(R.id.time);
            avtUserMSG2 = itemView.findViewById(R.id.avtUserMSG2);
        }
    }
}
