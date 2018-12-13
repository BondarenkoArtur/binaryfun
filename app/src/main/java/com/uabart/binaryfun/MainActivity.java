package com.uabart.binaryfun;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText signTextView;
    EditText expTextView;
    EditText mantissaTextView;
    EditText floatTextView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signTextView = findViewById(R.id.sign);
        expTextView = findViewById(R.id.exp);
        mantissaTextView = findViewById(R.id.mantissa);
        floatTextView = findViewById(R.id.float_text);
        textView = findViewById(R.id.text);
    }

    public void toBinary(View view) {
        String floatText = floatTextView.getText().toString();

        if (!floatText.matches("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$")) {
            textView.setText("Float is wrong");
        } else {
            float f = Float.parseFloat(floatText);
            int intBits = Float.floatToIntBits(f);
            String intBitsStr = Integer.toBinaryString(intBits);

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 32 - intBitsStr.length(); i++) {
                stringBuilder.append("0");
            }

            stringBuilder.append(intBitsStr);
            String resultString = stringBuilder.toString();

            signTextView.setText(resultString.substring(0, 1));
            expTextView.setText(resultString.substring(1, 9));
            mantissaTextView.setText(resultString.substring(9, 32));
            textView.setText("");
        }
    }

    public void toFloat(View view) {
        String signText = signTextView.getText().toString();
        String expText = expTextView.getText().toString();
        String mantissaText = mantissaTextView.getText().toString();

        if (!signText.matches("[01]")) {
            textView.setText("Sign is wrong");
        } else if (!expText.matches("[01]{8}")) {
            textView.setText("Exponent is wrong");
        } else if (!mantissaText.matches("[01]{23}")) {
            textView.setText("Fraction bits is wrong");
        } else {
            String intBitsStr = signText + expText + mantissaText;

            int intBits = (int) Long.parseLong(intBitsStr, 2);
            float f = Float.intBitsToFloat(intBits);
            floatTextView.setText(String.valueOf(f));
            textView.setText("");
        }
    }
}
