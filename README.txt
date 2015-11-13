Aquest �s el nostre projecte de Kenken.

A la cerpata jocsDeProva hi ha diversos inputs (fitxers .in) com a jocs de prova.

A continuaci�, comentem diversos algorismes interessants dins el projecte:

========= Creaci� aleat�ria de les regions (veure CpuBoardCreator.java) ========
L'algorisme fa un DFS a trav�s del tauler comen�ant en una posici� aleat�ria. Per cada casella, 
l'algorisme decideix de manera aleat�ria l'ordre en qu� es despla�a a la seg�ent casella, i nom�s
passa un cop per cada casella. Despr�s ajunta les caselles que s'han cridat de manera consecutiva 
en el DFS, sempre i quan siguin caselles contig�es.

A continuaci�, un exemple en un taulell 3x3:
+-+-+-+
| | | |
+-+-+-+
|1| | |
+-+-+-+
| | | |
+-+-+-+
Comen�a a 1,0

+-+-+-+
| | | |
+-+-+-+
|1|2| |
+-+-+-+
| | | |
+-+-+-+
Crida al DFS en una casella contigua aleat�ria.

+-+-+-+
|4|3| |
+-+-+-+
|1|2| |
+-+-+-+
| | | |
+-+-+-+
Segueix fent fins que es queda encallat; llavors continua per una branca anterior.

+-+-+-+
|4|3|5|
+-+-+-+
|1|2|6|
+-+-+-+
|9|8|7|
+-+-+-+
Acaba d'omplir el tauler. Ara tocar� tornar enrere i crear regions de mida aleat�ria (la probabilitat ve condicionada
per uns "pesos" assignats a cada mida).

+-+-+-+
|4|3|C|
+-+-+-+
|1|2|B|
+-+-+-+
|A|A|A|
+-+-+-+
Es crea una regi� A de 3 caselles i una B de 1 casella. La regi� C ha de ser de mida 1 perqu� la casella 5 i la 4 no 
s�n contig�es.

+-+-+-+
|D|D|C|
+-+-+-+
|E|E|B|
+-+-+-+
|A|A|A|
+-+-+-+
Finalment s'acaben de crear les regions. Els valors finals de cada casella ja s'havien precalculat abans del DFS i en 
funci� d'aix� i de quina operaci� es vol que tingui cada regi�, es calculen els resultats.

===============================================================