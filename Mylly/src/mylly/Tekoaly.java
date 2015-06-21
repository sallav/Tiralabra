/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;
import java.util.*;

/**
 * Tekoaly -luokka toteuttaa tekoälyn Myllyn pelaamista varten, jota AIPelaaja -luokan oliot
 * voivat hyödyntää tedessään siirtoja pelilaudalle.
 * @author Salla
 */
public class Tekoaly {
    int syvyys;
    char[] suunnat = {'y', 'a', 'v', 'o'};
    Heuristiikka heuristiikka;
    int alpha;
    int beta;
    
    /**
     * Luokan Tekoaly konstruktori alustaa algortimin generointisyvyydeksi parametrinaan
     * saamansa syvyyden sekä asettaa heuristiikaksi Heuristiikka -rajapinnan toteuttavan 
     * olion.
     * @param h Heuristiikka -rajapinnan toteuttava olio, joka määrittelee pelin arviointiheuristiikan
     * @param syvyys kuinka pitkälle pelipuuta generoidaan ts. mille rekursiotasolle edetään  
     */
    public Tekoaly(Heuristiikka h, int syvyys){
        this.heuristiikka = h;
        this.syvyys = syvyys;       //kuinka pitkälle puuta generoidaan;
    }
    
    /**
     * setAlphaBeta asettaa alphalle ja betalle alkuarvot
     */
    
    private void setAlphaBeta(){
        this.alpha = -1000;
        this.beta = 1000;
    }
    
    /**
     * getSyvyys palauttaa syvyyden johon pelipuuta generoidaan tekoälyssä
     * @return pelipuun generointisyvyys
     */
    public int getSyvyys(){
        return this.syvyys;
    }
    
    /**
     * parasTyhjista -metodi tekee arvion parhaasta sijainnista pelilaudalla, johon 
     * pelaaja voi sijoittaa uuden merkin.  
     * @param lauta Lauta -luokan ilmentymä, johon siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @param pelaamatta kuinka monta nappia pelaajilla on laittamatta laudalle
     * @return Solmu, jonka avaimena on paras sijainti (0-23) johon merkki kannattaisi sijoittaa
     */
    public Solmu parasTyhjista(Lauta lauta, int vari, int pelaamatta){      //kutsutaan kun kaikki napit ei vielä laudalla
        Puu tyhjat = lauta.getTyhjat().teeKopio();     //laudalla olevat tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        setAlphaBeta();                     //alphalle ja betalle alkuarvot
        Lista parhaat = listaaParhaat(1, juuri, lauta, vari, 0, pelaamatta);
        return jokuHyvaSijainti(parhaat);            //palautetaan paikka mihin siirtäminen johtaisi parhaaseen lopputulokseen pelaajan kannalta
    }
    
    /**
     * parasSiirto -metodi tekee arvion siitä, mitä jo laudalla olevista merkeistä
     * pelaajan kannattaa liikuttaa ja mihin suuntaan. Metodi palauttaa Solmu -luokan
     * ilmentymän, jonka avaimena on sijainti, jossa olevaa nappulaa kannataisi siirtää
     * (luku 0-23) sekä suunta muuttujana sitä suuntaa edustava kirjain(v=vasempaan, o=oikeaan, 
     * a=alas, y=ylös), johon nappia kannattaii siirtää.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari pelaajan väri(1=musta, 2=valkoinen) jonka siirtoa arvioidaan
     * @return Solmu, jonka avain on sijainti, missä olevaa merkkiä kannattaa siirtää(0-23)
     * ja jonka suunnaksi on asetettu suunta, johon merkkiä kannattaa liikuttaa
     */
    public Solmu parasSiirto(Lauta lauta, int vari){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getMustat().teeKopio();
        if(vari==2) merkit = lauta.getValkoiset().teeKopio();
        Solmu juuri = merkit.getJuuri();
        setAlphaBeta();                 //alphalle ja betalle alkuarvot
        Lista parhaat = listaaParhaat(2, juuri, lauta, vari, 0, 0);
        return jokuHyvaSijainti(parhaat);
    }
    
