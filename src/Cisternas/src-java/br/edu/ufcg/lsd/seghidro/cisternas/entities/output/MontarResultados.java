package br.edu.ufcg.lsd.seghidro.cisternas.entities.output;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import br.edu.ufcg.lsd.seghidro.cisternas.control.BasicCisternas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalConsenso;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.util.Parser;

/**
 * Esta classe é responsável pelo armazenamento dos resultados gerados pela
 * Aplicação Cisternas em formato ASCII.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 */
public class MontarResultados {

	/**
	 * Variável responsável por finalizar uma linha no arquivo de saída.
	 */
	private static final String FIM_DE_LINHA = System
			.getProperty("line.separator");

	private String fileOut;

	private OutputManager outputManager;
	
	private ArrayList<ResultadoFinalConsenso> resultadoConsenso;
	
	private Map<ResultadosCenarios, ResultadoFinalCompleto> resultadoCenarios;
	
	/**
	 * Variável quando se executa de forma normal.
	 */
	private Map<Coordenadas, ResultadosBalHid> resultadosNormais;
	
	/**
	 * Variável quando se executa com variação de parâmetros.
	 */
	private Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> resultadosVariacao;
	
	private Map<Coordenadas, BasicCisternas> basic;

	/**
	 * Construtor Consenso.
	 * 
	 * @param hidro
	 * @param fileOut
	 */
	public MontarResultados(Map<Coordenadas, BasicCisternas> basic, ArrayList<ResultadoFinalConsenso> result, String output_file) {

		this.resultadoConsenso = result;
		this.basic = basic;
		this.fileOut = output_file;
		this.outputManager = new OutputManagerASCII(output_file);
	}

	/**
	 * Construtor quando executado com cenários. Completo.
	 * @param basic
	 * @param result
	 * @param output_file
	 */
	public MontarResultados(Map<Coordenadas, BasicCisternas> basic,
			Map<ResultadosCenarios, ResultadoFinalCompleto> result, String output_file) {
		
		this.fileOut = output_file;
		this.basic = basic;
		this.resultadoCenarios = result;
	}
	
	/**
	 * Construtor quando se executa a aplicação de forma normal. Somente o balanço hídrico.
	 * @param basic
	 * @param result
	 * @param output_file
	 */
	public MontarResultados(Map<Coordenadas, BasicCisternas> basic, String output_file,
			Map<Coordenadas, ResultadosBalHid> result) {
		
		System.out.println(output_file);
		
		this.fileOut = output_file;
		this.resultadosNormais = result;
		this.basic = basic;
		this.outputManager = new OutputManagerASCII(output_file);
	}

	/**
	 * Construtor quando se executa a aplicação com variação de parâmetros, sem
	 * o consenso.
	 * @param basic2
	 * @param output_file
	 * @param result2
	 */
	public MontarResultados(String output_file, Map<ResultadosCenarios, Map<Coordenadas, ResultadosBalHid>> result,
			Map<Coordenadas, BasicCisternas> basic ) {
		
		this.fileOut = output_file;
		this.resultadosVariacao = result;
		this.basic = basic;
	}

	/**
	 * Formata um String com o tamanho desejado, completando com espaços em
	 * branco
	 * 
	 * @param size
	 *            Tamanho do String desejado
	 * @param value
	 *            String a ser formatado
	 * @return O String do tamanho desejado
	 */
	private String format(int size, String value) {
		String buffer = "                         ";
		int length = size - value.length();
		return value + buffer.subSequence(0, length);
	}

