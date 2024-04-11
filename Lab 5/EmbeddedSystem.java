public class EmbeddedSystem {
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

    public EmbeddedSystem(int humidity, int temp) {
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
            if(humidity < 25) {
                humidityStatus = "LOW";
            }
            else if (humidity > 55) {
                humidityStatus = "HIGH";
            }
            else {
                humidityStatus = "OK";
            }
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

            if(humidity < 25) {
                humidityStatus = "LOW";
            }
            else if (humidity > 55) {
                humidityStatus = "HIGH";
            }
            else {
                humidityStatus = "OK";
            }
        }
    }

    public String display() {
        return "\nCurrent Humidity: " + currentHumidity + "%\nMaximum Humidity: " + maxHumidity + "%\nMinimum Humidity: "
            + minHumidity + "%\nHumidity Trend: " + humidityTrend + "\nHumidity Status: " + humidityStatus + 
            "\n\nCurrent Tempurature: " + currentTemp + " Degrees\nMaximum Tempurature: " + maxTemp + " Degrees\nMinimum Tempurature: "
            + minTemp + " Degrees\nTempurature Trend: " + tempTrend + "\n";
    }

    public void setHumidity(int humidity) {
        if(humidity < 0 || humidity > 100) {
            System.out.println("Input humidity values in the range 0 - 100.");
        }
        else {
            if(humidity > currentHumidity) {
                humidityTrend = "INCREASING";
            }
            else if (humidity < currentHumidity) {
                humidityTrend = "DECREASING";
            }
            else {
                humidityTrend = "STABLE";
            }

            if(humidity < 25) {
                humidityStatus = "LOW";
            }
            else if (humidity > 55) {
                humidityStatus = "HIGH";
            }
            else {
                humidityStatus = "OK";
            }

            if(humidity > maxHumidity) {
                maxHumidity = humidity;
            }
            if(humidity < minHumidity) {
                minHumidity = humidity;
            }

            currentHumidity = humidity;
        }
    }

    public void setTemp(int temp) {
        if(temp < 0 || temp > 125) {
            System.out.println("Input temperature values in the range 0 - 125.");
        }
        else {
            if(temp > currentTemp) {
                tempTrend = "INCREASING";
            }
            else if (temp < currentTemp) {
                tempTrend = "DECREASING";
            }
            else {
                tempTrend = "STABLE";
            }

            if(temp > maxTemp) {
                maxTemp = temp;
            }
            if(temp < minTemp) {
                minTemp = temp;
            }

            currentTemp = temp;
        }
    }

    public void reset() {
        maxHumidity = currentHumidity;
        minHumidity = currentHumidity;
        maxTemp = currentTemp;
        minTemp = currentTemp;
        humidityTrend = "STABLE";
        tempTrend = "STABLE";

        if(currentHumidity < 25) {
            humidityStatus = "LOW";
        }
        else if (currentHumidity > 55) {
            humidityStatus = "HIGH";
        }
        else {
            humidityStatus = "OK";
        }
    }
}