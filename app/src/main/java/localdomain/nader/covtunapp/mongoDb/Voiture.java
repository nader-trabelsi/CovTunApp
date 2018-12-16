package localdomain.nader.covtunapp.mongoDb;

import java.io.Serializable;

public class Voiture implements Serializable {

    private int nbPlaces;
    private String modele;
    private String marque;
    private Double age;

    public Voiture(int nbPlaces, String modele, String marque, Double age) {
        this.nbPlaces = nbPlaces;
        this.modele = modele;
        this.marque = marque;
        this.age = age;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }
}
