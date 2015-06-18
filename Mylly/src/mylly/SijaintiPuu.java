/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.ArrayList;

/**
 * Puu -luokka tarjoaa toteutuksen binäärihakupuulle, johon voi tallettaa pelilaudan
 * paikkoja vastaavia sijainteja sisältäviä solmuja
 * @author Salla
 */
public class SijaintiPuu implements Puu{
    Solmu juuri;
    int koko;
    
    /**
     * Puu -luokan konstruktori rakentaa binäärihakupuun, joka sisältää solmuissa 
     * avaiminaan parametrina annetun taulukon luvut ja asettaa juureksi taulukon
     * ensimmäisen luvun
     * @param luvut taulukko, joka sisältää puun solmuihin laitettavat luvut
     */
    public SijaintiPuu(int[] luvut){
        this.juuri = new Solmu(luvut[0]);
        this.koko = 1;
        for(int i=1; i<luvut.length; i++){
            lisaa(luvut[i]);
        }
    }
    
    /**
     * Puu -luokan konstruktori alustaa puun juureksi parametrina saamansa solmun
     * @param juuri Solmu luokan ilmentymä
     */
    public SijaintiPuu(Solmu juuri, int koko){
        this.koko = koko;
        this.juuri = juuri;
    }
    
    /**
     * Puu luokan konstruktori asettaa puun juureksi nullin
     */
    public SijaintiPuu(){
        this.koko = 0;
        this.juuri = null;
    }
    
    public SijaintiPuu(Lista solmut){
        ListaSolmu x = solmut.getEka();
        this.juuri = new Solmu(x.getPuuSolmu().getAvain());
        while(x.getSeuraava()!=null){
            x = x.getSeuraava();
            lisaa(x.getPuuSolmu().getAvain());
        }
    }
    
    /**
     * getJuuri -metodi palauttaa puun juuren
     * @return Solmu -luokan olio, joka on puun juurena
     */
    public Solmu getJuuri(){
        return this.juuri;
    }
    
    /**
     * setJuuri -metodi asettaa puun juureksi parametrina annetun solmun sekä
     * asettaa sen vanhemman arvoksi null
     * @param uusijuuri Solmu -luokan ilmentymä josta tulee uusi juuri
     */
    public void setJuuri(Solmu uusijuuri){
        this.juuri = uusijuuri;
        this.juuri.vanhempi = null;
    }
    
    /**
     * lisaa -metodi luo ja lisää puuhun uuden Solmu -luokan olion, jonka
     * avaimena on parametrina saatu luku, jos tätä lukua ei ole puussa ennestään
     * ja palauttaa true. Jos parametrina annettu luku löytyy puun solmuista, ei
     * sitä lisätä ja metodi palauttaa false.
     * @param avain solmuun avaimeksi lisättävä kokonaisluku
     * @return true jos solmu lisättiin puuhun, false jos solmua ei lisätty
     */
    public boolean lisaa(int avain){
        Solmu uusi = new Solmu(avain);
        return lisaa(uusi, avain, this.juuri);
    }
    
    /**
     * lisaa -metodi lisää parametrina annetun Solmu -olion puuhun, olettaen, että lisättävän
     * solmun sisältämää avainta ei löydy muista puun solmuista
     * @param lisa Solmu -luokan ilmentymä
     * @return true jos solmu lisättiin puuhun, false jos puusta löytyy jo solmu samalla avaimella
     */
    public boolean lisaa(Solmu lisa){
        return lisaa(lisa, lisa.getAvain(), this.juuri);
    }
    /**
     * lisaa -metodi lisää parametrina annetun solmun parametrina annetusta juuresta alkavaan alipuuhun
     * ja palauttaa true jos lisäys onnistui. Jos parametrina annetun solmun avainta vastaava
     * kokonaisluku löytyy jo alipuusta, ei solmua lisätä ja metodi palauttaa false.
     * @param lisa puuhun lisättävä Solmu -luokan olio
     * @param avain puuhun lisättävän solmun avain (kokonaisluku)
     * @param juuri Solmu -luokan olio, josta alkavaan alipuuhun toinen solmu lisätään
     * @return true jos lisäys onnistui, false jos avain löytyy jo puun solmuista ja siten
     * lisäystä ei tehdä
     */
    private boolean lisaa(Solmu lisa, int avain, Solmu juuri){
        if(this.juuri==null){   //jos puu on tyhjä laitetaan juureksi         
            setJuuri(lisa);
            this.koko = 1;
        }      
        Solmu vanhempi = sopivaVanhempi(lisa, juuri);
        boolean lisatty = asetaLapsi(vanhempi, lisa, avain);
        if(!lisatty)    return false;       //jos ei onnistu palautetaan false
        this.koko++;        //jos onnistuu kasvatetaan puun kokoa
        return true;        //ja palautetaan true
    }
    
