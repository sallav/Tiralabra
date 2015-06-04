/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import mylly.TKayttoliittyma;
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
public class TKayttoliittymaTest {
    String lautaesitys;
    TKayttoliittyma kayttol;
    
    public TKayttoliittymaTest() {
        kayttol = new TKayttoliittyma(new Scanner(System.in));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        lautaesitys = "o---------o---------o\n" +
                         "|         |         |\n" +
                         "|  o------o------o  |\n" +
                         "|  |      |      |  |\n" +
                         "|  |  o---o---o  |  |\n" +
                         "|  |  |       |  |  |\n" +
                         "o--o--o       o--o--o\n" +
                         "|  |  |       |  |  |\n" +
                         "|  |  o---o---o  |  |\n" +
                         "|  |      |      |  |\n" +
                         "|  o------o------o  |\n" +
                         "|         |         |\n" +
                         "o---------o---------o\n";
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
    public void getLautaTest(){
        Assert.assertEquals(lautaesitys, kayttol.getLauta());
    }
    
    @Test
    public void paivitaLautaTest(){
        kayttol.paivitaLauta(7, 1, true);
        Assert.assertFalse(lautaesitys==kayttol.getLauta());
    }
    
    @Test
    public void paivitaLautaTest2(){
        kayttol.paivitaLauta(0, 1, false);
        Assert.assertEquals(lautaesitys.replaceFirst("o", "M"), kayttol.getLauta());
        kayttol.paivitaLauta(4, 2, false);
        Assert.assertEquals('m', kayttol.getLauta().charAt(0));
        kayttol.paivitaLauta(0, 1, true);
        Assert.assertEquals(lautaesitys.substring(0, 283) + "V\n", kayttol.getLauta().replaceFirst("o", "O"));
    }
    
    @Test
    public void indeksiTest(){
        Assert.assertEquals(20, kayttol.indeksi(2, 0));
        Assert.assertEquals(46, kayttol.indeksi(0, 1));
        Assert.assertEquals(60, kayttol.indeksi(2, 1));
        Assert.assertEquals(102, kayttol.indeksi(1, 2));
    }
    
    @Test
    public void indeksiTest2(){
        Assert.assertEquals(296, kayttol.indeksi(4, 0));
        Assert.assertEquals(247, kayttol.indeksi(4, 1));
        Assert.assertEquals(227, kayttol.indeksi(6, 1));
        Assert.assertEquals(188, kayttol.indeksi(5, 2));
    }
    
    @Test
    public void indeksiTest3(){
        Assert.assertEquals(141, kayttol.indeksi(7, 1));
        Assert.assertEquals(152, kayttol.indeksi(3, 2));
    }
}
