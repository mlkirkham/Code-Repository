"""
A program that imports DNA sequences in FASTA format from a .txt file
and globally aligns all four sequences using dynamic programming.
Program then outputs the optimum global alignment and it's corresponding
alignment score.

@author: Michael Kirkham
@version: 10/26/2017
"""

from readfasta import readfasta # imported from blackboard files
import numpy as np #uses numpy to create an array for dynamic programming

def main():
    np.set_printoptions(suppress=True)#sets the array output to a readable form
    """ creates an array of seq1 length by seq2 length and
        sets all the cells to zero. it then sets the base cases
        for the sequences and returns the array. takes in the two
        sequences as parameters to get the dimensions of the array
    """
    def createTable(seq1, seq2):
        baseArray = np.ndarray([len(seq1),len(seq2)])
        for i in range(1, len(seq1)): #sets base cases for seq1
            baseArray[i][0] = baseArray[i-1][0] - 11
        for j in range(1, len(seq2)): #sets base cases for seq2
            baseArray[0][j] = baseArray[0][j-1] - 11
        return baseArray
    """ function to get the max score from a global alignment and set up
        the table to get the global alignment sequence. paramters are the
        two sequences that need aligned and the base case table from
        the "createTable()" function.
    """
    def getScore(seq1, seq2, table):
        for i in range(1, len(seq1)):
            for j in range(1, len(seq2)):
                if(seq1[i] == seq2[j]):#checks if the bp's are the same
                    table[i][j] = max(table[i-1][j-1] + 7,#puts the max of these into
                                      table[i-1][j] - 11, #the current cell
                                      table[i][j-1] - 11)
                else:
                    table[i][j] = max(table[i-1][j-1] - 4,
                                      table[i-1][j] - 11,
                                      table[i][j-1] - 11)
        return (table[i][j], table)
    """ a function to get the global alignment from two
        sequences and their table. parameters are the start string,
        sequence 1, length of sequence 1 - 1, sequence 2, length of sequence 2 - 1,
        and the table. returns the global alignment string.
    """
    def getGA(seq1, i, seq2, j, table):
        gaSeq = ''
        while(table[i][j] != 0):
            if(table[i-1][j-1] == table[i][j] - 7): #finds match and moves to that square
                gaSeq += seq1[i]
                i -= 1
                j -= 1
            elif(table[i-1][j-1] == table[i][j] + 4):#finds mismatch and moves to that square
                gaSeq += seq1[i]
                i -= 1
            elif(table[i][j-1] == table[i][j] + 11):#finds gap in j axis and moves
                gaSeq += '_'
                j -= 1
            elif(table[i-1][j] == table[i][j] + 11):#finds gap in i axis and moves
                gaSeq += '_'
                i -= 1
        print('The global alignment of these genes is: ' + str(gaSeq[::-1]))
        return gaSeq[::-1]
    """ goes through a list of DNA sequences and scores each pair.
        it finds the pair with the max score and returns the score,
        the table for that pair, and which two in the list were
        paired together in a tuple. takes in a list of DNA sequences
        as the only parameter.
    """
    def getMaxGA(genes):
        maxScore = tuple()
        for i in range(0, len(genes)):#scores all pairs of genes
            for j in range(i+1, len(genes)):
                score = getScore(genes[i], genes[j], createTable(genes[i],genes[j]))
                if(score > maxScore):#keeps track of the max score and what genes 
                    maxScore = score #they consist of
                    duo = (i, j)
        maxScore += duo
        print('The max score is: ' + str(maxScore[0]) +
              ' between genes: ' + str(maxScore[2]) + ' and ' +
              str(maxScore[3]))                                    
        return maxScore

    genes = readfasta('MidCS4.txt')#puts the sequences from the file into a list
    for i in range(0, len(genes)):
        genes[i][2] = '_' + genes[i][2]# adds '_' to the beginning of each sequence for GA
    geneList = list()
    for i in range(0, len(genes)):#creates a list of just the sequences
        geneList.append(genes[i][2])

    for i in range(0, len(genes)-1): #loops getting alignement until there
        maxTuple = getMaxGA(geneList)#is only one in the list
        geneList.append(getGA(geneList[maxTuple[2]], len(geneList[maxTuple[2]])-1,
                       geneList[maxTuple[3]], len(geneList[maxTuple[3]])-1, maxTuple[1]))
        del geneList[maxTuple[2]]#gets rid of the two sequences that were aligned
        del geneList[maxTuple[3]-1]

main()
