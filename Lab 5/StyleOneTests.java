import org.junit.*;

import static org.junit.Assert.*;

public class StyleOneTests {
    EmbeddedSystem es;
    @Before
    public void initialize() {
        es = new EmbeddedSystem(53, 66);
    }

    @Test
    public void humidityTest() {
        es.reset();
        es.setHumidity(53);
        es.setHumidity(51);
        es.setHumidity(48);
        es.setHumidity(49);
        es.setHumidity(54);
        es.setHumidity(56);
        es.setHumidity(56);

        assertEquals(56, es.currentHumidity);
        assertEquals(56, es.maxHumidity);
        assertEquals(48, es.minHumidity);
        assertEquals("STABLE", es.humidityTrend);
        assertEquals("HIGH", es.humidityStatus);
    }

    @Test
    public void tempTest() {
        es.reset();
        es.setTemp(66);
        es.setTemp(68);
        es.setTemp(69);
        es.setTemp(67);
        es.setTemp(63);
        es.setTemp(59);
        es.setTemp(53);

        assertEquals(53, es.currentTemp);
        assertEquals(69, es.maxTemp);
        assertEquals(53, es.minTemp);
        assertEquals("DECREASING", es.tempTrend);
    }
}
