### File missionary.py
### Implements the missionaries and cannibals problem
### @author: Michael Kirkham
### @version: 9/25/2017

from search import *

class MissionaryState(ProblemState):
    """
    The missionaries vs cannibals problem: Three missionaries and three
    cannibals must cross a river using a boat which can carry at most two
    people. There cannot be more cannibals than missionaries on either
    side of the river and the boat cannot cross the river by itself.
    """
    def __init__(self, ml, cl, mr, cr, b):
        self.ml = ml
        self.cl = cl
        self.mr = mr
        self.cr = cr
        self.b = b
    def __str__(self):
        return "("+str(self.ml)+","+str(self.cl)+","+str(self.mr)+","+str(self.cr)+","+str(self.b)+")"
    def illegal(self):
        if self.ml < 0 or self.cl < 0 or self.mr < 0 or self.cr < 0: return 1
        if self.ml > 3 or self.cl > 3 or self.mr > 3 or self.cr > 3: return 1
        if self.ml != 0 and self.cl > self.ml: return 1
        if self.mr != 0 and self.cr > self.mr: return 1
        if self.b > 1 or self.b < 0: return 1
        return 0
    def equals(self, state):
        return self.ml==state.ml and self.cl==state.cl and self.mr==state.mr and self.cr==state.cr and self.b==state.b
    def ml2(self):
        return MissionaryState(self.ml -2, self.cl, self.mr +2, self.cr, self.b +1)
    def cl2(self):
        return MissionaryState(self.ml, self.cl -2, self.mr, self.cr +2, self.b +1)
    def ml1(self):
        return MissionaryState(self.ml -1, self.cl, self.mr +1, self.cr, self.b +1)
    def cl1(self):
        return MissionaryState(self.ml, self.cl -1, self.mr, self.cr +1, self.b +1)
    def mlcl1(self):
        return MissionaryState(self.ml -1, self.cl -1, self.mr +1, self.cr +1, self.b +1)
    def mr2(self):
        return MissionaryState(self.ml +2, self.cl, self.mr -2, self.cr, self.b -1)
    def cr2(self):
        return MissionaryState(self.ml, self.cl +2, self.mr, self.cr -2, self.b -1)
    def mr1(self):
        return MissionaryState(self.ml +1, self.cl, self.mr -1, self.cr, self.b -1)
    def cr1(self):
        return MissionaryState(self.ml, self.cl +1, self.mr, self.cr -1, self.b -1)
    def mrcr1(self):
        return MissionaryState(self.ml +1, self.cl +1, self.mr -1, self.cr -1, self.b -1)
    def operatorNames(self):
        return ["ml2", "cl2", "ml1", "cl1", "mlcl1", "mr2", "cr2", "mr1", "cr1", "mrcr1"]
    def applyOperators(self):
        return [self.ml2(), self.cl2(), self.ml1(), self.cl1(), self.mlcl1(),
                self.mr2(), self.cr2(), self.mr1(), self.cr1(), self.mrcr1()]
    
Search(MissionaryState(3,3,0,0,0), MissionaryState(0,0,3,3,1))
