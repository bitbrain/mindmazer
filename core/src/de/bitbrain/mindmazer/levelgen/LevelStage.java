package de.bitbrain.mindmazer.levelgen;

import java.util.List;

import de.bitbrain.mindmazer.Config;

public class LevelStage {

   private final List<Biom> biomes;
   private final int length;
   private final byte[][] completeData;
   private byte[][] currentData;
   private final List<Integer> offsetsX;
   private final List<Integer> offsetsY;

   public LevelStage(List<Biom> biomes, int length, byte[][] completeData, byte[][] currentData, List<Integer> offsetsX,
         List<Integer> offsetsY) {
      this.biomes = biomes;
      this.length = length;
      this.completeData = completeData;
      this.currentData = currentData;
      this.offsetsX = offsetsX;
      this.offsetsY = offsetsY;
   }

   public int getAbsoluteStartOffsetX(int index) {
      return getBiom(index).getStartX() + offsetsX.get(index);
   }

   public int getAbsoluteStartOffsetY(int index) {
      return getBiom(index).getStartY() + offsetsY.get(index);
   }

   public int convertToIndexX(float x) {
      return (int) (x / Config.TILE_SIZE);
   }

   public int convertToIndexY(float y) {
      return (int) (y / Config.TILE_SIZE);
   }

   public int getLength() {
      return length;
   }

   public Biom getBiom(int index) {
      return biomes.get(index);
   }

   public int getNumberOfBiomes() {
      return biomes.size();
   }

   public int getLastCellX() {
      Biom biom = getBiom(getNumberOfBiomes() - 1);
      int x = biom.getEndX();
      return offsetsX.get(getNumberOfBiomes() - 1) + x;
   }

   public int getLastCellY() {
      Biom biom = getBiom(getNumberOfBiomes() - 1);
      int y = biom.getEndY();
      return offsetsY.get(getNumberOfBiomes() - 1) + y;
   }

   public byte getCurrentCell(int indexX, int indexY) {
      if (indexX < 0 || indexY < 0) {
         return 0;
      }
      if (indexX >= currentData.length || indexY >= currentData[0].length) {
         return 0;
      }
      return currentData[indexX][indexY];
   }

   public byte getCompleteCell(int indexX, int indexY) {
      if (indexX < 0 || indexY < 0) {
         return 0;
      }
      if (indexX >= completeData.length || indexY >= completeData[0].length) {
         return 0;
      }
      return completeData[indexX][indexY];
   }

   public byte[][] getCompleteData() {
      return completeData;
   }

   public byte[][] getCurrentData() {
      return currentData;
   }

   public void setCurrentData(int indexX, int indexY, byte value) {
      if (indexX < 0 || indexY < 0) {
         return;
      }
      if (indexX >= currentData.length || indexY >= currentData[0].length) {
         return;
      }
      currentData[indexX][indexY] = value;
   }

   public void resetCurrentData() {
      currentData = new byte[completeData.length][completeData[0].length];
   }
}
