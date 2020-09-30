package datastructures;

import datastructures.interfaces.IList;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class should contain all the tests you implement to verify that
 * your 'delete' method behaves as specified. You should give your tests
 * with a timeout of 1 second.
 *
 * This test extends the BaseTestDoubleLinkedList class. This means that
 * you can use the helper methods defined within BaseTestDoubleLinkedList.
 * @see BaseTestDoubleLinkedList
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDoubleLinkedListDelete extends BaseTestDoubleLinkedList {
    @Test(timeout=SECOND)
    public void testExample() {
        // Feel free to modify or delete this dummy test.
        assertTrue(true);
        assertEquals(3, 3);
    }

    @Test(timeout = SECOND)
    public void testDeleteMiddleElement() {
        IList<String> list = this.makeBasicList();
        list.add("d");
        list.add("e");
        assertEquals("b", list.delete(1));
        assertEquals("c", list.delete(1));
        assertEquals("d", list.delete(1));
        this.assertListValidAndMatches(new String[]{"a", "e"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteIndexOfAndDeleteMiddle() {
        IList<String> list = this.makeBasicList();
        list.add("d");
        list.add("e");
        assertEquals("b", list.delete(1));
        assertEquals(-1, list.indexOf("b"));
        assertEquals("c", list.delete(1));
        assertEquals(-1, list.indexOf("c"));
        assertEquals("d", list.delete(1));
        assertEquals(-1, list.indexOf("d"));
        this.assertListValidAndMatches(new String[]{"a", "e"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteUpdatesSize() {
        IList<String> list = this.makeBasicList();
        list.add("d");
        list.add("e");
        assertEquals("b", list.delete(1));
        assertEquals(4, list.size());
        assertEquals("c", list.delete(1));
        assertEquals(3, list.size());
        assertEquals("d", list.delete(1));
        assertEquals(2, list.size());
        this.assertListValidAndMatches(new String[]{"a", "e"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteFrontElement() {
        IList<String> list = this.makeBasicList();
        list.add("d");
        list.add("e");
        assertEquals("a", list.delete(0));
        assertEquals("b", list.delete(0));
        assertEquals("c", list.delete(0));
        this.assertListValidAndMatches(new String[]{"d", "e"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteBackElement() {
        IList<String> list = this.makeBasicList();
        list.add("d");
        list.add("e");
        assertEquals("e", list.delete(4));
        assertEquals("d", list.delete(3));
        assertEquals("c", list.delete(2));
        this.assertListValidAndMatches(new String[]{"a", "b"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteDuplicates() {
        IList<String> list = this.makeBasicList();
        for (int i = 0; i < 100; i++) {
            list.add("d");
        }
        for (int i = 102; i > 2; i--) {
            assertEquals("d", list.delete(i));
        }
        this.assertListValidAndMatches(new String[]{"a", "b", "c"}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteSingleElementList() {
        IList<String> list = this.makeBasicList();
        list.remove();
        list.remove();
        assertEquals("a", list.delete(0));
        this.assertListValidAndMatches(new String[]{}, list);
    }

    @Test(timeout = SECOND)
    public void testDeleteOutOfBoundsThrowsException() {
        IList<String> list = this.makeBasicList();
        try {
            list.delete(-1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // empty
        } catch (Exception e) {
            System.out.println("youre a dumbass, wrong exception thrown");
        }
        try {
            list.delete(100);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // empty
        } catch (Exception e) {
            System.out.println("youre a dumbass, wrong exception thrown");
        }

    }


    // Above are some examples of provided assert methods from JUnit,
    // but in these tests you will also want to use a custom assert
    // we have provided you in BaseTestDoubleLinkedList called
    // assertListValidAndMatches. It will check many properties of
    // your DoubleLinkedList so you will want to use it frequently.
    // For usage examples, you can refer to TestDoubleLinkedList,
    // and refer to BaseTestDoubleLinkedList for the method comment.
}
