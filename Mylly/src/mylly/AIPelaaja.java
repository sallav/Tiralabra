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
public class AIPelaaja implements Pelaaja{
    int vari;
    Tekoaly aly;
    
    public AIPelaaja(int vari, Tekoaly aly){
        this.vari = vari;
        this.aly = aly;
    }
    
    @Override
    public int vari(){
        return this.vari;
    }
    
    @Override
    public int siirraLaudalle(Lauta lauta){
        int paikka = aly.parasTyhjista(lauta, this.vari);
        try{
            lauta.laitaMerkki(paikka/8, paikka%8, this.vari);   //ei periaatteessa pitäisi tulla virhettä
        }catch(Exception e){
            
        }
        return paikka;
    }
    
    @Override
    public int siirraLaudalla(Lauta lauta){
        String[] siirto = aly.parasViereisista(lauta, this.vari).split(" ");
        int uusip, paikka;
        try{
            paikka = Integer.valueOf(siirto[0]);
            uusip = lauta.siirra(paikka/8, paikka%8, siirto[1].charAt(0));  //ei pitäisi heittää poikkeusta
        }catch(Exception e){
            uusip = 0; //??
        }
        return uusip;
    }
    
    @Override
    public int lenna(Lauta lauta){
        
    }
    
    @Override
    public int laudalla(Lauta lauta){
        try{
        return lauta.montakoNappia(this.vari);
        }catch(Exception e){
            return 0;
        }
    }
    
    @Override
    public void poistaLaudalta(Lauta lauta){
        
    }
}
