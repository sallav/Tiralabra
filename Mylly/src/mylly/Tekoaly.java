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
    int syvyys;
    
    public Tekoaly(int syvyys){
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
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
    
    public char vastakohta(char suunta){
        switch(suunta){
            case 'a': return 'y';
            case 'y': return 'a';
            case 'v': return 'o';
            case 'o': return 'v';
            default: return 'x';
        }
    }
    
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
