package vendaVendedor;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

public class Principal extends javax.swing.JFrame {

    Vendedor vendedor;
    public Principal(Vendedor v) {
        
        initComponents();
        vendedor = v;
        setLayout(new BorderLayout());
        add(jdpPrincipal, BorderLayout.CENTER);
        setExtendedState(MAXIMIZED_BOTH);
        lbl_nomeVendedor.setText("("+v.getCodigo() +") " + v.getNome());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpPrincipal = new javax.swing.JDesktopPane();
        jLabel2 = new javax.swing.JLabel();
        lbl_nomeVendedor = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        crud = new javax.swing.JMenu();
        inserir = new javax.swing.JMenuItem();
        atualizar = new javax.swing.JMenuItem();
        menu_relatorio = new javax.swing.JMenu();
        relatorio_15_pedidos = new javax.swing.JMenuItem();
        relatorio_vendedores_quota = new javax.swing.JMenuItem();
        relatorio_regiaoXtempo = new javax.swing.JMenuItem();
        relatorio_produto_regiao = new javax.swing.JMenuItem();
        relatorio_produto_tempo = new javax.swing.JMenuItem();
        menu_cliente = new javax.swing.JMenu();
        cadastrar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Vendas");
        setAutoRequestFocus(false);
        setForeground(java.awt.Color.lightGray);
        setName(""); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jdpPrincipal.setBackground(new java.awt.Color(204, 204, 204));
        jdpPrincipal.setAutoscrolls(true);

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("Vendedor");

        lbl_nomeVendedor.setBackground(new java.awt.Color(0, 0, 0));
        lbl_nomeVendedor.setForeground(new java.awt.Color(255, 0, 0));
        lbl_nomeVendedor.setText("jLabel3");

        javax.swing.GroupLayout jdpPrincipalLayout = new javax.swing.GroupLayout(jdpPrincipal);
        jdpPrincipal.setLayout(jdpPrincipalLayout);
        jdpPrincipalLayout.setHorizontalGroup(
            jdpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdpPrincipalLayout.createSequentialGroup()
                .addContainerGap(550, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_nomeVendedor)
                .addGap(28, 28, 28))
        );
        jdpPrincipalLayout.setVerticalGroup(
            jdpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdpPrincipalLayout.createSequentialGroup()
                .addContainerGap(505, Short.MAX_VALUE)
                .addGroup(jdpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_nomeVendedor))
                .addContainerGap())
        );
        jdpPrincipal.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jdpPrincipal.setLayer(lbl_nomeVendedor, javax.swing.JLayeredPane.DEFAULT_LAYER);

        //UIManager.put("nimbusBase", new java.awt.Color(204, 204, 204));
        //UIManager.put("nimbusBlueGrey", new java.awt.Color(204, 204, 204));

        getContentPane().add(jdpPrincipal);
        jdpPrincipal.setBounds(0, 0, 690, 530);

        crud.setText("Pedido");
        crud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crudActionPerformed(evt);
            }
        });

        inserir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        inserir.setText("Inserir");
        inserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirActionPerformed(evt);
            }
        });
        crud.add(inserir);

        atualizar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        atualizar.setText("Atualizar e Deletar");
        atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarActionPerformed(evt);
            }
        });
        crud.add(atualizar);

        menu.add(crud);

        menu_relatorio.setText("Relatórios");

        relatorio_15_pedidos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        relatorio_15_pedidos.setText("Clientes com ao menos 15 Pedidos ");
        relatorio_15_pedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatorio_15_pedidosActionPerformed(evt);
            }
        });
        menu_relatorio.add(relatorio_15_pedidos);

        relatorio_vendedores_quota.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        relatorio_vendedores_quota.setText("Vendedores Atingiram Quota");
        relatorio_vendedores_quota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatorio_vendedores_quotaActionPerformed(evt);
            }
        });
        menu_relatorio.add(relatorio_vendedores_quota);

        relatorio_regiaoXtempo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        relatorio_regiaoXtempo.setText("Regiao X Tempo");
        relatorio_regiaoXtempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatorio_regiaoXtempoActionPerformed(evt);
            }
        });
        menu_relatorio.add(relatorio_regiaoXtempo);

        relatorio_produto_regiao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        relatorio_produto_regiao.setText("Produto X Região");
        relatorio_produto_regiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatorio_produto_regiaoActionPerformed(evt);
            }
        });
        menu_relatorio.add(relatorio_produto_regiao);

        relatorio_produto_tempo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        relatorio_produto_tempo.setText("Produto X Tempo");
        relatorio_produto_tempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatorio_produto_tempoActionPerformed(evt);
            }
        });
        menu_relatorio.add(relatorio_produto_tempo);

        menu.add(menu_relatorio);

        menu_cliente.setText("Cliente");

        cadastrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        cadastrar.setText("Cadastrar");
        cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarActionPerformed(evt);
            }
        });
        menu_cliente.add(cadastrar);

        menu.add(menu_cliente);

        setJMenuBar(menu);

        getAccessibleContext().setAccessibleName("Sistema de Venda");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void crudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crudActionPerformed
        
    }//GEN-LAST:event_crudActionPerformed

    private void inserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirActionPerformed
        JanelaCadastroPedido janelaInserir = new JanelaCadastroPedido(vendedor);
        jdpPrincipal.add(janelaInserir);
        janelaInserir.setVisible(true);
        
        try {
            janelaInserir.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_inserirActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(Conector.getInstance().getConexao()!= null){
            Conector.getInstance().fecharStatement();
            Conector.getInstance().fecharConexao();
        }
    }//GEN-LAST:event_formWindowClosing

    private void atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarActionPerformed
        JanelaAtualizacaoPedido janelaAtualizar = new JanelaAtualizacaoPedido(vendedor);
        jdpPrincipal.add(janelaAtualizar);
        janelaAtualizar.setVisible(true);
        
        try {
            janelaAtualizar.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_atualizarActionPerformed

    private void relatorio_15_pedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio_15_pedidosActionPerformed
       JanelaRelatorio15Pedidos janela15Pedidos = new JanelaRelatorio15Pedidos();
        jdpPrincipal.add(janela15Pedidos);
        janela15Pedidos.setVisible(true);
        
        try {
            janela15Pedidos.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_relatorio_15_pedidosActionPerformed

    private void relatorio_vendedores_quotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio_vendedores_quotaActionPerformed
       JanelaRelatorioVendedeoresQuota janelaVendedoresQuota = new JanelaRelatorioVendedeoresQuota();
        jdpPrincipal.add(janelaVendedoresQuota);
        janelaVendedoresQuota.setVisible(true);
        
        try {
            janelaVendedoresQuota.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_relatorio_vendedores_quotaActionPerformed

    private void relatorio_regiaoXtempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio_regiaoXtempoActionPerformed
        JanelaRelatorioRegiaoTempo janelaRegiaoTempo = new JanelaRelatorioRegiaoTempo();
        jdpPrincipal.add(janelaRegiaoTempo);
        janelaRegiaoTempo.setVisible(true);
        
        try {
            janelaRegiaoTempo.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_relatorio_regiaoXtempoActionPerformed

    private void relatorio_produto_regiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio_produto_regiaoActionPerformed
        JanelaRelatorioProdutoRegiao janelaProdutoRegiao = new JanelaRelatorioProdutoRegiao();
        jdpPrincipal.add(janelaProdutoRegiao);
        janelaProdutoRegiao.setVisible(true);
        
        try {
            janelaProdutoRegiao.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_relatorio_produto_regiaoActionPerformed

    private void relatorio_produto_tempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio_produto_tempoActionPerformed
       JanelaRelatorioProdutoTempo janelaProdutoTempo = new JanelaRelatorioProdutoTempo();
        jdpPrincipal.add(janelaProdutoTempo);
        janelaProdutoTempo.setVisible(true);
        
        try {
            janelaProdutoTempo.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_relatorio_produto_tempoActionPerformed

    private void cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarActionPerformed
         JanelaCadastroClientes janelaCadastroCliente = new JanelaCadastroClientes();
        jdpPrincipal.add(janelaCadastroCliente);
        janelaCadastroCliente.setVisible(true);
        
        try {
            janelaCadastroCliente.setMaximum(true);
        } catch (PropertyVetoException e) {
            System.out.println("Erro ao redimensionar a tela da janela Interna");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_cadastrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
          
            }
        });  
 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem atualizar;
    private javax.swing.JMenuItem cadastrar;
    private javax.swing.JMenu crud;
    private javax.swing.JMenuItem inserir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JDesktopPane jdpPrincipal;
    private javax.swing.JLabel lbl_nomeVendedor;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menu_cliente;
    private javax.swing.JMenu menu_relatorio;
    private javax.swing.JMenuItem relatorio_15_pedidos;
    private javax.swing.JMenuItem relatorio_produto_regiao;
    private javax.swing.JMenuItem relatorio_produto_tempo;
    private javax.swing.JMenuItem relatorio_regiaoXtempo;
    private javax.swing.JMenuItem relatorio_vendedores_quota;
    // End of variables declaration//GEN-END:variables
}
