import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Arena {
    private  int width;
    private  int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    public Arena(int width, int height){
        this.height=height;
        this.width=width;
        hero= new Hero(10,10);
        this.walls=createWalls();
        this.coins=createCoins();
        this.monsters=createMonsters();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }
    private List<Monster> createMonsters(){
        Random random=new Random();
        ArrayList<Monster> monsters=new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }
    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }
    public void processKey(KeyStroke key){
        System.out.println(key);
        switch (key.getKeyType()){
            case ArrowLeft: {
                moveHero(hero.moveLeft());
                break;
            }
            case ArrowDown : {
                moveHero(hero.moveDown());break;
            }
            case ArrowRight:{
                moveHero(hero.moveRight());break;
            }
            case ArrowUp :{
                moveHero(hero.moveUp());
                break;
            }
        }
        retrieveCoins();
        moveMonsters();


    }
    private boolean canHeroMove(Position position){
        if(position.getX()>width-1 || position.getY()>height-1 ||position.getX()<0 || position.getY()<0)
            return false;
        for (Wall wall : walls)
            if(wall.getPosition().equals(position))
                return  false;

        return true;
    }
    public void retrieveCoins(){
        for(Coin coin: coins)
            if(coin.getPosition().equals(hero.position)){
                coins.remove(coin);
                break;
            }
    }
    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for(Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
        verifyMonsterCollisions(graphics);
    }
    public void moveMonsters(){
        for (Monster monster : monsters){
            Position monsterposition=monster.monstermove();
            if (canHeroMove(monsterposition))
                monster.setPosition(monsterposition);
        }

    }
    public void verifyMonsterCollisions(TextGraphics graphics){
        for (Monster monster:monsters)
            if(monster.getPosition().equals(hero.getPosition())){
                graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                graphics.putString(new TerminalPosition(2, height/2), "You died!Please Restart the Program");
            }

    }

}
