import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Izziv_12{

    public static int[][] tabela;

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] matrika = new int[n][n];

        for(int i = 0; i < n - 1; i++){
            for(int j = i; j < n - 1; j++){
                int a = sc.nextInt();
                matrika[i][j+1] = a;
                matrika[j+1][i] = a;
            }
        }

        tabela = new int[n][1 << (n-1)];
        boolean[] obiskani = new boolean[n];
        obiskani[0] = true;
        int min = Hammingovi_cikli(matrika, n, 0, 0, obiskani);
        System.out.println(min);
        //for(int i = 0; i < n; i++){
        //    System.out.println(Arrays.toString(tabela[i]));
        //}
        
    }

    public static int Hammingovi_cikli(int[][] matrika, int n, int prejsnji, int stevec, boolean[] obiskani){
        if(stevec == n - 1){
            tabela[prejsnji][0] = matrika[prejsnji][0];
            return matrika[prejsnji][0];
        }
        

        int min = Integer.MAX_VALUE;

        for(int i = 1; i < n; i++){
            if(!obiskani[i]){
                obiskani[i] = true;
                int trenutna = Hammingovi_cikli(matrika, n, i, stevec + 1, obiskani);
                obiskani[i] = false;
                if(trenutna + matrika[prejsnji][i] < min){
                    min = trenutna + matrika[prejsnji][i];
                }
            }
        }
        int indeks = indeks(n, obiskani);
        tabela[prejsnji][indeks] = min;
        return min;

    }

    public static int indeks(int n, boolean[] obiskani){
        int indeks = 0;
        //System.out.println(Arrays.toString(obiskani));
        for(int i = 0; i < n; i++){
            if(!obiskani[i]){
                //System.out.println(i);
                indeks = (indeks << 1) + 1;
            }else{
                indeks = (indeks << 1);
            }
        }
        //System.out.println(indeks);
        return indeks;
    }
}