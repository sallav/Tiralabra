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
     * Luokan Tekoaly konstruktori algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden
     * @param syvyys kuinka pitkälle pelipuula generoidaan
     */
    public Tekoaly(int syvyys){
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
    public int getSyvyys(){
        return this.syvyys;
    }
    
    /**
     * parasTyhjista -metodi tekee arvion parhaasta sijainnista pelilaudalla, johon 
     * pelaaja voi sijoittaa uuden merkin.  
     * @param lauta Lauta -luokan ilmentymä, johon siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @param pelaamatta kuinka monta nappia pelaajalla on laittamatta laudalle
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
     * merkkiä tulisi liikuttaa('v', 'o', 'y', 'a')
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
     * vastakohta -metodi palauttaa suunnan vastakkaisen suunnan char merkkinä. 
     * @param suunta a=alas, y=ylös, v=vasemmalle, o=oikealle
     * @return suunnan vastakohta ('a'->'y', 'y'->'a', 'o'->'v', 'v'->'o')
     */
    public char vastakohta(char suunta){
        switch(suunta){
            case 'a': return 'y';
            case 'y': return 'a';
            case 'v': return 'o';
            case 'o': return 'v';
            default: return 'x';
        }
    }
    
    /**
     * omaSiirto -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia mahdollisia 
     * siirtoja omalla värillä sekä minkälaisia tilanteita niistä seuraa. 
     * @param lauta Lauta -luokan olio, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
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
     * vastaPuoli -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia mahdollisia 
     * siirtoja vastapuolen värillä sekä minkälaisia tilanteita niistä seuraa.
     * @param lauta Lauta -luokan ilmentymä, jossa siirtoja tehdään 
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
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
    
        public int kokeileSijainti(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta){
        int tulos;
        try{
            lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen merkki
            if(lauta.mylly(vari, (8*j)+i))  tulos = poisto(lauta, vari, k, pelaamatta-1, omavuoro);
            else if(omavuoro) tulos = vastaPuoliLaudalle(lauta, vari, k+1, pelaamatta-1);   //jos oma vuoro kutsutaan vastapuolen metodia
            else    tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, pelaamatta-1);    //jos vastapuolen vuoro kutsutaan oman värin metodia
            lauta.poista(j, i, vari);                       //perutaan siirto
        }catch(Exception e){    //jos paikka on epäsopiva, esim. varattu, heitetään poikkeus merkkiä laitettaessa
            if(omavuoro)tulos = -10;
            else tulos = 10;
        }
        return tulos;                       //kuinka hyvään lopputulokseen kys. sijainti johtaisi
    }
        
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
        
        public int kokeileSuunta(Lauta lauta, int j, int i, char suunta, int vari, int k, boolean omavuoro){
            int tulos;
            try{
                int uusip = lauta.siirra(j, i, suunta);                 //kokeillaan siirtää annettuun suuntaan
                    if(lauta.mylly(vari, uusip))    tulos = poisto(lauta, vari, k, 0, omavuoro);
                    else if(omavuoro)    tulos = vastaPuoliLaudalle(lauta, vari, k+1, 0);    //jos on oma vuoro kutsutaan vastapuolen siirtoa
                    else            tulos = omaSiirtoLaudalle(lauta, 3-vari, k+1, 0);     //jos on vastapuolen vuoro kutsutaan omaa siirtoa
                lauta.siirra(uusip/8, uusip%8, vastakohta(suunta));     //perutaan siirto
                }catch(Exception e){                //jos sijainti on epäsopiva, esim. varattu
                    if(omavuoro)    tulos = -10;
                    else    tulos = 10;
                    }
            return tulos;
        }
        
        public int poisto(Lauta lauta, int vari, int k, int pelaamatta, boolean omavuoro){
            int pal = 10;
            if(omavuoro) pal = -10;
            for(int i=0; i<24; i++){
                if(lauta.merkki(i/8, i%8)!=(3-vari) || lauta.mylly(3-vari, i))    continue;
                int tulos = kokeilePoistaa(lauta, i/8, i%8, vari, k, pelaamatta, omavuoro);
                if(omavuoro && tulos>pal) pal = tulos;
                if(!omavuoro && tulos<pal)  pal = tulos;
            }
            return pal;
        }
        
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
    
    public int arvioiTilanne(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 2;
        if(lauta.myllyja(3-vari))  return -2;
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(vari))  return 0;
        if(!lauta.voikoLiikkua(vari))   return -2;
        if(!lauta.voikoLiikkua(3-vari)) return 2;
        return 0;
    }
    
}
