

/*************
 * Name : Game.Game
 * Purpose : To have the constructor and make new objects of the game with different moves.
 * (The start of the strategy pattern)
 */

public class Game
{
    private GameBehaviour gameInstance;

    public Game(GameBehaviour gameInstance)
    {
        this.gameInstance = gameInstance;
    }

    public String[][] move(String[][] pMap)
    {
        this.gameInstance.move(pMap);
        return pMap;
    }
}
