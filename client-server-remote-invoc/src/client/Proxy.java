package client;

/**
 * Class Proxy
 * 	| + proxyAdd ( op1 : double, op2 : op2 ) : void
 * 	| + proxySub ( op1 : double, op2 : op2 ) : void
 *  | + proxyMult ( op1 : double, op2 : op2 ) : void
 *  | + proxyDiv ( op1 : double, op2 : op2 ) : void
 *	| + proxyClose ( ) : void
 *
 *   Class InputHandler
 *  | + run( ) : void
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Proxy {

	private TCPClient sClient;
//	private BufferedReader input;
//	private PrintWriter output;
	private boolean done;
	
	public Proxy( String host, int portNumber ) {
			try {
				done = false;
				sClient = new TCPClient( host, portNumber );
				
				InputHandler inHandler = new InputHandler();
				Thread handlerThread = new Thread( inHandler );
				handlerThread.start();
				
				sClient.getResponse();
				
			}catch (Exception e) {
				// TODO: handle exception
			}

	}
	
	
	
	public void proxyAdd( double oper1, double oper2) {
		String oper01 = Double.toString(oper1);
		String oper02 = Double.toString(oper2);
		sClient.sendRequest( "!calc + " + oper01 + " " + oper02 );
	}
	
	public void proxySub( double oper1, double oper2 ) {
		String oper01 = Double.toString(oper1);
		String oper02 = Double.toString(oper2);
		sClient.sendRequest( "!calc - " + oper01 + " " + oper02 );
	}
	
	public void proxyMult( double oper1, double oper2) {
		String oper01 = Double.toString(oper1);
		String oper02 = Double.toString(oper2);
		sClient.sendRequest( "!calc * " + oper01 + " " + oper02 );	
	}
	
	public void proxyDiv( double oper1, double oper2) {
		String oper01 = Double.toString(oper1);
		String oper02 = Double.toString(oper2);
		sClient.sendRequest( "!calc / " + oper01 + " " + oper02 );
	}
	
	
	public void proxyClose( ) {
		sClient.closeConnection();
	}
	
	
	
	
	class InputHandler implements Runnable{
		
		@Override
		public void run( ) {
			try {
				BufferedReader inReader = new BufferedReader( new InputStreamReader( System.in ) );
				
				while( !done ) {
					String message = inReader.readLine();
					
					if( !(message.equals("!sair") ) ) {
						
						
						if( message.startsWith("!calc" ) ) {
							String[ ] messageSplited = message.split(" ", 4);
							
							if ( messageSplited.length == 4 && !( messageSplited[1].toString().isBlank( ) ) ) {
								if( messageSplited[1].toCharArray()[0] == '+' ) {
									Double op1 = Double.valueOf( messageSplited[2] );
									Double op2 = Double.valueOf( messageSplited[3] );
									proxyAdd(op1, op2);
								}
								if( messageSplited[1].toCharArray()[0] == '-' ) {
									Double op1 = Double.valueOf( messageSplited[2] );
									Double op2 = Double.valueOf( messageSplited[3] );
									proxySub(op1, op2);
								}
								if( messageSplited[1].toCharArray()[0] == '*' ) {
									Double op1 = Double.valueOf( messageSplited[2] );
									Double op2 = Double.valueOf( messageSplited[3] );
									proxyMult(op1, op2);
								}
								if( messageSplited[1].toCharArray()[0] == '/' ) {
									Double op1 = Double.valueOf( messageSplited[2] );
									Double op2 = Double.valueOf( messageSplited[3] );
									proxyDiv(op1, op2);
								}
							}
						} else {
							sClient.sendRequest( message );
						}
						
						
						
					} else {
						sClient.sendRequest( message );
						inReader.close();
						done = true;
						sClient.closeConnection();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
