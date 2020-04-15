package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.ConsultationDao;
import fr.insalyon.dasi.dao.EmployeDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.dao.MediumDao;
import fr.insalyon.dasi.dao.UtilisateurDao;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import util.Message;

/**
 *
 * @author thibautgravey
 */
public class Service {

    protected ClientDao clientDao = new ClientDao();
    protected EmployeDao employeDao = new EmployeDao();
    protected UtilisateurDao utilisateurDao = new UtilisateurDao();
    protected MediumDao mediumDao=new MediumDao();
    protected ConsultationDao consultationDao=new ConsultationDao();
    
    public Long inscrireUtilisateur(Utilisateur utilisateur) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            utilisateurDao.creer(utilisateur);
            JpaUtil.validerTransaction();
            resultat = utilisateur.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        
        if(utilisateur instanceof Client) {
            StringWriter corps = new StringWriter();
            PrintWriter mailWriter = new PrintWriter(corps);
            String subject = null;
            
            if(resultat != null) { //Envoi d'un message de validation
                subject="Bienvenue chez PREDICT'IF";
                mailWriter.println("Bonjour "+utilisateur.getPrenom()+", nous vous confirmons votre inscription au service"
                        + " PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter"
                        + " des dons incroyables de nos médiums.");
            } else {
                subject="Echec de l'inscription chez PREDICT'IF";
                mailWriter.println("Bonjour "+utilisateur.getPrenom()+", votre inscription au service PREDICT'IF a"
                        + " malencontreusement échoué... Merci de recommencer ultérieurement.");
            }
            
            Message.envoyerMail(
                    "contact@predictif.fr",
                    utilisateur.getEmail(),
                    subject,
                    corps.toString()
                );
        }
        
