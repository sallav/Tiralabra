/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 *
 * @author Käyttäjä
 */
public interface Puu {
    
    public abstract Solmu getJuuri();
    
    public abstract boolean lisaa(int avain);
    
    public abstract boolean lisaa(Solmu solmu);
    
    public abstract Solmu poista(int avain);
    
    public abstract Solmu etsi(int avain);
    
    public abstract int getKoko();
    
    public abstract Puu teeKopio(); 
}
