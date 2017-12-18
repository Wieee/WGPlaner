package vorlesung.hslu.wgapp;

import java.util.HashMap;
import java.util.Map;

public class EinkaufszettelProdukt {

    private String name;
    private int amount;

    //Needed for Firebase communication
    public EinkaufszettelProdukt() {
    }

    public EinkaufszettelProdukt(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString() {
        return amount + "x " + name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("amount", amount);
        return result;
    }
}

