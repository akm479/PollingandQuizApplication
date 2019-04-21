package com.example.akshay.PollingApp.send_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshay.PollingApp.model.Opt;
import com.example.akshay.PollingApp.model.Poll;
import com.example.akshay.PollingApp.send_model.send;
import com.example.akshay.PollingApp.R;

import java.util.ArrayList;

public class SendListAdapter extends RecyclerView.Adapter<SendListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<send> list;
    ArrayList<String> listtosend= new ArrayList<>();
    public SendListAdapter(Context context, ArrayList<send> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.send_list_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final send poll = list.get(i);

        //Poll p = new Poll("grp"+list.get(i).getGroupName(),"",new ArrayList<Opt>() ,"","","");
//        list.get(i).setGroupName("er"+list.get(i).getGroupName());

        holder.groupname.setText(poll.getGroupName());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setChecked(isChecked);
            }
        });

        final int index = i;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupname;
        ImageView avatar;
        CheckBox cb;

        MyViewHolder(@NonNull View view) {
            super(view);

            groupname = view.findViewById(R.id.groupName);
            avatar = view.findViewById(R.id.image);
            cb = view.findViewById(R.id.checkBox);
        }
    }
}
