package com.example.datasalvestamine;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity {

    EditText productInput;
    TextView productOutput;
    Button addbutton, deletebutton;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productInput = (EditText) findViewById(R.id.productInput);
        productOutput = (TextView) findViewById(R.id.productOutput);
        addbutton = (Button) findViewById(R.id.addbutton);
        deletebutton = (Button) findViewById(R.id.deletebutton);

        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }
    /*add a product to database*/
    public void addButtonClicked(View view){
        String product = productInput.getText().toString();
        Products p = new Products(product);
        dbHandler.addProducts(p);
        printDatabase();
    }
    /*delete items*/
    public void deleteButtonClicked(View view){
        String inputText = productInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        productOutput.setText(dbString);
        productInput.setText("");
    }
}
