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

package br.edu.ufcg.lsd.seghidro.cisternasbalhid.control;

import java.util.GregorianCalendar;
import java.util.Map;

import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Precipitacao;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinal;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalConsenso;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.InconsistenciaArquivosException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.ReamostragemException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.model.Calculador;

/**
 * Classe responsável por receber os dados da aplicação cisternas para o cálculo do
 * balhid.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class CisternasBalHidFacade {

	private static CisternasBalHidFacade instance = null;

	/**
	 * Retorna uma instância de Facade. Foi utilizado o padrão Singleton
	 * 
	 * @return instância da classe.
	 */
	public static CisternasBalHidFacade getInstance() {
		if (instance == null)
			instance = new CisternasBalHidFacade();
		return instance;
	}

	/**
	 * Método que executa o balanço hídrico.
	 * @param precipitacao
	 * @param colecaoParametros
	 * @throws Exception 
	 */
	public ResultadoFinalConsenso rodarBalHidComConsenso(
			Double volumeInicial, String argumento,
			Map<Coordenadas, Map<String, Double>> mapPrecipitacoes,
			Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> colecaoParametros,
			Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios) throws Exception {
		
		Calculador calculador = new Calculador(volumeInicial, argumento, mapPrecipitacoes, colecaoParametros, periodos, pesoPeriodo, cenarios, "");
		
		calculador.calcularPrevisao();
		
		ResultadoFinalConsenso resultado = new ResultadoFinalConsenso(calculador.getMapPonderacaoDiasConsecutivosConsenso(), calculador.getMapResultadoFrequencia());
		
		// lista com os resultados do balanço hídrico.
		return resultado;
	}
	
	/**
	 * Método que executa o balanço hídrico com cenários.
	 * @param precipitacao
	 * @param colecaoParametros
	 * @throws Exception 
	 */
	public Map<ResultadosCenarios, ResultadoFinalCompleto> rodarBalHidCenariosCompleto(
			Double volumeInicial, String argumento,
			Map<Coordenadas, Map<String, Double>> mapPrecipitacoes,
			Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> colecaoParametros,
			Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios) throws Exception {
		
		Calculador calculador = new Calculador(volumeInicial, argumento, mapPrecipitacoes, colecaoParametros, periodos, pesoPeriodo, cenarios, "");
		
		calculador.calcularPrevisao();
		
		Map<ResultadosCenarios, ResultadoFinalCompleto> resultado = calculador.getMapResultadoCenarios();
		
		// lista com os resultados do balanço hídrico executados com cenários.
		return resultado;
	}

	/**
	 * Método que executa o balanço hídrico de forma normal.
	 * @param volumeInicial
	 * @param mapPrecips
	 * @param parametros
	 * @param periodos
	 * @param pesoPeriodo
	 * @param cenarios
	 * @return
	 * @throws Exception 
	 */
	public Map<Coordenadas, ResultadosBalHid> rodarBalHidNormal(
			Double volumeInicial, String argumento,
			Map<Coordenadas, Map<String, Double>> mapPrecips,
			Map<Coordenadas, Parametros> parametros,
			Map<Coordenadas, PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios, String detalhes) throws Exception {
		
		Calculador calculador = new Calculador(volumeInicial, argumento, mapPrecips, parametros, periodos, pesoPeriodo, cenarios, detalhes);
		
		calculador.calcularPrevisao();
		
		Map<Coordenadas, ResultadosBalHid> resultado = calculador.getMapResultadosBalHid();
		
		return resultado;
	}

	/**
	 * Método que executa o balanço hídrico com variação de parâmetros, sem o 
	 * consenso.
	 * @param volumeInicial
	 * @param argumento
	 * @param mapPrecips
	 * @param parametros
	 * @param periodos
	 * @param pesoPeriodo
	 * @param cenarios
	 * @param detalhes 
	 * @return
	 * @throws Exception 
	 */
	public Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> rodarBalHidComVariacao(
			Double volumeInicial, String argumento,
			Map<Coordenadas, Map<String, Double>> mapPrecips,
			Map<Coordenadas, Parametros> parametros,
			Map<Coordenadas, PeriodosIniciais> periodos,
			PesoPeriodo pesoPeriodo, Cenarios cenarios, String detalhes) throws Exception {
		
		Calculador calculador = new Calculador(volumeInicial, argumento, mapPrecips, parametros, periodos, pesoPeriodo, cenarios, detalhes);
		
		calculador.calcularPrevisao();
		
		Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> resultado = calculador.getMapResultadoVariacao();
		
		return resultado;
		
	}
	
}
