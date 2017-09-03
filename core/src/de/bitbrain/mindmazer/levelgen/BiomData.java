package de.bitbrain.mindmazer.levelgen;

public interface BiomData {
   
   interface Simple {
      
      byte[] LINE_UP = {
         1,
         1, 1
      };

      byte[] SIDE_RIGHT = {
              1, 1, 1,
              1, 0, 0, 3
      };

      byte[] SNAKE_LEFT = {
              1, 0, 0,
              1, 1, 1,
              0, 0, 1, 3
      };

      byte[] SNAKE_RIGHT = {
              0, 0, 1,
              1, 1, 1,
              1, 0, 0, 3
      };

      byte[] LINE_SIDE = {
              1, 1, 1, 3
      };
   }

   interface Advanced {
      
      byte[] SNAKE_LEFT = {
            1, 0, 0,
            1, 1, 1, 
            0, 0, 1, 3
      };
      
      byte[] SNAKE_RIGHT = {
            0, 0, 1,
            1, 1, 1, 
            1, 0, 0, 3
      };
      
      byte[] SUPER_SNAKE = {
            0, 1, 0,
            1, 1, 0,
            1, 0, 0,
            1, 1, 0,
            0, 1, 0, 3
      };
      
      byte[] STAIR_LEFT = {
            1, 0, 0,
            1, 1, 0,
            0, 1, 1,
            0, 0, 1, 3
      };
      
      byte[] STAIR_RIGHT = {
            0, 0, 1,
            0, 1, 1,
            1, 1, 0,
            1, 0, 0, 3
      };
   }
}
