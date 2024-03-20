import java.util.Arrays;

public class Izziv_2 {

    private static long stevilo_primerjav = 0;

    public static void main(String[] args) {
        System.out.println("               Naključne tabele");
        System.out.println("               |  10^2  |  10^3  |   10^4   |   10^5");
        System.out.println("---------------+--------+--------+----------+---------");
        System.out.printf("prvi           | %6d | %6d | %8d | %8d %n", testiranje(1, 1, 100), testiranje(1, 1, 1000), testiranje(1, 1, 10000), testiranje(1, 1, 100000));
        System.out.printf("naključni      | %6d | %6d | %8d | %8d %n", testiranje(2, 1, 100), testiranje(2, 1, 1000), testiranje(2, 1, 10000), testiranje(2, 1, 100000));
        System.out.printf("mediana treh   | %6d | %6d | %8d | %8d %n", testiranje(3, 1, 100), testiranje(3, 1, 1000), testiranje(3, 1, 10000), testiranje(3, 1, 100000));
        System.out.printf("mediana median | %6d | %6d | %8d | %8d %n", testiranje(4, 1, 100), testiranje(4, 1, 1000), testiranje(4, 1, 10000), testiranje(4, 1, 100000));

        System.out.println();

        System.out.println("               Urejene tabele");
        System.out.println("               |  10^2  |  10^3  |   10^4   |   10^5");
        System.out.println("---------------+--------+--------+----------+---------");
        System.out.printf("prvi           | %6d | %6d | %8d | %8d %n", testiranje(1, 2, 100), testiranje(1, 2, 1000), testiranje(1, 2, 10000), testiranje(1, 2, 100000));
        System.out.printf("naključni      | %6d | %6d | %8d | %8d %n", testiranje(2, 2, 100), testiranje(2, 2, 1000), testiranje(2, 2, 10000), testiranje(2, 2, 100000));
        System.out.printf("mediana treh   | %6d | %6d | %8d | %8d %n", testiranje(3, 2, 100), testiranje(3, 2, 1000), testiranje(3, 2, 10000), testiranje(3, 2, 100000));
        System.out.printf("mediana median | %6d | %6d | %8d | %8d %n", testiranje(4, 2, 100), testiranje(4, 2, 1000), testiranje(4, 2, 10000), testiranje(4, 2, 100000));
    }

    private static int[] generateTable_urejena(int n) {
        int[] table = new int[n];
        for (int i = 1; i <= n; i++) {
            table[i-1] = i;
        }
        return table;
    }

    private static int[] generateTable_nakljucna(int n) {
        int[] table = new int[n];
        for (int i = 0; i < n; i++) {
            table[i] = (int) (Math.random() * n);
        }
        return table;
    }

    private static long testiranje(int strategija, int vrsta_tabele, int n){
        long primerjave = 0;
        if (vrsta_tabele == 1){
            for(int i = 0; i < 1000; i++){
                int[] tabela = generateTable_nakljucna(n);
                stevilo_primerjav = 0;
                int pivot = izbira_pivota(tabela, strategija);
                long temp = 0;
                for(int j = 0; j < n/10; j++){
                    int stevilka_k = (int) (Math.random() * n);
                    quickselect(tabela, stevilka_k, pivot, strategija);
                    temp += stevilo_primerjav;
                    stevilo_primerjav = 0;
                }
                temp = temp / (n/10);
                primerjave += temp;
            }
            primerjave = primerjave / 1000;
        } else {
            int[] tabela = generateTable_urejena(n);
            stevilo_primerjav = 0;
            int pivot = izbira_pivota(tabela, strategija);
            for(int j = 0; j < n/10; j++){
                int stevilka_k = (int) (Math.random() * n);
                quickselect(tabela, stevilka_k, pivot, strategija);
                primerjave += stevilo_primerjav;
                stevilo_primerjav = 0;
            }
            primerjave = primerjave / (n/10);
        }
        return primerjave;
    }

