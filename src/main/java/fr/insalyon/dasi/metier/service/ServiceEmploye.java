/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.EmployeDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.ProfilAstral;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corentin
 */
public class ServiceEmploye {
    
   protected EmployeDao employeDao = new EmployeDao();
    
    public Long inscrireEmploye(Employe employe) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            employeDao.creer(employe);
            JpaUtil.validerTransaction();
            resultat = employe.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe authentifierEmploye(String mail, String motDePasse) {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'employe
            Employe employe = employeDao.chercherParMail(mail);
            if (employe != null) {
                // Vérification du mot de passe
                if (employe.getMdp().equals(motDePasse)) {
                    employe.setEstConnecte(true);
                    JpaUtil.ouvrirTransaction();
                    resultat=employeDao.modifier(employe);
                    JpaUtil.validerTransaction();
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierEmploye(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe deconnecterEmploye(Employe employe){
        JpaUtil.creerContextePersistance();
        employe.setEstConnecte(false);
        Employe resultat=null;
        try {
           JpaUtil.ouvrirTransaction();
           resultat=employeDao.modifier(employe);
           JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service déconnecterClient(client)", ex);
            JpaUtil.annulerTransaction();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        } 
        
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
    
}
