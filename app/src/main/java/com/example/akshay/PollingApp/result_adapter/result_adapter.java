package com.example.akshay.PollingApp.result_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshay.PollingApp.GroupDB;
import com.example.akshay.PollingApp.R;
import com.example.akshay.PollingApp.result_model.result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class result_adapter extends RecyclerView.Adapter<result_adapter.MyViewHolder> {

    private Context context;
    private ArrayList<result> list;
    DatabaseReference dbrefergroup;
    ArrayList<String> listofusers = new ArrayList<>();

    public result_adapter(Context context, ArrayList<result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_list_card, viewGroup, false);
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final result poll = list.get(i);

        //Poll p = new Poll("grp"+list.get(i).getGroupName(),"",new ArrayList<Opt>() ,"","","");
//        list.get(i).setGroupName("er"+list.get(i).getGroupName());

        holder.groupname.setText(poll.getGroupname());
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aajtomaja", "onClick :");
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Members");

                dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listofusers.clear();
                        for(DataSnapshot i: dataSnapshot.getChildren()){
                            GroupDB groupobj = i.getValue(GroupDB.class);
                            if(groupobj.getGid().equals(poll.getGroupname()))
                               listofusers = groupobj.getUserlist();

                        }

                            listofusers.remove("demo");






                        final CharSequence[] alertlist = listofusers.toArray(new String[listofusers.size()]);

                        builder.setItems(alertlist, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });
                        AlertDialog alertDialogobject  =builder.create();
                        builder.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupname;
        ImageView avatar;
        CheckBox cb;
        Button send;
        MyViewHolder(@NonNull View view) {
            super(view);

            groupname = view.findViewById(R.id.groupName);
            avatar = view.findViewById(R.id.image);

        }
    }

}
