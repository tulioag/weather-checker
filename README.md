#Weather checking app

## Running
```bash
#Setup API key
export OPENWEATHER_KEY=<your API key for open weather>

#Create configuration file from sample
cp configuration-sample.yaml configuration.yaml

#Start the application
docker-compose up
```
In your browser go to http://localhost:8080

Accessing the root will return a list of locations. To access a specific location,
pass it after the /. For example, for checking the weather in Turku go to http://localhost:8080/Turku
