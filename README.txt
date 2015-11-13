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
Acaba d'omplir el tauler. Ara tocar� tornar enrere i crear regions de mida aleat�ria (la probabilitat 
ve condicionada per uns "pesos" assignats a cada mida).

+-+-+-+
|4|3|C|
+-+-+-+
|1|2|B|
+-+-+-+
|A|A|A|
+-+-+-+
Es crea una regi� A de 3 caselles i una B de 1 casella. La regi� C ha de ser de mida 1 perqu� la 
casella 5 i la 4 no s�n contig�es.

+-+-+-+
|D|D|C|
+-+-+-+
|E|E|B|
+-+-+-+
|A|A|A|
+-+-+-+
Finalment s'acaben de crear les regions. Els valors finals de cada casella ja s'havien precalculat 
abans del DFS i en funci� d'aix� i de quina operaci� es vol que tingui cada regi�, es calculen els 
resultats.


Nota: l'algorisme es podria millorar si despr�s de fer el DFS s'ajuntessin algunes regions per tal 
que el n�mero de regions de cada mida s'ajust�s m�s al "pes" demanat per l'usuari.

Nota2: tamb� es podria millorar si es tingu�s en compte el n�mero de possibles solucions que t� el
taulell un cop ja creat. En general, crec que �s menys divertit de jugar a un kenken amb moltes
solucions diferents i s'haurien de penalitzar d'alguna manera. A part, tamb� es podria tenir en 
compte que hi ha operacions que donen m�s possibles combinacions per una regi� i que aix� influeix
en la dificultat del kenken (e.g. l'operaci� i resultat -1 en un taulell n*n t� (n-1)*2 solucions 
possibles, mentres que l'operaci� i resultat -8 en un taulell de 9*9 nom�s t� 2 solucions diferents).
===============================================================

