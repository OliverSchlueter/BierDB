package de.oliver.bierdb.entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.oliver.bierdb.MainActivity;

public class User {

    private static User currentUser;

    // Benutzerdaten
    private String username;
    private String email;

    // Promillerechner
    private float weight;
    private float height;
    private Gender gender;

    // Utils
    private List<Integer> favoriteDrinks; // only id

    public User(String username, String email, float weight, float height, Gender gender, List<Integer> favoriteDrinks) {
        this.username = username;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.favoriteDrinks = favoriteDrinks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Integer> getFavoriteDrinks() {
        return favoriteDrinks;
    }

    public void setFavoriteDrinks(List<Integer> favoriteDrinks) {
        this.favoriteDrinks = favoriteDrinks;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public static void loadCurrentUser(){
        SharedPreferences sharedPreferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);

        List<Integer> favoriteDrinks = new ArrayList<>();

        for (String s : sharedPreferences.getStringSet("currentUser_favoriteDrinks", new HashSet<>())) {
            favoriteDrinks.add(Integer.parseInt(s));
            Toast.makeText(MainActivity.getInstance(), "fav: " + s, Toast.LENGTH_SHORT).show();
        }
        
        setCurrentUser(new User(
                sharedPreferences.getString("currentUser_username", ""),
                sharedPreferences.getString("currentUser_email", ""),
                sharedPreferences.getFloat("currentUser_weight", Float.MAX_VALUE),
                sharedPreferences.getFloat("currentUser_height", Float.MAX_VALUE),
                Gender.parse(sharedPreferences.getString("currentUser_gender", "NONE")),
                favoriteDrinks
        ));
    }

    public static void saveCurrentUser(boolean withFeedback){
        SharedPreferences sharedPreferences = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);

        Set<String> favoriteDrinks = new HashSet<>();
        for (int id : User.getCurrentUser().favoriteDrinks) {
            favoriteDrinks.add(id + "");
        }

        sharedPreferences.edit()
                .putString("currentUser_username", currentUser.getUsername())
                .putString("currentUser_email", currentUser.getEmail())
                .putFloat("currentUser_weight", currentUser.getWeight())
                .putFloat("currentUser_height", currentUser.getHeight())
                .putString("currentUser_gender", currentUser.getGender().toString())
                .putStringSet("currentUser_favoriteDrinks", favoriteDrinks)
                .apply();

        if(withFeedback) {
            Toast.makeText(MainActivity.getInstance(), "Profildaten erfolgreich gespeichert", Toast.LENGTH_SHORT).show();
        }
    }
}