package seedu.ezdo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static seedu.ezdo.testutil.TestUtil.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import seedu.ezdo.commons.core.UnmodifiableObservableList;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;

    @Before
    public void setUp() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(new PriorityQueue<Number>()));
        Collection treeSet = new TreeSet<Number>();
        assertThrows(ex, () -> list.setAll(treeSet));
        assertThrows(ex, () -> list.setAll(1, 2));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final Iterator<Integer> iter2 = list.iterator();
        iter2.forEachRemaining(item -> {
            assertTrue(item.equals(10));
        });

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
        if (liter.hasPrevious()) {
            Integer a = liter.previous();
            assertTrue(a == 10);
        }

        list.forEach(item -> {
            assertTrue(item.equals(10));
        });

        liter.forEachRemaining(item -> {
            assertTrue(item.equals(10));
        });
    }
    //@@author A0139248X
    @Test
    public void contains_true() {
        assertTrue(list.contains(10));
        assertTrue(list.containsAll(new ArrayList<Integer>(10)));
    }

    @Test
    public void subList_equals() {
        assertTrue(list.subList(0, 1).containsAll(new ArrayList<Integer>(10)));
    }

    @Test
    public void indexOf_correct() {
        assertTrue(0 == list.indexOf(10));
    }

    @Test
    public void hashCode_equals() {
        List<Integer> backing;
        UnmodifiableObservableList<Integer> list;
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
        assertEquals(list.hashCode(), this.list.hashCode());
    }

    @Test
    public void toArray_correct() {
        Integer[] arr1 = list.toArray(new Integer[1]);
        Integer[] arr2 = new Integer[]{10};
        for (int i = 0; i < arr1.length; i++) {
            assertTrue(arr1[i].equals(arr2[i]));
        }
    }
    //@@author
    @Test
    public void add_remove_listeners() {
        list.removeListener((ListChangeListener<Integer>) (c -> { /* ... */ } ));
        list.addListener((InvalidationListener) (c -> { /* ... */ } ));
        list.removeListener((InvalidationListener) (c -> { /* ... */ } ));
    }
}
