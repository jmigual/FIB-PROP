#include <iostream>
#include <vector>
#include <time.h>
#include <algorithm>

using namespace std;
typedef vector<int> VI;
typedef vector<VI> VVI;

VVI m;
int size=0;
int cont=0;
int ai=-1,aj=-1;
void DFS(int i, int j){
    m[i][j]=-2;
    int n=4;
    while (n>0){

        vector<pair<int,int> > opcions;
        if (i>0 and m[i-1][j]==-1)opcions.push_back(make_pair(i-1,j));
        if (j<8 and m[i][j+1]==-1)opcions.push_back(make_pair(i,j+1));
        if (i<8 and m[i+1][j]==-1)opcions.push_back(make_pair(i+1,j));
        if (j>0 and m[i][j-1]==-1)opcions.push_back(make_pair(i,j-1));
        n= opcions.size();
        if (n>0) {
            int x=rand()%opcions.size();
            pair <int,int> p = opcions[x];
            DFS(p.first, p.second);
        }
    }

    if (size==0 or (abs(ai-i)+abs(aj-j))!=1){
        size=(rand()%3)+2;
        cont++;
    }
    ai=i;
    aj=j;
    m[i][j]=cont;
    size--;

}

int main()
{
    srand(time(0));
    m = VVI (9, VI(9,-1));

    DFS(0,0);
    for (int i=0; i<9; i++){
        if (i!=0){
            for (int j=0; j<9; j++){
                cout<<' ';
                if (m[i-1][j]!=m[i][j])cout<<"-";
                else cout<<" ";
            }
            cout<<endl;
        }
        for (int j=0; j<9; j++){
            if (j!=0 and m[i][j-1]!=m[i][j])cout<<'|';
            else cout<<' ';
            //if (m[i][j]<10)cout<<0;
            //cout<<m[i][j];
            cout<<" ";
        }
        cout<<endl;


    }
    return 0;
}

