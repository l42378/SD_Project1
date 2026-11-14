package sd;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;

/**
 *
 * @author jsaias
 */
public class TesteAcessoBD {

    /**
     * @param args the command line arguments
     */
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
        
        // PostgresConnector pc = new PostgresConnector( ?? );
        // NOTA: não DEVE ter configuracoes no código fonte!!!
        // passar como argumento ou ler de .properties
        PostgresConnector pc = new PostgresConnector( host, db, user, pw );
        
        
        
        // estabelecer a ligacao ao SGBD
        pc.connect();
        Statement stmt = pc.getStatement();

	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS personl99999 "
			+ "(id integer, name varchar(128), birth timestamp)");

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
