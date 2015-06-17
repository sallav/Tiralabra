/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Heuristiikka;
import mylly.Lauta;
import mylly.Lista;
import mylly.Perus;
import mylly.Puu;
import mylly.SijaintiPuu;
import mylly.Solmu;
import mylly.Tekoaly;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Käyttäjä
 */
public class TekoalyTest {
    Lauta lauta;
    Tekoaly AI;
    Heuristiikka h;
    
    public TekoalyTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        lauta = new Lauta();
        h = new Perus();
        AI = new Tekoaly(h, 3);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void kayLapiSijainnitTest() throws Exception{
        Solmu juuri = lauta.getTyhjat().getJuuri();
        int paras = AI.kayLapiSijainnit(1, lauta, juuri, 1, 0, 18, true, 0);
        Assert.assertNotNull(lauta);
        Assert.assertEquals(0, lauta.getLauta()[1][1]);
   //     lauta.laitaMerkki(1, 1, 1);
   //     Solmu paras2 = AI.kayLapiSijainnit(juuri, juuri, lauta, 1, 0, true, 17);
   //     Assert.assertFalse(paras1.getAvain()==paras2.getAvain());
   //     Assert.assertTrue(1==paras2.getAvain() || 17==paras2.getAvain());
    }
    
    @Test
    public void parasTyhjista() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(2, 1, 1);
        Assert.assertEquals(9, AI.parasTyhjista(lauta, 1, 16));
    }
    
    @Test
    public void parasTyhjista2() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        Assert.assertEquals(17, AI.parasTyhjista(lauta, 1, 18));
    }
    
    @Test
    public void parasSiirtoTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Solmu paras = AI.parasSiirto(lauta, 1);
        int paikka = paras.getAvain();
        Assert.assertEquals(18, paikka);
        Assert.assertEquals('v', paras.getSuunta());
    }
    
    @Test
    public void parasSiirtoTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 2);
        Solmu paras = AI.parasSiirto(lauta, 2);
        int paikka = paras.getAvain();
        Assert.assertEquals(18, paikka);
        Assert.assertEquals('v', paras.getSuunta());
    }
    
    @Test 
    public void parasPoistoTest() throws Exception{
        lauta.laitaMerkki(0, 6, 2);
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        int poistop = AI.parasPoisto(lauta, 1, 13, 18);
        Assert.assertFalse(poistop==6);
        Solmu siirrettava = AI.parasSiirto(lauta, 2);
        int paikka = siirrettava.getAvain();
        lauta.siirra(paikka/8, paikka%8, siirrettava.getSuunta());
        Assert.assertFalse(lauta.mylly(1, 1));
    }
    
    @Test
    public void jokuHyvaSijainti(){
        Assert.assertEquals(8, AI.jokuHyvaSijainti(new Lista(new Solmu(8))).getAvain());
    }
    
    @Test 
    public void listaaParhaat() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(1, 0, 1);
        Puu tyhjat = lauta.getTyhjat();
        Lista parhaat = AI.listaaParhaat(1, tyhjat.getJuuri(), lauta, 1, 0, 15, 8);
        Assert.assertEquals(2, parhaat.getKoko());
        int eka = parhaat.getEka().getPuuSolmu().getAvain();
        int toka = parhaat.getVika().getPuuSolmu().getAvain();
        Assert.assertTrue(eka==10 || eka==17);
        Assert.assertTrue(toka==10 || toka==17);
    }
    
    @Test
    public void paivitaLista(){
        Solmu eka = new Solmu(6);
        eka.setArvo(10);
        Solmu toka = new Solmu(8);
        toka.setArvo(10);
        Lista uusi = AI.paivitaLista(new Lista(eka), toka, 10);
        Assert.assertEquals(2, uusi.getKoko());
        Assert.assertEquals(10, uusi.getArvo());
        toka.setArvo(15);
        Lista paivitetty = AI.paivitaLista(uusi, toka, 15);
        Assert.assertEquals(15, paivitetty.getArvo());
        Assert.assertEquals(1, paivitetty.getKoko());
    }
    
    @Test
    public void parempiLista(){
        Solmu eka = new Solmu(8, 10);
        Solmu toka = new Solmu(2, 20);
        Lista v = new Lista(eka);
        Lista o = null;
        Lista parempi = AI.parempiLista(v, o);
        Assert.assertEquals(10, parempi.getArvo());
        o = new Lista(toka);
        parempi = AI.parempiLista(v, o);
        Assert.assertEquals(20, parempi.getArvo());
        
    }
    
    @Test
    public void kayLapiSijainnit2Test(){
        Puu puu = lauta.getTyhjat();
        Assert.assertFalse(-1000==AI.kayLapiSijainnit(1, lauta, puu.getJuuri(), 1, 0, 18, true, 0));
    }
    
    @Test
    public void kayLapiSijainnit3Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Puu mustat = lauta.getMustat();
        Assert.assertTrue(0<AI.kayLapiSijainnit(2, lauta, mustat.getJuuri(), 1, 0, 15, true, 18));
    }
    
    @Test
    public void parempiArvoTest(){
        int eka = 100;
        int toka = -60;
        Assert.assertEquals(eka, AI.parempiArvo(eka, toka, true));
        Assert.assertEquals(toka, AI.parempiArvo(eka, toka, false));
    }
    
    @Test
    public void kokeileTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertTrue(AI.kokeile(1, lauta, new Solmu(17), 1, 0, 16, true, 9)>AI.kokeile(1, lauta, new Solmu(17), 2, 0, 16, false, 9));
    }
    
    @Test
    public void kokeileTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(AI.kokeile(2, lauta, new Solmu(18), 1, 0, 6, true, 18)>AI.kokeile(2, lauta, new Solmu(1), 1, 0, 6, true, 18));
        Assert.assertTrue(AI.kokeile(2, lauta, new Solmu(18), 1, 0, 6, true, 18)>AI.kokeile(2, lauta, new Solmu(9), 1, 0, 6, true, 18));
    }
    
    @Test
    public void kokeileTest3() throws Exception{
        lauta.laitaMerkki(1, 0, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(1, 3, 2);
        lauta.laitaMerkki(0, 6, 2);
        Assert.assertTrue(AI.kokeile(3, lauta, new Solmu(11), 1, 0, 0, true, 6)>AI.kokeile(3, lauta, new Solmu(6), 1, 0, 0, true, 6));
    }
    
    @Test
    public void parasPoistoTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        lauta.laitaMerkki(2, 0, 2);
        lauta.laitaMerkki(0, 4, 2);
        int poistop = AI.parasPoisto(lauta, 1, 12, 18);
        Assert.assertFalse(poistop==4);
    }
    
    @Test
    public void vastaSuuntaTest(){
        Assert.assertEquals('v', AI.vastaSuunta('o'));
        Assert.assertEquals('o', AI.vastaSuunta('v'));
        Assert.assertEquals('a', AI.vastaSuunta('y'));
        Assert.assertEquals('y', AI.vastaSuunta('a'));
    }
    
    @Test
    public void siirtoLaudalleTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertTrue(0<AI.siirtoLaudalle(lauta, 1, 0, 16, true, 9));
        Assert.assertTrue(AI.siirtoLaudalle(lauta, 1, 0, 15, true, 9)>AI.siirtoLaudalle(lauta, 2, 0, 15, true, 9));
    }
    
    @Test
    public void siirtoLaudallaTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0<AI.siirtoLaudalla(lauta, 1, 0, true, 9));
    }
    
    @Test
    public void siirtoLaudalla2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(0, 0, 1);
        Assert.assertTrue(0>AI.siirtoLaudalla(lauta, 2, 0, true, 0));
    }
    
    @Test
    public void siirtoLaudalle2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        Assert.assertTrue(0>AI.siirtoLaudalle(lauta, 1, 0, 15, false, 18));
    }
    
    @Test
    public void siirtoLaudalla3Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0>AI.siirtoLaudalla(lauta, 1, 0, false, 18));
    }
    
    @Test
    public void siirtoLaudalla4Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0>AI.siirtoLaudalla(lauta, 2, 0, false, 18));
    }
    
    @Test
    public void kokeileSijaintiTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertEquals(100, AI.kokeileSijainti(lauta, 2, 1, 1, 0, true, 16));
