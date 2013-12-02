package com.mobproto.flamingoctopus;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    ArrayList<HashMap<String, String>> contacts;
    ArrayList<String> numbers = new ArrayList<String>();
    public static ArrayList<String> activeUsers = new ArrayList<String>();
    String username;
    String number;
    static boolean locked;
    public TasksDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locked = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getActionBar();

        dbAdapter = new TasksDbAdapter(this);
        dbAdapter.open();

        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        username = prefs.getString("username", null);
        number = prefs.getString("number", null);

        if (number == null) {
            number = getPhoneNumber(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("number", number);
            editor.commit();
        }

        if (username == null) {
            EditText input = new EditText(this);
            input.setId(1000);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(input).setTitle("Enter your full name!")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    EditText theInput = (EditText) ((AlertDialog) dialog)
                                            .findViewById(1000);
                                    String enteredText = theInput.getText()
                                            .toString();
                                    if (!enteredText.equals("")) {
                                        SharedPreferences.Editor editor = prefs
                                                .edit();
                                        editor.putString("username",
                                                enteredText);
                                        editor.commit();
                                        username = enteredText;
                                    }
                                }
                            }).create();
            dialog.show();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // hide the given tab

            }
            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // probably ignore this event

            }
        };

        // Add 2 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }

  
        if(number == null)
        {
            //for debug purposes only -- remove eventually
            number = "2012807565";
        }
        contacts = getContacts();
        numbers = getNumbers(contacts);
        FirebaseManager manager = new FirebaseManager(number, contacts);
        manager.setup(username);
        manager.getActiveUsers(numbers);
    }

    static void usersFound() {
        locked = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.add_task) {
            (new AddTaskDialogFragment()).show(getSupportFragmentManager(), "add task");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateListViews() {
        sectionsPagerAdapter.longTermFragment.updateListView();
        sectionsPagerAdapter.shortTermFragment.updateListView();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        LongTermTasksListFragment longTermFragment;
        ShortTermTasksListFragment shortTermFragment;
        ScorboardListFragment scoreboardFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            shortTermFragment = new ShortTermTasksListFragment();
            longTermFragment = new LongTermTasksListFragment();
            scoreboardFragment = new ScorboardListFragment();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) {
                return shortTermFragment;
            } else if (position == 1) {
                return longTermFragment;
            } else if (position == 2) {
                return scoreboardFragment;
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    public  String getPhoneNumber(Context context) {
        //Should raise exception if phone number not found -- prompt user to manually enter phone number
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String num = mTelephonyMgr.getLine1Number();
            return fillPhoneNumber(num);
        } catch (Exception e) {
            //hack for testing some functionality on emulator
            return "5402095219";
        }
    }

    private String fillPhoneNumber(String num) {
        //clean up phone number string
        try{
            if (num != null && num.length() > 0) {
                if (num.length() >= 9) {
                    num = num.replaceAll("\\D", "");
                    if (num.substring(0, 1).equals("8")) {
                        num = "+3" + num;
                    } else if (num.substring(0, 1).equals("0")) {
                        num = "+38" + num;
                    } else if (num.substring(0, 1).equals("3")) {
                        num = "+" + num;
                    }
                }
                return num;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @TODO: Perhaps needs to be async -- takes a little while to load at first

    private ArrayList<String> getNumbers(ArrayList<HashMap<String, String>> contacts) {
        ArrayList<String> numbers = new ArrayList<String>();
        for (HashMap<String, String> contact: contacts) {
            numbers.add(contact.get("number"));
        }
        return numbers;
    }

    private ArrayList<HashMap<String,String>> getContacts() {
        //get full list of names and phone numbers from phone contacts
        ArrayList<HashMap<String,String>> contactData=new ArrayList<HashMap<String,String>>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        while (cursor.moveToNext()) {
            try{
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                        HashMap<String,String> map=new HashMap<String,String>();
                        map.put("name", name);

                        //Normalize phone numbers to use as ids
                        phoneNumber = phoneNumber.replaceAll("[^0-9]","");
                        if (phoneNumber.length() > 10) {
                            phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);
                        } else if (phoneNumber.length() < 10) {
                            phoneNumber = "540" + phoneNumber;
                        }
                        map.put("number", phoneNumber);
                        contactData.add(map);
                    }
                    phones.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        for (int i=0; i<contactData.size(); i++) {
            HashMap<String, String> map = contactData.get(i);
            for (HashMap.Entry<String, String> entry : map.entrySet()) {
            }
        }
        return contactData;
    }



}
