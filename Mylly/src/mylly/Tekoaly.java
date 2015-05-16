/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 *
 * @author Käyttäjä
 */
public class Tekoaly {
    
    public int parasTyhjista(Lauta lauta, int vari){      //kutsutaan kun kaikki napit ei vielä laudalla
        ArrayList<Integer> tyhjat = lauta.tyhjia();         //kaikki mahdolliset sijainnit
        int paras = -10;
        int parasp = 0;
        for(int i=0; i<tyhjat.size(); i++){
            int tp = tyhjat.get(i);
            lauta.laitaMerkki(tp/8, tp%8, vari);
            int tulos = omaSiirto(lauta, vari);
            if(tulos>paras){
                paras = tulos;
                parasp = tp;
            }
            lauta.poista(tp/8, tp%8);
        }
        return parasp;
    }
    
    public int omaSiirto(Lauta lauta, int vari){
        if(lauta.myllyja(vari))    return 1;
        if(lauta.myllyja(3-vari))  return -1;
        if(!lauta.voikoLiikkua(vari))   return -1;
        if(!lauta.voikoLiikkua(3-vari)) return 1;

    }
    
    public int vastaPuoli(Lauta lauta){
        
    }
}
