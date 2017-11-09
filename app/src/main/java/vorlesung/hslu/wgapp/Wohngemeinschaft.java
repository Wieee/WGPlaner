package vorlesung.hslu.wgapp;

import java.util.ArrayList;

/**
 * Created by lukas on 08.11.2017.
 */

public class Wohngemeinschaft {

    private String id;
    private ArrayList<Person> mitbewohner;
    private ArrayList<EinkaufszettelProdukt> einkaufszettel;
    private ArrayList<HaushaltsbuchAusgabe> haushaltsbuch;
    private ArrayList<PutzplanAufgabe> putzplan;

    public Wohngemeinschaft(){}

    public Wohngemeinschaft(ArrayList<Person> mitbewohner, ArrayList<EinkaufszettelProdukt> einkaufszettel, ArrayList<HaushaltsbuchAusgabe> haushaltsbuch, ArrayList<PutzplanAufgabe> putzplan){
        this.mitbewohner = mitbewohner;
        this.einkaufszettel = einkaufszettel;
        this.haushaltsbuch = haushaltsbuch;
        this.putzplan = putzplan;
    }

}
