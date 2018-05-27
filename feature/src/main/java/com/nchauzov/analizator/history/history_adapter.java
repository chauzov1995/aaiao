package com.nchauzov.analizator.history;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;
import com.nchauzov.analizator.dkr.new_dkr_crea;

import java.util.ArrayList;


public class history_adapter extends RecyclerView.Adapter<history_adapter.ViewHolder> {


    static ArrayList<history_class> objects;
    static AppCompatActivity getactivity;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
        // наш пункт состоит только из одного TextView
        public TextView data_fakt_tv, summa_tv, komment_tv, kuda_tv, purse_tv;
        public Button button4;
        public LinearLayout llnp, llitem;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);


            data_fakt_tv = (TextView) v.findViewById(R.id.data_fakt_tv);
            summa_tv = (TextView) v.findViewById(R.id.summa_tv);
            komment_tv = (TextView) v.findViewById(R.id.komment_tv);
            kuda_tv = (TextView) v.findViewById(R.id.kuda_tv);
            purse_tv = (TextView) v.findViewById(R.id.purse_tv);

            v.setOnCreateContextMenuListener(this);


        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Что сделать?");

            MenuItem red = menu.add(this.getAdapterPosition(), v.getId(), 0, "Редактировать");//groupId, itemId, order, title
            MenuItem del = menu.add(this.getAdapterPosition(), v.getId(), 0, "Удалить");

            red.setOnMenuItemClickListener(this);
            del.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(final MenuItem item) {
            // Menu Item Clicked!

            final history_class d = objects.get(item.getGroupId());
            switch (item.getTitle().toString()) {
                case "Редактировать":


                    break;
            }
            return true;
        }


    }


    // Конструктор
    public history_adapter(ArrayList<history_class> _objects, AppCompatActivity _getactivity) {
        objects = _objects;
        getactivity = _getactivity;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public history_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewHolder vh;
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        vh = new ViewHolder(v);


        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final history_class p = objects.get(position);


        holder.data_fakt_tv.setText(p.data_fakt);
        holder.summa_tv.setText(Integer.toString(p.summa));
        holder.komment_tv.setText(p.komment);
        holder.kuda_tv.setText(p.kuda_komment);
        holder.purse_tv.setText(p.purse_komment);


        switch (this.getItemViewType(position)) {
            case 0:


                break;
        }


    }


    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;//(objects.get(position).new_plus == 1) ? 0 : 1;
    }


}