import java.util.Scanner;

public class Izziv_9{

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int [][] seznam_povezav = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                seznam_povezav[i][j] = -1;
            }
        }
        
        while (sc.hasNextInt()){
            int i = sc.nextInt();
            int j = sc.nextInt();
            int pretok = sc.nextInt();
            seznam_povezav[i][j] = pretok;
        }

        FordFulkerson2(seznam_povezav, n);
	}

    public static void FordFulkerson2(int[][] kapacitete, int n){
        int[][] pretoki = new int[n][n];
        String[] prejsne_oznaka = new String[n];
        int[] prejsne_vozlisce = new int[n];
        int[] pretok_oznaka = new int[n];
        int[] neobiskana_oznacena = new int[n];
        prejsne_oznaka[0] = "-";
        pretok_oznaka[0] = Integer.MAX_VALUE;
        neobiskana_oznacena[0] = 1;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                pretoki[i][j] = 0;
            }
        }
        int zakljuci = 0;

        while(true){
            if (zakljuci == 1){
                break;
            }
            zakljuci = 1;
            int i = 0;
            while(i != -1){
                neobiskana_oznacena[i] = 2;

                //1. označimo vozlišča po + povezavah
                for(int j = 0; j < n; j++){
                    if (kapacitete[i][j] <= 0){
                        continue;
                    }
                    if (neobiskana_oznacena[j] == 0 && pretoki[i][j] < kapacitete[i][j]){
                        //System.out.printf("Vozlisce %d -> %d\n", i, j);
                        prejsne_oznaka[j] = "+";
                        prejsne_vozlisce[j] = i;
                        pretok_oznaka[j] = Math.min(pretok_oznaka[i], kapacitete[i][j] - pretoki[i][j]);
                        neobiskana_oznacena[j] = 1;
                    }
                }

                //2. označimo vozlišča po - povezavah
                for(int j = 0; j < n; j++){
                    if (kapacitete[j][i] <= 0){
                        continue;
                    }
                    if (neobiskana_oznacena[j] == 0 && pretoki[j][i] > 0){
                        prejsne_oznaka[j] = "-";
                        prejsne_vozlisce[j] = i;
                        pretok_oznaka[j] = Math.min(pretok_oznaka[i], pretoki[j][i]);
                        neobiskana_oznacena[j] = 1;
                    }
                }

                if (neobiskana_oznacena[n-1] == 1){
                    int temp = n-1;
                    System.out.printf("%d: ", pretok_oznaka[temp]);
                    while(temp != 0){
                        System.out.printf("%d%s  ", temp, prejsne_oznaka[temp]);
                        if (prejsne_oznaka[temp].equals("+")){
                            pretoki[prejsne_vozlisce[temp]][temp] += pretok_oznaka[n-1];
                        }else{
                            //System.out.printf("Povezava: %d %d\n", prejsne_vozlisce[temp], temp);
                            //System.out.printf("Pretok prej: %d\n", pretoki[prejsne_vozlisce[temp]][temp]);
                            pretoki[temp][prejsne_vozlisce[temp]] -= pretok_oznaka[n-1];
                            //System.out.printf("Pretok potem: %d\n", pretoki[prejsne_vozlisce[temp]][temp]);
                        }
                        temp = prejsne_vozlisce[temp];
                    }
                    System.out.printf("0\n");
                    prejsne_oznaka = new String[n];
                    prejsne_oznaka[0] = "-";
                    pretok_oznaka = new int[n];
                    pretok_oznaka[0] = Integer.MAX_VALUE;
                    neobiskana_oznacena = new int[n];
                    neobiskana_oznacena[0] = 1;
                    zakljuci = 0;
                }
                i = neobiskana_poisci(neobiskana_oznacena);
            }
        }
    }

    public static int neobiskana_poisci(int[] neobiskana_oznacena){
        for (int i = 0; i < neobiskana_oznacena.length; i++){
            if (neobiskana_oznacena[i] == 1){
                return i;
            }
        }
        return -1;
    }
}