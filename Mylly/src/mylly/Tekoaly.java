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
    
    /**
     * Luokan Tekoaly konstruktori alustaa algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden
     * @param syvyys kuinka pitkälle pelipuula generoidaan
     */
    public Tekoaly(int syvyys){
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
        int paras = -10;                //paras lopputulos
        int parasp = -1;                //paras paikka mihin laittaa nappula
        for(int i=0; i<24; i++){        //käydään kaikki pelilaudan paikat läpi
            int tulos = kokeileSijainti(lauta, i/8, i%8, vari, 1, true, pelaamatta); //kokeillaan mihin tulokseen kyseiseen sijaintiin siirto johtaa
                if(tulos>paras){        //jos lopputulos on parempi kuin aikaisemmissa siirroissa, laitetaan tulos muistiin
                    paras = tulos;
                    parasp = i;         //sijainti muistiin
                }
        }
        return parasp;                  //palautetaan paikka mihin siirtäminen johtaisi parhaaseen lopputulokseen pelaajan kannalta
    }
    
    /**
     * parasViereisista -metodi tekee arvion siitä, mitä jo laudalla olevista merkeistä
     * pelaajan kannattaa liikuttaa ja mihin suuntaan. Metodi palauttaa String muotoisen 
     * esityksen, jossa ensimmäisenä on muutettavaa sijaintia edustava luku(0-23) sekä
     * välimerkin jälkeen suuntaa edustava kirjain(v=vasempaan, o=oikeaan, a=alas, y=ylös).
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @return sijainti, missä olevaa merkkiä kannattaa siirtää(0-23) + " " + suunta johon 
     * merkkiä tulisi liikuttaa('v', 'o', 'y' tai 'a')
     */
    public String parasViereisista(Lauta lauta, int vari){
        int paras = -10;
        String parass = "";           //paras siirto: mikä nappi ja mihin suuntaan
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)!=vari)    continue;       //kokeillaan siirtää vain omia nappeja
            String[] tulos = kokeileSiirtoaOma(lauta, i/8, 1%8, vari, 1).split(" ");
               int siirront = Integer.valueOf(tulos[0]);
                        if(siirront>paras) {
                            paras = siirront;
                            parass = i + " " + tulos[1];    //paikka, jossa olevaa nappia siirretään ja suunta mihin siirretään
                        }
        }
        return parass;
    }
    
    /**
     * parasPoistettava -metodi tekee arvion siitä mikä vastustajan merkeistä kannattaisi 
     * poistaa tilanteessa, jossa pelaaja on saanut myllyn.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari myllyn saaneen pelaajan väri, eli pelivuorossa olevan pelaajan väri
     * @param pelaamatta kuinka monta pelinappulaa pelaajilla on siirtämättä laudalle
     * @return sijainti (0-23) mistä vastustajan pelimerkki kannattaa poistaa
     */
    public int parasPoistettava(Lauta lauta, int vari, int pelaamatta){
        int paras = -10;
        int parasp = -1;
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)!=(3-vari) || lauta.mylly(3-vari, i))    continue;
            int tulos = kokeilePoistaa(lauta, i/8, i%8, vari, 1, pelaamatta, true); 
            if(tulos>paras){
                paras = tulos;
                parasp = i;
            }
        }
        return parasp;
    }
    
    /**
     * vastaSuunta -metodi palauttaa parametrina annetun suunnan vastakkaisen suunnan 
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
     * tilanteita niistä seuraa. Metodi palauttaa arvion siitä, kuinka edullinen kyseinen 
     * tilanne on pelaajalle.
     * @param lauta Lauta -luokan olio, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return paras mahdollinen tulos johon pelissä päädytään arvioituna asteikolla (-1)-(+1) 
     * (tai joku muu asteikko...)
     */
    public int omaSiirtoLaudalle(Lauta lauta, int vari, int k, int pelaamatta){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);        
        if(pelaamatta==0)   return omaSiirtoLaudalla(lauta, vari, k);      //jos kaikki nappulat on jo käytetty
        int paras = -10;
        for(int i=0; i<24; i++){
            int tulos = kokeileSijainti(lauta, i/8, i%8, vari, k, true, pelaamatta);
                if(tulos>paras) paras = tulos;
        }
        return paras;
    }
    
    /**
     * omaSiirtoLaudalla -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia
     * mahdollisia laudalla tehtävissä olevia siirtoja omalla värillä sekä minkälaisia
     * tilanteita niistä seuraa, kun kaikki pelinappulat on jo siirretty laudalle. Metodi 
     * palauttaa arvion siitä, kuinka edullinen kyseinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari  pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta tilanteesta, johon pelissä päädytään
     */
    public int omaSiirtoLaudalla(Lauta lauta, int vari, int k){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);
        int paras = -10;
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)!=vari)    continue;
            int tulos = Integer.valueOf(kokeileSiirtoaOma(lauta, i/8, i%8, vari, k).split(" ")[0]);    
                if(tulos>paras) paras = tulos;
        }
        return paras;
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
    public int vastaPuoliLaudalle(Lauta lauta, int vari, int k, int pelaamatta){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);        //halutulla syvyydellä tehdään arvio tilanteen hyvyydestä
        if(pelaamatta==0)   return vastaPuoliLaudalla(lauta, vari, k);     //jos kaikki nappulat on jo käytetty
        int huonoin = 10;
        for(int i=0; i<24; i++){                    //kokeillaan asettaa merkki jokaiseen laudan paikkaan
            int tulos = kokeileSijainti(lauta, i/8, i%8, 3-vari, k, false, pelaamatta); //lasketaan tulos mihin kyseinen siirto johtaisi   
                if(tulos<huonoin)   huonoin = tulos;    //jos tulos huonompi kuin aiemmat, pistetään se muistiin
        }
        return huonoin;         //palautetaan huonoin mahdollinen lopputulos
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
    public int vastaPuoliLaudalla(Lauta lauta, int vari, int k){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);
        int huonoin = 10;
        for(int i=0; i<24; i++){                    //kokeillaan kaikkia sijainteja
            if(lauta.merkki(i/8, i%8)!=vari)    continue;
            int tulos = Integer.valueOf(kokeileSiirtoaVastaP(lauta, i/8, i%8, 3-vari, k).split(" ")[0]);   
                if(tulos<huonoin)   huonoin = tulos;    //jos tulos huonompi kuin aiemmat, pistetään se muistiin
        }
        return huonoin;         //palautetaan huonoin mahdollinen lopputulos
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
        int tulos = 10;
        try{
            lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen merkki
            if(lauta.mylly(vari, (8*j)+i))  tulos = poisto(lauta, vari, k, pelaamatta-1, omavuoro);
            else if(omavuoro) tulos = vastaPuoliLaudalle(lauta, vari, k+1, pelaamatta-1);   //jos oma vuoro kutsutaan vastapuolen metodia
            else    tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, pelaamatta-1);    //jos vastapuolen vuoro kutsutaan oman värin metodia
            lauta.poista(j, i, vari);                       //perutaan siirto
        }catch(Exception e){    //jos paikka on epäsopiva, esim. varattu, heitetään poikkeus merkkiä laitettaessa
            if(omavuoro)tulos = -10;
        }
        return tulos;                       //kuinka hyvään lopputulokseen kys. sijainti johtaisi
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
    public String kokeileSiirtoaOma(Lauta lauta, int j, int i, int vari, int k){
            String tulos = "";
            int paras = -10;
                for(int l=0; l<4; l++){ // kokeillaan kaikkiin 4 eri suuntaan
                   int suunnantulos = kokeileSuunta(lauta, j, i, suunnat[l], vari, k, true);
                    if(suunnantulos>paras){
                        paras = suunnantulos;
                        tulos = suunnantulos + " " + suunnat[l];
                    }
                }
            return tulos;
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
    public String kokeileSiirtoaVastaP(Lauta lauta, int j, int i, int vari, int k){
            String tulos = "";
            int huonoin = 10;
                for(int l=0; l<4; l++){     //kokeillaan kaikkiin 4 eri suuntaan
                   int suunnantulos = kokeileSuunta(lauta, j, i, suunnat[l], 3-vari, k, false);
                    if(suunnantulos<huonoin){
                        huonoin = suunnantulos;
                        tulos = suunnantulos + " " + suunnat[l];
                    }
                }
            return tulos;    
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
                int uusip = lauta.siirra(j, i, suunta);                 //kokeillaan siirtää annettuun suuntaan
                    if(lauta.mylly(vari, uusip))    tulos = poisto(lauta, vari, k, 0, omavuoro);
                    else if(omavuoro)    tulos = vastaPuoliLaudalle(lauta, vari, k+1, 0);    //jos on oma vuoro kutsutaan vastapuolen siirtoa
                    else            tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, 0);     //jos on vastapuolen vuoro kutsutaan omaa siirtoa
                lauta.siirra(uusip/8, uusip%8, vastaSuunta(suunta));     //perutaan siirto
                }catch(Exception e){                //jos sijainti on epäsopiva, esim. varattu
                    if(omavuoro)    tulos = -10;
                    else    tulos = 10;
                    }
            return tulos;
        }
        
    /**
     * poisto -metodi kokeilee poistaa kaikkia mahdollisia parametrina annetun värin vastapuolen 
     * merkeistä ja arvioi mikä lopputulos olisi pelaajan kannalta.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari väri joka on saanut myllyn, eli joka saa poistaa vastustajan napin
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on pelaamatta laudalle
     * @param omavuoro onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
     * @return arvio lopputuloksesta johon voidaan poistolla päätyä
     */
    public int poisto(Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro){
            int pal = 10;
            if(omavuoro) pal = -10;
            for(int i=0; i<24; i++){        //käydään läpi kaikki pelilaudan paikat
                if(lauta.merkki(i/8, i%8)!=(3-vari) || lauta.mylly(3-vari, i))    continue; //jos paikassa ei ole vastapuolen väriä, tai vastapuolen nappi on myllyssä
                int tulos = kokeilePoistaa(lauta, i/8, i%8, vari, k, pelaamatta, omavuoro);
                if(omavuoro && tulos>pal) pal = tulos;  //jos vuorossa on pelaaja ja tulos parempi kuin aikaisemmat tulokset
                if(!omavuoro && tulos<pal)  pal = tulos; //jos vuorossa on vastapuoli ja tulos on huonompi kuin aikaisemmat tulokset
            }
            return pal; //palautetaan paras/huonoin lopputulos riippuen siitä onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
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
    public int kokeilePoistaa(Lauta lauta, int j, int i, int vari, int k, int pelaamatta, boolean omavuoro){
            int tulos;
            try{
                lauta.poista(j, i, 3-vari);
                if(omavuoro) tulos = vastaPuoliLaudalle(lauta, vari, k+1, pelaamatta);
                else    tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, pelaamatta);
                lauta.laitaMerkki(j, i, 3-vari);
            }catch(Exception e){
                if(omavuoro)tulos = -10;
                else tulos = 10;
            }
            return tulos;
        }
    
    /**
     *
     * @param lauta
     * @param vari
     * @return
     */
    public int arvioiTilanne(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 2;
        if(lauta.myllyja(3-vari))  return -2;
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(vari))  return 0;
        if(!lauta.voikoLiikkua(vari))   return -2;
        if(!lauta.voikoLiikkua(3-vari)) return 2;
        return 0;
    }
    
}
