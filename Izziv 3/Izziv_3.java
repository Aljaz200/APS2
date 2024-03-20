import java.util.Arrays;

public class Izziv_3 {

    private static long stevilo_primerjav = 0;
    private static int[] table;

    public static void main(String[] args) {
        
        for(int i = 1; i <= 19; i++){
            for(int j = 0; j < i; j++){
                int d = 1<<(i-j);
                Timsort(i, j, d);
                System.out.printf(stevilo_primerjav + " ");
            }
            System.out.println();
        }

    }

    private static void generateTable(int n, int k, int d) {
        int counter = 0;
        int dolzina = 1<<n;
        k = 1<<k;
        //System.out.println(dolzina + " " + k + " " + d);
        table = new int[dolzina];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < d; j++) {
                table[counter] = j + 1;
                counter += 1;
            }
        }
    }

    private static void Timsort(int n, int k, int d) {
        stevilo_primerjav = 0;
        generateTable(n, k, d);
        //printTable();
        int stevilo_cet = 1;
        int prejsno = Integer.MIN_VALUE;
        int[] temp = new int[1<<n];
        int counter = 1;
        for (int i = 1; i < 1<<n; i++) {
            if (table[i] > prejsno) { //strogo naraščajoče
                counter += 1;
            }else{
                temp[stevilo_cet-1] = counter;
                counter = 1;
                stevilo_cet += 1;
            }
            prejsno = table[i];
            stevilo_primerjav += 1;
        }
        temp[stevilo_cet-1] = counter;
        int[] dolzine_cet = Arrays.copyOf(temp, stevilo_cet);

        int[] stack = new int[stevilo_cet + 1];
        int h = 0;
        int velikost = 0;
        int i = 0;

        while (i < stevilo_cet){
            
            stack[h] = dolzine_cet[i]; // d je dolžina čete v našem primeru
            h += 1;
            velikost += dolzine_cet[i];
            i += 1;

            while (true) {
                if (h >= 3 && stack[h-1] >= stack[h-3]){
                    //zlij stack[h-2] in stack[h-3]
                    int prvi_indeks = velikost - stack[h-1] - stack[h-2] - stack[h-3];
                    int prva_dolzina = stack[h-3];
                    int drugi_indeks = velikost - stack[h-1] - stack[h-2];
                    int druga_dolzina = stack[h-2];
                    zlij(prvi_indeks, prva_dolzina, drugi_indeks, druga_dolzina);
                    stack[h-3] += stack[h-2];
                    stack[h-2] = stack[h-1];
                }else if (h >= 2 && stack[h-1] >= stack[h-2]){
                    //zlij stack[h-1] in stack[h-2]
                    int prvi_indeks = velikost - stack[h-1] - stack[h-2];
                    int prva_dolzina = stack[h-2];
                    int drugi_indeks = velikost - stack[h-1];
                    int druga_dolzina = stack[h-1];
                    zlij(prvi_indeks, prva_dolzina, drugi_indeks, druga_dolzina);
                    stack[h-2] += stack[h-1];
                }else if (h >= 3 && stack[h-1] + stack[h-2] >= stack[h-3]){
                    //zlij stack[h-1] in stack[h-2]
                    int prvi_indeks = velikost - stack[h-1] - stack[h-2];
                    int prva_dolzina = stack[h-2];
                    int drugi_indeks = velikost - stack[h-1];
                    int druga_dolzina = stack[h-1];
                    zlij(prvi_indeks, prva_dolzina, drugi_indeks, druga_dolzina);
                    stack[h-2] += stack[h-1];
                }else if (h >= 4 && stack[h-2] + stack[h-3] >= stack[h-4]){
                    //zlij stack[h-1] in stack[h-2]
                    int prvi_indeks = velikost - stack[h-1] - stack[h-2];
                    int prva_dolzina = stack[h-2];
                    int drugi_indeks = velikost - stack[h-1];
                    int druga_dolzina = stack[h-1];
                    zlij(prvi_indeks, prva_dolzina, drugi_indeks, druga_dolzina);
                    stack[h-2] += stack[h-1];
                }else{
                    break;
                }
                h -= 1;
            }
        }

        while (h > 1) {
            //zlij stack[h-1] in stack[h-2]
            int prvi_indeks = velikost - stack[h-1] - stack[h-2];
            int prva_dolzina = stack[h-2];
            int drugi_indeks = velikost - stack[h-1];
            int druga_dolzina = stack[h-1];
            zlij(prvi_indeks, prva_dolzina, drugi_indeks, druga_dolzina);
            stack[h-2] += stack[h-1];
            h -= 1;
        }
        
    }

    private static void zlij(int prvi_indeks, int prva_dolzina, int drugi_indeks, int druga_dolzina){
        int [] temp = new int[prva_dolzina + druga_dolzina];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < prva_dolzina && j < druga_dolzina){
            if (table[prvi_indeks + i] <= table[drugi_indeks + j]){
                temp[k] = table[prvi_indeks + i];
                i += 1;
            }else{
                temp[k] = table[drugi_indeks + j];
                j += 1;
            }
            k += 1;
            stevilo_primerjav += 1;
        }

        while (i < prva_dolzina){
            temp[k] = table[prvi_indeks + i];
            i += 1;
            k += 1;
        }

        while (j < druga_dolzina){
            temp[k] = table[drugi_indeks + j];
            j += 1;
            k += 1;
        }

        for (int l = 0; l < prva_dolzina + druga_dolzina; l++){
            table[prvi_indeks + l] = temp[l];
        }
    }

    private static void printTable(){
        for (int i = 0; i < table.length; i++){
            System.out.print(table[i] + " ");
        }
        System.out.println();
    }

}

/*
1
3 6
7 14 19
15 30 43 51
31 62 91 115 127
63 126 187 243 287 303
127 254 379 499 607 687 703
255 510 763 1011 1247 1455 1599 1599
511 1022 1531 2035 2527 2991 3391 3647 3583
1023 2046 3067 4083 5087 6063 6975 7743 8191 7935
2047 4094 6139 8179 10207 12207 14143 15935 17407 18175 17407
4095 8190 12283 16371 20447 24495 28479 32319 35839 38655 39935 37887
8191 16382 24571 32755 40927 49071 57151 65087 72703 79615 84991 87039 81919
16383 32766 49147 65523 81887 98223 114495 130623 146431 161535 175103 185343 188415 176127
32767 65534 98299 131059 163807 196527 229183 261695 293887 325375 355327 381951 401407 405503 376831
65535 131070 196603 262131 327647 393135 458559 523839 588799 653055 715775 775167 827391 864255 868351 802815
131071 262142 393211 524275 655327 786351 917311 1048127 1178623 1308415 1436671 1561599 1679359 1781759 1851391 1851391 1703935
262143 524286 786427 1048563 1310687 1572783 1834815 2096703 2358271 2619135 2878463 3134463 3383295 3616767 3817471 3948543 3932159 3604479
524287 1048574 1572859 2097139 2621407 3145647 3669823 4193855 4717567 5240575 5762047 6280191 6791167 7286783 7749631 8142847 8388607 8323071 7602175
*/