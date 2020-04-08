/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Medium;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import jdk.internal.net.http.common.Pair;

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

       public List<Pair<Medium, Integer>> CompterConsultationParMedium() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT m, COUNT(c) FROM Consultation c JOIN c.medium m ORDER BY COUNT(c) DESC, m.denomination", Object[].class);
        List<Object[]> list = query.getResultList();
        List<Pair<Medium, Integer>> resultat = new ArrayList();
        for(Object[] o : list) {
            resultat.add(new Pair((Medium)o[0],(Integer)o[1]));
        }
        
        return resultat;
    }
       
       public Consultation chercherParId(Long consultationId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Consultation.class, consultationId); // renvoie null si l'identifiant n'existe pas
    }

}
