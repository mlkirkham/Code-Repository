/* @author: Michael Kirkham
   @version: 2/14/2018
   CS 435

   The Earliest Common Meeting Time Problem:
   Takes in a text file of sets of elements as a command line parameter.
   Creates a thread for each element in the first set and looks in each
   subsequent set to see if each set contains the element. If multiple
   threads report an element the program will output the smallest of them.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <pthread.h>
#include <float.h>

#define THREAD_CREATION_FAILED -1
#define THREAD_JOIN_FAILED -2

#define MAX_THREADS 100

typedef struct data {
    double the_element;
    bool cond;
} Thread_Info;

void *thread_check_sets (void *arg);

char *file_name;
int num_sets;

int main (int argc, char *argv[])
{
    file_name = argv[1];
    FILE *fp;
    int num_thread = 0;
    int i;
    double d;
    double min_value = DBL_MAX;
    char c;

    pthread_t *tid[MAX_THREADS];

    fp = fopen(file_name, "r");

    if(fp == NULL){
        printf("Error finding file\n");
        exit(1);
    }

    fscanf(fp, "%d", &num_sets);

    do{
        fscanf(fp, "%lf", &d);

        double *parameter_p = (double *) malloc (sizeof (double));
        *parameter_p = d;

        tid[num_thread] = (pthread_t *) malloc(sizeof(pthread_t));

        if (pthread_create (tid[num_thread],
			NULL,
			thread_check_sets,
			(void *) parameter_p) ) {
            fprintf (stderr, "Error creating thread %lf.\n", d);
            exit (THREAD_CREATION_FAILED);
        }

        num_thread++;
        fscanf(fp, "%c", &c);
    } while( c != '\n');

    fclose(fp);

    void **thread_ret_val = (void **) malloc (sizeof (void *));
    for (i = 0; i < num_thread; i++) {

        if (pthread_join (*tid[i], thread_ret_val)) {
            fprintf (stderr, "Error joining with thread %d.", i);
            exit (THREAD_JOIN_FAILED);
        
        } else {
        
            Thread_Info *result =
                (Thread_Info *) *thread_ret_val;
            /*printf ("Thread with element %lf returned %d.\n", 
                result->the_element,
                result->cond); */
            if(result->cond == 1){
                if(result->the_element < min_value){
                    min_value = result->the_element;
                }
            }
        }
    }

    if(min_value != DBL_MAX){
        printf("The min value in all sets is: %lf\n", min_value);
    }
    else{
        printf("There was no common value in all sets\n");
    }

    exit (0);
}

void *thread_check_sets (void *arg){

    double element = *(double *) arg;
    double d;
    FILE *fp;
    int i;
    int j;
    char c;
    
    //printf ("Thread looking for element %lf is active.\n", element);

    Thread_Info *rv = malloc (sizeof (Thread_Info));
    rv->the_element = element;

    fp = fopen(file_name, "r");

    if(fp == NULL){
        printf("Error finding file in thread %lf\n", element);
        exit(1);
    }

    for(i = 0; i < 2; i++){
        do{
            fscanf(fp, "%c", &c);
        } while(c != '\n');
    }

    
    for(j = 0; j < num_sets - 1; j++){
        c = fgetc(fp);
        rv->cond = false;
        if(c != '\n'){
            ungetc(c, fp);
            do{
                fscanf(fp, "%lf", &d);
                //printf("Thread looking for %lf, looking at %lf\n", element, d);
                if(element == d){
                    //printf("Thread looking for %lf, found %lf\n", element, d);
                    rv->cond = true;
                    do{
                        fscanf(fp, "%c", &c);
                    } while(c != '\n');
                }
                else{
                    fscanf(fp, "%c", &c);
                }
            } while(c != '\n');
        }
        else{
            pthread_exit ((void *) rv);
        }
        if(rv->cond == false){
            pthread_exit ((void *) rv);
        }
    }

    fclose(fp);
    pthread_exit ((void *) rv);
}

