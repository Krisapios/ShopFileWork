import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        Basket basket = new Basket(products, prices);
        File basketJson = new File("basket.json");
        ClientLog log = new ClientLog();

        if (!basketJson.createNewFile()) {
            basket = Basket.loadJson(basketJson);
            System.out.println("Список загружен.");
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i]);
        }
        while (true) {
            System.out.println("Выберите товар и количество ИЛИ введите 'end' ");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
            log.log(productNumber, productCount); //записываем выбор в журнал
            log.exportAsCSV(new File("log.csv")); //выгружаем журнал в csv
        }
        basket.saveJson(basketJson);
        basket.printCart();
    }
}

