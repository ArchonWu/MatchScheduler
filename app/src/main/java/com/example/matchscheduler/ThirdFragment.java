package com.example.matchscheduler;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
    Display user previously saved matches
 */
public class ThirdFragment extends Fragment {
    private View theView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_first, container, false);

        return theView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: populate recyclerView from saved data
        loadFromFile();
    }

    private void loadFromFile(){
        ArrayList<PlayerMatchEntry> savedEntries = new ArrayList<PlayerMatchEntry>();
        try {
            JsonReader reader = new JsonReader(new FileReader(getActivity().getCacheDir() + "/scheduledMatchesSave.json"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}