import java.io.*;
import java.util.ArrayList;

public class SaveFiles {

    public static void criarFicheiro (ArrayList<String[]> linhas) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("resultados.txt");
        writer.printf("%-7s %-15s %-15s %-15s %-15s%n","Dia","S","I","R","T");
        for (String [] linha : linhas) {
            escreverLinhaEmArquivo(writer, linha);
        }
        writer.close();
    }

    public static void escreverLinhaEmArquivo(PrintWriter writer, String[] linha) {
        for (int coluna = 0; coluna < linha.length; coluna++) {
            writer.write(linha[coluna]);
            if (coluna < linha.length - 1) {
                writer.write("\t");
            }
        }
        writer.println();
    }

    public static void imagemGrafico(int[] days, ArrayList<Double> S, ArrayList<Double> I, ArrayList<Double> R) {
        try {
            String ComandosGnuplot = "set terminal png\n" +
                    "set output 'representacao_grafica.png'\n" +
                    "set title 'Evolução da propagação de um vício'\n" +
                    "set xlabel 'Dias'\n" +
                    "set ylabel 'População'\n" +
                    "set yrange [0:1]\n" +
                    "set ytics 0.1\n" +
                    "plot '-' with lines title 'Suscetiveis', '-' with lines title 'Infetados', '-' with lines title 'Recuperados'\n";

            for (int i = 0; i < days.length; i++) {
                ComandosGnuplot += days[i] + " " + S.get(i)+ "\n";
            }
            ComandosGnuplot += "e\n";

            for (int i = 0; i < days.length; i++) {
                ComandosGnuplot += days[i] + " " + I.get(i)+ "\n";
            }
            ComandosGnuplot += "e\n";

            for (int i = 0; i < days.length; i++) {
                ComandosGnuplot += days[i] + " " + R.get(i)+ "\n";
            }
            ComandosGnuplot += "e\n";

            // Executar o processo Gnuplot
            ProcessBuilder processBuilder = new ProcessBuilder("gnuplot");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Enviar comandos para o Gnuplot
            process.getOutputStream().write(ComandosGnuplot.getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().close();

            // Aguardar o processo Gnuplot terminar
            int exitCode = process.waitFor();
            System.out.println("Gnuplot exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}