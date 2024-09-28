package com.example.calculatoranuj;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatoranuj.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String inptext = "";
    private String sign = "";
    private String sign0 = "";
    private double r = 0.0;
    int l=0;
Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Assign listeners for number and operator buttons
        binding.zeo.setOnClickListener(Addnum("0"));
        binding.one.setOnClickListener(Addnum("1"));
        binding.two.setOnClickListener(Addnum("2"));
        binding.the.setOnClickListener(Addnum("3"));
        binding.four.setOnClickListener(Addnum("4"));
        binding.fiv.setOnClickListener(Addnum("5"));
        binding.six.setOnClickListener(Addnum("6"));
        binding.sev.setOnClickListener(Addnum("7"));
        binding.eig.setOnClickListener(Addnum("8"));
        binding.nin.setOnClickListener(Addnum("9"));
        binding.dot.setOnClickListener(Addnum("."));
        binding.div.setOnClickListener(Addnum("/"));
        binding.mul.setOnClickListener(Addnum("*"));
        binding.sub.setOnClickListener(Addnum("-"));
        binding.pp.setOnClickListener(Addnum("+"));
        binding.per.setOnClickListener(Addnum("%"));
        binding.eql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=true;
                onCalculate( sign0);
                    // Change the UI initially
                    binding.result.setTextSize(34); // Set result text size
                    binding.result.setTextColor(getResources().getColor(android.R.color.white)); // Set white color

                    binding.edit.setTextSize(25); // Set edit text size
                    binding.edit.setTextColor(Color.parseColor("#6D6D6D")); // Set gray color using parseColor

                    binding.result.setText(String.valueOf(r)); // Display result
              


        }});



        binding.callclearall.setOnClickListener(v -> {
            inptext = "";
r=0.0;
l=0;
            binding.edit.setText(inptext);
            binding.result.setText(String.valueOf(r));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.result.setText("");
                }
            }, 2000);
        });

        binding.cc.setOnClickListener(v -> {
            if (inptext.length() > 0) {

              String  inp = inptext.substring(0, inptext.length() - 1);
                binding.edit.setText(inp);
               inptext=inp;
                Log.d("shashwat", "onCreate: "+inptext);
                r=0.0;
                l=0;

            }
        });
    }

    private View.OnClickListener Addnum(String s) {
        return v -> {     // Revert the UI after 5 seconds
            binding.result.setTextSize(24); // Reset result text size
            binding.result.setTextColor(Color.parseColor("#6D6D6D")); // Reset gray color

            binding.edit.setTextSize(34); // Reset edit text size
            binding.edit.setTextColor(getResources().getColor(android.R.color.white)); // Reset white color

            binding.result.setText(String.valueOf(r)); // Display result again
            inptext += s;
            binding.edit.setText(inptext);


            if(s=="+" || s=="*" || s=="-" || s=="/" || s=="%"  ){

                l=l+1;

               if(l==1){
                   sign0=s;


               }
              if (l>1){
                    sign=s;
                  onCalculate(sign0);
                  sign0=sign;
                  binding.edit.setText(inptext);
                    l=l-1;


                }


            }

        };
    }

    private void onCalculate(String sign0) {
        Runnable task = () -> {
            try {
                String[] s = inptext.split("");
                int m = inptext.indexOf(sign0);

                double b = 0.0;
                double a = Double.parseDouble(inptext.substring(0, m));
                if (flag) {
                    b = Double.parseDouble(inptext.substring(m + 1));
                } else {
                    b = Double.parseDouble(inptext.substring(m + 1, inptext.length() - 1));
                }

                switch (sign0) {
                    case "+":
                        r = a + b;
                        break;
                    case "-":
                        r = a - b;
                        break;
                    case "*":
                        r = a * b;
                        break;
                    case "/":
                        // Handle division by zero
                        if (b == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        r = a / b;
                        break;
                    case "%":
                        r = a % b;
                        break;
                }

                runOnUiThread(() -> {
                    binding.result.setText(String.valueOf(r));
                    if (flag) {
                        inptext = String.valueOf(r);
                        flag = false;
                    } else {
                        inptext = String.valueOf(r) + sign;
                    }
                    binding.edit.setText(inptext);
                });
            } catch (NumberFormatException e) {
                Log.e("CalculatorError", "Invalid number format: " + e.getMessage());
                runOnUiThread(() -> binding.result.setText("Error"));
            } catch (ArithmeticException e) {
                Log.e("CalculatorError", "Math error: " + e.getMessage());
                runOnUiThread(() -> binding.result.setText("Math Error"));
            } catch (Exception e) {
                Log.e("CalculatorError", "An unexpected error occurred: " + e.getMessage());
                runOnUiThread(() -> binding.result.setText("Error"));
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }


}

