import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws IOException {
        Converter converter = new Converter();
        String c = new String();
        char[] charInput = input.toCharArray();
        int a;
        int b;
        Character currentSign = ' ';
        int isCorrectSign = 0;
        String[] component = input.split(" ");

        if(component.length > 3 || component.length <= 1)
            throw new IOException("Выражение не является математической операцией");



        for(int i = 0; i < charInput.length; i++){
            if(charInput[i] == '+' || charInput[i] == '-' || charInput[i] == '*' || charInput[i] == '/'){
                currentSign = charInput[i];
                isCorrectSign++;
            }
        }

        if(isCorrectSign != 1){
            throw new IOException("Формат математической операции не удовлетворяет заданию - две цифры и один оператор (+, -, /, *)!");
        }

        if(currentSign == '+'){
            component = input.split(" \\+ ");}
        else if (currentSign == '-') {
            component = input.split(" - ");}
        else if (currentSign == '*') {
            component = input.split(" \\* ");}
        else if (currentSign == '/') {
            component = input.split(" / ");}

        if(!converter.isRoman(component[0]) && !converter.isRoman(component[1])){
            a = Integer.parseInt(component[0]);
            b = Integer.parseInt(component[1]);

            c = Integer.toString(converter.calcInArabic(a, b, currentSign));

        } else if (!converter.isRoman(component[0]) == !converter.isRoman(component[1])) {
            a = converter.romanToArabic(component[0]);
            b = converter.romanToArabic(component[1]);

            int result = converter.calcInArabic(a, b, currentSign);

            if(result > 0)
                return converter.arabicToRoman(result);
            else
                throw new ArithmeticException("В римской системе нет отрицательных чисел!");


        } else {
            throw new IOException("Используются одновременно разные системы счисления");
        }
        return c;
    }

}

class Converter{
    private HashMap<Character, Integer> Map = new HashMap<>();
    public Converter(){
        Map.put('I', 1);
        Map.put('V', 5);
        Map.put('X', 10);
        Map.put('L', 50);
        Map.put('C', 100);
        Map.put('D', 500);
        Map.put('M', 1000);
    }

    public boolean inArrange(int currentNumber){
        if(currentNumber >= 1 && currentNumber <= 10)
            return true;
        else
            return false;
    }
    public int calcInArabic(int a, int b, char currentSign) throws IOException {
        int c = -1;
        if(inArrange(a) && inArrange(b)){
            switch (currentSign) {
                case ('+') -> {
                    c = a + b;
                }
                case ('-') -> {
                    c = a - b;
                }
                case ('*') -> {
                    c = a * b;
                }
                case ('/') -> {
                    try {
                        c = a / b;
                    } catch (ArithmeticException | InputMismatchException e) {
                        System.err.println("Exception : " + e);
                        System.err.println("Результатом операции могут быть только целые числа!");
                    }
                }
                default -> throw new IllegalArgumentException("Неверный знак операции");
            }
        } else {
            throw new IOException("Числа не находятся в диапазоне от 1 до 10!");

        }
        return c;
    }

    public boolean isRoman(String number){
        if(Map.containsKey(number.charAt(0))){
            return true;
        } else {
            return false;
        }
    }

    public int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = Map.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    public String arabicToRoman(int arabic){
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (arabic >= arabicValues[i]) {
                roman.append(romanSymbols[i]);
                arabic -= arabicValues[i];
            }
        }

        return roman.toString();
    }
}