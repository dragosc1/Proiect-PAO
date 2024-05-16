<b> Etapa I </b>
1) Definirea sistemului 
Să se creeze o lista pe baza temei alese cu cel puțin 10 acțiuni/interogări care se pot face în cadrul 
sistemului și o lista cu cel puțin 8 tipuri de obiecte. 
2) Implementare 
Sa se implementeze în limbajul Java o aplicație pe baza celor definite la primul punct. 
Aplicația va conține: 
• clase simple cu atribute private / protected și metode de acces 
• cel puțin 2 colecții diferite capabile să gestioneze obiectele definiteanterior (eg: List, Set, Map, etc.) 
dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni- /bidimensionale în cazul în care nu se 
parcurg colectiile pana la data checkpoint-ului. 
• utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor; 
• cel puțin o clasă serviciu care sa expună operațiile sistemului 
• o clasa Main din care sunt făcute apeluri către servicii

* Lista actiuni / interogari:
  1. Search all publications in a section
  2. Show all library users
  3. Display all books / magazines / newspapers ordered by title
  4. Display all publications which can be loaned
  5. Display all publications made by an author
  6. Display all publications loaned by a user
  7. Make a query such that you can return a Publication from a user perspective
  8. Display books sorted by publication year
  9. Display magazines sorted by number of copies
  10. Display publications sorted by author name
  11. Delete newspapers from a year
  12. Insert a new publication which is neither a book / newspaper / magazine
  13. Insert a new Book
  14. Insert a new Magazine
  15. Insert a new Newspaper
  16. Update a magazine

<b> Etapa II </b>
1) Extindeți proiectul din prima etapa prin realizarea persistentei utilizând o baza de date relationala 
si JDBC. 
Să se realizeze servicii care sa expună operații de tip create, read, update si delete pentru cel puțin 4 
dintre clasele definite. Se vor realiza servicii singleton generice pentru scrierea și citirea din baza de 
date. 
2) Realizarea unui serviciu de audit 
Se va realiza un serviciu care sa scrie într-un fișier de tip CSV de fiecare data când este executată una 
dintre acțiunile descrise în prima etapa. Structura fișierului: nume_actiune, timestamp
