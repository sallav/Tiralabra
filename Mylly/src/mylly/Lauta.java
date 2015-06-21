/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Lauta -luokassa luodaan ilmentymä pelilaudasta jolla peliä pelataan. Se tarjoaa erilaisia 
 * metodeja pelilaudalla olevien nappuloiden operointiin.
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
        for(int i=0; i<24; i++){
            lauta[i/8][i%8] = 0;
        }
        this.mustia = 0;
        this.valkoisia = 0;
        this.msyoty = 0;
        this.vsyoty = 0;
        int[] paikat = {11, 5, 18, 3, 9, 15, 22, 1, 4, 7, 10, 13, 16, 20, 23, 0, 2, 6, 8, 12, 14, 17, 19, 21};  //kaikki laudan paikat
        this.tyhjat = new SijaintiPuu2(paikat);  //puu jossa kaikki tyhjät paikat
        this.mustat = new SijaintiPuu2();        //tyhjä puu
        this.valkoiset = new SijaintiPuu2();     //tyhjä puu
    }
    
    /**
     * Konstruktori alustaa lauta matriisin parametrina annetuilla sijainneilla ja asettaa
     * syötyjen merkkien määrät parametrien mukaisiksi.
     * @param sijainnit pelilaudan sijainnit matriisina
     * @param msyoty kuinka monta mustia on syöty
     * @param vsyoty kuinka monta valkoisia on syöty
     */
    public Lauta(int[][] sijainnit, int msyoty, int vsyoty){
        this.lauta = new int[3][8];
        this.mustia = 0;
        this.valkoisia = 0;
        this.msyoty = msyoty;
        this.vsyoty = vsyoty;
        this.tyhjat = new SijaintiPuu2();
        this.mustat = new SijaintiPuu2();
        this.valkoiset = new SijaintiPuu2();
        for(int i=0; i<24; i++){
            if(sijainnit[i/8][i%8]==2){
                this.lauta[i/8][i%8] = 2;
                this.valkoiset.lisaa(i);
                this.valkoisia++;
            }
            else if(sijainnit[i/8][i%8]==1){
                this.lauta[i/8][i%8] = 1;
                this.mustat.lisaa(i);
                this.mustia++;
            }
            else{
                this.lauta[i/8][i%8] = 0;
                this.tyhjat.lisaa(i);
            }
        }
    }

    /**
     * getLauta -metodi palauttaa pelilautaa edustavan matriisin, joka sisältää pelimerkkien sijainnit.
     * @return matriisiesitys pelilaudan sisällöstä
     */
    public int[][] getLauta() {
        return this.lauta;
    }
    
    /**
     * getTyhjat -metodi palauttaa puun, jossa on tämän pelilaudan tyhjät sijainnit solmujen avaimina
     * @return tyhjät sijainnit sisältävä Puu -luokan ilmentymä
     */
    public Puu getTyhjat(){
        return this.tyhjat;
    }
    
    /**
     * getMustat -metodi palauttaa puun, jossa on tällä pelilaudalla olevien mustien merkkien sijainnit 
     * solmujen avaimina.
     * @return mustien merkkien sijainnit sisältävä Puu -luokan ilmentymä
     */
    public Puu getMustat(){
        return this.mustat;
    }
    
    /**
     * getValkoiset -metodi palauttaa puun, jossa on tällä pelilaudalla olevien valkoisten merkkien
     * sijainnit solmujen avaimina.
     * @return valkoisten merkkien sijainnit sisältävä Puu -luokan ilmentymä
     */
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
     * @return true jos merkin laittaminen sijaintiin onnistui, false jos sijainnissa on jo merkki
     */
    public boolean laitaMerkki(int j, int i, int vari) {
        if(this.lauta[j][i]==1 || this.lauta[j][i]==2)  return false; 
        else{
            this.lauta[j][i] = vari;     //uudeksi arvoksi 1 tai 2 varista riippuen
            if(vari==1){
                puuSiirto(((j*8)+i), this.tyhjat, this.mustat, 1, 1);   //siirto tyhjistä mustiiin
            }if(vari==2){
                puuSiirto(((j*8)+i), this.tyhjat, this.valkoiset, 2, 1); //siirto tyhjistä valkoisiin
            }
            return true;
        }
    }
    
    /**
     * poista -metodi poistaa parametrina annetun värisen nappulan parametrina annetusta 
     * sijainnista, sekä vähentää sen väristen nappuloiden määrää laudalla yhdellä. Metodi 
     * palauttaa false, jos annetussa sijainnissa ei ole annetun väristä nappulaa, eikä 
     * poistoa tehdä. Muuten metodi asettaa kyseisen paikan arvoksi 0 ja palauttaa true.
     * @param j rivi, jolta merkki poistetaan
     * @param i paikka rivillä, josta poistetaan
     * @param vari poistettavan merkin väri
     * @return true, jos poisto onnistui, false jos paikassa ei ole oikean väristä nappulaa
     */
    public boolean poista(int j, int i, int vari) {
        if(this.lauta[j][i]!=vari)  return false;  
        else{
            if(vari==1){
                puuSiirto(((j*8)+i), this.mustat, this.tyhjat, 1,(-1)); //siirretään mustista tyhjiin ja vähennetään mustien määrää
            }
            if(vari==2){
                puuSiirto(((j*8)+i), this.valkoiset, this.tyhjat, 2, (-1));  //siirretään valkoisista tyhjiin ja vähennetään valkoisten määrää
            }
            this.lauta[j][i] = 0;
            return true;
        }
    }
    
    /**
     * syo -metodi poistaa parametrina annetun värisen nappulan parametrina annetusta
     * sijainnista, vähentää sen väristen nappuloiden määrää laudalla yhdellä ja merkitsee
     * yhden sen värisen nappulan syödyksi lisäämällä mustien syötyjen nappejen määrää yhdellä.
     * Metodi palauttaa false, jos annetussa sijainnissa ei ole annetun väristä nappulaa eikä 
     * syöntiä tehdä. Muuten metodi asettaa kyseisen paikan arvoksi 0 ja palauttaa true.
     * @param j rivi, jolta merkki syödään
     * @param i paikka rivillä, josta merkki syödään
     * @param vari minkä värinen merkki on tarkoitus syödä
     * @return true, jos syönti onnistui, false, jos paikassa ei ole oikean väristä nappulaa
     */
    public boolean syo(int j, int i, int vari) {
        if(this.lauta[j][i]!=vari)  return false;  //ei oikean väristä merkkiä
        else {
            if(vari==1){
                puuSiirto(((j*8)+i), this.mustat, this.tyhjat, 1, (-1));    //siirretään sijainti mustista tyhjiin
                msyoty++;
            }if(vari==2){
                puuSiirto(((j*8)+i), this.valkoiset, this.tyhjat, 2, (-1)); //siirretään sijainti valkoisista tyhjiin
                vsyoty++;
            }
            this.lauta[j][i] = 0;
            return true;
            }
    }
    
    /**
     * peruSyonti -metodi peruu syo metodin tekemät toimet eli lisää parametrina annetun 
     * väristen merkkien määrää yhdellä ja vähentää sen väristen syötyjen nappien määrää.
     * Jos laudalla on kuitenkin jo kyseisessä kohdassa joku merkki, metodi palauttaa false 
     * eikä mitään tehdä. Muuten palautetaan true ja metodi asettaa parametrina annetun 
     * kohdan arvoksi laudalla parametrina annetun väriä ilmaisevan kokonaisluvun (1 tai 2).
     * @param j rivi, jolta syönti perutaan
     * @param i paikka rivillä, josta syönti perutaan
     * @param vari minkä värisen merkin syönti perutaan
     * @return true, jos peruminen onnistui, false jos paikka ei ole tyhjä
     */
    public boolean peruSyonti(int j, int i, int vari) {
        if(this.lauta[j][i]==1 || this.lauta[j][i]==2)  return false;  
        else {
            if(vari==1){
                puuSiirto(((j*8)+i), this.tyhjat, this.mustat, 1, 1);   //siirretään sijainti tyhjistä mustiin
                msyoty--;
            }else if(vari==2){
                puuSiirto(((j*8)+i), this.tyhjat, this.valkoiset, 2,1); //siirretään sijainti tyhjistä valkoisiin
                vsyoty--;
            }
            this.lauta[j][i] = vari;
            return true;
        }
    }
    
    /**
     * puuSiirto -metodi poistaa parametrina annetun avaimen sisältävän solmun parametrina
     * annetusta puusta sekä lisää sen parametrina annettuun toiseen puuhun. Sen jälkeen 
     * tehdään parametrina annettu muutos parametrina annetun väristen nappien määrään 
     * laudalla.
     * @param avain sijainti, joka halutaan siirtää puusta toiseen
     * @param poistettava puu, josta avaimen sisältävä solmu poistetaan
     * @param lisattava puu, johon avaimen sisältävä solmu lisätään
     * @param vari minkä väristen merkkien määrää muutetaan
     * @param muutos muutos, joka lisätään merkkien määrään (+1 tai -1)
     */
    public void puuSiirto(int avain, Puu poistettava, Puu lisattava, int vari, int muutos){
        try{
            Solmu lisa = poistettava.poista(avain);
            lisattava.lisaa(lisa);
            if(vari==1) this.mustia = this.mustia + muutos;
            else if(vari==2) this.valkoisia = this.valkoisia + muutos;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * siirra -metodissa tehdään parametrina annetun suunnan mukainen siirto laudalla olevalle 
     * nappulalle, joka on parametrina annetussa sijainnissa. Nappulaa voi siirtää neliön sisällä 
     * eli yhdellä rivillä yhden siirron vasemmalle tai oikealle ja neliön sivun keskipaikoilla, 
     * eli paikoilla joiden indeksi on pariton, sijainnin mukaan ylös tai alas, eli riviltä 
     * toiselle. Metodi palauttaa arvonaan uuden sijainnin (0-23). Jos kuitenkin yritetään siirtää 
     * laudan ulkopuolelle tai varattuun paikkaan, palauttaa metodi vanhan sijainnin.
     * @param j rivi, jossa siirrettävä nappi sijaitsee
     * @param i paikka rivillä, jossa siirrettävä nappi sijaitsee
     * @param suunta y=ylös, a=alas, v=vasemmalle, o=oikealle
     * @param vari siirrettävän nappulan väri (1=musta, 2=valkoinen)
     * @return nappulan uusi sijaintipaikka, eli paikka johon siirrettiin tai jos siirto ei onnistunut
     * palautetaan vanha sijainti
     */
    public int siirra(int j, int i, char suunta, int vari){
            if(suunta=='a' && (j>0 && i%2!=0))   return siirto(j, i, j-1, i, vari);    //riveillä 1 ja 0 voi siirtyä ylös eli ulommille neliöille keskipaikoilta 
            if(suunta=='y' && (j<2 && i%2!=0))   return siirto(j, i, j+1, i, vari);    //riveillä 1 ja 2 voi siirtyä alas eli sisemmille neliöille keskipaikoilta
            if(suunta=='v')   return siirto(j, i, j, vas(i), vari);                 //vasemmalle neliön sisällä
            if(suunta=='o')   return siirto(j, i, j, oik(i), vari);                 //oikealle neliön sisällä
            else return (8*j)+i;
    }
    
    /**
     * siirto -metodi tekee yhden siirron nappulalle vanhasta sijainnista uuteen sijaintiin. 
     * Metodi palauttaa vanhan sijainnin, jos parametrina annetussa uudessa sijainnissa on jo 
     * jokin merkki, muuten uusi sijainti saa arvokseen parametrina annetun vanhan sijainnin arvon, 
     * joka puolestaan nollaantuu. Metodi palauttaa siirron onnistuessa uuden paikan numeron.
     * @param vanhaj rivi, jolla siirrettävä nappi sijaitsee
     * @param vanhai rivin paikka, jossa siirrettävä nappi sijaitsee 
     * @param uusij rivi, johon siirrettävä nappi siirretään
     * @param uusii paikka rivillä, johon siirrettävä nappi siirretään
     * @return uuden paikan numero (0-23)
     */
    public int siirto(int vanhaj, int vanhai, int uusij, int uusii, int vari) {
        if(this.lauta[uusij][uusii]==1 || this.lauta[uusij][uusii]==2)  return (vanhaj*8)+vanhai;  //paikalla on jo merkki
        else{
            if(this.lauta[vanhaj][vanhai]==0)   return (vanhaj*8)+vanhai;
            this.lauta[uusij][uusii] = this.lauta[vanhaj][vanhai];
            puuVaihto(((vanhaj*8)+vanhai), ((uusij*8)+uusii), vari);        //sijainnit oikeisiin puihin
            this.lauta[vanhaj][vanhai] = 0;     //vanha sijainti jää tyhjäksi
            return (uusij*8) + uusii;           //palautetaan uusi sijainti
        }
    }
    
    /**
     * puuVaihto -metodi poistaa parametrina annetun vanhan sijainnin avaimenaan sisältävän 
     * solmun parametrina annettua väriä vastaavasta puusta ja siirtää sen tyhjiä sijainteja 
     * sisältävään puuhun. Sen jälkeen parametrina annettua uutta sijaintia vastaava solmu
     * siirretään tyhjien puusta parametrina annetun väristen merkkien sijaintien puuhun.
     * @param vanha vanha sijainti, joka tyhjenee
     * @param uusi uusi sijainti johon laitetaan merkki
     * @param vari minkä värinen merkki on siirretty (1=musta, 2=valkoinen)
     */
    public void puuVaihto(int vanha, int uusi, int vari){
        if(vari==1){
            puuSiirto(vanha, this.mustat, this.tyhjat, 0, 0);
            puuSiirto(uusi, this.tyhjat, this.mustat, 0, 0);
        }else if(vari==2){
            puuSiirto(vanha, this.valkoiset, this.tyhjat, 0, 0);
            puuSiirto(uusi, this.tyhjat, this.valkoiset, 0, 0);
        }
    }
    
    /**
     * merkki -metodi palauttaa parametrina annetussa sijainnissa olevan merkin värin, eli 1 
     * jos merkki on musta ja 2 jos merkki on valkoinen. Jos sijainti on tyhjä metodi palauttaa 0.
     * @param j rivi, josta merkkiä etsitään
     * @param i paikka rivillä, josta merkkiä etsitään
     * @return 1 jos merkki on musta, 2 jos merkki on valkoinen, 0 jos sijainti on tyhjä
     */
    public int merkki(int j, int i){
        return this.lauta[j][i];
    }
    
    /**
     * montakoNappia -metodi palauttaa parametrina annetun väristen merkkien määrän laudalla.
     * @param vari minkä väristen merkkien määrä halutaan tietää
     * @return kysytyn väristen merkkien määrä
     */
    public int montakoNappia(int vari){
        if(vari==1) return this.mustat.getKoko();
        else return this.valkoiset.getKoko();
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
     * eiMyllyssa -metodi tutkii, onko laudalla parametrina annetun värisiä merkkejä, jotka
     * eivät ole myllyssä
     * @param vari minkä värisiä merkkejä tutkitaan (1=musta, 2=valkoinen)
     * @return true, jos laudalla on annetun värisiä merkkejä, jotka eivät ole myllyssä, muuten false
     */
    public boolean eiMyllyssa(int vari){
        Solmu juuri = null;
        if(vari==1) juuri = this.mustat.getJuuri();
        if(vari==2) juuri = this.valkoiset.getJuuri();
        if(eiMyllyssaOlevia(juuri, vari))   return true;
        else return false;
    }
    
    /**
     * eiMyllyssaOlevia -metodi tutkii onko parametrina annetusta juuresta alkavassa 
     * alipuussa sijainteja, joissa olevat merkit eivät ole parametrina annetun värisessä 
     * myllyssä
     * @param juuri Solmu -olio, joka on tutkittavan alipuun juuri
     * @param vari minkä väristen merkkien muodostamia myllyjä tutkitaan
     * @return true, jos alipuussa on merkkejä, jotka eivät ole myllyssä, false jos kaikki
     * alipuun merkit ovat myllyssä
     */
    public boolean eiMyllyssaOlevia(Solmu juuri, int vari){
        if(juuri!=null){
            boolean vas = eiMyllyssaOlevia(juuri.getVasen(), vari);
            boolean oik = eiMyllyssaOlevia(juuri.getOikea(), vari);
            if(vas) return true;
            if(oik) return true;
            if(!mylly(vari, juuri.getAvain()))   return true;
        }
        return false;
    }
    
    /**
     * myllyja -metodi tarkistaa onko parametrina annetun väriselle pelaajalle syntynyt laudalle 
     * myllyjä ja laskee sekä palauttaa niiden määrän.
     * @param vari minkä värisiä nappeja tarkastellaan
     * @return laudalla oleva väriä vastaavien myllyjen määrä
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
     * melkeinMyllyja -metodi palauttaa listan, joka sisältää sijainnit, jotka täyttämällä
     * parametrina annetun värinen nappula saisi myllyn.
     * @param vari minkä värisiä nappuloita tarkastellaan
     * @return Lista sijainneista, jotka täyttämällä väri saisi myllyn
     */
    public Lista melkeinMyllyja(int vari){
        Lista mmyllyt = new Lista();
        for(int i=1; i<24; i=i+2){      //käydään läpi vain keskipaikat
            int tyhjap = melkeinMyllySivulla(i, vari);
            if(tyhjap!=-1)   mmyllyt.lisaa(tyhjap);             //neliön sivulla olevat melkein myllyt
            if(i<8){                                            //rivien väliset melkein myllyt ekalta riviltä
                tyhjap = melkeinMyllyKeskella(i, vari);
                if(tyhjap!=-1)  mmyllyt.lisaa(tyhjap);          
            }
        }
        return mmyllyt;     
    }
    
    /**
     * melkeinMyllySivulla -metodi tutkii onko neliön sivulla, jossa parametrina annettu paikka 
     * laudalla sijaitsee, kahta samanväristä nappulaa, toisin sanoen onko laudalla 
     * melkein mylly kyseisessä paikassa.
     * @param paikka (0-23) sijainti pelilaudalla 
     * @param vari minkä värisiä merkkejä tutkitaan
     * @return sen paikan numero, jonka täyttämällä väri saisi myllyn tai -1 jos etsityllä paikalla
     * ei ole kahta saman väristä nappulaa eli melkein myllyä
     */
    public int melkeinMyllySivulla(int paikka, int vari){
        int j = paikka/8;
        int i = paikka%8;
        if(lauta[j][i]==vari){  //jos kyseisessä paikassa on etsityn värinen nappula
            if(lauta[j][oik(i)]==vari && lauta[j][vas(i)]!=vari)    return (j*8)+vas(i);
            else if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]!=vari)   return (j*8)+oik(i);
        }else{                  //jos paikassa ei ole etsityn väristä nappulaa
            if(i%2!=0 &&lauta[j][oik(i)]==vari && lauta[j][vas(i)]==vari) return paikka;  //keskipaikat
            else if(i%2==0 && ((lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari) || (lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari))) return paikka;
        }
        return -1;
    }
    
    /**
     * melkeinMyllyKeskella -metodi tutkii onko parametrina annetussa sijainnissa
     * rivien välisellä suoralla kahta saman väristä nappulaa, eli onko neliön 
     * keskipaikoilla kahdella rivillä saman väriset nappulat samassa indeksissä.
     * Metodi palauttaa arvonaan sen paikan, jonka täyttämällä parametrina annettu
     * väri saisi myllyn tai -1, jos kohdassa ei ole kahta saman väristä nappulaa
     * eli melkein myllyä.
     * @param paikka sijainti laudalla (0-23)
     * @param vari minkä värisiä nappuloita tutkitaan (1=musta, 2=valkoinen)
     * @return sen paikan numero, jonka täyttämällä väri saisi myllyn tai -1 jos etsityllä paikalla
     * ei ole kahta saman väristä nappulaa eli melkein myllyä
     */
    public int melkeinMyllyKeskella(int paikka, int vari){
        int j = paikka/8;
        int i = paikka%8;
        if(i%2==0)  return -1;   //kulmapaikoilla ei rivien välisiä myllyjä
        else if(lauta[j][i]==vari){
            if(lauta[ala(j)][i]==vari && lauta[yla(j)][i]!=vari) return (yla(j)*8)+i;
            else if(lauta[yla(j)][i]==vari && lauta[ala(j)][i]!=vari) return (ala(j)*8)+i;
        }
        else if(lauta[j][i]!=vari && (lauta[ala(j)][i]==vari && lauta[yla(j)][i]==vari)) return paikka;
        return -1;
    }
    
    /**
     * getSuunnat -metodi palauttaa merkkijonoesityksenä ne suunnat joihin parametrina 
     * annetusta sijainnista voidaan siirtyä ts. merkkijonon, jossa on vieressä olevia 
     * tyhjiä paikkoja vastaavat kirkaimet (v=vasen, o=oikea, y=ylä, a=ala).
     * @param paikka sijainti laudalla
     * @return merkkijono, jossa on mahdollisia siirtymissuuntia vastaavat kirjaimet
     */
    public String getSuunnat(int paikka){
        String suunnat = "";
        int j = paikka/8;
        int i = paikka%8;
        if(lauta[j][vas(i)]==0) suunnat += 'v';     //jos voidaan liikkua vasemmalle
        if(lauta[j][oik(i)]==0) suunnat += 'o';     //jos voidaan liikkua oikealle
        if(i%2!=0 && j<2 && lauta[yla(j)][i]==0) suunnat += 'y';    //jos voidaan liikkua yläriville
        if(i%2!=0 && j>0 && lauta[ala(j)][i]==0) suunnat += 'a';    //jos voidaan liikkua alariville
        return suunnat; //esim. "voya" (kun kaikkiin suuntiin voidaan liikkua)
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
