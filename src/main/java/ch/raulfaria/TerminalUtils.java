package ch.raulfaria;

public final class TerminalUtils {
    
    public static void clearTerminal() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
    
    private TerminalUtils() {}
}
