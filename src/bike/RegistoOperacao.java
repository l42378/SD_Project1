package bike;
import java.io.Serializable;
import java.time.Instant;

/**
 * Esta classe representa informação sobre uma operação.
 * Nota: Serializável
 */
public class RegistoOperacao implements Serializable {

    private String userId;
    private String bikeId;
    private String operacao;
    private Instant ts;

    /**
     * Cria uma instância de Registo, inicializando os atributos com os valores recebidos.
     *  @param userId UserId da operação a registar.
     *  @param bikeId BikeId da operação.
     *  @param operacao Operação a registar.
     *  @param ts Timestamp da operação.
     */
    public RegistoOperacao(String userId, String bikeId, String operacao, Instant ts) {
        this.userId = userId;
        this.bikeId = bikeId;
        this.operacao = operacao;
        this.ts = ts;
    }


    // metodos selectores: para consultar valores de atributos
    /**
     * Devolve o userId da operação.  
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Devolve o bikeId da operação.  
     */
    public String getBikeId() {
        return bikeId;
    }

    /**
     * Devolve a operação.  
     */
    public String getOperacao() {
        return operacao;
    }

    /**
     * Devolve o timestamp.  
     */
    public Instant getTs() {
        return ts;
    }


    // da uma versao textual desde objecto
    /**
     * Devolve uma representacao textual desta instância de Registo de Operação
     */
    public String toString() {
        return "[registo: " +
                userId + ", " + bikeId + ", " +
                operacao + ", " + ts+"]";
    }
  
}
