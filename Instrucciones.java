package examen3;

import java.io.File;
import java.io.IOException;

public class Instrucciones {
 public void exe() {
	 ReadMaze importar = new ReadMaze();
	 ResolverDos run = new ResolverDos();
	 String fileName, filePath;
	 char[][] matriz;
	 
	 fileName = "Laberinto.txt";
	 filePath = "C:\\Users\\silen\\Downloads";
	 
	 try {
		matriz = importar.traerLaberinto(filePath, fileName);
		run.exe(matriz);
	} catch (IOException e) {
		System.out.println("Error! Archivo no encontrado");
		e.printStackTrace();
	}

	 //*/
	 /*
	 Generator gen = new Generator();
	 gen.exe();//*/
 }
}
