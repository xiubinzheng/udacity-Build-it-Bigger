package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidjokelibrary.app.jokeDisplayActivity;
import com.udacity.gradle.builditbigger.networkInterface.EndPoint;
import com.udacity.gradle.builditbigger.networkInterface.onJokeReceived;

public class MainActivityFragment extends Fragment {
    private String mJoke;

    public MainActivityFragment() {
    }

    private void loadJokeActivity(String joke) {
        Intent mIntent = new Intent(getActivity(), jokeDisplayActivity.class);
        mIntent.putExtra("JOKE", joke);
        startActivity(mIntent);
    }

    private void retriveJoke() {
        new EndPoint().execute(new onJokeReceived() {
            @Override
            public void OnJokeReceivedListener(String joke) {
                if (joke != null)
                    loadJokeActivity(joke);
                else
                    android.widget.Toast.makeText(getActivity(), "Error!Something went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = (Button) root.findViewById(R.id.jokebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveJoke();
            }
        });
        return root;
    }


}
