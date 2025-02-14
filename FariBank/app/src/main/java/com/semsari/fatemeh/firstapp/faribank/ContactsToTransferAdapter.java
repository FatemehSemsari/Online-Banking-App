package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactsToTransferAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> contacts;
    public ContactsToTransferAdapter(Context context, int resource, List<User> contacts){
        super(context, resource, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User contact = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_item_to_transfer, parent, false);
        }

        TextView userName = convertView.findViewById(R.id.contact_name);

        if(contact != null){
            userName.setText(contact.getName() + " " + contact.getFamilyName());
        }
        return convertView;
    }
}
