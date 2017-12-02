package vendaVendedor;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

public class JanelaRelatorio15Pedidos extends javax.swing.JInternalFrame {

    private final JTable tLPed;
    private ResultSet rs;
    private final Tabela listaPedido;

    private static final int NOME_CLIENTE = 0;
    private static final int QTD_PEDIDOS = 1;

    public JanelaRelatorio15Pedidos() {
        initComponents();
        listaPedido = new Tabela(tbl_lista_pedido);
        tLPed = listaPedido.getTabela();
        inicializarColunasTabelaPedido();
        gerarRelatorio();
    }

    private void inicializarColunasTabelaPedido() { //Lista de valores a ser inserido
        String[] colunasLista = {
            "<html>Nome<br>Cliente",
            "<html>Quantidade<br>de Compras"
        };
        for (String coluna : colunasLista) {
            listaPedido.criarColuna(coluna);
        }
    }

    private void gerarRelatorio() {
        if (Conector.getInstance().gerarConexao()) {
            String queryListagem;
            queryListagem
                    = "SELECT C.primeiroNome||' '||C.nomeDoMeio||' '||C.sobrenome as nomeCliente, PedCli15Mais.qtdPedidos"
                    + " FROM Cliente C JOIN ("
                    + "                      SELECT P.codigoCliente as codigo, count(*) as qtdPedidos"
                    + "                      FROM Pedido P"
                    + "                      GROUP BY(codigoCliente)"
                    + "                      HAVING (count(*) >= 15)"
                    + "                    ) PedCli15Mais"
                    + " USING (codigo)"
                    + " ORDER BY(PedCli15Mais.qtdPedidos) DESC";
            try {
                String[] linhaDaColuna = new String[2];
                rs = Conector.getInstance().criarQueryRS(queryListagem);
                while (rs.next()) {
                    linhaDaColuna[0] = rs.getString("nomeCliente");
                    linhaDaColuna[1] = rs.getString("qtdPedidos");
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
        btn_gerarPDF = new javax.swing.JButton();

        setClosable(true);
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    atualizacaoPedidos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    atualizacaoPedidos.setForeground(new java.awt.Color(255, 0, 0));
    atualizacaoPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    atualizacaoPedidos.setText("Relatório de clientes realizaram ao menos 15 Pedidos");

    btn_gerarPDF.setText("Gerar PDF");
    btn_gerarPDF.setPreferredSize(new java.awt.Dimension(103, 23));
    btn_gerarPDF.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_gerarPDFActionPerformed(evt);
        }
    });

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
            .addGap(0, 341, Short.MAX_VALUE))
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btn_gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    painel_fundoLayout.setVerticalGroup(
        painel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(painel_fundoLayout.createSequentialGroup()
            .addComponent(atualizacaoPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(painel_lista_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(140, Short.MAX_VALUE))
    );

    jScrollPane1.setViewportView(painel_fundo);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public void gerarPDF() {
        PDF pdf = new PDF(2);
        //Adicionar Colunas na Tabela do Relatorio
        String[] colunas = {"Nome Cliente", "Quantidade de Compras"};
        pdf.adicionarColunaTabela(colunas);

        //Adicionar Linhas na Tabela do Relatorio
        int qtdLinha = listaPedido.qtdLinhas();
        for (int linha = 0; linha < qtdLinha; linha++) {
            pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, NOME_CLIENTE));
            pdf.adicionarCelulaTabela(listaPedido.getCelula(linha, QTD_PEDIDOS));
        }
        pdf.gerarPF("ClientesCom15MaisPedidos", "Relatório de clientes que realizaram ao menos 15 Pedidos");
    }

    private void btn_gerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarPDFActionPerformed
        gerarPDF();

    }//GEN-LAST:event_btn_gerarPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atualizacaoPedidos;
    private javax.swing.JButton btn_gerarPDF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel painel_fundo;
    private javax.swing.JPanel painel_lista_pedido;
    private javax.swing.JTable tbl_lista_pedido;
    // End of variables declaration//GEN-END:variables
}
