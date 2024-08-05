/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Salario;

import bancodedados.Acesso;
import dados.*;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Eduardo dos Santos Figueiredo
 * http://www.guiatrabalhista.com.br/guia/salario_minimo.htm
 * http://www.guiatrabalhista.com.br/guia/salario_minimo_1940a1999.htm
 */
public class SalarioMinimo {

    //-----------------------------------Atributos  Constantes--------------------------------------------------//
    private static final String TABELA = SalarioMinimo.class.getSimpleName();
    private static final Date DATEDEFAULT = Date.valueOf("1970-01-01");

    //----------------------------------Atributos da Classe-------------------------------------------------------//
    private static int idSalarioMinimo;
    private static Date dataVigencia = null;
    private static double valordoMinimo;
    private static String decretoLei;

    //----------------------------------Atributos  para conexão---------------------------------------------------//
    private static Connection conexao;
    private static Statement stmt;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;

    public static void main(String[] args) {
        //System.out.println(createDadostoString());
        exportarSalarioMinimo();
    }
    //-----------------------------------Metodo para Acesso ao banco de dados --------------------------------------------------//

    /**
     * Este Metodo faz o acesso ao banco de dados.
     */
    private static void salarioMinimo() {
        conexao = ModuloConector.getConecction(Acesso.getBANCO());
    }

    //-----------------------------------Metodo para Acesso da Tabela --------------------------------------------------//
    /**
     * Este Metodo faz a criação da tabela Salario do Minimo.
     */
    public static void criarTabelaSalarioMinimo() {
        if (naoExisterTabelaSalarioMinimo()) {
            String sql = " create if not exists " + Acesso.getBANCO() + "." + getTABELA() + "(idsalarioMinimo int primary key auto_increment ,\n"
                    + "dataVigencia date not null,\n"
                    + "valordoMinimo double(8,2) not null,\n"
                    + "decretoLei varchar(200) not null)";
            dados.Table.criarTabela(sql, getTABELA());
        }
    }

    /**
     * Este Metodo faz a deletação da tabela Salario Minimo.
     */
    public static void deletarTabelaSalarioMinimo() {
        if (existerTabelaSalarioMinimo()) {
            dados.Table.deletarTabela(getTABELA());
        }
    }

    /**
     * Este Metodo retornar uma informação de valor boolean que a tabela existe
     * do Salario Minimo.
     *
     * @return retornar uma informação de valor boolean que a tabela existe do
     * Salario Minimo.
     */
    public static boolean existerTabelaSalarioMinimo() {
        return !naoExisterTabelaSalarioMinimo();
    }

    /**
     * Este Metodo retornar uma informação de valor boolean que a tabela não
     * existe do Salario Minimo.
     *
     * @return retornar uma informação de valor boolean que a tabela não existe
     * do Salario Minimo.
     */
    public static boolean naoExisterTabelaSalarioMinimo() {
        return dados.Table.verificarNaoExistirTabela(Acesso.getBANCO(), getTABELA());
    }

