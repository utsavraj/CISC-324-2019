#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <wait.h>
#include <stdlib.h>


int main ()
{
	pid_t pid_0, pid_1, pid_2, pid_3;

	//The parent process creates a child process
	pid_1 = fork();
	//The parent process tests whether fork() failed or not, if it did, the process exits.
	if (pid_1 < 0)  goto X;

	 if(pid_1==0)
        {
                //Child processes execute this segment of code

                //Process executes another program 
                execl("/home/karim/Desktop/Stack/C_program_test/Labs/L1/count.out","./count.out", NULL);
                //prints to user that execl failed
                printf("Process[%d]: Failed to execute execl()\n",getpid());
        }
        else
        {
                //Parent processes execute this segment of code
                printf("Parent process waiting for the first child to terminate ...\n");
                int status;
                pid_0 = wait(&status);
                printf("Parent[%d]: child %d has terminated (status=%d)\n",getpid(),pid_0,WIFEXITED(status));
                sleep(1);
                
        }


        //The parent process creates a child process
        if(pid_1 != 0) pid_2 = fork();
        //The parent process tests whether fork() failed or not, if it did, the process exits.
        if (pid_2 < 0)  goto X;

        if(pid_2==0)
        {
                //Child processes execute this segment of code

                //Process executes another program 
                execl("/home/karim/Desktop/Stack/C_program_test/Labs/L1/count.out","./count.out", NULL);
                //prints to user that execl failed
                printf("Process[%d]: Failed to execute execl()\n",getpid());
        }
        else
        {
                //Parent processes execute this segment of code
                printf("Parent process waiting for the second child to terminate ...\n");
                int status;
                pid_0 = wait(&status);
                printf("Parent[%d]: child %d has terminated (status=%d)\n",getpid(),pid_0,WIFEXITED(status));
           

        }


	//The parent process creates a child process
        if((pid_1 != 0) && (pid_2 != 0)) pid_3 = fork();
        //The parent process tests whether fork() failed or not, if it did, the process exits.
        if (pid_3 < 0)  goto X;


	if(pid_3==0)
        {
                //Child processes execute this segment of code

                //Process executes another program 
                execl("/home/karim/Desktop/Stack/C_program_test/Labs/L1/count.out","./count.out", NULL);
                //prints to user that execl failed
                printf("Process[%d]: Failed to execute execl()\n",getpid());
        }
        else
        {
                //Parent processes execute this segment of code
                printf("Parent process waiting for the third child to terminate ...\n");
                int status;
                pid_0 = wait(&status);
                printf("Parent[%d]: child %d has terminated (status=%d)\n",getpid(),pid_0,WIFEXITED(status));
                sleep(1);
		printf("Parent[%d]: Terminated \n",getpid());

        }


X: return 0;

}

