package de.oliver.bierdb.ui.bierInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import de.oliver.bierdb.R;
import de.oliver.bierdb.entities.Drink;
import de.oliver.bierdb.ui.suchen.SuchenFragment;

public class BierInfoFragment extends Fragment {

    private TextView txt_bier_info_name;
    private TextView txt_bier_info_id;
    private TextView txt_bier_info_typ;
    private TextView txt_bier_info_vol;
    private TextView txt_bier_info_size;
    private ImageButton btn_add_fav;
    private boolean fav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bier_info, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_bier_info_name = view.findViewById(R.id.txt_bier_info_name);
        txt_bier_info_id = view.findViewById(R.id.txt_bier_info_id);
        txt_bier_info_typ = view.findViewById(R.id.txt_bier_info_typ);
        txt_bier_info_vol = view.findViewById(R.id.txt_bier_info_vol);
        txt_bier_info_size = view.findViewById(R.id.txt_bier_info_size);
        btn_add_fav = view.findViewById(R.id.btn_add_fav);

        btn_add_fav.setOnClickListener(v -> {
            if (fav) {
                btn_add_fav.setColorFilter(null);
            } else {
                btn_add_fav.setColorFilter(getResources().getColor(R.color.red_700));
            }
            fav = !fav;
            // TODO: add to fav
        });

        update(SuchenFragment.clickedDrink);
    }

    public void update(Drink drink){
        txt_bier_info_name.setText(drink.getName());
        txt_bier_info_id.setText(drink.getId() + "");
        txt_bier_info_typ.setText(drink.getType().getName());
        txt_bier_info_vol.setText(drink.getVolumePercentage() + " % vol.");
        txt_bier_info_size.setText(drink.getSizeLiter() + " l");

        fav = false; // TODO: hard coded
    }
}