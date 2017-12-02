package vendaVendedor;

import com.toedter.calendar.JTextFieldDateEditor;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class JanelaRelatorioProdutoRegiao extends javax.swing.JInternalFrame {

    private ResultSet rs;
    private final Tabela listaPedido;
    private String granularidadeRelatorio;

    public JanelaRelatorioProdutoRegiao() {
        initComponents();
        listaPedido = new Tabela(tbl_lista_pedido);
        btn_gerarPDF.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        painel_fundo = new javax.swing.JPanel();
        painel_lista_pedido = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_lista_pedido = new javax.swing.JTable();
        cb_regiao = new javax.swing.JComboBox();
        cb_produtos = new javax.swing.JComboBox();
        calendar_inicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        calendar_fim = new com.toedter.calendar.JDateChooser();
        btn_gerarPDF = new javax.swing.JButton();
        btn_gerar_relatorio = new javax.swing.JButton();
        atualizacaoPedidos = new javax.swing.JLabel();

        setClosable(true);

        jScrollPane1.setAutoscrolls(true);

        painel_lista_pedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Relatório", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

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
    tbl_lista_pedido.setRowSelectionAllowed(false);
    tbl_lista_pedido.getTableHeader().setReorderingAllowed(false);
    jScrollPane3.setViewportView(tbl_lista_pedido);

    javax.swing.GroupLayout painel_lista_pedidoLayout = new javax.swing.GroupLayout(painel_lista_pedido);
    painel_lista_pedido.setLayout(painel_lista_pedidoLayout);
    painel_lista_pedidoLayout.setHorizontalGroup(
        painel_lista_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_lista_pedidoLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3)
            .addContainerGap())
    );
    painel_lista_pedidoLayout.setVerticalGroup(
        painel_lista_pedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
    );

    cb_regiao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cidade", "Estado", "Pais", "Continente" }));
    cb_regiao.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cb_regiaoActionPerformed(evt);
        }
    });

    cb_produtos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "todos_os_produtos", "cada_produto" }));

    calendar_inicio.getDateEditor().setEnabled(false);
    JTextFieldDateEditor editor_inicio = (JTextFieldDateEditor) calendar_inicio.getDateEditor();
    editor_inicio.setEditable(false);
    calendar_inicio.setDateFormatString("dd/MM/yyyy");
    calendar_inicio.setMinSelectableDate(new java.util.Date(1120708874000L));

    jLabel1.setText("Data de Inicio:");

    jLabel2.setText("Data de Fim:");

    calendar_fim.getDateEditor().setEnabled(false);
    JTextFieldDateEditor editor_fim = (JTextFieldDateEditor) calendar_fim.getDateEditor();
    editor_fim.setEditable(false);
    calendar_fim.setDateFormatString("dd/MM/yyyy");

    btn_gerarPDF.setText("Gerar PDF");
    btn_gerarPDF.setPreferredSize(new java.awt.Dimension(103, 23));
    btn_gerarPDF.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_gerarPDFActionPerformed(evt);
        }
    });

    btn_gerar_relatorio.setText("Gerar relatório");
    btn_gerar_relatorio.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_gerar_relatorioActionPerformed(evt);
        }
    });

    atualizacaoPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    atualizacaoPedidos.setForeground(new java.awt.Color(255, 0, 0));
    atualizacaoPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    atualizacaoPedidos.setText("Relatório de Vendas Produto X Região");

    javax.swing.GroupLayout painel_fundoLayout = new javax.swing.GroupLayout(painel_fundo);
    painel_fundo.setLayout(painel_fundoLayout);
    painel_fundoLayout.setHorizontalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(painel_lista_pedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painel_fundoLayout.createSequentialGroup()
                            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(painel_fundoLayout.createSequentialGroup()
                                    .addComponent(btn_gerar_relatorio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painel_fundoLayout.createSequentialGroup()
                                    .addComponent(cb_regiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cb_produtos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(calendar_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(calendar_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 173, Short.MAX_VALUE)))
            .addContainerGap())
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(2, 2, 2)
            .addComponent(painel_lista_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_regiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_produtos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendar_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendar_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGap(18, 18, 18)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_gerar_relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(74, Short.MAX_VALUE))
    );

    jScrollPane1.setViewportView(painel_fundo);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean dataInicioFimValida(String dataInicio, String dataFim) {
        if (dataInicio.equals("") || dataFim.equals("")) {
            JOptionPane.showMessageDialog(null, "Data de Inicio ou fim não escolhida", "Data não selecionada", JOptionPane.PLAIN_MESSAGE);
            return false;
        } else if (calendar_inicio.getDate().after(calendar_fim.getDate())) {
            JOptionPane.showMessageDialog(null, "Data de Inicio deve ser menor que a data de Fim", "Data inválida", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

        return true;
    }

    private String converterDataString(java.util.Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strData = "";
        if (data == null) {
            return strData;
        } else {
            return formatter.format(data);
        }

    }

    public void gerarPDF() {
        int qtdColunas = listaPedido.qtdColunas();
        String[] colunas = new String[qtdColunas];
        for (int col = 0; col < qtdColunas; col++) {
            colunas[col] = listaPedido.getTabela().getColumnName(col);
        }
        PDF pdf = new PDF(qtdColunas);
        //Adicionar Colunas na Tabela do Relatorio
        pdf.adicionarColunaTabela(colunas);

        //Adicionar Linhas na Tabela do Relatorio
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = 0; linha < qtdLinha; linha++) {
            for (int col = 0; col < qtdColunas; col++) {
                pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, col));
            }
        }
        pdf.gerarPF("ProdutoXRegiao" + granularidadeRelatorio, "Relatório de venda de Produto X Região");
    }

    private void limparFormularioPedidos() {
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = qtdLinha - 1; linha >= 0; linha--) {
            listaPedido.removerLinha(linha);
        }
    }

    private void gerarRelatorioProdutoRegiao(String produto, String regiao, String dataInicio, String dataFim, String proc) {
        granularidadeRelatorio = produto + "X" + regiao;
        //granularidade 1
        String[] gran1 = new String[2];
        gran1[0] = produto;
        gran1[1] = regiao;

        //granularirade 2
        String concDtInicioDtFim = dataInicio + "/" + dataFim;
        String gran2[] = new String[6];
        gran2 = concDtInicioDtFim.split("/");

        if (Conector.getInstance().gerarConexao()) {
            try {
                rs = Conector.getInstance().criarQueryCallable(proc, gran1, gran2);
                String[] linhaTab = new String[2];
                String nomeColuna, nomeLinha, valorFaturamento;
                if (produto.equals("todos_os_produtos")) {
                    listaPedido.criarColuna(regiao);
                    listaPedido.criarColuna("Faturamento");
                    while (rs.next()) {

                        nomeLinha = rs.getString(regiao); // Obtem Regiao
                        valorFaturamento = rs.getString("faturamento_total"); // Obtem faturamento

                        int linha = linhaQueDeveColocar(nomeLinha);
                        if (linha >= listaPedido.qtdLinhas()) { // Linha Nao Existe
                            linhaTab[0] = nomeLinha;
                            listaPedido.criarLinha(linhaTab);
                        }
                        listaPedido.getTabela().setValueAt(valorFaturamento, linha, 1);
                    }
                } else {
                    listaPedido.criarColuna("Produto");
                    while (rs.next()) {
                        String codigoProduto = rs.getString("codigo"); // Obtem Codigo Produto
                        nomeLinha = rs.getString("nome"); // Obtem Nome Produto
                        nomeColuna = rs.getString(regiao); // Obtem Regiao
                        valorFaturamento = rs.getString("faturamento_total"); // Obtem faturamento
                        int coluna = colunaQueDeveColocar(nomeColuna);
                        if (coluna >= listaPedido.qtdColunas()) { // Coluna nao existe
                            listaPedido.criarColuna(nomeColuna);
                        }

                        int linha = linhaQueDeveColocar(nomeLinha);
                        if (linha >= listaPedido.qtdLinhas()) { // Linha Nao Existe
                            linhaTab[0] = nomeLinha;
                            listaPedido.criarLinha(linhaTab);
                        }
                        listaPedido.getTabela().setValueAt(valorFaturamento, linha, coluna);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erro ao gerar Formulário");
                System.out.println(e.getMessage());
            }
        }
    }

    private int colunaQueDeveColocar(String nomeCol) {
        int col;
        for (col = 0; col < listaPedido.qtdColunas(); col++) {
            if (listaPedido.getTabela().getColumnName(col).equals(nomeCol)) {
                return col;
            }
        }
        return col; // acrescenta coluna
    }

    private int linhaQueDeveColocar(String nomeLin) {
        int lin;
        for (lin = 0; lin < listaPedido.qtdLinhas(); lin++) {
            String conteudoLinha = (String) listaPedido.getTabela().getValueAt(lin, 0);
            if (conteudoLinha.equals(nomeLin)) {
                return lin;
            }
        }
        return lin; // acrescenta linha
    }


    private void btn_gerar_relatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerar_relatorioActionPerformed
        String dataInicio = converterDataString(calendar_inicio.getDate());
        String dataFim = converterDataString(calendar_fim.getDate());
        if (dataInicioFimValida(dataInicio, dataFim)) {
            limparFormularioPedidos();
            listaPedido.removerColunas();
            gerarRelatorioProdutoRegiao((String) cb_produtos.getSelectedItem(), (String) cb_regiao.getSelectedItem(),
                    dataInicio, dataFim, "Vendas_ProdutoXRegiao(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            btn_gerarPDF.setEnabled(true);
        }

    }//GEN-LAST:event_btn_gerar_relatorioActionPerformed

    private void btn_gerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarPDFActionPerformed
        gerarPDF();
        btn_gerarPDF.setEnabled(false);
    }//GEN-LAST:event_btn_gerarPDFActionPerformed

    private void cb_regiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_regiaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_regiaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atualizacaoPedidos;
    private javax.swing.JButton btn_gerarPDF;
    private javax.swing.JButton btn_gerar_relatorio;
    private com.toedter.calendar.JDateChooser calendar_fim;
    private com.toedter.calendar.JDateChooser calendar_inicio;
    private javax.swing.JComboBox cb_produtos;
    private javax.swing.JComboBox cb_regiao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_pedido;
    private javax.swing.JTable tbl_lista_pedido;
    // End of variables declaration//GEN-END:variables
}
