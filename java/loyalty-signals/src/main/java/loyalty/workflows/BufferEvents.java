package loyalty.workflows;

import java.util.ArrayList;

public class BufferEvents {
    public final ArrayList<Event> bufferEvents = new ArrayList<>();

    public void add(Event e) {
        bufferEvents.add(e);
    }

    public Event remove(int i) {
        return bufferEvents.remove(i);
    }

    public int size() {
        return bufferEvents.size();
    }

    public void clear() {
        bufferEvents.clear();
    }
}
