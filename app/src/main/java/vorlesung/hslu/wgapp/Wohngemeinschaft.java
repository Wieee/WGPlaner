package vorlesung.hslu.wgapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Wohngemeinschaft(){

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("wg");
        final String uID = mAuth.getCurrentUser().getUid();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                for (DataSnapshot singlesnap : snapshot) {
                    if (singlesnap.child("mitbewohner").hasChild(uID)) {
                        wg  = singlesnap.getValue(Wohngemeinschaft.class);
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {}
        });

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
    public void removePutzplanAufgabe(PutzplanAufgabe aufgabe){
        putzplan.remove(aufgabe.getAufgabe());
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
