package de.bitbrain.mindmazer.levelgen;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bitbrain.mindmazer.core.GameStats;

public class LevelGenerator {

   private static final Map<byte[], Float> BASIC_POOL = new HashMap<byte[], Float>();

   static {
     BASIC_POOL.put(BiomData.Simple.LINE_VERTICAL, 0.8f);
     BASIC_POOL.put(BiomData.Simple.LINE_HORIZONTAL, 0.8f);
     BASIC_POOL.put(BiomData.Simple.LINE_HORIZONTAL_LONG, 0.4f);
     BASIC_POOL.put(BiomData.Simple.LINE_VERTICAL_LONG, 0.4f);
     BASIC_POOL.put(BiomData.Simple.CURVE_BOTTOM_LEFT, 1f);
     BASIC_POOL.put(BiomData.Simple.CURVE_BOTTOM_RIGHT, 1f);
     BASIC_POOL.put(BiomData.Simple.CURVE_TOP_LEFT, 1f);
     BASIC_POOL.put(BiomData.Simple.CURVE_TOP_RIGHT, 1f);
   }

   private final BiomFactory factory = new BiomFactory();
   private final Seeder seeder = new Seeder();
   private final GameStats stats;
   
   public LevelGenerator(GameStats stats) {
   	this.stats = stats;
   }

   public LevelStage generateLevel(String seed) {
   	Gdx.app.log("LEVELGEN", "Generating level [seed=" + seed + "]");
   	seeder.regenerate(seed);
      List<Biom> biomes = new ArrayList<Biom>();
      List<Integer> absolutesX = new ArrayList<Integer>();
      List<Integer> absolutesY = new ArrayList<Integer>();

      int length = 0;
      int maxY = 0;
      int minX = 0;
      int maxX = 0;
      int offsetX = 0;
      int stages = (int) (2 + seeder.output().nextFloat() * stats.getStage() * .2f);

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
      return new LevelStage(seeder.string(), biomes, length, completeData, currentData, absolutesX, absolutesY);
   }

   private Map<byte[], Float> getStagedPool(int stage) {
      return BASIC_POOL;
   }

   private byte[] getRandomData(Map<byte[], Float> pool) {
      List<byte[]> keysAsArray = new ArrayList<byte[]>(pool.keySet());
      float probability = 0f;
      byte[] selectedBytes = null;
      while (seeder.output().nextFloat() > probability) {
         selectedBytes = keysAsArray.get(seeder.output().nextInt(keysAsArray.size()));
         probability = pool.get(selectedBytes);
      }
      return selectedBytes;
   }
}
