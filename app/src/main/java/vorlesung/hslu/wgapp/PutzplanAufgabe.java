package vorlesung.hslu.wgapp;

import java.util.Date;

public class PutzplanAufgabe {

    private String id;
    String aufgabe;
    String haeufigkeit;
    Date ersterTag;

    //Liste für Mitbewohner, "wer ist als nächstes dran mit putzen"

    public PutzplanAufgabe(){}

    public PutzplanAufgabe(String aufgabe, String haeufigkeit, Date ersterTag) {
        this.aufgabe = aufgabe;
        this.haeufigkeit = haeufigkeit;
        this.ersterTag = ersterTag;
    }

}
