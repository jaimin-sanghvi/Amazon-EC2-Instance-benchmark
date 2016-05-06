/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud_assignment1;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaimin
 */
class TCPClient {

    public double latency[];
    public double throughput[];
    public int noOfThread = 0;
    public int block = 0;
    public boolean flag = false;
    public String hostName = null;

    public TCPClient(String noOfThrea, String blockSize, String hostName) throws UnknownHostException, IOException, InterruptedException {

        int port[] = new int[]{6776, 7667};

        this.noOfThread = Integer.parseInt(noOfThrea);
        this.block = Integer.parseInt(blockSize);
        this.hostName = hostName;

        latency = new double[noOfThread];
        throughput = new double[noOfThread];

        // create an service object to run thread concurrently.
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);

        for (int j = 0; j < noOfThread; j++) {
            service.execute(new WorkerThread(block, port[j], flag));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public class WorkerThread implements Runnable {

        private int blockSize = 0;
        private InputStream in = null;
        private OutputStream out = null;
        int port = 0;
        Socket objSocket = null;
        boolean flag;

        public WorkerThread(int blockSize, int port, boolean flag) throws IOException {

            this.blockSize = blockSize;
            this.flag = flag;
            this.port = port;

            System.out.println("port is - " + port);
            System.out.println("connected ");

        }

        @Override
        public void run() {
            try {
                objSocket = new Socket(hostName, port); // create socket object to connect with server
                in = objSocket.getInputStream(); // create input stream object to read incoming data 
                out = objSocket.getOutputStream();  // create output stream object to send data
                System.out.println("\n " + Thread.currentThread().getName() + " & Block Size = " + blockSize + " :-");

                sendPacket(blockSize, in, out);
                sleepThread();

            } catch (UnknownHostException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sleepThread() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String argv[]) throws UnknownHostException, IOException, InterruptedException {
        TCPClient obj = new TCPClient(argv[0], argv[1], argv[2]);
    }

    private void sendPacket(int blockSize, InputStream in, OutputStream out) throws IOException {

        int itr = 1000;
        long startTime = System.nanoTime();

        byte[] bytes = new byte[blockSize]; 
        // send data to server
        for (int i = 0; i < itr; i++) {
            out.write(bytes);
        }

        // receive data from server
        for (int i = 0; i < itr; i++) {
            in.read(bytes);
        }

        long endTime = System.nanoTime();

        System.out.println("thread name = " + Thread.currentThread().getName());
        String threadName = Thread.currentThread().getName();

        
        // calculate throughput and latency for network
        double costInMili = (endTime - startTime) / 1000000.0;
        double cost = (endTime - startTime) / 1000000000.0;
        double speed = (blockSize * itr * 2 * 8 / cost);

        if (threadName.contains("thread-1")) {
            latency[0] = costInMili / itr;
            throughput[0] = (speed) / (1024 * 1024);
        } else {
            latency[1] = costInMili / itr;
            throughput[1] = (speed) / (1024 * 1024);
        }

        if (noOfThread == 1) {
            System.out.println("Latency = " + latency[0] + " ms");
            System.out.println("Throughput speed = " + throughput[0] + " Mb/s");
        } else {
            if (flag) {
                System.out.println("Latency = " + ((latency[0] + latency[1]) / 2) + " ms");
                System.out.println("Throughput Speed = " + ((throughput[0] + throughput[1]) / 2) + "Mb/s");
            }
            flag = true;
        }
    }
}
