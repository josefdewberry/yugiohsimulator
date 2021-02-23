package yugioh;

import yugioh.Player;
import yugioh.Card.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Field {
    
    private ArrayList<Card> monsters;

    public Field() {
        
    }

    // public void display() {
    //     System.out.println("\nField:");
    //     for (int i = 0; i < monsters.size(); i++) {
    //         System.out.println(monsters.get(i).getName());
    //     }
    // }

    // public void summon(Card c) {
    //     monsters.add(c);
    // }

    // public boolean minorTributeSummon(Card c, Player p) {
    //     if (getNumberOfMonsters() >= 1) {

    //         System.out.print("Enter a monster to be tributed: ");
    //         Scanner scanner = new Scanner(System.in);
    //         String name = scanner.nextLine();

    //         if (destroyMonster(name)) {
    //             summon(c);
    //             return true;
    //         } 

    //         p.addCard(c);
    //         return false;

    //     } else {
    //         System.out.println("You don't have any monsters to tribute.");
    //         p.addCard(c);
    //         return false;
    //     }
    // }

    // public boolean majorTributeSummon(Card c, Player p) {
        
    //     if (getNumberOfMonsters() >= 2) {

    //         System.out.print("Enter the first monster to be tributed: ");
    //         Scanner scanner = new Scanner(System.in);
    //         String name = scanner.nextLine();

    //         if (findMonster(name)) {

    //             System.out.print("Enter the second monster to be tributed: ");
    //             String name2 = scanner.nextLine();

    //             if (findMonster(name2)) {
    //                 destroyMonster(name);
    //                 destroyMonster(name2);
    //                 summon(c);
    //                 return true;
    //             } else {
    //                 System.out.println("Card not found.");
    //                 p.addCard(c);
    //                 return false;
    //             }

    //         } else {
    //             System.out.println("Card not found.");
    //             p.addCard(c);
    //             return false;
    //         }

    //     } else {
    //         System.out.println("You don't have enough monsters to tribute.");
    //         p.addCard(c);
    //         return false;
    //     }

    // }

    // public int getNumberOfMonsters() {
    //     return monsters.size();
    // }

    // public boolean canNormalSummon() {
    //     return monsters.size() < 5;
    // }

    // public boolean findMonster(String name) {
    //     for (int i = 0; i < monsters.size(); i++) {
    //         if (name.toLowerCase().equals(monsters.get(i).getName().toLowerCase())) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public boolean destroyMonster(String name) {
    //     for (int i = 0; i < monsters.size(); i++) {
    //         if (name.toLowerCase().equals(monsters.get(i).getName().toLowerCase())) {
    //             monsters.remove(i);
    //             return true;
    //         }
    //     }
    //     System.out.println("Card not found.");
    //     return false;
    // }
}
