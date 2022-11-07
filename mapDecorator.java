import java.util.ArrayList;
/******************************************************
 * Author : Vishmi Kalansooriya
 * Date : 15th April 2022
 * Purpose : This is an abstract class which is used to implement the interface createMap.
 *
 */
public abstract class mapDecorator implements createMap
{
    protected createMap decoratedMap;

    public mapDecorator(createMap decoratedMap) {
        this.decoratedMap = decoratedMap;
    }

    @Override
    public String[][] readFile() {
       return  decoratedMap.readFile();
    }

    @Override
    public void fillMap(int pRows, int pCols, String[][] pMap, String pChar, String filler) {
        decoratedMap.fillMap( pRows, pCols,pMap,pChar,filler);
    }

    @Override
    public void positions(String inIndex, String pRow, String pCol, String[][] pMap) throws CustomException {
        decoratedMap.positions(inIndex,pRow,pCol,pMap);
    }


    public void connection(String[][] pMap, int pRows, int pCols) {
        decoratedMap.connection(pMap,pRows,pCols);
    }

    @Override
    public void printMap(int pRows, int pCols, String[][] pMap) {
        decoratedMap.printMap(pRows,pCols,pMap);
    }
}
