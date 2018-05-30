package com.nchauzov.analizator.purse;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;


/**
 * Created by nikita on 19.11.2017.
 */

public class purse_redak extends AppCompatActivity {

    int id_prihod;
    int name_dohod = 1;
    Activity tecactivity;
    //  Context thiscont;


    TextView purse_komment;

    Button doh_red_btn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purse_red);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_prihod = getIntent().getIntExtra("id", 0);


        tecactivity = this;
        //  thiscont = this;
        //  name_dohod= getIntent().getIntExtra("name_dohod", 0);


        purse_komment = (TextView) findViewById(R.id.purse_komment);
        doh_red_btn = (Button) findViewById(R.id.doh_red_btn);


        if (id_prihod != 0) {
            DB_sql dbHelper = new DB_sql(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.rawQuery("select * from `an_purse` Where id=" + id_prihod, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int komment = c.getColumnIndex("komment");

                purse_komment.setText(c.getString(komment));


            }
            doh_red_btn.setText("Сохранить");
        } else {

            doh_red_btn.setText("Создать");
        }


        doh_red_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                DB_sql dbHelper = new DB_sql(tecactivity);
                SQLiteDatabase db = dbHelper.getWritableDatabase();


                if (id_prihod != 0) {
                    db.execSQL("UPDATE `an_purse` SET " +
                            "   `komment`='" + purse_komment.getText() + "', " +
                            "   WHERE id=" + id_prihod);
                } else {
                    db.execSQL("INSERT INTO `an_dohod`" +
                            "( `summa`, `komment`, `visible`, `deafault`) VALUES" +
                            " ('0','" + purse_komment.getText().toString() + "',0,0)");

                }
                finish();
            }
        });


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