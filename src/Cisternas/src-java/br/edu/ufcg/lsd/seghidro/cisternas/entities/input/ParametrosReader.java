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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.ParametrosReaderException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

/**
 * Classe responsável por realizar a leitura do arquivo de parâmetros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 07/01/2009
 */
public class ParametrosReader {
	
	private String file;
	private InputManager reader;
	private ArrayList<Parametros> listaParametros;

	public ParametrosReader(String file) throws FileNotFoundException {
		this.file = file;
		this.reader = new InputManagerASCII(file);
		this.listaParametros = new ArrayList<Parametros>();

	}

	/**
	 * Recupera dados de um determinado Historico.
	 * 
	 * @return Historico
	 * @throws Exception 
	 */
	public Map<Coordenadas, Parametros> getParametros() throws Exception {
		
		return this.lerParametros();
	}
	
	/**
	 * Realiza a leitura do arquivo de parâmetros.
	 * @return lista de parâmetros.
	 * @throws Exception 
	 */
	public Map<Coordenadas, Parametros> lerParametros() throws Exception {
		
		String line = "";
		
		reader.openFile();
		line = reader.readNextLine();
		
		// enquanto a linha corrente contiver uma exclamação, avança.
		while (line.contains("!")) {
			line = reader.readNextLine();
		}

//		ArrayList<Parametros> lista = new ArrayList<Parametros>();
		Map<Coordenadas, Parametros> lista = new HashMap<Coordenadas, Parametros>();
		
		while (line != null) {

			StringTokenizer tk = new StringTokenizer(line);
		
			// enquanto tiver valores na linha corrente
			while (tk.hasMoreTokens()) {
				
				/*
				 * Formata um número decimal com casas separadoras de milhar para a
				 * notação padrão de ponto flutuante, ou seja, 1.000.532,44 será
				 * convertido para 1000532.44.
				 */
				try {
					Double coordenadaX = Double.parseDouble(tk.nextToken(";"));
					Double coordenadaY = Double.parseDouble(tk.nextToken(";"));
					String descricao = tk.nextToken(";");
					String area = tk.nextToken(";");
					String capacidade = tk.nextToken(";");
					
					String demandaAux = tk.nextToken(";");
					StringTokenizer tk2 = new StringTokenizer(demandaAux, "-");
					ArrayList<String> demanda = new ArrayList <String>();
		
					if (tk2.countTokens()!= 12 && tk2.countTokens()!= 1){
						throw new Exception("Lista de demandas deve possuir 12 valores, ou apenas 1.");
					}
					
					while(tk2.hasMoreTokens()){
						//TERRIVEL, MAS TO COM PREGUICA. RETIRAR "[" DO PRIMEITO TOKEN E "]" DO ULTIMO
						demanda.add((tk2.nextToken().replace("[", "")).replace("]", ""));
					}
					
					//System.out.println("TOKEN: "+demanda.get(0));
					//System.out.println("Demanda: "+demanda.get(1));
					
					
					String perdas = tk.nextToken();

					Locale locBrazil = new Locale("pt", "BR");
					NumberFormat nf = NumberFormat.getInstance(locBrazil);
					
					Number numberFormatted = nf.parse(area);
					Double areaCapitacao = new Double(numberFormatted.doubleValue());
					numberFormatted = nf.parse(capacidade);
					Double capacidadeValue = new Double(numberFormatted.doubleValue());
					//numberFormatted = nf.parse(demanda);
					//Double demandaValue = new Double(numberFormatted.doubleValue());
					numberFormatted = nf.parse(perdas);
					Double perdasValue = new Double(numberFormatted.doubleValue());
					
					Parametros parametros = new Parametros(areaCapitacao,
							capacidadeValue, descricao, perdasValue,
							demanda);
					
					// adiciona os valores na lista de parametros
					lista.put(new Coordenadas(coordenadaX, coordenadaY), parametros);
					
					
				} catch (ParseException e) {
					throw new ParametrosReaderException(
							"Erro ao ler valor númerico no arquivo de Parâmetros: "
									+ file);
				}

			}
			line = reader.readNextLine();
		}

		return lista;
		
	}
	
}
