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

package br.edu.ufcg.lsd.seghidro.cisternasbalhid.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Classe com métodos de utilidades.
 * @author Sávio Canuto.
 *
 */
public class Parser {
	
	/**
	 * Converte string para uma data
	 * 
	 * @param data string no formado yyyy/mm/dd
	 * @return 
	 * @throws CadastroException caso a data tenha um dia maior que 31 ou um mes maio que 12 ou a data estaja sem um ano
	 * @throws ParseException 
	 */
	public static GregorianCalendar string2Calendar(String data) {
		int ano = Integer.parseInt(data.substring(0,4));
		int mes = Integer.parseInt(data.substring(5,7));
		int dia = Integer.parseInt(data.substring(8,10));
		GregorianCalendar calendar = new GregorianCalendar(ano, mes-1, dia);
		calendar.setLenient(false);
		
		return calendar;
	}
	
	/**
	 * Método que faz uma cópia de uma data passada como parâmetro.
	 * @param date
	 * @return
	 */	
	public static GregorianCalendar copyDate(GregorianCalendar date){
		GregorianCalendar newDate = new GregorianCalendar(date.get(GregorianCalendar.YEAR)
				,date.get(GregorianCalendar.MONTH)
				,date.get(GregorianCalendar.DAY_OF_MONTH));
		newDate.setLenient(date.isLenient());
		return newDate;
	}
	
	/**
	 * Método que retorna um String no formato yyyy-MM-dd
	 * @param date
	 * @return 
	 */
	public static String calendar2String2(GregorianCalendar calendar){
		String ano = String.valueOf(calendar.get(calendar.YEAR));
		String mes = String.valueOf(calendar.get(calendar.MONTH) + 1);
		String dia = String.valueOf(calendar.get(calendar.DAY_OF_MONTH));
		
//		System.out.println(dia +"/"+ mes +"/"+ ano);

		String data = ano + "-" + mes + "-" + dia; 
		return data;
	}
	
	/**
	 * Método que retorna a string mês da data passada.
	 * @param calendar
	 * @return
	 */
	public static String getMes(GregorianCalendar calendar){
		String data = calendar2String2(calendar);
		return data.substring(6, 7); 
	}

