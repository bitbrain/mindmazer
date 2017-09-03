package de.bitbrain.mindmazer.levelgen;

public interface BiomData {
   
   interface Simple {
      
      byte[] LINE_UP = {
         1,
         1, 1
      };

      byte[] LINE_SIDE = {
              1, 1, 2
      };

      byte[] CURVE_BOTTOM_RIGHT = {
              1, 1,
              1, 0, 2
      };

      byte[] CURVE_BOTTOM_LEFT = {
              1, 1,
              0, 1, 2
      };

      byte[] CURVE_TOP_LEFT = {
              1, 0,
              1, 1, 2
      };

      byte[] CURVE_TOP_RIGHT = {
              0, 1,
              1, 1, 2
      };
   }

   interface Advanced {

      byte[] SPIKE_UP = {
              0, 1, 0,
              1, 1, 1, 3
      };

      byte[] CROSS = {
              0, 1, 0,
              1, 1, 1,
              0, 1, 0, 3
      };

      byte[] SWIRL = {
         1, 1, 1, 1,
         1, 0, 1, 0,
         1, 0, 0, 1,
         1, 1, 1, 1, 4
      };
   }
}
