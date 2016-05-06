/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud_assignment1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaimin
 */
public class Disk_Benchmark {

    public double laten[];
    public double throughp[];
    public int noOfThread = 0;
    public boolean flag1 = false;
    public boolean flag2 = false;
    public boolean flag3 = false;
    public boolean flag4 = false;

    public Disk_Benchmark(String thread, String blockSize) throws IOException, InterruptedException {

        int blocks = Integer.parseInt(blockSize);
        this.noOfThread = Integer.parseInt(thread);
        laten = new double[noOfThread];
        throughp = new double[noOfThread];

        // create an service object to run thread concurrently.
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);
        for (int j = 0; j < noOfThread; j++) {
            service.execute(new WorkerThread(blocks, flag1, flag2, flag3, flag4));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    }

    public class WorkerThread implements Runnable {

        private int blockSize = 0;
        public boolean flag1, flag2, flag3, flag4;

        public WorkerThread(int blockSize, boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
            this.blockSize = blockSize;
            this.flag1 = flag1;
            this.flag2 = flag2;
            this.flag3 = flag3;
            this.flag4 = flag4;
        }

        @Override
        public void run() {
            try {
                sequential_write(blockSize); // find performance for sequential write
                System.out.println();
                sequential_read(blockSize); // find performance for read
                System.out.println();
                random_write(blockSize); // find performance for random write
                System.out.println();
                random_read(blockSize); // find performance for random read
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Disk_Benchmark.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Disk_Benchmark.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        Disk_Benchmark objD = new Disk_Benchmark(args[0], args[1]);
    }

    private void sequential_read(int blockSize) throws FileNotFoundException, IOException {

        int iteration = 0;

        if (blockSize == 1) {
            iteration = 5000000;
        } else if (blockSize == 1024) {
            iteration = 5000;
        } else {
            iteration = 100;
        }

        byte[] b = new byte[blockSize];

        File objFile = new File("seq_test_" + blockSize + ".txt");
        InputStream is = new FileInputStream(objFile);

        long startTime = System.nanoTime();
        is.read(b);
        long endLatency = System.nanoTime();

        int x = 0;
        while (x < (iteration - 1)) {
            is.read(b);
            x++;
        }

        is.close(); 

        long endTime = System.nanoTime();

        // Calculate performance read for sequential read
        double timeTaken = (endTime - startTime) / 1000000000.0;
        double timeInMili = (endLatency - startTime) / 1000000.0;
        double data = blockSize * iteration;
        double throughput = data / timeTaken;


        String threadName = Thread.currentThread().getName();
        if (threadName.contains("thread-1")) {
            laten[0] = timeInMili;
            throughp[0] = (throughput) / (1024 * 1024);
        } else {
            laten[1] = timeInMili;
            throughp[1] = (throughput) / (1024 * 1024);
        }


        if (noOfThread == 1) {
            System.out.println("Sequential_file_read throughput " + blockSize + "(bytes)=" + throughp[0] + " MB/s");
            System.out.println("Sequential file read latency " + blockSize + "(bytes)=" + laten[0] + " ms");
        } else {
            if (flag2) {
                System.out.println("Sequential_file_read Final Latency " + blockSize + "(bytes)=" + ((laten[0] + laten[1]) / 2) + " ms");
                System.out.println("Sequential_file_read Final Throughput Speed " + blockSize + "(bytes)=" + ((throughp[0] + throughp[1]) / 2) + "MB/s");
            }
            flag2 = true;
        }
    }

    private void random_read(int blockSize) throws FileNotFoundException, IOException {

        int iteration = 0;

        if (blockSize == 1) {
            iteration = 5000000;
        } else if (blockSize == 1024) {
            iteration = 5000;
        } else {
            iteration = 100;
        }

        RandomAccessFile objFile = new RandomAccessFile("rand_test" + blockSize + ".txt", "rw");
        objFile.setLength(blockSize * iteration);
        FileChannel in = objFile.getChannel();

        ByteBuffer bytebuf = ByteBuffer.allocate(blockSize);
        long startTime = System.nanoTime();

        int bytesRead = in.read(bytebuf);   //read into buffer.

        for (int j = 0; j < iteration; j++) {

            Random rand = new Random();
            int randChar = rand.nextInt(blockSize * iteration);
            bytebuf.flip();
            while (bytebuf.hasRemaining()) {
                bytebuf.get();
            }

            bytebuf.clear();
            bytesRead = in.read(bytebuf);
            in.position(randChar);
        }

        objFile.close();

        long endTime = System.nanoTime();

        // Calculate performance read for random read

        double timeTaken = (endTime - startTime) / 1000000000.0;
        double timeInMili = (endTime - startTime) / 1000000.0;
        double data = blockSize * iteration;
        double throughput = data / timeTaken;

        String threadName = Thread.currentThread().getName();
        if (threadName.contains("thread-1")) {
            laten[0] = timeInMili / iteration;
            throughp[0] = (throughput) / (1024 * 1024);
        } else {
            laten[1] = timeInMili / iteration;
            throughp[1] = (throughput) / (1024 * 1024);
        }

        if (noOfThread == 1) {
            System.out.println("Random_file_read_throughput " + blockSize + "(bytes)=" + throughp[0] + " MB/s");
            System.out.println("Random_file_read _latency " + blockSize + "(bytes)=" + laten[0] + " ms");
        } else {
            if (flag4) {
                System.out.println("Random_file_read Final Latency " + blockSize + "(bytes)=" + ((laten[0] + laten[1]) / 2) + " ms");
                System.out.println("Random_file_read Final Throughput Speed " + blockSize + "(bytes)=" + ((throughp[0] + throughp[1]) / 2) + "MB/s");
            }
            flag4 = true;
        }



    }

    private void sequential_write(int blockSize) throws FileNotFoundException, IOException {

        int iteration = 0;

        if (blockSize == 1) {
            iteration = 5000000;
        } else if (blockSize == 1024) {
            iteration = 5000;
        } else {
            iteration = 100;
        }

        File objFile = new File("seq_test_" + blockSize + ".txt");
        OutputStream os = new FileOutputStream(objFile);
        objFile.setWritable(true);

        byte[] bytes = new byte[blockSize];

        long startTime = System.nanoTime();

        os.write(bytes);

        long endLatency = System.nanoTime();

        int x = 0;
        while (x < (iteration - 1)) {
            os.write(bytes);
            x++;
        }

        long endTime = System.nanoTime();

        // Calculate performance read for sequential write

        double timeTaken = (endTime - startTime) / 1000000000.0;
        double timeInMili = (endLatency - startTime) / 1000000.0;
        double data = blockSize * iteration;
        double throughput = data / timeTaken;


        String threadName = Thread.currentThread().getName();
        if (threadName.contains("thread-1")) {
            laten[0] = timeInMili;
            throughp[0] = (throughput) / (1024 * 1024);
        } else {
            laten[1] = timeInMili;
            throughp[1] = (throughput) / (1024 * 1024);
        }

        if (noOfThread == 1) {
            System.out.println("Sequential_file_write throughput" + blockSize + "(bytes)=" + throughp[0] + " MB/s");
            System.out.println("Sequential_file_write latency" + blockSize + "(bytes)=" + laten[0] + " ms");
        } else {
            if (flag1) {
                System.out.println("Sequential_file_write Final Latency " + blockSize + "(bytes)=" + ((laten[0] + laten[1]) / 2) + " ms");
                System.out.println("Sequential_file_write Final Throughput Speed " + blockSize + "(bytes)=" + ((throughp[0] + throughp[1]) / 2) + "MB/s");
//                System.out.println("-----------------Sequential write completed---------------------------------");
            }
            flag1 = true;
        }
        os.close();


    }

    private void random_write(int blockSize) throws FileNotFoundException, IOException {

        int iteration = 0;

        if (blockSize == 1) {
            iteration = 5000000;
        } else if (blockSize == 1024) {
            iteration = 5000;
        } else {
            iteration = 100;
        }

        RandomAccessFile objFile = new RandomAccessFile("rand_test" + blockSize + ".txt", "rw");
        objFile.setLength(blockSize * iteration);
        FileChannel out = objFile.getChannel();
        ByteBuffer byteBuf = ByteBuffer.allocate(blockSize * iteration);

        byte[] b = new byte[blockSize];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) i;
        }

