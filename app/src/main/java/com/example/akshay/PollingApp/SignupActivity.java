package com.example.akshay.PollingApp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText checkpwd;
    Button register;
    EditText rollno;
    DatabaseReference dbreferuser;
    boolean flag =true;
    String localemail  = "";
    ArrayList<String> grouplist = new ArrayList<>();
    String pwd;
    String againpwd;
       String rollNo;
    String convertemail  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.editText);
        password  = findViewById(R.id.editText2);
        checkpwd =findViewById(R.id.editText3);
        rollno = findViewById(R.id.editText4);
        register = findViewById(R.id.button2);
        dbreferuser = FirebaseDatabase.getInstance().getReference("User");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grouplist.clear();
                localemail="";
                convertemail = email.getText().toString();
                pwd  = password.getText().toString();
                againpwd = checkpwd.getText().toString();
                rollNo = rollno.getText().toString();
                Log.d("challe", "onClick: "+convertemail);
                Log.d("challe", "onClick: "+pwd);
                Log.d("challe", "onClick: "+againpwd);
                if(convertemail.matches("[a-zA-Z0-9_.-]+@[[a-z].]+[a-z]+") &&pwd.equals(againpwd)){

                    for(int i=0;i<convertemail.length() && convertemail.charAt(i)!='@';i++){
                            if(convertemail.charAt(i)!='.'){
                                localemail += convertemail.charAt(i);
                            }
                    }

                    dbreferuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot i: dataSnapshot.getChildren()){
                                UserDB userobj = i.getValue(UserDB.class);
                                if(userobj.username.equals(convertemail))flag=false;
                            }

                            if(flag){
                                grouplist.add("demo");
                                  UserDB usernew = new UserDB(localemail,grouplist,pwd,rollNo);
                                  dbreferuser.child(localemail).setValue(usernew);


                                Intent i;
                                i =  new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email already exists",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    // Do database thing;
                }
                else{
                    Toast.makeText(getApplicationContext(),"Not a valid email address/Wrong password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
