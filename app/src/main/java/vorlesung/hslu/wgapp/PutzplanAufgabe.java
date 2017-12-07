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

    //Liste für Mitbewohner, "wer ist als nächstes dran mit putzen"

    public PutzplanAufgabe(){}

    public PutzplanAufgabe(String aufgabe, String haeufigkeit, Date ersterTag) {
        this.aufgabe = aufgabe;
        this.haeufigkeit = haeufigkeit;
        this.ersterTag = ersterTag;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("aufgabe", aufgabe);
        result.put("haeufigkeit", haeufigkeit);
        result.put("ersterTag", ersterTag);


        return result;
    }
}
