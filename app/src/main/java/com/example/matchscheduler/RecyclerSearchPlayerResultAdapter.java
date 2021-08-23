package com.example.matchscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerSearchPlayerResultAdapter extends RecyclerView.Adapter<RecyclerSearchPlayerResultAdapter.MyViewHolder> {
    Context context;
    String playerName[];
//    int raceImg[];

    public RecyclerSearchPlayerResultAdapter(Context context, String[] playerName) {
        this.context = context;
        this.playerName = playerName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_player_search_result, parent, false);
        return new MyViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // dynamically add to recyclerView
        holder.theTextViewPlayerName.setText(playerName[position]);
    }

    @Override
    public int getItemCount() {
        return playerName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView theTextViewPlayerName;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theTextViewPlayerName = itemView.findViewById(R.id.textView_player_name_result);
            myImage = itemView.findViewById(R.id.img_item_view);
        }
    }
}
