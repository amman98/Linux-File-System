/**
 * Creates a bitmap where each index is used to represent whether
 * a direct block is available for use or not.
 *
 * @author (Amman Nega)
 * @version (August 20, 2020)
 */
public class Bitmap
{
    private boolean[] diskBlockMap;

    // initializes indices to false, indicating all blocks are available.
    Bitmap() {
        diskBlockMap = new boolean[1000];

        // initialize all indices to 0
        for(int i = 0; i < diskBlockMap.length; i++) {
            diskBlockMap[i] = false; // false is equivalent to 0 in this case
        }
    }

    public int findAvailableBlock() {
        int i = 0;

        while(diskBlockMap[i] != false && i < diskBlockMap.length) {
            i++;
        }

        if (i == diskBlockMap.length) {
            return -1; // error case, no available blocks in bitmap
        }

        this.setBlock(i);
        return i;
    }

    /*
     * setter method for when block is requested.
     * this method should never be called directly,
     * only called by findAvailableBlock() once it
     * is known which index is free
     */
    private void setBlock(int index) {
        diskBlockMap[index] = true;
    }

    public void removeBlock(int index) {
        diskBlockMap[index] = false;
    }

    public String toString()
    {
        String disk = "";
        for (int i = 0; i < diskBlockMap.length; i++)
            disk += (diskBlockMap[i] ? 1 : 0);
        return disk;
    }
}