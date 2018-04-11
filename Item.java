
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
    private boolean canBePicked;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weigth, String id, boolean canBePicked)
    {
        this.description = description;
        this.weigth = weigth;
        this.id = id;
        this.canBePicked = canBePicked;
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
    
    /**
     * Return if the object can be picked
     *
     * @return    the Item canBePickep
     */
    public boolean getCanBePicked()
    {
        return canBePicked;
    }
}
