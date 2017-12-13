package vorlesung.hslu.wgapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Wohngemeinschaft {

    private static Wohngemeinschaft wg;
    private String id;
    private String name;
    private HashMap<String, Person> mitbewohner;
    private HashMap<String, EinkaufszettelProdukt> einkaufszettel;
    private HashMap<String, HaushaltsbuchAusgabe> haushaltsbuch;
    private HashMap<String, PutzplanAufgabe> putzplan;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String uID;

    public static Wohngemeinschaft getInstance() {
        if (wg == null) {
            wg = new Wohngemeinschaft();
        }
        return wg;
    }

    public static void setInstance(Wohngemeinschaft neuewg) {
        wg = neuewg;
    }

    private Wohngemeinschaft() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("wg");
        try{
            uID = mAuth.getCurrentUser().getUid();
        } catch (NullPointerException e){
            //Logout
            wg = null;
            return;
        }

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                for (DataSnapshot singlesnap : snapshot) {
                    if (singlesnap.child("mitbewohner").hasChild(uID)) {
                        wg = singlesnap.getValue(Wohngemeinschaft.class);
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                wg = null;
            }
        });

        mitbewohner = new HashMap<String, Person>();
        einkaufszettel = new HashMap<String, EinkaufszettelProdukt>();
        haushaltsbuch = new HashMap<>();
        putzplan = new HashMap<String, PutzplanAufgabe>();
    }

    public void addMitbewohner(Person person) {
        mitbewohner.put(person.getName(), person);
    }

    public void addEinkaufszettelProdukt(EinkaufszettelProdukt produkt) {
        einkaufszettel.put(produkt.getName(), produkt);
    }

    public void addHaushaltsbuchAusgabe(HaushaltsbuchAusgabe ausgabe) {
        haushaltsbuch.put(ausgabe.getName(), ausgabe);
    }

    public void addPutzplanAufgaben(PutzplanAufgabe aufgabe) {
        putzplan.put(aufgabe.getAufgabe(), aufgabe);
    }

    public void removePutzplanAufgabe(PutzplanAufgabe aufgabe) {
        putzplan.remove(aufgabe.getAufgabe());
    }

    public HashMap<String, Person> getMitbewohner() {
        return mitbewohner;
    }

    public HashMap<String, EinkaufszettelProdukt> getEinkaufszettel() {
        return einkaufszettel;
    }

    public HashMap<String, HaushaltsbuchAusgabe> getHaushaltsbuch() {
        return haushaltsbuch;
    }

    public HashMap<String, PutzplanAufgabe> getPutzplan() {
        return putzplan;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("mitbewohner", mitbewohner);
        result.put("einkaufszettel", einkaufszettel);
        result.put("haushaltsbuch", haushaltsbuch);
        result.put("putzplan", putzplan);
        return result;
    }
}
