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
        if(this.juuri==null) this.koko = 0;
        else this.juuri.setVanhempi(null);
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
    
    private boolean lisaaJuureen(Solmu solmu, int avain, Solmu juuri){
        if(juuri==this.juuri && this.juuri==null)    setJuuri(solmu);
        else{
            if(solmu==null || juuri==null)    return false;
            Solmu vanhempi = etsiVanhempi(avain, juuri);
            int vavain = vanhempi.getAvain();
            if(vanhempi==null || vavain==avain)  return false;
            if(avain<vavain)   vanhempi.setVasen(solmu);
            else if(avain>vavain)  vanhempi.setOikea(solmu);
            solmu.setVanhempi(vanhempi);
        }
        return true;
    }
    
    @Override
    public Solmu poista(int avain){
        Solmu p = etsi(avain, this.juuri);
        if(p!=null){
            poisto(p);
        }
        return p;
    }
    
    private void poisto(Solmu p){
        Solmu vanhempi = p.getVanhempi();
        Solmu vas = p.getVasen();
        Solmu oik = p.getOikea();
        irrota(p);    
        Solmu uusilapsi = oik;
        if(vas!=null) uusilapsi = vas;
            if(vanhempi==null && uusilapsi==null)   nollaaJuuri();
            else{ 
                if(vanhempi==null)    setJuuri(uusilapsi);
                else if(uusilapsi==null)    nollaaLapsi(vanhempi, p==vanhempi.getVasen());
                else    vanhemmanLapseksi(vanhempi, uusilapsi, p==vanhempi.getVasen());
                this.koko--;
                if(vas!=null && oik!=null) lisaaAlipuuhun(oik, vas);
            }
    }
    
    private void lisaaAlipuuhun(Solmu solmu, Solmu juuri){
        solmu.setVanhempi(null);
        int avain = solmu.getAvain();
        lisaaJuureen(solmu, avain, juuri);
    }
    
    private void irrota(Solmu p){
        p.setVanhempi(null);
        p.setOikea(null);
        p.setVasen(null);        
    }
    
    private void nollaaLapsi(Solmu vanhempi, boolean vasen){
        if(vasen)   vanhempi.setVasen(null);
        else    vanhempi.setOikea(null);
    }
    
    private void nollaaJuuri(){
        this.juuri = null;
        this.koko = 0;
    }
    
    public void vanhemmanLapseksi(Solmu vanhempi, Solmu lapsi, boolean vasemmalle){
        if(vasemmalle)   vanhempi.setVasen(lapsi);
        else    vanhempi.setOikea(lapsi);
        lapsi.setVanhempi(vanhempi);
    }
    
    @Override
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
        if(juuri==null) return null;
        int juurenavain = juuri.getAvain();
        if(juurenavain==avain)  return juuri;
        if(avain<juurenavain && juuri.getVasen()==null) return juuri;          //sopiva vanhempi
        else if(avain>juurenavain && juuri.getOikea()==null)   return juuri;   //sopiva vanhempi
        else if(avain<juurenavain)   return etsiVanhempi(avain, juuri.getVasen());  //vasempaan alipuuhun
        else if(avain>juurenavain)  return etsiVanhempi(avain, juuri.getOikea());   //oikeaan alipuuhun
        else return null;   //ei voida lisätä puuhun
    }
    
    @Override
    public int getKoko(){
        return this.koko;
    }
    
    @Override
    public Puu teeKopio(){
        Puu kopio = new SijaintiPuu2();
        Lista solmut = jalkijarjestys(); 
        ListaSolmu x = solmut.getEka();
        while(x!=null){
            Solmu kopioitava = x.getPuuSolmu();
            Solmu uusi = new Solmu(kopioitava.getAvain());
            kopio.lisaa(uusi);
            x = x.getSeuraava();
            }
        return kopio;
    }
    
    @Override
    public Lista esijarjestys(){
        Lista luvut = new Lista();
        jarjHelper(luvut, this.juuri, true);
        return luvut;
    }
    
    public Lista jalkijarjestys(){
        Lista luvut = new Lista();
        jarjHelper(luvut, this.juuri, false);
        return luvut;
    }
    
    private void jarjHelper(Lista luvut, Solmu juuri, boolean esi){
        if(juuri!=null){
            if(esi) luvut.lisaa(juuri);
            jarjHelper(luvut, juuri.getVasen(), esi);
            jarjHelper(luvut, juuri.getOikea(), esi);
            if(!esi) luvut.lisaa(juuri);
        }
    }
}
