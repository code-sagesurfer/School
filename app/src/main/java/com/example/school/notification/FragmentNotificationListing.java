package com.example.school.notification;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.school.R;
import com.example.school.assessment.pendingforms.Forms_;
import com.example.school.home.main.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNotificationListing extends Fragment {
    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;

    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;

    boolean isLoading = false;
    private boolean firstTimeLoading = true;
    private ArrayList<Forms_> formsArrayList;
    private int minSize = 0;
    private int maxSize = 50;
    private static final String TAG = "FragmentNotificationLis";
    private AdapterNotification notificationAdapter;
    private ArrayList<Notification> notificationList = new ArrayList<>();
    private ArrayList<Notification> notificationFilterList = new ArrayList<>();
    MainActivity mainActivity;
    public FragmentNotificationListing() {

    }


    public static FragmentNotificationListing newInstance(String param1, String param2) {
        FragmentNotificationListing fragment = new FragmentNotificationListing();
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
        View view = inflater.inflate(R.layout.fragment_notification_listing, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_notification.setLayoutManager(mLayoutManager);
        rv_notification.setItemAnimator(new DefaultItemAnimator());

        rv_notification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == notificationFilterList.size() - 1) {
                        fetchNotificationNew(minSize, maxSize);
                        isLoading = true;
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchNotificationNew(0, 50);

        if (getContext() instanceof MainActivity){
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText("Notification");

            mainActivity.toggleBellIcon(false);
        }
    }

    private void fetchNotificationNew(int min, int max) {
        try {
            APIManager.Companion.getInstance().showProgressDialog(getContext(), true, "Loading....");

            Log.i(TAG, "fetchNotificationNew: onScroll  min " + min + " max " + max);
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put(General.ACTION, Actions_.NOTIFICATION);
            requestMap.put(General.MIN, "" + min);
            requestMap.put(General.MAX, "" + max);
            String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_YOUTH_OPERATIONS_URL;

            Log.e("fetchNotificationNew id", Preferences.get(General.USER_ID));
            //ApiService mApiService = ApiClient.getClient(getContext(), Preferences.get(General.DOMAIN) + "/").create(ApiService.class);
            RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
            //Call<JsonElement> call = mApiService.fetchNotifications(requestBody);
            APIManager.Companion.getInstance().mobile_youth_operations_new(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    try {
                        APIManager.Companion.getInstance().dismissProgressDialog();

                        AppLog.i(TAG, "fetchTeamDetailsNew onResponse: " + response.body().toString());
                        JsonElement mJsonElement = response.body();
                        Gson gson = new Gson();


                        ModelNotificationResponse modelNotificationResponse = gson.fromJson(mJsonElement.toString(), ModelNotificationResponse.class);
                        if (response != null) {
                            if (modelNotificationResponse.getNotifications().get(0).getStatus() == 1) {
                                minSize = maxSize + 1;
                                maxSize = maxSize + 50;
                                notificationList = modelNotificationResponse.getNotifications();
                                isLoading = false;
                                for (Notification item : notificationList) {

                                    if (item.getIs_delete() == 0 || item.getIs_delete() == 1) {
                                        notificationFilterList.add(item);
                                    }
                                }

                                if (notificationFilterList.size() > 0) {
                                    if ((notificationFilterList.get(0).getStatus() == 1 && notificationFilterList.get(0).getType() != null)
                                            || notificationFilterList.get(0).getRef_type() != null) {
                                        if (firstTimeLoading) {

                                            Log.i(TAG, "fetchTeamDetailsNew: fetchTeamDetailsNew firstTimeLoading");
                                            notificationAdapter = new AdapterNotification(getActivity(), notificationFilterList, FragmentNotificationListing.this);
                                            rv_notification.setAdapter(notificationAdapter);
                                            firstTimeLoading = false;
                                        } else {
                                            Log.i(TAG, "fetchTeamDetailsNew: fetchTeamDetails");
                                            notificationAdapter.addData(notificationList);
                                        }
                                        /* if(notificationList.size()<max){
                                               scrollingStatus=2;
                                          }*/
                                    }
                                } else {

                                }
                            } else {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    Log.i(TAG, "onFailure: fetchTeamDetailsNew " + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Notification notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_notification_item_clicked, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        CircleImageView iv_user_profile = view.findViewById(R.id.iv_user_profile);
        TextView tv_team_list_user_name = view.findViewById(R.id.tv_team_list_user_name);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_greeting = view.findViewById(R.id.tv_greeting);
        TextView tv_description = view.findViewById(R.id.tv_description);

        Glide.with(getContext())
                .load(notification.getProfile())
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(getContext().getDrawable(R.drawable.ic_user_male))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(iv_user_profile);
        String time = getDate(notification.getTimestamp());

        tv_date.setText(time);
        tv_greeting.setText("Dear " + Preferences.get(General.NAME) + ",");
        if (notification.getDescription() != null) {
            tv_description.setText(notification.getDescription());
        }else {
            tv_description.setText(notification.getDescription1());
        }
        tv_team_list_user_name.setText(notification.getType() + " added by " + notification.getAdded_by());


        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000);
        String date = DateFormat.format("MMM dd, yyyy | hh:mm a", cal).toString();
        return date;
    }
}