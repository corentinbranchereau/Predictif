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
                                                                    //les liste consultations de client et employé sont maj automatiquement par l'entity manager     
        Long resultat = null;
        
        if(consultation.getClient() !=null && consultation.getEmploye() !=null && consultation.getMedium() !=null){      
            JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                consultationDao.creer(consultation);
                clientDao.modifier(consultation.getClient());
                employeDao.modifier(consultation.getEmploye());
                JpaUtil.validerTransaction();
                resultat = consultation.getId();
            } catch (Exception ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service ajouterConsultation()", ex);
                JpaUtil.annulerTransaction();
                resultat = null;
            }
            finally {
                JpaUtil.fermerContextePersistance();
            }      
        }
        
        return resultat;
    }
}
