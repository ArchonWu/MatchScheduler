package com.example.matchscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchscheduler.Adapters.RecyclerUpcomingMatchesAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerMatchesActivity extends AppCompatActivity {
    private MatchEntryExtractor matchEntryExtractor;
    private RecyclerView recyclerViewPlayerUpcomingMatchesResult;
    private RecyclerUpcomingMatchesAdapter recyclerUpcomingMatchesAdapter;
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String playerNameHttp;
    private String allInfoString;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        context = this;
        playerName = "?";
        if (getIntent().hasExtra("playerName")) {
            playerName = getIntent().getExtras().getString("playerName");
            Toast.makeText(this, playerName, Toast.LENGTH_SHORT).show();
        }

        doOkHttpRequest();

        allInfoString = loadJsonFromAsset();

        matchEntryExtractor = new MatchEntryExtractor(playerName, allInfoString);
        playerMatchEntries = matchEntryExtractor.getPlayerMatchEntryList();

        recyclerViewPlayerUpcomingMatchesResult = findViewById(R.id.recycler_view_player_upcoming_matches);
//        RecyclerUpcomingMatchesAdapter recyclerUpcomingMatchesAdapter
//                = new RecyclerUpcomingMatchesAdapter(this, playerMatchEntries, null);
        recyclerUpcomingMatchesAdapter = new RecyclerUpcomingMatchesAdapter(this, playerMatchEntries, null);
        recyclerViewPlayerUpcomingMatchesResult.setAdapter(recyclerUpcomingMatchesAdapter);
        recyclerViewPlayerUpcomingMatchesResult.setLayoutManager(new LinearLayoutManager(this));
    }

    private String loadJsonFromAsset() {
        String jsonString = null;
        try {
            InputStream is = getAssets().open("testParse.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private void doOkHttpRequest() {
        playerName = "Zest";
        playerNameHttp = playerName.replace(' ', '_');
        String urlRequest = "https://liquipedia.net/starcraft2/api.php?action=parse&format=json&page=" + playerNameHttp + "&prop=text";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlRequest)
                .header("User-Agent", "MatchScheduler/aquila479572@gmail.com")
                .build();

        if (!playerName.equals(""))
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();
                        PlayerMatchesActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                allInfoString = myResponse;
                                matchEntryExtractor = new MatchEntryExtractor(playerName, allInfoString);
                                playerMatchEntries = matchEntryExtractor.getPlayerMatchEntryList();

                                recyclerUpcomingMatchesAdapter = new RecyclerUpcomingMatchesAdapter(context, playerMatchEntries, null);
                                recyclerViewPlayerUpcomingMatchesResult.setAdapter(recyclerUpcomingMatchesAdapter);
                                recyclerViewPlayerUpcomingMatchesResult.setLayoutManager(new LinearLayoutManager(context));
                                recyclerUpcomingMatchesAdapter.notifyDataSetChanged();

//                                TextView tv = findViewById(R.id.textView_trimmed);
//                                tv.setText(matchEntryExtractor.getTrimmedUpcomingText());

                                // TODO: save to assets

                            }
                        });
                    }
                }
            });
    }

}