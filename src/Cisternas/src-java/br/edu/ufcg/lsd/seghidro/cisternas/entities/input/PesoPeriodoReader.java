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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;

/**
 * Classe responsável por realizar a leitura do arquivo de peso período.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 14/01/2009.
 */
public class PesoPeriodoReader {

	private static final String CHUVOSO = "Chuvoso";

	private static final String NORMAL = "Normal";

	private static final String SECO = "Seco";

	private InputManager reader;

	private static final String DATA_INICIAL = "MesInicial";

	private static final String DATA_FINAL = "MesFinal";
	
	private  String file;

	/**
	 * Construtor.
	 * 
	 * @param file
	 * @throws JHidroMissingFileException 
	 * @throws FileNotFoundException
	 */
	public PesoPeriodoReader(String file) throws CisternasMissingFileException {
		this.file = file;
		try {
			this.reader = new InputManagerASCII(file);
		} catch (FileNotFoundException e) {
			throw new CisternasMissingFileException("O arquivo de pesos " + file
					+ " não pode ser encontrado.");
		}
	}

	/**
	 * Construtor vazio.
	 */
	public PesoPeriodoReader() {

	}

	/**
	 * Recupera dados do arquivo de pesoperiodo.
	 * 
	 * @return PesoPeriodo.
	 * @throws JHidroReaderException 
	 * @throws IOException
	 */
	public PesoPeriodo getPesoPeriodo() throws CisternasReaderException{
		return this.readPesoPeriodo();
	}

	/**
	 * Método que realiza a leitura do arquivo.
	 * 
	 * @return PesoPeriodo
	 * @throws JHidroReaderException 
	 * @throws IOException
	 */
	public PesoPeriodo readPesoPeriodo() throws CisternasReaderException {

		try {
			
			Double periodoSeco = readPeriodoSeco();
			Double periodoNormal = readPeriodoNormal();
			Double periodoChuvoso = readPeriodoChuvoso();
			String mesInicialChuvoso = readMesInicialChuvoso();
			String mesFinalChuvoso = readMesFinalChuvoso();
			
			return new PesoPeriodo(mesInicialChuvoso, mesFinalChuvoso, periodoSeco,
					periodoNormal, periodoChuvoso);
			
		} catch (IOException e) {
			throw new CisternasReaderException("Erro ao ler dados do arquivo: " + file);
		}
		
		
		
	}

	/**
	 * Método que recupera o peso Chuvoso do arquivo.
	 * 
	 * @return valor do peso chuvoso.
	 * @throws IOException
	 * @throws JHidroReaderException 
	 */
	public Double readPeriodoChuvoso() throws IOException, CisternasReaderException {
		return this.pegaValor(CHUVOSO);
	}

	/**
	 * Método que recupera o peso Normal do arquivo.
	 * 
	 * @return valor do peso normal.
	 * @throws IOException
	 * @throws JHidroReaderException 
	 */
	public Double readPeriodoNormal() throws IOException, CisternasReaderException {
		return this.pegaValor(NORMAL);
	}

	/**
	 * Método que recupera o peso Seco do arquivo.
	 * 
	 * @return valor do peso seco.
	 * @throws IOException
	 * @throws JHidroReaderException 
	 */
	public Double readPeriodoSeco() throws IOException, CisternasReaderException {
		return this.pegaValor(SECO);
	}

	/**
	 * Método que recupera a data inicial do arquivo de pesoPeriodo.
	 * 
	 * @return data inicial.
	 * @throws IOException
	 */
	public String readMesInicialChuvoso() throws IOException {

		String valor = this.lerString(DATA_INICIAL);

		return valor;
	}

	/**
	 * Método que recupera a data final do arquivo de pesoPeriodo.
	 * 
	 * @return data final
	 * @throws IOException
	 */
	public String readMesFinalChuvoso() throws IOException {

		String line = this.lerString(DATA_FINAL);

		return line;
	}

	/**
	 * Método que recupara uma string no arquivo de entrada
	 * 
	 * @param linha
	 * @return string de resultado
	 */
	private String pegaString(String linha) {
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
	private Double pegaValor(String value) throws IOException, CisternasReaderException {
		
		String line = "";
		Double valor = 0.0;
		
		try {
			reader.openFile();
			line = reader.readNextLine();
			// procura no arquivo a variável especificada
			while ((line != null)) {
				if (!(line.toLowerCase().indexOf(value.toLowerCase()) == -1)) {
					line = line.replace('=', ' ');
					String valorSemFormatacao = pegaString(line);
					/*
					 * Formata um número decimal com casas separadoras de milhar para a
					 * notação padrão de ponto flutuante, ou seja, 1.000.532,44 será
					 * convertido para 1000532.44.
					 */
					try {
						Locale locBrazil = new Locale("pt", "BR");
						NumberFormat nf = NumberFormat.getInstance(locBrazil);
						Number numberFormatted = nf.parse(valorSemFormatacao);
						valor = Double.parseDouble(numberFormatted.toString());
					} catch (ParseException e) {
						throw new CisternasReaderException(
								"Erro ao ler valor númerico no arquivo de Peso Período: "
										+ file);
					}
				}
				line = reader.readNextLine();
			}
			// fecha o arquivo.
			reader.closeFile();
		} catch (IOException e) {
			System.err.println("Erro ao ler a variável " + value);
			throw e;
		}
		// retorna o valor recuperado.
		return valor;
	}

	/**
	 * Método que realiza a leitura de uma linha passada, para ter um retorno
	 * de uma string.
	 * @param value
	 * @return
	 * @throws IOException
	 */
	private String lerString(String value) throws IOException {
		
		String line = "";
		String valor = "";
		
		try {
			
			reader.openFile();
			line = reader.readNextLine();
			
			// procura no arquivo a variável especificada
			while ((line != null)) {
				if (!(line.toLowerCase().indexOf(value.toLowerCase()) == -1)) {
					line = line.replace('=', ' ');
					valor = pegaString(line);
				}
				line = reader.readNextLine();
			}
			
		} catch (IOException e) {
			System.err.println("Erro ao ler a variável " + value);
			throw e;
		}
		
		// fecha o arquivo.
		reader.closeFile();
		
		// retorna o valor recuperado.
		return valor;
	}

}
