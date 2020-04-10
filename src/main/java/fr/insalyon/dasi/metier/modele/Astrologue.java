package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author thibautgravey
 */
@Entity
public class Astrologue extends Medium implements Serializable {
    
    private String formation;
    private Integer promotion;
    
    protected Astrologue() {
    }
    
    public Astrologue(String denomination, Genre genre, String presentation, String formation, Integer promotion) {
        super(denomination,genre,presentation);
        this.formation=formation;
        this.promotion=promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }
    
    @Override
    public String toString() {
        return super.toString()+", type=Astrologue, formation=" + formation + ", promotion="+promotion;
    }
    
}