    /**
     * parasPoisto -metodi tekee arvion siitä mikä vastustajan merkeistä kannattaisi 
     * poistaa tilanteessa, jossa pelaaja on saanut myllyn.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari myllyn saaneen pelaajan väri, eli pelivuorossa olevan pelaajan väri
     * @param pelaamatta kuinka monta pelinappulaa pelaajilla on siirtämättä laudalle
     * @return Solmu, jonka avain on sijainti (0-23) mistä vastustajan pelimerkki kannattaa poistaa
     */
    public Solmu parasPoisto(Lauta lauta, int vari, int pelaamatta) {
        Puu merkit = null;
        if(vari==1) merkit = lauta.getValkoiset().teeKopio();
        if(vari==2) merkit = lauta.getMustat().teeKopio();
        Solmu juuri = merkit.getJuuri();
        setAlphaBeta();                     //asetetaan alphalle ja betalle arvot
        Lista parhaat = listaaParhaat(3, juuri, lauta, vari, 0, pelaamatta);
        return jokuHyvaSijainti(parhaat);              //mikä merkki kannattaa poistaa (ts. merkin sijaintipaikka)
    }
    
    /**
     * parasLento -metodi tekee arvion siitä, mitä merkkiä pelaajan kannattaisi siirtää ja
     * mihin sijaintiin, kun pelaajalla on jäljellä enää kolme merkkiä laudalla ja hän voi
     * lentää, eli liikkua mihin tahansa sijaintiin laudalla, millä tahansa laudalla olevalla
     * merkillään.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan
     * @return Solmu, jonka avain on sijainti laudalla, jossa olevaa merkkiä pelaajan kannattaa
     * siirtää, ja jonka kohde -muuttujaan on asetettu solmu, jonka avaimena olevaan sijaintiin 
     * merkkiä kannattaisi liikuttaa
     */
    public Solmu parasLento(Lauta lauta, int vari){
        Lista merkit = null;
        if(vari==1) merkit = lauta.getMustat().teeKopio().esijarjestys();
        if(vari==2) merkit = lauta.getValkoiset().teeKopio().esijarjestys();
        return listanParas(lauta, vari, merkit);
    }
 
    /**
     * listanParas -metodi käy läpi parametrina annetun listan ja luo uuden listan, joka 
     * koostuu parhaista listan solmuista, eli solmuista, joiden avaimissa olevissa 
     * sijainneissa olevia nappeja kannattaa liikuttaa, sekä palauttaa jonkun näistä
     * solmuista.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri(1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan
     * @param lista Lista -luokan olio, joka sisältää laudalla olevien nappuloiden sijainnit
     * @return Solmu -olio, jonka avaimen osoittamassa sijainnissa olevaa nappulaa kannattaa liikuttaa
     */
    public Solmu listanParas(Lauta lauta, int vari, Lista lista){
        int parasa = -1000;
        ListaSolmu x = lista.getEka();
        Lista parhaat = new Lista();
        while(x!=null){
            parasa = poistaJaKokeile(lauta, x.getPuuSolmu(), vari, parhaat, parasa);
            x = x.getSeuraava();
        }
        return jokuHyvaSijainti(parhaat);
    }
    
    /**
     * poistaJaKokeile -metodi poistaa parametrina annetun solmun avaimen osoittamassa sijainnissa 
     * olevan nappulan ja kokeilee sijoittaa sen uudelleen laudalla oleviin tyhjiin sijainteihin. 
     * Jos saatava tulos on parempi kuin aikaisemmat, lisätään poistettava solmu parametrina annettuun
     * parhaiden poistettavien solmujen listaan. Lopuksi poistettu merkki laitetaan takaisin paikalleen
     * laudalle. Metodi palauttaa parhaan mahdollisen tuloksen.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param poistettava Solmu -olio, jonka avaimena olevasta sijainnista merkki poistetaan
     * @param vari pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan
     * @param parhaat Lista -luokan ilmentymä, johon on listattu parhaat poistettavat merkit
     * @param parasarvo aikaisemmista tuloksista paras
     * @return tähän astisista tuloksista paras, eli saatu uusi tulos tai aikaisemmin saatu paras arvo 
     * riippuen siitä kumpi on suurempi
     */
    public int poistaJaKokeile(Lauta lauta, Solmu poistettava, int vari, Lista parhaat, int parasarvo){
        int sij = poistettava.getAvain();
        try{
            lauta.poista(sij/8, sij%8, vari);                   //poistetaan laudalta oma nappi
            Solmu kohde = parasTyhjista(lauta, vari, 1);        //paras sijoituskohta poistetulle napille
            lauta.laitaMerkki(sij/8, sij%8, vari);              //nappi takaisin paikalleen
            return paivitaParas(poistettava, kohde, parhaat, parasarvo);    //oliko saatu tulos edellisiä parempi
        }catch(Exception e){
            return parasarvo;
        }
    }
 
