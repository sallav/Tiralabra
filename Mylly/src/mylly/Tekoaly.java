/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Tekoaly -luokka toteuttaa tekoälyn Myllyn pelaamista varten, jota AIPelaaja -luokan oliot
 * voivat hyödyntää tedessään siirtoja pelilaudalle.
 * @author Salla
 */
public class Tekoaly {
    int syvyys;
    char[] suunnat = {'y', 'a', 'v', 'o'};
    Heuristiikka heuristiikka;
    
    /**
     * Luokan Tekoaly konstruktori alustaa algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden
     * @param syvyys kuinka pitkälle pelipuula generoidaan
     */
    public Tekoaly(Heuristiikka h, int syvyys){
        this.heuristiikka = h;
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
    /**
     * getSyvyys palauttaa syvyyden johon pelipuuta generoidaan tekoälyssä
     * @return pelipuun generointisyvyys
     */
    public int getSyvyys(){
        return this.syvyys;
    }
    
    /**
     * parasTyhjista -metodi tekee arvion parhaasta sijainnista pelilaudalla, johon 
     * pelaaja voi sijoittaa uuden merkin.  
     * @param lauta Lauta -luokan ilmentymä, johon siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @param pelaamatta kuinka monta nappia pelaajilla on laittamatta laudalle
     * @return paras sijainti (0-23) johon merkki kannattaa sijoittaa
     */
    public int parasTyhjista(Lauta lauta, int vari, int pelaamatta){      //kutsutaan kun kaikki napit ei vielä laudalla
        Puu tyhjat = lauta.getTyhjat();     //laudalla olevat tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        Solmu paras = kayLapiSijainnit(juuri, juuri, lauta, vari, 0, true, pelaamatta);
        return paras.getAvain();            //palautetaan paikka mihin siirtäminen johtaisi parhaaseen lopputulokseen pelaajan kannalta
    }
    
    /**
     * parasSiirto -metodi tekee arvion siitä, mitä jo laudalla olevista merkeistä
     * pelaajan kannattaa liikuttaa ja mihin suuntaan. Metodi palauttaa String muotoisen 
     * esityksen, jossa ensimmäisenä on muutettavaa sijaintia edustava luku(0-23) sekä
     * välimerkin jälkeen suuntaa edustava kirjain(v=vasempaan, o=oikeaan, a=alas, y=ylös).
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @return sijainti, missä olevaa merkkiä kannattaa siirtää(0-23) + " " + suunta johon 
     * merkkiä tulisi liikuttaa('v', 'o', 'y' tai 'a')
     */
    public Solmu parasSiirto(Lauta lauta, int vari){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getMustat();
        if(vari==2) merkit = lauta.getValkoiset();
        Solmu juuri = merkit.getJuuri();
        Solmu paras = kayLapiSiirrot(juuri, juuri, lauta, vari, 0, true);
        return paras;
    }
    
    /**
     * parasPoistettava -metodi tekee arvion siitä mikä vastustajan merkeistä kannattaisi 
     * poistaa tilanteessa, jossa pelaaja on saanut myllyn.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari myllyn saaneen pelaajan väri, eli pelivuorossa olevan pelaajan väri
     * @param pelaamatta kuinka monta pelinappulaa pelaajilla on siirtämättä laudalle
     * @return sijainti (0-23) mistä vastustajan pelimerkki kannattaa poistaa
     */
    public int parasPoistettava(Lauta lauta, int vari, int pelaamatta, int edel){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getValkoiset();
        if(vari==2) merkit = lauta.getMustat();
        Solmu juuri = merkit.getJuuri();
        Solmu paras = kayLapiPoistot(juuri, juuri, lauta, vari, 0, pelaamatta, true, edel);
        return paras.getArvo();              //mikä merkki kannattaa poistaa (ts. merkin sijaintipaikka)
    }
    
    public Solmu kayLapiSijainnit(Solmu juuri, Solmu paras, Lauta lauta, int vari, int k, boolean omavuoro, int pelaamatta){
        if(juuri!=null){            //saavutettu lehdet           
            Solmu v = kayLapiSijainnit(juuri.getVasen(), paras, lauta, vari, k, omavuoro, pelaamatta);  //vasen puoli puuta
            Solmu o = kayLapiSijainnit(juuri.getOikea(), paras, lauta, vari, k, omavuoro, pelaamatta);  //oikea puoli puuta
            paras = parempi(v, o, paras, omavuoro);
            paras = kokeile(1, juuri, paras, lauta, vari, k, pelaamatta, omavuoro, 0);
        }
        return paras;
    }
    
    public Solmu kayLapiSiirrot(Solmu juuri, Solmu paras, Lauta lauta, int vari, int k, boolean omavuoro){
        if(juuri!=null){
            Solmu v = kayLapiSiirrot(juuri.getVasen(), paras, lauta, vari, k, omavuoro);
            Solmu o = kayLapiSiirrot(juuri.getOikea(), paras, lauta, vari, k, omavuoro);
            paras = parempi(v, o, paras, omavuoro);
            if(omavuoro)    paras = kokeile(2, juuri, paras, lauta, vari, k, 0, omavuoro, 0);
            else            paras = kokeile(3, juuri, paras, lauta, vari, k, 0, omavuoro, 0);
        }
        return paras;
    }
    
    public Solmu kayLapiPoistot(Solmu juuri, Solmu paras, Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        if(juuri!=null){
            Solmu v = kayLapiPoistot(juuri.getVasen(), paras, lauta, vari, k, pelaamatta, omavuoro, edel); 
            Solmu o = kayLapiPoistot(juuri.getOikea(), paras, lauta, vari, k, pelaamatta, omavuoro, edel);
            paras = parempi(v, o, paras, omavuoro);
            paras = kokeile(4, juuri, paras, lauta, vari, k, pelaamatta, omavuoro, edel);
        }
        return paras;
    }
    
    public Solmu parempi(Solmu vasen, Solmu oikea, Solmu paras, boolean omavuoro){
        if(vasen==null && oikea==null) return paras;
        else if(vasen==null || (omavuoro && oikea.getArvo()>vasen.getArvo()))    paras = oikea;
        else paras = vasen;
        return paras;
    }
    
    public Solmu kokeile(int mita, Solmu solmu, Solmu paras, Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        int sij = solmu.getAvain();
        int tulos = 0;
        switch(mita){
            case 1: tulos = kokeileSijainti(lauta, sij/8, sij%8, vari, k, omavuoro, pelaamatta);
            case 2: tulos = kokeileSiirtoaOma(lauta, solmu, vari, k);
            case 3: tulos = kokeileSiirtoaVastaP(lauta, solmu, vari, k);
            case 4: tulos = kokeilePoistaa(lauta, sij/8, sij%8, vari, k, pelaamatta, omavuoro, edel);
        }
        solmu.setArvo(tulos);
        if(omavuoro && tulos>paras.getArvo())   paras = solmu;
        else if(!omavuoro && tulos<paras.getArvo()) paras = solmu;
        return paras;
    }
    
    /**
     * vastaSuunta -metodi palauttaa parametrina annettua suuntaa vastakkaisen suunnan 
     * char merkkinä. 
     * @param suunta a=alas, y=ylös, v=vasemmalle, o=oikealle
     * @return suunnan vastakohta ('a'->'y', 'y'->'a', 'o'->'v', 'v'->'o')
     */
    public char vastaSuunta(char suunta){
        switch(suunta){
            case 'a': return 'y';
            case 'y': return 'a';
            case 'v': return 'o';
            case 'o': return 'v';
            default: return 'x';
        }
    }
    
    /**
     * omaSiirtoLaudalle -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee 
     * kaikkia mahdollisia laudalle tehtäviä siirtoja omalla värillä sekä minkälaisia 
     * tilanteita niistä seuraa. Metodi palauttaa arvion siitä, kuinka edullinen sen 
     * hetkinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan olio, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return paras mahdollinen tulos johon pelissä päädytään arvioituna asteikolla (-1)-(+1) 
     * (tai joku muu asteikko...)
     */
    public int omaSiirtoLaudalle(Lauta lauta, int vari, int k, int pelaamatta, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, pelaamatta, edel);     //kun haluttu syvyys saavutettu palautetaan arvio tilanteesta        
        if(pelaamatta==0)   return omaSiirtoLaudalla(lauta, vari, k, edel);      //jos kaikki nappulat on jo käytetty kutsutaan toista metodia
        Puu tyhjat = lauta.getTyhjat();     //tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        return kayLapiSijainnit(juuri, juuri, lauta, vari, k, true, pelaamatta).getArvo(); //palautetaan paras mahdollinen lopputulos joka voi syntyä alkuperäisestä tilanteesta
    }
    
