/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud_assignment1;

/**
 *
 * @author jaimin
 */
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class UDPClient {

    public double latency[];
    public double throughput[];
    public int noOfThread = 0;
    public int block = 0;
    int port[] = new int[]{6776, 7667};
    public boolean flag = false;
    public String hostName = null;

    public UDPClient(String noOfThrea, String blockSize, String hostName) throws UnknownHostException, SocketException, InterruptedException, IOException {

        this.noOfThread = Integer.parseInt(noOfThrea);
        this.block = Integer.parseInt(blockSize);
        this.hostName = hostName;

        latency = new double[noOfThread];
        throughput = new double[noOfThread];

        // create an service object to run thread concurrently.
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);
        for (int j = 0; j < noOfThread; j++) {
            service.execute(new WorkerThread(block, port[j], flag));
            flag = true;
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public class WorkerThread implements Runnable {

        private int blockSize = 0;
        private InputStream in = null;
        private OutputStream out = null;
        private DatagramSocket clientSocket = null;
        private InetAddress IPAddress = null;
        public int port;
        boolean flag;

        public WorkerThread(int blockSize, int port, boolean flag) throws IOException {

            this.blockSize = blockSize;
            this.port = port;
            this.flag = flag;
        }

        @Override
        public void run() {

            try {
                clientSocket = new DatagramSocket();
                IPAddress = InetAddress.getByName(hostName);
                sendPacket(blockSize, clientSocket, IPAddress, port);
            } catch (UnknownHostException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendPacket(int blockSize, DatagramSocket clientSocket, InetAddress IPAddress, int port) throws IOException {

            int iteration = 100;
            long startTime = System.nanoTime();

            byte[] sendData = new byte[blockSize];
            byte[] receiveData = new byte[blockSize];

            // send data to server
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            for (int i = 0; i < iteration; i++) {
                clientSocket.send(sendPacket);
            }

            // receive data from server
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            for (int j = 0; j < iteration; j++) {
                receiveData = receivePacket.getData();
            }

            clientSocket.close(); // close connection

            long endTime = System.nanoTime();

            System.out.println("thread name = " + Thread.currentThread().getName());
            String threadName = Thread.currentThread().getName();

            // calculate throughput and latency
            double costInMili = (endTime - startTime) / 1000000.0;
            double cost = (endTime - startTime) / 1000000000.0;
            double speed = (blockSize * iteration * 2 * 8 / cost);

            if (threadName.contains("thread-1")) {
                latency[0] = costInMili / iteration;
                throughput[0] = (speed) / (1024 * 1024);
            } else {
                latency[1] = costInMili / iteration;
                throughput[1] = (speed) / (1024 * 1024);
            }
            System.out.println("noofthread= " + noOfThread);
            if (noOfThread == 1) {
                System.out.println("Latency = " + latency[0] + " ms");
                System.out.println("Throughput speed = " + throughput[0] + " Mb/s");
            } else {
                if (this.flag) {
                    System.out.println("Latency = " + ((latency[0] + latency[1]) / 2) + " ms");
                    System.out.println("Throughput Speed = " + ((throughput[0] + throughput[1]) / 2) + "Mb/s");
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        UDPClient obj = new UDPClient(args[0], args[1], args[2]);
    }
}
