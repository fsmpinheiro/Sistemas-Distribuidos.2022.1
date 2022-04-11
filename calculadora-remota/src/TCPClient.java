import java.net.*;
import java.io.*;

public class TCPClient {
	int serverPort;
	Socket sckt = null;
	
	DataInputStream inPut;		//will receive a new DataInputStream( s.getInputStream())
	DataOutputStream outPut;
	
	
	public TCPClient() {
		try {
			serverPort = 7996;
			sckt = new Socket("localhost", serverPort);
			inPut = new DataInputStream( sckt.getInputStream());
			outPut = new DataOutputStream( sckt.getOutputStream());
		}catch( UnknownHostException e ){
			System.out.println("Sock: " + e.getMessage());
		}catch( EOFException e ) {
			System.out.println( "EOF: " + e.getMessage() );
		}catch( IOException e ) {
			System.out.println( "IO: " + e.getMessage() );
		}
		
	}
	
	
	public void sendRequisition( String requisition ) {
		try {
			outPut.writeUTF( requisition );
		} catch ( Exception e ){
			System.out.println( "ClientSentRequisition: " + e.getMessage() );
		}
	}
	
	public String getResponse() {
		String response = null;
		try {
			response = inPut.readUTF();
		} catch ( Exception e ){
			System.out.println( "ClientSentRequisition: " + e.getMessage() );
		}
		return response;
	}
	
	public void closeConnection() {
		try {
			sckt.close();
		} catch ( IOException e ){
			System.out.println( "Close: " + e.getMessage() );			
		}
	}
	
	
}
