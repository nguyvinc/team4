package models;

import java.util.ArrayList;

public class GameReg {
    public java.util.List<Column> cols = new ArrayList<>();
    public RegDeck deck = new RegDeck();
    //public SpanDeck span = new SpanDeck();

    public GameReg(){

        /*for(int i = 0; i < 4; i++)
        {
            cols.add(new ArrayList<Card>());
        }*/
        for(int i = 0; i < 4; i++) {
            cols.add(new Column(i + 1));
        }
    }

    public void dealFour() {
        // remove the top card from the deck and add it to a column; repeat for each of the four columns
        if(deck.getSize() > 0) {
            for (int i = 0; i < 4 && deck.getSize() > 0; i++) {
                Card top = deck.popCard(deck.getSize() - 1);        //retrieve top card of deck
                deck.removeCard(deck.getSize() - 1);                //remove it right after LuL
                addCardToCol(i, top);
            }
        }
    }

    public void remove(int columnNumber) {
        // remove the top card from the indicated column
        if (!columnHasCards(columnNumber))
            return;
        Card toRemove = getTopCard(columnNumber);
        //System.out.println("toRemove is a " + toRemove.getValue() + " of " + toRemove.getSuit());
        for (int i = 0; i < 4; i++) {
            if (i != columnNumber && columnHasCards(i)) {
                Card temp = getTopCard(i);
                if ((toRemove.getSuit() == temp.getSuit()) && (toRemove.getValue() < temp.getValue() || temp.getValue() == 1)) {
                    removeCardFromCol(columnNumber);
                    return;
                }
            }
        }
    }

    private boolean columnHasCards(int columnNumber) {
        // check indicated column for number of cards; if no cards return false, otherwise return true
        return (this.cols.get(columnNumber).cards.size() != 0);
    }

    private Card getTopCard(int columnNumber) {
        return this.cols.get(columnNumber).cards.get(this.cols.get(columnNumber).cards.size()-1);
    }


    public void move(int columnFrom, int columnTo){
       if(columnHasCards(columnFrom)) {
           Card temp = getTopCard(columnFrom);
           if (!columnHasCards(columnTo) && ((temp.getValue() == 14) || (temp.getValue() == 0) || (temp.getValue() == 1))) {
               removeCardFromCol(columnFrom);
               addCardToCol(columnTo, temp);
           }
       }
    }

    private void addCardToCol(int columnTo, Card cardToMove) {
        cols.get(columnTo).cards.add(cardToMove);
    }

    private void removeCardFromCol(int colFrom) {
        this.cols.get(colFrom).cards.remove(this.cols.get(colFrom).cards.size() - 1);
    }

    //Adding a clear columns function to work with toggling between the decks
    public void clearColumns(){
        deck = new RegDeck();
        for(int i = 0; i < 4; i++)
        {
            while(columnHasCards(i))
                cols.get(i).cards.remove(cols.get(i).cards.size() - 1);

        }
    }

    //Used for Testing Unit
    public void customDeal(int flag) {
        if (flag == 0) {
            for (int i = 0; i < 4; i++) {
                Card temp = new Card(i + 11, Suit.Clubs);
                addCardToCol(i, temp);
            }
        }
    }
}
