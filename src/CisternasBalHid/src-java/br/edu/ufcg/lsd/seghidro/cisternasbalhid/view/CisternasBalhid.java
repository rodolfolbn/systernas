package br.edu.ufcg.lsd.seghidro.cisternasbalhid.view;

public class CisternasBalhid {

	/**
	 * Método para a execução do JHidro.
	 */
	public static void main(String[] args) {

		try {

//			if ((args.length == 1) && (args[0].equals("--help"))) {
//				throw new HelpException();
//			} else if ((args.length < 5)) {
//				throw new ParameterApplicationException();
//			}

			Double volumeInicial = 0.0;
			String precipitacao_file = "";
			String parametros_file = "";
			String cenarios_file = "";
			String output_file = "";
			String pesoPeriodo_file = "";
			String argumento = "";

//				volumeInicial = Double.parseDouble(args[0]);
//				precipitacao_file = args[1];
//				parametros_file = args[2];
//				pesoPeriodo_file = args[3];
//				output_file = args[4];
//
//				if (args.length == 11) {
//					cenarios_file = args[5];
//				} else {
//					cenarios_file = "";
//				}

				volumeInicial = 75.0;
				argumento = "normal";
				precipitacao_file = "testes/pmh.pmh";
				parametros_file = "testes/parametros.par";
				pesoPeriodo_file = "testes/pesoPeriodo.pes";
				output_file = "testes/savioSaida";
				cenarios_file = "testes/cenarios.cen";
//				cenarios_file = "";

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
