/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Corentin
 */
public class ConsultationDao {
    
    public void creer(Consultation consultation){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(consultation);
    }
   
    public Consultation modifier(Consultation consultationModifiee) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Consultation resultat = em.merge(consultationModifiee);
        return resultat;
    }
    
    public Consultation chercherParId(Long consultationId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Consultation.class, consultationId); // renvoie null si l'identifiant n'existe pas
    }

    public List<Pair<Medium, Long>> CompterConsultationParMedium() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT m, COUNT(c) FROM Consultation c JOIN c.medium m WHERE c.estTerminée = TRUE GROUP BY(m) ORDER BY COUNT(c) DESC, m.denomination", Object[].class);
        List<Object[]> list = query.getResultList();
        List<Pair<Medium, Long>> resultat = new ArrayList();
        for(Object[] o : list) {
            resultat.add(new Pair((Medium)o[0],(Long)o[1]));
        }
        
        return resultat;
    }
       
    public List<Pair<Employe, Long>> CompterClientParEmploye() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT e, COUNT(DISTINCT cl) FROM Employe e LEFT JOIN e.consultations c LEFT JOIN c.client cl GROUP BY(e) ORDER BY COUNT(DISTINCT cl) DESC, e.nom, e.prenom", Object[].class);
        List<Object[]> list = query.getResultList();
        List<Pair<Employe, Long>> resultat = new ArrayList();
        for(Object[] o : list) {
            resultat.add(new Pair((Employe)o[0],(Long)o[1]));
        }
        
        return resultat;
    }

}
