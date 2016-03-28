package com.davidgassner.audioqz;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davidgassner.audioqz.audio.Player;
import com.davidgassner.audioqz.database.DatabaseHelper;
import com.davidgassner.audioqz.layout.CueListAdapter;
import com.davidgassner.audioqz.layout.CueListFragment;
import com.davidgassner.audioqz.model.Cue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
implements CueListFragment.OnFragmentInteractionListener {

    private final String TAG = getClass().getSimpleName();
    private DatabaseHelper dbHelper;

    private TextView fileNameText, volumePercentText;
    private SeekBar seekBar;
    private RelativeLayout playerControls;
    private Button goButton, stopButton;

    private List<Cue> cueList;
    private CueListAdapter listAdapter;

    private Map<String, Player> players = new HashMap<>();

    //    temp: key for a single cue player
    private static final String PLAYER_KEY = "XYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      Call init methods
        dbHelper = new DatabaseHelper(this);
        dbHelper.seedDatabase();

        initWidgetIds();
        initListenersAndState();

//***** Testing code ***********
//      Add cue from database to cue list for testing
        Cue cue = dbHelper.getCueByIndex(1);
        Log.i(TAG, "onCreate: " + cue);


        players.put(PLAYER_KEY, new Player(this, cue));
        Player player = players.get(PLAYER_KEY);

        fileNameText.setText(cue.getTargetFile());
//***** End testing code **********

        cueList = new ArrayList<>();
        cueList.add(cue);

        ListView listView = (ListView) findViewById(R.id.cue_list);
        listAdapter = new CueListAdapter(this, cueList);
        if (listView != null) {
            listView.setAdapter(listAdapter);
        }

    }

    private void initWidgetIds() {
        //        Init widget id's
        playerControls = (RelativeLayout) findViewById(R.id.playerControls);
        fileNameText = (TextView) findViewById(R.id.nowPlayingMessage);
        volumePercentText = (TextView) findViewById(R.id.volumePercentText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        goButton = (Button) findViewById(R.id.goButton);
        stopButton = (Button) findViewById(R.id.stopButton);
    }

    private void initListenersAndState() {

        //        Init widget listeners and states
        playerControls.setVisibility(View.INVISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SeekBar", "progress: " + progress);
                players.get(PLAYER_KEY).setVolume(progress);
                volumePercentText.setText(String.format("%d%%", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playCue(v);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAllCues(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playCue(View view) {
        try {
            players.get(PLAYER_KEY).prepareAndPlay();
            seekBar.setProgress(seekBar.getMax());
            playerControls.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stopAllCues(null);
    }

    public void stopAllCues(View view) {
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            entry.getValue().stop();
        }
        playerControls.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "onFragmentInteraction: " + uri.toString());
    }
}
