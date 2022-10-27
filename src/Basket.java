import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket {
    protected int price[];
    protected String products[];
    protected int counts[];
    protected int sum = 0;

    public Basket(String products[], int price[]) {
        this.price = price;
        this.products = products;
        counts = new int[products.length];
    }

    public Basket(String[] products, int[] prices, int[] counts) {
        this.price = prices;
        this.products = products;
        this.counts = counts;
    }

    public static Basket loadFromTxtFile(File textFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(textFile))) {
            String[] product = scanner.nextLine().split(" ");
            int[] price = Arrays.stream(scanner.nextLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();

            int[] totalProduct = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            return new Basket(product, price, totalProduct);
        }
    }
    public void addToCart(int productNum, int amount) {
        counts[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                System.out.println(products[i] + " " + counts[i] + " шт. " + price[i] + " руб/шт. " + (counts[i] * price[i]) + " рублей в сумме.");
                sum += counts[i] * price[i];
            }
        }
        System.out.println("Вы купили на сумму " + sum + " руб.");
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter printText = new PrintWriter(textFile)) {
            for (String product : products) {
                printText.print(product + " ");
            }
            printText.println();
            for (int priceProd : price) {
                printText.print(priceProd + " ");
            }
            printText.println();
            for (int countProd : counts) {
                printText.print(countProd + " ");
            }
        }
    }
}
