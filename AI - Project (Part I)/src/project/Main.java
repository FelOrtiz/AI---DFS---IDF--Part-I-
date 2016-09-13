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
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
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
        
        
        dfs(b, 0);        
    }
    
    public static int dfs(Board b, int distancia)
    {
        if (b.isStalemate())
        {
            System.out.println("Stalemate");
            return 0;
        }
        else if(b.isCheckMate())
        {
            if( b.turn == -1 )
            {
                System.out.println("|---------CheckMate-----------|");
                System.out.println("|---------Gano El Jugador-----------|");
                System.out.println(b.toString());
                System.out.println("|-------Profundidad------|\n"+distancia);
                return 1;
            }
            return 2;
        }
        else
        {
            Move[] moves = b.getValidMoves();
            for(Move m : moves)
            {
                Board newBoard = b.clone();
                newBoard.makeMove(m);
                dfs(newBoard, distancia++);
                
            }
            return -1;
        }
    }
}
