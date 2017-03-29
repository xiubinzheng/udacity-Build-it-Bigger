package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidjokelibrary.app.DisplayJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.networkInterface.IncomingJoke;
import com.udacity.gradle.builditbigger.networkInterface.NetworkEndPoint;

public class MainActivityFragment extends Fragment {
    private String mJoke;
    private InterstitialAd mInterstitialAd;


    public MainActivityFragment() {
    }

    private void loadJokeActivity(String joke) {
        Intent mIntent = new Intent(getActivity(), DisplayJokeActivity.class);
        mIntent.putExtra("JOKE", joke);
        startActivity(mIntent);
    }

    private void retriveJoke() {
        new NetworkEndPoint().execute(new IncomingJoke() {
            @Override
            public void onIncomingJokeReceived(String joke) {
                mJoke = joke;
                if (joke != null) {
                    Log.d("log", joke);
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        loadJokeActivity(mJoke);
                    }
                }
            }

        });
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadJokeActivity(mJoke);
                loadInterstitialAd();
            }
        });

        loadInterstitialAd();

        Button button = (Button) root.findViewById(R.id.jokebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveJoke();
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }
}
