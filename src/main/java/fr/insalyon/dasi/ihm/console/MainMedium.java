package fr.insalyon.dasi.ihm.console;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Spirite;
import fr.insalyon.dasi.metier.service.Service;
import fr.insalyon.dasi.metier.service.ServiceMedium;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        initialiserMediums();            // Question 3
       
        testerListeMedium();
        testerListeMediumTriée();
        testerRechercheMedium();
        //testerInscriptionClient();       // Question 4 & 5
        //testerRechercheClient();         // Question 6
        //testerListeClients();            // Question 7
        //testerAuthentificationClient();  // Question 8
        //saisirInscriptionClient();       // Question 9
        //saisirRechercheClient();

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

        Medium gwenaëlle = new Spirite("Gwenaëlle", false, "Spécialiste des grandes conversations au-delà de TOUTES les frontières", "Boule de cristal");
        Medium tran = new Spirite("Professeur Tran", true, "Votre avenir est devant vous : regardons-le ensemble !", "Marc de café, boule de cristal, oreilles de lapin");
        
        Medium irma = new Cartomancien("Mme Irma", false, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Medium endora = new Cartomancien("Endora", false, "Mes cartes répondront à toutes vos questions personnelles.");
        
        Medium serena = new Astrologue("Serena", false, "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé", "Ecole Nationale Supérieure d'Astrologie (ENS-Astro)",2006);
        Medium m = new Astrologue("Mr M", true, "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!", "Institut des Nouveaux Savoirs Astrologiques", 2010);
        
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
        
        ServiceMedium serviceMedium = new ServiceMedium();
        Employe employe;
        String mail;
        String motDePasse;

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada1012";
        employe = serviceMedium.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada2020";
        employe = serviceMedium.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        employe = serviceMedium.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
    }
}