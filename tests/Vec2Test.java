import org.junit.Test;
import util.Vec2;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Vec2Test {
    @Test
    public void testAdds() {
        Random random = new Random();

        for(int i = 0; i < 100; ++i) {
            Vec2 a = new Vec2(random.nextInt(1000), random.nextInt(1000));
            Vec2 b = new Vec2(random.nextInt(1000), random.nextInt(1000));

            Vec2 sum = Vec2.sum(a, b);
            assertEquals(sum.getX(), a.getX() + b.getX());
            assertEquals(sum.getY(), a.getY() + b.getY());

            Vec2 oldA = new Vec2(a);
            a.add(b);
            assertEquals(a.getX(), oldA.getX() + b.getX());
            assertEquals(a.getY(), oldA.getY() + b.getY());
        }
    }

    @Test
    public void testSubs() {
        Random random = new Random();

        for(int i = 0; i < 100; ++i) {
            Vec2 a = new Vec2(random.nextInt(1000), random.nextInt(1000));
            Vec2 b = new Vec2(random.nextInt(1000), random.nextInt(1000));

            Vec2 oldA = new Vec2(a);
            a.sub(b);
            assertEquals(a.getX(), oldA.getX() - b.getX());
            assertEquals(a.getY(), oldA.getY() - b.getY());
        }
    }
}
