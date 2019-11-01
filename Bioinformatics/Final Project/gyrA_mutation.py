"""
Using Python version 3.6.3
@author: Michael Kirkham
@version: December 11, 2017
Program to run mutations with a constant population, over a number of generations,
using a GTR model of mutation. Takes in a gene from a file in FASTA format to start
the mutations. The program looks at the base pairs that develop pinicillin resistance
and sees how close to the starting gyrA gene the new population is. 
"""

from random import *
from readfasta import readfasta # imported from blackboard files

def main():
    generations = 1000 # the number of generations the simulation will run
    pop = 100 # the bacteria population
    aATGC = 8e-5 #A>G, T>C transition rate
    aGCAT = 1.35e-4 #G>A, C>T transition rate
    bATTA = 2.8e-5 #A>T, T>A transversion rate
    bGCTA = 5e-5 #G>T, C>A transversion rate
    bATCG = 6.8e-5 #A>C, T>G transversion rate
    bGCCG = 2.6e-5 #G>C, C>G transversion rate
    pinResistNucl = [250, 261, 243, 252, 201, 320] #nucleotides where pinicillin resistance occurs in the gyrA gene

    """
    Function that takes in a gene sequence, counts the number of nucleotides of each
    type and returns the percentage of the gene that are those nucleotides in a list.
    The list returned will be [%A, %C, %G, %T].
    """
    def baseFrequency(seq): #gives the equilibruim frequencies for base pairs as the list [A,C,G,T]
        pairFreq = [0, 0, 0, 0]
        for i in range(0, len(seq)):
            if seq[i] == 'A':
                pairFreq[0] += 1
            elif seq[i] == 'C':
                pairFreq[1] += 1
            elif seq[i] == 'G':
                pairFreq[2] += 1
            else:
                pairFreq[3] += 1
        for i in range(0, len(pairFreq)):
            pairFreq[i] = float(pairFreq[i] / len(seq)) #puts the list into percentages
        return pairFreq
    """
    Attempts to mutate the A nucleotide based on the GTR model and
    mutation rates at the top of the program. 
    """
    def mutateA(basePair, pi):
        mAC = bATCG * pi[1]
        mAG = aATGC * pi[2]
        mAT = bATTA * pi[3]
        randNum = uniform(0, 1)#generates random number
        if randNum < mAC:
            mutatedPair = 'C'
        elif randNum > mAC and randNum < mAC + mAG:
            mutatedPair = 'G'
        elif randNum > mAC + mAG and randNum < mAC + mAG + mAT:
            mutatedPair = 'T'
        else:
            mutatedPair = basePair
        return mutatedPair
    """
    Attempts to mutate the C nucleotide based on the GTR model and
    mutation rates at the top of the program. 
    """
    def mutateC(basePair, pi):
        mCA = bGCTA * pi[0]
        mCG = bGCCG * pi[2]
        mCT = aGCAT * pi[3]
        randNum = uniform(0, 1)#generates random number
        if randNum < mCA:
            mutatedPair = 'A'
        elif randNum > mCA and randNum < mCA + mCG:
            mutatedPair = 'G'
        elif randNum > mCA + mCG and randNum < mCA + mCG + mCT:
            mutatedPair = 'T'
        else:
            mutatedPair = basePair
        return mutatedPair
    """
    Attempts to mutate the G nucleotide based on the GTR model and
    mutation rates at the top of the program. 
    """
    def mutateG(basePair, pi):
        mGA = aGCAT * pi[0]
        mGC = bGCCG * pi[1]
        mGT = bGCTA * pi[3]
        randNum = uniform(0, 1)#generates random number
        if randNum < mGA:
            mutatedPair = 'A'
        elif randNum > mGA and randNum < mGA + mGC:
            mutatedPair = 'C'
        elif randNum > mGA + mGC and randNum < mGA + mGC + mGT:
            mutatedPair = 'T'
        else:
            mutatedPair = basePair
        return mutatedPair
    """
    Attempts to mutate the T nucleotide based on the GTR model and
    mutation rates at the top of the program. 
    """
    def mutateT(basePair, pi):
        mTA = bATTA * pi[0]
        mTC = aATGC * pi[1]
        mTG = bATCG * pi[2]
        randNum = uniform(0, 1)#generates random number
        if randNum < mTA:
            mutatedPair = 'A'
        elif randNum > mTA and randNum < mTA + mTC:
            mutatedPair = 'C'
        elif randNum > mTA + mTC and randNum < mTA + mTC + mTG:
            mutatedPair = 'G'
        else:
            mutatedPair = basePair
        return mutatedPair
    """
    Function that runs a mutation over an entire gene sequence. If the nucleotide is not
    in the nucleotide space that is pinicillin resistant, it will not
    attempt to mutate. It does this to save on computations for testing with higher population
    or more generations. 
    """
    def mutate(sequence, pi):
        muString = ''
        for i in range(0, len(sequence)):
            if i in pinResistNucl:
                if sequence[i] == 'A':
                    muString += mutateA(sequence[i], pi)
                elif sequence[i] == 'C':
                    muString += mutateC(sequence[i], pi)
                elif sequence[i] == 'G':
                    muString += mutateG(sequence[i], pi)
                else:
                    muString += mutateT(sequence[i], pi)
            else:
                muString += sequence[i]
        return muString

    """
    function to stochastically reproduce asexually for a given
    population size. picks randomly from the parents until it reaches
    the given "pop" size population. 
    """
    def reproduce(seqList):
        childList = list()#list of parents.
        for i in range(0, pop): #randomly choose from parents to get next population
            childList.append(choice(seqList))
        return childList
    """
    Checks the percent of gene2 that is equal to gene1 nucleotide by
    nucleotide.
    """
    def checkPercent(gene1, gene2):
        correct = 0
        for i in range(0, len(gene1)):
            if gene1[i] == gene2[i]:
                correct += 1
        return float(correct / len(gene1))

    """
    the rest of the code gets the gene and simulates the mutation and reproduction
    as well as prints the compared values of the new gene population to the
    original gyrA gene.
    """
    genes = readfasta('genes.txt')#puts the gyrA gene into a list
    geneList = list()
    for i in range(0, len(genes)):#creates a list of just the gene
        geneList.append(genes[i][2])
    #creates the population to be mutated
    testList = list()
    for i in range(0, pop):
        testList.append(geneList[0])
    #gets the equilibruim frequencies for GTR model and begins mutation/reproduction
    baseFreq = baseFrequency(geneList[0])
    for g in range(0, generations):
        for i in range(0, len(testList)):
            testList[i] = mutate(testList[i], baseFreq)
        testList = reproduce(testList)
    #looks to see how equal each new gene is to the original
    checkList = []
    for i in range(0, pop):
        checkList.append(checkPercent(geneList[0], testList[i]))
    print(checkList)



main()