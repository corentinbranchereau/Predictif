/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ConsultationDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.dao.MediumDao;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author thibautgravey
 */
public class ServiceStatistiques {
    
    ConsultationDao consultationDao = new ConsultationDao();
    MediumDao mediumDao = new MediumDao();
    
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
