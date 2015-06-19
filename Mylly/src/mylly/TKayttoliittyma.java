/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.Scanner;

/**
 * TKayttoliittyma -luokka määrittelee mylly -pelille tekstikäyttöliittymän, jolla peliä
 * voi pelata tai kahden tekoälyn peliä seurata.
 * @author Salla
 */
public class TKayttoliittyma implements Kayttoliittyma{
    Scanner lukija;
    StringBuilder pelilauta;
    String lautaesitys = "o---------o---------o\n" +
                         "|         |         |\n" +
                         "|  o------o------o  |\n" +
                         "|  |      |      |  |\n" +
                         "|  |  o---o---o  |  |\n" +
                         "|  |  |       |  |  |\n" +
                         "o--o--o       o--o--o\n" +
                         "|  |  |       |  |  |\n" +
                         "|  |  o---o---o  |  |\n" +
                         "|  |      |      |  |\n" +
                         "|  o------o------o  |\n" +
                         "|         |         |\n" +
                         "o---------o---------o\n";
    int edel; 
    
    /**
     * TKayttoliittyma -luokan konstruktori asettaa lukija muuttujan arvoksi parametrina
     * annetun Scanner -olion sekä pelilauta muuttujan arvoksi uuden StringBuilder -olion 
     * johon lisätään pelilautaa esittävä merkkijono.
     * @param lukija Scanner -luokan ilmentymä
     */
    public TKayttoliittyma(Scanner lukija){
        this.lukija = lukija;
        this.pelilauta = new StringBuilder();
        pelilauta.append(lautaesitys);
    }
    
    @Override
    public int pelinValinta(){
        System.out.println("Valitse peli: ");
        System.out.println("1 Tekoäly vs. tekoäly \n 2 Pelaaja vs. tekoäly \n3 Pelaaja vs. pelaaja");
        String vastaus = lukija.nextLine();
        int vast;
        try{
            vast = Integer.valueOf(vastaus);
            return vast;
        }catch(Exception e){
            return 1;
        }
    }
    
    /**
     * getLauta -metodi palauttaa pelilautaa kuvastavan merkkijonoesityksen
     * @return pelilautaa kuvastava String -olio
     */
    public String getLauta(){
        String merkit = pelilauta.toString();
        return merkit;
    }
    
    @Override
    public void ilmoitaVuoro(int vari){
        if(vari==1) System.out.println("Vuorossa musta");
        else if(vari==2) System.out.println("Vuorossa valkoinen");
    }
    
    @Override
    public void ilmoitaMylly(int vari){
        if(vari==1) System.out.println("Mustalla mylly, poista vastustajan pelimerkki");
        else if(vari==2)    System.out.println("Valkoisella mylly, poista vastustajan pelimerkki");
    }

    /**
     * tulostaLauta -metodi tulostaa merkkijonoesityksen pelin pelilaudasta ja sen 
     * sen hetkisestä tilasta, jossa näkyvät merkkien sijainnit.
     */
    @Override
    public void tulostaLauta(){
        String merkit = pelilauta.toString();
        System.out.println(merkit);
    }
    
    /**
     * paivitaLauta -metodi päivittää laudalla tehdyn siirron pelilautaa vastaavaan 
     * merkkijonoesitykseen, joka on StringBuilder muuttujassa pelilauta. Kirjain m
     * edustaa mustaa nappulaa ja v valkoista. Uusi siirto merkitään isolla kirjaimella
     * ja metodin alussa edellinen siirto muutetan pieneksi kirjaimeksi. Jos on kyseessä
     * poisto, sijainti pelilaudalla tyhjenee ja merkkijonoesityksessä se merkitään o 
     * -kirjaimella. 
     * @param siirto sijainti laudalla, johon siirto tehtiin (0-23)
     * @param vari minkä värinen nappula siirrettiin tai halutaan poistaa
     * @param poisto true jos on kyseessä poisto, false jos on kyseessä tavallinen siirto
     */
    @Override
    public void paivitaLauta(int siirto, int vari, boolean poisto){
        if(siirto>=0){
            char merkki = 'o';
            int ind = indeksi(siirto%8, siirto/8);
            pelilauta.setCharAt(edel, pelilauta.substring(edel, edel+1).toLowerCase().charAt(0)); 
            if(vari==1) merkki = 'M';
            if(vari==2) merkki = 'V';
            if(poisto)  merkki = 'O';        
            pelilauta.setCharAt(ind, merkki);
            edel = ind;
            System.out.println(siirto + ": rivi " + siirto/8 + " paikka " + siirto%8);
        }
    }

    /**
     * indeksi -metodi palauttaa parametrina annettua pelilaudan paikkaa vastaavan 
     * indeksin pelilautaa kuvaavasta merkkijonoesityksestä. 
     * @param paikka monesko paikka rivillä
     * @param rivi millä rivillä paikka on
     * @return merkkijonon indeksi, joka vastaa parametrina annettua paikkaa
     */
    public int indeksi(int paikka, int rivi){
        if(paikka<3)    return rivi*44 + paikka*10 + rivi*3 - rivi*paikka*3;
        if(paikka>3 && paikka<7)    return 286 - (rivi*44) - 2 - ((-4 + paikka)*10) - (rivi*3) + ((-4+paikka)*3*rivi);
        if(paikka==3)   return 132 + 20 - (rivi*3);
        else            return 132 + (rivi*3);
    }
    
    @Override
    public void kerroVoittaja(int vari){
        if(vari==1) System.out.println("Pelin voittaja: musta");
        if(vari==2) System.out.println("Pelin voittaja: valkoinen");
        if(vari==0) System.out.println("Peli päättyi tasapeliin");
    }
    
}
