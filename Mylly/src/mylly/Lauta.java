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
    
    int msyoty;     //montako mustaa on syöty
    int vsyoty;     //montako valkoista on syöty
    Puu tyhjat;
    Puu mustat;
    Puu valkoiset;
    
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
        this.msyoty = 0;
        this.vsyoty = 0;
        int[] paikat = new int[24];
        for(int i=0; i<24; i++){
            paikat[i] = i;
        }
        this.tyhjat = new Puu(paikat);
        this.mustat = new Puu();
        this.valkoiset = new Puu();
    }

    /**
     * getLauta -metodi palauttaa pelilautaa edustavan matriisin, joka sisältää pelimerkkien sijainnit.
     * @return matriisiesitys pelilaudan sisällöstä
     */
    public int[][] getLauta() {
        return this.lauta;
    }
    
    public Puu getTyhjat(){
        return this.tyhjat;
    }
    
    public Puu getMustat(){
        return this.mustat;
    }
    
    public Puu getValkoiset(){
        return this.valkoiset;
    }
    
    /**
     * syoty -metodi palauttaa kuinka monta parametrina annetun väristä nappulaa pelilaudalta
     * on syöty.
     * @param vari minkä väristen merkkien määrää tutkitaan
     * @return kuinka monta parametrina annetun väristä merkkiä on syöty pelilaudalta.
     */
    public int syoty(int vari){
        if(vari==1) return this.msyoty;
        if(vari==2) return this.vsyoty;
        else return 0;
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
        else{ 
            this.lauta[j][i] = vari;     //uudeksi arvoksi 1 tai 2 varista riippuen
            if(vari==1){
                puuSiirto((j*8)+i, this.tyhjat, this.mustat, mustia, +1);   //siirto tyhjistä mustiiin
            }if(vari==2){
                puuSiirto((j*8)+i, this.tyhjat, this.valkoiset, valkoisia, +1); //siirto tyhjistä valkoisiin
            }
        }
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
        else{
            if(vari==1){
                puuSiirto((j*8)+i, this.mustat, this.tyhjat, this.mustia, -1);  //siirretään mustista tyhjiin ja vähennetään mustien määrää
            }
            if(vari==2){
                puuSiirto((j*8)+i, this.valkoiset, this.tyhjat, this.valkoisia, -1);    //siirretään valkoisista tyhjiin ja vähennetään valkoisten määrää
            }
            this.lauta[j][i] = 0;
        }
    }
    
    /**
     * syo -metodi poistaa parametrina annetun värisen nappulan parametrina annetusta
     * sijainnista, vähentää sen väristen nappuloiden määrää laudalla yhdellä ja merkitsee
     * yhden sen värisen nappulan syödyksi lisäämällä mustien syötyjen nappejen määrää yhdellä.
     * Metodi heittää poikkeuksen, jos annetussa sijainnissa ei ole annetun väristä nappulaa. 
     * Muuten metodi asettaa kyseisen paikan arvoksi 0.
     * @param j rivi, jolta merkki syödään
     * @param i paikka rivillä, josta merkki syödään
     * @param vari minkä värinen merkki on tarkoitus syödä
     * @throws Exception jos paikassa ei ole oikean väristä merkkiä
     */
    public void syo(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]!=vari)  throw new Exception();  //ei oikean väristä merkkiä
        if(vari==1){
            puuSiirto((j*8)+i, this.mustat, this.tyhjat, this.mustia, -1);
            msyoty++;
        }if(vari==2){
            puuSiirto((j*8)+i, this.valkoiset, this.tyhjat, this.valkoisia, -1);
            vsyoty++;
        }
        this.lauta[j][i] = 0;
    }
    
    /**
     * peruSyonti -metodi peruu syo metodin tekemät toimet eli lisää parametrina annetun 
     * väristen merkkien määrää yhdellä ja vähentää sen väristen syötyjen nappien määrää.
     * Lopuksi metodi asettaa parametrina annetun kohdan arvoksi laudalla parametrina
     * annetun väriä ilmaisevan kokonaisluvun (1 tai 2). Jos luku on väärä eli <1 tai >2
     * metodi heittää poikkeuksen. Samoin jos laudalla on kyseisessä kohdassa jo merkki
     * metodi heittää poikkeuksen.
     * @param j rivi, jolta syönti perutaan
     * @param i paikka rivillä, josta syönti perutaan
     * @param vari minkä värisen merkin syönti perutaan
     * @throws Exception jos vari parametri on virheellinen tai parametrina annetun paikan
     * arvo on 1 tai 2
     */
    public void peruSyonti(int j, int i, int vari) throws Exception{
        if(this.lauta[j][i]==1 || this.lauta[j][i]==2)  throw new Exception();
        if(vari==1){
            puuSiirto((j*8)+i, this.tyhjat, this.mustat, this.mustia, 1);
            msyoty--;
        }if(vari==2){
            puuSiirto((j*8)+i, this.tyhjat, this.valkoiset, this.valkoisia, 1);
            vsyoty++;
        }else{
            throw new Exception();
        }
        this.lauta[j][i] = vari;
    }
    
    public void puuSiirto(int avain, Puu poistettava, Puu lisattava, int nappulat, int muutos){
        lisattava.lisaa(poistettava.poista(avain), lisattava.getJuuri());
        nappulat = nappulat + muutos;
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
                    if(j<2 && merkki(j+1, i)==0) return true;  //riveillä 0 ja 1: jos alapaikka on tyhjä
                    if(j>0 && merkki(j-1, i)==0) return true;  //riveillä 1 ja 2: jos yläpaikka on tyhjä
                    }
                }
        return false;
    }
    
    /**
     * myllyja -metodi tarkistaa onko parametrina annetun väriselle pelaajalle syntynyt laudalle 
     * myllyjä ja laskee sekä palauttaa niiden määrän.
     * @param vari minkä värisiä nappeja tarkastellaan
     * @return laudalla oleva myllyjen määrä
     */
    public int myllyja(int vari){
        int myllyt = 0;
        for(int k=1; k<24; k=k+2){              //riittää että käydään läpi laudan keskipaikat
            if(this.lauta[k/8][k%8]==vari){     //jos haettu väri
                int j = k/8;                    //rivi
                int i = k%8;                    //paikka rivillä
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    myllyt++;    //rivillä mylly
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)  myllyt++;  //rivillä 0 tarkastetaan onko keskipaikoille syntynyt rivien välinen mylly
            }
        }
        return myllyt;
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
        if(i%2==0)  return myllyKulmassa(j, i, vari);     //onko kulmapaikalla mylly?
        else        return keskellaMyllya(j ,i, vari);     //onko keskipaikalla mylly?
    }
    
    /**
     * myllyKulmassa metodi tutkii onko parametrina annetun paikan vasemmalla puolella 
     * kahta parametrina annetun väristä merkkiä tai onko paikan oikealla puolella kahta
     * parametrina annetun väristä merkkiä. Metodi palauttaa arvonaan true jos näin on 
     * ja muuten false.
     * @param j rivi, jolla olevaa paikkaa tutkitaan
     * @param i paikka rivillä, jossa olevaa paikkaa tutkitaan
     * @param vari minkä värisiä merkkejä etsitään (1=musta, 2=valkoinen)
     * @return true jos kaksi parametrina annetun väristä merkkiä löytyy parametrina annetun 
     * paikan oikealta tai vasemmalta puolelta
     */
    public boolean myllyKulmassa(int j, int i, int vari){
        if(lauta[j][i]!=vari)   return false;
        if(lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari)   return true;    //vasemmalla mylly
        if(lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari)   return true;    //oikealla mylly
        else return false;
    }  
    
    /**
     * keskellaMyllya -metodi tutkii onko parametrina annettu paikka laudalla kahden
     * parametrina annetun värisen merkin välissä ja palauttaa true, jos näin on. Muuten 
     * metodi palauttaa false.
     * @param j rivi, jolla olevaa paikkaa tutkitaan
     * @param i paikka rivillä eli indeksi, jota tutkitaan
     * @param vari minkä värisiä merkkejä etsitään (1=musta, 2=valkoinen)
     * @return true, jos parametrina annettu paikka laudalla on kahden parametrina annetun
     * värisen merkin ympäröimä
     */
    public boolean keskellaMyllya(int j, int i, int vari){          
        if(lauta[j][i]!=vari)   return false;
        if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;  //rivillä mylly
        if(lauta[ala(j)][i]==vari && lauta[yla(j)][i]==vari)    return true;  //rivien välinen mylly         
        else return false;       //ei myllyä kyseisessä kohdassa
    }
    
    /**
     * melkeinMyllyja -metodi palauttaa laudalle muodostuvien melkein myllyjen määrän, 
     * eli niiden kolmen suorien määrän, joista puuttuu vielä yksi nappula.
     * @param vari minkä värisiä nappuloita tarkastellaan
     * @return myllyjen määrä, joista puuttuu vielä yksi nappula
     */
    public int melkeinMyllyja(int vari){
        int mmyllyt = 0;
        for(int i=1; i<24; i=i+2){      //käydään läpi vain keskipaikat
            if(melkeinMyllySivulla(i, vari))   mmyllyt++;           //neliön sivulla olevat melkein myllyt
            if(i<8 && melkeinMyllyKeskella(i, vari))  mmyllyt++;    //rivien väliset melkein myllyt        
        }
        return mmyllyt;     //melkein myllyjen määrä
    }
    
    /**
     * melkeinMyllySivulla -metodi tutkii onko neliön sivulla, jossa parametrina annettu paikka 
     * laudalla sijaitsee, kahta samanväristä nappulaa, toisin sanoen onko laudalla 
     * melkein mylly kyseisessä paikassa.
     * @param paikka (0-23) sijainti pelilaudalla 
     * @param vari minkä värisiä merkkejä tutkitaan
     * @return true, jos neliön sivulla on melkein mylly eli kaksi saman väristä nappulaa, 
     * muuten false
     */
    public boolean melkeinMyllySivulla(int paikka, int vari){
        int j = paikka/8;
        int i = paikka%8;
        if(lauta[j][i]==vari){  //jos kyseisessä paikassa on etsityn värinen nappula
            if(lauta[j][oik(i)]==vari || lauta[j][vas(i)]==vari)   return true;
        }else{                  //jos paikassa ei ole etsityn väristä nappulaa
            if(i%2!=0 &&lauta[j][oik(i)]==vari && lauta[j][vas(i)]==vari) return true;  //keskipaikat
            else if((lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari) || (lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari)) return true;
        }
        return false;
    }
    
    /**
     * melkeinMyllyKeskella -metodi tutkii onko parametrina annetussa sijainnissa
     * rivien välisellä suoralla kahta saman väristä nappulaa, eli onko neliön 
     * keskipaikoilla kahdella rivillä saman väriset nappulat samassa indeksissä.
     * Metodi palauttaa true jos näin on ja false jos näin ei ole tai jos parametrina
     * annettu sijainti on kulmapaikka.
     * @param paikka sijainti laudalla (0-23)
     * @param vari minkä värisiä nappuloita tutkitaan (1=musta, 2=valkoinen)
     * @return true, jos kaksi samanväristä nappulaa ovat kahdella rivillä samoissa
     * indekseissä neliön keskipaikoilla, false jos ei ole kahta samanväristä nappulaa
     * tai sijainti on kulmapaikka
     */
    public boolean melkeinMyllyKeskella(int paikka, int vari){
        int j = paikka/8;
        int i = paikka%8;
        if(i%2==0)  return false;   //kulmapaikoilla ei rivien välisiä myllyjä
        else if(lauta[j][i]==vari && (lauta[ala(j)][i]==vari | lauta[yla(j)][i]==vari))  return true;
        else if(lauta[j][i]!=vari && (lauta[ala(j)][i]==vari && lauta[yla(j)][i]==vari)) return true;
        return false;
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
    
    /**
     * yla -metodi palauttaa rivin yläpuolisen rivin, tai jos on kyseessä ylin rivi,
     * taulukon ensimmäisen rivin.
     * @param j rivi jonka yläpuolista paikkaa etsitään
     * @return riviä ylemmän rivin numero tai alin, jos parametrina on annettu ylin rivi
     */
    public int yla(int j){
        if(j==2)    return 0;
        else return j+1;
    }
    
    /**
     * ala -metodi palauttaa rivin alapuolisen rivin, tai jos on kyseessä alin rivi,
     * taulukon viimeisen rivin.
     * @param j rivi, jonka alapuolista paikkaa etsitään
     * @return riviä alemman rivin numero tai ylin, jos parametrina on annettu alin rivi
     */
    public int ala(int j){
        if(j==0)    return 2;
        else return j-1;
    }
}
