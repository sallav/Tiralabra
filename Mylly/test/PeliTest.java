/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.AIPelaaja;
import mylly.Heuristiikka;
import mylly.Lauta;
import mylly.Pelaaja;
import mylly.Peli;
import mylly.Perus;
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
public class PeliTest {
    Pelaaja musta;
    Pelaaja valkoinen;
    Tekoaly AI;
    Peli peli;
    Heuristiikka h;
    
    public PeliTest() {
        h = new Perus();
        AI = new Tekoaly(h, 3);
        musta = new AIPelaaja(1, AI);
        valkoinen = new AIPelaaja(2, AI);
        peli = new Peli(musta, valkoinen);
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
    public void pelaaTest() throws Exception{
            int voittajanvari = peli.pelaa();
            Lauta lauta = peli.getLauta();
            int mustia = lauta.montakoNappia(1);
            int valkoisia = lauta.montakoNappia(2);
            int voittavavari = Math.max(mustia, valkoisia);
            Assert.assertTrue(voittajanvari==voittavavari || voittajanvari==0);
            Assert.assertTrue(lauta.voikoLiikkua(3-voittajanvari) || lauta.montakoNappia(3-voittajanvari)<3 || voittajanvari==0);
    }
    
    @Test
    public void selvitaVoittajaTest(){
        Assert.assertEquals(0, peli.selvitaVoittaja());
    }
        
    @Test
    public void selvitaVoittajaTest2() throws Exception{
        Lauta lauta = peli.getLauta();
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 2, 1);
        lauta.laitaMerkki(2, 1, 1);
        lauta.laitaMerkki(0, 0, 2);
        lauta.laitaMerkki(0, 4, 2);
        lauta.laitaMerkki(1, 4, 2);
        Assert.assertEquals(1, peli.selvitaVoittaja());
    }
    
    @Test
    public void pelaaLaudalleTest() throws Exception{
        for(int i=0; i<4; i++){
            peli.pelaaLaudalle(musta, 18-i);
        }
        Assert.assertEquals(3, peli.getLauta().montakoNappia(1));
    }
    
    @Test
    public void pelaaLaudalleMyllyTest() throws Exception{
        Lauta lauta = peli.getLauta();
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(2, 1, 1);
        lauta.laitaMerkki(2, 4, 2);
        peli.pelaaLaudalle(musta, 15);
        Assert.assertEquals(3, peli.getLauta().montakoNappia(1));
        Assert.assertEquals(0, peli.getLauta().montakoNappia(2));
        Assert.assertTrue(1==peli.getLauta().myllyja(1));
    }
    
    @Test
    public void pelaaLaudallaTest() throws Exception{
        Lauta lauta = peli.getLauta();
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 2, 1);
        lauta.laitaMerkki(2, 1, 1);
        lauta.laitaMerkki(0, 4, 2);
        peli.pelaaLaudalla(musta);
        Assert.assertEquals(0, peli.getLauta().montakoNappia(2));
        Assert.assertTrue(1==peli.getLauta().myllyja(1));        
    }
    
    @Test
    public void pelaaLaudallaJaLennaTest() throws Exception{
        Lauta lauta = peli.getLauta();
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(2, 1, 1);
        lauta.laitaMerkki(0, 6, 1);
        lauta.laitaMerkki(0, 4, 2);
        peli.pelaaLaudalla(musta);
        Assert.assertEquals(0, peli.getLauta().montakoNappia(2));
        Assert.assertTrue(1==peli.getLauta().myllyja(1));        
    }
    
    @Test
    public void voittajaTest(){
        Assert.assertEquals(1, peli.voittaja(musta.vari()));
        Assert.assertEquals(2, peli.voittaja(valkoinen.vari()));
        Assert.assertEquals(0, peli.voittaja(0));
    }

}
