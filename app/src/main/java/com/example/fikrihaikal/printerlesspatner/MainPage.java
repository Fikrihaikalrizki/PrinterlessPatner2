package com.example.fikrihaikal.printerlesspatner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView transaksi;
    DatabaseReference databaseTransaksi;
    List<SaveTransaksi> transaksiList;
    public static final String TRANSACTION_ID = "ID";
    public static final String TRANSACTION_NAME = "NAME";
    public static final String TRANSACTION_KIND = "KIND";
    public static final String TRANSACTION_AGREEMENT = "AGREE";
    public static final String TRANSACTION_MANY = "MANY";
    public static final String TRANSACTION_ADDRESS = "ADDRESS";
    public static final String TRANSACTION_FOLDER = "FOLDER";
    public static final String TRANSACTION_FILE = "FILE";
    public static final String TRANSACTION_DECISION = "DECISION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        transaksi = findViewById(R.id.list_transaksi);
        databaseTransaksi = FirebaseDatabase.getInstance().getReference("Transaksi");
        transaksiList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        transaksi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveTransaksi saveTransaksi = transaksiList.get(position);
                Intent i  = new Intent(MainPage.this,DetailTransaksi.class);
                i.putExtra(TRANSACTION_ID,saveTransaksi.getId());
                i.putExtra(TRANSACTION_NAME,saveTransaksi.getNamaUser());
                i.putExtra(TRANSACTION_KIND,saveTransaksi.getJenis());
                i.putExtra(TRANSACTION_AGREEMENT,saveTransaksi.getKetentuan());
                i.putExtra(TRANSACTION_MANY,saveTransaksi.getBanyak());
                i.putExtra(TRANSACTION_ADDRESS,saveTransaksi.getAlamatPrint());
                i.putExtra(TRANSACTION_FOLDER,saveTransaksi.getFolderPrint());
                i.putExtra(TRANSACTION_FILE,saveTransaksi.getFilePrint());
                i.putExtra(TRANSACTION_DECISION,saveTransaksi.getKeputusan());
                startActivity(i);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(MainPage.this,companyProfile.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTransaksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transaksiList.clear();
                for (DataSnapshot transaksiSnapshot: dataSnapshot.getChildren()){
                    SaveTransaksi saveTransaksi = transaksiSnapshot.getValue(SaveTransaksi.class);
                    transaksiList.add(saveTransaksi);
                }
                TransaksiList adapter = new TransaksiList(MainPage.this,transaksiList);
                transaksi.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
