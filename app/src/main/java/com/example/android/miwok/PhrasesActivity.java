package com.example.android.miwok;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private WordsAdapter words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);

        ArrayList<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?","minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?","tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...","oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?","michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I'm feeling good.","kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I'm coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I'm coming.","әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Let's go.","yoowutis", R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.","әnni'nem", R.raw.phrase_come_here));

        words = new WordsAdapter(this,phrases, R.color.category_phrases);

        ListView numbersList = findViewById(R.id.list);
        numbersList.setAdapter(words);

    }

    @Override
    protected void onStop() {
        super.onStop();
        words.mediaPlayerRelease();
    }
}
