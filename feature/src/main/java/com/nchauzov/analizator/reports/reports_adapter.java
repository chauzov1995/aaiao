package com.nchauzov.analizator.reports;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


class reports_adapter extends FragmentStatePagerAdapter {

    ArrayList<String> datamassiv;

    public reports_adapter(FragmentManager fm, ArrayList<String> _datamassiv) {
        super(fm);
        datamassiv = _datamassiv;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return reports_fragment.newInstance(position, datamassiv);
    }

    @Override
    public int getCount() {
        return datamassiv.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Дата " + datamassiv.get(position);
    }

}
