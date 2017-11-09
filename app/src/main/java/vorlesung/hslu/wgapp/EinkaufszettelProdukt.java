package vorlesung.hslu.wgapp;

/**
 * Created by lukas on 08.11.2017.
 */

public class EinkaufszettelProdukt {

    private String id;
    private String name;
    private int amount;
    private String descirption;

    public EinkaufszettelProdukt() {
    }

    public EinkaufszettelProdukt(String name, int amount, String descirption) {
        this.name = name;
        this.amount = amount;
        this.descirption = descirption;
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

    public String getDescirption() {
        return descirption;
    }

    public void setDescirption(String descirption) {
        this.descirption = descirption;
    }
}
