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
import javax.persistence.OneToMany;

/**
 *
 * @author Corentin
 */
@Entity
public class Employe extends Utilisateur implements Serializable {
    
    private boolean genre; //true: homme , false: femme
    private boolean enConsultation;
    private Integer tempsTravail;
    
    @OneToMany(mappedBy="employe",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Consultation> consultations;

    protected Employe() {
    }

    public Employe(boolean genre, boolean enConsultation, int tempsTravail, String nom, String prenom, String email, String mdp) {
        super(nom, prenom, email, mdp);
        this.genre = genre;
        this.enConsultation = enConsultation;
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

    public boolean isGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public boolean isEnConsultation() {
        return enConsultation;
    }

    public void setEnConsultation(boolean enConsultation) {
        this.enConsultation = enConsultation;
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
    
    @Override
    public String toString() {
        return super.toString() + "type=Employe, genre=" + genre + ", enLigne=" + enConsultation + "tempsTravail=" + tempsTravail;
    }
}
