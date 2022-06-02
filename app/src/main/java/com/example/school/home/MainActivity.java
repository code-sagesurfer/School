package com.example.school.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.databinding.ActivityMainBinding;
import com.example.school.home.dailyplanner.FragmentPlannerMain;
import com.example.school.journaling.JournalingMainListing;
import com.example.school.journaling.iSelectedImageResponse;
import com.example.school.login.LoginActivity;
import com.example.school.moodtracking.FragmentMoodTrackingListing;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.CheckFileType;
import com.example.school.resources.FileOperations;
import com.example.school.resources.FileUpload;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.UriUtils;
import com.example.school.resources.showstatus.ShowLoader;
import com.example.school.resources.Urls_;
import com.example.school.resources.oauth.OauthPreferences;
import com.example.school.resources.showstatus.ShowToast;
import com.example.school.settings.FragmentSettings;
import com.example.school.toolkit.ItemListDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * <p>
 * Last Modified on
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private LinearLayout ll_home, ll_nav_second_menu, ll_nav_third_menu, ll_nav_toolkit;
    private Toolbar main_toolbar;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    boolean showHide;
    CircleImageView iv_user_profile;
    private TextView tv_role, tv_user_name;
    private TextView tv_settings, tv_toolbar_title;
    iSelectedImageResponse imageResponseInterface;
    private static final String TAG = "MainActivity";
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    long SelectedFile;
    TextView iv_save_data;
    ImageView iv_settings;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        //mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                //toolbar.setNavigationIcon(R.drawable.vi_drawer_hamburger_icon);
            }
        };
        //toggle.setDrawerIndicatorEnabled(false);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        tv_toolbar_title = toolbar.findViewById(R.id.tv_toolbar_title);
        iv_save_data = toolbar.findViewById(R.id.iv_save_data);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().getHideOffset();
        toolbar.setNavigationIcon(R.drawable.drawer_menu);


        imageResponseInterface = new JournalingMainListing();
        mDrawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        tv_user_name = headerview.findViewById(R.id.tv_user_name);
        tv_role = headerview.findViewById(R.id.tv_role);
        iv_user_profile = headerview.findViewById(R.id.iv_user_profile);
        tv_user_name.setText(Preferences.get(General.NAME));
        tv_role.setText(Preferences.get(General.ROLE));

        if (Preferences.get(General.IMAGE)!=null && Preferences.get(General.IMAGE).length()!=0 ) {
            Glide.with(this)
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(R.drawable.ic_user_male)
                    .error(R.drawable.ic_user_male)
                    .into(iv_user_profile);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AppLog.i(MainActivity.class.getSimpleName(), "Toolbar icon" + toolbar.getNavigationIcon());
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        tv_settings = headerview.findViewById(R.id.tv_settings);
        iv_settings = headerview.findViewById(R.id.iv_settings);
        iv_settings.setOnClickListener(this);
        tv_settings.setOnClickListener(this);

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new HomeFragment(), "HomeFragment");
        //ft.addToBackStack("HomeFragment");
        ft.commit();

        //mDrawerList = (ListView) findViewById(R.id.left_drawer);
       /* binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.appBarMain.myToolbar.mainToolbar);*/
        /*DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;*/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_journaling)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.main_container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.drawer_menu);
        binding.appBarMain.includeContentMain.llHome.setOnClickListener(this);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu three dots; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        /*NavController navController = Navigation.findNavController(this, R.id.main_container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_nav_toolkit:
                openToolkit();
                break;

            case R.id.tv_settings:
            case R.id.iv_settings:
                FragmentManager fragManager =getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ft.replace(R.id.main_container, new FragmentSettings(), "FragmentSettings");
                //ft.addToBackStack("HomeFragment");
                ft.commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    public void changeDrawerIcon(boolean showHide) {
        this.showHide = showHide;
        if (showHide) {
            toolbar.setNavigationIcon(R.drawable.vi_left_arrow_white);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            toolbar.setNavigationIcon(R.drawable.drawer_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    AppLog.i(MainActivity.class.getSimpleName(), "Toolbar icon" + toolbar.getNavigationIcon());
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            });
        }
    }


    private void openToolkit() {
        ItemListDialogFragment fragmentCreateFolder =
                ItemListDialogFragment.newInstance(1);
        fragmentCreateFolder.show(getSupportFragmentManager(), "ItemListDialogFragment");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AppLog.i(TAG, "onNavigationItemSelected: ");
        AppLog.i(TAG, "onMenuItemClick: item " + item.getTitle());
        if (item.getTitle().toString().equals("Home")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new HomeFragment(), "HomeFragment");

            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (item.getTitle().toString().equals("Journaling")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new JournalingMainListing(), "JournalingMainListing");
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else if (item.getTitle().toString().equals("Mood Tracking")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentMoodTrackingListing(), "FragmentMoodTrackingListing");
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (item.getTitle().toString().equals("Daily Planner")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentPlannerMain(), "FragmentPlannerMain");
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (item.getTitle().toString().equals("Logout")) {
            OauthPreferences.clear();
            Preferences.clear();

            SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
            loginPrefsEditor.clear();
            loginPrefsEditor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode " + requestCode);
        try {
            String file_path = UriUtils.getPathFromUri(MainActivity.this, data.getData());
            double size = FileOperations.getSizeMB(file_path);
            if (file_path == null || file_path.trim().length() <= 0) {
                Log.i(TAG, "onActivityResult: path");
                imageResponseInterface.showErrorMessage("Please Select Valid File");
                //Toast.makeText(MainActivity.this, "Please select valid file", Toast.LENGTH_SHORT).show();
            } else if (size > 10.0) {
                Log.i(TAG, "onActivityResult: size " + size);
                Log.i(TAG, "onActivityResult: size greater");
                //showErrorMessage
                imageResponseInterface.showErrorMessage("Max file size allowed is 10MB");
                //Toast.makeText(MainActivity.this, "Max file size allowed is 10MB", Toast.LENGTH_SHORT).show();
            } else if (CheckFileType.isDocument(file_path) || CheckFileType.xlsFile(file_path)
                    || CheckFileType.pdfFile(file_path) || CheckFileType.imageFile(file_path)) {
                Log.i(TAG, "onActivityResult: uploading");
                new UploadFile().execute(file_path);
            } else {
                imageResponseInterface.showErrorMessage("Please Select Valid File");
                //Toast.makeText(MainActivity.this, "Please Select Valid File", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onActivityResult: requestCode " + requestCode);
        switch (requestCode) {
            case 2021:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    musicIntent.setType("*/*");
                    startActivityForResult(musicIntent, 2021);
                }
                break;
        }
    }

    public void setToolbarTitleText(String displayText) {
        tv_toolbar_title.setText(displayText);
        toolbar.setBackgroundColor(getResources().getColor(R.color.setting_page_background_yellow));
    }

    @SuppressLint("StaticFieldLeak")
    private class UploadFile extends AsyncTask<String, Void, Integer> {
        ShowLoader showLoader;
        String path;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoader = new ShowLoader();
            showLoader.showUploadDialog(MainActivity.this, "Uploading...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            path = params[0];
            int status = 12;
            String file_name = FileOperations.getFileName(path);
            String url = Preferences.get(General.DOMAIN).
                    replaceAll(General.INSATNCE_NAME, "") + Urls_.MOBILE_UPLOADER;
            AppLog.i(TAG, "File Upload URL: " + url);
            String user_id = Preferences.get(General.USER_ID);
            try {
                String response = FileUpload.uploadFile(path, file_name, user_id, url,
                        Actions_.FMS, getApplicationContext(), MainActivity.this);
                //Log.i(UploadFileActivity.class.getSimpleName(), "upload response " + response);
                if (response != null) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                    if (jsonObject.has(Actions_.FMS)) {
                        JsonArray jsonArray = jsonObject.getAsJsonArray(Actions_.FMS);
                        if (jsonArray != null) {
                            JsonObject object = jsonArray.get(0).getAsJsonObject();
                            if (object.has(General.STATUS)) {
                                status = object.get(General.STATUS).getAsInt();
                                if (status == 1) {
                                    SelectedFile = object.get(General.ID).getAsLong();
                                    AppLog.i(TAG, "doInBackground: file_id" + SelectedFile);
                                }
                            } else {
                                status = 11;
                            }
                        } else {
                            status = 11;
                        }
                    }
                } else {
                    status = 11;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (showLoader != null)
                showLoader.dismissUploadDialog();

            switch (result) {
                case 1:
                    ShowToast.toast(getResources().getString(R.string.upload_successful), MainActivity.this);
                    AppLog.i(TAG, "onPostExecute: " + FileOperations.getFileName(path));
                    AppLog.i(TAG, "onPostExecute: path" + path);
                    //filename = FileOperations.getFileName(path);
                    //et_select_file.setText(filename);
                    //Preferences.save("FileName", filename);
                    Preferences.save("file_id", SelectedFile);
                    if (Preferences.contains(General.UPLOADING_FROM)
                            && Preferences.get(General.UPLOADING_FROM).equalsIgnoreCase("GratitudeJournalingMain")) {
                        //permissionResult.onUploadImageForGratitude(SelectedFile,MainActivity.this,path);
                        //showErrorMessage
                        imageResponseInterface.onImageSelectedMethod1(MainActivity.this, path, SelectedFile);
                    } else {
                        //permissionResult.onUploadImageForMedicine(SelectedFile, MainActivity.this);
//                    permissionResult.onUploadImageForMedicine(SelectedFile,MainActivity.this);
                        Log.i(TAG, "onPostExecute : file_id" + SelectedFile);
                    }
                    //et_select_file.setText(FileOperations.getFileName(path));
                    break;
                case 2:
                    ShowToast.toast(getResources().getString(R.string.failed), MainActivity.this);
                    showLoader.dismissUploadDialog();
                    break;
                case 11:
                    ShowToast.toast(getResources().getString(R.string.internal_error_occurred), MainActivity.this);
                    showLoader.dismissUploadDialog();
                    break;
                case 12:
                    ShowToast.toast(getResources().getString(R.string.network_error_occurred), MainActivity.this);
                    showLoader.dismissUploadDialog();
                    break;
                default:
                    break;
            }
        }
    }


}