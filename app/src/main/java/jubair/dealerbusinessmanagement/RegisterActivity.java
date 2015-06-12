package jubair.dealerbusinessmanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class RegisterActivity extends ActionBarActivity {

    DealerHandler db;
    CompanyHandler ch;

    EditText name , password , company;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);

        spinner = (Spinner) findViewById(R.id.company_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company_names, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        db = new DealerHandler(this);
        ch = new CompanyHandler(this);
        company = (EditText) findViewById(R.id.user_company);
    }


    public void submit(View view){
        String dealerName = name.getText().toString();
        String dealerPassword = password.getText().toString();

        if(dealerName.equals("") || dealerPassword.equals("") || dealerName == null  || dealerPassword == null){
            Toast.makeText(this, "Name or password field is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Dealer dealer = new Dealer(dealerName, dealerPassword);
            db.insert(dealer);
            Toast.makeText(this, "*****", Toast.LENGTH_SHORT).show();

            String companyName = company.getText().toString();
            int dealerId = db.getID(dealer);
            if(companyName != null && dealerId != -1){
                Company com = new Company(companyName,dealerId);
                ch.insert(com);
            }

            List<Company> list =ch.getAllCompanies();
            for(Company c : list){
                Toast.makeText(this, c.toString(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
