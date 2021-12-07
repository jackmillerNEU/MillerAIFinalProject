# MillerAIFinalProject
### John Miller
## Search algorithm benchmarking tests


### About
I made this project to experiment with different search algorithms and observe how they work differently from eachother. The three algorithms I implemented where DFS, BFS, and A* with several heuristics. The "World" for this project is a 20x20 grid in which can be placed empty cells, wall cells, water cells, or a mobile monster cell. 

### Instructions:
#### The board is controlled by keyboard inputs

#### O and I:
Cycle through 8 grid maps with O to go forward and I to go backward

#### P:
To simply go through each algorithm one by one, press P. 

#### 1,2,3,4,5,6,7,8,9,0,-:
These are the keys to run a single search algorithm, labeled in the key to the right of the grid in the GUI.

#### R and C
The R key simply resets the game to its initial state, while the C button simply clears the board of any paths.

#### E, Arrow Keys:
This enables the editor, a gray tile which can be moved with the arrow keys.

#### Q, W, A:
If the editor is on, pressing these keys will change the type of cell selected by the editor.
Q - Empty tile
W - Water tile
A - Wall tile

### The Monster:
The monster is a mobile tile, that when active, another search algorithm will run every half a second that uses A* with a heuristic that is biased against any cells near the monster and any water cells.

#### Shift, Enter, Backspace:
The monster can be controlled by the user by hovering the editor over a tile and pressing shift. The monster will spawn and can be moved with the arrow keys. The monster can also be spawned with random movement every half second by pressing enter over a tile. The monster can be removed by pressing backspace.
