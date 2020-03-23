package com.fmanda.autopartdashboard;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.helper.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        temporaryResetDB();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
            R.id.nav_home,  R.id.nav_profitLoss, R.id.nav_sales, R.id.nav_setting,
                R.id.nav_cashflow, R.id.nav_apaging, R.id.nav_inventory
        ).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavController navController = Navigation.findNavController(this, R.id.nav_profitLoss);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        navController.navigate(R.id.nav_inventory);
//        setBackgroundColor(this.getColor(R.color.colorBackground));
        if (isProjectEmpty()){
//            DownloadProjectOnce();
            navController.navigate(R.id.nav_setting);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void temporaryResetDB(){
        DBHelper dbHelper = DBHelper.getInstance(this);
        dbHelper.resetDatabase(dbHelper.getWritableDatabase());
        Toast.makeText(this, "Database Reset done", Toast.LENGTH_SHORT).show();

    }

    private void DownloadProjectOnce(){
        try {
            ControllerRest cr = new ControllerRest(this);
            cr.setListener(new ControllerRest.Listener() {
                @Override
                public void onSuccess(String msg) {
                    Toast.makeText(MainActivity.this, "Project Updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(String msg) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean isProjectEmpty(){
        ControllerProject cp = new ControllerProject(this);
        return (cp.getProjects().size() == 0);
    }
}
