/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 *
 * @author Salla
 */
public class Perus implements Heuristiikka{
    
    @Override
    public int tilanneArvio(Lauta lauta, int vari, int pelaamatta){
        if(voikoLiikkua(lauta, vari)!=0) return voikoLiikkua(lauta, vari);
        
        
        return 0;
    }
    
    public int voikoLiikkua(Lauta lauta, int vari){
        if(!lauta.voikoLiikkua(vari) && !lauta.voikoLiikkua(3-vari));
        if(!lauta.voikoLiikkua(vari))   return -100;
        if(!lauta.voikoLiikkua(3-vari)) return 100; 
        else return 0;
    }
    
}