    /**
     * paivitaParas -metodi päivittää parametrina annettuun lähtösolmuun kohde muuttujaksi
     * parametrina annetun kohdesolmun sekä arvoksi kohdesolmun arvon. Jos arvo on parempi
     * tai yhtäsuuri kuin siihen mennessä saatu paras arvo, päivitetään parhaat lähtösolmut
     * sisältävä lista uudella lähtösolmulla.
     * @param lahtos solmu, jonka avaimena olevassa sijainnissa olevaa nappia on kokeiltu siirtää
     * @param kohde solmu, jonka avaimena olevaan sijaintiin nappia on kokeiltu siirtää
     * @param parhaat Lista -luokan olio, joka sisältää parhaat lähtösolmut
     * @param parasarvo aikaisemmista tuloksista paras
     * @return tähän astisista tuloksista paras, eli saatu uusi tulos tai aikaisemmin saatu paras arvo 
     * riippuen siitä kumpi on suurempi
     */
    public int paivitaParas(Solmu lahtos, Solmu kohde, Lista parhaat, int parasarvo){
        int tulos = kohde.getArvo();
        lahtos.setKohde(kohde);
        lahtos.setArvo(tulos);
        if(tulos>=parasarvo){
            paivitaLista(parhaat, lahtos, tulos);
            return tulos;
        }
        return parasarvo;
    } 
    
    /**
     * jokuHyvaSijainti -metodi arpoo yhden solmun parametrina annetusta listasta
     * @param sijainnit Lista johon on talletettu parhaat puusolmut
     * @return joku listan solmuista
     */
    public Solmu jokuHyvaSijainti(Lista sijainnit){
        if(sijainnit.getKoko()<2)   return sijainnit.getSolmu(0);
        Random arpa = new Random();
        int monesko = arpa.nextInt(sijainnit.getKoko());
        return sijainnit.getSolmu(monesko);
    }
    
    /**
     * listaaParhaat -metodi tekee listan parhaista sijainneista, joihin 
     * parametrina annetun värisen nappulan voisi sijoittaa. Metodi kokeilee 
     * parametrina annettua toimenpidettä, jokaiseen sijaintiin, joka on parametrina 
     * annetusta juuresta alkavassa puussa. Sijainnit käydään läpi jälkijärjestyksessä.
     * @param toimenpide mikä toimenpide tehdään puusolmuja läpikäytäessa (1=uusi nappi 
     * laudalle, 2=laudalla olevien nappuloiden siirto, 3=vastustajan nappulan poisto)
     * @param juuri sen puun juuri, joka halutaan käydä läpi
     * @param lauta pelilauta, jossa siirtoja tehdään
     * @param vari sen pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka syvälle pelipuussa on edetty (puun taso)
     * @param pelaamatta kuinka monta nappia on vielä siirtämättä laudalle
     * @return lista solmuista, jotka edustavat parhaita sijainteja laudalla
     */
    public Lista listaaParhaat(int toimenpide, Solmu juuri, Lauta lauta, int vari, int k, int pelaamatta) {
        Lista parhaat;
        parhaat = new Lista();
        if(juuri!=null){            //saavutettu lehdet           
            Lista v = listaaParhaat(toimenpide, juuri.getVasen(), lauta, vari, k, pelaamatta);  //vasen puoli puuta
            Lista o = listaaParhaat(toimenpide, juuri.getOikea(), lauta, vari, k, pelaamatta);  //oikea puoli puuta
            parhaat = parempiLista(v, o);   //kumpi listoista on parempi
            int paras = parhaat.getArvo();
            int uusiarvo = kokeile(toimenpide, lauta, juuri, vari, k, true, pelaamatta);   //mikä tulos juuren solmulle saadaan
            parhaat = paivitaLista(parhaat, juuri, uusiarvo);   //onko juuri arvoltaan parempi tai yhtä hyvä kuin siihen mennesssä parhaat
        }
        return parhaat;
    }
        
