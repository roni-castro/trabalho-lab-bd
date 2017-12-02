package vendaVendedor;

public class DetalhesPedido {

    private String codigoProduto;
    private String nomeProduto;
    private String quantidade;
    private String precoUnitario;
    private String desconto;
    private String cor;
    private String tamanho;

    private String codigoPedido;

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(String precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        if (cor == null) {
            this.cor = "";
        } else {
            this.cor = cor;
        }
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        if (tamanho == null) {
            this.tamanho = "";
        } else {
            this.tamanho = tamanho;
        }
    }

}
