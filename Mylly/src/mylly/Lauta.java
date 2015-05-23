/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Lauta -luokassa luodaan ilmentymä pelilaudasta jolla pelataan. Se tarjoaa erilaisia metodeja 
 * pelilaudalla olevien nappuloiden operointiin.
 * @author Salla
 */
public class Lauta {
    /**
     * lauta -matriisi sisältää pelilaudan kaikki sijainnit, joiden arvo voi olla 0(=tyhjä), 
     * 1(=musta) tai 2(=valkoinen)
     */
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
     * Konstruktori alustaa laudan 3x8 matriisiksi, joka edustaa myllyn pelilautaa. Pelilauta 
     * muodostuu siis 24 paikasta, jotka on jaettu kolmelle riville. Kukin rivi edustaa 
     * yhtä pelilaudalla olevaa neliötä. Aluksi mustien sekä valkoisten määrä alustetaan 
     * nollaksi.
     */
    public Lauta(){
        this.lauta = new int[3][8];     //yhdessä neliössä on 8 paikkaa
        this.mustia = 0;
        this.valkoisia = 0;
    }

    /**
     * getLauta -metodi palauttaa pelilautaa edustavan matriisin, joka sisältää pelimerkkien sijainnit.
     * @return matriisiesitys pelilaudan sisällöstä
     */
    public int[][] getLauta() {
        return lauta;
    }
    
