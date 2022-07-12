package com.sagesurfer.school.support;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.ArrayOperations;
import com.sagesurfer.school.resources.CheckFileType;
import com.sagesurfer.school.resources.FileOperations;
import com.sagesurfer.school.resources.FileUpload;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.UriUtils;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.KeyMaker_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.resources.showstatus.ShowLoader;
import com.sagesurfer.school.resources.showstatus.ShowToast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFeedback extends Fragment implements View.OnClickListener {

    @BindView(R.id.roundedImageView)
    ImageView roundedImageView;

    @BindView(R.id.roundedImageView2)
    ImageView roundedImageView2;

    /*@BindView(R.id.roundedImageView3)
    RoundedImageView roundedImageView3;*/

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.iv_select_image_btn_1)
    ImageView iv_select_image_btn_1;

    @BindView(R.id.iv_select_image_btn_2)
    ImageView iv_select_image_btn_2;

    @BindView(R.id.iv_close_icon_1)
    ImageView iv_close_icon_1;

    @BindView(R.id.iv_close_icon_2)
    ImageView iv_close_icon_2;

    @BindView(R.id.et_describe_problem)
    EditText et_describe_problem;
    private static final String TAG = "FragmentFeedback";
    private ArrayList<WallAttachment_> attachmentArrayList;
    MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public FragmentFeedback() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.feedback));
            mainActivity.changeDrawerIcon(true);

            mainActivity.toggleBellIcon(true);
        }
    }

    public static FragmentFeedback newInstance(String param1, String param2) {
        FragmentFeedback fragment = new FragmentFeedback();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        roundedImageView.setOnClickListener(this);
        roundedImageView2.setOnClickListener(this);

        iv_close_icon_1.setOnClickListener(this);
        iv_close_icon_2.setOnClickListener(this);

        iv_select_image_btn_2.setOnClickListener(this);
        iv_select_image_btn_1.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        attachmentArrayList = new ArrayList<>();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_select_image_btn_1:
            case R.id.roundedImageView:
                Preferences.save(General.SELECTED_IMAGE_BOX, "first");
                takePermissionFromUser();
                break;

            case R.id.roundedImageView2:
            case R.id.iv_select_image_btn_2:
                Preferences.save(General.SELECTED_IMAGE_BOX, "second");
                takePermissionFromUser();
                break;

               /* case R.id.roundedImageView3:
                Preferences.save(General.SELECTED_IMAGE_BOX, "third");
                takePermissionFromUser();
                break;*/

            case R.id.iv_close_icon_1:
                new RemoveAttachment(1, ""+Preferences.get(General.IMAGE_ID_1)).execute();
                break;

                case R.id.iv_close_icon_2:
                new RemoveAttachment(2, "" +Preferences.get(General.IMAGE_ID_2)).execute();
                break;

            case R.id.btn_submit:
                String message = et_describe_problem.getText().toString().trim();
                if (validate(message)) {
                    submitFeedback(message);
                }
                break;
        }
    }

    // get attachment id based on location
    private String getAttachmentId(int position) {
        ArrayList<Integer> idList = new ArrayList<>();
        if (attachmentArrayList.size() > 0) {
            for (WallAttachment_ wallAttachment_ : attachmentArrayList) {
                if (position == 0) {
                    idList.add(wallAttachment_.getId());
                } else {
                    if (wallAttachment_.getPosition() == position) {
                        idList.add(wallAttachment_.getId());
                    }
                }
            }
        }
        return ArrayOperations.listToString(idList);
    }

    private void submitFeedback(String message) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "add");
        requestMap.put(General.MESSAGE, message);

        if (Preferences.get(General.IMAGE_ID_1).contains(General.IMAGE_ID_1)) {
            requestMap.put("img1", "" + Preferences.get(General.IMAGE_ID_1));
        } else {
            requestMap.put("img1", "");
        }
        if (Preferences.get(General.IMAGE_ID_1).contains(General.IMAGE_ID_2)) {
            requestMap.put("img2", "" + Preferences.get(General.IMAGE_ID_2));
        } else {
            requestMap.put("img2", "");
        }
        Log.d(TAG, "submitFeedback onResponse: 1");
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.FEEDBACK_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        if (requestBody != null) {
            Log.d(TAG, "submitFeedback onResponse: 2");
            try {

                APIManager.Companion.getInstance().showProgressDialog(getContext(), false, "Loading..");


                    APIManager.Companion.getInstance().mobile_feedback(requestBody, new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            APIManager.Companion.getInstance().dismissProgressDialog();
                            try {
                                Log.d(TAG, "submitFeedback onResponse: 3");
                                JsonElement mJsonElement = response.body();
                                Gson gson = new Gson();
                                ModelFeedbackResponse listResponse = gson.fromJson(mJsonElement.toString(), ModelFeedbackResponse.class);
                                if (listResponse.getFeedback().get(0).getStatus()==1){
                                    Toast.makeText(mainActivity, ""+listResponse.getFeedback().get(0).getSuccess(), Toast.LENGTH_SHORT).show();
                                    requireActivity().onBackPressed();
                                }else{
                                    Toast.makeText(mainActivity, ""+listResponse.getFeedback().get(0).getSuccess(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.d(TAG, "submitFeedback onResponse: 4");
                            APIManager.Companion.getInstance().dismissProgressDialog();
                        }
                    });

            /*                String response = NetworkCall_.post(url, requestBody, TAG, getActivity(), getActivity());
                if (response != null) {
                    if (Error_.oauth(response, getContext()) == 13) {
                        showResponses(13);
                        return;
                    }
                    JsonArray jsonArray = GetJson_.getArray(response, "feedback");
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(General.STATUS)) {
                            showResponses(object.get(General.STATUS).getAsInt());
                        } else {
                            showResponses(11);
                            return;
                        }
                    } else {
                        showResponses(11);
                        return;
                    }
                } else {
                    showResponses(12);
                    return;
                }
                return;*/
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "submitFeedback onResponse: 5");
            }
        }

    }

    private void takePermissionFromUser() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            Intent intentOne = new Intent(Intent.ACTION_GET_CONTENT);
                            intentOne.setType("*/*");
                            startActivityForResult(intentOne, 1);
                        } else {
                            Toast.makeText(getContext(), "Please granted all.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // validate feedback message for it's min/max length
    private boolean validate(String message) {
        if (message == null || message.length() <= 0) {
            et_describe_problem.setError("Please enter message");
            return false;
        }
        if (message.length() < 3) {
            et_describe_problem.setError("Minimum 3 char required");
            return false;
        }
        if (message.length() > 250) {
            et_describe_problem.setError("Maximum 250 char allowed");
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            // String file_path = PathUtils.getFilePath(getApplicationContext(), data.getData());
            String file_path = UriUtils.getPathFromUri(getContext(), data.getData());
            if (file_path == null || file_path.trim().length() <= 0) {
                //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, , getContext());
                Toast.makeText(mainActivity, "" + this.getResources().getString(R.string.valid_file_error), Toast.LENGTH_SHORT).show();
                return;
            }

            double size = FileOperations.getSizeMB(file_path);
            if (size > 10) {
                //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, this.getResources().getString(R.string.max_10_mb_allowed), getContext());
                Toast.makeText(mainActivity, "" + this.getResources().getString(R.string.max_10_mb_allowed), Toast.LENGTH_SHORT).show();
                return;
            }

            if (CheckFileType.imageFile(file_path)) {
                new UploadFile(requestCode, file_path, getActivity()).execute();
            } else {
                //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, this.getResources().getString(R.string.valid_file_error), getContext());
                Toast.makeText(mainActivity, "" + this.getResources().getString(R.string.valid_file_error), Toast.LENGTH_SHORT).show();
            }
        }

    }

    // background call to make upload file call
    @SuppressLint("StaticFieldLeak")
    private class UploadFile extends AsyncTask<Void, Void, Integer> {
        ShowLoader showLoader;
        final int position;
        final String path;
        Activity activity;

        UploadFile(int position, String path, Activity activity) {
            this.position = position;
            this.path = path;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoader = new ShowLoader();
             showLoader.showUploadDialog(activity,"Uploading..");
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int status = 12;
            String file_name = FileOperations.getFileName(path);
            String url = Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, "")
                    + Urls_.MOBILE_UPLOADER;
            String user_id = Preferences.get(General.USER_ID);
            try {
                String response = FileUpload.uploadFile(path, file_name, user_id, url,
                        Actions_.SUPPORT, getContext(), getActivity());
                if (response != null) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                    if (jsonObject.has(Actions_.SUPPORT)) {
                        JsonArray jsonArray = jsonObject.getAsJsonArray(Actions_.SUPPORT);
                        if (jsonArray != null) {
                            JsonObject object = jsonArray.get(0).getAsJsonObject();
                            if (object.has(General.STATUS)) {
                                status = object.get(General.STATUS).getAsInt();
                                if (status == 1) {
                                    WallAttachment_ wallAttachment_ = new WallAttachment_();
                                    wallAttachment_.setId(object.get(General.ID).getAsInt());
                                    wallAttachment_.setPath(path);
                                    wallAttachment_.setImage(file_name);
                                    wallAttachment_.setSize(FileOperations.getSize(path));
                                    wallAttachment_.setPosition(position);
                                    attachmentArrayList.add(wallAttachment_);
                                    if (Preferences.get(General.SELECTED_IMAGE_BOX).equalsIgnoreCase("first")) {
                                        Preferences.save(General.IMAGE_ID_1,""+object.get(General.ID).getAsInt());
                                    }else if (Preferences.get(General.SELECTED_IMAGE_BOX).equalsIgnoreCase("second")) {
                                        Preferences.save(General.IMAGE_ID_2,""+object.get(General.ID).getAsInt());
                                    }
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
                    ShowToast.toast(getContext().getResources().getString(R.string.upload_successful), getContext());
                    setAttachments(position, path);
                    break;
                case 2:
                    Toast.makeText(mainActivity, "" + getContext().getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, getContext().getResources().getString(R.string.failed), getContext());
                    break;
                case 11:
                    Toast.makeText(mainActivity, "" + getContext().getResources().getString(R.string.internal_error_occurred), Toast.LENGTH_SHORT).show();
                    //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, getContext().getResources().getString(R.string.internal_error_occurred), getContext());
                    break;
                case 12:
                    Toast.makeText(mainActivity, "" + getContext().getResources().getString(R.string.network_error_occurred), Toast.LENGTH_SHORT).show();
                    //ShowSnack.viewWarning(imageViewSupportFeedbackAttachmentOne, getContext().getResources().getString(R.string.network_error_occurred), getContext());
                    break;
                default:
                    break;
            }
        }
    }

    private void setAttachments(int position, String path) {
        if (Preferences.get(General.SELECTED_IMAGE_BOX).equalsIgnoreCase("first")) {
            //imageViewSupportFeedbackAttachmentOneCancel.setVisibility(View.VISIBLE);

            //Monika M 2/6/18- Glide upgradation 3.7.0 to 4.1.1
            iv_select_image_btn_1.setVisibility(View.GONE);
            roundedImageView.setEnabled(false);
            roundedImageView.setClickable(false);
            iv_close_icon_1.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(path)
                    .transition(withCrossFade())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_add)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) //.RESULT
                    .transition(withCrossFade())
                    .into(roundedImageView);
        } else if (Preferences.get(General.SELECTED_IMAGE_BOX).equalsIgnoreCase("second")) {
            iv_select_image_btn_2.setVisibility(View.GONE);
            roundedImageView2.setEnabled(false);
            roundedImageView2.setClickable(false);
            iv_close_icon_2.setVisibility(View.VISIBLE);
            //imageViewSupportFeedbackAttachmentTwoCancel.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(path)
                    .transition(withCrossFade())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_add)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) //.RESULT
                    .into(roundedImageView2);
        }
    }


    // background call to remove uploaded file
    @SuppressLint("StaticFieldLeak")
    private class RemoveAttachment extends AsyncTask<Void, Void, Void> {
        final int position;
        final String id;

        RemoveAttachment(int position, String id) {
            this.position = position;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, String> keyMap = KeyMaker_.getKey();
            RequestBody getBody = new FormBody.Builder()
                    .add(General.KEY, keyMap.get(General.KEY))
                    .add(General.TOKEN, keyMap.get(General.TOKEN))
                    .add(General.ACTION, Actions_.DELETE)
                    .add(General.ID, id)
                    .build();
            try {
                MakeCall.postNormal(Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, "") + Urls_.MOBILE_UPLOADER, getBody, TAG, getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (position == 0) {
                attachmentArrayList.clear();
            }
            if (position == 1) {
                ShowToast.toast(getContext().getResources().getString(R.string.removed), getContext());
                roundedImageView.setImageDrawable(null);
                iv_close_icon_1.setVisibility(View.GONE);
                iv_select_image_btn_1.setVisibility(View.VISIBLE);
                removeAttachmentId(1);
                Preferences.removeKey(General.IMAGE_ID_1);
            }
            if (position == 2) {
                ShowToast.toast(getContext().getResources().getString(R.string.removed), getContext());
                roundedImageView2.setImageDrawable(null);
                iv_close_icon_2.setVisibility(View.GONE);
                iv_select_image_btn_2.setVisibility(View.VISIBLE);
                removeAttachmentId(2);
                Preferences.removeKey(General.IMAGE_ID_2);
            }
        }
    }

    // remove attachment from list based on position
    private void removeAttachmentId(int position) {
        int i = 0;
        if (attachmentArrayList.size() > 0) {
            for (WallAttachment_ wallAttachment_ : attachmentArrayList) {
                if (wallAttachment_.getPosition() == position) {
                    attachmentArrayList.remove(i);
                    break;
                }
                i++;
            }
        }
    }
}