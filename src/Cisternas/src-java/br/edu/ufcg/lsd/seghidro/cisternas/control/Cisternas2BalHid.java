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

import java.util.HashMap;
import java.util.Map;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Precipitacao;

/**
 * Classe responsável por realizar a conversão dos objetos para o balhid.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Cisternas2BalHid {
	
	/**
	 * Construtor vazio.
	 */
	public Cisternas2BalHid(){
	}
	
	/**
	 * Pega as informações dos arquivos de solo e cultura para gerar um arquivo de 
	 * configuração nos moldes do BalHid.
	 * @param solo
	 * @param cultura
	 * @return
	 */
	public static Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> parse2Config(
			Map<Coordenadas, Parametros> par) {

		// coleção da lista de parametros
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros> lista = new HashMap<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros>();

		for (Coordenadas coordenadas : par.keySet()) {
			br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros parBalHid = new br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Parametros();

			parBalHid.setAreaCapitacao(par.get(coordenadas).getAreaCapitacao());
			parBalHid.setCapacidade(par.get(coordenadas).getCapacidade());
			
			
			parBalHid.setDemanda(par.get(coordenadas).getDemanda());
			
			parBalHid.setDescricao(par.get(coordenadas).getDescricao());
			parBalHid.setPerdas(par.get(coordenadas).getPerdas());

			lista.put(coordenadas, parBalHid);
		}

		return lista;
	}
	
	/**
	 * Método que realiza a conversão das precipitações de cisternas para os
	 * cálculos.
	 * 
	 * @param p
	 * @return
	 */
//	public static Map<Coordenadas, Map<String, Double>> parse2Precip(
//			Map<Coordenadas, Map<String, Double>> p) {
//		
//		Precipitacao precip = new Precipitacao();
//		precip.setPrec(p.getPrec());
//		return precip;
//	}

	/**
	 * Método que realiza a conversão do objeto de períodos iniciais e finais.
	 * @param periodosIniciais
	 * @return
	 */
	public static Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> parse2Periodos(
			Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternas.entities.PeriodosIniciais> periodosIniciais) {
		
		Map<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais> lista = new HashMap<Coordenadas, br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais>();
		
		for (Coordenadas coordenadas : periodosIniciais.keySet()) {
			br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais periodos = new br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.PeriodosIniciais();
			
			periodos.setCoordenada(coordenadas);
			periodos.setDataInicial(periodosIniciais.get(coordenadas).getDataInicial());
			periodos.setDataFinal(periodosIniciais.get(coordenadas).getDataFinal());
			
			lista.put(coordenadas, periodos);
		}
		
		return lista;
	}

	/**
	 * Método que converte o objeto de peso período para o balhid.
	 * @param pesoPeriodo
	 * @return objeto de peso período convertido.
	 */
	public static PesoPeriodo parse2PesoPeriodo(
			br.edu.ufcg.lsd.seghidro.cisternas.entities.PesoPeriodo pesoPeriodo) {
		
		PesoPeriodo peso = new PesoPeriodo();
		
		peso.setAll(pesoPeriodo.getMesInicialChuvoso(), pesoPeriodo
						.getMesFinalChuvoso(), pesoPeriodo.getPeriodoSeco(),
						pesoPeriodo.getPeriodoNormal(), pesoPeriodo
								.getPeriodoChuvoso());
		
		return peso;
	}

	/**
	 * Método que converte o objeto de cenários para o formato do balhid.
	 * @param cenarios
	 * @return
	 */
	public static Cenarios parse2Cenarios(
			br.edu.ufcg.lsd.seghidro.cisternas.entities.Cenarios cenarios) {
		
		Cenarios cenario;
		if (cenarios == null) {
			cenario = new Cenarios();
		} else {
			cenario = new Cenarios();
			cenario.setAreaCaptacao(cenarios.getAreaCaptacao());
			cenario.setDemanda(cenarios.getDemanda());
			cenario.setVolume(cenarios.getVolume());
		}
		
		return cenario;
	}

}