    /**
     * paivitaLista -metodi lisää parametrina annetun solmun parhaiden sijaintien listaan
     * jos solmun arvo on yhtä hyvä kuin listan muiden solmujen arvo. Jos solmun arvo on
     * parempi kuin listan solmujen arvot, palauttaa metodi uuden listan, jonka ainoa solmu
     * on tämä kyseinen solmu, muuten palautetaan parametrina annettu lista muuttumattomana.
     * @param parhaat lista parhaista sijainneista
     * @param uusi puusolmu jota verrataan listan solmuihin
     * @param uusiarvo verrattavan puusolmun sen hetkinen arvo
     * @return lista, jossa on tähän mennessä parhaat sijainnit
     */
    public Lista paivitaLista(Lista parhaat, Solmu uusi, int uusiarvo){
        int listanarvo = -1000;
        if(parhaat!=null && !parhaat.tyhja()) listanarvo = parhaat.getArvo();
        if(parhaat==null) parhaat = new Lista();
        if((uusiarvo==listanarvo || parhaat.tyhja()) && uusiarvo!=-1000)   parhaat.lisaa(uusi);
        else if(uusiarvo>listanarvo) parhaat = new Lista(uusi);
        return parhaat;                                     //palautetaan päivitetty lista
    }

    /**
     * parempiLista -metodi vertaa kahta listaa ja palauttaa niistä arvoltaan paremman.
     * Jos listat ovat samanarvoisia, ne yhdistetään ja palautetaan yhdistetty lista.
     * @param vasen toinen verrattava lista
     * @param oikea toinen verrattava lista
     * @return parempi lista tai jos listat ovat samanarvoisia yhdistetty lista
     */
    public Lista parempiLista(Lista vasen, Lista oikea){
        int vasena = -1000;
        int oikeaa = -1000;
        if(vasen==null && oikea==null) return new Lista();
        if(vasen!=null) vasena = vasen.getArvo();    //vasemman listan arvo
        if(oikea!=null) oikeaa = oikea.getArvo();    //oikean listan arvo
        if(vasen==null || oikeaa>vasena)    return oikea;
        else if(vasen!=null && oikea!=null && vasena==oikeaa)  vasen.lisaa(oikea);     //jos saman arvoisia yhdistetään listat
        return vasen;   //palautetaan vasen 
    }
    
    /**
     * kayLapiSijainnit -metodi käy läpi parametrina annetusta juuresta alkavan puun, 
     * johon on talletettu laudalla olevia sijainteja, ja suorittaa niihin parametrina 
     * annetun toimenpiteen. Sijainnit käydään läpi jälkijärjestyksessä ja metodi palauttaa
     * arvona alipuun parhaan solmun arvon. 
     * @param toimenpide 1=nappula laudalle, 2=siirrä sijainnissa olevaa nappulaa, 3=vastustajan
     * nappulan poisto, 4=lentäminen
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param juuri sen puun juuri, joka halutaan käydä läpi
     * @param vari sen pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka syvälle pelipuussa ollaan edetty (puun taso)
     * @param pelaamatta kuinka monta nappulaa on siirtämättä laudalle
     * @param omavuoro onko arvioitavan pelaajan vai vastustajan vuoro tehdä siirto
     * @return paras mahdollinen arvo, joka saadaan puussa olevista sijainneista tai huonoin
     * jos on vastapuolen pelivuoro
     */
    public int kayLapiSijainnit(int toimenpide, Lauta lauta, Solmu juuri, int vari, int k, boolean omavuoro, int pelaamatta) {
        if(juuri!=null){
            int v = kayLapiSijainnit(toimenpide, lauta, juuri.getVasen(), vari, k, omavuoro, pelaamatta);
            int o = kayLapiSijainnit(toimenpide, lauta, juuri.getOikea(), vari, k, omavuoro, pelaamatta);
            int paras = parempiArvo(v, o, omavuoro);
            if((omavuoro && paras>=beta) || (!omavuoro && paras<=alpha)) return paras;
                if(omavuoro)    alpha = parempiArvo(alpha, paras, true);    //alpha on parempi arvo kahdesta
                else            beta = parempiArvo(beta, paras, false);     //beta on huonompi arvo kahdesta
            int uusiarvo = kokeile(toimenpide, lauta, juuri, vari, k, omavuoro, pelaamatta);
            return parempiArvo(paras, uusiarvo, omavuoro);
        }
        else if(omavuoro)    return -1000;
        else    return 1000;
    }
        
