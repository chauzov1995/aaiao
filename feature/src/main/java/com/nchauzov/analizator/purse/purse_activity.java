package com.nchauzov.analizator.purse;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.dkr.new_dkr_crea;
import com.nchauzov.analizator.reports.history_class;

import java.util.ArrayList;

public class purse_activity extends AppCompatActivity {


    FragmentTransaction fTrans;


    public static ArrayList<history_class> history = new ArrayList<history_class>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purse_activity);


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

        ArrayList<purse_class> dohod_list = new ArrayList<purse_class>();


        Cursor c = db.rawQuery("SELECT a.id, (IFNULL(c.summa_fakt,0)-IFNULL(b.summa_fakt,0)) as summa, a.komment, a.deafault" +
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


            int id = c.getColumnIndex("id");
            int summa = c.getColumnIndex("summa");
            int komment = c.getColumnIndex("komment");
               int deafault = c.getColumnIndex("deafault");


            do {

                dohod_list.add(new purse_class(c.getInt(id), c.getInt(summa),
                        c.getString(komment), c.getInt(deafault)));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }



        c.close();


        purse_adapter boxAdapter1 = new purse_adapter(dohod_list, this);
        RecyclerView gvdoh = (RecyclerView) findViewById(R.id.gvdoh);
        LinearLayoutManager glm1 = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManager1 = glm1;

        gvdoh.setLayoutManager(mLayoutManager1);

        gvdoh.setAdapter(boxAdapter1);
        gvdoh.setHasFixedSize(true);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}