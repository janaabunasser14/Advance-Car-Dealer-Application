package com.example.project;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }

    public static void getDataAsync(String URL, OnDataReceivedListener listener) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return getData(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null && listener != null) {
                    listener.onDataReceived(result);
                } else {
                    Log.d("HttpManager", "Failed to retrieve data");
                }
            }
        }.execute(URL);
    }

    protected static String getData(String URL) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception ex) {
            Log.d("HttpURLConnection", ex.toString());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    Log.d("HttpURLConnection", e.toString());
                }
            }
        }
        return null;
    }
}
