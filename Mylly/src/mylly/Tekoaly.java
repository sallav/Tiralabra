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
    
    /**
     * Luokan Tekoaly konstruktori algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden
     * @param syvyys kuinka pitkälle pelipuula generoidaan
     */
    public Tekoaly(int syvyys){
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
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
        int parasn = -1;        //sijainti, jossa paras nappula, jota siirtää
        char parass = 'x';      //paras suunta, eli suunta mihin kannattaa siirtää nappulaa
        for(int i=0; i<24; i++){
            String[] tulos = kokeileSiirtoa(lauta, i/8, 1%8, vari, 1, true).split(" ");
                        if(tulos[0]>paras) {
                            paras = tulos[0];
                            parasn = i;
                            parass = tulos[1];
                        }
        }
        return parasn + " " + parass;
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
    public int omaSiirto(Lauta lauta, int vari, int k, int pelaamatta){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);        
        int paras = -10;
        for(int i=0; i<24; i++){
            int tulos;
                if(pelaamatta>0) tulos = kokeileSijainti(lauta, i/8, i%8, vari, k, true, pelaamatta);
                else tulos = kokeileSiirtoa(lauta, i/8, i%8, vari, k, true);    
                if(tulos>paras) paras = tulos;
        }
        return paras;
    }
    
    public int omaSiirto2(Lauta lauta, int vari, int k){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);
        
        int paras = -10;
        char[] suunnat = {'y', 'a', 'v', 'o'};  //ylös, alas, vasemmalle, oikealle
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)==vari){
                for(int j=0; j<4; j++){
                    try{
                        int uusip = lauta.siirra(i/8, i%8, suunnat[j]);
                        int tulos = vastaPuoli2(lauta, vari, k+1);
                        if(tulos>paras)   paras = tulos;
                        lauta.siirra(uusip/8, uusip%8, vastakohta(suunnat[j]));     //perutaan siirto
                    }catch(Exception e){
                        continue;
                    }
                }
            }
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
    public int vastaPuoli(Lauta lauta, int vari, int k, int pelaamatta){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);        //halutulla syvyydellä tehdään arvio tilanteen hyvyydestä
        int huonoin = 10;
        for(int i=0; i<24; i++){                    //kokeillaan asettaa merkki jokaiseen laudan paikkaan
            int tulos;
                if(pelaamatta>0) tulos = kokeileSijainti(lauta, i/8, i%8, 3-vari, k, false, pelaamatta); //lasketaan tulos mihin kyseinen siirto johtaisi
                else tulos = kokeileSiirtoa(lauta, i/8, i%8, 3-vari, k, false);   
                if(tulos<huonoin)   huonoin = tulos;    //jos tulos huonompi kuin aiemmat, pistetään se muistiin
        }
        return huonoin;         //palautetaan huonoin mahdollinen lopputulos
    }
    
    public int vastaPuoli2(Lauta lauta, int vari, int k){
        if(k==syvyys)   return arvioiTilanne(lauta, vari);
        
        int huonoin = 10;
        char[] suunnat = {'y', 'a', 'v', 'o'};  //ylös, alas, vasemmalle, oikealle
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)==vari){
                for(int j=0; j<4; j++){
                    try{
                        int uusip = lauta.siirra(i/8, i%8, suunnat[j]);
                        int tulos = omaSiirto2(lauta, vari, k+1);
                        if(tulos<huonoin)   huonoin = tulos;
                        lauta.siirra(uusip/8, uusip%8, vastakohta(suunnat[j]));     //perutaan siirto
                    }catch(Exception e){
                        continue;
                    }
                }
            }
        }
        
        return huonoin;
    }
    
        public int kokeileSijainti(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta){
        int tulos;
        try{
            lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen merkki
            if(omavuoro) tulos = vastaPuoli(lauta, vari, k+1, pelaamatta-1);   //jos oma vuoro kutsutaan vastapuolen metodia
            else    tulos = omaSiirto(lauta, vari, k+1, pelaamatta);    //jos vastapuolen vuoro kutsutaan oman värin metodia
            lauta.poista(j, i, vari);                       //perutaan siirto
        }catch(Exception e){    //jos paikka on epäsopiva, esim. varattu, heitetään poikkeus merkkiä laitettaessa
            if(omavuoro)tulos = -10;
            else tulos = 10;
        }
        return tulos;                       //kuinka hyvään lopputulokseen kys. sijainti johtaisi
    }
        
        public String kokeileSiirtoa(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro){
            String tulos;
            if(omavuoro)    tulos = -10;
            else            tulos = 10;
            if(lauta.merkki(j, i)==vari){
                char[] suunnat = {'y', 'a', 'v', 'o'};
                for(int l=0; l<4; l++){
                   int suunnantulos = kokeileSuunta(lauta, j, i, suunnat[l], vari, k, omavuoro);
                    if(omavuoro)    tulos = Math.max(tulos, suunnantulos);
                    else    tulos = Math.min(tulos, suunnantulos);
                }
            }
            return tulos;
        }
        
        public int kokeileSuunta(Lauta lauta, int j, int i, char suunta, int vari, int k, int omavuoro){
            int tulos;
            try{
                int uusip = lauta.siirra(j, i, suunta);
                    if(omavuoro)    tulos = vastaPuoli(lauta, vari, k+1, 0);
                    else            tulos = omaSiirto(lauta, vari, k+1, 0);
                lauta.siirra(uusip/8, uusip%8, vastakohta(suunnat[l]));
                }catch(Exception e){
                    if(omavuoro)    tulos = -10
                    else    tulos = 10;
                    }
            return tulos;
        }
    
    public int arvioiTilanne(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 2;
        if(lauta.myllyja(3-vari))  return -2;
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(vari))  return 0;
        if(!lauta.voikoLiikkua(vari))   return -2;
        if(!lauta.voikoLiikkua(3-vari)) return 2;
    }
    
}
