package vendaVendedor;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public final class JanelaCadastroPedido extends javax.swing.JInternalFrame {

    private Pedido p;
    private DetalhesPedido dp;
    private Vendedor vendedor;
    private ResultSet rs;
    private final JTable tLPed, tLProd, tLDetProd;
    private final Tabela listaPedido;
    private final Tabela listaProduto;
    private final Tabela listaDetalhesProduto;

    //Constantes para tabela ListaPedido
    private static final int CODIGO_CLIENTE = 0;
    private static final int CONTA_CLIENTE = 1;
    private static final int IMPOSTO = 2;
    private static final int CODIGO_ENDERECO_ENTREGA = 3;
    private static final int CODIGO_ENDERECO_FATURA = 4;
    private static final int CODIGO_TRANSPORTADORA = 5;

    //Constantes para tabela ListaProduto
    private static final int CODIGO_PRODUTO = 0;
    private static final int NOME_PRODUTO = 1;
    private static final int PRECO_PRODUTO = 2;
    private static final int COR_PRODUTO = 3;
    private static final int TAMANHO_PRODUTO = 4;

    //Constantes para tabela ListaDetalhesProduto
    private static final int CODIGO_PRODUTO_DP = 0;
    private static final int NOME_PRODUTO_DP = 1;
    private static final int PRECO_UNITARIO_DP = 2;
    private static final int COR_DP = 3;
    private static final int TAMANHO_DP = 4;
    private static final int QUANTIDADE_DP = 5;

    public JanelaCadastroPedido(Vendedor v) {
        initComponents();
        //getContentPane().add(new JScrollPane(), "Center");
        vendedor = v;
        listaPedido = new Tabela(tbl_lista_pedido);
        listaProduto = new Tabela(tbl_lista_produto);
        listaDetalhesProduto = new Tabela(tbl_lista_DetalhesPedido);
        tLPed = listaPedido.getTabela();
        tLProd = listaProduto.getTabela();
        tLDetProd = listaDetalhesProduto.getTabela();
        p = new Pedido();
        dp = new DetalhesPedido();
        inicializarForm();
        inicializarListaPedido();
        if (Conector.getInstance().gerarConexao()) {
            InicializarListaProduto();
        }
    }

    public void inicializarForm() {
        txt_codigoCliente.setEditable(false);
        txt_nomeCliente.setEditable(false);
        txt_contaCliente.setEditable(false);
        txt_imposto.setEditable(false);
        txt_cepEnderecoEntrega.setEditable(false);
        txt_cepEnderecoFatura.setEditable(false);
        txt_cidadeEnderecoEntrega.setEditable(false);
        txt_cidadeEnderecoFatura.setEditable(false);
        txt_codigoEnderecoEntrega.setEditable(false);
        txt_codigoEnderecoFatura.setEditable(false);
        txt_complementoEnderecoEntrega.setEditable(false);
        txt_complementoEnderecoFatura.setEditable(false);
        txt_estadoEnderecoEntrega.setEditable(false);
        txt_estadoEnderecoFatura.setEditable(false);
        txt_logradouroEnderecoEntrega.setEditable(false);
        txt_logradouroEnderecoFatura.setEditable(false);
        txt_paisEnderecoEntrega.setEditable(false);
        txt_paisEnderecoFatura.setEditable(false);
        txt_codigoTransportadora.setEditable(false);
        txt_nomeTransportadora.setEditable(false);
    }

    private void inicializarListaPedido() { //Lista de valores a ser inserido
        String[] colunasLista = {"<html>Codigo<br>Cliente", "<html>Conta<br>Cliente",
            "<html>Imposto", "<html>Endereço<br>Entrega",
            "<html>Endereco<br>Fatura", "<html>Código<br>Transportadora"
        };

        for (String coluna : colunasLista) {
            listaPedido.criarColuna(coluna);
        }
        String[][] novoPedido = {{"2", "11-2222-33333", "12.33", "410", "411", "1"}, // nenhum pedido
        {"17180", "17-1801-71801", "17", "24444", "24444", "2"}, // varios pedidos
        {"", "10-0000-00000", "10", "406", "407", "3"}, // cliente nao cadastrado
        };

        for (String[] pedido : novoPedido) {
            listaPedido.criarLinha(pedido);
        }
    }

    private void limparFormulario() {
        // Limpar Formulário dos dados do Pedido
        txt_codigoCliente.setText("");
        txt_nomeCliente.setText("");
        txt_contaCliente.setText("");
        txt_imposto.setText("");
        txt_codigoTransportadora.setText("");
        txt_nomeTransportadora.setText("");

        // Limpar Formulário dos dados do Endereco de Fatura
        txt_codigoEnderecoFatura.setText("");
        txt_logradouroEnderecoFatura.setText("");
        txt_complementoEnderecoFatura.setText("");
        txt_cidadeEnderecoFatura.setText("");
        txt_estadoEnderecoFatura.setText("");
        txt_paisEnderecoFatura.setText("");
        txt_cepEnderecoFatura.setText("");

        // Limpar Formulário dos dados do Endereco de Entrega
        txt_codigoEnderecoEntrega.setText("");
        txt_logradouroEnderecoEntrega.setText("");
        txt_complementoEnderecoEntrega.setText("");
        txt_cidadeEnderecoEntrega.setText("");
        txt_estadoEnderecoEntrega.setText("");
        txt_paisEnderecoEntrega.setText("");
        txt_cepEnderecoEntrega.setText("");

        txt_desconto.setText("0");
    }

    private void limparFormularioDP() {
        int qtdLinha = listaDetalhesProduto.qtdLinhas();
        for (int linha = qtdLinha - 1; linha >= 0; linha--) {
            listaDetalhesProduto.removerLinha(linha);
        }
    }

    private boolean passarDadosListaParaForm(int linhaSelecionada) {
        // Se não tiver cliente cadastrado
        if (((String) tLPed.getValueAt(linhaSelecionada, CODIGO_CLIENTE)).equals("")) {
            return false;
        }

        String queryNomeCliente, queryEnderecoEntrega, queryEnderecoFatura, queryNomeTransportadora;

        // Strings que armazenarao os dados da tabela "Lista de Pedido"
        p.setCodigoCliente((String) tLPed.getValueAt(linhaSelecionada, CODIGO_CLIENTE));
        p.setContaCliente((String) tLPed.getValueAt(linhaSelecionada, CONTA_CLIENTE));
        p.setImposto((String) tLPed.getValueAt(linhaSelecionada, IMPOSTO));
        p.setCodigoEnderecoEntrega((String) tLPed.getValueAt(linhaSelecionada, CODIGO_ENDERECO_ENTREGA));
        p.setCodigoEnderecoFatura((String) tLPed.getValueAt(linhaSelecionada, CODIGO_ENDERECO_FATURA));
        p.setCodigoTransportadora((String) tLPed.getValueAt(linhaSelecionada, CODIGO_TRANSPORTADORA));

        // Strings que armazenam os comandos SELECT que servirão para preencher campos do formulário
        queryNomeCliente
                = "SELECT primeironome||' '||nomedomeio||' '||sobrenome as nomeCliente"
                + " FROM Cliente WHERE codigo =" + p.getCodigoCliente();

        queryNomeTransportadora
                = "SELECT nome as nomeTransportadora"
                + " FROM Transportadora WHERE codigo =" + p.getCodigoTransportadora();

        queryEnderecoEntrega
                = "SELECT EndE.logradouro, EndE.complemento, EndE.cidade, EndE.estado, EndE.pais, EndE.codigoPostal"
                + " FROM Endereco EndE WHERE id =" + p.getCodigoEnderecoEntrega();

        queryEnderecoFatura
                = "SELECT EndF.logradouro, EndF.complemento, EndF.cidade, EndF.estado, EndF.pais, EndF.codigoPostal"
                + " FROM Endereco EndF WHERE id =" + p.getCodigoEnderecoFatura();

        // Obter nome do Cliente  no campo do formulario
        try {
            rs = Conector.getInstance().criarQueryRS(queryNomeCliente);
            while (rs.next()) {
                p.setNomeCliente(rs.getString("nomeCliente"));
            }
            Conector.getInstance().fecharStatement();
        } catch (SQLException ex) {
            Conector.getInstance().fecharStatement();
            System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
        }

        // Obter nome da Transportadora no campo do formulario
        try {
            rs = Conector.getInstance().criarQueryRS(queryNomeTransportadora);
            while (rs.next()) {
                p.setNomeTransportadora(rs.getString("nomeTransportadora"));
            }
            Conector.getInstance().fecharStatement();
        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
            Conector.getInstance().fecharStatement();
        }

        // Obter campos do EnderecoFatura
        try {
            rs = Conector.getInstance().criarQueryRS(queryEnderecoFatura);
            while (rs.next()) {
                p.setLogradouroEnderecoFatura(rs.getString("logradouro"));
                p.setComplementoEnderecoFatura(rs.getString("complemento"));
                p.setCidadeEnderecoFatura(rs.getString("cidade"));
                p.setEstadoEnderecoFatura(rs.getString("estado"));
                p.setPaisEnderecoFatura(rs.getString("pais"));
                p.setCepEnderecoFatura(rs.getString("codigoPostal"));
            }
            Conector.getInstance().fecharStatement();
        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
            Conector.getInstance().fecharStatement();
        }

        // Obter campos do EnderecoEntrega
        try {
            rs = Conector.getInstance().criarQueryRS(queryEnderecoEntrega);
            while (rs.next()) {
                p.setLogradouroEnderecoEntrega(rs.getString("logradouro"));
                p.setComplementoEnderecoEntrega(rs.getString("complemento"));
                txt_cidadeEnderecoEntrega.setText(rs.getString("cidade"));
                p.setEstadoEnderecoEntrega(rs.getString("estado"));
                p.setPaisEnderecoEntrega(rs.getString("pais"));
                p.setCepEnderecoEntrega(rs.getString("codigoPostal"));
            }
            Conector.getInstance().fecharStatement();
        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
            Conector.getInstance().fecharStatement();

        }

        // Insere no Formulário os dados do Pedido
        txt_codigoCliente.setText(p.getCodigoCliente());
        txt_nomeCliente.setText(p.getNomeCliente());
        txt_contaCliente.setText(p.getContaCliente());
        txt_imposto.setText(p.getImposto());
        txt_nomeTransportadora.setText(p.getNomeTransportadora());
        txt_codigoTransportadora.setText(p.getCodigoTransportadora());

        // Insere no Formulário os dados do Endereco de Fatura
        txt_codigoEnderecoFatura.setText(p.getCodigoEnderecoFatura());
        txt_logradouroEnderecoFatura.setText(p.getLogradouroEnderecoFatura());
        txt_complementoEnderecoFatura.setText(p.getComplementoEnderecoFatura());
        txt_cidadeEnderecoFatura.setText(p.getCidadeEnderecoFatura());
        txt_estadoEnderecoFatura.setText(p.getEstadoEnderecoFatura());
        txt_paisEnderecoFatura.setText(p.getPaisEnderecoFatura());
        txt_cepEnderecoFatura.setText(p.getCepEnderecoFatura());

        // Insere no Formulário os dados do Endereco de Entrega
        txt_codigoEnderecoEntrega.setText(p.getCodigoEnderecoEntrega());
        txt_logradouroEnderecoEntrega.setText(p.getLogradouroEnderecoEntrega());
        txt_complementoEnderecoEntrega.setText(p.getComplementoEnderecoEntrega());
        txt_cidadeEnderecoEntrega.setText(p.getCidadeEnderecoEntrega());
        txt_estadoEnderecoEntrega.setText(p.getEstadoEnderecoEntrega());
        txt_paisEnderecoEntrega.setText(p.getPaisEnderecoEntrega());
        txt_cepEnderecoEntrega.setText(p.getCepEnderecoEntrega());
        return true;
    }

    private void passarDadosListaParaFormDP(int linhaSelecionada) {

        // Strings que armazenarao os dados da tabela "Lista de Pedido"
        dp.setCodigoProduto((String) tLProd.getValueAt(linhaSelecionada, CODIGO_PRODUTO));
        dp.setNomeProduto((String) tLProd.getValueAt(linhaSelecionada, NOME_PRODUTO));
        dp.setPrecoUnitario((String) tLProd.getValueAt(linhaSelecionada, PRECO_PRODUTO));
        dp.setCor((String) tLProd.getValueAt(linhaSelecionada, COR_PRODUTO));
        dp.setTamanho((String) tLProd.getValueAt(linhaSelecionada, TAMANHO_PRODUTO));
        dp.setQuantidade("1");

        //Verifica se produto já não esta na tabela         
        int linha = produtoEstaDetalhesPedido(dp.getCodigoProduto());
        if (linha != -1) {
            int qtdAtual = Integer.parseInt((String) tLDetProd.getValueAt(linha, QUANTIDADE_DP)); // pega valor da quantidade na tabela
            dp.setQuantidade(Integer.toString(qtdAtual + 1));
            tLDetProd.setValueAt(dp.getQuantidade(), linha, QUANTIDADE_DP);
        } //Se não estiver na tabela, entao insere o produto na DetalhesPedido com a quantidade = 1
        else {
            String[] linhaProduto = new String[6];
            linhaProduto[0] = dp.getCodigoProduto();
            linhaProduto[1] = dp.getNomeProduto();
            linhaProduto[2] = dp.getPrecoUnitario();
            linhaProduto[3] = dp.getCor();
            linhaProduto[4] = dp.getTamanho();
            linhaProduto[5] = dp.getQuantidade();

            listaDetalhesProduto.criarLinha(linhaProduto);
        }

    }

    private int produtoEstaDetalhesPedido(String strCodigoProduto) {
        int qtdLinha = listaDetalhesProduto.qtdLinhas();
        for (int linha = qtdLinha - 1; linha >= 0; linha--) {
            if (strCodigoProduto.equals((String) tLDetProd.getValueAt(linha, CODIGO_PRODUTO_DP))) {
                return linha;
            }
        }
        return -1;
    }

    private void InicializarListaProduto() {
        // Strings que armazenam os comandos SELECT que servirão para preencher campos do formulário
        String queryProdutos = "SELECT codigo, nome, preco, cor, tamanho"
                + " FROM Produto WHERE preco >0 and rownum <=20 ORDER BY codigo";
        String[] linhaProduto = new String[5];

        // Obetr nome do Cliente no campo do formulario
        try {
            rs = Conector.getInstance().criarQueryRS(queryProdutos);
            while (rs.next()) {
                linhaProduto[0] = rs.getString("codigo");
                linhaProduto[1] = rs.getString("nome");
                linhaProduto[2] = rs.getString("preco");
                linhaProduto[3] = rs.getString("cor");
                linhaProduto[4] = rs.getString("tamanho");
                listaProduto.criarLinha(linhaProduto);
            }
            Conector.getInstance().fecharStatement();
        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set" + ex.getMessage());
            Conector.getInstance().fecharStatement();
        }

    }

    /*---------------------------------------- INSERÇÃO ---------------------------------*/
    private void cadastrarPedido() {
        String insertDetalhesPedido, insertPedido, strNovoPedido;
        try {
            Conector.getInstance().getConexao().setAutoCommit(false); //--------- Inicio Transação
            strNovoPedido = "select seq_codigo_Pedido.NEXTVAL from dual";
            long codigoNovoPedido = Conector.getInstance().retornarValorProximaSequencia(strNovoPedido);
            if (codigoNovoPedido != -1) {
                insertPedido
                        = "INSERT INTO Pedido VALUES (" + codigoNovoPedido + ", cast(SYSDATE as timestamp), null, null,"
                        + p.getCodigoCliente() + "," + "'" + p.getContaCliente() + "'" + "," + "null, null,"
                        + vendedor.getCodigo() + "," + p.getImposto() + "," + p.getCodigoEnderecoFatura() + ","
                        + p.getCodigoEnderecoEntrega() + "," + p.getCodigoTransportadora() + ")";
                Conector.getInstance().criarQueryPrepared(insertPedido);

                // Insere produto por produto na tabela DetalhesPedido a partir da tabela da interface
                for (int linha = 0; linha < tLDetProd.getRowCount(); linha++) {
                    dp.setCodigoProduto((String) tLDetProd.getValueAt(linha, CODIGO_PRODUTO_DP));
                    dp.setQuantidade((String) tLDetProd.getValueAt(linha, QUANTIDADE_DP));
                    dp.setPrecoUnitario((String) tLDetProd.getValueAt(linha, PRECO_UNITARIO_DP));
                    dp.setDesconto(txt_desconto.getText());

                    insertDetalhesPedido
                            = "INSERT INTO DetalhesPedido VALUES (" + codigoNovoPedido + ",'" + dp.getCodigoProduto() + "'," + dp.getQuantidade() + ","
                            + dp.getPrecoUnitario() + "," + dp.getDesconto() + ")";
                    Conector.getInstance().criarQueryPrepared(insertDetalhesPedido);
                }
                Conector.getInstance().getConexao().commit(); //---------------- Commit Transação
                JOptionPane.showMessageDialog(null, "Novo Pedido foi criado com o código: " + codigoNovoPedido, "Pedido criado com sucesso", JOptionPane.PLAIN_MESSAGE);
            } else {
                System.out.println("Transação foi desfeita(rollback");
                Conector.getInstance().getConexao().rollback(); //--------- Rollback incremento sequencia
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gravar dados(commit) no banco de dados: " + e.getMessage());
            try {
                Conector.getInstance().getConexao().rollback(); //--------- Rollback Transação
                System.out.println("Transação foi desfeita(rollback");
            } catch (SQLException excep) {
                System.out.println("Erro ao desfazer operação(rollback): " + excep.getMessage());
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barra_rolagem_fundo = new javax.swing.JScrollPane();
        painel_fundo = new javax.swing.JPanel();
        cadastroPedidos = new javax.swing.JLabel();
        painel_lista_pedidos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_lista_pedido = new javax.swing.JTable();
        painel_dados_pedidos = new javax.swing.JPanel();
        lbl_nomeCliente = new javax.swing.JLabel();
        lbl_imposto = new javax.swing.JLabel();
        lbl_codigoCliente1 = new javax.swing.JLabel();
        lbl_codigoEnderecoFatura = new javax.swing.JLabel();
        lbl_logradouroEnderecoFatura = new javax.swing.JLabel();
        lbl_complementoEnderecoFatura = new javax.swing.JLabel();
        lbl_cidadeEnderecoFatura = new javax.swing.JLabel();
        lbl_estadoEnderecoFatura = new javax.swing.JLabel();
        lbl_paisEnderecoFatura = new javax.swing.JLabel();
        lbl_cepEnderecoFatura = new javax.swing.JLabel();
        lbl_codigoEnderecoEntrega = new javax.swing.JLabel();
        lbl_logradouroEnderecoEntrega = new javax.swing.JLabel();
        lbl_complementoEnderecoEntrega = new javax.swing.JLabel();
        lbl_cidadeEnderecoEntrega = new javax.swing.JLabel();
        lbl_estadoEnderecoEntrega = new javax.swing.JLabel();
        lbl_paisEnderecoEntrega = new javax.swing.JLabel();
        lbl_cepEnderecoEntrega = new javax.swing.JLabel();
        txt_nomeCliente = new javax.swing.JTextField();
        txt_imposto = new javax.swing.JTextField();
        txt_codigoCliente = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txt_codigoEnderecoFatura = new javax.swing.JTextField();
        txt_logradouroEnderecoFatura = new javax.swing.JTextField();
        txt_complementoEnderecoFatura = new javax.swing.JTextField();
        txt_cidadeEnderecoFatura = new javax.swing.JTextField();
        txt_estadoEnderecoFatura = new javax.swing.JTextField();
        txt_paisEnderecoFatura = new javax.swing.JTextField();
        txt_cepEnderecoFatura = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        txt_codigoEnderecoEntrega = new javax.swing.JTextField();
        txt_logradouroEnderecoEntrega = new javax.swing.JTextField();
        txt_complementoEnderecoEntrega = new javax.swing.JTextField();
        txt_cidadeEnderecoEntrega = new javax.swing.JTextField();
        txt_estadoEnderecoEntrega = new javax.swing.JTextField();
        txt_paisEnderecoEntrega = new javax.swing.JTextField();
        txt_cepEnderecoEntrega = new javax.swing.JTextField();
        btn_criarPedido = new javax.swing.JButton();
        lbl_enderecoFatura = new javax.swing.JLabel();
        lbl_enderecoEntrega = new javax.swing.JLabel();
        lbl_contaCliente = new javax.swing.JLabel();
        txt_contaCliente = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_lista_DetalhesPedido = new javax.swing.JTable();
        lbl_desconto = new javax.swing.JLabel();
        txt_desconto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lbl_codigoTransportadora = new javax.swing.JLabel();
        txt_codigoTransportadora = new javax.swing.JTextField();
        lbl_nomeTransportadora = new javax.swing.JLabel();
        txt_nomeTransportadora = new javax.swing.JTextField();
        btn_removerProdutoPedido = new javax.swing.JButton();
        painel_lista_produto = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_lista_produto = new javax.swing.JTable();
        btn_inserirProdutoPedido = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pedido");
        setToolTipText("Criar um novo pedido e inserir produtos");
        setName(""); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                fecharJanelaInterna(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        painel_fundo.setVerifyInputWhenFocusTarget(false);

        cadastroPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cadastroPedidos.setForeground(new java.awt.Color(255, 0, 0));
        cadastroPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cadastroPedidos.setText("Cadastro de Pedidos");

        painel_lista_pedidos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de pedidos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        tbl_lista_pedido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbl_lista_pedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
        {public boolean isCellEditable(int row, int column){return false;}}
    );
    tbl_lista_pedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_lista_pedido.getTableHeader().setReorderingAllowed(false);
    tbl_lista_pedido.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tbl_lista_pedidoMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tbl_lista_pedido);

    javax.swing.GroupLayout painel_lista_pedidosLayout = new javax.swing.GroupLayout(painel_lista_pedidos);
    painel_lista_pedidos.setLayout(painel_lista_pedidosLayout);
    painel_lista_pedidosLayout.setHorizontalGroup(
        painel_lista_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_lista_pedidosLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addContainerGap())
    );
    painel_lista_pedidosLayout.setVerticalGroup(
        painel_lista_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_pedidosLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    painel_dados_pedidos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
    painel_dados_pedidos.setForeground(new java.awt.Color(255, 255, 255));

    lbl_nomeCliente.setText("Nome Cliente:");

    lbl_imposto.setText("Imposto:");

    lbl_codigoCliente1.setText("Código Cliente:");

    lbl_codigoEnderecoFatura.setText("Código:");

    lbl_logradouroEnderecoFatura.setText("Logradouro:");

    lbl_complementoEnderecoFatura.setText("Complemento:");

    lbl_cidadeEnderecoFatura.setText("Cidade:");

    lbl_estadoEnderecoFatura.setText("Estado:");

    lbl_paisEnderecoFatura.setText("País:");

    lbl_cepEnderecoFatura.setText("CEP:");

    lbl_codigoEnderecoEntrega.setText("Código:");

    lbl_logradouroEnderecoEntrega.setText("Logradouro:");

    lbl_complementoEnderecoEntrega.setText("Complemento:");

    lbl_cidadeEnderecoEntrega.setText("Cidade:");

    lbl_estadoEnderecoEntrega.setText("Estado:");

    lbl_paisEnderecoEntrega.setText("País:");

    lbl_cepEnderecoEntrega.setText("CEP:");

    txt_nomeCliente.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txt_nomeClienteActionPerformed(evt);
        }
    });

    txt_imposto.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txt_impostoActionPerformed(evt);
        }
    });

    btn_criarPedido.setText("Criar Pedido");
    btn_criarPedido.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_criarPedidoActionPerformed(evt);
        }
    });

    lbl_enderecoFatura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    lbl_enderecoFatura.setText("Endereço da Fatura:");

    lbl_enderecoEntrega.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    lbl_enderecoEntrega.setText("Endereço de Entrega:");

    lbl_contaCliente.setText("Conta Cliente:");

    tbl_lista_DetalhesPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
    tbl_lista_DetalhesPedido.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Código Produto", "Nome Produto", "Preço Unitário", "Cor", "Tamanho", "Quantidade"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, true
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    tbl_lista_DetalhesPedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_lista_DetalhesPedido.getTableHeader().setReorderingAllowed(false);
    jScrollPane3.setViewportView(tbl_lista_DetalhesPedido);

    lbl_desconto.setText("Desconto por produto:");

    txt_desconto.setText("0");

    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel1.setText("Produtos:");

    lbl_codigoTransportadora.setText("Código Transportadora:");

    lbl_nomeTransportadora.setText("Nome Transportadora:");

    btn_removerProdutoPedido.setText("<html><center>Remover<br>Produto");
    btn_removerProdutoPedido.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_removerProdutoPedidoActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout painel_dados_pedidosLayout = new javax.swing.GroupLayout(painel_dados_pedidos);
    painel_dados_pedidos.setLayout(painel_dados_pedidosLayout);
    painel_dados_pedidosLayout.setHorizontalGroup(
        painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lbl_desconto)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(txt_desconto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jSeparator1))
        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator3)
                .addComponent(jSeparator2)
                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                    .addComponent(lbl_codigoCliente1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lbl_nomeCliente)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txt_nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lbl_contaCliente)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txt_contaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbl_imposto)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txt_imposto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_dados_pedidosLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btn_criarPedido)
                    .addGap(83, 83, 83))
                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                            .addComponent(lbl_codigoTransportadora)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_codigoTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_nomeTransportadora)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_nomeTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1)
                        .addComponent(lbl_enderecoFatura)
                        .addComponent(lbl_enderecoEntrega)
                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_codigoEnderecoFatura)
                                .addComponent(lbl_logradouroEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_estadoEnderecoFatura))
                            .addGap(10, 10, 10)
                            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_codigoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_logradouroEnderecoFatura)
                                        .addComponent(txt_estadoEnderecoFatura, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_complementoEnderecoFatura)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_complementoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_paisEnderecoFatura)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_paisEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_cidadeEnderecoFatura)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_cidadeEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_cepEnderecoFatura)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cepEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_codigoEnderecoEntrega)
                                .addComponent(lbl_logradouroEnderecoEntrega)
                                .addComponent(lbl_estadoEnderecoEntrega))
                            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_estadoEnderecoEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                        .addComponent(txt_logradouroEnderecoEntrega))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_complementoEnderecoEntrega)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_complementoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addComponent(lbl_paisEnderecoEntrega)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_paisEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addComponent(lbl_cidadeEnderecoEntrega)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_cidadeEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(lbl_cepEnderecoEntrega)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_cepEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(txt_codigoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_removerProdutoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE))))
    );
    painel_dados_pedidosLayout.setVerticalGroup(
        painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_nomeCliente)
                .addComponent(txt_nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoCliente1)
                .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_contaCliente)
                .addComponent(txt_contaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_imposto)
                .addComponent(txt_imposto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_codigoTransportadora)
                .addComponent(txt_codigoTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_nomeTransportadora)
                .addComponent(txt_nomeTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(4, 4, 4)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                    .addGap(27, 27, 27)
                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_codigoEnderecoFatura)
                        .addComponent(txt_codigoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_logradouroEnderecoFatura)
                        .addComponent(txt_logradouroEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_complementoEnderecoFatura)
                        .addComponent(txt_complementoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_cidadeEnderecoFatura)
                        .addComponent(txt_cidadeEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_estadoEnderecoFatura)
                        .addComponent(lbl_paisEnderecoFatura)
                        .addComponent(lbl_cepEnderecoFatura)
                        .addComponent(txt_estadoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_paisEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_cepEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(painel_dados_pedidosLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbl_enderecoFatura)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbl_enderecoEntrega)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_codigoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoEnderecoEntrega))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_logradouroEnderecoEntrega)
                .addComponent(txt_logradouroEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_complementoEnderecoEntrega)
                .addComponent(txt_complementoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cidadeEnderecoEntrega)
                .addComponent(txt_cidadeEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_estadoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_paisEnderecoEntrega)
                .addComponent(txt_paisEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_cepEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cepEnderecoEntrega)
                .addComponent(lbl_estadoEnderecoEntrega))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(btn_removerProdutoPedido)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
            .addGap(9, 9, 9)
            .addGroup(painel_dados_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_desconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_criarPedido)
                .addComponent(lbl_desconto)))
    );

    painel_lista_produto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

    tbl_lista_produto.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Código", "Nome", "Preço", "Cor", "Tamanho"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    tbl_lista_produto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_lista_produto.getTableHeader().setReorderingAllowed(false);
    jScrollPane2.setViewportView(tbl_lista_produto);

    btn_inserirProdutoPedido.setText("Inserir Produto");
    btn_inserirProdutoPedido.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_inserirProdutoPedidoActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout painel_lista_produtoLayout = new javax.swing.GroupLayout(painel_lista_produto);
    painel_lista_produto.setLayout(painel_lista_produtoLayout);
    painel_lista_produtoLayout.setHorizontalGroup(
        painel_lista_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_produtoLayout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_inserirProdutoPedido)
            .addGap(856, 856, 856))
        .addGroup(painel_lista_produtoLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    painel_lista_produtoLayout.setVerticalGroup(
        painel_lista_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_produtoLayout.createSequentialGroup()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
            .addComponent(btn_inserirProdutoPedido))
    );

    javax.swing.GroupLayout painel_fundoLayout = new javax.swing.GroupLayout(painel_fundo);
    painel_fundo.setLayout(painel_fundoLayout);
    painel_fundoLayout.setHorizontalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(cadastroPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addComponent(painel_dados_pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(painel_lista_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(painel_lista_pedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(89, 89, 89)))
            .addContainerGap(458, Short.MAX_VALUE))
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(cadastroPedidos)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addComponent(painel_lista_pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(painel_lista_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(painel_dados_pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(207, Short.MAX_VALUE))
    );

    barra_rolagem_fundo.setViewportView(painel_fundo);
    painel_fundo.getAccessibleContext().setAccessibleParent(barra_rolagem_fundo);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(barra_rolagem_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, 1313, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(barra_rolagem_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fecharJanelaInterna(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_fecharJanelaInterna

    }//GEN-LAST:event_fecharJanelaInterna

    private void btn_inserirProdutoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inserirProdutoPedidoActionPerformed
        int linhaSelecionada = tLProd.getSelectedRow();
        if (linhaSelecionada != -1 && Conector.getInstance().gerarConexao()) {
            passarDadosListaParaFormDP(linhaSelecionada);
        } else {
            JOptionPane.showMessageDialog(null, "Clique no produto a ser inserido", "Produto inválido", JOptionPane.PLAIN_MESSAGE);
        }

    }//GEN-LAST:event_btn_inserirProdutoPedidoActionPerformed

    private void txt_impostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_impostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_impostoActionPerformed

    private void txt_nomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomeClienteActionPerformed

    private void btn_criarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_criarPedidoActionPerformed
        if (validaCamposParaCriarPedido() && Conector.getInstance().gerarConexao()) {
            cadastrarPedido();
            limparFormulario();
            limparFormularioDP();
        }
    }//GEN-LAST:event_btn_criarPedidoActionPerformed

    public boolean validaCamposParaCriarPedido() {
        String floatRegexp = "[0-1]*(\\.[0-9]{1,2})?";
        boolean ehNumerico = txt_desconto.getText().matches(floatRegexp);
        double desconto = -1;
        if (ehNumerico) { //verifica se tem texto e é >0 e <=0.9
            desconto = Double.parseDouble(txt_desconto.getText());
        }

        if (ehNumerico == false || desconto >= 10) {
            JOptionPane.showMessageDialog(null, "Insira um desconto válido de 0.00-1.00 Ex: 0.55", "Desconto inválido", JOptionPane.PLAIN_MESSAGE);
            return false;
        } else if (txt_codigoCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Clique na lista para criar um novo pedido", "Campos inválido", JOptionPane.PLAIN_MESSAGE);
            return false;
        } else if (listaDetalhesProduto.qtdLinhas() <= 0) {
            JOptionPane.showMessageDialog(null, "Pedido precisa ter pelo menos um produto", "Pedido sem Produto", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        return true;
    }
    private void btn_removerProdutoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerProdutoPedidoActionPerformed
        int linhaSelecionada = tLDetProd.getSelectedRow();
        if (linhaSelecionada != -1) {
            listaDetalhesProduto.removerLinha(linhaSelecionada);
        } else {
            JOptionPane.showMessageDialog(null, "Clique no produto a ser removido", "Produto inválido", JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_btn_removerProdutoPedidoActionPerformed

    
    private void tbl_lista_pedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_lista_pedidoMouseClicked
        limparFormulario();
        int linhaSelecionada = tLPed.getSelectedRow();
        if (linhaSelecionada != -1 && Conector.getInstance().gerarConexao()) {
            if (passarDadosListaParaForm(linhaSelecionada) == false) {
                JOptionPane.showMessageDialog(null, "Clique no menu Cliente > Cadastrar para criar um novo cliente", "Cliente inexistente", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }//GEN-LAST:event_tbl_lista_pedidoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane barra_rolagem_fundo;
    private javax.swing.JButton btn_criarPedido;
    private javax.swing.JButton btn_inserirProdutoPedido;
    private javax.swing.JButton btn_removerProdutoPedido;
    private javax.swing.JLabel cadastroPedidos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_cepEnderecoEntrega;
    private javax.swing.JLabel lbl_cepEnderecoFatura;
    private javax.swing.JLabel lbl_cidadeEnderecoEntrega;
    private javax.swing.JLabel lbl_cidadeEnderecoFatura;
    private javax.swing.JLabel lbl_codigoCliente1;
    private javax.swing.JLabel lbl_codigoEnderecoEntrega;
    private javax.swing.JLabel lbl_codigoEnderecoFatura;
    private javax.swing.JLabel lbl_codigoTransportadora;
    private javax.swing.JLabel lbl_complementoEnderecoEntrega;
    private javax.swing.JLabel lbl_complementoEnderecoFatura;
    private javax.swing.JLabel lbl_contaCliente;
    private javax.swing.JLabel lbl_desconto;
    private javax.swing.JLabel lbl_enderecoEntrega;
    private javax.swing.JLabel lbl_enderecoFatura;
    private javax.swing.JLabel lbl_estadoEnderecoEntrega;
    private javax.swing.JLabel lbl_estadoEnderecoFatura;
    private javax.swing.JLabel lbl_imposto;
    private javax.swing.JLabel lbl_logradouroEnderecoEntrega;
    private javax.swing.JLabel lbl_logradouroEnderecoFatura;
    private javax.swing.JLabel lbl_nomeCliente;
    private javax.swing.JLabel lbl_nomeTransportadora;
    private javax.swing.JLabel lbl_paisEnderecoEntrega;
    private javax.swing.JLabel lbl_paisEnderecoFatura;
    private javax.swing.JPanel painel_dados_pedidos;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_pedidos;
    private javax.swing.JPanel painel_lista_produto;
    private javax.swing.JTable tbl_lista_DetalhesPedido;
    private javax.swing.JTable tbl_lista_pedido;
    private javax.swing.JTable tbl_lista_produto;
    private javax.swing.JTextField txt_cepEnderecoEntrega;
    private javax.swing.JTextField txt_cepEnderecoFatura;
    private javax.swing.JTextField txt_cidadeEnderecoEntrega;
    private javax.swing.JTextField txt_cidadeEnderecoFatura;
    private javax.swing.JTextField txt_codigoCliente;
    private javax.swing.JTextField txt_codigoEnderecoEntrega;
    private javax.swing.JTextField txt_codigoEnderecoFatura;
    private javax.swing.JTextField txt_codigoTransportadora;
    private javax.swing.JTextField txt_complementoEnderecoEntrega;
    private javax.swing.JTextField txt_complementoEnderecoFatura;
    private javax.swing.JTextField txt_contaCliente;
    private javax.swing.JTextField txt_desconto;
    private javax.swing.JTextField txt_estadoEnderecoEntrega;
    private javax.swing.JTextField txt_estadoEnderecoFatura;
    private javax.swing.JTextField txt_imposto;
    private javax.swing.JTextField txt_logradouroEnderecoEntrega;
    private javax.swing.JTextField txt_logradouroEnderecoFatura;
    private javax.swing.JTextField txt_nomeCliente;
    private javax.swing.JTextField txt_nomeTransportadora;
    private javax.swing.JTextField txt_paisEnderecoEntrega;
    private javax.swing.JTextField txt_paisEnderecoFatura;
    // End of variables declaration//GEN-END:variables

}
