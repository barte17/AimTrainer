package Main;

import java.io.Serializable;

public class Profil implements Serializable {
    String name;
    int id;




    public Profil(int id,String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Id: "+id+"  Name: "+name;
    }
}
