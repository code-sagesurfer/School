package com.example.school.resources


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.school.R
import com.example.school.login.LoginActivity


import com.google.gson.JsonElement


import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


class APIManager {

    private val SHARED_PREF = "ah_firebase"
    private var context: Context? = null
    private var dialog: ProgressDialog? = null
    private var mApiService: Api? = null

    init {

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(Preferences.get(General.DOMAIN))
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()

        mApiService = retrofit.create(Api::class.java)

    }


    companion object {

        private var instance: APIManager? = null

        fun getInstance(): APIManager {
            if (instance == null) {
                instance = APIManager()
            }
            return instance as APIManager
        }


        fun getClient(context: Context): Api {
            var retrofit: Retrofit? = null
            if (retrofit == null) {
                val interceptor = HttpLoggingInterceptor()
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

//                val client =
//                    OkHttpClient.Builder().addInterceptor(interceptor)
//                        .addInterceptor(ChuckInterceptor(context)).build()

                val client =
                    OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .addInterceptor(interceptor).build()


                retrofit = Retrofit.Builder()
                    .baseUrl(Preferences.get(General.DOMAIN))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit!!.create(
                Api::class.java
            )
        }
    }


    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        return OkHttpClient().newBuilder().addInterceptor { chain ->

            val originalRequest = chain.request()


            val builder = originalRequest.newBuilder().header(
                "Content-Type",
                Credentials.basic("application", "json")
            )

            val newRequest = builder.build()

            //chain.proceed(newRequest)

            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 401) {
                doUserLogout(context)
            }
            Log.i("data", "response.code :" + response.code);

            response

        }.addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }


    fun doUserLogout(context: Context?) {
        dismissProgressDialog()

        /*UiThreadUtil.runOnUiThread(Runnable {
            kotlin.run {
                Toast.makeText(context, "please contact administrator.", Toast.LENGTH_SHORT)
                    .show()
                val newintent = Intent(context, LoginActivity::class.java)
                newintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(newintent)
            }
        });*/


        Thread(Runnable {
            kotlin.run { Toast.makeText(context, "please contact administrator.", Toast.LENGTH_SHORT)
                .show()
                val newintent = Intent(context, LoginActivity::class.java)
                newintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(newintent) }
        }).start();
    }


    fun updateAPi() {

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(Preferences.get(General.DOMAIN))
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()

        mApiService = retrofit.create(Api::class.java)
    }

    fun updateDesignAPi() {

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://designstaging.sagesurfer.com/phase3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()

        mApiService = retrofit.create(Api::class.java)
    }

    fun updateAPiDoxy() {

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://api.doxy.me/api/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()

        mApiService = retrofit.create(Api::class.java)
    }

    @Synchronized
    fun showProgressDialog(mContext: Context?, showFullScreen: Boolean, message: String?) {
        try {
            context = mContext;

            if (dialog == null) {
                dialog = ProgressDialog(mContext)
                dialog!!.setCancelable(false)
            }



            if (message != null && !message.isEmpty()) {
                dialog!!.setMessage(message)
            } else {
                dialog!!.setMessage("")
            }
            if (!dialog!!.isShowing) {
                dialog!!.show()
                if (showFullScreen) {
                    if (dialog!!.window != null) {
                        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog!!.setContentView(R.layout.progress_dialog)
                    }
                }
            }
        } catch (e: WindowManager.BadTokenException) {
            Log.e("TAG", "" + e.message)
        }
    }

    @Synchronized
    fun dismissProgressDialog() {
        try {

            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
        } catch (e: Exception) {

        }
    }

    private fun getMediaTypePlainText(): MediaType? {
        return "text/plain".toMediaTypeOrNull()
    }


    fun showToast(message: String?) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        //toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showToast(message: String?, mview: Context?) {
        context = mview
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        //toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    fun getMobileCommunity(@Body body: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.getMobileCommunity(body)
        call.enqueue(aCallback)
    }



    fun mobile_wel_come(@Body body: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_wel_come(body)
        call.enqueue(aCallback)
    }

    fun mobile_selfcare_new(@Body body: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.getMobile_selfcare_new(body)
        call.enqueue(aCallback)
    }

    fun getRewardsData(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.getRewardsData(params)
        call.enqueue(aCallback)
    }

    fun getmobile_dashboard(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.getmobile_dashboard(params)
        call.enqueue(aCallback)
    }

    fun getMobileCrisis(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.getMobileCrisis(params)
        call.enqueue(aCallback)
    }

    fun mobile_youth_dashboard(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()

        val call: Call<JsonElement> = mApiService!!.mobile_youth_dashboard(params)
        call.enqueue(aCallback)
    }

    fun fetchselfcare(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetchselfcare(params)
        call.enqueue(aCallback)
    }


    fun mobile_templates(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_templates(params)
        call.enqueue(aCallback)
    }




    fun mobile_self_goal(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_self_goal(params)
        call.enqueue(aCallback)
    }

    fun mobile_sos_counter(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_sos_counter(params)
        call.enqueue(aCallback)
    }

    fun mobile_postcard(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_postcard(params)
        call.enqueue(aCallback)
    }

    fun mobile_mobile_cometchat(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_mobile_cometchat(params)
        call.enqueue(aCallback)
    }

    fun mobile_mhaw_appointment(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_mobile_mhaw_appointment(params)
        call.enqueue(aCallback)
    }

    fun mobile_mood_dashboard(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_mood_dashboard(params)
        call.enqueue(aCallback)
    }

    fun get_gratitude_list(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_gratitude_list(params)
        call.enqueue(aCallback)
    }

    fun get_team_list(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_team_list(params)
        call.enqueue(aCallback)
    }

    fun get_mobile_fms(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_mobile_fms(params)
        call.enqueue(aCallback)
    }

    fun get_mobile_doxy(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_mobile_doxy(params)
        call.enqueue(aCallback)
    }

    fun mobile_invitation_operations(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_invitation_operations(params)
        call.enqueue(aCallback)
    }

    fun mobile_medication(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_medication(params)
        call.enqueue(aCallback)
    }

    fun mobile_addiction_track(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_addiction_track(params)
        call.enqueue(aCallback)
    }

    fun get_instances(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_instances(params)
        call.enqueue(aCallback)
    }

    fun get_mobile_consent(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.get_mobile_consent(params)
        call.enqueue(aCallback)
    }


    fun upload_gratitude_with_file(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.upload_gratitude_with_file(params)
        call.enqueue(aCallback)
    }

    fun UploadGratitude(
        file_attachment: MultipartBody.Part,
        action: String,
        title: String,
        category: String,
        userid: String,
        desc: String,
        shared_user_id: String,
        debug: String, aCallback: Callback<JsonElement>
    ) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.UploadGratitude(
            file_attachment,
            action,
            title,
            category,
            userid,
            desc,
            shared_user_id,
            debug
        )
        call.enqueue(aCallback)
    }




    fun mobile_notes(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_notes(params)
        call.enqueue(aCallback)
    }

    fun mobile_team_contacts(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_team_contacts(params)
        call.enqueue(aCallback)
    }

    fun mobile_wall_feeds(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_wall_feeds(params)
        call.enqueue(aCallback)
    }

    fun mobile_mobile_profile(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_mobile_profile(params)
        call.enqueue(aCallback)
    }

    fun mobile_uploader_(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_self_care(params)
        call.enqueue(aCallback)
    }


    fun mobile_self_care(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_self_care(params)
        call.enqueue(aCallback)
    }

    fun mobile_user_settings(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_user_settings(params)
        call.enqueue(aCallback)
    }

    fun mobile_from_builder(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_form_builder(params)
        call.enqueue(aCallback)
    }

    fun mobile_youth_operations_new(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_youth_operations_new(params)
        call.enqueue(aCallback)
    }

    fun mobile_cometchat(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_cometchat(params)
        call.enqueue(aCallback)
    }

    fun mobile_calendar(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_calendar(params)
        call.enqueue(aCallback)
    }

    fun mobile_token(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_token(params)
        call.enqueue(aCallback)
    }

    fun mobile_werhope_team_operations(
        @Body params: RequestBody,
        aCallback: Callback<JsonElement>
    ) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_werhope_team_operations(params)
        call.enqueue(aCallback)
    }

    fun mobile_teams(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_teams(params)
        call.enqueue(aCallback)
    }

    fun mobile_firebase_register(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_firebase_register(params)
        call.enqueue(aCallback)
    }

    fun fetchCaseloadData(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetchCaseloadData(params)
        call.enqueue(aCallback)
    }


    fun fetch_mobile_team_all_data(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetch_mobile_team_all_data(params)
        call.enqueue(aCallback)
    }

    fun fetch_mobile_team_data(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetch_mobile_team_data(params)
        call.enqueue(aCallback)
    }

    fun mobile_gallery_operations(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_gallery_operations(params)
        call.enqueue(aCallback)
    }


    fun fetch_fms_data(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetch_fms_data(params)
        call.enqueue(aCallback)
    }


    fun mobile_sos_new(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_sos_new(params)
        call.enqueue(aCallback)
    }


    fun mobilesosnew(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_sos_new(params)
        call.enqueue(aCallback)
    }


    fun mobile_sos_fetch(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_sos_fetch(params)
        call.enqueue(aCallback)
    }

    fun mobile_sos_read(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_sos_read(params)
        call.enqueue(aCallback)
    }




    fun fetchTeamsData(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.fetchTeamsData(params)
        call.enqueue(aCallback)
    }

    fun mobile_users(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_users(params)
        call.enqueue(aCallback)
    }

    fun mobile_alerts(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_alerts(params)
        call.enqueue(aCallback)
    }

    fun mobile_tasklist(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_tasklist(params)
        call.enqueue(aCallback)
    }

    fun mobile_day_planner(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_day_planner(params)
        call.enqueue(aCallback)
    }



    fun mobile_my_story(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_my_story(params)
        call.enqueue(aCallback)
    }

    fun mobile_mood(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_mood(params)
        call.enqueue(aCallback)
    }

    fun mobile_quotes(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_quotes(params)
        call.enqueue(aCallback)
    }

    fun mobile_uplift(@Body params: RequestBody, aCallback: Callback<JsonElement>) {
        updateAPi()
        val call: Call<JsonElement> = mApiService!!.mobile_uplift(params)
        call.enqueue(aCallback)
    }


    fun getToken(context: Context): String? {
        val token: String
        val pref = context.getSharedPreferences(APIManager.instance?.SHARED_PREF, 0)
        token = pref.getString("regId", "")!!
        return token
    }



    @Synchronized
    fun showAlertDialog(
        aContext: Context, setNegativeButton: Boolean,
        message: String?, positiveText: String?, negativeText: String?,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(aContext)
        builder.setTitle(aContext.resources.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, positiveButtonListener)
        if (setNegativeButton) {
            builder.setNegativeButton(negativeText, negativeButtonListener)
        }
        builder.show()
    }


    /*"action=get_list_month_year, userid=//login user id,
    month_year=//Add here month and year like this 12-2021
    tz=//time zone" */
}