        long startWrite = System.nanoTime();

        out.position(0);

        int z = 0;
        for (int i = 0; i < iteration; i++) {

            Random rand = new Random();

            byteBuf.clear();
            byteBuf.put(b);
            byteBuf.flip();

            int randChar2 = rand.nextInt(blockSize * iteration);
            out.position(randChar2);

            while (byteBuf.hasRemaining()) {
                out.write(byteBuf);
            }
        }

        objFile.close();

        long endWrite = System.nanoTime();

        // Calculate performance read for random read
        double timeTaken = (endWrite - startWrite) / 1000000000.0;
        double timeInMili = (endWrite - startWrite) / 1000000.0;
        double data = blockSize * iteration;
        double throughput = data / timeTaken;


        String threadName = Thread.currentThread().getName();
        if (threadName.contains("thread-1")) {
            laten[0] = timeInMili / iteration;
            throughp[0] = (throughput) / (1024 * 1024);
        } else {
            laten[1] = timeInMili / iteration;
            throughp[1] = (throughput) / (1024 * 1024);
        }

        if (noOfThread == 1) {
            System.out.println("Random_file_write throughput " + blockSize + "(bytes)=" + throughp[0] + " MB/s");
            System.out.println("Random_file_write latency " + blockSize + "(bytes)=" + laten[0] + " ms");
        } else {
            if (flag3) {
                System.out.println("Random_file_write Final Latency " + blockSize + "(bytes)=" + ((laten[0] + laten[1]) / 2) + " ms");
                System.out.println("Random_file_write Final Throughput Speed " + blockSize + "(bytes)=" + ((throughp[0] + throughp[1]) / 2) + "MB/s");
            }
            flag3 = true;
        }



    }
}
