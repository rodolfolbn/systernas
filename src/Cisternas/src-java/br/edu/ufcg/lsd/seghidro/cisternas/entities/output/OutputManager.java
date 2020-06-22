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

package br.edu.ufcg.lsd.seghidro.cisternas.entities.output;

import java.io.IOException;

/**
 * Define métodos necessários para genrenciar tarefas de gravação em arquivos.
 * 
 * @author José Flávio Mendes Vieria Júnior
 *
 */
public interface OutputManager {
	
	/**
     * Define o caminho do arquivo a ser gravado.
     * 
     * @param saida Caminho totalmente qualificado do arquivo a ser gravado.
     */
	public void setFilePath(String saida);

	/**
     * Cria o arquivo que vai ser gravado.
     * 
	 * @return True se a operação for realizada com sucesso.
     */
	public void createFile() throws IOException;

	/**
	 * Grava uma linha na posição corrente.
	 * 
	 * @param line String com o conteúdo a ser gravado na linha corrente.
	 */
	public void writeLine(String line) throws IOException;
	
	/**
	 * Fecha o arquivo.
	 * 
	 * @return True se a operação for realizada com sucesso.
	 */
	public void closeFile() throws IOException;

}
