
/**
 * Class Room - an Item in an adventure game.
 *
 * @author (Cristian de la Fuente)
 * @version (14/03/2018)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private int weigth;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weigth)
    {
        this.description = description;
        this.weigth = weigth;
    }

    /**
     * Return the Item description and the Item weigth
     *
     * @return    the Item description and weigth
     */
    public String getItem()
    {
        return description + " " + weigth + " gramos";
    }
}
