package de.bitbrain.mindmazer.levelgen;

public class BiomFactory {

   public Biom create(byte[] biomData) {
      int columns = biomData[biomData.length - 1];
      int rows = (biomData.length - 1) / columns;
      byte[][] data = new byte[columns][rows];
      int xCounter = 0;
      int yCounter = 0;
      int startX = -1;
      int endX = -1;
      int endY = -1;
      int length = 0;
      for (int index = 0; index < biomData.length - 1; ++index) {
         if (endX < 0 && biomData[index] > 0) {
            endX = xCounter;
            endY = yCounter;
            length++;
         } else if (biomData[index] > 0) {
            startX = xCounter;
            length++;
         }
         data[xCounter][yCounter] = biomData[index];
         if (++xCounter >= columns) {
            xCounter = 0;
            yCounter++;
         }
      }
      return new Biom(data, startX, 0, endX, endY + rows - 1, columns, rows, length);
   }
}
