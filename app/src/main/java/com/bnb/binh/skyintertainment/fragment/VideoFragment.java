package com.bnb.binh.skyintertainment.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.activity.RoomChatActivity;
import com.bnb.binh.skyintertainment.adapters.VideoPlayerRecyclerAdapter;
import com.bnb.binh.skyintertainment.custom.VerticalSpacingItemDecorator;
import com.bnb.binh.skyintertainment.custom.VideoPlayerRecyclerView;
import com.bnb.binh.skyintertainment.models.MediaObject;
import com.bnb.binh.skyintertainment.models.WaitingRoom;
import com.bnb.binh.skyintertainment.resoucres.Resources;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.gigamole.library.PulseView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class VideoFragment extends Fragment {

    private View layoutEncontrarPers;
    private ImageView imgAmin1, imgAmin2;
    private Handler handlerIMG;
    private CircleImageView imgLogo;
    private TextView coutDelayTv;
    private PulseView pulseView;
    private boolean roomChat = false;
    private RadioButton btnOderNam;
    private RadioButton btnOderNu;
    private String myId = HomeFragment.mID;
    private Timer timer;
    private TimerTask timerTask;
    private String timeKey ="";
    private int timeDelays = 0;
    private boolean requestCheck = false;


    private VideoPlayerRecyclerView mRecyclerView;

    public VideoFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyclerview);
        pulseView = view.findViewById(R.id.pv);
        coutDelayTv = view.findViewById(R.id.coutDelays);
        btnOderNam = view.findViewById(R.id.btnOderNam);
        btnOderNu = view.findViewById(R.id.btnOderNu);

        // Glide.with(getContext()).load(R.mipmap.deptonglao2).apply(new RequestOptions().centerCrop()).into(imgLogo);
        view.findViewById(R.id.btnBatDau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulseView.startPulse();
                coutDelayTv.setVisibility(View.VISIBLE);
                if (btnOderNam.isChecked() && btnOderNu.isChecked()) {
                    Toast.makeText(getContext(), "Vui chọn đối tượng của bạn!", Toast.LENGTH_SHORT).show();
                } else {
                    if (btnOderNam.isChecked()) {
                        String oder = "nam";
                        String noOder = "nu";
                        setTimer(oder, noOder);
                    } else {
                        String oder = "nu";
                        String noOder = "nam";
                        setTimer(oder, noOder);
                    }
                }

            }
        });

        view.findViewById(R.id.btnDungLai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pulseView.finishPulse();
                timerTask.cancel();
                coutDelayTv.setVisibility(View.GONE);
                timeDelays = 0;
                //cần cái id phongg = thời gian tạo ra nó
                removeRoom();
            }
        });

        return view;
    }

    private void removeRoom() {

    }




    public RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (mRecyclerView != null)
            mRecyclerView.releasePlayer();
        super.onDestroy();
    }


    private void setTimer(final String oder, final String oderFalse) {
        checkRoom(oder, oderFalse);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeDelays++;
                        if (timeDelays > 60) {

                            if (timeDelays % 60 >= 10) {
                                coutDelayTv.setText(timeDelays / 60 + ":" + timeDelays % 60);
                            } else {
                                coutDelayTv.setText(timeDelays / 60 + ":0" + timeDelays % 60);
                            }
                        } else {
                            if (timeDelays % 60 >= 10) {
                                coutDelayTv.setText("00:" + timeDelays % 60);
                            } else {
                                coutDelayTv.setText("00:0" + timeDelays % 60);
                            }
                        }

                        if (timeDelays % 5 == 0) {

                            Log.d("REQEST", "Bắt đầu reqest");
                            //5s request 1 lần
                            if (requestCheck) {
                                requestCheck = false;
                                requestRoom(oderFalse);
                            }
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    private void requestRoom(String oderFalse){
        DatabaseReference request = FirebaseDatabase.getInstance().getReference().child("WaittingRoom");
        request.child(oderFalse).child(timeKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   if (dataSnapshot.hasChild("check")){
                       String data = "" + dataSnapshot.child("myId").getValue();
                       Toast.makeText(getContext(), ""+data, Toast.LENGTH_SHORT).show();
                       if (data != null) {
                           Toast.makeText(getContext(), "Đã ghép cặp thành công ^^"+data, Toast.LENGTH_SHORT).show();
                           showChat(data);
                       }
                   }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkRoom(final String oder, final String oderFalse) {
        final List<WaitingRoom> waitingRoomList = new ArrayList<>();
        waitingRoomList.clear();
        if (!roomChat) {
            //chưa lấy dữ liệu
            //chay code nhu bt
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("WaittingRoom").child(oder);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        WaitingRoom waitingRoom = ds.getValue(WaitingRoom.class);
                        waitingRoomList.add(waitingRoom);
                    }
                    Log.d("SIZE", "Size: " + waitingRoomList.size());
                    if (waitingRoomList.size() == 0) {

                        roomChat = true;
                        //vào phòng chờ
                        timeKey = String.valueOf(System.currentTimeMillis());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("hisId", "");
                        map.put("check", false);
                        map.put("myId", myId);
                        map.put("timeKey", timeKey);

                        DatabaseReference goWaittingRoom = FirebaseDatabase.getInstance().getReference().child("WaittingRoom");
                        goWaittingRoom.child(oderFalse).child(timeKey).setValue(map);
                        // tạo phòng chát và chờ
                        DatabaseReference chatRoom = FirebaseDatabase.getInstance().getReference().child("Room").child(myId);
                        HashMap<String, Object> roomData = new HashMap<>();
                        roomData.put("myId", myId);
                        roomData.put("hisId", "");
                        chatRoom.setValue(roomData);
                    } else {
                        roomChat = true;
                        //ghép cặp mới người đầu tiên vào chờ...
                        for (int i = 0; i < waitingRoomList.size(); i++) {
                            if (!waitingRoomList.get(i).isCheck()) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("WaittingRoom");
                                HashMap<String, Object> update = new HashMap<>();
                                update.put("hisId", myId);
                                update.put("check", true);
                                reference.child(oder).child(waitingRoomList.get(i).getTimeKey()).updateChildren(update);
                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference()
                                        .child("Room");
                                HashMap<String, Object> update2 = new HashMap<>();
                                update2.put("hisId", myId);
                                reference2.child(waitingRoomList.get(i).getMyid()).updateChildren(update2);
                                showChat(waitingRoomList.get(i).getMyid());
                                break;
                            }
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        } else {
            //nếu đnag trong phòng chát thì
            //show phòng đang chát
            DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child(myId);
            mData.child("hisId").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String data = "" + ds.getValue();
                        if (data != null) {
                            Toast.makeText(getContext(), "Đã ghép cặp thành công ^^", Toast.LENGTH_SHORT).show();
                            showChat(data);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        requestCheck = true;
    }

    private void showChat(String hisId) {
        //vào phòng chát
        roomChat = true;
        pulseView.finishPulse();
        timerTask.cancel();
        coutDelayTv.setVisibility(View.GONE);
        timeDelays = 0;
        removeRoom();
        Toast.makeText(getContext(), "Đã vào phòng chát...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), RoomChatActivity.class));

    }





    //chua can dung toi
//    private void startTask() {
//        //        handlerIMG = new Handler();
////        layoutEncontrarPers= view.findViewById(R.id.layoutpersion);
////        imgLogo= view.findViewById(R.id.imgLogo);
////        imgAmin1= view.findViewById(R.id.imgAmin1);
////        imgAmin2= view.findViewById(R.id.imgAmin2);
//        runnable.run();
//        layoutEncontrarPers.setVisibility(View.VISIBLE);
//    }
//
//    private void stopTask() {
//        handlerIMG.removeCallbacks(runnable);
//        layoutEncontrarPers.setVisibility(View.GONE);
//    }
//
//
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            imgAmin1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700).withEndAction(new Runnable() {
//                @Override
//                public void run() {
//                    imgAmin1.setScaleX(1f);
//                    imgAmin1.setScaleY(1f);
//                    imgAmin1.setAlpha(1f);
//                }
//            });
//
//            imgAmin2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(500).withEndAction(new Runnable() {
//                @Override
//                public void run() {
//                    imgAmin2.setScaleX(1f);
//                    imgAmin2.setScaleY(1f);
//                    imgAmin2.setAlpha(1f);
//                }
//            });
//            handlerIMG.postDelayed(runnable, 1000);
//        }
//    };
//
//    private void initRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
//        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
//        mRecyclerView.addItemDecoration(itemDecorator);
//
//        ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>(Arrays.asList(Resources.MEDIA_OBJECTS));
//        mRecyclerView.setMediaObjects(mediaObjects);
//        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(mediaObjects, initGlide());
//        mRecyclerView.setAdapter(adapter);
//    }


}