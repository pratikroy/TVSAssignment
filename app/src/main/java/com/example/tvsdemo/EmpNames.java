package com.example.tvsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class EmpNames extends AppCompatActivity implements EmpAdapter.EmpAdapterClickHandler{

    private static final String TAG = EmpNames.class.getSimpleName();
    private EditText mEditText;
    ArrayList<EmpDetails> empDetails = new ArrayList<>();
    private EmpAdapter empAdapter;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmpNames.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_names);
        Intent newIntent = getIntent();
        empDetails = newIntent.getExtras().getParcelableArrayList("empList");
        Log.v(TAG, String.valueOf(empDetails.size()));
        recyclerView = findViewById(R.id.rv_employee_names);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        empAdapter = new EmpAdapter(EmpNames.this, EmpNames.this);
        recyclerView.setAdapter(empAdapter);
        empAdapter.setEmpObject(empDetails);
        mEditText = findViewById(R.id.et_search_list);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String textToSearch){
        ArrayList<EmpDetails> filteredList = new ArrayList<>();
        for(EmpDetails emp : empDetails){
            if(emp.getEmpName().toLowerCase().contains(textToSearch.toLowerCase())){
                filteredList.add(emp);
            }
        }
        empAdapter.viewSearchResult(filteredList);
    }

    @Override
    public void onClickForEmpDetails(EmpDetails details) {
        Log.v(TAG, details.getEmpName());
        Intent viewDetails = new Intent(EmpNames.this, ViewDetails.class);
        viewDetails.putExtra("empObject", details);
        startActivity(viewDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.show_salary){
            Intent salaryIntent = new Intent(EmpNames.this, ViewSalary.class);
            salaryIntent.putParcelableArrayListExtra("showSalary", empDetails);
            startActivity(salaryIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
