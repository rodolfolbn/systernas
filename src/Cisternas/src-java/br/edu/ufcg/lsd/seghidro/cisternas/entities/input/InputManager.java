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

package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Define métodos necessários para genrenciar tarefas de leitura em arquivos
 * 
 * @author José Flávio Mendes Vieria Júnior
 *
 */
public interface InputManager {
    
	/**
     * Define o caminho do arquivo a ser lido
     * 
     * @param fullyPath Caminho totalmente qualificado do arquivo
     */
	public void setFilePath(String fullyPath);
	
	/**
	 * Abre o arquivo
	 * 
	 * @return True se a operação for realizada com sucesso
	 */
	public void openFile() throws FileNotFoundException;
	
	/**
	 * Lê a linha corrente
	 * 
	 * @return String com o conteúdo referente a linha corrente
	 */
	public String readLine() throws IOException;
	
	/**
	 * Lê a linha especificada
	 * 
	 * @param lineNumber Número da linha a ser lida
	 * @return String com o conteúdo referente a linha especificada
	 */
	public String readLine(int lineNumber) throws FileNotFoundException, IOException;
	
    /**
     * Lê a próxima linha válida, desprezando linhas em branco e com comentários
     * 
     * @return Próxima linha válida
     * @throws IOException
     */
    public String readNextLine() throws IOException;
	
	/**
	 * Lê o conteúdo, da linha especificada, entre os pontos inicial e final especificados   
	 * 
	 * @param lineNumber Número da linha a ser lida
	 * @param startColumn Inicio da coluna a ser lida
	 * @param endColumn Final da coluna a ser lida
	 * @return String com o valor encontrado na linha e entre os pontos especificados 
	 */
	public String readColumn(int lineNumber, int startColumn, int endColumn) throws IOException;
	
	/**
	 * Fecha o arquivo
	 * 
	 * @return True se a operação for realizada com sucesso
	 */
	public void closeFile() throws IOException;
	
}

