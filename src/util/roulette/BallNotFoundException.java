package util.roulette;

class BallNotFoundException extends RuntimeException {
    BallNotFoundException() {
        super("The ball does not exist.");
    }

    BallNotFoundException(String text) {
        super(text);
    }
}
