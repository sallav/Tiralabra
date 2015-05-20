/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Lauta -luokassa luodaan ilmentymä pelilaudasta jolla pelataan. Se tarjoaa erilaisia metodeja pelilaudalla
 * olevien nappuloiden operointiin.
 * @author Salla
 */
public class Lauta {
    int[][] lauta;

    /**
     * Kuinka monta mustaa nappia on laudalla.
     */
    public int mustia;

    /**
     * Kuinka monta valkoista nappia on laudalla.
     */
    public int valkoisia;
    
    /**
     * Pelilauta muodostuu 24 paikasta, jotka on jaettu kolmelle riville. Kukin rivi edustaa yhtä myllyn
     * pelilaudalla olevaa neliötä. Aluksi mustien sekä valkoisten määräksi alustetaan 0.
     */
    public Lauta(){
        this.lauta = new int[3][8];
        this.mustia = 0;
        this.valkoisia = 0;
    }
    
    /**
     * laitaMerkki metodissa laitetaan parametrina annettuun sijaintiin parametrina annetun värinen merkki,
     * eli kyseinen kohta laudalla saa arvokseen 1, jos väri on musta ja 2, jos väri on valkoinen. 
     * Metodi heittää poikkeuksen, jos annetussa sijainnissa on jo joku toinen nappi.
     * Jos sijainti on tyhjä ja merkin asettaminen onnistuu lisätään laudalla olevien sen väristen merkkien
     * määrää yhdellä, eli mustia tai valkoisia -muuttujan arvo kasvaa yhdellä.
     * @param j rivi eli neliö jolle merkki asetetaan
     * @param i paikka rivillä
     * @param vari asetettavan merkin väri
     * @throws Exception jos paikan arvo on 1 tai 2
     */
    public void laitaMerkki(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]==1 || this.lauta[j][i]==2) throw new Exception();
        this.lauta[j][i] = vari;
        if(vari==1) mustia++;
        if(vari==2) valkoisia++;
    }
    
    /**
     * poista -metodi poistaa parametrina annetun värisen nappulan parametrina annetusta sijainnista, sekä 
     * vähentää sen väristen nappuloiden määrää laudalla yhdellä, toisin sanoen mustia tai valkoisia saa 
     * arvokseen yhden vähemmän. Metodi heittää poikkeuksen, jos annetussa sijainnissa ei ole annetun väristä
     * nappulaa. Muuten metodi asettaa kyseisen paikan arvoksi 0.
     * @param j rivi, jolta merkki poistetaan
     * @param i paikka rivillä, josta poistetaan
     * @param vari poistettavan merkin väri
     * @throws Exception jos paikan arvo ei vastaa poistettavan merkin väriä
     */
    public void poista(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]!=vari)  throw new Exception();
        if(vari==1) mustia--;
        if(vari==2) valkoisia--;
        this.lauta[j][i] = 0;
    }
    
    /**
     * siirra -metodissa tehdään parametrina annetun suunnan mukainen siirto laudalla olevalle nappulalle, 
     * joka on parametrina annetussa sijainnissa. Nappulaa voi siirtää neliön sisällä eli yhdellä rivillä
     * yhden siirron vasemmalle tai oikealle ja neliön sivun keskipaikoilla, eli paikoilla joiden indeksi 
     * on pariton, sijainnin mukaan ylös tai alas, eli riviltä toiselle.
     * Metodi heittää poikkeuksen jos yritetään siirtää laudan ulkopuolelle tai varattuun paikkaan.
     * @param j rivi, jossa siirrettävä nappi sijaitsee
     * @param i paikka rivillä, jossa siirrettävä nappi sijaitsee
     * @param suunta y=ylös, a=alas, v=vasemmalle, o=oikealle
     * @return nappulan uusi sijaintipaikka 
     * @throws Exception jos uudessa paikassa on jo nappi tai siirto on laiton.
     */
    public int siirra(int j, int i, char suunta) throws Exception{
        switch(suunta){
            case 'y':   if(j>1 || i%2!=0)   return siirto(j, i, j-1, i);
            case 'a':   if(j<1 || i%2!=0)   return siirto(j, i, j+1, i);
            case 'v':   return siirto(j, i, j, vas(i));
            case 'o':   return siirto(j, i, j, oik(i)); 
            default:    throw new Exception();
        }
    }
    
    /**
     * siirto -metodi tekee yhden siirron nappulalle vanhasta sijainnista uuteen sijaintiin. 
     * Metodi heittää poikkeuksen, jos parametrina annetussa uudessa sijainnissa on jo jokin
     * merkki, muuten uusi sijainti saa arvokseen parametrina annetun vanhan sijainnin arvon, 
     * joka puolestaan nollaantuu. Metodi palauttaa siirron onnistuessa uuden paikan numeron.
     * @param vanhaj rivi, jolla siirrettävä nappi sijaitsee
     * @param vanhai rivin paikka, jossa siirrettävä nappi sijaitsee 
     * @param uusij rivi, johon siirrettävä nappi siirretään
     * @param uusii paikka rivillä, johon siirrettävä nappi siirretään
     * @return uuden paikan numero
     * @throws Exception jos paikka, johon yritetään siirtää on jo varattu
     */
    public int siirto(int vanhaj, int vanhai, int uusij, int uusii) throws Exception{
        if(this.lauta[uusij][uusii]==1 || this.lauta[uusij][uusii]==2)  throw new Exception();
            this.lauta[uusij][uusii] = this.lauta[vanhaj][vanhai];
            this.lauta[vanhaj][vanhai] = 0;
            return (uusij*8) + uusii;
    }
    
    /**
     * merkki -metodi palauttaa parametrina annetussa sijainnissa olevan merkin värin, eli 1 jos merkki
     * on musta ja 2 jos merkki on valkoinen. Jos sijainti on tyhjä metodi palauttaa 0.
     * @param j rivi, josta merkkiä etsitään
     * @param i paikka rivillä, josta merkkiä etsitään
     * @return 1 jos merkki on musta, 2 jos merkki on valkoinen, 0 jos sijainti on tyhjä
     */
    public int merkki(int j, int i){
        if(this.lauta[j][i]==1) return 1;
        if(this.lauta[j][i]==2) return 2;  
        else return 0;
    }
    
    /**
     * montakoNappia -metodi palauttaa parametrina annetun väristen merkkien määrän laudalla.
     * @param vari minkä väristen merkkien määrä halutaan tietää
     * @return kysytyn väristen merkkien määrä
     * @throws Exception jos annettu väri on tuntematon eli ei 1 tai 2
     */
    public int montakoNappia(int vari) throws Exception{
        if(vari==1) return mustia;
        if(vari==2) return valkoisia;
        else throw new Exception();
    }
    
    public ArrayList<Integer> sijainnit(int vari){      //tarvitaanko??
        ArrayList<Integer> paikat = new ArrayList<Integer>();
        for(int j=0; j<this.lauta.length; j++){
            for(int i=0; i<this.lauta[0].length; i++) {
                if (this.lauta[j][i] == vari)   paikat.add((8*j)+i);
            }
        }
        return paikat;
    }
    
    public ArrayList<Integer> tyhjia(){     //tarvitaanko??
        ArrayList<Integer> paikat = new ArrayList<Integer>();
        for(int j=0; j<this.lauta.length; j++){
            for(int i=0; i<this.lauta[0].length; i++) {
                if (this.lauta[j][i]!=1 && this.lauta[j][i]!=2)   paikat.add((8*j)+i);
            }
        }
        return paikat;
    }
    
    /**
     * voikoLiikkua -metodi tarkistaa voiko parametrina annetun värinen pelaaja tehdä vielä siirtoja laudalla.
     * Jos pelaaja ei voi enää liikuttaa nappejaan laudalla, palauttaa metodi false, muuten palautetaan true.
     * @param vari minkä värisiä nappeja halutaan tarkastella
     * @return true jos nappeja voi liikuttaa, false jos vieressä olevia tyhjiä paikkoja ei ole
     */
    public boolean voikoLiikkua(int vari){
            for(int k=0; k<24; k++){
                int j = k/8;
                int i = k%8;
                if(this.lauta[j][i]!=vari)  continue;
                if(merkki(j, vas(i))==0 || merkki(j, oik(i))==0)  return true;
                if(j==0 || j==1){
                    if(merkki(j+1, i)==0) return true;
                }if(j==1 || j==2){
                    if(merkki(j-1, i)==0) return true;
                }
            }
        return false;
    }
    
    /**
     * myllyja -metodi tarkistaa onko parametrina annetun väriselle pelaajalle syntynyt laudalle myllyä eli
     * onko laudalla kolmen samanvärisen merkin suoraa. Metodi käy läpi kaikki mahdolliset paikat myllyille 
     * ja palauttaa true, jos havaitsee laudalla myllyn. Jos myllyjä ei löytynyt metodi palauttaa false.
     * @param vari minkä värisiä nappeja tarkastellaan
     * @return true jos löytyy mylly, false jos ei ole myllyä
     */
    public boolean myllyja(int vari){
        for(int k=1; k<24; k=k+2){
            if(this.lauta[k/8][k%8]==vari){
                int j = k/8;
                int i = k%8;
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)    return true;
                if(j==1 && lauta[j-1][i]==vari && lauta[j+1][i]==vari)    return true;
                if(j==2 && lauta[j-1][i]==vari && lauta[j-2][i]==vari)    return true;
            }
        }
        return false;
    }
    
    /**
     * mylly -metodi tarkistaa onko parametrina annetussa paikassa myllyä eli kolmen samanvärisen merkin
     * suoraa parametrina annetulla värillä. Kulmapaikoilla tarkistetaan onko myllyä syntynyt jommalle 
     * kummalle sivulle ja sivupaikoilla tarkistetaan onko paikka keskellä myllyä. 
     * @param vari minkä värisiä merkkejä tarkastellaan
     * @param paikka mitä kohtaa laudalla tarkastellaan
     * @return true jos paikkaan on syntynyt mylly, false jos ei ole myllyä
     */
    public boolean mylly(int vari, int paikka){
        int j = paikka/8;   //rivi
        int i = paikka%8;   //sarake ts. paikka rivillä
        
            if(i%2==0){     //kulmapaikka
                if(lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari)   return true;
                if(lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari)   return true;
            }  
            else{     //sivupaikka
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)    return true;
                if(j==1 && lauta[j-1][i]==vari && lauta[j+1][i]==vari)    return true;
                if(j==2 && lauta[j-1][i]==vari && lauta[j-2][i]==vari)    return true;
            }
            return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    public int vas(int i){
        if(i==0)    return 7;
        else        return i-1;
    }
    
    /**
     *
     * @param i
     * @return
     */
    public int vvas(int i){
        if(i==0)    return 6;
        if(i==1)    return 7;
        else        return i-2;
    }
    
    /**
     *
     * @param i
     * @return
     */
    public int oik(int i){
        if(i==7)    return 0;
        else        return i+1;
    }
    
    /**
     *
     * @param i
     * @return
     */
    public int ooik(int i){
        if(i==7)    return 1;
        if(i==6)    return 0;
        else        return i+2;
    }
}
