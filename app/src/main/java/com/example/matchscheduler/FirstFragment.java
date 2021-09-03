package com.example.matchscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
    Display user previously saved matches
 */
public class FirstFragment extends Fragment {
    private RecyclerView recyclerView;
    public SearchView searchView;
    private View theView;
    private TextView tv;
    private FetchJson fetchProcess;

//    private String s1[] = new String[0];
//    private String s2[] = new String[0];
    private String[] s1, s2;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_first, container, false);
        setHasOptionsMenu(true);
        s1 = new String[0];
        s2 = new String[0];
        return theView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = getView().findViewById(R.id.searchView_fragment_first);
        searchView.setQueryHint("Search player id here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(searchView.getContext(), "Searching: " + query, Toast.LENGTH_SHORT).show();
                searchView.onActionViewCollapsed();
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                fetchProcess = new FetchJson(query, getContext());
                fetchProcess.execute();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        recyclerView = getView().findViewById(R.id.first_fragment_result_recyclerView);
        tv = (TextView) theView.findViewById(R.id.textView_first_fragment);

//        s1[0] = "s1_0";
//        s1[1] = "s1_1";
//        s2[0] = "s2_0";
//        s2[1] = "s2_1";

        RecyclerSavedAdapter recyclerResultAdapter = new RecyclerSavedAdapter(getActivity(), s1, s2, null);
        recyclerView.setAdapter(recyclerResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}