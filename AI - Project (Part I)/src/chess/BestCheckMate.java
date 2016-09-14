/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import static chess.Board.BOARDSIZE;

/**
 *
 * @author yerco
 */
public class BestCheckMate 
{
    private Board board;
    private long time;
    private int deep;

    public BestCheckMate(Board board, long time, int deep) {
        this.board = board;
        this.time = time;
        this.deep = deep;
    }

    public Board getBoard() {
        return board;
    }

    public long getTime() {
        return time;
    }

    public int getDeep() {
        return deep;
    }
    
    /**	Convert a given board into a string
    **	@return a string representing the board state
    **/
    public String toString() {
           //TODO: fix the enroques
            String endl=System.getProperty("line.separator");
            String s="CheckMate"+endl;
            int[][] b = board.b;
            for (int i=0; i<BOARDSIZE;i++) {
                    for (int j=0; j<BOARDSIZE;j++) {
                                    s+=b[i][j]+" ";
                    }
                    s+=endl;
            }
            s+="Deep: "+deep+endl;
            s+="Time total: "+time+endl;
            return s;
    }

    
}
