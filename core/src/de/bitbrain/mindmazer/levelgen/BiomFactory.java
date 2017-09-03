package de.bitbrain.mindmazer.levelgen;

public class BiomFactory {

   public Biom create(byte[] biomData) {
      int columns = biomData[biomData.length - 1];
      int rows = (biomData.length - 1) / columns;
      return new Biom(
         convertToBiomBytes(biomData, columns, rows),
         startX(biomData, columns, rows),
         startY(biomData, columns, rows),
         endX(biomData, columns, rows),
         endY(biomData, columns, rows),
         columns,
         rows,
         length(biomData, columns, rows)
      );
   }

   private byte[][] convertToBiomBytes(byte[] biomData, int columns, int rows) {
      byte[][] data = new byte[columns][rows];

      int xIndex = 0;
      int yIndex = 0;
      for (int i = 0; i < biomData.length - 1; ++i) {
         data[xIndex][yIndex] = biomData[i];
         xIndex++;
         if (xIndex >= columns) {
            xIndex = 0;
            yIndex++;
         }
      }

      return data;
   }

   private int startX(byte[] biomData, int columns, int rows) {
      int startX = 0;
      int startIndex = columns * rows - columns;
      // Find the last cell in the second-last row
      int upIndex = -1;
      if (rows > 1) {
         for (int i = columns * rows - columns * 2, xCounter = 0; i < columns * rows - columns; ++i, xCounter++) {
            if (biomData[i] == 1) {
               upIndex = xCounter;
            }
         }
      }
      for (int i = startIndex, xIndex = 0; i < startIndex + columns; ++i, xIndex++) {
         int cell = biomData[i];
         if (cell == 1) {
            startX = xIndex;
            if (startX < upIndex) {
               return startX;
            }
         }
      }
      return startX;
   }

   // We always start from the bottom -> 0
   private int startY(byte[] biomData, int columns, int rows) {
      return 0;
   }

   private int endX(byte[] biomData, int columns, int rows) {
      int xIndex = -1;
      // Find the first cell in the second-last row
      int downIndex = -1;
      if (rows > 1) {
         for (int i = columns, xCounter = 0; i < columns * 2; ++i, xCounter++) {
            if (biomData[i] == 1) {
               downIndex = xCounter;
               break;
            }
         }
      }
      int endX = 0;
      for (int i = 0; i < columns; ++i) {
         xIndex++;
         int cell = biomData[i];
         if (cell == 1) {
            endX = xIndex;
            if (rows == 1 || xIndex < downIndex) {
               break;
            }
         }
      }
      return endX;
   }

   private int endY(byte[] biomData, int columns, int rows) {
      return rows - 1;
   }

   private int length(byte[] biomData, int columns, int rows) {
      int length = 0;
      for (int index = 0; index < biomData.length - 1; ++index) {
         if (biomData[index] == 1) {
            length++;
         }
      }
      return length;
   }
}
