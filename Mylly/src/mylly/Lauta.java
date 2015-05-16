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
public class Lauta {
    int[][] lauta;
    public int mustia;
    public int valkoisia;
    
    public Lauta(){
        this.lauta = new int[3][8];
        this.mustia = 0;
        this.valkoisia = 0;
    }
    
    public int[][] getLauta(){
        int[][] kopio = this.lauta;
        return kopio;
    }
    
    public void laitaMerkki(int j, int i, int vari){
        this.lauta[j][i] = vari;
        if(vari==1) mustia++;
        if(vari==2) valkoisia++;
    }
    
    public void poista(int j, int i){
        if(this.lauta[j][i]==1) mustia--;
        if(this.lauta[j][i]==2) valkoisia--;
        this.lauta[j][i] = 0;
    }
    
    public int merkki(int j, int i){
        if(this.lauta[j][i]==1) return 1;
        if(this.lauta[j][i]==2) return 2;  
        else return 0;
    }
    
    public int montakoNappia(int vari) throws Exception{
        if(vari==1) return mustia;
        if(vari==2) return valkoisia;
        else throw new Exception();
    }
    
    public ArrayList<Integer> sijainnit(int vari){
        ArrayList<Integer> paikat = new ArrayList<Integer>();
        for(int j=0; j<this.lauta.length; j++){
            for(int i=0; i<this.lauta[0].length; i++) {
                if (this.lauta[j][i] == vari)   paikat.add((8*j)+i);
            }
        }
        return paikat;
    }
    
    public ArrayList<Integer> tyhjia(){
        ArrayList<Integer> paikat = new ArrayList<Integer>();
        for(int j=0; j<this.lauta.length; j++){
            for(int i=0; i<this.lauta[0].length; i++) {
                if (this.lauta[j][i]!=1 && this.lauta[j][i]!=2)   paikat.add((8*j)+i);
            }
        }
        return paikat;
    }
    
        public boolean voikoLiikkua(int vari){
        ArrayList<Integer> merkit = sijainnit(vari);
            for(int k=0; k<merkit.size(); k++){
                int paikka = merkit.get(k);
                int j = paikka/8;
                int i = paikka%8;
                if(merkki(j, vas(i))==0 || merkki(j, oik(i))==0)  return true;
                if(j==0 || j==1){
                    if(merkki(j+1, i)==0) return true;
                }if(j==1 || j==2){
                    if(merkki(j-1, i)==0) return true;
                }
            }
        return false;
    }
    
    public boolean myllyja(int vari){
        for(int i=0; i<24; i++){
            if(this.lauta[i/8][i%8]==vari && mylly(vari, i))   return true;
        }
        return false;
    }
    
    public boolean mylly(int vari, int paikka){
        int j = paikka/8;   //rivi
        int i = paikka%8;   //sarake ts. paikka rivillä
        
            if(i%2==0){     //kulmapaikka
                if(lauta[j][vas(i)]==vari && lauta[j][vvas(i)]==vari)   return true;
                if(lauta[j][oik(i)]==vari && lauta[j][ooik(i)]==vari)   return true;
            }  
            else{     //sivupaikka
                if(lauta[j][vas(i)]==vari && lauta[j][oik(i)]==vari)    return true;
                if(j==0 && lauta[j+1][i]==vari && lauta[j+2][i]==vari)    return true;
                if(j==1 && lauta[j-1][i]==vari && lauta[j+1][i]==vari)    return true;
                if(j==2 && lauta[j-1][i]==vari && lauta[j-2][i]==vari)    return true;
            }
            return false;
    }
    
    public int vas(int i){
        if(i==0)    return 7;
        else        return i-1;
    }
    
    public int vvas(int i){
        if(i==0)    return 6;
        if(i==1)    return 7;
        else        return i-2;
    }
    
    public int oik(int i){
        if(i==7)    return 0;
        else        return i+1;
    }
    
    public int ooik(int i){
        if(i==7)    return 1;
        if(i==6)    return 0;
        else        return i+2;
    }
}