	/**
	 * Método que monta o arquivo de saída sem cálculos com cenários.
	 * 
	 * @param volumeMensal
	 * @param demanda
	 * @param nomeReservatorio
	 * @throws IOException
	 */
	public void montarResultadosComConsenso() throws IOException {

		String line = "";
		
		outputManager.createFile();
		System.out.println("--- Gravando os resultados... ---");
		
		ArrayList<ResultadoFinalConsenso> resultadoConsenso = this.resultadoConsenso;
		
		boolean ehPrimeiro = true;
		
		// para cada resultado do consenso.
		for (ResultadoFinalConsenso resultadoFinalConsenso : resultadoConsenso) {

			Map<Coordenadas, Double> resultado = resultadoFinalConsenso.getDiasConsecutivos();
			
			// lista de frequencia estimada
			Map<Coordenadas, Map<String, Double>> resultadoFinal = resultadoFinalConsenso
					.getResultadoFrequenciaEstimada();

			// para cada coordenada
			for (Coordenadas coordenadas : resultadoFinal.keySet()) {

				if (ehPrimeiro) {
					ehPrimeiro = false;
					line += "!Previsão Climática: seco "
							+ this.basic.get(coordenadas).getPesoPeriodo()
									.getPeriodoSeco() * 100 + "%;";
					line += "normal "
							+ this.basic.get(coordenadas).getPesoPeriodo()
									.getPeriodoNormal() * 100 + "%;";
					line += "chuvoso "
							+ this.basic.get(coordenadas).getPesoPeriodo()
									.getPeriodoChuvoso() * 100 + "%"
							+ FIM_DE_LINHA;
					line += "!Volume inicial: "
							+ this.basic.get(coordenadas).getVolumeInicial()
							+ "%" + FIM_DE_LINHA;
					line += FIM_DE_LINHA;
					line += "!Latitude;Longitude;Descrição;0-10;10-20;20-30;30-40;40-50;50-60;60-70;70-80;80-90;90-100;DiasDeficitConsecutivos"
							+ FIM_DE_LINHA;
				}

				String descricao = this.basic.get(coordenadas)
						.getListaParametros().get(coordenadas).getDescricao();
				Map<String, Double> resultado2 = resultadoFinal
						.get(coordenadas);

				line += coordenadas.getCoordX() + ";" + coordenadas.getCoordY()
						+ ";";
				line += descricao + ";";

				// para cada amostra
				for (String amostra : resultado2.keySet()) {

					line += resultado2.get(amostra) + ";";
				}

				line += resultado.get(coordenadas).intValue();
				line += FIM_DE_LINHA;
			}
			
		} //fim do for
		outputManager.writeLine(line);
		outputManager.closeFile();
		
		System.out.println("--- Finalizado! ---");
	}

	/**
	 * Método que realiza a montagem dos arquivos de resultados executados com 
	 * cenários.
	 * @throws IOException 
	 */
	public void montarResultadosComCenariosCompleto() throws IOException {
		
		String line = "";
		System.out.println("--- Gravando os resultados... ---");
		
		// Cria o arquivo zip
		String zipFileName = this.fileOut + ".zip";
		
		// lista com os nomes dos arquivos de os resultados
		Map<String, String> mapLine = new HashMap<String, String>();
		
		// para cada cenário gerado
		for (ResultadosCenarios cenarios : this.resultadoCenarios.keySet()) {

			// serve como cabeçalho, mas no momento não pode ser separado.
			
			//TODO ajeitar isso aqui abaixo
//			line += "!Previsão Climática: seco " + this.basic.getPesoPeriodo().getPeriodoSeco() * 100 + "%;";
//			line += "normal " + this.basic.getPesoPeriodo().getPeriodoNormal() * 100 + "%;";
//			line += "chuvoso " + this.basic.get()//getPesoPeriodo().getPeriodoChuvoso() * 100 + "%" + FIM_DE_LINHA;
//			line += "!Volume inicial: " + this.basic.getVolumeInicial() + "%" + FIM_DE_LINHA;
			
			line += "!Área de Captação: " + cenarios.getAreaCapitacao() + FIM_DE_LINHA;
			line += "!Volume da cisterna: " + cenarios.getVolume() + FIM_DE_LINHA;
			line += "!Demanda: " + cenarios.getDemanda() + FIM_DE_LINHA;
			
			line += FIM_DE_LINHA;
			
			line += "!Latitude;Longitude;Descrição;0-10;10-20;20-30;30-40;40-50;50-60;60-70;70-80;80-90;90-100;DiasDeficitConsecutivos" + FIM_DE_LINHA;
			
			
			// nome do arquivo de saida de acordo com o cenário executado.
			String saida = this.fileOut + "-acap" + cenarios.getAreaCapitacao()
					+ "-vol" + cenarios.getVolume() + "-dem"
					+ cenarios.getDemanda() + ".txt";

			Map<Coordenadas, Map<String, Double>> mapFrequencia = this.resultadoCenarios.get(cenarios).getMapResultadoFrequencia();
			Map<Coordenadas, Map<String, ResultadosBalHid>> resultadoBalhidCompleto = this.resultadoCenarios.get(cenarios).getMapResultadosBalHidConsenso();
			Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso = this.resultadoCenarios.get(cenarios).getMapPonderacaoDiasConsecutivosConsenso();
			
			// para cada coordenada
			for (Coordenadas coordenadas : mapFrequencia.keySet()) {
				
				Map<String, ResultadosBalHid> balhid = resultadoBalhidCompleto.get(coordenadas);
				String descricao = "";
				// laço para recuperar somente a localidade da coordenada.
				for (String anoCorrente : balhid.keySet()) {
					descricao = balhid.get(anoCorrente).getDescricao();
					break;
				}
				
				Map<String, Double> resultado2 = mapFrequencia.get(coordenadas);
				
				line += coordenadas.getCoordX() + ";" + coordenadas.getCoordY() + ";";
				line += descricao + ";";
				
				// para cada amostra
				for (String amostra: resultado2.keySet()) {
					
					line += resultado2.get(amostra) + ";";
				} //for
				
				
				Double valor = mapPonderacaoDiasConsecutivosConsenso.get(coordenadas);
				line += valor.intValue();
//				line += this.resultadoCenarios.get(coordenadas).getMapPonderacaoDiasConsecutivosConsenso().get(coordenadas);
				line += FIM_DE_LINHA;
				
			} //for
			
			// lista com o nome do arquivo e a string de resultados para ser gerado o zip
			mapLine.put(saida, line);
			line = "";
			
		} //for cenarios

		// método criar o zip e compactar todos os resultados obtidos em um só arquivo.
		this.compactarArquivos(zipFileName, mapLine);
		
		System.out.println("--- Arquivos gravados! ---");
		
	} //metodo
	
