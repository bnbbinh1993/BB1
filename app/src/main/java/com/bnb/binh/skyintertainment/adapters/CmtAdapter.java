package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.ProfileActivity;
import com.bnb.binh.skyintertainment.models.Cmt;
import com.bnb.binh.skyintertainment.models.ModelUser;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CmtAdapter extends RecyclerView.Adapter<CmtAdapter.Viewholder> {
    private Context context;
    private List<Cmt> list;


    public CmtAdapter(Context context, List<Cmt> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.item_cmt_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        Cmt cmt = list.get(position);

        final String id = cmt.getHisId();
        String time = cmt.getTimeCmt();
        String cmtData = cmt.getCmt();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(time));
        String dateTime = DateFormat.format("hh:mm aa",cal).toString();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(id).child("name").getValue().toString();
                String avt = dataSnapshot.child(id).child("avt").getValue().toString();

                holder.name.setText(name);
                Glide.with(context).load(avt).into(holder.avt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.cmt.setText(cmtData);
        holder.time.setText(dateTime);


        if (id.equals("t0D8H6PMCGbTFaJ1Ohrta5S5z4i1")) {
            holder.checkUser.setVisibility(View.VISIBLE);
        } else {
            holder.checkUser.setVisibility(View.GONE);
        }
        holder.checkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Tích xanh chính chủ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("id",id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.name.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("id",id);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private CircleImageView avt;
        private CircleImageView checkUser;
        private TextView name;
        private TextView time;
        private TextView cmt;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            avt=itemView.findViewById(R.id.avtUserCmt);
            name=itemView.findViewById(R.id.nameUserCmt);
            time=itemView.findViewById(R.id.timeCmt);
            cmt=itemView.findViewById(R.id.cmt);
            checkUser=itemView.findViewById(R.id.checkUser);
        }
    }
}
