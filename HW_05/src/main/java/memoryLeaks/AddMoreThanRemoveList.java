package memoryLeaks;

import java.util.ArrayList;
import java.util.List;

public class AddMoreThanRemoveList extends MemoryLeaks {
    public AddMoreThanRemoveList(String name) {
        super(name);
    }

    @Override
    public void run() {
        List<String> list = new ArrayList<>(70000);

        while (true) {
            for (int i = 0; i < 60000; i++) {
                list.add("abc");
            }
            for (int i = 0; i < 9000; i++) {
                list.remove(list.size() - 1);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
