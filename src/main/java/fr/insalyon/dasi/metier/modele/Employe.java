/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

/**
 *
 * @author Corentin
 */
@Entity
public class Employe extends Utilisateur implements Serializable {
    @Enumerated(EnumType.STRING)
    private Genre genre; //Masculin, Feminin, Autre
    private boolean estDisponible;
    private Integer tempsTravail;
    
    @OneToMany(mappedBy="employe",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Consultation> consultations;

    protected Employe() {
    }

    public Employe(Genre genre, int tempsTravail, String nom, String prenom, String email, String mdp) {
        super(nom, prenom, email, mdp);
        this.genre = genre;
        this.estDisponible = true;
        this.tempsTravail = tempsTravail;
        this.consultations = new ArrayList();
    }
    
    public void addConsultation(Consultation consultation)
    {
        this.consultations.add(consultation);
        if(consultation.getEmploye() != this)
        {
            consultation.setEmploye(this);
        }
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public Integer getTempsTravail() {
        return tempsTravail;
    }

    public void setTempsTravail(int tempsTravail) {
        this.tempsTravail = tempsTravail;
    }
    
    public void addTempsTravail(int tempsTravail) {
        this.tempsTravail += tempsTravail;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
    
    @Override
    public String toString() {
        return super.toString() + "type=Employe, genre=" + genre + ", enLigne=" + estDisponible + "tempsTravail=" + tempsTravail;
    }
}