	/**
	 * Arredonda um double de acordo com a quantidade de casas decimais passadas.
	 * @param casasDecimais
	 * @param value
	 * @return
	 */
	public static double roundDouble(int casasDecimais, double value) {
		double currency = new BigDecimal(value).setScale(casasDecimais,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		return currency;
	}
	
	/**
	 * Método que formata uma string para uma data. É informado somente
	 * o ano e o mês, sendo acrescido internamente do dia 01.
	 * @param data
	 * @return
	 */
	public static GregorianCalendar string2CalendarAnoMes(String data) {
		int ano = Integer.parseInt(data.substring(0,4));
		int mes = Integer.parseInt(data.substring(5,7));
		int dia = 1;
		GregorianCalendar calendar = new GregorianCalendar(ano, mes-1, dia);
		calendar.setLenient(false);
		
		return calendar;
	}
	
	/**
	 * Método que retorna a string dia da data passada.
	 * @param calendar
	 * @return
	 */
	public static String getDia(GregorianCalendar calendar){
		String data = calendar2String2(calendar);
		return data.substring(9, 10); 
	}
	
	/**
	 * Método que retorna a string ano da data passada.
	 * @param calendar
	 * @return
	 */
	public static String getAno(GregorianCalendar calendar){
		String data = calendar2String2(calendar);
		return data.substring(0, 4);
	}
	
	/**
	 * Método que retorna uma data somada de N dias passados como parâmetro.
	 * @param data
	 * @param quantidadeDias
	 * @return GregorianCalendar data somada aos N dias.
	 */
	public static GregorianCalendar somaDia(GregorianCalendar data, int quantidadeDias) {
		
//		data.setTimeInMillis((quantidadeDias * MILISEGUNDOSDIA) + data.getTime().getTime());
		
		data.add(Calendar.DAY_OF_MONTH, quantidadeDias);
		
		return data;
	}
	
	/**
	 * Método que retorna uma data somada de N dias passados como parâmetro.
	 * @param data
	 * @param quantidadeDias
	 * @return GregorianCalendar data somada aos N dias.
	 */
	public static GregorianCalendar subtraiDia(GregorianCalendar data, int quantidadeDias) {
		
		data.add(Calendar.DAY_OF_MONTH , -quantidadeDias);
		
		return data;
	}
	
	/**
	 * Método que recupara uma string no arquivo de entrada
	 * @param linha
	 * @return string de resultado
	 */
	private static String pegaString(String linha){
    	linha = linha.trim();
    	String valor = "";
		StringTokenizer tokens = new StringTokenizer(linha);
		tokens.nextToken();
		while(tokens.hasMoreTokens()){
			valor = tokens.nextToken();
		}
    	return valor;
    }
	
	/**
	 * Método que transforma um GregorianCalendar em String no formato
	 * de 01/mm/aaaa.
	 * @param dataCorrente
	 * @return
	 */
	public static String calendar2String(GregorianCalendar dataCorrente) {
		
		String diaInicioMes = "01/";
		String mesTemporario = String.valueOf(dataCorrente.get(dataCorrente.MONTH) + 1);
		String mesCorr = "";

		if (mesTemporario.length() == 1) {
			mesCorr = "0" + mesTemporario + "/";
		} else {
			mesCorr = mesTemporario + "/";
		}
		
		String ano = String.valueOf( dataCorrente.get(dataCorrente.YEAR) );
		diaInicioMes = diaInicioMes + mesCorr + ano;
		
		return diaInicioMes;
	}
	
	/**
	 * Método que recupera um determinado ano passando uma string como data. 
	 * O padrão dessa string é yyyy/mm/dd.
	 * @param dataCorrente
	 * @return
	 */
	public static String getAno(String dataCorrente) {
		
		String anoCorrente = dataCorrente.substring(0, 4); 		
		return anoCorrente;
	}
	
	/**
	 * Método que retorna o mês de acordo com a String de data passado.
	 * @param data
	 * @return
	 */
	public static String getMes(String data) {
		String mesCorrente = data.substring(5, 7);
		return mesCorrente;
	}
	
	/**
	 * Método que retorna o mês de acordo com a String de data passado.
	 * @param data
	 * @return
	 */
	public static String getDia(String data) {
		String diaCorrente = data.substring(8, 10);
		return diaCorrente;
	}
	
	/**
	 * Método que retorna o resultado de uma regra de três simples.
	 * @param total da quantidade
	 * @param quantidade para ser medida
	 * @return resultado em porcentagem
	 */
	public static Double calcularRegraDeTres(Integer total, Integer quantidade) {
		
		Double totalDouble = Double.parseDouble(total.toString());
		Double quantidadeDouble = Double.parseDouble(quantidade.toString());
		
		Double retorno = (quantidadeDouble * 100) / totalDouble;
		return retorno;
	}
	
	/**
	 * Método que calcula o MDC entre dois números.
	 * @param a
	 * @param b
	 * @return
	 */
	public static int mdc(int a, int b) {
		
		int resto;

		while (b != 0) {
			resto = a % b;
			a = b;
			b = resto;
		}

		return a;
	}
	
	/*
	 * Teste de cálculo do MDC.
	 */
	public static void main(String[] args){
	    Scanner in = new Scanner(System.in);
	    
	    System.out.print(
	      "Este programa permite calcular o MDC\n");
	    System.out.print("Informe o primeiro número: ");
	    int x = in.nextInt();

	    System.out.print("Informe o segundo número: ");
	    int y = in.nextInt();

	    System.out.println("\nO Máximo Divisor Comum de "
	      + x + " e " + y + " é " + mdc(x, y));
	  }
	
	/**
	 * Método para compactar os arquivos gerados por execuções de cenários.
	 * @param zipFileName
	 * @param mapBytes
	 */
	public static void compactarArquivos(String zipFileName, Map<String, String> mapBytes) {

		byte b[] = new byte[512];

		try {

//			zipFileName="cisternas.zip";
			
			OutputStream out = new FileOutputStream(zipFileName + ".zip");
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
