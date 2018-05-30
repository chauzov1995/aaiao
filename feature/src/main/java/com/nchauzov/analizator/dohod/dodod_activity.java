package com.nchauzov.analizator.dohod;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.history.history_activity;
import com.nchauzov.analizator.purse.purse_activity;
import com.nchauzov.analizator.reports.history_class;
import com.nchauzov.analizator.reports.reports_activity;
import com.nchauzov.analizator.statistics.statistics;

import java.util.ArrayList;

public class dodod_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FragmentTransaction fTrans;


    public static ArrayList<history_class> history = new ArrayList<history_class>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dohod_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        load_spisDoh();
    }

    public void load_spisDoh() {

        DB_sql dbHelper = new DB_sql(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<dohod_class> dohod_list = new ArrayList<dohod_class>();

        String startmes_time = "2018.05.01";
        String endmes_time = "2018.05.31";


        Cursor c = db.rawQuery("SELECT a.id, a.komment, a.summa_dohod, b.summa_fakt, a.name_dohod" +
                "        FROM `an_dohod` a" +
                "        LEFT JOIN (" +
                "                SELECT SUM(summa) AS summa_fakt, kuda" +
                "        FROM an_dkr_hist" +
                "        WHERE visible=0 and data_fakt>=? and data_fakt<?" +
                "        GROUP BY kuda) AS b" +
                "        ON a.id = b.kuda" +
                "        WHERE a.visible=0  ORDER by a.id ASC", new String[]{startmes_time, endmes_time});

        if (c.moveToFirst()) {


            int id = c.getColumnIndex("id");
            int summa_dohod = c.getColumnIndex("summa_dohod");
            int komment = c.getColumnIndex("komment");
            int summa_fakt = c.getColumnIndex("summa_fakt");
            int name_dohod = c.getColumnIndex("name_dohod");


            do {
                dohod_list.add(new dohod_class(c.getString(komment), c.getInt(summa_dohod),
                        c.getInt(summa_fakt), c.getInt(id), c.getInt(name_dohod), 0));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }

        dohod_list.add(new dohod_class("Добавить доход", 0,
                0, 0, 1, 1));

        c.close();


        dohod_adapter boxAdapter1 = new dohod_adapter(dohod_list, this);
        RecyclerView gvdoh = (RecyclerView) findViewById(R.id.gvdoh);
        GridLayoutManager glm1 = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager mLayoutManager1 = glm1;

        gvdoh.setLayoutManager(mLayoutManager1);

        gvdoh.setAdapter(boxAdapter1);
        gvdoh.setHasFixedSize(true);

      //  getBalance();
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


    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_micro) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        fTrans = getFragmentManager().beginTransaction();

        if (id == R.id.report_menu) {

            Intent intent = new Intent(this, reports_activity.class);
            startActivity(intent);
        } else if (id == R.id.purse_menu) {
            Intent intent = new Intent(this, purse_activity.class);
            startActivity(intent);
        } else if (id == R.id.statistics) {
            Intent intent = new Intent(this, statistics.class);
            startActivity(intent);
        } else if (id == R.id.history_menu) {
            Intent intent = new Intent(this, history_activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void getBalance(){
        DB_sql dbHelper = new DB_sql(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor c = db.rawQuery("SELECT  (IFNULL(c.summa_fakt,0)-IFNULL(b.summa_fakt,0)) as summa " +
                "   FROM `an_purse` a" +
                "   LEFT JOIN (" +
                "   SELECT SUM(b1.summa) AS summa_fakt, b1.purse " +
                "   FROM an_dkr_hist b1" +
                "   LEFT JOIN an_dohod b2" +
                "   ON b1.kuda=b2.id" +
                "   WHERE b1.visible=0 and b2.name_dohod!=1 " +
                "   GROUP BY b1.purse)  b" +
                "   ON a.id = b.purse" +
                "   LEFT JOIN (" +
                "   SELECT SUM(c1.summa) AS summa_fakt, c1.purse " +
                "   FROM an_dkr_hist c1" +
                "   LEFT JOIN an_dohod c2" +
                "   ON c1.kuda=c2.id" +
                "   WHERE c1.visible=0 and c2.name_dohod=1" +
                "   GROUP BY c1.purse)  c" +
                "   ON a.id = c.purse" +
                "   WHERE a.visible=0  ORDER by a.id ASC", null);

        if (c.moveToFirst()) {


            int summa = c.getColumnIndex("summa");
            Toast.makeText(this, c.getString(summa),Toast.LENGTH_LONG).show();
        }
        c.close();
    }
}