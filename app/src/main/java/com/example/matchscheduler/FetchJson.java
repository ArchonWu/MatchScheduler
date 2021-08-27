package com.example.matchscheduler;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*

 */
public class FetchJson extends AsyncTask {
    private String data;
    private JSONArray arrayJson;
    private String keyword;
    private Context theContext;

    public FetchJson(String keyword, Context theContext) {
        this.data = "";
        this.keyword = keyword;
        this.theContext = theContext;
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
            filterExtraUrls(arrayJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(theContext, "finished searching", Toast.LENGTH_SHORT).show();

        // notifies second fragment to update recycler view
        try {
            Intent intent = new Intent("fetchJson_search_player_ok");
            intent.putExtra("playerNames", getPlayerNamesSearchResult());
            intent.putExtra("playerUrls", getPlayerUrlsSearchResult());
            theContext.sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String[] getPlayerUrlsSearchResult() throws JSONException {
        if (!data.equals("")) {
            JSONArray urls = (JSONArray) arrayJson.get(3);
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
        if (!data.equals("")) {
            JSONArray urls = (JSONArray) arrayJson.get(3);
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
}
