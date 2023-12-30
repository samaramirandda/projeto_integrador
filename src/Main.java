import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Main metodo = new Main();
        int opcao = 0;
        int days = 0;

        System.out.println("MODELO SIR\n");

        // MODO INTERATIVO
        if (args.length == 0) {
            System.out.println("A iniciar o modo interativo...\n");
            executarModoInterativo();
        }

        // MODO NÃO INTERATIVO
        else {
            System.out.println("A iniciar o modo não interativo...\n");
            executarModoNaoInterativo(args);
        }
    }


    public static void executarModoInterativo() throws IOException {

        double S0 = 0, I0 = 0, R0 = 0;
        double caso = 0, lambda = 0, mu = 0, k = 0, beta = 0, b = 0, delta1 = 0, delta2 = 0;
        Scanner ler = new Scanner(System.in);
        int opcao = 0;

        //leitura do ficheiro
        System.out.println("Insira ficheiro estado inicial");
        String fich1 = ler.nextLine();
        System.out.println("Insira ficheiro parametros");
        String fich2 = ler.nextLine();

        Scanner lerFich1 = new Scanner(new File(fich1));
        Scanner lerFich2 = new Scanner(new File(fich2));

        //estado inicial
        if (lerFich1.hasNextLine()) {
            lerFich1.nextLine();
        }
        while (lerFich1.hasNext()) {
            String linhaArq1 = lerFich1.nextLine();
            String linhaFixed1 = linhaArq1.replace(",", ".");
            String[] values = linhaFixed1.split(";");

            // Extrair os valores
            S0 = Double.parseDouble(values[0]);
            I0 = Double.parseDouble(values[1]);
            R0 = Double.parseDouble(values[2]);
        }
        //parametros
        if (lerFich2.hasNextLine()) {
            lerFich2.nextLine();
        }
        while (lerFich2.hasNext()) {
            String linhaArq2 = lerFich2.next();
            String linhaFixed = linhaArq2.replace(",", ".");
            String[] values = linhaFixed.split(";");

            // Extrair os valores
            caso = Double.parseDouble(values[0]);
            lambda = Double.parseDouble(values[1]);
            mu = Double.parseDouble(values[2]);
            k = Double.parseDouble(values[3]);
            beta = Double.parseDouble(values[4]);
            b = Double.parseDouble(values[5]);
            delta1 = Double.parseDouble(values[6]);
            delta2 = Double.parseDouble(values[7]);
        }
        lerFich1.close();
        lerFich2.close();

        System.out.print("\nNúmero de dias = ");
        int days;
        do {
            days = ler.nextInt();
        } while (days <= 1);

        System.out.print("h (passo) = ");
        double h;
        do {
            h = ler.nextDouble();
        } while (h < 0 || h >= 1);

        System.out.println("\nQual método você deseja usar?\n1-Método de Euler\t2-Método de Runge-Kutta");
        opcao = ler.nextInt();

        switch (opcao) {
            case 1:
                EulerMethod.euler(S0, I0, R0, days, h, lambda, mu, k, beta, b, delta1, delta2);
                break;
            case 2:
                RungeKuttaMethod.rungeKutta(S0, R0, I0, days, h, lambda, b, mu, delta1, delta2, beta, k);
                break;
        }


    }


    public static void executarModoNaoInterativo(String[] p) throws IOException {
        lerParametros(p);
    }

    public static void lerParametros(String[] args) throws IOException {
//        java -jar nome programa.jar -b parametros.csv -c val iniciais.csv -m X -p Y -d K

        // ARQUIVOS
        String fich1 = "resources/estado_inicial.csv";
        String fich2 = "resources/params_exemplo1.csv";

        int days = 0, metodo = 0, caso = 0;
        double S0 = 0, I0 = 0, R0 = 0, h = 0, lambda = 0, mu = 0, k = 0, beta = 0, b = 0, delta1 = 0, delta2 = 0;

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-b")) {
                fich1 = args[i + 1];
            } else if (args[i].equals("-c")) {
                fich2 = args[i + 1];
            } else if (args[i].equals("-m")) {
                metodo = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-p")) {
                h = Double.parseDouble(args[i + 1].replace(",", "."));
                if((h < 0 || h >= 1)){
                    System.out.println("Valor do passo incorrecto!");
                }
            } else if (args[i].equals("-d")) {
                days = Integer.parseInt(args[i + 1]);
                if (days < 1){
                    System.out.println("Número de dias inválido!");
                }
            }


            // EXECUTAR OS FICHEIROS INICIAIS
            try {
                Scanner scannerArquivo1 = new Scanner(new File("resources/estado_inicial.csv"));
                Scanner scannerArquivo2 = new Scanner(new File("resources/params_exemplo1.csv"));


                //arquivo 1:
                if (scannerArquivo1.hasNextLine()) {
                    scannerArquivo1.nextLine();
                }
                while (scannerArquivo1.hasNextLine()) {
                    String linhaArq1 = scannerArquivo1.nextLine();
//                    String linhaFixed1 = linhaArq1.replace(",", ".");
                    String[] values = linhaArq1.split(";");
                    // Extrair os valores
                    // Extrair os valores
                    S0 = Double.parseDouble(values[0].replace(",", "."));
                    I0 = Double.parseDouble(values[1].replace(",", "."));
                    R0 = Double.parseDouble(values[2].replace(",", "."));
                }
                //arquivo 2:
                if (scannerArquivo2.hasNextLine()) {
                    scannerArquivo2.nextLine();
                }
                while (scannerArquivo2.hasNextLine()) {
                    String linhaArq2 = scannerArquivo2.nextLine();
                    String[] values = linhaArq2.split(";");

                    // Extrair os valores
                    caso = Integer.parseInt(values[0]);
                    lambda = Double.parseDouble(values[1].replace(",", "."));
                    mu = Double.parseDouble(values[2].replace(",", "."));
                    k = Double.parseDouble(values[3].replace(",", "."));
                    beta = Double.parseDouble(values[4].replace(",", "."));
                    b = Double.parseDouble(values[5].replace(",", "."));
                    delta1 = Double.parseDouble(values[6].replace(",", "."));
                    delta2 = Double.parseDouble(values[7].replace(",", "."));
                }


                if (metodo == 1) {
                    EulerMethod.euler(S0, I0, R0, days, h, lambda, mu, k, beta, b, delta1, delta2);

                }
                if (metodo == 2) {
                    RungeKuttaMethod.rungeKutta(S0, R0, I0, days, h, lambda, b, mu, delta1, delta2, beta, k);

                }


                scannerArquivo1.close();
                scannerArquivo2.close();

            } catch (FileNotFoundException e) {
                System.out.println("Um dos arquivos não foi encontrado.");
                e.printStackTrace();
            }
        }
    }
}
