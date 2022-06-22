package com.example.school.journaling;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.exifinterface.media.ExifInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.DataJournaling;
import com.example.school.home.MainActivity;
import com.example.school.home.ModelGratitudeListingResponseData;
import com.example.school.home.ui.ModelGratitudeListingResponse;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.CheckFileType;
import com.example.school.resources.FileOperations;
import com.example.school.resources.FileUpload;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.UriUtils;
import com.example.school.resources.Urls_;
import com.example.school.resources.Utils;
import com.example.school.resources.apidata.MakeCall;
import com.example.school.resources.showstatus.ShowLoader;
import com.example.school.resources.showstatus.ShowToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
    DataJournaling dataJournaling;
    private String mParam1;
    private String mParam2;
    static String SelectedFileId = "";
    String file_path = "";
    AlertDialog.Builder builder;
    boolean isStartDate=true;
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
    MainActivity mainActivity;
    static RoundedImageView attached_image;
    ImageView iv_filter;
    ModelGratitudeListingResponseData gratitudeModelDataForEdit;
    public JournalingMainListing() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.menu_journaling));
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

        iv_filter = view.findViewById(R.id.iv_filter);
        et_gratitudeSearchBar = view.findViewById(R.id.et_gratitudeSearchBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataJournaling = new DataJournaling();
        modelFriendListData = new ArrayList<>();

        nameBuffer = new StringBuffer();
        idBuffer = new StringBuffer();

        fb_add_gratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = false;
                getAllCategories();

            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogForFilterByDate();
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
        dataJournaling.fetchGratitudesDataList("", strDate, "", "", getContext(), getActivity(), JournalingMainListing.this);
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


    public void editGratitude(ModelGratitudeListingResponseData modelGratitudeListingResponseData) {
        gratitudeModelDataForEdit = modelGratitudeListingResponseData;
        isEdit = true;
        getAllCategories();
        //openDialogAddGratitude();

    }
    public void dialogAddGratitude(ArrayList<ModelCategoryResponseData> data, ModelCategoryResponse categoryResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_journaling, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppLog.i(TAG, " handleIntent showMembersDialog: ");
        //AppCompatImageView iv_main_banner = view.findViewById(R.id.iv_main_banner);
        //Glide.with(getContext()).load(categoryResponse.getBanner_image()).into(iv_main_banner);

        et_title = view.findViewById(R.id.et_goal_title);
        Spinner sp_category = view.findViewById(R.id.sp_goal_frequency);
        tv_share_with_friends = view.findViewById(R.id.tv_share_with_friends);
        et_desc = view.findViewById(R.id.et_desc);
        et_location = view.findViewById(R.id.et_location);
        Button btn_submit = view.findViewById(R.id.btn_submit);
        Button btn_cancel_gratitude = view.findViewById(R.id.btn_cancel_gratitude);

        tv_attachment = view.findViewById(R.id.tv_attachment);
        //iv_error_message = view.findViewById(R.id.iv_error_message);

        // ImageView imageviewclose = view.findViewById(R.id.imageviewclose);
        attached_image = view.findViewById(R.id.iv_goal_attached_image);
        //ImageView imageviewsubmit = view.findViewById(R.id.imageviewsubmit);
        //tv_file_name = view.findViewById(R.id.tv_file_name);

        //ImageView imageviespeak1 = view.findViewById(R.id.imageviespeak1);

        if (isEdit) {
            et_title.setText(gratitudeModelDataForEdit.getGratituteName());
            et_desc.setText(gratitudeModelDataForEdit.getGratituteDescription());
            //tv_file_name.setText(gratitudeModelDataForEdit.getFileAttachment());
            if (!gratitudeModelDataForEdit.getSharedUserName().equalsIgnoreCase("")
                    || gratitudeModelDataForEdit.getSharedUserName().equalsIgnoreCase(" ")) {
                tv_share_with_friends.setText(gratitudeModelDataForEdit.getSharedUserName());
            } else {
                tv_share_with_friends.setText(getContext().getString(R.string.share_with_friends));
            }

            //idBuffer.append(gratitudeModelDataForEdit.getSharedUserIds());
            //tv_share_with_friends.setText(gratitudeModelDataForEdit.get);
            tv_share_with_friends.setEnabled(false);
            tv_share_with_friends.setBackgroundColor(getContext().getResources().getColor(R.color.disable_button_color));
            String fileName = gratitudeModelDataForEdit.getFileAttachment();
            if (gratitudeModelDataForEdit.getFileAttachment().equalsIgnoreCase("")) {
                attached_image.setVisibility(View.GONE);
            } else {
                attached_image.setVisibility(View.VISIBLE);
            }

            int index = fileName.lastIndexOf('.');
            if (index > 0) {
                String extension = fileName.substring(index + 1);
                System.out.println("File extension is " + extension);
                if (extension.equalsIgnoreCase("docx")) {
                    attached_image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.vi_doc_file));
                } else if (extension.equalsIgnoreCase("jpg")
                        || extension.equalsIgnoreCase("jpeg")
                        || extension.equalsIgnoreCase("png")
                ) {
                    Glide.with(getContext()).load(gratitudeModelDataForEdit.getFileAttachment()).into(attached_image);
                } else if (extension.equalsIgnoreCase("pdf")) {
                    attached_image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.vi_pdf_file));
                } else if (extension.equalsIgnoreCase("xlsx")
                        || extension.equalsIgnoreCase("xls")
                        || extension.equalsIgnoreCase("xlt")
                ) {
                    attached_image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.vi_xls_file));
                } else if (extension.equalsIgnoreCase("txt")) {
                    attached_image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.vi_text_file));
                }
            }
        }


        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Select Category");
        for (ModelCategoryResponseData item : data) {
            nameList.add(item.getCategoryName());
        }


        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, nameList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(aa);

        if (isEdit) {
            for (int i = 0; i < data.size(); i++) {
                if (gratitudeModelDataForEdit.getGratituteCategory().equalsIgnoreCase(data.get(i).getCategoryName())) {
                    sp_category.setSelection(i + 1);
                }
            }
        }



        btn_cancel_gratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_share_with_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelFriendListData.size() > 0) {
                    dialogShowFriendAndTeamMembers();
                    Log.i(TAG, "onClick: tv_share_with_friends openDialogFriendAndTeamMembers");
                } else {
                    Log.i(TAG, "onClick: tv_share_with_friends getTeamMembersAndFriends");
                    getTeamMembersAndFriends();
                }
            }
        });

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
                } else if (sp_category.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_SHORT).show();
                } else {
                    /*addGratitudeJournaling(gratitude_title.getText().toString(),
                            et_description.getText().toString(),
                            sp_category.getSelectedItemPosition());*/
                    try {
                        String categoryId = data.get(sp_category.getSelectedItemPosition() - 1).getCategoryId();

                        addGratitudeJournaling(et_title.getText().toString(),
                                et_desc.getText().toString(), categoryId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
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
        Preferences.initialize(getContext());
        Preferences.save(General.UPLOADING_CONTENT_FROM,"JournalingMainListing");

        if (checkSelfPermission(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT);
            musicIntent.setType("*/*");
            getActivity().startActivityForResult(musicIntent, 2021);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showRotationalPremissionDialog();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    2021);
        }
    }

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
                                dialogShowFriendAndTeamMembers();
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

    public void dialogShowFriendAndTeamMembers() {
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


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode);
        try {
            String file_path = UriUtils.getPathFromUri(MainActivity.this, data.getData());
            double size = FileOperations.getSizeMB(file_path);
            if (file_path == null || file_path.trim().length() <= 0) {
                Log.i(TAG, "onActivityResult: path");
                showErrorMessage("Please Select Valid File");
               // imageResponseInterface.showErrorMessage("Please Select Valid File");
                //Toast.makeText(MainActivity.this, "Please select valid file", Toast.LENGTH_SHORT).show();
            } else if (size > 10.0) {
                Log.i(TAG, "onActivityResult: size " + size);
                Log.i(TAG, "onActivityResult: size greater");
                //showErrorMessage
                showErrorMessage("Max file size allowed is 10MB");
                //Toast.makeText(MainActivity.this, "Max file size allowed is 10MB", Toast.LENGTH_SHORT).show();
            } else if (CheckFileType.isDocument(file_path) || CheckFileType.xlsFile(file_path)
                    || CheckFileType.pdfFile(file_path) || CheckFileType.imageFile(file_path)) {
                Log.i(TAG, "onActivityResult: uploading");
                new MainActivity.UploadFile().execute(file_path);
            } else {
                showErrorMessage("Please Select Valid File");
                //Toast.makeText(MainActivity.this, "Please Select Valid File", Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    public void onImageSelectedMethod1(Context context, String path, long file_id) {
        file_path = path;
       // tv_file_name.setText(file_path);
        SelectedFileId = String.valueOf(file_id);
        this.attached_image.setVisibility(View.VISIBLE);
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
                            dataJournaling.fetchGratitudesDataList("", strDate, "", "", getContext(), getActivity(), JournalingMainListing.this);
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

    public void showEmptyDataMessage() {
        tv_error_msg.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    public void dialogForFilterByDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_gratitude_date_filter_, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppLog.i(TAG, "openDialogForFilterByDate: ");

        TextView et_start_date = view.findViewById(R.id.et_start_date);
        TextView tv_end_error = view.findViewById(R.id.tv_end_error);
        TextView tv_start_error = view.findViewById(R.id.tv_start_error);
        TextView tv_end_date = view.findViewById(R.id.tv_sober_start_date);
        ImageView imageviewclose = view.findViewById(R.id.imageviewclose);
        ImageView imageviewsubmit = view.findViewById(R.id.imageviewsubmit);
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (isStartDate) {
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    et_start_date.setText(sdf.format(myCalendar.getTime()));
                    if (tv_start_error.isShown()) {
                        tv_start_error.setVisibility(View.GONE);
                    }
                } else {
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    tv_end_date.setText(sdf.format(myCalendar.getTime()));
                    tv_end_error.setVisibility(View.GONE);
                }
            }

        };

        et_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageviewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        imageviewsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_start_date.getText().toString().equalsIgnoreCase("")) {
                    tv_start_error.setVisibility(View.VISIBLE);
                    tv_start_error.setText("Field Required.");
                } else if (tv_end_date.getText().toString().equalsIgnoreCase("")) {
                    tv_end_error.setVisibility(View.VISIBLE);
                    tv_end_error.setText("Field Required.");
                } else {
                    Date startDate = Utils.convertStringToDate(et_start_date.getText().toString(), "yyyy-MM-dd");
                    Date endDate = Utils.convertStringToDate(tv_end_date.getText().toString(), "yyyy-MM-dd");
                    if (endDate.after(startDate)) {
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = dateFormat.format(currentTime);
                        //getGratitudes("", strDate, et_start_date.getText().toString(), tv_end_date.getText().toString());
                        dataJournaling.fetchGratitudesDataList("", strDate, ""+et_start_date.getText().toString(), ""+tv_end_date.getText().toString(), getContext(), getActivity(), JournalingMainListing.this);
                        dialog.dismiss();
                    } else {
                        tv_end_error.setVisibility(View.VISIBLE);
                        tv_end_error.setText("End date should be greater.");
                        //tv_end_date.setError("End date should be greater..");
                    }
                }
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void showData() {
        tv_error_msg.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void onClickedLike(ModelGratitudeListingResponseData model) {
        LikeUnlikeGratitude likeUnlikeGratitude=new LikeUnlikeGratitude();
        likeUnlikeGratitude.onClickedLike(model,getContext(),getActivity());
       /* HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "like_gratitute");
        requestMap.put("gratitute_journling_id", model.getGratituteId());

        //APIManager.Companion.getInstance().showProgressDialog(getContext(), false, "loading..");
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(),getActivity());
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        //APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Gson gson = new Gson();
                            ModelLikeResponse mTodoMonthListingModel = gson.fromJson(response.body(), ModelLikeResponse.class);
                            if (mTodoMonthListingModel.getStatus() == 200) {
                                if (!mTodoMonthListingModel.getData().get(0).getIsLikeSymbol().equalsIgnoreCase("Like")) {
                                    Toast.makeText(getActivity(), "Like", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Unlike", Toast.LENGTH_SHORT).show();
                                }
                                //mTodoMonthListingModel.getData().get(0).getIsLikeSymbol()
                                //adapterGratitudeMainListing.changeLikeForGratitude(mTodoMonthListingModel.getData(), model.getGratituteId());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        //APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //showError(true, status);
        }*/
    }

    public void getAllCategories() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GRATITUDE_GET_CATAGORY);

        APIManager.Companion.getInstance().showProgressDialog(getContext(), false,
                getActivity().getString(R.string.loading));
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getActivity(), getActivity());
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Gson gson = new Gson();
                            ModelCategoryResponse categoryResponse = gson.fromJson(response.body(), ModelCategoryResponse.class);
                            if (categoryResponse.getStatus() == 200) {
                                ArrayList<ModelCategoryResponseData> data = categoryResponse.getData();


                                dialogAddGratitude(data, categoryResponse);


                                //Toast.makeText(getActivity(), "Deleted Successfully..", Toast.LENGTH_SHORT).show();
                            } else {

                                //showError(true, 2);
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
        } else {
            //showError(true, status);
        }
    }

    long SelectedFile;
    @SuppressLint("StaticFieldLeak")
    private class UploadFile extends AsyncTask<String, Void, Integer> {
        ShowLoader showLoader;
        String path;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoader = new ShowLoader();
            showLoader.showUploadDialog(getActivity(), "Uploading...");
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
                        Actions_.FMS, getContext(), getActivity());
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
                    ShowToast.toast(getResources().getString(R.string.upload_successful), getActivity());
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
                        Preferences.save(General.UPLOADING_CONTENT_FROM, "JournalingMainListing");
                       /* if (Preferences.contains(General.UPLOADING_CONTENT_FROM)) {
                            if (Preferences.get(General.UPLOADING_CONTENT_FROM).equalsIgnoreCase("JournalingMainListing")) {
                                imageResponseInterface.onImageSelectedMethod1(MainActivity.this, path, SelectedFile);
                            } else if (Preferences.get(General.UPLOADING_CONTENT_FROM).equalsIgnoreCase("FragmentAddGoal")) {
                                interfaceGoalImageResponseHandler.onGoalImageSelected(MainActivity.this, path, SelectedFile);
                            }
                        }*/

                        onImageSelectedMethod1(getContext(), path, SelectedFile);


                    } else {
                        //permissionResult.onUploadImageForMedicine(SelectedFile, MainActivity.this);
//                    permissionResult.onUploadImageForMedicine(SelectedFile,MainActivity.this);
                        Log.i(TAG, "onPostExecute : file_id" + SelectedFile);
                    }
                    //et_select_file.setText(FileOperations.getFileName(path));
                    break;
                case 2:
                    ShowToast.toast(getResources().getString(R.string.failed), getActivity());
                    showLoader.dismissUploadDialog();
                    break;
                case 11:
                    ShowToast.toast(getResources().getString(R.string.internal_error_occurred), getActivity());
                    showLoader.dismissUploadDialog();
                    break;
                case 12:
                    ShowToast.toast(getResources().getString(R.string.network_error_occurred), getActivity());
                    showLoader.dismissUploadDialog();
                    break;
                default:
                    break;
            }
        }
    }
}