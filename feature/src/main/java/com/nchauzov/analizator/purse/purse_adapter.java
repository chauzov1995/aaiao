package com.nchauzov.analizator.purse;


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
import com.nchauzov.analizator.dohod.dohod_class;
import com.nchauzov.analizator.dohod.dohod_redak;

import java.util.ArrayList;


public class purse_adapter extends RecyclerView.Adapter<purse_adapter.ViewHolder> {


    static ArrayList<purse_class> objects;
    static AppCompatActivity getactivity;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
        // наш пункт состоит только из одного TextView
        public TextView purse_komment, purse_summa;
        public Button button4;
        public LinearLayout llnp, llitem;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);


            purse_komment = (TextView) v.findViewById(R.id.purse_komment);
            purse_summa = (TextView) v.findViewById(R.id.purse_summa);

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

            final purse_class d = objects.get(item.getGroupId());

            return true;
        }


    }


    // Конструктор
    public purse_adapter(ArrayList<purse_class> _objects, AppCompatActivity _getactivity) {
        objects = _objects;
        getactivity = _getactivity;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public purse_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewHolder vh;
        View v;


        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_purse, parent, false);
        vh = new ViewHolder(v);


        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)


        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final purse_class p = objects.get(position);

        holder.purse_komment.setText(p.komment);
        holder.purse_summa.setText(Integer.toString(p.summa));


    }


    void click_cart(dohod_class p) {
        Intent intent = new Intent(getactivity, new_dkr_crea.class);
        intent.putExtra("kuda", p.id);
        getactivity.startActivity(intent);
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