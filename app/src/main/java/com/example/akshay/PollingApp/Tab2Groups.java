package com.example.akshay.PollingApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.akshay.PollingApp.group_adapter.GroupListAdapter;
import com.example.akshay.PollingApp.group_model.Group;
import com.example.akshay.PollingApp.model.Poll;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tab2Groups extends Fragment {
DatabaseReference dbreferuser;
DatabaseReference dbrefergroup;
 String username = LoginActivity.returnemail();
boolean flag=false;
private String m_text;
private  String g_id;
boolean flag1=false;
    public interface OnFragmentInteractionListener{
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.akshay.PollingApp.R.layout.tab2groups, container, false);
        FloatingActionButton fab =  rootView.findViewById(R.id.fabgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                builder.setTitle("New Group Code");

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        g_id = input.getText().toString();

                        if(!g_id.equals(null)){

                            dbrefergroup.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot i: dataSnapshot.getChildren()){
                                        GroupDB groupobj = i.getValue(GroupDB.class);

                                        if(g_id.equals(groupobj.gid)){
//                                            Toast.makeText(getActivity(), "You have entered a pre existing ID Try another one ", Toast.LENGTH_SHORT).show();
                                            flag=true;
                                            break;
                                        }

                                    }
                                    if(!flag){

                                        Log.d("rachu", g_id);

                                        ArrayList<String> listofusers = new ArrayList<>();
                                        listofusers.add(LoginActivity.returnemail());
                                        listofusers.add("demo");
                                        final ArrayList<String> listofoptions = new ArrayList<>();
                                        listofoptions.add("Sample Option 1");
                                        listofoptions.add("Sample Option 2");
                                        HashMap<String,String>responses =new HashMap<>();
                                        responses.put("demo","0");

                                        PollDB polldb = new PollDB(LoginActivity.returnemail(),"This is a sample question",listofoptions,"123456789123","123456789123",responses);
                                        ArrayList<PollDB> polllist = new ArrayList<>();
                                        polllist.add(polldb);

                                        GroupDB groupdb = new GroupDB(g_id,listofusers,polllist);
                                        dbrefergroup.child(g_id).setValue(groupdb);

                                        dbreferuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot i : dataSnapshot.getChildren()){

                                                    UserDB userobj = i.getValue(UserDB.class);

                                                    if(userobj.username.equals(username)){
                                                        ArrayList<String> locallistofgroups = userobj.listofgroups;
                                                        locallistofgroups.add(g_id);

                                                        UserDB userdb = new UserDB(userobj.username,locallistofgroups,userobj.getPassword(),userobj.getRollno());
                                                        dbreferuser.child(userobj.username).setValue(userdb);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                    flag=false;

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog alertDialog = builder.create();

                alertDialog.getWindow().setLayout(600, 400);


                builder.show();




            }
        });
        return rootView;
    }
    @Override
    public  void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(com.example.akshay.PollingApp.R.id.tab2groupRecyclerView);
        dbreferuser = FirebaseDatabase.getInstance().getReference("User");
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        getData(recyclerView,view);

    }
    ArrayList<String> listofusersforaddcode = new ArrayList<>();
    ArrayList<PollDB> listofpollsforaddcode = new ArrayList<>();
    String groupidforaddcode;
     private void getData(RecyclerView recyclerView,View view){







//
//
//        Group group = new Group("Group Name");
//        String username2 = "malav2";
//        ArrayList<String> hm = new ArrayList<>();
//        hm.add("CS321");
//        hm.add("HS311");
//        hm.add("CS322");
//        hm.add("MA312");
//
//
//        UserDB userdb = new UserDB(username2,hm);
////        Log.d("asdf");
//        dbreferuser.child(username2).setValue(userdb);
//
//        String username1 = "garg2";
//        ArrayList<String> hm1 = new ArrayList<>();
//        hm1.add("CS321");
//        hm1.add("HS311");
//        hm1.add("CS322");
//        hm1.add("MA312");
//        UserDB userdb1 = new UserDB(username1,hm1);
//        dbreferuser.child(username1).setValue(userdb1);

        final ArrayList<Group> list = new ArrayList<>();
        final GroupListAdapter adapter = new GroupListAdapter(getContext(),list);
        Log.d("ramukaka", "onDataChange: ");
        dbreferuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("aajabeta", "onDataChange: ");
                list.clear();
                for(DataSnapshot i :   dataSnapshot.getChildren()){
                    Log.d("chaumu", i.toString());




                    UserDB  userobj = i.getValue(UserDB.class);



//                    ArrayList<Group> locallist = new ArrayList<>();
                    // Log.d("asdf",userobj.toString());
                    if(userobj.username.equals(username)){
                        Log.d("trymytry",  username);
                        for(String str : userobj.listofgroups){
                            if(!str.equals("demo")){
                            Group tempgrp = new Group(str);





                            list.add(tempgrp);
                            adapter.notifyDataSetChanged();}
                         //   Log.d("kkrjeetgayi", tempgrp.toString());

                        }

                        Log.d("kkrjeetgayi", list.toString());


                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ImageButton imgbtn = view.findViewById(R.id.imagejoingroup);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                builder.setTitle("ADD GROUP CODE");

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_text = input.getText().toString();


                    if(!m_text.equals(null)) {

                        dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                listofusersforaddcode.clear();
                                listofpollsforaddcode.clear();
                                for (DataSnapshot i : dataSnapshot.getChildren()) {
                                    GroupDB groupobj = i.getValue(GroupDB.class);
                                    if (groupobj.gid.equals(m_text)) {
                                        flag1=true;
                                        listofusersforaddcode = groupobj.userlist;
                                        listofpollsforaddcode = groupobj.listofpolls;
                                        groupidforaddcode = groupobj.gid;
                                    }

                                }
                                if(!flag1){
                                    Toast.makeText(getContext(), "You have entered a Wrong ID ", Toast.LENGTH_LONG).show();
                                }
                                else{




                                     dbreferuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot i : dataSnapshot.getChildren()){
                                                    UserDB userobj = i.getValue(UserDB.class);
                                                    if(userobj.username.equals(LoginActivity.returnemail())){
                                                        if(!userobj.listofgroups.contains(groupidforaddcode)){
                                                            listofusersforaddcode.add(LoginActivity.returnemail());
                                                            GroupDB groupnew = new GroupDB(groupidforaddcode,listofusersforaddcode,listofpollsforaddcode);
                                                            dbrefergroup.child(groupidforaddcode).setValue(groupnew);
                                                            userobj.listofgroups.add(groupidforaddcode);
                                                            UserDB usernew = new UserDB(userobj.username,userobj.listofgroups,userobj.getPassword(),userobj.getRollno());
                                                            dbreferuser.child(userobj.username).setValue(usernew);
                                                            break;
                                                        }
                                                    }
                                                }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError databaseError) {

                                         }
                                     });

                                }
                                flag1=false;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }




                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog alertDialog = builder.create();

                alertDialog.getWindow().setLayout(600, 400);


                builder.show();



//                if(m_text.length()>0){
//                    final DatabaseReference dbreferlcoal;
//                    dbreferlcoal = FirebaseDatabase.getInstance().getReference("group");
//                    dbreferlcoal.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for(DataSnapshot i : dataSnapshot.getChildren()){
//                                    GroupDB groupobj = i.getValue(GroupDB.class);
//
//                                    if(groupobj.gid==m_text){
//
//
//
//                                        break;
//                                    }
//                                }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
            }
        });

//        list.add(group);
//        list.add(group);
//        list.add(group);
//        list.add(group);
//        list.add(group);

        Log.d("asdf",list.toString());



        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

    }
}
