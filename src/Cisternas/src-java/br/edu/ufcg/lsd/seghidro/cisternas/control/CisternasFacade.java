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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.input.CenariosReader;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.input.ParametrosReader;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.input.PesoPeriodoReader;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.input.PrecipitacaoReader;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.ParametrosReaderException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinal;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalConsenso;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.InconsistenciaArquivosException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.ReamostragemException;

/**
 * Classe responsável por repassar as informações de path dos arquivos para
 * serem lidos.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class CisternasFacade {
	
	private static CisternasFacade instance = null;
	
	/** guarda dados de somente balanço hídrico. */
	private Map<Coordenadas, ResultadosBalHid> resultadoNormal = new HashMap<Coordenadas, ResultadosBalHid>();

	/** guarda dados de resultados de variação. */
	private Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> resultadoVariacao = new HashMap<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>>();
	
	/** guarda dados de resultados de consenso. */
	private ArrayList<ResultadoFinalConsenso> resultadoConsenso = new ArrayList<ResultadoFinalConsenso>();
	
	/** guarda dados de resultados de completo. */
	private Map<ResultadosCenarios, ResultadoFinalCompleto> resultadoCompleto = new HashMap<ResultadosCenarios, ResultadoFinalCompleto>();
	
	/** guarda a lista de basic */
	private Map<Coordenadas, BasicCisternas> listaBasic = new HashMap<Coordenadas, BasicCisternas>();
	
	private static ArrayList<String> listaPmh = new ArrayList<String>();
	
	private String nomePmhCorrente = "";
	private String path = "";
	
	/**
	 * Retorna uma instância de Facade. Foi utilizado o padrão Singleton
	 * 
	 * @return instância da classe.
	 */
	public static CisternasFacade getInstance() {
		if (instance == null)
			instance = new CisternasFacade();
		return instance;
	}
	
	/**
	 * Método que faz a leitura dos arquivos de entrada e executa o balanço hídrico.
	 * @param volumeInicial
	 * @param tipoExecucao
	 * @param precipitacao_file
	 * @param parametros_file
	 * @param pesoPeriodo_file
	 * @param output_file
	 * @param cenarios_file
	 * @param executarGrid
	 * @throws Exception 
	 */
	public void rodarCisternas(Double volumeInicial, String tipoExecucao, String precipitacao_file,
			String parametros_file, String pesoPeriodo_file, 
			String output_file, String cenarios_file, String detalhes)
			throws Exception {
			
			String pathCorrente="";
			
			if(precipitacao_file.contains("$")){
				
				listaPmh = obtemPmh(precipitacao_file);
				pathCorrente = this.path;
				
				for (int i = 0; i < listaPmh.size(); i++) {
					
					nomePmhCorrente = listaPmh.get(i).replace(".pmh", "");
					nomePmhCorrente = nomePmhCorrente.substring(pathCorrente.length());
					
					this.paralelizarAplicacao(volumeInicial, tipoExecucao, listaPmh.get(i), parametros_file, pesoPeriodo_file, output_file+nomePmhCorrente+".txt", cenarios_file, detalhes);
				}
				
			}else{
				this.paralelizarAplicacao(volumeInicial, tipoExecucao, precipitacao_file, parametros_file, pesoPeriodo_file, output_file, cenarios_file, detalhes);
			}
			
	        
	}
	
	private ArrayList<String> obtemPmh(String precipitacao_file) {
		
			ArrayList<String> listaComPmhsEsperados = null;
			String subStr = "";
			String path = "";
			int pre = precipitacao_file.indexOf("$");
			
			String fileSeparator = System.getProperty("file.separator").trim();
			
			
			if(precipitacao_file.contains("\\") || precipitacao_file.contains("/")){
				
				
				while(!("\\").equalsIgnoreCase(""+precipitacao_file.charAt(pre-1)) &&  !("/").equalsIgnoreCase(""+precipitacao_file.charAt(pre-1))){
					pre = pre -1;
				}
				path = precipitacao_file.substring(0, pre);
				this.path = path;
			}
			
			subStr = precipitacao_file.substring(pre);
			
			if(subStr.contains(".")){
				subStr = (String) subStr.subSequence(0, subStr.indexOf("."));
			}
			
			int pre1;
			int pre2;
			//tratar prefixo
			if(subStr.contains("$")){
				pre1 = subStr.indexOf("$");
				pre2 = subStr.lastIndexOf("$");
				
				if(pre1==pre2){
					//segue
				}else{
					subStr = subStr.substring(pre1+1, pre2);
				}
			}
			
			
			final File DIRETORIO_PADRAO = new File(path);
			File[] todosArquivosFile = DIRETORIO_PADRAO.listFiles();
			String[] todosArquivosString = new String[todosArquivosFile.length];
			
			for(int i=0; i < todosArquivosFile.length;i++){
				todosArquivosString[i] = todosArquivosFile[i].toString();
			}
					
			listaComPmhsEsperados = obtemArquivosPMH(todosArquivosString, subStr, path);
			if (listaComPmhsEsperados.size() == 0){
				try {
					throw new Exception("Nao existe(m) arquivo(s) pmh com o nome, prefixo ou sufixo passado como parametro.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.exit(1);
				}
			}
			
			return listaComPmhsEsperados;
	}

	private ArrayList<String> obtemArquivosPMH(String[] arquivos,
			String subStr, String path) {
		
			ArrayList<String> pmhsEsperados = new ArrayList<String>();
			
			String aux = subStr;
			String arquivoStringAux="";
			for (int j = 0; j < arquivos.length; j++){
				
				arquivoStringAux = arquivos[j];
				if(arquivos[j].contains(".")){
					arquivoStringAux = (String) arquivos[j].subSequence(0, arquivos[j].indexOf("."));
				}
				
				if(aux.contains("$")){
					
					if(aux.indexOf("$")>0){
						//pega com prefixo
						subStr = aux.substring(0, aux.indexOf("$"));
						
						if (arquivoStringAux.substring(path.length()).contains(subStr)){
							//garante que nao pega um arquivo que possui a string "prefixo" no meio do nome
							
							if(arquivoStringAux.substring(path.length()).startsWith(subStr)){
								pmhsEsperados.add(arquivos[j]);
							}
						}
					}else{
						//pega com sufixo
						subStr = aux.replace("$", "");
						
						if (arquivoStringAux.substring(path.length()).contains(subStr)){
							//garante que nao pega um arquivo que possui a string "prefixo" no meio do nome
							
							if(arquivoStringAux.substring(path.length()).endsWith(subStr)){
								pmhsEsperados.add(arquivos[j]);
							}
						}
					}
				}else{
					if (arquivoStringAux.substring(path.length()).contains(subStr)){
							pmhsEsperados.add(arquivos[j]);
						}
					}
				}
			return pmhsEsperados;
	}

	public void rodarCisternas(Double volumeInicial, String tipoExecucao, String precipitacao_file,
			String parametros_file, String pesoPeriodo_file, 
			String output_file, String cenarios_file)
			throws Exception {
		
			String detalhes = "";
			String pathCorrente="";
			
			if(precipitacao_file.contains("$")){
				
				listaPmh = obtemPmh(precipitacao_file);
				pathCorrente = this.path;
				
				for (int i = 0; i < listaPmh.size(); i++) {
					
					nomePmhCorrente = listaPmh.get(i).replace(".pmh", "");
					nomePmhCorrente = nomePmhCorrente.substring(pathCorrente.length());
					
					this.paralelizarAplicacao(volumeInicial, tipoExecucao, listaPmh.get(i), parametros_file, pesoPeriodo_file, output_file+nomePmhCorrente+".txt", cenarios_file, detalhes);
				}
				
			}else{
				this.paralelizarAplicacao(volumeInicial, tipoExecucao, precipitacao_file, parametros_file, pesoPeriodo_file, output_file, cenarios_file, detalhes);
			}
	}

	/**
	 * Método que irá paralelizar os arquivos de entrada para não ocorrer estouro
	 * de memória caso os arquivos de entrada sejam grandes demais.
	 * @throws Exception 
	 */
	private void paralelizarAplicacao(Double volumeInicial,
			String tipoExecucao, String precipitacao_file,
			String parametros_file, String pesoPeriodo_file,
			String output_file, String cenarios_file, String detalhes)
			throws Exception {
		
			
		// leitura dos arquivos de entrada.
		Map<Coordenadas, Parametros> listaParametros = (new ParametrosReader(parametros_file)).getParametros();
		PrecipitacaoReader precipitacaoReader = new PrecipitacaoReader(precipitacao_file);
		Map<Coordenadas, Map<String, Double>> precip_map = new HashMap<Coordenadas, Map<String,Double>>();
		
		
		// para cada cisterna
		for (Coordenadas coordenada : listaParametros.keySet()) {
			
			try {
				precip_map = precipitacaoReader.read();

				// leitura dos arquivos de entrada.
				Map<Coordenadas, PeriodosIniciais> periodosIniciais = precipitacaoReader
						.getMapPeriodosIniciais();
				
				PesoPeriodo pesoPeriodo = (new PesoPeriodoReader(pesoPeriodo_file))
						.getPesoPeriodo();

				Cenarios cenarios;

				// se o arquivo de cenários for passado faz-se a leitura.
				if (cenarios_file == "") {
					cenarios = null;
				} else {
					cenarios = (new CenariosReader(cenarios_file)).getCenario();
				}
				BasicCisternas basic = new BasicCisternas(volumeInicial,
						tipoExecucao, precip_map, listaParametros,
						periodosIniciais, pesoPeriodo, cenarios, detalhes);

				if (tipoExecucao.equals("normal")) {
					
					// executa somente o balanço hídrico
					Map<Coordenadas, ResultadosBalHid> resultado = basic.executeNormal(); 

					
					if (this.resultadoNormal.isEmpty()) {
						this.resultadoNormal.putAll(resultado);
					} else {
						for (Coordenadas coord : resultado.keySet()) {
							this.resultadoNormal.put(coord, resultado.get(coord));
						}
					}

				} else if (tipoExecucao.equals("variacao")) {

					if (cenarios != null) {
						// executando cálculos
						Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> result = basic.executeVariacao();
						// armazenando resultados
						if (this.resultadoVariacao.isEmpty()) {
							this.resultadoVariacao.putAll(result);
						} else {
							for (ResultadosCenarios resultados : result.keySet()) {
								this.resultadoVariacao.put(resultados, result.get(resultados));
							}
						}
						
					} else {
						throw new InconsistenciaArquivosException(
								"É preciso enviar o arquivo de cenários para esse tipo de execução!");
					}

				} else if (tipoExecucao.equals("consenso")) {
					// executando cálculos com previsão de consenso
					ResultadoFinalConsenso result = basic.executeComConsenso();
					// armazenando resultados
					this.resultadoConsenso.add(result);
			

				} else if (tipoExecucao.equals("completo")) {

					if (cenarios != null) {
						// executando cálculos
						Map<ResultadosCenarios, ResultadoFinalCompleto> result = basic.executeCenariosCompleto();
						// armazenando resultados
						if (this.resultadoCompleto.isEmpty()) {
							this.resultadoCompleto.putAll(result);
						} else {
							for (ResultadosCenarios resultados : result.keySet()) {
								this.resultadoCompleto.put(resultados, result.get(resultados));
							}
						}	
					} else {
						throw new InconsistenciaArquivosException("É preciso enviar o arquivo de cenários para esse tipo de execução!");
					}
				}
				
				this.listaBasic.put(coordenada, basic);
				
			} catch (CisternasReaderException e) {
				e.printStackTrace();
			} catch (CisternasMissingFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} //fim for
		
		if (tipoExecucao.equals("normal")) {
			BasicCisternas basic = new BasicCisternas();
			basic.montarResultadosNormal(this.listaBasic, this.resultadoNormal,
					output_file);
			
		} else if (tipoExecucao.equals("variacao")) {
			BasicCisternas basic = new BasicCisternas();
			basic.montarResultadosComVariacao(this.listaBasic, this.resultadoVariacao,
					output_file);
			
		} else if (tipoExecucao.equals("consenso")) {
			BasicCisternas basic = new BasicCisternas();
			basic.montarResultadosComConsenso(this.listaBasic, this.resultadoConsenso,
					output_file);
			
		} else if (tipoExecucao.equals("completo")) {
			BasicCisternas basic = new BasicCisternas();
			basic.montarResultadosComCenariosCompleto(this.listaBasic,
					this.resultadoCompleto, output_file);
			
		}
		
	} //fim do método

}
