package tupro2;
import java.io.*;
import java.util.*;
import java.util.stream.DoubleStream;


/**
 *
 * @author Mujaddid
 */
public class Tupro2 {
    static String suhu[]=new String[80];
    static String waktu[]=new String[80];
    static String kondisi[]=new String[80];
    static String kelembapan[]=new String[80];
    static String hasil[]=new String[80];
    static String Usuhu[]=new String[20];
    static String Uwaktu[]=new String[20];
    static String Ukondisi[]=new String[20];
    static String Ukelembapan[]=new String[20];
    static String Uhasil[]=new String[20];
    static String Kromosom[]=new String[10];
    static String nextGenKromosom[]=new String[10];
    static String bestKromosom,tmpbestKromosom;
    static String decodedKromosom[][][]=new String[10][10][5];
    static String decodedNextKromosom[][][]=new String[10][10][5];
    static String decodedBestKromosom[][][]=new String[1][10][5];
    static String tmpdecodedBestKromosom[][][]=new String[1][10][5];
    static String tKromosom[][];
    static String ortu[]=new String [10];
    static String decodedOrtu[][][]=new String[10][10][5]; 
    static double akurasi[]=new double[10];
    static double ortuAkurasi[]=new double[10];
    static double nextAkurasi[]=new double[10];
    static double bestAkurasi,tmpBestAkurasi;
    static int jmlAturan[]=new int [10];
    static double peluang[]=new double [10];
    static double plotPeluang[]=new double [10];
    static String hasil1="";
    static String hasil2="";
    static int lolos[]=new int[80];
    static String terbang[]=new String[20];

