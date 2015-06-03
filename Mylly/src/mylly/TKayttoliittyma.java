/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylly;

import java.util.Scanner;

/**
 *
 * @author Salla
 */
public class TKayttoliittyma implements Kayttoliittyma{
    Scanner lukija;
    StringBuilder pelilauta;
    String lautaesitys = "o---------o---------o\n" +
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
    int edel; 
    
    public TKayttoliittyma(Scanner lukija){
        this.lukija = lukija;
        this.pelilauta = new StringBuilder();
        pelilauta.append(lautaesitys);
    }
    
    @Override
    public int pelinValinta(){
        System.out.println("Valitse peli: ");
        System.out.println("1 Tekoäly vs. tekoäly \n 2 Pelaaja vs. tekoäly \n3 Pelaaja vs. pelaaja");
        String vastaus = lukija.nextLine();
        int vast;
        try{
            vast = Integer.valueOf(vastaus);
            return vast;
        }catch(Exception e){
            return 1;
        }
    }

    public void tulostaLauta(){
        String merkit = pelilauta.toString();
        System.out.println(merkit);
    }
    
    public void paivitaLauta(int siirto, int vari, boolean poisto){
        char merkki = 'o';
        int ind = indeksi(siirto%8, siirto/8);
        pelilauta.setCharAt(edel, pelilauta.substring(edel, edel+1).toLowerCase().charAt(0));
        if(vari==1) merkki = 'M';
        if(vari==2) merkki = 'V';
        if(poisto)  merkki = 'O';        
        pelilauta.setCharAt(ind, merkki);
        edel = ind;
    }

    public int indeksi(int paikka, int rivi){
        if(paikka<3)    return rivi*44 + paikka*10 + rivi*3 - paikka*3;
        if(paikka>3 && paikka<7)    return 286 - (rivi*44) - 2 - ((-4 + paikka)*10) - (rivi*3) + ((-4+paikka)*3*rivi);
        if(paikka==3)   return 132 + 20 - (rivi*3);
        else            return 132 + (rivi*3);
    }
    
    @Override
    public void kerroVoittaja(int vari){
        if(vari==1) System.out.println("Pelin voittaja: musta");
        if(vari==2) System.out.println("Pelin voittaja: valkoinen");
        if(vari==0) System.out.println("Peli päättyi tasapeliin");
    }
    
}
