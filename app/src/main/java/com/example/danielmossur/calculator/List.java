package com.example.danielmossur.calculator;

import android.content.Context;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Daniel Mossur on 16.01.2017.
 */

class List {
    private ListElement head;       // указатель на первый элемент
    private ListElement tail;       // указатель последний элемент
    private Context context;
    private CharSequence text;
    private Toast toast;

    void set_context(Context a){
        context = a;
    }

    void factorial()
    {
        if (tail.data == null || tail.data.equals(""))
            return;
        tail.factorial = !tail.factorial;
    }

    void reverse()
    {
        if(tail.data != null)
            if (tail.data.contains("-"))
                tail.data = tail.data.substring(1);
            else
                tail.data = "-" + tail.data;
    }

    void redo() {
        if (tail.factorial){
            factorial();
            return;
        }
        if (tail.fraction_range >= 0)
            tail.fraction_range--;
        if (tail.data != null){
            if (tail.data.length() < 2 || (tail.data.contains("-") && tail.data.length() == 2))
                tail.data = null;
            else
                tail.data = tail.data.substring(0, tail.data.length() - 1);

            if (tail.fraction_range < 0 && tail.data != null && tail.data.endsWith("."))
                tail.data = tail.data.substring(0, tail.data.length() - 1);
        } else
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

        while (t != null){
            if (t.data != null) {
                if (t.fraction_range >= 0)
                    s += t.operator + String.format(Locale.getDefault(), "%,." + t.fraction_range + "f", Double.parseDouble(t.data));
                else
                    s += t.operator + String.format(Locale.getDefault(), "%,d", Integer.parseInt(t.data));
                if (t.factorial)
                    s += "!";
            } else
                s += t.operator;
            if (t.fraction_range == 0){
                s += ",";
            }
            t = t.next;
        }

        return s;
    }

    void clean()
    {
        while (head.next != null){
            delEl(head.next);
        }
        head.data = null;
        head.operator = ' ';
        head.fraction_range = -1;
        head.factorial = false;
    }

    String equals ()
    {
        if (head.data == null)
            return "Ошибка! Ничего не введено!";

        if (tail.data == null && tail.operator != ' ')
            redo();

        ListElement t = head;

        while (t != null) {
            factr_math(t);
            t = t.next;
        }

        t = head;
        while (t.next != null) {
            if (t.next.operator == '^')
            {
                t.data = "" + Math.pow(Double.parseDouble(t.data), Double.parseDouble(t.next.data));
                delEl(t.next);
            } else
                t = t.next;
        }

        t = head;
        while (t.next != null) {    //пока следующий элемент существует
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

        if (head.data.contains(".")) { // проблема вывода данных в большей своей части зависит от точности вычислений
            if ( head.data.endsWith(".0"))
            {
                head.data = head.data.substring(0, head.data.indexOf('.'));
                head.fraction_range = -1;
            } else head.fraction_range = head.data.substring(head.data.indexOf('.')).length();
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
            if (tail.fraction_range < 0){
                tail.data += new_number;
                tail.fraction_range++;
            }
            return;
        }

        if(tail.fraction_range >= 0 && tail.fraction_range<5)
            tail.fraction_range++;
        if ((tail.fraction_range < 0 && tail.data.length()<9)
                || (tail.fraction_range >= 0 && tail.data.substring(tail.data.indexOf('.')).length()<=5))
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
        a.fraction_range = -1;
        a.factorial = false;
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
        if (a.factorial){ //факториал
            int tmp = 1, n = Integer.parseInt(a.data);
            if (n>12){
                a.factorial = !a.factorial;
                text = "Факториал больше 12 взять тяжело! \nВот результат без факториала:";
                toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            for (int i=1; i<=n; i++)
            {
                tmp *= i;
            }
            a.data = "" + tmp;
            a.factorial = !a.factorial;
        }
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
