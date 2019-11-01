/*  @author: Michael Kirkham
    @version: 5/02/2018
    CS 435 Program 3: File Processing Using the Producer Consumer Paradigm

    A program that takes a file as a command line argument and makes one 
    producer and one consumer thread. the producer thread sends each line of the file
    to a shared buffer. the consumer file counts the vowels, consonants,
    non-alphabetic characters and words in the line and prints those numbers
    to the screen neatly. 
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <stdbool.h>
#include <ctype.h>
#include <limits.h>
#include "q.h"

#define _GNU_SOURCE
#define ESC_STRING "ju3j\nsd5k8\n\0\r\t\nhuj39\0823kjh"

Q *q;

pthread_mutex_t *q_lock;

pthread_cond_t *q_not_empty;

char *file_name;

void *producer(void *arg){

   FILE *fp;
   fp = fopen(file_name, "r");
   if(fp == NULL){
        printf("Error finding file in producer thread.\n");
        exit(1);
    }

    char *line = NULL;
    size_t len = 0;
    ssize_t next_line;

    while((next_line = getline(&line, &len, fp)) != -1){
        pthread_mutex_lock(q_lock);
        char* new_line;
        new_line = malloc(strlen(line) + 1);
        strcpy(new_line, line);
        //printf("Produced: %s\n", new_line);
        enqueue(q, (void *) new_line);

        pthread_cond_broadcast (q_not_empty);
        pthread_mutex_unlock(q_lock);
    }

    pthread_mutex_lock(q_lock);
    enqueue(q, (void *) ESC_STRING);
    pthread_cond_broadcast(q_not_empty);
    pthread_mutex_unlock(q_lock);

    free(line);
    fclose(fp);
   pthread_exit ((void *) NULL);
}

void *consumer(void *arg){

    bool loop_cond = true;

    while(true)    {
        pthread_mutex_lock(q_lock);
        while(size(q) == 0){
            pthread_cond_wait(q_not_empty, q_lock);
        }

        char* work_line;
        work_line = (char*)dequeue(q);
        if(!strcmp(work_line, ESC_STRING)){
            break;
        }

        //printf("Consumed: %s\n", work_line);
        pthread_mutex_unlock(q_lock);

        int vowels = 0;
        int consonants = 0;
        int non_alphas = 0;
        int words = 0;

        for(int i = 0; work_line[i] != '\0'; i++){
            char c = work_line[i];
            if(i == 0 && c != ' ' && c != '\n'){
                words++;
            }
            if(isalpha(c)){
                if(c == 'a' || c == 'e' || c == 'i' ||
                c == 'o' || c == 'u' || c == 'A' ||
                c == 'E' || c == 'I' || c == 'O' ||
                c == 'U'){
                    vowels++;
                }
                else{
                    consonants++;
                }
            }
            else{
                if(c != '\n'){
                    non_alphas++;
                }
                if(c == ' ' && !isspace(work_line[i+1]) && work_line[i+1] != '\0'){
                    words++;
                }
            }
        }

        printf("vowe cons non- word: the line\n");
        printf("---- ---- ---- ----\n");
        printf("%4d %4d %4d %4d: %s\n", vowels, consonants, non_alphas, words, work_line);
    }

   pthread_exit ((void *) NULL);
}

int main(int argc, char *argv[]){

   file_name = argv[1];

   q = (Q *) malloc (sizeof (Q));
   init_q(q);

   q_lock = (pthread_mutex_t *) malloc (sizeof (pthread_mutex_t));
   if (pthread_mutex_init (q_lock, NULL)) {
      fprintf (stderr, "Error initializing q_lock.\n");
      exit (-1);
   }

   q_not_empty = (pthread_cond_t *) malloc (sizeof (pthread_cond_t));
   if (pthread_cond_init (q_not_empty, NULL)) {
      fprintf(stderr, "Error initializing q_not_empty.\n");
      exit(-1);
   }

   pthread_t *producer_thread = (pthread_t *) malloc (sizeof (pthread_t));
   if(pthread_create (producer_thread,
                      NULL,
                      producer,
                      (void *) NULL)) {
      fprintf(stderr, "Error creating producer thread.\n");
      exit(-1);
   }

   pthread_t *consumer_thread = (pthread_t *) malloc (sizeof (pthread_t));
   if(pthread_create (consumer_thread,
                      NULL,
                      consumer,
                      (void *) NULL)) {
      fprintf(stderr, "Error creating consumer thread.\n");
      exit(-1);
   }

   if(pthread_join (*producer_thread, NULL)) {
      fprintf(stderr, "Error joining producer thread.\n");
      exit(-1);
   }

   if(pthread_join (*consumer_thread, NULL)) {
      fprintf(stderr, "Error joining consumer thread.\n");
      exit(-1);
   }


   exit (0);
}
