/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * ListaSolmu -luokka määrittelee listan alkion, joka voidaan lisätä Lista -luokan
 * määrittelemään listaan
 * @author Salla
 */
public class ListaSolmu {
    ListaSolmu seur;
    Solmu puusolmu;
    int avain;
    
    /**
     * ListaSolmu -luokan konstruktori asettaa listasolmun muuttujaksi parametrina
     * annetun Solmu -luokan ilmentymän ja asettaa sen seuraajan nulliksi.
     * @param solmu Solmu -luokan ilmentymä, joka halutaan lisätä listaan
     */
    public ListaSolmu(Solmu solmu){
        this.seur = null;               //seuraajaa ei ole vielä määritelty
        this.puusolmu = solmu;
        this.avain = solmu.getAvain();
    }
    
    /**
     * ListaSolmu -luokan konstruktori asettaa listasolmun muuttujaksi parametrina
     * annetun Solmu -luokan ilmentymän ja asettaa sen seuraajaksi parametrina annetun
     * ListaSolmu -olion
     * @param solmu Solmu -luokan ilmentymä, joka halutaan lisätä listaan
     * @param seuraava  solmua seuraava ListaSolmu -olio listassa
     */
    public ListaSolmu(Solmu solmu, ListaSolmu seuraava){
        this.seur = seuraava;       //seuraava listan alkio
        this.puusolmu = solmu;      //tämän alkion sisältö
        this.avain = solmu.getAvain();
    }
    
    /**
     * ListaSolmu -luokan konstruktori asettaa solmun avaimeksi parametrina annetun
     * kokonaisluvun. Puusolmuksi ja seuraavaksi listasolmuksi asetetaan null.
     * @param avain kokonaisluku joka asetetaan solmun avaimeksi
     */
    public ListaSolmu(int avain){
        this.avain = avain;
        this.puusolmu = null;
        this.seur = null;
    }
    
    /**
     * ListaSolmu -luokan konstruktori asettaa solmun avaimeksi parametrina annetun
     * kokonaisluvun sekä listasolmun seuraajaksi listassa parametrina annetun 
     * ListaSolmu -olion. Listasolmun puusolmu saa arvon null.
     * @param avain kokonaisluku, joka asetetaan listasolmun avaimeksi
     * @param seuraava ListaSolmu -luokan ilmentymä, josta tehdään tämän solmun seuraaja
     */
    public ListaSolmu(int avain, ListaSolmu seuraava){
        this.avain = avain;
        this.puusolmu = null;
        this.seur = seuraava;
    }
    
    /**
     * setSeuraava -metodi asettaa tämän listasolmun seuraajaksi parametrina 
     * annetun ListaSolmu -luokan ilmentymän
     * @param seuraava listasolmun uusi seuraaja listassa
     */
    public void setSeuraava(ListaSolmu seuraava){
        this.seur = seuraava;                       //linkki seuraavaan listasolmuun
    }
    
    /**
     * getSeuraava -metodi palauttaa listassa tätä listasolmua seuraavan alkion, 
     * eli alkion, joka on yleensä lisätty juuri ennen tätä alkiota 
     * @return tätä listasolmua seuraava ListaSolmu -luokan ilmentymä listassa
     */
    public ListaSolmu getSeuraava(){
        return this.seur;
    }
    
    /**
     * getPuuSolmu -metodi palauttaa tähän listasolmuun talletetun Solmu -luokan ilmentymän
     * @return tähän listasolmuun talletettu Solmu -luokan ilmentymä
     */
    public Solmu getPuuSolmu(){
        return this.puusolmu;
    }
    
    /**
     * getArvo -metodi palauttaa tähän listasolmuun talletetun Solmu -luokan ilmentymän
     * arvon. Jos puusolmua ei ole, palautetaan arvo 0.
     * @return tähän listasolmuun talletetun Solmu -olion sen hetkinen arvo
     */
    public int getArvo(){
        if(puusolmu==null)  return 0;
        return this.puusolmu.getArvo();
    }
    
    /**
     * getAvain -metodi palauttaa listasolmun avaimen
     * @return
     */
    public int getAvain(){
        return this.avain;
    }
}
