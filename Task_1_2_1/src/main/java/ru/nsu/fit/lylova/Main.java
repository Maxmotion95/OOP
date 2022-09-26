package ru.nsu.fit.lylova;

import java.util.Scanner;

/**
 * Class with function main
 */
public class Main {

    /**
     * A function that communicates with the user and simulates stack actions
     *
     * @param args arguments of command line (don't used)
     * @throws Exception when stack cannot do that operation
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Stack<Integer> st = new Stack<>();
        while (true){
            System.out.println("Введите команду и её параметры, или введите 'q' чтобы закончить.");
            String line  = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            String type = sc2.next();
            if (type.equals("q")){
                break;
            }
            if (type.equals("pop")){
                System.out.println("Элемент: " + st.pop());
                continue;
            }
            if (type.equals("push")){
                if (!sc2.hasNextInt()){
                    System.out.println("Числа не найдено.");
                    continue;
                }
                Integer next = sc2.nextInt();
                st.push(next);
                continue;
            }
            if (type.equals("popStack")){
                if (!sc2.hasNextInt()){
                    System.out.println("Числа не найдено.");
                    continue;
                }
                int cnt = sc2.nextInt();
                Stack<Integer> res = st.popStack(cnt);
                System.out.println("Результат: " + res);
                continue;
            }
            if (type.equals("count")){
                System.out.println("Кол-во элементов в стеке: " + st.count());
                continue;
            }
            if (type.equals("pushStack")){
                Stack<Integer> tmp = new Stack<>();
                while (sc2.hasNextInt()){
                    tmp.push(sc2.nextInt());
                }
                System.out.println("В стек добавлен стек: " + tmp);
                st.pushStack(tmp);
                continue;
            }
            System.out.println("Не знаю такой команды :(");
        }
        sc.close();
    }
}