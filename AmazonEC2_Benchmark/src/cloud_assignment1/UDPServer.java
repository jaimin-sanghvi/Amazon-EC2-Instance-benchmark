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

class UDPServer {

    public int noOfThread = 0;
    public int block = 0;
    public String hostName = null;

    public UDPServer(String noOfThrea, String blockSize, String hostName) throws SocketException, InterruptedException, IOException {

        this.noOfThread = Integer.parseInt(noOfThrea);
        this.block = Integer.parseInt(blockSize);
        this.hostName = hostName;

        int port[] = new int[]{6776, 7667};
        // create an service object to run thread concurrently.
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);
        for (int j = 0; j < noOfThread; j++) {
            service.execute(new WorkerThread(port[j], block));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public static void main(String args[]) throws Exception {
        UDPServer obj = new UDPServer(args[0], args[1], args[2]);
    }

    public class WorkerThread implements Runnable {

        private int blockSize = 0;
        private int port = 0;
        private InputStream in = null;
        private OutputStream out = null;
        private DatagramSocket serverSocket = null;

        public WorkerThread(int port, int blockSize) throws IOException {
            this.blockSize = blockSize;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                serverSocket = new DatagramSocket(port);
                sendPacket(blockSize, serverSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendPacket(int blockSize, DatagramSocket serverSocket) throws IOException {

            int iteration = 100;
            byte[] receiveData = new byte[blockSize];
            byte[] sendData;

            // create datagram object to receive data from client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            for (int j = 0; j < iteration; j++) {
                receiveData = receivePacket.getData();
            }

            InetAddress IPAddress = InetAddress.getByName(hostName);
            port = receivePacket.getPort();

            // create datagram object to receive data from client
            DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, port);
            for (int i = 0; i < 100; i++) {
                serverSocket.send(sendPacket);
            }

        }
    }
}