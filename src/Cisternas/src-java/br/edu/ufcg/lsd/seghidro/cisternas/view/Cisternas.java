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

package br.edu.ufcg.lsd.seghidro.cisternas.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import br.edu.ufcg.lsd.seghidro.cisternas.control.CisternasFacade;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Cenarios;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PeriodosIniciais;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.PesoPeriodo;
import br.edu.ufcg.lsd.seghidro.cisternas.entities.Precipitacao;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.HelpException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.InvalidParameterException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.ParameterApplicationException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.ParametrosReaderException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ColecaoParametros;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Deficit;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Reamostragem;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinal;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalCompleto;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadoFinalConsenso;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosBalHid;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.ResultadosCenarios;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.InconsistenciaArquivosException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.exceptions.ReamostragemException;

/**
 * Classe responsável por executar a aplicação de Cisternas.
 * @author Sávio Canuto de Oliveira Sousa
 * @since 05/01/2009
 */
public class Cisternas {

	/** Variável estatica que representa a fachada. */
	private static CisternasFacade fachada;
	
	/**
	 * Método para a execução do JHidro.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		/** Instância da fachada. */
		fachada = CisternasFacade.getInstance();
		
		fachada.rodarCisternas(Double.parseDouble(args[0].replace(',', '.')), args[1], args[2], args[3], args[4], args[5], args[6]);

	}

}
