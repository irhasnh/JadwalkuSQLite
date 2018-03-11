package id.rackspira.jadwalku;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import id.rackspira.jadwalku.Database.DbHelper;
import id.rackspira.jadwalku.RecyclerView.RecyclerViewMakulHome;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static int showPage = 0;

    private Button buttonHari;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        try{
            refreshListJadwal("Senin");
        } catch (NullPointerException e){
            e.printStackTrace();
            Log.d("refreshlist", e+"");
        }

        buttonHari= findViewById(R.id.buttonHari);
        buttonHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence={"Senin", "Selasa", "Rabu", "Kamis", "Jumat"};
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showPage(i);
                    }
                });
                builder.create().show();
            }
        });
    }

    public void refreshListJadwal(String hari){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMakulHome);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        RecyclerViewMakulHome adapter = new RecyclerViewMakulHome(this, dbHelper.getJadwal(hari));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void showPage(int i){
        switch (i){
            case 0 :
                refreshListJadwal("Senin");
                buttonHari.setText("Senin");

                showPage = 0;
                break;
            case 1 :
                refreshListJadwal("Selasa");
                buttonHari.setText("Selasa");

                showPage = 1;
                break;
            case 2 :
                refreshListJadwal("Rabu");
                buttonHari.setText("Rabu");

                showPage = 2;
                break;
            case 3 :
                refreshListJadwal("Kamis");
                buttonHari.setText("Kamis");

                showPage = 3;
                break;
            case 4 :
                refreshListJadwal("Jumat");
                buttonHari.setText("Jumat");

                showPage = 4;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_input_jadwal) {
            Intent intent=new Intent(this, InputJadwalActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
