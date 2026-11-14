package bike;
import java.util.Scanner;

class ClienteGeral {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.println("1. Registo de elemento gerido");	
		System.out.println("2. Registo de utilizadores");	
		System.out.println("3. Registo da operação principal");	
		System.out.println("4. Listagem com filtros");	
		System.out.println("5. Consulta de estado atual");	
		System.out.println("6. Consulta de histórico");	
		System.out.println("");	
		System.out.print("Opção: ");
		input.nextLine();
	}
}