    /**
     * parempiArvo -metodi palauttaa kahdesta arvosta paremman suhteutettuna siihen 
     * onko pelaajan oma vuoro vai vastustajan vuoro, ts. paremman arvon, jos on pelaajan
     * oma vuoro ja huonomman arvon, jos on vastapelaajan vuoro
     * @param ensimmainen toinen verrattava arvo
     * @param toinen toinen verrattava arvo
     * @param omavuoro onko pelaajan vai vastapelaajan vuoro
     * @return parempi arvo, jos pelaajan oma vuoro, huonompi arvo, jos vastapelaajan vuoro
     */
    public int parempiArvo(int ensimmainen, int toinen, boolean omavuoro){
        if(omavuoro)    return Math.max(ensimmainen, toinen);
        else    return Math.min(ensimmainen, toinen); 
    }
    
    
    /**
     * kokeile -metodi suorittaa parametrina annetun toimenpiteen parametrina annetun
     * solmun edustamaan sijaintiin ja palauttaa siitä seuraavan arvioidun tuloksen, 
     * asetettuaan sen ensin solmun arvoksi.
     * @param toimenpide 1=uusi nappula laudalle, 2=sijainnissa olevan napin siirto, 
     * 3=vastustajan napin poisto, 4=lentäminen
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param solmu solmu, jonka avaimena on kokeiltava sijainti
     * @param vari sen pelaajan väri, joka on vuorossa
     * @param k kuinka syvälle pelipuuta on jo gneroitu (puun taso)
     * @param pelaamatta kuinka monta nappulaa on siirtämättä laudalle
     * @param omavuoro onko pelaajan vai vastapelaajan vuoro
     * @return tulos joka kokeillusta siirrosta voidaan saavuttaa
     */
    public int kokeile(int toimenpide, Lauta lauta, Solmu solmu, int vari, int k, boolean omavuoro, int pelaamatta) {
        int sij = solmu.getAvain();     //sijainti
        int tulos = 0;
            if(toimenpide==1) tulos = kokeileSijainti(lauta, sij/8, sij%8, vari, k, omavuoro, pelaamatta);
            if(toimenpide==2) tulos = kokeileSiirto(lauta, solmu, vari, k, omavuoro);
            if(toimenpide==3) tulos = kokeilePoistaa(lauta, sij/8, sij%8, vari, k, omavuoro, pelaamatta);
            if(toimenpide==4) tulos = kokeileLentaa(lauta, sij/8, sij%8, vari, k, omavuoro);
        solmu.setArvo(tulos);   //päivitetään parametrina annetun solmun arvo tuloksen mukaan
        return tulos;           //palautetaan parametrina annettu solmu
    }

    /**
     * vastaSuunta -metodi palauttaa parametrina annettua suuntaa vastakkaisen suunnan 
     * char merkkinä. 
     * @param suunta a=alas, y=ylös, v=vasemmalle, o=oikealle
     * @return suunnan vastasuunta ('a'->'y', 'y'->'a', 'o'->'v', 'v'->'o')
     */
    public char vastaSuunta(char suunta){
        switch(suunta){
            case 'a': return 'y';
            case 'y': return 'a';
            case 'v': return 'o';
            case 'o': return 'v';
            default: return 'x';
        }
    }
    
