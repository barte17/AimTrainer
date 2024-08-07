package MainMenuPanel;

public class Wynik {
    int id;
    String nazwa;
    String tryb;
    String poziom;
    int punkty;

    public Wynik(int id, String nazwa, String tryb, String poziom, int punkty) {
        this.id = id;
        this.nazwa = nazwa;
        this.tryb = tryb;
        this.poziom = poziom;
        this.punkty = punkty;
    }

    public int getId() {
        return this.id;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public String getTryb() {
        return this.tryb;
    }

    public String getPoziom() {
        return this.poziom;
    }

    public int getPunkty() {
        return this.punkty;
    }

    public String toString() {
        return "ID="+id+", "+nazwa+": "+punkty;
    }
}
