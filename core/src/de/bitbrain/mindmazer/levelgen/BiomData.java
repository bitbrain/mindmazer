package de.bitbrain.mindmazer.levelgen;

public interface BiomData {
   
   interface Simple {
      
      byte[] LINE_VERTICAL = {
         1,
         1, 1
      };

      byte[] LINE_HORIZONTAL = {
              1, 1, 2
      };

      byte[] LINE_VERTICAL_LONG = {
              1,
              1,
              1,
              1, 1
      };

      byte[] LINE_HORIZONTAL_LONG = {
              1, 1, 1, 1, 4
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
}
