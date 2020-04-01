/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Corentin
 */
@Entity
public class ProfilAstral implements Serializable {
    
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String signeZodiaque;
    private String signeAstroChinois;
    private String couleur;
    private String animalTotem;

    public ProfilAstral() {
    }

    public ProfilAstral(String signeZodiaque, String signeAstroChinois, String couleur, String animalTotem) {
        this.signeZodiaque = signeZodiaque;
        this.signeAstroChinois = signeAstroChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }

    public Long getId() {
        return id;
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public String getSigneAstroChinois() {
        return signeAstroChinois;
    }

    public void setSigneAstroChinois(String signeAstroChinois) {
        this.signeAstroChinois = signeAstroChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }

    public void setAnimalTotem(String animalTotem) {
        this.animalTotem = animalTotem;
    }
    
}
