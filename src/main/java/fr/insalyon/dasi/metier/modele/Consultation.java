/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Consultation() {
    }

    public Consultation(Date dateDebut, Integer duree, String commentaire) {
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.commentaire = commentaire;
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

    @Override
    public String toString() {
        return "Consultation{" + "dateDebut=" + dateDebut + ", duree=" + duree + ", commentaire=" + commentaire + '}';
    }
    
}
