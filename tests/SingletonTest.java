import model.GameSettings;
import org.junit.Test;
import util.LoadResources;

import static org.junit.Assert.assertEquals;

public class SingletonTest {
    @Test
    public void testSingletons() {
        GameSettings settingsA = GameSettings.getInstance();
        GameSettings settingsB = GameSettings.getInstance();

        assertEquals(settingsA, settingsB);

        LoadResources resourcesA = LoadResources.getInstance();
        LoadResources resourcesB = LoadResources.getInstance();

        assertEquals(resourcesA, resourcesB);
    }
}
