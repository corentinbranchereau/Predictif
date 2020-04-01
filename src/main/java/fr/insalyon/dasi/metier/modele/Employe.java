/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Corentin
 */
@Entity
public class Employe extends Utilisateur implements Serializable {
    
    private boolean genre; //true: homme , false: femme
    private boolean enLigne;
    private Integer tempsTravail;
    
    @OneToMany(mappedBy="employe")
    private List<Consultation> consultations;

    protected Employe() {
    }

    public Employe(boolean genre, boolean enLigne, int tempsTravail, String nom, String prenom, String email, String mdp) {
        super(nom, prenom, email, mdp);
        this.genre = genre;
        this.enLigne = enLigne;
        this.tempsTravail = tempsTravail;
    }

    public boolean isGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public boolean isEnLigne() {
        return enLigne;
    }

    public void setEnLigne(boolean enLigne) {
        this.enLigne = enLigne;
    }

    public Integer getTempsTravail() {
        return tempsTravail;
    }

    public void setTempsTravail(int tempsTravail) {
        this.tempsTravail = tempsTravail;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
