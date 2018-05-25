package com.nchauzov.analizator.dkr;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class new_dkr_crea extends AppCompatActivity {

    Calendar dateAndTime = Calendar.getInstance();
    int kuda_intent, id;
    int name_doh_intent, postoyan_intent, summa_intent;
    String date, komment_intent;
    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
    Toolbar mToolbar;
    EditText summa_edit, komment_edit;
    AppCompatActivity getact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dkr_crea);

        getact=this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        id = getIntent().getIntExtra("id", 0);

        summa_edit = (EditText) findViewById(R.id.summa_edit);
        komment_edit = (EditText) findViewById(R.id.komment_edit);

        if (id != 0) {
            DB_sql dbHelper = new DB_sql(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.rawQuery("select * from `an_dkr_hist` Where id=" + id, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int summa = c.getColumnIndex("summa");
                int komment = c.getColumnIndex("komment");
                int kuda = c.getColumnIndex("kuda");

                komment_edit.setText(c.getString(komment));
                summa_edit.setText(c.getString(summa));
                kuda_intent=c.getInt(kuda);
            }

        }else {

            kuda_intent = getIntent().getIntExtra("kuda", 0);
        }





        Button crea_dkr = (Button) findViewById(R.id.crea_dkr);
        Button crea_dkr_to = (Button) findViewById(R.id.crea_dkr_to);
        Button crea_dkr_kal = (Button) findViewById(R.id.crea_dkr_kal);


        crea_dkr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                setInitialDateTime();
                create_dkr();
            }
        });
        crea_dkr_to.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                dateAndTime.add(Calendar.DATE, -1);
                setInitialDateTime();
                create_dkr();
            }
        });
        crea_dkr_kal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                new DatePickerDialog(new_dkr_crea.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
                setInitialDateTime();

            }
        });


    }


    void create_dkr() {

        DB_sql dbHelper = new DB_sql(new_dkr_crea.this);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();


//внимание потом тут искать чтобы по календарю

// до сюда

        int purse = 1;
        int kuda = kuda_intent;
        int summa = Integer.parseInt(summa_edit.getText().toString());
        String komment = komment_edit.getText().toString();
        String data_fakt = date;

        if (id != 0) {
            db.execSQL("UPDATE `an_dkr_hist` SET " +
                    "`kuda`=" + kuda + ", " +
                    "`purse`=" + purse + ", " +
                    "`data_fakt`='" + data_fakt + "', " +
                    "`summa`=" + summa + ", " +
                    "`komment`='" + komment + "' " +
                    "WHERE id=" + id);
        } else {
            db.execSQL("INSERT INTO `an_dkr_hist`" +
                    " ( `kuda`, `summa`, `komment`, `data_fakt`, `visible`, `purse`)" +
                    " VALUES" +
                    " ( " + kuda + ", " + summa + ", '" + komment + "', '" + data_fakt + "', 0, 1)");
        }


        finish();


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInitialDateTime() {


        date = ft.format(dateAndTime.getTime());
    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

            create_dkr();
        }
    };


}