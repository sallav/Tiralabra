/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 * Perus -luokka tarjoaa yksinkertaisen heuristiikan pelitilanteiden arvioimiseen
 * @author Salla
 */
public class Perus implements Heuristiikka{
    
    @Override
    public int tilanneArvio(Lauta lauta, int vari, int pelaamatta, int edel){
        if(pelaamatta==0 && voikoLiikkua(lauta, vari)!=1) return voikoLiikkua(lauta, vari); //jos jompikumpi ei voi liikkua->voitto tai häviö
        int arvo = syodyt(lauta, vari);                     //kuinka monta napeista syöty
            if(arvo==100 || arvo==-100) return arvo;        //voitto tai häviö
            else return myllyt(lauta, vari, edel, arvo);    //lasketaan lopullinen arvo tilanteelle
    }
    
    /**
     * voikoLiikkua -metodi tarkistaa voivatko pelaajat vielä tehdä siirtoja laudalla. 
     * Jos jompi kumpi tai molemmat pelaajista eivät voi enää liikkua, palauttaa metodi
     * arvion tilanteen edullisuudesta parametrina annetun väriselle pelaajalle. 100
     * tarkoittaa pelaajan voittoa kun vastapuoli ei voi enää liikkua. -100 tarkoittaa 
     * häviötä, kun pelaaja ei voi itse enää liikkua. Muuten metodi palauttaa arvon 1.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka mahdollisuuksia arvioidaan
     * @return arvio tilanteen edullisuudesta pelaajalle, jos jompi kumpi pelaajista ei
     * voi enää liikkua. Jos pelaajat voivat vielä liikkua metodi palauttaa arvon 1.
     */
    public int voikoLiikkua(Lauta lauta, int vari){
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(3-vari)) return 0;
        if(!lauta.voikoLiikkua(vari))   return -100;
        if(!lauta.voikoLiikkua(3-vari)) return 100; 
        else return 1;
    }
    
    public int syodyt(Lauta lauta, int vari){
        if(lauta.syoty(vari)>6)     return -100;
        if(lauta.syoty(3-vari)>6)   return 100;
        else    return (lauta.syoty(3-vari)-lauta.syoty(vari))*10;
    }
    
    public int myllyt(Lauta lauta, int vari, int edel, int arvo){
        if(lauta.mylly(vari, edel)){
            arvo = arvo + (lauta.myllyja(vari) - 1 - lauta.myllyja(3-vari))*3 + 10;
        }
        if(lauta.mylly(3-vari, edel)){
            arvo = arvo + (lauta.myllyja(vari) - lauta.myllyja(3-vari) + 1)*3 - 10;
        }
        else    arvo = arvo + (lauta.myllyja(vari)-lauta.myllyja(3-vari))*3;
        arvo = arvo + (lauta.melkeinMyllyja(vari)-lauta.melkeinMyllyja(3-vari));
        return arvo;
    }
}