//        Assert.assertTrue(AI.kokeileSijainti(lauta, 2, 1, 1, 0, true, 16)>AI.kokeileSijainti(lauta, 2, 1, 2, 0, true, 16));
    }
    
    @Test
    public void kokeileSijainti2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        Assert.assertTrue(0>AI.kokeileSijainti(lauta, 0, 6, 1, 0, true, 15));
    }
    
    @Test
    public void kokeileSiirtoTest() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        int tulos = AI.kokeileSiirto(lauta, new Solmu(18), 2, 0, true);
        Assert.assertTrue(0>tulos);
    }
            
    @Test
    public void kokeileSiirto2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        lauta.laitaMerkki(2, 0, 1);
        lauta.laitaMerkki(0, 6, 1);
        Assert.assertTrue(AI.kokeileSiirto(lauta, new Solmu(16), 1, 0, true)>AI.kokeileSiirto(lauta, new Solmu(6), 1, 0, true));        
        Assert.assertTrue(AI.kokeileSiirto(lauta, new Solmu(18), 2, 0, true)>AI.kokeileSiirto(lauta, new Solmu(16), 1, 0, true));
    }
    
    @Test
    public void kokeileSiirto3Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        lauta.laitaMerkki(2, 0, 1);
        lauta.laitaMerkki(0, 6, 1);
        Assert.assertTrue(AI.kokeileSiirto(lauta, new Solmu(16), 2, 0, false)<AI.kokeileSiirto(lauta, new Solmu(6), 2, 0, false));        
        Assert.assertTrue(AI.kokeileSiirto(lauta, new Solmu(18), 1, 0, false)<AI.kokeileSiirto(lauta, new Solmu(16), 2, 0, false));
    }
    
    @Test
    public void kokeileSuuntaTest() throws Exception{
        lauta.laitaMerkki(0, 2, 2);
        lauta.laitaMerkki(0, 3, 2);
        lauta.laitaMerkki(0, 5, 2);
        Assert.assertTrue(AI.kokeileSuunta(lauta, 0, 5, 'v', 2, 0, true)>AI.kokeileSuunta(lauta, 0, 5, 'o', 2, 0, true));
    }
    
    @Test
    public void poistoTest() throws Exception{
        lauta.laitaMerkki(0, 2, 2);
        lauta.laitaMerkki(0, 3, 2);
        lauta.laitaMerkki(0, 5, 2);   
        lauta.laitaMerkki(2, 2, 1);
        lauta.laitaMerkki(2, 3, 1);
        lauta.laitaMerkki(2, 4, 1);
        Assert.assertTrue(AI.poisto(lauta, 1, 0, 12, true, 20)<AI.poisto(lauta, 2, 0, 12, true, 20));
    }
    
    @Test
    public void kokeilePoistaa() throws Exception{
        lauta.laitaMerkki(0, 2, 2);
        lauta.laitaMerkki(0, 3, 2);
        lauta.laitaMerkki(0, 5, 2);
        lauta.laitaMerkki(2, 0, 2);
        Assert.assertTrue(AI.kokeilePoistaa(lauta, 0, 5, 1, 0, 14, true, 16)>AI.kokeilePoistaa(lauta, 2, 0, 1, 0, 14, true, 16));
    }
           
    @Test
    public void arvioiTilanneTest() throws Exception{
        int tulos = AI.arvioiTilanne(lauta, 1, 18, 0);
        lauta.laitaMerkki(0, 2, 2);
        lauta.laitaMerkki(0, 3, 2);
        lauta.laitaMerkki(0, 5, 2);        
        Assert.assertTrue(tulos>AI.arvioiTilanne(lauta, 1, 15, 5));
        Assert.assertTrue(AI.arvioiTilanne(lauta, 2, 15, 5)>AI.arvioiTilanne(lauta, 1, 15, 5));
    }
    
}
