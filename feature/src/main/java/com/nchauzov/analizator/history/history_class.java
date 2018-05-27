package com.nchauzov.analizator.history;

public class history_class {

    int id;
    int id_purse;
    int id_kuda;
    String kuda_komment;
    String purse_komment;
    String komment;
    int summa;
    String data_fakt;
    int name_doh;


    history_class(int _id, int _id_purse, int _id_kuda, String _kuda_komment, String _purse_komment, String _komment, int _summa, String _data_fakt, int _name_doh) {
        id = _id;
        id_purse = _id_purse;
        id_kuda = _id_kuda;
        kuda_komment = _kuda_komment;
        purse_komment = _purse_komment;
        komment = _komment;
        summa = _summa;
        data_fakt = _data_fakt;
        name_doh = _name_doh;
    }
}