package fr.insalyon.dasi.metier.modele;

/**
 *
 * @author thibautgravey
 */
public enum Genre {
    Masculin("Mr"),
    Feminin("Mme"), 
    Autre("");
    
    private String abreviation = "";
    
    //Constructeur
    Genre(String abreviation){
        this.abreviation=abreviation;
    }
    
    public String getAbreviation() {
        return this.abreviation;
    }
}
