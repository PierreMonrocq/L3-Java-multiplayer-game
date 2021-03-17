import model.map.VisibleElement;
import org.junit.Test;
import util.VisibleType;
import util.proxy.ProxyVisibleElement;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ProxyVisibleElementTest {
    @Test
    public void testProxy() {
        ProxyVisibleElement proxy = new ProxyVisibleElement();
        Random random = new Random();

        for(int i = 0; i < 100; ++i) {
            // playerIds are assumed to be != 0.
            int playerId = 1 + random.nextInt(100);
            int otherPlayerId = playerId + 1 + random.nextInt(15);

            VisibleElement bomb = new VisibleElement(VisibleType.BOMB, playerId);
            VisibleElement mine = new VisibleElement(VisibleType.MINE, otherPlayerId);
            VisibleElement player = new VisibleElement(VisibleType.PLAYER, playerId);

            VisibleElement proxyBombShown = proxy.displayElement(bomb, playerId);
            VisibleElement proxyBombHidden = proxy.displayElement(bomb, otherPlayerId);

            VisibleElement proxyMineShown = proxy.displayElement(mine, otherPlayerId);
            VisibleElement proxyMineHidden = proxy.displayElement(mine, playerId);

            VisibleElement proxyPlayerA = proxy.displayElement(player, playerId);
            VisibleElement proxyPlayerB = proxy.displayElement(player, otherPlayerId);

            assertEquals(proxyBombShown.getType(), VisibleType.BOMB);
            assertEquals(proxyBombHidden.getType(), VisibleType.EMPTY);
            assertEquals(proxyMineShown.getType(), VisibleType.MINE);
            assertEquals(proxyMineHidden.getType(), VisibleType.EMPTY);
            assertEquals(proxyPlayerA.getType(), VisibleType.PLAYER);
            assertEquals(proxyPlayerB.getType(), VisibleType.PLAYER);
        }
    }
}
