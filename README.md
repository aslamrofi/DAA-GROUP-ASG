# DAA-GROUP-ASG

# 💧 Aqua-Optimizers: Northern Malaysia Water Distribution

> **A JavaFX Algorithm Analysis Project** > *Saving the "rice bowl" of Malaysia, one optimized water truck at a time.*

## 👥 The Squad
Developed collaboratively by **The Aqua-Optimizers**:
* **Aslam** * **Wan Afiq** * **Alex** * **Safwan** * **Rofy** ## 🌍 The Mission
Northern Malaysia (Kedah and Perlis) is facing a severe, prolonged drought. The central reservoirs are critically low, and local farming districts are on the brink of catastrophic crop failure. 

Our mission? Build a Java-based algorithmic model to distribute a strictly limited water supply to various farming districts. We need to maximize the survival of the most critical crops while finding the absolute shortest physical routes for the water trucks. 

## 🛠️ The Tech Stack
* **Language:** Java 21
* **GUI Framework:** JavaFX (MVC Architecture)
* **Design Pattern:** Strategy Pattern (Isolated algorithm interfaces for clean testing)

## 🧠 The Algorithms
We pitted three classic algorithmic paradigms against each other to see who handles a disaster relief scenario best:

1. **The Sprinter: Greedy Algorithm (Fractional Knapsack)**
   * **How it works:** Sorts districts by a "priority-to-need" ratio and pours water as fast as possible.
   * **The Verdict:** Lightning fast ($O(N \log N)$) and perfect if water can be divided into infinite fractions. Fails in the real world if water is delivered in whole trucks.
2. **The Perfectionist: Dynamic Programming (0/1 Knapsack)**
   * **How it works:** Uses a 2D recurrence table to find the absolute mathematical optimum for discrete, indivisible units (like whole water trucks).
   * **The Verdict:** Flawless logic for small-scale operations, but suffers catastrophic time and space complexity scaling issues ($O(NW)$) when dealing with industrial volumes of water.
3. **The Navigator: Dijkstra's Algorithm (Graph Routing)**
   * **How it works:** Maps the physical roads between Alor Setar, Kangar, Sungai Petani, etc., and calculates the shortest transport paths.
   * **The Verdict:** The only algorithm anchored in geographic reality. Scales beautifully for large capacities, making it essential for the actual physical deployment of the trucks.

**🏆 Our Ultimate Conclusion:** The best real-world solution is a **hybrid approach**. Use the Greedy algorithm to rapidly calculate *who* gets the water, and feed those destination nodes into Dijkstra to figure out *how* to drive it there.

## 🚀 How to Run Locally

Because we used a clean separation of the JavaFX UI and the core mathematical logic, running this locally requires linking the JavaFX SDK.

**1. Clone the repo:**
```bash
git clone [https://github.com/your-username/aqua-optimizers.git](https://github.com/your-username/aqua-optimizers.git)
cd aqua-optimizers
