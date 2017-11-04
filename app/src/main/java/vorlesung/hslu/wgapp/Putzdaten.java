package vorlesung.hslu.wgapp;

import java.util.Date;

/**
 * Created by D064744 on 31.10.2017.
 */

public class Putzdaten {

    String aufgabe;
    String haeufigkeit;
    Date ersterTag;
    int profilbild;

    public Putzdaten(String aufgabe, String haeufigkeit, Date ersterTag, int profilbild)
    {
        this.aufgabe = aufgabe;
        this.haeufigkeit=haeufigkeit;
        this.ersterTag=ersterTag;
        this.profilbild = profilbild;

    }

}
