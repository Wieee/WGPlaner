package vorlesung.hslu.wgapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukas on 08.11.2017.
 */

public class Wohngemeinschaft {

    private static Wohngemeinschaft wg;
    private String id;
    private String name;
    private HashMap<String,Person> mitbewohner;
    private HashMap<String,EinkaufszettelProdukt> einkaufszettel;
    private ArrayList<HaushaltsbuchAusgabe> haushaltsbuch;
    private HashMap <String,PutzplanAufgabe> putzplan;

    private Wohngemeinschaft(){
        //JSON LADEN
        mitbewohner = new HashMap<String,Person>();
        einkaufszettel = new HashMap<String,EinkaufszettelProdukt>();
        haushaltsbuch = new ArrayList<>();
        putzplan = new HashMap<String,PutzplanAufgabe>();
    }

    public static Wohngemeinschaft getInstance(){
        if (wg == null){
            wg = new Wohngemeinschaft();
        }
        return wg;
    }

    public static void  setInstance(Wohngemeinschaft neuewg)
    {
        wg = neuewg;
    }

    //EIGENTLICH UNNÖTIG
    private Wohngemeinschaft(HashMap<String,Person> mitbewohner,HashMap<String,EinkaufszettelProdukt> einkaufszettel, ArrayList<HaushaltsbuchAusgabe> haushaltsbuch, HashMap<String,PutzplanAufgabe> putzplan){
        this.mitbewohner = mitbewohner;
        this.einkaufszettel = einkaufszettel;
        this.haushaltsbuch = haushaltsbuch;
        this.putzplan = putzplan;
    }

    public void addMitbewohner(Person person){
        mitbewohner.put(person.getName(),person );
    }

    public void addEinkaufszettelProdukt(EinkaufszettelProdukt produkt){
        einkaufszettel.put(produkt.getName(),produkt);
    }

    public void addHaushaltsbuchAusgabe(HaushaltsbuchAusgabe ausgabe){
        haushaltsbuch.add(ausgabe);
    }

    public void addPutzplanAufgaben(PutzplanAufgabe aufgabe){
        putzplan.put(aufgabe.getAufgabe(),aufgabe);
    }

    public HashMap<String, Person> getMitbewohner(){
        return mitbewohner;
    }


    public HashMap<String,EinkaufszettelProdukt> getEinkaufszettel(){
        return einkaufszettel;
    }


    public ArrayList getHaushaltsbuch(){
        return haushaltsbuch;
    }


    public HashMap<String, PutzplanAufgabe> getPutzplan(){
        return putzplan;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("mitbewohner", mitbewohner);
        result.put("einkaufszettel", einkaufszettel);
        result.put("haushaltsbuch", haushaltsbuch);
        result.put("putzplan", putzplan);
        return result;
    }
}
