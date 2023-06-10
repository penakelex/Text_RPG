package penakelex.textRPG.homeland.Map;

public interface OnMapClickListener {
    void goingWest();
    void goingSouth();
    void goingNorth();
    void goingEast();
    void startLocation(byte location);
}