    private static void quickselect(int[] tabela, int k, int pivot, int strategija) {
        int m = 0;
        int v = 0;
        int e = 0;
        int[] manjsi_temp = new int[tabela.length];
        int[] vecji_temp = new int[tabela.length];
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] < pivot) {
                manjsi_temp[m] = tabela[i];
                m++;
            } else if (tabela[i] > pivot) {
                vecji_temp[v] = tabela[i];
                v++;
            } else {
                e++;
            }
            stevilo_primerjav++;
        }
        if (k < m) {
            int[] manjsi = new int[m];
            manjsi = Arrays.copyOfRange(manjsi_temp, 0, m);
            pivot = izbira_pivota(manjsi, strategija);
            quickselect(manjsi, k, pivot, strategija);
        } else if (k < m + e) {
            return;
        } else {
            int[] vecji = new int[v];
            vecji = Arrays.copyOfRange(vecji_temp, 0, v);
            pivot = izbira_pivota(vecji, strategija);
            quickselect(vecji, k - m - e, pivot, strategija);
        }
    }

    private static int izbira_pivota(int[] tabela, int strategija) {
        switch (strategija) {
            case 1:
                // prvi element v tabeli
                return tabela[0];
            case 2:
                // naključni element v tabeli
                return tabela[(int) (Math.random() * tabela.length)];
            case 3:
                // srednji element treh naključno izbranih
                int prvi = tabela[(int) (Math.random() * tabela.length)];
                int drugi = tabela[(int) (Math.random() * tabela.length)];
                int tretji = tabela[(int) (Math.random() * tabela.length)];
                stevilo_primerjav += 3;
                if (prvi < drugi){
                    if (drugi < tretji){
                        return drugi;
                    } else if (prvi < tretji){
                        return tretji;
                    } else {
                        return prvi;
                    }
                } else {
                    if (prvi < tretji){
                        return prvi;
                    } else if (drugi < tretji){
                        return tretji;
                    } else {
                        return drugi;
                    }
                }
            case 4:
                // mediana (pivot dobljen z algoritmom mediana median)
                return mediana_median(tabela);
            default:
                return 0;
        }
    }

    private static int mediana_median(int[] tabela) {
        if (tabela.length == 1){
            return tabela[0];
        }
        if (tabela.length < 5){
            Arrays.sort(tabela);
            // recimo, da je število primerjav enako dolžini tabele
            stevilo_primerjav += tabela.length;
            return tabela[tabela.length / 2];
        }
        int dolzina = tabela.length / 5;
        if (tabela.length % 5 != 0) {
            dolzina++;
        }
        int[] nova = new int [dolzina];
        for (int i = 0; i < dolzina; i++) {
            int[] temp = new int[5];
            for (int j = 0; j < 5; j++) {
                if (i*5 + j >= tabela.length){
                    Arrays.sort(temp);
                    // recimo, da je število primerjav enako dolžini tabele
                    stevilo_primerjav += j;
                    nova[i] = temp[j/2];
                    return mediana_median(nova);
                }
                temp[j] = tabela[i*5 + j];
            }
            // 6 primerjav za iskanje mediane za 5 števil
            if (temp[0] > temp[1]) {
                int zacasno = temp[0];
                temp[0] = temp[1];
                temp[1] = zacasno;
            }
            if (temp[2] > temp[3]) {
                int zacasno = temp[2];
                temp[2] = temp[3];
                temp[3] = zacasno;
            }
            if (temp[0] > temp[2]) {
                int zacasno1 = temp[0];
                int zacasno2 = temp[1];
                temp[0] = temp[2];
                temp[1] = temp[3];
                temp[2] = zacasno1;
                temp[3] = zacasno2;
            }
            if (temp[1] > temp[4]) {
                int zacasno = temp[1];
                temp[1] = temp[4];
                temp[4] = zacasno;
            }
            if (temp[2] > temp[4]) {
                int zacasno = temp[2];
                temp[2] = temp[4];
                temp[4] = zacasno;
            }
            if (temp[1] > temp[2]) {
                int zacasno = temp[1];
                temp[1] = temp[2];
                temp[2] = zacasno;
            }
            nova[i] = temp[2];
            stevilo_primerjav += 6;
        }
        return mediana_median(nova);
    }
}

// Izpisana tabela do 10^4, ker je izpis za 10^5 trajal veliko preveč časa
/*
               Naključne tabele
               |  10^2  |  10^3  |   10^4   |   10^5
---------------+--------+--------+----------+---------
prvi           |    266 |   2944 |    29648 |       x
naključni      |    266 |   2938 |    29888 |       x
mediana treh   |    238 |   2473 |    24926 |       x
mediana median |    333 |   3508 |    35477 |       x

               Urejene tabele
               |  10^2  |  10^3  |   10^4   |   10^5
---------------+--------+--------+----------+---------
prvi           |   2661 | 343113 | 33690071 |       x
naključni      |    272 |   2660 |    26110 |       x
mediana treh   |    237 |   2781 |    24421 |       x
mediana median |    448 |   4043 |    38129 |       x

*/