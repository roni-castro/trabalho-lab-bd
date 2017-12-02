package vendaVendedor;

import com.toedter.calendar.JTextFieldDateEditor;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class JanelaAtualizacaoPedido extends javax.swing.JInternalFrame {


    private Vendedor vendedor;
    private ResultSet rs;
    private final JTable tLPed, tLProd;
    private final Tabela listaPedido;
    private final Tabela listaProduto;

    //Constantes para tabela ListaPedido
    private static final int CODIGO_PEDIDO = 0;
    private static final int DATA_PEDIDO = 1;
    private static final int DATA_ENVIO = 2;
    private static final int CODIGO_CLIENTE = 3;
    private static final int NOME_CLIENTE = 4;
    private static final int CODIGO_VENDEDOR = 5;

    public JanelaAtualizacaoPedido(Vendedor v) {
        initComponents();
        getContentPane().add(new JScrollPane(), "Center");
        vendedor = v;
        listaPedido = new Tabela(tbl_lista_pedido);
        listaProduto = new Tabela(tbl_lista_pedido);
        tLPed = listaPedido.getTabela();
        tLProd = listaProduto.getTabela();
        inicializarFormulario();
        inicializarColunasTabelaPedido();
        btn_deletarPedido.setEnabled(false);
        btn_atualizarPedido.setEnabled(false);
    }

    private void inicializarColunasTabelaPedido() { //Lista de valores a ser inserido
        String[] colunasLista = {
            "<html>Código<br>Pedido",
            "<html>Data<br>Pedido",
            "<html>Data<br>Envio",
            "<html>Código<br>Cliente",
            "<html>Nome<br>Cliente",
            "<html>Código<br>Vendedor"
        };

        for (String coluna : colunasLista) {
            listaPedido.criarColuna(coluna);
        }
    }

    private void inicializarFormulario() {
        txt_codigoPedido.setEnabled(false);
        txt_codigoCliente.setEnabled(false);
        txt_nomeCliente.setEnabled(false);
        txt_codigoVendedor.setEnabled(false);
        txt_nomeVendedor.setEnabled(false);
        txt_dtPedido.setEnabled(false);
        txt_nomeTransportadora.setEnabled(false);
        txt_codigoEnderecoFatura.setEnabled(false);
        txt_codigoEnderecoEntrega.setEnabled(false);
    }

    private void limparFormulario() {
        // Limpar Formulário dos dados do Pedido
        txt_codigoPedido.setText("");
        txt_codigoCliente.setText("");
        txt_nomeCliente.setText("");
        txt_codigoVendedor.setText("");
        txt_nomeVendedor.setText("");
        txt_dtPedido.setText("");
        calendar_dtEnvio.setCalendar(null);
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
    }

    private void limparFormularioPedidos() {
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = qtdLinha - 1; linha >= 0; linha--) {
            listaPedido.removerLinha(linha);
        }
    }

    private Date converterStringData(String strData) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date convertedDate = null;
        try {
            convertedDate = (Date) formatter.parse(strData);
        } catch (ParseException ex) {
        }
        return convertedDate;
    }

    private String converterDataString(Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String strData = "";
        if (data == null) {
            return strData;
        } else {
            return formatter.format(data);
        }

    }

    private boolean dataEnvioEhNula(int linhaSelecionada) {
        String strDataEnvio = (String) tLPed.getValueAt(linhaSelecionada, DATA_ENVIO);
        if (strDataEnvio == null) {
            strDataEnvio = "";
        }
        Date dataEnvio = converterStringData(strDataEnvio);
        return dataEnvio == null;
    }

    private boolean passarDadosListaParaForm(int linhaSelecionada) {
        // Sabe-se que a data de Envio eh Nula
        calendar_dtEnvio.setDate(converterStringData(""));
        txt_codigoPedido.setText((String) tLPed.getValueAt(linhaSelecionada, CODIGO_PEDIDO));
        txt_dtPedido.setText((String) tLPed.getValueAt(linhaSelecionada, DATA_PEDIDO));

        txt_codigoCliente.setText((String) tLPed.getValueAt(linhaSelecionada, CODIGO_CLIENTE));
        txt_nomeCliente.setText((String) tLPed.getValueAt(linhaSelecionada, NOME_CLIENTE));

        String queryDadosPedidoEndereco;

        queryDadosPedidoEndereco
                = "SELECT CPT.*,  V.primeiroNome||' '||V.nomeDoMeio||' '||V.sobrenome as nomeVendedor"
                + " FROM ( SELECT "
                + "         P.enderecoFatura,P.enderecoEntrega, "
                + "         EndF.logradouro as logradouroF, EndF.complemento as complementoF, EndF.cidade as cidadeF,"
                + "         EndF.estado as estadoF, EndF.pais as paisF, EndF.codigoPostal as codigoPostalF,"
                + "         EndE.logradouro as logradouroE, EndE.complemento as complementoE, EndE.cidade as cidadeE,"
                + "         EndE.estado as estadoE, EndE.pais as paisE, EndE.codigoPostal as codigoPostalE,"
                + "         T.nome as nomeTransportadora,"
                + "         P.codigoVendedor"
                + "         FROM Pedido P, Transportadora T, Endereco EndE, Endereco EndF"
                + "         WHERE  P.codigo =" + txt_codigoPedido.getText()
                + "         AND T.codigo = P.codigoTransportadora"
                + "         AND P.enderecoFatura = EndF.id "
                + "         AND P.enderecoEntrega = EndE.id "
                + "      )  CPT LEFT OUTER JOIN Vendedor V"
                + " ON V.codigo = CPT.codigoVendedor";

        // Obter campos do EnderecoFatura
        try {
            rs = Conector.getInstance().criarQueryRS(queryDadosPedidoEndereco);
            while (rs.next()) {
                txt_codigoEnderecoFatura.setText(rs.getString("enderecoFatura"));
                txt_codigoEnderecoEntrega.setText(rs.getString("enderecoEntrega"));

                txt_logradouroEnderecoFatura.setText(rs.getString("logradouroF"));
                txt_complementoEnderecoFatura.setText(rs.getString("complementoF"));
                txt_cidadeEnderecoFatura.setText(rs.getString("cidadeF"));
                txt_estadoEnderecoFatura.setText(rs.getString("estadoF"));
                txt_paisEnderecoFatura.setText(rs.getString("paisF"));
                txt_cepEnderecoFatura.setText(rs.getString("codigoPostalF"));

                txt_logradouroEnderecoEntrega.setText(rs.getString("logradouroE"));
                txt_complementoEnderecoEntrega.setText(rs.getString("complementoE"));
                txt_cidadeEnderecoEntrega.setText(rs.getString("cidadeE"));
                txt_estadoEnderecoEntrega.setText(rs.getString("estadoE"));
                txt_paisEnderecoEntrega.setText(rs.getString("paisE"));
                txt_cepEnderecoEntrega.setText(rs.getString("codigoPostalE"));

                txt_nomeTransportadora.setText(rs.getString("nomeTransportadora"));

                txt_codigoVendedor.setText(rs.getString("codigoVendedor"));
                txt_nomeVendedor.setText(rs.getString("nomeVendedor"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
        }
        return true;
    }

    private boolean radioBtnSelecionado() {
        return (rbtn_codigoCliente.isSelected() || rbtn_nomeCliente.isSelected()
                || rbtn_codigoPedido.isSelected() || rbtn_codigoVendedor.isSelected());
    }

    private boolean campoPesquisaVazio() {
        return (txt_pesquisa.getText().equals(""));

    }

    public boolean podeCriarPedido(int linhaSelecionada) {
        if (txt_codigoPedido.getText().equals("")
                || txt_logradouroEnderecoFatura.getText().equals("")
                || txt_cidadeEnderecoFatura.getText().equals("")
                || txt_estadoEnderecoFatura.getText().equals("")
                || txt_paisEnderecoFatura.getText().equals("")
                || txt_cepEnderecoFatura.getText().equals("")
                || txt_logradouroEnderecoEntrega.getText().equals("")
                || txt_cidadeEnderecoEntrega.getText().equals("")
                || txt_estadoEnderecoEntrega.getText().equals("")
                || txt_paisEnderecoEntrega.getText().equals("")
                || txt_cepEnderecoEntrega.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campos obrigatórios não foram preenchidos", "Campos incompletos", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo_pesquisa = new javax.swing.ButtonGroup();
        scroll_painel_fundo = new javax.swing.JScrollPane();
        painel_fundo = new javax.swing.JPanel();
        atualizacaoPedidos = new javax.swing.JLabel();
        painel_lista_pedido = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_lista_pedido = new javax.swing.JTable();
        painel_dados_pedidos2 = new javax.swing.JPanel();
        lbl_nomeCliente2 = new javax.swing.JLabel();
        lbl_codigoCliente = new javax.swing.JLabel();
        lbl_codigoEnderecoFatura2 = new javax.swing.JLabel();
        lbl_logradouroEnderecoFatura2 = new javax.swing.JLabel();
        lbl_complementoEnderecoFatura2 = new javax.swing.JLabel();
        lbl_cidadeEnderecoFatura2 = new javax.swing.JLabel();
        lbl_estadoEnderecoFatura2 = new javax.swing.JLabel();
        lbl_paisEnderecoFatura2 = new javax.swing.JLabel();
        lbl_cepEnderecoFatura2 = new javax.swing.JLabel();
        lbl_codigoEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_logradouroEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_complementoEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_cidadeEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_estadoEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_paisEnderecoEntrega2 = new javax.swing.JLabel();
        lbl_cepEnderecoEntrega2 = new javax.swing.JLabel();
        txt_nomeCliente = new javax.swing.JTextField();
        txt_codigoCliente = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        txt_codigoEnderecoFatura = new javax.swing.JTextField();
        txt_logradouroEnderecoFatura = new javax.swing.JTextField();
        txt_complementoEnderecoFatura = new javax.swing.JTextField();
        txt_cidadeEnderecoFatura = new javax.swing.JTextField();
        txt_estadoEnderecoFatura = new javax.swing.JTextField();
        txt_paisEnderecoFatura = new javax.swing.JTextField();
        txt_cepEnderecoFatura = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txt_codigoEnderecoEntrega = new javax.swing.JTextField();
        txt_logradouroEnderecoEntrega = new javax.swing.JTextField();
        txt_complementoEnderecoEntrega = new javax.swing.JTextField();
        txt_cidadeEnderecoEntrega = new javax.swing.JTextField();
        txt_estadoEnderecoEntrega = new javax.swing.JTextField();
        txt_paisEnderecoEntrega = new javax.swing.JTextField();
        txt_cepEnderecoEntrega = new javax.swing.JTextField();
        btn_atualizarPedido = new javax.swing.JButton();
        lbl_enderecoFatura = new javax.swing.JLabel();
        lbl_enderecoEntrega = new javax.swing.JLabel();
        lbl_dtPedido = new javax.swing.JLabel();
        txt_dtPedido = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        lbl_nomeTransportadora = new javax.swing.JLabel();
        txt_nomeTransportadora = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_nomeVendedor = new javax.swing.JTextField();
        lbl_dtEnvio = new javax.swing.JLabel();
        lbl_codigoPedido = new javax.swing.JLabel();
        txt_codigoPedido = new javax.swing.JTextField();
        lbl_codigoVendedor = new javax.swing.JLabel();
        txt_codigoVendedor = new javax.swing.JTextField();
        btn_deletarPedido = new javax.swing.JButton();
        calendar_dtEnvio = new com.toedter.calendar.JDateChooser();
        painel_pesquisa = new javax.swing.JPanel();
        txt_pesquisa = new javax.swing.JTextField();
        rbtn_codigoCliente = new javax.swing.JRadioButton();
        rbtn_nomeCliente = new javax.swing.JRadioButton();
        rbtn_codigoPedido = new javax.swing.JRadioButton();
        btn_pesquisar = new javax.swing.JButton();
        rbtn_codigoVendedor = new javax.swing.JRadioButton();

        setClosable(true);
        setMaximumSize(new java.awt.Dimension(2147, 2147));
        setPreferredSize(new java.awt.Dimension(1148, 674));

        painel_fundo.setPreferredSize(new java.awt.Dimension(1148, 674));

        atualizacaoPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        atualizacaoPedidos.setForeground(new java.awt.Color(255, 0, 0));
        atualizacaoPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atualizacaoPedidos.setText("Atualizar e Deletar Pedido");

        painel_lista_pedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Pedidos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        tbl_lista_pedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
        {
            public boolean isCellEditable(int row, int column){return false;}
        }
    );
    tbl_lista_pedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_lista_pedido.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tbl_lista_pedidoMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tbl_lista_pedido);

    javax.swing.GroupLayout painel_lista_pedidoLayout = new javax.swing.GroupLayout(painel_lista_pedido);
    painel_lista_pedido.setLayout(painel_lista_pedidoLayout);
    painel_lista_pedidoLayout.setHorizontalGroup(
        painel_lista_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_pedidoLayout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
            .addContainerGap())
    );
    painel_lista_pedidoLayout.setVerticalGroup(
        painel_lista_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
    );

    painel_dados_pedidos2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
    painel_dados_pedidos2.setForeground(new java.awt.Color(255, 255, 255));

    lbl_nomeCliente2.setText("Nome Cliente:");

    lbl_codigoCliente.setText("Código Cliente:");

    lbl_codigoEnderecoFatura2.setText("Código:");

    lbl_logradouroEnderecoFatura2.setText("*Logradouro:");

    lbl_complementoEnderecoFatura2.setText("*Complemento:");

    lbl_cidadeEnderecoFatura2.setText("*Cidade:");

    lbl_estadoEnderecoFatura2.setText("*Estado:");

    lbl_paisEnderecoFatura2.setText("*País:");

    lbl_cepEnderecoFatura2.setText("*CEP:");

    lbl_codigoEnderecoEntrega2.setText("Código:");

    lbl_logradouroEnderecoEntrega2.setText("*Logradouro:");

    lbl_complementoEnderecoEntrega2.setText("*Complemento:");

    lbl_cidadeEnderecoEntrega2.setText("*Cidade:");

    lbl_estadoEnderecoEntrega2.setText("*Estado:");

    lbl_paisEnderecoEntrega2.setText("*País:");

    lbl_cepEnderecoEntrega2.setText("*CEP:");

    btn_atualizarPedido.setText("Atualizar Pedido");
    btn_atualizarPedido.setPreferredSize(new java.awt.Dimension(73, 23));
    btn_atualizarPedido.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_atualizarPedidoActionPerformed(evt);
        }
    });

    lbl_enderecoFatura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    lbl_enderecoFatura.setText("Endereço da Fatura:");

    lbl_enderecoEntrega.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    lbl_enderecoEntrega.setText("Endereço de Entrega:");

    lbl_dtPedido.setText("Data Pedido:");

    lbl_nomeTransportadora.setText("Nome Transportadora:");

    jLabel1.setText("Nome Vendedor:");

    lbl_dtEnvio.setText("Data Envio:");

    lbl_codigoPedido.setText("Código Pedido:");

    lbl_codigoVendedor.setText("Código Vendedor:");

    btn_deletarPedido.setText("Deletar Pedido");
    btn_deletarPedido.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_deletarPedidoActionPerformed(evt);
        }
    });

    calendar_dtEnvio.getDateEditor().setEnabled(false);
    JTextFieldDateEditor editor_dtEnvio = (JTextFieldDateEditor) calendar_dtEnvio.getDateEditor();
    editor_dtEnvio.setEditable(false);
    calendar_dtEnvio.setDateFormatString("dd/MM/yyyy");

    javax.swing.GroupLayout painel_dados_pedidos2Layout = new javax.swing.GroupLayout(painel_dados_pedidos2);
    painel_dados_pedidos2.setLayout(painel_dados_pedidos2Layout);
    painel_dados_pedidos2Layout.setHorizontalGroup(
        painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator7)
                .addComponent(jSeparator8)
                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_enderecoFatura)
                        .addComponent(lbl_enderecoEntrega))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator9)
                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_dtPedido)
                                    .addGap(11, 11, 11)
                                    .addComponent(txt_dtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_codigoPedido)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_codigoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(4, 4, 4)
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_dtEnvio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(calendar_dtEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_codigoCliente)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_nomeTransportadora)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_nomeTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_nomeCliente2)
                                    .addGap(1, 1, 1)
                                    .addComponent(txt_nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_codigoVendedor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_codigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_nomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 23, Short.MAX_VALUE))))
                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_codigoEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_codigoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lbl_logradouroEnderecoEntrega2)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_logradouroEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lbl_complementoEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_complementoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addGap(135, 135, 135)
                                            .addComponent(lbl_estadoEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_estadoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(37, 37, 37)
                                            .addComponent(lbl_paisEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_paisEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_cepEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cepEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_cidadeEnderecoEntrega2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cidadeEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_codigoEnderecoFatura2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_codigoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_estadoEnderecoFatura2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_estadoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_logradouroEnderecoFatura2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_logradouroEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_paisEnderecoFatura2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_paisEnderecoFatura))
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_complementoEnderecoFatura2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_complementoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_cepEnderecoFatura2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cepEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                            .addComponent(lbl_cidadeEnderecoFatura2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cidadeEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                    .addComponent(btn_deletarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_atualizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(195, 195, 195)))
            .addContainerGap())
    );
    painel_dados_pedidos2Layout.setVerticalGroup(
        painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
            .addGap(2, 2, 2)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_nomeCliente2)
                .addComponent(txt_nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoCliente)
                .addComponent(txt_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1)
                .addComponent(txt_nomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoPedido)
                .addComponent(txt_codigoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoVendedor)
                .addComponent(txt_codigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nomeTransportadora)
                    .addComponent(txt_nomeTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dtEnvio)
                    .addComponent(lbl_dtPedido)
                    .addComponent(txt_dtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(calendar_dtEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbl_enderecoFatura)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_codigoEnderecoFatura2)
                .addComponent(txt_codigoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_logradouroEnderecoFatura2)
                .addComponent(txt_logradouroEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_complementoEnderecoFatura2)
                .addComponent(txt_complementoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cidadeEnderecoFatura2)
                .addComponent(txt_cidadeEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_cepEnderecoFatura2)
                    .addComponent(txt_cepEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_paisEnderecoFatura2)
                    .addComponent(txt_paisEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_estadoEnderecoFatura2)
                    .addComponent(txt_estadoEnderecoFatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbl_enderecoEntrega)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_codigoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_codigoEnderecoEntrega2)
                .addComponent(lbl_logradouroEnderecoEntrega2)
                .addComponent(txt_logradouroEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_complementoEnderecoEntrega2)
                .addComponent(txt_complementoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cidadeEnderecoEntrega2)
                .addComponent(txt_cidadeEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_estadoEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_paisEnderecoEntrega2)
                .addComponent(txt_paisEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cepEnderecoEntrega2)
                .addComponent(lbl_estadoEnderecoEntrega2)
                .addComponent(txt_cepEnderecoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btn_deletarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_atualizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    painel_pesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa de Pedidos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
    painel_pesquisa.setMaximumSize(new java.awt.Dimension(327, 327));

    grupo_pesquisa.add(rbtn_codigoCliente);
    rbtn_codigoCliente.setText("Código Cliente");

    grupo_pesquisa.add(rbtn_nomeCliente);
    rbtn_nomeCliente.setText("Nome Cliente");

    grupo_pesquisa.add(rbtn_codigoPedido);
    rbtn_codigoPedido.setText("Código Pedido");
    rbtn_codigoPedido.setContentAreaFilled(false);

    btn_pesquisar.setText("Pequisar");
    btn_pesquisar.setAlignmentX(0.5F);
    btn_pesquisar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_pesquisarActionPerformed(evt);
        }
    });

    grupo_pesquisa.add(rbtn_codigoVendedor);
    rbtn_codigoVendedor.setText("Código Vendedor");

    javax.swing.GroupLayout painel_pesquisaLayout = new javax.swing.GroupLayout(painel_pesquisa);
    painel_pesquisa.setLayout(painel_pesquisaLayout);
    painel_pesquisaLayout.setHorizontalGroup(
        painel_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_pesquisaLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rbtn_codigoPedido)
                .addComponent(rbtn_nomeCliente)
                .addComponent(rbtn_codigoCliente)
                .addComponent(rbtn_codigoVendedor)
                .addComponent(txt_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    painel_pesquisaLayout.setVerticalGroup(
        painel_pesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_pesquisaLayout.createSequentialGroup()
            .addComponent(txt_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(rbtn_codigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rbtn_nomeCliente)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rbtn_codigoPedido)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rbtn_codigoVendedor)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_pesquisar)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout painel_fundoLayout = new javax.swing.GroupLayout(painel_fundo);
    painel_fundo.setLayout(painel_fundoLayout);
    painel_fundoLayout.setHorizontalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painel_fundoLayout.createSequentialGroup()
                        .addComponent(painel_lista_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painel_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(painel_dados_pedidos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(17, Short.MAX_VALUE))
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(atualizacaoPedidos)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(painel_pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addComponent(painel_lista_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(painel_dados_pedidos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(1803, 1803, 1803))
    );

    scroll_painel_fundo.setViewportView(painel_fundo);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scroll_painel_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, 1250, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scroll_painel_fundo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisarActionPerformed
        btn_deletarPedido.setEnabled(false);
        btn_atualizarPedido.setEnabled(false);
        if (Conector.getInstance().gerarConexao()) {
            if (!campoPesquisaVazio() && radioBtnSelecionado()) {
                String queryListagem;
                limparFormularioPedidos();
                if (rbtn_codigoCliente.isSelected()) {
                    queryListagem
                            = "SELECT P.codigo as codigoPedido,"
                            + "       to_char(P.dtPedido, 'dd/mm/yy') as dtPedido,"
                            + "       to_char(P.dtEnvio, 'dd/mm/yy') as dtEnvio,"
                            + "       C.codigo as codigoCliente, "
                            + "       C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome as nomeCliente,"
                            + "       T.codigo, T.nome as nomeTransportadora, P.codigoVendedor"
                            + "       FROM Pedido P, Transportadora T, Cliente C"
                            + "       WHERE C.codigo = " + txt_pesquisa.getText()
                            + "              AND C.codigo = P.codigoCliente "
                            + "              AND T.codigo = P.codigoTransportadora";

                } else if (rbtn_nomeCliente.isSelected()) {
                    queryListagem
                            = "SELECT P.codigo as codigoPedido,"
                            + "       to_char(P.dtPedido, 'dd/mm/yy') as dtPedido,"
                            + "       to_char(P.dtEnvio, 'dd/mm/yy') as dtEnvio,"
                            + "       C.codigo as codigoCliente, "
                            + "       C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome as nomeCliente,"
                            + "       T.codigo, T.nome as nomeTransportadora, P.codigoVendedor"
                            + "       FROM Pedido P, Transportadora T, Cliente C"
                            + "       WHERE C.codigo = P.codigoCliente "
                            + "             AND T.codigo = P.codigoTransportadora"
                            + "             AND LOWER(C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome)"
                            + "             LIKE '%" + txt_pesquisa.getText().toLowerCase() + "%'";
                } else if (rbtn_codigoPedido.isSelected()) {
                    queryListagem
                            = "SELECT P.codigo as codigoPedido,"
                            + "       to_char(P.dtPedido, 'dd/mm/yy') as dtPedido,"
                            + "       to_char(P.dtEnvio, 'dd/mm/yy') as dtEnvio,"
                            + "       C.codigo as codigoCliente, "
                            + "       C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome as nomeCliente,"
                            + "       T.codigo, T.nome as nomeTransportadora, P.codigoVendedor"
                            + "       FROM Pedido P, Transportadora T, Cliente C"
                            + "       WHERE P.codigo = " + txt_pesquisa.getText()
                            + "              AND C.codigo = P.codigoCliente "
                            + "              AND T.codigo = P.codigoTransportadora";

                } else {
                    queryListagem
                            = "SELECT P.codigo as codigoPedido,"
                            + "       to_char(P.dtPedido, 'dd/mm/yy') as dtPedido,"
                            + "       to_char(P.dtEnvio, 'dd/mm/yy') as dtEnvio,"
                            + "       C.codigo as codigoCliente, "
                            + "       C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome as nomeCliente,"
                            + "       T.codigo, T.nome as nomeTransportadora, P.codigoVendedor"
                            + "       FROM Pedido P, Transportadora T, Cliente C, Vendedor V"
                            + "       WHERE V.codigo = " + txt_pesquisa.getText()
                            + "              AND V.codigo = P.codigoVendedor "
                            + "              AND C.codigo = P.codigoCliente "
                            + "              AND T.codigo = P.codigoTransportadora";
                }

                try {
                    String[] linhaDaColuna = new String[6];
                    rs = Conector.getInstance().criarQueryRS(queryListagem);
                    while (rs.next()) {
                        linhaDaColuna[0] = rs.getString("codigoPedido");
                        linhaDaColuna[1] = rs.getString("dtPedido");
                        linhaDaColuna[2] = rs.getString("dtenvio");
                        linhaDaColuna[3] = rs.getString("codigoCliente");
                        linhaDaColuna[4] = rs.getString("nomeCliente");
                        linhaDaColuna[5] = rs.getString("codigoVendedor");
                        listaPedido.criarLinha(linhaDaColuna);
                    }
                } catch (SQLException ex) {
                    System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Há dados inuficientes para realizar a pesquisa: ", "Falha na pesquisa", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_pesquisarActionPerformed
    public long ehNecessarioNovoEndereco(String logr, String comp, String cid, String est, String pais, String cep) {
        String queryEnderecoExitente, strComplemento;

        strComplemento
                = comp.equals("") ? " IS NULL" : "='" + comp + "'";
        queryEnderecoExitente
                = "SELECT id "
                + " FROM Endereco "
                + " WHERE logradouro='" + logr + "'"
                + " AND complemento " + strComplemento
                + " AND cidade = '" + cid + "'"
                + " AND estado = '" + est + "'"
                + " AND pais = '" + pais + "'"
                + " AND codigoPostal = '" + cep + "'";

        try {
            rs = Conector.getInstance().criarQueryRS(queryEnderecoExitente);
            if (rs.next()) {
                return Long.parseLong(rs.getString("id")); // Se encotrar id, significa que este endereco já existe
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter Tupla do Result Set");
        }
        return -1; // Se chegar aqui é porque não encontrou id do endereco, vai ser necessario cadastra-lo
    }

    private void atualizaPedido() {
        long codigoEnderecoEntrega, codigoEnderecoFatura;
        String logrE, compE, cidE, estE, paisE, cepE, logrF, compF, cidF, estF, paisF, cepF;
        logrE = txt_logradouroEnderecoEntrega.getText();
        compE = txt_complementoEnderecoEntrega.getText();
        cidE = txt_cidadeEnderecoEntrega.getText();
        estE = txt_estadoEnderecoEntrega.getText();
        paisE = txt_paisEnderecoEntrega.getText();
        cepE = txt_cepEnderecoEntrega.getText();

        logrF = txt_logradouroEnderecoFatura.getText();
        compF = txt_complementoEnderecoFatura.getText();
        cidF = txt_cidadeEnderecoFatura.getText();
        estF = txt_estadoEnderecoFatura.getText();
        paisF = txt_paisEnderecoFatura.getText();
        cepF = txt_cepEnderecoFatura.getText();

        codigoEnderecoEntrega = ehNecessarioNovoEndereco(logrE, compE, cidE, estE, paisE, cepE); // retorna codigoEndereco ou -1(eh necessario)
        codigoEnderecoFatura = ehNecessarioNovoEndereco(logrF, compF, cidF, estF, paisF, cepF);  // retorna codigoEndereco ou -1(eh necessario)
        try {
            Conector.getInstance().getConexao().setAutoCommit(false); //--------- Inicio Transação 
            if (codigoEnderecoEntrega == -1) {
                codigoEnderecoEntrega = cadastrarEndereco(logrE, compE, cidE, estE, paisE, cepE);
            }
            if (codigoEnderecoFatura == -1) {
                codigoEnderecoFatura = cadastrarEndereco(logrF, compF, cidF, estF, paisF, cepF);
            }
            // codigoNovoEndereco chega como novo endereco gerado pela sequencia, ou já existente
            String updatePedido
                    = "UPDATE Pedido "
                    + " SET enderecoEntrega =" + codigoEnderecoEntrega + ","
                    + "     enderecoFatura =" + codigoEnderecoFatura + ","
                    + "     dtEnvio = cast(to_date('" + converterDataString(calendar_dtEnvio.getDate()) + "','DD/MM/YYYY') as timestamp)"
                    + " WHERE Pedido.codigo =" + txt_codigoPedido.getText();

            Conector.getInstance().criarQueryPrepared(updatePedido);
            Conector.getInstance().getConexao().commit(); //---------------- Commit Transação
            JOptionPane.showMessageDialog(null,
                    "Pedido foi atualizado. enderecoFatura:" + codigoEnderecoFatura + ". enderecoEntrega:" + codigoEnderecoEntrega,
                    "Pedido atualizado com sucesso", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException ex) {
            try {
                Conector.getInstance().getConexao().rollback(); //--------- Rollback. Deu algum erro
                System.out.println("Transação foi desfeita(rollback):");
                System.out.println(ex.getMessage());
            } catch (SQLException ex1) {
                System.out.println("Erro ao dar rollback");
            }
        }
    }

    private void excluirPedido(int linhaSelecionada) {
        String codigoPedido = (String) tLPed.getValueAt(linhaSelecionada, CODIGO_PEDIDO);

        try {
            Conector.getInstance().getConexao().setAutoCommit(false); //--------- Inicio Transação 
            String queryDeletarPedido = "DELETE Pedido WHERE Pedido.codigo =" + codigoPedido;
            Conector.getInstance().criarQueryPrepared(queryDeletarPedido);
            Conector.getInstance().getConexao().commit(); //---------------- Commit Transação
            JOptionPane.showMessageDialog(null, "Pedido " + codigoPedido + " foi deletado.", "Exclusão de Pedido", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException ex) {
            try {
                Conector.getInstance().getConexao().rollback(); //--------- Rollback. Deu algum erro
                System.out.println("Transação foi desfeita(rollback):");
                System.out.println(ex.getMessage());
            } catch (SQLException ex1) {
                System.out.println("Erro ao dar rollback");
            }
        }
    }

    private long cadastrarEndereco(String logr, String comp, String cid, String est, String pais, String cep) throws SQLException {
        String strNovoEndereco, updatePedido;
        strNovoEndereco = "select seq_codigo_Endereco.NEXTVAL from dual";
        long codigoNovoEndereco = Conector.getInstance().retornarValorProximaSequencia(strNovoEndereco);
        String insertEndereco
                = "INSERT INTO Endereco VALUES ("
                + codigoNovoEndereco + ","
                + "'" + logr + "',"
                + "'" + comp + "',"
                + "'" + cid + "',"
                + "'" + est + "',"
                + "'" + pais + "',"
                + "'" + cep + "')";
        Conector.getInstance().criarQueryPrepared(insertEndereco);
        return codigoNovoEndereco;
    }

    private void tbl_lista_pedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_lista_pedidoMouseClicked
        limparFormulario();
        int linhaSelecionada = tLPed.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido da tabela", "Selecione pedido", JOptionPane.PLAIN_MESSAGE);

        } else if (!dataEnvioEhNula(linhaSelecionada)) {
            JOptionPane.showMessageDialog(null, "Pedido já foi enviado. Não pode ser modificado", "Pedido não pode ser modificado", JOptionPane.PLAIN_MESSAGE);

        } else if (!vendedorPodeModificarPedido(linhaSelecionada)) {
            JOptionPane.showMessageDialog(null,
                    "Pedido de outro vendedor. Não pode ser modificado por você: (" + vendedor.getCodigo() + ") " + vendedor.getNome(),
                    "Pedido não pode ser modificado", JOptionPane.PLAIN_MESSAGE);

        } else if (Conector.getInstance().gerarConexao()) {
            btn_deletarPedido.setEnabled(true);
            btn_atualizarPedido.setEnabled(true);
            passarDadosListaParaForm(linhaSelecionada);
        }
    }//GEN-LAST:event_tbl_lista_pedidoMouseClicked

    private boolean vendedorPodeModificarPedido(int linhaSelecionada) {
        String codVendLinhaSelecionada = (String) tLPed.getValueAt(linhaSelecionada, CODIGO_VENDEDOR);
        return (vendedor.getCodigo().equals(codVendLinhaSelecionada) || codVendLinhaSelecionada.equals(""));
    }

    private void btn_atualizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarPedidoActionPerformed
        if (Conector.getInstance().gerarConexao()) {
            atualizaPedido();
            limparFormulario();
            limparFormularioPedidos();
        }
        btn_deletarPedido.setEnabled(false);
        btn_atualizarPedido.setEnabled(false);
    }//GEN-LAST:event_btn_atualizarPedidoActionPerformed

    private void btn_deletarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarPedidoActionPerformed
        int linhaSelecionada = tLPed.getSelectedRow();
        if (Conector.getInstance().gerarConexao()) {
            excluirPedido(linhaSelecionada);
            limparFormulario();
            limparFormularioPedidos();
        }
        btn_deletarPedido.setEnabled(false);
        btn_atualizarPedido.setEnabled(false);
    }//GEN-LAST:event_btn_deletarPedidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atualizacaoPedidos;
    private javax.swing.JButton btn_atualizarPedido;
    private javax.swing.JButton btn_deletarPedido;
    private javax.swing.JButton btn_pesquisar;
    private com.toedter.calendar.JDateChooser calendar_dtEnvio;
    private javax.swing.ButtonGroup grupo_pesquisa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbl_cepEnderecoEntrega2;
    private javax.swing.JLabel lbl_cepEnderecoFatura2;
    private javax.swing.JLabel lbl_cidadeEnderecoEntrega2;
    private javax.swing.JLabel lbl_cidadeEnderecoFatura2;
    private javax.swing.JLabel lbl_codigoCliente;
    private javax.swing.JLabel lbl_codigoEnderecoEntrega2;
    private javax.swing.JLabel lbl_codigoEnderecoFatura2;
    private javax.swing.JLabel lbl_codigoPedido;
    private javax.swing.JLabel lbl_codigoVendedor;
    private javax.swing.JLabel lbl_complementoEnderecoEntrega2;
    private javax.swing.JLabel lbl_complementoEnderecoFatura2;
    private javax.swing.JLabel lbl_dtEnvio;
    private javax.swing.JLabel lbl_dtPedido;
    private javax.swing.JLabel lbl_enderecoEntrega;
    private javax.swing.JLabel lbl_enderecoFatura;
    private javax.swing.JLabel lbl_estadoEnderecoEntrega2;
    private javax.swing.JLabel lbl_estadoEnderecoFatura2;
    private javax.swing.JLabel lbl_logradouroEnderecoEntrega2;
    private javax.swing.JLabel lbl_logradouroEnderecoFatura2;
    private javax.swing.JLabel lbl_nomeCliente2;
    private javax.swing.JLabel lbl_nomeTransportadora;
    private javax.swing.JLabel lbl_paisEnderecoEntrega2;
    private javax.swing.JLabel lbl_paisEnderecoFatura2;
    private javax.swing.JPanel painel_dados_pedidos2;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_pedido;
    private javax.swing.JPanel painel_pesquisa;
    private javax.swing.JRadioButton rbtn_codigoCliente;
    private javax.swing.JRadioButton rbtn_codigoPedido;
    private javax.swing.JRadioButton rbtn_codigoVendedor;
    private javax.swing.JRadioButton rbtn_nomeCliente;
    private javax.swing.JScrollPane scroll_painel_fundo;
    private javax.swing.JTable tbl_lista_pedido;
    private javax.swing.JTextField txt_cepEnderecoEntrega;
    private javax.swing.JTextField txt_cepEnderecoFatura;
    private javax.swing.JTextField txt_cidadeEnderecoEntrega;
    private javax.swing.JTextField txt_cidadeEnderecoFatura;
    private javax.swing.JTextField txt_codigoCliente;
    private javax.swing.JTextField txt_codigoEnderecoEntrega;
    private javax.swing.JTextField txt_codigoEnderecoFatura;
    private javax.swing.JTextField txt_codigoPedido;
    private javax.swing.JTextField txt_codigoVendedor;
    private javax.swing.JTextField txt_complementoEnderecoEntrega;
    private javax.swing.JTextField txt_complementoEnderecoFatura;
    private javax.swing.JTextField txt_dtPedido;
    private javax.swing.JTextField txt_estadoEnderecoEntrega;
    private javax.swing.JTextField txt_estadoEnderecoFatura;
    private javax.swing.JTextField txt_logradouroEnderecoEntrega;
    private javax.swing.JTextField txt_logradouroEnderecoFatura;
    private javax.swing.JTextField txt_nomeCliente;
    private javax.swing.JTextField txt_nomeTransportadora;
    private javax.swing.JTextField txt_nomeVendedor;
    private javax.swing.JTextField txt_paisEnderecoEntrega;
    private javax.swing.JTextField txt_paisEnderecoFatura;
    private javax.swing.JTextField txt_pesquisa;
    // End of variables declaration//GEN-END:variables
}
