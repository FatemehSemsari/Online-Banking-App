package com.semsari.fatemeh.firstapp.faribank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<User> items = new ArrayList<>();
    private int itemid;
    AllUsers allUsers = Main.getAllUsers();

    User user = null;


    public MyAdapter(Context context, List<User> items, User user) {
        this.context = context;
        this.items = items;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.contactName.setText(items.get(position).getName() + " " + items.get(position).getFamilyName());
        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_add_contacts);
                EditText newName = dialog.findViewById(R.id.new_contact_name);
                EditText newFamilyName = dialog.findViewById(R.id.new_contact_familyName);
                EditText newPhone = dialog.findViewById(R.id.new_contact_phonenumber);
                Button actionBtn = dialog.findViewById(R.id.add_contact_button);
                TextView title = dialog.findViewById(R.id.signUpText);

                title.setText("Update Contacts");
                actionBtn.setText("Update");

                newName.setText(items.get(position).getName());
                newFamilyName.setText(items.get(position).getFamilyName());
                newPhone.setText(items.get(position).getPhoneNumber());

                actionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = "", familyName = "" , phoneNumber = "";
                        if (newName.getText().toString().isEmpty()) {
                            newName.setError("you have to enter name");
                            return;
                        }
                        if (newFamilyName.getText().toString().isEmpty()) {
                            newFamilyName.setError("you have to enter family name");
                            return;
                        }
                        if (newPhone.getText().toString().isEmpty()) {
                            newPhone.setError("you have to enter phone number");
                            return;
                        }
                        name = newName.getText().toString();
                        familyName = newFamilyName.getText().toString();
                        phoneNumber = newPhone.getText().toString();
                        User contact = new Contact(name, familyName, phoneNumber, items.get(position).getId(), items.get(position).getPassword());
                        contact.setCreditCard(items.get(position).getCreditCard());
                        items.set(position, contact);
                        String[] fullName = new String[2];
                        fullName[0] = name;
                        fullName[1] = familyName;
                        user.updateContact(fullName, phoneNumber);
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] name = new String[2];
                name[0] = items.get(position).getName();
                name[1] = items.get(position).getFamilyName();
                user.removeContact(name);
                items.remove(position);
                notifyItemRemoved(position);
            }
        });

//        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                        .setTitle("Delete Contact")
//                        .setMessage("Are you sure want to delete?")
//                        .setIcon(R.drawable.delete)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                items.remove(position);
//                                notifyItemRemoved(position);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                builder.show();
//                return true;
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteitem(int itemid){
        items.remove(itemid);
        notifyItemRemoved(itemid);
    }

    public void updateContactsList(List<User> newContactsList) {
        this.items.clear();
        this.items = newContactsList;
        notifyDataSetChanged();
    }

}