    //-----------------------------------Metodo para Manipulação  na Tabela no Banco de Dados--------------------------------------------------//
    /**
     * OK Este metodo faz a adição do registro na tabela Salario Minimo conforme
     * os parametros.
     *
     * @param idSalarioMinimo Setar uma informação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param messagem Setar uma informação de valor boolean da Messagem de
     * comfirmação aonde TRUE: exiber a Mensagem ou FALSE: não se Exiber a
     * Messagem.
     */
    public static void adicionarSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean messagem) {
        if (naoExisteroSalarioMinimo(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei)) {
            try {
                String sql, campo = "", valor = "";
                int i = 1;
                if (idSalarioMinimo > 0) {
                    campo = "idSalarioMinimo";
                    valor = "?";
                    i++;
                }
                if (dataVigencia != null || dataVigencia.after(DATEDEFAULT)) {
                    if (i == 1) {
                        campo = "dataVigencia";
                        valor = "?";
                    } else {
                        campo += ", dataVigencia";
                        valor += ", ?";
                    }
                    i++;
                }
                if (valordoMinimo > 0.00) {
                    if (i == 1) {
                        campo = "valordoMinimo";
                        valor = "?";
                    } else {
                        campo += ", valordoMinimo";
                        valor += ", ?";
                    }
                    i++;
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    if (i == 1) {
                        campo = "decretoLei";
                        valor = "?";
                    } else {
                        campo += ", decretoLei";
                        valor += ", ?";
                    }
                    i++;
                }
                sql = "insert into " + Acesso.getBANCO() + "." + getTABELA() + "( " + campo + " ) values (" + valor + ")";
                System.out.println(sql);
                i = 1;
                salarioMinimo();
                pst = conexao.prepareStatement(sql);
                if (idSalarioMinimo > 0) {
                    pst.setInt(i++, idSalarioMinimo);
                }
                if (dataVigencia != null || dataVigencia.after(DATEDEFAULT)) {
                    pst.setDate(i++, dataVigencia);
                }
                if (valordoMinimo > 0.00) {
                    pst.setDouble(i++, valordoMinimo);
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    pst.setString(i, decretoLei);
                }
                int adicionar = pst.executeUpdate();
                if (adicionar > 0 && messagem) {
                    System.out.println("" + exibirSalarioMinimoString(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, false));
                }
            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, se);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * OK Este metodo faz a Edição do registro na tabela Salario Minimo conforme
     * os parametros.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param messagem Setar uma informação de valor boolean da Messagem de
     * comfirmação aonde TRUE: exiber a Mensagem ou FALSE: não se Exiber a
     * Messagem.
     */
    public static void editarSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean messagem) {
        boolean existe = existeroSalarioMinimo(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, true);
        System.out.println(existe);
        if (existe) {
            String campo = "dataVigencia = ?, valordoMinimo = ?, decretoLei = ?", sql;
            try {
                sql = "update " + Acesso.getBANCO() + "." + getTABELA() + " set " + campo + " where idSalarioMinimo = ?";
                System.out.println(sql);
                int i = 1;
                salarioMinimo();
                pst = conexao.prepareStatement(sql);
                if (idSalarioMinimo != 0) {
                    pst.setInt(i++, idSalarioMinimo);
                }
                if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                    pst.setDate(i++, dataVigencia);
                }
                if (valordoMinimo > 0.00) {
                    pst.setDouble(i++, valordoMinimo);
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    pst.setString(i++, decretoLei);
                }
                int editar = pst.executeUpdate();
                if (editar > 0 && messagem) {
                    System.out.println("1 " + exibirSalarioMinimoString(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, false));
                }
            } catch (SQLException se) {
                System.out.println("" + se);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * OK Fazer Este metodo faz a Exclução do registro na tabela Salario Minimo
     * conforme os parametros.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param ou Setar uma informação de valor boolean aonde True adicionar OR
     * na instrução sql ou False adicionar AND na instrução sql.
     * @param messagem Setar uma informação de valor boolean da Messagem de
     * comfirmação aonde TRUE: exiber a Mensagem ou FALSE: não se Exiber a
     * Messagem.
     */
    public static void excluirSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean ou, boolean messagem) {
        if (existeroSalarioMinimo(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, false)) {
            try {
                int i = 1;
                String sql = "delete from " + Acesso.getBANCO() + "." + getTABELA() + " where ", a, ex = exibirSalarioMinimoString(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, false);
                if (ou) {
                    a = " or ";
                } else {
                    a = " and ";
                }
                if (idSalarioMinimo != 0) {
                    sql += "idSalarioMinimo = ?";
                }
                if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                    if (i++ > 1) {
                        sql += a;
                    }
                    sql += "dataVigencia = ?";
                }
                if (valordoMinimo > 0.00) {
                    if (i++ > 1) {
                        sql += a;
                    }
                    sql += "valordoMinimo = ?";
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    if (i++ > 1) {
                        sql += a;
                    }
                    sql += "decretoLei = ?";
                }
                i = 1;
                salarioMinimo();
                pst = conexao.prepareStatement(sql);
                if (idSalarioMinimo != 0) {
                    pst.setInt(i++, idSalarioMinimo);
                }
                if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                    pst.setDate(i++, dataVigencia);
                }
                if (valordoMinimo > 0.00) {
                    pst.setDouble(i++, valordoMinimo);
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    pst.setString(i++, decretoLei);
                }
                int editar = pst.executeUpdate();
                if (editar > 0) {
                    System.out.println("" + ex);
                }
            } catch (SQLException se) {
                System.out.println("" + se);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * OK Este metodo faz a Exibição e Retornar em forma de valor String do
     * registro na tabela Salario Minimo conforme os parametros.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param ou Setar uma informação de valor boolean aonde True adicionar OR
     * na instrução sql ou False adicionar AND na instrução sql.
     * @return Retornar em forma de valor String do registro na tabela Salario
     * Minimo.
     */
    public static String exibirSalarioMinimoString(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean ou) {
        String resposta = "";
        pesquisarSalarioMinino(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, ou);
        if (getIdSalarioMinimo() > 0 || (getDataVigencia() != null && getDataVigencia().after(DATEDEFAULT)) || getValordoMinimo() > 0.00 || (getDecretoLei() != null && getDecretoLei().isEmpty())) {
            resposta = "Salario Minimo\n";
        }
        if (getIdSalarioMinimo() > 0) {
            resposta += "\nId do Salario: " + getIdSalarioMinimo();
        }
        if (getDataVigencia() != null && getDataVigencia().after(DATEDEFAULT)) {
            resposta += "\nData  de Vigencia do Salario: " + formatarDate(getDataVigencia());
        }
        if (getValordoMinimo() > 0.00) {
            resposta += "\nValor do Salario Minimo: " + formatarValorR$(getValordoMinimo());
        }
        if (getDecretoLei() != null && !getDecretoLei().isEmpty()) {
            resposta += "\nDecreto de lei do Salario: " + getDecretoLei();
        }
        return resposta;
    }

    /**
     * OK Este metodo faz a Exibição em forma de tela do registro na tabela
     * Salario Minimo conforme os parametros.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param ou Setar uma informação de valor boolean aonde True adicionar OR
     * na instrução sql ou False adicionar AND na instrução sql.
     */
    public static void exibirSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean ou) {
        if (idSalarioMinimo > 0 || (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) || valordoMinimo > 0.00 || (decretoLei != null && decretoLei.isEmpty())) {
            JOptionPane.showMessageDialog(null, exibirSalarioMinimoString(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, ou));
        }
    }

    //-------------------------------------Metodo para Manipulação e Obtenção na Tabela  do Banco de dados ------------------------------------------------//
    /**
     * OK Este metodo faz a pesquisa do registro na tabela Salario Minimo
     * conforme os parametros.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param ou Setar uma informação de valor boolean aonde True adicionar OR
     * na instrução sql ou False adicionar AND na instrução sql.
     */
    public static void pesquisarSalarioMinino(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean ou) {
        String sql = "select idSalarioMinimo, dataVigencia,  valordoMinimo, decretoLei  from " + Acesso.getBANCO() + "." + getTABELA() + " where ", a;
        int i = 1;
        if (ou) {
            a = " or ";
        } else {
            a = " and ";
        }
        if (idSalarioMinimo > 0) {
            sql += "idSalarioMinimo = ?";
            i++;
        }
        if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
            if (i > 1) {
                sql += a;
            }
            sql += "dataVigencia <= ?";
            i++;
        }
        if (valordoMinimo > 0.00) {
            if (i > 1) {
                sql += a;
            }
            sql += "valordoMinimo <= ?";
            i++;
        }
        if (decretoLei != null && !decretoLei.isEmpty()) {
            if (i > 1) {
                sql += a;
            }
            sql += "decretoLei = ?";
        }
        i = 1;

        try {
            conexao = ModuloConector.getConecction(Acesso.getBANCO());
            pst = conexao.prepareStatement(sql);
            if (idSalarioMinimo > 0) {
                pst.setInt(i++, idSalarioMinimo);
            }
            if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                pst.setDate(i++, dataVigencia);
            }
            if (valordoMinimo > 0.00) {
                pst.setDouble(i++, valordoMinimo);
            }
            if (decretoLei != null && !decretoLei.isEmpty()) {
                pst.setString(i++, decretoLei);
            }
            rs = pst.executeQuery();
            if (rs.next()) {
                i = 1;
                setIdSalarioMinimo(rs.getInt(i++));
                setDataVigencia(rs.getDate(i++));
                setValordoMinimo(rs.getDouble(i++));
                setDecretoLei(rs.getString(i++));
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, se);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
    }

    /**
     * Este Metodo retornar a obtenção uma informação de valor inteiro do ID do
     * salario conforme os parametros
     *
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param ou Setar uma informação de valor boolean aonde True adicionar OR
     * na instrução sql ou False adicionar AND na instrução sql.
     * @return retornar a obtenção uma informação de valor inteiro do ID do
     * salario
     */
    public static int obterIDSalarioMinimo(Date dataVigencia, double valordoMinimo, String decretoLei, boolean ou) {
        int id = 0;
        if (naoHaCampaVazio(0, dataVigencia, valordoMinimo, decretoLei)) {
            String sql = "select idSalarioMinimo from " + Acesso.getBANCO() + "." + getTABELA() + " where ", a;
            int i = 1;
            if (ou) {
                a = " or ";
            } else {
                a = " and ";
            }
            if (idSalarioMinimo > 0) {
                sql += "idSalarioMinimo = ?";
                i++;
            }
            if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                if (i > 1) {
                    sql += a;
                }
                sql += "dataVigencia <= ?";
                i++;
            }
            if (valordoMinimo > 0.00) {
                if (i > 1) {
                    sql += a;
                }
                sql += "valordoMinimo = ?";
                i++;
            }
            if (decretoLei != null && !decretoLei.isEmpty()) {
                if (i > 1) {
                    sql += a;
                }
                sql += "decretoLei = ?";
            }
            i = 1;
            try {
                salarioMinimo();
                pst = conexao.prepareStatement(sql);
                if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
                    pst.setDate(i++, dataVigencia);
                }
                if (valordoMinimo > 0.00) {
                    pst.setDouble(i++, valordoMinimo);
                }
                if (decretoLei != null && !decretoLei.isEmpty()) {
                    pst.setString(i++, decretoLei);
                }
                rs = pst.executeQuery();
                if (rs.next()) {
                    i = 1;
                    id = rs.getInt(i++);
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
        return id;
    }

    /**
     * Este Metodo retornar a obtenção uma omformação de valor inteiro do ultimo
     * ID da tabela Salario Minimo
     *
     * @return retornar a obtenção uma omformação de valor inteiro do ultimo ID
     * da tabela Salario Minimo
     */
    public static int obterUltimoIDSalarioMinimo() {
        int id = 0;
        String sql = "select idSalarioMinimo from " + Acesso.getBANCO() + "." + getTABELA() + " order by idSalarioMinimo desc";
        try {
            salarioMinimo();
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
        return id;
    }

    /**
     * OK Este Metodo faz o retornar uma informação de valor double do valor do
     * salario minimo do banco na tabela conforme o parametro.
     *
     * @param dataVigenciaString Setar uma informação de valor String da data da
     * vigencia do salario;
     * @return retornar uma informação de valor double do valor do salario
     * minimo do banco na tabela
     */
    public static double obterValordoSalarioMinimo(String dataVigenciaString) {
        return SalarioMinimo.obterValordoSalarioMinimo(Date.valueOf(dataVigenciaString));
    }

    /**
     * OK Este Metodo faz o retornar uma informação de valor double do valor do
     * salario minimo do banco na tabela conforme o parametro.
     *
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @return retornar uma informação de valor double do valor do salario
     * minimo do banco na tabela
     */
    public static double obterValordoSalarioMinimo(Date dataVigencia) {
        double salario = 0;
        if (dataVigencia != null && dataVigencia.after(DATEDEFAULT)) {
            try {
                String sql = "select valordoMinimo from tabela.salariominimo where dataVigencia  <=  ?";
                salarioMinimo();
                pst = conexao.prepareStatement(sql);
                pst.setDate(1, dataVigencia);
                rs = pst.executeQuery();
                if (rs.next()) {
                    salario = rs.getDouble(1);
                }
            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, se);
            } catch (NullPointerException npe) {
                JOptionPane.showInternalMessageDialog(null, npe);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
        return salario;
    }

    /**
     * OK Este Metodo faz o retornar uma informação de valor double do valor do
     * salario minimo do banco na tabela conforme o parametro.
     *
     * @param dataVigencia Setar uma informação de valor long da data da
     * vigencia do salario;
     * @return retornar uma informação de valor double do valor do salario
     * minimo do banco na tabela
     */
    public static double obterValordoSalarioMinimo(long dataVigencia) {
        return obterValordoSalarioMinimo(dataVigencia, true);
    }

    /**
     * OK Este Metodo faz o retornar uma informação de valor double do valor do
     * salario minimo do banco na tabela conforme o parametro.
     *
     * @param dataVigencia Setar uma informação de valor long da data da
     * vigencia do salario;
     * @param mensagem Setar uma informação de valor boolean da Messagem de
     * comfirmação aonde TRUE: exiber a Mensagem ou FALSE: não se Exiber a
     * Messagem.
     * @return retornar uma informação de valor double do valor do salario
     * minimo do banco na tabela
     */
    public static double obterValordoSalarioMinimo(long dataVigencia, boolean mensagem) {
        String data = DATEDEFAULT.toString(), ano, mes, dia;
        int l = 8, dt = String.valueOf(dataVigencia).length();
        if (dt == l) {
            ano = String.valueOf(dataVigencia).substring(0, 4);
            mes = String.valueOf(dataVigencia).substring(4, 6);
            dia = String.valueOf(dataVigencia).substring(6, 8);
            data = ano + "-" + mes + "-" + dia;
        } else if (dt < l && mensagem) {
            JOptionPane.showMessageDialog(null, "");
        } else if (dt > l && mensagem) {
            JOptionPane.showMessageDialog(null, "");
        }
        System.out.println(data);
        return SalarioMinimo.obterValordoSalarioMinimo(data);
    }
    //-------------------------------------Metodos de Importação E Exportação na tabela e no Banco de  ------------------------------------------------//

    /**
     * OK Este Metodo faz a importação do arquilo sql do salario minimo.
     */
    public static void importarSalarioMinino() {
        DataBase.importarBackupdataBaseSQL(Acesso.CAMINHOSALARIO, Acesso.getBANCO());
    }

    /**
     * fazer Este Metodo faz a exportação do arquivo sql do salario minimo do
     * banco de dados.
     */
    public static void exportarSalarioMinimo() {
        String criar = createDadostoString(), insercao = SalarioMinimo.InsertDadostoString(), backup = criar + "\n" + insercao;

        dados.DataBase.exportarBackupdataBaseSQL("C:\\Users\\Carlos\\Desktop\\PROGRAMAR\\" + getTABELA() + "1.sql", backup);

    }

    /**
     * Este Metodo faz a criação da instrução sql da criação do banco.
     */
    private static String createDadostoString() {
        String criar = "";
        if (Acesso.VerificarExisterBanco()) {
            criar += "create database if not exists " + Acesso.getBANCO() + ";\n";
        }
        if (SalarioMinimo.existerTabelaSalarioMinimo()) {
            criar += "create table if not exists " + Acesso.getBANCO() + "." + getTABELA()
                    + "(\nidsalarioMinimo int primary key auto_increment,\n"
                    + "dataVigencia date not null,\n"
                    + "valordoMinimo double(8,2) not null,\n"
                    + "decretoLei varchar(200) not null)";
        }
        return criar + ";";
    }

    /**
     * Este Metodo faz a criação da instrução sql da inserção de dados do Banco
     * de dados.
     */
    private static String InsertDadostoString() {
        String g = "";
        int i = 1, ultima = obterUltimoIDSalarioMinimo() + 1;
        for (; i < ultima;) {
            if (existeroSalarioMinimo(i, null, 0.00, null, false)) {
                pesquisarSalarioMinino(i, null, 0.00, null, false);
                if (i == 1) {
                    g += "insert into " + Acesso.getBANCO() + "." + getTABELA() + " ( idSalarioMinimo, dataVigencia,  valordoMinimo, decretoLei  ) values"
                            + "\n (" + getIdSalarioMinimo() + ",\"" + getDataVigencia() + "\"," + getValordoMinimo() + ",\"" + getDecretoLei() + "\")";
                } else {
                    g += ", (" + getIdSalarioMinimo() + ",\"" + getDataVigencia() + "\"," + getValordoMinimo() + ",\"" + getDecretoLei() + "\")";
                }
                if (i % 2 == 0) {
                    g += "\n";
                }
            }
            i++;
        }
        limpaCampo();
        return g + ";";
    }
    //-----------------------------------Metodos de validação de dados--------------------------------------------------//

    /**
     * Este Metodo Retorna uma informação de valor boolean aonde se for TRUE ha
     * algum campo vazio e se for FALSE não ha algum campo vazio, aonde
     * parametro "mensagem" se for TRUE: exiber a Mensagem ou FALSE: não se
     * Exiber a Messagem.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param mensagem Setar uma informação de valor boolean da Messagem de
     * comfirmação aonde TRUE: exiber a Mensagem ou FALSE: não se Exiber a
     * Messagem.
     * @return Retorna uma informação de valor boolean aonde se for TRUE ha
     * algum campo vazio e se for FALSE não ha algum campo vazio, aonde
     * parametro "mensagem" se for TRUE: exiber a Mensagem ou FALSE: não se
     * Exiber a Messagem.
     */
    public static boolean haCampaVazio(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean mensagem) {
        boolean campo = idSalarioMinimo < 0
                || (dataVigencia != null && dataVigencia.before(DATEDEFAULT) && dataVigencia.equals(DATEDEFAULT) && String.valueOf(dataVigencia).isEmpty())
                || (String.valueOf(valordoMinimo).isEmpty() && valordoMinimo <= 0)
                || decretoLei != null && decretoLei.isEmpty();
        if (campo && mensagem) {
            JOptionPane.showMessageDialog(null, CampoVazio(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei));
        }
        return campo;
    }

    /**
     * Este Metodo Retornar uma informação de valor String dos campos vazios
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @return Retornar uma informação de valor String dos campos vazios
     */
    public static String CampoVazio(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei) {
        String campo = "";
        if (idSalarioMinimo < 0) {
            campo += "O id  salario minimo e menor do que 0 ";
        }
        if ((dataVigencia != null) && dataVigencia.before(DATEDEFAULT)) {
            campo += dataVigencia + "" + DATEDEFAULT;
        } else if (dataVigencia != null && dataVigencia.equals(DATEDEFAULT)) {
            campo += dataVigencia + "" + DATEDEFAULT;
        } else {

        }
        if (String.valueOf(valordoMinimo).isEmpty() && valordoMinimo < 0.00) {
            campo += "Valor do Salario Minimo e menor do que Zero !!!";
        }
        if ((decretoLei != null) && decretoLei.isEmpty()) {
            campo += "Decreto de leo não informado";
        }
        return campo;
    }

    /**
     * Este Metodo Retorna uma informação de valor boolean aonde se for TRUE ha
     * algum campo vazio e se for FALSE não ha algum campo vazio.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @return Retorna uma informação de valor boolean aonde se for TRUE ha
     * algum campo vazio e se for FALSE não ha algum campo vazio
     */
    public static boolean haCampaVazio(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei) {
        return haCampaVazio(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, true);
    }

    /**
     * Este Metodo Retorna uma informação de valor boolean aonde se for TRUE não
     * ha algum campo vazio e se for FALSE ha algum campo vazio.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @return Retorna uma informação de valor boolean aonde se for TRUE não ha
     * algum campo vazio e se for FALSE ha algum campo vazio.
     */
    public static boolean naoHaCampaVazio(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei) {
        return !haCampaVazio(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei);
    }

    /**
     * OK Este Metodo retorrna uma informacao de valor boolean aonde se for TRUE
     * o registro existe com parametro mensagem se for TRUE: exibira a mensagem
     * ou se for FALSE: Não exibira a mensagem,se for FALSE: o registro não
     * existe.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @return retorrna uma informacao de valor boolean aonde se for TRUE o
     * registro existe com parametro mensagem se for TRUE: exibira a mensagem ou
     * se for FALSE: Não exibira a mensagem,se for FALSE: o registro não existe.
     */
    public static boolean existeroSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei) {
        return existeroSalarioMinimo(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, true);
    }

    /**
     * OK Este Metodo retorrna uma informacao de valor boolean aonde se for TRUE
     * o registro existe com parametro mensagem se for TRUE: exibira a mensagem
     * ou se for FALSE: Não exibira a mensagem,se for FALSE: o registro não
     * existe.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @param mensagem se for TRUE: exibira a mensagem ou se for FALSE: Não
     * exibira a mensagem
     * @return retorrna uma informacao de valor boolean aonde se for TRUE o
     * registro existe com parametro mensagem se for TRUE: exibira a mensagem ou
     * se for FALSE: Não exibira a mensagem,se for FALSE: o registro não existe.
     */
    public static boolean existeroSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei, boolean mensagem) {
        boolean exister;
        int id;

        if (idSalarioMinimo == 0) {
            id = obterIDSalarioMinimo(dataVigencia, valordoMinimo, decretoLei, false);

        } else {
            id = idSalarioMinimo;
        }
        pesquisarSalarioMinino(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei, false);
        exister = getIdSalarioMinimo() > 0;
        if (exister && mensagem) {
            String exibe = exibirSalarioMinimoString(id, dataVigencia, valordoMinimo, decretoLei, false);
            JOptionPane.showMessageDialog(null, "O registro :\n" + exibe + "\n já existe");
        }
        limpaCampo();
        return exister;
    }

    /**
     * OK Este Metodo retorrna uma informacao de valor boolean aonde se for TRUE
     * o registro existe com parametro mensagem se for TRUE: exibira a mensagem
     * ou se for FALSE: Não exibira a mensagem,se for FALSE: o registro não
     * existe.
     *
     * @param idSalarioMinimo Setar uma iinformação de valor inteiro do ID do
     * salario;
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     * @return
     */
    public static boolean naoExisteroSalarioMinimo(int idSalarioMinimo, Date dataVigencia, double valordoMinimo, String decretoLei) {
        return !existeroSalarioMinimo(idSalarioMinimo, dataVigencia, valordoMinimo, decretoLei);
    }
    //-----------------------------------Metodos de Manutenção de dados--------------------------------------------------//

    /**
     * Este Metodo faz a limpeza da variaveis da classe.
     */
    private static void limpaCampo() {
        setIdSalarioMinimo(0);
        setDataVigencia(DATEDEFAULT);
        setValordoMinimo(0.00);
        setDecretoLei("");
    }

    //-----------------------------------Metodos de formação de dados--------------------------------------------------//
    /**
     * Este Metodo retornar uma informaçaõ de valor String da data formatada
     * conforme parametro
     *
     * @param dataVigencia Setar uma informação de valor Date da data de
     * vigencia do salario Minimo.
     * @return retornar uma informaçaõ de valor String da data formatada
     */
    private static String formatarDate(Date dataVigencia) {
        String data = dataVigencia.toString(), ano, mes, dia;
        ano = data.substring(0, 4);
        mes = data.substring(5, 7);
        dia = data.substring(8, 10);
        data = dia + '-' + mes + "-" + ano;
        return data;
    }

    /**
     * Este Metodo Retornar uma informação de valor String da formação do valor
     * conforme o parametro
     *
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * salario minimo.
     * @return Retornar uma informação de valor String da formação do valor.
     */
    private static String formatarValor(double valordoMinimo) {
        String fv = String.valueOf(valordoMinimo);
        int n = fv.length(), i = fv.indexOf(".", 0) + 1;
        if (n - i != 2) {
            fv += "0";
        }
        return fv;
    }

    /**
     * Este Metodo Retornar uma informação de valor String da formação do valor
     * e com R$ conforme o parametro
     *
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * salario minimo.
     * @return Retornar uma informação de valor String da formação do valor e
     * com R$
     */
    private static String formatarValorR$(double valordoMinimo) {
        String fv = "R$ " + formatarValor(valordoMinimo);
        return fv;
    }

    //----------------------------------Metodos  Sets e Gets---------------------------------------------------//
    /**
     * Este metodo Retornar uma informação de valor inteiro do ID do salario;
     *
     * @return Retornar uma informação de valor inteiro do ID do salario;
     */
    public static int getIdSalarioMinimo() {
        return idSalarioMinimo;
    }

    /**
     * Este metodo Setar uma informação de valor inteiro do ID do salario;
     * @
     * @param idSalarioMinimo Setar uma informação de valor inteiro do ID do
     * salario;
     */
    public static void setIdSalarioMinimo(int idSalarioMinimo) {
        SalarioMinimo.idSalarioMinimo = idSalarioMinimo;
    }

    /**
     * Este Metodo Retornar uma informação de valor Date da data da vigencia do
     * salario;
     *
     * @return Retornar uma informação de valor Date da data da vigencia do
     * salario;
     */
    public static Date getDataVigencia() {
        return dataVigencia;
    }

    /**
     * este Metodo Setar uma informação de valor Date da data da vigencia do
     * salario;
     *
     * @param dataVigencia Setar uma informação de valor Date da data da
     * vigencia do salario;
     */
    public static void setDataVigencia(Date dataVigencia) {
        SalarioMinimo.dataVigencia = dataVigencia;
    }

    /**
     * Este Metodo Retornar uma informação de valor double do valor do minimo
     * legal do salario;
     *
     * @return Retornar uma informação de valor double do valor do minimo legal
     * do salario;
     */
    public static double getValordoMinimo() {
        return valordoMinimo;
    }

    /**
     * Este Metodo Setar uma informação de valor double do valor do minimo legal
     * do salario;
     *
     * @param valordoMinimo Setar uma informação de valor double do valor do
     * minimo legal do salario;
     */
    public static void setValordoMinimo(double valordoMinimo) {
        SalarioMinimo.valordoMinimo = valordoMinimo;
    }

    /**
     * Este Metodo Retornar uma informação de valor String do decreto de Lei
     *
     * @return Retornar uma informação de valor String do decreto de Lei
     */
    public static String getDecretoLei() {
        return decretoLei;
    }

    /**
     * Este Metodo setar uma informação de valor String do decreto de Lei do
     * salario;
     *
     * @param decretoLei Setar uma informação de valor String do decreto de Lei
     * do salario;
     */
    public static void setDecretoLei(String decretoLei) {
        SalarioMinimo.decretoLei = decretoLei;
    }

    /**
     * Este Metodo Retornar uma informação de valor String do nome da Tabela.
     *
     * @return Retornar uma informação de valor String do nome da Tabela.
     */
    public static String getTABELA() {
        return TABELA;
    }
}
//-------------------------------------------------------------------------------------//
