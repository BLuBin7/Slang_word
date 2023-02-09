package Model;


import java.util.Scanner;

// Option of client
public class Validation {
    private static Scanner sc = new Scanner(System.in);
    public  static int getInt(String msg , int min , int max)
    {
        if(min >max)
        {
            int temp = min;
            min = max;
            max = temp;
        }
        while (true)
        {
            try {
                System.out.print(msg);
                int n = Integer.parseInt(sc.nextLine());
                if(min <= n && n <= max) return n;
                System.err.println("PLEASE INPUT A NUMBER IN RANGE" + min +"->"+max);
            }catch (NumberFormatException ex) {
                System.err.println("WRONG FORMAT!! ");
            }
        }
    }

    public static String getString(String msg)
    {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if(!s.isEmpty()) return s;
            System.err.println("EMPTY STRING IS NOT ALLOWED!!");
        }
    }

    public static boolean getYN(String msg)
    {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("y")) return true;
            else if (s.equalsIgnoreCase("n")) return false;
            System.err.println("PLEASE INPUT ONLY Y/N");
        }
    }
}
