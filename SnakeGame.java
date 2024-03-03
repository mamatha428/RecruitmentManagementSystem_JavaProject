import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
       int x;
       int y;//this class can be accessed by snakegame only
       Tile(int x,int y){
        this.x=x;
        this.y=y;
       }
    }
    int boardWidth;
    int boardHeight;
    int tileSize=25;
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver=false;

    SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);//listening for key presses

        snakeHead=new Tile(5,5);
        snakeBody=new ArrayList<Tile>();//to store snake body parts
        food=new Tile(10,10);
        random=new Random();
        placeFood();

        velocityX=0;//-1;//0;
        velocityY=0;//1;//moving down

        gameloop=new Timer(200,this);
        gameloop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid
        //we have 24 rows and columns
        for(int i=0;i<boardWidth/tileSize;i++){
            //x1,y1,x2,y2
        g.drawLine(i*tileSize,0,i*tileSize,boardHeight);//vertical lines
        g.drawLine(0,i*tileSize,boardWidth,i*tileSize);//horizontal line

        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);

        //snake head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize ,tileSize ,tileSize);
        //this two tilesizes are for width and height of the box,that is snake

        //drawing snake body
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize);
        }

        //score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
        else{
            g.drawString("Score: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
    }
    public void placeFood(){
        //this function is to keep food randomly in the box
        //a random number from 0 to 24,bcz 600/25=24
        food.x=random.nextInt(boardWidth/tileSize);//600/25=24
        food.y=random.nextInt(boardHeight/tileSize);
    }
    
    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void move(){
        //eat food
        if(collision(snakeHead,food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }

        //snakebody
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }
        snakeHead.x=snakeHead.x+velocityX;
        snakeHead.y=snakeHead.y+velocityY;//as we gave x=0 intially ,snake
        //will move in y that is to down

        //game over conditions
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            if(collision(snakeHead,snakePart)){
                gameOver=true;
            }
        }
        if(snakeHead.x*tileSize <0|| snakeHead.x*tileSize>boardWidth||
        snakeHead.y*tileSize<0|| snakeHead.y*tileSize>boardHeight){
            gameOver=true;
        }
        
    }
   @Override
   public void actionPerformed(ActionEvent e){
    repaint();
    move();//upate the x and y positions of the x and y positions of snake
    if(gameOver){
        gameloop.stop();
    }
   }
   @Override
   public void keyPressed(KeyEvent e){
    
    if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
        velocityX=0;
        velocityY=-1;
    }
    else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
        velocityX=0;
        velocityY=1;
    }
    else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
        velocityX=-1;
        velocityY=0;
    }
    else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
        velocityX=1;
        velocityY=0;
    }
   }
   //do not need
   @Override
   public void keyTyped(KeyEvent e){}
   @Override
   public void keyReleased(KeyEvent e){}
}