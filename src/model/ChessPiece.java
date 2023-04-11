package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (target.getOwner() == this.owner){
            return false;
        }
        if (this.name.equals("Elephant") && target.name.equals("Rat")){
            return false;
        }
        if (this.name.equals("Rat") && target.name.equals("Elephant")){
            return true;
        }
        if (this.rank >= target.rank) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    //toString
    @Override
    public String toString() {
        return "("+ owner + ","+ name + ","+ rank +") ";
    }
}
