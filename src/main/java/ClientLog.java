import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String> clientLog = new ArrayList<>();
    private boolean firstSave = true;

    public void log(int productNum, int amount) {
        if (firstSave) {
            clientLog.add("productNum,amount\n");
            clientLog.add((productNum + 1) + "," + amount + "\n");
            firstSave = !firstSave;
        } else {
            clientLog.add((productNum + 1)+ "," + amount + "\n");
        }
    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(txtFile))) {
            for (String line : clientLog) {
                buff.write(line);
            }
        }
    }
}
