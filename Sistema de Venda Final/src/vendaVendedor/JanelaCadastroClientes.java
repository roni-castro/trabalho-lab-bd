package vendaVendedor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class JanelaCadastroClientes extends javax.swing.JInternalFrame {

    private ResultSet rs;
    private final JTable tLPed;
    private final Tabela listaClientes;

    //Constantes para tabela ListaPedido
    private static final int TRATAMENTO = 0;
    private static final int PRIMEIRO_NOME = 1;
    private static final int NOME_DO_MEIO = 2;
    private static final int SOBRENOME = 3;
    private static final int SUFIXO = 4;
    private static final int SENHA = 5;
    private static final int LOGRADOURO = 6;
    private static final int COMPLEMENTO = 7;
    private static final int CIDADE = 8;
    private static final int ESTADO = 9;
    private static final int PAIS = 10;
    private static final int CEP = 11;

    public JanelaCadastroClientes() {
        initComponents();
        getContentPane().add(new JScrollPane(), "Center");
        listaClientes = new Tabela(tbl_lista_clientes);
        tLPed = listaClientes.getTabela();
        inicializarTabelaCleinte();
        btn_criarCadastro.setEnabled(false);
    }

    private void inicializarTabelaCleinte() { //Lista de valores a ser inserido
        String[] colunasLista = {
            "<html>Tratamento",
            "<html>PrimeiroNome",
            "<html>NomedoMeio",
            "<html>Sobrenome",
            "<html>Sufixo",
            "<html>Senha",
            "<html>Logradouro",
            "<html>Complemento",
            "<html>Cidade",
            "<html>Estado",
            "<html>Pais",
            "<html>CEP"
        };
        for (String coluna : colunasLista) {
            listaClientes.criarColuna(coluna);
        }

        String[][] novoCliente = {
            {"Sr.", "Roni", "César", "Castro", "", "1", "Rua Carlos de Camargo Salles, 46", "Ap", "São Carlos", "São Paulo", "Brasil", "13560555"},
            {"", "Camila", "Jales", "Rodrigues", "", "22", "Rua Flor de Lis, 20", "", "São José do Campos", "São Paulo", "Brasil", "12247655"},
            {"Sr.", "Robson", "Rodrigues", "Souza", "", "333", "Rua Flor de Lis, 20", "", "São José do Campos", "São Paulo", "Brasil", "12247655"},
            {"Sr.", "Mario", "Rodrigues", "Silva", "Jr", "4444", "5863 Sierra", "", "Bellevue", "Washington", "United States", "98004"},
            {"Sra.", "Aline", "Ferreira", "Santos", "", "55555", "555 Sierra", "", "Bellevue", "Washington", "United States", "55555"}};

        for (String[] cli : novoCliente) {
            listaClientes.criarLinha(cli);
        }
    }

    private void limparFormulario() {
        // Limpar Formulário dos dados do Cliente
        txt_tratamento.setText("");
        txt_primeiroNome.setText("");
        txt_nomeDoMeio.setText("");
        txt_sobrenome.setText("");
        txt_sufixo.setText("");
        txtp_senha.setText("");
        txt_logradouro.setText("");
        txt_complemento.setText("");
        txt_cidade.setText("");
        txt_estado.setText("");
        txt_pais.setText("");
        txt_cep.setText("");

    }

    private void removerClienteFormulario(int linhaSelecionada) {
        listaClientes.removerLinha(linhaSelecionada);

    }

    private void passarDadosListaParaForm(int linhaSelecionada) {
        txt_tratamento.setText((String) tLPed.getValueAt(linhaSelecionada, TRATAMENTO));
        txt_primeiroNome.setText((String) tLPed.getValueAt(linhaSelecionada, PRIMEIRO_NOME));
        txt_nomeDoMeio.setText((String) tLPed.getValueAt(linhaSelecionada, NOME_DO_MEIO));
        txt_sobrenome.setText((String) tLPed.getValueAt(linhaSelecionada, SOBRENOME));
        txt_sufixo.setText((String) tLPed.getValueAt(linhaSelecionada, SUFIXO));
        txtp_senha.setText((String) tLPed.getValueAt(linhaSelecionada, SENHA));
        txt_logradouro.setText((String) tLPed.getValueAt(linhaSelecionada, LOGRADOURO));
        txt_complemento.setText((String) tLPed.getValueAt(linhaSelecionada, COMPLEMENTO));
        txt_cidade.setText((String) tLPed.getValueAt(linhaSelecionada, CIDADE));
        txt_estado.setText((String) tLPed.getValueAt(linhaSelecionada, ESTADO));
        txt_pais.setText((String) tLPed.getValueAt(linhaSelecionada, PAIS));
        txt_cep.setText((String) tLPed.getValueAt(linhaSelecionada, CEP));
    }

    public boolean campoObrigatorioPreenchido() {
        String password = new String(txtp_senha.getPassword());
        if (password.equals("")
                || txt_primeiroNome.getText().equals("")
                || txt_sobrenome.getText().equals("")
                || txt_logradouro.getText().equals("")
                || txt_cidade.getText().equals("")
                || txt_estado.getText().equals("")
                || txt_pais.getText().equals("")
                || txt_cep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campos obrigatórios não foram preenchidos", "Cadastro não Realizado", JOptionPane.PLAIN_MESSAGE);
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
        painel_lista_clientes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_lista_clientes = new javax.swing.JTable();
        painel_dados_pedidos2 = new javax.swing.JPanel();
        lbl_primeiroNome = new javax.swing.JLabel();
        lbl_tratamento = new javax.swing.JLabel();
        lbl_tipoEndereco = new javax.swing.JLabel();
        lbl_logradouro = new javax.swing.JLabel();
        lbl_complemento = new javax.swing.JLabel();
        lbl_cidade = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        lbl_pais = new javax.swing.JLabel();
        lbl_cep = new javax.swing.JLabel();
        txt_primeiroNome = new javax.swing.JTextField();
        txt_tratamento = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txt_logradouro = new javax.swing.JTextField();
        txt_complemento = new javax.swing.JTextField();
        txt_cidade = new javax.swing.JTextField();
        txt_estado = new javax.swing.JTextField();
        txt_pais = new javax.swing.JTextField();
        txt_cep = new javax.swing.JTextField();
        btn_criarCadastro = new javax.swing.JButton();
        lbl_enderecoEntrega = new javax.swing.JLabel();
        lbl_sufixo = new javax.swing.JLabel();
        txt_sufixo = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        lbl_senha = new javax.swing.JLabel();
        txt_sobrenome = new javax.swing.JTextField();
        lbl_nomeDoMeio = new javax.swing.JLabel();
        txt_nomeDoMeio = new javax.swing.JTextField();
        lbl_sobrenome = new javax.swing.JLabel();
        txtp_senha = new javax.swing.JPasswordField();
        cb_tipoEndereco = new javax.swing.JComboBox();

        setClosable(true);
        setMaximumSize(new java.awt.Dimension(2147, 2147));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1148, 674));

        scroll_painel_fundo.setAutoscrolls(true);

        painel_fundo.setPreferredSize(new java.awt.Dimension(1148, 674));

        atualizacaoPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        atualizacaoPedidos.setForeground(new java.awt.Color(255, 0, 0));
        atualizacaoPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atualizacaoPedidos.setText("Cadastro Usuário");

        painel_lista_clientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Novos Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        tbl_lista_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
        {
            public boolean isCellEditable(int row, int column){return false;}
        }
    );
    tbl_lista_clientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_lista_clientes.getTableHeader().setReorderingAllowed(false);
    tbl_lista_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tbl_lista_clientesMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tbl_lista_clientes);

    javax.swing.GroupLayout painel_lista_clientesLayout = new javax.swing.GroupLayout(painel_lista_clientes);
    painel_lista_clientes.setLayout(painel_lista_clientesLayout);
    painel_lista_clientesLayout.setHorizontalGroup(
        painel_lista_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_clientesLayout.createSequentialGroup()
            .addComponent(jScrollPane1)
            .addContainerGap())
    );
    painel_lista_clientesLayout.setVerticalGroup(
        painel_lista_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_lista_clientesLayout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    painel_dados_pedidos2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
    painel_dados_pedidos2.setForeground(new java.awt.Color(255, 255, 255));

    lbl_primeiroNome.setText("*Primeiro Nome:");

    lbl_tratamento.setText("Tratamento");

    lbl_tipoEndereco.setText("Tipo:");

    lbl_logradouro.setText("*Logradouro:");

    lbl_complemento.setText("Complemento:");

    lbl_cidade.setText("*Cidade:");

    lbl_estado.setText("*Estado:");

    lbl_pais.setText("*País:");

    lbl_cep.setText("*CEP:");

    btn_criarCadastro.setText("Criar Cadastro");
    btn_criarCadastro.setPreferredSize(new java.awt.Dimension(73, 23));
    btn_criarCadastro.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_criarCadastroActionPerformed(evt);
        }
    });

    lbl_enderecoEntrega.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    lbl_enderecoEntrega.setText("Endereço de Entrega:");

    lbl_sufixo.setText("Sufixo:");

    lbl_senha.setText("*Senha:");

    lbl_nomeDoMeio.setText("Nome do Meio:");

    lbl_sobrenome.setText("*Sobrenome:");

    cb_tipoEndereco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Residencial", "Comercial" }));

    javax.swing.GroupLayout painel_dados_pedidos2Layout = new javax.swing.GroupLayout(painel_dados_pedidos2);
    painel_dados_pedidos2.setLayout(painel_dados_pedidos2Layout);
    painel_dados_pedidos2Layout.setHorizontalGroup(
        painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator8)
                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator9)
                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_tipoEndereco)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cb_tipoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(lbl_logradouro)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_complemento)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addGap(132, 132, 132)
                                    .addComponent(lbl_estado)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_pais)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_pais, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_cep)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                                    .addComponent(lbl_cidade)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 228, Short.MAX_VALUE))))
                .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                    .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_enderecoEntrega)
                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                            .addComponent(lbl_tratamento)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_tratamento, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_primeiroNome)
                            .addGap(1, 1, 1)
                            .addComponent(txt_primeiroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(4, 4, 4)
                            .addComponent(lbl_nomeDoMeio)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_nomeDoMeio, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_sobrenome)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_sobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_sufixo)
                            .addGap(5, 5, 5)
                            .addComponent(txt_sufixo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
                            .addComponent(lbl_senha)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtp_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btn_criarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    painel_dados_pedidos2Layout.setVerticalGroup(
        painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_dados_pedidos2Layout.createSequentialGroup()
            .addGap(2, 2, 2)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_primeiroNome)
                .addComponent(txt_primeiroNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_tratamento)
                .addComponent(txt_tratamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_sobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_nomeDoMeio)
                .addComponent(txt_nomeDoMeio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_sobrenome)
                .addComponent(lbl_sufixo)
                .addComponent(txt_sufixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_senha)
                .addComponent(txtp_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbl_enderecoEntrega)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_tipoEndereco)
                .addComponent(lbl_logradouro)
                .addComponent(txt_logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_complemento)
                .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cidade)
                .addComponent(txt_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cb_tipoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(painel_dados_pedidos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_pais)
                .addComponent(txt_pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbl_cep)
                .addComponent(lbl_estado)
                .addComponent(txt_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_criarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout painel_fundoLayout = new javax.swing.GroupLayout(painel_fundo);
    painel_fundo.setLayout(painel_fundoLayout);
    painel_fundoLayout.setHorizontalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(painel_lista_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painel_dados_pedidos2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(32, Short.MAX_VALUE))
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(painel_lista_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(painel_dados_pedidos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(198, Short.MAX_VALUE))
    );

    scroll_painel_fundo.setViewportView(painel_fundo);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scroll_painel_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scroll_painel_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents
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
            System.out.println("Erro ao obter Tupla do Result Set" + ex.getMessage());
        }
        return -1; // Se chegar aqui é porque não encontrou id do endereco, vai ser necessario cadastra-lo
    }

    public long gerarNovoCodigoCliente() throws SQLException {
        String queryNovoCodigo = "select seq_codigo_Cliente.NEXTVAL as codigoCliente from dual";
        rs = Conector.getInstance().criarQueryRS(queryNovoCodigo);
        if (rs.next()) {
            return Long.parseLong(rs.getString("codigoCliente")); // Se encotrar id, significa que este endereco já existe
        }
        return -1;
    }

    private long cadastrarEndereco(String logr, String comp, String cid, String est, String pais, String cep) throws SQLException {
        String strNovoEndereco;
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

    private long criarNovoCadastro() throws SQLException {
        long novoCodigoCliente = gerarNovoCodigoCliente();
        String password = new String(txtp_senha.getPassword());
        String insertCliente
                = "INSERT INTO CLIENTE VALUES ("
                + novoCodigoCliente + ","
                + "'" + txt_tratamento.getText() + "'" + ","
                + "'" + txt_primeiroNome.getText() + "'" + ","
                + "'" + txt_nomeDoMeio.getText() + "'" + ","
                + "'" + txt_sobrenome.getText() + "'" + ","
                + "'" + txt_sufixo.getText() + "'" + ","
                + "'" + password + "'"
                + ")";
        System.out.println(insertCliente);
        Conector.getInstance().criarQueryPrepared(insertCliente);
        return novoCodigoCliente;
    }

    public void cadastarClienteEndereco(long codCliente, long idEnd, String tipoEnd) throws SQLException {
        String insertClienteEndereco
                = "INSERT INTO CLIENTEENDERECO VALUES ("
                + codCliente + ","
                + idEnd + ","
                + "'" + tipoEnd + "'"
                + ")";
        System.out.println(insertClienteEndereco);
        Conector.getInstance().criarQueryPrepared(insertClienteEndereco);
    }

    private void criarCadastro() {
        long codigoEnderecoCliente, codigoNovoCliente;
        String logrE, compE, cidE, estE, paisE, cepE;
        logrE = txt_logradouro.getText();
        compE = txt_complemento.getText();
        cidE = txt_cidade.getText();
        estE = txt_estado.getText();
        paisE = txt_pais.getText();
        cepE = txt_cep.getText();

        try {
            Conector.getInstance().getConexao().setAutoCommit(false); //--------- Inicio Transação 
            codigoEnderecoCliente = ehNecessarioNovoEndereco(logrE, compE, cidE, estE, paisE, cepE); // retorna codigoEndereco ou -1(eh necessario)
            if (codigoEnderecoCliente == -1) {
                // Insere na tabela Endereco se não existir endereco
                codigoEnderecoCliente = cadastrarEndereco(logrE, compE, cidE, estE, paisE, cepE);
            }
            System.out.println("Sucesso");
            // Insere na tabela Cliente
            codigoNovoCliente = criarNovoCadastro();
            System.out.println("Sucesso2");
            //Insere na tabela ClienteEndereco
            cadastarClienteEndereco(codigoNovoCliente, codigoEnderecoCliente, (String) cb_tipoEndereco.getSelectedItem());
            System.out.println("Sucesso3");
            Conector.getInstance().getConexao().commit(); //---------------- Commit Transação
            removerClienteFormulario(listaClientes.getTabela().getSelectedRow());
            JOptionPane.showMessageDialog(null,
                    "Cliente foi cadastrado com código = " + codigoNovoCliente + " com endereco = " + codigoEnderecoCliente,
                    "Cliente cadastrado", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException ex) {
            try {
                Conector.getInstance().getConexao().rollback(); //--------- Rollback. Deu algum erro
                 JOptionPane.showMessageDialog(null,
                    "Cliente não foi cadastrado" + ex.getMessage() ,
                    "Cliente não cadastrado" , JOptionPane.PLAIN_MESSAGE);
                System.out.println("Transação foi desfeita(rollback):");
                System.out.println(ex.getMessage());
            } catch (SQLException ex1) {
                System.out.println("Erro ao dar rollback");
            }
        }
    }


    private void tbl_lista_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_lista_clientesMouseClicked
        limparFormulario();
        int linhaSelecionada = tLPed.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente da tabela", "Selecione Cliente", JOptionPane.PLAIN_MESSAGE);

        } else if (Conector.getInstance().gerarConexao()) {
            btn_criarCadastro.setEnabled(true);
            passarDadosListaParaForm(linhaSelecionada);
        }
    }//GEN-LAST:event_tbl_lista_clientesMouseClicked

    private void btn_criarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_criarCadastroActionPerformed
        if (campoObrigatorioPreenchido() && Conector.getInstance().gerarConexao()) {
            criarCadastro();
            limparFormulario();

        }
        btn_criarCadastro.setEnabled(false);
    }//GEN-LAST:event_btn_criarCadastroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atualizacaoPedidos;
    private javax.swing.JButton btn_criarCadastro;
    private javax.swing.JComboBox cb_tipoEndereco;
    private javax.swing.ButtonGroup grupo_pesquisa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbl_cep;
    private javax.swing.JLabel lbl_cidade;
    private javax.swing.JLabel lbl_complemento;
    private javax.swing.JLabel lbl_enderecoEntrega;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JLabel lbl_logradouro;
    private javax.swing.JLabel lbl_nomeDoMeio;
    private javax.swing.JLabel lbl_pais;
    private javax.swing.JLabel lbl_primeiroNome;
    private javax.swing.JLabel lbl_senha;
    private javax.swing.JLabel lbl_sobrenome;
    private javax.swing.JLabel lbl_sufixo;
    private javax.swing.JLabel lbl_tipoEndereco;
    private javax.swing.JLabel lbl_tratamento;
    private javax.swing.JPanel painel_dados_pedidos2;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_clientes;
    private javax.swing.JScrollPane scroll_painel_fundo;
    private javax.swing.JTable tbl_lista_clientes;
    private javax.swing.JTextField txt_cep;
    private javax.swing.JTextField txt_cidade;
    private javax.swing.JTextField txt_complemento;
    private javax.swing.JTextField txt_estado;
    private javax.swing.JTextField txt_logradouro;
    private javax.swing.JTextField txt_nomeDoMeio;
    private javax.swing.JTextField txt_pais;
    private javax.swing.JTextField txt_primeiroNome;
    private javax.swing.JTextField txt_sobrenome;
    private javax.swing.JTextField txt_sufixo;
    private javax.swing.JTextField txt_tratamento;
    private javax.swing.JPasswordField txtp_senha;
    // End of variables declaration//GEN-END:variables
}
