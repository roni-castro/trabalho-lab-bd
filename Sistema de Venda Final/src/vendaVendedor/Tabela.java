package vendaVendedor;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabela {

    private final DefaultTableModel dm;
    private final JTable tabela;

    Tabela(JTable tab) {
        tabela = tab;
        dm = (DefaultTableModel) tabela.getModel();
    }

    public JTable getTabela() {
        return tabela;
    }

    public void criarColuna(String nomeColuna) {
        dm.addColumn(nomeColuna);
    }

    public void criarLinha(String[] linha) {
        dm.addRow(linha);
    }

    public void removerLinha(int linha) {
        dm.removeRow(linha);
    }

    public void removerColunas() {
        dm.setColumnCount(0);
    }

    public int qtdLinhas() {
        return tabela.getRowCount();
    }

    public int qtdColunas() {
        return tabela.getColumnCount();
    }

    public String getCelula(int linha, int coluna) {
        return (String) tabela.getValueAt(linha, coluna);
    }

}
