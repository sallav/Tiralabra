/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import ui.TKayttoliittyma;
import ui.Kayttoliittyma;
import heuristiikat.Perus;
import heuristiikat.Heuristiikka;
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
        Peli peli = pelinLuonti(kl);
        int kukavoitti = peli.pelaa();
        kl.kerroVoittaja(kukavoitti);
    }
    
    /**
     * pelinLuonti -metodi kysyy käyttäjältä halutun pelityypin ja palauttaa sen
     * mukaan luodun Peli -olion (Tekoäly vs tekoäly, pelaaja vs. tekoäly, pelaaja vs. pelaaja)
     * @param kal Kayttoliittyma rajapinnan toteuttavan luokan ilmentymä
     * @return uusi Peli -olio
     */
    public static Peli pelinLuonti(Kayttoliittyma kal){
        int pelityyppi = kal.pelinValinta();
        Peli peli = uusiPeli(pelityyppi, kal);
        return peli;
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
        boolean vsKayttaja = false;
        int syvyys = 5;
        switch(pelityyppi){
            case 3: return pelaajaVsPelaaja(kal); 
            case 2: vsKayttaja = true;                        
            case 1: syvyys = kal.valitseTaso(); 
            default: return vsTekoaly(kal, syvyys, vsKayttaja); 
        }
    }
    
    /**
     * tekoalyVsTekoaly -metodi luo uuden pelin, jossa ovat pelaajina vastakkain kaksi 
     * tekoälyä jos parametri vsKayttaja on false tai käyttäjä ja tekoäly jos vsKayttaja 
     * on true. Pelin käyttöliittymäksi asetetaan parametrina annettu Kayttoliittyma -olio 
     * ja syvyydeksi parametrina annettu syvyys.
     * @param kal käyttöliittymä luotavaan peliin
     * @param syvyys kuinka syvälle pelipuuta halutaan generoida 
     * @param vsKayttaja true jos kayttaja vs. tekoäly, false jos tekoäly vs. tekoäly
     * @return uusi Peli -olio valmiina pelattavaksi
     */
    public static Peli vsTekoaly(Kayttoliittyma kal, int syvyys, boolean vsKayttaja){
        if(vsKayttaja)  return pelaajaVsTekoaly(kal, syvyys);
        Heuristiikka h = new Perus();              //heuristiikan valinta
        Tekoaly taly = new Tekoaly(h, syvyys);    //uusi tekoäly -olio halutulla heuristiikalla ja syvyytenä 5
        AIPelaaja eka = new AIPelaaja(1, taly);   //pelaajat käyttävät samaa tekoälyä
        AIPelaaja toka = new AIPelaaja(2, taly);  //joten periaatteessa sama tekoäly pelaa itseään vastaan
        return new Peli(eka, toka, kal);
    }
    
    /**
     * tekoalyVsPelaaja luo Peli -olion, jossa pelaavat vastakkain käyttäjä ja tekoäly.
     * @param kal Kayttoliittyma -rajapinnan toteuttavan luokan ilmentymä, joka liitetään peliin
     * @param syvyys kuinka syvälle pelipuuta halutaan generoida
     * @return uusi Peli -olio
     */
    public static Peli pelaajaVsTekoaly(Kayttoliittyma kal, int syvyys){
        System.out.println("Ei saatavilla!");
        return pelinLuonti(kal);
    }
    
    /**
     * pelaajaVsPelaaja -metodi luo Peli -olion, jossa pelaavat vastakkain kaksi käyttäjää.
     * @param kal Kayttoliittyma -rajapinnan toteuttavan luokan ilmentymä, joka liitetään peliin
     * @return uusi Peli -olio
     */
    public static Peli pelaajaVsPelaaja(Kayttoliittyma kal){
        System.out.println("Ei saatavilla!");
        return pelinLuonti(kal);
    }
}
