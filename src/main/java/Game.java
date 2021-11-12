import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private int x=10;
    private int y=10;
    public Game(){
        try {
            //   Terminal terminal = new DefaultTerminalFactory().createTerminal();
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void draw() throws IOException{
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }
    private void processKey(com.googlecode.lanterna.input.KeyStroke key) throws IOException{
        System.out.println(key);
        switch (key.getKeyType()){
            case ArrowLeft: {
                x=x-1;break;
            }
            case ArrowDown : {
                y=y+1;break;
            }
            case ArrowRight:{
                x=x+1;break;
            }
            case ArrowUp :{
                y=y-1;break;
            }
        }
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            screen.close();
        }
    }
    public void run() throws IOException{
        draw();
        while(true){
            KeyStroke chave = screen.readInput();
            if (chave.getKeyType()==KeyType.EOF )
                break;
            processKey(chave);
            draw();
        }


    }

}