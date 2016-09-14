/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import chess.BestCheckMate;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import chess.Board;
import chess.Move;
import java.util.ArrayList;
/**
 *
 * @author HoLeX
 */
public class Chess
{
    static final int TURNBLACK=-1; 	/// Black turn is -1, White turn is 1 (corresponds to the sign of the pieces)
    static final int TURNWHITE=1;
    static final int BOARDSIZE=8;	/// The size of the board
    static final String BOARDFILENAME="problem2.tbl";	/// The size of the board
    static int deep=Integer.MAX_VALUE;
    long timeBegin, timeEnd;
    ArrayList<BestCheckMate> bestCheckMateDFS;
    public Chess()
    {
        this.bestCheckMateDFS = new ArrayList<BestCheckMate>();
    }
            
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        Chess chess = new Chess();
        Board b = new Board();
        int[][] board = new int[BOARDSIZE][BOARDSIZE];
        BufferedReader input;
        //Load board from file "problem2.tbl"
        try {
            input = new BufferedReader(new FileReader(BOARDFILENAME));
            for (int i=0; i<BOARDSIZE; i++) {
                String line;
                try {
                    line = input.readLine();
                    String[] pieces=line.split("\\s");
                    for (int j=0; j<BOARDSIZE; j++) {
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
        //Algorithm DFS
        chess.timeBegin = System.currentTimeMillis();
        chess.dfs(b, 0);  
        chess.showBestCheckMate();
        
        //Algorithm IDS
        chess.timeBegin = System.currentTimeMillis();
        BestCheckMate bestCheckMate= chess.ids(b);
        if(bestCheckMate!=null)
        {
            System.out.println( bestCheckMate.toString() );
        }
           
    }
    
    /**	Compare alls the bestCheckmates found for algorithm DFS and selected the minimum deep
    **/
    public void showBestCheckMate()
    {
        if(bestCheckMateDFS.size()>0)
        {
            BestCheckMate bestCheckMateAux = bestCheckMateDFS.get(0);
            for(BestCheckMate bestCheckMate: bestCheckMateDFS)
            {
                if(bestCheckMate.getDeep()<bestCheckMateAux.getDeep())
                {
                    bestCheckMateAux = bestCheckMate;
                }
            }
            System.out.println(bestCheckMateAux.toString());
        }
        this.bestCheckMateDFS.clear();
        
    }
    /**	Find to best checkmate for player pieces WHITE using algorithm DFS
    **	@param b A node board
    **	@param distance is the deep to the board b
    **/
    public void dfs(Board b, int distance)
    {
                    
        if(distance>Chess.deep)return ;
        if (b.isStalemate())return ;
        else if(b.isCheckMate())
        {
            /*
            Encontramos el jaque mate que hace ganar al jugador de piezas blancas,
            nos quedamos con el primer jaque mate encontrado a esa profundidad, ya que,
            es el que nos lleva menor tiempo a encontrarlo
            */
            if( b.turn == TURNBLACK && distance<Chess.deep)
            {
                Chess.deep = distance;
                timeEnd = System.currentTimeMillis();
                long time = timeEnd - timeBegin;
                BestCheckMate bestCheckMate = new BestCheckMate(b, time, Chess.deep);
                this.bestCheckMateDFS.add(bestCheckMate);
                return ;
            }
            return ;
        }
        else
        {
            Move[] moves = b.getValidMoves();
            int newDistance = distance+1;
            for(Move m : moves)
            {
                    Board newBoard = b.clone();
                    newBoard.makeMove(m);
                    dfs(newBoard, newDistance);
            }
            return ;
        }
    }
    
    
    /**	Find to best checkmate calling to dls
    **	@param b A node
    **	@return the best checkmate found
    **/
    public BestCheckMate ids(Board b)
    {
        BestCheckMate found;
        for(int i=0;i<Integer.MAX_VALUE;i++)
        {
            found = dls(b, i, 0);
            if(found != null)
            {
                return found;
            }
        }
        return null;
    }
    /**	Implementation to the algorithm DFS but with limite
    **	@param b A node
    **	@return the best checkmate found
    **/
    public BestCheckMate dls(Board b, int LimitDeep, int distance)
    {
        if(distance>LimitDeep)return null;
        if (b.isStalemate())return null;
        else if(b.isCheckMate())
        {
            /*
            Encontramos el jaque mate que hace ganar al jugador de piezas blancas,
            nos quedamos con el primer jaque mate encontrado a esa profundidad, ya que,
            es el que nos lleva menor tiempo a encontrarlo
            */
            if( b.turn == TURNBLACK && distance<LimitDeep)
            {
                timeEnd = System.currentTimeMillis();
                long time = timeEnd - timeBegin;
                BestCheckMate bestCheckMate = new BestCheckMate(b, time, distance);
                return bestCheckMate;
                
            }
            return null;
        }
        else
        {
            Move[] moves = b.getValidMoves();
            int newDistance = distance+1;
            BestCheckMate bestCheckMate=null;
            for(Move m : moves)
            {
                    Board newBoard = b.clone();
                    newBoard.makeMove(m);
                    bestCheckMate = dls(newBoard, LimitDeep, newDistance);
                    if(bestCheckMate!=null)
                    {
                        return bestCheckMate;
                    }
            }
            return bestCheckMate;
        }
    }
    
    
}
