"""
Using python 3.6.3
@author: Michael Kirkham
@version: 12/14/2017

A program that takes in 4 different sequences in FASTA format and performs maximum parsimony
on those sequences to get a bootstrap value and runs that 1000 times. 
"""

from readfasta import readfasta
from random import *

def main():
    """
    a function to randomly assemble new sets of base pair lists for parsimony.
    """
    def resample(matrix):
        resampledMatrix = []
        for i in range(0, len(matrix)):
            resampledMatrix.append(choice(matrix))
        return resampledMatrix
    """
    Function that takes in a list of base pair lists and runs maximum parsimony on
    them to get a consensus tree and minimum value.
    """
    def getBootstrapValue(sequence):
        treeDict = {'AB' : 0, #dictionary to store change values. each represents a different tree
                    'AC' : 0,
                    'AD' : 0}
        treeList = []
        for i in range(0, len(sequence)):
            changes = 0
            for j in range(0, 3):
                if sequence[i][j+1] != sequence[i][0]: #counts the changes between pair A and the rest.
                    changes += 1
            if changes == 0:
                pass
            elif changes == 1:
                treeDict['AB'] += 1
                treeDict['AC'] += 1
                treeDict['AD'] += 1
            elif changes == 3:
                treeDict['AB'] += 3
                treeDict['AC'] += 3
                treeDict['AD'] += 3
            else:
                if sequence[i][0] == sequence[i][1]:
                    treeDict['AB'] += 1
                    treeDict['AC'] += 2
                    treeDict['AD'] += 2
                elif sequence[i][0] == sequence[i][2]:
                    treeDict['AB'] += 2
                    treeDict['AC'] += 1
                    treeDict['AD'] += 2
                else:
                    treeDict['AB'] += 2
                    treeDict['AC'] += 2
                    treeDict['AD'] += 1
        #puts the tuple of the minimum value and the tree it goes with in a list to be returned.
        treeList.append(list(treeDict.keys())[list(treeDict.values()).index(min(treeDict.values()))])
        treeList.append(min(treeDict.values()))
        return treeList

    genes = readfasta('CSPars.txt')#puts the sequences into a list in FASTA format.
    sequences = []
    for i in range(0, len(genes)): #pulls just the sequences from the list.
        sequences.append(genes[i][2])
    pairList = [] #list of base pairs to be compared.
    for i in range(0, len(sequences[0])): #loop to get the base pairs into a list.
        parseList = []
        for j in range(0, 4):
            parseList.append(sequences[j][i])
        pairList.append(parseList)
    resampleList = []
    for i in range(0, 1000): #loop to get bootstrap values and then resample 1000 times. 
        resampleList.append(getBootstrapValue(pairList))
        pairList = resample(pairList)
    print("The minimum tree length and its supported tree are in the list below.")
    print("Sequences are in the order 1:A, 2:B, 3:C, 4:D")
    print("AB assumes the best unrooted tree has A and B on one side and C and D on the other, etc.")
    print(resampleList[:3])
    AB = 0.0
    AC = 0.0
    AD = 0.0
    total = 0.0
    for i in range(0, len(resampleList)): #loop to get the minimum value and tree in the entire 1000 bootstraps 
        if resampleList[i][0] == 'AB':
            AB += 1.0
        elif resampleList[i][0] == 'AC':
            AC += 1.0
        else:
            AD += 1.0
        total += 1.0
    print("The consensus tree topology and the bootstrap values based on all 1000 replicates are:")
    print("AB: %.1f" % (AB/total * 100))
    print("AC: %.1f" % (AC/total * 100))
    print("AD: %.1f" % (AD/total * 100))

main()