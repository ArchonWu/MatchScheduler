package com.example.matchscheduler;

import android.os.AsyncTask;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*

 */
public class FetchJson extends AsyncTask {
    private String data;
    private JSONArray arrayJson;
    private String keyword;

    public FetchJson(String keyword) {
        this.data = "";
        this.keyword = keyword;
    }


    // Find the user's search target with opensearch function (api) to get the url,
    // return the result as a string
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            String urlStr = "https://liquipedia.net/starcraft2/api.php?action=opensearch&format=json&search=" + keyword;
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    // Display search result from doInBackground on textview fetchResult (current)
    // TODO: returns target player's match schedule
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            arrayJson = new JSONArray(data);
            MainActivity.fetchResult.setText(getPlayerLinkJson(arrayJson)); // for testing

            // TODO: display search result (maybe multiple players with the same name)


            // TODO: parse link's data as json

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // returns the url of the player's page
    private String getPlayerLinkJson(JSONArray arrayJson) throws JSONException {
        JSONArray totalUrls = ((JSONArray) filterExtraUrls(arrayJson).get(3));
        boolean isDuplicate = totalUrls.length() > 1;

        if (isDuplicate) {
            // TODO: display the options
            return "OH! " + totalUrls.length() + " PLAYERS HAVE THE SAME NAME YOU SEARCHED!";
        } else {
            // return the only valid url
            String playerUrl = ((JSONArray) (arrayJson.get(3))).get(0).toString();
            return playerUrl;
        }
    }

    // filters out urls end with "Results" and "Matches"
    private JSONArray filterExtraUrls(JSONArray arrayJson) throws JSONException {
        JSONArray urls = (JSONArray) arrayJson.get(3);
        int totalUrls = urls.length();
        for (int i = totalUrls - 1; i > 0; i--) {
            String currentUrl = ((JSONArray) arrayJson.get(3)).get(i).toString();
            if (currentUrl.contains("Results") || currentUrl.contains("Matches")) {
                ((JSONArray) arrayJson.get(3)).remove(i);
            }
        }
        return arrayJson;
    }
}
