package util.playingcard;

public class TestClass {
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Hand hand = new Hand();
        hand.add(new Card(1, 2));
        hand.add(new Card(2, 3));
        hand.add(new Card(0, 0));
        hand.add(new Card(1, 3));
        hand.add(new Card(2, 2));

        hand.sort();
        hand.show();



    }
}
