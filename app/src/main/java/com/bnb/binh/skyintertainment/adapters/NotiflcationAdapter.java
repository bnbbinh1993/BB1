package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.CommentActivity;
import com.bnb.binh.skyintertainment.fragment.HomeFragment;
import com.bnb.binh.skyintertainment.models.Notiflcation;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotiflcationAdapter extends RecyclerView.Adapter<NotiflcationAdapter.ViewHolder> {
    private Context context;
    private List<Notiflcation> notificationList;
    private static final int NTF_REP = 0;
    private static final int NTF_NO_REP = 1;

    public NotiflcationAdapter(Context context, List<Notiflcation> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == NTF_NO_REP){
            View view = LayoutInflater.from(context).inflate(R.layout.item_notiflcation_recyclerview,viewGroup,false);
            return new NotiflcationAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_notification_reyclerview_rep,viewGroup,false);
            return new NotiflcationAdapter.ViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Notiflcation notiflcation = notificationList.get(i);
        final String hisId= notiflcation.getHisId();
        final String key =notiflcation.getKey();
        final String id = notiflcation.getId();

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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("key",key );
                intent.putExtra("keyId", hisId);

                HashMap<String,Object> map =new HashMap<>();
                map.put("reply",true);
                DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Notification");
                reference.child(HomeFragment.mID).child(id).updateChildren(map);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(!notificationList.get(position).isReply()){
            return NTF_NO_REP;
        }else {
            return NTF_REP;
        }
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
