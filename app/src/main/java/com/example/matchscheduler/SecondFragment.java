package com.example.matchscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

/*
    Display search result from search bar
 */
public class SecondFragment extends Fragment{
    public static RecyclerView recyclerViewPlayerSearchResult;
    private TextView tv;
    private View theView;
    public static String[] playerList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_second, container, false);

        return theView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        try {
//            while(!FetchJson.dataReady);
//            if(FetchJson.dataReady)
//                playerList = FirstFragment.fetchProcess.getPlayerSearchResult();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        recyclerViewPlayerSearchResult = getView().findViewById(R.id.second_fragment_player_search_result_recyclerView);
        RecyclerSearchPlayerResultAdapter recyclerSearchPlayerResultAdapter = new RecyclerSearchPlayerResultAdapter(getActivity(), playerList);
        recyclerViewPlayerSearchResult.setAdapter(recyclerSearchPlayerResultAdapter);
        recyclerViewPlayerSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));


        // useless "previous button"
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}