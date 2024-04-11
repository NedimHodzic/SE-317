public class EmbeddedSystemRefactored {
    public int currentHumidity;
    public int currentTemp;
    public int maxHumidity;
    public int maxTemp;
    public int minHumidity;
    public int minTemp;
    public String humidityStatus;
    //Trends are initially stable so we set the values here
    public String humidityTrend = "STABLE";
    public String tempTrend = "STABLE";

    public EmbeddedSystemRefactored(int humidity, int temp) {
        if((humidity < 0 || humidity > 100) && (temp < 0 || temp > 125)) {
            this.reset();
            System.out.println("Input humidity values in the range 0 - 100.");
            System.out.println("Input temperature values in the range 0 - 125.");
        }
        else if(humidity < 0 || humidity > 100) {
            currentTemp = temp;
            maxTemp = temp;
            minTemp = temp;
            this.reset();
            System.out.println("Input humidity values in the range 0 - 100.");
        }
        else if(temp < 0 || temp > 125) {
            currentHumidity = humidity;
            maxHumidity = humidity;
            minHumidity = humidity;
            humidityStatus = updateStatus(humidity);
            this.reset();
            System.out.println("Input temperature values in the range 0 - 125.");
        }
        else {
            currentHumidity = humidity;
            currentTemp = temp;
            maxHumidity = humidity;
            maxTemp = temp;
            minHumidity = humidity;
            minTemp = temp;
            //Now instead of doing the checking in the constructor we use a new method. We do this because
            //the code was duplicated in the constructor and in set humidity.
            humidityStatus = updateStatus(humidity);
        }
    }

    //New updateStatus method. Since this code was duplicated I made it into its own method.
    public String updateStatus(int humidity) {
        if(humidity < 25) {
            return "LOW";
        }
        else if (humidity > 55) {
            return "HIGH";
        }
        else {
            return "OK";
        }
    }
    //This new updateMax can be used in both setHumidity and setTemperature. It works by taking in the
    //current max as well as the new current value. It then returns the new max based on the comparison
    //between the new value and the current max.
    public int updateMax(int max, int value) {
        if(value > max) {
            max = value;
        }
        return max;
    }
    //This new updateMin can be used in both setHumidity and setTemperature. It works by taking in the
    //current min as well as the new current value. It then returns the new min based on the comparison
    //between the new value and the current min.
    public int updateMin(int min, int value) {
        if(value < min) {
            min = value;
        }
        return min;
    }
    //This new updateTrend can be used in both setHumidity and setTemperature. It works by taking in the
    //current value and the new value. It then returns the trend string based on the comparison of the
    //new value and the current one.
    public String updateTrend(int currentValue, int newValue) {
        if(newValue > currentValue) {
            return "INCREASING";
        }
        else if(newValue < currentValue) {
            return "DECREASING";
        }
        else {
            return "STABLE";
        }
    }

    public void setHumidity(int humidity) {
        if(humidity < 0 || humidity > 100) {
            System.out.println("Input humidity values in the range 0 - 100.");
        }
        else {
            //Here we utilize the new methods that we created.
            humidityStatus = updateStatus(humidity);
            maxHumidity = updateMax(maxHumidity, humidity);
            minHumidity = updateMin(minHumidity, humidity);
            humidityTrend = updateTrend(currentHumidity, humidity);
            currentHumidity = humidity;
        }
    }

    public void setTemp(int temp) {
        if(temp < 0 || temp > 125) {
            System.out.println("Input temperature values in the range 0 - 125.");
        }
        else {
            //Here we utilize the new methods that we created.
            maxTemp = updateMax(maxTemp, temp);
            minTemp = updateMin(minTemp, temp);
            tempTrend = updateTrend(currentTemp, temp);
            currentTemp = temp;
        }
    }

    public String display() {
        return "\nCurrent Humidity: " + currentHumidity + "%\nMaximum Humidity: " + maxHumidity + "%\nMinimum Humidity: "
                + minHumidity + "%\nHumidity Trend: " + humidityTrend + "\nHumidity Status: " + humidityStatus +
                "\n\nCurrent Tempurature: " + currentTemp + " Degrees\nMaximum Tempurature: " + maxTemp + " Degrees\nMinimum Tempurature: "
                + minTemp + " Degrees\nTempurature Trend: " + tempTrend + "\n";
    }

    public void reset() {
        maxHumidity = currentHumidity;
        minHumidity = currentHumidity;
        maxTemp = currentTemp;
        minTemp = currentTemp;
        humidityTrend = "STABLE";
        tempTrend = "STABLE";
        //Here we use one of the new methods we created.
        humidityStatus = updateStatus(currentHumidity);
    }
}