    /**
     * asetaLapsi -metodi asettaa parametrina annetun lapsisolmun parametrina annetun
     * vanhempisolmun vasemmaksi tai oikeaksi lapseksi sen mukaan onko sen avain suurempi
     * vai pienempi kuin vanhemman avain. Suurempi avain lisätään oikealle puolelle ja 
     * pienempi vasemmalle. Jos avaimet ovat yhtäsuuret lasta ei lisätä ja metodi palauttaa
     * false, muuten palautetaan true.
     * @param vanhempi Solmu -luokan ilmentymä jonka lapseksi lapsi lisätään
     * @param lapsi Solmu -luokan ilmentymä, joka lisätään vanhemman lapseksi
     * @param lapsenavain kokonaisluku, joka on lapsen avain
     * @return true jos lisäys tehtiin, false jos avain on sama kuin vanhemman, jolloin 
     * lisäystä ei tehdä
     */
    private boolean asetaLapsi(Solmu vanhempi, Solmu lapsi, int lapsenavain){
        if(vanhempi!=null){
            int vavain = vanhempi.getAvain();                       //vanhemman avain
            if(lapsenavain>vavain) vanhempi.setOikea(lapsi);        //suurempi lisätään oikealle puolelle
            else if(lapsenavain<vavain) vanhempi.setVasen(lapsi);   //pienempi lisätään vasemmalle puolelle
            else return false;                          //jos avain on sama kuin vanhemmalla ei lisätä puuhun
            lapsi.setVanhempi(vanhempi);                //vanhempi lapsen vanhemmaksi
            return true;
        }
        return false;           //jos vanhempi null ei voida lisätä puuhun
    }
    
    /**
     * poista -metodi poistaa puusta parametrina annetun avaimen sisältävän Solmu -luokan
     * olion ja asettaa sen lapset sen vanhemmasta alkavaan alipuuhun sille puolelle, josta
     * solmu eli lapsi poistettiin. Metodi palauttaa poistetun Solmu -luokan olion.
     * @param avain poistettavan Solmu -luokan olion avain
     * @return  poistettu Solmu -luokan olio
     */
    public Solmu poista(int avain){
        Solmu p = etsi(avain);
        if(p!=null){
            Solmu vas = p.getVasen();       //poistettavan vasen lapsi
            Solmu oik = p.getOikea();       //poistettavan oikea lapsi
            Solmu vanh = p.getVanhempi();   //poistettavan vanhempi
            if(vanh!=null && vanh.getOikea()==p) vanhemmanLapseksi(vanh, vas, oik, false);  //jos poistettava on vanhempansa oikea lapsi
            else vanhemmanLapseksi(vanh, vas, oik, true);
            if(oik!=null) this.koko--;      //jos oikea ei null tehdään yksi lisäys puuhun joten kokoa kasvatetaan
            this.koko--;                    //kokoa vähennetään
        }
        return p;
    }
    
    /**
     * vanhemmanLapseksi -metodi asettaa parametrina annetun isov solmun vasemmaksi lapseksi 
     * parametrina annetun vasen solmun, jos parametri vasenlapsi on true ja lisää oikea solmun
     * vasen solmusta alkavaan alipuuhun. Jos parametri vasenlapsi on false metodiasettaa 
     * isov solmun oikeaksi lapseksi parametrina annetun oikea solmun ja lisää vasen solmun 
     * siitä alkavaan alipuuhun. 
     * @param isov solmu josta alkavaan alipuuhun vasen ja oikea lisätään
     * @param vasen Solmu -luokan ilmentymä
     * @param oikea Solmu -luokan ilmentymä
     * @param vasenlapsi true jos poistettu solmu oli isov solmun vasen lapsi, muuten false
     */
    public void vanhemmanLapseksi(Solmu isov, Solmu vasen, Solmu oikea, boolean vasenlapsi){
        Solmu lapsi;
        if(vasen!=null) lapsi = vasen;
        else lapsi = oikea;
        if(lapsi!=null){
            if(vasenlapsi){         //jos poistettu lapsi oli vanhempansa vasen lapsi
                if (isov!=null) isov.setVasen(lapsi);   //poistetun vasemman isovanhemman vasemmaksi lapseksi
                else this.juuri = lapsi;                //jos poistettava oli juuri
            }else   isov.setOikea(lapsi);   //poistetun oikean isovanhemman oikeaksi lapseksi
            lapsi.setVanhempi(isov);
            if(oikea!=null && lapsi!=oikea)    lisaa(oikea, oikea.getAvain(), lapsi);   //oikea lisätään toisesta lapsesta alkavaan alipuuhun
        }
    }
    
