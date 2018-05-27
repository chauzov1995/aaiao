package com.nchauzov.analizator.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.dohod.dohod_adapter;
import com.nchauzov.analizator.dohod.dohod_class;

import java.util.ArrayList;

public class history_activity extends AppCompatActivity {


    ArrayList<String> datamassiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DB_sql dbHelper = new DB_sql(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<history_class> history_list = new ArrayList<history_class>();


        Cursor c = db.rawQuery("SELECT a.id, a.purse as id_purse, a.kuda as id_kuda, b.komment as kuda_komment," +
                " c.komment as purse_komment, a.komment, a.summa, a.data_fakt, b.name_dohod as name_doh " +
                "        FROM `an_dkr_hist` a" +
                "        LEFT JOIN `an_dohod` AS b" +
                "        ON a.kuda = b.id " +
                "        LEFT JOIN `an_purse` AS c" +
                "        ON a.purse = c.id " +
                "        WHERE a.visible=0  ORDER by a.id ASC", null);

        if (c.moveToFirst()) {

            int id = c.getColumnIndex("id");
            int id_purse = c.getColumnIndex("id_purse");
            int id_kuda = c.getColumnIndex("id_kuda");
            int kuda_komment = c.getColumnIndex("kuda_komment");
            int purse_komment = c.getColumnIndex("purse_komment");
            int komment = c.getColumnIndex("komment");
            int summa = c.getColumnIndex("summa");
            int data_fakt = c.getColumnIndex("data_fakt");
            int name_doh = c.getColumnIndex("name_doh");


            do {
                history_list.add(new history_class(
                        c.getInt(id),
                        c.getInt(id_purse),
                        c.getInt(id_kuda),
                        c.getString(kuda_komment),
                        c.getString(purse_komment),
                        c.getString(komment),
                        c.getInt(summa),
                        c.getString(data_fakt),
                        c.getInt(name_doh)
                ));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }

        c.close();

        history_adapter boxAdapter1 = new history_adapter(history_list, this);
        RecyclerView gvdoh = (RecyclerView) findViewById(R.id.gvdoh);
     //   GridLayoutManager glm1 = new GridLayoutManager(this, 3);
    //    RecyclerView.LayoutManager mLayoutManager1 = glm1;

     //   gvdoh.setLayoutManager(mLayoutManager1);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        gvdoh.setLayoutManager(llm);
        gvdoh.setAdapter(boxAdapter1);
        gvdoh.setHasFixedSize(true);
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