package com.example.school.team_care;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTeamDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTeamDetails extends Fragment {
    @BindView(R.id.tv_team_name)
    TextView tv_team_name;

    @BindView(R.id.vw_view_all)
    View vw_view_all;

    @BindView(R.id.iv_user_profile)
    CircleImageView iv_user_profile;

    //main user which is in center
    @BindView(R.id.iv_user_main)
    CircleImageView iv_user_main;

    @BindView(R.id.iv_status_main)
    CircleImageView iv_status_main;

    @BindView(R.id.tv_user_name_main)
    TextView tv_user_name_main;

    @BindView(R.id.tv_role_main)
    TextView tv_role_main;

    //user one
    @BindView(R.id.cl_user_1)
    ConstraintLayout cl_user_1;

    @BindView(R.id.iv_user_1)
    CircleImageView iv_user_1;

    @BindView(R.id.iv_status_1)
    CircleImageView iv_status_1;

    @BindView(R.id.tv_user_name_1)
    TextView tv_user_name_1;

    @BindView(R.id.tv_role_1)
    TextView tv_role_1;

    //user two
    @BindView(R.id.cl_user_2)
    ConstraintLayout cl_user_2;

    @BindView(R.id.iv_user_2)
    CircleImageView iv_user_2;

    @BindView(R.id.iv_status_2)
    CircleImageView iv_status_2;

    @BindView(R.id.tv_user_name_2)
    TextView tv_user_name_2;

    @BindView(R.id.tv_role_2)
    TextView tv_role_2;

    //user three
    @BindView(R.id.cl_user_3)
    ConstraintLayout cl_user_3;

    @BindView(R.id.iv_user_3)
    CircleImageView iv_user_3;

    @BindView(R.id.iv_status_3)
    CircleImageView iv_status_3;

    @BindView(R.id.tv_user_name_3)
    TextView tv_user_name_3;

    @BindView(R.id.tv_role_3)
    TextView tv_role_3;

    //user four
    @BindView(R.id.cl_user_4)
    ConstraintLayout cl_user_4;

    @BindView(R.id.iv_user_4)
    CircleImageView iv_user_4;

    @BindView(R.id.iv_status_4)
    CircleImageView iv_status_4;

    @BindView(R.id.tv_user_name_4)
    TextView tv_user_name_4;

    @BindView(R.id.tv_role_4)
    TextView tv_role_4;

    //user five
    @BindView(R.id.cl_user_5)
    ConstraintLayout cl_user_5;

    @BindView(R.id.iv_user_5)
    CircleImageView iv_user_5;

    @BindView(R.id.iv_status_5)
    CircleImageView iv_status_5;

    @BindView(R.id.tv_user_name_5)
    TextView tv_user_name_5;

    @BindView(R.id.tv_role_5)
    TextView tv_role_5;

    //user six
    @BindView(R.id.cl_user_6)
    ConstraintLayout cl_user_6;

    @BindView(R.id.iv_user_6)
    CircleImageView iv_user_6;

    @BindView(R.id.iv_status_6)
    CircleImageView iv_status_6;

    @BindView(R.id.tv_user_name_6)
    TextView tv_user_name_6;

    @BindView(R.id.tv_role_6)
    TextView tv_role_6;

    //bottom seven plus member card view
    @BindView(R.id.cv_bottom_seven_plus_panel)
    CardView cv_bottom_seven_plus_panel;

   @BindView(R.id.iv_user1)
    CircleImageView iv_user1;

 @BindView(R.id.iv_user2)
    CircleImageView iv_user2;

 @BindView(R.id.iv_user3)
    CircleImageView iv_user3;

 @BindView(R.id.iv_user4)
    CircleImageView iv_user4;


    MainActivity mainActivity;

    ModelTeamListResponse teamData;
    private static final String TAG = "FragmentTeamDetails";
    private ArrayList<CometChatTeamMembers_> cometChatTeamMemberListMain = new ArrayList<>();
    private ArrayList<CometChatTeamMembers_> cometChatTeamMemberList= new ArrayList<>();
    public FragmentTeamDetails() {

    }


    public static FragmentTeamDetails newInstance(String param1, String param2) {
        FragmentTeamDetails fragment = new FragmentTeamDetails();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(General.TEAM_DATA)) {
                teamData = getArguments().getParcelable(General.TEAM_DATA);
                Log.i(TAG, "onCreate: Team name " + teamData.getAllTeams().get(0).getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_details, container, false);
        ButterKnife.bind(this, view);
        hideCards();
        getGroupMemberListWithRales(teamData.getAllTeams().get(0).getGroupId());
        setDataToMainAndOtherUsers();

        vw_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTeamDetailsListing fragmentTeamDetailsListing=new FragmentTeamDetailsListing();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(General.TEAM_LIST,cometChatTeamMemberList);
                fragmentTeamDetailsListing.setArguments(bundle);

                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.addToBackStack(TAG);
                ft.replace(R.id.main_container,fragmentTeamDetailsListing,"FragmentTeamDetailsListing");
                ft.commit();
            }
        });

        return view;
    }

    private void getGroupMemberListWithRales(String groupId) {
        try {

            ArrayList<CometChatTeamMembers_> teamMemberList = new ArrayList<>();
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put(General.ACTION, Actions_.COMETCHAT);
            requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));
            requestMap.put(General.GROUP_ID, groupId);
            requestMap.put(General.CODE, Preferences.get(General.DOMAIN_CODE));
            requestMap.put(General.ISFORTEAMCHAT, "0");
            String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_USERS;
            RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());

