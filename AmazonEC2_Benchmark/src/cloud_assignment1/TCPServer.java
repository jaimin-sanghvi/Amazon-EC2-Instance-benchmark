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
import java.io.InputStream;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class TCPServer {

    public int noOfThread = 0;
    public int block = 0;
    public String hostName = null;

    public TCPServer(String noOfThrea, String blockSize, String hostName) throws InterruptedException, IOException {

        int port[] = new int[]{6776, 7667};

        this.noOfThread = Integer.parseInt(noOfThrea);
        this.block = Integer.parseInt(blockSize);
        this.hostName = hostName;

        // create an service object to run thread concurrently.
        ExecutorService service = Executors.newFixedThreadPool(noOfThread);

        for (int j = 0; j < noOfThread; j++) {
            service.execute(new WorkerThread(block, port[j]));
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public static void main(String argv[]) throws Exception {

        TCPServer obj = new TCPServer(argv[0], argv[1], argv[2]);
    }

    public class WorkerThread implements Runnable {

        private int blockSize = 0;
        private InputStream in = null;
        private OutputStream out = null;
        ServerSocket servSocket = null;
        Socket connSocket = null;
        int port = 0;

        public WorkerThread(int blockSize, int port) throws IOException {

            this.blockSize = blockSize;
            this.port = port;
            System.out.println("port - " + port);
            System.out.println("connection accepted");
        }

        @Override
        public void run() {
            try {
                servSocket = new ServerSocket(port); // create server socket 
                connSocket = servSocket.accept();   // create socket to accept client request 
                in = connSocket.getInputStream();
                out = connSocket.getOutputStream();
            
                // receive and send packet 
                sendPacket(blockSize, in, out);
                sleepThread();
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
        }

        private void sleepThread() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void sendPacket(int blockSize, InputStream in, OutputStream out) throws IOException {

            int iter = 1000;
            byte[] bytes = new byte[blockSize];
            // receive data from client
            for (int i = 0; i < iter; i++) {
                in.read(bytes);
            }

            // send data to client
            for (int i = 0; i < iter; i++) {
                out.write(bytes);
            }
        }
    }
}
