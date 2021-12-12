import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Puzzle {

    int mat[][] = new int[4][4];
    GraphicsContext gc;
    Canvas canvas;

    class Position {
        int i, j;
        Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
    
    public void print(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
                System.out.print(mat[i][j]+" ");
       System.out.println();
        }
    }

    public Puzzle(GraphicsContext gc) {
        canvas = gc.getCanvas();
        this.gc = gc;
        Scene scene = gc.getCanvas().getParent().getScene();
        generate();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                play(e.getCode().toString());
            }
        });
    }

    public Position locateFree() {
        Position p = null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] == 0) {
                    p = new Position(i, j);
                }
            }
        }
        return p;
    }

    public void generate() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int x;
        for (int i = 0; i < 16; i++) {
            numbers.add(i);// 0 is the empty place
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x = (int) (numbers.size() * Math.random());
                mat[i][j] = numbers.get(x);
                numbers.remove(x);
            }
        }
    }
    
    public void generateWin(){
        int x=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x++;
                mat[i][j] = x;
                }
            }
        mat[3][3]=0;
    }

    public boolean win() {
        boolean w = true;
        int x = 0;
        if (mat[3][3] != 0) return false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x++;
                if ((i+j) == 6) {
                    break;
                }
                if (mat[i][j] != x) {
                    w = false;
                    break;
                }
            }
        }
        return w;
    }

    public void play(String arrow) {
        Position pf = locateFree();
        int i, j;
        if (arrow.equals("UP")) {
            if (pf.i < 3) {
                mat[pf.i][pf.j] = mat[pf.i + 1][pf.j];
                mat[pf.i + 1][pf.j] = 0;
            }
        }
        if (arrow.equals("DOWN")) {
            if (pf.i > 0) {
                mat[pf.i][pf.j] = mat[pf.i - 1][pf.j];
                mat[pf.i - 1][pf.j] = 0;
            }
        }
        if (arrow.equals("LEFT")) {
            if (pf.j < 3) {
                mat[pf.i][pf.j] = mat[pf.i][pf.j + 1];
                mat[pf.i][pf.j + 1] = 0;
            }
        }
        if (arrow.equals("RIGHT")) {
            if (pf.j > 0) {
                mat[pf.i][pf.j] = mat[pf.i][pf.j - 1];
                mat[pf.i][pf.j - 1] = 0;
            }
        }
        draw();
    }

    public void draw() {
        print();
        
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int step = (int) Math.min(canvas.getWidth(), canvas.getHeight()) / 4;
        int dx = step/4;
        int dy = step-step/3;
        String sp;

        gc.setFill(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] == 0) {
                    continue;
                }
                gc.fillOval(j * step, i * step, step, step);
            }
        }
        gc.setFill(Color.RED);
        gc.setFont(Font.font(48));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] == 0) {
                    continue;
                }
                if (mat[i][j] > 9) {
                    sp = "";
                } else {
                    sp = " ";
                }
                gc.fillText(sp + mat[i][j], j* step + dx, i * step + dy);
            }
        }
        if (win()) {
            gc.setFill(Color.BLUE);
            gc.fillText("Congratulation", step/2, step);
        }
    }
}