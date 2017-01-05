package com.example.danielmossur.calculator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/*Вся программа стоит на ведении данных через кнопки и выведение через TextView
 * ограничение ввода в onClickListener'e
 * математика же прописана через односвязный список */

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button plus, minus, multiple, division, one, two, three, four, five, six, seven, eight, nine, zero, equal, dot, clean;
    List numbers = new List();
    /*проконтролировать кнопку добавления точки!!!!
    *
    * продумать ограничение ввода соответственно типу данных*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
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
        numbers.addBack('+');

        /*убрать все textView.setText из литснера, остапить только один, после свича
        * текст реализовать через стринг!!!!*
        *
        * придумать одноразовую запись числа*/
        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == null || !(v instanceof TextView)) {
                    return; //нет свойства text
                }
                switch (v.getId()) {
                    case R.id.plus:
                        addSymb('+');
                        break;
                    case R.id.minus:
                        addSymb('-');
                        break;
                    case R.id.multiple:
                        addSymb('*');
                        break;
                    case R.id.division:
                        addSymb('/');
                        break;
                    case R.id.dot:
                        if (textView.getText().length() != 0 &&
                                Character.isDigit(textView.getText().charAt(textView.getText().length() - 1)) &&
                                !numbers.fraction(true)) {
                            textView.setText(textView.getText() + ".");
                        }
                        break;
                    case R.id.equal:
                        textView.setText(numbers.equals());
                        break;
                    case R.id.clean:
                        textView.setText("");
                        numbers.clean();
                        break;
                    default:
                        if (numbers.tailZero() && !numbers.fraction(false) &&
                                (textView.getText().length() != 0) &&
                                ((textView.getText().charAt(textView.getText().length() - 1)) == '0'))
                            textView.setText(textView.getText().toString().substring(0, textView.getText().length() - 1) + ((TextView) v).getText().toString());
                        else
                            textView.setText(textView.getText() + ((TextView) v).getText().toString());
                        numbers.editTailData(Double.parseDouble(((TextView) v).getText().toString()));
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
    }

    public void addSymb(char ch) {
        /*при вооде нового знака создавать новый элеметн списка и вносить вводимый знак в этот элемент*/
        if (textView.getText().length() == 0) return;
        CharSequence s = textView.getText();
        if (Character.isDigit(s.charAt(s.length()-1))) {
            textView.setText(s + "" + ch);
            numbers.addBack(ch);
        } else { //иначе, если требуется замена оператора, его замена в строке и в последнем элементе
            textView.setText(s.toString().substring(0, s.length()-1) + ch);
            numbers.editTailOperator(ch);
        }
    };
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

    boolean fraction(boolean i)              //проверка дробной части
    {
        boolean x = tail.fraction_path;
        if (i)
        {
            tail.fraction_path = true;
        }
        return x;
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
        head.operator='+';
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

        if (head.next == null){
            return "" + head.data;
        } else
            return "Undefined Error";
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
