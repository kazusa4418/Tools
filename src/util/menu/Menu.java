package util.menu;

import util.checker.Checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> items = new ArrayList<>();

    public void addMenu(int id, String title, Callback cb) {
        MenuItem item = new MenuItem(id, title, cb);
        items.add(item);
    }

    void resetMenu() {
        items.clear();
    }

    public void select() {
        try {
            int no = input();
            int id = items.get(no - 1).getID();
            items.get(no - 1).callMethod(id);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.print("> ");
            String input = br.readLine();
            if (Checker.numberCheck(input, items.size() + 1))
                return Integer.parseInt(input);
            System.out.println("入力が間違っています。");
        }
    }

    public void show() {
        for (int i = 0; i < items.size(); i++ ) {
            System.out.println((i + 1) + ": " + items.get(i));
        }
    }
}
