'''
Jon Beck
Two routines to use to read a fasta file
Last modified: 9 September 2016
'''

'''
parseHeader - split out the label from the header line
Parameter: a string starting with ">" and ending without a newline
Return: 
  1. the first item in the string, after the ">", up to the first space
'''
def parseHeaderLine(line):
    label = line[1:].split(' ')[0]
    return label

'''
readfasta - the subroutine that reads the fasta file
Parameter: a filename that must be in fasta format.  The file is
assumed to have:
1. the first line a header line
2. arbitrary blank lines
3. every line (especially including the last) is terminated by a newline
   terminator (enter key)
4. no line has only spaces on it
Return: a list of lists. Each inner list will have three elements:
1. the sequence identifier, the characters between the leading ">"
   and the first space
2. the entire header, the entire first line not including the ">"
3. the sequence, a single string of all the letters with no line terminators
'''
def readfasta(filename):
    resultList = []
    infile = open(filename, 'r')

    # process the first line, which must be a header line
    line = infile.readline()
    headerLine = line.rstrip()

    label = parseHeaderLine(headerLine)

    # initialize the sequence accumulator
    sequence = ''

    # process all the rest of the lines in the file
    for line in infile:
        line = line.rstrip()

        # ignore blank lines
        if line != '':

            # if it's a header line, finish the previous sequence
            # and start a new one
            if line[0] == '>':
                resultList.append([label, headerLine, sequence])

                label = parseHeaderLine(line)
                sequence = ''
            
            # if we're here, we must be in letters of the sequence
            else:
                sequence += line
            
    # we're done, so clean up, terminate the last sequence, and return
    infile.close()
    resultList.append([label, headerLine, sequence])
    return resultList

