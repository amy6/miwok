package com.example.android.miwok;

import android.annotation.TargetApi;
import android.app.LauncherActivity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mahima on 8/2/18.
 */

public class WordsAdapter extends ArrayAdapter<Word> {

    private int categoryColor;
    private Word word;
    private boolean audioFocusGranted;

    private MediaPlayer player;
    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private MediaPlayer.OnCompletionListener onCompleteListener = new MediaPlayer.OnCompletionListener() {
        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayerRelease();
        }
    };

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focus) {
            Log.v("WordsAdapter", "Focus changed");
            switch (focus) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.v("WordsAdapter", "TRANSIENT_CAN_DUCK - Pausing");
                    if (player != null && player.isPlaying()) {
                        player.pause();
                        player.seekTo(0);
                    }
                    break;

                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.v("WordsAdapter", "LOSS - Stopping");
                    mediaPlayerRelease();
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.v("WordsAdapter", "GAIN - Starting");
                    player.start();
                    break;
            }
        }
    };

    public WordsAdapter(Context context, ArrayList<Word> words, int colorID) {
        super(context, 0, words);
        this.categoryColor = colorID;
    }

    @TargetApi((Build.VERSION_CODES.O))
    public View getView(int position, View convertView, ViewGroup parent) {

        word = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView defaultText = convertView.findViewById(R.id.default_text_view);
        defaultText.setText(word.getDefaultTranslation());

        TextView miwokText = convertView.findViewById(R.id.miwok_text_view);
        miwokText.setText(word.getMiwokTranslation());

        ImageView image = convertView.findViewById(R.id.miwok_image);
        if(word.hasImage()) {
            image.setImageResource(word.getImageId());
            image.setVisibility(View.VISIBLE);
        }
        else
                image.setVisibility(View.GONE);

        final int audioID = word.getAudioID();

        View textContainer = convertView.findViewById(R.id.text_container);
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), categoryColor));


        textContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!audioFocusGranted) {
                        Log.v("WordsAdapter", "Requesting audio focus");
                        int focus = requestAudioFocus();

                        switch (focus) {
                            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                                Log.v("WordsAdapter", "Request Success! Granted!");
                                Log.v("WordsAdapter", "Setting focusGranted to TRUE and then playing audio");
                                audioFocusGranted = true;
                                playAudio(audioID);
                                break;

                            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                                Log.e("WordsAdapter", "Audio Focus request failed");
                                break;
                        }
                    } else {
                        Log.v("WordsAdapter", "Focus already granted, I'm just gonna play");
                        playAudio(audioID);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return convertView;
    }

    private void playAudio(int audioID) {
        mediaPlayerRelease();
        player = MediaPlayer.create(getContext(), audioID);
        player.start();
        Log.v("WordsAdapter", "Calling OnComplete");
        player.setOnCompletionListener(onCompleteListener);
    }


    @TargetApi(Build.VERSION_CODES.O)
    private int requestAudioFocus() {
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();
        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .build();
        return audioManager.requestAudioFocus(audioFocusRequest);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void abandonAudioFocus() {
        if (audioFocusRequest != null) {
            Log.v("WordsAdapter", "Abandoning audio focus NOW");
            audioManager.abandonAudioFocusRequest(audioFocusRequest);
            Log.v("WordsAdapter", "Setting focusGranted to FALSE");
            audioFocusGranted = false;
        }
    }

    public void mediaPlayerRelease() {
        if (player != null) {
            Log.v("WordsAdapter", "Releasing Media Player");
            player.reset();
            player.release();
            player = null;
        }
        abandonAudioFocus();
    }
}
