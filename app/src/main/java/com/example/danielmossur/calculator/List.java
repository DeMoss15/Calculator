package com.example.danielmossur.calculator;

import android.content.Context;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Daniel Mossur on 16.01.2017.
 */

public class List {
    private ListElement head;       // указатель на первый элемент
    private ListElement tail;       // указатель последний элемент
    private Context context;
    private CharSequence text;
    Toast toast;

    void set_context(Context a){
        context = a;
    }

    void factr()
    {
        if (tail.data == null || tail.data.equals(""))
            return;
        if(tail.data.contains("!"))
            tail.data = tail.data.substring(0, tail.data.length()-1);
        else
            tail.data += "!";
    }

    void reverse()
    {
        if(tail.data != null)
            if (tail.data.contains("-"))
                tail.data = tail.data.substring(1);
            else
                tail.data = "-" + tail.data;
    }

    void redo()
    {
        if (tail.fraction_range > 0)
            tail.fraction_range--;
        if (tail.fraction_range == 0 && tail.fraction_path){
            tail.fraction_path = false;
            tail.data = tail.data.substring(0,tail.data.length()-1);
        }
        if (tail.data != null)
            if (tail.data.length() < 2 || (tail.data.contains("-") && tail.data.length() == 2))
                tail.data = null;
            else
                tail.data = tail.data.substring(0,tail.data.length()-1);

        else
        if (tail.operator != ' ')
            delEl(tail);
    }

    String print()
    {
        ListElement t = head;
        String s = "";
        if (t.data == null && t.operator == ' ')
        {
            clean();
            return "Enter the number";
        }

        while (t.next != null){
            s += t.operator + String.format(Locale.getDefault(),"%,."+t.fraction_range+"f",Double.parseDouble(t.data));
            t = t.next;
        }

        if (t.data != null)
            s += t.operator + String.format(Locale.getDefault(),"%,."+t.fraction_range+"f",Double.parseDouble(t.data));
        else
            s += t.operator;

        if (t.fraction_path && t.fraction_range == 0){
            s += ",";
        }

        return s;
    }

    void clean()
    {
        while (head.next != null){
            delEl(head.next);
        }
        head.data=null;
        head.operator=' ';
        head.fraction_path=false;
        head.fraction_range=0;
    }

    String equals ()
    {
        if (head.data == null)
            return "Ошибка! Ничего не введено!";

        if (tail.data == null && tail.operator != ' ')
            redo();

        ListElement t = head;

        factr_math(t);

        while (t.next != null) {    //пока следующий элемент существует
            factr_math(t);
            if (t.next.operator == '^')
            {
                t.data = "" + Math.pow(Double.parseDouble(t.data), Double.parseDouble(t.next.data));
                delEl(t.next);
            } else
            if (t.next.operator == '*') {
                t.data = "" + (Double.parseDouble(t.data) * Double.parseDouble(t.next.data));
                delEl(t.next);
            } else
            if (t.next.operator == '/') {
                if (Double.parseDouble(t.next.data) != 0.0){
                    t.data = "" + (Double.parseDouble(t.data) / Double.parseDouble(t.next.data));
                    delEl(t.next);
                } else
                    return "Ошибка! Деление на ноль!";
            } else
                t = t.next;                //иначе ищем дальше
        }

        t = head;
        while (t.next != null) {
            if (t.next.operator == '+') {
                t.data = "" + (Double.parseDouble(t.data) + Double.parseDouble(t.next.data));
                delEl(t.next);
            } else
            if (t.next.operator == '-') {
                if (Double.parseDouble(t.next.data) != 0.0) {
                    t.data = "" + (Double.parseDouble(t.data) - Double.parseDouble(t.next.data));
                    delEl(t.next);
                }
            }
        }

        if (head.data.contains(".")) {
            head.fraction_path = true;
            head.fraction_range = 6;
            head.data = head.data.substring(0, head.data.indexOf('.')+7);
        }

        if (head.next == null){
            return head.data;
        } else
            return "Ошибка! Undefined Error";
    }

    void editTailData(String new_number)
    {
        if (tail.data == null)
            tail.data = "";

        if (new_number.equals(".")){
            if (!tail.fraction_path){
                tail.data += new_number;
                fraction();
            }
            return;
        }

        if(tail.fraction_path && tail.fraction_range<5)
            tail.fraction_range++;
        if ((!tail.fraction_path && tail.data.length()<9)
                || (tail.fraction_path && tail.data.substring(tail.data.indexOf('.')).length()<=5))
            tail.data += new_number;
        else{
            text = "Слишком много цифр!";
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void addBack(char operator)       //добавление в конец списка
    {
        ListElement a = new ListElement();  //создаём новый элемент
        a.data = null;
        a.operator = operator;
        a.fraction_path=false;
        a.fraction_range = 0;
        if (tail == null)           //если список пуст
        {                           //то указываем ссылки начала и конца на новый элемент
            head = a;               //т.е. список теперь состоит из одного элемента
            tail = a;
        } else {
            if (tail.data == null && tail.operator != ' ')
                tail.operator = a.operator;
            else {
                tail.next = a;          //иначе "старый" последний элемент теперь ссылается на новый
                tail = a;               //а в указатель на последний элемент записываем адрес нового элемента
            }
        }
    }

    private void factr_math(ListElement a)
    {
        if (a.data.contains("!")){ //факториал
            int tmp = 1, n = Integer.parseInt(a.data.substring(0, a.data.length()-1));
            if (n>12){
                a.data = a.data.substring(0, a.data.length()-1);
                text = "Факториал больше 12 взять тяжело! \nВот результат без факториала:";
                toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
                return; //здесь будет тост о точности вычислений
            }
            for (int i=1; i<=n; i++)
            {
                tmp *= i;
            }
            a.data = "" + tmp;
        }
    }

    private void fraction()              //добавление точки
    {
        tail.fraction_path = true;
    }

    private void delEl(ListElement el)          //удаление элемента
    {
        if(head == null)        //если список пуст -
            return;             //ничего не делаем

        if (head == tail) {     //если список состоит из одного элемента
            head = null;        //очищаем указатели начала и конца
            tail = null;
            return;             //и выходим
        }

        if (head == el) {    //если первый элемент - тот, что нам нужен
            head = head.next;       //переключаем указатель начала на второй элемент
            return;                 //и выходим
        }

        ListElement t = head;       //иначе начинаем искать
        while (t.next != null) {    //пока следующий элемент существует
            if (t.next == el) {  //проверяем следующий элемент
                if(tail == t.next)      //если он последний
                {
                    tail = t;           //то переключаем указатель на последний элемент на текущий
                }
                t.next = t.next.next;   //найденный элемент выкидываем
                return;                 //и выходим
            }
            t = t.next;                //иначе ищем дальше
        }
    }
}
