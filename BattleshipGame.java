import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class BattleshipGame {

    private static final int GRID_SIZE = 10;
    private static final char WATER = '~';
    private static final char HIT = 'X';
    private static final char MISS = 'O';

    private static char[][] playerView = new char[GRID_SIZE][GRID_SIZE];
    private static char[][] shipGrid = new char[GRID_SIZE][GRID_SIZE];

    private static List<Ship> ships = new ArrayList<>();

    private static class Ship {
        int size;
        List<int[]> coordinates = new ArrayList<>();
        int hits = 0;

        Ship(int size) {
            this.size = size;
        }

        boolean isSunk() {
            return hits >= size;
        }
    }
    private static void simulateRadioCheck() throws InterruptedException {
        String[][] dialogue = {
            {"CONTROL", "Private, This is Control, radio check. Do you copy?"},
            {"PRIVATE", "Loud and clear, Control. Ready for bootcamp."},
            {"CONTROL", "Coordinates check. Are we locked in?"},
            {"PRIVATE", "AFFIRMATIVE, Coordinates confirmed. Targets locked."},
            {"CONTROL", "Missiles check. Status?"},
            {"PRIVATE", "Missiles primed and ready."},
            {"CONTROL", "This is not a drill. Repeat, not a drill."},
            {"PRIVATE", "Understood. Awaiting final orders."},
            {"CONTROL", "Engage when ready and come back home. CONTROL Over and out."},
            {"PRIVATE", "ROGER that. Over and out."}
        };

        System.out.println("\n📡 Initiating pre-battle communication...\n");
        Thread.sleep(1000);

        for (String[] line : dialogue) {
            System.out.println("[" + line[0] + "] " + line[1]);
            Thread.sleep(1900);
        }

        System.out.println("\n🔔 All systems green. Commence operation BATTLESHIP.\n");
        Thread.sleep(1000);
    }
    private static void showDramaticIntro() throws InterruptedException {
        String[] introLines = {
        "*************************************************************************************************",
        "*************************************************************************************************",
        "*****██████╗    █████╗  ████████╗ ████████╗ ██╗      ███████╗ ███████╗ ██╗  ██╗ ██╗ ██████╗ *****",
        "*****██╔══██╗  ██╔══██╗ ╚══██╔══╝ ╚══██╔══╝ ██║      ██╔════╝ ██╔════╝ ██║  ██║ ██║ ██╔══██╗*****",
        "*****██████╔╝  ███████║    ██║       ██║    ██║      █████╗   ███████╗ ███████║ ██║ ██████╔╝*****",
        "*****██╔═══╝   ██╔══██║    ██║       ██║    ██║      ██╔══╝   ╚════██║ ██╔══██║ ██║ ██╔═══╝ *****",
        "*****██║  ██╗  ██║  ██║    ██║       ██║    ██║      ██║           ██║ ██║  ██║ ██║ ██║     *****",
        "*****██║  ██║  ██║  ██║    ██║       ██║    ███████╗ ███████╗ ███████║ ██║  ██║ ██║ ██║     *****",
        "*****██████╔╝  ╚═╝  ╚═╝    ╚═╝       ╚═╝    ╚══════╝ ╚══════╝ ╚══════╝ ╚═╝  ╚═╝ ╚═╝ ╚═╝     *****",
        "*************************************************************************************************",
        "****************************   GAME CREATED BY THALU & MAKOMA    ********************************",
        "*************************************************************************************************",
        "",
        "               ⚓ Prepare for Naval Warfare ⚓",
        "               🛥️  The fleet is waiting, Private...",
        "               💣  Coordinates locked in...",
        "               🎯  Missiles armed...",
        "               🔥  Targets are unknown...",
        "",
        "                   It's time to fight..."
        };

        for (String line : introLines) {
            System.out.println(line);
            Thread.sleep(300);
        }

        String battleCry = "************* WELCOME TO BATTLESHIP, GOOD LUCK PRIVATE! YOUR SACRIFICES WILL BE REMEMBERED, GIVE THEM HELL SON!!!!!!! **********************";
        for (char c : battleCry.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100);
        }

        System.out.println("\n\n🛥️  Deploying fleet...\n");
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        showDramaticIntro();
        simulateRadioCheck();
        initializeGrids();
        placeAllShips();

        Scanner scanner = new Scanner(System.in);
        System.out.println("************************* Battle Started ***********************");

        while (!allShipsSunk()) {
            printPlayerView();
            System.out.print("Enter coordinates (e.g., A5): ");
            String input = scanner.nextLine().toUpperCase().trim();

            if (!isValidInput(input)) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;

            if (playerView[row][col] == HIT || playerView[row][col] == MISS) {
                System.out.println("You already tried that spot.");
                continue;
            }

            if (shipGrid[row][col] == 'S') {
                playerView[row][col] = HIT;
                System.out.println("Hit!");
                updateShipStatus(row, col);
            } else {
                playerView[row][col] = MISS;
                System.out.println("Miss!");
            }
        }

        System.out.println("\n🎉 You sank all the battleships! Game Over.");
        scanner.close();
    }

    private static void initializeGrids() {
        for (int i = 0; i < GRID_SIZE; i++) {
            Arrays.fill(playerView[i], WATER);
            Arrays.fill(shipGrid[i], WATER);
        }
    }

    private static void printPlayerView() {
        System.out.print("  ");
        for (int i = 1; i <= GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int row = 0; row < GRID_SIZE; row++) {
            System.out.print((char) ('A' + row) + " ");
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(playerView[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValidInput(String input) {
        if (input.length() < 2 || input.length() > 3) return false;
        char rowChar = input.charAt(0);
        if (rowChar < 'A' || rowChar > 'J') return false;
        try {
            int col = Integer.parseInt(input.substring(1));
            return col >= 1 && col <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void placeAllShips() {
        placeShip(4);
        placeShip(3); 
        placeShip(2); 
    }

    private static void placeShip(int size) {
        Random rand = new Random();
        boolean placed = false;

        while (!placed) {
            int row = rand.nextInt(GRID_SIZE);
            int col = rand.nextInt(GRID_SIZE);
            boolean horizontal = rand.nextBoolean();

            if (canPlaceShip(row, col, size, horizontal)) {
                Ship ship = new Ship(size);
                for (int i = 0; i < size; i++) {
                    int r = row + (horizontal ? 0 : i);
                    int c = col + (horizontal ? i : 0);
                    shipGrid[r][c] = 'S';
                    ship.coordinates.add(new int[]{r, c});
                }
                ships.add(ship);
                placed = true;
            }
        }
    }

    private static boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && col + size > GRID_SIZE) return false;
        if (!horizontal && row + size > GRID_SIZE) return false;

        for (int i = 0; i < size; i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (shipGrid[r][c] == 'S') return false;
        }
        return true;
    }

    private static void updateShipStatus(int hitRow, int hitCol) {
        for (Ship ship : ships) {
            for (int[] coord : ship.coordinates) {
                if (coord[0] == hitRow && coord[1] == hitCol) {
                    ship.hits++;
                    if (ship.isSunk()) {
                        System.out.println("You sunk my battleship!");
                    }
                    return;
                }
            }
        }
    }

    private static boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }
}
