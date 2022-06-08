package de.oliver.bierdb;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import de.oliver.bierdb.databinding.ActivityMainBinding;
import de.oliver.bierdb.entities.Drink;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView navigationView;
    private NavController navController;

    private static MainActivity instance;
    private static FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        database = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Diese Funktion ist noch in der Entwicklung", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;

        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_suchen, R.id.nav_promillerechner, R.id.nav_beitragen)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.close();

                switch (item.getItemId()){
                    case R.id.nav_home:
                        navController.navigate(R.id.nav_home);
                        //Snackbar.make(navigationView, "Home", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        break;
                    case R.id.nav_suchen:
                        //Snackbar.make(navigationView, "Suchen", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        navController.navigate(R.id.suchenFragment);
                        break;
                    case R.id.nav_promillerechner:
                        //Snackbar.make(navigationView, "Promillerechner", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        navController.navigate(R.id.promillerechnerFragment);
                        break;
                    case R.id.nav_beitragen:
                        //Snackbar.make(navigationView, "Beitragen", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        navController.navigate(R.id.beitragenFragment);
                        break;
                }

                navigationView.setCheckedItem(item);
                return false;
            }
        });

        // TODO: remove when database gets big
        // Temporally fetching all items to cache
        database.collection("drinks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Drink.parse(task);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Snackbar.make(navigationView, "Einstellungen", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                navController.navigate(R.id.einstellungenFragment);
                return true;
            case R.id.action_profil:
                //Snackbar.make(navigationView, "Profil", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                navController.navigate(R.id.profilFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public static FirebaseFirestore getDatabase() {
        return database;
    }
}
