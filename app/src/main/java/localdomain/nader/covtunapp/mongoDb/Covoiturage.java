package localdomain.nader.covtunapp.mongoDb;

import java.io.Serializable;

public class Covoiturage implements Serializable {

    private String heureDepart;
    private String dateDepart;
    private Double prix;
    private int nbReservations;
    private boolean fumeurAutorise;
    private Trajet trajet;
    private Voiture voiture;
    private Utilisateur publieePar;

    public Covoiturage(String dateDepart, Trajet trajet) {
        this.dateDepart = dateDepart;
        this.trajet = trajet;
    }

    public Covoiturage(String heureDepart, String dateDepart, Double prix, int nbReservations, boolean fumeurAutorise, Trajet trajet, Voiture voiture, Utilisateur publieePar) {
        this.heureDepart = heureDepart;
        this.dateDepart = dateDepart;
        this.prix = prix;
        this.nbReservations = nbReservations;
        this.fumeurAutorise = fumeurAutorise;
        this.trajet = trajet;
        this.voiture = voiture;
        this.publieePar = publieePar;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbRservations(int nbRservations) {
        this.nbReservations = nbRservations;
    }

    public boolean isFumeurAutorisee() {
        return fumeurAutorise;
    }

    public void setFumeurAutorisee(boolean fumeurAutorisee) {
        this.fumeurAutorise = fumeurAutorisee;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Utilisateur getPublieePar() {
        return publieePar;
    }

    public void setPublieePar(Utilisateur publieePar) {
        this.publieePar = publieePar;
    }


}
