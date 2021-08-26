package com.example.matchscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

/*
    Display search result from search bar
 */
public class SecondFragment extends Fragment {
    private RecyclerView recyclerViewPlayerSearchResult;
    private View theView;
    private String[] playerList;
    private AsyncBroadcastReceiver asyncBroadcastReceiver;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_second, container, false);

        asyncBroadcastReceiver = new AsyncBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fetchJson_search_player_ok");
        getContext().registerReceiver(asyncBroadcastReceiver, intentFilter);
        Toast.makeText(getContext(), "set up receiver", Toast.LENGTH_SHORT).show();

        return theView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (playerList != null) {
            recyclerViewPlayerSearchResult = getView().findViewById(R.id.second_fragment_player_search_result_recyclerView);
            RecyclerSearchPlayerResultAdapter recyclerSearchPlayerResultAdapter = new RecyclerSearchPlayerResultAdapter(getActivity(), playerList);
            recyclerViewPlayerSearchResult.setAdapter(recyclerSearchPlayerResultAdapter);
            recyclerViewPlayerSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        // useless "previous button"
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    private class AsyncBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getContext(), "received intent", Toast.LENGTH_SHORT).show();
            playerList = intent.getStringArrayExtra("playerList");

            if (playerList != null) {
                recyclerViewPlayerSearchResult = getView().findViewById(R.id.second_fragment_player_search_result_recyclerView);
                RecyclerSearchPlayerResultAdapter recyclerSearchPlayerResultAdapter = new RecyclerSearchPlayerResultAdapter(getActivity(), playerList);
                recyclerViewPlayerSearchResult.setAdapter(recyclerSearchPlayerResultAdapter);
                recyclerViewPlayerSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerSearchPlayerResultAdapter.notifyDataSetChanged();
            }

        }
    }
}