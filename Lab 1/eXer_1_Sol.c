#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <wait.h>
#include <math.h>

int A(int y) 
{ 
	int total = 0;
	for(int i=0; i<=floor(y/2);i++) total = total + i;
	return total;
}

int B(int y) 
{ 
        int total = 0;
        for(int i=(floor(y/2)+1);i<=y;i++) total = total + i;
        return total;
}


int main (int argc, char *argv[]) 
{
	int Total =0;
        pid_t pid;


	if(argc==1 || argc>2)
        { 
                printf("Program needs one parameter to be executed (e.g., ./a.out 13)\n"); 
                exit(0);
        }

        int x = atoi(argv[1]);

        if (x<=0)
        { 
                printf("Unvalid parameter: The parameter should be greater than 0, exiting ... \n");
                exit(0);
        }


	   
        pid = fork();
        
	if (pid != 0) 
	{
		
		Total = Total + A(x);
	}
        else 
        {
                Total = Total + B(x);
                exit(Total);
        }

       int status;
       wait(&status);

       Total = Total + (WEXITSTATUS(status));
        printf("The total is: %d\n",Total) ;





        return 0 ;
}


