package Gui;

import Model.Dict.Dictionary;
import Model.Dict.Validation;

import java.io.IOException;

public class Menu {
    private static String[] ops = {
            "Add word",
            "Delete word",
            "Translate word",
            "Exit"
    };
    public static void display() throws IOException {
        Dictionary d = new Dictionary();
        int choice;

        do{
            System.out.println("=============Slang-Word============");
            for(int i =0;i<ops.length; i++)
            {
                System.out.println((i+1)+"."+ops[i]);
            }
            choice = Validation.getInt("Choose : " , 1, ops.length );
            switch (choice)
            {
                case 1 :
                    d.addWord();
                    break;
                case 2 :
                    d.RemoveWord();
                    break;
                case 3 :
                    d.Translate();
                    break;
                case 4 :
                    break;
            }
        }while (choice != ops.length);
    }
}
