package com.example.android.miwok;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private WordsAdapter words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);

        ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("one","lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two","oṭiiko", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three","tolokoosu", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four","oyyiisa", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five","massokka", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six","temmokka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight","kawinṭa", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine","wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten","na'aacha", R.drawable.number_ten, R.raw.number_ten));

        words = new WordsAdapter(this,numbers, R.color.category_numbers);

        ListView numbersList = findViewById(R.id.list);
        numbersList.setAdapter(words);

//        numbersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Word currentWord = (Word) adapterView.getItemAtPosition(i);
//                MediaPlayer player = MediaPlayer.create(getApplicationContext(), currentWord.getAudioID());
//                player.start();
//                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        mediaPlayer.release();
//                    }
//                });
//            }
//        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        words.mediaPlayerRelease();
    }
}
