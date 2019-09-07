package com.mezan.contactdetails;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Message extends Fragment {
    private List<ContactData> contactDataList=new ArrayList<>();
    private ContactAdapter contactAdapter;
    public Message(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message,container,false);
        RecyclerView recyclerView= view.findViewById(R.id.msg_recycler);
        contactAdapter = new ContactAdapter(contactDataList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        PrepareData();

        return view;
    }
    public void PrepareData() {
//        ContactData movie = new ContactData("Mad Max: Fury Road", "Action & Adventure", "2015");
//        contactDataList.add(movie);

        ContactData data;

        //Inbox

        Uri inboxUri = Uri.parse("content://sms/inbox");

        ContentResolver cr = getContext().getContentResolver();
        Cursor c = cr.query(inboxUri,null,null,null,null);
        data = new ContactData("Inbox","N/A","N/A");
        contactDataList.add(data);
        if(c.moveToFirst()){
            while (c.moveToNext()){
                String number = c.getString(c.getColumnIndexOrThrow("address"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                long date = Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")));

                data = new ContactData(number,body,LongToDate(date));
                contactDataList.add(data);

            }
            c.close();
        }else {
            data = new ContactData("Empty","N/A","N/A");
            contactDataList.add(data);
        }
        //Sent Box
        Uri sentBox = Uri.parse("content://sms/sent");

        cr = getContext().getContentResolver();
        c = cr.query(sentBox,null,null,null,null);
        data = new ContactData("SentBox","N/A","N/A");
        contactDataList.add(data);
        if(c.moveToFirst()){
            while (c.moveToNext()){
                String number = c.getString(c.getColumnIndexOrThrow("address"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                String date = c.getString(c.getColumnIndexOrThrow("date"));
                data = new ContactData(number,body,date);
                contactDataList.add(data);

            }
            c.close();
        }else {
            data = new ContactData("Empty","N/A","N/A");
            contactDataList.add(data);
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
