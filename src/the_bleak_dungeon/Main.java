import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
	// We declare some constants that will be referenced several times later
	// throughout the code.
	// Text color ANSI constants
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	// Room IDs, which would be used by the parser in order to determine the
	// properties of every room.
	public static final int ROOM_HALLWAY = 0;
	public static final int ROOM_EASY = 1; // East
	public static final int ROOM_MEDIUM = 2; // West
	public static final int ROOM_HARD = 3; // South
	public static final int ROOM_BOSS = 4; // North

	// Directions which directly correspond to our room IDs.
	public static final String DIRECTION_NORTH = "north";
	public static final String DIRECTION_SOUTH = "south";
	public static final String DIRECTION_EAST = "east";
	public static final String DIRECTION_WEST = "west";

	// The following commands declare our valid commands for the parser.
	public static final String EXIT_COMMAND = "exit";
	public static final String GO_COMMAND = "go";
	public static final String HELP_COMMAND = "help";
	public static final String INVENTORY_COMMAND = "inventory";
	public static final String LOOK_COMMAND = "look around";

	// The next constants declare messages intended to be displayed to our user.
	public static final String MESSAGE_EXCEPTION = "An error has occured. %s\nIf you experience problems, please restart the program.";
	public static final String MESSAGE_WELCOME = "Note: if you see weird formatting marks, then please use an ANSI-supported terminal.\nExamples include Linux's various terminal emulators and the Windows Terminal app on Windows 10 and above.\n\nWelcome to \"The Bleak Dungeon\"! \nIn this game, you get to follow around your character as they attempt to escape a dark, deep, dungeon filled with secrets and terror.\nTo continue, enter your name: ";
	public static final String MESSAGE_HALLWAYDESC = "The vast walls of the dark dreary dungeon send shivers down your spine. You, %s, carry around a small torch illuminating some spots of this dingy abyss. The air is cool and smells acrid and corrosive. \nUp ahead, there's a vast door with what looks like a sword smybol secured with a heavy padlock.\nEast, you notice a long hallway with a torch at its end, mysteriously lit. \nWest, there's a short but unlit hallway.\nThere's also a long dark hallway to your south. \nYou have woken up in this mysterious dungeon without any clue to how you got here and wonder what to do, a little scared but excited to try following adventure's path for once. You notice, in your backpack, that you have %s vials, where each one of them has a plain myseterious label holding only the word "
			+ ANSI_RED + "\"LIFE\"" + ANSI_RESET + ".";
	public static final String MESSAGE_HALLWAYDESC_BOSS = "The vast walls of the dark dreary dungeon send shivers down your spine. You, %s, carry around a small torch illuminating some spots of this dingy abyss. The air is cool and smells acrid and corrosive. \nUp ahead, there's a vast door with what looks like a sword smybol, however, it's open and dark. You feel urged to explore the darkness behind it.";
	public static final String MESSAGE_GETHELP = "To get help, type: \"help\".";
	public static final String MESSAGE_ENTERCOMMAND = "Input your command to continue.";
	public static final String MESSAGE_HELP = "\"The Bleak Dungeon\" is an RPG Style game which follows your character along their journey through an uncharted dungeon. You interact with your character by typing in commands that instruct your character what to do. The most notable commands are:"
			+ "\n\"" + HELP_COMMAND + "\" - which prints this help text." + "\n\"" + LOOK_COMMAND
			+ "\" - which gives you an overview of the room you're in." + "\n\"" + EXIT_COMMAND
			+ "\" - which exits the game." + "\n\"" + GO_COMMAND
			+ " direction\" - which instructs your character to go in a specified direction." + "\n\""
			+ INVENTORY_COMMAND + "\" - which lists the contents of your inventory.";
	public static final String MESSAGE_ROOM_EASYDESC = "You enter the mysteriously lit hallway.\nYou notice, at its end, other than the silence accompanying you wherever you go, that this hallway has three doors.\nYou are not able to see anything through the doorframes, the doors are magically jet black.\nYou notice the words on the wall, \"If you want the rock, guess the door! No cheating!\"";
	public static final String MESSAGE_ROOM_MEDIUMDESC = "Despite this room being completely dark, your dim torch illuminates most of its contents. There's an odd tree growing in its center, a pretty impossible thing for a regular plant without sunlight or water. On this tree, there are 4 apples, 3 of which are rotten and one of which is strangely fresh and ripe. Behind the tree, there are 4 unlabeled doors leading to what you presume would be 4 different rooms.";
	public static final String MESSAGE_ROOM_HARDDESC = "As you crawl through the hallway, you can't help but notice you've been walking for quite a while.\nAs you look back, you suddenly find, instead of the hallway you were standing in, a massive cavern with 5 odd looking openings which presumably lead to 5 different rooms.\nYou look back to where you were walking and you find there the hallway instead.\nThe cavern seems to be filled with long fat stalagmites which look very sharp.";
	public static final String MESSAGE_ROOM_GUIDE = "Type the number of the door you desire or type \"0\" to go back.";
	public static final String MESSAGE_ITEM_AQUIRE = "You carefully enter the room. In the middle of the room, there's a table with only a %s on it. Success!\nYou victoriously leave the room.";
	public static final String MESSAGE_TRAP = "You find the room empty, odd.\nHowever, the floor starts shaking and you soon find yourself falling down a lethal fall with an unknown fate.\nNot knowing what to do, you hastily drink one of the life potions in your inventory. You suddenly find yourself in the same room you were once in but you now have %s vials instead of %s.";
	public static final String MESSAGE_TRAP_LOSS = "You find the room empty, odd.\nHowever, the floor starts shaking and you soon find yourself falling down a lethal fall with an unknown fate.\nYou seek your backpack for something that can help you but you don't find anything.\nYou are left to experience a shameful, painful death.\n%s has not passed the trial of the dungeons! Better luck next time!";
	public static final String MESSAGE_RETRY = "Try Again? (Y|N)";
	public static final String MESSAGE_BOSSDESC = "Filled with anticipation and curiosity, you vigilantly enter the large, inky room.\nSurprised, you find a gentleman-like skeleton sitting in the corner of the room with what looks like a firey sword blade. The skeleton starts to approach you with foul-filled eyes.\nAfraid, you try to turn back and run, however, the door you came from is simply not there anymore.\nThe skeleton, finally having reached you, forcefully takes the items you've acquried and lays them out in front of you.\n\"So, you want to leave this place, huh?\", he asks. Unsure, you reply with a small nod.\nThe skeleton chuckles, then says, \"If you want to leave this place you have to beat me in a best-of-three game of ro-sham-bo first.\"";
	public static final String MESSAGE_WIN = "\nThe skeleton suddenly shrieks, and his large firey sword drops to the ground.\nBlack ash surrounds the skeleton, then he disappears without warning. Behind him, you see a long tunnel with a dim light at the end.\nFollowing the tunnel, you find yourself exiting a hidden cave on your local beach."
			+ ANSI_RED + "\nThe End. %s has escaped the dungeon. Congratulations!" + ANSI_RESET;
	public static final String MESSAGE_LOSS = "\nThe skeleton laughs a malice-filled hollow laugh, then, without warning, proceeds to stab you with his sword.\nAs you lose conciousness, you look at that blank skeleton face one last time and wonder how it all went wrong."
			+ ANSI_RED + "\nThe End. %s has failed to escape the dungeon. Better luck next time!" + ANSI_RESET;

	// We now declare some global variables which will be referenced and changed by
	// our different methods.
	// This variable stores the user's name throughout the program to be used later
	// in the story line.
	private static String username;

	// This variable stores the current room the user is in, in order to determine
	// room-specific actions. It is set to the hallway by default.
	private static int currentRoom = ROOM_HALLWAY;

	// This variable stores the number of lives the user has left and is 5 by
	// default.
	private static int livesLeft = 5;

	// These booleans determine whether our user has acquired the items required to
	// fight the boss.
	private static boolean hasRock = false; // Easy
	private static boolean hasPaper = false; // Medium
	private static boolean hasScissors = false; // Hard

	// This boolean control whether or not the game is in cheat mode. In cheat mode,
	// the game would inform the player of the choices they need to make in order to
	// win. It is accessible by setting the user's name to "cheat".
	private static boolean isCheatMode = false;

	// This variable contains our scanner, which is our own instance of the Java
	// Scanner class we will use to get user input.
	protected static Scanner scn;

	/**
	 * The main method is executed as soon as we hit the run button, and is used to
	 * execute other methods and perform different actions.
	 * 
	 * @param args contains any string arguments that we might've passed to the
	 *             program during startup. For our purposes however this isn't used.
	 */
	public static void main(String[] args) {
		scn = new Scanner(System.in);
		// We first set the background to blue using an ANSI escape code, because it
		// looks slightly better.
		System.out.print("\u001B[44m");
		// We then print our welcome message to the user.
		System.out.print(MESSAGE_WELCOME);
		// We finally call the method responsible for getting the user's name.
		getUsername();
		// After our user name has been set, we proceed with the story line by executing
		// all actions necessary in the hallway of rooms.
		executeHallwayActions();
	}

	/**
	 * In order to avoid cluttering the main method, we separate the logic to get
	 * the user's name in a separate method.
	 */
	private static void getUsername() {
		// This while loop would continue looping until a valid username is set.
		while (username == null) {
			// We proceed to get user input by using a separate method that shares the user
			// input acquisition by the program with other methods.
			String input = getUserInput();
			// If the user has input the cheat code, we have to activate cheat mode which
			// would allow the user to win easily.
			if (input.equals("cheat")) {
				isCheatMode = true;
				System.out.println("Cheat mode activated!");
			}
			// If the username is too long, we ask the user to input a shorter one. This is
			// because we don't want odd looking text because of insanely long usernames
			// that might break our program.
			if (input.length() > 48) {
				System.out.println("The name you've input is too long. Try again.");
				continue;
			}
			// After making sure all conditions are met, we proceed with saving the username
			// we acquired from the user.
			username = input;
		}
	}

	/**
	 * This method is executed whenever our user ends up in the hallway of rooms. It
	 * is also executed after the user inputs their username.
	 */
	private static void executeHallwayActions() {
		boolean isBoss = (hasRock && hasPaper && hasScissors); // This boolean checks whether or not the user is ready
		// to fight the boss.
		// If the current room is not the hallway, we make sure that it is to help other
		// methods identify the user's location.
		currentRoom = ROOM_HALLWAY;
		System.out.println(""); // We print an empty line for cosmetic purposes.
		// We first have to check if the hallway door should be unlocked or not.
		if (!isBoss) {
			// We proceed to inform the user with the description of the hallway they are
			// currently standing in. We also need to format the message with the user's
			// name and the number of remaining lives they have.
			System.out.println(String.format(MESSAGE_HALLWAYDESC, username, livesLeft));
		} else {
			// We proceed to inform the user with the description of the hallway they are
			// currently standing in (which has the boss room unlocked). We also need to
			// format the message with the user's
			// name.
			System.out.println(String.format(MESSAGE_HALLWAYDESC_BOSS, username));
		}
		// After that we inform new users how to get help in our program.
		System.out.println(MESSAGE_GETHELP);

		// We will now use logic which handles what sort of command the user has input.
		// We start with an integer which stores the user's next destination as per
		// their will.
		int nextRoom = -1;
		// We use this while loop to ensure that we eventually get a valid room to go
		// to.
		while (nextRoom == -1) {
			System.out.println(MESSAGE_ENTERCOMMAND); // Each time a command is input, we remind the user how to
			// interact with our program.
			// We start by using the scanner to get user input with our custom method.
			String input = getUserInput();
			System.out.println(""); // We print an empty line for cosmetic purposes.
			// If the user has entered a "go" command, we have to handle this command in a
			// special way because it is more specific than other commands.
			if (input.startsWith(GO_COMMAND)) { // We first check if the input starts with the special "go" command.
				// We then determine the direction the user has entered, and we use conditional
				// logic to determine where to go.
				String direction = input.replace(GO_COMMAND, "").strip();
				switch (direction) {
				case DIRECTION_NORTH: // Boss
					if (!isBoss) { // If the door is locked, the user shouldn't be able to
						// go there.
						System.out.println("You need to unlock this door first!");
						break;
					}
					nextRoom = ROOM_BOSS; // Else, we proceed to the boss challenge.
					break;
				case DIRECTION_EAST: // Easy
					if (hasRock) { // If the user already has the rock, their action is irrelevant.
						System.out.println("You already have the rock!");
						break;
					}
					nextRoom = ROOM_EASY; // Else, we proceed to the easy challenge.
					break;
				case DIRECTION_WEST: // Medium
					if (hasPaper) { // If the user already has the piece of paper, their action is irrelevant.
						System.out.println("You already have the piece of paper!");
						break;
					}
					nextRoom = ROOM_MEDIUM; // Else, we proceed to the medium challenge.
					break;
				case DIRECTION_SOUTH: // Hard
					if (hasScissors) { // If the user already has the pair of scissors, their action is irrelevant.
						System.out.println("You already have the pair of scissors!");
						break;
					}
					nextRoom = ROOM_HARD; // Else, we proceed to the hard challenge.
					break;
				default: // If the user has input an invalid direction, we handle that accordingly.
					System.out.println(direction
							+ " is not a valid direction! Valid directions are: north, south, east, and west.");
					break;
				}
			} else {
				switch (input) { // This switch statement will help us parse some of the commands that we might
				// receive other than the "go" command.
				case HELP_COMMAND: // If the user passes in the "help" command, we clear the screen then print out
					// the help text.
					clearScreen();
					System.out.println(MESSAGE_HELP);
					break;
				case LOOK_COMMAND: // If the user passes in the "look around" command, we inform them of their
					// surroundings again.
					// We first have to check if the hallway door should be unlocked or not.
					if (!isBoss) {
						// We proceed to inform the user with the description of the hallway they are
						// currently standing in. We also need to format the message with the user's
						// name and the number of remaining lives they have.
						System.out.println(String.format(MESSAGE_HALLWAYDESC, username, livesLeft));
					} else {
						// We proceed to inform the user with the description of the hallway they are
						// currently standing in (which has the boss room unlocked). We also need to
						// format the message with the user's
						// name.
						System.out.println(String.format(MESSAGE_HALLWAYDESC_BOSS, username));
					}
					break;
				case INVENTORY_COMMAND: // If the user passes in the "inventory" command we proceed with listing their
					// inventory's contents.
					// We first print out their lives because the life vials are essentially a part
					// of the inventory after all.
					System.out.println(ANSI_RED + "LIVES LEFT = " + livesLeft + ANSI_RESET);
					String acquiredItem = "You have%sacquired the %s."; // This sample string will be formatted
					// accordingly to indicate if the user has
					// acquired or has not acquired a particular
					// item.
					String not = " not ";
					String space = " ";
					if (hasRock) {
						System.out.println(String.format(acquiredItem, space, "rock"));
					} else {
						System.out.println(String.format(acquiredItem, not, "rock"));
					}
					if (hasPaper) {
						System.out.println(String.format(acquiredItem, space, "piece of paper"));
					} else {
						System.out.println(String.format(acquiredItem, not, "piece of paper"));
					}
					if (hasScissors) {
						System.out.println(String.format(acquiredItem, space, "pair of scissors"));
					} else {
						System.out.println(String.format(acquiredItem, not, "pair of scissors"));
					}
					if (isCheatMode) { // If the user has cheat mode enabled, it is shown in the inventory.
						System.out.println("Cheat mode active.");
					}
					break;
				case EXIT_COMMAND: // If the user passes in the "exit" command, we first have to confirm their
					// choice.
					System.out.print("Are you sure? (Y|N) ");
					String yn = getUserInput();
					if (yn.equals("y")) { // Only after we confirm that the user desires to exit we proceed
						// We close the scanner first to avoid creating any exceptions.
						scn.close();
						// We then exit the program with error code 0 (success error code, meaning no
						// errors).
						System.exit(0);
					}
					break;
				case "debug_00fca1d2e": // If the user passes in an undocumented "debug" command, we proceed with
					// skipping directly to the boss room.
					// FOR DEVELOPMENT USES ONLY
					hasRock = true;
					hasPaper = true;
					hasScissors = true;
					isBoss = true;
					System.out.println("Debug_00fca1d2e active. Skipped to boss room.");
					break;
				default: // If the user has input an invalid command, we inform them their character
					// cannot perform the action they desire.
					System.out.println(username + " does not know how to " + input + "!");
					break;
				}
			}
		}
		currentRoom = nextRoom; // After we've acquired which room should we go to next, we proceed with
		// executing the appropriate commands in a separate method.
		// We make a quick check to make sure that the room we want to go to is not the
		// final room or a nonexistent room, then we proceed.
		if (currentRoom != -1 && currentRoom != ROOM_BOSS) {
			executeRoomActions();
		}
		// If the user is finally ready to go to the boss room, we proceed by calling
		// the relevant method.
		if (isBoss && currentRoom == ROOM_BOSS) {
			executeBossActions();
		}
	}

	/**
	 * This method is used whenever a user enters one of the challenge rooms.
	 */
	private static void executeRoomActions() {
		// We first start out with an infinite while loop that would only be broken by
		// winning the item found in the room or under the user's will.
		boolean leaveRoom = false;
		while (!leaveRoom) {
			// We check which room the player is currently in to know what sort of actions
			// must we take.
			switch (currentRoom) {
			case ROOM_EASY: // Easy
				leaveRoom = executeRoomShared(MESSAGE_ROOM_EASYDESC, 3, "rock");
				break;
			case ROOM_MEDIUM: // Medium
				leaveRoom = executeRoomShared(MESSAGE_ROOM_MEDIUMDESC, 4, "piece of paper");
				break;
			case ROOM_HARD: // Hard
				leaveRoom = executeRoomShared(MESSAGE_ROOM_HARDDESC, 5, "pair of scissors");
				break;
			default: // If it's not one of the rooms then something's certainly wrong.
				System.out.println(MESSAGE_EXCEPTION);
				break;
			}
		}
		executeHallwayActions(); // After exiting our loop, we are sure to return to the hallway.
	}

	/**
	 * This method contains shared code for all rooms and returns whether the
	 * program should leave the room or not.
	 * 
	 * @param roomDesc      is the description of the room the user is in.
	 * @param numberOfDoors is the number of doors contained in that room.
	 * @param item          is the item that the user will acquire upon winning the
	 *                      guessing game.
	 * @param hasItem       is the boolean storing whether the user has this
	 *                      specified item or not.
	 */
	private static boolean executeRoomShared(String roomDesc, int numberOfDoors, String item) {
		// We first print out the description of the room we're in to help our user know
		// what to do.
		System.out.println(roomDesc);
		// The system generates a random correct door every time we execute our code and
		// stores it in memory.
		int correctDoor = generateRandomNumber(numberOfDoors + 1);
		// If the user is in cheat mode, we have to tell them of the correct number to
		// win the room.
		if (isCheatMode) {
			System.out.println("The correct number to win the " + item + " is " + correctDoor + ".");
		}
		// We proceed with getting the door number the user desires.
		int doorNumber = getDoorNumber(numberOfDoors);
		// If the user has input a zero, this means that they want to leave to the
		// hallway.
		if (doorNumber == 0) {
			return true;
		} else { // Else, our user enters through the door they chose.
			System.out.println("You enter through door number " + doorNumber + ".");
		}
		// If the user has indeed guessed the correct door number
		if (correctDoor == doorNumber) {
			// They now have the item they've found
			if (numberOfDoors == 3) { // Because we can't pass the booleans as arguments to the method (or else they
				// won't get updated), we have to take the approach of manually updating them
				hasRock = true;
			} else if (numberOfDoors == 4) {
				hasPaper = true;
			} else if (numberOfDoors == 5) {
				hasScissors = true;
			}
			System.out.println(String.format(MESSAGE_ITEM_AQUIRE, item)); // We tell them that, then
			return true; // We go back to the hallway in order for the user to complete the remaining
			// challenges (if any)
		} else { // Else, they did not and they enter a trap room.
			if (livesLeft > 0) { // If their life count is positive
				// It is first decreased, then..
				livesLeft--;
				// The system informs the user that they have indeed lost life by falling in a
				// trap and asks them if they want to retry guessing or not.
				System.out.println(String.format(MESSAGE_TRAP, livesLeft, livesLeft + 1));
				System.out.println(MESSAGE_RETRY);
				String yn = getUserInput();
				if (yn.equals("y")) { // If they do, the work of this method ends and the same thing that has already
					// happened would be repeated.
					return false;
				} else { // Else, the user goes back to the hallway.
					return true;
				}
			} else { // BUT if the user's lives are already zero, this means that they have lost the
				// game.
				// We inform them first of their loss
				System.out.println(String.format(MESSAGE_TRAP_LOSS, username));
				// We then close the scanner first to avoid creating any exceptions.
				scn.close();
				// We then exit the program with error code 0 (success error code, meaning no
				// errors).
				System.exit(0);
			}
		}
		return false;
	}

	/**
	 * In order to avoid cluttering our room code, we separate the logic to get the
	 * door number in a separate method because it's not a typical string.
	 * 
	 * @param maxNumber is the maximum number the user can input (which should be
	 *                  the number of doors in a given room).
	 */
	private static int getDoorNumber(int maxNumber) {
		// We start out by printing the message which instructs the user to type a
		// desired door number.
		System.out.println(MESSAGE_ROOM_GUIDE);
		int input = -1; // This integer acts as a temporary storage for the input acquired from the
		// user.
		// As long as the input is still invalid we keep looping until we get a valid
		// door number.
		while (true) {
			try { // We surround this logic with a try-catch statement because the user might
					// input a string instead of a number.
				if (scn.hasNextLine()) { // This if-statement prevents crashes with the scanner if it is not ready to
					// receive input.
					// We attempt to read the door number with the help of the Java's Scanner class.
					// The next() method returns to us one character input by the user because our
					// door number should only be a single byte.
					// We also parse this character as an integer.
					input = Integer.parseInt(scn.next());
					scn.nextLine(); // This line is required because the scanner consumes an empty line after
					// reading a single character.
				} else {
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid number! Try again.");
				continue;
			}
			// We do a quick check in order to make sure that the input we got is strictly
			// positive and also less than our door count, else we ask the user to input
			// another number.
			if (input > maxNumber || input < 0) {
				System.out.println("Invalid number! Try again.");
				continue;
			}
			return input; // After making sure all conditions are met, we proceed with saving the door
			// number we acquired from the user.
		}
	}

	/**
	 * This method is used when the user enters the boss room.
	 */
	private static void executeBossActions() {
		// We first print out the description of the boss's room to help our user know
		// what to do.
		System.out.println(MESSAGE_BOSSDESC);
		// We have to create a score variable which would determine the user's combined
		// result in the three rounds of the rock-paper-scissors game.
		int score = 0;
		// This string array will contain the three valid choices which will organize
		// our conditional check and organize our random logic generation
		String[] choices = { "rock", "paper", "scissors" };
		for (int i = 0; i < 3;) { // This for loop would repeat the same actions taken to present a round of
			// rock-paper-scissors to the user for three times (= three rounds)
			// This while loop infinitely repeats itself to ensure repeating the round in
			// case a tie occurs for as long as the user keeps getting a tie with the
			// computer.
			while (true) {
				// The following code uses the random number generator to generate a random
				// number between 1 and 3 since our number generator removes the zero from the
				// possible choices, and then subtracts one from it so that our number is
				// between 0 and 2 which would directly correspond to an index in the array of
				// possible choices.
				String computerChoice = choices[generateRandomNumber(4) - 1];
				// If the user is in cheat mode we have to inform them of the computer generated
				// choice before inputting their own.
				if (isCheatMode)
					System.out.println("\nThe skeleton scores a " + computerChoice + ".");
				// We present the user the choices that they can play from.
				System.out.print("\nRock, paper, scissors! Type your choice: ");
				// We then proceed to acquire the user's choice
				String input = getUserInput();
				// If the user has input something invalid, we cannot continue. We have to
				// re-prompt the user for input.
				if (!Arrays.asList(choices).contains(input)) {
					System.out.println("Please input a valid choice!");
					continue;
				}
				// The system then informs the user with the generated choice, only if the user
				// is not in cheat mode.
				if (!isCheatMode)
					System.out.println("The skeleton scores a " + computerChoice + ".");
				if (input.equals(computerChoice)) { // If a tie occurs, it is not a valid case so we have to repeat the
					// round.
					System.out.println("A tie! Try again.");
					continue;
				}
				boolean result = false; // This boolean will store the result of the player in the round.
				// The following conditionals check if the user would win the skeleton or would
				// not.
				if (input.equals(choices[0])) { // Rock beats scissors
					if (computerChoice.equals(choices[2]))
						result = true;
				} else if (input.equals(choices[1])) { // Paper beats rock
					if (computerChoice.equals(choices[0]))
						result = true;
				} else if (input.equals(choices[2])) { // Scissors beat paper
					if (computerChoice.equals(choices[1]))
						result = true;
				}

				// We then proceed to inform the user whether they won the round or not and we
				// update the score variable accordingly.
				if (result == true) {
					System.out.println(username + " won this round!");
					score++;
				} else {
					System.out.println(username + " lost this round!");
					score--;
				}
				break;
			}
			i++; // We have to increment our index to proceed with the for loop
		}

		if (score > 0) { // So long as the score is positive, the user has won the game.
			System.out.println(String.format(MESSAGE_WIN, username));
		} else { // Else, they have lost.
			System.out.println(String.format(MESSAGE_LOSS, username));
		}
		// After informing the user with their final result, we proceed to exit the
		// game.
		// We close the scanner first to avoid creating any exceptions.
		scn.close();
		// We then exit the program with error code 0 (success error code, meaning no
		// errors).
		System.exit(0);
	}

	// The following methods are helper methods that are used by various other
	// methods to make our code simple and clutter-free.
	/**
	 * We make a shared method which would acquire string input from the user that
	 * is used by our username logic as well as by the command parser.
	 */
	private static String getUserInput() {
		// We first create a sample input string and we set it to be empty
		String input = "";
		// We then use a while loop that would continue to loop as long as the scanner
		// has not received valid input.
		boolean hasInput = false;
		while (!hasInput) {
			if (scn.hasNextLine()) { // This if-statement prevents crashes with the scanner if it is not ready to
				// receive input.
				// We attempt to read user input with the help of the Java's Scanner class.
				// The nextLine() method returns to us the string of characters input by the
				// user.
				input = scn.nextLine();
			} else {
				continue;
			}
			// We do a quick check in order to make sure that the input we got is not empty.
			// If we did, we prompt the user to try entering input again.
			if (input == null || input.strip().isEmpty()) {
				System.out.println("You must enter something!");
				continue;
			}
			// After we make sure that we have a non-empty string, we format it and return
			// it to the calling method.
			input = input.strip().toLowerCase();
			hasInput = true; // Break while loop
		}
		return input;
	}

	/**
	 * This method clears the screen using ANSI escape codes. It will not work on
	 * Windows's CMD.
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J"); // This ANSI escape code is responsible for clearing the screen.
		System.out.flush();
	}

	/**
	 * This method generates a random number from 0 to a given upper bound.
	 * 
	 * @param upperBound is the upperBound specified by the calling method, the
	 *                   highest number returned would be upperBound - 1.
	 */
	private static int generateRandomNumber(int upperBound) {
		// To create this random number, we use Java's Random class.
		Random ran = new Random();
		// We create a dummy result and we set it to zero, this is because the number
		// generator might produce a zero and this messes up our program's logic.
		int result = 0;
		// This while loop ensures that the generated number is always strictly
		// positive.
		while (result == 0) {
			result = ran.nextInt(upperBound);
		}
		return result; // We return the result after it met our condition.
	}
}
