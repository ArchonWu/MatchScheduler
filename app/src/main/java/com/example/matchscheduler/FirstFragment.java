package com.example.matchscheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
    Display user previously saved matches
 */
public class FirstFragment extends Fragment {
    public static RecyclerView recyclerView;
    String s1[] = new String[2];
    String s2[] = new String[2];
    private View theView;
    private TextView tv;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_first, container, false);
        return theView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.result_recyclerView);
        tv = (TextView) theView.findViewById(R.id.textView_first_fragment);
        if (recyclerView==null) {
            tv.setText("Cannot find recyclerView");
        }else{
            tv.setText("Found recyclerView");
        }

        s1[0] = "s1_0";
        s1[1] = "s1_1";
        s2[0] = "s2_0";
        s2[1] = "s2_1";

        RecyclerResultAdapter recyclerResultAdapter = new RecyclerResultAdapter(getActivity(), s1, s2, null);
        recyclerView.setAdapter(recyclerResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }
}