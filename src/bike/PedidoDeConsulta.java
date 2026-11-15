package bike;

public class PedidoDeConsulta extends Pedido {
    private Registo reg;

    public PedidoDeConsulta(Registo r) {
        this.reg = r;
    }

    public Registo getRegisto() {
        return reg;
    }
}

