/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.console;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.ServiceClient;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        //testInscrireClients();
        testAuthentifierClient();
        testDeconnecterClient();
        
        testGenererProfilAstral();
        JpaUtil.destroy();
    }

    public static void afficherClient(Client client) {
        System.out.println("-> " + client);
    }

    public static void initialiserClients(){
        
        System.out.println();
        System.out.println("**** initialiserClients() ****");
        System.out.println();
        
        ServiceClient service=new ServiceClient();
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
        
        service.inscrireClient(ainoha);
        service.inscrireClient(sidi);
        service.inscrireClient(michel);
        
        
            
        /*
        try {
            em.getTransaction().begin();
            em.persist(ada);
            em.persist(blaise);
            em.persist(fred);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service", ex);
            try {
                em.getTransaction().rollback();
            }
            catch (IllegalStateException ex2) {
                // Ignorer cette exception...
            }
        } finally {
            em.close();
        }*/

        
        System.out.println();
        System.out.println("** Clients après persistance: ");
        afficherClient(ainoha);
        afficherClient(sidi);
        afficherClient(michel);
        System.out.println();
    }
    
    public static void testInscrireClients(){
        
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
       
        ServiceClient serv=new ServiceClient();
       
        
        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();
        
        
       
        Long idAda = serv.inscrireClient(ada);
        if (idAda != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(ada);

        
        Long idBlaise = serv.inscrireClient(blaise);
        if (idBlaise != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(blaise);

        Long idFred = serv.inscrireClient(fred);  //devrait échouer car même adresse mail
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
    
    public static void testAuthentifierClient() {
        
        System.out.println();
        System.out.println("**** testerAuthentificationClient() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        Client client;
        String mail;
        String motDePasse;
        
        mail = "sidiparisi@orange.fr";
        motDePasse = "123sidia";
        client = service.authentifierClient(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "obelix";
        client = service.authentifierClient(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        client = service.authentifierClient(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
    }

    public static void testDeconnecterClient(){
        ServiceClient service=new ServiceClient();
        String mail="michelpouche@yahoo.fr";
        String mdp="polucheisking";
        Client c=service.authentifierClient(mail,mdp);
        c=service.deconnecterClient(c);
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
        ServiceClient service=new ServiceClient();
        String mail="michelpouche@yahoo.fr";
        String mdp="polucheisking";
        
        Client c=service.authentifierClient(mail,mdp);
        try{
            service.genererProfilAstral(c);
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
    
}
