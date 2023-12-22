import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SchiffeVersenken {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";


	private static final int GROESSE = 5;
	private static final char LEER = '~';
	private static final char SCHIFF = 'S';
	private static final char TREFFER = 'X';
	private static final char VERFEHLT = 'O';

	private char[][] spielerFeld;
	private char[][] gegnerFeld;
	private int verbleibendeSpielerSchiffe;
	private int verbleibendeGegnerSchiffe;


	private static void zeigeStartbildschirm() {

		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~" + ANSI_RESET + " Ahoi Seemann! " + ANSI_CYAN + "~~~~~~~~~~~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~" + ANSI_RESET + " Willkommen bei Schiffe Versenken! " + ANSI_CYAN + "~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET + ANSI_YELLOW + "⚓︎" + ANSI_RESET + ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~~" + ANSI_RESET + "Versuche alle Schiffe zu treffen." + ANSI_CYAN + "~~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~" + ANSI_RESET + "Drücke Enter, um das Spiel zu starten." + ANSI_CYAN + "~~~~~" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
		new Scanner(System.in).nextLine(); 
		// Warte auf Eingabe
	}

	public SchiffeVersenken() {
		spielerFeld = new char[GROESSE][GROESSE];
		gegnerFeld = new char[GROESSE][GROESSE];

		for (char[] row : spielerFeld) {
			Arrays.fill(row, LEER);
		}
		for (char[] row : gegnerFeld) {
			Arrays.fill(row, LEER);
		}

		verbleibendeSpielerSchiffe = GROESSE;
		verbleibendeGegnerSchiffe = GROESSE;
	}

	public void platziereSpielerSchiffe() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(ANSI_YELLOW + "Platziere deine Schiffe." + ANSI_RESET);
		for (int i = 0; i < GROESSE; i++) {
			printSpielerFeld();
			System.out.print("Gib die x-Koordinate für Schiff " + (i + 1) + ": ");
			int x = scanner.nextInt();
			System.out.print("Gib die y-Koordinate für Schiff " + (i + 1) + ": ");
			int y = scanner.nextInt();

			if (x >= 0 && x < GROESSE && y >= 0 && y < GROESSE && spielerFeld[x][y] == LEER) {
				spielerFeld[x][y] = SCHIFF;
			} else {
				System.out.println(ANSI_RED + "Ungültige Koordinaten. Versuche es erneut." + ANSI_RESET);
				System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
				i--;
			}
		}
	}

	public void platziereGegnerSchiffe() {
		Random random = new Random();
		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "Der Gegner platziert seine Schiffe." + ANSI_RESET);
		System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);

		for (int i = 0; i < GROESSE; i++) {
			int x = random.nextInt(GROESSE);
			int y = random.nextInt(GROESSE);
			if (gegnerFeld[x][y] == LEER) {
				gegnerFeld[x][y] = SCHIFF;
			} else {
				i--; // Versuche erneut, wenn das Feld bereits belegt ist
			}
		}
	}

	public void printSpielerFeld() {
		System.out.println(ANSI_YELLOW + "Dein Spielfeld:" + ANSI_RESET);
		System.out.print("  ");
		for (int i = 0; i < GROESSE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < GROESSE; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < GROESSE; j++) {
				System.out.print(spielerFeld[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printGegnerFeld() {
		System.out.println(ANSI_YELLOW + "Gegnerisches Spielfeld:" + ANSI_RESET);
		System.out.print("  ");
		for (int i = 0; i < GROESSE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < GROESSE; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < GROESSE; j++) {
				char anzeigeZeichen = gegnerFeld[i][j] == SCHIFF ? LEER : gegnerFeld[i][j];
				System.out.print(anzeigeZeichen + " ");
			}
			System.out.println();
		}
	}

	public boolean spielerSchuss(int x, int y) {
		if (gegnerFeld[x][y] == SCHIFF) {
			System.out.println(ANSI_CYAN + " " + ANSI_RESET);
			System.out.println("Du hast " + ANSI_GREEN + "getroffen!" + ANSI_RESET);
			gegnerFeld[x][y] = TREFFER;
			verbleibendeGegnerSchiffe--;
			return true;
		} else {
			System.out.println(ANSI_CYAN + " " + ANSI_RESET);
			System.out.println("Du hast " + ANSI_RED + "verfehlt!" + ANSI_RESET);
			gegnerFeld[x][y] = VERFEHLT;
			return false;
		}
	}

	public void gegnerSchuss() {
		Random random = new Random();
		int x, y;
		do {
			x = random.nextInt(GROESSE);
			y = random.nextInt(GROESSE);
		} while (spielerFeld[x][y] == TREFFER || spielerFeld[x][y] == VERFEHLT);

		if (spielerFeld[x][y] == SCHIFF) {
	        System.out.println("Der Gegner hat" + ANSI_RED + " getroffen" + ANSI_RESET + "!");
	        System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
	        spielerFeld[x][y] = TREFFER;
	        verbleibendeSpielerSchiffe--;

	        // Der Gegner darf erneut schießen
	        gegnerSchuss();
	    } else {
	        System.out.println("Der Gegner hat" + ANSI_GREEN + " verfehlt" + ANSI_RESET + "!");
	        System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
	        spielerFeld[x][y] = VERFEHLT;
	    }
	}

	public boolean istSpielerSpielvorbei() {
		return verbleibendeSpielerSchiffe == 0;
	}

	public boolean istGegnerSpielvorbei() {
		return verbleibendeGegnerSchiffe == 0;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		SchiffeVersenken spiel = new SchiffeVersenken();
		zeigeStartbildschirm(); 
		spiel.platziereSpielerSchiffe();
		spiel.platziereGegnerSchiffe();

		while (!spiel.istSpielerSpielvorbei() && !spiel.istGegnerSpielvorbei()) {
			spiel.printSpielerFeld();
			System.out.println();
			spiel.printGegnerFeld();

			System.out.print("Gib die x-Koordinate für deinen Schuss ein: ");
			int x = scanner.nextInt();
			System.out.print("Gib die y-Koordinate für deinen Schuss ein: ");
			int y = scanner.nextInt();

			if (x >= 0 && x < GROESSE && y >= 0 && y < GROESSE) {
				if (spiel.spielerSchuss(x, y)) {
					// Der Spieler schießt erneut, wenn ein Schiff getroffen wurde
					continue;
				}
			} else {
				System.out.println(ANSI_RED + "Ungültige Koordinaten. Versuche es erneut." + ANSI_RESET);
				System.out.println(ANSI_CYAN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ANSI_RESET);
				continue;
			}

			spiel.gegnerSchuss();
		}

		if (spiel.istSpielerSpielvorbei()) {
			System.out.println(ANSI_RED + "Du hast verloren! Der Gegner hat alle deine Schiffe versenkt." + ANSI_RESET);
		} else {
			System.out.println(ANSI_GREEN + "Herzlichen Glückwunsch! Du hast alle gegnerischen Schiffe versenkt!" + ANSI_RESET);
		}

		scanner.close();
	}
}