    /**
     * sopivaVanhempi -metodi etsii binäärihakupuusta kohdan, johon parametrina annettu 
     * uusi solmu voidaan lisätä
     * @param uusi Solmu -luokan ilmentymä, joka halutaan lisätä puuhun 
     * @param juuri Solmu -luokan ilmentymä, josta alkavaan alipuuhun uusi solmu
     * halutaan lisätä
     * @return Solmu -luokan ilmentymä, jonka lapseksi uuden solmun voi lisätä 
     */
    public Solmu sopivaVanhempi(Solmu uusi, Solmu juuri){
        if(uusi==null)  return null;
        int avain = uusi.getAvain();    //uuden solmun avain
        Solmu j = juuri;
        Solmu v = null;
        while(j != null){       //kunnes saavutetaan sopiva kohta
            v = j;              //seuraavan j:n vanhempi
            j = lapsi(avain, j);    //seuraava j, eli edellisen lapsi
            if(v==j)    return j;   //tai jos sama avain palautetaan edellinen j
        }
        return v;       //palautetaan halutun kohdan vanhempi
    }
    
    /**
     * etsi -metodi etsii ja palauttaa puusta Solmu -luokan ilmentymän, jonka avain
     * on parametrina annettu kokonaisluku.
     * @param avain etsittävä avain 
     * @return Solmu -luokan ilmentymä, jonka avain on etsittävä avain tai null, jos 
     * avainta ei löydy puusta
     */
    public Solmu etsi(int avain){
        Solmu x = this.juuri;
        while(x!=null && x!=lapsi(avain, x)){
            x = lapsi(avain, x);
        }
        return x;
    }
    
    /**
     * lapsi -metodi palauttaa avainta lähempänä olevan parametrina annetun juuri -solmun lapsen
     * eli oikean lapsen jos avain on suurempi kuin juuren avain tai vasemman lapsen jos avain 
     * on pienempi kuin juuren avain. Jos juuren avain on sama kuin parametrina annettu avain
     * palautetaan juuri itse
     * @param avain avain johon juuren avainta verrataan
     * @param juuri solmu jonka avainta verrataan annettuun avaimeen
     * @return avainta lähinnä oleva solmu juuresta ja sen lapsista
     */
    private Solmu lapsi(int avain, Solmu juuri){
        int javain = juuri.getAvain();
        if(avain>javain)    return juuri.getOikea();        //jos juuri on avainta pienempi palautetaan oikea
        else if(avain<javain)   return juuri.getVasen();    //jos juuri on avainta isompi palautetaan vasen
        else return juuri;                                  //jos juuren avain on sama palautetaan juuri
    }
    
    /**
     * getKoko -metodi palauttaa puun sen hetkisen koon
     * @return puun koko eli solmujen määrä
     */
    public int getKoko(){
        return this.koko;
    }
    
    public Puu teeKopio(){
        Solmu juuri = kopioiPuu(this.juuri);
        return new SijaintiPuu(juuri, this.koko);
    }
    
    public Solmu kopioiPuu(Solmu juuri){
        if(juuri!=null){
            Solmu v = kopioiPuu(juuri.getVasen());
            Solmu o = kopioiPuu(juuri.getVasen());
            Solmu uusijuuri = new Solmu(juuri.getAvain());
            uusijuuri.setArvo(juuri.getArvo());
            uusijuuri.setVasen(v);
            uusijuuri.setOikea(o);
            return uusijuuri;
        }
        return null;
    }
    
    public Lista esijarjestys(){
        Lista luvut = new Lista();
        esijarjHelper(luvut, this.juuri);
        return luvut;
    }
    
    private void esijarjHelper(Lista luvut, Solmu juuri){
        if(juuri!=null){
        luvut.lisaa(juuri);
        esijarjHelper(luvut, juuri.getVasen());
        esijarjHelper(luvut, juuri.getOikea());
        }
    }
}
