package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author thibautgravey
 */
@Entity
public class Cartomancien extends Medium implements Serializable{
    
    protected Cartomancien() {
        
    }
    
    public Cartomancien(String denomination, Genre genre, String presentation) {
        super(denomination,genre,presentation);
    }
    
    @Override
    public String toString() {
        return super.toString()+", type=Cartomancien";
    } 
}