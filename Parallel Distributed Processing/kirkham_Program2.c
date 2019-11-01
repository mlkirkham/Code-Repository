/*  @author: Michael Kirkham
    @version: 3/21/2018
    CS 435 Program 2: Concurrent Array Convolutions

    A program that creates an array with size as a command line parameter, and
    performs operations on the given array based on operations in text files
    also given as command line parameters. Command line input should be as follows:
    ./kirkham_Program2 [array size] "file1.txt" ... "fileN.txt"
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>

#define THREAD_CREATION_FAILED -1
#define THREAD_JOIN_FAILED -2

int array_index;
int thread_count = 0;
int *array;

pthread_mutex_t *lock_array;

void *thread_file_operation (void *arg);

int main (int argc, char *argv[]){

    sscanf(argv[1], "%d", &array_index);
    array = (int*)malloc(array_index * sizeof(int));
    
    for (int i = 0; i < array_index; i++){
	    array[i] = 0;
    }

    thread_count = argc - 2;

    lock_array = (pthread_mutex_t*)malloc(array_index * sizeof(pthread_mutex_t));
    for(int i = 0; i < array_index; i++){
        pthread_mutex_init (&lock_array[i], NULL);
    }

    pthread_t *tid[thread_count];

    for(int i = 0; i < thread_count; i++){
        tid[i] = (pthread_t *) malloc(sizeof(pthread_t));

        if (pthread_create (tid[i],
			NULL,
			thread_file_operation,
			(void *) argv[i+2]) ) {
            fprintf (stderr, "Error creating thread %d.\n", i);
            exit (THREAD_CREATION_FAILED);
        }
    }

    void **thread_ret_val = (void **) malloc (sizeof (void *));

    for (int i = 0; i < thread_count; i++) {

        if (pthread_join (*tid[i], thread_ret_val)) {
            fprintf (stderr, "Error joining with thread %d.\n", i);
            exit (THREAD_JOIN_FAILED);
        
        } else {
        
            printf("threads joined\n");
            free(tid[i]);
        }
    }

    for(int i = 0; i < array_index; i++){
        pthread_mutex_destroy(&lock_array[i]);
    }
    free(lock_array);

    for( int j = 0; j < array_index; j++){
        printf("%d\n", array[j]);
    }
    free(array);
}

void *thread_file_operation (void *arg){

    char c;
    int l_array_index;
    char operation;
    int right_operand;
    int r_array_index;

    FILE *fp;
    fp = fopen(arg, "r");

    if(fp == NULL){
        printf("Error finding file %s\n", arg);
        exit(1);
    }

    while((c = fgetc(fp))!= EOF){
        ungetc(c, fp);
        fscanf(fp, "%d", &l_array_index);
        operation = fgetc(fp);
        c = fgetc(fp);
        if(c == 'i'){
            fscanf(fp, "%d", &r_array_index);
            right_operand = array[r_array_index];
        }
        else{
            ungetc(c, fp);
            fscanf(fp, "%d", &right_operand);
        }

        //printf("a[%d]=%d %c %d in %s\n", l_array_index, array[l_array_index], operation, right_operand, arg);
        
        pthread_mutex_lock(&lock_array[l_array_index]);
        if(l_array_index != r_array_index){
            pthread_mutex_lock(&lock_array[r_array_index]);
        }

        int temp_val;
        switch(operation){
            case '+' :
                array[l_array_index] = array[l_array_index] + right_operand;
                break;
            case '-' :
                array[l_array_index] = array[l_array_index] - right_operand;
                break;
            case '*' :
                array[l_array_index] = array[l_array_index] * right_operand;
                break;
            case '/' :
                if(right_operand == 0){
                    printf("Cannot divide by zero. Error in file %s\n", arg);
                } else{
                    array[l_array_index] = array[l_array_index] / right_operand;
                }
                break;
            case '^' :
                if(right_operand < 0){
                    temp_val = array[l_array_index];
                    if(temp_val == 0){
                        printf("Cannot divide by zero. Error in file %s\n", arg);
                    } else{
                        array[l_array_index] = 1 / temp_val;
                    }
                } else if(right_operand == 0){
                    array[l_array_index] = 1;
                } else {
                    temp_val = array[l_array_index];
                    for(int i = 0; i < right_operand - 1; i++){
                    array[l_array_index] = array[l_array_index] * temp_val;
                    }
                }
                break;
            default :
                printf("invalid %c operation in file %s\n", operation, arg);
        }

        pthread_mutex_unlock(&lock_array[l_array_index]);
        if(l_array_index != r_array_index){
            pthread_mutex_unlock(&lock_array[r_array_index]);
        }
        
        c = fgetc(fp);
    }

    fclose(fp);
    pthread_exit ((void *) NULL);
}