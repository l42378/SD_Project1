package bike;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;
import java.io.*;
import java.net.*;

public class Servidor extends Thread {

	private int serverPort;

	public Servidor(int p) {
		serverPort= p;
	}

	/**
	 * @param args the command line arguments
	 */
	/*public static void main(String[] args) throws Exception {
	  }*/
	public static void main(String[] args) throws Exception {
		System.err.println("SERVER...");
		if (args.length<1) {
			System.err.println("Missing parameter: port number");
			System.exit(1);
		}
		int p=0;
		try {
			p= Integer.parseInt( args[0] );
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}


		Servidor serv= new Servidor(p);
		serv.servico();   // activa o servico
	}

	public void servico() throws Exception {

		// coloque os argumentos
		/*String PG_HOST = args[0];
		  String PG_DB = args[1];
		  String USER = args[2];

		  Console cnsl = System.console();
		  char[] PWD = cnsl.readPassword("Password: ");
		  System.out.println("Password:");*/

		//String configFile = (args.length > 0) ? args[0] : "db.properties";
		String configFile = "db.properties";
		Properties cfg = new Properties();
		try (FileInputStream in = new FileInputStream(configFile)) {
			cfg.load(in);
		}
		String host = cfg.getProperty("host");
		String port = cfg.getProperty("port");
		String db = cfg.getProperty("db");
		String user = cfg.getProperty("user");
		String pw = cfg.getProperty("password");

		/*System.out.println("Host:\t\033[7m" + host + "\033[m");
		  System.out.println("Port:\t\033[7m" + port + "\033[m");
		  System.out.println("DB:\t\033[7m" + db + "\033[m");
		  System.out.println("User:\t\033[7m" + user + "\033[m");
		  System.out.println("PW:\t\033[7m" + pw + "\033[m");*/

		// PostgresConnector pc = new PostgresConnector( ?? );
		// NOTA: não DEVE ter configuracoes no código fonte!!!
		// passar como argumento ou ler de .properties
		PostgresConnector pc = new PostgresConnector( host, db, user, pw );



		// estabelecer a ligacao ao SGBD
		pc.connect();
		PreparedStatement stmt;
		stmt = pc.con.prepareStatement("SELECT * FROM bicicletas");



		try {
			ServerSocket ss = new ServerSocket(serverPort);

			while (true) {
				Socket s = ss.accept();

				try {
					Object objPedido= null;
					// le os dados do pedido, como um OBJECTO "objPedido"

					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());

					objPedido = input.readObject();

					// apreciar o objecto com o pedido recebido:
					if (objPedido==null)
						System.err.println("PEDIDO NULL: esqueceu-se de alguma coisa");

					if (objPedido instanceof PedidoDeConsulta) {
						String rows = "";
						String table = "";
						String condition = "";
						PedidoDeConsulta pdc= null;

						pdc = (PedidoDeConsulta) objPedido;
						Registo registo = pdc.getRegisto();

						if (registo instanceof RegistoBicicleta) {
							RegistoBicicleta rb = (RegistoBicicleta) registo;
							stmt = pc.con.prepareStatement("SELECT * FROM bicicletas WHERE estado_administrativo LIKE 'aprovado' AND velocidade LIKE ? AND autonomia LIKE ? AND peso LIKE ?");
							stmt.setInt(1, rb.getVelocidade());
							stmt.setInt(2, rb.getAutonomia());
							stmt.setInt(3, rb.getPeso());
						}
						else if (registo instanceof RegistoEstado) {
							RegistoEstado re = (RegistoEstado) registo;
							stmt = pc.con.prepareStatement("SELECT estado, estado_administrativo FROM bicicletas WHERE id = ?");
						       	stmt.setInt(1, re.getBikeId());
						}
						else if (registo instanceof RegistoHistorico) {
							RegistoHistorico rh = (RegistoHistorico) registo;
							stmt = pc.con.prepareStatement("SELECT * FROM historico_operacao UNION historico_estado WHERE historico_operacao.bicicleta_id = ? AND historico_estado.bicicleta_id = ?");
							stmt.setInt(1, rh.getBikeId());
							stmt.setInt(2, rh.getBikeId());
						}

						stmt = pc.con.prepareStatement("SELECT ? FROM ? WHERE ?");
						stmt.setString(1, rows);
						stmt.setString(2, table);
						stmt.setString(3, condition);
						try {
							ResultSet rs = stmt.executeQuery();
							String queryResult = "\n| ";
							ResultSetMetaData rsmd = rs.getMetaData();
							for(int i = 1; i <= rsmd.getColumnCount(); i++){
								queryResult += rsmd.getColumnName(i) + " | ";
							}
							queryResult += "\n| ";
							while(rs.next()) {
								for(int i = 1; i <= rsmd.getColumnCount(); i++){
									//queryResult += String.format("'%" + rsmd.getColumnName(i).length() +  "." + rsmd.getColumnName(i).length() + "' | ", rs.getString(i));
									switch (rsmd.getColumnName(i)){
										case "autonomia":
											queryResult += String.format("%-" + rsmd.getColumnName(i).length() + "sh | ", rs.getString(i));
											break;
										case "peso":
											queryResult += String.format("%-" + rsmd.getColumnName(i).length() + "skg | ", rs.getString(i));
											break;
										case "velocidade":
											queryResult += String.format("%-" + rsmd.getColumnName(i).length() + "skm/h | ", rs.getString(i));
											break;
										default:
											queryResult += String.format("%-" + rsmd.getColumnName(i).length() + "s | ", rs.getString(i));
									}
								}
								queryResult += "\n";
							}
							rs.close();
							output.writeObject(queryResult);
						} catch (Exception e){
							output.writeObject(e);
							System.out.println(e);
						}


						// procurar o registo associado a matricula pretendida

						// pesquisar no servidor (List, mais tarde num ficheiro)


						// enviar objecto Cliente via socket
						// se encontra devolve o registo, se não, devolve um novo objecto ou string a representar que nao encontrou



					}
					else if (objPedido instanceof PedidoDeRegisto) {
						String table = "";
						String rows = "";
						String values = "";
						PedidoDeRegisto pr= null; // ...

						pr = (PedidoDeRegisto) objPedido;
						Registo registo = pr.getRegisto();

						if (registo instanceof RegistoBicicleta) {
							RegistoBicicleta rb = (RegistoBicicleta) registo;
							stmt = pc.con.prepareStatement("INSERT INTO bicicletas (estado_administrativo, velocidade, autonomia, peso) values ?, ?, ?, ?");
							stmt.setString(1, "não aprovado"); 
							stmt.setInt(2, rb.getVelocidade()); 
							stmt.setInt(3, rb.getAutonomia()); 
							stmt.setInt(4, rb.getPeso()); 
						}
						else if (registo instanceof RegistoUtilizador) {
							RegistoUtilizador ru = (RegistoUtilizador) registo;
							stmt = pc.con.prepareStatement("INSERT INTO utilizadores (nome, email, username) values ?, ?, ?");
							stmt.setString(1, ru.getNome());
							stmt.setString(2, ru.getEmail());
							stmt.setString(3, ru.getUsername());
						}
						else if (registo instanceof RegistoOperacao) {
							RegistoOperacao ro = (RegistoOperacao) registo;
							stmt = pc.con.prepareStatement("INSERT INTO historico_operacao (utilizador_id, bicicleta_id, operacao, ts) VALUES ?, ?, ?, ?");
							stmt.setInt(1, ro.getUserId());
							stmt.setInt(2, ro.getBikeId());
							stmt.setString(3, ro.getOperacao());
							stmt.setObject(4, ro.getTs());
						}
						// ver se ja existia registo desta matricula
						//
						try {
							output.writeObject(stmt.executeUpdate());
						} catch (Exception e){
							output.writeObject(e);
							System.out.println(e);
						}



						// adicionar ao rep local (se nao e' uma repeticao)


						// responder ao cliente
						//output.writeChars("It worked");
						output.flush();

						// TODO


					}
					else
						System.out.println("PROBLEMA");

				}
				catch (Exception exNoAtendimento) {
					exNoAtendimento.printStackTrace();
				}
				finally {
					// fechar o socket de dados dedicado a este cliente:
					try {
						//AQUI: fechar o socket de dados
					}
					catch (Exception e002) {
					}
				}
			}
		} catch (Exception problemaBindAccept) {
			problemaBindAccept.printStackTrace();
		}




		/*Random rnd = new Random();
		  stmt.executeUpdate("INSERT INTO bicicletas (velocidade, autonomia, estado, estado_administrativo, peso) VALUES ('" + rnd.nextInt(25) + "km/h', '" + rnd.nextInt(10) + "h', 0, 'aprovado', '" + rnd.nextInt(30) + "kg')");

		//ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables");
		ResultSet rs = stmt.executeQuery("SELECT bicicletas.*, estados.estado FROM bicicletas JOIN estados ON bicicletas.estado = estados.id");
		while(rs.next()) {
		for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
		System.out.print(rs.getString(i) + ", ");
		}
		System.out.println();
		}
		rs.close();*/

		/*
		// *******************
		// update/insert
		try {

		// AQUI: operação para inserir um registo com o seu nome...
		try {
		stmt.executeUpdate("INSERT INTO personl99999 VALUES(1,'Enrico',CURRENT_TIMESTAMP)");
		} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Problemas ao inserir dados");
		}

		} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Problems on insert...");
		}

		// ******************
		// query
		try {

		// AQUI: código para realizar a CONSULTA


		} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Problems retrieving data from db...");
		}

		// desligar do SGBD:
		pc.disconnect();
		*/
	}


}
