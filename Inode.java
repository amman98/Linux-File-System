/**
 * The Inode class .
 *
 * @author (Amman Nega)
 * @version (August 20, 2020)
 */
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Inode
{
    private int size = 0; //equal to blockCount * 512 for kb (* 1000 for bytes)
    private int blockCount = 0;
    private int[] directBlocks = new int[10];
    private String mode = "-rwxr--r--";
    private String uid = "CSS430";
    private String gid = "CSS430";
    private String aTime = "", cTime = "", mTime = "";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    Inode()
    {
        aTime = cTime = mTime = dtf.format(LocalDateTime.now());
    }

    public void addBlocks(Bitmap array, int numBlocks) {
        for(int i = 0; i < numBlocks; i++) {
            int blockPointer = array.findAvailableBlock();
            try {
                if (blockPointer == -1) {
                    // error message
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Bitmap is full");
                return;
            }

            // block size is 512K bytes, or 2^19 bytes
            size += (int)Math.pow(2, 19);

            // set direct block value to index in bitmap
            directBlocks[blockCount] = blockPointer;

            blockCount++;
        }
    }

    /*
     * this method would be called when using DB filename numblocks command.
     */
    public void deleteBlocks(Bitmap array, int numBlocks) {
        for(int i = 0; i < numBlocks; i++) {
            int blockPtr = directBlocks[blockCount - 1];
            array.removeBlock(blockPtr);
            blockCount--;
        }
    }

    /*
     * this method would be called when using DF filename command.
     */
    public void deleteFile(Bitmap array) {
        // simply calls deleteBlocks() for every block allocated
        deleteBlocks(array, blockCount - 1);
    }

    public String toString()
    {
        String data = "INode\n";
        data += "\tMode: " + mode + "\n";
        data += "\tUID: " + uid + "\n";
        data += "\tGID: " + gid + "\n";
        data += "\tAccess Time: " + aTime + "\n";
        data += "\tCreate Time: " + cTime + "\n";
        data += "\tModify Time: " + mTime + "\n";
        data += "\tSize: " + size + "\n";
        data += "\tBlock Count: " + blockCount + "\n";
        return data;
    }
}