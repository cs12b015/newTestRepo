/*
* @file botTemplate.cpp
* @author Arun Tejasvi Chaganty <arunchaganty@gmail.com>
* @date 2010-02-04
* Template for users to create their own bots
*/

#include <cstdlib>
#include "Othello.h"
#include "OthelloBoard.h"
#include "OthelloPlayer.h"
#include <cstdlib>
#define MAX_DEPTH 6

using namespace std;
using namespace Desdemona;


int eval(const OthelloBoard& board,Turn turn );
int alphabeta( const OthelloBoard& board,int depth,int alpha,int beta, Turn t1,Move t);
int eval2(const OthelloBoard& board,Turn turn );
int fulleval(const OthelloBoard& board,int depth,int alpha,int beta, Turn t1,Move ret);


class MyBot: public OthelloPlayer
{
    public:
        /**
         * Initialisation routines here
         * This could do anything from open up a cache of "best moves" to
         * spawning a background processing thread. 
         */
        MyBot( Turn turn );

        /**
         * Play something 
         */
        virtual Move play( const OthelloBoard& board );
    private:
};



MyBot::MyBot( Turn turn )
    : OthelloPlayer( turn )
{
}

Move MyBot::play( const OthelloBoard& board )
{
   list<Move> moves = board.getValidMoves( turn );
    
    list<Move>::iterator it = moves.begin();

	/*if(board.getRedCount()+board.getBlackCount()>53)
		return fulleval(board,turn);*/
	Move ret=*it;
	board.print();	

	if(board.getRedCount()+board.getBlackCount()>52)
	{
		fulleval(board,0,-1000,1000,turn,ret);
		return ret;
	}

	alphabeta(board,0,-1000,1000,turn,ret);
	return ret;
   
}

// The following lines are _very_ important to create a bot module for Desdemona

extern "C" {
    OthelloPlayer* createBot( Turn turn )
    {
        return new MyBot( turn );
    }

    void destroyBot( OthelloPlayer* bot )
    {
        delete bot;
    }
}



int alphabeta( const OthelloBoard& board,int depth,int alpha,int beta, Turn t1,Move ret){

		
		

     if (depth == MAX_DEPTH)
          return eval(board,t1);

      if (t1==BLACK)
	  {

			Turn t2= RED;
			list<Move> moves = board.getValidMoves(t1 );
			list<Move>::iterator it = moves.begin();
			//Move ret;
			Move t=*it;
			
			//cout<<"\nzz-"<<moves.size()<<endl;
			int a;
    		for(int i=0; i < moves.size(); it++, i++)
			{
				OthelloBoard temp;
				temp=board;
				t=*it;//cout<<"hi1";
				temp.makeMove(t1,t);
				//cout<<"hi2";
				a=alphabeta(temp,depth+1,alpha,beta,t2,ret);
				if(a>alpha)
				{
					if(depth==0)
						ret=t;
					alpha=a;
				}
					
				//alpha=max(alpha,alphabeta(temp,depth-1,alpha,beta,t2);
				if(alpha >= beta)
					return beta;
			}
			return alpha;
   				
         
	}
      else
	  {
         	Turn t2= BLACK;
			list<Move> moves = board.getValidMoves( t1 );
			list<Move>::iterator it = moves.begin();
			//Move ret;
			Move t=*it;
			
			//cout<<"\nrr"<<moves.size()<<endl;
			int b;
    		for(int i=0; i < moves.size(); it++, i++)
			{
				OthelloBoard temp ;
				temp=board;
				t=*it;
				temp.makeMove(t1,t);
				b=alphabeta(temp,depth+1,alpha,beta,t2,ret);
				if(b<beta)
				{
					if(depth==0)
						ret=t;
					beta=b;
				}
					
				//alpha=max(alpha,alphabeta(temp,depth-1,alpha,beta,t2);
				if(alpha >= beta)
					return alpha;
			}
			return beta;
		}
}


