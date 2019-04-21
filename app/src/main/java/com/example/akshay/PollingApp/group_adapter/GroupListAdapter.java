package com.example.akshay.PollingApp.group_adapter;

import android.content.Context;
import android.os.LocaleList;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshay.PollingApp.GroupDB;
import com.example.akshay.PollingApp.LoginActivity;
import com.example.akshay.PollingApp.R;
import com.example.akshay.PollingApp.UserDB;
import com.example.akshay.PollingApp.group_model.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.MyViewHolder> {


    DatabaseReference dbrefergroup;
    DatabaseReference dbreferuser;
    private Context  context;
    private ArrayList<Group> list;

    public GroupListAdapter(Context context,ArrayList<Group>list){
        this.context = context;
        this.list=list;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup viewGroup,int i){
        View view = LayoutInflater.from(context).inflate(R.layout.group_list_card, viewGroup, false);
        dbrefergroup = FirebaseDatabase.getInstance().getReference("group");
         dbreferuser = FirebaseDatabase.getInstance().getReference("User");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int i){
        final Group  group =  list.get(i);

        holder.groupname.setText(group.getGroupname());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 dbreferuser.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         for(DataSnapshot i : dataSnapshot.getChildren()){
                             UserDB userobj = i.getValue(UserDB.class);
                             ArrayList<String> listofgroups = new ArrayList<>();
                             listofgroups = userobj.getListofgroups();
                             ArrayList<String>locallist = new ArrayList<>();
                             for(int it=0;it<listofgroups.size();it++){
                                 if(!listofgroups.get(it).equals(group.getGroupname())){
                                     locallist.add(listofgroups.get(it));
                                 }

                             }
                             UserDB userdb = new UserDB(LoginActivity.returnemail(),locallist,userobj.getPassword(),userobj.getPassword());
                             dbreferuser.child(LoginActivity.returnemail()).setValue(userdb);

                             dbrefergroup.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     for(DataSnapshot i : dataSnapshot.getChildren()){
                                         GroupDB groupobj =  i.getValue(GroupDB.class);
                                         if(group.getGroupname().equals(groupobj.getGid())){




                                                 ArrayList<String> locallist = new ArrayList<>();
                                                 for (int it = 0; it < groupobj.getUserlist().size(); it++) {
                                                     if (!groupobj.getUserlist().get(it).equals(LoginActivity.returnemail())) {
                                                         locallist.add(groupobj.getUserlist().get(it));
                                                     }
                                                 }
                                                 GroupDB groupdb = new GroupDB(groupobj.getGid(), locallist, groupobj.getListofpolls());
                                                 dbrefergroup.child(groupobj.getGid()).setValue(groupdb);

                                         }
                                     }
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });



                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
            }
        });
    }
    @Override
    public  int getItemCount(){
        return list.size();
    }
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView groupname;
    ImageView avatar, delete;

    MyViewHolder (View view){
        super(view);

        groupname = view.findViewById(R.id.groupName);
        avatar = view.findViewById(R.id.image);
        delete = view.findViewById(R.id.deleteImage);
    }

}
}
