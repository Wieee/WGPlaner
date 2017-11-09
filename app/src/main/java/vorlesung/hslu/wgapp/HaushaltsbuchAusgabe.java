package vorlesung.hslu.wgapp;

import java.util.ArrayList;

public class HaushaltsbuchAusgabe {

    private String id;
    private String name;
    private String amount; //Währung
    private String date;
    private ArrayList<Person> boughtFor;
    private Person boughtBy;

    public HaushaltsbuchAusgabe(){}

    public HaushaltsbuchAusgabe(String name, String amount, String date) {
        setName(name);
        setAmount(amount);
        setDate(date);
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public Person getBoughtBy() {
        return boughtBy;
    }

    public ArrayList<Person> getBoughtFor() {
        return boughtFor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBoughtBy(Person boughtBy) {
        this.boughtBy = boughtBy;
    }

    public void setBoughtFor(ArrayList<Person> boughtFor) {
        this.boughtFor = boughtFor;
    }

    public String toString() {
        return name + "wurde am " + date + ", von " + boughtBy + " für " + amount + "€ gekauft.";
    }


}
