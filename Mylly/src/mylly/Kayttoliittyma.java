/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Käyttöliittymä rajapinta mylly -peliin
 * @author Salla
 */
public interface Kayttoliittyma {
    
    /**
     * pelinValinta -metodissa käyttäjä voi valita minkä tyyppinen peli pelataan
     * @return pelin tyyppiä vastaava kokonaisluku 
     */
    public abstract int pelinValinta();
    
    /**
     * kerroVoittaja -metodi ilmoittaa käyttäjälle voittajan värin tai tasapelin
     * sattuessa kertoo pelin loppuneen tasan.
     * @param voittajanvari voittajan väriä vastaava kokonaisluku tai tasapelin sattuessa 0
     */
    public abstract void kerroVoittaja(int voittajanvari);
}
