package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
	
	private Socket sClient;
	private boolean done;
	private BufferedReader input;
	private PrintWriter output;
	
	public TCPClient( String host, int portNumber ) {
		try {
			done = false;
			sClient = new Socket( host, portNumber);
			
			output = new PrintWriter( sClient.getOutputStream(), true);
			input = new BufferedReader( new InputStreamReader( new DataInputStream( sClient.getInputStream() ) ) );
			
		} catch (Exception e) {
			System.out.println( "TCPClient( ): " + e.getMessage() );
			closeConnection();
		}
	}
	
	
	public void sendRequest( String requisition ) {
		try {
			this.output.println( requisition );
		} catch (Exception e) {
			System.out.println("sendRequest( ): " + e.getMessage() );
		}
	}
	
	
	public void getResponse( ){
		String inMessage;
		try {
			while( ( inMessage = input.readLine() ) != null) {
				System.out.println( inMessage );			
				
			}
		} catch (Exception e) {
			System.out.println("getResponse( ): " + e.getMessage() );
		}
		
	}
	
	
	public void closeConnection( ) {
		done = true;
		try {
				   input.close();
			 	  output.close();
			if( !sClient.isClosed() ) {
				 sClient.close();
			}
		}catch (Exception e) {
			System.out.println("closeConnection( ): " + e.getMessage() );
		}
	}
		
	

	
	
}
