/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

/**
 *
 * @author Käyttäjä
 */
public class Peli {
    Pelaaja eka;
    Pelaaja toka;
    Lauta lauta;
    
    Peli(Pelaaja eka, Pelaaja toka){
        this.eka = eka;
        this.toka = toka;
        this.lauta = new Lauta();
    }
    
    public int pelaa(){
        int nappeja = 9;
        
        while(nappeja>0){
            pelaaLaudalle(eka);
            pelaaLaudalle(toka);
            nappeja--;
        }
        while(lauta.voikoLiikkua(eka.vari()) && lauta.voikoLiikkua(toka.vari())){
            if(!lauta.voikoLiikkua(eka.vari()) || eka.laudalla(lauta)<3)     return 2;  //jos ei voi liikuttaa nappeja peli loppuu
                pelaaLaudalla(eka);
            if(!lauta.voikoLiikkua(toka.vari()) || toka.laudalla(lauta)<3)    return 1;  //jos ei voi liikuttaa peli loppuu
                pelaaLaudalla(toka);
        }
        
        if(eka.laudalla(lauta)>toka.laudalla(lauta)) return 1;  //eka voitti
        if(eka.laudalla(lauta)<toka.laudalla(lauta))  return 2; //toka voitti
        else return 0;                                          //tasapeli
    }
    
    public void pelaaLaudalle(Pelaaja pelaaja){
        int sij = pelaaja.siirraLaudalle(lauta);
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta);
    }
    
    public void pelaaLaudalla(Pelaaja pelaaja){
        int sij;
        if(pelaaja.laudalla(lauta)>3)   sij = pelaaja.siirraLaudalla(lauta);
        else sij = pelaaja.lenna(lauta);
        if(lauta.mylly(pelaaja.vari(), sij))    pelaaja.poistaLaudalta(lauta);
    }
    
    public String voittaja(int v){
        int vari = 0;
        if(v==1){
            vari = eka.vari();
        }if(v==2){
            vari = toka.vari();
        }
        if(vari==1) return "musta";
        if(vari==2) return "valkoinen";
        else    return "tasapeli";
    }
    
}
