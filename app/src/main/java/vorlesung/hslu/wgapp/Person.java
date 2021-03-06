package vorlesung.hslu.wgapp;

import java.util.HashMap;
import java.util.Map;

public class Person {

    private String id;
    private String name;
    private String email;

    //Needed for Firebase communication
    public Person() {
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);

        return result;
    }

}