	/**
	 * Método que monta os resultados de forma normal. Quando se executa somente
	 * o balanço hídrico.
	 * @throws IOException 
	 */
	public void montarResultadosNormal() throws IOException {
		
		ArrayList<String> detalhesLista;
		String line = "";
		this.outputManager.createFile();
		System.out.println("--- Gravando os resultados... ---");
		
		line += "!Latitude;Longitude;Descricao;Acap;Capacidade;Dem;Perdas;VolumeInicial(%);Garantia(%);Deficit(%);DiasDeficitConsecutivos;DataInicioDosDiasDeficitConsecutivos" + FIM_DE_LINHA;
		
		// para cada coordenada
		for (Coordenadas coordenadas : this.resultadosNormais.keySet()) {
			line += FIM_DE_LINHA;
			line += coordenadas.getCoordX() + ";" + coordenadas.getCoordY() + ";";
			line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getDescricao() + ";";
			line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getAreaCapitacao() + ";";
			line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getCapacidade() + ";";
			line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getDemanda() + ";";
			line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getPerdas() + ";";
			line += this.basic.get(coordenadas).getVolumeInicial() + ";";
			
			line += Parser.roundDouble(2, this.resultadosNormais.get(coordenadas).getGarantia()) + ";";
			line += Parser.roundDouble(2, this.resultadosNormais.get(coordenadas).getDeficit()) + ";";
			line += this.resultadosNormais.get(coordenadas).getDef().getDiasConsecutivos() + ";";
			line += this.resultadosNormais.get(coordenadas).getDef().getDataInicialDeficit();
			
			detalhesLista = this.resultadosNormais.get(coordenadas).getDetalhesLista();
			
			if (detalhesLista.size() > 0){
				line += FIM_DE_LINHA;
				line += FIM_DE_LINHA;
				line += FIM_DE_LINHA;
				
				line += "Detalhes:";
				line += FIM_DE_LINHA;
				line += FIM_DE_LINHA;
				
				for (String element : this.resultadosNormais.get(coordenadas).getDetalhesLista()) {
					line += element.toString();
					line += FIM_DE_LINHA;
				}
			}

		} //for
		
		this.outputManager.writeLine(line);
		this.outputManager.closeFile();
		System.out.println("--- Finalizado! ---");
	}

