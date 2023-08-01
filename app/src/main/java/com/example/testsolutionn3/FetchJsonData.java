package com.example.testsolutionn3;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchJsonData extends AsyncTask<Void, Void, ArrayList<String>> {

    private static final String TAG = "FetchJsonData";
    private static final String API_URL = "https://shibe.online/api/shibes?count=10&urls=true&httpsUrls=true";

    private OnDataFetchedListener listener;

    public FetchJsonData(OnDataFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        ArrayList<String> jsonDataList = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            // Read the data from the API response
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            if (stringBuilder.length() == 0) {
                return null;
            }

            String jsonData = stringBuilder.toString();
            String[] urls = jsonData.split(",");
            for (String urlStr : urls) {
                jsonDataList.add(urlStr.trim());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        return jsonDataList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> jsonDataList) {
        super.onPostExecute(jsonDataList);
        if (jsonDataList != null && !jsonDataList.isEmpty()) {
            listener.onDataFetched(jsonDataList);
        }
    }

    public interface OnDataFetchedListener {
        void onDataFetched(ArrayList<String> jsonDataList);
    }
}
