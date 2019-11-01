"""
Using python 3.6.3
@author: Michael Kirkham
@version: 12/14/2017

A program to calculate the probability and likelihood, using a Markov Model, that the alpha-helix
for the amino acid sequence 'MQTRRSAFIILQGRPEYVMMDLHVHNMQSHTACWDMMAFKEEIWSHTALR'
starts at the only Cysteine.
"""

import numpy as np #for use of arrays
import math #to calculate log values

def main():
    np.set_printoptions(suppress=True)#sets the array output to a readable form
    aaSeq = 'MQTRRSAFIILQGRPEYVMMDLHVHNMQSHTACWDMMAFKEEIWSHTALR' #the amino acid sequence

    transProbMatrix = np.ndarray([2, 3]) #creates the transition probability matrix
    transProbMatrix = [0.95, 0.05, 0,
                       0, 0.9, 0.1]
    markovModel = np.ndarray([len(aaSeq) - 1, 4]) #creates the markov model matrix
    for i in range(0, len(aaSeq) - 1): #initializes the markov model with 0's.
        for j in range(0, 4):
            markovModel[i][j] = 0
    #dictionary of emmision probabilities in alpha-helical regions
    helixEmission = {'A' : 0.106,
                     'C' : 0.014,
                     'D' : 0.016,
                     'E' : 0.070,
                     'F' : 0.040,
                     'G' : 0.006,
                     'H' : 0.028,
                     'I' : 0.066,
                     'K' : 0.082,
                     'L' : 0.098,
                     'M' : 0.091,
                     'N' : 0.019,
                     'P' : 0.001,
                     'Q' : 0.074,
                     'R' : 0.098,
                     'S' : 0.051,
                     'T' : 0.017,
                     'V' : 0.023,
                     'W' : 0.057,
                     'Y' : 0.043}
    #dictionary of emmision probabilities in non-helical regions
    coilEmission = { 'A' : 0.078,
                     'C' : 0.019,
                     'D' : 0.054,
                     'E' : 0.058,
                     'F' : 0.041,
                     'G' : 0.073,
                     'H' : 0.024,
                     'I' : 0.058,
                     'K' : 0.059,
                     'L' : 0.094,
                     'M' : 0.023,
                     'N' : 0.045,
                     'P' : 0.046,
                     'Q' : 0.038,
                     'R' : 0.053,
                     'S' : 0.060,
                     'T' : 0.062,
                     'V' : 0.069,
                     'W' : 0.013,
                     'Y' : 0.033}
    #fills the first 3 columns of the markov model
    for i in range(0, len(aaSeq) - 1):
        markovModel[i][0] = -math.log10((0.95**(i) * 0.5 * 0.9**(len(aaSeq) - i - 1) * 0.1)) #equation for the probability of state path
        obsSeq = 1
        for j in range(0, len(aaSeq)):
            if j < (i + 1):
                obsSeq *= coilEmission[aaSeq[j]]
            else:
                obsSeq *= helixEmission[aaSeq[j]]
        markovModel[i][1] = -math.log10(obsSeq) #equation for the probability of observed path
        markovModel[i][2] = markovModel[i][0] + markovModel[i][1] #equation for the probability of state path and observed path

    totalLikelihood = 0
    for k in range(0, len(aaSeq)-1):
        totalLikelihood += 10**(-markovModel[k][2]) #calculates the total for the probabilities of the state path and observed paths

    for i in range(0, len(aaSeq) - 1):
        markovModel[i][3] = (10**(-markovModel[i][2])) / totalLikelihood #calculate likelihood of observed path
    #print statements
    print("The transition probability matrix is: ")
    print(transProbMatrix[0:3])
    print(transProbMatrix[3:])
    print("The probablity that the alpha-helix starts at the only Cysteine is " + str(10**(-markovModel[aaSeq.index('C')][2])))
    print("The likelihood that the alpha-helix starts at the only Cysteine is " + str(markovModel[aaSeq.index('C')][3]))
    return 0

main()