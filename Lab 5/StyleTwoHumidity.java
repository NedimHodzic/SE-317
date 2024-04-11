import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class StyleTwoHumidity {
    public static EmbeddedSystem es;
    public int humidity;
    public int temperature;

    static {
        es = new EmbeddedSystem(53, 30);
        es.reset();
    }

    public StyleTwoHumidity(int humidity, int temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    @Parameters
    public static Collection<Object []> paramaters() {
        return Arrays.asList(new Object[][] {{53, 30}, {51, 30}, {48, 30}, {49, 30}, {54, 30}, {56, 30}, {56, 30}});
    }

    @Test
    public void testHumiditySequence() {
        int prevHumidity = es.currentHumidity;
        int prevMax = es.maxHumidity;
        int prevMin = es.minHumidity;
        String trend = "STABLE";
        String status = "OK";
        es.setHumidity(humidity);

        if(humidity > prevHumidity) {
            trend = "INCREASING";
        }
        else if(humidity < prevHumidity) {
            trend = "DECREASING";
        }

        if(humidity > 55) {
            status = "HIGH";
        }
        else if(humidity < 25) {
            status = "LOW";
        }

        assertEquals(humidity, es.currentHumidity);
        assertEquals(Math.max(humidity, prevMax), es.maxHumidity);
        assertEquals(Math.min(humidity, prevMin), es.minHumidity);
        assertEquals(trend, es.humidityTrend);
        assertEquals(status, es.humidityStatus);
    }
}


