import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatTCPClient implements Runnable{
	
	private Socket cClient;
	private BufferedReader in;
	private PrintWriter out;
	private boolean done;
//	private DataInputStream inpStream;
	
	public ChatTCPClient() {
		done = false;
	}
	
	@Override
	public void run() {
		try {
			cClient = new Socket("localhost", 7997);
			
			out = new PrintWriter( cClient.getOutputStream(), true );
			in = new BufferedReader( new InputStreamReader( new DataInputStream( cClient.getInputStream()) ) );
			
			InputHandler inHandler = new InputHandler( );
			Thread handlerThread = new Thread( inHandler );
			handlerThread.start();
			
			String inMessage;
			while ( (inMessage = in.readLine() ) != null ) {
				System.out.println( inMessage );
			}
		} catch ( Exception e ) {
			shutdown();
		}
	}
	
	public void shutdown() {
		done = true;
		try {
					  in.close();
					 out.close();
			if( !cClient.isClosed() ) {
				 cClient.close();
			} 
		} catch ( IOException e ) {
			System.out.println( "shutdown(): " + e.getMessage() );
			//TODO: handle
		}
	}
	
	
	class InputHandler implements Runnable{

		@Override
		public void run() {
			try {
				BufferedReader inReader = new BufferedReader( new InputStreamReader( System.in ) );
				
				while( !done ) {
					String message = inReader.readLine();
					
					if ( message.equals( "!sair" ) ) {
							 out.println( message );
						inReader.close();
								 shutdown();
					} else {
							 out.println( message );
					}
				}
				
			} catch ( IOException e ) {
				System.out.println( "run(): " + e.getMessage());
			}
		}
	}

	
	public static void main(String[] args) {
	ChatTCPClient client = new ChatTCPClient();
	client.run();
	
}

}
//	private Socket cClient;
//	private BufferedReader inpBuffReader;
//	private PrintWriter outPrtWriter;	
//	private boolean valid = false; 
//	
//	@Override
//	public void run() {
//		try {
//			String host = "localhost";
//			int serverPort = 7997;
//			
//			cClient = new Socket( host , serverPort);
//			//this.valid = false;
//			System.out.println("p: " + cClient.getLocalPort() + " h:" + cClient.getLocalAddress() );
//			
//			inpBuffReader = new BufferedReader( new InputStreamReader( cClient.getInputStream() ) );
//			outPrtWriter = new PrintWriter( cClient.getOutputStream() );//, autoFlush: true);
//			
//			InputDealer inDealer = new InputDealer();
//			Thread dealerThread = new Thread( inDealer ); 
//			dealerThread.start();
//			
//			String askMessage;
//			
//			if( inpBuffReader == null ) {
//				System.out.println("buffer is nom readyy-yet..");
//			}
//			while ( ( askMessage = inpBuffReader.readLine() ) != null ) {
//				System.out.println(askMessage);
//			}
//			
//		}catch ( IOException e ) {
//			System.out.println("deu ruim na run do ChatTCPClient");
//			System.out.println("erro: " + e.getMessage());
//			desconectar();
//		}
//	}
//	
//	public void desconectar() {
//		this.valid = true;
//		try {
//			inpBuffReader.close();
//			outPrtWriter.close();
//			if ( !cClient.isClosed() ) {
//				cClient.close();
//			}
//		} catch (IOException e) {
//			System.out.println("erro ao desconectar() :" + e.getMessage() );
//			//ignorar
//		}
//		
//	}
//	
//	class InputDealer implements Runnable{
//		
//		@Override
//		public void run() {
//			try {
//				BufferedReader inptReader = new BufferedReader( new InputStreamReader(System.in) );
//				while ( !valid ) {
//					
//					String message = inptReader.readLine();
//					if( message.equals("!sair")) {
//						
//						outPrtWriter.println( message );
//						inptReader.close();
//						desconectar();
//						
//					} else {
//						outPrtWriter.println( message );
//						
//					}
//				}
//			} catch ( IOException e ) {
//				desconectar();
//			}
//		}
//		
//	}
//	
//	public static void main(String[] args) {
//		ChatTCPClient client = new ChatTCPClient();
//		client.run();
//		
//	}

