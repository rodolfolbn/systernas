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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Esta classe tem como função genrenciar tarefas de criação dos arquivos de
 * saída em padrão ASCII.
 * 
 * @author José Flávio Mendes Vieira Júnior
 *
 */
public class OutputManagerASCII implements OutputManager {

	private String filePath;
	private BufferedWriter output;
	
	/**
	 * @param output Nome do arquivo a ser gravado
	 */
	public OutputManagerASCII(String saida) {
		this.setFilePath(saida);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.io.InputManager#setFilePath(java.lang.String)
	 */
	public void setFilePath(String path) {
		this.filePath = path;
	}

	/* (non-Javadoc)
	 * @see interfaces.io.OutputManager#createFile(java.lang.String)
	 */
	public void createFile() throws IOException{
		boolean result = true;
		try {
			output = new BufferedWriter( new FileWriter(this.filePath) );
		} catch (IOException ioe) {
			System.out.println("OutputManagerASCII.createFile()");
			System.err.println("Erro ao criar o arquivo "+ filePath);
			System.err.println(ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}		 
	}

	/* (non-Javadoc)
	 * @see interfaces.io.OutputManager#closeFile()
	 */
	public void closeFile() throws IOException{
		try {
			output.flush();
			output.close();	
		} catch (IOException ioe) {
			System.out.println("OutputManagerASCII.closeFile()");
			System.err.println("Erro ao fechar o arquivo "+ filePath);
			System.err.println(ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}		 
	}

	/* (non-Javadoc)
	 * @see interfaces.io.OutputManager#writeLine(java.lang.String)
	 */
	public void writeLine(String line) throws IOException{
		try {
			output.write(line);
			output.newLine();			
		} catch (IOException ioe) {
			System.out.println("OutputManagerASCII.writeLine()");
			System.err.println("Erro ao gravar linha no arquivo "+ filePath);
			System.err.println(ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}
	}

}

