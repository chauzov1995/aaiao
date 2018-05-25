package com.nchauzov.analizator.statistics;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;

import java.util.Calendar;

public class statistics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onResume() {
        super.onResume();


        DB_sql dbHelper = new DB_sql(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int summa_dohod = 0, summa_rashod = 0, summa_zel = 0, summa_rashod_postoyan=0;

        Cursor c = db.rawQuery("select sum(summa_dohod) as summa_dohod, name_dohod from `an_dohod` where visible=0 GROUP BY name_dohod", null);
        if (c.moveToFirst()) {
            do {
                switch (c.getInt(c.getColumnIndex("name_dohod"))) {
                    case 1:
                        summa_dohod = c.getInt(c.getColumnIndex("summa_dohod"));
                        break;
                    case 2:
                        summa_rashod = c.getInt(c.getColumnIndex("summa_dohod"));
                        break;
                    case 3:
                        summa_zel = c.getInt(c.getColumnIndex("summa_dohod"));
                        break;
                    case 4:
                        summa_rashod_postoyan = c.getInt(c.getColumnIndex("summa_dohod"));
                        break;
                }
            } while (c.moveToNext());
        }
        c.close();


        Log.d("adasd", "onResume: " + summa_dohod);

        Calendar calendar = Calendar.getInstance();
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // определяем номера столбцов по имени в выборке

        //   int vsego_mes_potr = c.getColumnIndex("vsego_mes_potr");
        //   int vsego_mes_zarab = c.getColumnIndex("vsego_mes_zarab");
        //int balance = c.getColumnIndex("balance");
        int limitmes = summa_dohod - summa_rashod_postoyan - summa_zel;
        int limitned = limitmes / maxDays * 7;

        String[] names = {
                "Баланс: ",
                "Доходы: " + summa_dohod,
                "Постоянные расходы: " + summa_rashod_postoyan,
                "Откладываем на цель: " + summa_zel,
                //   "Потрачено мес: " + c.getInt(vsego_mes_potr),
                //  "Заработано мес: " + c.getInt(vsego_mes_zarab),
                "Лимит на месяц: " + limitmes,
                "Лимит на неделю: " + limitned

        };


        GridView grid = (GridView) findViewById(R.id.grid);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        grid.setAdapter(adapter);


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