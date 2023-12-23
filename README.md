# RisingTides Java Project

## Overview
The RisingTides project is an assignment that explores the effects of rising sea levels on major cities around the world using 2-D arrays. This project involves array manipulation, referencing objects, the Weighted UnionFind algorithm, and the object-oriented programming (OOP) paradigm.

### Components

1. **GridLocation**
   - The `GridLocation` class represents a cell or a location on the 2-D array, holding the row and column of a cell.

2. **RisingTides**
   - The `RisingTides` class contains all the methods needed to construct the functioning visualizer and return relevant information regarding the effects of rising sea levels.

3. **RisingTidesVisualizer**
   - The `RisingTidesVisualizer` class is responsible for converting terrain heights to RGB colors to be displayed in the driver.

4. **Terrain**
   - The `Terrain` class is a driver dependency containing the heights of terrain and the locations of water sources.

5. **TerrainLoader**
   - The `TerrainLoader` class is responsible for loading the provided `.terrain` files from local or online sources.

6. **WeightedQuickUnionUF**
   - Implementation of the Weighted Quick-Union algorithm.

7. **Terrain Files**
   - Inside the "terrains/" folder, various `.terrain` files (e.g., `BayArea.terrain`, `CraterLake.terrain`) are provided for testing. These files contain information about the grid size, water sources, and terrain heights.

## Usage

1. **Setup**
   - Ensure you have the necessary Java environment set up.

2. **Terrain Files**
   - Locate the ".terrain" files inside the "terrains/" folder. These files contain information about the grid size, water sources, and terrain heights.

3. **Run RisingTides**
   - Execute the main program by running the appropriate driver class (e.g., `RisingTidesVisualizer`) to visualize the effects of rising sea levels on the specified terrains.

4. **Testing**
   - Utilize all provided input files for testing various methods in the project. Each input file contains information about the grid size, water sources, and terrain heights.

## License
This project is licensed under the [MIT License](LICENSE.md).

## Acknowledgments
- The project was developed as an assignment to practice array manipulation, referencing objects, and implementing the Weighted UnionFind algorithm.

---
