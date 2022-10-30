import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, Exception {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        Basket basket = new Basket(products, prices);
        File basketJson = new File("basket.json");
        File basketText = new File("basket.txt");
        File csvFile = new File("client.csv");
        ClientLog clientLog = new ClientLog();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        XPath xPath = XPathFactory.newInstance().newXPath();
        String loadFileName = xPath
                .compile("config/load/fileName")
                .evaluate(doc);
        boolean loadFileEnabled = Boolean.parseBoolean(xPath
                .compile("config/load/enabled")
                .evaluate(doc));
        String loadFileFormat = xPath
                .compile("config/load/format")
                .evaluate(doc);
        boolean saveFileEnabled = Boolean.parseBoolean(xPath
                .compile("config/save/enabled")
                .evaluate(doc));
        String saveFileName = xPath
                .compile("config/save/fileName")
                .evaluate(doc);
        String saveFileFormat = xPath
                .compile("config/load/format")
                .evaluate(doc);
        boolean logFileEnabled = Boolean.parseBoolean(xPath
                .compile("config/log/enabled")
                .evaluate(doc));
        String logFileName = xPath
                .compile("config/log/fileName")
                .evaluate(doc);

        if (loadFileEnabled && loadFileFormat.equals("json")) {
            if (!basketJson.createNewFile()) {
                basket = Basket.loadJson(new File(loadFileName));
                System.out.println("Список загружен.");
            } else if (loadFileEnabled && loadFileFormat.equals("txt")) {
                if (!basketText.createNewFile()) {
                    basket = Basket.loadFromTxtFile(new File(loadFileName));
                    System.out.println("Список загружен.");
                }
            }
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
            clientLog.log(productNumber, productCount);
        }
        if (saveFileEnabled && saveFileFormat.equals("json")) {
            basket.saveJson(new File(saveFileName));
        } else if (saveFileEnabled && saveFileFormat.equals("txt")) {
            basket.saveTxt(new File(saveFileName));
        }
        if (logFileEnabled) {
            clientLog.exportAsCSV(new File(logFileName));
        }
        basket.printCart();
    }
}

