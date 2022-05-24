package server;

/**
 * Classe Calculadora 
 * | + add ( op1 : double ) : double
 * | + sub ( op1 : double ) : double
 * | + mult( op1 : double ) : double
 * | + div ( op1 : double ) : double
 * 
 * @author FSamuelMPinheiro
 *
 */
//mensagem deve ser recebida como: NomeDoMetodo<separador>Operando01<separador>Operando02
public class Calculadora extends Thread{

	public Calculadora( ) {
	}
	
	public double add(double oper01, double oper02 ) {
		return ( oper01 + oper02 );
	}
	
	public double sub(double oper01, double oper02 ) {
		return ( oper01 - oper02 );
	}
	
	public double mult(double oper01, double oper02 ) {
		return ( oper01 * oper02 );
	}
	
	public double div(double oper01, double oper02 ) {
		return ( oper01 / oper02 );
	}

}
