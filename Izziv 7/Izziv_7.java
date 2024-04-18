import java.util.Scanner;

public class Izziv_7{

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int p = n + 1;
        int[] primitivni = null;

        while(true){
            if (prastevilo(p)){
                primitivni = primitivni(n, p);
                if (primitivni != null){
                    break;
                }
            }
            p++;
        }

        System.out.printf("%d:", p);
        for (int i = 0; i < primitivni.length; i++){
            System.out.printf(" %d", primitivni[i]);
        }
        System.out.println();

        int najmanjsi = primitivni[0];

        vandermondovaMatrika(n, p, najmanjsi);      
	}

    private static boolean prastevilo(int p){
        for (int i = 2; i < p; i++){
            if (p % i == 0){
                return false;
            }
        }
        return true;
    }

    private static int[] primitivni(int n, int p){
        boolean primitivni = true;
        int[] primitivniKoren = null;
        for (int i = 2; i < p; i++){
            primitivni = false;
            if (Math.pow(i, n) % p == 1){
                for (int j = 1; j < n; j++){
                    if (Math.pow(i, j) % p != 1){
                        primitivni = true;
                    } else {
                        primitivni = false;
                        break;
                    }
                }
            }
            if (primitivni){
                if (primitivniKoren == null){
                    primitivniKoren = new int[1];
                    primitivniKoren[0] = i;
                } else {
                    int[] temp = new int[primitivniKoren.length + 1];
                    for (int j = 0; j < primitivniKoren.length; j++){
                        temp[j] = primitivniKoren[j];
                    }
                    temp[primitivniKoren.length] = i;
                    primitivniKoren = temp;
                }
            }
        }
        return primitivniKoren;
    }

    private static void vandermondovaMatrika(int n, int p, int najmanjsi){
        int[][] matrika = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                int temp = 1;
                int potenca = i * j;
                for (int k = 0; k < potenca; k++){
                    temp = (temp * najmanjsi) % p;
                }
                //long temp2 = (long) Math.pow(najmanjsi, potenca) % p;
                matrika[i][j] = temp;
            }
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.printf("%2d ", matrika[i][j]);
            }
            System.out.println();
        }
    }
}