    /**
     * laitaMerkki metodissa laitetaan parametrina annettuun sijaintiin parametrina annetun 
     * värinen merkki, eli kyseinen kohta laudalla saa arvokseen 1 jos väri on musta ja 2 jos 
     * väri on valkoinen. Metodi heittää poikkeuksen, jos annetussa sijainnissa on jo joku 
     * toinen merkki. Jos sijainti on tyhjä ja merkin asettaminen onnistuu lisätään laudalla 
     * olevien sen väristen merkkien määrää yhdellä, eli mustia tai valkoisia -muuttujan arvo 
     * kasvaa yhdellä.
     * @param j rivi eli neliö jolle merkki asetetaan
     * @param i paikka rivillä
     * @param vari asetettavan merkin väri
     * @throws Exception jos paikan arvo on 1 tai 2
     */
    public void laitaMerkki(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]==1 || this.lauta[j][i]==2) throw new Exception();   //jos paikassa on jo merkki
        this.lauta[j][i] = vari;        //uudeksi arvoksi 1 tai 2 varista riippuen
        if(vari==1) mustia++;
        if(vari==2) valkoisia++;
    }
    
    /**
     * poista -metodi poistaa parametrina annetun värisen nappulan parametrina annetusta 
     * sijainnista, sekä vähentää sen väristen nappuloiden määrää laudalla yhdellä. Metodi heittää 
     * poikkeuksen, jos annetussa sijainnissa ei ole annetun väristä nappulaa. Muuten metodi asettaa 
     * kyseisen paikan arvoksi 0.
     * @param j rivi, jolta merkki poistetaan
     * @param i paikka rivillä, josta poistetaan
     * @param vari poistettavan merkin väri
     * @throws Exception jos paikan arvo ei vastaa poistettavan merkin väriä
     */
    public void poista(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]!=vari)  throw new Exception(); //ei oikean väristä merkkiä
        if(vari==1) mustia--;
        if(vari==2) valkoisia--;
        this.lauta[j][i] = 0;
    }
    
    /**
     * siirra -metodissa tehdään parametrina annetun suunnan mukainen siirto laudalla olevalle 
     * nappulalle, joka on parametrina annetussa sijainnissa. Nappulaa voi siirtää neliön sisällä 
     * eli yhdellä rivillä yhden siirron vasemmalle tai oikealle ja neliön sivun keskipaikoilla, 
     * eli paikoilla joiden indeksi on pariton, sijainnin mukaan ylös tai alas, eli riviltä 
     * toiselle. Metodi heittää poikkeuksen jos yritetään siirtää laudan ulkopuolelle tai 
     * varattuun paikkaan.
     * @param j rivi, jossa siirrettävä nappi sijaitsee
     * @param i paikka rivillä, jossa siirrettävä nappi sijaitsee
     * @param suunta y=ylös, a=alas, v=vasemmalle, o=oikealle
     * @return nappulan uusi sijaintipaikka, eli paikka johon siirrettiin 
     * @throws Exception jos uudessa paikassa on jo nappi tai siirto on laiton.
     */
    public int siirra(int j, int i, char suunta) throws Exception{
        switch(suunta){
            case 'y':   if(j>0 || i%2!=0)   return siirto(j, i, j-1, i);    //riveillä 1 ja 2 voi siirtyä ylös eli ulommille neliöille keskipaikoilta 
            case 'a':   if(j<2 || i%2!=0)   return siirto(j, i, j+1, i);    //riveillä 0 ja 1 voi siirtyä alas eli sisemmille neliöille keskipaikoilta
            case 'v':   return siirto(j, i, j, vas(i));                 //vasemmalle neliön sisällä
            case 'o':   return siirto(j, i, j, oik(i));                 //oikealle neliön sisällä
            default:    throw new Exception();          //virheellinen parametri
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
     * @return uuden paikan numero (0-23)
     * @throws Exception jos paikka, johon yritetään siirtää on jo varattu
     */
    public int siirto(int vanhaj, int vanhai, int uusij, int uusii) throws Exception{
        if(this.lauta[uusij][uusii]==1 || this.lauta[uusij][uusii]==2)  throw new Exception();  //paikalla on jo merkki
            this.lauta[uusij][uusii] = this.lauta[vanhaj][vanhai];
            this.lauta[vanhaj][vanhai] = 0;     //vanha sijainti jää tyhjäksi
            return (uusij*8) + uusii;       
    }
    
    /**
     * merkki -metodi palauttaa parametrina annetussa sijainnissa olevan merkin värin, eli 1 
     * jos merkki on musta ja 2 jos merkki on valkoinen. Jos sijainti on tyhjä metodi palauttaa 0.
     * @param j rivi, josta merkkiä etsitään
     * @param i paikka rivillä, josta merkkiä etsitään
     * @return 1 jos merkki on musta, 2 jos merkki on valkoinen, 0 jos sijainti on tyhjä
     */
    public int merkki(int j, int i){
        if(this.lauta[j][i]==1) return 1;   //musta
        if(this.lauta[j][i]==2) return 2;   //valkoinen
        else return 0;                      //tyhjä -> voidaan kokeilla onko paikka varattu vai ei
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
        else throw new Exception();     //virheellinen parametri
    }
    
    /**
     * voikoLiikkua -metodi tarkistaa voiko parametrina annetun värinen pelaaja tehdä vielä 
     * siirtoja laudalla. Jos pelaaja ei voi enää liikuttaa nappejaan laudalla, palauttaa metodi 
     * false, muuten palautetaan true.
     * @param vari minkä värisiä nappeja halutaan tarkastella
     * @return true jos nappeja voi liikuttaa, false jos vieressä olevia tyhjiä paikkoja ei ole
     */
    public boolean voikoLiikkua(int vari){
            for(int k=0; k<24; k++){    //paikat 0-23
                int j = k/8;            //rivi
                int i = k%8;            //paikka rivillä
                if(this.lauta[j][i]!=vari)  continue;   //jos paikassa ei ole haetun väristä merkkiä siirrytään seuraavaan
                if(merkki(j, vas(i))==0 || merkki(j, oik(i))==0)  return true;  //jos voidaan siirtää oikealle tai vasemmalle
                if(i%2!=0){                             //ei kulmapaikoilla!
                    if(j==0 || j==1){                   //rivit 0 ja 1
                        if(merkki(j+1, i)==0) return true;  //jos alapaikka on tyhjä
                    }if(j==1 || j==2){                  //rivit 1 ja 2    
                        if(merkki(j-1, i)==0) return true;  //jos yläpaikka on tyhjä
                }
                }
            }
        return false;
    }
    
    /**
     * myllyja -metodi tarkistaa onko parametrina annetun väriselle pelaajalle syntynyt laudalle 
     * myllyä eli onko laudalla kolmen samanvärisen merkin suoraa. Metodi käy läpi kaikki 
     * mahdolliset paikat myllyille ja palauttaa true, jos havaitsee laudalla myllyn. Jos myllyjä 
     * ei löytynyt metodi palauttaa false.
     * @param vari minkä värisiä nappeja tarkastellaan
     * @return true jos löytyy mylly, false jos ei ole myllyä
     */
    public boolean myllyja(int vari){
        for(int k=1; k<24; k=k+2){              //riittää että käydään läpi laudan keskipaikat
            if(this.lauta[k/8][k%8]==vari){     //jos haettu väri
                int j = k/8;        //rivi
                int i = k%8;        //paikka rivillä
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;    //rivillä mylly
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)    return true;  //rivillä 0 tarkastetaan onko keskipaikoille syntynyt rivien välinen mylly
            }
        }
        return false;
    }
    
    /**
     * mylly -metodi tarkistaa onko parametrina annetussa paikassa myllyä eli kolmen samanvärisen 
     * merkin suoraa parametrina annetulla värillä. Kulmapaikoilla tarkistetaan onko myllyä 
     * syntynyt jommalle kummalle sivulle ja keskipaikoilla tarkistetaan onko paikalla oleva nappi 
     * keskellä myllyä. 
     * @param vari minkä värisiä merkkejä tarkastellaan
     * @param paikka mitä kohtaa laudalla tarkastellaan
     * @return true jos paikkaan on syntynyt mylly, false jos ei ole myllyä
     */
    public boolean mylly(int vari, int paikka){
        if(paikka<0 || paikka>23)   return false;       //paikka laudan ulkopuolella
        int j = paikka/8;   //rivi
        int i = paikka%8;   //sarake ts. paikka rivillä
        
            if(i%2==0){     //kulmapaikka
                if(lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari)   return true;    //vasemmalla mylly
                if(lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari)   return true;    //oikealla mylly
            }  
            else{           //keskipaikka
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;    //rivillä mylly
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)    return true;  //rivien välinen mylly kun rivi 0
                if(j==1 && lauta[j-1][i]==vari && lauta[j+1][i]==vari)    return true;  //rivien välinen mylly kun rivi 1
                if(j==2 && lauta[j-1][i]==vari && lauta[j-2][i]==vari)    return true;  //rivien välinen mylly kun rivi 2
            }
            return false;       //ei myllyä kyseisessä kohdassa
    }
    
    /**
     * metodi vas palauttaa parametrina annetun indeksin vasemman puoleisen paikan indeksin. 
     * @param i indeksi, jonka vasemman puoleista paikkaa kysytään
     * @return vasemman puoleisen paikan indeksi samalla rivillä 
     */
    public int vas(int i){
        if(i==0)    return 7;   //indeksin 0 vasen indeksi on 7
        else        return i-1; //muuten -1
    }
    
    /**
     * metodi vvas palauttaa parametrina annetun indeksin vasemman puoleisen paikan vasemman 
     * puoleisen indeksin.
     * @param i indeksin, jonka vasemman puoleisen paikan vasenta kysytään
     * @return vasemman puoleisen paikan vasen indeksi samalla rivillä
     */
    public int vvas(int i){
        if(i==0)    return 6;   //indeksin 0 vasemman vasen on 6
        if(i==1)    return 7;   //indeksin 1 vasemman vasen on 1
        else        return i-2; //muuten -2
    }
    
    /**
     * metodi oik palauttaa parametrina annetun indeksin oikean puoleisen paikan indeksin.
     * @param i indeksi, jonka oikean puoleisen paikan indeksiä kysytään
     * @return oikean puoleisen paikan indeksi samalla rivillä
     */
    public int oik(int i){
        if(i==7)    return 0;   //indeksin 7 oikea indeksi on 0
        else        return i+1; //muuten +1
    }
    
    /**
     * metodi ooik palauttaa parametrina annetun indeksin oikean puoleisen paikan oikean 
     * puoleisen indeksin.
     * @param i indeksi, jonka oikean puoleisen paikan oikeaa kysytään
     * @return oikean puoleisen paikan oikea indeksi samalla rivillä
     */
    public int ooik(int i){
        if(i==7)    return 1;   //indeksin 7 oikean oikea on 1
        if(i==6)    return 0;   //indeksin 6 oikean oikea on 0
        else        return i+2; //muuten +2
    }
}
