package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.udacity.gradle.builditbigger.networkInterface.NetworkEndPoint;
import com.udacity.gradle.builditbigger.networkInterface.IncomingJoke;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class JokeTest extends ApplicationTestCase<Application> {

    CountDownLatch signal;
    String mJoke;

    public JokeTest() {
        super(Application.class);
    }

    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            new NetworkEndPoint().execute(new IncomingJoke() {
                @Override
                public void onIncomingJokeReceived(String joke) {
                    mJoke = joke;
                    signal.countDown();
                }
            });
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", mJoke);
            assertFalse("joke is empty", mJoke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }
}
