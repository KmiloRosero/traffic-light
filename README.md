# TrafficLightStatePattern

Simulador de semáforo construido con el **patrón State** (Java).

Este proyecto incluye:
- Un demo por consola en Java (`com.trafficlight.Main`)
- Un simulador visual en Java Swing (`com.trafficlight.ui.SimulatorMain`)

## Estructura Java

### Paquetes
- `com.trafficlight.state`: Contrato del estado
- `com.trafficlight.states`: Estados concretos (`RedState`, `GreenState`, `YellowState`)
- `com.trafficlight.context`: Contexto (`TrafficLight`)
- `com.trafficlight.controller`: Controlador (`TrafficLightController`)
- `com.trafficlight.Main`: Punto de entrada del demo
- `com.trafficlight.ui`: Simulador Swing

## Ejecutar en local (PowerShell)

Desde la raíz del proyecto:

```powershell
mkdir out -Force | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src/main/java | ForEach-Object { $_.FullName })
```

### Demo por consola

```powershell
java -cp out com.trafficlight.Main
```

### Simulador visual (Swing)

```powershell
java -cp out com.trafficlight.ui.SimulatorMain
```

## Notas

- Si compilaste anteriormente dentro de `src/main/java`, puede que veas archivos `.class` ahí; se pueden eliminar sin problema.

## Autor / Curso

State Pattern | Universidad Cooperativa de Colombia
