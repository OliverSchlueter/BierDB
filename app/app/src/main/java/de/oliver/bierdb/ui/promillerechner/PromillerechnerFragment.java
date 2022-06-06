package de.oliver.bierdb.ui.promillerechner;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.oliver.bierdb.R;

public class PromillerechnerFragment extends Fragment {


    public static PromillerechnerFragment newInstance() {
        return new PromillerechnerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_promillerechner, container, false);
    }

}