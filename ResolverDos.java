package examen3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ResolverDos {
    private int size;
    private char[][] laybrinth;
    private static final int[][] DIRECCIONES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // Direcciones de mov


    public void exe(char[][] matriz) {
    	this.size = matriz.length;
    	this.laybrinth = matriz;
        char[][] laberinto = new char[size][size];

        Nodo solucion = resolverLaberintoAStar(laybrinth);
        if (solucion != null) {
            try {
                escribirCamino(solucion, "Solución.txt");
                System.out.println("Camino guardado como Solución.txt");
            } catch (IOException e) {
                System.out.println("Error al escribir el camino: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró una solución.");
        }
    }

    private Nodo resolverLaberintoAStar(char[][] laberinto) {
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo + n.heuristica));
        boolean[][] visitado = new boolean[size][size];
        int[] inicio = encontrarInicio(laberinto);
        int[] fin = encontrarFin(laberinto);
        Nodo inicial = new Nodo(inicio[0], inicio[1], null, 0, manhattanDistance(inicio[0], inicio[1], fin[0], fin[1]), ' ');

        frontera.add(inicial);

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll();

            if (actual.x == fin[0] && actual.y == fin[1]) {
                return actual; // Llegamos al final
            }

            if (visitado[actual.x][actual.y]) continue;
            visitado[actual.x][actual.y] = true;

            for (int[] dir : DIRECCIONES) {
                int nx = actual.x + dir[0];
                int ny = actual.y + dir[1];
                char dirChar = getDirectionChar(dir[0], dir[1]);
                if (esValido(nx, ny, laberinto) && !visitado[nx][ny]) {
                    int nuevoCosto = actual.costo + 1; // Asumiendo que cada paso cuesta 1
                    Nodo vecino = new Nodo(nx, ny, actual, nuevoCosto, manhattanDistance(nx, ny, fin[0], fin[1]), dirChar);
                    frontera.add(vecino);
                }
            }
        }

        return null; // No se encontró camino
    }

    private static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private boolean esValido(int x, int y, char[][] laberinto) {
        return x >= 0 && x < size && y >= 0 && y < size && laberinto[x][y] != '1';
    }

    private int[] encontrarInicio(char[][] laberinto) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (laberinto[i][j] == 'S') return new int[] {i, j};
            }
        }
        throw new RuntimeException("Inicio no encontrado en el laberinto.");
    }

    private int[] encontrarFin(char[][] laberinto) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (laberinto[i][j] == 'F') return new int[] {i, j};
            }
        }
        throw new RuntimeException("Fin no encontrado en el laberinto.");
    }

    private static char getDirectionChar(int dx, int dy) {
        if (dx == 1) return '2'; // Down - Abajo
        if (dx == -1) return '8'; // Up - Arriba
        if (dy == 1) return '6'; // Right - Derecha
        if (dy == -1) return '4';  // Left - Izquierda
        return ' ';
    }

    private static void escribirCamino(Nodo solucion, String archivo) throws IOException {
        StringBuilder camino = new StringBuilder();
        Nodo current = solucion;
        while (current.padre != null) {
            camino.append(current.direccion).append(',');
            current = current.padre;
        }
        camino.reverse().deleteCharAt(0);
        
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(camino.toString());
        }
    }

    static class Nodo {
        int x, y;
        Nodo padre;
        int costo; 
        int heuristica; 
        char direccion; 

        public Nodo(int x, int y, Nodo padre, int costo, int heuristica, char direccion) {
            this.x = x;
            this.y = y;
            this.padre = padre;
            this.costo = costo;
            this.heuristica = heuristica;
            this.direccion = direccion;
        }
    }
}