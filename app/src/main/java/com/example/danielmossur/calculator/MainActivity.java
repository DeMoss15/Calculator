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
    /*Нужно добавить односвязный список, эллементы которого будут содержать число и знак, предествующий ему.
    * при подсчете сначала будут проверяться умножение и деление(текущий объект взаимодействует с пердыдущим,
    * после чего результат записывается в число предыдущего, а текущий удаляется из цепи !!! не потерять связь!!!)
    * потом проверяется действие на +/- аналогичным образом. и так пока не останентся один элемент, он и будет
    * результатом. !!!!! не потерять ссылку на первый элемент!!!!!
    *
    * также описать кнопку сброса (очистка текстового поля и списка)
    *
    * проконтролировать кнопку добавления точки!!!!
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
                        break;
                    case R.id.equal:
                        //equals();
                        break;
                    case R.id.clean:
                        textView.setText("");
                        break;
                    default:
                        if (textView.getText().length() == 0) textView.setText(textView.getText() + ((TextView) v).getText().toString());
                        if (((textView.getText().charAt(textView.getText().length()-1)) == '0')
                                && (!Character.isDigit(textView.getText().charAt(textView.getText().length()-2)))) {
                            textView.setText(textView.getText().toString().substring(0, textView.getText().length()-1) + ((TextView) v).getText().toString());
                        } else
                            textView.setText(textView.getText() + ((TextView) v).getText().toString());
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
        } else {
            textView.setText(s.toString().substring(0, s.length()-1) + ch);
        }
    };

    /*public void equals() {

    };*/
}
