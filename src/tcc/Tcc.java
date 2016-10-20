/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import java.io.IOException;

/**
 *
 * @author root
 */
public class Tcc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Test
//        FeatureExtraction fe = new FeatureExtraction();
//        try{
//            fe.tamuraExtraction2();
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }
        
        //Test
        Warp warp = new Warp();
        try{
            warp.warp();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
}
