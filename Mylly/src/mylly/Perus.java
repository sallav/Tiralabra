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
    public int tilanneArvio(Lauta lauta, int vari, int pelaamatta){
        int arvo = voikoLiikkua(lauta, vari);
        if(pelaamatta==0 && arvo!=1) return arvo; //jos jompikumpi ei voi liikkua->voitto tai häviö
        arvo = syodyt(lauta, vari);                     //kuinka monta napeista syöty
            if(arvo==100 || arvo==-100) return arvo;        //voitto tai häviö
            else return myllyt(lauta, vari, arvo);    //lasketaan lopullinen arvo tilanteelle
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
    
    /**
     * Jos vastustajan nappeja on syöty seitsemän, palauttaa metodi arvon 100. Jos omia nappeja on
     * syöty seitsemän, palauttaa metodi arvon -100. Muussa tapauksessa metodi palauttaa vastustajan
     * napeista syötyjen määrästä vähennetyn omien syötyjen pelinappien määrän kerrottuna kymmenellä. 
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @return pelitilanteen arvo syötyjen nappien määrä huomioituna
     */
    public int syodyt(Lauta lauta, int vari){
        if(lauta.syoty(vari)>6)     return -100;
        if(lauta.syoty(3-vari)>6)   return 100;
        else    return (lauta.syoty(3-vari)-lauta.syoty(vari))*10;
    }
    
    /**
     * myllyt -metodi saa parametrinaan pelilaudan arvon syödyt napit huomioituna ja lisää 
     * tai vähentää siihen arvoa pelilaudalla olevat pelaajan ja vastapuolen pelaajan myllyt 
     * sekä melkein myllyt huomioiden. Myllyn arvo on 3, yhdellä napilla vajaan myllyn arvo 2, 
     * jos kolmas paikka ei ole vastapuolen merkin "blokkaama" tai 1, jos vastapuoli on onnistunut
     * sen blokkaamaaan. Myllyt -metodi palauttaa pelilaudan lopullisen arvon.   
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari Pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan
     * @param edel viimeinen siirto
     * @param arvo pelilaudalle laskettu arvo syödyt napit huomioiden
     * @return pelilaudan lopullinen arvo
     */
    public int myllyt(Lauta lauta, int vari, int arvo){
        arvo = arvo + (lauta.myllyja(vari)-lauta.myllyja(3-vari))*3;    
        Lista omat = lauta.melkeinMyllyja(vari);            //sijainnit, jotka täyttäämällä pelaaja saisi myllyn
        Lista vastap = lauta.melkeinMyllyja(3-vari);        //sijainnit, jotka täyttämällä vastapuoli saisi myllyn
        int vastapblokit = blokatut(omat, lauta, vari);     //vastapuolen blokkaamat myllyt
        int omatblokit = blokatut(vastap, lauta, 3-vari);   //omat blokit
        int omatmm = omat.getKoko()*2 - vastapblokit;
        int vastapmm = vastap.getKoko()*2 - omatblokit; 
        arvo = arvo + omatmm - vastapmm + omatblokit;
        return arvo;
    }
    
    /**
     * blokatut -metodi laskee kuinka moni parametrina annetun listan sijainneista on vastapuolen
     * blokkaama, eli niissä on vastapuolen merkki.
     * @param melkeinmyllyt Lista sijainneista, jotka täyttämällä pelaaja saisi myllyn
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari Pelaajan väri(1=musta, 2=valkoinen), jonka mahdollisuuksia myllyyn arvioidaan
     * @return niiden sijaintien määrä, joissa oleva mahdollisuus myllyyn on vastapuolen estämä
     */
    public int blokatut(Lista melkeinmyllyt, Lauta lauta, int vari){
        ListaSolmu x = melkeinmyllyt.getEka();
        int blokattu = 0;
        while(x!=null){
            int paikka = x.getAvain();
            if(paikka<0 || paikka>23)   System.out.println("Virhe: paikka " + paikka);
            else if(lauta.merkki(paikka/8, paikka%8)==3-vari)    blokattu++;
            x = x.getSeuraava();
        }
        return blokattu;
    }
}
