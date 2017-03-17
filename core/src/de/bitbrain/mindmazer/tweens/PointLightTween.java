package de.bitbrain.mindmazer.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import box2dLight.PointLight;

public class PointLightTween implements TweenAccessor<PointLight> {
   
   public static final int DISTANCE = 1;
   public static final int POS_X = 2;
   public static final int POS_Y = 3;

   @Override
   public int getValues(PointLight from, int type, float[] args) {
      switch (type) {
         case DISTANCE:
            args[0] = from.getDistance();
            return 1;
         case POS_X:
            args[0] = from.getX();
            return 1;
         case POS_Y:
            args[0] = from.getY();
            return 1;
      }
      return 0;
   }

   @Override
   public void setValues(PointLight to, int type, float[] args) {
      switch (type) {
         case DISTANCE:
            to.setDistance(args[0]);
            break;
         case POS_X:
            to.setPosition(args[0], to.getY());
            break;
         case POS_Y:
            to.setPosition(to.getX(), args[0]);
            break;
      }
   }

}
