package fr.insalyon.dasi.ihm.console;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Genre;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Spirite;
import fr.insalyon.dasi.metier.service.Service;
import fr.insalyon.dasi.metier.service.ServiceClient;
import fr.insalyon.dasi.metier.service.ServiceConsultation;
import fr.insalyon.dasi.metier.service.ServiceEmploye;
import fr.insalyon.dasi.metier.service.ServiceMedium;
import fr.insalyon.dasi.metier.service.ServiceStatistiques;
import fr.insalyon.dasi.metier.service.ServiceUtilisateur;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DASI Team
 */
public class MainMedium {

    public static void main(String[] args) {

        // TODO : Pensez à créer une unité de persistance "DASI-PU" et à vérifier son nom dans la classe JpaUtil
        // Contrôlez l'affichage du log de JpaUtil grâce à la méthode log de la classe JpaUtil
        JpaUtil.init();

        //initialiserMediums();            // Question 3
       
        //testerListeMedium();
        //testerListeMediumTriée();
        //testerRechercheMedium();
        //testerInscriptionClient();       // Question 4 & 5
        //testerRechercheClient();         // Question 6
        //testerListeClients();            // Question 7
        //testerAuthentificationClient();  // Question 8
        //saisirInscriptionClient();       // Question 9
        //saisirRechercheClient();
        
        //testStatistiquesMediumConsultes();
        testStatistiquesClientsParEmploye();

        JpaUtil.destroy();
    }

    public static void afficherMedium(Medium medium) {
        System.out.println("-> " + medium);
    }
    
    public static void afficherEmploye(Employe employe) {
        System.out.println("-> " + employe);
    }

