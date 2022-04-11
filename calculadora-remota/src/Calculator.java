
public class Calculator {

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
}
