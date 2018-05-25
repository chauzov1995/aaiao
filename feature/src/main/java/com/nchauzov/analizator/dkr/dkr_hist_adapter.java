package com.nchauzov.analizator.dkr;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.dohod.dohod_class;
import com.nchauzov.analizator.dohod.dohod_redak;

import java.util.ArrayList;
import java.util.Calendar;


public class dkr_hist_adapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<dohod_class> objects;
    Activity getactivity;
    String date;
    dohod_redak new_Dohod;


    DB_sql dbHelper;

    dkr_hist_adapter(Context context, ArrayList<dohod_class> products, Activity _getactivity, String _date) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getactivity = _getactivity;
        date = _date;
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        final dohod_class p = getProduct(position);


        if (view == null) {
            view = lInflater.inflate(R.layout.item_dkr, parent, false);
        }
        Button dkr_item_btn = (Button) view.findViewById(R.id.dkr_item_btn);
        dkr_item_btn.setText(p.komment);
        switch (p.name_doh) {
            case 1:
                dkr_item_btn.setBackgroundColor(Color.parseColor("#99cc55"));
                break;
            case 2:
                dkr_item_btn.setBackgroundColor(Color.parseColor("#ff9966"));
                break;
        }

        dkr_item_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {

                DB_sql dbHelper = new DB_sql(getactivity);
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();


                EditText summa_edit = (EditText) getactivity.findViewById(R.id.summa_edit);
                EditText komment_edit = (EditText) getactivity.findViewById(R.id.komment_edit);


                int kuda = p.id;
                int summa = Integer.parseInt(summa_edit.getText().toString());
                String komment = komment_edit.getText().toString();
                String data_fakt = date;
                int name_doh = p.name_doh;
                // int postoyan = p.postoyan;

                db.execSQL("INSERT INTO `an_dkr_hist`" +
                        " ( `kuda`, `summa`, `komment`, `data_fakt`, `visible`,  name_dohod)" +
                        " VALUES" +
                        " ( '" + kuda + "', '" + summa + "', '" + komment + "', '" + data_fakt + "', 0, '" + name_doh + "')");


                db.execSQL("UPDATE `an_dohod` SET" +
                        " `summa_fakt`=summa_fakt+'" + summa + "'" +
                        " WHERE id='" + kuda + "'");

//определим текущий ли месяц
                boolean istekmes = false;
                String[] tekmesprov = data_fakt.split("\\.");

                String now = Calendar.getInstance().get(Calendar.YEAR) + "." + (Calendar.getInstance().get(Calendar.MONTH) + 1);
                if ((tekmesprov[0] + "." + tekmesprov[1]) == now) istekmes = true;
                String vstavka = "";
                switch (name_doh) {
                    case 1:
                        vstavka = "`balance`=balance+'" + summa + "'";
                        if (istekmes)
                            vstavka += ", vsego_mes_zarab=vsego_mes_zarab+'" + summa + "'";
                        break;
                    case 2:
                        vstavka = "`balance`=balance-'" + summa + "'";
                        if (istekmes) vstavka += ", vsego_mes_potr=vsego_mes_potr+'" + summa + "'";
                        break;
                }

                db.execSQL("UPDATE `an_users` SET " + vstavka);


                getactivity.finish();
            }
        });


        return view;
    }

    // товар по позиции
    dohod_class getProduct(int position) {
        return ((dohod_class) getItem(position));
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {


        return (getProduct(position).new_plus == 1) ? 0 : 1;
    }


}