package bike;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;

public class Servidor extends Thread {

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) throws Exception {
    }*/
    public static void main(String[] args) throws Exception {

        // coloque os argumentos
	/*String PG_HOST = args[0];
	String PG_DB = args[1];
	String USER = args[2];

	Console cnsl = System.console();
	char[] PWD = cnsl.readPassword("Password: ");
	System.out.println("Password:");*/

	String configFile = (args.length > 0) ? args[0] : "db.properties";
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
        Statement stmt = pc.getStatement();

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

	System.exit(0);

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
    }


}
