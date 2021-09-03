package com.example.matchscheduler;

import android.content.ClipData;
import android.content.ContentProviderClient;
import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerSearchPlayerResultAdapter extends RecyclerView.Adapter<RecyclerSearchPlayerResultAdapter.MyViewHolder> {
    private final Context context;
    private String[] playerNames;
    private String[] playerUrls;
    private int[] isFavImg;
    private ItemClickListener itemClickListener;

    public RecyclerSearchPlayerResultAdapter(Context context, String[] playerNames, String[] playerUrls, ItemClickListener itemClickListener) {
        this.context = context;
        this.playerNames = playerNames;
        this.playerUrls = playerUrls;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_player_search_result, parent, false);
        return new MyViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // dynamically add to recyclerView
        holder.theTextViewPlayerName.setText(playerNames[position]);
        holder.theTextViewPlayerUrl.setText(playerUrls[position]);

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return playerUrls.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        TextView theTextViewPlayerName;
        TextView theTextViewPlayerUrl;
        ImageView myImage;
        ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            theTextViewPlayerName = itemView.findViewById(R.id.textView_player_name_result);
            theTextViewPlayerUrl = itemView.findViewById(R.id.textView_player_url_result);
            myImage = itemView.findViewById(R.id.img_item_view);
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }

}
