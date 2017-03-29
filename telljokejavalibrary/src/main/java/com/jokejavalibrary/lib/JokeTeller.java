package com.jokejavalibrary.lib;

import java.util.Random;

public class JokeTeller {
    public JokeTeller() {

    }

    String joke[] = {"I love salted fish.",
            "Fish oils are good for you  ?",
            "what would J do?",
            "I am working on my houmor.",
            "have you seem the rainbow?",
            "Sun does not always shine in Philadelphia"
    };


    public String getRandomJoke() {
        Random rm = new Random();
        return joke[rm.nextInt(joke.length)];
    }
}
