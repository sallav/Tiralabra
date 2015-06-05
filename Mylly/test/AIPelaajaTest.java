/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.AIPelaaja;
import mylly.Heuristiikka;
import mylly.Lauta;
import mylly.Perus;
import mylly.Tekoaly;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author varkoi
 */
public class AIPelaajaTest {
    private Tekoaly AI;
    private AIPelaaja pelaaja;
    private Lauta pelilauta;
    private int ekapaikka;
    private Heuristiikka h;
    
    public AIPelaajaTest() {
        h = new Perus();
        AI = new Tekoaly(h, 0);
        pelaaja = new AIPelaaja(1, AI);
        pelilauta = new Lauta();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ekapaikka = pelaaja.siirraLaudalle(pelilauta, 18);
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
    public void variTest(){
        Assert.assertEquals(1, pelaaja.vari());
    }
    
    @Test
    public void siirraLaudalleTest(){
        int uusipaikka = pelaaja.siirraLaudalle(pelilauta, 17);
        Assert.assertTrue(uusipaikka>=0);
        Assert.assertTrue(uusipaikka<24);
        Assert.assertEquals(1, pelilauta.merkki(uusipaikka/8, uusipaikka%8));
    }
    
    @Test
    public void siirraLaudalle23nappiaTest(){
        int uusipaikka;
        int vanha = ekapaikka;
        int nappeja = 24;
        for(int i=0; i<23; i++){
            nappeja = nappeja--;
            uusipaikka = pelaaja.siirraLaudalle(pelilauta, nappeja);
            Assert.assertTrue(uusipaikka!=ekapaikka);
            Assert.assertTrue(uusipaikka!=vanha);
            Assert.assertTrue(uusipaikka>=0);
            Assert.assertTrue(uusipaikka<24);
            vanha = uusipaikka;
        }
    }
    
    @Test
    public void siirraLaudallaTest(){
        int uusipaikka = pelaaja.siirraLaudalla(pelilauta);
        Assert.assertTrue(uusipaikka>=0);
        Assert.assertTrue(uusipaikka<24);
        Assert.assertEquals(1, pelilauta.merkki(uusipaikka/8, uusipaikka%8));
        Assert.assertFalse(uusipaikka==ekapaikka);
    }
    
    @Test
    public void lennaTest(){
        int uusipaikka = pelaaja.lenna(pelilauta);
        Assert.assertTrue(uusipaikka>=0);
        Assert.assertTrue(uusipaikka<24);
        Assert.assertEquals(1, pelilauta.merkki(uusipaikka/8, uusipaikka%8));
    }
    
    @Test
    public void laudallaTest() throws Exception{
        Assert.assertEquals(pelaaja.laudalla(pelilauta), pelilauta.montakoNappia(1));
    }
    
    @Test
    public void laudallaTest2(){
        AIPelaaja toinen = new AIPelaaja(2, AI);
        Assert.assertEquals(0, toinen.laudalla(pelilauta));
        toinen.siirraLaudalle(pelilauta, 17);
        Assert.assertEquals(1, toinen.laudalla(pelilauta));
    }
    
    @Test
    public void poistaLaudaltaTest(){
        AIPelaaja toinen = new AIPelaaja(2, AI);
        int sij = toinen.siirraLaudalle(pelilauta, 17);
        int poistopaikka = toinen.poistaLaudalta(pelilauta, 17, sij);
        Assert.assertFalse(poistopaikka<0);
        Assert.assertFalse(poistopaikka>23);
        Assert.assertEquals(0, pelilauta.merkki(poistopaikka/8, poistopaikka%8));
        Assert.assertEquals(0, pelaaja.laudalla(pelilauta));
    }
}
