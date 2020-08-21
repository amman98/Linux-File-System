import java.util.HashMap;
import java.util.Map;

public class Directory
{
    private HashMap<String, Integer> dir = new HashMap<String, Integer>();

    public void addElement(String name, int index) { dir.put(name, index); }

    public void removeElement(String name) { dir.remove(name); }

    public int getIndex(String name) { return dir.get(name); }

    public String toString()
    {
        String dirContents = "";
        for (Map.Entry<String, Integer> e : dir.entrySet())
            dirContents += "Filename: " + e.getKey() + " | Index: " +  e.getValue() + "\n";
        return dirContents.trim();
    }
}