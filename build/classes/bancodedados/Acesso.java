/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodedados;

import dados.DataBase;

/**
 *
 * @author Carlos
 */
public class Acesso {
    //-------------------------------------------------------------------------------------//

    private static final String BANCO = "tabela";
    public static final String CAMINHOSALARIO = "src\\Salario\\tabelaSalarioMinino.sql";
    public static final String CAMINHOCONTRIBUICAO = "";
    //-------------------------------------------------------------------------------------//

    public static void AcessoBanco(java.sql.Connection conexao) {
        conexao = ModuloConector.getConecction(BANCO);
    }

    public static String getBANCO() {
        return BANCO;
    }
    public static boolean VerificarExisterBanco(){
        return DataBase.verificarExisterDataBase(BANCO);
    }
    //-------------------------------------------------------------------------------------//
  
}
