/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;


import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.ConsultationDao;
import fr.insalyon.dasi.dao.EmployeDao;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.dao.MediumDao;
import fr.insalyon.dasi.metier.modele.Client;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corentin
 */
public class ServiceConsultation {
    
    ClientDao clientDao=new ClientDao();
    MediumDao mediumDao=new MediumDao();
    EmployeDao employeDao=new EmployeDao();
    ConsultationDao consultationDao=new ConsultationDao();
            
            
    public Long ajouterConsultation(Consultation consultation){ //permet d'ajouter une consultation dans la bd
        
         
        Long resultat = null;
        if(consultation!=null){
            if(consultation.getClient() !=null && consultation.getEmploye() !=null && consultation.getMedium() !=null){      
                JpaUtil.creerContextePersistance();
                try {
                    JpaUtil.ouvrirTransaction();
                    
                   // consultationDao.creer(consultation);
                    Client c=clientDao.modifier(consultation.getClient());
                    employeDao.modifier(consultation.getEmploye());
                    /*Employe e=employeDao.chercherParId(consultation.getEmploye().getId());
                    e.setEstDisponible(false);
                    employeDao.modifier(e);*/
                    
                    
                    JpaUtil.validerTransaction();
                    List<Consultation> list=c.getConsultations();
                    resultat = list.get(list.size()-1).getId();
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
             
               Long id=ajouterConsultation(consultation);
                if(id==null){
                 consultation=null; 
                }
            }else{
                System.out.println("Pas d'employé de disponible pour la consultation");
            }
           
         return consultation;    
      }
      
      public Consultation validerConsultation(Consultation consultation,Date dateDebut,Integer duree, String commentaire){ //revoie true si validation a fonctionne, false sinon
          
         
          if(   consultation.getEmploye()!=null && consultation.getMedium()!=null 
             && consultation.getClient()!=null && dateDebut!=null && duree!=null){
              
                consultation.setDateDebut(dateDebut);
                consultation.setDuree(duree);
                consultation.setCommentaire(commentaire);
                consultation.setEstTerminee(true);
                Employe employe=consultation.getEmploye();
                employe.addTempsTravail(duree);
                employe.setEstDisponible(true);
                
                JpaUtil.creerContextePersistance();
                try {
                    JpaUtil.ouvrirTransaction();
                    consultationDao.modifier(consultation);   
                    clientDao.modifier(consultation.getClient());
                    employeDao.modifier(consultation.getEmploye());
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
}
