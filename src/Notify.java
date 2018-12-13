public class Notify implements Observer {
    String message;

    @Override
    public String notifyObserver(String amount) {
        return "$"+amount+" was withdrawled from your U Card!";
    }
}
