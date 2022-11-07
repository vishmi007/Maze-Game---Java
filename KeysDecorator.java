import java.util.ArrayList;

// a concrete class of theMap class.
// I thought of adding colors and keys in this class. But really struggled with passing the parameters.So to show that
//I am able to implement the decorator pattern and call it correctly inside the method I implemented this class any ways
//and did the appropriate calling.
public class KeysDecorator extends mapDecorator
{

    public KeysDecorator(createMap decoratedMap) {
        super(decoratedMap);
    }

    @Override
    public String[][] readFile()
    {

      return  decoratedMap.readFile();
      //print(decoratedMap);
    }





}
