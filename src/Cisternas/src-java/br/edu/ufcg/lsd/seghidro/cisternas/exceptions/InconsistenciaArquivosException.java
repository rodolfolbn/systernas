package br.edu.ufcg.lsd.seghidro.cisternas.exceptions;

/**
 * Classe responsável por lançar a exceção de inconsistência de arquivos.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 22/01/2009.
 * 
 */
public class InconsistenciaArquivosException extends Exception {

	public InconsistenciaArquivosException(String mensagem) {
		super(mensagem);
	}
}
