package bike;

import java.util.Scanner;
import java.time.Instant;
import java.net.*;
import java.io.*;

class ClienteGeral {

	private String address = "localhost";
	private int sPort = 0;

	public ClienteGeral(String add, int p) {
		address = add;
		sPort = p;
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Argumentos insuficientes:  java VeiculosClient ADDRESS PORT");
			System.exit(1);
		}

		try {
			String addr = args[0];
			int p = Integer.parseInt(args[1]);

			ClienteGeral cg = new ClienteGeral(addr, p);

			cg.menu();   // interage com o utilizador
		} catch (Exception e) {
			System.out.println("Problema no formato dos argumentos");
			e.printStackTrace();

		}
	}

	public void menu() {
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("Press Enter to continue...");
			input.nextLine();

			System.out.print("\033[H\033[2J");
			System.out.println("1. Registo de elemento gerido");	
			System.out.println("2. Registo de utilizadores");	
			System.out.println("3. Registo da operação principal");	
			System.out.println("4. Listagem com filtros");	
			System.out.println("5. Consulta de estado atual");	
			System.out.println("6. Consulta de histórico");	
			System.out.println("7. Sair");	
			System.out.println("");	
			System.out.print("Opção: ");
			String option = input.nextLine();

			String request = "";

			String nome, email, username;
			String velocidade, autonomia, peso;
			String bikeId, userId;
			PedidoDeRegisto pr;
			pr = new PedidoDeRegisto(new RegistoBicicleta("ERR", "ERR", "ERR"));
			PedidoDeConsulta pc;
			pc = new PedidoDeConsulta(new RegistoBicicleta("0", "0", "0"));
			switch(option){
				case "1":
					System.out.print("Velocidade: ");
					velocidade = input.nextLine();
					System.out.print("Autonomia: ");
					autonomia = input.nextLine();
					System.out.print("Peso: ");
					peso = input.nextLine();
					//request = "INSERT BIKE " + velocidade + "," + autonomia + "," + peso;
					pr = new PedidoDeRegisto(new RegistoBicicleta(velocidade, autonomia, peso));
					break;
				case "2":
					System.out.print("Nome: ");
					nome = input.nextLine();
					System.out.print("Email: ");
					email = input.nextLine();
					System.out.print("Nome de Utilizador: ");
					username = input.nextLine();
					//request = "INSERT USER " + username;
					pr = new PedidoDeRegisto(new RegistoUtilizador(nome, email, username));
					break;
				case "3":
					System.out.print("ID do utilizador: ");
					userId = input.nextLine();
					System.out.print("ID da bicicleta: ");
					bikeId = input.nextLine();
					System.out.print("Operação: ");
					String operacao = input.nextLine();
					//request = "INSERT OP " + username + "," + bikeId + "," + operacao + "," + Instant.now();
					pr = new PedidoDeRegisto(new RegistoOperacao(userId, bikeId, operacao, Instant.now()));
					break;
				case "4":
					System.out.print("Velocidade: ");
					velocidade = input.nextLine();
					System.out.print("Autonomia: ");
					autonomia = input.nextLine();
					System.out.print("Peso: ");
					peso = input.nextLine();
					//request = "LIST BIKE velocidade=" + velocidade + ",autonomia=" + autonomia + ",peso=" + peso;
					pc = new PedidoDeConsulta(new RegistoBicicleta(velocidade, autonomia, peso));
					break;
				case "5":
					System.out.print("ID da bicicleta: ");
					bikeId = input.nextLine();
					//request = "SELECT STATE " + bikeId;
					pc = new PedidoDeConsulta(new RegistoEstado(Integer.parseInt(bikeId)));
					break;
				case "6":
					System.out.print("ID da bicicleta: ");
					bikeId = input.nextLine();
					pc = new PedidoDeConsulta(new RegistoHistorico(Integer.parseInt(bikeId)));
					break;
				case "7":
					System.exit(0);
				default:
					System.out.println("\033[7mERROR: Option not available\033[m");
			}
			// TODO: sent request to server
			if (Integer.parseInt(option) <= 3){
				if (pr != null){
					enviaPedido(pr);
				}
			} else if (Integer.parseInt(option) <= 6) {
				if (pc != null){
					enviaPedido(pc);
				}
			}
			System.out.println(request);


		}
	}

	public void enviaPedido(Pedido p) {
		try {
			Socket s = new Socket(address, sPort);


			// que Streams usar????


			// escrever a mensagem?

			ObjectOutputStream socketOut= new ObjectOutputStream(s.getOutputStream());
			socketOut.writeObject(p);

			// ler a resposta e mostrar o resultado

			ObjectInputStream socketIn= new ObjectInputStream(s.getInputStream());
			Object resposta = socketIn.readObject();

			// fechar o socket


			// ver a resposta - modo simples
			System.out.println("RESPOSTA: " + resposta);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
