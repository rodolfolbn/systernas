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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternas.util.Parser;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

/**
 * Classe responsável por realizar a leitura do arquivo de precipitação.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class PrecipitacaoReader {

	/**
	 * Representa o objeto de criação do arquivo.
	 */
	private InputManagerASCII reader;
	
	String line = "";
	
	boolean ehPrimeiraVez = true;
	
	/**
	 * Lista de precipitações.
	 * Para cada coordenada, existe uma lista de data com suas precipitações.
	 */
	private Map<Coordenadas, Map<String, Double>> mapPrecips;

	/**
	 * Lista com os períodos iniciais e finais de acordo com cada coordenada.
	 */
	private Map<Coordenadas, PeriodosIniciais> mapPeriodosIniciais;
	
	/**
	 * Lista com as precipitações diárias.
	 */
	Map<String, Double> precipitacoes;
	
	/**
	 * Construtor vazio.
	 */
	public PrecipitacaoReader() {
		
	}
	
	/**
	 * Construtor.
	 * @param file
	 * @throws FileNotFoundException
	 */
    public PrecipitacaoReader( String file ) throws FileNotFoundException{
		this.reader = new InputManagerASCII( file );
		this.reader.openFile();
		this.mapPrecips = new HashMap<Coordenadas, Map<String,Double>>();
		this.mapPeriodosIniciais = new HashMap<Coordenadas, PeriodosIniciais>();
		this.precipitacoes = new TreeMap<String, Double>();
    }
    
    /**
	 * @return the precipitacoes
	 */
	public Map<String, Double> getPrecipitacoes() {
		return precipitacoes;
	}

	/**
	 * @param precipitacoes the precipitacoes to set
	 */
	public void setPrecipitacoes(Map<String, Double> precipitacoes) {
		this.precipitacoes = precipitacoes;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * @return the mapPrecips
	 */
	public Map<Coordenadas, Map<String, Double>> getMapPrecips() {
		return mapPrecips;
	}

	/**
	 * @param mapPrecips the mapPrecips to set
	 */
	public void setMapPrecips(
			Map<Coordenadas, Map<String, Double>> mapPrecips) {
		this.mapPrecips = mapPrecips;
	}
	
	/**
	 * @return the mapPeriodosIniciais
	 */
	public Map<Coordenadas, PeriodosIniciais> getMapPeriodosIniciais() {
		return mapPeriodosIniciais;
	}

	/**
	 * @param mapPeriodosIniciais the mapPeriodosIniciais to set
	 */
	public void setMapPeriodosIniciais(
			Map<Coordenadas, PeriodosIniciais> mapPeriodosIniciais) {
		this.mapPeriodosIniciais = mapPeriodosIniciais;
	}

	/**
     * Método de leitura do arquivo.
     * @return
     * @throws IOException
     */
	public Map<Coordenadas, Map<String, Double>> read() throws IOException {
		
		try {
			
			// limpando as listas.
			this.mapPrecips.clear();
			this.mapPeriodosIniciais.clear();
			this.precipitacoes.clear();
			
			// se for a primeira vez que for executado pega a primeira linha do 
			// arquivo de precipitação.
			if (ehPrimeiraVez) {
				line = reader.readNextLine();
				this.ehPrimeiraVez = false;
			}
			
			while ((line.trim().equals("")) && (line.contains("!"))) {
				line = reader.readNextLine(); 
			}
			
			Double precipi = 0.0;
			Coordenadas pontoAtual;
			Coordenadas pontoAnterior = lerPonto(line);
			
			String dataInicial = lerData(line);
			String dataAnterior = "";
			
			// enquanto existir linha no PMH
			while (line != null) {
				
				pontoAtual = lerPonto(line);
				// se as coordenadas lidas da linha sao iguais
				if (pontoAnterior.equals(pontoAtual)) {
					dataAnterior = lerData(line);
					precipi = lerPrecipitacao(line);
					this.precipitacoes.put(dataAnterior, precipi);
					line = reader.readNextLine();
					
				} else {
					
					// caso entre nos dados de outra coordenada, grava na lista.
					PeriodosIniciais periodosIniciais = new PeriodosIniciais();
					periodosIniciais.setCoordenada(pontoAnterior);
					periodosIniciais.setDataInicial(dataInicial);
					periodosIniciais.setDataFinal(dataAnterior);
					
					dataInicial = lerData(line);
					
					// adiciona os períodos na lista de acordo com a coordenada.
					this.mapPeriodosIniciais.put(pontoAnterior, periodosIniciais);
					
					// adiciona as precipitações de acordo com a coordenada.
					this.mapPrecips.put(pontoAnterior, this.precipitacoes);
					
					break;
				}
//				line = reader.readNextLine();
			
			} //while
			
			/*
			 * Caso só exista uma coordenada, não entraria no else anterior,
			 * por isso que é preciso fazer essa verificação abaixo.
			 */
			if ( (this.mapPeriodosIniciais.isEmpty()) || ( !this.mapPeriodosIniciais.containsKey(pontoAnterior) )) {
				
				// caso entre nos dados de outra coordenada, grava na lista.
				PeriodosIniciais periodosIniciais = new PeriodosIniciais();
				periodosIniciais.setCoordenada(pontoAnterior);
				periodosIniciais.setDataInicial(dataInicial);
				periodosIniciais.setDataFinal(dataAnterior);
				
				// adiciona os períodos na lista de acordo com a coordenada.
				this.mapPeriodosIniciais.put(pontoAnterior, periodosIniciais);
			}
			
			/*
			 * Se no pmh só contiver uma coordenada terá que fazer essa condição
			 * abaixo, pois não iria preencher a lista de precipitações.
			 */
			if ( (this.mapPrecips.isEmpty()) || ( !this.mapPrecips.containsKey(pontoAnterior)) ) {
				this.mapPrecips.put(pontoAnterior, this.precipitacoes);
			}
			
			// caso chegue no fim do arquivo.
			if (line == null) {
				this.reader.closeFile();
			}
			
		} catch (IOException ioe) {
			System.err.println("Erro ao ler valores de precipitação");
			System.err.println(ioe.getMessage());
			throw ioe;
		} catch (NullPointerException npe) {
			System.err
					.println("Erro ao ler valores de precipitação: Período especificado invalido!");
			System.err.println(npe.getMessage());
			throw npe;
		} catch (NumberFormatException nfe) {
			System.err.println("Erro ao ler valor: formato de dado inválido");
			System.err.println(nfe.getMessage());
			throw nfe;
		}
		
		// retorna a lista de precipitações.
		return this.mapPrecips;
	}

	/**
	 * Método responsável por fazer a leitura da data no arquivo de PMH.
	 * @param line
	 * @return data
	 */
	public String lerData(String line) {
		return line.substring(48, 59);
	}

	/**
	 * Faz a leitura da precipitação no arquivo.
	 * @param line
	 * @return precipitação.
	 */
	public double lerPrecipitacao(String line) {
		return new Double(line.substring(75, 81).replace(',', '.').trim()).doubleValue();
	}

	/**
	 * Realiza a leitura da ponto de coordenada.
	 * @param line
	 * @return coordenada.
	 */
	public Coordenadas lerPonto(String line) {
		double x = new Double(line.substring(0, 21).replace(',', '.').trim()).doubleValue();
		double coordx = Parser.roundDouble(4,x);
		double y = new Double(line.substring(22, 40).replace(',', '.').trim()).doubleValue();
		double coordy = Parser.roundDouble(4,y);
		
		return new Coordenadas(coordx, coordy);
	}
	
	/**
	 * Realiza a leitura do uma coordenada.
	 * @return coordenada.
	 * @throws IOException
	 */
	private Coordenadas lerPrimeiroPonto() throws IOException{
		
		reader.openFile();
		String line = reader.readNextLine();
		
		while ((line.trim().equals("")) && (line.contains("!"))) {
			line = reader.readNextLine(); 
		}
		
		return lerPonto(line);
	}
	
}