	/**
	 * Método que monta os resultados com variação de parâmetros, sem o consenso.
	 * @throws IOException 
	 */
	public void montarResultadosComVariacao() throws IOException {
		
		ArrayList<String> detalhesLista;
		
		String line = "";
		System.out.println("--- Gravando os resultados... ---");
		
		// Cria o arquivo zip
		String zipFileName = this.fileOut + ".zip";
		
		// lista com os nomes dos arquivos de os resultados
		Map<String, String> mapLine = new HashMap<String, String>();
		
		// para cada cenário gerado
		for (ResultadosCenarios cenarios : this.resultadosVariacao.keySet()) {

			// serve como cabeçalho, mas no momento não pode ser separado.
			//TODO ajeitar isso abaixo
//			line += "!Volume inicial: " + this.basic.getVolumeInicial() + "%" + FIM_DE_LINHA;
			line += "!Área de Captação: " + cenarios.getAreaCapitacao() + FIM_DE_LINHA;
			line += "!Volume da cisterna: " + cenarios.getVolume() + FIM_DE_LINHA;
			line += "!Demanda: " + cenarios.getDemanda() + FIM_DE_LINHA;
			line += FIM_DE_LINHA;
			
			line += "!Latitude;Longitude;Descrição;Perdas;Garantia(%);Déficit(%);DiasDeficitConsecutivos;DataInícioDosDiasDéficitConsecutivos" + FIM_DE_LINHA;
			
			// nome do arquivo de saida de acordo com o cenário executado.
			String saida = this.fileOut + "-acap" + cenarios.getAreaCapitacao()
					+ "-vol" + cenarios.getVolume() + "-dem"
					+ cenarios.getDemanda() + ".txt";

			Map<Coordenadas, ResultadosBalHid> resultadoBalhid = this.resultadosVariacao.get(cenarios);
			
			
			
			// para cada coordenada
			for (Coordenadas coordenadas : resultadoBalhid.keySet()) {
				
				detalhesLista = resultadoBalhid.get(coordenadas).getDetalhesLista();
				String descricao = resultadoBalhid.get(coordenadas).getDescricao();
				
				// impressão das informações para cada cisterna
				line += coordenadas.getCoordX() + ";" + coordenadas.getCoordY() + ";";
				line += descricao + ";";
				line += this.basic.get(coordenadas).getListaParametros().get(coordenadas).getPerdas() + ";";
				line += Parser.roundDouble(2, resultadoBalhid.get(coordenadas).getGarantia()) + ";";
				line += Parser.roundDouble(2, resultadoBalhid.get(coordenadas).getDeficit()) + ";";
				line += resultadoBalhid.get(coordenadas).getDef().getDiasConsecutivos() + ";";
				line += resultadoBalhid.get(coordenadas).getDef().getDataInicialDeficit();
				line += FIM_DE_LINHA;
				
				
				
				if (detalhesLista.size() > 0){
					line += FIM_DE_LINHA;
					line += FIM_DE_LINHA;
					line += FIM_DE_LINHA;
					
					line += "Detalhes:";
					line += FIM_DE_LINHA;
					line += FIM_DE_LINHA;
					
					for (String element : detalhesLista) {
						line += element.toString();
						line += FIM_DE_LINHA;
					}
				}
				
			} //for

			// lista com o nome do arquivo e a string de resultados para ser gerado o zip
			mapLine.put(saida, line);
			line = "";
			
		} //for cenarios

		// método criar o zip e compactar todos os resultados obtidos em um só arquivo.
		this.compactarArquivos(zipFileName, mapLine);
		System.out.println("--- Arquivos gravados! ---");
		
	}

	/**
	 * Método para compactar os arquivos gerados por execuções de cenários.
	 * @param zipFileName
	 * @param mapBytes
	 */
	public void compactarArquivos(String zipFileName, Map<String, String> mapBytes) {

		byte b[] = new byte[512];

		try {

//			zipFileName="cisternas.zip";
			
			OutputStream out = new FileOutputStream(zipFileName);
			ZipOutputStream zout = new ZipOutputStream(out);

			for (String fileName : mapBytes.keySet()) {
				InputStream in = new ByteArrayInputStream(mapBytes.get(fileName).getBytes());
				ZipEntry e = new ZipEntry(fileName);
				zout.putNextEntry(e);
				int len = 0;
				
				while ((len = in.read(b)) != -1) {
					zout.write(b, 0, len);
				}
				
				zout.closeEntry();
			}
			zout.close();

			// out.write();
			out.flush();
			out.close();
		} catch (IOException ex) {
			System.err.println("Error in downloadFile: " + ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
}
