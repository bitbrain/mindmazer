package de.bitbrain.mindmazer.levelgen;

import java.util.List;

public class LevelStage {

   private final List<Biom> biomes;
   private final int length;
   private final byte[][] completeData;
   private final byte[][] currentData;

   public LevelStage(List<Biom> biomes, int length, byte[][] completeData, byte[][] currentData) {
      this.biomes = biomes;
      this.length = length;
      this.completeData = completeData;
      this.currentData = currentData;
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

   public byte getCurrentCell(int indexX, int indexY) {
      return currentData[indexX][indexY];
   }

   public byte getCompleteCell(int indexX, int indexY) {
      return completeData[indexX][indexY];
   }

   public byte[][] getCompleteData() {
      return completeData;
   }
}
