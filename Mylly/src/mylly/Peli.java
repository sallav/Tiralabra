/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Peli -luokka luo uuden pelin, jossa on kaksi pelaajaa sekä pelilauta. 
 * Pelaajat tekevät vuorollaan siirtonsa laudalle ja lopulta selvitetään voittaja.
 * @author Salla
 */
public class Peli {
    Pelaaja eka;
    Pelaaja toka;
    Lauta lauta;
    
    Peli(Pelaaja eka, Pelaaja toka){
        this.eka = eka;
        this.toka = toka;
        this.lauta = new Lauta();
    }
    
    /** 
     * pelaa -metodi aloittaa pelin. Pelaajat asettavat vuorotellen 9 nappia laudalle
     * ja sen jälkeen selvitetään pelin voittaja.
     * @return pelin voittaja: musta, valkoinen tai tasapeli
     */
    public String pelaa(){
        int nappeja = 9;
        
        while(nappeja>0){
            pelaaLaudalle(eka);
            pelaaLaudalle(toka);
            nappeja--;
        }
        int tulos = selvitaVoittaja();
        return voittaja(tulos);
    }
        
    /**
     * selvitaVoittaja -metodia kutsutaan, kun molemmat pelaajat ovat asettaneet kaikki merkkinsä 
     * pelilaudalle. Pelaajat tekevät vuorotellen siirtonsa laudalla, niin kauan kunnes jompi kumpi voittaa
     * tai peli päättyy tasapeliin.
     * @return 1 jos ensimmäinen pelaaja voitti, 2 jos toinen pelaaja voitti, 0 jos peli päättyi tasapeliin
     */
    public int selvitaVoittaja(){
        
        while(lauta.voikoLiikkua(eka.vari()) && lauta.voikoLiikkua(toka.vari())){
            if(!lauta.voikoLiikkua(eka.vari()) || eka.laudalla(lauta)<3)     return 2;  //jos ei voi liikuttaa nappeja tai toisella vain kaksi peli loppuu
                pelaaLaudalla(eka);
            if(!lauta.voikoLiikkua(toka.vari()) || toka.laudalla(lauta)<3)    return 1;  //jos ei voi liikuttaa nappeja tai toisella vain kaksi peli loppuu
                pelaaLaudalla(toka);
        }
        
        if(eka.laudalla(lauta)>toka.laudalla(lauta)) return 1;  //eka voitti
        if(eka.laudalla(lauta)<toka.laudalla(lauta))  return 2; //toka voitti
        else return 0;                                          //tasapeli
    }
    
    /**
     * pelaaLaudalle -metodissa parametrina annettu pelaaja tekee siirtonsa laudalle, jonka jälkeen tarkistetaan 
     * onko syntynyt mylly. Jos pelaaja saa myllyn, tulee hänen poistaa yksi vastapuolen napeista.
     * @param pelaaja
     */
    public void pelaaLaudalle(Pelaaja pelaaja){
        int sij = pelaaja.siirraLaudalle(lauta);
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta);
    }
    
    /**
     * pelaaLaudalla -metodia käytetään silloin kun kaikki pelaajan napit ovat jo laudalla. Tällöin pelaaja voi
     * siirtää jotain laudalla jo olevaa nappia vieressä olevaan tyhjään sijaintiin. Jos pelaajalla on kuitenkin 
     * jäljellä vain kolme nappia laudalla, voi uusi sijainti olla mikä tahansa laudalla oleva tyhjä sijainti, 
     * eli pelaaja saa "lentää". Siirron jälkeen tarkistetaan syntyikö kyseiseen kohtaan mylly ja jos näin on 
     * tapahtunut, tulee pelaajan poistaa yksi vastapuolen napeista.
     * @param pelaaja
     */
    public void pelaaLaudalla(Pelaaja pelaaja){
        int sij;
        if(pelaaja.laudalla(lauta)>3)   sij = pelaaja.siirraLaudalla(lauta);
        else sij = pelaaja.lenna(lauta);
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta);
    }
    
    /**
     * voittaja -metodi selvittää voittajan värin tai vaihtoehtoisesti ilmoittaa tasapelistä.
     * Parametsina metodi saa voittajan numeron, joka on 1 jos pelaaja aloitti pelin, 2 jos 
     * pelaaja oli toisena pelivuorossa tai 0, jos peli päättyi tasapeliin. Metodi palauttaa
     * voittajan pelinappuloiden värin String muotoisena tai vaihtoehtoisesti "tasapeli" ilmoituksen.
     * @param numero voittajan numero, joka on 1 tai 2 tai tasapelin kohdalla 0
     * @return musta, valkoinen tai tasapeli
     */
    public String voittaja(int numero){
        int vari = 0;
        if(numero==1){
            vari = eka.vari();
        }if(numero==2){
            vari = toka.vari();
        }
        if(vari==1) return "musta";
        if(vari==2) return "valkoinen";
        else    return "tasapeli";
    }
    
}
