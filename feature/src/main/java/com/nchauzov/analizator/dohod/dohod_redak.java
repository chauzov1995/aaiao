package com.nchauzov.analizator.dohod;


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

public class dohod_redak extends AppCompatActivity {

    int id_prihod;
    int name_dohod = 1;
    Activity tecactivity;
    //  Context thiscont;


    TextView tb_red_name;
    TextView tb_red_summa;
    CheckBox checkBox;
    Button doh_red_btn;
    EditText doh_red_komment;
    EditText doh_red_summa;
    Spinner spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dohod_red);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_prihod = getIntent().getIntExtra("id", 0);


        tecactivity = this;
        //  thiscont = this;
        //  name_dohod= getIntent().getIntExtra("name_dohod", 0);


        tb_red_name = (TextView) findViewById(R.id.tb_red_name);
        tb_red_summa = (TextView) findViewById(R.id.tb_red_summa);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        doh_red_btn = (Button) findViewById(R.id.doh_red_btn);
        doh_red_komment = (EditText) findViewById(R.id.doh_red_komment);
        doh_red_summa = (EditText) findViewById(R.id.doh_red_summa);
        spinner = (Spinner) findViewById(R.id.spinner);




        if (id_prihod != 0) {
            DB_sql dbHelper = new DB_sql(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.rawQuery("select * from `an_dohod` Where id=" + id_prihod, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int summa_dohod = c.getColumnIndex("summa_dohod");
                int komment = c.getColumnIndex("komment");
                int name_dohod = c.getColumnIndex("name_dohod");
                // do {
                doh_red_komment.setText(c.getString(komment));
                doh_red_summa.setText(c.getString(summa_dohod));
                spinner.setSelection(name_dohod==4?1:name_dohod-1);
                checkBox.setChecked(name_dohod==4);
                // } while (c.moveToNext());
            }
            doh_red_btn.setText("Сохранить");
        } else {

            doh_red_btn.setText("Создать");
        }


        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                checkBox.setVisibility(View.GONE);
                if (item.equals("Расход")) {
                    checkBox.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doh_red_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                DB_sql dbHelper = new DB_sql(tecactivity);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                int postoyan_post = 0;
                if (((CheckBox) findViewById(R.id.checkBox)).isChecked()) postoyan_post = 1;

                String selected = spinner.getSelectedItem().toString();
                switch (selected) {
                    case "Доход":
                        name_dohod = 1;
                        break;
                    case "Расход":
                        if (postoyan_post == 1) name_dohod = 4;
                        else name_dohod = 2;

                        break;
                    case "Цель":
                        name_dohod = 3;
                        break;
                }


                if (id_prihod != 0) {
                    db.execSQL("UPDATE `an_dohod` SET " +
                            "`name_dohod`=" + name_dohod + ", " +
                            "`summa_dohod`=" + doh_red_summa.getText() + ", " +
                            "`komment`='" + doh_red_komment.getText() + "', " +
                            "`postoyan`=" + postoyan_post + " " +
                            "WHERE id=" + id_prihod);
                } else {
                    db.execSQL("INSERT INTO `an_dohod`" +
                            "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`) VALUES" +
                            " ('" + name_dohod + "','" + Integer.parseInt(doh_red_summa.getText().toString()) + "',0,'" + doh_red_komment.getText().toString() + "',0)");

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