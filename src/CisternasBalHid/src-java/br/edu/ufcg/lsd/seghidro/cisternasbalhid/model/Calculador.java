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

package br.edu.ufcg.lsd.seghidro.cisternasbalhid.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Deficit;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Reamostragem;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.InconsistenciaArquivosException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.ReamostragemException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.util.Parser;

/**
 * Classe responsável por realizar o balanço hídrico.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Calculador {
	
	/**
	 * Variável responsável por finalizar uma linha no arquivo de saída.
	 */
	private static final String FIM_DE_LINHA = System
			.getProperty("line.separator");
	
	private static final String AMOSTRA_90_100 = "90_100";

	private static final String AMOSTRA_80_90 = "80_90";

	private static final String AMOSTRA_70_80 = "70_80";

	private static final String AMOSTRA_60_70 = "60_70";

	private static final String AMOSTRA_50_60 = "50_60";

	private static final String AMOSTRA_40_50 = "40_50";

	private static final String AMOSTRA_30_40 = "30_40";

	private static final String AMOSTRA_20_30 = "20_30";

	private static final String AMOSTRA_10_20 = "10_20";

	private static final String AMOSTRA_0_10 = "0_10";

	/**
	 * Constante do período chuvoso.
	 */
	private static final String CHUVOSO = "Chuvoso";

	/**
	 * Constante do período normal.
	 */
	private static final String NORMAL = "Normal";

	/**
	 * Constante do período seco.
	 */
	private static final String SECO = "Seco";

	/**
	 * Variável que representa a porcentagem do volume inicial.
	 */
	private Double volumeInicial;
	
	/**
	 * Variável que representa um objeto de peso período.
	 */
	private PesoPeriodo pesoPeriodo;
	
	/**
	 * Lista de precipitações de acordo com a data, para cada coordenada.
	 */
	private Map<Coordenadas, Map<String, Double>> precipitacao;
	
	/**
	 * Lista de parâmetros para cada coordenada.
	 */
	private Map<Coordenadas, Parametros> colecaoParametros;
	
	/**
	 * Lista com os períodos iniciais para cada coordenada.
	 */
	private Map<Coordenadas, PeriodosIniciais> periodos;

	/**
	 * Variável de resultados do balanço hídrico.
	 */
	private Map<Coordenadas, ResultadosBalHid> mapResultadosBalHid;
	
	/**
	 * Lista de deficits para uma determinada data.
	 */
	private Map<String, Integer> deficit;
	
	/**
	 * Somatório de precipitações de cada ano. Essa variável é passada como valor
	 * para a variável "somatorioPrecipCoordenada".
	 */
	private Map<String, Double> somatorioPrecipitacao;
	
	/**
	 * Variável responsável por representar o somatorio da precipitação anual
	 * de acordo com cada coordenada.
	 */
	private Map<Coordenadas, Map<String, Double>> somatorioPrecipCoordenada;

	/**
	 * Variável responsável por representar a lista de deficits anuais para
	 * cada coordenada.
	 */
	private Map<Coordenadas, Map<String, Integer>> deficitCoordenada;
	
	/**
	 * Lista de anos classificados em seco, normal e chuvoso.
	 */
	private Map<Coordenadas, Map<String, ArrayList<String>>> listaAnosClassificados;

	/**
	 * Lista com as reamostragens da previsão de consenso.
	 */
	private Map<Coordenadas, ArrayList<Reamostragem>> mapReamostragem;

	/**
	 * Lista de frequência estimada por coordenada.
	 */
	private Map<Coordenadas, Map<String,ArrayList<Reamostragem>>> mapFrequenciaEstimada;

	/**
	 * Lista de quantidade observada na reamostragem, de acordo com a porcentagem
	 * de deficit hídrico.
	 */
	private Map<Coordenadas, Map<String, Integer>> mapQuantidadeObservada;

	/**
	 * Lista de quantidade total observada para cada coordenada.
	 */
	private Map<Coordenadas, Integer> mapQuantidadeTotalObservado;

	/**
	 * Lista com os resultados finais da frequência estimada.
	 */
	private Map<Coordenadas, Map<String, Double>> mapResultadoFrequencia;
	
	/**
	 * Variável que representa a entidade de cenários.
	 */
	private Cenarios cenarios;

	/**
	 * Lista de resultados caso a aplicação seja executado com cenários.
	 */
	private Map<ResultadosCenarios, ResultadoFinalCompleto> mapResultadoCenarios;
	
	/**
	 * Argumento para execução da aplicação.
	 */
	private String argumento;

	/**
	 * Lista de resultados caso a aplicação seja executado com variação de cenários, sem o consenso.
	 */
	private Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> mapResultadoVariacao;

	/**
	 * Lista de resultados do balanço hídrico caso seja executado por consenso.
	 */
	private Map<Coordenadas, Map<String, ResultadosBalHid>> mapResultadosBalHidConsenso;

	private Double demandaCorrente = 0.0;
	
	/**
	 * Lista de resultados da ponderação dos dias consecutivos obtidos pela 
	 * execução por consenso.
	 */
	private Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso;

	private String detalhes;
	
	/**
	 * Construtor
	 * @param colecaoParametros
	 * @param precipitacao
	 */
	public Calculador(Double volumeInicial, String argumento, 
			Map<Coordenadas, Map<String, Double>> mapPrecipitacoes, 
			Map<Coordenadas, Parametros> colecaoParametros,
			Map<Coordenadas, PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios, String detalhes) {
		
		super();
		this.volumeInicial = volumeInicial;
		this.colecaoParametros = colecaoParametros;
		this.precipitacao = mapPrecipitacoes;
		this.periodos = periodos;
		this.pesoPeriodo = pesoPeriodo;
		this.cenarios = cenarios;
		this.argumento = argumento;
		this.detalhes = detalhes;
		
		this.mapResultadosBalHid = new HashMap<Coordenadas, ResultadosBalHid>();
		this.somatorioPrecipitacao = new HashMap<String, Double>();
		this.somatorioPrecipCoordenada = new HashMap<Coordenadas, Map<String,Double>>();
		this.deficit = new HashMap<String, Integer>();
		this.deficitCoordenada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.listaAnosClassificados = new HashMap<Coordenadas, Map<String,ArrayList<String>>>();
		this.mapReamostragem = new HashMap<Coordenadas, ArrayList<Reamostragem>>();
		this.mapFrequenciaEstimada = new HashMap<Coordenadas, Map<String,ArrayList<Reamostragem>>>();
		this.mapQuantidadeObservada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.mapQuantidadeTotalObservado = new HashMap<Coordenadas, Integer>();
		this.mapResultadoFrequencia = new HashMap<Coordenadas, Map<String,Double>>();
		this.mapResultadoCenarios = new HashMap<ResultadosCenarios, ResultadoFinalCompleto>();
		this.mapResultadoVariacao = new HashMap<ResultadosCenarios, Map<Coordenadas,ResultadosBalHid>>();
		this.mapResultadosBalHidConsenso = new HashMap<Coordenadas, Map<String,ResultadosBalHid>>();
		this.mapPonderacaoDiasConsecutivosConsenso = new HashMap<Coordenadas, Double>();
		
	}

	/**
	 * Construtor vazio.
	 */
	public Calculador() {
		
		this.volumeInicial = 0.0;
		this.pesoPeriodo = null;
		this.cenarios = null;
		this.argumento = "";
		this.precipitacao = new TreeMap<Coordenadas, Map<String, Double>>();
		this.colecaoParametros = new HashMap<Coordenadas, Parametros>();
		
		this.mapResultadosBalHid = new HashMap<Coordenadas, ResultadosBalHid>();
		this.somatorioPrecipitacao = new HashMap<String, Double>();
		this.somatorioPrecipCoordenada = new HashMap<Coordenadas, Map<String,Double>>();
		this.deficit = new HashMap<String, Integer>();
		this.deficitCoordenada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.listaAnosClassificados = new HashMap<Coordenadas, Map<String,ArrayList<String>>>();
		this.mapReamostragem = new HashMap<Coordenadas, ArrayList<Reamostragem>>();
		this.mapFrequenciaEstimada = new HashMap<Coordenadas, Map<String,ArrayList<Reamostragem>>>();
		this.mapQuantidadeObservada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.mapQuantidadeTotalObservado = new HashMap<Coordenadas, Integer>();
		this.mapResultadoFrequencia = new HashMap<Coordenadas, Map<String,Double>>();
		this.mapResultadoCenarios = new HashMap<ResultadosCenarios, ResultadoFinalCompleto>();
		this.mapResultadoVariacao = new HashMap<ResultadosCenarios, Map<Coordenadas,ResultadosBalHid>>();
		this.mapResultadosBalHidConsenso = new HashMap<Coordenadas, Map<String,ResultadosBalHid>>();
		this.mapPonderacaoDiasConsecutivosConsenso = new HashMap<Coordenadas, Double>();
		
	}
	
	/**
	 * @return the mapPonderacaoDiasConsecutivosConsenso
	 */
	public Map<Coordenadas, Double> getMapPonderacaoDiasConsecutivosConsenso() {
		return mapPonderacaoDiasConsecutivosConsenso;
	}

	/**
	 * @param mapPonderacaoDiasConsecutivosConsenso the mapPonderacaoDiasConsecutivosConsenso to set
	 */
	public void setMapPonderacaoDiasConsecutivosConsenso(
			Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso) {
		this.mapPonderacaoDiasConsecutivosConsenso = mapPonderacaoDiasConsecutivosConsenso;
	}

	/**
	 * @return the mapResultadosBalHidConsenso
	 */
	public Map<Coordenadas, Map<String, ResultadosBalHid>> getMapResultadosBalHidConsenso() {
		return mapResultadosBalHidConsenso;
	}

	/**
	 * @param mapResultadosBalHidConsenso the mapResultadosBalHidConsenso to set
	 */
	public void setMapResultadosBalHidConsenso(
			Map<Coordenadas, Map<String, ResultadosBalHid>> mapResultadosBalHidConsenso) {
		this.mapResultadosBalHidConsenso = mapResultadosBalHidConsenso;
	}

	/**
	 * @return the mapResultadoVariacao
	 */
	public Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> getMapResultadoVariacao() {
		return mapResultadoVariacao;
	}

	/**
	 * @param mapResultadoVariacao the mapResultadoVariacao to set
	 */
	public void setMapResultadoVariacao(
			Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> mapResultadoVariacao) {
		this.mapResultadoVariacao = mapResultadoVariacao;
	}

	/**
	 * @return the argumento
	 */
	public String getArgumento() {
		return argumento;
	}

	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(String argumento) {
		this.argumento = argumento;
	}

	/**
	 * @return the mapResultadoCenarios
	 */
	public Map<ResultadosCenarios, ResultadoFinalCompleto> getMapResultadoCenarios() {
		return mapResultadoCenarios;
	}

	/**
	 * @param mapResultadoCenarios the mapResultadoCenarios to set
	 */
	public void setMapResultadoCenarios(
			Map<ResultadosCenarios, ResultadoFinalCompleto> mapResultadoCenarios) {
		this.mapResultadoCenarios = mapResultadoCenarios;
	}

	/**
	 * @return the cenarios
	 */
	public Cenarios getCenarios() {
		return cenarios;
	}

	/**
	 * @param cenarios the cenarios to set
	 */
	public void setCenarios(Cenarios cenarios) {
		this.cenarios = cenarios;
	}

	/**
	 * @return the mapReamostragem
	 */
	public Map<Coordenadas, ArrayList<Reamostragem>> getMapReamostragem() {
		return mapReamostragem;
	}

	/**
	 * @param mapReamostragem the mapReamostragem to set
	 */
	public void setMapReamostragem(
			Map<Coordenadas, ArrayList<Reamostragem>> mapReamostragem) {
		this.mapReamostragem = mapReamostragem;
	}

	/**
	 * @return the mapFrequenciaEstimada
	 */
	public Map<Coordenadas, Map<String, ArrayList<Reamostragem>>> getMapFrequenciaEstimada() {
		return mapFrequenciaEstimada;
	}

	/**
	 * @param mapFrequenciaEstimada the mapFrequenciaEstimada to set
	 */
	public void setMapFrequenciaEstimada(
			Map<Coordenadas, Map<String, ArrayList<Reamostragem>>> mapFrequenciaEstimada) {
		this.mapFrequenciaEstimada = mapFrequenciaEstimada;
	}

	/**
	 * @return the mapQuantidadeObservada
	 */
	public Map<Coordenadas, Map<String, Integer>> getMapQuantidadeObservada() {
		return mapQuantidadeObservada;
	}

	/**
	 * @param mapQuantidadeObservada the mapQuantidadeObservada to set
	 */
	public void setMapQuantidadeObservada(
			Map<Coordenadas, Map<String, Integer>> mapQuantidadeObservada) {
		this.mapQuantidadeObservada = mapQuantidadeObservada;
	}

	/**
	 * @return the mapQuantidadeTotalObservado
	 */
	public Map<Coordenadas, Integer> getMapQuantidadeTotalObservado() {
		return mapQuantidadeTotalObservado;
	}

	/**
	 * @param mapQuantidadeTotalObservado the mapQuantidadeTotalObservado to set
	 */
	public void setMapQuantidadeTotalObservado(
			Map<Coordenadas, Integer> mapQuantidadeTotalObservado) {
		this.mapQuantidadeTotalObservado = mapQuantidadeTotalObservado;
	}

	/**
	 * @return the mapResultadoFrequencia
	 */
	public Map<Coordenadas, Map<String, Double>> getMapResultadoFrequencia() {
		return mapResultadoFrequencia;
	}

	/**
	 * @param mapResultadoFrequencia the mapResultadoFrequencia to set
	 */
	public void setMapResultadoFrequencia(
			Map<Coordenadas, Map<String, Double>> mapResultadoFrequencia) {
		this.mapResultadoFrequencia = mapResultadoFrequencia;
	}

	/**
	 * @return the pesoPeriodo
	 */
	public PesoPeriodo getPesoPeriodo() {
		return pesoPeriodo;
	}

	/**
	 * @param pesoPeriodo the pesoPeriodo to set
	 */
	public void setPesoPeriodo(PesoPeriodo pesoPeriodo) {
		this.pesoPeriodo = pesoPeriodo;
	}

	/**
	 * @return the listaAnosClassificados
	 */
	public Map<Coordenadas, Map<String, ArrayList<String>>> getListaAnosClassificados() {
		return listaAnosClassificados;
	}

	/**
	 * @param listaAnosClassificados the listaAnosClassificados to set
	 */
	public void setListaAnosClassificados(
			Map<Coordenadas, Map<String, ArrayList<String>>> listaAnosClassificados) {
		this.listaAnosClassificados = listaAnosClassificados;
	}

	/**
	 * @return the deficit
	 */
	public Map<String, Integer> getDeficit() {
		return deficit;
	}

	/**
	 * @param deficit the deficit to set
	 */
	public void setDeficit(Map<String, Integer> deficit) {
		this.deficit = deficit;
	}

	/**
	 * @return the deficitCoordenada
	 */
	public Map<Coordenadas, Map<String, Integer>> getDeficitCoordenada() {
		return deficitCoordenada;
	}

	/**
	 * @param deficitCoordenada the deficitCoordenada to set
	 */
	public void setDeficitCoordenada(
			Map<Coordenadas, Map<String, Integer>> deficitCoordenada) {
		this.deficitCoordenada = deficitCoordenada;
	}

	/**
	 * @return the somatorioPrecipCoordenada
	 */
	public Map<Coordenadas, Map<String, Double>> getSomatorioPrecipCoordenada() {
		return somatorioPrecipCoordenada;
	}

	/**
	 * @param somatorioPrecipCoordenada the somatorioPrecipCoordenada to set
	 */
	public void setSomatorioPrecipCoordenada(
			Map<Coordenadas, Map<String, Double>> somatorioPrecipCoordenada) {
		this.somatorioPrecipCoordenada = somatorioPrecipCoordenada;
	}

	/**
	 * @return the volumeInicial
	 */
	public Double getVolumeInicial() {
		return volumeInicial;
	}

	/**
	 * @param volumeInicial the volumeInicial to set
	 */
	public void setVolumeInicial(Double volumeInicial) {
		this.volumeInicial = volumeInicial;
	}

	/**
	 * @return the somatorioPrecipitacao
	 */
	public Map<String, Double> getSomatorioPrecipitacao() {
		return somatorioPrecipitacao;
	}

	/**
	 * @param somatorioPrecipitacao the somatorioPrecipitacao to set
	 */
	public void setSomatorioPrecipitacao(Map<String, Double> somatorioPrecipitacao) {
		this.somatorioPrecipitacao = somatorioPrecipitacao;
	}

	/**
	 * @return the mapResultadosBalHid
	 */
	public Map<Coordenadas, ResultadosBalHid> getMapResultadosBalHid() {
		return mapResultadosBalHid;
	}

	/**
	 * @param mapResultadosBalHid the mapResultadosBalHid to set
	 */
	public void setMapResultadosBalHid(
			Map<Coordenadas, ResultadosBalHid> mapResultadosBalHid) {
		this.mapResultadosBalHid = mapResultadosBalHid;
	}

	/**
	 * @return the periodos
	 */
	public Map<Coordenadas, PeriodosIniciais> getPeriodos() {
		return periodos;
	}

	/**
	 * @param periodos the periodos to set
	 */
	public void setPeriodos(Map<Coordenadas, PeriodosIniciais> periodos) {
		this.periodos = periodos;
	}

	/**
	 * @return the precipitacao
	 */
	public Map<Coordenadas, Map<String, Double>> getPrecipitacao() {
		return precipitacao;
	}

	/**
	 * @param precipitacao the precipitacao to set
	 */
	public void setPrecipitacao(Map<Coordenadas, Map<String, Double>> precipitacao) {
		this.precipitacao = precipitacao;
	}

	/**
	 * Recupera a coleção de Parâmetros.
	 * @return lista com a coleção de parâmetros.
	 */
	public Map<Coordenadas, Parametros> getColecaoParametros() {
		return colecaoParametros;
	}

	/**
	 * Modifica a lista de parâmetros.
	 * @param colecaoParametros
	 */
	public void setColecaoParametros(Map<Coordenadas, Parametros> colecaoParametros) {
		this.colecaoParametros = colecaoParametros;
	}

	/**
	 * Método que recupera o número de dias totais do periodo.
	 * @return
	 */
	public Map<Coordenadas, Integer> recuperarQuantidadeDiasPMH() {
		
		// lista recebida da leitura do arquivo de precipitação.
		Map<Coordenadas, Map<String, Double>> listaPrecipitacao = this.precipitacao;
		
		Integer dias = 0;
		Map<Coordenadas, Integer> diasPMH = new HashMap<Coordenadas, Integer>();
		
		for (Coordenadas coordenadas : listaPrecipitacao.keySet()) {
			
			Map<String, Double> precipitacoes = listaPrecipitacao.get(coordenadas);
			
			for (String dataCorrente : precipitacoes.keySet()) {
				dias++;
			}
			
			diasPMH.put(coordenadas, dias);
			dias = 0;
			
		} //for
		
		return diasPMH;
	}
	
	/**
	 * Método que separa as precipitações por ano, para cada coordenada.
	 * @return lista de precipitações anuais por coordenada.
	 */
	public Map<Coordenadas, Map<String, LinkedList<Double>>> mapearPrecipitacoesAnuais() {
		
		// lista recebida da leitura do arquivo de precipitação.
		Map<Coordenadas, Map<String, Double>> listaPrecipitacao = this.precipitacao;
		// lista de resultados.
		Map<Coordenadas, Map<String, LinkedList<Double>>> resultados = new TreeMap<Coordenadas, Map<String, LinkedList<Double>>>();
		
		for (Coordenadas coordenadas : listaPrecipitacao.keySet()) {
			
			// lista de precipitacoes de acordo pelo data corrente.
			Map<String, Double> precipitacoes = listaPrecipitacao.get(coordenadas);
			// lista de todos os anos com suas precipitações
			Map<String, LinkedList<Double>> lista = new TreeMap<String, LinkedList<Double>>();
			// lista de precipitacoes do ano corrente
			LinkedList<Double> precip = new LinkedList<Double>();
			// variável de ano corrente
			String anoCorrente = "";
			
			// para cada data diária da coordenada
			for (String dataAtual : precipitacoes.keySet()) {
				
				anoCorrente = dataAtual.substring(0, 4);
				
				// se a lista for vazia
				if (lista.isEmpty()) {
					precip.add(precipitacoes.get(dataAtual));
					lista.put(anoCorrente, precip);
					
					// se não for vazia e contiver uma chave com o ano corrente
				} else if ((!lista.isEmpty()) && (lista.containsKey(anoCorrente))) {
					lista.get(anoCorrente).add(precipitacoes.get(dataAtual));
					
					// se não for vazia e não contiver uma chave com o ano corrente.
				} else {
					precip = new LinkedList<Double>();
					precip.add(precipitacoes.get(dataAtual));
					lista.put(anoCorrente, precip);
				}
				
			} //for
			
			resultados.put(coordenadas, lista);
		}
		
		return resultados;
	}
	
	/**
	 * Método que realiza a ponderação de valores de precipitação anuais.
	 * @return lista de anos classificados de acordo com o somatório de suas precipitações, 
	 * tudo para cada coordenada do pmh.
	 */
	public void ponderarValores() {
		
//		Map<String, ArrayList<BigDecimal>> precipitacoes = historico.getPrecipitacao();
		Map<Coordenadas, Map<String, Double>> precipitacoes = this.precipitacao;
		
		// para cada coordenada
		for (Coordenadas coordenadas : precipitacoes.keySet()) {

			Map<String, Double> precipitacoesDoPonto = precipitacoes.get(coordenadas);
			Integer inicio = Integer.parseInt(this.pesoPeriodo.getMesInicialChuvoso());
			Integer fim = Integer.parseInt(this.pesoPeriodo.getMesFinalChuvoso());
			String anoCorrente = "";
			int mesCorrente = 0;

			// entre o período de inicio e fim informado no arquivo de peso período.
			for (int i = inicio - 1; i < fim; i++) {
				
				// iterar para os meses correspondentes ao período do for acima.
				for (String dataAtual : precipitacoesDoPonto.keySet()) {
					
					anoCorrente = Parser.getAno(dataAtual);
					mesCorrente = Integer.parseInt(Parser.getMes(dataAtual));
					Double precipitacaoAtual = precipitacoesDoPonto.get(dataAtual);
					
					// se já contiver o ano na lista de somatórios
					if ((this.somatorioPrecipitacao.containsKey(anoCorrente)) && (mesCorrente == i)){
						Double value = this.somatorioPrecipitacao.get(anoCorrente);
						this.somatorioPrecipitacao.put(anoCorrente, value + precipitacaoAtual);
					} else if ((!this.somatorioPrecipitacao.containsKey(anoCorrente)) && (mesCorrente == i)){
						this.somatorioPrecipitacao.put(anoCorrente, precipitacaoAtual);
					}
					
				} //for
				
			} //for
			
			this.somatorioPrecipCoordenada.put(coordenadas, this.somatorioPrecipitacao);
			this.somatorioPrecipitacao = new HashMap<String, Double>();
			
		} //for
		
//		Map<Coordenadas, Map<String, ArrayList<String>>> listaAnosClassificados = new HashMap<Coordenadas, Map<String,ArrayList<String>>>();
		
		// para cada coordenada existente.
		for (Coordenadas coord : this.somatorioPrecipCoordenada.keySet()) {
		
			// lista com as precipitações ordenadas não repitidas.
			SortedSet<Double> precip = new TreeSet<Double>();
			Map<String, Double> somatorio = this.somatorioPrecipCoordenada.get(coord);
			
			// adiciona o somatorio em uma lista ordenada.
			for (String ano : somatorio.keySet()) {
				precip.add(somatorio.get(ano));
			}
			
			// regra para classificação
			Integer listaOrdenada = precip.size();
			Integer quociente = (listaOrdenada / 3);
			
			List<Double> temp = new ArrayList<Double>(precip);
	
			// map para classificar o ano em seco normal e chuvoso
			Map<String, ArrayList<String>> classificacoes = new HashMap<String, ArrayList<String>>();
			
			classificacoes.put(SECO, new ArrayList<String>());
			classificacoes.put(NORMAL, new ArrayList<String>());
			classificacoes.put(CHUVOSO, new ArrayList<String>());
			
			for (String ano : somatorio.keySet()) {
				
				// preenche o map com a classificação
				Double precCorrente = somatorio.get(ano);
				
				if ( precCorrente.compareTo(temp.get(quociente - 1)) != 1 ) {
					classificacoes.get(SECO).add(ano);
				} else if ( precCorrente.compareTo(temp.get(listaOrdenada - quociente - 1)) != 1 ) {
					classificacoes.get(NORMAL).add(ano);
				} else {
					classificacoes.get(CHUVOSO).add(ano);
				}
				
			} //for
			
			this.listaAnosClassificados.put(coord, classificacoes);
			
		} //for
		
	}
	
	/**
	 * Método que realiza o balanço hídrico da cisterna para o período completo.
	 * @throws Exception 
	 */
	public void calcularBalHidNormal() throws Exception {
		
		Map<Coordenadas, Map<String, Double>> listaPrecipitacao = this.precipitacao;
		Map<Coordenadas, Integer> diasPMH = this.recuperarQuantidadeDiasPMH();
		
		// para cada coordenada do pmh
		for (Coordenadas coordenadas : listaPrecipitacao.keySet()) {
			
			// precipitações do ponto
			Map<String, Double> precipitacoesDoPonto = listaPrecipitacao.get(coordenadas);
			boolean ehPrimeitoVD = true;
			LinkedList<Double> listaVD = new LinkedList<Double>();
			// lista de objetos de deficit.
			ArrayList<Deficit> listaDeficit = new ArrayList<Deficit>();
			
			Integer demandaAtendida = 0;
			Double garantiaSuprimento = 0.0;
			Double deficit = 0.0;
			String localidade = "";
			Double areaCapitacao = 0.0;
			Double perdas = 0.0;
			ArrayList<String> demanda = null;
			Double capacidade = 0.0;
			
			// inicialização das variáveis vindas do arquivo de parâmetros.
			if (this.colecaoParametros.containsKey(coordenadas)) {
				localidade = this.colecaoParametros.get(coordenadas).getDescricao();
				areaCapitacao = this.colecaoParametros.get(coordenadas).getAreaCapitacao();
				perdas = this.colecaoParametros.get(coordenadas).getPerdas();
				
//AQUI
				
				demanda = (ArrayList<String>)this.colecaoParametros.get(coordenadas).getDemanda();
				
				
				capacidade = this.colecaoParametros.get(coordenadas).getCapacidade();
			} else {
				throw new InconsistenciaArquivosException("Há uma inconsistência entre o arquivo de parâmetros e o PMH!");
			}
			
			// bloco de fórmulas para o primeiro dia do PMH.
			Double precipitacaoAtual = precipitacoesDoPonto.get(this.periodos.get(coordenadas).getDataInicial());
			
			StringTokenizer tk2 = null;
			String indexBaseadoNaData = "";
			try {
				tk2 = new StringTokenizer(this.periodos.get(coordenadas).getDataInicial(), "-");
				
				tk2.nextToken();
				indexBaseadoNaData = tk2.nextToken();
			} catch (Exception e) {
				System.err.println("Nao foi possivel obter informação de uma data no arquivo pmh. Verifique o formato aaaa-mm-dd");
				e.printStackTrace();
			}
			
			
			if(indexBaseadoNaData.startsWith("0")){
				indexBaseadoNaData = indexBaseadoNaData.substring(1);
			}
			
			//Se so houver um elemento, pegar sempre a posição inicial
			if(demanda.size() == 1){
				indexBaseadoNaData = "1";
			}else{
				if (demanda.size() > 1 && demanda.size() < 12){
					throw new Exception("Data inválida no arquivo pmh"); 
				}
			}
			
			
			Double volumeCaptado = (areaCapitacao * perdas) * (precipitacaoAtual / 1000);
			
			if(!argumento.equalsIgnoreCase("normal")){
				indexBaseadoNaData = "1";
			}
			
			Double demandaAux = null;
			try {
				demandaAux = new Double(demanda.get(new Integer(indexBaseadoNaData)-1).replace(",", "."));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Double volumeConsumido = Math.min(demandaAux, ((this.volumeInicial / 100) * capacidade) + volumeCaptado);

			Double volumeDisponivel = ((this.volumeInicial / 100) * capacidade) - volumeConsumido;
			Deficit def = null;
			
			ArrayList<String> detalhesLista = new ArrayList<String>();
			String detalhesAux = "";
			

			// para cada data da coordenada
			for (String dataAtual : precipitacoesDoPonto.keySet()) {
				
//				Double precipitacaoAtual = precipitacoesDoPonto.get(dataAtual);

				// se for a primeira execução
				if (ehPrimeitoVD) {
					//adiciona na lista de volume disponível
					listaVD.add(volumeDisponivel);
					ehPrimeitoVD = false;
					
					if(detalhes.equals("detalhes")){
						detalhesAux = "Data: "+dataAtual+ " VC: "+Parser.roundDouble(2,volumeCaptado)+ " VD: "+Parser.roundDouble(2,volumeDisponivel)+ " Garantia: - ";
						detalhesLista.add(detalhesAux);
					}
					
					
				} else {
					StringTokenizer tk3 = null;
					String data = "";
					String indexCorrenteDaDemandaBaseadoNaData = "";
					try {
						tk3 = new StringTokenizer(dataAtual, "-");
						
						tk3.nextToken();
						data = tk3.nextToken();
						indexCorrenteDaDemandaBaseadoNaData = data;
						
					} catch (Exception e) {
						System.err.println("Nao foi possivel obter informação de uma data no arquivo pmh. Verifique o formato aaaa-mm-dd");
						e.printStackTrace();
					}
					
		
					
		
					
					
					if(indexCorrenteDaDemandaBaseadoNaData.startsWith("0")){
						indexCorrenteDaDemandaBaseadoNaData = indexCorrenteDaDemandaBaseadoNaData.substring(1);
					}
					
					//Se so houver um elemento, pegar sempre a posição inicial
					if(demanda.size() == 1){
						indexCorrenteDaDemandaBaseadoNaData = "1";
					}
					
					if(!argumento.equalsIgnoreCase("normal")){
						indexCorrenteDaDemandaBaseadoNaData = "1";
					}
					
					demandaCorrente = new Double(demanda.get(new Integer(indexCorrenteDaDemandaBaseadoNaData)-1).replace(",", "."));
					
					
					// para o segundo dia em diante
					precipitacaoAtual = precipitacoesDoPonto.get(dataAtual);
					volumeCaptado = (areaCapitacao * perdas) * (precipitacaoAtual / 1000);
					
			
					
					volumeConsumido = Math.min(demandaCorrente, (listaVD.getLast() + volumeCaptado));
					
					volumeDisponivel = Math.min(listaVD.getLast() + (volumeCaptado - volumeConsumido), capacidade);
					
					listaVD.add(volumeDisponivel);
					
					garantiaSuprimento = ( Double.valueOf(demandaAtendida.toString()) / Double.valueOf("" + diasPMH.get(coordenadas) ) ) * 100;
					
					if(detalhes.equals("detalhes")){
						detalhesAux = "Data: "+dataAtual+ " VC: "+Parser.roundDouble(2,volumeCaptado)+ " VD: "+Parser.roundDouble(2,volumeDisponivel)+ " Garantia: "+Parser.roundDouble(2,garantiaSuprimento);
						detalhesLista.add(detalhesAux);
					}
					
					
				} //if
				
				// se a demanda for atendida
				if (volumeDisponivel >= demandaCorrente) {
					demandaAtendida++;
					if (def != null) {
						listaDeficit.add(new Deficit(def.getDataInicialDeficit(), def.getDiasConsecutivos()));
						def = null;
					}
					
				} else {
					/*
					 * Caso não seja a demanda não seja atendida, é preciso 
					 * guardar os valores consecutivos em uma lista, chaveada
					 * para cada ano.
					 */
					if (def == null) {
						def = new Deficit();
						String dataConsecutiva = dataAtual;
						def.setDataInicialDeficit(dataConsecutiva);
						def.setDiasConsecutivos(def.incrementaDias());
						
					} else {
						def.setDiasConsecutivos(def.incrementaDias());
					}
					
				} //if-else
				
			} //for diario

			/* 
			 * Caso tenha terminado a iteracao do periodo para essa coordenada
			 * e a variável def contenha dados para serem adicionados na lista.
			 */ 
			if (def != null) {
				listaDeficit.add(def);
				def = null;
			}
			
			Deficit def2 = new Deficit();
			
			if (!listaDeficit.isEmpty()) {
				// Bloco onde se recupera o maior período de deficit.
				Integer number = listaDeficit.get(0).getDiasConsecutivos();
				def2 = listaDeficit.get(0);
				
				for (Deficit deficit2 : listaDeficit) {
					if (deficit2.getDiasConsecutivos() > number) {
						def2.setDataInicialDeficit(deficit2.getDataInicialDeficit());
						def2.setDiasConsecutivos(deficit2.getDiasConsecutivos());
						number = deficit2.getDiasConsecutivos();
					}
				}
				
			}
			
			// limpando a lista evitando acúmulo em memória
			listaDeficit.clear();
			// calcula a garantia de suprimento
			garantiaSuprimento = ( Double.valueOf(demandaAtendida.toString()) / Double.valueOf("" + diasPMH.get(coordenadas) ) ) * 100;
			deficit = 100 - garantiaSuprimento;
			
			ResultadosBalHid resultados = new ResultadosBalHid(localidade, this.periodos
					.get(coordenadas).getDataInicial(), this.periodos.get(
					coordenadas).getDataFinal(), garantiaSuprimento, deficit, def2, detalhesLista);
			
			// adiciona na lista os resultados do balanço hídrico de acordo com a coordenada.
			this.mapResultadosBalHid.put(coordenadas, resultados);
			
		} //for coordenada
		
	}

	/**
	 * Método que calcula o balanço hídrico da cisterna por consenso.
	 * @throws InconsistenciaArquivosException 
	 */
	public void calcularBalHidPorConsenso() throws InconsistenciaArquivosException {

		Map<Coordenadas, Map<String, LinkedList<Double>>> listaPrecipitacao = this.mapearPrecipitacoesAnuais();
		
		// para cada coordenada do pmh
		for (Coordenadas coordenadas : listaPrecipitacao.keySet()) {
			
			// precipitações do ponto
			Map<String, LinkedList<Double>> precipitacoesDoPonto = listaPrecipitacao.get(coordenadas);
			
			String dataInicial = this.periodos.get(coordenadas).getDataInicial();
			String localidade = "";
			Double areaCapitacao = 0.0;
			Double perdas = 0.0;
			Double demanda = 0.0;
			Double capacidade = 0.0;
			
			// inicialização das variáveis vindas do arquivo de parâmetros.
			if (this.colecaoParametros.containsKey(coordenadas)) {
				localidade = this.colecaoParametros.get(coordenadas).getDescricao();
				areaCapitacao = this.colecaoParametros.get(coordenadas).getAreaCapitacao();
				perdas = this.colecaoParametros.get(coordenadas).getPerdas();
				
				//Um unico valor, mas vem na lista
				demanda = new Double (this.colecaoParametros.get(coordenadas).getDemanda().get(0).replace(",", "."));
				capacidade = this.colecaoParametros.get(coordenadas).getCapacidade();
			} else {
				throw new InconsistenciaArquivosException("Há uma inconsistência entre o arquivo de parâmetros e o PMH!");
			}
			
			Map<String, ResultadosBalHid> resultadoAnual = new HashMap<String, ResultadosBalHid>();
			
			// para cada ano da coordenada
			for (String anoAtual : precipitacoesDoPonto.keySet()) {

				// lista de objetos de deficit.
				ArrayList<Deficit> listaDeficit = new ArrayList<Deficit>();
				
				Deficit def = null;
				// lista de volumes disponiveis
				LinkedList<Double> listaVD = new LinkedList<Double>();
				Integer demandaAtendida = 0;
				Double garantiaSuprimento = 0.0;
				Double deficit = 0.0;
				boolean ehPrimeitoVD = true;
				LinkedList<Double> precip = precipitacoesDoPonto.get(anoAtual);
				int quantidadeDiasConsecutivos = 0;
				
				// bloco de fórmulas para o primeiro dia do PMH.
				Double precipitacaoAtual = precip.get(0);
				Double volumeCaptado = (areaCapitacao * perdas) * (precipitacaoAtual / 1000);
				Double volumeConsumido = Math.min(demanda, ((this.volumeInicial / 100) * capacidade) + volumeCaptado);
				Double volumeDisponivel = ((this.volumeInicial / 100) * capacidade) - volumeConsumido;
				
				// para cada precipitação do ano atual.
				for (Double double1 : precip) {
				
					quantidadeDiasConsecutivos++;
					precipitacaoAtual = double1;
					
					// se for a primeira execução
					if (ehPrimeitoVD) {
						
						listaVD.add(volumeDisponivel);
						ehPrimeitoVD = false;
						
					} else {
						// para o segundo dia em diante
						volumeCaptado = (areaCapitacao * perdas) * (precipitacaoAtual / 1000);
						volumeConsumido = Math.min(demanda, (listaVD.getLast() + volumeCaptado));
						volumeDisponivel = Math.min(listaVD.getLast() + (volumeCaptado - volumeConsumido), capacidade);
						listaVD.add(volumeDisponivel);
						
					} //if
					
					// se a demanda for atendida
					if (volumeDisponivel >= demanda) {
						demandaAtendida++;
						if (def != null) {
							listaDeficit.add(def);
							def = null;
						}
						
					} else {
						/*
						 * Caso não seja a demanda não seja atendida, é preciso 
						 * guardar os valores consecutivos em uma lista, chaveada
						 * para cada ano.
						 */
						if (def == null) {
							
							def = new Deficit();
							// tem que criar esse objeto dessa forma por causa da referência.
							String dataConsecutiva = "";

							// caso aconteça deficit no primeiro dia, a data tem que ser o do primeiro dia, 
							// sem realizar soma de dias.
							if (quantidadeDiasConsecutivos == 1) {
								dataConsecutiva = dataInicial;
								
							} else {
								String dataInicialAuxiliar = anoAtual +"/"+ Parser.getMes(dataInicial) +"/"+ Parser.getDia(dataInicial);
								dataConsecutiva = Parser.calendar2String2(Parser.somaDia(Parser.string2Calendar(dataInicialAuxiliar), quantidadeDiasConsecutivos - 1));
							}
							
							def.setDataInicialDeficit(dataConsecutiva);
							def.setDiasConsecutivos(def.incrementaDias());
							
						} else {
							def.setDiasConsecutivos(def.incrementaDias());
						}
						
					} //if-else
				
				} //for precipitação diária
				
				/* 
				 * Caso tenha terminado a iteracao do periodo para essa coordenada
				 * e a variável def contenha dados para serem adicionados na lista.
				 */ 
				if (def != null) {
					listaDeficit.add(def);
					def = null;
				}
				
				Deficit def2;
				
				// se a lista de deficits não estiver vazia
				if (!listaDeficit.isEmpty()) {
					// Bloco onde se recupera o maior período de deficit.
					Integer number = listaDeficit.get(0).getDiasConsecutivos();
					def2 = listaDeficit.get(0);
					
					for (Deficit deficit2 : listaDeficit) {
						if (deficit2.getDiasConsecutivos() > number) {
							def2.setDataInicialDeficit(deficit2.getDataInicialDeficit());
							def2.setDiasConsecutivos(deficit2.getDiasConsecutivos());
							number = deficit2.getDiasConsecutivos();
						}
					}
					
				} else {
					// caso a lista esteja vazia, cria-se um objeto vazio para adicionar nos resultados.
					def2 = new Deficit();
				}
				
				// calcula a garantia de suprimento
				garantiaSuprimento = ( Double.valueOf(demandaAtendida.toString()) / Double.valueOf("" + precip.size() ) ) * 100;
				deficit = 100 - garantiaSuprimento;
				
				ResultadosBalHid resultados = new ResultadosBalHid(localidade, this.periodos
						.get(coordenadas).getDataInicial(), this.periodos.get(
						coordenadas).getDataFinal(), garantiaSuprimento, deficit, def2, null);
				
				resultadoAnual.put(anoAtual, resultados);
				
				// adiciona na lista os resultados do balanço hídrico de acordo com a coordenada.
				if (this.mapResultadosBalHidConsenso.isEmpty()) {
					// caso a lista esteja vazia
					this.mapResultadosBalHidConsenso.put(coordenadas, resultadoAnual);
					
				} else if ( (!this.mapResultadosBalHidConsenso.isEmpty()) && (this.mapResultadosBalHidConsenso.containsKey(coordenadas)) ) {
					// caso a lista não esteja vazia e contenha a coordenada corrente, recupera os valores 
					// e seta os novos valores
					Map<String, ResultadosBalHid> map = this.mapResultadosBalHidConsenso.get(coordenadas);
					map.put(anoAtual, resultados);
					
				} else if ((!this.mapResultadosBalHidConsenso.isEmpty()) && (!this.mapResultadosBalHidConsenso.containsKey(coordenadas))) {
					// caso a lista não esteja vazia e não contenha a coordenada corrente.
					Map<String, ResultadosBalHid> map = new HashMap<String, ResultadosBalHid>();
					map.put(anoAtual, resultados);
					this.mapResultadosBalHidConsenso.put(coordenadas, map);
					
				}
			
			} //for anual
			
		} //for coordenada
		
	} //metodo

	/**
	 * Método que povoa o objeto referente a reamostragem da 
	 * previsão de consenso.
	 * @throws ReamostragemException
	 */
	public void montarObjetoReamostragem() throws ReamostragemException {
		
		// para cada coordenada.
		for (Coordenadas coordenada : this.somatorioPrecipCoordenada.keySet()) {
			
			Map<String, Double> listaPrecip = this.somatorioPrecipCoordenada.get(coordenada);
			ArrayList<Reamostragem> listaReamostragem = new ArrayList<Reamostragem>();
			
			// para cada ano dessa coordenada.
			for (String anoCorrente : listaPrecip.keySet()) {
				
				Reamostragem amostra = new Reamostragem();
				amostra.setAno(anoCorrente);
				amostra.setSomatorioPrecipitacao(listaPrecip.get(anoCorrente));
				amostra.setDeficit(this.mapResultadosBalHidConsenso.get(coordenada).get(anoCorrente).getDeficit());
				amostra.setGarantia(this.mapResultadosBalHidConsenso.get(coordenada).get(anoCorrente).getGarantia());
				amostra.setDiasConsecutivos(this.mapResultadosBalHidConsenso.get(coordenada).get(anoCorrente).getDef().getDiasConsecutivos());
				
				if (this.listaAnosClassificados.get(coordenada).get(SECO).contains(anoCorrente)) {
					amostra.setClassificacao(SECO);
				} else if (this.listaAnosClassificados.get(coordenada).get(NORMAL).contains(anoCorrente)) {
					amostra.setClassificacao(NORMAL);
				} else if (this.listaAnosClassificados.get(coordenada).get(CHUVOSO).contains(anoCorrente)) {
					amostra.setClassificacao(CHUVOSO);
				} else {
					throw new ReamostragemException("Falha: Não foi possível realizar a reamostragem da previsão de consenso!");
				}
				
				listaReamostragem.add(amostra);
				
			} //for ano
			this.mapReamostragem.put(coordenada, listaReamostragem);
			
		} //for coordenada
		
	}
	
	/**
	 * Método que realiza a ponderação e classificação dos dias de deficit
	 * consecutivos quando se executa por consenso, para obter o resultado único de dias,
	 * visto que só tem os dias de forma anual e precisa-se de forma para o período todo.
	 */
	public void ponderarValoresParaDiasConsecutivos() {
		
		Double pesoSeco = this.pesoPeriodo.getPeriodoSeco();
		
		Double pesoNormal = this.pesoPeriodo.getPeriodoNormal();
		
		Double pesoChuvoso = this.pesoPeriodo.getPeriodoChuvoso();
		
		// para cada coordenada
		for (Coordenadas coordenadas : this.mapReamostragem.keySet()) {
			
			ArrayList<Reamostragem> listaReamostragem = this.mapReamostragem.get(coordenadas);
			
			Double pesoMedio = 0.0;
			Double somatorioPesoPeriodo = 0.0;
			ArrayList<Double> listaPeso = new ArrayList<Double>();
			ArrayList<Double> listaPesoMedio = new ArrayList<Double>();
			
			// para todos os anos existentes
			for (Reamostragem reamostragem : listaReamostragem) {
				
				/*
				 * De acordo com a classificação, faz-se a multiplicação
				 * pelo peso correspondente, passado como parametro em um arquivo de 
				 * entrada.
				 */
				if (reamostragem.getClassificacao().equals(SECO)) {
					listaPesoMedio.add(reamostragem.getDiasConsecutivos() * pesoSeco);
					listaPeso.add(pesoSeco);
				} else if (reamostragem.getClassificacao().equals(NORMAL)) {
					listaPesoMedio.add(reamostragem.getDiasConsecutivos() * pesoNormal);
					listaPeso.add(pesoNormal);
				} else if (reamostragem.getClassificacao().equals(CHUVOSO)) {
					listaPesoMedio.add(reamostragem.getDiasConsecutivos() * pesoChuvoso);
					listaPeso.add(pesoChuvoso);
				}
				
			} //for ramostragem
			
			for (Double medio : listaPesoMedio) {
				pesoMedio += medio;
			}
			
			for (Double peso : listaPeso) {
				somatorioPesoPeriodo += peso;
			}
						
			// realiza fórmula da ponderação
			Double valor = pesoMedio / somatorioPesoPeriodo;
			// map de resultados do volume medio ponderado
			this.mapPonderacaoDiasConsecutivosConsenso.put(coordenadas, valor);
			
		} //for coordenadas
		
	}
	
	/**
	 * Método que realiza a classificação do prognóstico.
	 */
	public void classificarPrognostico() {
		
		// Maps de classificação de frequência estimada
		Map<String, ArrayList<Reamostragem>> amostra = new TreeMap<String, ArrayList<Reamostragem>>();
		
		amostra.put(AMOSTRA_0_10, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_10_20, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_20_30, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_30_40, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_40_50, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_50_60, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_60_70, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_70_80, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_80_90, new ArrayList<Reamostragem>());
		amostra.put(AMOSTRA_90_100, new ArrayList<Reamostragem>());
		
		// para cada coordenada.
		for (Coordenadas coordenadas : this.mapReamostragem.keySet()) {
			
			ArrayList<Reamostragem> reamostragem = this.mapReamostragem.get(coordenadas);
			
			// para cada reamostragem
			for (Reamostragem reamostragem2 : reamostragem) {
				
				/*
				 * Verifica em qual porcentagem de deficit hídrico se encontra
				 * a reamostragem.
				 */
				if (reamostragem2.getDeficit() < 10) {
					amostra.get(AMOSTRA_0_10).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 10 ) && (reamostragem2.getDeficit() < 20)) {
					amostra.get(AMOSTRA_10_20).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 20 ) && (reamostragem2.getDeficit() < 30)) {
					amostra.get(AMOSTRA_20_30).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 30 ) && (reamostragem2.getDeficit() < 40)) {
					amostra.get(AMOSTRA_30_40).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 40 ) && (reamostragem2.getDeficit() < 50)) {
					amostra.get(AMOSTRA_40_50).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 50 ) && (reamostragem2.getDeficit() < 60)) {
					amostra.get(AMOSTRA_50_60).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 60 ) && (reamostragem2.getDeficit() < 70)) {
					amostra.get(AMOSTRA_60_70).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 70 ) && (reamostragem2.getDeficit() < 80)) {
					amostra.get(AMOSTRA_70_80).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 80 ) && (reamostragem2.getDeficit() < 90)) {
					amostra.get(AMOSTRA_80_90).add(reamostragem2);
				} else if ((reamostragem2.getDeficit() >= 90 ) && (reamostragem2.getDeficit() <= 100)) {
					amostra.get(AMOSTRA_90_100).add(reamostragem2);
				}
				
			} //for reamostragem
			
			this.mapFrequenciaEstimada.put(coordenadas, amostra);
			
		} //for coordenadas
		
	}
	
	/**
	 * Método que calcula o MDC dos prognósticos de seco, normal e chuvoso.
	 * @return o MDC
	 */
	public Integer calcularMDC() {
		
		Double secoDouble = this.pesoPeriodo.getPeriodoSeco() * 100;
		Double normalDouble = this.pesoPeriodo.getPeriodoNormal() * 100;
		Double chuvosoDouble = this.pesoPeriodo.getPeriodoChuvoso() * 100;
		
		Integer seco = secoDouble.intValue();
		Integer normal = normalDouble.intValue();
		Integer chuvoso = chuvosoDouble.intValue();
		
		Integer mdc = Parser.mdc(seco, Parser.mdc(normal, chuvoso));
		return mdc;
	}
	
	/**
	 * Método que monta a lista referente a tabela de frequência estimada, multiplicando
	 * os valores de pesos reais depois de fazer o MDC.
	 */
	public void calcularQuantidadeObservadaReamostragem() {
		
		Integer mdc = this.calcularMDC();
		
		Double secoDouble = this.pesoPeriodo.getPeriodoSeco() * 100;
		Double normalDouble = this.pesoPeriodo.getPeriodoNormal() * 100;
		Double chuvosoDouble = this.pesoPeriodo.getPeriodoChuvoso() * 100;
		
		Integer seco = secoDouble.intValue() / mdc;
		Integer normal = normalDouble.intValue() / mdc;
		Integer chuvoso = chuvosoDouble.intValue() / mdc;
		
		// para cada coordenada.
		for (Coordenadas coordenada : this.mapFrequenciaEstimada.keySet()) {
			
			Map<String, ArrayList<Reamostragem>> reamostragem = this.mapFrequenciaEstimada.get(coordenada);
			Map<String, Integer> quantObservada = new TreeMap<String, Integer>();
			Integer quantidadeTotal = 0;
			
			// para cada porcentagem de frequência.
			for (String amostras : reamostragem.keySet()) {
				
				ArrayList<Reamostragem> amostra = reamostragem.get(amostras);
				Integer quantidadeObservada = 0;
				
				// para cada reamostragem, verifica-se a classificação para a multiplicação do peso.
				for (Reamostragem amostra2 : amostra) {
					
					if (amostra2.getClassificacao() == SECO) {
						quantidadeObservada += seco;
					} else if (amostra2.getClassificacao() == NORMAL) {
						quantidadeObservada += normal;
					} else {
						quantidadeObservada += chuvoso;
					}
					
				} //for amostra2
				
				quantidadeTotal += quantidadeObservada;
				quantObservada.put(amostras, quantidadeObservada);
				
			} //for amostras
			this.mapQuantidadeTotalObservado.put(coordenada, quantidadeTotal);
			this.mapQuantidadeObservada.put(coordenada, quantObservada); 
			
		} //for coordenadas
		
	}
	
	/**
	 * Método que monta os resultados finais da tabela de frequência estimada.
	 */
	public void montarTabelaFrequencia() {
		
		// para cada coordenada.
		for (Coordenadas coordenada : this.mapQuantidadeObservada.keySet()) {
			
			Map<String, Integer> quantidadeObservada = this.mapQuantidadeObservada.get(coordenada);
			Map<String, Double> quantidadeFinal = new TreeMap<String, Double>();
			
			// para cada amostra.
			for (String amostras : quantidadeObservada.keySet()) {
				
				Integer total = this.mapQuantidadeTotalObservado.get(coordenada);
				Integer quantidade = quantidadeObservada.get(amostras);
				
				Double porcentagemFrequenciaEstimada = Parser.calcularRegraDeTres(total, quantidade);
				
				quantidadeFinal.put(amostras, porcentagemFrequenciaEstimada);
				
			} //for amostras
		
			this.mapResultadoFrequencia.put(coordenada, quantidadeFinal);
			
		} //for coordenada
		
	}
	
	/**
	 * Método para se realizar a previsão por consenso. 
	 * @throws ReamostragemException 
	 * @throws InconsistenciaArquivosException 
	 */
	public void calcularPrevisaoConsenso() throws ReamostragemException, InconsistenciaArquivosException {
		
		this.calcularBalHidPorConsenso();
		this.ponderarValores();
		this.montarObjetoReamostragem();
		this.ponderarValoresParaDiasConsecutivos();
		this.classificarPrognostico();
		this.calcularQuantidadeObservadaReamostragem();
		this.montarTabelaFrequencia();
		
	}
	
	/**
	 * Método para zerar os valores das listas caso esteja sendo executado de
	 * forma completa.
	 * @param valor
	 */
	public void zerarListas() {
		
		this.mapResultadosBalHid = new TreeMap<Coordenadas, ResultadosBalHid>();
		this.somatorioPrecipitacao = new HashMap<String, Double>();
		this.somatorioPrecipCoordenada = new HashMap<Coordenadas, Map<String,Double>>();
		this.deficit = new HashMap<String, Integer>();
		this.deficitCoordenada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.listaAnosClassificados = new HashMap<Coordenadas, Map<String,ArrayList<String>>>();
		this.mapReamostragem = new HashMap<Coordenadas, ArrayList<Reamostragem>>();
		this.mapFrequenciaEstimada = new HashMap<Coordenadas, Map<String,ArrayList<Reamostragem>>>();
		this.mapQuantidadeObservada = new HashMap<Coordenadas, Map<String,Integer>>();
		this.mapQuantidadeTotalObservado = new HashMap<Coordenadas, Integer>();
		this.mapResultadoFrequencia = new HashMap<Coordenadas, Map<String,Double>>();
		this.mapResultadoCenarios = new HashMap<ResultadosCenarios, ResultadoFinalCompleto>();
		this.mapResultadoVariacao = new HashMap<ResultadosCenarios, Map<Coordenadas,ResultadosBalHid>>();
		this.mapResultadosBalHidConsenso = new HashMap<Coordenadas, Map<String,ResultadosBalHid>>();
		this.mapPonderacaoDiasConsecutivosConsenso = new HashMap<Coordenadas, Double>();
		
	}
	
	/**
	 * Método para se realizar a previsão utilizando variação de cenários.
	 * @throws ReamostragemException 
	 * @throws InconsistenciaArquivosException 
	 */
	public void calcularPrevisaoCompleta() throws ReamostragemException, InconsistenciaArquivosException {
		
		Double demanda1 = this.cenarios.getDemanda().get(0);
		Double demanda2 = this.cenarios.getDemanda().get(1);
		Double demanda3 = this.cenarios.getDemanda().get(2);
		
		Double volume1 = this.cenarios.getVolume().get(0);
		Double volume2 = this.cenarios.getVolume().get(1);
		Double volume3 = this.cenarios.getVolume().get(2);
		
		Double area1 = this.cenarios.getAreaCaptacao().get(0);
		Double area2 = this.cenarios.getAreaCaptacao().get(1);
		Double area3 = this.cenarios.getAreaCaptacao().get(2);
		
		// para cada probabilidade do cenário de demanda
		for (Double dem = demanda1; dem <= demanda2; dem += demanda3) {
			
			// para cada probabilidade do cenário de volume
			for (Double vol = volume1; vol <= volume2; vol += volume3) {
				
				// para cada probabilidade do cenários de area de captação
				for (Double area = area1; area <= area2; area += area3) {
					
					// altera os parâmetros de entrada para esses cenários
					for (Coordenadas coordenada : this.colecaoParametros.keySet()) {
						
						Parametros parametros = this.colecaoParametros.get(coordenada);
						parametros.setAreaCapitacao(area);
						
						parametros.setDemanda(dem);
						parametros.setCapacidade(vol);
						this.colecaoParametros.put(coordenada, parametros);
						
					}

					// instacia esse objeto para guardar os valores utilizados para esse cenário.
					ResultadosCenarios cenarios = new ResultadosCenarios();
					cenarios.setAreaCapitacao(area);
					cenarios.setDemanda(dem);
					cenarios.setVolume(vol);
					
					// para cada cenário, verifica se a lista de resultados está vazia,
					// senão, limpa as listas intermediárias para evitar problemas com referência.
					if (this.mapResultadoCenarios.isEmpty()) {
						
						// realiza os cálculos de consenso da mesma forma já com os valores modificados.
						this.calcularPrevisaoConsenso();
						ResultadoFinalCompleto resultadoBalhid = new ResultadoFinalCompleto(this.mapResultadosBalHidConsenso, this.mapPonderacaoDiasConsecutivosConsenso, this.mapResultadoFrequencia);
						this.mapResultadoCenarios.put(cenarios, resultadoBalhid);
						
					} else {
						// limpa as listas intermediárias
						this.mapResultadosBalHidConsenso = new HashMap<Coordenadas, Map<String,ResultadosBalHid>>();
						this.mapPonderacaoDiasConsecutivosConsenso = new HashMap<Coordenadas, Double>();
						this.mapResultadoFrequencia = new HashMap<Coordenadas, Map<String,Double>>();
						
						// calcula a previsaão de consenso.
						this.calcularPrevisaoConsenso();
						ResultadoFinalCompleto resultadoBalhid = new ResultadoFinalCompleto(this.mapResultadosBalHidConsenso, this.mapPonderacaoDiasConsecutivosConsenso, this.mapResultadoFrequencia);
						this.mapResultadoCenarios.put(cenarios, resultadoBalhid);
					}
					
//					this.zerarListas();
					
				}
				
				
			} //for volume
			
		} //for demanda
			
	} //metodo
	
	/**
	 * Método que executa a aplicação com variação de cenários. Sem o consenso.
	 * @throws Exception 
	 */
	public void calcularPrevisaoComVariacao() throws Exception {
		
		Double demanda1 = this.cenarios.getDemanda().get(0);
		Double demanda2 = this.cenarios.getDemanda().get(1);
		Double demanda3 = this.cenarios.getDemanda().get(2);
		
		Double volume1 = this.cenarios.getVolume().get(0);
		Double volume2 = this.cenarios.getVolume().get(1);
		Double volume3 = this.cenarios.getVolume().get(2);
		
		Double area1 = this.cenarios.getAreaCaptacao().get(0);
		Double area2 = this.cenarios.getAreaCaptacao().get(1);
		Double area3 = this.cenarios.getAreaCaptacao().get(2);
		
		// para cada probabilidade do cenário de demanda
		for (Double dem = demanda1; dem <= demanda2; dem += demanda3) {
			
			// para cada probabilidade do cenário de volume
			for (Double vol = volume1; vol <= volume2; vol += volume3) {
				
				// para cada probabilidade do cenários de area de captação
				for (Double area = area1; area <= area2; area += area3) {
					
					// altera os parâmetros de entrada para esses cenários
					for (Coordenadas coordenada : this.colecaoParametros.keySet()) {
						
						Parametros parametros = this.colecaoParametros.get(coordenada);
						parametros.setAreaCapitacao(area);
						parametros.setDemanda(dem);
						parametros.setCapacidade(vol);
						this.colecaoParametros.put(coordenada, parametros);
						
					}

					// instacia esse objeto para guardar os valores utilizados para esse cenário.
					ResultadosCenarios cenarios = new ResultadosCenarios();
					cenarios.setAreaCapitacao(area);
					cenarios.setDemanda(dem);
					cenarios.setVolume(vol);
					
					// verifica se a lista contem algum valor, para ser zerado por
					// causa da referência.
					if (this.mapResultadosBalHid.isEmpty()) {
						this.calcularBalHidNormal();
					} else {
						this.mapResultadosBalHid = new HashMap<Coordenadas, ResultadosBalHid>();
						this.calcularBalHidNormal();
					}
					
					this.mapResultadoVariacao.put(cenarios, this.mapResultadosBalHid);
					
				}
				
			} //for volume
			
		} //for demanda
		
	}
	
	/**
	 * Método que executa somente o balanço hídrico.
	 * @throws Exception 
	 */
	public void calcularPrevisaoNormal() throws Exception {
		this.calcularBalHidNormal();
	}
	
	/**
	 * Método que chama os métodos de execução de cálculos de acordo com o argumento
	 * informado na linha de comando.
	 * @throws Exception 
	 */
	public void calcularPrevisao() throws Exception {
		
		
		if (this.argumento.equals("normal")) {
			this.calcularPrevisaoNormal();
		} else if ((this.cenarios != null) && (this.argumento.equals("variacao"))) {
			this.calcularPrevisaoComVariacao();
		} else if (this.argumento.equals("consenso")) {
			this.calcularPrevisaoConsenso();
		} else if ((this.cenarios != null) && (this.argumento.equals("completo"))) {
			this.calcularPrevisaoCompleta();
		} else {
			throw new InconsistenciaArquivosException("Há uma inconsistência no argumento passado como parâmetro com os arquivos de entrada!");
		}
		
	}
	
}
