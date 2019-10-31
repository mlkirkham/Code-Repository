
from othello import *

class KirkhamPlayer(othello_player):
    #  This will be called once at the beginning of the game, after
    #  a few random moves have been made.  Boardstate is the initial
    #  boardstate for the game, totalTime is the total amount of time
    #  (in seconds) in the range 60-1800 that your player will get for
    #  the game.  For our tournament, I will generally set this to 300.
    #  color is one of Black or White (which are just constants defined
    #  in the othello.py file) saying what color the player will be
    #  playing.
    def initialize(self, boardstate, totalTime, color):
        print("Initializing", self.name)
        self.mycolor = color
        pass;
    # This should return the utility of the given boardstate.
    # It can access (but not modify) the to_move and _board fields.
    def calculate_utility(self, boardstate):
        cornerList = (11, 18, 81, 88)
        cornerNextList = (12, 17, 21, 22, 27, 28, 71, 72, 82, 78, 87)
        sideList = (31, 41, 51, 61, 13, 14, 15, 16, 83, 84, 85, 86, 38,
                    48, 58, 68)
        total = 0
        # This prioritizes getting more move options available for the
        # program to look at giving more options
        for i in range(11, 89):
            if boardstate._board[i] == 2:
                if boardstate._board[i-10] == 0:
                    total += 1
                if boardstate._board[i+10] == 0:
                    total += 1
                if boardstate._board[i-1] == 0:
                    total += 1
                if boardstate._board[i+1] == 0:
                    total += 1
        # This prioritizes getting corner spaces
        for i in cornerList:
            if boardstate._board[i] == 1:
                total += 20
            if boardstate._board[i] == 2:
                total -= 20
        # Prioritizes letting the other player get the spaces next to
        # the corners
        for i in cornerNextList:
            if boardstate._board[i] == 1:
                total -= 10
            if boardstate._board[i] == 2:
                total += 10
        # Prioritizes getting the side squares
        for i in sideList:
            if boardstate._board[i] == 1:
                total =+ 5
            if boardstate._board[i] == 2:
                total -= 5

        return total
    def alphabeta_parameters(self, boardstate, remainingTime):
        # This should return a tuple of (cutoffDepth, cutoffTest, evalFn)
        # where any (or all) of the values can be None, in which case the
        # default values are used:
        #        cutoffDepth default is 4
        #        cutoffTest default is None, which just uses cutoffDepth to
        #            determine whether to cutoff search
        #        evalFn default is None, which uses your boardstate_utility_fn
        #            to evaluate the utility of board states.
        if remainingTime > 1000:
            return (4, None, None)
        elif remainingTime > 300:
            return (3, None, None)
        elif remainingTime > 60:
            return (2, None, None)
        elif remainingTime > 10:
            return (1, None, None)
    def mycount_difference(self,boardstate):
        return (boardstate._board.count(self.mycolor) -
                boardstate._board.count(opponent(self.mycolor)))

def count_difference(boardstate):
    return (boardstate._board.count(boardstate.to_move)
            - boardstate._board.count(opponent(boardstate.to_move)))
