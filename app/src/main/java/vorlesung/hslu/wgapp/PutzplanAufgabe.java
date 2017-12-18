package vorlesung.hslu.wgapp;

import java.util.HashMap;
import java.util.Map;

public class PutzplanAufgabe {

    private String id;
    private String name;
    private String haeufigkeit;
    private Person firstCleaner;

    //Needed for Firebase communication
    public PutzplanAufgabe() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHaeufigkeit() {
        return haeufigkeit;
    }

    public void setHaeufigkeit(String haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    public PutzplanAufgabe(String name, String haeufigkeit, Person firstCleaner) {
        this.name = name;
        this.haeufigkeit = haeufigkeit;
        this.firstCleaner = firstCleaner;
    }

    public String getName() {
        return this.name;
    }

    public void setFirstCleaner(Person cleaner) {
        firstCleaner = cleaner;
    }

    public Person getFirstCleaner() {
        return firstCleaner;
    }

    public Person getNextCleaner() {
        Wohngemeinschaft wg = Wohngemeinschaft.getInstance();
        HashMap<String, Person> mitbewohnermap = wg.getMitbewohner();
        Person[] mitbewohnerarray = mitbewohnermap.values().toArray(new Person[mitbewohnermap.size()]);
        int index = 0;
        for (int i = 0; i < mitbewohnerarray.length; i++) {
            if (mitbewohnerarray[i].getName().equals(firstCleaner.getName())) {
                index = i;
            }
        }
        if (index < (mitbewohnerarray.length - 1)) {
            return mitbewohnerarray[++index];

        } else {
            return mitbewohnerarray[0];
        }

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("haeufigkeit", haeufigkeit);
        result.put("firstCleaner", firstCleaner);
        return result;
    }
}
