/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.console;

import fr.insalyon.dasi.dao.JpaUtil;
import static fr.insalyon.dasi.ihm.console.MainMedium.afficherMedium;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Genre;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Spirite;
import fr.insalyon.dasi.metier.service.ServiceClient;
import fr.insalyon.dasi.metier.service.ServiceConsultation;
import fr.insalyon.dasi.metier.service.ServiceEmploye;
import fr.insalyon.dasi.metier.service.ServiceMedium;
import fr.insalyon.dasi.metier.service.ServiceUtilisateur;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;


/**
 *
 * @author Corentin
 */
public class MainUtilisateur {
    
    
    
     public static void main(String[] args) {

       // TODO : Pensez à créer une unité de persistance "DASI-PU" et à vérifier son nom dans la classe JpaUtil
        // Contrôlez l'affichage du log de JpaUtil grâce à la méthode log de la classe JpaUtil
        JpaUtil.init();
        initialiserClients();
        initialiserMediums();
        initialiserEmployes();
        testCreerConsultation();
        /*testInscrireClients();
        testAuthentifierClient();
        testDeconnecterClient();
        
        testGenererProfilAstral();
        
        testAjouterHistoriqueClient("sidiparisi@orange.fr", "123sidia");
        
        testObtenirHistoriqueClient("sidiparisi@orange.fr", "123sidia");*/
        
        JpaUtil.destroy();
    }

    public static void afficherClient(Client client) {
        System.out.println("-> " + client);
    }

