package com.example.akshay.PollingApp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.akshay.PollingApp.R;
import com.example.akshay.PollingApp.model.Opt;


import java.util.List;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.MyViewHolder> {

    private Context context;
    private List<Opt> list;

    public OptionListAdapter(Context context, List<Opt> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
            Opt opt = list.get(i);
            myViewHolder.checkBox.setActivated(opt.getCheckBox());
            myViewHolder.option.setText(opt.getOption_text());

            myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(i).setCheckBox(isChecked);

                }
            });
    }



  //  @Override
    //public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        //Poll poll = list.get(i);
        //holder.gropname.setText(poll.getGroupName());

        //final int index = i;
//        holder.avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "OnClick " + index, Toast.LENGTH_SHORT).show();
//            }
//        });
    //}

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView option;
        CheckBox checkBox;

        MyViewHolder(@NonNull View view) {
            super(view);

            option = view.findViewById(R.id.option_text);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }
}
