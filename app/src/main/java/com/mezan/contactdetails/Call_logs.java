package com.mezan.contactdetails;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.PermissionChecker.checkSelfPermission;


public class Call_logs extends Fragment {

    private List<ContactData> contactDataList=new ArrayList<>();
    private ContactAdapter contactAdapter;

    String[] projection = new String[] {
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE
    };
    public Call_logs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_logs, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.calllogs_recycler);
        contactAdapter = new ContactAdapter(contactDataList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        PrepareData();

        return view;
    }
    public void PrepareData(){
//        ContactData movie = new ContactData("Mad Max: Fury Road", "Action & Adventure", "2015");
//        contactDataList.add(movie);

        ContactData data;

        Cursor c = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null,
                null, CallLog.Calls.DATE + " DESC");

        if (c.getCount() > 0)
        {
            c.moveToFirst();
            do{
                String callerID = c.getString(c.getColumnIndex(CallLog.Calls._ID));
                String type = "Not Identified";
                String callerNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                long callDateandTime = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                long callDuration = c.getLong(c.getColumnIndex(CallLog.Calls.DURATION));
                int callType = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
                if(callType == CallLog.Calls.INCOMING_TYPE)
                {
                    //incoming call
                    Log.d("type: ","Incoming");
                    type = "Incoming";


                }
                else if(callType == CallLog.Calls.OUTGOING_TYPE)
                {
                    //outgoing call
                    Log.d("type: ","Outgoing");
                    type = "Outgoing";
                }
                else if(callType == CallLog.Calls.MISSED_TYPE)
                {
                    //missed call
                    Log.d("type: ","Missed call");
                    type = "Missed Call";
                }

                Log.d("number: ",callerNumber);
                Log.d("datetime: ",Long.toString(callDateandTime));
                Log.d("duration: ",Long.toString(callDuration));

                ContactData calllog = new ContactData(callerNumber, "Duration:"+Long.toString(callDuration)+"s Type :"+type, LongToDate(callDateandTime));
                contactDataList.add(calllog);
            }while(c.moveToNext());

        }

        contactAdapter.notifyDataSetChanged();

    }
    public String LongToDate(long ms){

        Date date = new Date(ms);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        System.out.println(dateformat.format(date));
        Log.d("Result : ",dateformat.format(date));
        return dateformat.format(date);
    }

}