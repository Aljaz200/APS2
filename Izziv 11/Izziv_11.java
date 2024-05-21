import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Izziv_11{

    static List<int[]> zaporedje = new ArrayList<>();
    static int[] indeksi;
    static int[] neodvisna_podmnozica;

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] povezave = new int[n][n];

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            povezave[a][b] = 1;
            povezave[b][a] = 1;
        }
        for (int i = 0; i < n; i++) {
            povezave[i][i] = 1;
        }


        int dolzina_grafa = 1 << n;

        long startTime = System.nanoTime();
        for(int i = 0; i <= n; i++){
            List<int[]> zaporedje1 = new ArrayList<>();
            kombinacije2(new int[n], n - 1, i, zaporedje1);
            for(int j = zaporedje1.size() - 1; j >= 0; j--){
                zaporedje.add(zaporedje1.get(j));
            }
        }
        long endTime = System.nanoTime();
        long duration1 = (endTime - startTime);
        //System.out.println("Cas: " + duration1 / 1000000 + "ms");

        indeksi = new int[dolzina_grafa];
        neodvisna_podmnozica = new int[dolzina_grafa];
        for (int i = 0; i < dolzina_grafa; i++) {
            neodvisna_podmnozica[i] = -1;
        }
        //System.out.println("Zaporedje velikosti: " + zaporedje.size());

        startTime = System.nanoTime();
        kromatksa_stevila2(povezave, n);
        endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //System.out.println("Cas zaporedje: " + duration1 / 1000000 + "ms");
        //System.out.println("Cas: " + duration / 1000000 + "ms");
    }

    private static void kombinacije2(int[] current, int start, int k, List<int[]> zaporedje1) {
        if (k == 0) {
            zaporedje1.add(current.clone());
            return;
        }

        for (int i = start; i >= k - 1; i--) {
            current[i] = 1;
            kombinacije2(current, i - 1, k - 1, zaporedje1);
            current[i] = 0;
        }
    }    

    public static void kromatksa_stevila2(int[][] povezave, int n){
        int velikost = 1 << n;
        int[] kromatska = new int[velikost];

        //System.out.println("{} : 0");
        int zap = 0;
        for(int[] arr : zaporedje){
            int[] graf = arr;
            //int[] neodvisna = neodvisna_mnozica2(graf, povezave, n, zap-1, zap);
            int min1 = neodvisna_mnozica3(graf, povezave, n, zap, kromatska);
            
            if(min1 == Integer.MAX_VALUE){
                min1 = kromatsko_stevilo(graf, povezave, n);
            }
            int indeks = vrni_stevilo(graf);
            if (indeks == 0){
                min1 = 0;
            }
            kromatska[indeks] = min1;
            indeksi[indeks] = zap;
            //System.out.println("Zap: " + zap + " Indeks: " + indeks);
            izpisi1(graf, min1);
            zap++;
        }
        int[] izpisano = new int[n];
        int[] zadnja_uporabljena = new int[n];
        for (int i = 0; i < n; i++) {
            zadnja_uporabljena[i] = 1;
        }
        izpisi_zadnje(zadnja_uporabljena, n, povezave, kromatska, izpisano);

    }

    private static int prestej_povezave(int[] graf, int[][] povezave, int n) {
        int stevilo = 0;
        for (int i = 0; i < n; i++) {
            if (graf[i] == 1) {
                for (int j = i + 1; j < n; j++) {
                    if (graf[j] == 1 && povezave[i][j] == 1) {
                        stevilo++;
                    }
                }
            }
        }
        return stevilo;
    }

    private static void izpisi1(int[] graf, int kromatsko) {
        System.out.print("{");
        int temp = 0;
        for (int i = 0; i < graf.length; i++) {
            if (graf[i] == 1) {
                System.out.print(i);
                temp = i;
                break;
            }
        }
        for (int i = temp + 1; i < graf.length; i++) {
            if (graf[i] == 1) {
                System.out.print("," + i);
            }
        }
        System.out.println("} : " + kromatsko);
    }

    private static void izpisi2(int[] graf) {
        System.out.print("{");
        int temp = 0;
        for (int i = 0; i < graf.length; i++) {
            if (graf[i] == 1) {
                System.out.print(i);
                temp = i;
                break;
            }
        }
        for (int i = temp + 1; i < graf.length; i++) {
            if (graf[i] == 1) {
                System.out.print("," + i);
            }
        }
        System.out.println("}");
    }

    private static int vrni_stevilo(int[] graf){
        int stevilo = 0;
        for (int i = 0; i < graf.length; i++) {
            stevilo <<= 1;
            stevilo |= graf[i];
        }
        return stevilo;
    }

    public static int[] neodvisna_mnozica2(int[] graf, int[][] povezave, int n, int prejsnje, int zap){
        for(int i = prejsnje + 1; i <= zap; i++){
            int[] temp = zaporedje.get(i);
            //System.out.println("Temp: " + i);
            //izpisi(temp);
            if (neodvisna_podmnozica[i] == 0) {
                continue;
            }
            boolean podmnozica = true;
            for(int j = 0; j < n; j++){
                if(temp[j] == 1 && graf[j] == 0){
                    podmnozica = false;
                    break;
                }
            }
            if (podmnozica) {
                //izpisi(temp);
                boolean neodvisna_mnozica = true;
                if (neodvisna_podmnozica[i] == 1) {
                    int[] neodvisna = new int[n];
                    for (int j = 0; j < n; j++) {
                        neodvisna[j] = graf[j] - temp[j];
                    }
                    return neodvisna;
                }else{
                    for (int j = 0; j < n; j++) {
                        if (temp[j] == 1) {
                            for (int k = j + 1 ; k < n; k++) {
                                if (temp[k] == 1 && povezave[j][k] == 1) {
                                    neodvisna_mnozica = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (neodvisna_mnozica) {
                        //System.out.println("Neodvisna mnozica");
                        //izpisi(temp);
                        int[] neodvisna = new int[n];
                        for (int j = 0; j < n; j++) {
                            neodvisna[j] = graf[j] - temp[j];
                        }
                        neodvisna_podmnozica[i] = 1;
                        return neodvisna;
                    }else{
                        neodvisna_podmnozica[i] = 0;
                    }
                }
            }
        }
        return null;
    }

    public static int neodvisna_mnozica3(int[] graf, int[][] povezave, int n, int zap, int[] kromatska){
        int minimalno = Integer.MAX_VALUE;
        for(int i = 1; i <= zap; i++){
            int[] temp = zaporedje.get(i);
            //System.out.println("Temp: " + i);
            //izpisi(temp);
            if (neodvisna_podmnozica[i] == 0) {
                continue;
            }
            boolean podmnozica = true;
            for(int j = 0; j < n; j++){
                if(temp[j] == 1 && graf[j] == 0){
                    podmnozica = false;
                    break;
                }
            }
            if (podmnozica) {
                //izpisi(temp);
                boolean neodvisna_mnozica = true;
                if (neodvisna_podmnozica[i] == 1) {
                    int[] neodvisna = new int[n];
                    for (int j = 0; j < n; j++) {
                        neodvisna[j] = graf[j] - temp[j];
                    }
                    int stevilo = vrni_stevilo(neodvisna);
                    stevilo = kromatska[stevilo] + 1;
                    if (stevilo < minimalno) {
                        minimalno = stevilo;
                    }
                }else{
                    for (int j = 0; j < n; j++) {
                        if (temp[j] == 1) {
                            for (int k = j + 1 ; k < n; k++) {
                                if (temp[k] == 1 && povezave[j][k] == 1) {
                                    neodvisna_mnozica = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (neodvisna_mnozica) {
                        //System.out.println("Neodvisna mnozica");
                        //izpisi(temp);
                        int[] neodvisna = new int[n];
                        for (int j = 0; j < n; j++) {
                            neodvisna[j] = graf[j] - temp[j];
                        }
                        neodvisna_podmnozica[i] = 1;
                        int stevilo = vrni_stevilo(neodvisna);
                        stevilo = kromatska[stevilo] + 1;
                        if (stevilo < minimalno) {
                            minimalno = stevilo;
                        }
                    }else{
                        neodvisna_podmnozica[i] = 0;
                    }
                }
            }
        }
        return minimalno;
    }

    public static void izpisi(int[] graf){
        for (int i = 0; i < graf.length; i++) {
            System.out.print(graf[i] + " ");
        }
        System.out.println();
    }

    public static int kromatsko_stevilo(int[] graf, int[][] povezave, int n) {
        int prvo = 0;
        for (int i = 0; i < n; i++) {
            if (graf[i] == 1) {
                prvo = i;
                break;
            }
        }
        int stevilo = 0;
        int[] sosedi = new int[n];
        int[] barve = new int[n];
        for (int i = 0; i < n; i++) {
            barve[i] = -1;
        }
        barve[prvo] = 0;
        for (int i = prvo+1; i < n; i++) {
            if (graf[i] == 1) {
                for (int j = 0; j < n; j++) {
                    if (graf[j] == 1 && povezave[i][j] == 1) {
                        if (barve[j] != -1) {
                            sosedi[barve[j]] = 1;
                        }
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (sosedi[j] == 0) {
                        barve[i] = j;
                        break;
                    }
                }
                for (int j = 0; j < n; j++) {
                    sosedi[j] = 0;
                }
                //izpisi(barve);
            }
        }
        for (int i = 0; i < n; i++) {
            if (graf[i] == 1) {
                if (barve[i] > stevilo) {
                    stevilo = barve[i];
                }
            }
        }
        
        return stevilo + 1;
    }

    public static void izpisi_zadnje(int[] zadnja_uporabljena, int n, int[][] povezave, int[] kromatska, int[] izpisano){
        boolean koncaj = true;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = zadnja_uporabljena[i] - 1;
            temp[i] = temp[i] * -1;
            temp[i] ^= izpisano[i];
        }
        int izpisii = vrni_stevilo(temp);
        if (izpisii != 0) {
            izpisi2(temp);
        }
        //izpisi(izpisano);
        for (int i = 0; i < n; i++) {
            izpisano[i] |= temp[i];
        }
        //izpisi(izpisano);
        for (int i = 0; i < n; i++) {
            if (zadnja_uporabljena[i] == 1) {
                koncaj = false;
                break;
            }
        }
        if (koncaj) {
            return;
        }
        int[] graf = new int[n];
        for (int i = 0; i < n; i++) {
            graf[i] = zadnja_uporabljena[i];
        }
        int zap = vrni_stevilo(graf);
        zap = indeksi[zap];
        int[] neodvisna = neodvisna_mnozica2(graf, povezave, n, 0, zap);
        //izpisi(graf);
        int min1 = Integer.MAX_VALUE;
        int temp1 = 0;
        while(neodvisna != null){
            int stevilo_grafa = vrni_stevilo(neodvisna);
            //System.out.println("Stevilo grafa: " + stevilo_grafa);
            temp1 = kromatska[stevilo_grafa] + 1;
            if(temp1 == kromatska[vrni_stevilo(graf)]){
                //System.out.println("Min1: " + min1);
                //izpisi(neodvisna);
                for (int k = 0; k < n; k++) {
                    zadnja_uporabljena[k] = neodvisna[k];
                }
                break;
            }
            for (int k = 0; k < n; k++) {
                neodvisna[k] = graf[k] - neodvisna[k];
            }
            stevilo_grafa = vrni_stevilo(neodvisna);
            if (stevilo_grafa == vrni_stevilo(graf)) {
                break;
            }
            stevilo_grafa = indeksi[stevilo_grafa];  
            neodvisna = neodvisna_mnozica2(graf, povezave, n, stevilo_grafa, zap);
        }
        izpisi_zadnje(zadnja_uporabljena, n, povezave, kromatska, izpisano);
    }

}