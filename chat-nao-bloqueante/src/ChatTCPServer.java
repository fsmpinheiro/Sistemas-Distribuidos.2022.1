import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatTCPServer implements Runnable {
	
	private ArrayList<ServerDealer> connectedClients;
	private ServerSocket sSocket;
	private boolean done;
	private ExecutorService setOfThreads;
	
	
	public ChatTCPServer() {
		connectedClients = new ArrayList<>();
		done = false;
	}
	

	@Override
	public void run() {
		try {
			
			sSocket = new ServerSocket( 7997 );
			//pool = new ExecutorService();
			setOfThreads = Executors.newCachedThreadPool();
			System.out.println("Servidor iniciado; \nEscutando na porta " + sSocket.getLocalPort() + ";");
			
			while( !done ) {
			
			Socket sClient = sSocket.accept();
			
			ServerDealer dealer = new ServerDealer( sClient );
			connectedClients.add( dealer );
			setOfThreads.execute( dealer );
			}
		} catch ( Exception e) {
			shutdownServer();
		}
	}
	
	public void broadcastToEneryOne( String message ) {
		for ( ServerDealer ch : connectedClients) {
			if( ch != null ) {
				ch.sendMessage( message );
			}
		}
	}
	
	public void broadcastToExceptThis( ServerDealer clientHandler , String message ) {
		for ( ServerDealer ch : connectedClients ) {
			if ( ch != null && ch != clientHandler ) {
				ch.sendMessage( message );
			}
		}
	}
	
	public void broadcastToThisOne ( ServerDealer ch , String message ) {
		ch.sendMessage( message );
	}
	
	public void shutdownServer() {
		try {
			done = true;
			if( !sSocket.isClosed() ) {
				sSocket.close();
			}
			for ( ServerDealer ch : connectedClients ) {
				ch.shutdownThisClientDealer();
			}
		} catch ( EOFException e) {
			System.out.println("EOFE error em shutdownServer()"+ e.getMessage() );
		} catch ( IOException e) {
			System.out.println("IOE error em shutdownServer()" + e.getMessage() );
		}
	}
	
	class ServerDealer implements Runnable{
		
		private Socket client;
		private BufferedReader in;
//		private DataInputStream inpStream;
		private PrintWriter out;
		private String nickname;
		
		public ServerDealer( Socket client ) {
			this.client = client;
		}
		
		@Override
		public void run() {
			try {
				out = new PrintWriter( client.getOutputStream(), true );
				in = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
				
				
				out.println( "Por favor me diga seu nome: " );
				nickname = in.readLine();

				//debug -- comportamento do sistema
//				System.out.println(nickname + " connected! :D");
				//broadcastToEneryOne( nickname + " entrou no chat;");
				
				broadcastToThisOne(this , "Seja bem vindo ao chat " + nickname + ";");
				broadcastToExceptThis(this, nickname + " entrou no chat;");
				
				//objeto mensagem
				String message;
				
				while ( (message = in.readLine() ) != null )  {
//					System.out.println( message );
					if ( message.equals( "!sair" ) ){
						
						broadcastToThisOne(this, nickname + " volte logo;" );						
						broadcastToEneryOne( nickname + " saiu do chat, desconectou;" );
						
						shutdownThisClientDealer();
						message = null;
					} else if ( message.startsWith( "!nick" ) ) {
						String[] messageSplit = message.split(" ", 2);
						if( messageSplit.length == 2) {
							broadcastToEneryOne( nickname + " pediu pra mudar para: " + messageSplit[1] );							
							
							nickname = messageSplit[1];
							
							out.println( nickname + " renomeado com sucesso" );
						} else {
							out.println("pediu mas não mandou novo nome;");
						}
					} else {
						broadcastToEneryOne( nickname + ": " + message );
					}

				}
				
			}catch ( IOException e) {
				shutdownThisClientDealer();
			}
			
		}
		
		public void sendMessage(String message) {
			out.println( message );
		}
		
		public void shutdownThisClientDealer() {
			try {
						 in.close(); 
//				  inpStream.close(); 
						out.close();
				if( !client.isClosed() ) { 
					 client.close(); 
				}
				
			} catch ( EOFException e) {
				System.out.println("EOF em client.shutdown(): " + e.getMessage());				
			} catch ( IOException e) {
				System.out.println("IO em client.shutdown(): " + e.getMessage());
			} 
		}
	}
	
	
	public static void main(String[] args) {
	ChatTCPServer service = new ChatTCPServer();
	service.run();
	}
}
