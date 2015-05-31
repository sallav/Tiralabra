/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Lauta;
import mylly.Perus;
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
        perush = new Perus();
        lauta = new Lauta();
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
    public void tilanneArvioTest(){
        Assert.assertEquals(0, perush.tilanneArvio(lauta, 1, 18));
    }
    
    @Test
    public void voikoLiikkuaTest(){
        Assert.assertEquals(1, perush.voikoLiikkua(lauta, 1));
    }
    
    @Test
    public void voikoLiikkuaTest2() throws Exception{
        lauta.laitaMerkki(0, 0, 1);
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(0, 7, 2);
        Assert.assertEquals(-100, perush.voikoLiikkua(lauta, 1));
    }
}