/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Const.Messagem;
import java.sql.*;

/**
 *
 * @author Carlos
 */
public class Table extends ModuloConector {

    public static void main(String[] args) {

    }
    private static Connection conexao;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    private static PreparedStatement pst;

    private static void Table() {
        conexao = ModuloConector.getConecction();
    }

    /**
     * Este Metodo Verifica se determinada Tabela nao existe return um valor
     * Boolean
     *
     * @since 01/05/2019
     * @param Tabela Setar uma Informação de tipo String com o nome da Tabela
     * @return Retornar um valor Boolean
     *
     */
    public static Boolean VerificarNaoExistirTabela(String dataBase, String Tabela) {
        try {
            if (NaoHaCampoVazio(null, Tabela)) {
                Table();
                String sql = "show tables in " + dataBase + " like ?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, Tabela + "%");
                rs = pst.executeQuery();
                if (rs.next()) {
                    return false;
                }
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            } else {
                Messagem.chamarTela("O campo tabela esta Vazio !!!");
            }
        } catch (SQLException e) {
            Messagem.chamarTela(e);
        }
        ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        return true;
    }

    // Incio das Tabelas
    /**
     * Este Metodo criar uma tabela no banco de dados.
     *
     * @version 1.6
     * @since 21/07/2019
     * @param sql Seta uma informação de valor String da instrução MySql.
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    public static void criarTabela(String sql, String Tabela) {
        if (NaoHaCampoVazio(sql, Tabela)) {
            try {
                Table();
                Messagem.criadoTabela(Tabela);
                if (Messagem.getCriada() == 0) {
                    stmt = conexao.createStatement();
                    int criar = stmt.executeUpdate(sql);
                    if (criar == 0) {
                        Messagem.chamarTela(Messagem.tabelaCriada(Tabela));
                        ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
                    }
                }
            } catch (SQLException e) {
                Messagem.chamarTela(Tabela + " erro Metodo CriarTabela: " + e);
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * ok Este Metodo deletar a tabela mo banco de dados
     *
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    public static void deletarTabela(String Tabela) {
        if (NaoHaCampoVazio(null, Tabela)) {
            try {
                Table();
                String sql = "drop table if exists " + Tabela;
                Messagem.deletadaTabela(Tabela);
                if (Messagem.getDeleta() == 0) {
                    stmt = conexao.createStatement();
                    int DEL = stmt.executeUpdate(sql);
                    if (DEL > 0) {
                        Messagem.chamarTela(Messagem.tabelaCriada(Tabela));
                    }
                }
            } catch (SQLException e) {
                Messagem.chamarTela(Tabela + " erro Metodo DeletaTabela: " + e);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * @version 1.6
     * @since 21/07/2019
     * @param sql Seta uma informação de valor String da instrução MySql.
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    private static boolean HaCampoVazio(String sql, String Tabela) {
        boolean campo = false;
        if (sql != null && sql.isEmpty() && Tabela != null && Tabela.isEmpty()) {
            campo = Tabela.isEmpty() || sql.isEmpty();
        } else if (sql != null && sql.isEmpty()) {
            campo = sql.isEmpty();
        } else if (Tabela != null && Tabela.isEmpty()) {
            campo = Tabela.isEmpty();
        }
        if (campo) {
            Messagem.chamarTela(Messagem.VAZIO(HaCampoVaziotoString(sql, Tabela)));
        }
        return campo;
    }

    /**
     * @version 1.6
     * @since 21/07/2019
     * @param sql Seta uma informação de valor String da instrução MySql.
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    private static String[] HaCampoVaziotoString(String sql, String Tabela) {
        String[] campo = new String[2];
        int i = 0;
        if (sql != null && sql.isEmpty()) {
            campo[i++] = "Instrução SQL";
        }
        if (Tabela != null && Tabela.isEmpty()) {
            campo[i++] = "Tabela";
        }
        return campo;
    }

    /**
     * @version 1.6
     * @since 21/07/2019
     * @param sql Seta uma informação de valor String da instrução MySql.
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    private static boolean NaoHaCampoVazio(String sql, String Tabela) {
        return !HaCampoVazio(sql, Tabela);
    }

    // Fim das Tabelas
    // Inicio dos Sets e Gets
    /**
     * @version 1.6
     * @since 21/07/2019
     * @param Tabela Seta uma informação de valor String do nome da Tabela.
     */
    public static void selecionarTodos(String Tabela) {
        if (NaoHaCampoVazio(null, Tabela)) {
            String sql = "select * from " + Tabela;
            try {
                Table();
                stmt = conexao.createStatement();
                rs = stmt.executeQuery(sql);
                rs.getArray(1);
            } catch (SQLException se) {
                Messagem.chamarTela(se);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * Este Metodo Obtem o numero de Linha de uma determinada tabela no banco
     *
     * @since 10/07/2019
     * @param tabela Setar uma informação devalor String com o none da tabela .
     * @return Retornar uma informação de valor inteiro com a quantidade de
     * linha.
     */
    public static int quantLinha(String dataBase, String tabela) {
        String sql = "select * from " + tabela;
        return quantLinha(dataBase, tabela, sql);
    }

    /**
     * Este Metodo Obtem o numero de Linha de uma determinada tabela no banco
     *
     * @since 11/07/2019
     * @version 1.5
     * @param tabela Setar uma informação devalor String com o none da tabela .
     * @param sql Setar uma Informação de Valor String com a instrução MYSQL.
     * @return Retornar uma informação de valor inteiro com a quantidade de
     * linha.
     */
    // para obter o numero de linha eu tenho que sabe se  Tabela existe; Depois saber ser a instrução mysql obter algum resultado
    public static int quantLinha(String dataBase, String tabela, String sql) {
        try {
            Table();
            if (!Table.VerificarNaoExistirTabela(dataBase, tabela)) {
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                rsmd = rs.getMetaData();
                if (rs.next()) {
                } else {
                    Messagem.chamarTela("0");
                }
            } else {
                Messagem.chamarTela("1");
            }
        } catch (SQLException e) {
            Messagem.chamarTela("Metodo QuantLinha da Classe ModuloConector: " + e);
        }
        return 0;
    }

    /**
     * Este Metodo Obtem o numero de Coluna de uma determinada tabela no banco.
     *
     * @since 10/07/2019
     * @version 1.4
     * @param tabela Setar uma informação devalor String com o none da tabela .
     * @return Retornar uma infornação de valor inteiro com a quantidade de
     * coluna.
     */
    public static int quantColuna(String dataBase, String tabela) {
        String sql = "selec * from ?";
        return quantColuna(dataBase, tabela, sql);
    }

    /**
     * Este Metodo Obtem o numero de Coluna de uma determinada tabela no banco.
     *
     * @since 11/07/2019
     * @version 1.5
     * @param tabela Setar uma informação devalor String com o none da tabela .
     * @param sql Setar uma Informação de Valor String com a instrução MYSQL.
     * @return Retornar uma informação de valor inteiro com a quantidade de
     * coluna.
     */
    public static int quantColuna(String dataBase, String tabela, String sql) {
        Table();
        try {
            if (Table.VerificarNaoExistirTabela(dataBase, tabela)) {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tabela);
                rs = pst.executeQuery();
                if (rs.next()) {
                    rsmd = rs.getMetaData();
                    return rsmd.getColumnCount();
                } else {
                    Messagem.chamarTela("");
                }
            } else {
                Messagem.chamarTela("");
            }
        } catch (SQLException e) {
            Messagem.chamarTela("Metodo QuantColuna da Classe ModuloConector: " + e);
        }
        return 0;
    }

}
