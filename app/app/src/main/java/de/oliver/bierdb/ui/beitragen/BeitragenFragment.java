package de.oliver.bierdb.ui.beitragen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.oliver.bierdb.R;

public class BeitragenFragment extends Fragment {

    public static BeitragenFragment newInstance() {
        return new BeitragenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beitragen, container, false);
    }
}