package com.example.matchscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PlayerMatchesActivity extends AppCompatActivity {
    private MatchEntryExtractor matchEntryExtractor;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        // TODO: bundle things
        matchEntryExtractor = new MatchEntryExtractor(playerName, loadJsonFromAsset());



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