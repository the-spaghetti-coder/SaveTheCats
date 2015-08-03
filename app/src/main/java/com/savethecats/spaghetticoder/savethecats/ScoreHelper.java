package com.savethecats.spaghetticoder.savethecats;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
/**
 * Created by Jeff on 8/3/2015.
 */
public class ScoreHelper {

    Context activity;
    SharedPreferences scorePreferences;
    SharedPreferences.Editor editor;
    String score;

    final String DAILY_SCORE_KEY = "dailyScore";
    final String TOTAL_SCORE_KEY = "totalScore";

    public void setDailyScore(String dailyScore, Context activity) {
        scorePreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = scorePreferences.edit();
        editor.putString(DAILY_SCORE_KEY, dailyScore);
    }

    public void setTotalScore(String totalScore) {
        editor = scorePreferences.edit();
        editor.putString(DAILY_SCORE_KEY, totalScore);
    }

    public String getDailyScore(Context activity){

        scorePreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        score = scorePreferences.getString(DAILY_SCORE_KEY, "N/A");
        System.out.println("Daily score retrieved: " + score);
        return score;


    }

    public String getTotalScore(Context activity){

        scorePreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        score = scorePreferences.getString(TOTAL_SCORE_KEY, "N/A");
        System.out.println("Total score retrieved: " + score);
        return score;

    }



}

