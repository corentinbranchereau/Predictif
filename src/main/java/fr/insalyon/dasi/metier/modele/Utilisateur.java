package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Corentin
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Utilisateur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;
    protected String prenom;
    @Column(unique = true)
    protected String email;
    protected String mdp;
    private String telephone;
    protected boolean estConnecte;
    @Enumerated(EnumType.STRING)
    private Genre genre; //Masculin, Feminin, Autre

    protected Utilisateur() {
    }


     public Utilisateur(String nom, String prenom, String email, String mdp, Genre genre, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.estConnecte=false;
        this.genre=genre;
        this.telephone=telephone;
    }
    
    public Long getId() {
        return id;
    }
    
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isEstConnecte() {
        return estConnecte;
    }

    public void setEstConnecte(boolean estConnecte) {
        this.estConnecte = estConnecte;
    }
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Utilisateur : id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", email=" + email + ", telephone=" + telephone + ", mdp=" + mdp;
    }

}
