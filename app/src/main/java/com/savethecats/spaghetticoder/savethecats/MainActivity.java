package com.savethecats.spaghetticoder.savethecats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.savethecats.spaghetticoder.savethecats.UtilityHelper;
import com.savethecats.spaghetticoder.savethecats.ScoreHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    //CLASS WIDE VARIABLES
    private CharSequence mTitle;

    AlertDialog dialogBox;
    AdView dialogAd;
    Button dialogBtn;
    int randomNumber;
    String randomText;
    String score;
    TextView randomTextView;
    TextView dailyScoreTextView;
    TextView totalScoreTextView;

    UtilityHelper utilityHelper;
    ScoreHelper scoreHelper;
    View layoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        utilityHelper = new UtilityHelper();
        scoreHelper = new ScoreHelper();

        createBannerAds();
        scoreHelper.setDailyScore("1", this);
        score = scoreHelper.getDailyScore(this);
        setTextViewText(dailyScoreTextView, R.id.dailyScoreTextView, score);

    }

    public void createBannerAds() {
        AdView bannerAdTop = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdTop.loadAd(adRequest);

        AdView bannerAdBottom = (AdView) findViewById(R.id.adView2);
        AdRequest dialogAdRequest = new AdRequest.Builder().build();
        bannerAdBottom.loadAd(dialogAdRequest);
    }

    public String createRandomCatString() {
        randomNumber = utilityHelper.createRandomNumber();
        randomText = String.valueOf(randomNumber) + " cat pictures!";
        return randomText;
    }

    public void setTextViewText(TextView tv, int tvID, String tvStringSet) {
        tv = (TextView)findViewById(tvID);
        tv.setText(tvStringSet);
    }

    public void setTextViewVisibility(TextView tv, int tvID, int visibility) {
        tv = (TextView)findViewById(tvID);
        tv.setVisibility(visibility);
    }


    public void saveCatsButtonClicked(View v) {
        layoutView = this.getLayoutInflater().inflate(R.layout.dialog_adview, null);
        dialogBtn = (Button) layoutView.findViewById(R.id.dialogBtn);
        dialogAd = (AdView) layoutView.findViewById(R.id.dialogAdView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        AdRequest dialogAdRequest = new AdRequest.Builder().build();
        dialogAd.loadAd(dialogAdRequest);

        //Wait for ad to load before showing exit button
        dialogAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                dialogBtn.setVisibility(View.VISIBLE);
            }
        });

        builder.setView(layoutView);
        builder.setCancelable(false);
        dialogBox = builder.create();
        dialogBox.show();
    }

    public void dialogButtonClicked(View v) {
        randomText = createRandomCatString();
        setTextViewText(randomTextView, R.id.randomTextView, randomText);
        setTextViewVisibility(randomTextView, R.id.randomTextView, View.VISIBLE);
        dialogBox.cancel();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
