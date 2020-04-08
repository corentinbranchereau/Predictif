/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Utilisateur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Corentin
 */
public class UtilisateurDao {
    
     public void creer(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(utilisateur);
    }
    
    public Utilisateur modifier(Utilisateur utilisateurModifie) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Utilisateur resultat = em.merge(utilisateurModifie);
        return resultat;
    }
    
    public Utilisateur chercherParMail(String utilisateurMail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Utilisateur> query = em.createQuery("SELECT e FROM Utilisateur e WHERE e.email = :mail", Utilisateur.class);
        query.setParameter("mail", utilisateurMail); // correspond au paramètre ":mail" dans la requête
        List<Utilisateur> utilisateurs = query.getResultList();
        Utilisateur result = null;
        if (!utilisateurs.isEmpty()) {
            result = utilisateurs.get(0); // premier de la liste
        }
        return result;
    }
    
    public Utilisateur chercherParId(Long utilisateurId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Utilisateur.class, utilisateurId); // renvoie null si l'identifiant n'existe pas
    }
    
}
