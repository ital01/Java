package sistema_pedidos;

public class ListaPedidos {
    private Pedido[] lista;
    private int count;

    public ListaPedidos(int capMax) {
        lista = new Pedido[capMax];
        count = 0;
    }

    // Procura um pedido na lista
    public Pedido buscarPedido(int numPed) {
        for (int i = 0; i < count; i++) {
            if (lista[i].getNumPed() == numPed) {
                return lista[i];
            }
        }
        return null;
    }

    // adiciona um pedido na lista
    public void cadastrarPedido(Pedido p) throws listaCheiaException, pedidoExistenteException {
        if (buscarPedido(p.getNumPed()) != null) {
            throw new pedidoExistenteException(p.getNumPed());
        }
        if (count >= lista.length) {
            throw new listaCheiaException(p.getNumPed());
        }
        lista[count] = p;
        count++;
    }

    public void atenderPedido(int numPed) throws pedidoNaoEncontradoException, pedidoAtendidoException {
        Pedido p = buscarPedido(numPed);
        if (p == null) {
            throw new pedidoNaoEncontradoException(numPed);
        }
        p.atender();
    }

    public void relatorioCliente(int codCli, boolean todos, boolean atendidos) {
        double total = 0;
        Pedido.printCols();
        for (int i = 0; i < count; i++) {
            if (lista[i].getCodCli() != codCli) continue;
            if (todos || atendidos == lista[i].isAtendido()) {
                lista[i].printPed();
                total += lista[i].getValor();
            }
        }
        System.out.println("Valor total: " + total);
    }

    public void pagarVendedor(int codVend, double porcent) throws pedidoPagoException, pedidoNaoAtendidoException {
        double total = 0;
        Pedido.printCols();
        for (int i = 0; i < count; i++) {
            if (lista[i].getCodVend() != codVend) continue;
            if (!lista[i].isAtendido()) continue;
            if (lista[i].isPago()) continue;
            lista[i].pagar();
            total += lista[i].getValor();
            lista[i].printPed();
        }

        System.out.println("Valor total: " + total);
        System.out.println("Comissão a pagar: " + total * porcent / 100);
    }
}