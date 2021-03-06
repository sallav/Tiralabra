/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import ui.TKayttoliittyma;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TKayttoliittyma -luokan metodien testejä
 * @author Salla
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
        Assert.assertEquals(lautaesitys.substring(0, lautaesitys.length()-2) + "v\n", kayttol.getLauta().replaceFirst("O", "o"));
    }
    
    @Test
    public void indeksiTest(){
        Assert.assertEquals(0, kayttol.indeksi(0, 0));
        Assert.assertEquals(10, kayttol.indeksi(1, 0));
        Assert.assertEquals(20, kayttol.indeksi(2, 0));
        Assert.assertEquals(47, kayttol.indeksi(0, 1));
        Assert.assertEquals(54, kayttol.indeksi(1, 1));
        Assert.assertEquals(61, kayttol.indeksi(2, 1));
        Assert.assertEquals(94, kayttol.indeksi(0, 2));
        Assert.assertEquals(98, kayttol.indeksi(1, 2));
        Assert.assertEquals(102, kayttol.indeksi(2, 2));
    }
    
    @Test
    public void indeksiTest2(){
        Assert.assertEquals(284, kayttol.indeksi(4, 0));
        Assert.assertEquals(274, kayttol.indeksi(5, 0));
        Assert.assertEquals(264, kayttol.indeksi(6, 0));
        Assert.assertEquals(237, kayttol.indeksi(4, 1));
        Assert.assertEquals(228, kayttol.indeksi(5, 1));
        Assert.assertEquals(221, kayttol.indeksi(6, 1));
        Assert.assertEquals(190, kayttol.indeksi(4, 2));
        Assert.assertEquals(186, kayttol.indeksi(5, 2));
        Assert.assertEquals(182, kayttol.indeksi(6, 2));
    }
    
    @Test
    public void indeksiTest3(){
        Assert.assertEquals(132, kayttol.indeksi(7, 0));
        Assert.assertEquals(135, kayttol.indeksi(7, 1));
        Assert.assertEquals(138, kayttol.indeksi(7, 2));
        Assert.assertEquals(146, kayttol.indeksi(3, 2));
        Assert.assertEquals(149, kayttol.indeksi(3, 1));
        Assert.assertEquals(152, kayttol.indeksi(3, 0));
    }
}
