# TrafficLightStatePattern

Traffic Light simulator built with the **State Pattern** (Java).

This project includes:
- A Java console demo (`com.trafficlight.Main`)
- A Java Swing visual simulator (`com.trafficlight.ui.SimulatorMain`)

## Java Structure

### Structure
- `com.trafficlight.state`: State contract
- `com.trafficlight.states`: Concrete states (`RedState`, `GreenState`, `YellowState`)
- `com.trafficlight.context`: Context (`TrafficLight`)
- `com.trafficlight.controller`: Controller (`TrafficLightController`)
- `com.trafficlight.Main`: Demo entry point
- `com.trafficlight.ui`: Swing simulator

## Run Locally (PowerShell)

From the project root:

```powershell
mkdir out -Force | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src/main/java | ForEach-Object { $_.FullName })
```

### Console demo

```powershell
java -cp out com.trafficlight.Main
```

### Swing visual simulator

```powershell
java -cp out com.trafficlight.ui.SimulatorMain
```

## Notes

- If you compiled previously inside `src/main/java`, you may see `.class` files there; they can be safely removed.

## Author / Course

State Pattern | Universidad Cooperativa de Colombia
