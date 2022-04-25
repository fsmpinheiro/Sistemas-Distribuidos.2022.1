import java.util.Scanner;

//Classe Window � a janela para a comunica��o para cada 
//"usu�rio" (na verdade cliente) que entrar no nosso chat
public class Window {

	public static void main ( String[] args ) {
		try {
			int serverPort = 7997; 			//a porta do meu servi�o
			String host = "localhost";
			
			Proxy userProxy = new Proxy(host, serverPort);
			Scanner ask = new Scanner(System.in);
		}
		
		
	}
		
	
	class Proxy {
		ChatTCPClient userClient;
		
		public Proxy(String host , int srvrPort) {
			try {
				userClient = new ChatTCPClient(host , srvrPort);
			} catch ( Exception e ){
				System.out.println( "Proxy: " + e.getMessage() );
			}
		}
		
		public void initChat() {
			
		}
		public void showClients() {
			
		}
	}
}
