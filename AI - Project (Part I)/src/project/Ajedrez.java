/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author HoLeX
 */
public class Ajedrez
{
    static int deep=100;
    long tInicio, tFin;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        Ajedrez ajedrez = new Ajedrez();
        Board b = new Board();
        int[][] board = new int[8][8];
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("problem2.tbl"));
            for (int i=0; i<8; i++) {
                String line;
                try {
                    line = input.readLine();
                    String[] pieces=line.split("\\s");
                    for (int j=0; j<8; j++) {
                            board[i][j]=Integer.parseInt(pieces[j]);
                    }
                    b.fromArray(board);
                } catch (IOException ex) {
                    System.out.println("No se pudo leer");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el tablero que se intenta cargar");
        }
        ajedrez.tInicio = System.currentTimeMillis();
        ajedrez.dfs(b, 0);        
    }
    
    public int dfs(Board b, int distance)
    {
                    
        //System.out.println("Minumum "+Ajedrez.deep);

        if(distance>Ajedrez.deep)return -1;
        
        if (b.isStalemate())
        {
            //System.out.println("Stalemate");
            return 0;
        }
        else if(b.isCheckMate())
        {
            /*
            Encontramos el jaque mate que hace ganar al jugador de piezas blancas,
            nos quedamos con el primer jaque mate encontrado a esa profundidad, ya que,
            es el que nos lleva menor tiempo a encontrarlo
            */
            if( b.turn == -1 && distance<Ajedrez.deep)
            {
                System.out.println("|---------CheckMate-----------|");
                System.out.println("|---------Gano El Jugador-----------|");
                System.out.println(b.toString());
                System.out.println("|-------Profundidad------|");
                System.out.println(distance);
                System.out.println("");
                Ajedrez.deep = distance;
                tFin = System.currentTimeMillis();
                long tiempo = tFin - tInicio;
                System.out.println("Tiempo: "+tiempo);
                return 1;
            }
            return 2;
        }
        else
        {
            Move[] moves = b.getValidMoves();
            int newDistance = distance+1;
            //System.out.println("Move para empate: "+b.movestodraw);
            //System.out.println("Moves : "+moves.length);
            for(Move m : moves)
            {
                    Board newBoard = b.clone();
                    newBoard.makeMove(m);
                    dfs(newBoard, newDistance);
            }
            return -1;
        }
    }
}
