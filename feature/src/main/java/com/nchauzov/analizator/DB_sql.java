package com.nchauzov.analizator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DB_sql extends SQLiteOpenHelper {

    public DB_sql(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу с полями
//ЕСЛИ


        db.execSQL(" CREATE TABLE `an_dkr_hist` (\n" +
                "  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                //   "  `id_clienta` int(11) NOT NULL,\n" +
                "  `kuda` int(11) NOT NULL,\n" +
                "  `purse` int(11) NOT NULL,\n" +
                "  `summa` decimal(10,0) NOT NULL DEFAULT '0',\n" +
                "  `komment` varchar(255) NOT NULL DEFAULT '',\n" +
                "  `data_fakt` varchar(255) NOT NULL,\n" +
                "  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `visible` int(11) NOT NULL DEFAULT '0'\n" +
                ");"
        );
        db.execSQL(" CREATE TABLE `an_dohod` (\n" +
                "  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                //    "  `id_clienta` int(11) NOT NULL,\n" +
                "  `name_dohod` int(11) NOT NULL,\n" +
                "  `summa_dohod` decimal(11,0) NOT NULL DEFAULT '0',\n" +
                "  `summa_fakt` decimal(10,0) NOT NULL DEFAULT '0',\n" +
                "  `komment` varchar(255) NOT NULL,\n" +
                "  `visible` int(11) NOT NULL DEFAULT '0',\n" +
                "  `postoyan` int(11) NOT NULL DEFAULT '0'\n" +

                ");"
        );
        db.execSQL("CREATE TABLE `an_purse` (\n" +
                "  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                //  "  `id_clienta` int(11) NOT NULL,\n" +
                "  `summa` int(11) NOT NULL,\n" +
                "  `komment` varchar(255) NOT NULL,\n" +
                "  `visible` int(11) NOT NULL DEFAULT '0',\n" +
                "  `deafault` int(11) NOT NULL DEFAULT '0'\n" +
                ");"
        );


        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (1,0,0,'Доход',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Продукты',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Еда вне дома',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Транспорт',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Покупки',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Дом. хоз-во',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Развлечения',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (2,0,0,'Услуги',0,0)");
        db.execSQL("INSERT INTO `an_dohod`" +
                "( `name_dohod`, `summa_dohod`, `summa_fakt`, `komment`, `visible`, `postoyan`) VALUES" +
                " (3,0,0,'Цель',0,0)");

        db.execSQL("INSERT INTO `an_purse`" +
                "( `summa`, `komment`, `visible`, `deafault`) VALUES" +
                " (0,'Кошелёк',0,1)");


        //   Toast.makeText(getactivity,
        //          Long.toString(rowID), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

