package de.oliver.bierdb.entities;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import de.oliver.bierdb.MainActivity;

public class Drink {

    private static Map<Integer, Drink> drinkCache = new HashMap<>();

    private final int id;
    private final String name;
    private final DrinkType type;
    private final float volumePercentage;
    private final float sizeLiter;

    public Drink(int id, String name, DrinkType type, float volumePercentage, float sizeLiter) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.volumePercentage = volumePercentage;
        this.sizeLiter = sizeLiter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DrinkType getType() {
        return type;
    }

    public float getVolumePercentage() {
        return volumePercentage;
    }

    public float getSizeLiter() {
        return sizeLiter;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", volumePercentage=" + volumePercentage +
                ", sizeLiter=" + sizeLiter +
                '}';
    }

    public static Map<Integer, Drink> getDrinkCache() {
        return drinkCache;
    }

    public static boolean isCached(int id){
        return drinkCache.containsKey(id);
    }

    public static Drink getDrinkFromCache(int id){
        return drinkCache.get(id);
    }


    public static Drink getDrink(int id){
        if(isCached(id)){
            return getDrinkFromCache(id);
        }

        AtomicReference<Drink> drink = new AtomicReference<>();

        MainActivity.getDatabase().collection("drinks")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(task -> {
                    List<Drink> drinks = Drink.parse(task);
                    if(drinks.size() >= 1){
                        drink.set(drinks.get(0));
                    }
            });

        return drink.get();
    }

    public static List<Drink> getDrinkByName(String name){
        List<Drink> drinks = new ArrayList<>();

        for(Drink d : drinkCache.values()){
            if(d.getName().equalsIgnoreCase(name)){
                drinks.add(d);
            }
        }

        MainActivity.getDatabase().collection("drinks")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(task -> drinks.addAll(parse(task)));

        return drinks;
    }

    public static List<Drink> parse(Task<QuerySnapshot> task){
        List<Drink> drinks = new ArrayList<>();

        for (DocumentSnapshot document : task.getResult().getDocuments()) {
            int id = document.get("id", Integer.class);
            String name = document.getString("name");
            DrinkType drinkType = DrinkType.getById(document.get("type", Integer.class));
            float volumePercentage = document.get("volumePercentage", Float.class);
            float sizeLiter = document.get("sizeLiter", Float.class);

            Drink drink = new Drink(id, name, drinkType, volumePercentage, sizeLiter);
            drinks.add(drink);

            if(!isCached(id)){
                drinkCache.put(id, drink);
            }
        }

        return drinks;
    }

    public static void addAllDrinksToCache(){
        MainActivity.getDatabase().collection("drinks").get().addOnCompleteListener(Drink::parse);
    }
}
