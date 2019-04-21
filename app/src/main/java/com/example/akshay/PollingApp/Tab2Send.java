package com.example.akshay.PollingApp;


import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.akshay.PollingApp.group_model.Group;
import com.example.akshay.PollingApp.model.Poll;
import com.example.akshay.PollingApp.send_adapter.SendListAdapter;
import  com.example.akshay.PollingApp.send_model.send;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tab2Send extends Fragment {
    DatabaseReference dbrefergroup;
    DatabaseReference dbreferuser;
    String username = LoginActivity.returnemail();
       ArrayList<send> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.akshay.PollingApp.R.layout.tab2send, container, false);
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(com.example.akshay.PollingApp.R.id.fabsend);





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> responses=new HashMap<>();
                responses.put("demo","0");
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat Time = new SimpleDateFormat("HHmm");
                String currentDate = date.format(new Date());
                String currenttime = Time.format(new Date());
                Log.d("worldcup", "onClick: "+currentDate);
                int hour_asked = (currenttime.charAt(0)-48)*10+   currenttime.charAt(1)-48;
                int  minute_asked = (currenttime.charAt(2)-48)*10+   currenttime.charAt(3)-48;

                String timeperiod  = Tab1Edit.gettime();
                int time_int = Integer.parseInt(timeperiod);


                if(time_int>1440){
                    time_int = 1440;
                }
                int hour = time_int/60;
                int minute = time_int%60;
                int newhour;
                int newminute;
                String Expiry_time ;


                if(hour+hour_asked+(minute+minute_asked)/60>24){
                    newhour = hour+hour_asked+(minute+minute_asked)/60-24;

                    newminute = (minute+minute_asked)%60;
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 1);
                    String newdate = date.format(c.getTime());
                    Expiry_time = newdate;
                    if(newhour/10==0){
                        Expiry_time +="0";

                    }
                    Expiry_time+=newhour;
                    if(newminute/10==0){
                        Expiry_time  += "0";

                        Expiry_time+=newminute;}
                }
                else{
                    newhour = hour+hour_asked+(minute+minute_asked)/60;
                    newminute = (minute+minute_asked)%60;
                    Expiry_time = currentDate;

                    if(newhour/10==0){
                        Expiry_time +="0";

                    }
                    Expiry_time+=newhour;
                    if(newminute/10==0){
                        Expiry_time  += "0";
                        }
                        Expiry_time+=newminute;


                }

                String asked_time = currentDate+currenttime;

                final PollDB pollnew = new PollDB(LoginActivity.returnemail(),Tab1Edit.getquestion(),Tab1Edit.getoptionlist(),asked_time,Expiry_time,responses);

                for(int it=0;it<list.size();it++){
                    if(list.get(it).getChecked()){

                        final int finalIt = it;
                        dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot i : dataSnapshot.getChildren()){
                                    GroupDB groupobj = i.getValue(GroupDB.class);
                                    if(groupobj.gid.equals(list.get(finalIt).getGroupName())){
                                        groupobj.listofpolls.add(pollnew);
                                        GroupDB groupnew = new GroupDB(groupobj.gid,groupobj.userlist,groupobj.listofpolls);
                                        dbrefergroup.child(groupobj.gid).setValue(groupnew);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                getActivity().finish();
            }

        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(com.example.akshay.PollingApp.R.id.tab2sendRecyclerView);

        dbreferuser = FirebaseDatabase.getInstance().getReference("User");
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        getData(recyclerView);
    }

    private void getData(RecyclerView recyclerView) {





        final SendListAdapter adapter = new SendListAdapter(getContext(), list);

        send poll = new send("Group name");


        dbreferuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i :   dataSnapshot.getChildren()){




                    UserDB  userobj = i.getValue(UserDB.class);
                    // Log.d("asdf",userobj.toString());
                    if(userobj.username.equals(username)){
                        for(String str : userobj.listofgroups){
                            if(!str.equals("demo")){
                            send tempgrp = new send(str);


                            list.add(tempgrp);

                            adapter.notifyDataSetChanged();}
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        list.add(poll);
//        list.add(poll);
//        list.add(poll);
//        list.add(poll);
//        list.add(poll);



        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setHasFixedSize(true);
}


}
