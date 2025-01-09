/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contribuicao;

import bancodedados.Acesso;
import static dados.;
import java.sql.*;

/**
 *
 * @author Carlos
 *
 * https://www.inss.gov.br/servicos-do-inss/calculo-da-guia-da-previdencia-social-gps/tabela-de-contribuicao-mensal/
 * https://www.inss.gov.br/servicos-do-inss/calculo-da-guia-da-previdencia-social-gps/como-preencher-a-gps/
 * http://receita.economia.gov.br/orientacao/tributaria/pagamentos-e-parcelamentos/emissao-e-pagamento-de-darf-das-gps-e-dae/calculo-de-contribuicoes-previdenciarias-e-emissao-de-gps/multa-de-mora-sobre-contribuicoes-previdenciarias
 * http://sal.receita.fazenda.gov.br/PortalSalInternet/faces/pages/tabelaPratica/exibirTabela.xhtml#
 *
 */
public class dadosContribuicao {

    //--------------------------------------Atributos da Classe-----------------------------------------------//
    private static int idContribuicao;
    private static String nome, telefone, endereço, NIT, PIS, PASEP;
    private static Date dataEmissao, dataVencimento;
    private int codidoPagamento, mes, ano;
    private static double valorContribuido, valorAContribuir;
    //----------------------------------------------Atributos de Conexao----------------------------------------------------------//
    private static Connection conexao;
    private static Statement stmt;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    //-------------------------------------Metodos de Acesso do Banco de dados------------------------------------------------------//

    /**
     * 
     */
    private static void dadosCont() {
        conexao = dados.ModuloConector.getConecction(Acesso.getBANCO());
    }
    //------------------------------------Metodos de Manipilação do Banco de dados-------------------------------------------------//

    /**
     * 
     */
    public static void adicionarDadosContribuicao() {
        try {
            dadosCont();
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
    }

    /**
     * 
     */
    public static void editarDadosContribuicao() {
        try {
            dadosCont();
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
    }

    /**
     * 
     */
    public static void excluirDadosContribuicao() {
        try {
            dadosCont();
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
    }

    /**
     * 
     */
    public static void pesquisarDadosContribuicao() {
        try {
            dadosCont();
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
        }
    }
    //-------------------------------------------------------------------------------------//

    //-------------------------------------------------------------------------------------//
    public static void a() {

    }

    /**
     *
     */
    public static void importarDadosContribuicao() {
        dados.DataBase.importarBackupdataBaseSQL(Acesso.CAMINHOCONTRIBUICAO, Acesso.getBANCO());
    }
    //------------------------------------Metodos gets e sets-------------------------------------------------//

    /**
     *
     * @return
     */
    public static int getIdContribuicao() {
        return idContribuicao;
    }

    /**
     *
     * @param idContribuicao
     */
    public static void setIdContribuicao(int idContribuicao) {
        dadosContribuicao.idContribuicao = idContribuicao;
    }

    /**
     *
     * @return
     */
    public static String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public static void setNome(String nome) {
        dadosContribuicao.nome = nome;
    }

    /**
     *
     * @return
     */
    public static String getTelefone() {
        return telefone;
    }

    /**
     *
     * @param telefone
     */
    public static void setTelefone(String telefone) {
        dadosContribuicao.telefone = telefone;
    }

    /**
     *
     * @return
     */
    public static String getEndereço() {
        return endereço;
    }

    /**
     *
     * @param endereço
     */
    public static void setEndereço(String endereço) {
        dadosContribuicao.endereço = endereço;
    }

    /**
     *
     * @return
     */
    public static String getNIT() {
        return NIT;
    }

    /**
     *
     * @param NIT
     */
    public static void setNIT(String NIT) {
        dadosContribuicao.NIT = NIT;
    }

    /**
     *
     * @return
     */
    public static String getPIS() {
        return PIS;
    }

    /**
     *
     * @param PIS
     */
    public static void setPIS(String PIS) {
        dadosContribuicao.PIS = PIS;
    }

    /**
     *
     * @return
     */
    public static String getPASEP() {
        return PASEP;
    }

    /**
     *
     * @param PASEP
     */
    public static void setPASEP(String PASEP) {
        dadosContribuicao.PASEP = PASEP;
    }

    /**
     *
     * @return
     */
    public static Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     *
     * @param dataEmissao
     */
    public static void setDataEmissao(Date dataEmissao) {
        dadosContribuicao.dataEmissao = dataEmissao;
    }

    /**
     *
     * @return
     */
    public static Date getDataVencimento() {
        return dataVencimento;
    }

    /**
     *
     * @param dataVencimento
     */
    public static void setDataVencimento(Date dataVencimento) {
        dadosContribuicao.dataVencimento = dataVencimento;
    }

    /**
     *
     * @return
     */
    public int getCodidoPagamento() {
        return codidoPagamento;
    }

    /**
     *
     * @param codidoPagamento
     */
    public void setCodidoPagamento(int codidoPagamento) {
        this.codidoPagamento = codidoPagamento;
    }

    /**
     *
     * @return
     */
    public int getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     *
     * @return
     */
    public int getAno() {
        return ano;
    }

    /**
     *
     * @param ano
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     *
     * @return
     */
    public static double getValorContribuido() {
        return valorContribuido;
    }

    /**
     *
     * @param valorContribuido
     */
    public static void setValorContribuido(double valorContribuido) {
        dadosContribuicao.valorContribuido = valorContribuido;
    }

    /**
     *
     * @return
     */
    public static double getValorAContribuir() {
        return valorAContribuir;
    }

    /**
     *
     * @param valorAContribuir
     */
    public static void setValorAContribuir(double valorAContribuir) {
        dadosContribuicao.valorAContribuir = valorAContribuir;
    }
    //-------------------------------------------------------------------------------------//
}
