package localdomain.nader.covtunapp.mongoDb;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private int tel;
    private String nom;
    private String prenom;
    private String pass;

    public Utilisateur(int tel, String nom, String prenom) {
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur(int tel, String pass) {
        this.tel = tel;
        this.pass = pass;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
