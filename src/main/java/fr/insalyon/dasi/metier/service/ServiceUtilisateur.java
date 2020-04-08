/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.UtilisateurDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corentin
 */
public class ServiceUtilisateur {
    
    protected UtilisateurDao utilisateurDao = new UtilisateurDao();
    
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
    
    public Utilisateur deconnecterUtilisateur(Utilisateur utilisateur){
        JpaUtil.creerContextePersistance();
        utilisateur.setEstConnecte(false);
        Utilisateur resultat=null;
        try {
           JpaUtil.ouvrirTransaction();
           resultat=utilisateurDao.modifier(utilisateur);
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
    
   
}
