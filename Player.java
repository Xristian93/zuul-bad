import java.util.ArrayList;
import java.util.Stack;

/**
 * This class simulate a player in the game
 *
 * @author (Cristian de la Fuente Garcia)
 * @version (23/03/2018)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> roomStack;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        currentRoom = null;
        roomStack = new Stack<Room>();
    }
    
    public void setCurrentRoom(Room room){
        currentRoom = room;
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("No hay ninguna puerta!");
        }
        else {
            roomStack.push(currentRoom);
            // Try to leave current room.
            currentRoom = currentRoom.getExit(direction);
            look();
        }
    }
    
    /**
     * Print out a look of the actual location.
     */
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
        if (currentRoom.getDescription().equals("La terraza exterior, esta lloviendo")){
            System.out.println("Has encontrado y asesinado a tu victima. Felicidades!!");
        }
    }
    
    /** 
     * Intenta ir a la anterior habitacion. El jugador no debe cambiar de localización si este comando
     * se invoca al inicio o si se invoca dos o más veces seguidas sin haber ejecutado el comando go entre ellas.
     */
    public void back() 
    {
        if (!roomStack.empty()){
            currentRoom = roomStack.pop();
            look();
        }
        else{
            System.out.println("No puedes volver atras, estas en la posicion inicial");
        }
    }
    
    /**
     * Eat some food
     */
    public void eat() 
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }
}
