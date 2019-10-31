### File eightpuzzle.py
### Implements the eight puzzle problem
### @author: Michael Kirkham
### @version: 9/27/2017

from informedSearch import *

class EightPuzzle(InformedProblemState):
    def __init__(self, puzzle):
        self.puzzle = list(puzzle)
    def __str__(self):
        return "("+str(self.puzzle)+")"
    """
    # return 0 heuristic
    def heuristic(self, goal):
        return 0
    
    #tiles out of place heuristic
    def heuristic(self, goal):
        cost = 0
        puz1 = self.puzzle[:]
        puz2 = goal.puzzle[:]
        for i in range(0, 9):
            if puz1[i] != puz2[i]:
                cost += 1
        return cost
    """
    #Manhattan distance heuristic
    def heuristic(self, goal):
        cost = 0
        puz1 = self.puzzle[:]
        puz2 = goal.puzzle[:]
        for i in range(0, 9):
            dist = abs(puz1.index(i) - puz2.index(i))
            cost += (dist/3)+(dist%3)
        return cost
    
    def illegal(self):
        return 0
    def equals(self, state):
        return self.puzzle==state.puzzle
    def up(self):
        newPuzzle = self.puzzle[:]
        zeroPos = newPuzzle.index(0)
        if zeroPos > 2:
            newPuzzle[zeroPos], newPuzzle[zeroPos-3] = newPuzzle[zeroPos-3], newPuzzle[zeroPos]
        return EightPuzzle(newPuzzle)
    def down(self):
        newPuzzle = self.puzzle[:]
        zeroPos = newPuzzle.index(0)
        if zeroPos < 6:
            newPuzzle[zeroPos], newPuzzle[zeroPos+3] = newPuzzle[zeroPos+3], newPuzzle[zeroPos]
        return EightPuzzle(newPuzzle)
    def left(self):
        newPuzzle = self.puzzle[:]
        zeroPos = newPuzzle.index(0)
        if zeroPos != 0 and zeroPos != 3 and zeroPos != 6:
            newPuzzle[zeroPos], newPuzzle[zeroPos-1] = newPuzzle[zeroPos-1], newPuzzle[zeroPos]
        return EightPuzzle(newPuzzle)
    def right(self):
        newPuzzle = self.puzzle[:]
        zeroPos = newPuzzle.index(0)
        if zeroPos != 2 and zeroPos != 5 and zeroPos != 8:
            newPuzzle[zeroPos], newPuzzle[zeroPos+1] = newPuzzle[zeroPos+1], newPuzzle[zeroPos]
        return EightPuzzle(newPuzzle)
    def operatorNames(self):
        return ["up", "down", "left", "right"]
    def applyOperators(self):
        return [self.up(), self.down(), self.left(), self.right()]

InformedSearch(EightPuzzle((1,3,0,8,2,4,7,6,5)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#A
InformedSearch(EightPuzzle((1,3,4,8,6,2,0,7,5)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#B
InformedSearch(EightPuzzle((0,1,3,4,2,5,8,7,6)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#C
InformedSearch(EightPuzzle((7,1,2,8,0,3,6,5,4)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#D
InformedSearch(EightPuzzle((8,1,2,7,0,4,6,5,3)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#E
InformedSearch(EightPuzzle((2,6,3,4,0,5,1,8,7)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#F
InformedSearch(EightPuzzle((7,3,4,6,1,5,8,0,2)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#G
InformedSearch(EightPuzzle((7,4,5,6,0,3,8,1,2)), EightPuzzle((1,2,3,8,0,4,7,6,5)))#H

"""
Problem      BFS      A*(tiles)      A*(dist)      Steps
A            7        3              3             2
B            66       9              7             6
C            156      20             9             8
D            690      48             27            10
E            856      48             27            10
F            1621     102            21            12
G            7934     380            36            15
H            50312    3529           153           20
"""
