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
     * @return pelin tyyppiä vastaava kokonaisluku (1=tekoäly vs. tekoäly, 2=pelaaja vs.
     * tekoäly, 3=pelaaja vs. pelaaja)
     */
    public abstract int pelinValinta();
    
    /**
     * paivitaLauta -metodi päivittää laudalle parametrina annetun siirron
     * @param siirto sijainti johon siirto tehtiin
     * @param vari minkä värinen nappula siirrettiin
     * @param poisto true, jos kyseessä on nappulan poisto, false jos kyseessä
     * on tavallinen siirto
     */
    public abstract void paivitaLauta(int siirto, int vari, boolean poisto);
    
    /**
     * tulostaLauta -metodi tulostaa pelilaudan sen hetkisen tilan käyttäjälle 
     * nähtäväksi
     */
    public abstract void tulostaLauta();
    
    /**
     * kerroVoittaja -metodi ilmoittaa käyttäjälle voittajan värin tai tasapelin
     * sattuessa kertoo pelin päättyneen tasan.
     * @param voittajanvari voittajan väriä vastaava kokonaisluku tai tasapelin sattuessa 0
     */
    public abstract void kerroVoittaja(int voittajanvari);
}
