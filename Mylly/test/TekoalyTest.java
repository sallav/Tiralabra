/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mylly.Lauta;
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
    
    public TekoalyTest() {
        lauta = new Lauta();
        AI = new Tekoaly(0);
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
    public void parasViereisistaTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 1);
        String[] paras = AI.parasViereisista(lauta, 1).split(" ");
        int paikka = Integer.valueOf(paras[0]);
        Assert.assertEquals(18, paikka);
        Assert.assertEquals("v", paras[1]);
    }
    
    @Test
    public void parasViereisistaTest2() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        lauta.laitaMerkki(2, 2, 2);
        String[] paras = AI.parasViereisista(lauta, 2).split(" ");
        int paikka = Integer.valueOf(paras[0]);
        Assert.assertEquals(18, paikka);
        Assert.assertEquals("v", paras[1]);
    }
    
    @Test
    public void vastakohtaTest(){
        Assert.assertEquals('v', AI.vastaSuunta('o'));
        Assert.assertEquals('o', AI.vastaSuunta('v'));
        Assert.assertEquals('a', AI.vastaSuunta('y'));
        Assert.assertEquals('y', AI.vastaSuunta('a'));
    }
    
    @Test
    public void omaSiirtoTest() throws Exception{
        lauta.laitaMerkki(0, 1, 1);
        lauta.laitaMerkki(1, 1, 1);
        Assert.assertEquals(1, AI.omaSiirtoLaudalle(lauta, 1, AI.getSyvyys(), 16));
    }
    
    @Test
    public void vastaPuoliTest() throws Exception{
        lauta.laitaMerkki(0, 1, 2);
        lauta.laitaMerkki(1, 1, 2);
        lauta.laitaMerkki(2, 2, 2);
        Assert.assertEquals(-1, AI.vastaPuoliLaudalle(lauta, 1, AI.getSyvyys(), 15));
    }
}
