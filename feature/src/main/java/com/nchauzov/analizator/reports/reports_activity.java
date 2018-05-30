package com.nchauzov.analizator.reports;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;

import java.util.ArrayList;

public class reports_activity extends AppCompatActivity {


    ArrayList<String> datamassiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_day);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        load_spisDoh();
    }

    public void load_spisDoh() {


        DB_sql dbHelper = new DB_sql(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT DISTINCT data_fakt FROM `an_dkr_hist` WHERE visible=0 order by data_fakt asc", null);

        datamassiv = new ArrayList<String>();
        if (c.moveToFirst()) {
            int data_fakt = c.getColumnIndex("data_fakt");
            do {
                datamassiv.add(c.getString(data_fakt));

            } while (c.moveToNext());
        }
        c.close();


        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        reports_adapter pagerAdapter = new reports_adapter(getSupportFragmentManager(), datamassiv);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(datamassiv.size() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}