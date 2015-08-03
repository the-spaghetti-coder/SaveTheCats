package com.savethecats.spaghetticoder.savethecats;

import java.util.Random;

/**
 * Created by Jeff on 8/2/2015.
 */
public class UtilityHelper {

    public int createRandomNumber() {

        //Creates a random number from 1-3

        Random r = new Random();
        int random = 0;
        random = r.nextInt(3);
        random++;

        return random;
    }

}
