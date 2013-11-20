package semestralniprace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
/**
 *
 * @author Efremov Denis
 */
public class SemestralniPrace {
    
    //zapis maticy do pole
    public double[][] loadMatrix(File f){
        String str="";
        double[][]matrix = new double[1][1];
        try{
           DataInputStream myfile = new DataInputStream( new FileInputStream("Matice1.txt"));
           int i=0;
           while(myfile.available()>0){
               str+=myfile.readLine()+"\n";
               i++;
           }
           char[] ch = str.substring(0, str.indexOf("\n")).toCharArray();
           int q=1;
           for (int k = 0; k<ch.length; k++){
              if (ch[k]=='\t') q++; 
           }
           if (str!=""){
               String[]b=new String[i];
               b = str.split("\n");
               String[][]a=new String[i][q];
               matrix=new double[i][q];
               for (int j=0;j<i;j++){
                   a[j]=b[j].split("\t");
               }
               for (int j=0;j<i;j++){
                   for (int k=0;k<q;k++){
                       matrix[j][k]= Double.parseDouble(a[j][k]);
                   }
               } 
            }
            else {System.out.println("Zadejte maticu do souboru Matice1.txt, kde cleny budou "+
                    "rozdeleny pres symbol \\t");}
         }
         catch(IOException e){System.out.println("Nemate soubor Matice1.txt");}
         catch(NumberFormatException e){System.out.println("Zadejte realni cisla!");}
         catch(ArrayIndexOutOfBoundsException e){System.out.println("Matice zadana nespravna");}
    return matrix;}
    
    
    //zapis do souboru
    public void zapis(double[][] matrix) throws FileNotFoundException, IOException{
        DataOutputStream myfile = new DataOutputStream( new FileOutputStream("Vysledky.txt"));
        String str="";
        for (int i=0; i<matrix.length;i++){
            for (int j=0; j<matrix[0].length;j++){
                str+=Double.toString(matrix[i][j])+"\t";
            }
            str+="\r\n";
        }
        myfile.writeUTF(str);
        System.out.println(str);
        //myfile.writeChars(str);
        myfile.close();
    }
    
    
    //kontrola no zadani cisel
    public int check(int a){
        Scanner sc = new Scanner(System.in);
        try {a = sc.nextInt();}
                catch(InputMismatchException e) {System.out.println("Zadej naturalni CISLO");}
    return a;}
    
    public double check(double a){
        Scanner sc = new Scanner(System.in);
        try {a = sc.nextDouble();}
                catch(InputMismatchException e) {System.out.println("Zadej realni CISLO");}
    return a;}
    
    
    
