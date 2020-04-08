/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author thibautgravey
 */
@Entity
public class Spirite extends Medium implements Serializable {
    
    private String support;
    
    protected Spirite() {
    }
    
    public Spirite(String denomination, Genre genre, String presentation, String support) {
        super(denomination,genre,presentation);
        this.support=support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
    
    @Override
    public String toString() {
        return super.toString()+", type=Spirite, support=" + support;
    }
}
