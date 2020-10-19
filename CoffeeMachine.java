package machine;
import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        
        machine coffee_machine = new machine();
        coffee_machine.machineMenu();
        
    }
    
}

class machine {

    private static String readText() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public void machineMenu () {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action = readText();
        if (!action.equals("exit")) {
            operateMachine(action);
        }
    }

    private void operateMachine (String action) {
        switch (action) {
            case "buy":
                buy();
                machineMenu();
                break;
            case "fill":
                fill();
                machineMenu();
                break;
            case "remaining":
                printState();
                machineMenu();
                break;
            case "take":
                take();
                machineMenu();
                break;
            default:
                System.out.println("Invalid choice.... Try again");
                machineMenu();
                break;
        }
    }

    private static void buy() {
        switch (buyMenu()) {
            case "1":
                makeCoffee(CoffeeType.ESPRESSO);
                break;
            case "2":
                makeCoffee(CoffeeType.LATTE);
                break;
            case "3":
                makeCoffee(CoffeeType.CAPPUCCINO);
                break;
            case "back":
                break;
            default:
                System.out.println("Invalid Entry");
                break;
        }
    }

    private static String buyMenu() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        return readText();
    }

    private static void makeCoffee(CoffeeType coffeeType) {

        if (checkStock(coffeeType.name())) {
            CurrentStock.CURRENT_STOCK.water -= coffeeType.water;
            CurrentStock.CURRENT_STOCK.milk -= coffeeType.milk;
            CurrentStock.CURRENT_STOCK.coffeeBeans -= coffeeType.coffeeBeans;
            CurrentStock.CURRENT_STOCK.cups -= coffeeType.cups;
            CurrentStock.CURRENT_STOCK.money += coffeeType.cost;
        }
    }

    private static boolean checkStock(String s) {
        int water = CurrentStock.CURRENT_STOCK.water;
        int milk = CurrentStock.CURRENT_STOCK.milk;
        int beans = CurrentStock.CURRENT_STOCK.coffeeBeans;
        int cups = CurrentStock.CURRENT_STOCK.cups;
        if (water >= CoffeeType.valueOf(s).water && milk >= CoffeeType.valueOf(s).milk && beans >= CoffeeType.valueOf(s).coffeeBeans && cups >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            return true;
        } else if (water < CoffeeType.valueOf(s).water && milk >= CoffeeType.valueOf(s).milk && beans >= CoffeeType.valueOf(s).coffeeBeans && cups >= 1) {
            System.out.println("Sorry, not enough water!");
            return false;
        } else if (water >= CoffeeType.valueOf(s).water && milk < CoffeeType.valueOf(s).milk && beans >= CoffeeType.valueOf(s).coffeeBeans && cups >= 1) {
            System.out.println("Sorry, not enough milk!");
            return false;
        } else if (water >= CoffeeType.valueOf(s).water && milk >= CoffeeType.valueOf(s).milk && beans < CoffeeType.valueOf(s).coffeeBeans && cups >= 1) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        } else if (water >= CoffeeType.valueOf(s).water && milk >= CoffeeType.valueOf(s).milk && beans >= CoffeeType.valueOf(s).coffeeBeans && cups < 1) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }

        return false;
    }

    private static void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(CurrentStock.CURRENT_STOCK.water + " of water");
        System.out.println(CurrentStock.CURRENT_STOCK.milk + " of milk");
        System.out.println(CurrentStock.CURRENT_STOCK.coffeeBeans + " of coffee beans");
        System.out.println(CurrentStock.CURRENT_STOCK.cups + " of disposable cups");
        System.out.println(CurrentStock.CURRENT_STOCK.money + " of money");
    }

    private static void fill() {
        System.out.println("Write how many ml of water do you want to add:");
        CurrentStock.CURRENT_STOCK.water += Integer.parseInt(readText());
        System.out.println("Write how many ml of milk do you want to add:");
        CurrentStock.CURRENT_STOCK.milk += Integer.parseInt(readText());;
        System.out.println("Write how many grams of coffee beans do you want to add:");
        CurrentStock.CURRENT_STOCK.coffeeBeans += Integer.parseInt(readText());;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        CurrentStock.CURRENT_STOCK.cups += Integer.parseInt(readText());;
    }

    private static void take() {
        System.out.println("I gave you " + CurrentStock.CURRENT_STOCK.money);
        CurrentStock.CURRENT_STOCK.money = 0;
    }
}

enum CoffeeType {
    ESPRESSO(1,250, 0, 16, 4, 1),
    LATTE(2,350, 75, 20, 7, 1),
    CAPPUCCINO(3,200, 100, 12, 6, 1);

    int type, water, milk, coffeeBeans, cost, cups;

    CoffeeType(int type, int water, int milk, int coffeeBeans, int cost, int cups) {
        this.type = type;
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
        this.cups = cups;
    }
}

enum CurrentStock {
    CURRENT_STOCK(400,540,120, 550, 9);

    int water, milk, coffeeBeans, money, cups;

    CurrentStock(int water, int milk, int coffeeBeans, int money, int cups) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.money = money;
        this.cups = cups;
    }
}
