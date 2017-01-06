package com.example.danielmossur.calculator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView, result_line;
    Button plus, minus, multiple, division, one, two, three, four, five, six, seven, eight, nine, zero, equal, dot, clean, redo;
    String output_text;
    List numbers = new List();
    /*продумать ограничение ввода соответственно типу данных*/

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
        numbers.addBack(' ');
        output_text = "";

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
                    case R.id.dot:
                        numbers.fraction();
                        break;
                    case R.id.equal:
                        result_line.setText(numbers.equals());
                        break;
                    case R.id.clean:
                        numbers.clean();
                        result_line.setText("result");
                        break;
                    case R.id.redo:
                        break;
                    default:
                        numbers.editTailData(Double.parseDouble(((TextView) v).getText().toString()));
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
    }
}

class ListElement{
    ListElement next;
    Double data;
    char operator;
    int fraction_range;
    boolean fraction_path;
}

class List {
    private ListElement head;       // указатель на первый элемент
    private ListElement tail;       // указатель последний элемент

    String print()
    {
        ListElement t = head;
        String s = "";
        while (t != null){
            if (t.fraction_path)
                s = s + t.operator + t.data;
            else
                s = s + t.operator + (int)((double)t.data);//костыль)
            t = t.next;
        }
        return s;
    }

    void fraction()              //проверка дробной части
    {
        tail.fraction_path = true;
    }

    boolean tailZero()
    {
        if (tail.data == 0.0)
            return true;
        else
            return false;
    }

    void clean()
    {
        while (head.next != null){
            delEl(head.next);
        }
        head.data=0.0;
        head.operator=' ';
        head.fraction_path=false;
        head.fraction_range=1;
    }

    String equals ()
    {
        ListElement t = head;
        while (t.next != null) {    //пока следующий элемент существует
            if (t.next.operator == '*') {
                t.data = t.data * t.next.data;
                delEl(t.next);
            } else
            if (t.next.operator == '/') {
                if (t.next.data != 0.0){
                    t.data = t.data / t.next.data;
                    delEl(t.next);
                } else
                    return "Ошибка! Деление на ноль!";
            } else
                t = t.next;                //иначе ищем дальше
        }

        t = head;
        while (t.next != null) {
            if (t.next.operator == '+') {
                t.data = t.data + t.next.data;
                delEl(t.next);
            } else
            if (t.next.operator == '-') {
                if (t.next.data != 0.0) {
                    t.data = t.data - t.next.data;
                    delEl(t.next);
                }
            }
        }

        if (head.data%1 != 0.0)
            head.fraction_path=true;
        if (head.next == null){
            return "" + head.data;
        } else
            return "Ошибка! Undefined Error";
    }

    void editTailOperator(char operator)
    {
        tail.operator = operator;
    }

    void editTailData(Double new_number)
    {
        if (!tail.fraction_path)
            tail.data = tail.data*10 + new_number;
        else
        {
            tail.data = tail.data + new_number/Math.pow(10,tail.fraction_range);
            tail.fraction_range++;
        }
    }

    void addBack(char operator)       //добавление в конец списка
    {
        ListElement a = new ListElement();  //создаём новый элемент
        a.data = 0.0;
        a.operator = operator;
        a.fraction_path=false;
        a.fraction_range = 1;
        if (tail == null)           //если список пуст
        {                           //то указываем ссылки начала и конца на новый элемент
            head = a;               //т.е. список теперь состоит из одного элемента
            tail = a;
        } else {
            tail.next = a;          //иначе "старый" последний элемент теперь ссылается на новый
            tail = a;               //а в указатель на последний элемент записываем адрес нового элемента
        }
    }

    void printList()                //печать списка
    {
        ListElement t = head;       //получаем ссылку на первый элемент
        while (t != null)           //пока элемент существуе
        {
            System.out.print(t.data + " "); //печатаем его данные
            t = t.next;                     //и переключаемся на следующий
        }
    }

    void delEl(ListElement el)          //удаление элемента
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
