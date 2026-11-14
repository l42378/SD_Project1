package bike;

import java.util.Scanner;
import java.time.Instant;

class ClienteGeral {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.print("\033[H\033[2J");
		System.out.println("1. Registo de elemento gerido");	
		System.out.println("2. Registo de utilizadores");	
		System.out.println("3. Registo da operação principal");	
		System.out.println("4. Listagem com filtros");	
		System.out.println("5. Consulta de estado atual");	
		System.out.println("6. Consulta de histórico");	
		System.out.println("");	
		System.out.print("Opção: ");
		String option = input.nextLine();
		
		String request = "";

		String username;
		String velocidade;
		String autonomia;
		String peso;
		String bike_id;
		switch(option){
			case "1":
				System.out.print("Velocidade: ");
				velocidade = input.nextLine();
				System.out.print("Autonomia: ");
				autonomia = input.nextLine();
				System.out.print("Peso: ");
				peso = input.nextLine();
				request = "INSERT BIKE " + velocidade + "," + autonomia + "," + peso;
				break;
			case "2":
				System.out.print("Nome de utilizador: ");
				username = input.nextLine();
				request = "INSERT USER " + username;
				break;
			case "3":
				System.out.print("Nome de utilizador: ");
				username = input.nextLine();
				System.out.print("ID da bicicleta: ");
				bike_id = input.nextLine();
				System.out.print("Operação: ");
				String operacao = input.nextLine();
				request = "INSERT OP " + username + "," + bike_id + "," + operacao + "," + Instant.now();
				break;
			case "4":
				System.out.print("Velocidade: ");
				velocidade = input.nextLine();
				System.out.print("Autonomia: ");
				autonomia = input.nextLine();
				System.out.print("Peso: ");
				peso = input.nextLine();
				request = "LIST BIKE velocidade=" + velocidade + ",autonomia=" + autonomia + ",peso=" + peso;
				break;
			case "5":
				System.out.print("ID da bicicleta: ");
				bike_id = input.nextLine();
				request = "SELECT STATE " + bike_id;
				break;
			case "6":
				System.out.print("ID da bicicleta: ");
				bike_id = input.nextLine();
				request = "SELECT HISTORY " + bike_id;
				break;
			default:
				System.out.println("\033[7mERROR: Option not available\033[m");
		}
		// TODO: sent request to server
		System.out.println(request);
	}
}
