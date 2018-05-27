package com.nchauzov.analizator.reports;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.dkr.new_dkr_crea;
import com.nchauzov.analizator.dohod.dohod_redak;
import com.nchauzov.analizator.reports.history_class;

import java.util.ArrayList;


public class history_adapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<history_class> objects;
    Activity getactivity;

    dohod_redak new_Dohod;


    DB_sql dbHelper;

    public history_adapter(Context context, ArrayList<history_class> products, Activity _getactivity) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getactivity = _getactivity;
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

        final history_class p = getProduct(position);


        if (view == null) {
            view = lInflater.inflate(R.layout.hist_item, parent, false);
        }

        TextView textView7 = (TextView) view.findViewById(R.id.textView7);
        TextView textView8 = (TextView) view.findViewById(R.id.textView8);
        TextView textView9 = (TextView) view.findViewById(R.id.textView9);
        LinearLayout histitem = (LinearLayout) view.findViewById(R.id.histitem);


        textView8.setText(p.komment);
        textView7.setText(p.nazv_doh);

        if (p.name_doh == 1) {

            textView9.setTextColor(0xFF88bb55);
            textView9.setText("+" + p.suuma + " р.");
        } else {
            textView9.setTextColor(0xFFff5555);
            textView9.setText("-" + p.suuma + " р.");

        }

        histitem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                Intent intent = new Intent(getactivity, new_dkr_crea.class);
                intent.putExtra("id", p.id);
                getactivity.startActivity(intent);
            }
        });


        return view;
    }

    // товар по позиции
    history_class getProduct(int position) {
        return ((history_class) getItem(position));
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


}