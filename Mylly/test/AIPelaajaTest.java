/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.AIPelaaja;
import mylly.Lauta;
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
    
    public AIPelaajaTest() {
        AI = new Tekoaly(0);
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
        int uusipaikka = pelaaja.siirraLaudalle(pelilauta);
        Assert.assertTrue(uusipaikka>=0);
        Assert.assertTrue(uusipaikka<24);
        Assert.assertEquals(1, pelilauta.merkki(uusipaikka/8, uusipaikka%8));
    }
    
    @Test
    public void siirraLaudallaTest(){
        int uusipaikka = pelaaja.siirraLaudalla(pelilauta);
        Assert.assertTrue(uusipaikka>=0);
        Assert.assertTrue(uusipaikka<24);
        Assert.assertEquals(1, pelilauta.merkki(uusipaikka/8, uusipaikka%8));
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
        Assert.assertEquals(toinen.laudalla(pelilauta), 0);
    }
    
    @Test
    public void poistaLaudaltaTest(){
        AIPelaaja toinen = new AIPelaaja(2, AI);
        int poistopaikka = toinen.poistaLaudalta(pelilauta);
        Assert.assertFalse(poistopaikka<0);
        Assert.assertFalse(poistopaikka>23);
        Assert.assertEquals(0, pelilauta.merkki(poistopaikka/8, poistopaikka%8));
    }
}
