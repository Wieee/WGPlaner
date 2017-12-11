package vorlesung.hslu.wgapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meike on 9.11.2017
 */

public class PutzplanAufgabe {

    private String id;
    String aufgabe;
    String haeufigkeit;
    Date ersterTag;
    Person firstCleaner;

    //Liste für Mitbewohner, "wer ist als nächstes dran mit putzen"

    public PutzplanAufgabe(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAufgabe(String aufgabe) {
        this.aufgabe = aufgabe;
    }

    public String getHaeufigkeit() {
        return haeufigkeit;
    }

    public void setHaeufigkeit(String haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    public Date getErsterTag() {
        return ersterTag;
    }

    public void setErsterTag(Date ersterTag) {
        this.ersterTag = ersterTag;
    }

    public PutzplanAufgabe(String aufgabe, String haeufigkeit, Date ersterTag, Person firstCleaner) {
        this.aufgabe = aufgabe;
        this.haeufigkeit = haeufigkeit;
        this.ersterTag = ersterTag;
        this.firstCleaner = firstCleaner;
    }
    public String getAufgabe(){
    return this.aufgabe;
    }
    public void setFirstCleaner(Person cleaner){firstCleaner = cleaner;}
    public Person getFirstCleaner() {return firstCleaner;}

    public Person getNextCleaner(){
      Wohngemeinschaft wg = Wohngemeinschaft.getInstance();
        HashMap<String,Person> mitbewohnermap = wg.getMitbewohner();
        Person[] mitbewohnerarray = mitbewohnermap.values().toArray(new Person[mitbewohnermap.size()]);
        int index = 0;
       for (int i=0; i<mitbewohnerarray.length; i++)
       {
           if(mitbewohnerarray[i].getName().equals(firstCleaner.getName()))
           {index = i;}
       }
       if(index < (mitbewohnerarray.length-1))
       {
           return mitbewohnerarray[++index];

       }
       else{
           return  mitbewohnerarray[0];
       }




    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("aufgabe", aufgabe);
        result.put("haeufigkeit", haeufigkeit);
        result.put("ersterTag", ersterTag);
        result.put("firstCleaner",firstCleaner);


        return result;
    }
}
