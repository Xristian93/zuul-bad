import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> mapaDirecciones;
    private Item roomItem;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, Item item) 
    {
        this.description = description;
        mapaDirecciones = new HashMap<>();
        roomItem = item;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor){
        mapaDirecciones.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param direccion La direccion a comprobar.
     * @return el objeto de la clase Room asociado a la linea introducida por parametro.
     */
    public Room getExit(String direccion)
    {
        return mapaDirecciones.get(direccion);
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        Set<String> direcciones = mapaDirecciones.keySet();
        String descripcionDirecciones = "Salidas: ";
        
        for (String direccionActual : direcciones){
            descripcionDirecciones += direccionActual + " ";
        }
        
        return descripcionDirecciones;
    }
    
    /**
     * Return a long description of this room, of the form:
     *      You are in the "name of room"
     *      Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        String actualItem = "";
        if (roomItem != null){
            actualItem = roomItem.getItem();
        }
        return "Estas en " + description + ".\n" + getExitString() + ".\n" + actualItem;
    }
}
