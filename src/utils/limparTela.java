package utils;

public class limparTela {
        public static void limpeza() {
            try {
                final String os = System.getProperty("os.name");

                if (os.contains("Windows")) {
                    // Para Windows
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    // Para sistemas baseados em Unix/Linux/Mac
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
