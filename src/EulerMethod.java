import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EulerMethod {
    public static void euler (double S, double I, double R, int n, double h, double lambda, double mu, double kapa, double beta, double b, double delta1, double delta2) throws FileNotFoundException {

        int[] dias = new int[n+1];
        ArrayList<Double> suscetiveis = new ArrayList<>();
        ArrayList<Double> infetados = new ArrayList<>();
        ArrayList<Double> recuperados = new ArrayList<>();

        ArrayList<String[]> linhas = new ArrayList<>();

        for (int step = 0; step <= n; step++) {
            double dsdt = f_sir_s(S, I, lambda, b, mu);
            double didt = f_sir_i(S, I, R, b, kapa, beta, mu, delta1);
            double drdt = f_sir_r(I, R, kapa, beta, mu, delta2);

            S = S + h * dsdt;
            I = I + h * didt;
            R = R + h * drdt;
            double T = S + I + R;

            System.out.println("Dia:" + step);
            System.out.println("S: " + S);
            System.out.println("I: " + I);
            System.out.println("R: " + R);
            System.out.printf("T: %.0f ", T);
            System.out.println();

            String[] linha = {String.valueOf(step), String.format("%.9f", S), String.format("%.9f", I), String.format("%.9f", R), String.format("%.0f", T)};
            linhas.add(linha);

            suscetiveis.add(S);
            infetados.add(I);
            recuperados.add(R);
            dias [step] = step;
        }
        SaveFiles.criarFicheiro(linhas);
        SaveFiles.imagemGrafico(dias, suscetiveis, infetados, recuperados);

    }

    private static double f_sir_s(double s, double i, double lambda, double b, double mu) {
        return lambda - b * s * i - mu * s;
    }

    private static double f_sir_i(double s, double i, double r, double b, double k, double beta, double mu, double delta1) {
        return b * s * i - k * i + beta * i * r - (mu + delta1) * i;
    }

    private static double f_sir_r(double i, double r, double k, double beta, double mu, double delta2) {
        return k * i - beta * i * r - (mu + delta2) * r;
    }
}

