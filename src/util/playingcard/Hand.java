package util.playingcard;

import java.util.ArrayList;

public class Hand extends ArrayList<Card> {

    public void show() {
        if (isEmpty())
            System.out.println("(Empty)");
        else {
            for (Card card : this)
                System.out.print(card);
        }
        System.out.println();
    }

    public void show(String param) {
        if (isEmpty())
            System.out.println("(Empty)");
        else {
            switch (param) {
                case "WITH_INDEX":
                    for (int i = 0; i < this.size(); i++)
                        System.out.println((i + 1) + ": " + this.get(i));
                    break;
                case "NUM_ONLY":
                    for (Card card : this)
                        System.out.println(card.toString(true));
                    break;
                case "SUIT_ONLY":
                    for (Card card : this)
                        System.out.println(card.toString(false));
                    break;
            }
        }
    }

    public void sort() {
        super.sort(new CardSorter());
    }

}
