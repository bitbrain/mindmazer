package de.bitbrain.mindmazer.levelgen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BiomFactoryTest {

   private BiomFactory biomFactory;

   @Before
   public void beforeTest() {
      biomFactory = new BiomFactory();
   }

   @Test
   public void testSimpleShape() {
      byte[] simpleShape = {
        1,
        1,
        1, 1
      };
      Biom biom = biomFactory.create(simpleShape);
      Assert.assertEquals(0, biom.getEndX());
      Assert.assertEquals(2, biom.getEndY());
      Assert.assertEquals(0, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(1, biom.getWidth());
      Assert.assertEquals(3, biom.getHeight());
      Assert.assertEquals(3, biom.getLength());
   }

   @Test
   public void testHorizontalShape() {
      byte[] horizontalShape = {
              1, 1, 1, 3
      };
      Biom biom = biomFactory.create(horizontalShape);
      Assert.assertEquals(0, biom.getEndX());
      Assert.assertEquals(0, biom.getEndY());
      Assert.assertEquals(2, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(3, biom.getWidth());
      Assert.assertEquals(1, biom.getHeight());
      Assert.assertEquals(3, biom.getLength());
   }

   @Test
   public void testBiomData() {
      byte[] biomData = {
              1, 0,
              1, 1,
              0, 1, 2
      };
      byte[][] expectedBiomData = {
              {1, 1, 0},
              {0, 1, 1}
      };
      Biom biom = biomFactory.create(biomData);
      Assert.assertArrayEquals(expectedBiomData, biom.data);
   }

   @Test
   public void testSnakeLeftShape() {
      byte[] shapeLeftShape = {
              1, 0,
              1, 1,
              0, 1, 2
      };
      Biom biom = biomFactory.create(shapeLeftShape);
      Assert.assertEquals(0, biom.getEndX());
      Assert.assertEquals(2, biom.getEndY());
      Assert.assertEquals(1, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(2, biom.getWidth());
      Assert.assertEquals(3, biom.getHeight());
      Assert.assertEquals(4, biom.getLength());
   }

   @Test
   public void testLLeftShape() {
      byte[] lShape = {
              1, 1, 1,
              0, 0, 1, 3
      };
      Biom biom = biomFactory.create(lShape);
      Assert.assertEquals(0, biom.getEndX());
      Assert.assertEquals(1, biom.getEndY());
      Assert.assertEquals(2, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(3, biom.getWidth());
      Assert.assertEquals(2, biom.getHeight());
      Assert.assertEquals(4, biom.getLength());
   }

   @Test
   public void testLRightShape() {
      byte[] lShape = {
              1, 1, 1,
              1, 0, 0, 3
      };
      Biom biom = biomFactory.create(lShape);
      Assert.assertEquals(2, biom.getEndX());
      Assert.assertEquals(1, biom.getEndY());
      Assert.assertEquals(0, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(3, biom.getWidth());
      Assert.assertEquals(2, biom.getHeight());
      Assert.assertEquals(4, biom.getLength());
   }

   @Test
   public void testMiddleShape() {
      byte[] lShape = {
              0, 1, 1,
              1, 1, 0,
              1, 0, 0,
              1, 1, 1, 3
      };
      Biom biom = biomFactory.create(lShape);
      Assert.assertEquals(2, biom.getEndX());
      Assert.assertEquals(3, biom.getEndY());
      Assert.assertEquals(2, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(3, biom.getWidth());
      Assert.assertEquals(4, biom.getHeight());
      Assert.assertEquals(8, biom.getLength());
   }

   @Test
   public void testSmallLeftShape() {
      byte[] lShape = {
              0, 1,
              1, 1, 2
      };
      Biom biom = biomFactory.create(lShape);
      Assert.assertEquals(1, biom.getEndX());
      Assert.assertEquals(1, biom.getEndY());
      Assert.assertEquals(0, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(2, biom.getWidth());
      Assert.assertEquals(2, biom.getHeight());
      Assert.assertEquals(3, biom.getLength());
   }

   @Test
   public void testSmallUpShape() {
      byte[] lShape = {
              1, 1,
              1, 0, 2
      };
      Biom biom = biomFactory.create(lShape);
      Assert.assertEquals(1, biom.getEndX());
      Assert.assertEquals(1, biom.getEndY());
      Assert.assertEquals(0, biom.getStartX());
      Assert.assertEquals(0, biom.getStartY());
      Assert.assertEquals(2, biom.getWidth());
      Assert.assertEquals(2, biom.getHeight());
      Assert.assertEquals(3, biom.getLength());
      }
}
