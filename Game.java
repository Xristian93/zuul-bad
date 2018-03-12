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

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
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
        System.out.println("   go quit help");
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

        // Try to leave current room.
        currentRoom = currentRoom.getExit(direction);

        if (currentRoom == null) {
            System.out.println("No hay ninguna puerta!");
        }
        else {
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
        System.out.println("Estas en " + currentRoom.getDescription());
        if (currentRoom.getDescription().equals("La terraza exterior, esta lloviendo")){
            System.out.println("Has encontrado y asesinado a tu victima. Felicidades!!");
        }
        else{
            System.out.println(currentRoom.getExitString());
        }
        System.out.println();
    }
}