int fulleval(const OthelloBoard& board,int depth,int alpha,int beta, Turn t1,Move ret){

	
		if (board.getBlackCount()+board.getRedCount()==64)
          return eval2(board,t1);

		if(board.getValidMoves(RED).size()+board.getValidMoves(BLACK).size()==0)
			return eval2(board,t1);

      if (t1==BLACK)
	  {

			Turn t2= RED;
			list<Move> moves = board.getValidMoves(t1 );
			list<Move>::iterator it = moves.begin();
			//Move ret;
			Move t=*it;
			
			//cout<<"\nzz-"<<moves.size()<<endl;
			int a;
    		for(int i=0; i < moves.size(); it++, i++)
			{
				OthelloBoard temp;
				temp=board;
				t=*it;//cout<<"hi1";
				temp.makeMove(t1,t);
				//cout<<"hi2";
				a=fulleval(temp,depth+1,alpha,beta,t2,ret);
				if(a>alpha)
				{
					if(depth==0)
						ret=t;
					alpha=a;
				}
					
				//alpha=max(alpha,alphabeta(temp,depth-1,alpha,beta,t2);
				if(alpha >= beta)
					return beta;
			}
			return alpha;
   				
         
	}
      else
	  {
         	Turn t2= BLACK;
			list<Move> moves = board.getValidMoves( t1 );
			list<Move>::iterator it = moves.begin();
			//Move ret;
			Move t=*it;
			
			//cout<<"\nrr"<<moves.size()<<endl;
			int b;
    		for(int i=0; i < moves.size(); it++, i++)
			{
				OthelloBoard temp ;
				temp=board;
				t=*it;
				temp.makeMove(t1,t);
				b=fulleval(temp,depth+1,alpha,beta,t2,ret);
				if(b<beta)
				{
					if(depth==0)
						ret=t;
					beta=b;
				}
					
				//alpha=max(alpha,alphabeta(temp,depth-1,alpha,beta,t2);
				if(alpha >= beta)
					return alpha;
			}
			return beta;
		}



}

int eval2(const OthelloBoard& board,Turn turn )
{

	return board.getBlackCount()-board.getRedCount();


}
int eval(const OthelloBoard& board,Turn turn )
{
	int in,jn,score1,blackscore=0,redscore=0;
	int finalscore;
	

	for(in=0;in<8;in++){
		for(jn=0;jn<8;jn++)
		{
			score1=0;
			if ((in==1) || (in== 6) || (jn == 1) || (jn == 6))
			{
				score1 = -4;
			}
			if ((in == 0) || (in == 7) || (jn == 0) || (jn == 7))
			{
				score1 = 5;
			}
			if ((in== 1 && jn== 1) || (in== 1 && jn== 6) || (in== 6 && jn== 1) || (in== 6 && jn== 6)) 
			{
				score1 = -6;
			}	
			if ((in== 2 && jn== 2) || (in== 2 && jn== 5) || (in== 5 && jn== 2) || (in== 5 && jn== 5)) 
			{
				score1 = 2;
			}
			if ((in== 0 && jn== 0) || (in== 0 && jn== 7) || (in== 7 && jn== 0) || (in== 7 && jn== 7)) 
			{
				score1 = 25;
			}
			if(board.get(in,jn)==BLACK)
			{
				blackscore=blackscore+1+score1;			
			}
			if(board.get(in,jn)==RED)
			{
				redscore=redscore+1+score1;			
			}

		}
	}
if (turn==BLACK)
{
	finalscore=blackscore-redscore;
}
else if (turn==RED)
{
	finalscore=blackscore-redscore;
}
return finalscore;
}


/*int eval(const OthelloBoard& board,Turn turn )
{
	int in,jn,i,j,score1,blackscore=0,redscore=0;
	int finalscore;
	int V[8][8];

	V[0][0] =V[0][7]= 20;V[0][1]=V[0][6]=-3;V[0][2]=V[0][5]=11;V[0][3]=V[0][4]=8;
	V[1][0]=V[1][7]=-3;V[1][1]=V[1][6]=-7;V[1][2]=V[1][5]=-4;V[1][3]=V[1][4]=1;
	V[2][0]=V[2][7]=11;V[2][1]=V[2][6]=-4;V[2][2]=V[2][5]=2;V[2][3]=V[2][4]=2;
	V[3][0]=V[3][7]=8;V[3][1]=V[3][6]=1;V[3][2]=V[3][5]=2;V[3][3]=V[3][4]=-3;


	for(i=4;i<8;i++)
		for(j=0;j<8;j++)
			V[i][j]=V[7-i][j];



	
	

	for(in=0;in<8;in++){
		for(jn=0;jn<8;jn++)
		{
			score1=V[in][jn];
			if(board.get(in,jn)==BLACK)
			{
				blackscore=blackscore+1+score1;			
			}
			if(board.get(in,jn)==RED)
			{
				redscore=redscore+1+score1;			
			}

		}
	}
	if (turn==BLACK)
	{
		finalscore=blackscore-redscore;
	}
	else if (turn==RED)
	{
		finalscore=blackscore-redscore;
	}

	return finalscore;
}*/
/*int max(int x ,int y)
{
	if(x>=y)
		return x;
	return y;
}*/


