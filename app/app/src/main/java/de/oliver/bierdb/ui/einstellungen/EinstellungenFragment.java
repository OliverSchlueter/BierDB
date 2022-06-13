package de.oliver.bierdb.ui.einstellungen;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.oliver.bierdb.BuildConfig;
import de.oliver.bierdb.MainActivity;
import de.oliver.bierdb.R;
import de.oliver.bierdb.entities.Drink;
import de.oliver.bierdb.entities.User;


public class EinstellungenFragment extends Fragment {

    private Button btn_delete_search_history;
    private Button btn_delete_cache;
    private Button btn_remove_all_fav;
    private TextView txt_version;

    public static EinstellungenFragment newInstance() {
        return new EinstellungenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_einstellungen, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_delete_search_history = view.findViewById(R.id.btn_delete_search_history);
        btn_delete_cache = view.findViewById(R.id.btn_delete_cache);
        btn_remove_all_fav = view.findViewById(R.id.btn_remove_all_fav);
        txt_version = view.findViewById(R.id.txt_version);

        txt_version.setText("Version: " + BuildConfig.VERSION_NAME);

        btn_delete_search_history.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
            builder.setMessage("Wollen Sie wirklich den Suchverlauf löschen?");
            builder.setTitle("Suchverlauf löschen");

            builder.setPositiveButton("JA", (dialog, which) -> {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("searchHistory").apply();
                Toast.makeText(MainActivity.getInstance(), "Erfolgreich den Suchverlauf gelöscht", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("NEIN", (dialog, which) -> {
                Toast.makeText(MainActivity.getInstance(), "Suchverlauf NICHT gelöscht", Toast.LENGTH_SHORT).show();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btn_delete_cache.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
            builder.setMessage("Wollen Sie wirklich den Cache löschen?\nDies läd die Daten der Biere neu");
            builder.setTitle("Cache löschen");

            builder.setPositiveButton("JA", (dialog, which) -> {
                Drink.getDrinkCache().clear();
                Drink.addAllDrinksToCache(); //TODO: remove when database gets large (or in production)
                Toast.makeText(MainActivity.getInstance(), "Erfolgreich den Cache gelöscht", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("NEIN", (dialog, which) -> {
                Toast.makeText(MainActivity.getInstance(), "Cache NICHT gelöscht", Toast.LENGTH_SHORT).show();
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btn_remove_all_fav.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
            builder.setMessage("Wollen Sie wirklich alle Bier-Favoriten löschen?");
            builder.setTitle("Favoriten löschen");

            builder.setPositiveButton("JA", (dialog, which) -> {
                User.getCurrentUser().getFavoriteDrinks().clear();
                User.saveCurrentUser(false);
                Toast.makeText(MainActivity.getInstance(), "Erfolgreich alle Bier-Favoriten gelöscht", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("NEIN", (dialog, which) -> {
                Toast.makeText(MainActivity.getInstance(), "Bier-Favoriten NICHT gelöscht", Toast.LENGTH_SHORT).show();
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}