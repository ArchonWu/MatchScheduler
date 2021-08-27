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
    String playerNames[];
    String playerUrls[];
//    int raceImg[];

    public RecyclerSearchPlayerResultAdapter(Context context, String[] playerNames, String[] playerUrls) {
        this.context = context;
        this.playerNames = playerNames;
        this.playerUrls = playerUrls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_player_search_result, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // dynamically add to recyclerView
        holder.theTextViewPlayerName.setText(playerNames[position]);
        holder.theTextViewPlayerUrl.setText(playerUrls[position]);
    }

    @Override
    public int getItemCount() {
        return playerUrls.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView theTextViewPlayerName;
        TextView theTextViewPlayerUrl;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theTextViewPlayerName = itemView.findViewById(R.id.textView_player_name_result);
            theTextViewPlayerUrl = itemView.findViewById(R.id.textView_player_url_result);
            myImage = itemView.findViewById(R.id.img_item_view);
        }
    }
}
