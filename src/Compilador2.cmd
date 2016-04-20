set path=%path%;C:\Program Files\Java\jdk1.7.0_79\bin
javac Model/*.java 
jar cf Libreria.jar Model/*.class 
javac Controller/*.java View/*.java 
jar cvfm Juego_Cuy.jar MANIFEST2.MF Controller/*.class View/*.class  *.txt
pause