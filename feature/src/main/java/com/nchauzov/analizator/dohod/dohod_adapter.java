package com.nchauzov.analizator.dohod;


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


public class dohod_adapter extends RecyclerView.Adapter<dohod_adapter.ViewHolder> {


    static ArrayList<dohod_class> objects;
    static AppCompatActivity getactivity;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
        // наш пункт состоит только из одного TextView
        public TextView tvText, tvText3, textView4, textView5, textView6;
        public Button button4;
        public LinearLayout llnp, llitem;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);


            imageView = (ImageView) v.findViewById(R.id.imageView);
            tvText = (TextView) v.findViewById(R.id.tvText);
            textView5 = (TextView) v.findViewById(R.id.textView5);
            tvText3 = (TextView) v.findViewById(R.id.tvText3);
            textView4 = (TextView) v.findViewById(R.id.textView4);
            textView5 = (TextView) v.findViewById(R.id.textView5);
            textView6 = (TextView) v.findViewById(R.id.textView6);
            button4 = (Button) v.findViewById(R.id.button4);
            llnp = (LinearLayout) v.findViewById(R.id.llnp);
            llitem = (LinearLayout) v.findViewById(R.id.llitem);
            //   v.setOnClickListener(this);
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

            final dohod_class d = objects.get(item.getGroupId());
            switch (item.getTitle().toString()) {
                case "Редактировать":

                    int id = objects.get(item.getGroupId()).id;


                    Intent intent = new Intent(getactivity, dohod_redak.class);
                    intent.putExtra("id", id);
                    getactivity.startActivity(intent);
                    // Log.d("asdasd", "onMenuItemClick1: " + komment);

                    break;
                case "Удалить":
                    AlertDialog.Builder builder = new AlertDialog.Builder(getactivity);
                    builder.setTitle("Важное сообщение!")
                            .setMessage("Покормите кота!")
                            .setCancelable(true)
                            .setNegativeButton("Отмена",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).setPositiveButton("Удалить",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    DB_sql dbHelper = new DB_sql(getactivity);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();


                                    db.execSQL("UPDATE `an_dohod` SET `visible`=1 WHERE" +
                                            " id='" + d.id + "'");

                                    // getactivity.load_spisDoh();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
            }
            return true;
        }


    }


    // Конструктор
    public dohod_adapter(ArrayList<dohod_class> _objects, AppCompatActivity _getactivity) {
        objects = _objects;
        getactivity = _getactivity;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public dohod_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewHolder vh;
        View v;

        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_plus, parent, false);
                vh = new ViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_green, parent, false);
                vh = new ViewHolder(v);
                break;

        }

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)


        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final dohod_class p = objects.get(position);


        switch (this.getItemViewType(position)) {
            case 0:

               // holder.tvText3.setText(p.komment);
                holder.llnp.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View r) {

                        Intent intent = new Intent(getactivity, dohod_redak.class);
                        getactivity.startActivity(intent);

                    }

                });
                break;
            case 1:


                switch (p.name_doh) {
                    case 1:
                        //  dohod_adapter.TintIcons.tintImageView((ImageView) holder.imageView, R.color.ri_green);
                        holder.tvText.setTextColor(getactivity.getResources().getColor(R.color.ri_green));
                        holder.textView4.setTextColor(getactivity.getResources().getColor(R.color.ri_green));
                        holder.textView6.setTextColor(getactivity.getResources().getColor(R.color.ri_green));
                        holder.button4.setBackground(getactivity.getResources().getDrawable(R.drawable.btn_green_item));
                        break;
                    case 2:

                        //  dohod_adapter.TintIcons.tintImageView((ImageView) holder.imageView, R.color.ri_orage);
                        holder.tvText.setTextColor(getactivity.getResources().getColor(R.color.ri_orage));
                        holder.textView4.setTextColor(getactivity.getResources().getColor(R.color.ri_orage));
                        holder.textView6.setTextColor(getactivity.getResources().getColor(R.color.ri_orage));
                        holder.button4.setBackground(getactivity.getResources().getDrawable(R.drawable.btn_orange_item));

                        break;
                    case 3:
                        //  dohod_adapter.TintIcons.tintImageView((ImageView) holder.imageView, R.color.ri_blue);
                        holder.tvText.setTextColor(getactivity.getResources().getColor(R.color.ri_blue));
                        holder.textView4.setTextColor(getactivity.getResources().getColor(R.color.ri_blue));
                        holder.textView6.setTextColor(getactivity.getResources().getColor(R.color.ri_blue));
                        holder.button4.setBackground(getactivity.getResources().getDrawable(R.drawable.btn_blue_item));
                        break;
                    case 4:
                        // dohod_adapter.TintIcons.tintImageView((ImageView) holder.imageView, R.color.ri_brown);
                        holder.tvText.setTextColor(getactivity.getResources().getColor(R.color.ri_brown));
                        holder.textView4.setTextColor(getactivity.getResources().getColor(R.color.ri_brown));
                        holder.textView6.setTextColor(getactivity.getResources().getColor(R.color.ri_brown));
                        holder.button4.setBackground(getactivity.getResources().getDrawable(R.drawable.btn_brown_item));

                        break;


                }

                holder.tvText.setText(Integer.toString(p.suuma_fakt));
                holder.textView4.setText(p.komment);
                holder.textView5.setText(p.suuma_doh + " р.");
                holder.button4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View r) {
                        click_cart(p);
                    }
                });
                holder.llitem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View r) {
                        click_cart(p);
                    }
                });


                break;
        }


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
        return (objects.get(position).new_plus == 1) ? 0 : 1;
    }


    public static class TintIcons {

        public static Drawable tintIcon(Drawable icon, ColorStateList colorStateList) {
            if (icon != null) {
                icon = DrawableCompat.wrap(icon).mutate();
                DrawableCompat.setTintList(icon, colorStateList);
                DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN);
            }
            return icon;
        }


    }
}