package com.nchauzov.analizator.dohod;



public class dohod_class {

    public String komment;
    int suuma_doh;
    int suuma_fakt;
    public int id;
    public int name_doh;
    public int new_plus;


    dohod_class(String _komment, int _suuma_doh, int _suuma_fakt, int _id, int _name_doh, int _new_plus ) {
        komment = _komment;
        suuma_doh = _suuma_doh;
        suuma_fakt = _suuma_fakt;
        id = _id;
        name_doh = _name_doh;
        new_plus = _new_plus;

    }


}