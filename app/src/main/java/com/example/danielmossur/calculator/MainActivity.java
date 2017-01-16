package com.example.danielmossur.calculator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView, result_line;
    Button plus, minus, multiple, division, one, two, three, four, five, six, seven, eight, nine,
            zero, equal, dot, clean, redo, reverse, pow, factr;
    String output_text;
    List numbers = new List();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        result_line = (TextView) findViewById(R.id.result_line);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiple = (Button) findViewById(R.id.multiple);
        division = (Button) findViewById(R.id.division);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        equal = (Button) findViewById(R.id.equal);
        dot = (Button) findViewById(R.id.dot);
        clean = (Button) findViewById(R.id.clean);
        redo = (Button) findViewById(R.id.redo);
        factr = (Button) findViewById(R.id.factr);
        pow = (Button) findViewById(R.id.pow);
        reverse = (Button) findViewById(R.id.reverse);
        output_text = "Enter the number";
        numbers.addBack(' ');

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == null || !(v instanceof TextView)) {
                    return; //нет свойства text
                }
                switch (v.getId()) {
                    case R.id.plus:
                        numbers.addBack('+');
                        break;
                    case R.id.minus:
                        numbers.addBack('-');
                        break;
                    case R.id.multiple:
                        numbers.addBack('*');
                        break;
                    case R.id.division:
                        numbers.addBack('/');
                        break;
                    case R.id.equal:
                        result_line.setText(numbers.equals());
                        break;
                    case R.id.clean:
                        numbers.clean();
                        result_line.setText("result");
                        break;
                    case R.id.reverse:
                        numbers.reverse();
                        break;
                    case R.id.pow:
                        numbers.addBack('^');
                        break;
                    case R.id.factr:
                        numbers.factr();
                        break;
                    case R.id.redo:
                        numbers.redo();
                        break;
                    default:
                        numbers.editTailData(((TextView) v).getText().toString());
                }
                if (output_text.length() >= 6 && output_text.substring(0, 5).equals("Ошибка"))
                    textView.setText(output_text);
                else {
                    output_text = numbers.print();
                    textView.setText(output_text);
                }
            }
        };


        plus.setOnClickListener(OnClickListener);
        minus.setOnClickListener(OnClickListener);
        multiple.setOnClickListener(OnClickListener);
        division.setOnClickListener(OnClickListener);
        one.setOnClickListener(OnClickListener);
        two.setOnClickListener(OnClickListener);
        three.setOnClickListener(OnClickListener);
        four.setOnClickListener(OnClickListener);
        five.setOnClickListener(OnClickListener);
        six.setOnClickListener(OnClickListener);
        seven.setOnClickListener(OnClickListener);
        eight.setOnClickListener(OnClickListener);
        nine.setOnClickListener(OnClickListener);
        zero.setOnClickListener(OnClickListener);
        equal.setOnClickListener(OnClickListener);
        dot.setOnClickListener(OnClickListener);
        clean.setOnClickListener(OnClickListener);
        redo.setOnClickListener(OnClickListener);
        factr.setOnClickListener(OnClickListener);
        pow.setOnClickListener(OnClickListener);
        reverse.setOnClickListener(OnClickListener);
    }
}

class ListElement{
    ListElement next;
    String data;
    char operator;
    int fraction_range;
    boolean fraction_path;
}

class List {
    private ListElement head;       // указатель на первый элемент
    private ListElement tail;       // указатель последний элемент

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
        if (tail.data != null)
            if (tail.data.equals("") || (tail.data.contains("-") && tail.data.length() == 2))
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
            s += t.operator + t.data;
            t = t.next;
        }

        if (t.data != null)
            s += t.operator + t.data;
        else
            s += t.operator;

        return s;
    }

    private void fraction()              //добавление точки
    {
        tail.fraction_path = true;
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
        while (t.next != null) {    //пока следующий элемент существует
            if (t.data.contains("!")){ //факториал
                int tmp = 1;
                for (int i=1; i<=(int)(Double.parseDouble(t.data.substring(0, t.data.length()-1))); i++)
                {
                    tmp *= i;
                }
                t.data = "" + tmp;
            }
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

        if ((!tail.fraction_path && tail.data.length()<12)
                || (tail.fraction_path && tail.data.substring(tail.data.indexOf('.')).length()<=3))
            tail.data += new_number;
        else
            return;/*тут должен быть тост, предупреждающий об ограниченоом вводе в одно слагаемое*/
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
