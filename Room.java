import java.util.HashMap;

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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        mapaDirecciones = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param southEast The southEast exit.
     * * @param northWest The northWest exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast, Room northWest) 
    {
        if(north != null)
            mapaDirecciones.put("north", north);
        if(east != null)
            mapaDirecciones.put("east", east);
        if(south != null)
            mapaDirecciones.put("south", south);
        if(west != null)
            mapaDirecciones.put("west", west);
        if(southEast != null)
            mapaDirecciones.put("southEast", southEast);
        if(northWest != null)
            mapaDirecciones.put("northWest", northWest);
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
        Room actualRoomExit = null;
        if(direccion.equals("north"))
            actualRoomExit = mapaDirecciones.get("north");
        if(direccion.equals("east"))
            actualRoomExit = mapaDirecciones.get("east");
        if(direccion.equals("south"))
            actualRoomExit = mapaDirecciones.get("south");
        if(direccion.equals("west"))
            actualRoomExit = mapaDirecciones.get("west");
        if(direccion.equals("southEast"))
            actualRoomExit = mapaDirecciones.get("southEast");
        if(direccion.equals("northWest"))
            actualRoomExit = mapaDirecciones.get("northWest");
        return actualRoomExit;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        String exitString = "";
        if(mapaDirecciones.get("north") != null) {
            exitString += "north ";
        }
        if(mapaDirecciones.get("east") != null) {
            exitString += "east ";
        }
        if(mapaDirecciones.get("south") != null) {
            exitString += "south ";
        }
        if(mapaDirecciones.get("west") != null) {
            exitString += "west ";
        }
        if(mapaDirecciones.get("southEast") != null) {
            exitString += "southEast ";
        }
        if(mapaDirecciones.get("northWest") != null) {
            exitString += "northWest";
        }
        return exitString.trim();
    }
}
