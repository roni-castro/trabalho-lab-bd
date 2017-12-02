package vendaVendedor;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class TelaLogin extends javax.swing.JFrame {

    private ResultSet rs;

    public TelaLogin() {
        initComponents();
        setLocationRelativeTo(null);  // centralizar tela de login
    }

    // Retorna um objeto vendedor com seus dados ou null se nao encontrar
    private Vendedor dadosValidoVendedor(int codigoVendedor, char[] senhaVendedor) {
        String query = "SELECT codigo, primeironome||' '||nomedomeio||' '||sobrenome as nome,"
                + "senha FROM Vendedor WHERE codigo =" + codigoVendedor
+ " and senha ='" + String.valueOf(senhaVendedor)+"'";

        Vendedor v = new Vendedor(); // codigoVendedor e nome
        if (Conector.getInstance().gerarConexao()) {
            try {
                rs = Conector.getInstance().criarQueryRS(query);
                if (rs.isBeforeFirst()) { // verifica se há pelo menos uma linha
                    while (rs.next()) {
                        v.setCodigo(rs.getString("codigo"));
                        v.setNome(rs.getString("nome"));
                    }
                    Conector.getInstance().fecharStatement();
                    return v;

                }
            } catch (SQLException ex) {
                Conector.getInstance().fecharStatement();
                System.out.println("Erro ao obter Tupla do Result Set" + ex.getMessage());
            }

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icone_vendedor = new javax.swing.JLabel();
        titulo_tela = new javax.swing.JLabel();
        btn_entrar = new javax.swing.JButton();
        jf_senha = new javax.swing.JPasswordField();
        jf_codigoVendedor = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        icone_vendedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vendaVendedor/imagens/vendedor.png"))); // NOI18N

        titulo_tela.setBackground(new java.awt.Color(102, 102, 255));
        titulo_tela.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titulo_tela.setText("Sistema de Venda - Login Vendedor");

        btn_entrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vendaVendedor/imagens/entrar.png"))); // NOI18N
        btn_entrar.setText("Entrar");
        btn_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entrarActionPerformed(evt);
            }
        });

        jf_senha.setToolTipText("");
        jf_senha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jf_senhaActionPerformed(evt);
            }
        });

        jf_codigoVendedor.setText("274");
        jf_codigoVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jf_codigoVendedorActionPerformed(evt);
            }
        });

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vendaVendedor/imagens/cancelar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Código do Vendedor");
        jLabel1.setToolTipText("274");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Senha");
        jLabel2.setToolTipText("123");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titulo_tela)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(icone_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(50, 50, 50)
                                .addComponent(jf_codigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(31, 31, 31)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jf_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo_tela, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jf_codigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jf_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(icone_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entrarActionPerformed
        if (jf_codigoVendedor.getText().equals("") || jf_senha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Insira o código do vendedor e senha", "Campo inválido", JOptionPane.PLAIN_MESSAGE);
        } else {
            Vendedor vendedor = dadosValidoVendedor(Integer.parseInt(jf_codigoVendedor.getText()), jf_senha.getPassword());
            if (vendedor != null) {
                JOptionPane.showMessageDialog(null, "Bem Vindo ao Sistema de Venda: " + vendedor.getNome(), "Vendedor autenticado", JOptionPane.PLAIN_MESSAGE);

                // INICIALIZAR PROGRAMA E FECHAR JANELA DE LOGIN
                new Principal(vendedor).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Codigo ou senha inválido. Tente novamente", "Vendedor inexistente", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_entrarActionPerformed

    private void jf_codigoVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jf_codigoVendedorActionPerformed

    }//GEN-LAST:event_jf_codigoVendedorActionPerformed

    private void jf_senhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jf_senhaActionPerformed

    }//GEN-LAST:event_jf_senhaActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        if (Conector.getInstance().getConexao() != null) {
            Conector.getInstance().fecharStatement();
            Conector.getInstance().fecharConexao();
        }
        this.dispose();

    }//GEN-LAST:event_btn_cancelarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_entrar;
    private javax.swing.JLabel icone_vendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jf_codigoVendedor;
    private javax.swing.JPasswordField jf_senha;
    private javax.swing.JLabel titulo_tela;
    // End of variables declaration//GEN-END:variables

}
