package com.semsari.fatemeh.firstapp.faribank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<ReqestsViewHolder> {

    List<UserRequests> requests = new ArrayList<>();

    public RequestsAdapter(List<UserRequests> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public ReqestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        ReqestsViewHolder reqestsViewHolder = new ReqestsViewHolder(view);
        return reqestsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReqestsViewHolder holder, int position) {

        UserRequests userRequests = requests.get(position);
        String sender = "";
        if(userRequests.isUserRequest()){
            sender = "You";
        } else {
            sender = "Manager";
        }
        holder.bind(userRequests, sender);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
