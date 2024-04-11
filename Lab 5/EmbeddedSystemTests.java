import org.junit.*;

import static org.junit.Assert.*;

public class EmbeddedSystemTests {
    EmbeddedSystem es;

    @Before
    public void initialize() {
        es = new EmbeddedSystem(30, 30);
    }

    //Humidity Tests
    //Current Test
    @Test
    public void currentHumidityTest() {
        es.setHumidity(43);
        es.setHumidity(50);
        assertEquals(50, es.currentHumidity);
    }

    //Max Tests
    @Test
    public void inputNewHumidityMax() {
        es.setHumidity(43);
        es.setHumidity(55);
        assertEquals(55, es.maxHumidity);
    }
    @Test
    public void inputSmallerHumidity() {
        es.setHumidity(29);
        es.setHumidity(20);
        assertEquals(30, es.maxHumidity);
    }
    @Test
    public void inputNewHumidityMaxAndNewValue() {
        es.setHumidity(43);
        es.setHumidity(55);
        es.setHumidity(20);
        assertEquals(55, es.maxHumidity);
    }

    //Min Tests
    @Test
    public void inputNewHumidityMin() {
        es.setHumidity(43);
        es.setHumidity(20);
        assertEquals(20, es.minHumidity);
    }
    @Test
    public void inputBiggerHumidity() {
        es.setHumidity(43);
        es.setHumidity(55);
        assertEquals(30, es.minHumidity);
    }
    @Test
    public void inputNewHumidityMinAndNewValue() {
        es.setHumidity(43);
        es.setHumidity(20);
        es.setHumidity(55);
        assertEquals(20, es.minHumidity);
    }

    //Trend Tests
    @Test
    public void stableHumidity() {
        es.setHumidity(30);
        es.setHumidity(30);
        assertEquals("STABLE", es.humidityTrend);
    }
    @Test
    public void risingHumidity() {
        es.setHumidity(40);
        es.setHumidity(55);
        assertEquals("INCREASING", es.humidityTrend);
    }
    @Test
    public void decreasingHumidity() {
        es.setHumidity(25);
        es.setHumidity(20);
        assertEquals("DECREASING", es.humidityTrend);
    }

    //Status Tests
    @Test
    public void okHumidityHigherBound() {
        es.setHumidity(56);
        es.setHumidity(55);
        assertEquals("OK", es.humidityStatus);
    }
    @Test
    public void okHumidityLowerBound() {
        es.setHumidity(24);
        es.setHumidity(25);
        assertEquals("OK", es.humidityStatus);
    }
    @Test
    public void highHumidityHigherBound() {
        es.setHumidity(55);
        es.setHumidity(100);
        assertEquals("HIGH", es.humidityStatus);
    }
    @Test
    public void highHumidityLowerBound() {
        es.setHumidity(55);
        es.setHumidity(56);
        assertEquals("HIGH", es.humidityStatus);
    }
    @Test
    public void lowHumidityHigherBound() {
        es.setHumidity(25);
        es.setHumidity(24);
        assertEquals("LOW", es.humidityStatus);
    }
    @Test
    public void lowHumidityLowerBound() {
        es.setHumidity(25);
        es.setHumidity(0);
        assertEquals("LOW", es.humidityStatus);
    }

    //Temp Tests
    //Current Test
    @Test
    public void currentTempTest() {
        es.setTemp(43);
        es.setTemp(40);
        assertEquals(40, es.currentTemp);
    }

    //Max Tests
    @Test
    public void inputNewTempMax() {
        es.setTemp(43);
        es.setTemp(67);
        assertEquals(67, es.maxTemp);
    }
    @Test
    public void inputSmallerTemp() {
        es.setTemp(29);
        es.setTemp(13);
        assertEquals(30, es.maxTemp);
    }
    @Test
    public void inputNewTempMaxAndNewValue() {
        es.setTemp(43);
        es.setTemp(67);
        es.setTemp(13);
        assertEquals(67, es.maxTemp);
    }

    //Min Tests
    @Test
    public void inputNewTempMin() {
        es.setTemp(43);
        es.setTemp(13);
        assertEquals(13, es.minTemp);
    }
    @Test
    public void inputBiggerTemp() {
        es.setTemp(43);
        es.setTemp(67);
        assertEquals(30, es.minTemp);
    }
    @Test
    public void inputNewTempMinAndNewValue() {
        es.setTemp(43);
        es.setTemp(13);
        es.setTemp(67);
        assertEquals(13, es.minTemp);
    }

    //Trend Tests
    @Test
    public void stableTemp() {
        es.setTemp(30);
        es.setTemp(30);
        assertEquals("STABLE", es.tempTrend);
    }
    @Test
    public void risingTemp() {
        es.setTemp(56);
        es.setTemp(67);
        assertEquals("INCREASING", es.tempTrend);
    }
    @Test
    public void decreasingTemp() {
        es.setTemp(20);
        es.setTemp(13);
        assertEquals("DECREASING", es.tempTrend);
    }

    //Reset Test
    @Test
    public void testReset() {
        es.setHumidity(40);
        es.setTemp(45);
        es.setHumidity(13);
        es.setTemp(25);
        es.setHumidity(86);
        es.setTemp(77);
        es.reset();
        assertEquals(86, es.currentHumidity);
        assertEquals(77, es.currentTemp);
        assertEquals(86, es.maxHumidity);
        assertEquals(86, es.minHumidity);
        assertEquals(77, es.maxTemp);
        assertEquals(77, es.minTemp);
        assertEquals("STABLE", es.humidityTrend);
        assertEquals("STABLE", es.tempTrend);
        assertEquals("HIGH", es.humidityStatus);
    }

    //Display Test
    @Test
    public void testDisplay() {
        es.setHumidity(67);
        es.setTemp(45);
        assertEquals("\nCurrent Humidity: 67%\n" +
                "Maximum Humidity: 67%\n" +
                "Minimum Humidity: 30%\n" +
                "Humidity Trend: INCREASING\n" +
                "Humidity Status: HIGH\n" +
                "\n" +
                "Current Tempurature: 45 Degrees\n" +
                "Maximum Tempurature: 45 Degrees\n" +
                "Minimum Tempurature: 30 Degrees\n" +
                "Tempurature Trend: INCREASING\n", es.display());
    }
}
