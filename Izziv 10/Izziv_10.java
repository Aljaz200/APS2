import java.util.Scanner;

public class Izziv_10{

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] povezave = new int[n][n];

        while(sc.hasNextInt()){
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            povezave[a][b] = c;
        }

        int[][] razdalje = BelmanFord(povezave, n);

        for(int i = 0; i < n; i++){
            System.out.print("h" + i + ":");
            for(int j = 0; j < n; j++){
                if(razdalje[i][j] == Integer.MAX_VALUE){
                    System.out.print(" Inf");
                }else{
                    System.out.print(" " + razdalje[i][j]);
                }
            }
            if (i != n-1){
                System.out.print("\n");
            }
        }
	}

    public static int[][] BelmanFord(int[][] povezave, int n){
        int[][] razdalje = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                razdalje[i][j] = Integer.MAX_VALUE;
            }
        }
        razdalje[0][0] = 0;

        int[] obiskana = new int[n];
        obiskana[0] = 1;

        for(int i = 1; i < n; i++){
            int[] temp = new int[n];
            for(int j = 0; j < n; j++){
                if (obiskana[j] == 1){
                    for(int k = 0; k < n; k++){
                        if(k == -1){
                            razdalje[i][k] = 0;
                        }else if(povezave[j][k] != 0){
                            if (i == 1){
                                razdalje[i][k] = povezave[j][k];
                            }else{
                                if (razdalje[i][k] == Integer.MAX_VALUE){
                                    razdalje[i][k] = razdalje[i-1][k];
                                }
                                razdalje[i][k] = Math.min(razdalje[i][k], razdalje[i-1][j] + povezave[j][k]);
                                //System.out.println("razdalje[" + (i-1) + "][" + k + "]:" + razdalje[i-1][k] + " razdalje[" + (i-1) + "][" + j + "]:" + razdalje[i-1][j] + " povezave[" + j + "][" + k + "]:" + povezave[j][k] + " razdalje[" + i + "][" + k + "]:" + razdalje[i][k]);
                            }
                            temp[k] = 1;
                        }else{
                            if (razdalje[i][k] == Integer.MAX_VALUE){
                                razdalje[i][k] = razdalje[i-1][k];
                            }
                        }
                    }
                }
            }
            obiskana = temp;
            //System.out.print("i:" + i + "   ");
            //for(int j = 0; j < n; j++){
            //    System.out.print(obiskana[j] + " ");
            //}
            //System.out.print("\n");
        }
        return razdalje;
    
    }   
}