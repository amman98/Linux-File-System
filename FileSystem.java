import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileSystem
{
    private static Directory directory = new Directory();
    private static ArrayList<Inode> inodes = new ArrayList<Inode>();
    private static Bitmap disk = new Bitmap();

    public static void main(String[] args)
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader("./command.txt"));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                String[] arr = line.split(" ");
                if (arr[0].equals("FM") && arr.length == 1)
                    format();
                else if (arr[0].equals("NF") && arr.length == 3)
                    newFile(arr);
                else if (arr[0].equals("MF") && arr.length == 3)
                    modifyFile(arr);
                else if (arr[0].equals("DF") && arr.length == 2)
                    deleteFile(arr);
                else if (arr[0].equals("DB") && arr.length == 3)
                    deleteBlocks(arr);
                else if (arr[0].equals("PR") && arr.length == 1)
                    print();
                else
                    System.err.println("ERROR: erroneous command entered");
            }
        }
        catch (IOException e)
        {
            System.err.println("ALERT: ensure command.txt is in root directory of program");
            e.printStackTrace();
        }
    }

    private static void format()
    {
        directory = new Directory();
        inodes.clear();
        disk = new Bitmap();
    }

    private static void newFile(String[] arr)
    {
        int index = inodes.size();
        if (inodes.isEmpty())
            directory.addElement(arr[1], 0);
        else
        {
            for (int i = 0; i < inodes.size(); i++)
                if (inodes.get(i) == null)
                {
                    index = i;
                    inodes.remove(index);
                    break;
                }
            directory.addElement(arr[1], index);
        }
        Inode node = new Inode();
        node.addBlocks(disk, Integer.parseInt(arr[2]));
        inodes.add(index, node);
    }

    private static void modifyFile(String[] arr)
    {
        int index = directory.getIndex(arr[1]);
        inodes.get(index).addBlocks(disk, Integer.parseInt(arr[2]));
    }

    private static void deleteFile(String[] arr)
    {
        int index = directory.getIndex(arr[1]);
        inodes.get(index).deleteFile(disk);
        directory.removeElement(arr[1]);
        inodes.set(index, null);
    }

    private static void deleteBlocks(String[] arr)
    {
        int index = directory.getIndex(arr[1]);
        inodes.get(index).deleteBlocks(disk, Integer.parseInt(arr[2]));
    }

    private static void print()
    {
        System.out.println("\nDIRECTORY\n---------");
        System.out.println(directory);
        System.out.println("\nINODE LIST\n----------");
        if (inodes.isEmpty())
            System.out.println("null\n");
        else
            for (int i = 0; i < inodes.size(); i++)
            {
                if (inodes.get(i) == null)
                    System.out.println("null\n");
                else
                    System.out.println(inodes.get(i));
            }
        System.out.println("BITMAP\n------");
        System.out.println(disk);
    }
}