/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;


/**
 *
 * @author Corentin
 */
@Entity
public class Consultation implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut;
    private Integer duree; //minutes
    private String commentaire;
    private Boolean estTerminee;
    
    @ManyToOne
    private Medium medium;
    
    
    @ManyToOne
    private Employe employe;
    
    
    @ManyToOne
    private Client client;
    
    
    public Consultation() {
        this.dateDebut = null;
        this.duree = null;
        this.commentaire = null;
        this.medium = null;
        this.employe = null;
        this.client=null;
        this.estTerminee=false;
    }
    
      public Long getId() {
        return id;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
    
    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
        if(!employe.getConsultations().contains(this))
        {
            employe.addConsultation(this);
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if(!client.getConsultations().contains(this))
        {
            client.addConsultation(this);
        }
    }

    public Boolean getEstTerminee() {
        return estTerminee;
    }

    public void setEstTerminee(Boolean estTerminee) {
        this.estTerminee = estTerminee;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", dateDebut=" + dateDebut + ", duree=" + duree + ", commentaire=" + commentaire + ", estTerminée" + estTerminee + ", medium=" + medium.getId() + ", employe=" + employe.getId() + ", client=" + client.getId() + '}';
    }
   
    
    
}
