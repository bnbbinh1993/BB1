package com.bnb.binh.skyintertainment.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.MSG1;

import java.util.List;

public class MSG1Adapter extends RecyclerView.Adapter<MSG1Adapter.ViewHolder> {
    private Context context;
    private List<MSG1> msg1List;

    public MSG1Adapter(Context context, List<MSG1> msg1List) {
        this.context = context;
        this.msg1List = msg1List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg1_recyclerview,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            MSG1 msg1 = msg1List.get(i);
            viewHolder.userNameMGG1.setText(msg1.getNameMSG1());
    }

    @Override
    public int getItemCount() {
        return msg1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameMGG1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameMGG1 = itemView.findViewById(R.id.userNameMGG1);
        }
    }
}
