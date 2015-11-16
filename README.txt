Aquest és el nostre projecte de Kenken.

Relació de drivers i classes provades

1.DriverCell: Cell , ItemPossibilities
2.DriverRegion: Region, KKRegion, KKRegionProduct, KKRegionSubtraction, KKRegionAddition, KKRegionDivision, CellLine, Row, Column + 1
3.DriverKKBoard: Board, KKBoard + 1 + 2
4.DriverMatch: Match, Move + 1 + 2 + 3
5.DriverBoardCreator: CPUBoardCreator, HumanBoardCreator + 1 + 2  + 3
6.DriverAdminPlayers: DB, KKDB, Table, Player
7.DriverGame: Game + 1 + 2 + 3 + 4 + 5 + 6

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
Acaba d'omplir el tauler. Ara tocarà tornar enrere i crear regions de mida aleatòria (la probabilitat 
ve condicionada per uns "pesos" assignats a cada mida).

+-+-+-+
|4|3|C|
+-+-+-+
|1|2|B|
+-+-+-+
|A|A|A|
+-+-+-+
Es crea una regió A de 3 caselles i una B de 1 casella. La regió C ha de ser de mida 1 perquè la 
casella 5 i la 4 no són contigües.

+-+-+-+
|D|D|C|
+-+-+-+
|E|E|B|
+-+-+-+
|A|A|A|
+-+-+-+
Finalment s'acaben de crear les regions. Els valors finals de cada casella ja s'havien precalculat 
abans del DFS i en funció d'això i de quina operació es vol que tingui cada regió, es calculen els 
resultats.


Nota: l'algorisme es podria millorar si després de fer el DFS s'ajuntessin algunes regions per tal 
que el número de regions de cada mida s'ajustés més al "pes" demanat per l'usuari.

Nota2: també es podria millorar si es tingués en compte el número de possibles solucions que té el
taulell un cop ja creat. En general, crec que és menys divertit de jugar a un kenken amb moltes
solucions diferents i s'haurien de penalitzar d'alguna manera. A part, també es podria tenir en 
compte que hi ha operacions que donen més possibles combinacions per una regió i que això influeix
en la dificultat del kenken (e.g. l'operació i resultat -1 en un taulell n*n té (n-1)*2 solucions 
possibles, mentres que l'operació i resultat -8 en un taulell de 9*9 només té 2 solucions diferents).
===============================================================

