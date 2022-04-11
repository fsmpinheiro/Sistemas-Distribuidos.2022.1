import java.util.Scanner;

//Classe Display chama os métodos da classe TCPCliente, que é 
//responsável por chamar a conexão com a classe TCPServer
public class Display {

	public static void main(String[] args) {
		
		TCPClient operCalcular = null;
		
		try {
			operCalcular = new TCPClient();
			String answerReturn = new String();
			String questSend = new String();
			
			Scanner ask = new Scanner(System.in);
			
			System.out.println(" Insira a opreação a ser realizada, "
					+ "			\n necessáriamente no seguinte formato:"
					+ 			"\n <valor1><espaço><operador><espaço><valor2>");
			
			questSend = ask.nextLine();
			
			operCalcular.sendRequisition(questSend);		//Envia frase ao server
			
			answerReturn = operCalcular.getResponse();		//respota do servidor
			System.out.println(answerReturn);
			
			ask.close();
		} catch (Exception e) {
			System.out.println("Display erro: " + e.getMessage() );
		}finally {
			try {
				operCalcular.closeConnection();
			}catch (Exception e){
				System.out.println("TCP Connection error: " + e.getMessage());
			}
		}
	}

}
