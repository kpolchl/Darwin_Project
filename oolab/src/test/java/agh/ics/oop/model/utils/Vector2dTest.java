package agh.ics.oop.model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    @Test
    void testAreVectorsEqual() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(1,3);

        assertEquals(v1, v2);
        assertNotEquals(v2, v3);
    }

    @Test
    void testChangeVectorToString() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(0,0);

        assertEquals(v1.toString(),"(1,2)");
        assertEquals(v2.toString(),"(0,0)");
    }

    @Test
    void testPrecedesu() {
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(5,5);
        Vector2d v3 = new Vector2d(4,5);
        Vector2d v4 = new Vector2d(5,4);
        Vector2d v5 = new Vector2d(4,4);
        Vector2d v6 = new Vector2d(6,5);
        Vector2d v7 = new Vector2d(5,6);
        Vector2d v8 = new Vector2d(6,6);

        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v6));
        assertTrue(v1.precedes(v7));
        assertTrue(v1.precedes(v8));
        assertFalse(v1.precedes(v3));
        assertFalse(v1.precedes(v4));
        assertFalse(v1.precedes(v5));
    }

    @Test
    void testFollowowania() {
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(5,5);
        Vector2d v3 = new Vector2d(4,5);
        Vector2d v4 = new Vector2d(5,4);
        Vector2d v5 = new Vector2d(4,4);
        Vector2d v6 = new Vector2d(6,5);
        Vector2d v7 = new Vector2d(5,6);
        Vector2d v8 = new Vector2d(6,6);

        assertTrue(v1.follows(v2));
        assertTrue(v1.follows(v3));
        assertTrue(v1.follows(v4));
        assertTrue(v1.follows(v5));
        assertFalse(v1.follows(v6));
        assertFalse(v1.follows(v7));
        assertFalse(v1.follows(v8));

    }

    @Test
    void testAddingVector() {
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(5,5);
        Vector2d v3 = new Vector2d(10,10);
        Vector2d v4 = new Vector2d(-3,-5);
        Vector2d v5 = new Vector2d(2,0);

        assertEquals(v1.add(v1), v3);
        assertEquals(v1.add(v2), v3);
        assertEquals(v1.add(v4), v5);
    }

    @Test
    void testSubtractionVector() {
        //given
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(5,5);
        Vector2d v3 = new Vector2d(0,0);
        Vector2d v4 = new Vector2d(-3,-5);
        Vector2d v5 = new Vector2d(8,10);
        //when
        Vector2d v6 = v1.subtract(v2);
        Vector2d v7 = v1.subtract(v4);
        //then
        assertEquals(v3, v6);
        assertEquals(v5, v7);
    }

    @Test
    void testUpperRightFunc() {
        //given
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(-1,5);
        Vector2d v3 = new Vector2d(5,-1);
        Vector2d v4 = new Vector2d(-1,-1);
        //when
        Vector2d v5 = v1.upperRight(v1);
        Vector2d v6 = v1.upperRight(v2);
        Vector2d v7 = v1.upperRight(v3);
        Vector2d v8 = v1.upperRight(v4);
        //then
        assertEquals(v1, v5);
        assertEquals(v1, v6);
        assertEquals(v1, v7);
        assertEquals(v1, v8);
    }

    @Test
    void testLowerLeftFunc() {
        //given
        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(-1,5);
        Vector2d v3 = new Vector2d(5,-1);
        Vector2d v4 = new Vector2d(-1,-1);
        //when
        Vector2d v5 = v4.lowerLeft(v4);
        Vector2d v6 = v4.lowerLeft(v3);
        Vector2d v7 = v4.lowerLeft(v2);
        Vector2d v8 = v4.lowerLeft(v1);
        //then
        assertEquals(v4, v5);
        assertEquals(v4, v6);
        assertEquals(v4, v7);
        assertEquals(v4, v8);
    }

    @Test
    void testOppositeFunc() {
        //given
        Vector2d v1 = new Vector2d(5,-5);
        Vector2d v2 = new Vector2d(-5,5);
        Vector2d v3 = new Vector2d(5,5);
        //when
        Vector2d v4 = v1.opposite();
        Vector2d v5 = v3.opposite();
        //then
        assertEquals(v4, v2);
        assertNotEquals(v4, v5);
    }

    @Test
    void testHash() {
        Vector2d v1 = new Vector2d(5,5);
        System.out.println(v1.hashCode());
    }

}