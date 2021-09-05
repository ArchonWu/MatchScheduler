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
    ArrayList<String> matchTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        try {
            String afterTrim = trimText(loadJsonFromAsset());
            filterMatchTime(afterTrim);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private String trimText(String jsonString) throws IOException {
        jsonString = jsonString.substring(jsonString.indexOf("Upcoming Matches"), jsonString.indexOf("Recent Matches"));
        TextView tv = findViewById(R.id.textView_trimmed);
        tv.setText(jsonString);
        return jsonString;
    }

    private String rightPlayer(String jsonString) {
        // left player is always the searched player by user


        return jsonString;
    }

    // only for one single match
    private String filterMatchTime(String jsonString) throws IOException {
        // default time are in UTC, need to convert
        String inHere = jsonString.substring(jsonString.indexOf("<span class=\\\"match-countdown\\\">"));
        String somewhere = inHere.substring(0, inHere.indexOf("</span>"));
        String morePrecise = somewhere.substring(somewhere.indexOf("<span class=\\\"timer-object timer-object-countdown-only\\\""), somewhere.indexOf("<abbr"));
        String theActualTime = morePrecise.substring(morePrecise.lastIndexOf(">") + 1);

        TextView tv = findViewById(R.id.textView_match_time);
        tv.setText(theActualTime);
        return theActualTime;
    }
}