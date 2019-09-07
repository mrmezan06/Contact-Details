package com.mezan.contactdetails;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Contacts extends Fragment {

    private List<ContactData> contactDataList=new ArrayList<>();
    private ContactAdapter contactAdapter;

    public Contacts(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_contacts,container,false);
        RecyclerView recyclerView= view.findViewById(R.id.contact_recycler);
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
        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.i("Name", "Name: " + name);
//                        Log.i("Number", "Phone Number: " + phoneNo);
                        data = new ContactData(name,phoneNo,"N/A");
                        Log.d("Name",name);
                        contactDataList.add(data);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        contactAdapter.notifyDataSetChanged();

    }
}