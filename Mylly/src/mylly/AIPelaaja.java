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
    
    /**
     * siirraLaudalle -metodi arvioi tekoalyn avulla parhaan paikan mihin siirtää uusi nappula 
     * ja tekee arvion mukaisen siirron. 
     * @param lauta pelilauta, jossa siirto tehdään
     * @param nappeja kuinka monta nappia on vielä siirtämätä laudalle
     * @return  sijainti johon siirto tehtiin
     */
    @Override
    public int siirraLaudalle(Lauta lauta, int nappeja){
        int paikka = 0;
        try{
            paikka = aly.parasTyhjista(lauta, this.vari, nappeja); //paras tyhjistä laudalla olevista sijainneista
            lauta.laitaMerkki(paikka/8, paikka%8, this.vari);   //laitetaan uusi merkki valittuun sijaintiin
        }catch(Exception e){        //ei periaatteessa pitäisi tulla virhettä
            
        }
        return paikka;
    }
    
    /**
     * siirraLaudalla -metodi arvioi tekoälyn avulla parhaan siirron, mikä voidaan tehdä laudalla
     * jo olevilla nappuloilla ja toteuttaa sen.
     * @param lauta pelilauta, jossa siirto tehdään
     * @return sijainti johon siirto tehtiin
     */
    @Override
    public int siirraLaudalla(Lauta lauta){
        String[] siirto = aly.parasSiirto(lauta, this.vari).split(" ");    //arvioidaan paras siirto: taulukossa ovat arvot mistä ja mihin suuntaan
        int uusip, paikka;      
        try{
            paikka = Integer.valueOf(siirto[0]);        //paikka jossa olevaa nappulaa siirretään
            uusip = lauta.siirra(paikka/8, paikka%8, siirto[1].charAt(0));  //tehdään siirto haluttuun suuntaan (ylös, alas, vasemmalle tai oikealle mahdollisuuksien mukaan) 
        }catch(Exception e){    //ei pitäisi tulla virhettä tässä tapauksessa, vaikka metodi heittää poikkeuksen
            uusip = -1;      
        }
        return uusip;   //uusi paikka
    }
    
    /**
     * lenna -metodi arvioi tekoälyn avulla parhaan paikan, johon voidaan "lentää" laudalla, eli 
     * minkä tahansa tyhjän sijainnin, johon voidaan siirtää joku jo laudalla olevista nappuloista,
     * sekä tekee sitten arvion mukaisen siirron.
     * @param lauta pelilauta, jossa siirto tehdään
     * @return  sijainti, johon siirto tehtiin
     */
    @Override
    public int lenna(Lauta lauta){
        return 0;
    }
    
    /**
     * laudalla -metodi palauttaa pelaajan pelimerkkien määrän laudalla.
     * @param lauta pelilauta, jossa pelataan
     * @return kuinka monta omaa pelimerkkiä on laudalla
     */
    @Override
    public int laudalla(Lauta lauta){
        try{
        return lauta.montakoNappia(this.vari);
        }catch(Exception e){    //heittää poikkeuksen, jos vari -parametri on virheellinen
            return 0;
        }
    }
    
    /**
     * poistaLaudalta -metodi arvioi tekoälyn avulla, mikä vastustajan merkeistä kannattaisi
     * poistaa, kun tähän on mahdollisuus (kun on saatu mylly), ja tekee arvion mukaisen poiston.
     * @param lauta pelilauta jossa pelataan
     * @param nappeja kuinka monta nappia on vielä siirtämättä laudalle
     * @return sijainti josta vastustajan nappi poistettiin
     */
    @Override
    public int poistaLaudalta(Lauta lauta, int nappeja){
        int poistettava;
        try{
            poistettava = aly.parasPoistettava(lauta, this.vari, nappeja);
            lauta.syo(poistettava/8, poistettava%8, 3-this.vari);
        }catch(Exception e){
            return -1;
        }
        return poistettava;
    }
}
