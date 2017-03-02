/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

import tietorakenteet.Lista;

/**
 * Puu rajapinta määrittelee metodit puuhun, johon voidaan tallettaa Solmu -olioita
 * @author Salla
 */
public interface Puu {
    
    /**
     * getJuuri palauttaa puun juurisolmun
     * @return Solmu -luokan ilmentymä, joka on puun juuressa
     */
    public abstract Solmu getJuuri();
    
    /**
     * lisaa -metodi lisää puuhun uuden solmun, jonka avaimeksi tulee parametrina
     * annettu avain
     * @param avain kokonaisluku, joka tulee lisättävän Solmu-luokan ilmentymän avaimeksi
     * @return true, jos solmu lisättiin puuhun, false, jos lisäys ei onnistunut
     */
    public abstract boolean lisaa(int avain);
    
    /**
     * lisaa -metodi lisää puuhun parametrina annetun Solmu -luokan ilmentymän
     * @param solmu Solmu -olio, joka lisätään puuhun
     * @return true, jos solmu lisättiin puuhun, false, jos lisäys ei onnistunut
     */
    public abstract boolean lisaa(Solmu solmu);
    
    /**
     * poista -metodi poistaa parametrina annetun avaimen sisältävän solmun puusta.
     * @param avain kokonaisluku, joka on avaimena solmussa, joka halutaan poistaa
     * @return poistettu Solmu -luokan ilmentymä
     */
    public abstract Solmu poista(int avain);
    
    /**
     * etsi -metodi etsii ja palauttaa Solmu -luokan ilmentymän, jolla on avaimenaan 
     * parametrina annettu kokonaisluku
     * @param avain kokonaisluku, joka on etsittävän solmun avain
     * @return etsitty Solmu -luokan ilmentymä
     */
    public abstract Solmu etsi(int avain);
    
    /**
     * getKoko -metodi palauttaa puun koon
     * @return kuinka monta solmua puussa on
     */
    public abstract int getKoko();
    
    /**
     * teeKopio -metodi palauttaa toisen Puu -luokan ilmentymän, joka sisältää yhtä
     * monta solmua samoilla avaimilla kuin tämä puu
     * @return Puu, jossa on solmut samoilla avaimilla kuin tässä puussa
     */
    public abstract Puu teeKopio(); 
    
    /**
     * esijarjestys -metodi palauttaa listan, joka sisältää puun solmut käänteisessä
     * esijärjestyksessä
     * @return Lista -luokan ilmentymä, jossa käänteisessä järjestyksessä puussa olevat
     * Solmu -luokan ilmentymät sisältävät listasolmut
     */
    public abstract Lista esijarjestys();
}
