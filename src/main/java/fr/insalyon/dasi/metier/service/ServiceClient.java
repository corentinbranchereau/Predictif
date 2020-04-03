/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.ProfilAstral;

import java.io.IOException;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corentin
 */
public class ServiceClient {
    
    public Long inscrireClient(Client client) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            ClientDao.creer(client);
            JpaUtil.validerTransaction();
            resultat = client.getId();
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
    
    public Client authentifierClient(String email, String mdp) {
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'utilsateur
            Client client = ClientDao.chercherParEmail(email);
            if (client != null) {
                // Vérification du mot de passe
                if (client.getMdp().equals(mdp)) {
                    client.setEstConnecte(true);
                    JpaUtil.ouvrirTransaction();
                    resultat=ClientDao.modifierClient(client);
                    JpaUtil.validerTransaction();
                    
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierClient(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Client deconnecterClient(Client client){
        JpaUtil.creerContextePersistance();
        client.setEstConnecte(false);
        Client resultat=null;
        try {
           JpaUtil.ouvrirTransaction();
           resultat=ClientDao.modifierClient(client);
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
    
    public ProfilAstral genererProfilAstral(Client client) throws IOException{
         JpaUtil.creerContextePersistance();
         AstroTest astroApi = new AstroTest();
         ProfilAstral resultat = null;

        
        List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateNaissance());
        ProfilAstral profilClient=new ProfilAstral(profil.get(0),profil.get(1),profil.get(2),profil.get(3));
 
        
        try {
            JpaUtil.ouvrirTransaction();
            ClientDao.sauvegarderProfilAstral(profilClient);
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
               client=ClientDao.modifierClient(client);
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
    
}
