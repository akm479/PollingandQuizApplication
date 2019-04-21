package com.example.akshay.PollingApp;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class Tab1Edit extends Fragment {
    public interface OnFragmentInteractionListener{
        public void onFragmentInteraction(Uri uri);
    }
    private Button buttonView;
    private RelativeLayout parentLayout;
    private int hint=0;
    static  String question;
    private  EditText edt;
       static  EditText time;
    static  String timeperiod;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.akshay.PollingApp.R.layout.tab1edit, container, false);

        buttonView=(Button)rootView.findViewById(R.id.buttonView);
        parentLayout =  rootView.findViewById(R.id.scroll);
        edt = rootView.findViewById(R.id.editText3);
        time  = rootView.findViewById(R.id.editText6);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    question  = edt.getText().toString();
                    createEditTextView();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }
    static  ArrayList<EditText> list = new ArrayList<>();
    protected void createEditTextView() throws InterruptedException {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
                 );
        hint++;
        int id = hint-1;
        if (hint > 1) {

            params.addRule(RelativeLayout.BELOW, id);
        }
        else{
            params.addRule(RelativeLayout.BELOW,R.id.buttonView);
        }
        params.setMargins(40,10,40,10);

        EditText edittTxt = new EditText(getContext());
        int maxLength = 100;

        edittTxt.setHint("Option "+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setId(hint);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);

        parentLayout.addView(edittTxt);


        list.add(edittTxt);


    }


    public  static  ArrayList<String> getoptionlist(){
        ArrayList<String> locallist = new ArrayList<>();
        Log.d("movie", "getoptionlist: "+list.size());
       for(int i=0;i<list.size();i++){
           locallist.add(list.get(i).getText().toString());
       }
        list.clear();

        return locallist;


    }
    public  static  String getquestion(){
        return question;
    }
    public   static String gettime(){

        return time.getText().toString();
    }
}
