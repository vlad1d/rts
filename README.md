# RTS Simulator

## Description
This project is a Lord of the Rings-themed real-time strategy (RTS) simulator, built as part of the OOP course final assignment. It allows users to construct a battlefield map using nodes (locations) and edges (routes), assign armies of different factions, and simulate battles and events over time.

The application is implemented in **Java** using the **Swing** framework for the GUI. It follows the **Model-View-Controller (MVC)** architecture, supports JSON exporting, and includes a custom event/battle simulation engine.

For a more detailed explanation of the program structure, design decisions, and development process, please see [REPORT.md](REPORT.md).


## Running the Application

### Prerequisites
- Java 17+
- Maven 3.6+

### Build & Run
```bash
mvn clean install
mvn exec:java
```

The app will launch in a new window with a visual editor for creating the simulation map.

## Features

- [x] GUI-based map editor using Swing
- [x] Node and edge creation/deletion
- [x] Army management with factions and units
- [x] Event system with randomly triggered effects
- [x] Step-by-step simulation with movement and battle logic
- [x] Custom JSON saving of the entire simulation state

## Further Improvements

The project could be extended with:
- [ ] JSON loading support
- [ ] More advanced battle strategies
- [ ] AI-controlled player or interactive army control
- [ ] Undo/redo functionality
- [ ] Sound and animations

## Contact
Vlad Ichim | 0681097927 | vladichim17@yahoo.ro
