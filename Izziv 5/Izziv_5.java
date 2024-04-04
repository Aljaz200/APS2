import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Izziv_5 {

    public static void main(String[] args) {
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()){
            temp.add(sc.nextInt());
        }

        int[] table = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++){
            table[i] = temp.get(i);
        }

        int max = NajvecjiSum(table);      

    }

    private static int NajvecjiSum(int[] table){
        if (table.length == 1){
            System.out.print("[" + table[0] + "]: " + table[0] + "\n");
            return table[0];
        }

        int dolzina1 = table.length/2;
        if (dolzina1 * 2 != table.length){
            dolzina1++;
        }
        int table1[] = Arrays.copyOfRange(table, 0, dolzina1);
        int table2[] = Arrays.copyOfRange(table, dolzina1, table.length);

        int max1 = NajvecjiSum(table1);
        int max2 = NajvecjiSum(table2);
        int max3 = Math.max(max1, max2);

        int sum = table1[dolzina1 - 1] + table2[0];
        int maxSum = sum;
        //System.out.println("sum: " + sum);

        for (int i = dolzina1 - 2; i >= 0; i--){
            int temp = table1[i];
            sum += temp;
            if (sum > maxSum){
                maxSum = sum;
            }            
        }
        sum = maxSum;

        for (int i = 1; i < table2.length; i++){
            int temp = table2[i];
            sum += temp;
            if (sum > maxSum){
                maxSum = sum;
            }            
        }
        max3 = Math.max(max3, maxSum);
        
        System.out.print("[");
        for (int i = 0; i < table.length - 1; i++){
            System.out.print(table[i] + ", ");            
        }
        System.out.print(table[table.length - 1] + "]: ");
        System.out.printf("%d\n", max3);

        return max3;
    }
}