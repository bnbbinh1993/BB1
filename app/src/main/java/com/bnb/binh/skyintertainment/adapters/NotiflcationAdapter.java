package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.Notiflcation;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotiflcationAdapter extends RecyclerView.Adapter<NotiflcationAdapter.ViewHolder> {
    private Context context;
    private List<Notiflcation> notificationList;

    public NotiflcationAdapter(Context context, List<Notiflcation> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_notiflcation_recyclerview,
                                viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Notiflcation notiflcation = notificationList.get(i);
        viewHolder.tvNotiflcation.setText(notiflcation.getId());
        String hisId= notificationList.get(i).getHisId();
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("Users");
        mData.child(hisId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = ""+dataSnapshot.child("name").getValue()+" ";
                String avt = ""+dataSnapshot.child("avt").getValue();

                Spanned spanned;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    spanned = Html.fromHtml("<font color=\"#FF000000\"><b>"+name+"</b></font>"+notificationList.get(i).getContent(), Html.FROM_HTML_MODE_LEGACY);
                } else {
                    spanned = Html.fromHtml("<font color=\"#FF000000\"><b>"+name+"</b></font>"+notificationList.get(i).getContent());
                }
                viewHolder.tvNotiflcation.setText(spanned);

                Glide.with(context)
                        .load(avt)
                        .centerCrop()
                        .error(R.mipmap.logoavatar)
                        .into(viewHolder.avtUserNotiflcation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotiflcation;
        private CircleImageView avtUserNotiflcation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotiflcation = itemView.findViewById(R.id.tvNotiflcation);
            avtUserNotiflcation = itemView.findViewById(R.id.avtUserNotiflcation);
        }
    }
}
