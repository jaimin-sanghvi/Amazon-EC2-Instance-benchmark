/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud_assignment1;

/**
 *
 * @author jaimin
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CPU_Bench {

    
    public final static long iteration = 100000000;
    public final static int instrPerCycle =2;

    public CPU_Bench(String type) throws InterruptedException {

        int cas = Integer.parseInt(type);
        switch (cas) {
            case 1:
                iops1(1); // find iops for one thread
                break;
            case 2:
                iops2(2); // find iops for twon thread
                break;
            case 3:
                iops4(4); // find iops for three thread
                break;
            case 4:
                flops1(1); // find flops for one thread
                break;
            case 5:
                flops2(2); // find flops for one thread
                break;
            case 6:
                flops4(4); // find flops for one thread
                break;
            default:
                System.out.println("Please select correct option");
                System.exit(cas);
                break;
        }

    }

    private void iops1(int noOfThread) throws InterruptedException {

        long time = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);

        long itr = iteration / noOfThread;
        for (int j = 0; j < noOfThread; j++) {
            service.execute(new IntThread(itr));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        long endTime = System.nanoTime();
        double timeUsed = (endTime - time) / 1000000000.0;

        // Calculate iops for one thread
        double iop = ((iteration * 6) / timeUsed) * instrPerCycle;
        System.out.println("IOPS1=" + (iop));
        System.out.println("GIOPS1=" + (iop / 1000000000));
    }

    private void iops2(int noOfThread) throws InterruptedException {

        long time2 = System.nanoTime();
        ExecutorService service2 = Executors.newFixedThreadPool(noOfThread);

        long itr = iteration / noOfThread;
        for (int j = 0; j < noOfThread; j++) {
            service2.execute(new IntThread(itr));
        }

        service2.shutdown();
        service2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // Calculate iops for two thread
        long endTime = System.nanoTime();
        double timeUsed2 = (endTime - time2) / 1000000000.0;
        double iop2 = ((iteration * 6) / timeUsed2) * instrPerCycle;
        System.out.println("IOPS2=" + (iop2));
        System.out.println("GIOPS2=" + (iop2 / 1000000000));
    }

    private void iops4(int noOfThread) throws InterruptedException {

        long time4 = System.nanoTime();
        ExecutorService service4 = Executors.newFixedThreadPool(noOfThread);

        long itr = iteration / noOfThread;
        for (int j = 0; j < noOfThread; j++) {
            service4.execute(new IntThread((itr)));
        }

        service4.shutdown();
        service4.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // Calculate iops for three thread
        long endTime = System.nanoTime();
        double timeUsed4 = (endTime - time4) / 1000000000.0;
        double iop3 = ((iteration * 6) / timeUsed4) * instrPerCycle;
        System.out.println("IOPS4=" + (iop3));
        System.out.println("GIOPS4=" + (iop3 / 1000000000));
    }

    private void flops1(int noOfThread) throws InterruptedException {

        long time3 = System.nanoTime();
        ExecutorService service3 = Executors.newFixedThreadPool(noOfThread);

        long itr = iteration / noOfThread;

        for (int j = 0; j < noOfThread; j++) {
            service3.execute(new FloatThread(itr));
        }

        service3.shutdown();
        service3.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // Calculate flops for one thread
        long endTime = System.nanoTime();
        double timeUsed3 = (endTime - time3) / 1000000000.0;
        double flop3 = ((iteration * 5) / timeUsed3) * instrPerCycle;
        System.out.println("FLOPS1=" + (flop3));
        System.out.println("GFLOPS1=" + (flop3 / 1000000000));
    }

    private void flops2(int noOfThread) throws InterruptedException {

        long time2 = System.nanoTime();
        ExecutorService service4 = Executors.newFixedThreadPool(noOfThread);

        long itr = iteration / noOfThread;

        for (int j = 0; j < noOfThread; j++) {
            service4.execute(new FloatThread(itr));
        }

        service4.shutdown();
        service4.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // Calculate iops for two thread
        long endTime = System.nanoTime();
        double timeUsed2 = (endTime - time2) / 1000000000.0;
        double flop2 = ((iteration * 5) / timeUsed2) * instrPerCycle;
        System.out.println("GFLOPS2=" + (flop2));
        System.out.println("GFLOPS2=" + (flop2 / 1000000000));
    }

    private void flops4(int noOfThread) throws InterruptedException {
        long time4 = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);
        long itr = iteration / noOfThread;
        for (int j = 0; j < noOfThread; j++) {
            service.execute(new FloatThread(itr));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // Calculate iops for three thread
        long endTime = System.nanoTime();
        double timeUsed = (endTime - time4) / 1000000000.0;
        double flop4 = ((iteration * 5) / timeUsed) * instrPerCycle;
        System.out.println("FLOPS=" + (flop4));
        System.out.println("GFLOPS=" + (flop4 / 1000000000));
        
    }

    private class IntThread implements Runnable {

        public long iteration = 0;

        public IntThread(long iteration) {
            this.iteration = iteration;
        }

        @Override
        public void run() {
            for (int i = 0; i < this.iteration; i++) {
                int a = 20, b = 5;
                a += b;
                b -= a;
                a += b;
                b -= a;
                b += a;
                b += a;
            }
        }
    }

    private class FloatThread implements Runnable {

        public long iteration;

        public FloatThread(long iteration) {
            this.iteration = iteration;
        }

        @Override
        public void run() {
            for (int i = 0; i < this.iteration; i++) {
                float a = 20.5f, b = 5.2f;
                a += b;
                b -= a;
                a += b;
                b -= a;
                b += a;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CPU_Bench obj = new CPU_Bench(args[0]);
    }
}