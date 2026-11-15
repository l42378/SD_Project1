package bike;
import java.io.Serializable;

/**
 * Esta classe representa informação sobre uma bicicleta.
 * Nota: Serializável
 */
public class RegistoBicicleta extends Registo implements Serializable {

    private String velocidade;
    private String autonomia;
    private String peso;

    /**
     * Cria uma instância de Registo, inicializando os atributos com os valores recebidos.
     *  @param velocidade A velocidade da bicicleta a registar.
     *  @param autonomia Autonomia da bicicleta.
     *  @param peso Peso da bicicleta.
     */
    public RegistoBicicleta(String velocidade, String autonomia, String peso) {
        this.velocidade = velocidade;
        this.autonomia = autonomia;
        this.peso = peso;
    }


    // metodos selectores: para consultar valores de atributos
    /**
     * Devolve a velocidade da bicicleta.
     */
    public String getVelocidade() {
        return velocidade;
    }


    /**
     * Devolve a autonomia da bicicleta.
     */
    public String getAutonomia() {
        return autonomia;
    }

    /**
     * Devolve o peso da bicicleta.
     */
    public String getPeso() {
        return peso;
    }



    // da uma versao textual desde objecto
    /**
     * Devolve uma representacao textual desta instância de Registo de Bicicleta
     */
    public String toString() {
        return "[registo: " +
                velocidade + ", " + autonomia + ", " +
                peso+"]";
    }

}
