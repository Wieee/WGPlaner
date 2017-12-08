package vorlesung.hslu.wgapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukas on 08.11.2017.
 */

public class EinkaufszettelProdukt {

    private String id;
    private String name;
    private int amount;
    private String description;

    public EinkaufszettelProdukt() {
    }

    public EinkaufszettelProdukt(String name, int amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.amount = amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return amount + "x " + name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("description", description);
        result.put("amount", amount);
        return result;
    }
}

