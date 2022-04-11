import java.net.*;
import java.io.*;


//Classe servidor chama a classe calculadora passando a mensagem para realizar a operação 

public class TCPServer {
    public static void main (String args[]) {
	try{
		System.out.println("SERVIDOR Iniciado");
		int serverPort = 7996; 	//server port
		try (ServerSocket listenSocket = new ServerSocket(serverPort)) {
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		}
	} catch(IOException e) {
		System.out.println("Listen socket:"+e.getMessage());
		}
    }
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
	    try {
			clientSocket = aClientSocket;
			in = new DataInputStream(  clientSocket.getInputStream());
			
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
	     } catch(IOException e)  {
	    	 System.out.println("Connection: "+e.getMessage());
    	 }
	}
	public void run(){
	    try {			                 
	    	String data = in.readUTF();		//captura a mensagem transmitida
	    	
	    	String array [] = new String[3];
	    	array = data.split(" ");
	    	
	    	data = "null answer";
	    	
	    	double num01 = Double.valueOf(array[0]);
	    	double num02 = Double.valueOf(array[2]);
	    	char oper = array[1].toCharArray()[0];
	    	
	    	
	    	if (oper == '+' || oper == '-' || oper == '*' || oper == '/' ) {
	    		if( oper == '/' && num02 <= 0 ) {
	    			data = "Zero não divide nada";
	    		}else {
	    			Calculator calc = new Calculator();
	    			
	    			double answer = calc.operation( num01, num02, oper );
	    			data = Double.toString( answer );
	    		}
	    	}
	    	
	    	
	    	
	    	out.writeUTF(data);
	    	
	    } catch(EOFException e) {
	    	System.out.println("EOF:"+e.getMessage());
	    } catch(IOException e)  {
	    	System.out.println("IO:"+e.getMessage());
	    } finally{ 
	    	try {
	    		clientSocket.close();
    		}catch (IOException e) {
    			/*close failed*/
    			}
	    	}
	}
}