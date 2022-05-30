package com.example.school.journaling;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.JournalingData;
import com.example.school.home.ModelGratitudeListingResponseData;
import com.example.school.home.ui.ModelGratitudeListingResponse;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 28/05/2022
 * Last Modified on
 */
public class JournalingMainListing extends Fragment implements iSelectedImageResponse{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    ArrayList<ModelFriendListResponseData> modelFriendListData;
    JournalingData journalingData;
    private String mParam1;
    private String mParam2;
    static String SelectedFileId = "";
    String file_path = "";
    AdapterGratitudeJournalingMainList adapterGratitudeJournalingList;
    StringBuffer nameBuffer, idBuffer;
    FragmentActivity fragmentActivity;
    private static final String TAG = "JournalingMainListing";
    FloatingActionButton fb_add_gratitude;
    boolean isEdit=false;
    TextView tv_error_msg;
    EditText et_gratitudeSearchBar;
    EditText et_title, et_desc, et_location;
    static TextView tv_share_with_friends, tv_attachment;
    static RoundedImageView attached_image;

    public JournalingMainListing() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            fragmentActivity = (FragmentActivity) context;
        }
    }

    public static JournalingMainListing newInstance(String param1, String param2) {
        JournalingMainListing fragment = new JournalingMainListing();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journaling_main_listing, container, false);
        fb_add_gratitude = view.findViewById(R.id.fb_add_gratitude);
        recyclerView = view.findViewById(R.id.recyclerView);
        tv_error_msg = view.findViewById(R.id.tv_error_msg);
        et_gratitudeSearchBar = view.findViewById(R.id.et_gratitudeSearchBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        journalingData = new JournalingData();
        modelFriendListData = new ArrayList<>();

        nameBuffer = new StringBuffer();
        idBuffer = new StringBuffer();

        fb_add_gratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddGratitude();
            }
        });

        et_gratitudeSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                performSearch(editable.toString());
            }
        });

        return view;
    }

    private void performSearch(String toString) {
        if (adapterGratitudeJournalingList != null) {
            adapterGratitudeJournalingList.getFilter().filter(toString);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentTime);
        journalingData.getGratitudes("", strDate, "", "", getContext(), getActivity(), JournalingMainListing.this);
    }


    public void journalingData(ArrayList<ModelGratitudeListingResponseData> dataArrayList) {
        tv_error_msg.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
         adapterGratitudeJournalingList = new AdapterGratitudeJournalingMainList(dataArrayList, getContext(), JournalingMainListing.this);
        recyclerView.setAdapter(adapterGratitudeJournalingList);
/*
        adapterGratitudeMainListing = new AdapterGratitudeMainListing(dataArrayList, getContext(), GratitudeJournalingMain.this);
        recyclerViewgj.setAdapter(adapterGratitudeMainListing);*/
    }





    public void openDialogAddGratitude() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_journaling, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppLog.i(TAG, " handleIntent showMembersDialog: ");
        //AppCompatImageView iv_main_banner = view.findViewById(R.id.iv_main_banner);
        // Glide.with(getContext()).load(categoryResponse.getBanner_image()).into(iv_main_banner);

        et_title = view.findViewById(R.id.et_title);
        //Spinner sp_category = view.findViewById(R.id.sp_category);
        tv_share_with_friends = view.findViewById(R.id.tv_share_with_friends);
        et_desc = view.findViewById(R.id.et_desc);
        et_location = view.findViewById(R.id.et_location);
        Button btn_submit = view.findViewById(R.id.btn_submit);

        tv_attachment = view.findViewById(R.id.tv_attachment);
        //iv_error_message = view.findViewById(R.id.iv_error_message);

        // ImageView imageviewclose = view.findViewById(R.id.imageviewclose);
        attached_image = view.findViewById(R.id.attached_image);
        //ImageView imageviewsubmit = view.findViewById(R.id.imageviewsubmit);
        //tv_file_name = view.findViewById(R.id.tv_file_name);

        //ImageView imageviespeak1 = view.findViewById(R.id.imageviespeak1);

        TextView tv_desc = view.findViewById(R.id.tv_desc);
        /*ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, nameList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
       /* if (isEdit) {
            for (int i = 0; i < data.size(); i++) {
                if (gratitudeModelDataForEdit.getGratituteCategory().equalsIgnoreCase(data.get(i).getCategoryName())) {
                    sp_category.setSelection(i + 1);
                }
            }
        }*/
        tv_share_with_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelFriendListData.size() > 0) {
                    openDialogFriendAndTeamMembers();
                    Log.i(TAG, "onClick: tv_share_with_friends openDialogFriendAndTeamMembers");
                } else {
                    Log.i(TAG, "onClick: tv_share_with_friends getTeamMembersAndFriends");
                    getTeamMembersAndFriends();
                }
            }
        });

       /* imageviespeak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT1);
                } catch (Exception e) {
                    Toast
                            .makeText(requireContext(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });*/

        /*tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT2);
                } catch (Exception e) {
                    Toast
                            .makeText(requireContext(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });*/

        tv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.save(General.UPLOADING_FROM, "GratitudeJournalingMain");
               /* ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2021);*/
                checkPermission();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_title.getText().toString().equalsIgnoreCase("")) {
                    et_title.setError("Field required.");
                }/* else if (sp_category.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_SHORT).show();
                }*/ else {
                    /*addGratitudeJournaling(gratitude_title.getText().toString(),
                            et_description.getText().toString(),
                            sp_category.getSelectedItemPosition());*/
                    try {
                        //String categoryId = data.get(sp_category.getSelectedItemPosition() - 1).getCategoryId();

                        addGratitudeJournaling(et_title.getText().toString(),
                                et_desc.getText().toString(), "2");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });

        /* imageviewsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gratitude_title.getText().toString().equalsIgnoreCase("")) {
                    gratitude_title.setError("Field required.");
                } else if (sp_category.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_SHORT).show();
                } else {
                    */
        /*addGratitudeJournaling(gratitude_title.getText().toString(),
                            et_description.getText().toString(),
                            sp_category.getSelectedItemPosition());*/
        /*
                    try {
                        String categoryId = data.get(sp_category.getSelectedItemPosition() - 1).getCategoryId();

                        addGratitudeJournaling(gratitude_title.getText().toString(),
                                et_description.getText().toString(), categoryId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });*/
        /* imageviewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SelectedFileId = "";
                idBuffer.delete(0, idBuffer.length());
                nameBuffer.delete(0, nameBuffer.length());
                modelFriendListData.clear();
                //nameBuffer.delete(0, nameBuffer.length());
                //idBuffer.delete(0, idBuffer.length());
            }
        });*/



        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void checkPermission() {
        if (checkSelfPermission(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
            musicIntent.setType("*/*");
            startActivityForResult(musicIntent, 2021);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showRotationalPremissionDialog();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    2021);
        }
    }
    AlertDialog.Builder builder;
    @SuppressLint("ResourceType")
    private void showRotationalPremissionDialog() {
        builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(requireActivity().getResources().getString(R.string.upload_permission))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                2021);
                    }
                })
                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }


    /*"action=get_friend_team_members
  user_id=//login user id
  gratitude_id=//gratitude id when share from gratitude main listing otherwise add nad update send empty"*/
    private void getTeamMembersAndFriends() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "get_friend_team_members");
        requestMap.put("userid", "" + Preferences.get(General.USER_ID));
        requestMap.put("gratitude_id", "");

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());

        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Gson gson = new Gson();
                            ModelTeamAndFriendListResponse listResponse = gson.fromJson(response.body(), ModelTeamAndFriendListResponse.class);

                            if (listResponse.getStatus() == 200) {
                                modelFriendListData = listResponse.getData();
                                openDialogFriendAndTeamMembers();
                            } else {
                                Toast.makeText(getActivity(), "No friends available.", Toast.LENGTH_SHORT).show();
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
        } else {
            //showError(true, status);
        }

    }

    public void openDialogFriendAndTeamMembers() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_friend_list, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppLog.i(TAG, " handleIntent showMembersDialog: data size " + modelFriendListData.size());
        TextView single_choice_team_dialog_title = view.findViewById(R.id.single_choice_team_dialog_title);
        /*TextView single_choice_team_dialog_submit = view.findViewById(R.id.single_choice_team_dialog_submit);
        AppCompatImageButton single_choice_team_dialog_back = view.findViewById(R.id.single_choice_team_dialog_back);*/
        RecyclerView rv_friend_list = view.findViewById(R.id.rv_friend_list);
        ImageView iv_close_button = view.findViewById(R.id.iv_close_button);
        Button btn_submit = view.findViewById(R.id.btn_submit);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv_friend_list.setLayoutManager(mLayoutManager);
        rv_friend_list.setItemAnimator(new DefaultItemAnimator());

        AdapterFriendAndTeams adapter = new AdapterFriendAndTeams(modelFriendListData, getContext(),
                JournalingMainListing.this);
        rv_friend_list.setAdapter(adapter);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<ModelFriendListResponseData> updatedList = adapter.getAllUpdatedList();

                //stringBuffer.length()==0
                if (nameBuffer.length() > 0) {
                    nameBuffer.delete(0, nameBuffer.length());
                }

                if (idBuffer.length() > 0) {
                    idBuffer.delete(0, idBuffer.length());
                }
                for (ModelFriendListResponseData item : updatedList) {
                    if (item.getSelected_status() == 1) {

                        if (nameBuffer.length() == 0) {
                            nameBuffer.append(item.getFullname());
                        } else {
                            nameBuffer.append(",").append(item.getFullname());
                        }
                        if (idBuffer.length() == 0) {
                            idBuffer.append(item.getUserId());
                        } else {
                            idBuffer.append(",").append(item.getUserId());
                        }

                        for (ModelFriendListResponseData data : modelFriendListData) {
                            if (data.getUserId().equalsIgnoreCase(item.getUserId())) {
                                data.setSelected_status(item.getSelected_status());
                            }
                        }

                    }
                }
                tv_share_with_friends.setText(nameBuffer);
            }
        });

        iv_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode);
    }

    @Override
    public void onImageSelectedMethod1(Context context, String path, long file_id) {
        file_path = path;
       // tv_file_name.setText(file_path);
        SelectedFileId = String.valueOf(file_id);
        attached_image.setVisibility(View.VISIBLE);
        //iv_error_message.setVisibility(View.GONE);
        checkThumbnailAndSet(file_path);
    }

    public void checkThumbnailAndSet(String file_path) {
        int index = file_path.lastIndexOf('.');
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        if (index > 0) {
            String extension = file_path.substring(index + 1);
            System.out.println("checkThumbnailAndSet File extension is  " + extension);
            if (extension.equalsIgnoreCase("docx")) {
                // final Bitmap b = BitmapFactory.decodeFile(file_path, options);
                //iv_attched_img.setImageBitmap(b);
                attached_image.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.vi_doc_file));
            } else if (extension.equalsIgnoreCase("jpg")
                    || extension.equalsIgnoreCase("jpeg")
                    || extension.equalsIgnoreCase("png")
            ) {
                // will results in a much smaller image than the original
                final Bitmap b = BitmapFactory.decodeFile(file_path, options);
                attached_image.setImageBitmap(b);
                Log.i(TAG, "onImageSelectedMethod1: id - " + SelectedFileId);
            } else if (extension.equalsIgnoreCase("pdf")) {
                //final Bitmap b = BitmapFactory.decodeFile(file_path, options);
                //iv_attched_img.setImageBitmap(b);
                attached_image.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.vi_pdf_file));
            } else if (extension.equalsIgnoreCase("xlsx")
                    || extension.equalsIgnoreCase("xls")
                    || extension.equalsIgnoreCase("xlt")) {
                // final Bitmap b = BitmapFactory.decodeFile(file_path, options);
                //iv_attched_img.setImageBitmap(b);

                attached_image.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.vi_xls_file));
            } else if (extension.equalsIgnoreCase("txt")) {
                final Bitmap b = BitmapFactory.decodeFile(file_path, options);
                attached_image.setImageBitmap(b);
                attached_image.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.vi_text_file));
            }

            tv_attachment.setText("File Attached");
        }
    }


    @Override
    public void showErrorMessage(String Message) {

    }

    /*"action= gratitude_add
    title= Test title 123
    category= 1
    userid= 10207
    desc= Test description
    file_attachment= file in post method
    shared_user_id=//select user id from drop down"*/
    public void addGratitudeJournaling(String title, String desc, String catId) {
        HashMap<String, String> requestMap = new HashMap<>();
        if (isEdit) {
            requestMap.put(General.ACTION, Actions_.GRATITUDE_EDIT);
            //requestMap.put("gratitudeid", "" + gratitudeModelDataForEdit.getGratituteId());
            if (idBuffer.length() > 0) {
                idBuffer.delete(0, idBuffer.length());
            }
        } else {
            requestMap.put(General.ACTION, Actions_.GRATITUDE_ADD);
        }
        requestMap.put(General.TITLE, title);
        requestMap.put(General.CATEGORY, "" + catId);
        requestMap.put("desc", "" + desc);
        requestMap.put("userid", "" + Preferences.get(General.USER_ID));
        requestMap.put("file_attachment", "" + SelectedFileId);//SelectedFileId,idBuffer
        requestMap.put("shared_user_id", "" + idBuffer);
        AppLog.i(TAG, "addGratitudeJournaling: catId " + catId);
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getActivity(), getActivity());

        APIManager.Companion.getInstance().showProgressDialog(getContext(), false,
                getActivity().getString(R.string.loading));
        try {
            APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        Gson gson = new Gson();
                        ModelGratitudeListingResponse gratitudeListingResponse = gson.fromJson(response.body(), ModelGratitudeListingResponse.class);
                        if (gratitudeListingResponse.getStatus() == 200) {
                            if (isEdit) {
                                Toast.makeText(getContext(), "Successfully updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Successfully uploaded.", Toast.LENGTH_SHORT).show();
                            }
                            modelFriendListData.clear();
                            SelectedFileId = "";

                            idBuffer.delete(0, idBuffer.length());
                            nameBuffer.delete(0, nameBuffer.length());

                            Date currentTime = Calendar.getInstance().getTime();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String strDate = dateFormat.format(currentTime);

                            Date calDate = Calendar.getInstance().getTime();
                            //calender_view.setDate(calDate);
                            journalingData.getGratitudes("", strDate, "", "", getContext(), getActivity(), JournalingMainListing.this);
                            //getEventsForMonth();
                        } else {
//                          showError(true, 2);
                            Toast.makeText(getContext(), "Something went wrong try later..", Toast.LENGTH_SHORT).show();
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
    }

    public void showEmptyDataMessage(ArrayList<ModelGratitudeListingResponseData> dataArrayList) {
        tv_error_msg.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}