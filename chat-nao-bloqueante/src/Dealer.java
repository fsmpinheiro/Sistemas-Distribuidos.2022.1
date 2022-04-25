import java.io.IOException;

//Classe Dealer é o distribuidor de mensagens, aquele que 
// vê quem é o autor e manda pro destinatório dizendo
public class Dealer extends Thread {
	
	public double operation (double num01, double num02, char operator) {

		double result = 0;
		
		if ( operator == '+' ) {
			result = ( num01 + num02 );
		} else if ( operator == '-' ){
			result = ( num01 - num02 );
		} else if ( operator == '*' ) {
			result = ( num01 * num02 );
		} else if ( operator == '/' ){
			if (num02 != 0) {
				result = ( num01 / num02 );
			}
		}
		return result;		
	}
	
	public void sendTo( String data , Connection connection ) throws IOException {
		
		connection.outputStream.writeUTF(data);
		
	}
	
}
