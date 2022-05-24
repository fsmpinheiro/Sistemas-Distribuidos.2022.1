package client;

import java.util.Scanner;

public class UserClass {

	public UserClass( ) {
	}
	
	
	public void run( ) {
		System.out.println( "informe endereço <ip><espaço><porta> do servidor :" );
		try {
			Scanner scann = new Scanner( System.in );
			String hostInf = new String();
			hostInf = scann.nextLine();
			
			String[ ] hostInfSplited = hostInf.split(" ", 2);
			String host = hostInfSplited[0].toString();
			int port = Integer.parseInt( hostInfSplited[1] );
			
			Proxy prx = new Proxy( host, port); 

		} catch ( Exception e) {
			System.out.println("UserClass.run( ): " + e.getMessage());
		}
	}
	
	
	
	public static void main( String args[] ) {
		UserClass user = new UserClass();
		user.run();
	}
}
