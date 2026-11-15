package bike;
import java.io.Serializable;

/**
 * Esta classe representa informação sobre uma bicicleta.
 * Nota: Serializável
 */
public class RegistoHistorico extends Registo implements Serializable {

    private int bikeId;

    /**
     * Cria uma instância de Registo, inicializando os atributos com os valores recebidos.
     *  @param bikeId O id da bicicleta a registar.
     */
    public RegistoHistorico(int bikeId) {
        this.bikeId = bikeId;
    }


    // metodos selectores: para consultar valores de atributos
    /**
     * Devolve o id da bicicleta.
     */
    public int getBikeId() {
        return bikeId;
    }




    // da uma versao textual desde objecto
    /**
     * Devolve uma representacao textual desta instância de Registo de Historico
     */
    public String toString() {
        return "[registo: " +
                bikeId+"]";
    }

}
