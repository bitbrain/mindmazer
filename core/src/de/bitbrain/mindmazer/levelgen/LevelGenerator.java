package de.bitbrain.mindmazer.levelgen;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class LevelGenerator {
   
   private static final List<byte[]> BASIC_POOL = Lists.newArrayList(
         BiomData.Basic.LINE_UP, 
         BiomData.Basic.SNAKE_LEFT,
         BiomData.Basic.SNAKE_RIGHT
   );

   private final BiomFactory factory = new BiomFactory();

   public LevelStage generateLevel() {
      final int stages = 3;
      List<Biom> biomes = new ArrayList<Biom>();
      int length = 0;
      byte[][] completeData = null; // TODO
      byte[][] currentData = null; // TODO
      for (int i = 0; i < stages; ++i) {
         Biom biom = factory.create(getRandomData(getStagedPool(i)));
         length += biom.getLength();
         biomes.add(biom);
      }
      return new LevelStage(biomes, length, completeData, currentData);
   }

   private List<byte[]> getStagedPool(int stage) {
      // TODO Depending on stage change the pool
      return BASIC_POOL;
   }

   private byte[] getRandomData(List<byte[]> pool) {
      return pool.get((int) (Math.random() * pool.size()));
   }
}
