/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Lauta;
import heuristiikat.Perus;
import tietorakenteet.Lista;
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
public class PerusTest {
    Perus perush;
    Lauta lauta;
    
    public PerusTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        perush = new Perus();
        lauta = new Lauta();
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
    public void tilanneArvioTest(){
        Assert.assertEquals(0, perush.tilanneArvio(lauta, 1, 18));
    }
    
    @Test
    public void tilanneArvioTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 1, 1);
        Assert.assertTrue(0<perush.tilanneArvio(lauta, 1, 15));
        Assert.assertTrue(0>perush.tilanneArvio(lauta, 2, 15));
    }
    
    @Test
    public void voikoLiikkuaTest(){
        Assert.assertEquals(0, perush.voikoLiikkua(lauta, 1));
    }
    
    @Test
    public void voikoLiikkuaTest2() throws Exception{
        lauta.laitaMerkki(0, 0, 1);
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(0, 7, 2);
        Assert.assertEquals(-100, perush.voikoLiikkua(lauta, 1));
    }
    
    @Test
    public void syodytTest(){
        Assert.assertEquals(0, perush.syodyt(lauta, 1));
        lauta.laitaMerkki(0, 2, 1);
        Assert.assertEquals(0, perush.syodyt(lauta, 2));
        lauta.syo(0, 2, 1);
        Assert.assertEquals(-10, perush.syodyt(lauta, 1));
        Assert.assertEquals(10, perush.syodyt(lauta, 2));
    }
    
    @Test 
    public void syodytTest2(){
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(0, 2, 2);
        lauta.laitaMerkki(1, 1, 1);
        lauta.syo(0, 1, 2);
        lauta.syo(1, 1, 1);
        Assert.assertEquals(perush.syodyt(lauta, 1), perush.syodyt(lauta, 2));
        lauta.syo(0, 2, 2);
        Assert.assertEquals(10, perush.syodyt(lauta, 1));
        lauta.peruSyonti(1, 1, 1);
        Assert.assertEquals(20, perush.syodyt(lauta, 1));
        Assert.assertEquals(-20, perush.syodyt(lauta, 2));
    }
    
    @Test
    public void syodytTest3(){
        for(int i=0; i<7; i++){
            lauta.laitaMerkki(1, 1, 1);
            lauta.syo(1, 1, 1);
        }
        Assert.assertEquals(-100, perush.syodyt(lauta, 1));
    }
    
    @Test
    public void myllytTest(){
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 1, 1);
        Assert.assertEquals(3, perush.myllyt(lauta, 1, 0));
        lauta.laitaMerkki(0, 6, 2);
        lauta.laitaMerkki(0, 5, 2);
        Assert.assertEquals(1, perush.myllyt(lauta, 1, 0));
        lauta.laitaMerkki(0, 4, 1);
        Assert.assertEquals(3, perush.myllyt(lauta, 1, 0));
        Assert.assertEquals(-2, perush.myllyt(lauta, 2, 0));
    }
    
    @Test
    public void blokatutTest(){
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(0, 1, 2);
        Lista tarkistettavat = new Lista();
        tarkistettavat.lisaa(1);
        tarkistettavat.lisaa(9);
        tarkistettavat.lisaa(17);
        Assert.assertEquals(2, perush.blokatut(tarkistettavat, lauta, 1));
        lauta.laitaMerkki(2, 1, 2);
        Assert.assertEquals(3, perush.blokatut(tarkistettavat, lauta, 1));
    }
}
