package br.edu.ufcg.lsd.seghidro.cisternas.exceptions;

/**
 * Classe responsável por lançar a exceção caso o arquivo não seja encontrado.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 14/01/2009.
 */
public class CisternasMissingFileException extends Exception {
	
	public CisternasMissingFileException(String message){
		super(message);
	}

}
