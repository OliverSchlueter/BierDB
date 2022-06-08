package de.oliver.bierdb.ui.suchen;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.oliver.bierdb.MainActivity;
import de.oliver.bierdb.R;
import de.oliver.bierdb.entities.Drink;

public class SuchenFragment extends Fragment {

    private SearchView search_bier;
    private LinearLayout list_bier_suggestion;
    private TextView txt_amount_suggestions;

    public static SuchenFragment newInstance() {
        return new SuchenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suchen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_bier = view.findViewById(R.id.search_bier);
        list_bier_suggestion = view.findViewById(R.id.list_bier_suggestion);
        txt_amount_suggestions = view.findViewById(R.id.txt_amount_suggestions);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        for (String s : sharedPreferences.getStringSet("searchHistory", new HashSet<>())) {
            int id = Integer.parseInt(s);
            Drink drink = Drink.getDrink(id);
            if(drink != null){
                generateViewFromDrink(drink);
            }
        }

        search_bier.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "ResourceAsColor", "MutatingSharedPrefs"})
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(view.getContext(), "Search: " + query, Toast.LENGTH_SHORT).show();
                list_bier_suggestion.removeAllViews();
                List<Drink> suggestedDrinks = new ArrayList<>();

                // Searching by ID
                int id;
                try {
                    id = Integer.parseInt(query);
                    suggestedDrinks.add(Drink.getDrink(id));
                } catch (NumberFormatException ignore){ }

                // Searching by Name
                for (Drink drink : Drink.getDrinkCache().values()) {
                    if(drink.getName().toLowerCase().contains(query.toLowerCase())){
                        suggestedDrinks.add(drink);
                    }
                }

                txt_amount_suggestions.setText(suggestedDrinks.size() + " BIER" + (suggestedDrinks.size() > 1 ? "E" : "") + " GEFUNDEN!");


                for (int i = 0; i < suggestedDrinks.size(); i++) {
                    final Drink drink = suggestedDrinks.get(i);

                    generateViewFromDrink(drink);

                    // Add first 3 results to history if they don't already exists
                    if(i <= 3) {
                        Set<String> currentHistory = new HashSet<>();
                        currentHistory.addAll(sharedPreferences.getStringSet("searchHistory", new HashSet<>()));

                        if (!currentHistory.contains(drink.getId() + "")) {
                            currentHistory.add(drink.getId() + "");
                            sharedPreferences.edit().putStringSet("searchHistory", currentHistory).commit();
                            Toast.makeText(MainActivity.getInstance(), "Added: " + drink.getName() + " to search history", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void generateViewFromDrink(Drink drink){
        LinearLayout linearLayout = new LinearLayout(MainActivity.getInstance());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        linearLayout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.getInstance(), "Click on: " + drink.getName(), Toast.LENGTH_SHORT).show();
        });

        ImageView icon = new ImageView(MainActivity.getInstance());
        icon.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_local_drink_24));
        icon.setColorFilter(Color.rgb(235, 146, 52), PorterDuff.Mode.MULTIPLY);

        Space space = new Space(MainActivity.getInstance());
        space.setLayoutParams(new ViewGroup.LayoutParams(20, 1));

        TextView name = new TextView(MainActivity.getInstance());
        name.setText(drink.getName() + " (" + drink.getType().getName() + ")");
        name.setTextSize(18);

        DecimalFormat decimalFormat = new DecimalFormat("###.##");

        TextView desc = new TextView(MainActivity.getInstance());
        desc.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        desc.setText(decimalFormat.format(drink.getSizeLiter()) + " l ");
        desc.setTextColor(getResources().getColor(R.color.gray));
        desc.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        desc.setTextSize(18);

        linearLayout.addView(icon);
        linearLayout.addView(space);
        linearLayout.addView(name);
        linearLayout.addView(desc);

        Space itemSpace = new Space(MainActivity.getInstance());
        itemSpace.setLayoutParams(new ViewGroup.LayoutParams(1, 25));

        list_bier_suggestion.addView(linearLayout);
        list_bier_suggestion.addView(itemSpace);
    }
}