        return resultat;
    }
    
    public Utilisateur authentifierUtilisateur(String mail, String motDePasse) {
        Utilisateur resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'utilisateur
            Utilisateur utilisateur = utilisateurDao.chercherParMail(mail);
            if (utilisateur != null) {
                // Vérification du mot de passe
                if (utilisateur.getMdp().equals(motDePasse)) {
                    utilisateur.setEstConnecte(true);
                    JpaUtil.ouvrirTransaction();
                    resultat=utilisateurDao.modifier(utilisateur);
                    JpaUtil.validerTransaction();
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierUtilisateur(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Utilisateur obtenirUtilisateurParId(Long id){
          Utilisateur utilisateur=null;
          JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                utilisateur=utilisateurDao.chercherParId(id);
                JpaUtil.validerTransaction();
            } catch (Exception ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service obtenirUtilisateurParId()", ex);
                JpaUtil.annulerTransaction();
                utilisateur = null;
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }      
         return utilisateur;
      }
    
    public ProfilAstral genererProfilAstral(Client client) throws IOException{
         JpaUtil.creerContextePersistance();
         AstroTest astroApi = new AstroTest();
         ProfilAstral resultat = null;
        List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateNaissance());
        ProfilAstral profilClient=new ProfilAstral(profil.get(0),profil.get(1),profil.get(2),profil.get(3));
        try {
            JpaUtil.ouvrirTransaction();
            clientDao.sauvegarderProfilAstral(profilClient);
            JpaUtil.validerTransaction();
            resultat = profilClient;
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service GenererProfilAstral(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        }
     
        if(resultat!=null){
           try {
              JpaUtil.ouvrirTransaction();
              client.setProfilAstral(profilClient);
              client=clientDao.modifier(client);
              JpaUtil.validerTransaction();
           } catch (Exception ex) {
               Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service GenererProfilAstral(client)", ex);
               JpaUtil.annulerTransaction();
               client.setProfilAstral(null);
           }
           finally {
               JpaUtil.fermerContextePersistance();
           }
        }
        return resultat;
    }
    
    
    public List<Consultation> HistoriqueClientTrié(Client client){
        List<Consultation> resultat = client.getConsultations();
        Collections.sort(resultat,new Comparator<Consultation>() {
            public int compare(Consultation c1, Consultation c2) {
                //Si des erreurs de NULL, à ajouter ici.
                return c1.getDateDebut().compareTo(c2.getDateDebut());
            }
        });
        Collections.reverse(resultat); //les plus récentes d'abord
        return resultat;   
    }
    
    public List<Consultation> HistoriqueEmployeTrié(Employe employe){
        List<Consultation> resultat = employe.getConsultations();
        Collections.sort(resultat,new Comparator<Consultation>() {
            public int compare(Consultation c1, Consultation c2) {
                //Si des erreurs de NULL, à ajouter ici.
                return c1.getDateDebut().compareTo(c2.getDateDebut());
            }
        });
        Collections.reverse(resultat); //les plus récentes d'abord
        return resultat;   
    }
    
    public Long inscrireMedium(Medium medium) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            mediumDao.creer(medium);
            JpaUtil.validerTransaction();
            resultat = medium.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireMedium(medium)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Medium> listeMedium() {
        List<Medium> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.listerMediums();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listeMediumDisponibleTriée()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Medium> listeMediumTriée() {
        List<Medium> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.listerSpirites();
            resultat.addAll(mediumDao.listerCartomanciens());
            resultat.addAll(mediumDao.listerAstrologues());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listeMediumDisponibleTriée()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Medium detailMediumParId(Long clientId) {
        Medium resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.chercherParId(clientId);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service DetailMediumParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Consultation ajouterConsultation(Consultation consultation){ //permet d'ajouter une consultation dans la bd
        
         
        Consultation resultat = null;
        if(consultation!=null){
            if(consultation.getClient() !=null && consultation.getEmploye() !=null && consultation.getMedium() !=null){      
                JpaUtil.creerContextePersistance();
                try {
                    JpaUtil.ouvrirTransaction();     
                    Client c=clientDao.modifier(consultation.getClient());
                    employeDao.modifier(consultation.getEmploye());
                    JpaUtil.validerTransaction();
                    
                    List<Consultation> list=c.getConsultations();
                    resultat = list.get(list.size()-1);
                } catch (Exception ex) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                    JpaUtil.annulerTransaction();
                    resultat = null;
                }
                finally {
                    JpaUtil.fermerContextePersistance();
                }      
            }
        }
        return resultat;
    }
    
      public Consultation demanderConsultation(Client client, Medium medium){ //renvoie la consultation crée avec l'employé assigné 
                                                                              // si pas d'employé trouvé,  renvoie null;
          // Atention: si on appelle plusieurs fois cette méthode avec le même client, penser à mettre à jour le client à chaque fois, 
          // avec la valeur de consultation de retour: client=consultation.getClient();
          
          Consultation consultation=null;
          List<Employe> listeEmployes=null;
          JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                listeEmployes=employeDao.listerEmployes();
                JpaUtil.validerTransaction();
            } catch (Exception ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                JpaUtil.annulerTransaction();
                listeEmployes = null;
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }      
            
            Employe employeLibre=null;
            Iterator<Employe> iterator=listeEmployes.iterator();
            while(iterator.hasNext() && employeLibre==null){
                Employe e=iterator.next();
                if(e.isEstDisponible()){
                    employeLibre=e;
                }
            }
            
            if(employeLibre!=null){
                employeLibre.setEstDisponible(false);
                consultation=new Consultation();
                consultation.setClient(client);
                consultation.setMedium(medium);
                consultation.setEmploye(employeLibre);
             
                consultation=ajouterConsultation(consultation);
               
                StringWriter message = new StringWriter();
                PrintWriter notificationWriter = new PrintWriter(message);
        
                notificationWriter.println("Bonjour "+employeLibre.getPrenom()+". Consultation"
                        + " requise pour "+client.getGenre().getAbreviation()+" "+client.getPrenom()
                        + " "+client.getNom()+". Médium à incarner : "+medium.getDenomination()+".");

                Message.envoyerNotification(
                        employeLibre.getTelephone(),
                        message.toString()
                );
                
            }else{
                System.out.println("Pas d'employé de disponible pour la consultation");
            }
           
         return consultation;    
      }
      
      public Consultation commencerConsultation(Consultation consultation) {
          
            consultation.setDateDebut(new Date(System.currentTimeMillis()));
        
            Client cl = consultation.getClient();
            Medium m = consultation.getMedium();
            Employe e = consultation.getEmploye();
            SimpleDateFormat calendarStyle = new SimpleDateFormat("'du' dd/MM/yy à hh:mm");
            
            JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                cl=clientDao.modifier(consultation.getClient());
                employeDao.modifier(consultation.getEmploye());
                
                consultation=consultationDao.chercherParId(consultation.getId());
                
                JpaUtil.validerTransaction();
                
                StringWriter message = new StringWriter();
                PrintWriter notificationWriter = new PrintWriter(message);
        
                notificationWriter.println("Bonjour "+cl.getPrenom()+". J'ai bien reçu"
                        + " votre demande de consultation "+ calendarStyle.format(consultation.getDateDebut())+"."
                        + " Vous pouvez dès à présent me contacter au "+e.getTelephone()+". A tout de suite !"
                        + " Médiumiquement votre, "+m.getDenomination()+".");

                Message.envoyerNotification(
                        cl.getTelephone(),
                        message.toString()
                );
                      
            } catch (Exception ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                JpaUtil.annulerTransaction();

                consultation=null;               
            }
            finally {
                JpaUtil.fermerContextePersistance();             
            }    
            return consultation;
    }
      
    public Consultation validerConsultation(Consultation consultation, String commentaire){ //revoie true si validation a fonctionne, false sinon
        if( consultation!=null && consultation.getEmploye()!=null && consultation.getMedium()!=null 
             && consultation.getClient()!=null){
            
                int duree = (int)(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-consultation.getDateDebut().getTime()));
                consultation.setDuree(duree);
                consultation.setCommentaire(commentaire);
                consultation.setEstTerminee(true);
                Employe employe=consultation.getEmploye();
                employe.addTempsTravail(duree);
                employe.setEstDisponible(true);
                
                JpaUtil.creerContextePersistance();
                try {
                    JpaUtil.ouvrirTransaction();
                    Client c=clientDao.modifier(consultation.getClient());
                    employeDao.modifier(consultation.getEmploye());
                    
                    consultation=consultationDao.chercherParId(consultation.getId());
                    
                    JpaUtil.validerTransaction();          
                } catch (Exception ex) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                    JpaUtil.annulerTransaction();
                    
                    consultation=null;
                }
                finally {
                    JpaUtil.fermerContextePersistance();
                }      
              
                
            }
         return consultation;
      }
      
      public Consultation obtenirConsultationParId(Long id){
          Consultation consultation=null;
          JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                consultation=consultationDao.chercherParId(id);
                JpaUtil.validerTransaction();
            } catch (Exception ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                JpaUtil.annulerTransaction();
                consultation = null;
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }      
         return consultation;
      }
      
      public List<Pair<Medium, Long>> ListeMediumConsultes() {
        List<Pair<Medium, Long>>  resultat = null;
       
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.CompterConsultationParMedium();
            List<Medium> listePourOuterJoin = mediumDao.listerMediums();
            
            if(resultat.size()!=listePourOuterJoin.size()) {
                for(Pair<Medium, Long> pair : resultat) {
                    listePourOuterJoin.remove(pair.getKey());
                }

                for(Medium m : listePourOuterJoin) {
                    resultat.add(new Pair(m,0));
                }
            }
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ListeMediumConsultes()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    public List<Pair<Employe, Long>> ListeNombreClientParEmploye() {
        List<Pair<Employe, Long>> resultat = null;
        
        JpaUtil.creerContextePersistance();
        try {
            
            resultat = consultationDao.CompterClientParEmploye();
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ListeMediumConsultes()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }
}
