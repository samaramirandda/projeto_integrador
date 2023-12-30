import java.io.FileNotFoundException;
import java.util.ArrayList;

/*define function f (x,y)
        read values of initial condition (x0 and y0)
        read number of steps (n)
        read size of step (h)
        i ← 0
        do
        k1 ← h ∗ f (x0, y0)
        k2 ← h ∗ f (x0 + h/2, y0 + k1/2)
        k3 ← h ∗ f (x0 + h/2, y0 + k2/2)
        k4 ← h ∗ f (x0 + h, y0 + k3)
        k ← (k1 + 2 ∗ k2 + 2 ∗ k3 + k4)/6
        yn ← y0 + k
        i ← i + 1
        x0 ← x0 + h
        y0 ← yn
        while i < n
display yn as result*/

/*  Equações(f(x,y))

        dS = λ − bSI − μS

        dI = bSI − kI + βIR − (μ + δ1)I

        dR = kI − βIR − (μ + δ2)R
*/


public class RungeKuttaMethod {
//Neste método, o numero de iterações(n) é dado pelo utilizador, e é preciso repetir o algoritmo para cada estado inicial(S,R,I).
//O meu h(passo) varia entre 0.1 e 1 e de acordo com o numero de iterações. Se o meu nº de iterações for 4, o h = 0.25.
//O meu "Yn" é o meu S ou I ou R, e sempre recorre ao anterior. O S1 vai recorrer ao S0, e o S2 vai recorrer ao S1 e assim por diante.
// i < n(numero de pontos no gráfico) e n = nº de dias/h
//ignorar o x0.
//o meu t só será usado no gráfico (S em função de t), e o t = t + h



    public static void rungeKutta(double S, double R, double I, int dias, double h, double lambda, double b, double mu, double delta1, double delta2, double beta, double k) throws FileNotFoundException {

        int[] days = new int[dias+1];
        ArrayList<Double> suscetiveis = new ArrayList<>();
        ArrayList<Double> infetados = new ArrayList<>();
        ArrayList<Double> recuperados = new ArrayList<>();

        ArrayList<String[]> linhas = new ArrayList<>();

        // double n = dias / h;

        for (int step = 0; step <= dias; step++) {

            double kS1 = h * f_sir_s(S, I, lambda, b, mu);
            double kS2 = h * (f_sir_s(S, I, lambda, b, mu) + kS1 / 2);
            double kS3 = h * (f_sir_s(S, I, lambda, b, mu) + kS2 / 2);
            double kS4 = h * (f_sir_s(S, I, lambda, b, mu) + kS3);
            double kS = (kS1 + 2 * kS2 + 2 * kS3 + kS4) / 6;

            double kI1 = h * f_sir_i( S,  I,  R,  b,  k,  beta,  mu,  delta1);
            double kI2 = h * (f_sir_i( S,  I,  R,  b,  k,  beta,  mu,  delta1) + kI1 / 2);
            double kI3 = h * (f_sir_i( S,  I,  R,  b,  k,  beta,  mu,  delta1) + kI2 / 2);
            double kI4 = h * (f_sir_i( S,  I,  R,  b,  k,  beta,  mu,  delta1) + kI3);
            double kI = (kI1 + 2 * kI2 + 2 * kI3 + kI4) / 6;

            double kR1 = h * f_sir_r( I, R, k, beta, mu, delta2);
            double kR2 = h * (f_sir_r( I, R, k, beta, mu, delta2) + kR1 / 2);
            double kR3 = h * (f_sir_r( I, R, k, beta, mu, delta2) + kR2 / 2);
            double kR4 = h * (f_sir_r( I, R, k, beta, mu, delta2) + kR3);
            double kR = (kR1 + 2 * kR2 + 2 * kR3 + kR4) / 6;

            S = S + kS;
            I = I + kI;
            R = R + kR;
            double T = S + R + I;

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
            days [step] = step;
        }

        SaveFiles.criarFicheiro(linhas);
        SaveFiles.imagemGrafico(days, suscetiveis, infetados, recuperados);

    }

    private static double f_sir_s(double S, double I, double lambda, double b, double mu) {
        return lambda - b * S * I - mu * S;
    }

    private static double f_sir_i(double S, double I, double R, double b, double k, double beta, double mu, double delta1) {
        return b * S * I - k * I + beta * I * R - (mu + delta1) * I;
    }

    private static double f_sir_r(double I, double R, double k, double beta, double mu, double delta2) {
        return k * I - beta * I * R - (mu + delta2) * R;
    }
}
