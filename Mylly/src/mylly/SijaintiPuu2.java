/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Käyttäjä
 */
public class SijaintiPuu2 implements Puu{
    Solmu juuri;
    int koko;
    
    public SijaintiPuu2(int[] luvut){
        this.koko = 1;
        setJuuri(new Solmu(luvut[0]));
        for(int i=1; i<luvut.length; i++){
            lisaa(luvut[i]);
        }
    }
    
    public SijaintiPuu2(Solmu juuri){
        this.koko = 1;
        setJuuri(juuri);
    }
    
    public SijaintiPuu2(){
        this.koko = 0;
        this.juuri = null;
    }
    
    @Override
    public Solmu getJuuri(){
        return this.juuri;
    }
    
    public void setJuuri(Solmu solmu){
        this.juuri = solmu;
        solmu.setVanhempi(null);
    }
    
    @Override
    public boolean lisaa(int avain){
        Solmu solmu = new Solmu(avain);
        if(this.juuri==null){   //puu on tyhjä
            setJuuri(solmu);
            this.koko++;
        }
        else{
        boolean onnistuu = lisaaJuureen(solmu, avain, this.juuri);
            if(onnistuu)    this.koko++;
            else return false;
        }
        return true;
    }
    
    @Override
    public boolean lisaa(Solmu solmu){
        if(solmu==null) return false;
        if(this.juuri==null){   //puu on tyhjä
            setJuuri(solmu);
            this.koko++;
        }
        else{
        int avain = solmu.getAvain();
        boolean onnistuu = lisaaJuureen(solmu, avain, this.juuri);
            if(onnistuu)    this.koko++;
            else return false;
        }
        return true;
    }
    
    private boolean lisaaJuureen(Solmu usolmu, int uavain, Solmu juuri){
        if(usolmu==null)    return false;
        Solmu vanhempi = etsiVanhempi(uavain, juuri);
        if(vanhempi==null)  return false;
        if(uavain<vanhempi.getAvain())   vanhempi.setVasen(usolmu);
        else if(uavain>vanhempi.getAvain())  vanhempi.setOikea(usolmu);
        usolmu.setVanhempi(vanhempi);
        return true;
    }
    
    @Override
    public Solmu poista(int avain){
        Solmu p = etsi(avain, this.juuri);
        if(p!=null){
            Solmu vanhempi = p.getVanhempi();
            Solmu vas = p.getVasen();
            Solmu oik = p.getOikea();
            Solmu uusilapsi;
            if(vas!=null)   {
                uusilapsi = vas;
                if(oik!=null)  lisaaJuureen(oik, oik.getAvain(), vas);
            }
            else uusilapsi = oik; 
            if(vanhempi==null) setJuuri(uusilapsi);
            else if(p==vanhempi.getVasen()) vanhempi.setVasen(uusilapsi);
            else vanhempi.setOikea(uusilapsi);
            uusilapsi.setVanhempi(vanhempi);
            p.setVanhempi(null);
            this.koko--;
        }
        return p;
    }
    
    public Solmu etsi(int avain){
        return etsi(avain, this.juuri);
    }
    
    private Solmu etsi(int avain, Solmu juuri){
        if(juuri==null) return null;        //avainta ei löytynyt alipuusta
        int juurenavain = juuri.getAvain();
        if(avain<juurenavain) return etsi(avain, juuri.getVasen());        //etsitään vasemmasta alipuusta
        else if(avain>juurenavain) return etsi(avain, juuri.getOikea());   //etsitään oikeasta alipuusta
        else return juuri;      //jos etsitty avain on juuren avain palautetaan juuri
    }
    
    public Solmu etsiVanhempi(int avain, Solmu juuri){
        int juurenavain = juuri.getAvain();
        if(avain<juurenavain && juuri.getVasen()==null) return juuri;          //sopiva vanhempi
        else if(avain>juurenavain && juuri.getOikea()==null)   return juuri;   //sopiva vanhempi
        else if(avain<juurenavain)   return etsiVanhempi(avain, juuri.getVasen());
        else if(avain>juurenavain)  return etsiVanhempi(avain, juuri.getOikea());
        else return null;   //ei voida lisätä puuhun
    }
    
    @Override
    public int getKoko(){
        return this.koko;
    }
    
    @Override
    public Puu teeKopio(){
        Puu kopio = new SijaintiPuu2();
        for(int i=0; i<this.koko; i++){
            kopio.lisaa(etsi(i, this.juuri));
                }
        return kopio;
    }
}
