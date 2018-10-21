package memoryLeaks;

import java.util.ArrayList;
import java.util.List;

public class AddMoreThanRemoveList extends MemoryLeaks {
    public AddMoreThanRemoveList(String name) {
        super(name);
    }

    @Override
    public void run() {
        List<String> list = new ArrayList<>();

        while (true) {
            for (int i = 0; i < 900000; i++) {
                list.add("abcdef");
            }
            for (int i = 0; i < 10000; ) {
                list.remove(0);
            }
        }
    }
}
