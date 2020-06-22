package br.edu.ufcg.lsd.seghidro.cisternas.exceptions;

/**
 * Classe responsável por lançar a exceção caso o arquivo de parâmetros esteja com
 * erros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 12/01/2009.
 *
 */
public class ParametrosReaderException extends Exception {
	
	public ParametrosReaderException(String message){
		super(message);
	}

}
