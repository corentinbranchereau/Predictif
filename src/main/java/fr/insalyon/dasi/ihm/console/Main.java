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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author tgravey
 */
public class Main {
    
    public static void main(String[] args) {

        // Contrôlez l'affichage du log de JpaUtil grâce à la méthode log de la classe JpaUtil
        JpaUtil.init();
        
        initialiserClients();
        initialiserMediums();
        initialiserEmployes();
        testCreerConsultation();
  
        JpaUtil.destroy();
    }

   public static void afficherClient(Client client) {
        System.out.println("-> " + client);
    }

    public static void initialiserClients(){
        
        
        System.out.println();
        System.out.println("**** initialiserClients() ****");
        System.out.println();
        
        Service service = new Service();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client ainoha=null ;
        Client sidi=null;
        Client michel=null;
        try{
             ainoha =new Client("Sing","Ainoha",Genre.Feminin,"ainoha.sing@free.fr","abahisgod123",simpleDateFormat.parse("11-09-1989"),"4 rue Phelypeayx, Villeurbanne","0509040503");
             sidi = new Client ("Parisi","Sidi",Genre.Masculin,"sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
             michel = new Client ("Poluche","Michel",Genre.Masculin,"michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
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
        
        Service service = new Service();
        
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

        service.inscrireMedium(gwenaëlle);
        service.inscrireMedium(tran);
        service.inscrireMedium(irma);
        service.inscrireMedium(endora);
        service.inscrireMedium(serena);
        service.inscrireMedium(m);

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
        
        Service service = new Service();
       
         Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123","0908034567");
         Employe martine= new Employe(Genre.Feminin,0,"Geroud","Martine","geroudmartine@gmail.com","petitfilou","0308054543");
         Employe jeanne=new Employe(Genre.Feminin,0,"Elbert","Jeanne","elbertjeanne@gmail.com","magnitude43","0708027594");
        
        service.inscrireUtilisateur(patrick);
        service.inscrireUtilisateur(martine);
        service.inscrireUtilisateur(jeanne);
      
        System.out.println();
        System.out.println("** Les Employes sont bien inscrit");
        
        System.out.println();
    }
      
    public static void testCreerConsultation(){
        
        Service service = new Service();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        Client client=(Client) service.authentifierUtilisateur("sidiparisi@orange.fr","123sidia");
        Client clientBis=(Client) service.authentifierUtilisateur("ainoha.sing@free.fr","abahisgod123");
        Client clientTierce=(Client) service.authentifierUtilisateur("michelpouche@yahoo.fr","polucheisking");
        
        Medium medium=service.detailMediumParId(Long.valueOf(1));
        Consultation consultation=service.demanderConsultation(client, medium);
        //TEST 1: Crée une consultation
        System.out.println("Consultation 1 crée: "+consultation);
        System.out.println();
        
        //TEST 2: choisi un employé qui n'est pas en consultation
        Consultation consultationBis=service.demanderConsultation(clientBis, medium);
        System.out.println("Consultation 2 crée avec un employé différent: "+consultationBis);
         System.out.println();
       
         
        //TEST 3: refuse la création de la 4ème consultation car aucun employé dispo 
         Consultation consultation3=service.demanderConsultation(consultation.getClient(), medium);
         Consultation consultation4=null;
        try{
           consultation4=service.demanderConsultation(new Client("Lovelace", "Ada", Genre.Feminin ,"ada.lovelace@insa-lyon.fr", "Ada1012", simpleDateFormat.parse("26-04-1990") ," 2 rue de la croix", "0628196194"), medium);
            System.out.println("Consultation 3 crée avec le même client que consultation 1 "+ consultation3);
            System.out.println();
            System.out.println("Consultation 4 refusée : "+ consultation4);
            System.out.println();
        }catch(ParseException e){}
        
        
        
        //TEST 4: Validation des consultations
        
        try{
            consultation=service.validerConsultation(consultation, simpleDateFormat.parse("09-10-2020"),34,"super seance");
            System.out.println(" 1ère Consultation terminée "+ consultation);
        }catch(ParseException e){}
       try{
            System.out.println(" 2ème Consultation terminée "+ service.validerConsultation(consultationBis, simpleDateFormat.parse("09-11-2020"),15,"client ennuyant"));
        }catch(ParseException e){}
        try{
            consultation3=service.validerConsultation(consultation3, simpleDateFormat.parse("09-10-2020"),15,"client rigolo");
            System.out.println(" 3ème Consultation terminée "+ consultation3);
        }catch(ParseException e){}
         try{
            System.out.println(" 4ème Consultation terminée - devrait échouer :"+ service.validerConsultation(consultation4, simpleDateFormat.parse("09-08-2020"),15,"client joyeux"));
        }catch(ParseException e){}
        
       //TEST 3: choisi l'employé ayant le moins d'heure
        Consultation consultation5=service.demanderConsultation(consultation3.getClient(), medium);
         System.out.println("Consultation crée avec l'employé ayant le moins d'heure: "+consultation5);
         try{
            System.out.println(" Consultation 5 terminée "+ service.validerConsultation(consultation5, simpleDateFormat.parse("08-10-2020"),22,"ok"));
        }catch(ParseException e){}
         
        //TEST 4: choisi l'employé avec le moins d'heure
         Consultation consultation6=service.demanderConsultation(clientTierce,medium);
         try{
            System.out.println(" Consultation 6 terminée "+ service.validerConsultation(consultation6, simpleDateFormat.parse("02-11-2020"),16,"client très intéressant"));
        }catch(ParseException e){}
         
    }
    
    public static void testInscrireClients(){
        Service service = new Service();
   
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client ada=null;
        Client blaise=null;
        Client fred=null;
        
        boolean testPassed=true;
        try{
            ada = new Client("Lovelace", "Ada", Genre.Feminin ,"ada.lovelace@insa-lyon.fr", "Ada1012", simpleDateFormat.parse("26-04-1990") ," 2 rue de la croix", "0628196194");
            blaise = new Client("Pascal", "Blaise", Genre.Masculin ,"blaise.pascal@insa-lyon.fr", "Blaise1906", simpleDateFormat.parse("01-04-1976"), "4 rue de l'aubergine", "0102030405");
            fred = new Client("Fotiadu", "Frédéric", Genre.Masculin ,"blaise.pascal@insa-lyon.fr", "INSA-Forever", simpleDateFormat.parse("25-02-1986"), "9 route du marais", "0203040506");

        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }
       
       
        
        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();
        
        
       
        Long idAda = service.inscrireUtilisateur(ada);
        if (idAda != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(ada);

        
        Long idBlaise = service.inscrireUtilisateur(blaise);
        if (idBlaise != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
            testPassed=false;
        }
        afficherClient(blaise);

        Long idFred = service.inscrireUtilisateur(fred);  //devrait échouer car même adresse mail
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
        System.out.println();
        System.out.println("**** testerAuthentificationClient() ****");
        System.out.println();
        
        Service service = new Service();
        
        Client client;
        String mail;
        String motDePasse;
        
        mail = "sidiparisi@orange.fr";
        motDePasse = "123sidia";
        client = (Client) service.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "obelix";
        client = (Client) service.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        client = (Client) service.authentifierUtilisateur(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherClient(client);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
    }
    
     public static void testGenererProfilAstral(){
        
        System.out.println();
        System.out.println("**** testGenererProfilAstral() ****");
        System.out.println(); 
        
        Service service = new Service();
        
        String mail="michelpouche@yahoo.fr";
        String mdp="polucheisking";
        
        Client c=(Client) service.authentifierUtilisateur(mail,mdp);
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
   
    public static void testAjouterHistoriqueClient(String mail, String motDePasse)
    {
        
        System.out.println();
        System.out.println("**** testAjouterHistoriqueClient() ****");
        System.out.println();
        
        Service service = new Service();
        
        Client client = (Client) service.authentifierUtilisateur(mail, motDePasse);
        
        Medium irma = new Cartomancien("Mme Irma", Genre.Feminin, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123","09876754");
        service.inscrireMedium(irma);
        service.inscrireUtilisateur(patrick);
        
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
        
        service.ajouterConsultation(consultation);
        
    }
     
    public static void testObtenirHistoriqueClient(String mail, String motDePasse){
        
        System.out.println();
        System.out.println("**** testObtenirHistoriqueClient() ****");
        System.out.println(); 
        
        Service service = new Service();
        
        Client client = (Client) service.authentifierUtilisateur(mail, motDePasse);
        
        List<Consultation> historique=client.getConsultations();
        
         for (Consultation c : historique) {
             System.out.println(c);
         }
    }
    
    public static void afficherMedium(Medium medium) {
        System.out.println("-> " + medium);
    }
    
    public static void afficherEmploye(Employe employe) {
        System.out.println("-> " + employe);
    }
    
    public static void testerRechercheMedium() {
        
        System.out.println();
        System.out.println("**** testerRechercheMedium() ****");
        System.out.println();
        
        Service service = new Service();
        
        long id;
        Medium medium;

        id = 1;
        System.out.println("** Recherche du Médium #" + id);
        medium = service.detailMediumParId(id);
        if (medium != null) {
            afficherMedium(medium);
        } else {
            System.out.println("=> Médium non-trouvé");
        }

        id = 3;
        System.out.println("** Recherche du Médium #" + id);
        medium = service.detailMediumParId(id);
        if (medium != null) {
            afficherMedium(medium);
        } else {
            System.out.println("=> Médium non-trouvé");
        }

        id = 17;
        System.out.println("** Recherche du Médium #" + id);
        medium = service.detailMediumParId(id);
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
        
        Service service = new Service();
        
        List<Medium> listeMediums = service.listeMedium();
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
        
       Service service = new Service();
        
        List<Medium> listeMediums = service.listeMediumTriée();
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
        
        Service service = new Service();
        
        Employe employe;
        String mail;
        String motDePasse;

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada1012";
        employe = (Employe)service.authentifierUtilisateur(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "ada.lovelace@insa-lyon.fr";
        motDePasse = "Ada2020";
        employe = (Employe)service.authentifierUtilisateur(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
            afficherEmploye(employe);
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "etudiant.fictif@insa-lyon.fr";
        motDePasse = "********";
        employe = (Employe)service.authentifierUtilisateur(mail, motDePasse);
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
        
        Service service = new Service();
        
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123","0987675643");
        patrick.setEstDisponible(true);
        service.inscrireUtilisateur(patrick);
        
        Employe valerie= new Employe(Genre.Feminin,0,"Franoux","Valerie","fraval@gmail.com","rude670","0389765643");
        valerie.setEstDisponible(true);
        service.inscrireUtilisateur(valerie);
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client michel=null;
        Client sidi = null;
        try{  
            michel = new Client ("Poluche","Michel",Genre.Masculin,"michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
            sidi = new Client ("Parisi","Sidi",Genre.Masculin,"sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }

        service.inscrireUtilisateur(michel);
        service.inscrireUtilisateur(sidi);
        
        Medium medium1 = service.detailMediumParId(new Long(1));
        Medium medium2 = service.detailMediumParId(new Long(2));
        
        Consultation consult1 = service.demanderConsultation(michel, medium1);
        
        if(consult1==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 1");
            return;
        }
        
        Consultation consult2 = service.demanderConsultation(sidi, medium1);
                
        if(consult2==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 2");
            return;
        }
        
        service.validerConsultation(consult1, new Date(System.currentTimeMillis()), new Integer(20), "pas mal!");
        
        List<Pair<Medium,Long>> stats = service.ListeMediumConsultes();
        
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
        
        Service service = new Service();
        
        Employe patrick= new Employe(Genre.Masculin,0,"Dolan","Patrick","patrickdolan@gmail.com","lion123","0987675643");
        patrick.setEstDisponible(true);
        service.inscrireUtilisateur(patrick);
        
        Employe valerie= new Employe(Genre.Feminin,0,"Franoux","Valerie","fraval@gmail.com","rude670","0389768756");
        valerie.setEstDisponible(true);
        service.inscrireUtilisateur(valerie);
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        
        Client michel=null;
        Client sidi = null;
        try{  
            michel = new Client ("Poluche","Michel",Genre.Masculin,"michelpouche@yahoo.fr","polucheisking",simpleDateFormat.parse("13-02-1965"),"4 avenue de la place, Avignon","0908650306");
            sidi = new Client ("Parisi","Sidi",Genre.Masculin,"sidiparisi@orange.fr","123sidia",simpleDateFormat.parse("23-05-1999"),"4 avenue des grandes Herbes, Saint Michel","0102040306");
        }catch (ParseException e){
            System.out.println("Erreur: ParseException saisie des dates");
        }

        service.inscrireUtilisateur(michel);
        service.inscrireUtilisateur(sidi);
        
        Medium medium1 = service.detailMediumParId(new Long(1));
        Medium medium2 = service.detailMediumParId(new Long(2));
        
        Consultation consult1 = service.demanderConsultation(michel, medium1);
        
        if(consult1==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 1");
            return;
        }
        
        Consultation consult2 = service.demanderConsultation(sidi, medium2);
                
        if(consult2==null)
        {
            System.out.println("Erreur : Pas d'employé disponible pour la consultation 2");
            return;
        }
        
        service.validerConsultation(consult1, new Date(System.currentTimeMillis()), new Integer(20), "pas mal!");
        
        List<Pair<Employe,Long>> stats = service.ListeNombreClientParEmploye();
        
        for(Pair<Employe,Long> ligne : stats)
        {
            System.out.println("Employe : "+ligne.getKey()+" - "+ligne.getValue());
        }
        
    }
}
