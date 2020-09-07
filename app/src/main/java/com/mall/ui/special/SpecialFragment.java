package com.mall.ui.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mall.R;

public class SpecialFragment extends Fragment {

    private SpecialViewModel specialViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        specialViewModel =
                ViewModelProviders.of(this).get(SpecialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_special, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        specialViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}