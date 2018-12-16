package localdomain.nader.covtunapp.mongoDb;

import java.io.Serializable;

public class Trajet implements Serializable {

    private String villeDepart;
    private String villeArrivee;
    private String pointDepart;
    private String pointArrivee;

    public Trajet(String villeDepart, String villeArrivee, String pointDepart, String pointArrivee) {
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public String getPointArrivee() {
        return pointArrivee;
    }

    public void setPointArrivee(String pointArrivee) {
        this.pointArrivee = pointArrivee;
    }
}
