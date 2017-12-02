package vendaVendedor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Conector {

    private static Conector instance = null;

    private Connection connection;
    private Statement stmt;
    CallableStatement cstmt;
    private PreparedStatement pstmt;

    //Singleton
    public static Conector getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (Conector.class) {
                if (instance == null) {
                    instance = new Conector();
                }
            }
        }
        return instance;
    }

    Conector() {
        connection = null;
    }

    public Connection getConexao() {
        return connection;
    }

    public void fecharConexao() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
                System.out.println("Fechando conexao");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexao");
            System.out.println(ex.getMessage());
        }
    }

    public void fecharStatement() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao fechar Statement");
            System.out.println(ex.getMessage());
        }
    }


    /*CONEXÃO Oracle Cloud ICMC - Fora do Laboratório*/
    public boolean gerarConexao() {
        //if(connection == null){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@grad.icmc.usp.br:15214:orcl14",
                    "gr1",
                    "gr1");
//                    connection = DriverManager.getConnection(
//                                "jdbc:oracle:thin:@192.168.183.14:1521:orcl14",
//                                "gs12",
//                                "gs12");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao Criar Conexao");
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe para gerar conexao não encontrada");
            return false;
        }
        //}

    }

    public ResultSet criarQueryRS(String query) throws SQLException {
        ResultSet rs = null;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(query);
        return rs;

    }

    public void criarQueryPrepared(String query) throws SQLException {
        pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Query realizada com sucesso");

    }

    public ResultSet criarQueryCallable(String procedimento, String[] granularidade1, String[] granularidade2) throws SQLException {
            cstmt = connection.prepareCall("BEGIN " + procedimento + " END;");

            // Inserir parametros para a procedure
            int param = 1;
            for (String gran1 : granularidade1) {
                cstmt.setString(param, gran1); // granularidade1
                param++;
            }
            for (String gran2 : granularidade2) {
                cstmt.setString(param, gran2); // granularidade2
                param++;
            }
            cstmt.registerOutParameter(param, OracleTypes.CURSOR); //REF CURSOR
            cstmt.execute();
            ResultSet rs = ((OracleCallableStatement) cstmt).getCursor(param);
            return rs;
    }

    public long retornarValorProximaSequencia(String query) throws SQLException {
        long valorSequencia = -1;
        synchronized (this) {
            pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(); // Executar Query

            if (rs.next()) {
                valorSequencia = rs.getLong(1);
                pstmt.close();
            }
        }

        return valorSequencia;
    }
}
