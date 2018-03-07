package com.example.android.miwok;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Define the inflation logic for tab and specify the content for the view pager
 */

public class SampleFragment extends Fragment {

    private static final String PAGE_COUNT = "PAGE_COUNT";
    private int page;
    private WordsAdapter words;

    public static SampleFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(PAGE_COUNT, position);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(PAGE_COUNT);
        Log.v("SampleFragment", "Fragment OnCreate called. This will be called only once for each page - PAGE COUNT = " + page);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_words_list, container, false);

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

        words = new WordsAdapter(getActivity(),numbers, R.color.category_numbers);

        ListView numbersList = view.findViewById(R.id.list);
        numbersList.setAdapter(words);

        return numbersList;

    }
}
