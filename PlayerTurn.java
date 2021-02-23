// package yugioh;

// import yugioh.Player;
// import yugioh.Card.*;
// import yugioh.CardType;

// import java.io.IOException;
// import java.util.concurrent.TimeUnit;

// public class PlayerTurn {
    
//     public static void start(Player p, boolean first) throws IOException {

//         System.out.println("\nShuffling deck.");
//         p.shuffle();
//         // TimeUnit.MILLISECONDS.wait(500);
//         System.out.println("Deck shuffled.\n");
//         // TimeUnit.MILLISECONDS.wait(500);
//         System.out.println("Hand:");
//         for (int i = 0; i < 5; i++) {
//             // TimeUnit.MILLISECONDS.wait(500);
//             p.draw();
//             System.out.println(p.hand.get(p.hand.size() - 1).getName());
//         }

//         if (first) {
//             drawPhase(p);
//         } else {
//             opponentsTurn(p);
//         }
//     }

//     public static void opponentsTurn(Player p) throws IOException {
//         String action = p.in.readLine();
//         String action2 = "";

//         while (!action.equalsIgnoreCase("end")) {
//             if (!action.equals(action2)) {
//                 System.out.println("\n" + action);
//                 action2 = p.in.readLine();
//             } else {
//                 action = p.in.readLine();
//             }
//         }

//         drawPhase(p);
//     }

//     public static void endPhase(Player p) throws IOException {
//         System.out.println("End Phase:\n");
//         p.out.println("Opponent's End Phase");
//         p.out.println("end");
//         opponentsTurn(p);
//     }

//     public static void mainPhase(Player p) throws IOException {
//         System.out.println("Main Phase 1:\n");
//         p.out.println("Opponent's Main Phase 1");
//         p.displayHand();

//         System.out.print("\nAction?\n>");

//         String action = p.keyboard.readLine();

//         while (!action.equalsIgnoreCase("end turn")) {
//             System.out.println("Invalid input.");
//             System.out.print("\nAction?\n>");
//             action = p.keyboard.readLine();
//         }

//         endPhase(p);
//     }

//     public static void standbyPhase(Player p) throws IOException {
//         System.out.println("Standby Phase:\n");
//         p.out.println("Opponent's Standby Phase");
//         mainPhase(p);
//     }

//     public static void drawPhase(Player p) throws IOException {
//         System.out.println("Draw Phase:\n");
//         p.out.println("Opponent's Draw Phase");
//         p.draw();
//         p.out.println("Opponent drew a card.");
//         standbyPhase(p);
//     }
// }
