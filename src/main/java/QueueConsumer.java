import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

public class QueueConsumer {

    public static void main(String[] args) {
        String path = "queue";
        SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).readOnly(true).build();
        ExcerptTailer tailer = queue.createTailer();


        System.out.println("Queue Consumer Started - waiting for producer");

        while (true) {
            String text = tailer.readText();

            if (text == null)
                Jvm.pause(10);
            else
                System.out.println( text);
        }
    }
}
