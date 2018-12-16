package localdomain.nader.covtunapp.mongoDb.mLabDataAPI;

import localdomain.nader.covtunapp.mongoDb.Covoiturage;
import localdomain.nader.covtunapp.mongoDb.Utilisateur;

public class Common {
    private static String DB_NAME = "covoituragedb";
    private static String COLLECTION_NAME_UTILISATEUR = "Utilisateurs" ;
    private static String COLLECTION_NAME_COVOITURAGE = "Covoiturages" ;
    private static String COLLECTION_NAME_DEMANDE = "Demandes" ;
    public static String API_KEY = ""; // API key here

    public static String getUtilisateursCollection(){
        return String.format("https://api.mlab.com/api/1/databases/%s/collections/%s?apiKey=%s",DB_NAME,COLLECTION_NAME_UTILISATEUR,API_KEY);
    }

    public static String getCovoituragesCollection(){
        return String.format("https://api.mlab.com/api/1/databases/%s/collections/%s?apiKey=%s",DB_NAME,COLLECTION_NAME_COVOITURAGE,API_KEY);
    }

    public static String getDemandesCollection(){
        return String.format("https://api.mlab.com/api/1/databases/%s/collections/%s?apiKey=%s",DB_NAME,COLLECTION_NAME_DEMANDE,API_KEY);
    }

    public static String queryUtilisateur(Utilisateur utilisateur){
        return  getUtilisateursCollection()
                +"&q={\"tel\":"
                +utilisateur.getTel()
                +"}&c=true&f={\"prenom\": 1, \"nom\": 1}";
    }

    public static String queryUtilisateurWithPass(Utilisateur utilisateur){
        return getUtilisateursCollection()
                +"&q={\"tel\":"
                +utilisateur.getTel()
                +", \"motdepasse\":\""
                + utilisateur.getPass()
                +"\""+"}&f={\"prenom\": 1, \"nom\": 1}&fo=true";
    }

    public static String queryCovoiturages(Covoiturage cov){
        return getCovoituragesCollection()+"&q={ $or: ["+
                "{\"dateDepart\":\""+cov.getDateDepart()+"\"},"
                + "{\"Trajet.villeDepart\":\""+cov.getTrajet().getVilleDepart()+"\"},"
                + "{\"Trajet.villeArrivee\":\""+cov.getTrajet().getVilleArrivee()+"\"},"
                + "{\"Trajet.pointDepart\":\""+cov.getTrajet().getPointDepart()+"\"},"
                + "{\"Trajet.pointArrivee\":\""+cov.getTrajet().getPointArrivee()+"\"}"
                +"]}";
    }

}