    /**
     * omaSiirtoLaudalle -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee 
     * kaikkia mahdollisia laudalle tehtäviä siirtoja omalla värillä sekä minkälaisia 
     * tilanteita niistä seuraa. Metodi palauttaa arvion siitä, kuinka edullinen sen 
     * hetkinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan olio, jossa siirtoja tehdään
     * @param vari pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro true jos generoidaan pelaajan omia siirtoja, false jos vastapuolen 
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return paras mahdollinen tulos johon pelissä voidaan päätyä tai huonoin jos on vastapuolen
     * pelivuoro
     */
    public int siirtoLaudalle(Lauta lauta, int vari, int k, boolean omavuoro, int pelaamatta){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, omavuoro, pelaamatta);     //kun haluttu syvyys saavutettu palautetaan arvio tilanteesta        
        if(pelaamatta==0)   return siirtoLaudalla(lauta, vari, k, omavuoro);      //jos kaikki nappulat on jo käytetty kutsutaan toista metodia
        Puu tyhjat = lauta.getTyhjat().teeKopio();     //tyhjät sijainnit
        Solmu juuri = tyhjat.getJuuri();    
        return kayLapiSijainnit(1, lauta, juuri, vari, k, omavuoro, pelaamatta); //palautetaan paras mahdollinen lopputulos joka voi syntyä alkuperäisestä tilanteesta
    }
    
    /**
     * omaSiirtoLaudalla -metodi arvioi laudalla olevaa pelitilannetta ja kokeilee kaikkia
     * mahdollisia laudalla tehtävissä olevia siirtoja omalla värillä sekä minkälaisia
     * tilanteita niistä seuraa, kun kaikki pelinappulat on jo siirretty laudalle. Metodi 
     * palauttaa arvion siitä, kuinka edullinen alkuperäinen tilanne on pelaajalle.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param vari  pelaajan väri, jonka mahdollisuuksia arvioidaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro true jos generoidaan pelaajan omia siirtoja, false jos vastapuolen
     * @return arvio parhaasta mahdollisesta tilanteesta, johon pelissä päädytään tai huonoimmasta
     * jos on vastapuolen vuoro
     */
    public int siirtoLaudalla(Lauta lauta, int vari, int k, boolean omavuoro){
        if(k==syvyys)   return arvioiTilanne(lauta, vari, omavuoro, 0);   //arvio tilanteesta kun haluttu syvyys saavutettu
        Puu merkit = null;
        int toimenpide = 2;     //kokeile siirtoa
            if(vari==1) merkit = lauta.getMustat().teeKopio();
            else if(vari==2) merkit = lauta.getValkoiset().teeKopio();
        Solmu juuri = merkit.getJuuri();                
            if(merkit.getKoko()<4)  toimenpide = 4; //lennetään
        return kayLapiSijainnit(toimenpide, lauta, juuri, vari, k, omavuoro, 0); 
    }
    
    /**
     * poisto -metodi kokeilee poistaa jokaista parametrina annetun värisen pelaajan
     * vastapuolen nappuloista ja palauttaa parhaan mahdollisen tuloksen tai huonoimman
     * mahdollisen tuloksen riippuen onko pelaajan vai vastapelaajan vuoro.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param vari pelaajan väri, joka on vuorossa
     * @param k kuinka syvälle pelipuuta on jo generoitu (puun taso)
     * @param pelaamatta kuinka monta nappulaa on vielä mahdollisesti siirtämättä laudalle
     * @param omavuoro true jos pelaajan, false jos vastapelaajan vuoro
     * @return paras mahdollinen tulos, jos on pelaajan vuoro tai huonoin mahdollinen tulos,
     * jos on vastapelaajan vuoro
     */
    public int poisto(Lauta lauta, int vari, int k, boolean omavuoro, int pelaamatta){
        Puu merkit = null;
        if(vari==1) merkit = lauta.getValkoiset().teeKopio();
        else if(vari==2) merkit = lauta.getMustat().teeKopio();
        Solmu juuri = merkit.getJuuri();
        return kayLapiSijainnit(3, lauta, juuri, vari, k, omavuoro, pelaamatta);
    }
    
    /**
     * kokeileSijainti -metodi asettaa parametrina annetun värisen merkin parametrina annettuun 
     * sijaintiin. Jos tästä siirrosta syntyy mylly tekoäly poistaa vastapuolen värisen merkin
     * laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa pelipuun generointia jatketaan 
     * seuraavana vuorossa olevan pelaajan siirrolla. Kun siirtojen tulos on saatu selville
     * poistetaan laudalta metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param j rivi jolla kokeiltava paikka sijaitsee
     * @param i paikka rivillä eli indeksi, johon siirtoa kokeillaan
     * @param vari pelaajan väri, jonka siirtoa kokeillaan
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro true jos kyseessä on pelaaja, jonka mahdollisuuksia arvioidaan, false,
     * jos kyseessä on vastapuolen siirto
     * @param pelaamatta kuinka monta nappulaa pelaajilla on vielä siirtämättä laudalle
     * @return arvio siitä kuinka hyvä on tilanne, johon siirrolla päädytään
     */
    public int kokeileSijainti(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta) {
        int tulos = 1000;
            if(omavuoro) tulos = -1000;
        boolean onnistuuko = lauta.laitaMerkki(j, i, vari);                  //kokeillaan sijaintia laittamalla siihen halutun värinen merkki
            if(!onnistuuko) return tulos;
            if(lauta.mylly(vari, (8*j)+i))  tulos = poisto(lauta, vari, k, omavuoro, pelaamatta-1); //jos syntyi mylly voidaan poistaa yksi vastapuolen merkeistä
            else tulos = siirtoLaudalle(lauta, 3-vari, k+1, !omavuoro, pelaamatta-1);  //jos ei myllyä, on vastapuolen vuoro
        lauta.poista(j, i, vari);                       //perutaan tehty siirto
        return tulos;                           //kuinka hyvään lopputulokseen kys. sijainti johtaisi
    }
        
    /**
     * kokeileSiirtoaOma -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa 
     * merkkiä kaikkiin mahdollisiin suuntiin ja palauttaa arvion parhaasta mahdollisesta
     * tilanteesta, johon jollain siirrolla voidaan päästä. Tällä metodilla on tarkoitus 
     * kokeilla siirtää sen pelaajan nappuloita, jonka mahdollisuuksia arvioidaan.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param solmu Solmu -luokan ilmentymä, jonka avaimen osoittamassa sijainnissa olevaa
     * nappulaa kokeillaan siirtää
     * @param vari pelaajan väri, jonka mahdollisuuksia yritetään arvioida
     * @param omavuoro true jos generoidaan pelaajan oma siirto, false jos vastapuolen
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @return arvio parhaasta mahdollisesta lopputuloksesta, johon nappulaa siirrettäessä
     * voidaan päätyä, tai huonoin, jos on vastapuolen vuoro
     */
    public int kokeileSiirto(Lauta lauta, Solmu solmu, int vari, int k, boolean omavuoro){
            int paras = -1000;
            if(!omavuoro)   paras = 1000;
            int sijainti = solmu.getAvain();
            String suunnat = lauta.getSuunnat(sijainti);    //suunnat joihin voidaan liikkua sijainnista
                for(int l=0; l<suunnat.length(); l++){      //kokeillaan siirtoa kaikkiin mahdollisiin suuntiin
                   int suunnantulos = kokeileSuunta(lauta, sijainti/8, sijainti%8, suunnat.charAt(l), vari, k, omavuoro);
                    if(parempiKuinEnnen(suunnantulos, paras, omavuoro)){
                        paras = suunnantulos;   //jos tulos parempi kuin aikaisemmista siirroista, laitetaan se muistiin
                        solmu.setArvo(suunnantulos);
                        solmu.setSuunta(suunnat.charAt(l));    //myös suunta muistiin
                    }
                }
            return paras;   //palautetaan paras mahdollinen lopputulos ja suunta mistä se syntyi
        }
        
    /**
     * parempiKuinEnnen -metodi palauttaa true, jos parametrina annettu tulos
     * on parempi tai huonompi kuin parametrina annettu, siihen mennessä paras tulos, 
     * riippuen siitä onko pelaajan vai vastapelaajan vuoro. Muuten metodi palauttaa false.
     * @param tulos tulos, jota verrataan aikaisempiin
     * @param paras tähän mennessä paras tulos
     * @param omavuoro true jos pelaajan oma vuoro, false jos on vastapelaajan vuoro
     * @return true jos on oma vuoro ja tulos on parempi kuin edellinen tai vastapuolen vuoro
     * ja tulos huonompi kuin edellinen, muuten false
     */
    public boolean parempiKuinEnnen(int tulos, int paras, boolean omavuoro){
        if(omavuoro){
            if(tulos>paras) return true;
            else return false;
        }else{
            if(tulos<paras) return true;
            else return false;
        }
    }

    /**
     * kokeileSuunta -metodi kokeilee siirtää parametrina annetussa sijainnissa olevaa
     * nappulaa parametrina annettuun suuntaan. Jos siirrosta seuraa mylly tekoäly poistaa 
     * vastapuolen värisen merkin laudalta ja jatkaa pelipuun generointia. Muussa tapauksessa 
     * pelipuun generointia jatketaan seuraavana vuorossa olevan pelaajan siirrolla. Kun 
     * siirrosta seuraava tulos on saatu selville perutaan laudalla metodin alussa tehty siirto.
     * @param lauta Lauta -luokan ilmentymä, jolla siirtoja tehdään
     * @param j rivi, jolla olevaa nappia halutaan siirtää
     * @param i paikka rivillä eli indeksi, jossa olevaa nappia halutaan siirtää
     * @param suunta suunta johon laudalla olevaa nappia halutaan siirtää
     * @param vari minkä väristä merkkiä kokeillaan siirtää
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param omavuoro onko vuorossa pelaaja, jonka mahdollisuuksia arvioidaan vai vastapuoli
     * @return arvio tilanteesta johon siirrolla päädytään
     */
    public int kokeileSuunta(Lauta lauta, int j, int i, char suunta, int vari, int k, boolean omavuoro){
            int tulos = 1000; 
                if(omavuoro)    tulos = -1000;
            int uusip = lauta.siirra(j, i, suunta, vari); //kokeillaan siirtää merkkiä annettuun suuntaan
                if(uusip==(j*8)+i) return tulos;    //ei voitu siirtää nappia mihinkään
                else if(lauta.mylly(vari, uusip))    tulos = poisto(lauta, vari, k, omavuoro, 0);    //jos syntyi mylly saadaan poistaa vastapuolen merkki
                else tulos = siirtoLaudalla(lauta, 3-vari, k+1, !omavuoro);          //jos ei myllyä on toisen pelaajan vuoro
                if(uusip!=(j*8)+i) lauta.siirra(uusip/8, uusip%8, vastaSuunta(suunta), vari);     //perutaan tehty siirto
            return tulos;                           //lopputulos joka siirrosta seuraisi
        }
        
    /**
     * kokeilePoistaa -metodi kokeilee poistaa parametrina annetussa sijainnissa olevan merkin
     * ja tekee arvion siitä syntyvästä lopputuloksesta generoimalla pelipuuta eteenpäin.
     * Kun arvio on tehty metodi palauttaa poistetun napin paikalleen.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param j rivi, jolla oleva nappi halutaan poistaa
     * @param i paikka rivillä eli indeksi, josta nappi poistetaan
     * @param vari pelaajan väri, joka saa tehdä poiston
     * @param k kuinka pitkälle pelipuuta on jo generoitu
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @param omavuoro true, jos vuorossa on pelaaja jonka mahdollisuuksia arvioidaan, false
     * jos on vastapuolen pelivuoro
     * @return arvio tilanteesta johon poistolla päädytään
     */
    public int kokeilePoistaa(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro, int pelaamatta){
            int tulos = 1000;
            if(omavuoro) tulos = -1000;
            if(!(lauta.mylly(3-vari, (j*8)+i) && lauta.eiMyllyssa(3-vari))){    //jos kyseinen kohta on myllyssä ja laudalla on vielä merkkejä, jotka eivät ole myllyssä, ei voida poistoa tehdä
                boolean syoty = lauta.syo(j, i, 3-vari);         //kokeillaan poistaa vastapuolen merkki
                if(!syoty)  return tulos;
                tulos = siirtoLaudalle(lauta, 3-vari, k+1, !omavuoro, pelaamatta);  //toisen pelaajan vuoro
                lauta.peruSyonti(j, i, 3-vari);         //perutaan tehty siirto    
            }
            return tulos;       //lopputulos joka poistosta seuraisi
        }
    
    /**
     * kokeileLentaa -metodi kokeilee ensin poistaa parametrina annetusta sijainnista 
     * pelaajan oman nappula ja sitten sijoittaa napin uudelleen laudalle kaikkiin 
     * mahdollisiin sijainteihin. Metodi palauttaa näin saavutetun parhaan tuloksen.
     * @param lauta Lauta -luokan ilmentymä, jossa peliä pelataan
     * @param j rivi, jolla olevaa nappia koitetaan siirtää
     * @param i rivin paikka, eli indeksi, jossa olevaa nappia koitetaan siirtää 
     * @param vari vuorossa olevan pelaajan väri (1=musta, 2=valkoinen)
     * @param k kuinka syvälle pelipuuta on jo generoitu
     * @param omavuoro true jos generoidaan arvioitavan pelaajan siirto, false jos vastapuolen
     * @return paras mahdollinen tulos tai huonoin jos on vastapuolen pelivuoro
     */
    public int kokeileLentaa(Lauta lauta, int j, int i, int vari, int k, boolean omavuoro){
        int tulos = 1000;
            if(omavuoro)    tulos = -1000;
        boolean onnistuuko = lauta.poista(j, i, vari);
            if(!onnistuuko) return tulos;
        tulos = siirtoLaudalle(lauta, vari, k, omavuoro, 1);
        lauta.laitaMerkki(j, i, vari);
        return tulos;
    }
    
    /**
     * arvioiTilanne -metodi palauttaa arvion siitä, kuinka hyvä parametrina
     * annetulla laudalla oleva pelitilanne on pelaajan kannalta, jonka väri on
     * annettu parametrina.
     * @param lauta Lauta -luokan ilmentymä, jolla peliä pelataan
     * @param vari sen pelaajan väri (1=musta, 2=valkoinen), jonka mahdollisuuksia arvioidaan 
     * @param omavuoro onko pelaajan vai vastapuolen siirtovuoro
     * @param pelaamatta kuinka monta nappia pelaajilla on siirtämättä laudalle
     * @return kokonaislukumerkkinen arvo, joka kuvastaa sitä kuinka hyvä pelitilanne 
     * on annetun värisen pelaajan kannalta
     */
    public int arvioiTilanne(Lauta lauta, int vari, boolean omavuoro, int pelaamatta){
        if(!omavuoro)   vari = 3-vari;  //halutaan arvio oman pelaajan kannalta
        return this.heuristiikka.tilanneArvio(lauta, vari, pelaamatta);
    }
    
}
