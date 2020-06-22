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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;

/**
 * Classe responsável por fazer a leitura do arquivo de cenários.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 20/01/2009.
 * 
 */
public class CenariosReader {

	// Variáveis a serem recuperadas no arquivo de entrada.
	private static final String DEMANDA = "Demanda";

	private static final String VOLUME = "Volume";

	private static final String AREA_CAPTACAO = "AreaCaptacao";

	private String file;

	private InputManager reader;

	public CenariosReader(String file) throws CisternasMissingFileException {
		this.file = file;
		try {
			this.reader = new InputManagerASCII(file);
		} catch (FileNotFoundException e) {
			throw new CisternasMissingFileException("O arquivo de cenarios "
					+ file + " não pôde ser encontrado");
		}
	}

	public CenariosReader() {

	}

	// GETs e SETs
	public InputManager getReader() {
		return reader;
	}

	public void setReader(InputManager reader) {
		this.reader = reader;
	}

	/**
	 * Recupera dados de um determinado cenário.
	 * 
	 * @return Cenário.
	 * @throws IOException 
	 * @throws IOException
	 * @throws CisternasMissingFileException 
	 */
	public Cenarios getCenario() throws CisternasReaderException, IOException,
			CisternasMissingFileException {
		
		return this.readCenario();
	}

	/**
	 * Método que recupara uma string no arquivo de entrada
	 * 
	 * @param linha
	 * @return string de resultado
	 */
	public String pegaString(String linha) {
		linha = linha.trim();
		String valor = "";
		StringTokenizer tokens = new StringTokenizer(linha);
		tokens.nextToken();
		while (tokens.hasMoreTokens()) {
			valor = tokens.nextToken();
		}
		return valor;
	}
	
	/**
	 * Método que recupera um valor Double do arquivo de entrada.
	 * 
	 * @param value
	 * @return BigDecimal
	 * @throws IOException
	 * @throws JHidroReaderException 
	 */
	private ArrayList<Double> pegarValores(String value) throws IOException, CisternasReaderException {
		
		String line = value;
		Double valor = 0.0;
		ArrayList<Double> lista = new ArrayList<Double>();
		
		if (!(line.toLowerCase().indexOf(value.toLowerCase()) == -1)) {
			
			line = line.replace('=', ' ');
			
			// recupera somente os valores correspondente a linha
			String valorSemFormatacao = pegaString(line);
			
			// cria uma array de strings com os valores separados para a conversão.
			String array[] = valorSemFormatacao.split(";");

			for (String string : array) {
				
				try {
					Locale locBrazil = new Locale("pt", "BR");
					NumberFormat nf = NumberFormat.getInstance(locBrazil);
					Number numberFormatted = nf.parse(string);
					valor = Double.parseDouble(numberFormatted.toString());
					lista.add(valor);
				} catch (ParseException e) {
					throw new CisternasReaderException(
							"Erro ao ler valor númerico no arquivo de Peso Período: "
							+ file);
				}
			}
			
				
			
		} //if
			
		// retorna o valor recuperado.
		return lista;
	}
	
	/**
	 * Método que realiza a leitura do arquivo de cenários.
	 * @return
	 * @throws CisternasReaderException
	 * @throws CisternasMissingFileException
	 */
	public Cenarios readCenario() throws CisternasReaderException,
			CisternasMissingFileException {

		String line = "";

		// abre o arquivo para leitura
		try {
			reader.openFile();
		} catch (FileNotFoundException e) {
			throw new CisternasMissingFileException("O arquivo de Cenários: "
					+ file + ", não pode ser encontrado.");
		}
		
		Cenarios cenarios = null;
		try {

			// realiza a leitura das informações de demanda
			line = reader.readNextLine();
			ArrayList<Double> demanda = pegarValores(line);
			
			// realiza a leitura das informações de volume
			line = reader.readNextLine();
			ArrayList<Double> volume = pegarValores(line);
			
			// realiza a leitura do valor de área de captação
			line = reader.readNextLine();
			ArrayList<Double> areaCaptacao = pegarValores(line);
			
			reader.closeFile();
			
			cenarios = new Cenarios(demanda, volume, areaCaptacao);

		} catch (IOException e) {
			throw new CisternasReaderException(
					"Erro ao ler dados de cenários do arquivo: " + file);
		}

		return cenarios;

	}
	
}
