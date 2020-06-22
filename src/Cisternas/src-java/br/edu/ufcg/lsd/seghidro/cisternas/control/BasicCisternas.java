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

package br.edu.ufcg.lsd.seghidro.cisternas.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Precipitacao;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.output.MontarResultados;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.control.CisternasBalHidFacade;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinal;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalConsenso;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.InconsistenciaArquivosException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.ReamostragemException;

/**
 * Classe responsável por converter os dados de cisternas para enviar para balhid.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class BasicCisternas {
	
	/**
	 * Lista de precipitações do arquivo de PMH de acordo com sua coordenada.
	 */
	private Map<Coordenadas, Map<String, Double>> mapPrecips;

	/**
	 * Lista de parâmetros do arquivo de Parâmetros.
	 */
	private Map<Coordenadas, Parametros> listaParametros;
	
	/**
	 * Lista de períodos iniciais e finais de acordo com a coordenada.
	 */
	private Map<Coordenadas, PeriodosIniciais> periodosIniciais;
	
	/**
	 * Variável que representa a porcentagem inicial do volume da cisterna.
	 */
	private Double volumeInicial;
	
	/**
	 * Variável que representa os dados do arquivo de peso período.
	 */
	private PesoPeriodo pesoPeriodo;
	
	/**
	 * Variável que representa a entidade de cenários.
	 */
	private Cenarios cenarios;
	
	/**
	 * Argumento para o tipo de execução.
	 */
	private String argumento;

	private String detalhes;
	
	public BasicCisternas() {
		
	}
	
	/**
	 * @param mapPrecips
	 */
	public BasicCisternas(
			Double volumeInicial, String argumento,
			Map<Coordenadas, Map<String, Double>> mapPrecips,
			Map<Coordenadas, Parametros> listaParametros,
			Map<Coordenadas, PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios, String detalhes) {
		
		this.volumeInicial = volumeInicial;
		this.mapPrecips = mapPrecips;
		this.listaParametros = listaParametros;
		this.periodosIniciais = periodos;
		this.pesoPeriodo = pesoPeriodo;
		this.cenarios = cenarios;
		this.argumento = argumento;
		this.detalhes = detalhes;
	}

	/**
	 * @return the argumento
	 */
	public String getArgumento() {
		return argumento;
	}
	
	/**
	 * @return the detalhes
	 */
	public String getDetalhes() {
		return detalhes;
	}

	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(String argumento) {
		this.argumento = argumento;
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
	 * @return the periodosIniciais
	 */
	public Map<Coordenadas, PeriodosIniciais> getPeriodosIniciais() {
		return periodosIniciais;
	}

	/**
	 * @param periodosIniciais the periodosIniciais to set
	 */
	public void setPeriodosIniciais(
			Map<Coordenadas, PeriodosIniciais> periodosIniciais) {
		this.periodosIniciais = periodosIniciais;
	}

	/**
	 * @return the listaParametros
	 */
	public Map<Coordenadas, Parametros> getListaParametros() {
		return listaParametros;
	}

	/**
	 * @param listaParametros the listaParametros to set
	 */
	public void setListaParametros(Map<Coordenadas, Parametros> listaParametros) {
		this.listaParametros = listaParametros;
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
	 * Método que converte os dados de cisternas para a entrada do balhid.
	 * @return 
	 * @throws Exception 
	 */
	public ResultadoFinalConsenso executeComConsenso() throws Exception {
		
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> parametros = Cisternas2BalHid.parse2Config( this.listaParametros );
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos = Cisternas2BalHid.parse2Periodos( this.periodosIniciais );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo pesoPeriodo = Cisternas2BalHid.parse2PesoPeriodo( this.pesoPeriodo );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios cenarios = Cisternas2BalHid.parse2Cenarios( this.cenarios );
		
		CisternasBalHidFacade facadeBalHid = CisternasBalHidFacade.getInstance();
		ResultadoFinalConsenso resultados = facadeBalHid.rodarBalHidComConsenso(this.volumeInicial, this.argumento, this.mapPrecips, parametros, periodos, pesoPeriodo, cenarios);
		
		return resultados;
	}
	
	/**
	 * Método que converte os dados de cisternas para a entrada do balhid, quando 
	 * se executa a aplicação com cenários.
	 * @return 
	 * @throws Exception 
	 */
	public Map<ResultadosCenarios, ResultadoFinalCompleto> executeCenariosCompleto() throws Exception {
		
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> parametros = Cisternas2BalHid.parse2Config(this.listaParametros);
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos = Cisternas2BalHid.parse2Periodos( this.periodosIniciais );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo pesoPeriodo = Cisternas2BalHid.parse2PesoPeriodo( this.pesoPeriodo );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios cenarios = Cisternas2BalHid.parse2Cenarios( this.cenarios );
		
		CisternasBalHidFacade facadeBalHid = CisternasBalHidFacade.getInstance();
		Map<ResultadosCenarios, ResultadoFinalCompleto> resultados = facadeBalHid.rodarBalHidCenariosCompleto(this.volumeInicial, this.argumento, this.mapPrecips, parametros, periodos, pesoPeriodo, cenarios);
		
		return resultados;
	}
	
	/**
	 * Método que converte os dados de cisternas para a entrada do balhid, quando
	 * se executa a aplicação de forma normal.
	 * @return
	 * @throws Exception 
	 */
	public Map<Coordenadas, ResultadosBalHid> executeNormal() throws Exception {
		
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> parametros = Cisternas2BalHid.parse2Config(this.listaParametros);
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos = Cisternas2BalHid.parse2Periodos( this.periodosIniciais );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo pesoPeriodo = Cisternas2BalHid.parse2PesoPeriodo( this.pesoPeriodo );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios cenarios = Cisternas2BalHid.parse2Cenarios( this.cenarios );
		
		Map<Coordenadas, ResultadosBalHid> resultado = new HashMap<Coordenadas, ResultadosBalHid>();
		CisternasBalHidFacade facadeBalHid = CisternasBalHidFacade.getInstance();
		
		resultado = facadeBalHid.rodarBalHidNormal(this.volumeInicial, this.argumento, this.mapPrecips, parametros, periodos, pesoPeriodo, cenarios, detalhes);
		
		return resultado;
	}
	
	/**
	 * Método que converte os dados de cisternas para a entrada do balhid, quando
	 * se executa a aplicação com variação de cenários, sem o consenso.
	 * @return
	 * @throws Exception 
	 */
	public Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> executeVariacao() throws Exception {
		
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> parametros = Cisternas2BalHid.parse2Config(this.listaParametros);
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos = Cisternas2BalHid.parse2Periodos( this.periodosIniciais );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo pesoPeriodo = Cisternas2BalHid.parse2PesoPeriodo( this.pesoPeriodo );
		br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios cenarios = Cisternas2BalHid.parse2Cenarios( this.cenarios );
		
		Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> resultado = new HashMap<ResultadosCenarios, Map<Coordenadas,ResultadosBalHid>>();
		CisternasBalHidFacade facadeBalHid = CisternasBalHidFacade.getInstance();
		resultado = facadeBalHid.rodarBalHidComVariacao(this.volumeInicial, this.argumento, this.mapPrecips, parametros, periodos, pesoPeriodo, cenarios, detalhes);
		
		return resultado;
	}
	
	/**
	 * Método que realiza a montagem do arquivo de saída.
	 * @param basic
	 * @param result
	 * @param output_file
	 * @throws IOException 
	 */
	public void montarResultadosComConsenso(Map<Coordenadas, BasicCisternas> basic,
			ArrayList<ResultadoFinalConsenso> result, String output_file) throws IOException {
		
		MontarResultados montarResultados = new MontarResultados(basic, result, output_file);
		montarResultados.montarResultadosComConsenso();
	}

	/**
	 * Método que realiza a montagem dos arquivos de saída quando executa com cenários.
	 * @param basic
	 * @param result
	 * @param output_file
	 * @throws IOException
	 */
	public void montarResultadosComCenariosCompleto(Map<Coordenadas, BasicCisternas> basic,
			Map<ResultadosCenarios, ResultadoFinalCompleto> result, String output_file) throws IOException {
		
		MontarResultados montarResultados = new MontarResultados(basic, result, output_file);
		montarResultados.montarResultadosComCenariosCompleto();
	}
	
	/**
	 * Método que realiza a montagem dos arquivos de saída quando executa de forma normal.
	 * @param basic
	 * @param result
	 * @param output_file
	 * @throws IOException 
	 */
	public void montarResultadosNormal(Map<Coordenadas, BasicCisternas> basic,
			Map<Coordenadas, ResultadosBalHid> result, String output_file) throws IOException {
		
		MontarResultados resultados = new MontarResultados(basic, output_file, result);
		resultados.montarResultadosNormal();
	}

	public void montarResultadosComVariacao(Map<Coordenadas, BasicCisternas> basic,
			Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> result,
			String output_file) throws IOException {
		
		MontarResultados resultados = new MontarResultados(output_file, result, basic);
		resultados.montarResultadosComVariacao();
		
	}



}
