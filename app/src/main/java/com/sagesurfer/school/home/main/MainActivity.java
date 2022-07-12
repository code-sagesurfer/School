package com.sagesurfer.school.home.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.sagesurfer.school.R;

import com.sagesurfer.school.assessment.FragmentAssessmentMain;
import com.sagesurfer.school.databinding.ActivityMainBinding;
import com.sagesurfer.school.goalmanagement.FragmentAddGoal;
import com.sagesurfer.school.goalmanagement.InterfaceGoalImageResponseHandler;
import com.sagesurfer.school.home.HomeFragment;
import com.sagesurfer.school.home.dailyplanner.FragmentPlannerMain;
import com.sagesurfer.school.journaling.JournalingMainListing;
import com.sagesurfer.school.journaling.iSelectedImageResponse;
import com.sagesurfer.school.login.LoginActivity;
import com.sagesurfer.school.moodtracking.FragmentMoodTrackingListing;
import com.sagesurfer.school.notification.FragmentNotificationListing;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.CheckFileType;
import com.sagesurfer.school.resources.FileOperations;
import com.sagesurfer.school.resources.FileUpload;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.UriUtils;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.resources.showstatus.ShowLoader;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.oauth.OauthPreferences;
import com.sagesurfer.school.resources.showstatus.ShowToast;
import com.sagesurfer.school.selfcaremanagement.FragmentSelfcareManagement;
import com.sagesurfer.school.settings.FragmentSettings;
import com.sagesurfer.school.skill_development.FragmentSkillDevelopment;
import com.sagesurfer.school.support.FragmentSupport;
import com.sagesurfer.school.team_care.FragmentTeamDetails;
import com.sagesurfer.school.team_care.ModelTeamListResponse;
import com.sagesurfer.school.toolkit.ItemListDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    RecyclerView rv_drawer_menu;
    boolean showHide;
    CircleImageView iv_user_profile;
    private TextView tv_role, tv_user_name;
    private TextView tv_settings, tv_toolbar_title;
    iSelectedImageResponse imageResponseInterface;
    InterfaceGoalImageResponseHandler interfaceGoalImageResponseHandler;
    private static final String TAG = "MainActivity";
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    long SelectedFile;

    ImageView iv_settings, iv_notification,iv_save_img;

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

        iv_notification = toolbar.findViewById(R.id.iv_notification);
        iv_save_img = toolbar.findViewById(R.id.iv_save_img);

        iv_notification.setOnClickListener(this);

        LinearLayout ll_home = findViewById(R.id.ll_home);
        LinearLayout ll_nav_second_menu = findViewById(R.id.ll_nav_second_menu);
        LinearLayout ll_nav_third_menu = findViewById(R.id.ll_nav_third_menu);
        LinearLayout ll_nav_toolkit = findViewById(R.id.ll_nav_toolkit);
        ll_home.setOnClickListener(this);
        ll_nav_second_menu.setOnClickListener(this);
        ll_nav_third_menu.setOnClickListener(this);
        ll_nav_toolkit.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().getHideOffset();
        toolbar.setNavigationIcon(R.drawable.drawer_menu);

        imageResponseInterface = new JournalingMainListing();
        interfaceGoalImageResponseHandler = new FragmentAddGoal();
        mDrawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        tv_user_name = navigationView.findViewById(R.id.tv_user_name);
        tv_role = navigationView.findViewById(R.id.tv_role);
        iv_user_profile = navigationView.findViewById(R.id.iv_user_profile);
        tv_user_name.setText(Preferences.get(General.NAME));
        tv_role.setText(Preferences.get(General.ROLE));

        rv_drawer_menu = navigationView.findViewById(R.id.rv_drawerMenus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_drawer_menu.setLayoutManager(layoutManager);
        rv_drawer_menu.setItemAnimator(new DefaultItemAnimator());


        if (Preferences.get(General.IMAGE) != null && Preferences.get(General.IMAGE).length() != 0) {
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

        tv_settings = navigationView.findViewById(R.id.tv_settings);
        iv_settings = navigationView.findViewById(R.id.iv_settings);
        iv_settings.setOnClickListener(this);
        tv_settings.setOnClickListener(this);


        setDrawerMenus();
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

    private void setDrawerMenus() {
        Log.i(TAG, "setDrawerMenus: size " + DrawerMenuList.getDrawerMenuList().size());
        AdapterDrawerMenu adapterDrawerMenu = new AdapterDrawerMenu(DrawerMenuList.getDrawerMenuList(), MainActivity.this);
        rv_drawer_menu.setAdapter(adapterDrawerMenu);
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
    protected void onResume() {
        super.onResume();
        changeDrawerIcon(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_nav_toolkit:
                openToolkit();
                break;

            case R.id.tv_settings:
            case R.id.iv_settings:
                FragmentManager fragManager = getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ft.replace(R.id.main_container, new FragmentSettings(), "FragmentSettings");
                //ft.addToBackStack("HomeFragment");
                ft.commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.ll_home:
                FragmentManager fragManagerHome = getSupportFragmentManager();
                FragmentTransaction ftHome = fragManagerHome.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ftHome.replace(R.id.main_container, new HomeFragment(), "HomeFragment");
                //ft.addToBackStack("HomeFragment");
                ftHome.commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.ll_nav_second_menu:
                FragmentManager fragManagerPlanner = getSupportFragmentManager();
                FragmentTransaction ftPlanner = fragManagerPlanner.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ftPlanner.replace(R.id.main_container, new FragmentPlannerMain(), "FragmentPlannerMain");
                //ft.addToBackStack("HomeFragment");
                ftPlanner.commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.ll_nav_third_menu:
                getTeamListFromServer();
                break;

            case R.id.iv_notification:
                FragmentManager fragManagerNotification = getSupportFragmentManager();
                FragmentTransaction ftNotification = fragManagerNotification.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ftNotification.replace(R.id.main_container, new FragmentNotificationListing(), "FragmentNotificationListing");
                //ft.addToBackStack("HomeFragment");
                ftNotification.commit();
                break;


        }
    }

    public void changeDrawerIcon(boolean showHide) {
        this.showHide = showHide;
        if (showHide) {
            toolbar.setNavigationIcon(R.drawable.ic_app_back_button);
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


    private void getTeamListFromServer() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.ALL_TEAMS);
        requestMap.put(General.CODE, Preferences.get(General.DOMAIN_CODE));
        requestMap.put(General.ISFORTEAMCHAT, "0");
       /* if (isTeamDetails) {
            requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));
            requestMap.put(General.GROUP_ID, Preferences.get(General.GROUP_ID));
        }*/
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_NORMAL_TEAMS;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, this, MainActivity.this);

        try {
            APIManager.Companion.getInstance().get_team_list(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        Gson gson = new Gson();
                        assert response.body() != null;
                        String resposeBody = response.body().toString();
                        AppLog.i(TAG, "onResponse: " + resposeBody);
                        ModelTeamListResponse gratitudeListingResponse = gson.fromJson(response.body(), ModelTeamListResponse.class);
                        if (gratitudeListingResponse.getAllTeams().get(0).getStatus() == 1) {
                            if (gratitudeListingResponse.getAllTeams().size() == 1) {
                                Bundle bundle = new Bundle();
                                FragmentTeamDetails teamDetails = new FragmentTeamDetails();
                                bundle.putParcelable(General.TEAM_DATA, (Parcelable) gratitudeListingResponse);
                                teamDetails.setArguments(bundle);

                                FragmentManager fragManager = getSupportFragmentManager();
                                FragmentTransaction ft = fragManager.beginTransaction();
                                ft.replace(R.id.main_container, teamDetails, "FragmentTeamDetails");
                                ft.addToBackStack("HomeFragment");
                                ft.commit();
                                mDrawerLayout.closeDrawer(Gravity.LEFT);
                            } else {
                                Toast.makeText(MainActivity.this, "Please login with adult..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        ft.replace(R.id.main_container, new FragmentTeamDetails(), "FragmentTeamDetails");
        ft.addToBackStack("HomeFragment");
        ft.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode " + requestCode + " " + resultCode);

        switch (requestCode) {
            case General.JOURNALING_PERMISSION:
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
                    break;


                } catch (Exception e) {
                    e.printStackTrace();
                }

            case 201:
                try {
                    String file_path = UriUtils.getPathFromUri(MainActivity.this, data.getData());
                    double size = FileOperations.getSizeMB(file_path);
                    if (file_path == null || file_path.trim().length() <= 0) {
                        Log.i(TAG, "onActivityResult: path");
                        interfaceGoalImageResponseHandler.showGoalErrorMessage("Please Select Valid File");
                        //Toast.makeText(MainActivity.this, "Please select valid file", Toast.LENGTH_SHORT).show();
                    } else if (size > 10.0) {
                        Log.i(TAG, "onActivityResult: size " + size);
                        Log.i(TAG, "onActivityResult: size greater");
                        //showErrorMessage
                        interfaceGoalImageResponseHandler.showGoalErrorMessage("Max file size allowed is 10MB");
                        //Toast.makeText(MainActivity.this, "Max file size allowed is 10MB", Toast.LENGTH_SHORT).show();
                    } else if (CheckFileType.isDocument(file_path) || CheckFileType.xlsFile(file_path)
                            || CheckFileType.pdfFile(file_path) || CheckFileType.imageFile(file_path)) {
                        Log.i(TAG, "onActivityResult: uploading");
                        new UploadFile().execute(file_path);

                    } else {
                        interfaceGoalImageResponseHandler.showGoalErrorMessage("Please Select Valid File");
                        //Toast.makeText(MainActivity.this, "Please Select Valid File", Toast.LENGTH_SHORT).show();
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onActivityResult: requestCode " + requestCode);
        switch (requestCode) {

            case General.JOURNALING_PERMISSION:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    musicIntent.setType("*/*");
                    startActivityForResult(musicIntent, General.JOURNALING_PERMISSION);
                }
                break;

            case 201:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    musicIntent.setType("*/*");
                    startActivityForResult(musicIntent, General.GOAL_PERMISSION);
                }
                break;
        }
    }

    public void setToolbarTitleText(String displayText) {
        tv_toolbar_title.setText(displayText);
    }

    public void toggleBellIcon(boolean toggle) {
        if (toggle) {
            iv_notification.setVisibility(View.GONE);
        }else{
            iv_notification.setVisibility(View.VISIBLE);
        }
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




                    } else {
                        //permissionResult.onUploadImageForMedicine(SelectedFile, MainActivity.this);
//                    permissionResult.onUploadImageForMedicine(SelectedFile,MainActivity.this);
                        Log.i(TAG, "onPostExecute : file_id" + SelectedFile);
                    }

                    if (Preferences.contains(General.UPLOADING_CONTENT_FROM)) {
                        if (Preferences.get(General.UPLOADING_CONTENT_FROM).equalsIgnoreCase("JournalingMainListing")) {
                            imageResponseInterface.onImageSelectedMethod1(MainActivity.this, path, SelectedFile);
                        } else if (Preferences.get(General.UPLOADING_CONTENT_FROM).equalsIgnoreCase("FragmentAddGoal")) {
                            Log.i(TAG, "onPostExecute:FragmentAddGoal ");
                            interfaceGoalImageResponseHandler.onGoalImageSelected(MainActivity.this, path, SelectedFile);
                        }
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

    public void onDrawerMenuItemClicked(ModelDrawerMenuListItems modelDrawerMenuListItems) {
        if (modelDrawerMenuListItems.getName().equals("Home")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new HomeFragment(), "HomeFragment");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Journaling")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new JournalingMainListing(), "JournalingMainListing");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Mood Tracking")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentMoodTrackingListing(), "FragmentMoodTrackingListing");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Team Care")) {
            getTeamListFromServer();
        } else if (modelDrawerMenuListItems.getName().equals("Self-Care")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSelfcareManagement(), "FragmentSelfcareManagement");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Support")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSupport(), "FragmentSupport");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Goal Management")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSkillDevelopment(), "FragmentSkillDevelopment");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Daily Planner")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentPlannerMain(), "FragmentPlannerMain");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Assessment")) {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentAssessmentMain(), "FragmentAssessmentMain");
            ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
            ft.addToBackStack("HomeFragment");
            ft.commit();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (modelDrawerMenuListItems.getName().equals("Logout")) {
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().toString().equals("Home")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new HomeFragment(), "HomeFragment");

            ft.commit();
        } else if (item.getTitle().toString().equals("Journaling")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new JournalingMainListing(), "JournalingMainListing");
            ft.addToBackStack("HomeFragment");
            ft.commit();
        } else if (item.getTitle().toString().equals("Mood Tracking")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);

            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentMoodTrackingListing(), "FragmentMoodTrackingListing");
            ft.addToBackStack("HomeFragment");
            ft.commit();
        } else if (item.getTitle().toString().equals("Team Care")) {
            getTeamListFromServer();
        } else if (item.getTitle().toString().equals("Self-Care")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);

            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSelfcareManagement(), "FragmentSelfcareManagement");
            ft.addToBackStack("HomeFragment");
            ft.commit();
        } else if (item.getTitle().toString().equals("Support")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSupport(), "FragmentSupport");
            ft.addToBackStack("HomeFragment");
            ft.commit();
        } else if (item.getTitle().toString().equals("Goal Management")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentSkillDevelopment(), "FragmentSkillDevelopment");
            ft.addToBackStack("HomeFragment");
            ft.commit();

        } else if (item.getTitle().toString().equals("Daily Planner")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentPlannerMain(), "FragmentPlannerMain");
            ft.addToBackStack("HomeFragment");
            ft.commit();

        } else if (item.getTitle().toString().equals("Assessment")) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, new FragmentAssessmentMain(), "FragmentAssessmentMain");
            ft.addToBackStack("HomeFragment");
            ft.commit();

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
}