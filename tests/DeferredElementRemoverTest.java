import org.junit.Test;
import util.DeferredElementRemover;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DeferredElementRemoverTest {
    @Test
    public void testDeferredRemoval() {
        ArrayList<Integer> array = new ArrayList<>();
        DeferredElementRemover<Integer> remover = new DeferredElementRemover<>(array);

        Random random = new Random();

        for(int i = 0; i < 100; ++i) {
            int addCount = random.nextInt(1000);

            for(int add = 0; add < addCount; ++add) {
                array.add(random.nextInt());
            }

            for(Integer it : array) {
                remover.add(it);
            }

            assertEquals(array.size(), addCount);

            remover.enactRemovals();

            assertEquals(array.size(), 0);
        }
    }
}
