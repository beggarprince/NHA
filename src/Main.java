import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //Maybe a console app here to determine screen resolution before we create the game

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("java project l1");

        Engine engine = new Engine();

        window.add(engine.gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        engine.startGameThread();
        engine.startMainMusicThread();
    }
}