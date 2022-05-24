package server;

/**
 * Classe Skeleton 
 * | + add ( args : String ) : String
 * | + sub ( args : String ) : String
 * | + mult( args : String ) : String
 * | + div ( args : String ) : String
 * 
 * @author FSamuelMPinheiro
 *
 */

public class Skeleton {

	public Skeleton() {
		// TODO Auto-generated constructor stub
	}
	
	public String setWhichOperand( String message ) {
		String[ ] messageSplited = message.split( " ", 4 ) ;
		String response = "error: operation chegou errada";
		
		if ( messageSplited.length == 4 && !( messageSplited[1].toString().isBlank( ) ) ) {
			
			if( messageSplited[1].toCharArray()[0] == '+' ) {
				response = add( message );
				return response;
			}
			if( messageSplited[1].toCharArray()[0] == '-' ) {
				response = sub( message );
				return response;
			}
			if( messageSplited[1].toCharArray()[0] == '*' ) {
				response = mult( message );
				return response;
			}
			if( messageSplited[1].toCharArray()[0] == '/' ) {
				double check = Double.valueOf( messageSplited[3] );
				if ( check <= 0 ) {
					return response = "Num < Zero || Num == Zero, não divide nada";
				} else {
					response = div( message );
					return response;
				}
				
			}
			
		}
		return response;
	}
	
	
	
	
	public String add( String message ) {
		String[ ] messageSplited = message.split( " ", 4);
		
		Double oper01 = Double.valueOf( messageSplited[2] );
		Double oper02 = Double.valueOf( messageSplited[3] );
		
		Calculadora calcBasic = new Calculadora();
		String response = Double.toString( calcBasic.add(oper01, oper02) );
		
		return response;
	}
	
	public String sub( String message ) {
		String[ ] messageSplited = message.split( " ", 4);
		
		Double oper01 = Double.valueOf( messageSplited[2] );
		Double oper02 = Double.valueOf( messageSplited[3] );
		
		Calculadora calcBasic = new Calculadora();
		String response = Double.toString( calcBasic.sub(oper01, oper02) );
		
		return response;
	}
	
	public String mult( String message ) {
		String[ ] messageSplited = message.split( " ", 4);
		
		Double oper01 = Double.valueOf( messageSplited[2] );
		Double oper02 = Double.valueOf( messageSplited[3] );
		
		Calculadora calcBasic = new Calculadora();
		String response = Double.toString( calcBasic.mult(oper01, oper02) );
		
		return response;
	}
	
	public String div( String message ) {
		String[ ] messageSplited = message.split( " ", 4);
		
		Double oper01 = Double.valueOf( messageSplited[2] );
		Double oper02 = Double.valueOf( messageSplited[3] );
		
		Calculadora calcBasic = new Calculadora();
		String response = Double.toString( calcBasic.div(oper01, oper02) );
		
		return response;
	}
	
}
