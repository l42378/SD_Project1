package bike;
import java.io.Serializable;

/**
 * Esta classe representa informação sobre um utilizador.
 * Nota: Serializável
 */
public class RegistoUtilizador extends Registo implements Serializable {

    private String nome;
    private String email;
    private String username;

    /**
     * Cria uma instância de Registo, inicializando os atributos com os valores recebidos.
     *  @param nome Nome do utilizador a registar.
     *  @param email Email do utilizador.
     *  @param username Username do utilizador.
     */
    public RegistoUtilizador(String nome, String email, String username) {
        this.nome = nome;
        this.email = email;
        this.username = username;
    }


    // metodos selectores: para consultar valores de atributos
    /**
     * Devolve o nome do utilizador.
     */
    public String getNome() {
        return nome;
    }


    /**
     * Devolve o email do utilizador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devolve o username do utilizador.
     */
    public String getUsername() {
        return username;
    }



    // da uma versao textual desde objecto
    /**
     * Devolve uma representacao textual desta instância de Registo de Utilizador
     */
    public String toString() {
        return "[registo: " +
                nome + ", " + email + ", " +
                username+"]";
    }

}
