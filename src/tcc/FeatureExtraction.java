/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import de.lmu.ifi.dbs.jfeaturelib.LibProperties;
import de.lmu.ifi.dbs.jfeaturelib.features.*;
import de.lmu.ifi.dbs.utilities.Arrays2;
import ij.process.ColorProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author root
 */
public class FeatureExtraction {

    static List<String> images;

    static void getImageFiles(File folder) {
            File folders[] = folder.listFiles();
            for (int i = folders.length - 1; i >= 0; i--) {
                    if (folders[i].isDirectory())
                            getImageFiles(folders[i]);
                    else if (folders[i].isFile()) {
                            String file = folders[i].getName();
                            if (file.substring(file.length() - 4).equals(".png"))
                                    images.add(""+folders[i].getAbsolutePath());
                    }
            }
    }
        
    public FeatureExtraction(){
        images = new ArrayList<String>();
        File f = new File("/root/ImagensWarp"); // /root/ImagensWarp
    	getImageFiles(f);
        Collections.sort(images);
    }
    
    public void tamuraExtraction() throws IOException{
       
        Tamura tamura;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/tamuraWarp.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));
               //System.out.println(images.get(i));
	    	tamura = new Tamura();
	    	tamura.run(image);
                
                
	    	bw.write(Arrays2.join(tamura.getFeatures().get(0), ","));
	    	bw.write(",");
               
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
                
                /*Com Fatias */
                /*
                 if(slice <= 63){
                    bw.write("N");
                    bw.newLine();
                }else{
                    bw.write("P");
                    bw.newLine();
                }
                */
                
                
                

         }
        
        bw.close();
    	fw.close();
    }
    
     public void tamuraMediaExtraction() throws IOException{
       
        Tamura tamura;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/tamuraMedia.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));
               //System.out.println(images.get(i));
	    	tamura = new Tamura();
	    	tamura.run(image);
                
                double coar = tamura.getFeatures().get(0)[0];
                double contrast = tamura.getFeatures().get(0)[1];
                double dir = 0.0;
                /*tirando a média das 16 direções*/
                for(int j = 2; j<18; j++){
                    dir = dir + tamura.getFeatures().get(0)[j];
                    System.out.print(dir+" ");
                }
                dir = dir/16;
                System.out.print(dir);
                System.out.println();
	    	bw.write(coar+","+contrast+","+dir);
	    	bw.write(",");
               
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }

         }
        
        bw.close();
    	fw.close();
    }
    
    public void tamuraExtraction2() throws IOException{
       
        Tamura tamura;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/tamura2Test.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	tamura = new Tamura();
	    	tamura.run(image);
                
                double coar = tamura.getFeatures().get(0)[0];
                double contrast = tamura.getFeatures().get(0)[1];
	    	bw.write(coar+","+contrast);
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }

         }
        
        bw.close();
    	fw.close();
    }
    
    public void tamura2MomentoExtraction() throws IOException{
       
        Tamura tamura;
        ColorProcessor image;
        Moments moments;    
        
        File csv = new File("/root/TCC/Resultados/tamura2Momento.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	tamura = new Tamura();
                moments = new Moments();
                
	    	tamura.run(image);
                moments.run(image);
                
                double coar = tamura.getFeatures().get(0)[0];
                double contrast = tamura.getFeatures().get(0)[1];
	    	bw.write(coar+","+contrast+","+Arrays2.join(moments.getFeatures().get(0), ","));
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }

         }
        
        bw.close();
    	fw.close();
    }
    
    public void tamura2HaralickExtraction() throws IOException{
       
        Tamura tamura;
        ColorProcessor image;
        Haralick haralick;    
        
        File csv = new File("/root/TCC/Resultados/tamura2Haralick.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	tamura = new Tamura();
                haralick = new Haralick();
                
	    	tamura.run(image);
                haralick.run(image);
                
                double coar = tamura.getFeatures().get(0)[0];
                double contrast = tamura.getFeatures().get(0)[1];
	    	bw.write(coar+","+contrast+","+Arrays2.join(haralick.getFeatures().get(0), ","));
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }

         }
        
        bw.close();
    	fw.close();
    }
    
    
    public void gaborExtraction() throws IOException{
       
        Gabor gabor;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/gabor.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	gabor = new Gabor();
	    	gabor.run(image);	    	
	    	bw.write(Arrays2.join(gabor.getFeatures().get(0), ","));
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }

         }
        
        bw.close();
    	fw.close();
    }
    
    public void haralickExtraction() throws IOException{
       
        Haralick haralick;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/haralickWarp.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	haralick = new Haralick();
	    	haralick.run(image);	    	
	    	bw.write(Arrays2.join(haralick.getFeatures().get(0), ","));
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
                /*if(slice <= 63){
                    bw.write("N");
                    bw.newLine();
                }else{
                    bw.write("P");
                    bw.newLine();
                }*/

         }
        
        bw.close();
    	fw.close();
    }
    
    public void momentsExtraction() throws IOException{
       
        Moments moments;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/momentsWarp.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	moments = new Moments();
	    	moments.run(image);	    	
	    	bw.write(Arrays2.join(moments.getFeatures().get(0), ","));
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
                /*if(slice <= 63){
                    bw.write("N");
                    bw.newLine();
                }else{
                    bw.write("P");
                    bw.newLine();
                }*/

         }
        
        bw.close();
    	fw.close();
    }
    
    public void moments6Extraction() throws IOException{
       
        Moments moments;
        ColorProcessor image;

        File csv = new File("/root/TCC/Resultados/moments6Features.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

	    	moments = new Moments();
	    	moments.run(image);
                bw.write(moments.getFeatures().get(0)[1] * moments.getFeatures().get(0)[1] + ",");//variance
                bw.write(moments.getFeatures().get(0)[1] / moments.getFeatures().get(0)[0] + ",");//coefficient of deviation
	    	bw.write(Arrays2.join(moments.getFeatures().get(0), ","));//mean, standard deviation, skewness, kurtosis
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
               /* if(slice <= 63){
                    bw.write("N");
                    bw.newLine();
                }else{
                    bw.write("P");
                    bw.newLine();
                }*/

         }
        
        bw.close();
    	fw.close();
    }
    
    
    public void moments7Extraction() throws IOException{
       
        Moments moments;
        ColorProcessor image;
        String numberoflevels[];  
        Histogram histogram;
        LibProperties prop;

        File csv = new File("/root/TCC/Resultados/moments7FeaturesWarp.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
                
            
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));

                
	    	moments = new Moments();
	    	moments.run(image);
                histogram = new Histogram();
                prop = LibProperties.get();
	        prop.setProperty(LibProperties.HISTOGRAMS_TYPE, Histogram.TYPE.Gray.name());
	        prop.setProperty(LibProperties.HISTOGRAMS_BINS, 256);
	        histogram.setProperties(prop);
	        histogram.run(image);
                numberoflevels = Arrays2.join(histogram.getFeatures().get(0), ",", "%.0f").split(",");	        
                int maximum = 255;        
	        for(int ii = 0; ii < numberoflevels.length; ii++){        	
	        	if(!numberoflevels[ii].equals("0"))
	        		maximum = ii;
	        }	        
	        bw.write(maximum + ",");//maximum
                bw.write(moments.getFeatures().get(0)[1] * moments.getFeatures().get(0)[1] + ",");//variance
                bw.write(moments.getFeatures().get(0)[1] / moments.getFeatures().get(0)[0] + ",");//coefficient of deviation
	    	bw.write(Arrays2.join(moments.getFeatures().get(0), ","));//mean, standard deviation, skewness, kurtosis
	    	bw.write(",");
	    	//numberofFeatures = numberofFeatures + tamura.getFeatures().get(0).length;
                
                slice++;
                //Por paciente
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
                
                //Por fatia
               /*if(slice <= 63){
                    bw.write("N");
                    bw.newLine();
                }else{
                    bw.write("P");
                    bw.newLine();
                }*/

         }
        
        bw.close();
    	fw.close();
    }
    
    
    public void fourierFeatures() throws IOException{ //TODO Fourier Features
             
        
        
        Moments moments;
        ColorProcessor image;
        String numberoflevels[];  
        Histogram histogram;
        LibProperties prop;

        File csv = new File("/root/TCC/Resultados/fourierFeatures.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
                
            
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));
                
                //Fourier f = new Fourier(image.);

        }
    }
    
    public void allExtraction() throws IOException{
       
        Tamura tamura;
        Haralick haralick;
        Moments moments;
        String numberoflevels[];  
        Histogram histogram;
        LibProperties prop;
        ColorProcessor image;

        File csv = new File("/root/TCC/TamuraHaralickMomentosWarp.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        int slice = 0;
	int classification = 0;
	int numberofFeatures = 0;
        
        
    	for (int i = 0; i < images.size(); i++) {			
    	
	    	image = new ColorProcessor(ImageIO.read(new File(images.get(i))));
                   
                //Tamura
                tamura = new Tamura();
	    	tamura.run(image);
                
                
	    	bw.write(Arrays2.join(tamura.getFeatures().get(0), ","));
	    	bw.write(",");
               
                
                //Haralick
                haralick = new Haralick();
                haralick.run(image);
                
                bw.write(Arrays2.join(haralick.getFeatures().get(0), ","));
	    	bw.write(",");
                
                //7 momentos
                moments = new Moments();
	    	moments.run(image);
                histogram = new Histogram();
                prop = LibProperties.get();
	        prop.setProperty(LibProperties.HISTOGRAMS_TYPE, Histogram.TYPE.Gray.name());
	        prop.setProperty(LibProperties.HISTOGRAMS_BINS, 256);
	        histogram.setProperties(prop);
	        histogram.run(image);
                numberoflevels = Arrays2.join(histogram.getFeatures().get(0), ",", "%.0f").split(",");	        
                int maximum = 255;        
	        for(int ii = 0; ii < numberoflevels.length; ii++){        	
	        	if(!numberoflevels[ii].equals("0"))
	        		maximum = ii;
	        }	        
	        bw.write(maximum + ",");//maximum
                bw.write(moments.getFeatures().get(0)[1] * moments.getFeatures().get(0)[1] + ",");//variance
                bw.write(moments.getFeatures().get(0)[1] / moments.getFeatures().get(0)[0] + ",");//coefficient of deviation
	    	bw.write(Arrays2.join(moments.getFeatures().get(0), ","));//mean, standard deviation, skewness, kurtosis
	    	bw.write(",");

                
                
                slice++;
	        if(slice == 6){
	        	classification++;
	        	if(classification <= 29)
	        		bw.write("N");
	        	else
	        		bw.write("P");
	        	bw.newLine();
	        	slice = 0;	        	
	        }
                
                /*Com Fatias */
//                
//                 if(slice <= 173){
//                    bw.write("N");
//                    bw.newLine();
//                }else{
//                    bw.write("P");
//                    bw.newLine();
//                }
//                
                
                
                

         }
        
        bw.close();
    	fw.close();
        
        
        for (int i = 1; i <= 234; i++) 
			System.out.print("feature"+i+",");
    }
    
    
    /*
    
        Classifiers
    
    */
    
    
    public void knn() throws IOException{
        //parsing CSV to Arff
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.csv"));
        Instances inst = loader.getDataSet();
        
        ArffSaver saver = new ArffSaver();
        saver.setInstances(inst);
        saver.setFile(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        saver.setDestination(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        saver.writeBatch();
        
        BufferedReader reader = new BufferedReader(new FileReader("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        Instances data = new Instances(reader);
        reader.close();
        data.setClassIndex(data.numAttributes() - 1);
        
        //Normalizando
        try {
            Normalize norm = new Normalize();
            norm.setInputFormat(data);
            data = Filter.useFilter(data, norm);
            
        } catch (Exception ex) {
            Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File csv = new File("/root/TCC/Resultados/knn.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for(int i=1; i<51; i++){
            //instanciando o classificador
            IBk knn = new IBk();
            knn.setKNN(i);
            
            
            try {

                knn.buildClassifier(data);
                Evaluation eval = new Evaluation(data);
                //System.out.println(eval.toSummaryString("\nResults\n======\n", false));
                eval.crossValidateModel(knn, data, 10, new Random(1),new Object[]{});
                double auc = eval.areaUnderROC(1);
                System.out.println(auc);
                bw.write(Double.toString(auc));
                bw.newLine();

            } catch (Exception ex) {
                Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        bw.close();
        
    }
    
    
     public void rbf() throws IOException{
        //parsing CSV to Arff
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.csv"));
        Instances inst = loader.getDataSet();
        
        ArffSaver saver = new ArffSaver();
        saver.setInstances(inst);
        saver.setFile(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        saver.setDestination(new File("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        saver.writeBatch();
        
        BufferedReader reader = new BufferedReader(new FileReader("/root/TCC/Resultados/Parte 4 - Novos Casos/TamuraHaralickMomentos.arff"));
        Instances data = new Instances(reader);
        reader.close();
        data.setClassIndex(data.numAttributes() - 1);
        
        //Normalizando
        try {
            Normalize norm = new Normalize();
            norm.setInputFormat(data);
            data = Filter.useFilter(data, norm);
            
        } catch (Exception ex) {
            Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File csv = new File("/root/TCC/Resultados/rbf.csv");
        FileWriter fw = new FileWriter(csv);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for(int i=1; i<51; i++){
            //instanciando o classificador
            RBFNetwork rbf = new RBFNetwork();
            rbf.setNumClusters(i);
            
            
            try {

                rbf.buildClassifier(data);
                Evaluation eval = new Evaluation(data);
                //System.out.println(eval.toSummaryString("\nResults\n======\n", false));
                eval.crossValidateModel(rbf, data, 10, new Random(1),new Object[]{});
                double auc = eval.areaUnderROC(1);
                System.out.println(auc);
                bw.write(Double.toString(auc));
                bw.newLine();

            } catch (Exception ex) {
                Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        bw.close();
        
    }
    
}
