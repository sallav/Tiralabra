/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
     * valitseTaso -metodissa käyttäjä voi valita pelin tason ts. kuinka syvälle pelipuuta
     * pelissä generoidaan (taso 1=syvyys 3, taso 2=syvyys 4, taso 3=syvyys 5)
     * @return pelipuun generointisyvyyttä vastaava kokonaisluku
     */
    public abstract int valitseTaso();
    
    /**
     * ilmotaVuoro -metodi ilmoittaa käyttäjälle kumpi pelaajista on vuorossa
     * @param pelaajanvari vuorossa olevan pelaajan väriä vastaava kokonaisluku
     */
    public abstract void ilmoitaVuoro(int pelaajanvari);
    
    /**
     * ilmoitaMylly -metodi ilmoittaa pelaajalle laudalle muodostuneesta myllystä
     * @param pelaajanvari myllyn saaneen pelaajan väriä vastaava kokonaisluku
     */
    public abstract void ilmoitaMylly(int pelaajanvari);
    
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
