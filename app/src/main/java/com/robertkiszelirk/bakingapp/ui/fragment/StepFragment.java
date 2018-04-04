package com.robertkiszelirk.bakingapp.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @BindView(R.id.step_short_description)
    AppCompatTextView stepShortDescription;

    @BindView(R.id.step_video)
    PlayerView videoPlayerView;

    @BindView(R.id.no_video_image)
    ImageView noVideoImage;

    @BindView(R.id.no_internet_connection)
    TextView noInternetConnection;

    @BindView(R.id.step_description)
    AppCompatTextView stepDescription;

    SimpleExoPlayer player;
    boolean playWhenReady = false;
    int currentWindow = 0;
    long playbackPosition = 0;
    private Step step;
    private View rootView;

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Create view
        rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);

        // Release player if it is not null
        if (player != null) {
            releasePlayer();
        }

        if (getArguments() != null) {

            // Get step data
            step = getArguments().getParcelable(rootView.getContext().getString(R.string.pass_step_data_to_fragment_bundle));

            // Set step data
            if (step != null) {
                stepShortDescription.setText(step.getShortDescription());

                stepDescription.setText(step.getDescription());

                if (checkInternetConnection()) {
                    noInternetConnection.setVisibility(View.GONE);
                    if (step.getVideoURL().length() != 0) {
                        videoPlayerView.setVisibility(View.VISIBLE);
                        noVideoImage.setVisibility(View.GONE);
                        // Set player
                        initializePlayer(rootView.getContext());
                    } else {
                        videoPlayerView.setVisibility(View.GONE);
                        noVideoImage.setVisibility(View.VISIBLE);
                    }
                } else {
                    videoPlayerView.setVisibility(View.GONE);
                    noVideoImage.setVisibility(View.GONE);
                    noInternetConnection.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(rootView.getContext(), rootView.getContext().getString(R.string.no_passed_data), Toast.LENGTH_SHORT).show();
            }
        }

        return rootView;
    }

    private void initializePlayer(Context context) {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(), new DefaultLoadControl());

        videoPlayerView.setPlayer(player);


        player.setPlayWhenReady(playWhenReady);

        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
