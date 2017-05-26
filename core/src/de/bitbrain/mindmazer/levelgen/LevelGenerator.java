package de.bitbrain.mindmazer.levelgen;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {

   private static final List<byte[]> BASIC_POOL = new ArrayList<byte[]>();

   static {
     BASIC_POOL.add(BiomData.Simple.LINE_UP);
     BASIC_POOL.add(BiomData.Simple.LINE_UP);
     BASIC_POOL.add(BiomData.Advanced.SNAKE_RIGHT);
     BASIC_POOL.add(BiomData.Advanced.SNAKE_LEFT);
     BASIC_POOL.add(BiomData.Advanced.LINE_UP);
   }

   private final BiomFactory factory = new BiomFactory();

   public LevelStage generateLevel(int stages) {
      List<Biom> biomes = new ArrayList<Biom>();
      List<Integer> absolutesX = new ArrayList<Integer>();
      List<Integer> absolutesY = new ArrayList<Integer>();

      int length = 0;
      int maxY = 0;
      int minX = 0;
      int maxX = 0;
      int offsetX = 0;

      // Calculate and position biomes
      for (int i = 0; i < stages; ++i) {

         Biom biom = factory.create(getRandomData(getStagedPool(i)));

         int diffX = biom.getEndX() - biom.getStartX();

         int biomLeft = offsetX - (biom.getWidth() - (biom.getWidth() - biom.getStartX()));
         int biomRight = offsetX + (biom.getWidth() - biom.getStartX());

         if (i == 0 || biomLeft < minX) {
            minX = biomLeft;
         }
         if (i == 0 || biomRight > maxX) {
            maxX = biomRight;
         }

         offsetX += diffX;
         absolutesX.add(biomLeft);
         absolutesY.add(maxY);

         maxY += biom.getHeight();
         length += biom.getLength();
         biomes.add(biom);
      }

      // Generate world
      int worldWidth = maxX - minX;
      int worldHeight = maxY;
      byte[][] completeData = new byte[worldWidth][worldHeight];
      byte[][] currentData = new byte[worldWidth][worldHeight];

      // Convert absolute offsets
      for (int i = 0; i < absolutesX.size(); ++i) {
         int convertedOffsetX = absolutesX.get(i) - minX;
         absolutesX.set(i, convertedOffsetX);
      }

      // Extract biome data into world
      for (int i = 0; i < biomes.size(); ++i) {
         Biom biom = biomes.get(i);
         for (int x = 0; x < biom.getWidth(); ++x) {
            for (int y = 0; y < biom.getHeight(); ++y) {
               int translatedX = absolutesX.get(i) + x;
               int translatedY = absolutesY.get(i) + y;
               completeData[translatedX][translatedY] = biom.getCell((biom.getWidth() - 1) - x, y);
            }
         }
      }
      return new LevelStage(biomes, length, completeData, currentData, absolutesX, absolutesY);
   }

   private List<byte[]> getStagedPool(int stage) {
      // TODO Depending on stage change the pool
      return BASIC_POOL;
   }

   private byte[] getRandomData(List<byte[]> pool) {
      int index = (int) (Math.random() * pool.size());
      return pool.get(index);
   }
}