//            ApiService mApiService = ApiClient.getClient(getContext(), Preferences.get(General.DOMAIN) + "/").create(ApiService.class);
            //Call<JsonElement> call = mApiService.fetchTeamData(requestBody);
            APIManager.Companion.getInstance().showProgressDialog(getContext(),false,"Loading...");
            APIManager.Companion.getInstance().mobile_users(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {

                        JsonElement mJsonElement = response.body();
                        Gson gson = new Gson();
                        ModelCometchatTeamListResponse teamResponse = gson.fromJson(mJsonElement.toString(), ModelCometchatTeamListResponse.class);

                        if (teamResponse != null) {
                            if (teamResponse.getCometchat() != null) {
                                cometChatTeamMemberListMain = teamResponse.getCometchat();
                                setDataToCircularUsers();
                            }
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
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }

    private void setDataToMainAndOtherUsers() {
        tv_team_name.setText(teamData.getAllTeams().get(0).getName());
        if (Preferences.get(General.IMAGE) != null) {
            Glide.with(getContext())
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_profile);

            Glide.with(getContext())
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_main);
        }

        tv_user_name_main.setText(Preferences.get(General.NAME));
        tv_role_main.setText(Preferences.get(General.ROLE));


    }


    private void setDataToCircularUsers() {
        /*removing adult from list because we wanted to show adult at bottom*/
        for (CometChatTeamMembers_ item : cometChatTeamMemberListMain){
            if (!item.getRole_id().equals("31")) {
                cometChatTeamMemberList.add(item);
            }
        }


        // showing and hiding  bottom panel for 7 plus users and setting images as well
        if (teamData.getAllTeams().get(0).getMembers() > 6) {
            cv_bottom_seven_plus_panel.setVisibility(View.VISIBLE);
            showImagesInBottomView();
        } else {
            cv_bottom_seven_plus_panel.setVisibility(View.GONE);
        }


        if (teamData.getAllTeams().get(0).getMembersList().size() ==1 ) {
            cl_user_1.setVisibility(View.VISIBLE);
            setFirstUser(cometChatTeamMemberList.get(0));
        }else if (cometChatTeamMemberList.size() == 2) {
            /*When we have only 2 users in list then we have to show top one and
             *bottom one user in circle so we will show 1st and 4th user because it should be look good*/
            cl_user_1.setVisibility(View.VISIBLE);
            cl_user_4.setVisibility(View.VISIBLE);

            setFirstUser(cometChatTeamMemberList.get(0));
            setForthUser(cometChatTeamMemberList.get(1));
        }else if (cometChatTeamMemberList.size() == 3) {

            cl_user_1.setVisibility(View.VISIBLE);
            cl_user_3.setVisibility(View.VISIBLE);
            cl_user_5.setVisibility(View.VISIBLE);

            setFirstUser(cometChatTeamMemberList.get(0));
            setThirdUser(cometChatTeamMemberList.get(1));
            setFifthUser(cometChatTeamMemberList.get(2));
        }else if (cometChatTeamMemberList.size() == 4) {
            /*When we have only 2 users in list then we have to show top one and
             *bottom one user in circle so we will show 1st and 4th user because it should be look good*/
            cl_user_2.setVisibility(View.VISIBLE);
            cl_user_3.setVisibility(View.VISIBLE);
            cl_user_5.setVisibility(View.VISIBLE);
            cl_user_6.setVisibility(View.VISIBLE);


            setSecondUser(cometChatTeamMemberList.get(0));
            setThirdUser(cometChatTeamMemberList.get(1));
            setFifthUser(cometChatTeamMemberList.get(3));
            setSixthUser(cometChatTeamMemberList.get(4));

        }else if (cometChatTeamMemberList.size() == 5) {
            /*When we have only 2 users in list then we have to show top one and
             *bottom one user in circle so we will show 1st and 4th user because it should be look good*/
            cl_user_1.setVisibility(View.VISIBLE);
            cl_user_2.setVisibility(View.VISIBLE);
            cl_user_3.setVisibility(View.VISIBLE);
            cl_user_5.setVisibility(View.VISIBLE);
            cl_user_4.setVisibility(View.VISIBLE);


            setFirstUser(cometChatTeamMemberList.get(0));
            setSecondUser(cometChatTeamMemberList.get(1));
            setThirdUser(cometChatTeamMemberList.get(2));
            setFifthUser(cometChatTeamMemberList.get(3));
            setForthUser(cometChatTeamMemberList.get(4));

        }else if (cometChatTeamMemberList.size() >= 6) {
            /*When we have only 2 users in list then we have to show top one and
             *bottom one user in circle so we will show 1st and 4th user because it should be look good*/
            cl_user_1.setVisibility(View.VISIBLE);
            cl_user_2.setVisibility(View.VISIBLE);
            cl_user_3.setVisibility(View.VISIBLE);
            cl_user_5.setVisibility(View.VISIBLE);
            cl_user_4.setVisibility(View.VISIBLE);
            cl_user_6.setVisibility(View.VISIBLE);


            setFirstUser(cometChatTeamMemberList.get(0));
            setSecondUser(cometChatTeamMemberList.get(1));
            setThirdUser(cometChatTeamMemberList.get(2));
            setFifthUser(cometChatTeamMemberList.get(3));
            setForthUser(cometChatTeamMemberList.get(4));
            setSixthUser(cometChatTeamMemberList.get(5));
        }
    }

    public void setFirstUser(CometChatTeamMembers_ cometChatTeamMembers_) {

        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_1);
        }

        tv_role_1.setText(cometChatTeamMembers_.getRole());
        tv_user_name_1.setText(cometChatTeamMembers_.getName());
    }


    private void setSecondUser(CometChatTeamMembers_ cometChatTeamMembers_) {
        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_2);
        }

        tv_role_2.setText(cometChatTeamMembers_.getRole());
        tv_user_name_2.setText(cometChatTeamMembers_.getName());
    }

    private void setThirdUser(CometChatTeamMembers_ cometChatTeamMembers_) {
        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_3);
        }

        tv_role_3.setText(cometChatTeamMembers_.getRole());
        tv_user_name_3.setText(cometChatTeamMembers_.getName());
    }



    private void setForthUser(CometChatTeamMembers_ cometChatTeamMembers_) {
        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_4);
        }

        tv_role_4.setText(cometChatTeamMembers_.getRole());
        tv_user_name_4.setText(cometChatTeamMembers_.getName());
    }

    private void setFifthUser(CometChatTeamMembers_ cometChatTeamMembers_) {
        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_5);
        }

        tv_role_5.setText(cometChatTeamMembers_.getRole());
        tv_user_name_5.setText(cometChatTeamMembers_.getName());
    }


    private void setSixthUser(CometChatTeamMembers_ cometChatTeamMembers_) {
        if (cometChatTeamMembers_.getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMembers_.getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_6);
        }

        tv_role_6.setText(cometChatTeamMembers_.getRole());
        tv_user_name_6.setText(cometChatTeamMembers_.getName());
    }





    /*here we are hiding cards*/
    private void hideCards() {
        cl_user_1.setVisibility(View.INVISIBLE);
        cl_user_2.setVisibility(View.INVISIBLE);
        cl_user_3.setVisibility(View.INVISIBLE);
        cl_user_4.setVisibility(View.INVISIBLE);
        cl_user_5.setVisibility(View.INVISIBLE);
        cl_user_6.setVisibility(View.INVISIBLE);


    }

    private void showImagesInBottomView() {
        if (cometChatTeamMemberList.get(0).getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(0).getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user1);
        }
        if (cometChatTeamMemberList.get(1).getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(1).getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user2);
        }
        if (cometChatTeamMemberList.get(2).getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(2).getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user3);
        }

        if (cometChatTeamMemberList.get(3).getA() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(3).getA())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user4);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity){
            mainActivity=(MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.Personal_Support_Team));
            mainActivity.changeDrawerIcon(false);
        }
    }



}