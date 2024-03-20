import java.util.Random;

public class Izziv_1 {
    public static void main(String[] args) {
        int n = 1000;
        // testiranje (ker se prvič izvede počasneje)
        for (int i = 1; i < 10; i++) {
            long temp = timeLinear(n*i);
            temp = timeBinary(n*i);
        }
        // konec testiranja
        System.out.println("   n     |    linearno  |   dvojisko   |");
        System.out.println("---------+--------------+---------------");
        while (n <= 100000){
            System.out.printf("%8d | %12d | %12d %n", n, timeLinear(n), timeBinary(n));
            n += 1000;
        }
    }

    private static int[] generateTable(int n) {
        int[] table = new int[n];
        for (int i = 1; i <= n; i++) {
            table[i] = i;
        }
        return table;
    }

    private static int findLinear (int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    private static int findBinary (int[] a, int l, int r, int v) {
        // l = 1, r = a.length - 1
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m] < v) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        if (a[l] == v) {
            return l;
        }
        return -1;
    }

    private static long timeLinear(int n){
        int[] a = generateTable(n);
        Random rand = new Random();
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int random_int = rand.nextInt(n) + 1;
            int indeks = findLinear(a, random_int);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    private static long timeBinary(int n){
        int[] a = generateTable(n);
        Random rand = new Random();
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int random_int = rand.nextInt(n) + 1;
            int indeks = findBinary(a, 0, n-1, random_int);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }
}

/* 
e) 1. časi so drugačni zaradi različnih procesorjev v računalnikih
   2. hitrejši je binarni iskalni algoritem
   3. Linerni algoritem bi lahko bil hitrejši od binarnega, če bi iskali v tabeli, ki ni urejena po velikosti ali pa če bi
        iskaali elemente, ki so na začetku tabele
   4. Linearni algoritem ima časovno zahtevnost O(n), binarni pa O(log(n))
   5. Časovna odvisnost dvojiškega iskanja je logaritemmska, mislim da je to bližje konstanti kot linearni časovni odvisnosti
*/