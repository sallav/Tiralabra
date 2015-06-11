/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Heuristiikka;
import mylly.Lauta;
import mylly.Perus;
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
        lauta = new Lauta();
        h = new Perus();
        AI = new Tekoaly(h, 3);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    public void parasTyhjista() throws Exception, Exception{
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
    public void parasPoistettavaTest() throws Exception{
        lauta.laitaMerkki(0, 6, 2);
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        int poistop = AI.parasPoistettava(lauta, 1, 13, 18);
        Assert.assertFalse(poistop==6);
        Solmu siirrettava = AI.parasSiirto(lauta, 2);
        int paikka = siirrettava.getAvain();
        lauta.siirra(paikka/8, paikka%8, siirrettava.getSuunta());
        Assert.assertFalse(lauta.mylly(1, 1));
    }
    
    @Test
    public void parasPoistettavaTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        lauta.laitaMerkki(2, 0, 2);
        lauta.laitaMerkki(0, 4, 2);
        int poistop = AI.parasPoistettava(lauta, 1, 12, 18);
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
    public void omaSiirtoLaudalleTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertTrue(0<AI.omaSiirtoLaudalle(lauta, 1, 0, 16, 9));
        Assert.assertTrue(AI.omaSiirtoLaudalle(lauta, 1, 0, 15, 9)>AI.omaSiirtoLaudalle(lauta, 2, 0, 15, 9));
    }
    
    @Test
    public void omaSiirtoLaudallaTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0>AI.omaSiirtoLaudalla(lauta, 1, 0, 9));
    }
    
    @Test
    public void omaSiirtoLaudalla2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(0, 0, 1);
        Assert.assertTrue(0>AI.omaSiirtoLaudalla(lauta, 2, 0, 0));
    }
    
    @Test
    public void vastaPuoliLaudalleTest() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        Assert.assertTrue(0>AI.vastaPuoliLaudalle(lauta, 1, 0, 15, 18));
    }
    
    @Test
    public void vastaPuoliLaudallaTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0>AI.vastaPuoliLaudalla(lauta, 1, 0, 18));
    }
    
    @Test
    public void vastaPuoliLaudalla2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(0>AI.vastaPuoliLaudalla(lauta, 2, 0, 18));
    }
    
    @Test
    public void kokeileSijaintiTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertTrue(AI.kokeileSijainti(lauta, 2, 1, 1, 0, true, 16)>AI.kokeileSijainti(lauta, 2, 1, 2, 0, true, 16));
    }
    
    @Test
    public void kokeileSijainti2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        Assert.assertTrue(0>AI.kokeileSijainti(lauta, 0, 6, 1, 0, true, 15));
    }
    
    @Test
    public void kokeileSiirtoaOmaTest() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        int tulos = AI.kokeileSiirtoaOma(lauta, new Solmu(18), 2, 0);
        Assert.assertTrue(0>tulos);
    }
            
    @Test
    public void kokeileSiirtoaOma2Test() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        lauta.laitaMerkki(2, 0, 1);
        lauta.laitaMerkki(0, 6, 1);
        Assert.assertTrue(AI.kokeileSiirtoaOma(lauta, new Solmu(16), 1, 0)>AI.kokeileSiirtoaOma(lauta, new Solmu(6), 1, 0));        
        Assert.assertTrue(AI.kokeileSiirtoaOma(lauta, new Solmu(18), 2, 0)>AI.kokeileSiirtoaOma(lauta, new Solmu(16), 1, 0));
    }
    
    @Test
    public void kokeileSiirtoaVastapTest() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        lauta.laitaMerkki(2, 0, 1);
        lauta.laitaMerkki(0, 6, 1);
        Assert.assertTrue(AI.kokeileSiirtoaVastaP(lauta, new Solmu(16), 2, 0)<AI.kokeileSiirtoaVastaP(lauta, new Solmu(6), 2, 0));        
        Assert.assertTrue(AI.kokeileSiirtoaVastaP(lauta, new Solmu(18), 1, 0)<AI.kokeileSiirtoaVastaP(lauta, new Solmu(16), 2, 0));
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
