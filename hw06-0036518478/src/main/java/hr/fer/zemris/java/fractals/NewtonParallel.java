
package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that represents parallel implementation of Mandelbrot fractal using Newton-Raphson iteration
 */
public class NewtonParallel {

    public static void main(String[] args) {
        int prvi = -1;
        int drugi = -1;

        if (args.length == 2) {
            if (args[0].startsWith("--workers=")) {
                prvi = Integer.parseInt(args[0].substring(args[0].lastIndexOf("=") + 1));
            }
            if (args[1].startsWith("--tracks=")) {
                drugi = Integer.parseInt(args[1].substring(args[1].lastIndexOf("=") + 1));
            }

            if (args[0].startsWith("--tracks=")) {
                drugi = Integer.parseInt(args[0].substring(args[0].lastIndexOf("=") + 1));
            }
            if (args[1].startsWith("--workers=")) {
                prvi = Integer.parseInt(args[1].substring(args[1].lastIndexOf("=") + 1));
            }

            if (args[0].startsWith("-t")) {
                drugi = Integer.parseInt(args[1]);
            }
            if (args[0].startsWith("-w")) {
                prvi = Integer.parseInt(args[1]);
            }

        } else if (args.length == 4) {
            if (args[0].startsWith("-w")) {
                prvi = Integer.parseInt(args[1]);
            }
            if (args[2].startsWith("-t")) {
                drugi = Integer.parseInt(args[3]);
            }

            if (args[0].startsWith("-t")) {
                drugi = Integer.parseInt(args[1]);
            }
            if (args[2].startsWith("-w")) {
                prvi = Integer.parseInt(args[3]);
            }
        } else if (args.length == 1) {
            if (args[0].startsWith("--workers=")) {
                prvi = Integer.parseInt(args[0].substring(args[0].lastIndexOf("=") + 1));
            }
            if (args[0].startsWith("--tracks=")) {
                drugi = Integer.parseInt(args[1].substring(args[0].lastIndexOf("=") + 1));
            }
        }

        FractalViewer.show(new MojProducer(prvi, drugi));
    }

    public static class PosaoIzracuna implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        short[] data;
        AtomicBoolean cancel;
        double threshold;
        double rootThreshold;
        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        public PosaoIzracuna(double reMin, double reMax, double imMin,
                             double imMax, int width, int height, int yMin, int yMax,
                             int m, short[] data, AtomicBoolean cancel) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = cancel;
            this.threshold = 0.001;
            this.rootThreshold = 0.002;
        }

        public PosaoIzracuna() {

        }

        @Override
        public void run() {

            Mandelbrot.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
        }
    }

    public static class MojProducer implements IFractalProducer {

        int tracks;
        int workers;

        public MojProducer(int workers, int tracks) {
            this.workers = workers;
            this.tracks = tracks;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

            System.out.println("Zapocinjem izracun...");


            int maxIter = 16 * 16 * 16;

            int availableProc = Runtime.getRuntime().availableProcessors();
            int jobCount = 4 * availableProc;
            int yTracks = height / jobCount;

            if (workers == -1) {
                workers = availableProc;
            }

            if (tracks == -1) {
                tracks = jobCount;
            } else if (tracks > yTracks) {
                tracks = yTracks;
            }
            System.out.println("Using " + workers + " workers and " + tracks + " tracks");
            short[] data = new short[width * height];

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

            Thread[] radnici = new Thread[workers];
            for (int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(() -> {
                    while (true) {
                        PosaoIzracuna p;
                        try {
                            p = queue.take();
                            if (p == PosaoIzracuna.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
            }

            for (int i = 0; i < radnici.length; i++) {
                radnici[i].start();
            }

            for (int i = 0; i < tracks; i++) {
                int yMin = i * yTracks;
                int yMax = (i + 1) * yTracks - 1;
                if (i == tracks - 1) {
                    yMax = height - 1;
                }
                PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, maxIter, data, cancel);
                while (true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }
            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        radnici[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) maxIter, requestNo);

        }


    }
}

