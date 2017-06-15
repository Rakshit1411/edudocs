package com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.dummy;

import com.example.rakshitsharma.edutiate.Home.my_batches.batchdetailsactivity;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.MysubjectsRecyclerViewAdapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.subjectsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static int COUNT = 4;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

public static int index;
    public static DummyItem createDummyItem(int position) {
        index=position-1;
        return new DummyItem(String.valueOf(position), "Name : " + batchdetailsactivity.sub_names.get(index).toString(), makeDetails(position));
    }

    public static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
