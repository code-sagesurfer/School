package com.sagesurfer.school.notification;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sagesurfer.school.R;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {
    private static final String TAG = "AdapterNotification";
    private ArrayList<Notification> notificationList = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    public AdapterNotification(Context context, ArrayList<Notification> notificationList,Fragment fragment) {
        this.notificationList = notificationList;
        this.context = context;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_list_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        String time = getDate(notification.getTimestamp());
        holder.notification_date.setText(time);
        Glide.with(context)
                .load(notification.getProfile())
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(context.getDrawable(R.drawable.ic_user_male))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.iv_user_profile);

        setText(notification, holder);
    }

    private void setText(Notification notification, NotificationViewHolder holder) {

        //holder.d.setVisibility(View.VISIBLE);
        String message;
        //AppLog.i(TAG, "setText: notification type " + NotificationTypeDetector.getType(notification.getType()));

        try {
            if (notification.getType() != null) {
                switch (NotificationTypeDetector.getType(notification.getType())) {
                    case 1103:
                        //message = notification.getAdded_by() + " Share addiction in " + notification.getGroup_name();
                        holder.notification_title.setText(notification.getTitle());
                        ////holder.descriptionText.setText(notification.getDescription());
                        ////holder.typeText.setVisibility(View.GONE);
                        break;
                    case 103:
                        //message = notification.getAdded_by() + " Share addiction in " + notification.getGroup_name();
                        holder.notification_title.setText(notification.getAdded_by() + " " + context.getResources().getString(R.string.addictition_notification_msg));
                        ////holder.descriptionText.setText(notification.getDescription());
                       // //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 101:
                        message = notification.getAdded_by() + " added new message in " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                       // //holder.descriptionText.setText(notification.getDescription());
                        ////holder.typeText.setVisibility(View.GONE);
                        break;
                    case 1:
                        message = notification.getAdded_by() + " added new message in " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        ////holder.descriptionText.setText(notification.getDescription());
                       // //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 2:
                        message = notification.getAdded_by() + " posted announcement in " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 3:
                        message = notification.getAdded_by() + " uploaded file in " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 4:
                        message = "New Member " + " join team " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 5:
                        message = "Member " + " left team " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 6:
                        message = notification.getAdded_by() + " posted blog for " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 7:
                        if (Preferences.get(General.DOMAIN_CODE).equalsIgnoreCase("sage015")) {
                            message = notification.getAdded_by() + " posted team discussion in " + notification.getGroup_name();
                        } else {
                            message = notification.getAdded_by() + " posted team talk in " + notification.getGroup_name();
                        }
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 8:
                        message = notification.getAdded_by() + " uploaded new video in " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 9:
                        message = notification.getAdded_by() + " added poll in team " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 10:
                        message = notification.getAdded_by() + " invited to join team " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 11:
                        message = notification.getAdded_by() + " sent friend request";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 12: //tasklist
                        if (notification.getGroup_name() == null || notification.getGroup_name().length() == 0) {
                            message = notification.getAdded_by() + " new task added ";
                        } else {
                            message = notification.getAdded_by() + " added new task in " + notification.getGroup_name();
                        }
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 17://upload_selfcare
                        message = notification.getTitle(); //title with uploaded by name
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 18://comment_selfcare
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 19://decline_selfcare
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 20://approve_selfcare
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 21://event or calendar
                        if (notification.getGroup_name() == null || notification.getGroup_name().length() == 0) {
                            message = notification.getAdded_by() + " added new event";
                        } else {
                            message = notification.getAdded_by() + " added new event in " + notification.getGroup_name();
                        }
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 22://selfgoal
                        message = notification.getAdded_by() + " added new selfgoal ";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 23://notes
                        message = notification.getAdded_by() + " submitted note for approval ";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 24://mood
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        /*holder.typeText.setVisibility(View.VISIBLE);
                        if (notification.getMood_name() != null) {
                            if (notification.getMood_name().length() == 0) {
                                holder.typeText.setText("");
                            } else {
                                holder.typeText.setText("Mood: " + Html.fromHtml(notification.getMood_name()));
                            }
                        }*/

                        //holder.descriptionText.setVisibility(View.GONE);
                        break;
                    case 25://accept event or calendar
                        if (notification.getGroup_name() == null || notification.getGroup_name().length() == 0) {
                            message = notification.getAdded_by() + " accepted an event ";
                        } else {
                            message = notification.getAdded_by() + " accepted an event in " + notification.getGroup_name();
                        }
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 26://decline event or calendar
                        if (notification.getGroup_name() == null || notification.getGroup_name().length() == 0) {
                            message = notification.getAdded_by() + " rejected an event ";
                        } else {
                            message = notification.getAdded_by() + " rejected an event in " + notification.getGroup_name();
                        }
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 27://approved notes
                        message = notification.getAdded_by() + " approved a note ";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 28://rejected notes
                        message = notification.getAdded_by() + " rejected a note ";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 29://updated notes
                        message = notification.getAdded_by() + " updated note for approval ";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 34://Assessment
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 30://team invitation
                        message = notification.getAdded_by() + " invited to join team " + notification.getGroup_name();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 37://team_request_decline
                    case 38://team_request_accept

                        try {
                            message = notification.getTitle();
                            holder.notification_title.setText(Html.fromHtml(message));
                            //holder.descriptionText.setVisibility(View.GONE);
                           /* holder.typeText.setVisibility(View.VISIBLE);
                            if (notification.getGroup_name() != null) {
                                if (notification.getGroup_name().length() == 0) {
                                    holder.typeText.setText("");
                                } else {
                                    holder.typeText.setText("Team: " + Html.fromHtml(notification.getGroup_name()));
                                }
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;


                    case 39://friend_request_decline
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                       /* holder.typeText.setVisibility(View.VISIBLE);
                        if (notification.getGroup_name().length() == 0) {
                            holder.typeText.setText("");
                        } else {
                            holder.typeText.setText("Friend: " + Html.fromHtml(notification.getGroup_name()));
                        }*/
                        break;

                    case 40://friend_request_accept
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                       /* holder.typeText.setVisibility(View.VISIBLE);
                        if (notification.getGroup_name().length() == 0) {
                            holder.typeText.setText("");
                        } else {
                            holder.typeText.setText("Friend: " + Html.fromHtml(notification.getGroup_name()));
                        }*/
                        break;

                    case 41://share_selfcare
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 42://add_journal
                        //                message = notification.getAdded_by() + " added new journal ";
                        message = "Note has been added by " + notification.getAdded_by();
                        holder.notification_title.setText(Html.fromHtml(message));
                        if (notification.getIs_delete() == 0) {
                            //holder.descriptionText.setVisibility(View.VISIBLE);
                            // holder.descriptionText.setText("Title: "+ notification.getDescription());
                            //holder.descriptionText.setText(notification.getDescription());
                        } else {
                            //holder.descriptionText.setVisibility(View.GONE);
                        }
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 43://update_journal
                        //                message = notification.getAdded_by() + " update journal ";
                        message = "Note has been update by " + notification.getAdded_by();
                        holder.notification_title.setText(Html.fromHtml(message));
                        /*if (notification.getIs_delete() == 0) {
                            holder.descriptionText.setVisibility(View.VISIBLE);
                            holder.descriptionText.setText("Title: " + notification.getDescription());
                        } else {
                            //holder.descriptionText.setVisibility(View.GONE);
                        }*/

                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 44://delete_journal
                        //                message = notification.getAdded_by() + " delete journal ";
                        message = "Note has been deleted by " + notification.getAdded_by();
                        holder.notification_title.setText(Html.fromHtml(message));
                        /*if (notification.getIs_delete() == 0) {
                            holder.descriptionText.setVisibility(View.VISIBLE);
                            holder.descriptionText.setText("Title: " + notification.getDescription());
                        } else {
                            //holder.descriptionText.setVisibility(View.GONE);
                        }*/
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 45://assign_goal
                        message = notification.getAdded_by() + " has been added/Updated the goal(s)";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 46://bhs
                        message = notification.getAdded_by() + " has been responded behavioral tracking survey.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 47://assign_student
                        holder.notification_title.setText(Html.fromHtml(notification.getTitle()));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 48://student_reassignment
                        holder.notification_title.setText(Html.fromHtml(notification.getTitle()));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 49://add_leave
                        message = notification.getAdded_by() + " has been added leave.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 50://unset_goal
                        message = notification.getTitle() + " by " + notification.getAdded_by();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 51://progress_note
                        message = notification.getAdded_by() + " has been added progress note.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 52://edit_progress_note
                        message = notification.getAdded_by() + " has been updated  progress note.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 53://delete_progress_note
                        message = notification.getAdded_by() + " has been deleted progress note.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 54://delete_goal
                        message = "Goal has been deleted by " + notification.getAdded_by();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getTitle());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 55://edit_leave
                        message = notification.getTitle();
                        //message = notification.getAdded_by() + " has been edit leave.";
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 56://delete_leave
                        //message = notification.getAdded_by() + " has been delete leave.";
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 57://platform_youth_message
                        message = notification.getTitle_1_other();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText(notification.getDescription1());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 58://add_appointment
                        message = notification.getTitle_1_other();
                        if (message != null) {
                            holder.notification_title.setText(Html.fromHtml(message));
                            //holder.descriptionText.setVisibility(View.GONE);
                            //holder.typeText.setVisibility(View.GONE);
                        }
                        break;

                    case 71://reminder_appointment
                        message = notification.getTitle_1_other();
                        if (message != null) {
                            holder.notification_title.setText(Html.fromHtml(message));
                            //holder.descriptionText.setVisibility(View.GONE);
                            //holder.typeText.setVisibility(View.GONE);
                        }
                        break;

                    case 59://updated_appointment
                        message = notification.getTitle_1_other();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 60://cancel_appointment
                        try {

                            message = notification.getTitle1();
                            holder.notification_title.setText(Html.fromHtml(message));
                            //holder.descriptionText.setVisibility(View.GONE);
                            //holder.typeText.setVisibility(View.GONE);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    case 61://rescheduled_appointment
                        /*changed by rahul maske on 15-07-2021
                         * because of some crashing issue for */
                        message = notification.getTitle_1_other();
                        if (message != null) {
                            holder.notification_title.setText(Html.fromHtml(message));
                            //holder.descriptionText.setVisibility(View.GONE);
                            //holder.typeText.setVisibility(View.GONE);
                        }
                        break;

                    case 62://delete_appointment
                        try {
                            message = notification.getTitle_1_other();
                            holder.notification_title.setText(message);
                            //holder.descriptionText.setVisibility(View.GONE);
                            //holder.typeText.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 63://peer_supervisor_notification
                        message = notification.getTitle_1_other();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText("Team: " + notification.getDescription1());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 64://Sows_notification
                        message = notification.getTitle1();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText("Sows: " + notification.getDescription1());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 65://immunity_survey
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;
                    case 66://submit_one_time_survey
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 67://Selfgoal_due
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 68://dailysurvey_due
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //                holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.descriptionText.setVisibility(View.INVISIBLE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    // case added by kishor k 06/05/2020
                    case 69:// caseload page
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        //                holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 70:// new Cometchat-pro group invitation  // 13-11-2020
                        message = notification.getTitle();
                        holder.notification_title.setText(Html.fromHtml(message));
                        Log.i(TAG, "setText: notification type cometchat_request message " + Html.fromHtml(message));
                        //                holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;

                    case 72://

                        holder.notification_title.setText("Journal has been shared by " + notification.getAdded_by());
                        //holder.descriptionText.setVisibility(View.VISIBLE);
                        //holder.descriptionText.setText("Journal has been shared");
                        break;

                    case 73:
                        holder.notification_title.setText("Journal has been updated by " + notification.getAdded_by());
                        //holder.descriptionText.setVisibility(View.VISIBLE);
                        //holder.descriptionText.setText("Journal has been updated");
                        break;
                    case 74:

                        holder.notification_title.setText("Journal has been added by " + notification.getAdded_by());
                        //holder.descriptionText.setVisibility(View.VISIBLE);
                        //holder.descriptionText.setText("Journal has been addded");
                        break;

                    case 75:
                        holder.notification_title.setText("Journal has been deleted by " + notification.getAdded_by());
                       // holder.descriptionText.setVisibility(View.VISIBLE);
                        //holder.descriptionText.setText("Journal has been deleted");
                        break;

                    case 102:
                        message = notification.getTitle_1_other();
                        //holder.notification_title.setText(message);
                        //                holder.descriptionText.setText("Goal title: " + notification.getDescription());
                        //holder.descriptionText.setVisibility(View.GONE);
                        //holder.typeText.setVisibility(View.GONE);
                        break;


                    default:
                        break;
                }
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("MMM dd, yyyy | hh:mm a", cal).toString();
        return date;
    }

    public void addData(ArrayList<Notification> notificationList2) {
        notificationList.clear();
        notificationList.addAll(notificationList2);
        this.notifyDataSetChanged();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView notification_title, notification_date;
        ImageView btn_delete;
        CircleImageView iv_user_profile;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_date = itemView.findViewById(R.id.notification_date);
            notification_title = itemView.findViewById(R.id.notification_title);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            iv_user_profile = itemView.findViewById(R.id.iv_user_profile);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (fragment instanceof FragmentNotificationListing){
                FragmentNotificationListing fragmentNotificationListing=(FragmentNotificationListing) fragment;
                fragmentNotificationListing.showDialog(notificationList.get(getAbsoluteAdapterPosition()));
            }
        }
    }
}
