import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

// The class to add all the boundaries,obstacles,pawn start position and end position on the map according to the user
//input files.
public class theMap implements createMap
{

    @Override
    /*********************************************************
     * Name : readFile
     * Date: 15/4/2022
     * Import : None
     * Export : A string 2D array called map.
     * Purpose: To read a text file and create the map of the maze game accordingly.
     *********************************************************************************************************/

    public String[][] readFile()
    {
        //Declaring and initializing int variables to store the sizes of the maze.
        int rows = 0;
        int cols = 0;
        int totalRows = 0;
        int totalCols = 0;
        String[][] map = {};



        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        try {
            fileStream = new FileInputStream("Sample.txt");
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            lineNum = 0;

            //bufRdr.readLine();
            line = bufRdr.readLine(); // Reading the first line of the text file to get the size of the maze.

            String[] k = line.split(""); // Splitting the String to get the rows and columns separately.
            rows = Integer.parseInt(k[0]);// Storing the fist char by casting it to an int in the row variable.
            //Skipping the first index because it's an empty space.
            cols = Integer.parseInt(k[2]); // Storing the third char by casting it to an int in the column variable.

            totalRows = rows+rows+1; // Because each maze square occupies 3 adjacent characters.
            totalCols = (cols*3) + (cols+1);

              map = new  String[totalRows][totalCols]; // Creating an empty 2D array with the appropriate sizes.

            //Filling the Array with empty spaces.
            fillMap(totalRows,totalCols,map," ","Entire");

            //Filling the first row with horizontal walls.
            fillMap(totalRows,totalCols,map,"\u2500","FirstRow");

            //Filling the last row with horizontal walls.
            fillMap(totalRows,totalCols,map,"\u2500","LastRow");

            //Filling the first column with vertical walls.
            fillMap(totalRows,totalCols,map,"\u2502","FirstCol");

            //Filling the last column with vertical walls.
            fillMap(totalRows,totalCols,map,"\u2502","LastCol");


            // Now to position the characters in the map.
            line = bufRdr.readLine(); // To omit the first row of the txt file.
            while (line != null) {
                lineNum++;
                String[] splitLine;
                splitLine = line.split(" ");

                {
                    //Calling the positions' method to place the pawn. walls and to mark the End.

                    positions(splitLine[0],splitLine[1],splitLine[2],map);

                    if(splitLine.length >=4) //Checking if there are more than 4 columns in the text file
                        //Because keys and doors have aa color which occupies an extra column.
                    {
                        addKeysAndDoors(splitLine[0],map,splitLine[1], splitLine[2],splitLine[3]);
                    }
                }

                line = bufRdr.readLine();
            }
            fileStream.close();

            connection(map,totalRows,totalCols);
            makeExtraConnections(map,totalRows,totalCols);
            //printMap(totalRows,totalCols,map);

        } catch (IOException | CustomException errorDetails) {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException ex2) {
                }
            }
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
            System.out.println("Sorry we could not find the file you are referring to");


        }

        return map;
    }

    @Override
    /*********************************************************
     * Name : fillMap
     * Date: 15/4/2022
     * Import : int pRows, int pCols,String[][] pMap,String pChar,String filler
     * Export : None
     * Purpose: To Fill the map boundaries.
     *********************************************************************************************************/
    public void fillMap(int pRows, int pCols,String[][] pMap,String pChar,String filler)
    {
        for(int i = 0; i < pRows; i++) {
            for (int j = 0; j < pCols; j++)
            {
                if(filler == "Entire")
                {
                    pMap[i][j] = pChar;

                }else if(filler == "FirstRow")
                {
                    pMap[0][j] = pChar;

                }else if(filler == "FirstCol")
                {
                    pMap[i][0] = pChar;

                }else if(filler == "LastRow")
                {
                    pMap[pRows-1][j] = pChar;

                }else if(filler == "LastCol")
                {
                    pMap[i][pCols-1] = pChar;

                }


            }
        }


    }




    @Override
    /*********************************************************
     * Name : positions
     * Date: 15/4/2022
     * Import : String[][] pMap,int pRows, int pCols
     * Export : None
     * Purpose: To place the walls,start position of the pawn and to mark the END.
     *********************************************************************************************************/
    public void positions(String inIndex, String pRow, String pCol,String[][] pMap) throws CustomException {
        int pawnStartRow = 0;
        int pawnStartCol = 0;

        int endRow = 0;
        int endCol = 0;

        int wHRow = 0;
        int wHCol = 0;

        int wVRow = 0;
        int wVCol = 0;

        // Using 2n+1 equation where n = row index to each an every row because a 4 by 4 grid should be converted to a 17 by 9 grid.
        //Using 4n+2 equation where n = column index  to each an every column because a 4 by 4 grid should be converted to a 17 by 9 grid.
        switch(inIndex)
        {
            case "S" : // To place the start position of the pawn.
            {

                pawnStartRow = Integer.parseInt(pRow)*2 + 1;
                pawnStartCol = Integer.parseInt(pCol) * 4 +2;
                pMap[pawnStartRow][pawnStartCol] = "P";
            }
            break;


            case "E" : // To mark the end point of the game.
            {
                endRow = Integer.parseInt(pRow) * 2 + 1;
                endCol = Integer.parseInt(pCol) *4 + 2;

                if(endRow == pawnStartRow && endCol == pawnStartCol)
                {
                    throw new CustomException("You have already won!");
                }
                pMap[endRow][endCol] = "E";

            }
            break;


            case "WH" : // To place the walls horizontally
            {
                wHRow = Integer.parseInt(pRow) * 2 + 1;
                wHCol = Integer.parseInt(pCol) *4 + 2;
                pMap[wHRow-1][wHCol] = "\u2500";
                pMap[wHRow-1][wHCol-1] = "\u2500";
                pMap[wHRow-1][wHCol+1] = "\u2500";
            }
            break;

            case "WV" : //To place the walls vertically.
            {
                wVRow = Integer.parseInt(pRow) * 2 + 1;
                wVCol = Integer.parseInt(pCol) *4 + 2;
                pMap[wVRow][wVCol-2] = "\u2502";

            }
            break;
//
//
        }


    }
    @Override
    /*********************************************************
     * Name : connection
     * Date: 15/4/2022
     * Import : String[][] pMap,int pRows, int pCols
     * Export : None
     * Purpose: To make the connections with boundary of the map.
     *********************************************************************************************************/

    public void connection(String[][] pMap,int pRows, int pCols)
    {
        for(int i = 0; i < pRows; i++) {
            for (int j = 0; j < pCols; j++)
            {
                if(pMap[i][1] == "\u2500" || pMap[i][1] =="\u2592")
                {
                    pMap[i][0] = "\u251c";

                }
                if(pMap[i][pCols-2]== "\u2500" || pMap[i][pCols-2] =="\u2592")
                {
                    pMap[i][pCols-1] = "\u2524";
                }
                if(pMap[1][j] == "\u2502" || pMap[1][j] =="\u2592")
                {
                    pMap[0][j] = "\u252c";
                }

                if(pMap[pRows-2][j] == "\u2502" || pMap[1][j] =="\u2592")
                {
                    pMap[pRows-1][j] = "\u2534";
                }
            }
        }



        // Marking the edge points of the map. Since they are fixed.
        pMap[0][0] = "\u250c";
        pMap[pRows-1][0] = "\u2514";
        pMap[0][pCols - 1] = "\u2510";
        pMap[pRows -1][pCols -1] = "\u2518";
    }

    @Override
    /*********************************************************
     * Name : printMap
     * Date: 15/4/2022
     * Import : int pRows, int pCols, String[][] pMap
     * Export : None
     * Purpose: To print the map to the user.
     *
     * (This method is not used eventhough i have implemented it :( )
     *********************************************************************************************************/


    public void printMap(int pRows, int pCols, String[][] pMap)
    {
        for(int i = 0; i < pRows; i++)
        {
            for(int j = 0; j < pCols; j++ )
            {
                System.out.print(pMap[i][j]+" ");
            }

            System.out.println(" ");

        }

    }

    /*********************************************************
     * Name :  makeExtraConnections
     * Date: 16/4/2022
     * Import : String[][] pMap,int pRows, int pCols
     * Export : None
     * Purpose: To make connections in the middle of the map. Between the walls and doors.
     *
     *
     *********************************************************************************************************/

    public void makeExtraConnections(String[][] pMap,int pRows, int pCols)
    {
        // Checked for all the 11 different patterns where connections can happen.

        for(int i = 0; i < pRows; i++)
        {
            for(int j = 0; j < pCols; j++)
            {
                if(pMap[i][j] == " ") // To check and connect the corners of all four sides.
                {
                    if((pMap[i+1][j].contains("\u2502") || pMap[i+1][j].contains("\u2592")  || pMap[i+1][j].contains("\u2500")) &&
                            ((pMap[i-1][j].contains("\u2502") || pMap[i-1][j].contains("\u2592") || pMap[i-1][j].contains("\u2500"))) &&
                            ((pMap[i][j-1].contains("\u2502") || pMap[i][j-1].contains("\u2592" )|| pMap[i][j-1].contains("\u2500"))) &&
                            ((pMap[i][j+1].contains("\u2502") || pMap[i][j+1].contains("\u2592") || pMap[i][j+1].contains("\u2500"))) )

                             {
                                    pMap[i][j] = "\u253c";

                             }else if(((pMap[i+1][j].contains("\u2502")) || (pMap[i+1][j].contains("\u2592"))) && ((pMap[i-1][j].contains("\u2502")) || (pMap[i-1][j].contains("\u2592"))) &&
                            ((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592"))))
                             {
                                 pMap[i][j] = "\u251c";
                             }
                    else if(((pMap[i+1][j].contains("\u2502")) || (pMap[i+1][j].contains("\u2592"))) && ((pMap[i-1][j].contains("\u2502")) || (pMap[i-1][j].contains("\u2592"))) &&
                            ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))))
                    {
                        pMap[i][j] = "\u251c";

                    }else if(((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592"))) && ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))) &&
                            ((pMap[i-1][j].contains("\u2502")) || (pMap[i-1][j].contains("\u2592"))))
                    {

                        pMap[i][j] = "\u2534";
                    }
                    else if(((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592"))) && ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))) &&
                            ((pMap[i+1][j].contains("\u2502")) || (pMap[i+1][j].contains("\u2592"))))
                    {

                        pMap[i][j] = "\u252c";
                    }

                    else if((pMap[i+1][j].contains("\u2502") || pMap[i+1][j].contains("\u2592")) && ((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592"))))
                    {
                        pMap[i][j] = "\u2510";

                    }else if((pMap[i+1][j].contains("\u2502") || pMap[i+1][j].contains("\u2592")) && ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))))
                    {
                        pMap[i][j] = "\u250c";

                    }else if((pMap[i-1][j].contains("\u2502") || pMap[i-1][j].contains("\u2592")) && ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))))
                    {
                        pMap[i][j] = "\u2514";

                    }else if((pMap[i-1][j].contains("\u2502") || pMap[i-1][j].contains("\u2592")) && ((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592")))) {
                        pMap[i][j] = "\u2518";

                    }else if(((pMap[i][j-1].contains("\u2500")) || (pMap[i][j-1].contains("\u2592"))) && ((pMap[i][j+1].contains("\u2500")) || (pMap[i][j+1].contains("\u2592"))) )
                    {
                        pMap[i][j] = "\u2500";

                    }else if(((pMap[i+1][j].contains("\u2502")) || (pMap[i+1][j].contains("\u2592"))) && ((pMap[i-1][j].contains("\u2502")) || (pMap[i-1][j].contains("\u2592"))))
                    {
                        pMap[i][j] = "\u2502";
                    }

                }
            }
        }

    }

    /*********************************************************
     * Name :  addKeysAndDoors
     * Date: 16/4/2022
     * Import : String inIndex,String[][] pMap,String pRow, String pCol,String pColor
     * Export : None
     * Purpose: To place the keys and doors on the map with respective to their colors.
     *
     *
     *********************************************************************************************************/

    public static void addKeysAndDoors(String inIndex,String[][] pMap,String pRow, String pCol,String pColor)
    {
        int keyRow = 0;
        int keyCol = 0;

        ArrayList<String> keyColor = new ArrayList<>(); // To store keys if there are more than one in a grid square.


        int dVRow = 0;
        int dVCol = 0;
        int dHRow = 0;
        int dHCol = 0;

        // Setting the unicodes into Strings
        final String  red = "\033[31m";
        final String green = "\033[32m";
        final String yellow = "\033[33m";
        final  String blue = "\033[34m";
        final String magenta = "\033[35m";
        final String cyan = "\033[36m";
        final String setBack = "\033[m";

        switch(inIndex) // Checking with the first column of the file.
        {
            case "K" :
            {

                keyRow = Integer.parseInt(pRow) * 2 + 1;
                keyCol = Integer.parseInt(pCol) * 4 + 2;
                if (pMap[keyRow][keyCol] == " ") {
                    switch (pColor) //Setting colors to the keys.
                    {
                        case "1": {
                            pMap[keyRow][keyCol] = red + "\u2555"+setBack;
                        }
                        break;
                        case "2": {
                            pMap[keyRow][keyCol] = green + "\u2555"+setBack;
                        }
                        break;
                        case "3": {
                            pMap[keyRow][keyCol] = yellow + "\u2555"+setBack;
                        }
                        break;
                        case "4": {
                            pMap[keyRow][keyCol] = blue + "\u2555"+setBack;
                        }
                        case "5": {
                            pMap[keyRow][keyCol] = magenta + "\u2555"+setBack;
                        }
                        break;
                        case "6": {
                            pMap[keyRow][keyCol] = cyan + "\u2555"+setBack;
                        }
                        break;
                    }


                }



            }
            break;

            case "DV" : //To place the doors vertically
            {
                dVRow = Integer.parseInt(pRow) * 2 + 1;
                dVCol = Integer.parseInt(pCol) * 4 + 2;

                switch (pColor) {
                    case "1": {
                        pMap[dVRow][dVCol - 2] = red+"\u2592"+setBack;
                    }
                    break;
                    case "2": {
                        pMap[dVRow][dVCol - 2] = green+"\u2592"+setBack;
                    }
                    break;
                    case "3": {
                        pMap[dVRow][dVCol - 2] = yellow+"\u2592"+setBack;
                    }
                    break;
                    case "4": {
                        pMap[dVRow][dVCol - 2] = blue+"\u2592"+setBack;
                    }
                    case "5": {
                        pMap[dVRow][dVCol - 2] = magenta+"\u2592"+setBack;
                    }
                    break;
                    case "6": {
                        pMap[dVRow][dVCol - 2] = cyan+"\u2592"+setBack;
                    }
                    break;


                }
            }
            break;

            case "DH" : //To place the doors horizontally.
            {
                dHRow = Integer.parseInt(pRow) * 2 + 1;
                dHCol = Integer.parseInt(pCol) *4 + 2;


                switch (pColor) {
                    case "1": {
                        pMap[dHRow-1][dHCol] = red+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = red+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = red+"\u2592"+setBack;
                    }
                    break;
                    case "2": {
                        pMap[dHRow-1][dHCol] = green+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = green+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = green+"\u2592"+setBack;
                    }
                    break;
                    case "3": {
                        pMap[dHRow-1][dHCol] = yellow+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = yellow+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = yellow+"\u2592"+setBack;
                    }
                    break;
                    case "4": {
                        pMap[dHRow-1][dHCol] = blue+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = blue+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = blue+"\u2592"+setBack;
                    }
                    case "5": {
                        pMap[dHRow-1][dHCol] = magenta+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = magenta+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = magenta+"\u2592"+setBack;
                    }
                    break;
                    case "6": {
                        pMap[dHRow-1][dHCol] = cyan+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol-1] = cyan+"\u2592"+setBack;
                        pMap[dHRow-1][dHCol+1] = cyan+"\u2592"+setBack;
                    }
                    break;


                }

            }
            break;

        }


    }


}
