import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{
    public Monster(int x,int y){
        super(x,y);
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        // graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFF00"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "T");
    }
    public Position monstermove(){
        int h;
        int v;
        Random random=new Random();
        do{
            v=random.nextInt(3)-1;
            h=random.nextInt(3)-1;
        }
        while (h==0 && v==0);
        return new Position(position.getX()+h, position.getY()+v);
    }
}
