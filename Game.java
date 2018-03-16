import java.util.Stack;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomStack;
    private ArrayList<Item> bag;
    private int bagWeigth;
    private static final int MAX_WEIGTH = 1000;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomStack = new Stack<Room>();
        bag = new ArrayList<Item>();
        bagWeigth = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entradaEdificio, salonPrincipal, baños, escaleras, pasillo, dormitorioPrincipal, terraza, bañoDormitorio, sotano, trastero, armario;

        // create the rooms
        entradaEdificio = new Room("La entrada al viejo edificio");
        salonPrincipal = new Room("Un gran salon, muy espacioso");
        baños = new Room("Unos simples baños");
        escaleras = new Room("Las escaleras al segundo piso");
        pasillo = new Room("Un pasillo que comunica con las habitaciones");
        dormitorioPrincipal = new Room("Un dormitorio muy grande");
        terraza = new Room("La terraza exterior, esta lloviendo");
        bañoDormitorio = new Room("El baño del dormitorio");
        sotano = new Room("Un sotano oscuro y sucio");
        trastero = new Room("Un simple trastero");
        armario = new Room("Un armario lleno de ropa");

        // create the room items
        salonPrincipal.addItem(new Item("Cofre de oro",500));
        salonPrincipal.addItem(new Item("Cofre de cobre",700));
        dormitorioPrincipal.addItem(new Item("Candelabro de plata",300));
        sotano.addItem(new Item("Antorcha",100));

        // initialise room exits
        entradaEdificio.setExit("north", salonPrincipal);
        entradaEdificio.setExit("east", escaleras);

        salonPrincipal.setExit("west", baños);
        salonPrincipal.setExit("northWest", trastero);
        salonPrincipal.setExit("south", entradaEdificio);

        baños.setExit("east", salonPrincipal);

        trastero.setExit("southEast", salonPrincipal);

        escaleras.setExit("north", pasillo);
        escaleras.setExit("west", entradaEdificio);
        escaleras.setExit("southEast", sotano);

        pasillo.setExit("north", dormitorioPrincipal);
        pasillo.setExit("south", escaleras);

        dormitorioPrincipal.setExit("southEast", bañoDormitorio);
        dormitorioPrincipal.setExit("west", terraza);
        dormitorioPrincipal.setExit("northWest", armario);
        dormitorioPrincipal.setExit("south", pasillo);

        terraza.setExit("east", dormitorioPrincipal);

        armario.setExit("southEast", dormitorioPrincipal);

        currentRoom = entradaEdificio;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar, hasta otra");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al edificio de tu nuevo trabajo, ¿Preparado para dar caza a tu victima?");
        System.out.println("Escribe 'help' si necesitas ayuda");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("items")) {
            items();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Te has perdido, te estas volviendo loco");
        System.out.println("Estas dando vueltas por el edificio.");
        System.out.println();
        System.out.println("Tus comandos son:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Print out the location info.
     */
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
        if (currentRoom.getDescription().equals("La terraza exterior, esta lloviendo")){
            System.out.println("Has encontrado y asesinado a tu victima. Felicidades!!");
        }
        System.out.println();
    }

    /**
     * Print out a look of the actual location.
     */
    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Eat some food
     */
    private void eat() 
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    /** 
     * Intenta ir a la anterior habitacion. El jugador no debe cambiar de localización si este comando
     * se invoca al inicio o si se invoca dos o más veces seguidas sin haber ejecutado el comando go entre ellas.
     */
    private void back() 
    {
        if (!roomStack.empty()){
            currentRoom = roomStack.pop();
            printLocationInfo();
        }
        else{
            System.out.println("No puedes volver atras, estas en la posicion inicial");
        }
    }

    /**
     * Take an item into the bag choosing the item position
     * @param command The position of the item to take
     */
    private void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado la posicion del objeto a coger");
            return;
        }
        ArrayList<Item> actualBag = null;
        if (currentRoom.getItem().size() > 0){
            actualBag = currentRoom.getItem();
        }
        String positionItemToTake = command.getSecondWord();

        if (actualBag != null && bagWeigth + actualBag.get(Integer.parseInt(positionItemToTake)).getItemWeigth() < MAX_WEIGTH){
            System.out.println("Has cogido el siguiente objeto:" + "\n");
            System.out.println("Posicion: " + Integer.parseInt(positionItemToTake) + " " 
                + actualBag.get(Integer.parseInt(positionItemToTake)).getItems());
            bagWeigth += actualBag.get(Integer.parseInt(positionItemToTake)).getItemWeigth();
            bag.add(actualBag.get(Integer.parseInt(positionItemToTake)));
            actualBag.remove(Integer.parseInt(positionItemToTake));
        }

        else{
            if (actualBag == null){
                System.out.println("No hay objetos en la habitacion");
            }
            else{
                System.out.println("Te has pasado del peso de la mochila");
            }
        }
    }

    /**
     * Drop an item into the actual room
     * @param command The position of the item to drop
     */
    private void drop(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado la posicion del objeto a dejar");
            return;
        }

        ArrayList<Item> actualBag = currentRoom.getItem();
        String positionItemToDrop = command.getSecondWord();

        if (bag.size() > 0){
            System.out.println("Has dejado el siguiente objeto:" + "\n");
            System.out.println("Posicion: " + Integer.parseInt(positionItemToDrop) + " " 
                + bag.get(Integer.parseInt(positionItemToDrop)).getItems());
            bagWeigth -= bag.get(Integer.parseInt(positionItemToDrop)).getItemWeigth();
            actualBag.add(bag.get(Integer.parseInt(positionItemToDrop)));
            bag.remove(Integer.parseInt(positionItemToDrop));
        }
        else{
            System.out.println("La mochila esta vacia");
        }
    }

    /**
     * Show information about the player objets
     */
    private void items() 
    {
        if (bag.size() > 0){
            System.out.println("Tu mochila tiene los siguientes objetos");
            for (int i = 0; i < bag.size(); i++){
                System.out.println(bag.get(i).getItems());
            }
        }
        else{
            System.out.println("Tu mochila esta vacia");
        }
    }
}
