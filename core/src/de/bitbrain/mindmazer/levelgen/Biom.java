package de.bitbrain.mindmazer.levelgen;

public class Biom {

   final byte[][] data;
   private final int startX, startY;
   private final int height, width;
   private final int endX, endY;
   private final int length;

   public Biom(byte[][] data, int startX, int startY, int endX, int endY, int width, int height, int length) {
      this.data = data;
      this.startX = startX;
      this.startY = startY;
      this.endX = endX;
      this.endY = endY;
      this.length = length;
      this.width = width;
      this.height = height;
   }

   public int getLength() {
      return length;
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
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
