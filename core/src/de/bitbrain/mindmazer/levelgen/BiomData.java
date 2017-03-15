package de.bitbrain.mindmazer.levelgen;

public interface BiomData {
   
   interface Simple {
      
      byte[] LINE_UP = {
         1,
         1, 1
      };
   }

   interface Advanced {

      byte[] LINE_UP = {
            1,
            1, 
            1, 1
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
   }
}
