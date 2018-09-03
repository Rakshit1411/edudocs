package com.example.rakshitsharma.edutiate.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rakshit on 10/17/2017.
 */

public class teacher_dummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<teacher_dummyContent.DummyItem> ITEMS = new ArrayList<teacher_dummyContent.DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, teacher_dummyContent.DummyItem> ITEM_MAP = new HashMap<String, teacher_dummyContent.DummyItem>();

    private static final int COUNT = teacher_loadingData1.teacher_subCode.size();


    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(teacher_dummyContent.DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static teacher_dummyContent.DummyItem createDummyItem(int position) {
        return new teacher_dummyContent.DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String name;
        public final String text;
        public final String date;

        public DummyItem(String name, String text, String date) {
            this.name = name;
            this.text = text;
            this.date = date;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
