package com.example.matchscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.matchscheduler.Adapters.RecyclerUpcomingMatchesAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class PlayerMatchesActivity extends AppCompatActivity {
    private MatchEntryExtractor matchEntryExtractor;
    private RecyclerView recyclerViewPlayerUpcomingMatchesResult;
    private ArrayList<PlayerMatchEntry> playerMatchEntries;
    private String playerName;
    private String allInfoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        // TODO: load from bundle

        // test
        matchEntryExtractor = new MatchEntryExtractor(playerName, loadJsonFromAsset());
        allInfoString = matchEntryExtractor.getTrimmedInfoText();
        PlayerMatchEntry playerMatchEntryTest =
                new PlayerMatchEntry("Neeb", "p", "PartinG",
                        "WardiTv", new Date(), new Time(10000));
        playerMatchEntries = new ArrayList<>();
        playerMatchEntries.add(playerMatchEntryTest);


        recyclerViewPlayerUpcomingMatchesResult = findViewById(R.id.recycler_view_player_upcoming_matches);
        RecyclerUpcomingMatchesAdapter recyclerUpcomingMatchesAdapter
                = new RecyclerUpcomingMatchesAdapter(this, playerMatchEntries, null);
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

}