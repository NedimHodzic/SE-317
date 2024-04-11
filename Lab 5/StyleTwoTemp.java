import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class StyleTwoTemp {
    public static EmbeddedSystem es;
    public int humidity;
    public int temperature;
    static {
        es = new EmbeddedSystem(30, 66);
        es.reset();
    }
    public StyleTwoTemp(int humidity, int temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }
    @Parameters
    public static Collection<Object []> paramaters() {
        return Arrays.asList(new Object[][] {{30, 66}, {30, 68}, {30, 69}, {30, 67}, {30, 63}, {30, 59}, {30, 53}});
    }
    @Test
    public void testTempSequence() {
        int prevTemp = es.currentTemp;
        int prevMax = es.maxTemp;
        int prevMin = es.minTemp;
        String trend = "STABLE";
        es.setTemp(temperature);
        if(temperature > prevTemp) {
            trend = "INCREASING";
        }
        else if(temperature < prevTemp) {
            trend = "DECREASING";
        }
        assertEquals(temperature, es.currentTemp);
        assertEquals(Math.max(temperature, prevMax), es.maxTemp);
        assertEquals(Math.min(temperature, prevMin), es.minTemp);
        assertEquals(trend, es.tempTrend);
    }
}


