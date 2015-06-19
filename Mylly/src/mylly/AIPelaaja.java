/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Luokka AIPelaaja luo tekoälyllä pelaavan pelaajan peliin.
 * @author Salla
 */
public class AIPelaaja implements Pelaaja{
    int vari;
    Tekoaly aly;
    
    /**
     * Konstruktori AIPelaaja asettaa pelaajan väriksi parametrinaan saamansa värin sekä 
     * tekoälyksi parametrinaan saamansa Tekoaly -olion.
     * @param vari musta=1, valkoinen=2
     * @param aly Tekoaly -luokan olio
     */
    public AIPelaaja(int vari, Tekoaly aly){
        this.vari = vari;
        this.aly = aly;
    }
    
    /**
     * vari -metodi palauttaa pelaajan värin
     * @return tämän pelaajan väri (1=musta, 2=valkoinen)
     */
    @Override
    public int vari(){
        return this.vari;
    }
    
    public Tekoaly getAly(){
        return this.aly;
    }
    
    /**
     * siirraLaudalle -metodi arvioi tekoalyn avulla parhaan paikan mihin siirtää uusi nappula 
     * ja tekee arvion mukaisen siirron. 
     * @param lauta pelilauta, jossa siirto tehdään
     * @param nappeja kuinka monta nappia on vielä siirtämätä laudalle
     * @return  sijainti johon siirto tehtiin
     */
    @Override
    public int siirraLaudalle(Lauta lauta, int nappeja){
        int paikka = aly.parasTyhjista(lauta, this.vari, nappeja).getAvain(); //paras tyhjistä laudalla olevista sijainneista
        lauta.laitaMerkki(paikka/8, paikka%8, this.vari);   //laitetaan uusi merkki valittuun sijaintiin
        return paikka;
    }
    
    /**
     * siirraLaudalla -metodi arvioi tekoälyn avulla parhaan siirron, mikä voidaan tehdä laudalla
     * jo olevilla nappuloilla ja toteuttaa sen.
     * @param lauta pelilauta, jossa siirto tehdään
     * @return taulukko, jossa on sijainti, josta siirto tehtiin ja sijainti johon siirto tehtiin
     */
    @Override
    public int[] siirraLaudalla(Lauta lauta){
        Solmu solmu = aly.parasSiirto(lauta, this.vari);    //arvioidaan paras siirto
        int uusip = -1;
        int vanhap = solmu.getAvain();          //paikka jossa olevaa nappulaa siirretään
        if(solmu.getArvo()!=-1000) uusip = lauta.siirra(vanhap/8, vanhap%8, solmu.getSuunta());  //tehdään siirto haluttuun suuntaan (ylös, alas, vasemmalle tai oikealle mahdollisuuksien mukaan) 
        if(uusip==vanhap){
            System.out.println("virhe, yritettiin siirtää virheellisesti");
            uusip = -1;
        }
        int[] pal = {vanhap, uusip};
        return pal;   
    }
    
    /**
     * lenna -metodi arvioi tekoälyn avulla parhaan paikan, johon voidaan "lentää" laudalla, eli 
     * minkä tahansa tyhjän sijainnin, johon voidaan siirtää joku jo laudalla olevista nappuloista,
     * sekä tekee sitten arvion mukaisen siirron.
     * @param lauta pelilauta, jossa siirto tehdään
     * @return  sijainti, johon siirto tehtiin
     */
    @Override
    public int[] lenna(Lauta lauta){
        Solmu solmu = aly.parasLento(lauta, this.vari);
        int vanhap = solmu.getAvain();
        int uusip = solmu.getKohde().getAvain();
        uusip = lauta.siirto(vanhap/8, vanhap%8, uusip/8, uusip%8);
        int[] pal = {vanhap, uusip};
        return pal;
    }
    
    /**
     * laudalla -metodi palauttaa pelaajan pelimerkkien määrän laudalla.
     * @param lauta pelilauta, jossa pelataan
     * @return kuinka monta omaa pelimerkkiä on laudalla
     */
    @Override
    public int laudalla(Lauta lauta){
        return lauta.montakoNappia(this.vari);
    }
    
    /**
     * poistaLaudalta -metodi arvioi tekoälyn avulla, mikä vastustajan merkeistä kannattaisi
     * poistaa, kun tähän on mahdollisuus (kun on saatu mylly), ja tekee arvion mukaisen poiston.
     * @param lauta pelilauta jossa pelataan
     * @param nappeja kuinka monta nappia on vielä siirtämättä laudalle
     * @return sijainti josta vastustajan nappi poistettiin
     */
    @Override
    public int poistaLaudalta(Lauta lauta, int nappeja, int edel){
        int poistettava;
            poistettava = aly.parasPoisto(lauta, this.vari, nappeja).getAvain();
            boolean onnistuu = lauta.syo(poistettava/8, poistettava%8, 3-this.vari);
            if(onnistuu)    return poistettava;
            else return -1; 
    }
}
