import java.util.Arrays;

public class Izziv_4 {

    private static long stevilo_primerjav = 0;
    private static long stevilo_zamenjav = 0;
    private static int[] table;

    public static void main(String[] args) {
        // čas
        long[] cas_random = new long[6];
        long[] cas_urejen = new long[6];
        // stevilo_primerjav
        long[] stevilo_primerjav_random = new long[6];
        long[] stevilo_primerjav_urejen = new long[6];
        // stevilo_zamenjav
        long[] stevilo_zamenjav_random = new long[6];
        long[] stevilo_zamenjav_urejen = new long[6];

        for(int j = 0; j < 1000; j++){
            // dodano za bolj natančne rezultate
            for (int i = 5; i <= 10; i++){
                // random tabela
                stevilo_primerjav = 0;
                stevilo_zamenjav = 0;
                generateTable(i, false);
                long startTime = System.nanoTime();
                DualPivotQuicksort(0, table.length - 1);
                long endTime = System.nanoTime();
                cas_random[i - 5] += endTime - startTime;
                stevilo_primerjav_random[i - 5] += stevilo_primerjav;
                stevilo_zamenjav_random[i - 5] += stevilo_zamenjav;

                // urejena tabela
                stevilo_primerjav = 0;
                stevilo_zamenjav = 0;
                generateTable(i, true);
                startTime = System.nanoTime();
                DualPivotQuicksort(0, table.length - 1);
                endTime = System.nanoTime();
                cas_urejen[i - 5] += endTime - startTime;
                stevilo_primerjav_urejen[i - 5] += stevilo_primerjav;
                stevilo_zamenjav_urejen[i - 5] += stevilo_zamenjav;
            }
        }

        for(int i = 0; i < 6; i++){
            // povprečje
            cas_random[i] /= 1000;
            cas_urejen[i] /= 1000;
            stevilo_primerjav_random[i] /= 1000;
            stevilo_primerjav_urejen[i] /= 1000;
            stevilo_zamenjav_random[i] /= 1000;
            stevilo_zamenjav_urejen[i] /= 1000;
        }

        System.out.println("Čas");
        System.out.printf("%-20s%-15s%-15s\n", "Dolžina seznama", "Random tabela", "Urejena tabela");
        for (int i = 0; i < 6; i++){
            int dolzina = 1 << (i + 5);
            System.out.printf("%-20d%-15d%-15d\n", dolzina, cas_random[i], cas_urejen[i]);
        }
        System.out.println();

        System.out.println("Število zamenjav");
        System.out.printf("%-20s%-15s%-15s\n", "Dolžina seznama", "Random tabela", "Urejena tabela");
        for (int i = 0; i < 6; i++){
            int dolzina = 1<<(i + 5);
            System.out.printf("%-20d%-15d%-15d\n", dolzina, stevilo_zamenjav_random[i], stevilo_zamenjav_urejen[i]);
        }
        System.out.println();

        System.out.println("Število primerjav");
        System.out.printf("%-20s%-15s%-15s\n", "Dolžina seznama", "Random tabela", "Urejena tabela");
        for (int i = 0; i < 6; i++){
            int dolzina = 1<<(i + 5);
            System.out.printf("%-20d%-15d%-15d\n", dolzina, stevilo_primerjav_random[i], stevilo_primerjav_urejen[i]);
        }

    }

    private static void generateTable(int n, boolean urejena) {
        int dolzina = 1<<n;
        table = new int[dolzina];
        if (urejena){
            for (int i = 0; i < dolzina; i++){
                table[i] = i + 1;
            }
        }else{
            for (int i = 0; i < dolzina; i++){
                table[i] = (int)(Math.random() * dolzina) + 1;
            }
        }
    }

    private static void DualPivotQuicksort(int left, int right){
        if (right - left >= 1){
            int p; int q;
            if (table[left] < table[right]){
                p = table[left];
                q = table[right];
            }else{
                p = table[right];
                q = table[left];
                //swap(left, right);
            }
            stevilo_primerjav++;
            int l = left + 1;
            int g = right - 1;
            int k = l;
            while (k <= g){
                if (table[k] < p){
                    stevilo_primerjav++;
                    swap(k, l);
                    l++;
                }else if (table[k] >= q){
                    stevilo_primerjav += 2;
                    while (table[g] > q && k < g){
                        stevilo_primerjav++;
                        g--;
                    }
                    stevilo_primerjav++;
                    swap(k, g);
                    g--;
                    if (table[k] < p){
                        swap(k, l);
                        l++;
                    }
                    stevilo_primerjav++;
                }else{
                    stevilo_primerjav += 2;
                }
                k++;
            }
            l--;
            g++;
            //printTable();
            table[left] = table[l];
            table[l] = p;
            table[right] = table[g];
            table[g] = q;
            stevilo_zamenjav += 2;
            //System.out.println("stevilo_zamenjav: " + stevilo_zamenjav + " stevilo_primerjav: " + stevilo_primerjav);
            //printTable();
            DualPivotQuicksort(left, l - 1);
            DualPivotQuicksort(l + 1, g - 1);
            DualPivotQuicksort(g + 1, right);
        }
    }

    private static void swap(int i, int j){
        stevilo_zamenjav++;
        int temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }

    private static void printTable(){
        for (int i = 0; i < table.length; i++){
            System.out.print(table[i] + " ");
        }
        System.out.println();
    }

}

/*
Čas
Dolžina seznama     Random tabela  Urejena tabela
32                  739            559
64                  1508           1855
128                 3377           6485
256                 7569           23347
512                 16604          88505
1024                36525          345665

Število zamenjav
Dolžina seznama     Random tabela  Urejena tabela
32                  63             32
64                  152            64
128                 352            128
256                 816            256
512                 1825           512
1024                4079           1024

Število primerjav
Dolžina seznama     Random tabela  Urejena tabela
32                  143            496
64                  362            2016
128                 884            8128
256                 2099           32640
512                 4873           130816
1024                11056          523776
*/