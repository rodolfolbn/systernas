/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package br.edu.ufcg.lsd.seghidro.cisternas.exceptions;

/**
 * Excessão lançada quando o parâmetro --help é passado para a aplicação
 * exibindo informações de como o programa deve ser executado
 * 
 * @author José Flávio Mendes Vieira Júnior
 */
public class HelpException extends Exception {

	/**
	 * Variável de serialização.
	 */
	private static final long serialVersionUID = 1L;

	/** Variável de exibição das informações necessárias para execução do jhidro. */
	private final String message = "Cisternas 2.0" + "\nUso: cisternas PARÂMETROS"
			+ "\n" + "\nPARÂMETROS" + "\n"
			+ "\n 1 - Percentagem do volume inicial"
			+ "\n 2 - Argumento para o tipo de execução da aplicação"
			+ "\n 3 - Nome do arquivo de precipitação (PMH)"
			+ "\n 4 - Nome do arquivo de parâmetros"
			+ "\n 5 - Nome do arquivo de peso período"
			+ "\n 6 - Nome do arquivo de saída"
			+ "\n 7 - Nome do arquivo de cenários (opcional)";
	
	/**
	 * Construtor.
	 */
	public HelpException() {
		super();
		System.err.println(this);
	}

	/**
	 * Recupera a mensagem de informações.
	 */
	public String getMessage() {
		return this.message;
	}

	public String toString() {
		return this.getMessage();
	}	

}

