
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author DASI Team
 */
public class ClientDao{
    
    public static void creer(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(client);
    }
    
    public static void sauvegarderProfilAstral(ProfilAstral profil){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(profil);
    }
    
    public static Client chercherParEmail(String clientEmail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.email = :mail", Client.class);
        query.setParameter("mail", clientEmail); // correspond au paramètre ":mail" dans la requête
        List<Client> clients = query.getResultList();
        Client result = null;
        if (!clients.isEmpty()) {
            result = clients.get(0); // premier de la liste
        }
        return result;
    }
    
    public static Client modifierClient(Client clientModifie){
        
         EntityManager em = JpaUtil.obtenirContextePersistance();
         Client resultat=em.merge(clientModifie);
         return resultat;
    }
    /*
    public Client chercherParId(Long clientId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Client.class, clientId); // renvoie null si l'identifiant n'existe pas
    }
    
    
    
    public List<Client> listerClients() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c ORDER BY c.nom ASC, c.prenom ASC", Client.class);
        return query.getResultList();
    }
    */
    // modifier / supprimer  ... 
}
