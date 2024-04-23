import java.util.Scanner;

public class Izziv_8{

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int n_soda = n*2 -1;
        n_soda = potencadva(n_soda);

        Complex[] c = new Complex[n_soda];
        Complex[] d = new Complex[n_soda];

        for (int i = 0; i < n; i++) {
            c[i] = new Complex(sc.nextInt(), 0);
        }
        for (int i = 0; i < n; i++) {
            d[i] = new Complex(sc.nextInt(), 0);
        }
        for (int i = n; i < n_soda; i++) {
            c[i] = new Complex(0, 0);
            d[i] = new Complex(0, 0);
        }
        //System.out.println(n_soda);

        c = recursiveFFT(c, 1);
        d = recursiveFFT(d, 1);
        Complex[] novo = new Complex[n_soda];
        for (int i = 0; i < n_soda; i++) {
            novo[i] = c[i].times(d[i]);
        }
        novo = recursiveFFT(novo, -1);
        Complex temp = new Complex(n_soda, 0);
        for (int i = 0; i < n_soda; i++) {
            novo[i] = novo[i].divides(temp);
        }
        for (int i = 0; i < n_soda; i++) {
            System.out.print(novo[i].toString() + " ");
        }
        System.out.println();

	}

    public static int potencadva(int n) {
        int i = 1;
        while (i <= n) {
            i = i * 2;
        }
        return i;
    }

    public static Complex[] recursiveFFT(Complex[] a, int inverse) {
        int n = a.length;
        if (n == 1){
            return a;
        }
        /*
        if (n % 2 != 0) {
            n = n + 1;
            Complex[] temp = new Complex[n];
            for (int i = 0; i < a.length; i++) {
                temp[i] = a[i];
            }
            temp[n - 1] = new Complex(0, 0);
            a = temp;
        }*/

        Complex[] soda = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            soda[k] = a[2*k];
        }
        Complex[] ys = recursiveFFT(soda, inverse);

        Complex[] liha = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            liha[k] = a[2*k + 1];
        }
        Complex[] yl = recursiveFFT(liha, inverse);

        double temp = inverse*2*Math.PI/n;
        Complex w = new Complex(0, temp);
        w = w.exp();
        Complex wk = new Complex(1, 0);
        Complex[] y = new Complex[n];

        for (int k = 0; k < n/2; k++) {
            y[k] = ys[k].plus(wk.times(yl[k]));
            y[k + n/2] = ys[k].minus(wk.times(yl[k]));
            wk = wk.times(w);
        }

        for(int i = 0; i < n; i++){
            System.out.print(y[i].toString() + " ");
        }
        System.out.println();

        return y;
    }
}

class Complex{
	double re;
	double im;

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
    	double tRe = (double)Math.round(re * 100000) / 100000;
    	double tIm = (double)Math.round(im * 100000) / 100000;
        if (tIm == 0) return tRe + "";
        if (tRe == 0) return tIm + "i";
        if (tIm <  0) return tRe + "-" + (-tIm) + "i";
        return tRe + "+" + tIm + "i";
    }

	public Complex conj() {
		return new Complex(re, -im);
	}

    // sestevanje
    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // odstevanje
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // mnozenje z drugim kompleksnim stevilo
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // mnozenje z realnim stevilom
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // reciprocna vrednost kompleksnega stevila
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // deljenje
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // e^this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }


    //potenca komplesnega stevila
    public Complex pow(int k) {

    	Complex c = new Complex(1,0);
    	for (int i = 0; i <k ; i++) {
			c = c.times(this);
		}
    	return c;
    }

}