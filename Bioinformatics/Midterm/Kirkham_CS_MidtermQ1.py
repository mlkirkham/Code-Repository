"""
Kirkham_CS_MidtermQ1.py

A program that simulates the molecular evolution of a population of DNA sequences,
then prints out each sequence in the final population. The model simulates natural selection
by using a model of stochastic genetic drift by calculating a relative fitness score for each sequence.
The user can input the change in relative fitness and the relative fitness threshold. 
The model uses the Kimura-2-parameter mutation model with user defined alpha and beta. The user can
also change the population, sequence length, generations, and the substring used in the genetic
drift model.

@author: Michael Kirkham
@version: 10/27/2017
"""

from random import *

def main():
    alpha = 0.1 # alpha, the probability of a transition over one generation
    beta = 0.004 # beta, the probability of a transversion over one generation
    rfChange = -0.5 #change in relative fitness. can be +/-.
    rfThresh = 1 #relative fitness threshold
    generations = 100 # the number G of generations for which the simulation will run
    pop = 100 # the number N of sequences in the population
    stringLength = 50 # length L of each sequence (in bp)
    gdSubstring = 'AGTC' # genetic drift user-submitted substring
    dnaString = ''#string used for the first randomly generated sequence
    stringList = list()
    trans1Dict = {'A' : 'C', # dictionary of transversions
                  'C' : 'A',
                  'G' : 'C',
                  'T' : 'A'}
    trans2Dict = {'A' : 'T', # dictionary of other transversions
                  'C' : 'G',
                  'G' : 'T',
                  'T' : 'G'}
    transitionDict = {'A' : 'G', # dictionary of transitions
                      'C' : 'T',
                      'G' : 'A',
                      'T' : 'C'}
    """ a function that takes in a sequence and mutates it using the
        K2P parameter model. Uses alpha and beta parameters.
    """
    def mutate(sequence):
        muString = ''
        for i in range(0, len(sequence)):
            randNum = random()#generates random number
            if randNum < beta:#checks to see if it's a transversion
                muString += trans1Dict[sequence[i]]
            elif randNum > beta and randNum < 2 * beta:#checks to see if it's the other transversion
                muString += trans2Dict[sequence[i]]
            elif randNum > 2 * beta and randNum < 2 * beta + alpha:#checks to see if it's a transition
                muString += transitionDict[sequence[i]]
            else:#keeps the bp in the sequence the same
                muString += sequence[i]
        return muString
    """ returns a boolean value whether the given sequence contains the given
        substring or not. uses a sliding window technique over the whole
        sequence. returns false if it doesn't, returns true if it does.
    """
    def checkSub(sequence, sub):
        boolValue = False
        for i in range(0, len(sequence)):
            if(sub == sequence[i:i+len(sub)]):
                boolValue = True
        return boolValue
    """ simulates a phase of reproduction.
        takes in a list of parent sequences and returns a list of
        child sequences. rf = relative fitness
    """
    def reproduce(seqList):
        parentList = list()#list of parents. the higher the rf, the better chance
                           #of making it into this list.
        repList = list()#list of randomly chosen parents that make it into the parent list.
        for i in range(0, len(seqList)):
            indRF = rfThresh#sets base rf
            if not checkSub(seqList[i], gdSubstring):#checks if the sequence does
                                                     #not have the substring
                indRF += rfChange#changes the rf
            if(indRF > uniform(0, rfThresh)):#generates a random number between 0 and the
                                             #rf threshold. if the parents rf is above this
                                             #it gets added to the parents list.
                parentList.append(seqList[i])
        for j in range(0, pop):#loop to add N sequences to the list of children
            repList.append(choice(parentList))
        return repList
    for i in range(0, stringLength): # creates a random DNA sequence L long
        dnaString = dnaString + choice(['A', 'G', 'C', 'T'])
    for i in range(0, pop): # puts N sequences in the population
        stringList.append(dnaString)
    """ loop that mutates each sequence and then reproduces the population
        for the next generation.
    """
    for g in range(0, generations): # mutates and reproduces the population for G generations
        for i in range(0, pop):
            stringList[i] = mutate(stringList[i])#mutate
    """ the rest is optional to the program. it checks to see the fraction of sequences in
        the final generation that contain the substring. The way the program works, the
        smaller the (substring length/sequence length) fraction is, the higher this count
        will be. The lower the loss to relative fitness for not having the substring,
        the higher this count will be as well. Finally the more generations that are looped
        through, the higher this count will be.
    """
    countSub = 0
    for i in range(0, len(stringList)):
        print(stringList[i])
        if(checkSub(stringList[i], gdSubstring)):
            countSub += 1
    print(str(countSub) + "/" + str(pop))

main()

