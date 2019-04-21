package com.example.akshay.PollingApp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.PollingApp.GroupDB;
import com.example.akshay.PollingApp.LoginActivity;
import com.example.akshay.PollingApp.PollDB;
import com.example.akshay.PollingApp.R;
import com.example.akshay.PollingApp.group_model.Group;
import com.example.akshay.PollingApp.model.Opt;
import com.example.akshay.PollingApp.model.Poll;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollListAdapter extends RecyclerView.Adapter<PollListAdapter.MyViewHolder> {

    public Context context;
    private ArrayList<Poll> list;
    DatabaseReference dbrefergroup;
    ArrayList<String>localuserlist = new ArrayList<>();
    ArrayList<PollDB> localpolllist = new ArrayList<>();
    String localuser;

    ArrayList<String> resultlist = new ArrayList<>();
    public PollListAdapter(Context context, ArrayList<Poll> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.poll_list_card, viewGroup, false);
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        final Poll poll = list.get(i);
        holder.groupname.setText(poll.getGroupName());
        holder.question.setText(poll.getQuestion());
        holder.postername.setText(poll.getAsker());
        String posted = "Posted: "+poll.getTimestamp().substring(0,4)+"-"+poll.getTimestamp().substring(4,6)+"-"+
        poll.getTimestamp().substring(6,8)+" "+
                poll.getTimestamp().substring(8,10)+":"+poll.getTimestamp().substring(10,12);
        final String expire = "Expires: "+poll.getExpiryDate().substring(0,4)+"-"+ poll.getExpiryDate().substring(4,6)+"-"+
                poll.getExpiryDate().substring(6,8)+
                " "+

               poll.getExpiryDate().substring(8,10)+":"+poll.getExpiryDate().substring(10,12);
        holder.askedtime.setText(posted);
        holder.expirytime.setText(expire);
        final int index = i;
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "OnClick " + index, Toast.LENGTH_SHORT).show();
            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    List<Opt> listofoptions = poll.getOptions();
                    String ansswerString="";
                    boolean flag = false;
                    for(int it=0;it<listofoptions.size();it++){
                        if(listofoptions.get(it).getCheckBox()){
                            flag=true;
                            ansswerString = ansswerString + Integer.toString(it);
                        }
                    }

                final String finalAnsswerString = ansswerString;

            if(flag){
                dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat Time = new SimpleDateFormat("HHmm");
                            String currentDate = date.format(new Date());
                            String currenttime = Time.format(new Date());
                            String finaltime = currentDate+currenttime;
                            Log.d("kaunhai", "onDataChange: "+finaltime);
                            Log.d("kaunhai", "onDataChange: "+poll.getExpiryDate());

                            int a =  poll.getExpiryDate().compareTo(finaltime);
                            Log.d("kaunhai", "onDataChange: "+Integer.toString(a));
                            if(a>=1){
                                for(DataSnapshot i : dataSnapshot.getChildren()){
                                    GroupDB groupobj = i.getValue(GroupDB.class);
                                    if(groupobj.getGid().equals(poll.getGroupName())){

                                        ArrayList<PollDB> locallistofpolls = new ArrayList<>();
                                        locallistofpolls = groupobj.getListofpolls();
                                        for(int it=0;it<locallistofpolls.size();it++){
                                            if(locallistofpolls.get(it).getQuestions().equals(poll.getQuestion())){
                                                    groupobj.getListofpolls().get(it).getResponses().put(LoginActivity.returnemail(), finalAnsswerString);
                                                    GroupDB groupnew  = new GroupDB(groupobj.getGid(),groupobj.getUserlist(),groupobj.getListofpolls());
                                                    dbrefergroup.child(groupobj.getGid()).setValue(groupnew);
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "Quiz is over  Can't submit", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

            }
            else{
                Toast.makeText(getContext(), "Select atleast one answer", Toast.LENGTH_SHORT).show();
            }
            }
        });

        holder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");

                dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        resultlist.clear();
                        for(DataSnapshot i : dataSnapshot.getChildren()){
                                GroupDB groupobj = i.getValue(GroupDB.class);
                                if(poll.getGroupName().equals(groupobj.getGid())){
                                    ArrayList<PollDB> localist = groupobj.getListofpolls();
                                    for(int it=0;it<localist.size();it++){
                                        if(localist.get(it).getQuestions().equals(poll.getQuestion())&& LoginActivity.returnemail().equals(poll.getAsker())){
                                            Map<String,String> localmap = localist.get(it).getResponses();
                                            Map<Integer,Integer> result = new HashMap<>();
                                            int array[];
                                            array  = new int[poll.getOptions().size()] ;

                                            for (Map.Entry<String,String> entry : localmap.entrySet()){
                                                if(!entry.getKey().equals("demo"))
                                                for(int var =0;var<entry.getValue().length();var++){
                                                    //result.put(entry.getValue().charAt(var)-48,result.get(entry.getValue().charAt(var)-48)+1);
                                                    array[entry.getValue().charAt(var)-48]++;
                                                }

                                            }
//                                            for(Map.Entry<Integer,Integer> entry : result.entrySet()){
//                                                resultlist.add(entry.getKey()+": "+entry.getValue());
//                                            }
                                            for(int var = 0;var <poll.getOptions().size();var++){
                                                resultlist.add(Integer.toString(var)+": "+Integer.toString(array[var]));

                                            }
                                                resultlist.add("-----------------------------------------------------------");
                                            for (Map.Entry<String,String> entry : localmap.entrySet()){
                                                String answer="";
                                                  for(int var =0;var<entry.getValue().length();var++){
                                                      answer = answer+entry.getValue().charAt(var);
                                                      if(var<entry.getValue().length()-1)answer+=',';
                                                  }
                                                  if(!entry.getKey().equals("demo"))
                                                  resultlist.add(entry.getKey()+":       "+answer);
                                            }

                                        }
                                    }
                                }
                        }
                        final CharSequence[] alertlist = resultlist.toArray(new String[resultlist.size()]);

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

        OptionListAdapter adapter = new OptionListAdapter(context, poll.getOptions());
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupname;
        ImageView avatar;
                ImageButton delete;
        RecyclerView recyclerView;
        TextView question;
        TextView postername;
        Button send ;
        Button result;
        TextView askedtime;
        TextView expirytime;
        MyViewHolder(@NonNull View view) {
            super(view);

            groupname = view.findViewById(R.id.groupName);
            avatar = view.findViewById(R.id.image);
            delete = view.findViewById(R.id.deleteImage);
            recyclerView = view.findViewById(R.id.option_recycleview);
            question = view.findViewById(R.id.question);
            postername = view.findViewById(R.id.posterName);
            send = view.findViewById(R.id.fabsend);
            result = view.findViewById(R.id.fabresult);
            askedtime  = view.findViewById(R.id.askedtime);
            expirytime  = view.findViewById(R.id.expirytime);
        }
    }

    public Context getContext() {
        return context;
    }
}
