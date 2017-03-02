/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import junit.framework.Assert;
import mylly.AIPelaaja;
import ui.Kayttoliittyma;
import mylly.Mylly;
import mylly.Pelaaja;
import mylly.Peli;
import ui.TKayttoliittyma;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Käyttäjä
 */
public class MyllyTest {
        Mylly peli;
        
    public MyllyTest() {
        this.peli = new Mylly();
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
    public void uusiPeliTest(){
        Kayttoliittyma kal = new TKayttoliittyma(new Scanner(System.in));
        Peli uusip = peli.uusiPeli(1, kal);
        Assert.assertTrue(uusip.pelaa()<3);
    }
    
}
