package vorlesung.hslu.wgapp;

import java.util.ArrayList;

/**
 * Created by lukas on 08.11.2017.
 */

public class Wohngemeinschaft {

    private static Wohngemeinschaft wg;
    private String id;
    private ArrayList<Person> mitbewohner;
    private ArrayList<EinkaufszettelProdukt> einkaufszettel;
    private ArrayList<HaushaltsbuchAusgabe> haushaltsbuch;
    private ArrayList<PutzplanAufgabe> putzplan;

    private Wohngemeinschaft(){

        //JSON ABSPEICHERUNG

        mitbewohner = new ArrayList<>();
        einkaufszettel = new ArrayList<>();
        haushaltsbuch = new ArrayList<>();
        putzplan = new ArrayList<>();
    }

    //EIGENTLICH UNNÖTIG
    private Wohngemeinschaft(ArrayList<Person> mitbewohner, ArrayList<EinkaufszettelProdukt> einkaufszettel, ArrayList<HaushaltsbuchAusgabe> haushaltsbuch, ArrayList<PutzplanAufgabe> putzplan){
        this.mitbewohner = mitbewohner;
        this.einkaufszettel = einkaufszettel;
        this.haushaltsbuch = haushaltsbuch;
        this.putzplan = putzplan;
    }

    public void addMitbewohner(Person person){
        mitbewohner.add(person);
    }

    public void addEinkaufszettelProdukt(EinkaufszettelProdukt produkt){
        einkaufszettel.add(produkt);
    }

    public void addHaushaltsbuchAusgabe(HaushaltsbuchAusgabe ausgabe){
        haushaltsbuch.add(ausgabe);
    }

    public void addPutzplanAufageb(PutzplanAufgabe aufgabe){
        putzplan.add(aufgabe);
    }

    public static Wohngemeinschaft getInstance(){
        if (wg == null){
            wg = new Wohngemeinschaft();
        }
        return wg;
    }

    public ArrayList getAttr(String name){
        switch (name){
            case "mitbewohner": return mitbewohner;
            case "einkaufszettel": return einkaufszettel;
            case "haushaltsbuch": return haushaltsbuch;
            case "putzplan": return putzplan;
            default: return null;
        }
    }

}
