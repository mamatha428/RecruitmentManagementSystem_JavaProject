import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth=600;//600px
        int boardHeight=boardWidth;

        JFrame frame=new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);//this for making frame to appear in
        //the middle
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame=new SnakeGame(boardWidth,boardHeight);
        frame.add(snakeGame);
        frame.pack();//it will place the jpanel inside the frme with 4 dimension
        snakeGame.requestFocus();
    }
}
