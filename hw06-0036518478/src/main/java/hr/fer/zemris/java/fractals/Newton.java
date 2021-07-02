package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that represents sequential implementation of Mandelbrot fractal using Newton-Raphson iteration
 */
public class Newton {
    public static void main(String[] args) {
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

        Scanner sc = new Scanner(System.in);
        int i = 1;

        List<Complex> roots = new ArrayList<>();
        List<Complex> roots2 = new ArrayList<>();

        System.out.print("Root " + i + ": ");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.equalsIgnoreCase("done")) {
                System.out.println("Image of fractal will appear shortly. Thank you.");
                break;
            }

            try {
                Complex c = parse(line);
                roots.add(c);
            } catch (NumberFormatException ex) {
                System.out.println("Entered wrong format of Complex number");
            }
            System.out.print("Root " + ++i + ": ");
        }
        sc.close();

        roots2.add(new Complex(1,0));
        roots2.add(new Complex(-1,1));
        roots2.add(new Complex(0,1));
        roots2.add(new Complex(0,-1));
        System.out.println(roots);
        FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE, roots2.toArray(new Complex[roots.size()]))));

    }

    public static class MojProducer implements IFractalProducer {

        ComplexRootedPolynomial polynomial;
        ComplexPolynomial derived;

        public MojProducer(ComplexRootedPolynomial polynomial) {
            this.polynomial = polynomial;
            this.derived = polynomial.toComplexPolynom();
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

            System.out.println("Zapocinjem izracun...");

            int maxIter = 16 * 16 * 16;
            int offset = 0;
            short[] data = new short[width * height];
            double convthreshold = 0.001;
            double rootThreshold = 0.002;

            for (int y = 0; y < height; y++) {
                if (cancel.get()) break;
                for (int x = 0; x < width; x++) {

                    Complex zn = mapToComplexPlain(x, y, x, width, y, height, reMin, reMax, imMin, imMax);
                    Complex znold;
                    double module;
                    int iters = 0;
                    do {

                        Complex numerator = polynomial.apply(zn);
                        Complex denominator = derived.derive().apply(zn);
                        Complex fraction = numerator.divide(denominator);
                        znold = zn.sub(fraction);
                        module = znold.sub(zn).module();
                        zn = znold;
                        iters++;
                    } while (iters < maxIter && module > convthreshold);
                    int index = polynomial.indexOfClosestRootFor(zn, rootThreshold);
                    System.out.println(index);
                    data[offset++] = (short) (index + 1);


                }
            }
            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) (derived.order() + 1), requestNo);
        }

        private Complex mapToComplexPlain(int x, int y, double xMin, double width,
                                          double yMin, double height, double reMin, double reMax, double imMin, double imMax) {
            double re = x / width * (reMax - reMin) + reMin;
            double im = (height - 1 - y) / height * (imMax - imMin) + imMin;
            return new Complex(re, im);
        }

    }


    public static Complex parse(String string) {
        string = string.replace(" ", "");

        if (!string.contains("i")) {
            double re = Double.parseDouble(string);
            return new Complex(re, 0);
        }

        String[] parts = string.split("i");

        double im = (parts.length < 2) ? 1 : Double.parseDouble(parts[1]);

        if (parts.length == 0 || parts[0].isEmpty()) {
            return new Complex(0, im);
        }

        String real = parts[0];
        int len = real.length();

        char sign = real.charAt(len - 1);
        if (sign != '+' && sign != '-') {
            throw new NumberFormatException("Invalid sign operator!");
        }
        double re = len != 1 ? Double.parseDouble(real.substring(0, len - 1)) : 0;
        return new Complex(re, sign == '+' ? im : -im);
    }
}