    public static void initialiserMediums() {
        
        ServiceMedium serviceMedium = new ServiceMedium(); 
        
        System.out.println();
        System.out.println("**** initialiserMediums() ****");
        System.out.println();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DASI-PU");
        EntityManager em = emf.createEntityManager();

        Medium gwenaëlle = new Spirite("Gwenaëlle", Genre.Feminin, "Spécialiste des grandes conversations au-delà de TOUTES les frontières", "Boule de cristal");
        Medium tran = new Spirite("Professeur Tran", Genre.Masculin, "Votre avenir est devant vous : regardons-le ensemble !", "Marc de café, boule de cristal, oreilles de lapin");
        
        Medium irma = new Cartomancien("Mme Irma", Genre.Feminin, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Medium endora = new Cartomancien("Endora", Genre.Feminin, "Mes cartes répondront à toutes vos questions personnelles.");
        
        Medium serena = new Astrologue("Serena", Genre.Feminin, "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé", "Ecole Nationale Supérieure d'Astrologie (ENS-Astro)",2006);
        Medium m = new Astrologue("Mr M", Genre.Masculin, "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!", "Institut des Nouveaux Savoirs Astrologiques", 2010);
        
        System.out.println();
        System.out.println("** Médium avant persistance: ");
        
        afficherMedium(gwenaëlle);
        afficherMedium(tran);
        afficherMedium(irma);
        afficherMedium(endora);
        afficherMedium(serena);
        afficherMedium(m);
        
        System.out.println();

        serviceMedium.inscrireMedium(gwenaëlle);
        serviceMedium.inscrireMedium(tran);
        serviceMedium.inscrireMedium(irma);
        serviceMedium.inscrireMedium(endora);
        serviceMedium.inscrireMedium(serena);
        serviceMedium.inscrireMedium(m);

        System.out.println();
        System.out.println("** Médiums après persistance: ");
        afficherMedium(gwenaëlle);
        afficherMedium(tran);
        afficherMedium(irma);
        afficherMedium(endora);
        afficherMedium(serena);
        afficherMedium(m);
        System.out.println();
    }
    
    public static void testerRechercheMedium() {
        
        System.out.println();
        System.out.println("**** testerRechercheMedium() ****");
        System.out.println();
        
        ServiceMedium serviceMedium = new ServiceMedium(); 
        long id;
        Medium medium;

        id = 1;
        System.out.println("** Recherche du Médium #" + id);
        medium = serviceMedium.detailMediumParId(id);
        if (medium != null) {
            afficherMedium(medium);
        } else {
            System.out.println("=> Médium non-trouvé");
        }

        id = 3;
        System.out.println("** Recherche du Médium #" + id);
        medium = serviceMedium.detailMediumParId(id);
        if (medium != null) {
            afficherMedium(medium);
        } else {
            System.out.println("=> Médium non-trouvé");
        }

        id = 17;
        System.out.println("** Recherche du Médium #" + id);
        medium = serviceMedium.detailMediumParId(id);
        if (medium != null) {
            afficherMedium(medium);
        } else {
            System.out.println("=> Médium #" + id + " non-trouvé");
        }
    }
    
    public static void testerListeMedium() {
        
        System.out.println();
        System.out.println("**** testerListeMedium() ****");
        System.out.println();
        
        ServiceMedium serviceMedium = new ServiceMedium();
        
        List<Medium> listeMediums = serviceMedium.listeMedium();
        System.out.println("*** Liste des Médiums (non triée)");
        if (listeMediums != null) {
            for (Medium medium : listeMediums) {
                afficherMedium(medium);
            }
        }
        else {
            System.out.println("=> ERREUR...");
        }
    }
    
    public static void testerListeMediumTriée() {
        
        System.out.println();
        System.out.println("**** testerListeMediumTriée() ****");
        System.out.println();
        
        ServiceMedium serviceMedium = new ServiceMedium();
        
        List<Medium> listeMediums = serviceMedium.listeMediumTriée();
        System.out.println("*** Liste des Médiums triée");
        if (listeMediums != null) {
            for (Medium medium : listeMediums) {
                afficherMedium(medium);
            }
        }
        else {
            System.out.println("=> ERREUR...");
        }
    }
    
    public static void testerAuthentificationEmploye() {
        
        System.out.println();
        System.out.println("**** testerAuthentificationEmploye() ****");
        System.out.println();
        
        ServiceEmploye serviceEmploye = new ServiceEmploye();
        
        Employe employe;
        String mail;
        String motDePasse;

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada1012";
        employe = serviceEmploye.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada2020";
        employe = serviceEmploye.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        employe = serviceEmploye.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
    }
    
     public static void testStatistiquesMediumConsultes() {
        
        System.out.println();
        System.out.println("**** testStatistiquesMediumConsultes() ****");
        System.out.println(); 
        
        
        initialiserMediums();
        
        ServiceUtilisateur service=new ServiceUtilisateur();
        ServiceEmploye serviceEmploye = new ServiceEmploye();
        ServiceConsultation serviceConsultation  = new ServiceConsultation();
        ServiceMedium serviceMedium  = new ServiceMedium();
        ServiceClient serviceClient = new ServiceClient();
        ServiceStatistiques serviceStats = new ServiceStatistiques();
        
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123");
        patrick.setEstDisponible(true);
        serviceEmploye.inscrireEmploye(patrick);
        
        Employe valerie= new Employe(Genre.Feminin,0,"Franoux","Valerie","fraval@gmail.com","rude670");
        valerie.setEstDisponible(true);
        serviceEmploye.inscrireEmploye(valerie);
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client michel=null;
        Client sidi = null;
        try{  
            michel = new Client ("Poluche","Michel","michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
            sidi = new Client ("Parisi","Sidi","sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }

        serviceClient.inscrireClient(michel);
        serviceClient.inscrireClient(sidi);
        
        Medium medium1 = serviceMedium.detailMediumParId(new Long(1));
        Medium medium2 = serviceMedium.detailMediumParId(new Long(2));
        
        Consultation consult1 = serviceConsultation.demanderConsultation(michel, medium1);
        
        if(consult1==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 1");
            return;
        }
        
        Consultation consult2 = serviceConsultation.demanderConsultation(sidi, medium1);
                
        if(consult2==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 2");
            return;
        }
        
        serviceConsultation.validerConsultation(consult1, new Date(System.currentTimeMillis()), new Integer(20), "pas mal!");
        
        List<Pair<Medium,Long>> stats = serviceStats.ListeMediumConsultes();
        
        for(Pair<Medium,Long> ligne : stats)
        {
            System.out.println("Medium : "+ligne.getKey()+" - "+ligne.getValue());
        }
        
    }
     
      public static void testStatistiquesClientsParEmploye() {
        
        System.out.println();
        System.out.println("**** testStatistiquesClientsParEmploye() ****");
        System.out.println(); 
        
        
        initialiserMediums();
        
        ServiceUtilisateur service=new ServiceUtilisateur();
        ServiceEmploye serviceEmploye = new ServiceEmploye();
        ServiceConsultation serviceConsultation  = new ServiceConsultation();
        ServiceMedium serviceMedium  = new ServiceMedium();
        ServiceClient serviceClient = new ServiceClient();
        ServiceStatistiques serviceStats = new ServiceStatistiques();
        
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123");
        patrick.setEstDisponible(true);
        serviceEmploye.inscrireEmploye(patrick);
        
        Employe valerie= new Employe(Genre.Feminin,0,"Franoux","Valerie","fraval@gmail.com","rude670");
        valerie.setEstDisponible(true);
        serviceEmploye.inscrireEmploye(valerie);
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client michel=null;
        Client sidi = null;
        try{  
            michel = new Client ("Poluche","Michel","michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
            sidi = new Client ("Parisi","Sidi","sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }

        serviceClient.inscrireClient(michel);
        serviceClient.inscrireClient(sidi);
        
        Medium medium1 = serviceMedium.detailMediumParId(new Long(1));
        Medium medium2 = serviceMedium.detailMediumParId(new Long(2));
        
        Consultation consult1 = serviceConsultation.demanderConsultation(michel, medium1);
        
        if(consult1==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 1");
            return;
        }
        
        Consultation consult2 = serviceConsultation.demanderConsultation(sidi, medium2);
                
        if(consult2==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 2");
            return;
        }
        
        serviceConsultation.validerConsultation(consult1, new Date(System.currentTimeMillis()), new Integer(20), "pas mal!");
        
        List<Pair<Employe,Long>> stats = serviceStats.ListeNombreClientParEmploye();
        
        for(Pair<Employe,Long> ligne : stats)
        {
            System.out.println("Employe : "+ligne.getKey()+" - "+ligne.getValue());
        }
        
    }
}