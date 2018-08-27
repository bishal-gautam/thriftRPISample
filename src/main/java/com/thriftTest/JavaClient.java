package com.thriftTest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
    private static String fileUrl = "/work/projects/thriftRPISample/encrypted.txt";
    public static void main(String [] args) {

        try {
            TTransport transport;
            transport = new TSocket("localhost", 9095); // service is hosted in port 9095 of local server
            transport.open(); // connection opened
            TProtocol protocol = new  TBinaryProtocol(transport); // we will use TBinaryProtocol in protocol layer
            ByteTestWithThrift.Client client = new ByteTestWithThrift.Client(protocol);

            try {
                byte[] data = Files.readAllBytes(Paths.get(fileUrl)); // reading file data as a byre array
                System.out.println("Data file length ( in Byte ): " + data.length);
                ByteBuffer buffer = ByteBuffer.wrap(data); // converting byte array to ByteBuffer for processing
                long t0 = System.currentTimeMillis(); // time before sending data to server
                int suucess_code = client.testByte(buffer); // suceess code : 0 for same data, -1 for different data
                System.out.println("Time taken to process data in server: " + (System.currentTimeMillis() - t0));
                System.out.println("Success Code: "+suucess_code);
                String reply=client.testMessage("Bishal"); // Another method on remote server is invoked
                System.out.println("Server replied following message: "+reply); //reply from "testMessage" method

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            transport.close(); // connection closed
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}