
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
    private String id;
    private boolean canBePickedUp;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weigth, String id, boolean canBePickedUp)
    {
        this.description = description;
        this.weigth = weigth;
        this.id = id;
        this.canBePickedUp = canBePickedUp;
    }

    /**
     * Return the Item description, the Item weigth and the Item id
     *
     * @return    the Item description, weigth and id
     */
    public String getItem()
    {
        return description + " " + weigth + " gramos" + " // ID: " + id;
    }
    
    /**
     * Return the Item weigth
     *
     * @return    the Item weigth
     */
    public int getWeigth()
    {
        return weigth;
    }
    
    /**
     * Return the Item ID
     *
     * @return    the Item ID
     */
    public String getItemId()
    {
        return id;
    }
}
