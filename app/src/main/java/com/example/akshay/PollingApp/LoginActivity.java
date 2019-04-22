package com.example.akshay.PollingApp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity   {

    EditText email ;
    EditText password;
    Button login;
    Button signup;
    DatabaseReference dbreferuser;
    static  String  convertemail;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences settings;
    static  String Localuser="";
    String pwd;
    static  String rollno;
    FloatingActionButton continu1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        email = (EditText)findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        continu1 = findViewById(R.id.continue1);
      settings  = getSharedPreferences(LoginActivity.MyPREFERENCES, 0);
        dbreferuser = FirebaseDatabase.getInstance().getReference("User");
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        final boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);


       continu1.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               if(hasLoggedIn)
               {
                   Intent i  = new Intent(getApplicationContext(),SplitActivity.class);

                   Localuser = settings.getString("email","");
                   rollno = settings.getString("rollno","");
                   startActivity(i);
                   finish();
                   //Go directly to main activity.
               }
           }
       });










        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                convertemail = email.getText().toString();
                pwd="";
                pwd =  password.getText().toString();
                Log.d("balle", "see: "+convertemail);
                if(convertemail.matches("[a-zA-Z0-9_.-]+@[[a-z].]+[a-z]+")){
                    Log.d("balle", "onClick: "+convertemail);
                    // Yet to do validation
                    Localuser="";

                    for(int i=0;i<convertemail.length()&& convertemail.charAt(i)!='@';i++){
                       if(convertemail.charAt(i)!='.' ){
                            Localuser +=convertemail.charAt(i);
                       }
                    }
                    Log.d("balle", "onClick: "+Localuser);
                    Log.d("balle", "onClick: "+pwd);
                    dbreferuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot i : dataSnapshot.getChildren()){
                                UserDB userobj  = i.getValue(UserDB.class);
                                if(userobj.username.equals(Localuser) && pwd.equals(userobj.password)){
                                    rollno = userobj.Rollno;
                                    Log.d("rrjeetgayi", "onDataChange: "+userobj.Rollno);
                                    SharedPreferences.Editor editor = settings.edit();
                                    settings.edit().putString("email",Localuser).apply();
                                    settings.edit().putString("rollno",rollno).apply();
//Set "hasLoggedIn" to true
                                    editor.putBoolean("hasLoggedIn", true).apply();

// Commit the edits!
                                    editor.commit();
                                    Intent I  = new Intent(getApplicationContext(),SplitActivity.class);
                                    //i.putExtra("emailid",convertemail);
                                    startActivity(I);
                                    finish();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else{
                    Toast.makeText(getApplicationContext(),"Not a valid email address/There shouldn't be . before @",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i;
                i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            }
        });

    }





    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public  static  String returnemail(){
        Log.d("chaumu", "returnemail: "+convertemail);
        return Localuser;
    }
    public static String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }
    public static String getRollno(){
        return rollno;
    }


}

