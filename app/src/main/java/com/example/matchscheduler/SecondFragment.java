package com.example.matchscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
    Display search result from search bar
 */
public class SecondFragment extends Fragment{
    private RecyclerView recyclerViewPlayerSearchResult;
    private View theView;
    private String[] playerNames;
    private String[] playerUrls;
    private AsyncBroadcastReceiver asyncBroadcastReceiver;
    //    private static Bundle recyclerStateBundle;
    private Parcelable recyclerState;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_second, container, false);

        if (playerNames == null) playerNames = new String[0];
        if (playerUrls == null) playerUrls = new String[0];
//        if (savedInstanceState != null) {
//            recyclerViewPlayerSearchResult = savedInstanceState.getParcelable("recycler_state_key");
//        }

        if (asyncBroadcastReceiver == null) {
            asyncBroadcastReceiver = new AsyncBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("fetchJson_search_player_ok");
            getContext().registerReceiver(asyncBroadcastReceiver, intentFilter);
            Toast.makeText(getContext(), "set up receiver", Toast.LENGTH_SHORT).show();
        }

        return theView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPlayerSearchResult
                = getView().findViewById(R.id.second_fragment_player_search_result_recyclerView);
        RecyclerSearchPlayerResultAdapter recyclerSearchPlayerResultAdapter
                = new RecyclerSearchPlayerResultAdapter(getActivity(),
                playerNames, playerUrls,
                new RecyclerSearchPlayerResultAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(), "onItemClick() in onViewCreated", Toast.LENGTH_SHORT).show();
                    }
                });
        recyclerViewPlayerSearchResult.setAdapter(recyclerSearchPlayerResultAdapter);
        recyclerViewPlayerSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerState = recyclerViewPlayerSearchResult.getLayoutManager().onSaveInstanceState();
//        Toast.makeText(getContext(), "onPause() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewPlayerSearchResult.getLayoutManager().onRestoreInstanceState(recyclerState);
//        Toast.makeText(getContext(), "onResume() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putStringArray("playerNames", playerNames);
//        outState.putStringArray("playerUrls", playerUrls);
        Toast.makeText(getContext(), "onSaveInstanceState called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            playerNames = savedInstanceState.getStringArray("playerNames");
//            playerUrls = savedInstanceState.getStringArray("playerUrls");
//        }
        Toast.makeText(getContext(), "onViewStateRestored called", Toast.LENGTH_SHORT).show();
    }

    private class AsyncBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            playerNames = intent.getStringArrayExtra("playerNames");
            playerUrls = intent.getStringArrayExtra("playerUrls");

            if (playerUrls != null) {
                RecyclerSearchPlayerResultAdapter recyclerSearchPlayerResultAdapter
                        = new RecyclerSearchPlayerResultAdapter(getActivity(),
                        playerNames, playerUrls,
                        new RecyclerSearchPlayerResultAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Toast.makeText(getContext(), position + ": onItemClick() in onViewCreated (AsyncReceiver)", Toast.LENGTH_SHORT).show();
                            }
                        });
                recyclerViewPlayerSearchResult.setAdapter(recyclerSearchPlayerResultAdapter);
                recyclerViewPlayerSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerSearchPlayerResultAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initRecyclerViewOnItemClick(){

    }
}