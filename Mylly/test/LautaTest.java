/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Lauta;
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
public class LautaTest {
    private Lauta pelilauta;
    
    public LautaTest() {
        pelilauta = new Lauta();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        pelilauta.laitaMerkki(2, 0, 1);
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
    public void laitaMerkkiTest() throws Exception{
        pelilauta.laitaMerkki(1, 0, 1);
        int[][] taul = pelilauta.getLauta();
        Assert.assertEquals(1, taul[1][0]);
    }
    
    @Test
    public void laitaMerkkiTestVaarallaSyotteella() throws Exception{
        pelilauta.laitaMerkki(1, 0, 1);
        Assert.assertFalse(0==pelilauta.getLauta()[1][0]);
    }
    
    @Test
    public void poistaTest() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void poistaTestVaarallaSyotteella() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertFalse(1==pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void siirraTest() throws Exception{
        pelilauta.siirra(2, 0, 'y');
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[1][0]);
        pelilauta.siirra(1, 0, 'a');
        Assert.assertEquals(0, pelilauta.getLauta()[1][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void siirraTest2() throws Exception{
        pelilauta.siirra(2, 0, 'v');
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(1, pelilauta.getLauta()[2][7]);
        pelilauta.siirra(2, 7, 'o');
        Assert.assertEquals(1, pelilauta.getLauta()[2][0]);
        Assert.assertEquals(0, pelilauta.getLauta()[2][7]);
    }
    
    @Test
    public void siirraTestVaarallaSyotteella() throws Exception{
        pelilauta.siirra(2, 0, 'y');
        Assert.assertFalse(1==pelilauta.getLauta()[2][0]);
        Assert.assertFalse(0==pelilauta.getLauta()[1][0]);
        pelilauta.siirra(1, 0, 'v');
        Assert.assertFalse(0==pelilauta.getLauta()[1][7]);
        Assert.assertFalse(1==pelilauta.getLauta()[1][0]);
    }
    
    @Test
    public void siirtoTest() throws Exception{
        int x = pelilauta.siirto(2, 0, 1, 6);
        Assert.assertEquals(x, 14);
        Assert.assertEquals(1, pelilauta.getLauta()[1][6]);
        Assert.assertEquals(0, pelilauta.getLauta()[2][0]);
    }
    
    @Test
    public void siirtoTestVaarallaSyotteella() throws Exception{
        int x = pelilauta.siirto(2, 0, 1, 7);
        Assert.assertFalse(x==16);
        Assert.assertFalse(pelilauta.getLauta()[2][0]==1);
        Assert.assertFalse(pelilauta.getLauta()[1][7]==0);
    }
    
    @Test
    public void merkkiTest() throws Exception{
        int merkki = pelilauta.merkki(2, 0);
        Assert.assertEquals(1, merkki);
        Assert.assertEquals(0, pelilauta.merkki(1, 0));
        pelilauta.laitaMerkki(0, 0, 2);
        Assert.assertEquals(2, pelilauta.merkki(0, 0));
    }
    
    @Test
    public void montakoNappiaTest() throws Exception{
        Assert.assertEquals(1, pelilauta.montakoNappia(1));
        Assert.assertEquals(0, pelilauta.montakoNappia(2));
    }
    
    @Test
    public void montakoNappiaVaarallaSyotteella() throws Exception{
        pelilauta.poista(2, 0, 1);
        Assert.assertFalse(1==pelilauta.montakoNappia(1));
    }
    
    @Test
    public void voikoLiikkuaTest() throws Exception{
        Assert.assertTrue(pelilauta.voikoLiikkua(1));
        pelilauta.laitaMerkki(2, 1, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertFalse(pelilauta.voikoLiikkua(1));
    }
    
    @Test
    public void voikoLiikkuaTest2(){
        Assert.assertFalse(pelilauta.voikoLiikkua(2));
    }
    
    @Test
    public void myllyjaTest(){
        Assert.assertFalse(pelilauta.myllyja(1));
        Assert.assertFalse(pelilauta.myllyja(2));
    }
    
    @Test
    public void myllyjaTest2() throws Exception{
        pelilauta.laitaMerkki(2, 1, 1);
        pelilauta.laitaMerkki(2, 2, 1);
        Assert.assertTrue(pelilauta.myllyja(1));
        Assert.assertFalse(pelilauta.myllyja(2));
    }
    
    @Test
    public void myllyjaTest3() throws Exception{
        pelilauta.laitaMerkki(0, 7, 2);
        pelilauta.laitaMerkki(1, 7, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertTrue(pelilauta.myllyja(2));
        Assert.assertFalse(pelilauta.myllyja(1));
    }
    
    @Test
    public void myllyTest() throws Exception{
        pelilauta.laitaMerkki(0, 7, 2);
        pelilauta.laitaMerkki(1, 7, 2);
        pelilauta.laitaMerkki(2, 7, 2);
        Assert.assertTrue(pelilauta.mylly(2, 7));
        Assert.assertTrue(pelilauta.mylly(2, 15));
        Assert.assertTrue(pelilauta.mylly(2, 23));
        Assert.assertFalse(pelilauta.mylly(1, 7));
    }
   
}
