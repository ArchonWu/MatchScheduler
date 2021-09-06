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

public class RecyclerSearchPlayerResultAdapter extends RecyclerView.Adapter<RecyclerSearchPlayerResultAdapter.MyViewHolder> {
    private final Context context;
    private String[] playerNames;
    private String[] playerUrls;
    private int[] isFavImg;
    private OnItemClickListener onItemClickListener;

    public RecyclerSearchPlayerResultAdapter(Context context, String[] playerNames, String[] playerUrls, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.playerNames = playerNames;
        this.playerUrls = playerUrls;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_player_search_result, parent, false);
        return new MyViewHolder(view, onItemClickListener);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        TextView theTextViewPlayerName;
        TextView theTextViewPlayerUrl;
        ImageView myImage;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            theTextViewPlayerName = itemView.findViewById(R.id.textView_player_name_result);
            theTextViewPlayerUrl = itemView.findViewById(R.id.textView_player_url_result);
            myImage = itemView.findViewById(R.id.imageView_player_isFavourite_result);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
