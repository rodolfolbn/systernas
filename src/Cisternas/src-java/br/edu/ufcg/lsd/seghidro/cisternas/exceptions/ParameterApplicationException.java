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
 * Excessão lançada caso o número de parâmetros sejam insuficientes
 * para a execução da tarefa
 * 
 * @author José Flávio Mendes Vieira Júnior
 */
public class ParameterApplicationException extends Exception {

	/**
	 * Variável de serialização.
	 */
	private static final long serialVersionUID = 8402128061502192444L;

	/**
	 * Construtor.
	 */
	public ParameterApplicationException() {
		super();
		System.err.println(this);
	}
	
	/**
	 * Método que recupera a mensagem de informação.
	 */
	public String getMessage() {
		String message = "Cisternas: Devem ser especificados os parâmetros mínimos necessários"+
						"\nTente `cisternas --help' para mais informações";

		return message;
	}

	public String toString() {
		return this.getMessage();
	}
}

