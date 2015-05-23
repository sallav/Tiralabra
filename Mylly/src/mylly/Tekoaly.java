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
     * syvyys määrittelee kuinka syvälle pelipuuta generoidaan 
     * @param syvyys
     */
    public Tekoaly(int syvyys){
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
    /**
     * parasTyhjista -metodi tekee arvion parhaasta sijainnista pelilaudalla, johon 
     * pelaaja voi sijoittaa uuden merkin.  
     * @param lauta Lauta -luokan ilmentymä, johon siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @return paras sijainti (0-23) johon merkki kannattaa sijoittaa
     */
    public int parasTyhjista(Lauta lauta, int vari){      //kutsutaan kun kaikki napit ei vielä laudalla
        int paras = -10;        
        int parasp = -1;        //paras paikka mihin laittaa nappula
        for(int i=0; i<24; i++){
            try{
                lauta.laitaMerkki(i/8, i%8, vari);
                int tulos = vastaPuoli(lauta, vari);
                if(tulos>paras){
                    paras = tulos;
                    parasp = i;
                }
                lauta.poista(i/8, i%8, vari);
            }catch(Exception e){        //heittää poikkeuksen jos paikka ei tyhjä
                continue;               //jatketaan seuraavaan...
                }
        }
        return parasp;
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
        char parass = 'x';      //paras suunta siirrolle
        char[] suunnat = {'y', 'a', 'v', 'o'};  //ylös, alas, vasemmalle, oikealle
        for(int i=0; i<24; i++){
            if(lauta.merkki(i/8, i%8)==vari){   //jos paikassa on nappi, jota voidaan siirtää
                for(int j=0; j<4; j++){         //kokeillaan siirtää kaikkiin suuntiin
                    try{
                        int uusip = lauta.siirra(i/8, i%8, suunnat[j]); //paikka mihin on siirretty
                        int tulos = vastaPuoli(lauta, vari);
                        if(tulos>paras) {
                            paras = tulos;
                            parasn = i;
                            parass = suunnat[j];
                        }
                        lauta.siirra(uusip/8, uusip%8, vastakohta(suunnat[j])); //perutaan siirto
                    }catch(Exception e){
                        continue;
                    }
                }
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
    public int omaSiirto(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 1;
        if(lauta.myllyja(3-vari))  return -1;
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(vari))  return 0;
        if(!lauta.voikoLiikkua(vari))   return -1;
        if(!lauta.voikoLiikkua(3-vari)) return 1;
        
        int paras = -10;
        for(int i=0; i<24; i++){
            try{
                lauta.laitaMerkki(i/8, i%8, vari);
                int tulos = vastaPuoli(lauta, vari);
                if(tulos>paras) paras = tulos;
                lauta.poista(i/8, i%8, vari);
            }catch(Exception e){
                continue;
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
    public int vastaPuoli(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 1;
        if(lauta.myllyja(3-vari))  return -1;
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(vari))  return 0;
        if(!lauta.voikoLiikkua(vari))   return -1;
        if(!lauta.voikoLiikkua(3-vari)) return 1;        
        
        int huonoin = 10;
        for(int i=0; i<24; i++){
            try{
                lauta.laitaMerkki(i/8, i%8, 3-vari);
                int tulos = omaSiirto(lauta, vari);
                if(tulos<huonoin)   huonoin = tulos;
                lauta.poista(i/8, i%8, 3-vari);
            }catch(Exception e){
                continue;
            }
        }
        return huonoin;
    }
}
