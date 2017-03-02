/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import ui.Kayttoliittyma;

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
     * asettaa pelin käyttöliittymäksi parametrina annetun Käyttöliittymä -olion. 
     * Konstruktori luo myös uuden Lauta -luokan ilmentymän, jossa peliä pelataan.
     * @param eka   pelaaja joka aloittaa
     * @param toka  toisena vuorossa oleva pelaaja
     * @param kl Kayttoliittyma rajapinnan toteuttava olio
     */
    public Peli(Pelaaja eka, Pelaaja toka, Kayttoliittyma kl){
        this.eka = eka;                 
        this.toka = toka;
        this.lauta = new Lauta();
        this.kayttol = kl;
    }
    
    /**
     * Peli -luokan konstruktori asettaa ekan ja tokan arvoiksi parametrina saadut Pelaaja
     * -oliot sekä luo uuden Lauta -olion, jossa peliä pelataan. Konstruktori asettaa pelin 
     * käyttöliittymäksi null.
     * @param eka pelaaja, joka aloittaa
     * @param toka toisena vuorossa oleva pelaaja
     */
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
     * getKayttoLiittyma -metodi palauttaa pelin käyttöliittymän
     * @return peliin kuuluva Kayttoliittyma -olio
     */
    public Kayttoliittyma getKayttoliittyma(){
        return this.kayttol;
    }
    
    /**
     * getPelaaja -metodi palauttaa parametrina annetun numeron mukaisen 
     * pelaaja -olion
     * @param nro sen pelaajan vuoronumero(1 tai 2), joka halutaan palautettavan 
     * @return vuoroltaan ensimmäinen tai toinen pelaaja
     */
    public Pelaaja getPelaaja(int nro){
        if(nro==1)  return this.eka;
        if(nro==2)  return this.toka;
        else    return null;
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
     * pelaaLoppuun -metodia kutsutaan, kun molemmat pelaajat ovat asettaneet kaikki merkkinsä 
     * pelilaudalle. Pelaajat tekevät vuorotellen siirtoja laudalla, niin kauan kunnes jompi 
     * kumpi voittaa tai peli päättyy tasapeliin.
     * @return 1 jos ensimmäinen pelaaja voitti, 2 jos toinen pelaaja voitti, 0 jos peli päättyi tasapeliin
     */
    public int pelaaLoppuun(){
        boolean voiliikkua = true;
        while(voiliikkua){   //jatketaan kunnes pelaajat eivät voi enää liikkua
            if(lauta.montakoNappia(eka.vari())<3)     return 2;  //jos pelaaja ei voi liikuttaa nappeja tai merkkejä jäljellä vain kaksi peli loppuu
                voiliikkua = pelaaLaudalla(eka);
                if(!voiliikkua) break;
            if(lauta.montakoNappia(toka.vari())<3)    return 1;  //jos pelaaja ei voi liikuttaa nappeja tai merkkejä jäljellä vain kaksi peli loppuu
                voiliikkua = pelaaLaudalla(toka);
                if(!voiliikkua) break;
        }
    return selvitaVoittaja();    
}
    
    /**
     * selvitaVoittaja -metodi palauttaa pelin voittajan numeron tai 0, jos peli päättyi tasan.
     * @return 1=ensimmäinen pelaaja voitti, 2=toinen pelaaja voitti, 0=tasapeli
     */
    public int selvitaVoittaja(){
        if(!lauta.voikoLiikkua(eka.vari()) && lauta.voikoLiikkua(toka.vari())) {
            System.out.println("Musta ei voi liikkua");
            return 2;
        }
        if(!lauta.voikoLiikkua(toka.vari()) && lauta.voikoLiikkua(eka.vari())) {
            System.out.println("Valkoinen ei voi liikkua");
            return 1;
        }
        if(eka.laudalla(lauta)>toka.laudalla(lauta)) return 1;  //eka voitti
        if(eka.laudalla(lauta)<toka.laudalla(lauta))  return 2; //toka voitti
        else return 0;                                          //tasapeli
    }
    
    /**
     * pelaaLaudalle -metodissa parametrina annettu pelaaja tekee siirtonsa laudalle, jonka jälkeen tarkistetaan 
     * onko syntynyt mylly. Jos pelaaja saa myllyn, tulee hänen poistaa yksi vastapuolen napeista.
     * @param pelaaja Pelaaja -olio, jonka vuoro on tehdä siirto
     * @param pelaamatta kuinka monta nappulaa pelaajilla on vielä pelaamatta laudalle
     */
    public void pelaaLaudalle(Pelaaja pelaaja, int pelaamatta){
        if(kayttol!=null)   this.kayttol.ilmoitaVuoro(pelaaja.vari());
        int sij = pelaaja.siirraLaudalle(lauta, pelaamatta);        //siirretään uusi nappula laudalle
        laudanPaivitys(sij, -1, pelaaja.vari());
        if(lauta.mylly(pelaaja.vari(), sij)){
            poistaLaudalta(pelaaja, pelaamatta);
        }
    }
    
    /**
     * pelaaLaudalla -metodia käytetään silloin kun kaikki pelaajan napit ovat jo laudalla. Tällöin pelaaja voi
     * siirtää jotain laudalla jo olevaa nappia vieressä olevaan tyhjään sijaintiin. Jos pelaajalla on kuitenkin 
     * jäljellä enää vain kolme nappia laudalla, voi uusi sijainti olla mikä tahansa laudalla oleva tyhjä sijainti, 
     * eli pelaaja saa "lentää". Siirron jälkeen tarkistetaan syntyikö kyseiseen kohtaan mylly ja jos näin on 
     * tapahtunut, tulee pelaajan poistaa yksi vastapuolen napeista.
     * @param pelaaja Pelaaja -olio, jonka vuoro on siirtää laudalla olevaa nappia
     * @return true, jos nappulan liikuttaminen onnistui, false, jos pelaaja ei voi enää liikuttaa nappejaan
     */
    public boolean pelaaLaudalla(Pelaaja pelaaja){
        int[] sij;
        if(kayttol!=null) this.kayttol.ilmoitaVuoro(pelaaja.vari());
        if(lauta.montakoNappia(pelaaja.vari())>3)   sij = pelaaja.siirraLaudalla(lauta); //siirretään jotain laudalla jo olevaa nappia
        else sij = pelaaja.lenna(lauta);                //kun vain 3 nappia jäljellä
        if(sij[1]==-1)  return false;                   //uutta paikkaa ei ole, koska merkkiä ei voitu siirtää
        laudanPaivitys(sij[1], sij[0], pelaaja.vari());
        if(lauta.mylly(pelaaja.vari(), sij[1])){
            poistaLaudalta(pelaaja, 0);    // poistetaan vastapuolen nappi
        }
        return true;
    }
    
    /**
     * poistaLaudalta -metodia kutsutaan, kun pelaaja on saanut myllyn ja metodissa
     * pelaaja poistaa jonkun vastapuolen nappuloista ja tämän jälkeen lauta
     * päivitetään ja tulostetaan käyttäjälle.
     * @param pelaaja Pelaaja -olio, joka on vuorossa
     * @param pelaamatta kuinka monta nappulaa pelaajilla on vielä siirtämättä laudalle
     */
    public void poistaLaudalta(Pelaaja pelaaja, int pelaamatta){
        if(kayttol!=null) this.kayttol.ilmoitaMylly(pelaaja.vari());
        int psij = pelaaja.poistaLaudalta(lauta, pelaamatta);
        laudanPaivitys(-1, psij, 3-pelaaja.vari());
    }
    
    /**
     * laudanPaivitys metodi päivittää pelilautaa edustavaa näkymää käyttöliittymässä
     * sekä tulostaa laudan näkymän käyttäjän näkyville.
     * @param siirto tehty siirto, joka päivitetään laudalle
     * @param poisto tehty poisto, joka päivitetään laudalle
     * @param vari kokonaisluku, joka edustaa siirron tehneen nappulan väriä (1=musta, 2=valkoinen)
     */
    public void laudanPaivitys(int siirto, int poisto, int vari){
        if(kayttol!=null){
        this.kayttol.paivitaLauta(poisto, vari, true);      //nappulan poisto
        this.kayttol.paivitaLauta(siirto, vari, false);     //nappula laudalle
        this.kayttol.tulostaLauta();
        }
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