    public static void initialiserClients(){
        
        
        System.out.println();
        System.out.println("**** initialiserClients() ****");
        System.out.println();
        
        ServiceUtilisateur service=new ServiceUtilisateur();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client ainoha=null ;
        Client sidi=null;
        Client michel=null;
        try{
             ainoha =new Client("Sing","Ainoha","ainoha.sing@free.fr","abahisgod123",simpleDateFormat.parse("11-09-1989"),"4 rue Phelypeayx, Villeurbanne","0509040503");
             sidi = new Client ("Parisi","Sidi","sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
             michel = new Client ("Poluche","Michel","michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        } 
        System.out.println();
        System.out.println("** Clients avant persistance: ");
        afficherClient(ainoha);
        afficherClient(sidi);
        afficherClient(michel);
        System.out.println();
        
        service.inscrireUtilisateur(ainoha);
        service.inscrireUtilisateur(sidi);
        service.inscrireUtilisateur(michel);
       

        
        System.out.println();
        System.out.println("** Clients après persistance: ");
        afficherClient(ainoha);
        afficherClient(sidi);
        afficherClient(michel);
        System.out.println();
    }
    
    public static void initialiserMediums() {
        
        ServiceMedium serviceMedium = new ServiceMedium(); 
        
        System.out.println();
        System.out.println("**** initialiserMediums() ****");
        System.out.println();
       

        Medium gwenaëlle = new Spirite("Gwenaëlle", Genre.Feminin, "Spécialiste des grandes conversations au-delà de TOUTES les frontières", "Boule de cristal");
        Medium tran = new Spirite("Professeur Tran", Genre.Masculin, "Votre avenir est devant vous : regardons-le ensemble !", "Marc de café, boule de cristal, oreilles de lapin");
        
        Medium irma = new Cartomancien("Mme Irma", Genre.Feminin, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Medium endora = new Cartomancien("Endora", Genre.Feminin, "Mes cartes répondront à toutes vos questions personnelles.");
        
        Medium serena = new Astrologue("Serena", Genre.Feminin, "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé", "Ecole Nationale Supérieure d'Astrologie (ENS-Astro)",2006);
        Medium m = new Astrologue("Mr M", Genre.Masculin, "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!", "Institut des Nouveaux Savoirs Astrologiques", 2010);
        
        System.out.println();
       /* System.out.println("** Médium avant persistance: ");
        
        afficherMedium(gwenaëlle);
        afficherMedium(tran);
        afficherMedium(irma);
        afficherMedium(endora);
        afficherMedium(serena);
        afficherMedium(m);
        
        System.out.println();*/

        serviceMedium.inscrireMedium(gwenaëlle);
        serviceMedium.inscrireMedium(tran);
        serviceMedium.inscrireMedium(irma);
        serviceMedium.inscrireMedium(endora);
        serviceMedium.inscrireMedium(serena);
        serviceMedium.inscrireMedium(m);

       /* System.out.println();
        System.out.println("** Médiums après persistance: ");
        afficherMedium(gwenaëlle);
        afficherMedium(tran);
        afficherMedium(irma);
        afficherMedium(endora);
        afficherMedium(serena);
        afficherMedium(m);
        System.out.println();*/
    }
    
    
      public static void initialiserEmployes(){
        
        
        System.out.println();
        System.out.println("**** initialiserEmployes() ****");
        System.out.println();
        
        ServiceUtilisateur service=new ServiceUtilisateur();
       
         Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123");
         Employe martine= new Employe(Genre.Feminin,0,"Geroud","Martine","geroudmartine@gmail.com","petitfilou");
         Employe jeanne=new Employe(Genre.Feminin,0,"Elbert","Jeanne","elbertjeanne@gmail.com","magnitude43");
        
    
        
        service.inscrireUtilisateur(patrick);
        service.inscrireUtilisateur(martine);
        service.inscrireUtilisateur(jeanne);
      
        System.out.println();
        System.out.println("** Les Employes sont bien inscrit");
        
        System.out.println();
    }
      
    public static void testCreerConsultation(){
        
        ServiceUtilisateur serviceUtilisateur=new ServiceUtilisateur();
        ServiceConsultation serviceConsultation=new ServiceConsultation();
        ServiceMedium serviceMedium=new ServiceMedium();
        
        Client client=(Client) serviceUtilisateur.authentifierUtilisateur("sidiparisi@orange.fr","123sidia");
        //Employe employe=(Employe) serviceUtilisateur.authentifierUtilisateur("patrickdolan@gmail.com","lion123");
  
        Medium medium=serviceMedium.detailMediumParId(Long.valueOf(1));
        Consultation consultation=serviceConsultation.demanderConsultation(client, medium);
        //Employe employe2=(Employe) serviceUtilisateur.authentifierUtilisateur("patrickdolan@gmail.com","lion123");
        System.out.println("Consultation crée: "+consultation);
        System.out.println("Employe dispo: "+consultation.getEmploye().isEstDisponible());
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        try{
            System.out.println("Consultation terminée "+ serviceConsultation.validerConsultation(consultation, simpleDateFormat.parse("09-10-2020"),34,"super seance"));
        }catch(ParseException e){}
        System.out.println("Employe dispo: "+employe2.isEstDisponible());*/
    }
    
    public static void testInscrireClients(){
        ServiceUtilisateur ServiceUtilisateur = new ServiceUtilisateur();
   
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client ada=null;
        Client blaise=null;
        Client fred=null;
        
        boolean testPassed=true;
        try{
            ada = new Client("Lovelace", "Ada", "ada.lovelace@insa-lyon.fr", "Ada1012", simpleDateFormat.parse("26-04-1990") ," 2 rue de la croix", "0628196194");
            blaise = new Client("Pascal", "Blaise", "blaise.pascal@insa-lyon.fr", "Blaise1906", simpleDateFormat.parse("01-04-1976"), "4 rue de l'aubergine", "0102030405");
            fred = new Client("Fotiadu", "Frédéric", "blaise.pascal@insa-lyon.fr", "INSA-Forever", simpleDateFormat.parse("25-02-1986"), "9 route du marais", "0203040506");

        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }
       
       
        
        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();
        
        
       
        Long idAda = ServiceUtilisateur.inscrireUtilisateur(ada);
        if (idAda != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(ada);

        
        Long idBlaise = ServiceUtilisateur.inscrireUtilisateur(blaise);
        if (idBlaise != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(blaise);

        Long idFred = ServiceUtilisateur.inscrireUtilisateur(fred);  //devrait échouer car même adresse mail
        if (idFred != null) {
            System.out.println("> Succès inscription");
            testPassed=false;
        } else {
            System.out.println("> Échec inscription");
        }
        afficherClient(fred);
        
        if(testPassed){
            System.out.println();
            System.out.println("**** testerInscriptionClient() validé ****");
            System.out.println();
        }else{
            System.out.println();
            System.out.println("**** testerInscriptionClient() échouée ****");
            System.out.println();
        }
        
    }
    
    public static  void testAuthentifierClient() {
        ServiceUtilisateur ServiceUtilisateur = new ServiceUtilisateur();

        
        System.out.println();
        System.out.println("**** testerAuthentificationClient() ****");
        System.out.println();
        
      
        Client client;
        String mail;
        String motDePasse;
        
        mail = "sidiparisi@orange.fr";
        motDePasse = "123sidia";
        client = (Client) ServiceUtilisateur.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "obelix";
        client = (Client) ServiceUtilisateur.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        client = (Client) ServiceUtilisateur.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
    }

    public static void testDeconnecterClient(){
        
        System.out.println();
        System.out.println("**** testDeconnecterClient() ****");
        System.out.println(); 
        
        
        ServiceUtilisateur service=new ServiceUtilisateur();
        String mail="michelpouche@yahoo.fr";
        String mdp="polucheisking";
        Client c=(Client) service.authentifierUtilisateur(mail,mdp);
        c=(Client) service.deconnecterUtilisateur(c);
        if(c!=null){
            System.out.println();
            System.out.println("test déconnexion réussie");
            System.out.println();
        }else{
            System.out.println();
            System.out.println("test déconnexion échoué");
            System.out.println();
        }
               
        
    }
    
     public static void testGenererProfilAstral(){
        
        System.out.println();
        System.out.println("**** testGenererProfilAstral() ****");
        System.out.println(); 
        
        ServiceUtilisateur service=new ServiceUtilisateur();
        ServiceClient serviceClient=new ServiceClient();
        String mail="michelpouche@yahoo.fr";
        String mdp="polucheisking";
        
        Client c=(Client) service.authentifierUtilisateur(mail,mdp);
        try{
            serviceClient.genererProfilAstral(c);
        }catch(IOException e){
            System.err.print("erreur testerProfilAstral");
        }
        if(c!=null){
            System.out.println();
            System.out.println("test générerProfilAStral réussie");
            System.out.println();
        }else{
            System.out.println();
            System.out.println("test générerProfilAStral échoué");
            System.out.println();
        }   
    }
   
    public static void testAjouterHistoriqueClient(String mail, String motDePasse)
    {
        
        System.out.println();
        System.out.println("**** testAjouterHistoriqueClient() ****");
        System.out.println();
        
        ServiceUtilisateur ServiceUtilisateur = new ServiceUtilisateur();
        ServiceMedium serviceMedium = new ServiceMedium();
        ServiceEmploye serviceEmploye = new ServiceEmploye();
        ServiceConsultation serviceConsultation=new ServiceConsultation();
        
        Client client = (Client) ServiceUtilisateur.authentifierUtilisateur(mail, motDePasse);
        
        Medium irma = new Cartomancien("Mme Irma", Genre.Feminin, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123");
        serviceMedium.inscrireMedium(irma);
        serviceEmploye.inscrireEmploye(patrick);
        
        //Ajouter des consultations au client
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        Date date=null;
        try{
            date=simpleDateFormat.parse("11-01-2020");
        }catch(ParseException e){}
        
        Consultation consultation=new Consultation();
        consultation.setEmploye(patrick);
        consultation.setClient(client);
        consultation.setMedium(irma);
        
        serviceConsultation.ajouterConsultation(consultation);
        
    }
     
    public static void testObtenirHistoriqueClient(String mail, String motDePasse){
        
        System.out.println();
        System.out.println("**** testObtenirHistoriqueClient() ****");
        System.out.println(); 
        
        ServiceUtilisateur ServiceUtilisateur = new ServiceUtilisateur();
        
        Client client = (Client) ServiceUtilisateur.authentifierUtilisateur(mail, motDePasse);
        
        List<Consultation> historique=client.getConsultations();
        
         for (Consultation c : historique) {
             System.out.println(c);
         }
     }
    
}
