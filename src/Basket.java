import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket implements Serializable{
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

    public static Basket loadBin(File binFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream loadBin = new ObjectInputStream(new FileInputStream(binFile))) {
            return (Basket) loadBin.readObject();
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

    public void saveBin(File binFile) throws IOException {
        try (ObjectOutputStream saveBin = new ObjectOutputStream(new FileOutputStream(binFile))) {
            saveBin.writeObject(this);
        }
    }
}
