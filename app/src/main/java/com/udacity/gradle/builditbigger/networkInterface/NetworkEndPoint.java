package com.udacity.gradle.builditbigger.networkInterface;

import android.os.AsyncTask;
import android.util.Log;

import com.example.udacity.xiubinzheng.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class NetworkEndPoint extends AsyncTask<IncomingJoke, Void, String> {
    private static MyApi myApiService = null;
    private IncomingJoke jokelistener;
    private String ROOT_URL = "https://192.168.2.2:8085/_ah/api/";

    @Override
    protected String doInBackground(IncomingJoke... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        jokelistener = params[0];

        try {
            String name = myApiService.tellJoke().execute().getData();
            //if(name!=null)
            Log.d("hello", name);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        jokelistener.onIncomingJokeReceived(result);
    }
}
