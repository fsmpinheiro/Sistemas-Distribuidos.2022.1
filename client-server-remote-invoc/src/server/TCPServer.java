package server;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPServer {
	
	private ArrayList<ServerDealer> connectedClients;
	private ServerSocket sSocket;
	private boolean done;
	private ExecutorService setOfThreads;
	
	
	public TCPServer( ){
		connectedClients = new ArrayList<ServerDealer>();
		done = false;
		
	}
	
	
	public void run( ) {
		try {
			sSocket = new ServerSocket( 7998 );
			
			setOfThreads = Executors.newCachedThreadPool();
			System.out.println( "Servidor iniciado; \nEscutando na porta " + sSocket.getLocalPort() + ";" );
			
			while( !done ) {
				Socket sClient = sSocket.accept();
				
				ServerDealer dealer = new ServerDealer( sClient );
				connectedClients.add( dealer );
				setOfThreads.execute( dealer );

			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 *Broadcast to all connected clients =)
	 *	| + sendResponse( response: String ) :void 
	 *	direcionado a todos os clientes conectados 	 
	 */
	public void broadcastToEveryOne( String message ) {
		for( ServerDealer ch : connectedClients ) {
			if( ch != null ) {
				ch.sendResponse( message );
			}
		}
	}
	
	
	/**
	 *Broadcast to every connected client, except the indicated client =)
	 *	| + sendResponse( response: String ) :void
	 *	direcionado a todos os clientes conectados, exceto o cliente informado
	 */
	public void broadcastToExceptThis( ServerDealer client, String message ) {
		for( ServerDealer ch : connectedClients ){
			if( ch != null && ch != client ){
				ch.sendResponse( message );
			}
		}
		
	}
	
	
	/**
	 *Broadcast only to the connected client, indicated =)
	 *	| + sendResponse( response: String ) :void
	 *	direcionado apenas ao cliente conectado que for informado
	 */
	public void broadcastToThisOne( ServerDealer client, String message ) {
		client.sendResponse( message );
	}
	
	
	public void shutdownServer( ) {
		try {
			done = true;
			if( !sSocket.isClosed() ) { sSocket.close(); }
			for( ServerDealer client : connectedClients ) {
				client.shutdownThisClientDealer();
			}
		} catch ( EOFException e) {
			System.out.println("EOFE error em shutdownServer()"+ e.getMessage() );
		} catch ( IOException e) {
			System.out.println("IOE error em shutdownServer()" + e.getMessage() );
		}
	}
	
	
	
	class ServerDealer implements Runnable{
	
		private Socket client;
		private String nickname;
		
		private BufferedReader input;
		private PrintWriter output;
		
		public ServerDealer( Socket client ) { this.client = client; }
		
		
	@Override
	public void run( ) {
		try {
			output = new PrintWriter( client.getOutputStream(), true );
			input = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
			
			output.println("-- Por favor me diga seu nickname usuário: ");
			nickname = input.readLine();
			
			broadcastToThisOne(this, "Seja bem vindo ao chat " + nickname + ";" );
			broadcastToExceptThis( this, nickname + " entrou no chat;" );
			
			String message;
			
			while( (message = input.readLine()) != null ) {
				if ( message.equals( "!sair" ) ) {
					broadcastToThisOne( this, " volte logo;" );
					broadcastToEveryOne( nickname + " saiu do chat, desconetou;" ); 
					shutdownThisClientDealer();
					message = null;
				} 
				
				else if ( message.startsWith( "!calc" ) ){
					String answer = null;
													ForwarderAgent fa = new ForwarderAgent();
					answer = fa.invoke(message);
					broadcastToThisOne( this, answer + "  :D" );
				} 
				
				else if ( message.startsWith( "!nick" ) ) {
						String[ ] messageSplited = message.split( " ", 2 );
						if( messageSplited.length == 2) {
							broadcastToEveryOne("-- " + nickname + " pediu pra mudar para: " + messageSplited[1]);
							nickname = messageSplited[1];
							output.println("-- " +  nickname + " renomeado com sucesso;");
						
						} 
						
						else {
							broadcastToThisOne(this, "-- a mudança de nickname é assim: !nick 'novo_nome'");
							output.println("-- pediu mas não mandou novo nome;");
						
						}
				}
				
				else {
					broadcastToExceptThis(this, this.nickname + " diz: " + message);
				}
			}
			
		} catch (Exception e) {
			shutdownThisClientDealer();
		}
	}
	
	public void sendResponse( String message ) {
		output.println( message );
	}
	
	
	public void shutdownThisClientDealer( ) {
		try {
				 input.close();
				output.close();
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
		TCPServer server = new TCPServer();
		server.run();
	}

}
