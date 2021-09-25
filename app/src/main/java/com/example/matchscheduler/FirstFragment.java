package com.example.matchscheduler;

import android.content.Intent;
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

import com.example.matchscheduler.Adapters.RecyclerSavedAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    Display user previously saved matches
 */
public class FirstFragment extends Fragment {
    private RecyclerView recyclerView;
    public SearchView searchView;
    private View theView;
    private TextView tv;
    private JSONArray dataJsonArray;

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
                searchView.onActionViewCollapsed();
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                doOkHttpRequest(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recyclerView = getView().findViewById(R.id.first_fragment_result_recyclerView);
        tv = (TextView) theView.findViewById(R.id.textView_first_fragment);

        // TODO: populate recyclerView from saved data

        RecyclerSavedAdapter recyclerResultAdapter = new RecyclerSavedAdapter(getActivity(), s1, s2, null);
        recyclerView.setAdapter(recyclerResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void doOkHttpRequest(String keyword) {
        String urlRequest = "https://liquipedia.net/starcraft2/api.php?action=opensearch&format=json&search=" + keyword;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlRequest)
                .header("User-Agent", "MatchScheduler/aquila479572@gmail.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    dataJsonArray = new JSONArray(myResponse);
                    filterExtraUrls(dataJsonArray);
                    Intent intent = new Intent("fetchJson_search_player_ok");
                    intent.putExtra("playerNames", getPlayerNamesSearchResult());
                    intent.putExtra("playerUrls", getPlayerUrlsSearchResult());
                    getContext().sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });

    }

    // filters out urls end with "Results" and "Matches"
    private JSONArray filterExtraUrls(JSONArray arrayJson) throws JSONException {
        JSONArray urls = (JSONArray) arrayJson.get(3);
        int totalUrls = urls.length();
        for (int i = totalUrls - 1; i > 0; i--) {
            String currentUrl = ((JSONArray) arrayJson.get(3)).get(i).toString();
            if (currentUrl.contains("Results") || currentUrl.contains("Matches")
                    || currentUrl.contains("%")) {
                ((JSONArray) arrayJson.get(3)).remove(i);
            }
        }
        return arrayJson;
    }

    private String[] getPlayerUrlsSearchResult() throws JSONException {
        if (!dataJsonArray.equals("")) {
            JSONArray urls = (JSONArray) dataJsonArray.get(3);
            int totalUrls = urls.length();
            String[] playerUrls = new String[totalUrls];

            for (int i = 0; i < totalUrls; i++) {
                playerUrls[i] = urls.get(i).toString();
            }
            return playerUrls;
        }
        return null;
    }

    private String[] getPlayerNamesSearchResult() throws JSONException {
        if (!dataJsonArray.equals("")) {
            JSONArray urls = (JSONArray) dataJsonArray.get(3);
            int totalUrls = urls.length();
            String[] playerNames = new String[totalUrls];
            for (int i = 0; i < totalUrls; i++) {
                String currentUrl = urls.get(i).toString();
                currentUrl = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
                currentUrl = currentUrl.replace('_', ' ');
                playerNames[i] = currentUrl;
            }
            return playerNames;
        }
        return null;
    }
}