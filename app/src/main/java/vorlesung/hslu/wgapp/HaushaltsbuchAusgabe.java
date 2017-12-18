package vorlesung.hslu.wgapp;

import java.util.HashMap;
import java.util.Map;

public class HaushaltsbuchAusgabe {

    private String name;
    private double amount;
    private HashMap<String, Person> boughtFor;
    private Person boughtBy;

    //Needed for Firebase communication
    public HaushaltsbuchAusgabe() {
    }

    public HaushaltsbuchAusgabe(String name, double amount, Person boughtBy, HashMap<String, Person> boughtFor) {
        setName(name);
        setAmount(amount);
        setBoughtBy(boughtBy);
        setBoughtFor(boughtFor);
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Person getBoughtBy() {
        return boughtBy;
    }

    public HashMap<String, Person> getBoughtFor() {
        return boughtFor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBoughtBy(Person boughtBy) {
        this.boughtBy = boughtBy;
    }

    public void setBoughtFor(HashMap<String, Person> boughtFor) {
        this.boughtFor = boughtFor;
    }

    public String toString() {
        return name + " wurde von " + boughtBy.getName() + " gekauft.";
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("amount", amount);
        result.put("boughtBy", boughtBy);
        result.put("boughtFor", boughtFor);
        return result;
    }
}