    public static void main(String[] args) throws IOException {
        SemestralniPrace sem = new SemestralniPrace();
        Functions sp = new Functions();
        Scanner sc = new Scanner(System.in);
        int oper=0;
        System.out.println("Zadej operaci: 1-determinant, 2-slozeny matic, 3-nasobeny matic");
        oper = sem.check(oper);
        
        switch (oper){
            //Determinant
            case 1:{
                int zad=0;
                System.out.println("Zadani matic z: 1-konzoly, 2-souboru");
                zad = sem.check(zad);
                switch (zad){
                    //Konzola
                    case 1:{
                        int sloupci=0;
                        System.out.println("Zadani cislo sloupcu a radku");
                        sloupci = sem.check(sloupci);
                        
                        double[][]a = new double[sloupci][sloupci];
                        
                        for (int i=0;i<sloupci;i++){
                            for (int j=0;j<sloupci;j++){
                                System.out.println("Zadej a["+i+"]["+j+"]");
                                a[i][j] = sem.check(a[i][j]);
                            }
                        }
                        
                        System.out.println("---Vysledky---");
                        System.out.println(sp.determinant(a));
                        
                    }break;
                        //Soubor
                    case 2:{
                        File f = new File("Matice1.txt");
                        try{
                            System.out.println("---Vysledky---");
                            System.out.println(sp.determinant(sem.loadMatrix(f)));}
                        catch(ArrayIndexOutOfBoundsException e){
                            System.out.println("Zadejte kvadratitckou matice");}
                    }break;
                    default:{System.out.println("Zadal jste neplatne cislo");}    
                }
            }    
            break;
                //Soucin
            case 2:{
                int zad=0;
                System.out.println("Zadani matic z: 1-konzoly, 2-souboru");
                zad = sem.check(zad);
                switch (zad){
                    //Konzola
                    case 1:{
                        int sloupci=0,radky=0;
                        System.out.println("Zadej cislo sloupcu");
                        sloupci = sem.check(sloupci);
                        System.out.println("Zadej cislo radku");
                        radky = sem.check(radky);
                        
                        double[][]a = new double[sloupci][radky];
                        double[][]b = new double[sloupci][radky];
                        
                        for (int i=0;i<sloupci;i++){
                            for (int j=0;j<radky;j++){
                                System.out.println("Zadej a["+i+"]["+j+"]");
                                a[i][j] = sem.check(a[i][j]);
                            }
                        }
                        for (int i=0;i<sloupci;i++){
                            for (int j=0;j<radky;j++){
                                System.out.println("Zadej b["+i+"]["+j+"]");
                                b[i][j] = sem.check(b[i][j]);
                            }
                        }
                        System.out.println("---Vysledky---");
                        sp.print(sp.soucet(a, b));
                    }break;
                        //Soubor
                    case 2:{
                        File f1 = new File("Matice1.txt");
                        File f2 = new File("Matice2.txt");
                        double[][] matrix1 = sem.loadMatrix(f1);
                        double[][] matrix2 = sem.loadMatrix(f2);
                        
                        if (matrix1.length==matrix2.length&&matrix1[0].length==matrix2[0].length){
                            double[][] c = sp.soucet(matrix1, matrix2);
                            sem.zapis(c);
                            
                        }
                        else System.out.println("Matice maji ruzne dimenze");
                    }break;
                    default:{System.out.println("Zadal jste neplatne cislo");}    
                }
            }    
            break;
                //Soucet
            case 3:{
                int zad=0;
                System.out.println("Zadani matic z: 1-konzoly, 2-souboru");
                zad = sem.check(zad);
                switch (zad){
                    //Konzola
                    case 1:{
                        int sloupci1=0,radky1=0,sloupci2=0,radky2=0;
                        
                        System.out.println("Zadej cislo sloupcu matici a");
                        sloupci1 = sem.check(sloupci1);
                        System.out.println("Zadej cislo radku matici a");
                        radky1 = sem.check(radky1);
                        System.out.println("Zadej cislo sloupcu matici b");
                        sloupci2 = sem.check(sloupci2);
                        System.out.println("Zadej cislo radku matici b");
                        radky2 = sem.check(radky2);
                        
                        double[][]a = new double[sloupci1][radky1];
                        double[][]b = new double[sloupci2][radky2];
                        
                        for (int i=0;i<sloupci1;i++){
                            for (int j=0;j<radky1;j++){
                                System.out.println("Zadej a["+i+"]["+j+"]");
                                a[i][j] = sem.check(a[i][j]);
                            }
                        }
                        
                        for (int i=0;i<sloupci2;i++){
                            for (int j=0;j<radky2;j++){
                                System.out.println("Zadej b["+i+"]["+j+"]");
                                b[i][j] = sem.check(b[i][j]);
                            }
                        }
                        
                        if (radky1==sloupci2){
                            System.out.println("---Vysledky---");
                            sp.print(sp.soucin(a, b));
                        }
                        else {
                            if (radky2==sloupci1){
                                System.out.println("---Vysledky---");
                                sp.print(sp.soucin(b, a));
                            }
                            else {
                                System.out.println("Tyto matici nasobit nelze!");
                                break;
                            }
                        }
                        
                        }break;
                        //Soubor
                    case 2:{
                        File f1 = new File("Matice1.txt");
                        File f2 = new File("Matice2.txt");
                        double[][] matrix1 = sem.loadMatrix(f1);
                        double[][] matrix2 = sem.loadMatrix(f2);
                        
                        if (matrix1.length==matrix2[0].length){
                            double[][] c = sp.soucin(matrix1, matrix2);
                            sem.zapis(c); 
                        }
                        else {
                        if (matrix1[0].length==matrix2.length){
                            double[][] c = sp.soucin(matrix2, matrix1);
                            sem.zapis(c); 
                        }
                        else {System.out.println("Matice nelze nasobit");}
                        }
                        
                    }break;
                    default:{System.out.println("Zadal jste neplatne cislo");}    
                }
            }    
            break;    
        
        default: {System.out.println("Zadal jste neplatne cislo");}
        }
    }
}
