import java.io.*;
import java.net.*;
import java.nio.file.*;
/*
    Author: Arpan Srivastava
*/

class UDPServer {
    public static void main(String[] args) throws Exception {
        //Create datagram socket at port 10005
        DatagramSocket serverSocket = new DatagramSocket(10005);

        byte[] receiveData = new byte[1024];
        String nullByte = "\0";
        String errorCode = "";

        while(true) {
            //Receiving from client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String sentence = new String(receivePacket.getData());
            System.out.println("Data received is: " + sentence);

            
            String[] arr = sentence.split(" ");
            String fileNameRequested = arr[1];

            //creating bufferedReader to read in the file 
    
            try {
            BufferedReader fileIn = new BufferedReader(new FileReader(fileNameRequested));
            StringBuilder fileData = new StringBuilder();
            String temp = fileIn.readLine();
            System.out.println("line: " + temp);
            while (temp != null) {
                System.out.println(temp);
                fileData.append(temp);
                temp = fileIn.readLine();
            }
             fileIn.close();
        } catch(FileNotFoundException e) { 
                // catch exception if file is not found, send error html file. 
                System.err.println("File not found. Program exiting. ");
                errorCode = "404 - File Not Found";
            }
            

            if (errorCode.isEmpty()) {
                errorCode = "200 - Document follows";
            }

            
            byte[] bytes = null;
            try {
                bytes = Files.readAllBytes(Paths.get(fileNameRequested));
            } catch(NoSuchFileException e) {
                bytes = Files.readAllBytes(Paths.get("errorFile.html"));
            }
            
            // HTTPHeader:
            String HTTPHeader = "\n" + arr[2] + " " + errorCode + "\r\nContent-Type: text/plain\r\nContent-Length: " + bytes.length + " bytes\r\n";
           
            byte[] headerPacket = HTTPHeader.getBytes();
            //creates a datagram with data to send, length, IP addr, port no.

            DatagramPacket sendedPacket = new DatagramPacket(headerPacket, headerPacket.length, IPAddress, port);
            
            serverSocket.send(sendedPacket); 

            boolean finished = false;
            int start = 0;
            int end = 1024;
            if (end > headerPacket.length) {
                end = headerPacket.length;
            }

            while (finished == false) {
                byte[] temp = new byte[1024];
                int dataIndex = 0;
                for(int i = start; i < end; i++) {
                
                    temp[dataIndex] = headerPacket[i];
                    dataIndex++;
                    if(i == headerPacket.length - 1) {
                        finished = true;
                    }
                }
                start = end;
                if (end + 1024 < headerPacket.length) {
                    end+= 1024;
                } else {
                    end = headerPacket.length;
                }
            
            DatagramPacket sendPacket = new DatagramPacket(temp, temp.length, IPAddress, port);
            
            serverSocket.send(sendPacket); 
            }


            finished = false;
            start = 0;
            end = 1024;
            if (end > bytes.length) {
                end = bytes.length;
            }

            while (finished == false) {
                byte[] data = new byte[1024];
                int dataIndex = 0;
                for(int i = start; i < end; i++) {
                
                    data[dataIndex] = bytes[i];
                    dataIndex++;
                    if(i == bytes.length - 1) {
                        finished = true;
                    }
                }
                start = end;
                if (end + 1024 < bytes.length) {
                    end+= 1024;
                } else {
                    end = bytes.length;
                }

            
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
            
            serverSocket.send(sendPacket); 
            }

            //Sending last nullbyte packet
            byte[] nullBytePacket = nullByte.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(nullBytePacket, nullBytePacket.length, IPAddress, port);
            serverSocket.send(sendPacket); 
            System.out.println("----------------------------------\nnullByte packet sent.\n----------------------------------");
            // end program
            System.exit(0); 
        }
    }
}