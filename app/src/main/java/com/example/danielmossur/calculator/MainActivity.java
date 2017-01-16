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
        numbers.set_context(getApplicationContext());

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
                        result_line.setText("");
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