package com.example.calculator.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.R;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.zip.Inflater;


public class CalculatorFragment extends Fragment {

    public static final String BUNDLE_KEY_LAST_DOT = "mLastDot";
    public static final String BUNDLE_KEY_STATE_ERROR = "mStateError";
    public static final String BUNDLE_KEY_LAST_NUMERIC = "mLastNumeric";
    public static final String BUNDLE_KEY_STRING_OF_TEXT_VIEW = "String of TextView";
    private TextView mTextViewString;
    private boolean mLastNumeric;
    private boolean mStateError;
    private boolean mLastDot;

    private Button mButtonDelete;
    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;
    private Button mButtonFour;
    private Button mButtonFive;
    private Button mButtonSix;
    private Button mButtonSeven;
    private Button mButtonEight;
    private Button mButtonNine;
    private Button mButtonZero;
    private Button mButtonSum;
    private Button mButtonDivide;
    private Button mButtonNegative;
    private Button mButtonPoint;
    private Button mButtonEqual;
    private Button mButtonMultiply;


    public CalculatorFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_KEY_LAST_DOT, mLastDot);
        outState.putBoolean(BUNDLE_KEY_STATE_ERROR, mStateError);
        outState.putBoolean(BUNDLE_KEY_LAST_NUMERIC, mLastNumeric);
        outState.putString(BUNDLE_KEY_STRING_OF_TEXT_VIEW, mTextViewString.getText().toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        findViews(view);
        if (savedInstanceState != null) {
            mLastDot = savedInstanceState.getBoolean(BUNDLE_KEY_LAST_DOT);
            mLastNumeric = savedInstanceState.getBoolean(BUNDLE_KEY_LAST_NUMERIC);
            mStateError = savedInstanceState.getBoolean(BUNDLE_KEY_STATE_ERROR);
            mTextViewString.setText(savedInstanceState.getString(BUNDLE_KEY_STRING_OF_TEXT_VIEW));
        }
        setNumericOnClickListener();
        setOperatorOnClickListener();
        return view;
    }

    private void findViews(View view) {
        mTextViewString = view.findViewById(R.id.textView_show_number);
        mButtonDelete = view.findViewById(R.id.button_delete);
        mButtonOne = view.findViewById(R.id.button_one);
        mButtonTwo = view.findViewById(R.id.button_two);
        mButtonThree = view.findViewById(R.id.button_three);
        mButtonFour = view.findViewById(R.id.button_four);
        mButtonFive = view.findViewById(R.id.button_five);
        mButtonSix = view.findViewById(R.id.button_six);
        mButtonSeven = view.findViewById(R.id.button_seven);
        mButtonEight = view.findViewById(R.id.button_eight);
        mButtonNine = view.findViewById(R.id.button_nine);
        mButtonNegative = view.findViewById(R.id.button_negative);
        mButtonSum = view.findViewById(R.id.button_sum);
        mButtonDivide = view.findViewById(R.id.button_divide);
        mButtonMultiply = view.findViewById(R.id.button_multiply);
        mButtonZero = view.findViewById(R.id.button_zero);
        mButtonPoint = view.findViewById(R.id.button_point);
        mButtonEqual = view.findViewById(R.id.button_equal);
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (mStateError) {
                    mTextViewString.setText(button.getText());
                    mStateError = false;
                } else {
                    mTextViewString.append(button.getText());
                }
                mLastNumeric = true;

            }
        };

        mButtonZero.setOnClickListener(listener);
        mButtonOne.setOnClickListener(listener);
        mButtonTwo.setOnClickListener(listener);
        mButtonThree.setOnClickListener(listener);
        mButtonFour.setOnClickListener(listener);
        mButtonFive.setOnClickListener(listener);
        mButtonSix.setOnClickListener(listener);
        mButtonSeven.setOnClickListener(listener);
        mButtonEight.setOnClickListener(listener);
        mButtonNine.setOnClickListener(listener);

    }

    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastNumeric && !mStateError) {
                    Button button = (Button) v;
                    String s = (String) button.getText();
                    if (s.equals("÷")) {
                        s = "/";
                    } else if (s.equals("×")) {
                        s = "*";
                    }
                    mTextViewString.append(s);
                    mLastNumeric = false;
                    mLastDot = false;
                }
            }
        };
        mButtonSum.setOnClickListener(listener);
        mButtonNegative.setOnClickListener(listener);
        mButtonMultiply.setOnClickListener(listener);
        mButtonDivide.setOnClickListener(listener);

        mButtonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastNumeric && !mStateError && !mLastDot) {
                    mTextViewString.append(".");
                    mLastNumeric = false;
                    mLastDot = true;
                }
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewString.setText("");
                mLastNumeric = false;
                mStateError = false;
                mLastDot = false;
            }
        });

        mButtonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual() {
        if (mLastNumeric && !mStateError) {
            String txt = mTextViewString.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                mTextViewString.setText(Double.toString(result));
                mLastDot = true;
            } catch (ArithmeticException ex) {
                mTextViewString.setText("Error");
                mStateError = true;
                mLastNumeric = false;
            }
        }
    }
}