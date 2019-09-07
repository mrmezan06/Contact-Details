package com.mezan.contactdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<ContactData> ContactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.Name);
            genre = view.findViewById(R.id.Mobile);
            year = view.findViewById(R.id.Email);
        }
    }


    public ContactAdapter(List<ContactData> ContactList) {
        this.ContactList = ContactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactData contactData = ContactList.get(position);
        holder.title.setText(contactData.getName());
        holder.genre.setText(contactData.getMobile());
        holder.year.setText(contactData.getEmail());
    }

    @Override
    public int getItemCount() {
        return ContactList.size();
    }
}
