package vorlesung.hslu.wgapp;

import java.util.ArrayList;

public class HaushaltsbuchAusgabenObjekt {

    private String name;
    private String amount;
    private String date;
    private ArrayList forUsers;
    private String byedBy;


    public HaushaltsbuchAusgabenObjekt(String name, String amount, String date) {
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

    public ArrayList getForUsers(){
        return forUsers;
    }

    public String getByedBy(){
        return byedBy;
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

    public void setForUsers(ArrayList forUsers){
        this.forUsers = forUsers;
    }

    public void setByedBy(String byedBy) {
        this.byedBy = byedBy;
    }

    public String toString(){
        return name + "wurde am " + date + ", von " + byedBy + " für " + amount + "€ gekauft.";
    }


}