    /**
     * omaSiirtoLaudalla -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia
     * mahdollisia laudalla tehtävissä olevia siirtoja omalla värillä sekä minkälaisia
     * tilanteita niistä seuraa, kun kaikki pelinappulat on jo siirretty laudalle. Metodi 
     * palauttaa arvion siitä, kuinka edullinen alkuperäinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari  pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta tilanteesta, johon pelissä päädytään
     */
    public int omaSiirtoLaudalla(Lauta lauta, int vari, int k, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, 0, edel);   //arvio tilanteesta kun haluttu syvyys saavutettu
        Puu merkit = null;
        if(vari==1) merkit = lauta.getMustat();
        else if(vari==2) merkit = lauta.getValkoiset();
        Solmu juuri = merkit.getJuuri();
        Solmu paras = kayLapiSiirrot(juuri, juuri, lauta, vari, k, true);
        return paras.getArvo();                                       //palautetaan paras mahdollinen lopputulos joka voi syntyä tilanteesta
    }
    
    /**
     * vastaPuoliLaudalle -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia 
     * mahdollisia siirtoja vastapuolen värillä sekä minkälaisia tilanteita niistä seuraa.
     * Metodi palauttaa arvion siitä, kuinka edullinen kyseinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jossa siirtoja tehdään 
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on vielä siirtämättä laudalle
     * @return huonoin mahdollinen tulos, johon pelissä päädytään arvioituna asteikolla (-1)-(+1)
     * (tai joku muu..)
     */
    public int vastaPuoliLaudalle(Lauta lauta, int vari, int k, int pelaamatta, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, pelaamatta, edel);     //halutulla syvyydellä tehdään arvio tilanteen hyvyydestä
        if(pelaamatta==0)   return vastaPuoliLaudalla(lauta, vari, k, edel);     //jos kaikki nappulat on jo käytetty
        Puu tyhjat = lauta.getTyhjat();     //kaikki tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();
        Solmu huonoin = kayLapiSijainnit(juuri, juuri, lauta, vari, k, false, pelaamatta);
        return huonoin.getArvo();                             //palautetaan huonoin mahdollinen lopputulos joka voi syntyä tilanteesta 
    }
    
    /**
     * vastaPuoliLaudalla -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia
     * mahdollisia siirtoja vastapuolen värillä sekä minkälaisia tilanteita niistä seuraa, 
     * siinä vaiheessa kun pelaajat ovat siirtäneet jo kaikki nappinsa laudalle. Metodi 
     * palauttaa arvion siitä, kuinka edullinen kyseinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return huonoin mahdollinen tulos, johon pelissä päädytään arvioituna asteikolla (-1)-(+1)
     * (tai joku muu..)
     */
    public int vastaPuoliLaudalla(Lauta lauta, int vari, int k, int edel){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, 0, edel);      //arvio tilanteesta kun haluttu syvyys saavutettu
        Puu merkit = null;
        if(vari==1) merkit = lauta.getMustat();
        else if(vari==2) merkit = lauta.getValkoiset();
        Solmu juuri = merkit.getJuuri();
        Solmu huonoin = kayLapiSiirrot(juuri, juuri, lauta, vari, k, false);
        return huonoin.getArvo();    
        }
    
    /**
     * kokeileSijainti -metodi asettaa parametrina annetun värisen merkin parametrina annettuun 
     * sijaintiin. Jos tästä siirrosta syntyy mylly tekoäly poistaa vastapuolen värisen merkin
     * laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa pelipuun generointia jatketaan 
     * seuraavana vuorossa olevan pelaajan siirrolla. Kun siirtojen tulos on saatu selville
     * poistetaan laudalta metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param j rivi jolla kokeiltava paikka sijaitsee
     * @param i paikka rivillä eli indeksi, johon siirtoa kokeillaan
     * @param vari pelaajan väri, jonka siirtoa kokeillaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro true jos kyseessä on pelaaja, jonka mahdollisuuksia arvioidaan, false,
     * jos kyseessä on vastapuolen siirto
     * @param pelaamatta kuinka monta nappulaa pelaajilla on vielä siirtämättä laudalle
     * @return arvio siitä kuinka hyvä on tilanne, johon siirrolla päädytään
     */
    public int kokeileSijainti(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta){
        int tulos = 1000;
        try{
            lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen halutun värinen merkki
            if(lauta.mylly(vari, (8*j)+i))  tulos = poisto(lauta, vari, k, pelaamatta-1, omavuoro, (j*8)+i); //jos syntyi mylly voidaan poistaa yksi vastapuolen merkeistä
            else if(omavuoro) tulos = vastaPuoliLaudalle(lauta, vari, k+1, pelaamatta-1, (j*8)+i);           //jos  ei myllyä ja oma vuoro kutsutaan vastapuolen metodia
            else    tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, pelaamatta-1, (j*8)+i);            //jos vastapuolen vuoro kutsutaan oman värin metodia
            lauta.poista(j, i, vari);                       //perutaan tehty siirto
        }catch(Exception e){                    //jos paikka on epäsopiva, esim. varattu, heitetään poikkeus merkkiä laitettaessa
            if(omavuoro)tulos = -1000;            //"tätä paikkaa ei haluta käyttää"
        }
        return tulos;                           //kuinka hyvään lopputulokseen kys. sijainti johtaisi
    }
        
    /**
     * kokeileSiirtoaOma -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa 
     * merkkiä kaikkiin mahdollisiin suuntiin ja palauttaa arvion parhaasta mahdollisesta
     * tilanteesta, johon jollain siirrolla voidaan päästä. Tällä metodilla on tarkoitus 
     * kokeilla siirtää sen pelaajan nappuloita, jonka mahdollisuuksia arvioidaan.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappia halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappulaa kokeillaan siirtää
     * @param vari pelaajan väri, jonka mahdollisuuksia yritetään arvioida
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta lopputuloksesta, johon nappulaa siirrettäessä
     * voidaan päätyä
     */
    public int kokeileSiirtoaOma(Lauta lauta, Solmu solmu, int vari, int k){
            int paras = -1000;      
            int sijainti = solmu.getAvain();
                for(int l=0; l<4; l++){ // kokeillaan siirtoa kaikkiin 4 eri suuntaan
                   int suunnantulos = kokeileSuunta(lauta, sijainti/8, sijainti%8, suunnat[l], vari, k, true);
                    if(suunnantulos>paras){
                        paras = suunnantulos;   //jos tulos parempi kuin aikaisemmista siirroista, laitetaan se muistiin
                        solmu.setArvo(suunnantulos);
                        solmu.setSuunta(suunnat[l]);    //myös suunta muistiin
                    }
                }
            return paras;   //palautetaan paras mahdollinen lopputulos ja suunta mistä se syntyi
        }
        
    /**
     * kokeileSiirtoaVastaP -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa 
     * merkkiä kaikkiin mahdollisiin suuntiin ja palauttaa arvion huonoimmasta mahdollisesta
     * tilanteesta, johon jollain siirrolla voidaan päästä. Tällä metodilla on tarkoitus 
     * kokeilla siirtää vastapelaajan nappuloita.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappulaa halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappulaa kokeillaan siirtää
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio huonoimmasta mahdollisesta lopputuloksesta, johon nappulaa siirrettäessä
     * voidaan päätyä
     */
    public int kokeileSiirtoaVastaP(Lauta lauta, Solmu solmu, int vari, int k){
            String tulos = "";
            int huonoin = 1000;
            int sijainti = solmu.getAvain();
                for(int l=0; l<4; l++){                                 //kokeillaan kaikkiin 4 eri suuntaan
                   int suunnantulos = kokeileSuunta(lauta, sijainti/8, sijainti%8, suunnat[l], 3-vari, k, false);
                    if(suunnantulos<huonoin){       
                        huonoin = suunnantulos;                         //jos tulos huonompi kuin aikaisemmissa suunnissa, laitetaan se muistiin
                        solmu.setSuunta(suunnat[l]);        //myös suunta muistiin
                        solmu.setArvo(suunnantulos);
                    }
                }
            return huonoin;               //kuinka huonoon tulokseen nappulaa siirtämällä voidaan päätyä
        }
        
    /**
     * kokeileSuunta -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa
     * nappulaa parametrina annettuun suuntaan. Jos siirrosta seuraa mylly tekoäly poistaa 
     * vastapuolen värisen merkin laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa 
     * pelipuun generointia jatketaan seuraavana vuorossa olevan pelaajan siirrolla. Kun 
     * siirrosta seuraava tulos on saatu selville perutaan laudalla metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappia halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappia halutaan siirtää
     * @param suunta suunta johon laudalla olevaa nappia halutaan siirtää
     * @param vari minkä väristä merkkiä kokeillaan siirtää
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
     * @return arvio tilanteesta johon siirrolla päädytään
     */
    public int kokeileSuunta(Lauta lauta, int j, int i, char suunta, int vari, int k, boolean omavuoro){
            int tulos;
            try{
                int uusip = lauta.siirra(j, i, suunta);                 //kokeillaan siirtää merkkiä annettuun suuntaan
                    if(lauta.mylly(vari, uusip))    tulos = poisto(lauta, vari, k, 0, omavuoro, (j*8)+i);    //jos syntyi mylly saadaan poistaa vastapuolen merkki
                    else if(omavuoro)    tulos = vastaPuoliLaudalle(lauta, vari, k+1, 0, (j*8)+i);  //jos ei myllyä jaon oma vuoro kutsutaan vastapuolen siirtoa
                    else            tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, 0, (j*8)+i);      //jos on vastapuolen vuoro kutsutaan omaa siirtoa
                lauta.siirra(uusip/8, uusip%8, vastaSuunta(suunta));     //perutaan tehty siirto
                }catch(Exception e){                //jos sijainti on epäsopiva, esim. varattu
                    if(omavuoro)    tulos = -1000;    //"tätä siirtoa ei haluta tehdä"
                    else    tulos = 1000;
                    }
            return tulos;                           //lopputulos joka siirrosta seuraisi
        }
        
    /**
     * poisto -metodi kokeilee poistaa kaikkia mahdollisia parametrina annetun värin vastapuolen 
     * merkeistä ja arvioi mikä on paras mahdollinen lopputulos pelaajan kannalta.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari väri joka on saanut myllyn, eli joka saa poistaa vastustajan napin
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on pelaamatta laudalle
     * @param omavuoro onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
     * @return arvio lopputuloksesta johon voidaan poistolla päätyä
     */
    public int poisto(Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getValkoiset();
        if(vari==2) merkit = lauta.getMustat();
        Solmu juuri = merkit.getJuuri();
        Solmu paras = kayLapiPoistot(juuri, juuri, lauta, vari, 0, pelaamatta, true, edel);
        return paras.getArvo();              //mikä merkki kannattaa poistaa (ts. merkin sijaintipaikka)
    }
        
    /**
     * kokeilePoistaa -metodi kokeilee poistaa parametrina annetussa sijainnissa olevan merkin
     * ja tekee arvion siitä syntyvästä lopputuloksesta generoimalla pelipuuta eteenpäin.
     * Kun arvio on tehty metodi palauttaa poistetun napin paikalleen.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param j rivi, jolla oleva nappi halutaan poistaa
     * @param i paikka rivillä eli indeksi, josta nappi poistetaan
     * @param vari pelaajan väri, joka saa tehdä poiston
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @param omavuoro true, jos vuorossa on pelaaja jonka mahdollisuuksia arvioidaan, false
     * jos on vastapuolen pelivuoro
     * @return arvio tilanteesta johon poistolla päädytään
     */
    public int kokeilePoistaa(Lauta lauta, int j, int i, int vari, int k, int pelaamatta, boolean omavuoro, int edel){
            int tulos;
            try{
                lauta.syo(j, i, 3-vari);         //kokeillaan poistaa vastapuolen merkki
                if(omavuoro) tulos = vastaPuoliLaudalle(lauta, vari, k+1, pelaamatta, edel);  //jos oli oma vuoro, seuraa vastapuolen siirto
                else    tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, pelaamatta, edel);      //jos oli vastapuolen poisto, seuraa oma siirto
                lauta.peruSyonti(j, i, 3-vari);    //perutaan tehty siirto    
            }catch(Exception e){                    //jos yritetään poistaa jotain muuta kuin pitäisi
                if(omavuoro)tulos = -1000;            //"tätä ei haluta poistaa"
                else tulos = 1000;
            }
            return tulos;       //lopputulos joka poistosta seuraisi
        }
    
    /**
     * arvioiTilanne -metodi palauttaa arvion siitä, kuinka hyvä parametrina
     * annetulla laudalla oleva pelitilanne on pelaajan kannalta, jonka väri on
     * annettu parametrina.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param vari sen pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan 
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return kokonaislukumerkkinen arvo, joka kuvastaa sitä kuinka hyvä pelitilanne 
     * on annetun värisen pelaajan kannalta
     */
    public int arvioiTilanne(Lauta lauta, int vari, int pelaamatta, int edel){
        return this.heuristiikka.tilanneArvio(lauta, vari, pelaamatta, edel);
    }
    
}
