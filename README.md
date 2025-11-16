# Dorling-Cartogram
This programm generates a *Dorling diagram*, which is a variant of a map where regions are represented by circles rather than their geographical shape. The size of each circle corresponds to a value (e.g., population) and the circles are placed so that their relative position remains as similar as possible to the real map.

Here is an example with the countries of South America and their GDP:
<p align="center">
  <img src="img/outSouthAmericaGDP.png" width="300">
</p>

## Getting Started
To build the project locally following steps are needed:

### Prerequisites
Following tools must be installed:
* JDK
* gnuplot (only for diagram generation)

### Installation
1. Clone the repo 
   ```sh
   https://github.com/peterrssen/Dorling-Cartogram.git
   ```
2. Enter the repo und compile the files under `src/` with the following command:
   ```sh
   javac -d out src/**/*.java 
   ```
   The files are now stored in the `out` folder.
3. To generate the executable `run.jar` in the root directroy run:
   ```sh
   jar cfm run.jar manifest.txt -C out .
   ```

## Usage
```sh
java -jar run.jar ./testfiles/ test_asien.txt 10 1 0 out
```
