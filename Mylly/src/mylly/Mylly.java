/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.Scanner;

/**
 * Mylly -peli
 * @author Salla 
 */
public class Mylly {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Kayttoliittyma kl = new TKayttoliittyma(new Scanner(System.in));
        int pelityyppi = kl.pelinValinta();
        Peli peli = uusiPeli(pelityyppi, kl);
        int kukavoitti = peli.pelaa();
        kl.kerroVoittaja(kukavoitti);
    }
        
    /**
     * uusiPeli -metodi luo ja palauttaa uuden pelin, joka on parametrina annettua 
     * tyyppiä(tekoäly vs. tekoäly, pelaaja vs. tekoäly tai pelaaja vs. pelaaja) ja
     * asettaa pelin käyttöliittymäksi parametrina annetun käyttöliittymä -olion.
     * @param pelityyppi 1=tekoäly vs. tekoäly, 2=pelaaja vs. tekoäly, 3=pelaaja vs. pelaaja
     * @param kal käyttöliittymä pelille
     * @return uusi peli, joka on valmis pelattavaksi
     */
    public static Peli uusiPeli(int pelityyppi, Kayttoliittyma kal){
        switch(pelityyppi){
            case 1: return tekoalyVsTekoaly(kal);
            case 2:
            case 3:
            default: return tekoalyVsTekoaly(kal);
        }
    }
    
    /**
     * tekoalyVsTekoaly -metodi luo uuden pelin, jossa ovat pelaajina vastakkain kaksi 
     * tekoälyä. Pelin käyttöliittymäksi asetetaan parametrina annettu käyttöliittymä
     * -olio.
     * @param kal käyttöliittymä luotavaan peliin
     * @return uusi peli, jossa pelaavat tekoälyt vastakkain
     */
    public static Peli tekoalyVsTekoaly(Kayttoliittyma kal){
        Heuristiikka h = new Perus();           //heuristiikan valinta
        Tekoaly taly = new Tekoaly(h, 5);       //uusi tekoäly -olio halutulla heuristiikalla ja syvyytenä 5
        AIPelaaja eka = new AIPelaaja(1, taly);   //pelaajat käyttävät samaa tekoälyä
        AIPelaaja toka = new AIPelaaja(2, taly);  //joten periaatteessa sama tekoäly pelaa itseään vastaan
        return new Peli(eka, toka, kal);
    }
}
