package de.bitbrain.mindmazer.levelgen;

public class Biom {

   private final byte[][] data;
   private final int startX, startY;
   private final int endX, endY;
   private final int length;

   public Biom(byte[][] data, int startX, int startY, int endX, int endY, int length) {
      this.data = data;
      this.startX = startX;
      this.startY = startY;
      this.endX = endX;
      this.endY = endY;
      this.length = length;
   }

   public int getLength() {
      return length;
   }

   public byte getCell(int indexX, int indexY) {
      if (indexX < 0 || indexX >= data.length) {
         return -1;
      }
      if (indexY < 0 || indexY >= data[indexX].length) {
         return -1;
      }
      return data[indexX][indexY];
   }

   public int getStartX() {
      return startX;
   }

   public int getStartY() {
      return startY;
   }

   public int getEndX() {
      return endX;
   }

   public int getEndY() {
      return endY;
   }
}
