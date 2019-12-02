import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

public class MultipleProducer {

    public static void main(String[] args) {
        new DelayedMultiProducer().start(100000);
    }

    static class DelayedMultiProducer {

        void start(final int count) {

            ExcerptAppender appender1 = createNewAppender();

            final Runnable simpleProducer = () -> produce(appender1, count, true);
            final Runnable delayedProducer = () -> produce(appender1, count, true);
            new Thread(simpleProducer).start();
            new Thread(delayedProducer).start();
        }

        void produce(final ExcerptAppender appender, final int count, final boolean addDelay) {
            for (int i = 0; i < count; i++) {
                // synchronized (appender){
                appender.writeText(" Index :  " + i);
                // }
            }

            if (addDelay) {
                addDelay();
            }
        }

        void addDelay() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                // No-Op lets swallow it
            }
        }

        ExcerptAppender createNewAppender() {
            String path = "queue";
            SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).build();
            return queue.acquireAppender();
        }
    }
}