    static void bacaDataLatih()
    {
        int i=0;
        BufferedReader br;
        String line;
        try {
            br=new BufferedReader(new FileReader(".\\src\\tupro2\\data_latih_opsi_1.csv"));
            while((line=br.readLine())!=null){
                
                String[] data=line.split(",");
                suhu[i]=data[0];
                waktu[i]=data[1];
                kondisi[i]=data[2];
                kelembapan[i]=data[3];
                hasil[i]=data[4];
                i++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static void bacaDataUji()
    {
        int i=0;
        BufferedReader br;
        String line;
        try {
            br=new BufferedReader(new FileReader(".\\src\\tupro2\\data_uji_opsi_1.csv"));
            while((line=br.readLine())!=null){
                
                String[] data=line.split(",");
                Usuhu[i]=data[0];
                if("Rendah".equals(Usuhu[i]))
                    Usuhu[i]="rendah";
                else if("Normal".equals(Usuhu[i]))
                    Usuhu[i]="normal";
                else if("Tinggi".equals(Usuhu[i]))
                    Usuhu[i]="tinggi";
                
                Uwaktu[i]=data[1];
                if("Pagi".equals(Uwaktu[i]))
                    Uwaktu[i]="pagi";
                else if("Siang".equals(Uwaktu[i]))
                    Uwaktu[i]="siang";
                else if("Sore".equals(Uwaktu[i]))
                    Uwaktu[i]="sore";
                else if("Malam".equals(Uwaktu[i]))
                    Uwaktu[i]="malam";
                
                Ukondisi[i]=data[2];
                if("Cerah".equals(Ukondisi[i]))
                    Ukondisi[i]="cerah";
                else if("Berawan".equals(Ukondisi[i]))
                    Ukondisi[i]="berawan";
                else if("Rintik".equals(Ukondisi[i]))
                    Ukondisi[i]="rintik";
                else if("Hujan".equals(Ukondisi[i]))
                    Ukondisi[i]="hujan";
                
                Ukelembapan[i]=data[3];
                if("Rendah".equals(Ukelembapan[i]))
                    Ukelembapan[i]="rendah";
                else if("Normal".equals(Ukelembapan[i]))
                    Ukelembapan[i]="normal";
                else if("Tinggi".equals(Ukelembapan[i]))
                    Ukelembapan[i]="tinggi";
                
                i++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static int jmlAturan(String kromosom)
    {
        int panjang=kromosom.length();
        int jml=panjang/15;
        return jml;
    }
    
    static int hitungSatu(String kromosom)
    {
        int jml=0,panjang=kromosom.length();
        if(panjang==3)
        {
            if("1".equals(kromosom.substring(0,1)))
            {
                jml++;
            }
            if("1".equals(kromosom.substring(1,2)))
            {
                jml++;
            }
            if("1".equals(kromosom.substring(2,3)))
            {
                jml++;
            }
        }
        if(panjang==4)
        {
            if("1".equals(kromosom.substring(0,1)))
            {
                jml++;
            }
            if("1".equals(kromosom.substring(1,2)))
            {
                jml++;
            }
            if("1".equals(kromosom.substring(2,3)))
            {
                jml++;
            }
            if("1".equals(kromosom.substring(3,4)))
            {
                jml++;
            }
        }

        return jml;
    }
    
    public static void decodeKromosom(String kromosom)
    {
        int jml=jmlAturan(kromosom),i=0,y=0,tmp1=0;
        String bKromosom[]=new String[jml];
        String tmp;
        tKromosom=new String[jml][5];
        while(i<jml)
        {
           tmp=kromosom.substring(y, y+15);
           bKromosom[i]=tmp;
           y=y+15;
           i++;
        }
        i=0;
        while(i<jml)
        {
            y=0;
            tmp=bKromosom[i].substring(0, 3);
            tmp1=hitungSatu(tmp);
            if(tmp1==1)
                tKromosom[i][y]="rendah";
            else if (tmp1==2)
                tKromosom[i][y]="normal";
            else if (tmp1==3)
                tKromosom[i][y]="tinggi";
            y++;
            tmp=bKromosom[i].substring(3, 7);
            tmp1=hitungSatu(tmp);
            if(tmp1==1)
                tKromosom[i][y]="pagi";
            else if (tmp1==2)
                tKromosom[i][y]="siang";
            else if (tmp1==3)
                tKromosom[i][y]="sore";
            else if (tmp1==4)
                tKromosom[i][y]="malam";
            y++;
            tmp=bKromosom[i].substring(7, 11);
            tmp1=hitungSatu(tmp);
            if(tmp1==1)
                tKromosom[i][y]="cerah";
            else if (tmp1==2)
                tKromosom[i][y]="berawan";
            else if (tmp1==3)
                tKromosom[i][y]="rintik";
            else if (tmp1==4)
                tKromosom[i][y]="hujan";
            y++;
            tmp=bKromosom[i].substring(11, 14);
            tmp1=hitungSatu(tmp);
            if(tmp1==1)
                tKromosom[i][y]="rendah";
            else if (tmp1==2)
                tKromosom[i][y]="normal";
            else if (tmp1==3)
                tKromosom[i][y]="tinggi";
            y++;
            if(tmp1==1)
                tKromosom[i][y]="ya";
            else
                tKromosom[i][y]="tidak";
            i++;
        }
        
    }
    
    static String cekPeluang(double peluang)
    {
        String Ortu="";
        if ((peluang>=0)&&(peluang<=plotPeluang[0]))
            Ortu=Kromosom[0];               
        else if ((peluang>=plotPeluang[0])&&(peluang<=plotPeluang[1]))
            Ortu=Kromosom[1]; 
        else if ((peluang>=plotPeluang[1])&&(peluang<=plotPeluang[2]))
            Ortu=Kromosom[2];
        else if ((peluang>=plotPeluang[2])&&(peluang<=plotPeluang[3]))
            Ortu=Kromosom[3];
        else if ((peluang>=plotPeluang[3])&&(peluang<=plotPeluang[4]))
            Ortu=Kromosom[4];
        else if ((peluang>=plotPeluang[4])&&(peluang<=plotPeluang[5]))
            Ortu=Kromosom[5];
        else if ((peluang>=plotPeluang[5])&&(peluang<=plotPeluang[6]))
            Ortu=Kromosom[6];
        else if ((peluang>=plotPeluang[6])&&(peluang<=plotPeluang[7]))
            Ortu=Kromosom[7];
        else if ((peluang>=plotPeluang[7])&&(peluang<=plotPeluang[8]))
            Ortu=Kromosom[8];
        else if ((peluang>=plotPeluang[8])&&(peluang<=plotPeluang[9]))
            Ortu=Kromosom[9];
        return Ortu;
    }
    
    static String Mutasi(String x)
    {
        String temp,subString;
        temp="";
        int tempBiner,probabilitas;
        int i=0;
        while(i<x.length())
        {
            double acakM=Math.random();
            subString=x.substring(i,(i+1));
            tempBiner=Integer.parseInt(subString);
            probabilitas=1/x.length();
            if (acakM<=probabilitas)
            {
                if(tempBiner==1)
                    tempBiner=0;
                else if (tempBiner==0)
                    tempBiner=1;
            }
            temp=temp+tempBiner;
            i++;
        }
        return temp;
    }
    
    static void CrossOver(String x, String y)
    {
        String temp1,temp2,temp3,tempM;
        int acak1=(int)(Math.random() * x.length());
        int acak2=(int)(Math.random() * x.length()+1);
        while ((acak2==acak1)||(acak2<acak1))
        {
            acak2=(int)(Math.random() * x.length()+1);
        }
        int titik1=acak1 % 15;
        int titik2=acak2 % 15;
        while ((titik1==titik2)||(titik2<titik1))
        {
            acak1=(int)(Math.random() * x.length());
            acak2=(int)(Math.random() * x.length()+1);
            while ((acak2==acak1)||(acak2<acak1))
            {
                acak2=(int)(Math.random() * x.length()+1);
            }
            titik1=acak1 % 15;
            titik2=acak2 % 15;
        }
        
        temp1=x.substring(0, acak1);
        temp2=y.substring(titik1, titik2);
        temp3=x.substring(titik2, x.length());
        hasil1=temp1+temp2+temp3;
        tempM=Mutasi(hasil1);
        hasil1=tempM;
        temp1=y.substring(0, titik1);
        temp2=x.substring(acak1, acak2);
        temp3=y.substring(titik2, y.length());
        hasil2=temp1+temp2+temp3;
        tempM=Mutasi(hasil2);
        hasil2=tempM;
    }
    
    static int cekArray(int z)
    {
        int i=0,hasil=1;
        for (i = 0; i < lolos.length; i++) 
        {
            if(z==lolos[i])
                hasil=0;
        }
        return hasil;
    }
    
    public static void pilihNextGen()
    {
        int i=0;
        while (i<=9)
        {
            int x=0;
            while (x<=9)
            {
                if(ortuAkurasi[i]>nextAkurasi[x])
                {
                    nextAkurasi[x]=ortuAkurasi[i];
                    nextGenKromosom[x]=ortu[i];
                    break;
                }   
                x++;
            }
            i++;
        }
    }
    
    public static void bestKromosom()
    {
        int i=0;
        double tmp=nextAkurasi[i];
        bestKromosom=nextGenKromosom[i];
        String tmp1="";
        while (i<=9)
        {
            if(nextAkurasi[i]>tmp)
            {
                tmp1=nextGenKromosom[i];
                tmp=nextAkurasi[i];
                
            }
            i++;
        }
        if(tmp>nextAkurasi[0])
        {
            bestKromosom=tmp1;
            bestAkurasi=tmp;
        }
        else
        {
            bestKromosom=nextGenKromosom[0];
            bestAkurasi=nextAkurasi[0];
        }
        
        int x=0,y=0;
        i=0;
        decodeKromosom(bestKromosom);
        jmlAturan[i]=jmlAturan(bestKromosom);
        while(x<tKromosom.length)
        {
            y=0;
            while(y<5)
            {
                decodedBestKromosom[i][x][y]=tKromosom[x][y];
                y++;
            }
            x++;
        }
    }
    
    public static void main(String[] args) {
        int i=0,x=0,y=0;
        int RandomNumber,jmlBit;
        Random acak = new Random();
        bacaDataLatih();
        bacaDataUji();
        while (i<=9)//pembutan Individu
            {
                Kromosom[i]="";
                x=0;
                RandomNumber=(int)(Math.random()*5);
                if(RandomNumber==0)
                    jmlBit=14;
                else if(RandomNumber==1)
                    jmlBit=29;
                else if(RandomNumber==2)
                    jmlBit=44;
                else if(RandomNumber==3)
                    jmlBit=59;
                else
                    jmlBit=74;
                while(x<=jmlBit)
                {
                    RandomNumber= acak.nextInt(10)+1;
                    int cek= RandomNumber%2;
                    if(cek==1)
                    {
                        Kromosom[i]=Kromosom[i]+"1";
                    }
                    else
                    {
                        Kromosom[i]=Kromosom[i]+"0";
                    }
                    x++;
                }
                i++;
            }
        i=0;
        while(i<=9) //decode kromosom
        {
            x=0;
            decodeKromosom(Kromosom[i]);
            jmlAturan[i]=jmlAturan(Kromosom[i]);
            while(x<tKromosom.length)
            {
                y=0;
                while(y<5)
                {
                    decodedKromosom[i][x][y]=tKromosom[x][y];
                    y++;
                }
                x++;
            }
            i++;
        }
        i=0;
        while(i<=9)// hitung akurasi
        {
            int l=0;
            double totBenar=0;
            x=0;
            lolos=new int[80];
            for(int it=0;it<lolos.length;it++)
            {
                lolos[it]=90;
            }
            while(x<jmlAturan[i]) 
            {
                y=0;
                int z=0;
                while(z<kondisi.length)
                {
                    if(cekArray(z)==1)
                    { 
                        if((suhu[z].equals(decodedKromosom[i][x][y])||decodedKromosom[i][x][y]==null)&&(waktu[z].equals(decodedKromosom[i][x][y+1])||decodedKromosom[i][x][y+1]==null)&& (kondisi[z].equals(decodedKromosom[i][x][y+2])||decodedKromosom[i][x][y+2]==null)&&(kelembapan[z].equals(decodedKromosom[i][x][y+3])||decodedKromosom[i][x][y+3]==null)&&(hasil[z].equals(decodedKromosom[i][x][y+4])))
                        {
                            totBenar++;
                            lolos[l]=z;
                            l++;
                        }
                    }
                    z++;    
                }
                x++;
            }
            akurasi[i]=(totBenar/80)*100;
            i++;
        }
        i=0;
        while(i<=9)//penghitungan peluang
        {
            double hitungPeluang=akurasi[i]/(DoubleStream.of(akurasi).sum());
            peluang[i]=hitungPeluang;
            i++;
        }
        plotPeluang[0]=peluang[0];
        i=1;
        while(i<=9)//pembuatan plot peluang individu
            {
                plotPeluang[i]=plotPeluang[i-1]+peluang[i];
                i++;
            }
        i=0;
        double totPeluang=DoubleStream.of(peluang).sum();
        while(i<=9)//pemilihan ortu
            {
                double acakO=Math.random()*totPeluang;
                ortu[i]=cekPeluang(acakO);
                x=0;
                decodeKromosom(ortu[i]);
                jmlAturan[i]=jmlAturan(ortu[i]);
                while(x<tKromosom.length)
                {
                    y=0;
                    while(y<5)
                    {
                        decodedOrtu[i][x][y]=tKromosom[x][y];
                        y++;
                    }
                    x++;
                }
                i++;
            }
        i=0;
        while(i<=9)// hitung akurasi ortu
        {
            int l=0;
            double totBenar=0;
            x=0;
            lolos=new int[80];
            for(int it=0;it<lolos.length;it++)
            {
                lolos[it]=90;
            }
            while(x<jmlAturan[i]) 
            {
                y=0;
                int z=0;
                while(z<kondisi.length)
                {
                    if(cekArray(z)==1)
                    { 
                        if((suhu[z].equals(decodedOrtu[i][x][y])||decodedOrtu[i][x][y]==null)&&(waktu[z].equals(decodedOrtu[i][x][y+1])||decodedOrtu[i][x][y+1]==null)&& (kondisi[z].equals(decodedOrtu[i][x][y+2])||decodedOrtu[i][x][y+2]==null)&&(kelembapan[z].equals(decodedOrtu[i][x][y+3])||decodedOrtu[i][x][y+3]==null)&&(hasil[z].equals(decodedOrtu[i][x][y+4])))
                        {
                            totBenar++;
                            lolos[l]=z;
                            l++;
                        }
                    }
                    z++;    
                }
                x++;
            }
            ortuAkurasi[i]=(totBenar/80)*100;
            i++;
        }
        i=0;
        while(i<=9)//cross over,mutasi,hitung nilai next gen
        {
            
            CrossOver(ortu[i],ortu[i+1]);
            nextGenKromosom[i]=hasil1;
            x=0;
            decodeKromosom(nextGenKromosom[i]);
            jmlAturan[i]=jmlAturan(nextGenKromosom[i]);
            while(x<tKromosom.length)
            {
                y=0;
                while(y<5)
                {
                    decodedNextKromosom[i][x][y]=tKromosom[x][y];
                    y++;
                }
                x++;
            }
            nextGenKromosom[i+1]=hasil2;
            x=0;
            decodeKromosom(nextGenKromosom[i+1]);
            jmlAturan[i]=jmlAturan(nextGenKromosom[i+1]);
            while(x<tKromosom.length)
            {
                y=0;
                while(y<5)
                {
                    decodedNextKromosom[i+1][x][y]=tKromosom[x][y];
                    y++;
                }
                x++;
            }
            
            i=i+2;
        }
        i=0;
        while(i<=9)// hitung akurasi next gen
        {
            int l=0;
            double totBenar=0;
            x=0;
            lolos=new int[80];
            for(int it=0;it<lolos.length;it++)
            {
                lolos[it]=90;
            }
            while(x<jmlAturan[i]) 
            {
                y=0;
                int z=0;
                while(z<kondisi.length)
                {
                    if(cekArray(z)==1)
                    { 
                        if((suhu[z].equals(decodedNextKromosom[i][x][y])||decodedNextKromosom[i][x][y]==null)&&(waktu[z].equals(decodedNextKromosom[i][x][y+1])||decodedNextKromosom[i][x][y+1]==null)&& (kondisi[z].equals(decodedNextKromosom[i][x][y+2])||decodedNextKromosom[i][x][y+2]==null)&&(kelembapan[z].equals(decodedNextKromosom[i][x][y+3])||decodedNextKromosom[i][x][y+3]==null)&&(hasil[z].equals(decodedNextKromosom[i][x][y+4])))
                        {
                            totBenar++;
                            lolos[l]=z;
                            l++;
                        }
                    }
                    z++;    
                }
                x++;
            }
            nextAkurasi[i]=(totBenar/80)*100;
            i++;
        }
        pilihNextGen();
        bestKromosom();
//        System.out.println(bestAkurasi);
        int checker=1;
        while(checker<=499) //perulangan generasi
        {
            i=0;
            while(i<=9)// hitung akurasi
            {
                int l=0;
                double totBenar=0;
                x=0;
                lolos=new int[80];
                for(int it=0;it<lolos.length;it++)
                {
                    lolos[it]=90;
                }
                while(x<jmlAturan[i]) 
                {
                    y=0;
                    int z=0;
                    while(z<kondisi.length)
                    {
                        if(cekArray(z)==1)
                        { 
                            if((suhu[z].equals(decodedKromosom[i][x][y])||decodedKromosom[i][x][y]==null)&&(waktu[z].equals(decodedKromosom[i][x][y+1])||decodedKromosom[i][x][y+1]==null)&& (kondisi[z].equals(decodedKromosom[i][x][y+2])||decodedKromosom[i][x][y+2]==null)&&(kelembapan[z].equals(decodedKromosom[i][x][y+3])||decodedKromosom[i][x][y+3]==null)&&(hasil[z].equals(decodedKromosom[i][x][y+4])))
                            {
                                totBenar++;
                                lolos[l]=z;
                                l++;
                            }
                        }
                        z++;    
                    }
                    x++;
                }
                akurasi[i]=(totBenar/80)*100;
                i++;
            }
            i=0;
            while(i<=9)//penghitungan peluang
            {
                double hitungPeluang=akurasi[i]/(DoubleStream.of(akurasi).sum());
                peluang[i]=hitungPeluang;
                i++;
            }
            plotPeluang[0]=peluang[0];
            i=1;
            while(i<=9)//pembuatan plot peluang individu
                {
                    plotPeluang[i]=plotPeluang[i-1]+peluang[i];
                    i++;
                }
            i=0;
            totPeluang=DoubleStream.of(peluang).sum();
            while(i<=9)//pemilihan ortu
                {
                    double acakO=Math.random()*totPeluang;
                    ortu[i]=cekPeluang(acakO);
                    x=0;
                    decodeKromosom(ortu[i]);
                    jmlAturan[i]=jmlAturan(ortu[i]);
                    while(x<tKromosom.length)
                    {
                        y=0;
                        while(y<5)
                        {
                            decodedOrtu[i][x][y]=tKromosom[x][y];
                            y++;
                        }
                        x++;
                    }
                    i++;
                }
            i=0;
            while(i<=9)// hitung akurasi ortu
            {
                int l=0;
                double totBenar=0;
                x=0;
                lolos=new int[80];
                for(int it=0;it<lolos.length;it++)
                {
                    lolos[it]=90;
                }
                while(x<jmlAturan[i]) 
                {
                    y=0;
                    int z=0;
                    while(z<kondisi.length)
                    {
                        if(cekArray(z)==1)
                        { 
                            if((suhu[z].equals(decodedOrtu[i][x][y])||decodedOrtu[i][x][y]==null)&&(waktu[z].equals(decodedOrtu[i][x][y+1])||decodedOrtu[i][x][y+1]==null)&& (kondisi[z].equals(decodedOrtu[i][x][y+2])||decodedOrtu[i][x][y+2]==null)&&(kelembapan[z].equals(decodedOrtu[i][x][y+3])||decodedOrtu[i][x][y+3]==null)&&(hasil[z].equals(decodedOrtu[i][x][y+4])))
                            {
                                totBenar++;
                                lolos[l]=z;
                                l++;
                            }
                        }
                        z++;    
                    }
                    x++;
                }
                ortuAkurasi[i]=(totBenar/80)*100;
                i++;
            }
            i=0;
            while(i<=9)//cross over,mutasi,hitung nilai next gen
            {

                CrossOver(ortu[i],ortu[i+1]);
                nextGenKromosom[i]=hasil1;
                x=0;
                decodeKromosom(nextGenKromosom[i]);
                jmlAturan[i]=jmlAturan(nextGenKromosom[i]);
                while(x<tKromosom.length)
                {
                    y=0;
                    while(y<5)
                    {
                        decodedNextKromosom[i][x][y]=tKromosom[x][y];
                        y++;
                    }
                    x++;
                }
                nextGenKromosom[i+1]=hasil2;
                x=0;
                decodeKromosom(nextGenKromosom[i+1]);
                jmlAturan[i]=jmlAturan(nextGenKromosom[i+1]);
                while(x<tKromosom.length)
                {
                    y=0;
                    while(y<5)
                    {
                        decodedNextKromosom[i+1][x][y]=tKromosom[x][y];
                        y++;
                    }
                    x++;
                }

                i=i+2;
            }
            i=0;
            while(i<=9)// hitung akurasi next gen
            {
                int l=0;
                double totBenar=0;
                x=0;
                lolos=new int[80];
                for(int it=0;it<lolos.length;it++)
                {
                    lolos[it]=90;
                }
                while(x<jmlAturan[i]) 
                {
                    y=0;
                    int z=0;
                    while(z<kondisi.length)
                    {
                        if(cekArray(z)==1)
                        { 
                            if((suhu[z].equals(decodedNextKromosom[i][x][y])||decodedNextKromosom[i][x][y]==null)&&(waktu[z].equals(decodedNextKromosom[i][x][y+1])||decodedNextKromosom[i][x][y+1]==null)&& (kondisi[z].equals(decodedNextKromosom[i][x][y+2])||decodedNextKromosom[i][x][y+2]==null)&&(kelembapan[z].equals(decodedNextKromosom[i][x][y+3])||decodedNextKromosom[i][x][y+3]==null)&&(hasil[z].equals(decodedNextKromosom[i][x][y+4])))
                            {
                                totBenar++;
                                lolos[l]=z;
                                l++;
                            }
                        }
                        z++;    
                    }
                    x++;
                }
                nextAkurasi[i]=(totBenar/80)*100;
                i++;
            }
            tmpdecodedBestKromosom=decodedBestKromosom;
            tmpbestKromosom=bestKromosom;
            tmpBestAkurasi=bestAkurasi;
            pilihNextGen();
            bestKromosom();
            if(bestAkurasi<tmpBestAkurasi)  //pengecekan next gen
            {
                decodedBestKromosom=tmpdecodedBestKromosom;
                bestKromosom=tmpbestKromosom;
                bestAkurasi=tmpBestAkurasi;
            }
//            System.out.println("ke-"+checker+": "+bestAkurasi);
            checker++;
        }
        i=0;
        while(i<=19) //pengecekan kromosom terbaik dengan data uji
        {
            x=0;
            while(x<=9)
            {
                if((decodedBestKromosom[0][x][0]!=null)&&(decodedBestKromosom[0][x][1]!=null)&&(decodedBestKromosom[0][x][2]!=null)&&(decodedBestKromosom[0][x][3]!=null))
                {
                    if((Usuhu[i].equals(decodedBestKromosom[0][x][0]))||(decodedBestKromosom[0][x][0]==null)&&(Uwaktu[i].equals(decodedBestKromosom[0][x][1]))||(decodedBestKromosom[0][x][1]==null)&&(Ukondisi[i].equals(decodedBestKromosom[0][x][2]))||(decodedBestKromosom[0][x][2]==null)&&(Ukelembapan[i].equals(decodedBestKromosom[0][x][3]))||(decodedBestKromosom[0][x][3]==null))
                    {
                        terbang[i]=decodedBestKromosom[0][x][4];
                        x++;
                        break;
                    }
                }
                x++;
            }
            if (terbang[i]==null)
            {
                terbang[i]="tidak";
            }
            i++;
        }

        i=0;  // pembuatan file hasil data uji
        try {
            BufferedWriter writer =new BufferedWriter(new FileWriter(".\\src\\tupro2\\Hasil.txt"));
            while(i<=19)
            {
                writer.write(terbang[i]+"\n");
                i++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("======");
        System.out.println("Kromosom = "+bestKromosom);
        System.out.println("Akurasi = "+bestAkurasi);
        System.out.println("=======");
        for(i = 0; i < 10; i++ )
        {
            System.out.println(terbang[i]);
        }
    }   
}
