package dk.itu.closed_and_anxious;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class TrackUI extends Fragment {
    private String test; // number of playlist (category),  number for track in playlist
    //GUI
    //Should have three buttons (and one image)
    private ImageView trackImg, playBtn, pauseBtn, stopBtn;
    private TextView titleText;

    // mediaplayer to connect to onClick methods
    Mediaplayer mpv;
    Track track;

    // ViewModel for Category
    private CatView cat_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cat_view = new ViewModelProvider(this).get(CatView.class);
        test = getArguments().getString("trackString");
        track = returnTrackFromInt(test);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.track_ui, container, false);

        //assign MediaPlayer to be shared data (Mediaplayer)
        mpv = new Mediaplayer();

        /**
         * Implementing methods from the OnClickListener interface.
         * Each ImageView representing a player button has been made clickable,
         * by using the onClick attribute in its layout and assigning each their related method (see corresponding XML).
         */

        titleText = v.findViewById(R.id.trackTitle);
        titleText.setText(track.getdName());

        trackImg =  v.findViewById(R.id.trackImage);
        trackImg.setImageResource(track.getImageID());

        playBtn = v.findViewById(R.id.play_button);
        playBtn.setOnClickListener(view -> {
                mpv.play(view, track.getKey());
        });

        pauseBtn = v.findViewById(R.id.pause_button);
        pauseBtn.setOnClickListener(view -> {
            mpv.pause(view);
        });

        stopBtn = v.findViewById(R.id.stop_button);
        stopBtn.setOnClickListener(view -> {
            mpv.stop(view);
        });

        return v;
    }

    /**
     * Overrides the onDestroy() lifecycle method of this fragment.
     * Using the Mediaplayer destroyPlayer method resource are released as well.
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        mpv.destroyPlayer();
    }

    /**
     * Gets the information of the activity an sets it orientation to portrait mode.
     * This makes it possible to keep the trackUI layout and continue its state.
     */
    @Override
    public void onResume() {
        super.onResume();

        // if the class of the current fragment is TrackUI...

        boolean DEBUG = false;
        if (DEBUG) Log.i("~~XX~~XX~~XX~~", "Current Fragment class is: " + FragmentManager.findFragment(getView()).getClass());
        if (DEBUG) Log.i("~~XX~~XX~~XX~~", "TrackUI Fragment class is: " + TrackUI.class);

        if (FragmentManager.findFragment(getView()).getClass() == TrackUI.class) {
            // force Portrait
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    public Track returnTrackFromInt(String trackLocation) {
        String[] trackArr = trackLocation.split(" ");

        int playlist = Integer.parseInt(trackArr[0]);
        int track = Integer.parseInt(trackArr[1]);

        Track tempTrack = cat_view.getPlaylist(playlist).getTrackList().get(track);

        return tempTrack;
    }
}
