package com.bnb.binh.skyintertainment.activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.Notiflcation;

import java.util.List;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notiflcation notiflcation = notificationList.get(i);
        viewHolder.tvNotiflcation.setText(notiflcation.getName());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotiflcation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotiflcation = itemView.findViewById(R.id.tvNotiflcation);
        }
    }
}
