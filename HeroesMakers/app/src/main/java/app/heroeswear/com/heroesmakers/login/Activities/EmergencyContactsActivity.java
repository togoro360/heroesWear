package app.heroeswear.com.heroesmakers.login.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.heroeswear.com.heroesmakers.R;
import app.heroeswear.com.heroesmakers.login.models.Contact;

public class EmergencyContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emargency_contacts);

        ArrayList<Contact> contacts = getContacts();

        final ArrayList<String> contactsName = getContactsNames(contacts);

        ImageButton imageButtonAdd = findViewById(R.id.add);
        ListView listViewContacts = findViewById(R.id.contactList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsName);

        listViewContacts.setAdapter(arrayAdapter);

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedContact = contactsName.get(i);
                Toast.makeText(getApplicationContext(), "Calling " + selectedContact,   Toast.LENGTH_LONG).show();
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyContactsActivity.this, NewContactActivity.class);
                startActivity(intent);
            }
        });
    }

    // TODO return emergency contacts from server or shard preferences
    private ArrayList<String> getContactsNames(ArrayList<Contact> contacts){
        ArrayList<String> arrayList = new ArrayList<>();
        for(Contact contact : contacts){
            arrayList.add(contact.getName());
        }
        return arrayList;
    }

    // TODO get contact from server
    private ArrayList<Contact> getContacts(){
        ArrayList<Contact> arrayList = new ArrayList<>();

        arrayList.add(new Contact("yehuda", "4434343", 1));
        arrayList.add(new Contact("david", "6565676", 2));

        return arrayList;
    }
}
