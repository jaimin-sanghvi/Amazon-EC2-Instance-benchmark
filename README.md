# Amazon-EC2-Instance-benchmark

 
Operation manual instructions :
-------------------------------

#1. Instruction to run CPU Benchmark :
---------------------------------------
- To run cpu benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to CPU
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.CPU_Bench" EXPERIMENT_NO
  EXPERIMENT_NO : 1-IOPS WITH 1-THREAD
		  2-IOPS WITH TWO THREAD
		  3-IOPS WITH THREE THREAD
  		  4-FLOPS WITH 1-THREAD 
		  5-FLOPS WITH TWO THREAD 
		  6-FLOPS WITH THREE THREAD
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.CPU_Bench" 3 (IOPS FOR 4-THREAD)
		OR 
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh CPU EXPERIMENT_NO (1,2,3,4,5,6)
  EXPERIMENT_NO : 1-IOPS WITH 1-THREAD
		  2-IOPS WITH TWO THREAD
		  3-IOPS WITH THREE THREAD
  		  4-FLOPS WITH 1-THREAD 
		  5-FLOPS WITH TWO THREAD 
		  6-FLOPS WITH THREE THREAD
  Example : ./cloud_assignment1.sh CPU 3 (IOPS FOR 4-THREAD)


#2. Instruction to run DISK Benchmark :
---------------------------------------
- To run disk benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to DISK
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.Disk_Benchmark" NO_OF_THREADS BLOCK_SIZE
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 1048576[1MB]
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.Disk_Benchmark" 1 1024 (FOR 1-THREAD, 1024[1KB] BLOCK)
		OR
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh NO_OF_THREADS BLOCK_SIZE
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 1048576[1MB]
  Example :  ./cloud_assignment1.sh DISK 1 1024 (FOR 1-THREAD, 1024[1KB] BLOCK)
  
#3. Instruction to run NETWORK Benchmark :
------------------------------------------
TCP_SERVER (Run on instance-1)

- To run disk benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to TCPServer
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.TCPServer" NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 65536[64K]
  HOSTNAME     : localhost
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.TCPServer" 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)
		OR
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 65536[64K]
  HOSTNAME     : localhost
  Example :  ./cloud_assignment1.sh NETWORKTCPSERVER 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)

TCP_CLIENT (Run on instance-2)

- To run disk benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to TCPClient
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.TCPClient" NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 65536[64K]
  HOSTNAME : localhost
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.TCPClient" 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)
		OR
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 65536[64K]
  HOSTNAME : localhost
  Example :  ./cloud_assignment1.sh NETWORKTCPCLIENT 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)


UDP_SERVER  (Run on instance-1)

- To run disk benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to UDPServer
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.UDPServer" NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 64000[64K] (we can not send packet more than 64000 Bytes)
  HOSTNAME     : localhost
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.UDPServer" 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)
		OR
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 64000[64K] (we can not send packet more than 64000 Bytes)
  HOSTNAME     : localhost
  Example :  ./cloud_assignment1.sh NETWORKUDPSERVER 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)


UDP_CLIENT  (Run on instance-2)

- To run disk benchmark load Cloud_Assignment1 project in netbeans
- Right click on project and set configure to UDPClient
- Right click on project and run project
		OR
- Run project through terminal
- Type command : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.UDPClient" NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 64000[64K] (we can not send packet more than 64000 Bytes)
  HOSTNAME     : localhost
  Example : /usr/bin/java -cp Cloud_Assignment1.jar "cloud_assignment1.UDPClient" 1 1024 localhost (FOR 1-THREAD, 1024[1KB] BLOCK)
		OR
- Run project through terminal using script cloud_assignment1.sh 
- Type command : ./cloud_assignment1.sh NO_OF_THREADS BLOCK_SIZE HOSTNAME
  NO_OF_THREAD : 1 OR 2
  BLOCK_SIZE   : 1, 1024[1KB], 64000[64K] (we can not send packet more than 64000 Bytes)
  HOSTNAME     : localhost
  Example :  ./cloud_assignment1.sh NETWORKUDPCLIENT 1 1024  localhost (FOR 1-THREAD, 1024[1KB] BLOCK)

Enclosed File Details :
-----------------------
1) Script : cloud_assignment1.sh 
2) Jar File : Cloud_Assignment1.jar
3) Netbeans Project : Cloud_Assignment1
4) Report : prog1-report.pdf (I have combined design and performance document in single file)
5) Source files : src
6) readme.txt

