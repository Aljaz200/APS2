import java.util.Scanner;


class Matrix {


	private int[][] m;

	public int n; //only square matrices

	public Matrix(int n){

		this.n = n;

		m = new int[n][n];

	}


    //set value at i,j
	public void setV(int i, int j, int val){

		m[i][j] = val;

	}


    // get value at index i,j
	public int v(int i, int j){

		return m[i][j];

	}


    // return a square submatrix from this
	public Matrix getSubmatrix(int startRow, int startCol, int dim){

		Matrix subM = new Matrix(dim);

		for (int i = 0; i<dim ; i++ )

			for (int j=0;j<dim ; j++ )

				subM.setV(i,j, m[startRow+i][startCol+j]);



		return subM;

	}


    // write this matrix as a submatrix from b (useful for the result of multiplication)
	public void putSubmatrix(int startRow, int startCol, Matrix b){

		for (int i = 0; i<b.n ; i++ )

			for (int j=0;j<b.n ; j++ )

				setV(startRow+i,startCol+j, b.v(i,j));

	}


    // matrix addition
	public Matrix sum(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i< n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]+b.v(i, j));

			}

		}

		return c;

	}





    // matrix subtraction
	public Matrix sub(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i< n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]-b.v(i, j));

			}

		}

		return c;

	}



	//simple multiplication
	public Matrix mult(Matrix b){
        Matrix c = new Matrix(n);
        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                for(int k = 0; k<n;k++){
                    int prejsna = c.v(i, j);
                    int nova = m[i][k] * b.v(k, j);
                    c.setV(i, j, prejsna + nova);
                }
            }
        }
        return c;	
	}


    // Strassen multiplication
	public Matrix multStrassen(Matrix b, int cutoff){
        Matrix c = new Matrix(n);
        if (n <= cutoff) {
            c = mult(b);
        }else{
            Matrix a11 = getSubmatrix(0, 0, n/2);
            Matrix a12 = getSubmatrix(0, n/2, n/2);
            Matrix a21 = getSubmatrix(n/2, 0, n/2);
            Matrix a22 = getSubmatrix(n/2, n/2, n/2);

            Matrix b11 = b.getSubmatrix(0, 0, n/2);
            Matrix b12 = b.getSubmatrix(0, n/2, n/2);
            Matrix b21 = b.getSubmatrix(n/2, 0, n/2);
            Matrix b22 = b.getSubmatrix(n/2, n/2, n/2);

            Matrix m1 = a11.sum(a22).multStrassen(b11.sum(b22), cutoff);
            Matrix m2 = a21.sum(a22).multStrassen(b11, cutoff);
            Matrix m3 = a11.multStrassen(b12.sub(b22), cutoff);
            Matrix m4 = a22.multStrassen(b21.sub(b11), cutoff);
            Matrix m5 = a11.sum(a12).multStrassen(b22, cutoff);
            Matrix m6 = a21.sub(a11).multStrassen(b11.sum(b12), cutoff);
            Matrix m7 = a12.sub(a22).multStrassen(b21.sum(b22), cutoff);

            Matrix c11 = m1.sum(m4).sub(m5).sum(m7);
            Matrix c12 = m3.sum(m5);
            Matrix c21 = m2.sum(m4);
            Matrix c22 = m1.sub(m2).sum(m3).sum(m6);

            int sum_m1 = m1.sum();
            int sum_m2 = m2.sum();
            int sum_m3 = m3.sum();
            int sum_m4 = m4.sum();
            int sum_m5 = m5.sum();
            int sum_m6 = m6.sum();
            int sum_m7 = m7.sum();

            //System.out.println(n/2);

            System.out.println("m1: " + sum_m1);
            System.out.println("m2: " + sum_m2);
            System.out.println("m3: " + sum_m3);
            System.out.println("m4: " + sum_m4);
            System.out.println("m5: " + sum_m5);
            System.out.println("m6: " + sum_m6);
            System.out.println("m7: " + sum_m7);

            c.putSubmatrix(0, 0, c11);
            c.putSubmatrix(0, n/2, c12);
            c.putSubmatrix(n/2, 0, c21);
            c.putSubmatrix(n/2, n/2, c22);
        }

        return c;
	}

    public int sum(){
        int sum = 0;
        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                sum += m[i][j];
            }
        }
        return sum;
    }

    public void print(){
        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }


}




public class Izziv_6{



	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int mejna = sc.nextInt();

        Matrix a = new Matrix(n);
        Matrix b = new Matrix(n);

        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                a.setV(i, j, sc.nextInt());
            }
        }

        for(int i = 0; i<n;i++){
            for(int j = 0; j<n;j++){
                b.setV(i, j, sc.nextInt());
            }
        }

        Matrix c = a.multStrassen(b, mejna);
        c.print();	

	}



}
