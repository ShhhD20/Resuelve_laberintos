package examen3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class ReadMaze {
	public char[][] traerLaberinto(String filePath, String fileName) throws IOException{
		boolean encontradoS = false, encontradoF = false;
		File archivo = new File(filePath, fileName);
		String[][] maze;
		String[] inicialData;
		String columna;
		int[] intInicial = new int[3];
		int col = 0;
		char[][] labyrith;
		if(!archivo.canExecute()) {
			System.out.println("Error! archivo no disponible!");
			System.exit(5);
		}
		
		 try {
			Scanner lector = new Scanner(archivo);
			
			if(lector.hasNextLine()) {
				columna = lector.nextLine();
				inicialData = columna.split(",");
								//Posibles errores
				if(inicialData.length < 3) {
	                System.out.println("Error! faltan valores en la primera columna de datos");
					System.exit(2);
				}
				if(inicialData.length > 3) {
	                System.out.println("Error! hay valores de más en la primera columna de datos");
					System.exit(2);
				}
								//Fin de errores
				for(int a  = 0; a < 3; a++) {
					try {
						intInicial[a] = Integer.parseInt(inicialData[a]);
					}catch(NumberFormatException e) {
						System.out.println("Error! Hay un caracter no disponible en la columna [0]");
						System.exit(1);
					}
				}
			}
							//Posibles errores
			if(intInicial[0] != intInicial[1]) {
				System.out.println("Error! el laberinto no esta declarado como rectangular!");
				System.exit(3);
			}
			if(intInicial[2] != 4) {
				System.out.println("Error! El codigo no permite movimientos en mas ni menos de 4 direcciones!");
				System.exit(3);
			}				//Fin de errores

			maze = new String[intInicial[0]][intInicial[0]];
			while(lector.hasNextLine()) {
				columna = lector.nextLine();
				maze[col] = columna.split(",");
				if(col >= intInicial[0] || maze[col].length != intInicial[0]) {
					System.out.println("Error! El laberinto no es cuadrado!");
					System.exit(3);
				}
				col++;
			}
			if(col != intInicial[0] || maze[col-1].length != col) {
				System.out.println("Error! El laberinto no es cuadrado!");
				System.exit(3);
			}
			lector.close();
			labyrith = new char[maze.length][maze.length];
			for(int a  = 0; a < maze.length; a++) {
				for(int b  = 0; b < maze.length; b++) {
					switch(maze[a][b]) {
					case "0":
						labyrith[a][b] = '0';
						break;
					case "1":
						labyrith[a][b] = '1';
						break;
					case "S":
						labyrith[a][b] = 'S';
						if(encontradoS) {					//Posibles errores
							System.out.println("Error! Hay mas de un punto de inicio!");
							System.exit(0);
						}							//Fin de errores
						encontradoS = true;
						break;
					case "F":
						labyrith[a][b] = 'F';
						encontradoF = true;
						break;
					default:							//Posibles errores
						System.out.println("Error! Hay un caracter no disponible en la posicion [" + a + "][" + b + "]");
						System.exit(1);
						break;					//Fin de errores
					}
				}
			}
			if(!encontradoF) {
				System.out.println("Error! Final no encontrado!");
				System.exit(4);
			}
			return labyrith;
		} catch (FileNotFoundException e) {
			System.out.println("Error! Archivo no encontrado");
			e.printStackTrace();
			System.exit(1);
			
		}
		 return null;
	}
}