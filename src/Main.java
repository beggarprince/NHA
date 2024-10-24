import javax.swing.*;
//Fuck intellij
public class Main {
    public static void main(String[] args) {
        System.out.println("Main running\n");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        Engine engine = new Engine();

        window.add(engine.gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        engine.startGameThread();
    }
}