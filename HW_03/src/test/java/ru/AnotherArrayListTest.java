package ru;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class AnotherArrayListTest {
    AnotherArrayList<String> anotherArrayList1 = new AnotherArrayList<>();
    AnotherArrayList<String> anotherArrayList2 = new AnotherArrayList<>();
    AnotherArrayList<String> anotherArrayList3 = new AnotherArrayList<>();
    String[] arr = new String[]{"nine", "ten", "eleven"};
    String[] arrForEquals = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "nine", "ten", "eleven"};
    String[] arrForCopy = new String[]{"1 string", "2 string", "3", "4", "5", "6", "7", "8"};
    String[] arrForSorting = new String[]{"0", "8", "999", "A", "a"};

    {
        anotherArrayList1.add("1");
        anotherArrayList1.add("2");
        anotherArrayList1.add("3");
        anotherArrayList1.add("4");
        anotherArrayList1.add("5");
        anotherArrayList1.add("6");
        anotherArrayList1.add("7");
        anotherArrayList1.add("8");
        anotherArrayList2.add("1 string");
        anotherArrayList2.add("2 string");
        anotherArrayList3.add("999");
        anotherArrayList3.add("0");
        anotherArrayList3.add("a");
        anotherArrayList3.add("A");
        anotherArrayList3.add("8");
    }

    @Test
    public void testCollectionsAddAll() {
        Collections.addAll(anotherArrayList1, "nine", "ten", "eleven");
        Assert.assertArrayEquals("Inner array and sample not equals!", arrForEquals, anotherArrayList1.toArray());

    }

    @Test
    public void testCollectionCopy() {
        Collections.copy(anotherArrayList1, anotherArrayList2);
        Assert.assertArrayEquals("Inner array and sample not equals!", arrForCopy, anotherArrayList1.toArray());
    }

    @Test
    public void testCollectionsSort() {
        ListIterator<String> listIteratorAnother = anotherArrayList3.listIterator();
        Collections.sort(anotherArrayList3, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Assert.assertArrayEquals("Inner array and sample not equals!", arrForSorting, anotherArrayList3.toArray());
    }
}
