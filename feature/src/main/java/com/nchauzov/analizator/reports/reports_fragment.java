package com.nchauzov.analizator.reports;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nchauzov.analizator.DB_sql;
import com.nchauzov.analizator.R;

import java.util.ArrayList;

public class reports_fragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static ArrayList<String> datamassiv;

    int pageNumber;

    View v;


    public static reports_fragment newInstance(int page, ArrayList<String> _datamassiv) {
        reports_fragment pageFragment = new reports_fragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        datamassiv = _datamassiv;
        return pageFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.new_dkr, null);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


        TextView textView3 = (TextView) v.findViewById(R.id.textView3);


        DB_sql dbHelper = new DB_sql(v.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT an_dkr_hist.id, an_dkr_hist.komment, an_dkr_hist.summa, an_dkr_hist.data_fakt, an_dkr_hist.kuda, an_dohod.name_dohod, an_dohod.komment as nazv_doh" +
                " FROM `an_dkr_hist` " +
                " LEFT JOIN an_dohod ON an_dkr_hist.kuda=an_dohod.id " +
                " WHERE an_dkr_hist.visible=0 ", null);

        ArrayList<history_class> history = new ArrayList<history_class>();
        if (c.moveToFirst()) {
            int id = c.getColumnIndex("id");
            int komment = c.getColumnIndex("komment");
            int suumma = c.getColumnIndex("summa");
            int data_fakt = c.getColumnIndex("data_fakt");
            int kuda = c.getColumnIndex("kuda");
            // int summa_fakt = c.getColumnIndex("summa_fakt");
            int name_dohod = c.getColumnIndex("name_dohod");
            int nazv_doh = c.getColumnIndex("nazv_doh");
            do {
                history.add(new history_class(c.getInt(id), c.getString(komment), c.getInt(suumma), c.getString(data_fakt),
                        c.getInt(kuda), c.getInt(name_dohod),c.getString(nazv_doh)));

            } while (c.moveToNext());
        }

        c.close();


        ArrayList<history_class> poisk = new ArrayList<history_class>();
        int vsego_port = 0;
        for (history_class data : history
                ) {
            if (data.data_fact.equals(datamassiv.get(pageNumber))) {
                poisk.add(data);
                if (data.name_doh !=1) {
                    vsego_port += data.suuma;
                }
                // Log.d("asdad", data.data_fact + " " + datamassiv);

            }

        }
        textView3.setText(Integer.toString(vsego_port) + " р.");

        history_adapter boxAdapter2 = new history_adapter(getActivity(), poisk, getActivity());
        // настраиваем список
        ListView history_lv = (ListView) v.findViewById(R.id.history);
        history_lv.setAdapter(boxAdapter2);
    }
}