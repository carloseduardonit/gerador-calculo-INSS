/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contribuicao;

/**
 *
 * @author Carlos
 */
public class Juros {

    private static double capital, taxa, montante;
    private static int tempo;

    /**
     * 1 - O juros pode ser simples ou composto 1.1 juros simples 1.2 juros
     * composto
     */
    public static void Juros() {

    }

    public static void main(String[] args) {
        System.err.println(JuroSimplestoString(1000, 2, 5));
    }

    // j=c*i*t
    /**
     * Este Metodo retornar uma informação de valor double do resultado do juros
     * simples.
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return Retornar uma informação de valor double do resultado do juros
     * simples.
     */
    public static double JuroSimples(double capital, double taxa, int tempo) {
        final double in = porcetagem(taxa);
        return capital * in * tempo;
    }

    /**
     * Este Metodo retornar uma informação de valor String do resultado do juros
     * simples e com R$.
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return Retornar uma informação de valor String do resultado do juros
     * simples e com R$.
     */
    public static String JuroSimplestoString(double capital, double taxa, int tempo) {
        return formatarValorR$(JuroSimples(capital, taxa, tempo));
    }

    /**
     *
     * @param montante Setar uma informação de valor
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @return Retornar uma informação de valor double do resultado do juros
     * composto
     */
    public static double JuroComposto(final double montante, double capital) {
        return montante - capital;
    }

    /**
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return
     */
    public static double montantedoJurosSimples(double capital, double taxa, int tempo) {
        final double montante = capital, juros = JuroSimples(capital, taxa, tempo);
        return montante + juros;
    }

    /**
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return
     */
    public static String montantedoJurosSimplesToString(double capital, double taxa, int tempo) {
        return formatarValorR$(montantedoJurosSimples(capital, taxa, tempo));
    }

    /**
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return
     */
    public static double montantedoJurosComposto(double capital, double taxa, int tempo) {
        double montante = capital, juros = Math.pow(1 + porcetagem(taxa), tempo);
        return montante * juros;
    }

    /**
     *
     * @param capital Setar uma informação de valor double do Capital que deseja
     * investir;
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @param tempo Setar uma informação de valor Inteiro do tempo que desejar
     * investir;
     * @return
     */
    public static String montantedoJurosCompostoToString(double capital, double taxa, int tempo) {
        return formatarValorR$(montantedoJurosComposto(capital, taxa, tempo));
    }

    /**
     *
     * @param taxa Setar uma informação de valor double do valor da taxa para
     * investir;
     * @return
     */
    public static double porcetagem(double taxa) {
        return taxa / 100;
    }
    //--------------------------------------------------------------------------------------------//

    /**
     * Este Metodo Retornar uma informação de valor String da formação do valor
     * conforme o parametro
     *
     * @param valor Setar uma informação de valor double do valor do salario
     * minimo.
     * @return Retornar uma informação de valor String da formação do valor.
     */
    private static String formatarValor(double valor) {
        String fv = String.valueOf(valor);
        int n = fv.length(), i = fv.indexOf(".", 0) + 1;
        if (n - i != 0) {
            fv += "0";
        }
        return fv;
    }

    /**
     * Este Metodo Retornar uma informação de valor String da formação do valor
     * e com R$ conforme o parametro
     *
     * @param valor Setar uma informação de valor double do valor do salario
     * minimo.
     * @return Retornar uma informação de valor String da formação do valor e
     * com R$
     */
    private static String formatarValorR$(double valor) {
        String fv = "R$ " + formatarValor(valor);
        return fv;
    }
    //--------------------------------------------------------------------------------------------//

    /**
     * Este Metodo retornar uma informação de valor double do Capital
     *
     * @return retornar uma informação de valor double do Capital
     */
    public static double getCapital() {
        return capital;
    }

    /**
     * Este Metodo Setar uma informação de valor double do Capital
     *
     * @param capital Setar uma informação de valor double do Capital
     */
    public static void setCapital(double capital) {
        Juros.capital = capital;
    }

    /**
     * Este Metodo Retornar uma informação de valor double do Taxa
     *
     * @return Retornar uma informação de valor double do Taxa
     */
    public static double getTaxa() {
        return taxa;
    }

    /**
     * Este Metodo Setar uma informação de valor double do Taxa
     *
     * @param taxa Setar uma informação de valor double do Taxa
     */
    public static void setTaxa(double taxa) {
        Juros.taxa = taxa;
    }

    /**
     * Este Metodo Retornar uma informação de valor double do Montante
     *
     * @return Retornar uma informação de valor double do Montante
     */
    public static double getMontante() {
        return montante;
    }

    /**
     * Este Metodo Setar uma informação de valor double do Montante
     *
     * @param montante Setar uma informação de valor double do Montante
     */
    public static void setMontante(double montante) {
        Juros.montante = montante;
    }

    /**
     * Este Metodo Retornar uma informação de valor Int do Tempo
     *
     * @return Retornar uma informação de valor Int do Tempo
     */
    public static int getTempo() {
        return tempo;
    }

    /**
     * Este Metodo Setar uma informação de valor Int do Tempo
     *
     * @param tempo Setar uma informação de valor Int do Tempo
     */
    public static void setTempo(int tempo) {
        Juros.tempo = tempo;
    }

}
