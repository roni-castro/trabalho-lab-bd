package vendaVendedor;

import com.toedter.calendar.JTextFieldDateEditor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class JanelaRelatorioVendedeoresQuota extends javax.swing.JInternalFrame {

    private final JTable tLPed;
    private ResultSet rs;
    private final Tabela listaPedido;
    
    private static final int NOME_VENDEDOR = 0;
    private static final int QUOTA_VENDA = 1;
    private static final int TOTAL_VENDIDO = 2;

    public JanelaRelatorioVendedeoresQuota() {
        initComponents();
        listaPedido = new Tabela(tbl_lista_pedido);
        tLPed = listaPedido.getTabela();
        inicializarColunasTabelaPedido();
        btn_gerarPDF.setEnabled(false);
    }

    private void inicializarColunasTabelaPedido() { //Lista de valores a ser inserido
        String[] colunasLista = {
            "<html>Nome<br>Vendedor",
            "<html>Quota<br>de Venda",
            "<html>Total<br>Vendido"
        };
        for (String coluna : colunasLista) {
            listaPedido.criarColuna(coluna);
        }
    }

    private void limparFormularioPedidos() {
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = qtdLinha - 1; linha >= 0; linha--) {
            listaPedido.removerLinha(linha);
        }
    }

    private void gerarRelatorio(String dataInicio, String dataFim) {
        if (Conector.getInstance().gerarConexao()) {
            String queryListagem
                    = "SELECT V.primeiroNome||' '||V.nomeDoMeio||' '||V.sobrenome as nomeVendedor, V.quota, TotalVendasVendedor"
                    + " FROM Vendedor V INNER JOIN"
                    + "                          ("
                    + "                            SELECT codigoVendedor, SUM(TotalPedido) as TotalVendasVendedor"
                    + "                              FROM("
                    + "                                    SELECT P.codigo as codigoPedido, codigoVendedor,"
                    + "                                     SUM(DP.precoUnitario * DP.quantidade * (1-DP.desconto)) as TotalPedido"
                    + "                                     FROM Pedido P, Vendedor V, DetalhesPedido DP"
                    + "                                     WHERE P.codigoVendedor = V.codigo AND"
                    + "                                          DP.codigoPedido = P.codigo AND"
                    + "                                           P.dtPedido >= to_date('" + dataInicio + "','DD/MM/YY') AND "
                    + "                                           P.dtPedido <= to_date('" + dataFim + "','DD/MM/YY') AND "
                    + "                                           codigoVendedor IS NOT NULL"
                    + "                                     GROUP BY(P.codigo, codigoVendedor)"
                    + "                                  ) PedVendDados"
                    + "                            GROUP BY(codigoVendedor)"
                    + "                          ) PedTotalVendasVendedor"
                    + " ON (V.codigo = PedTotalVendasVendedor.codigoVendedor)"
                    + " WHERE V.quota is NOT NULL AND TotalVendasVendedor >= V.quota";
            try {

                String[] linhaDaColuna = new String[3];
                rs = Conector.getInstance().criarQueryRS(queryListagem);
                while (rs.next()) {
                    linhaDaColuna[0] = rs.getString("nomeVendedor");
                    linhaDaColuna[1] = rs.getString("quota");
                    linhaDaColuna[2] = rs.getString("TotalVendasVendedor");
                    listaPedido.criarLinha(linhaDaColuna);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter Tupla do Result Set: " + ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        painel_fundo = new javax.swing.JPanel();
        painel_lista_pedido = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_lista_pedido = new javax.swing.JTable();
        atualizacaoPedidos = new javax.swing.JLabel();
        btn_gerar_relatorio = new javax.swing.JButton();
        btn_gerarPDF = new javax.swing.JButton();
        calendar_inicio = new com.toedter.calendar.JDateChooser();
        calendar_fim = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1148, 674));

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
        .addGroup(painel_lista_pedidoLayout.createSequentialGroup()
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    atualizacaoPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    atualizacaoPedidos.setForeground(new java.awt.Color(255, 0, 0));
    atualizacaoPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    atualizacaoPedidos.setText("Relatório Vendedores Cumpriram  Quota");

    btn_gerar_relatorio.setText("Gerar relatório");
    btn_gerar_relatorio.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_gerar_relatorioActionPerformed(evt);
        }
    });

    btn_gerarPDF.setText("Gerar PDF");
    btn_gerarPDF.setPreferredSize(new java.awt.Dimension(103, 23));
    btn_gerarPDF.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_gerarPDFActionPerformed(evt);
        }
    });

    calendar_inicio.getDateEditor().setEnabled(false);
    JTextFieldDateEditor editor_inicio = (JTextFieldDateEditor) calendar_inicio.getDateEditor();
    editor_inicio.setEditable(false);
    calendar_inicio.setDateFormatString("dd/MM/yy");
    calendar_inicio.setMinSelectableDate(new java.util.Date(1120708874000L));

    calendar_fim.getDateEditor().setEnabled(false);
    JTextFieldDateEditor editor_fim = (JTextFieldDateEditor) calendar_fim.getDateEditor();
    editor_fim.setEditable(false);
    calendar_fim.setDateFormatString("dd/MM/yy");

    jLabel1.setText("Data de Inicio:");

    jLabel2.setText("Data de Fim:");

    javax.swing.GroupLayout painel_fundoLayout = new javax.swing.GroupLayout(painel_fundo);
    painel_fundo.setLayout(painel_fundoLayout);
    painel_fundoLayout.setHorizontalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(painel_lista_pedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGap(0, 353, Short.MAX_VALUE))
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addComponent(btn_gerar_relatorio)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btn_gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(32, 32, 32))
                .addGroup(painel_fundoLayout.createSequentialGroup()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calendar_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)))
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(calendar_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(painel_lista_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(17, 17, 17)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(calendar_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(calendar_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1)
                .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(btn_gerar_relatorio, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addComponent(btn_gerarPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(140, Short.MAX_VALUE))
    );

    jScrollPane1.setViewportView(painel_fundo);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
    );

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

    private String converterDataString(Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String strData = "";
        if (data == null) {
            return strData;
        } else {
            return formatter.format(data);
        }

    }

    public void gerarPDF() {
        PDF pdf = new PDF(3);
        //Adicionar Colunas na Tabela do Relatorio
        String[] colunas = {"Nome Vendedor", "Quota de Venda", "Total Vendido"};
        pdf.adicionarColunaTabela(colunas);

        //Adicionar Linhas na Tabela do Relatorio
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = 0; linha < qtdLinha; linha++) {
            pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, NOME_VENDEDOR));
            pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, QUOTA_VENDA));
            pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, TOTAL_VENDIDO));            
        }
        pdf.gerarPF("VendededoresCumpriramQuota", "Relatório dos Vendedores que Cumpriram sua Quota");
    }

    private void btn_gerar_relatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerar_relatorioActionPerformed
        String dataInicio = converterDataString(calendar_inicio.getDate());
        String dataFim = converterDataString(calendar_fim.getDate());
        if (dataInicioFimValida(dataInicio, dataFim)) {
            limparFormularioPedidos();
            gerarRelatorio(dataInicio, dataFim);
            btn_gerarPDF.setEnabled(true);
        }
//
    }//GEN-LAST:event_btn_gerar_relatorioActionPerformed

    private void btn_gerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarPDFActionPerformed
           gerarPDF();
           btn_gerarPDF.setEnabled(false);
    }//GEN-LAST:event_btn_gerarPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atualizacaoPedidos;
    private javax.swing.JButton btn_gerarPDF;
    private javax.swing.JButton btn_gerar_relatorio;
    private com.toedter.calendar.JDateChooser calendar_fim;
    private com.toedter.calendar.JDateChooser calendar_inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_pedido;
    private javax.swing.JTable tbl_lista_pedido;
    // End of variables declaration//GEN-END:variables
}
