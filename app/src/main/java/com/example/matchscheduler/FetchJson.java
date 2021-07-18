package com.example.matchscheduler;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchJson extends AsyncTask {
    private String data;
    private JSONObject dataJson;
    private String keyword;

    public FetchJson(String keyword) {
        this.data = "";
        this.keyword = keyword;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            String urlStr = "https://en.wikipedia.org/w/api.php?action=opensearch&search=" + keyword + "&limit=5&namespace=0&format=json";
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            dataJson = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.fetchResult.setText(data); // got here
    }

    void makeSearchResult(){

    }
}