/*int eval(const OthelloBoard& board,Turn turn )
{
	 list<Move> moves = board.getValidMoves( BLACK );
	 list<Move> moves1 = board.getValidMoves( RED );
		
	int in,i,j,jn,score1,blackscore=0,redscore=0,bla_move_size,red_move_size;
	int nfr=0,nfb=0,nfscore=0,x,y;
	int finalscore;
	int V[8][8];
	V[0][0] =V[0][7]=99 ;V[0][1]=V[0][6]=-8;V[0][2]=V[0][5]=8;V[0][3]=V[0][4]=6;
	V[1][0]=V[1][7]=-8;V[1][1]=V[1][6]=-24;V[1][2]=V[1][5]=-4;V[1][3]=V[1][4]=-3;
	V[2][0]=V[2][7]=8;V[2][1]=V[2][6]=-4;V[2][2]=V[2][5]=7 ;V[2][3]=V[2][4]=4;
	V[3][0]=V[3][7]=6;V[3][1]=V[3][6]=-3;V[3][2]=V[3][5]=4;V[3][3]=V[3][4]=0;


/*V[0][0] =V[0][7]= 20;V[0][1]=V[0][6]=-3;V[0][2]=V[0][5]=11;V[0][3]=V[0][4]=8;
	V[1][0]=V[1][7]=-3;V[1][1]=V[1][6]=-7;V[1][2]=V[1][5]=-4;V[1][3]=V[1][4]=1;
	V[2][0]=V[2][7]=11;V[2][1]=V[2][6]=-4;V[2][2]=V[2][5]=2;V[2][3]=V[2][4]=2;
	V[3][0]=V[3][7]=8;V[3][1]=V[3][6]=1;V[3][2]=V[3][5]=2;V[3][3]=V[3][4]=-3;




	for(i=4;i<8;i++)
		for(j=0;j<8;j++)
			V[i][j]=V[7-i][j];
	

	int a[]={-1,-1,0,0,1,1,1,-1};
	int b[]={0,1,1,-1,0,1,-1,-1};


	for(in=0;in<8;in++){
		for(jn=0;jn<8;jn++)
		{
			score1=V[in][jn];
			if(board.get(in,jn)==BLACK)
			{
				blackscore=blackscore+1+score1;			
			}
			if(board.get(in,jn)==RED)
			{
				redscore=redscore+1+score1;			
			}
			if(board.get(in,jn) != EMPTY)
			{
				int k;
				for(k=0;k<8;k++)
				{
					x=in+a[k];
					y=jn+b[k];
					if(x>=0 && x<8 &&y>=0 &&y < 8 && board.get(x,y)== EMPTY)
					{
						if(board.get(x,y)==BLACK)
							nfb++;
						if(board.get(x,y)==RED)
							nfr++;
						break;
					}
				}
	
			}			

		}
	}
	
	
	//Corners-check
		int blacorn=0,redcorn=0,cornscore=0;
		if(board.get(0,0)==BLACK){blacorn++;}else if(board.get(0,0)==RED){redcorn++;}
		if(board.get(0,7)==BLACK){blacorn++;}else if(board.get(0,7)==RED){redcorn++;}
		if(board.get(7,0)==BLACK){blacorn++;}else if(board.get(7,0)==RED){redcorn++;}
		if(board.get(7,7)==BLACK){blacorn++;}else if(board.get(7,7)==RED){redcorn++;}

	//near cornerscheck
		int nblacorn=0;
		int nredcorn=0;
		int ncornscore=0;
		
		if(board.get(0,0)==EMPTY)
		{
			if(board.get(1,0)==BLACK){nblacorn++;}else if(board.get(0,0)==RED){nredcorn++;}
			if(board.get(0,1)==BLACK){nblacorn++;}else if(board.get(0,1)==RED){nredcorn++;}
			if(board.get(1,1)==BLACK){nblacorn++;}else if(board.get(1,1)==RED){nredcorn++;}
		}
		if(board.get(0,7)==EMPTY)
		{
			if(board.get(1,6)==BLACK){nblacorn++;}else if(board.get(1,6)==RED){nredcorn++;}
			if(board.get(0,6)==BLACK){nblacorn++;}else if(board.get(0,6)==RED){nredcorn++;}
			if(board.get(1,7)==BLACK){nblacorn++;}else if(board.get(1,7)==RED){nredcorn++;}
		}
		if(board.get(7,0)==EMPTY)
		{
			if(board.get(6,0)==BLACK){nblacorn++;}else if(board.get(6,0)==RED){nredcorn++;}
			if(board.get(6,1)==BLACK){nblacorn++;}else if(board.get(6,1)==RED){nredcorn++;}
			if(board.get(7,1)==BLACK){nblacorn++;}else if(board.get(7,1)==RED){nredcorn++;}
		}
		if(board.get(7,7)==EMPTY)
		{
			if(board.get(6,7)==BLACK){nblacorn++;}else if(board.get(6,7)==RED){nredcorn++;}
			if(board.get(6,6)==BLACK){nblacorn++;}else if(board.get(6,6)==RED){nredcorn++;}
			if(board.get(7,6)==BLACK){nblacorn++;}else if(board.get(7,6)==RED){nredcorn++;}
		}


		//moves check
		int move_score;
		bla_move_size = moves.size();
		red_move_size = moves1.size();
		
		//coins count
		int count_score=0,blacount=0,redcount=0;	
		blacount=board.getBlackCount();
		redcount=board.getRedCount();	






	/*if (turn==BLACK)
	{
		finalscore=10*(blackscore-redscore);
		cornscore = 25 *(blacorn-redcorn);
		ncornscore =-12.5 *(nblacorn-nredcorn);
		if(bla_move_size > red_move_size)
		{
			move_score = (bla_move_size*100)/(bla_move_size+red_move_size);
		}else if(bla_move_size < red_move_size){
			move_score = -(bla_move_size*100)/(bla_move_size+red_move_size);
		}else{
			move_score=0;
		}

		if(blacount > redcount)
		{
			count_score = (blacount*100)/(blacount+redcount);
		}
		else if(bla_move_size < red_move_size)
		{
			count_score = -(blacount*100)/(blacount+redcount);
		}else{
			count_score=0;
		}

		if(nfb > nfr)
		{
			nfscore = (nfb*100)/(nfb+nfr);
		}
		else if(nfb < nfr)
		{
			nfscore = -(nfb*100)/(nfb+nfr);
		}else{
			nfscore=0;
		}*/




	
		
//	}
	/*else if (turn==RED)
	{
		finalscore=10*(redscore-blackscore);
		cornscore = 25*(redcorn-blacorn);
		ncornscore =-12.5*(nredcorn-nblacorn);
		if(bla_move_size < red_move_size)
		{
			move_score = (red_move_size*100)/(bla_move_size+red_move_size);
		}else if(bla_move_size > red_move_size)
		{
			move_score = -(red_move_size*100)/(bla_move_size+red_move_size);
		}else{
			move_score=0;
		}
		
		if(blacount < redcount)
		{
			count_score = (redcount*100)/(blacount+redcount);
		}
		else if(bla_move_size > red_move_size)
		{
			count_score = -(redcount*100)/(blacount+redcount);
		}else{
			count_score=0;
		}


		if(nfb < nfr)
		{
			nfscore = (nfr*100)/(nfb+nfr);
		}
		else if(nfb > nfr)
		{
			nfscore = -(nfr*100)/(nfb+nfr);
		}else{
			nfscore=0;
		}




	}

	int baprescore=(10 * count_score) + (802 * cornscore) + (382 * ncornscore) + (79 * move_score)  + (10 * finalscore)+ (75 * nfscore);

	return baprescore;
}*/


