package com.example.akshay.PollingApp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akshay.PollingApp.result_adapter.result_adapter;
import com.example.akshay.PollingApp.result_model.result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tab3Results extends Fragment {
    public interface OnFragmentInteractionListener{
        public void onFragmentInteraction(Uri uri);
    }


    DatabaseReference dbrefergroup;
    DatabaseReference dbreferuser;
    String username = LoginActivity.returnemail();
    ArrayList<result> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.akshay.PollingApp.R.layout.tab3results, container, false);

        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.tab3resultRecyclerView);

        dbreferuser = FirebaseDatabase.getInstance().getReference("User");
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        getData(recyclerView);
    }

    private void getData(RecyclerView recyclerView) {





        final result_adapter adapter = new result_adapter(getContext(), list);




        dbreferuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot i :   dataSnapshot.getChildren()){




                    UserDB  userobj = i.getValue(UserDB.class);
                    // Log.d("asdf",userobj.toString());
                    if(userobj.username.equals(username)){
                        for(String str : userobj.listofgroups){
                            if(!str.equals("demo")){
                            result tempgrp = new result(str);


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
