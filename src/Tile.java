
/**
 *
 * @author Ronny
 * @date June 4th, 2019
 */

/*
combine: boolean
number: int

Tile(number)
getNumber(number): void
setCombine(): void
combining(Tile tile): void // combines two tiles
combinable(Tile tile): void // will check if tile can merge with tile calling on the method
 */
public class Tile {

    private boolean combine;
    private int number;

    public Tile(int num) {
        number = num;
    }

    public int getNumber() {
        return number;
    }

    public void setCombine(boolean combinable) {
        combine = combinable;
    }

    public int combine(Tile tile) {
        if (combinable(tile) == true) {
            number *= 2;
            combine = true;
            return number;
        } else {
            return -1;
        }
    }

    public boolean combinable(Tile tile) {
        return combine == false && tile != null && tile.combine == false && number == tile.getNumber();
    }

}
