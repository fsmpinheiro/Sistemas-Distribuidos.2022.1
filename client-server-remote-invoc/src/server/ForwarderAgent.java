package server;

/**
 * Classe Despachante 
 * | + invoke( message : string ) String
 * 
 * @author FSamuelMPinheiro
 *
 */
public class ForwarderAgent{
	
	private Skeleton skeleton;
	
	
	public ForwarderAgent( ) {
		// TODO Auto-generated constructor stub
	}
	
	
	public String invoke( String message ) {
		
		this.skeleton = new Skeleton( );
		String response = skeleton.setWhichOperand( message );
		
		return response;
	}
	


}
