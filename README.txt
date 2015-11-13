Aquest és el nostre projecte de Kenken.

A la cerpata jocsDeProva hi ha diversos inputs (fitxers .in) com a jocs de prova.

A continuació, comentem diversos algorismes interessants dins el projecte:

========= Creació aleatòria de les regions (veure CpuBoardCreator.java) ========
L'algorisme fa un DFS a través del tauler començant en una posició aleatòria. Per cada casella, 
l'algorisme decideix de manera aleatòria l'ordre en què es desplaça a la següent casella, i només
passa un cop per cada casella. Després ajunta les caselles que s'han cridat de manera consecutiva 
en el DFS, sempre i quan siguin caselles contigües.

A continuació, un exemple en un taulell 3x3:
+-+-+-+
| | | |
+-+-+-+
|1| | |
+-+-+-+
| | | |
+-+-+-+
Comença a 1,0

+-+-+-+
| | | |
+-+-+-+
|1|2| |
+-+-+-+
| | | |
+-+-+-+
Crida al DFS en una casella contigua aleatòria.

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
Acaba d'omplir el tauler. Ara tocarà tornar enrere i crear regions de mida aleatòria (la probabilitat ve condicionada
per uns "pesos" assignats a cada mida).

+-+-+-+
|4|3|C|
+-+-+-+
|1|2|B|
+-+-+-+
|A|A|A|
+-+-+-+
Es crea una regió A de 3 caselles i una B de 1 casella. La regió C ha de ser de mida 1 perquè la casella 5 i la 4 no 
són contigües.

+-+-+-+
|D|D|C|
+-+-+-+
|E|E|B|
+-+-+-+
|A|A|A|
+-+-+-+
Finalment s'acaben de crear les regions. Els valors finals de cada casella ja s'havien precalculat abans del DFS i en 
funció d'això i de quina operació es vol que tingui cada regió, es calculen els resultats.

===============================================================