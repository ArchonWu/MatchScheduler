package com.example.matchscheduler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchscheduler.PlayerMatchEntry;
import com.example.matchscheduler.R;

import java.util.ArrayList;

public class RecyclerUpcomingMatchesAdapter extends RecyclerView.Adapter<RecyclerUpcomingMatchesAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private OnItemClickListener onItemClickListener;

    public RecyclerUpcomingMatchesAdapter(Context context, ArrayList<PlayerMatchEntry> playerMatchEntries, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.playerMatchEntries = playerMatchEntries;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row_upcoming_matches, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewPlayerName.setText(playerMatchEntries.get(position).getPlayerName());
        holder.textViewRightPlayer.setText(playerMatchEntries.get(position).getOpponentName());
        holder.textViewTournamentName.setText(playerMatchEntries.get(position).getTournamentName());
        holder.textViewDate.setText(playerMatchEntries.get(position).getDate().toString());
        holder.textViewTime.setText(playerMatchEntries.get(position).getTime().toString());
    }

    @Override
    public int getItemCount() {
        return playerMatchEntries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        TextView textViewPlayerName, textViewRightPlayer, textViewTournamentName, textViewDate, textViewTime;
        RecyclerUpcomingMatchesAdapter.OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, RecyclerUpcomingMatchesAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewPlayerName = itemView.findViewById(R.id.textView_left_player_upcoming);
            textViewRightPlayer = itemView.findViewById(R.id.textView_right_player_upcoming);
            textViewTournamentName = itemView.findViewById(R.id.tournament_name_upcoming);
            textViewDate = itemView.findViewById(R.id.textView_date_upcoming);
            textViewTime = itemView.findViewById(R.id.textView_time_upcoming);
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
