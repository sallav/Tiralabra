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
    Kayttoliittyma kayttol;
    
    /**
     * Konstruktori asettaa ekan ja tokan arvoiksi parametreina saadut Pelaaja -oliot sekä
     * luo uuden Lauta -olion peliä varten.
     * @param eka   pelaaja joka aloittaa
     * @param toka  toisena vuorossa oleva pelaaja
     */
    public Peli(Pelaaja eka, Pelaaja toka, Kayttoliittyma kl){
        this.eka = eka;                 
        this.toka = toka;               
        this.lauta = new Lauta();
        this.kayttol = kl;
    }
    
    public Peli(Pelaaja eka, Pelaaja toka){
        this.eka = eka;
        this.toka = toka;
        this.lauta = new Lauta();
        this.kayttol = null;
    }
    
    /**
     * getLauta -metodi palauttaa pelilautaa edustavan Lauta -olion.
     * @return pelilautaa edustava Lauta -olio, jolla peliä pelataan
     */
    public Lauta getLauta(){
        return this.lauta;
    }
    
    /** 
     * pelaa -metodi aloittaa pelin. Pelaajat asettavat vuorotellen 9 nappia laudalle
     * ja sen jälkeen selvitetään pelin voittaja.
     * @return pelin voittaja: musta=1, valkoinen=2 tai tasapeli=0
     */
    public int pelaa(){
        int nappeja = 18;
        
        while(nappeja>0){       //9 nappulaa laudalle!
            pelaaLaudalle(eka, nappeja);
            nappeja--;
            pelaaLaudalle(toka, nappeja);
            nappeja--;
        }
        int tulos = pelaaLoppuun();  //pelataan peli loppuun
        return voittaja(tulos);         //palautetaan voittajan väriä vastaava numero
    }
        
    /**
     * selvitaVoittaja -metodia kutsutaan, kun molemmat pelaajat ovat asettaneet kaikki merkkinsä 
     * pelilaudalle. Pelaajat tekevät vuorotellen siirtoja laudalla, niin kauan kunnes jompi 
     * kumpi voittaa tai peli päättyy tasapeliin.
     * @return 1 jos ensimmäinen pelaaja voitti, 2 jos toinen pelaaja voitti, 0 jos peli päättyi tasapeliin
     */
    public int pelaaLoppuun(){
        
        while(lauta.voikoLiikkua(eka.vari()) || lauta.voikoLiikkua(toka.vari())){   //jatketaan kunnes pelaajat eivät voi enää liikkua
            if(!lauta.voikoLiikkua(eka.vari()) || eka.laudalla(lauta)<3)     return 2;  //jos pelaaja ei voi liikuttaa nappeja tai merkkejä jäljellä vain kaksi peli loppuu
                pelaaLaudalla(eka);
            if(!lauta.voikoLiikkua(toka.vari()) || toka.laudalla(lauta)<3)    return 1;  //jos pelaaja ei voi liikuttaa nappeja tai merkkejä jäljellä vain kaksi peli loppuu
                pelaaLaudalla(toka);
        }
    return selvitaVoittaja();    
}
    
    public int selvitaVoittaja(){
        if(eka.laudalla(lauta)>toka.laudalla(lauta)) return 1;  //eka voitti
        if(eka.laudalla(lauta)<toka.laudalla(lauta))  return 2; //toka voitti
        else return 0;                                          //tasapeli
    }
    
    /**
     * pelaaLaudalle -metodissa parametrina annettu pelaaja tekee siirtonsa laudalle, jonka jälkeen tarkistetaan 
     * onko syntynyt mylly. Jos pelaaja saa myllyn, tulee hänen poistaa yksi vastapuolen napeista.
     * @param pelaaja
     */
    public void pelaaLaudalle(Pelaaja pelaaja, int pelaamatta){
        int sij = pelaaja.siirraLaudalle(lauta, pelaamatta);        //siirretään uusi nappula laudalle
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta, pelaamatta-1); // poistetaan vastapuolen nappi
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
        if(pelaaja.laudalla(lauta)>3)   sij = pelaaja.siirraLaudalla(lauta); //siirretään jotain laudalla jo olevaa nappia
        else sij = pelaaja.lenna(lauta);                //kun vain 3 nappia jäljellä
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta, 0); // poistetaan vastapuolen nappi
    }
    
    /**
     * voittaja -metodi selvittää voittajan värin tai vaihtoehtoisesti ilmoittaa tasapelistä.
     * Parametsina metodi saa voittajan numeron, joka on 1 jos pelaaja aloitti pelin, 2 jos 
     * pelaaja oli toisena pelivuorossa tai 0, jos peli päättyi tasapeliin. Metodi palauttaa
     * 1 jos voittaja on musta, 2 jos voittaja on valkoinen tai 0 jos peli päättyi tasan.
     * @param numero voittajan numero, joka on 1 tai 2 tai tasapelin kohdalla 0
     * @return voittajan värin numero(1=musta, 2=valkoinen) tai 0 jos tasapeli
     */
    public int voittaja(int numero){
        int vari = 0;       
        if(numero==1){          //eka voitti
            vari = eka.vari();
        }if(numero==2){         //toka voitti
            vari = toka.vari();
        }
        return vari;
    }
    
}
