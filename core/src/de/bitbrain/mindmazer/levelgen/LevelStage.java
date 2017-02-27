package de.bitbrain.mindmazer.levelgen;

import java.util.List;

import de.bitbrain.braingdx.behavior.movement.TiledCollisionResolver;
import de.bitbrain.braingdx.world.GameObject;

public class LevelStage implements TiledCollisionResolver {

   private final List<Biom> biomes;
   private final int length;
   private final byte[][] completeData;
   private final byte[][] currentData;
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

   @Override
   public boolean isCollision(int x, int y, int layer) {
      return false;
   }

   @Override
   public boolean isCollision(float x, float y, int layer) {
      return false;
   }

   @Override
   public boolean isCollision(GameObject object, int xOffset, int yOffset) {
      return false;
   }
}
