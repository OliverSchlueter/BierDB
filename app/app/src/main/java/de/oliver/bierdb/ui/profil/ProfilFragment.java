package de.oliver.bierdb.ui.profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import de.oliver.bierdb.MainActivity;
import de.oliver.bierdb.R;
import de.oliver.bierdb.entities.Gender;
import de.oliver.bierdb.entities.User;

public class ProfilFragment extends Fragment {

    private Button btn_save_profil;
    private Button btn_profil_delete_all;

    private EditText et_username;
    private EditText et_email;
    private EditText et_gewicht;
    private EditText et_groesse;
    private RadioButton rb_maennlich;
    private RadioButton rb_weiblich;

    public static ProfilFragment newInstance() {
        return new ProfilFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_save_profil = view.findViewById(R.id.btn_save_profil);
        btn_profil_delete_all = view.findViewById(R.id.btn_profil_delete_all);
        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_gewicht = view.findViewById(R.id.et_gewicht);
        et_groesse = view.findViewById(R.id.et_groesse);
        rb_maennlich = view.findViewById(R.id.rb_maennlich);
        rb_weiblich = view.findViewById(R.id.rb_weiblich);

        btn_save_profil.setOnClickListener(v -> {
            // TODO: to input validation here

            User.getCurrentUser().setUsername(et_username.getText().toString().length() > 0 ? et_username.getText().toString() : "");
            User.getCurrentUser().setEmail(et_email.getText().toString().length() > 0 ? et_email.getText().toString() : "");
            try {
                float gewicht = Float.parseFloat(et_gewicht.getText().toString());
                User.getCurrentUser().setWeight(gewicht > 0 ? gewicht : Float.MAX_VALUE);
            } catch (NumberFormatException e){
                User.getCurrentUser().setWeight(Float.MAX_VALUE);
            }

            try {
                float groesse = Float.parseFloat(et_groesse.getText().toString());
                User.getCurrentUser().setHeight(groesse > 0 ? groesse : Float.MAX_VALUE);
            } catch (NumberFormatException e){
                User.getCurrentUser().setHeight(Float.MAX_VALUE);
            }

            User.getCurrentUser().setGender(rb_maennlich.isChecked() ? Gender.MALE : rb_weiblich.isChecked() ? Gender.FEMALE : Gender.NO_GENDER);
            User.saveCurrentUser(true);
            updateData();
        });

        btn_profil_delete_all.setOnClickListener(v -> {
            User.setCurrentUser(new User("", "", Float.MAX_VALUE, Float.MAX_VALUE, Gender.NO_GENDER, User.getCurrentUser().getFavoriteDrinks()));
            User.saveCurrentUser(false);
            updateData();
            Toast.makeText(MainActivity.getInstance(), "Erfolgreich alle Profildaten gelöscht", Toast.LENGTH_SHORT).show();
        });

        updateData();
    }

    private void updateData(){
        et_username.setText(User.getCurrentUser().getUsername());
        et_email.setText(User.getCurrentUser().getEmail());
        et_gewicht.setText(User.getCurrentUser().getWeight() != Float.MAX_VALUE ? User.getCurrentUser().getWeight() + "" : "");
        et_groesse.setText(User.getCurrentUser().getHeight() != Float.MAX_VALUE ? User.getCurrentUser().getHeight() + "" : "");
        rb_maennlich.setChecked(User.getCurrentUser().getGender() == Gender.MALE);
        rb_weiblich.setChecked(User.getCurrentUser().getGender() == Gender.FEMALE);
    }
}