package com.example.matchscheduler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchscheduler.R;

/*
    MyAdapter
 */
public class RecyclerSavedAdapter extends RecyclerView.Adapter<RecyclerSavedAdapter.MyViewHolder> {
    Context context;
    String s1[];
    String s2[];
    int img[];

    public RecyclerSavedAdapter(Context context, String s1[], String s2[], int img[]){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
        this.img = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_saved_matches, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // dynamically add to recyclerView
        holder.myText1.setText(s1[position]);
        holder.myText2.setText(s2[position]);
//        holder.myImage.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            myText1 = itemView.findViewById(R.id.tournament_name_txt);
//            myText2 = itemView.findViewById(R.id.time_txt);
//            myImage = itemView.findViewById(R.id.imageView_player_isFavourite_result);
        }
    }
}
