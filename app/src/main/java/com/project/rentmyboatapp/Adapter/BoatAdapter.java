package com.project.rentmyboatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rentmyboatapp.Boat;
import com.project.rentmyboatapp.BoatRenter;
import com.project.rentmyboatapp.R;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BoatAdapter extends RecyclerView.Adapter<BoatAdapter.BoatViewHolder> {


    private ArrayList<Boat> arrayList;
    private Context context;


    public BoatAdapter(ArrayList<Boat> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BoatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_boat, parent, false);
        BoatViewHolder holder = new BoatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoatViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.img_boat);
        holder.txt_name.setText(arrayList.get(position).getName());
        holder.txt_year.setText(String.valueOf(arrayList.get(position).getYear()));
        holder.txt_description.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        // ternary operator
        //        if arrayList is not null
        //        true arrayList.size()
        //        false 0
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class BoatViewHolder extends RecyclerView.ViewHolder {

        ImageView img_boat;
        TextView txt_name;
        TextView txt_year;
        TextView txt_description;

        public BoatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img_boat = itemView.findViewById(R.id.img_boat);
            this.txt_name = itemView.findViewById(R.id.txt_name);
            this.txt_year = itemView.findViewById(R.id.txt_year);
            this.txt_description = itemView.findViewById(R.id.txt_description);

        }
    }
}

