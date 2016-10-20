/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_32FC1;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMat;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetPerspectiveTransform;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvWarpPerspective;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;


/**
 *
 * @author root
 */
public class Warp {
    
    static private List<String> images;
    
    public Warp(){
        images = new ArrayList<String>();
        File f = new File("/root/ImagensWarp"); // /root/ImagensWarp
    	getImageFiles(f);
        Collections.sort(images);
    }
    
    public static void getImageFiles(File folder) {
        File folders[] = folder.listFiles();
        for (int i = folders.length - 1; i >= 0; i--) {
            if (folders[i].isDirectory()) {
                getImageFiles(folders[i]);
            } else if (folders[i].isFile()) {
                String file = folders[i].getName();
                if (file.substring(file.length() - 4).equals(".png")) {
                    images.add("" + folders[i].getAbsolutePath());
                }
            }
        }
    }
    
    public void warp() throws IOException{
        images = new ArrayList<String>();
        File f = new File("/root/TCC/Imagens Sacroilite/imagensFinal"); // /root/TCC/ImagensFatias  ||    /root/TCC/imagens
        getImageFiles(f);
        Collections.sort(images);
        ImageProcessor image;
        ByteProcessor imageBP;
        
        File pontos = new File("/root/Downloads/Pontos de vertice");
        FileReader arq = new FileReader(pontos);
        BufferedReader lerArq = new BufferedReader(arq);
        lerArq.readLine();
        
//        for(int i =0; i<6; i++){
//            lerArq.readLine();
//        }

        for (int i = 0; i <images.size(); i++) {
            String[] index = lerArq.readLine().split("\t");
            System.out.println(images.get(i));
//            System.out.println(i);
            image = new ColorProcessor(ImageIO.read(new File(images.get(i))));
                        
           
            //Compute warp matrix
//            for(int tamanho =0; tamanho<index.length; tamanho++){
//                System.out.println(index[tamanho]);
//            }
            int x1 = Integer.parseInt(index[1].split(" ")[0]);
            int y1 = Integer.parseInt(index[1].split(" ")[1]);
            int x2 = Integer.parseInt(index[2].split(" ")[0]);
            int y2 = Integer.parseInt(index[2].split(" ")[1]);
            int x3 = Integer.parseInt(index[3].split(" ")[0]);
            int y3 = Integer.parseInt(index[3].split(" ")[1]);
            int x4 = Integer.parseInt(index[4].split(" ")[0]);
            int y4 = Integer.parseInt(index[4].split(" ")[1]);
           // System.out.println(x1 + ", " + y1 + "\n" + x2 + ", " + y2 + "\n" + x3 + ", " + y3 + "\n" + x4 + ", " + y4 + "\n");

            BufferedImage bfimg = ImageIO.read(new File(images.get(i)));
            opencv_core.IplImage fundo = opencv_core.IplImage.createFrom(bfimg); // cvLoadImage("/root/TCC/testWarping.png");
            opencv_core.IplImage imgInput = opencv_core.IplImage.createFrom(bfimg);
            opencv_core.CvPoint2D32f srcTri = new opencv_core.CvPoint2D32f(4);
            opencv_core.CvPoint2D32f dstTri = new opencv_core.CvPoint2D32f(4);

            srcTri.position(0).x(x1);//primeiro ponto: superior esquerdo
            srcTri.position(0).y(y1);

            srcTri.position(1).x(x2);//segundo ponto: inferior esquerdo
            srcTri.position(1).y(y2);

            srcTri.position(2).x(x3);//terceiro ponto: superior direito
            srcTri.position(2).y(y3);

            srcTri.position(3).x(x4);//quarto ponto: inferior direito
            srcTri.position(3).y(y4);

            dstTri.position(0).x(0);
            dstTri.position(0).y(0);

            dstTri.position(1).x(0);
            dstTri.position(1).y(image.getHeight());

            dstTri.position(2).x(image.getWidth() / 2);
            dstTri.position(2).y(0);

            dstTri.position(3).x(image.getWidth() / 2);
            dstTri.position(3).y(image.getHeight());

            opencv_core.CvMat warp_mat = cvCreateMat(3, 3, CV_32FC1);
            cvGetPerspectiveTransform(srcTri.position(0), dstTri.position(0), warp_mat);

            cvWarpPerspective(imgInput, fundo, warp_mat);


//            ImageProcessor imgproc = new ColorProcessor(fundo.getBufferedImage());
//            ImagePlus imgplus = new ImagePlus("Final esquerdo "+i, imgproc);
//            imgplus.show();
            
            //Para o segundo quadrilatero:
            BufferedImage bfimg2 = ImageIO.read(new File(images.get(i)));
            opencv_core.IplImage fundo2 = opencv_core.IplImage.createFrom(bfimg2); // cvLoadImage("/root/TCC/testWarping.png");
            opencv_core.IplImage imgInput2 = opencv_core.IplImage.createFrom(bfimg2);
            
            
            //Testing Warp:
            //Compute warp matrix
            x1 = Integer.parseInt(index[5].split(" ")[0]);
            y1 = Integer.parseInt(index[5].split(" ")[1]);
            x2 = Integer.parseInt(index[6].split(" ")[0]);
            y2 = Integer.parseInt(index[6].split(" ")[1]);
            x3 = Integer.parseInt(index[7].split(" ")[0]);
            y3 = Integer.parseInt(index[7].split(" ")[1]);
            x4 = Integer.parseInt(index[8].split(" ")[0]);
            y4 = Integer.parseInt(index[8].split(" ")[1]);
           //System.out.println(x1 + ", " + y1 + "\n" + x2 + ", " + y2 + "\n" + x3 + ", " + y3 + "\n" + x4 + ", " + y4 + "\n");

            srcTri = new opencv_core.CvPoint2D32f(4);
            dstTri = new opencv_core.CvPoint2D32f(4);

            srcTri.position(0).x(x1);//primeiro ponto: superior esquerdo
            srcTri.position(0).y(y1);

            srcTri.position(1).x(x2);//segundo ponto: inferior esquerdo
            srcTri.position(1).y(y2);

            srcTri.position(2).x(x3);//terceiro ponto: superior direito
            srcTri.position(2).y(y3);

            srcTri.position(3).x(x4);//quarto ponto: inferior direito
            srcTri.position(3).y(y4);

            dstTri.position(0).x(image.getWidth() / 2);
            dstTri.position(0).y(0);

            dstTri.position(1).x(image.getWidth() / 2);
            dstTri.position(1).y(image.getHeight());

            dstTri.position(2).x(image.getWidth());
            dstTri.position(2).y(0);

            dstTri.position(3).x(image.getWidth());
            dstTri.position(3).y(image.getHeight());

            warp_mat = cvCreateMat(3, 3, CV_32FC1);
            cvGetPerspectiveTransform(srcTri.position(0), dstTri.position(0), warp_mat);

            cvWarpPerspective(imgInput2, fundo2 , warp_mat);

//            imgproc = new ColorProcessor(fundo2.getBufferedImage());
//            imgplus = new ImagePlus("Final direito "+i, imgproc);
//            imgplus.show();
            
            
            //ImagePlus imgp = new ImagePlus("teste", image);
            //imgp.show();
            
            //Juntando as duas imagens 
            ImageProcessor imagemFinalWarp = new ColorProcessor(image.getWidth(), image.getHeight());
            ImageProcessor imgproc1 = new ColorProcessor(fundo.getBufferedImage());
            ImageProcessor imgproc2 = new ColorProcessor(fundo2.getBufferedImage());
            int valor = 0;
            for(int x = 0; x<image.getWidth()/2; x++){
                for(int y = 0; y<image.getHeight(); y++){
                    imagemFinalWarp.putPixel(x, y,imgproc1.getPixel(x, y));
                }
            }
            for(int x = image.getWidth()/2; x< image.getWidth(); x++){
                for(int y = 0; y<image.getHeight(); y++){
                    imagemFinalWarp.putPixel(x, y,imgproc2.getPixel(x, y));
                }
            }
            ImagePlus imgplusFinal = new ImagePlus("Final ", imagemFinalWarp);
            //imgplusFinal.show();
            //save image
            Path pathToFile = Paths.get("/root/ImagensWarp/"+images.get(i).split("/")[5]+"/"+images.get(i).split("/")[6]+"/"+images.get(i).split("/")[7]+"/"+images.get(i).split("/")[8]);
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            
            File dir = new File("/root/ImagensWarp/"+images.get(i).split("/")[5]+"/"+images.get(i).split("/")[6]+"/"+images.get(i).split("/")[7]);
            //System.out.println(dir.getPath());
            if(!dir.exists())
                dir.mkdir();

            ImageIO.write(imgplusFinal.getBufferedImage(), "png", new File(dir.getPath(),images.get(i).split("/")[8]));
        }
        arq.close();
        lerArq.close();

    }
    
    /*
    Retorna o numero de elementos não nulls de um array
    @param arr - array
    @return count - numero de elementos não nulos
    Fonte: http://stackoverflow.com/questions/7466102/function-like-array-length-that-doesnt-include-null-elements
    */
    public static <T> int getLength(T[] arr) {
        int count = 0;
        for (T el : arr) {
            if (el != null) {
                ++count;
            }
        }
        return count;
    }
}
