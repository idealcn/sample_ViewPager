package com.idealcn.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by idealgn on 16-9-14.
 */
public class SimpleFragment extends Fragment {

    public static SimpleFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle!=null){
            title = bundle.getString("title");
        }
        TextView textView = new TextView(getActivity());
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(35);

        return textView;
    }
}
