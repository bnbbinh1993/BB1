package com.bnb.binh.skyintertainment.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.NotiflcationAdapter;
import com.bnb.binh.skyintertainment.models.Notiflcation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotiflcationFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Notiflcation> list;
    private NotiflcationAdapter adapter;

    public NotiflcationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_notiflcation, container, false);
        list = new ArrayList<>();
        adapter= new NotiflcationAdapter(getContext(),list);
        setData();
        recyclerView = v.findViewById(R.id.recyclerviewNotiflcation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void setData() {
        list.add(new Notiflcation("An An đã thích bài viết của bạn."));
        list.add(new Notiflcation("Hoàng Dũng đã thích bài viết của bạn."));
        list.add(new Notiflcation("Xuân Luật đã thích bài viết của bạn."));
        list.add(new Notiflcation("Xuân Luật đã bình luật về bài viết của bạn."));
        list.add(new Notiflcation("Hồng Ngọc đã bày tỏ cảm xúc về bình luận của bạn của bạn."));
        list.add(new Notiflcation("Xuân luật đã thích bài viết của bạn."));
        list.add(new Notiflcation("Hồng Ngọc đã thích bài viết của bạn."));
    }

}
