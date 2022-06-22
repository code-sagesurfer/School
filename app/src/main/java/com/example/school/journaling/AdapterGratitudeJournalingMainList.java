package com.example.school.journaling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.ModelGratitudeListingResponseData;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.makeramen.roundedimageview.RoundedImageView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterGratitudeJournalingMainList extends RecyclerView.Adapter<AdapterGratitudeJournalingMainList.ViewHolderJournaling> implements Filterable {
    ArrayList<ModelGratitudeListingResponseData> dataArrayList;
    ArrayList<ModelGratitudeListingResponseData> mainArrayList;
    Context mContext;
    Fragment fragment;
    JournalingMainListing journalingMain;
    private static final String TAG = "AdapterGratitudeMainListing";

    public AdapterGratitudeJournalingMainList(ArrayList<ModelGratitudeListingResponseData> dataArrayList, Context mContext, Fragment fragment) {
        this.dataArrayList = dataArrayList;
        this.mContext = mContext;
        this.fragment = fragment;
        mainArrayList = new ArrayList<>(dataArrayList);
        journalingMain = (JournalingMainListing) fragment;
    }

    @NonNull
    @Override
    public ViewHolderJournaling onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_journal_list_item, parent, false);
        return new ViewHolderJournaling(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderJournaling holder, int position) {

        ModelGratitudeListingResponseData model = dataArrayList.get(position);
        holder.tv_gj_list_username.setText(model.getFullname());
        holder.tv_gj_list_date.setText(model.getAddedDate());
        if (model.getLikeCount().equalsIgnoreCase("0")) {
            holder.tv_like_count.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_like_count.setVisibility(View.VISIBLE);
            holder.tv_like_count.setText(model.getLikeCount());
        }

        if (model.getTotalShareCount() == 0) {
            holder.tv_share_count.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_share_count.setVisibility(View.VISIBLE);
            holder.tv_share_count.setText("" + model.getTotalShareCount());
        }

        holder.tv_title.setText(model.getGratituteName());
//        holder.tv_category.setText(model.getGratituteCategory());

        if (model.getGratituteDescription().equalsIgnoreCase("")) {
           // holder.tv_desc_text_Na.setText("N/A");
            //holder.tv_desc_text_Na.setVisibility(View.VISIBLE);
        } else {
            //holder.tv_desc_text_Na.setVisibility(View.GONE);
            holder.tv_description.setText(model.getGratituteDescription());
        }

        holder.main_card.setCardBackgroundColor(Color.parseColor(model.getRandom_bg_color_code()));
        String fileName = model.getFileAttachment();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);

            if (extension.equalsIgnoreCase("docx")) {
                holder.attached_image.setVisibility(View.VISIBLE);
                holder.attached_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vi_doc_file));
            } else if (extension.equalsIgnoreCase("jpg")
                    || extension.equalsIgnoreCase("jpeg")
                    || extension.equalsIgnoreCase("png")
            ) {
                holder.attached_image.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(model.getFileAttachment()).into(holder.attached_image);
            } else if (extension.equalsIgnoreCase("pdf")) {
                holder.attached_image.setVisibility(View.VISIBLE);
                holder.attached_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vi_pdf_file));
            } else if (extension.equalsIgnoreCase("xlsx")
                    || extension.equalsIgnoreCase("xls")
                    || extension.equalsIgnoreCase("xlt")
            ) {
                holder.attached_image.setVisibility(View.VISIBLE);
                holder.attached_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vi_xls_file));
            } else if (extension.equalsIgnoreCase("txt")) {
                holder.attached_image.setVisibility(View.VISIBLE);
                holder.attached_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vi_text_file));
            } else {
                holder.attached_image.setVisibility(View.GONE);
            }
        }

        if (!model.getIsLikeSymbol().equalsIgnoreCase("Like")) {
            holder.animation_like.setMinAndMaxProgress(0.0f, 0.5f);
            holder.animation_like.playAnimation();
            holder.iv_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_gratitude_like_fill_red_heart));
        } else {
            holder.animation_like.setMinAndMaxProgress(0.5f, 1.0f);
            holder.animation_like.playAnimation();
            holder.iv_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_gratitude_unlike_heart));
        }
        //GratitudeJournalingMain gratitudeJournalingMain = (GratitudeJournalingMain) fragment;
        if (model.getProfileImage() != null && model.getProfileImage().length() > 0) {
            Glide.with(mContext).load(model.getProfileImage()).
                    into(holder.iv_user_prodile);
        } else {
            holder.iv_user_prodile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_user_male));
        }

        if (model.getGratituteUserId().equalsIgnoreCase(Preferences.get(General.USER_ID))) {
            holder.iv_more.setVisibility(View.VISIBLE);
        } else {
            holder.iv_more.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelGratitudeListingResponseData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 || constraint.equals("")) {
                AppLog.i(TAG, "performFiltering: main arraylist size" +
                        "" + mainArrayList.size());
                filteredList.addAll(mainArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelGratitudeListingResponseData item : dataArrayList) {
                    if (item.getFullname().toLowerCase().contains(filterPattern)
                            || item.getGratituteName().toLowerCase().contains(filterPattern)
                            || item.getGratituteCategory().toLowerCase().contains(filterPattern)
                    ) {
                        filteredList.add(item);
                    }

                }
            }
            Filter.FilterResults results = new Filter.FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataArrayList.clear();
            dataArrayList.addAll((List<ModelGratitudeListingResponseData>) results.values);
            if (dataArrayList.isEmpty()) {
                journalingMain.showEmptyDataMessage();
                // Toast.makeText(mContext, "No Result Found", Toast.LENGTH_SHORT).show();
            } else {
                journalingMain.showData();
                //journalingMain.showErrorMessage(false);
            }
            notifyDataSetChanged();
        }
    };

    class ViewHolderJournaling extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{
        CircleImageView iv_user_prodile, iv_share;
        CardView main_card;
        ImageView iv_like;
        RoundedImageView attached_image;
        AppCompatImageView iv_more;
        LottieAnimationView animation_like;
        TextView tv_gj_list_username, tv_desc_text, tv_desc_text_Na, tv_gj_list_date, tv_like_count, tv_share_count, tv_description, tv_category, tv_title;

        public ViewHolderJournaling(@NonNull View itemView) {
            super(itemView);
            animation_like = itemView.findViewById(R.id.animation_like);
            animation_like.setOnClickListener(this);
            iv_user_prodile = itemView.findViewById(R.id.iv_user_profile);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_gj_list_username = itemView.findViewById(R.id.tv_gj_list_username);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            tv_share_count = itemView.findViewById(R.id.tv_share_count);
            tv_description = itemView.findViewById(R.id.tv_description);
            //tv_category = itemView.findViewById(R.id.sp_category);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_gj_list_date = itemView.findViewById(R.id.tv_gj_list_date);
            iv_more = itemView.findViewById(R.id.iv_more);
            attached_image = itemView.findViewById(R.id.iv_goal_attached_image);
            //tv_desc_text_Na = itemView.findViewById(R.id.tv_desc_text_Na);
            main_card = itemView.findViewById(R.id.main_card);
            iv_more.setOnClickListener(this);
            iv_like.setOnClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {

                case R.id.menu_edit:
                    editItemDetails(dataArrayList.get(getAbsoluteAdapterPosition()));
                    return true;

                case R.id.menu_delete:
                    //deleteItem(dataArrayList.get(getAbsoluteAdapterPosition()));
                    dataArrayList.remove(getAbsoluteAdapterPosition());
                    notifyItemRemoved(getAbsoluteAdapterPosition());
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_more) {
                showOptionMenu(v);
            } /*else if (v.getId() == R.id.iv_like) {
                toggleLike(getAbsoluteAdapterPosition());
            } */else if (v.getId() == R.id.animation_like || v.getId()==R.id.iv_like) {
                if (!dataArrayList.get(getAbsoluteAdapterPosition()).getIsLikeSymbol().equalsIgnoreCase("Like")) {
                    animation_like.setMinAndMaxProgress(0.0f, 0.5f);
                    animation_like.playAnimation();
                    dataArrayList.get(getAbsoluteAdapterPosition()).setIsLikeSymbol("Like");
                    toggleLike(getAbsoluteAdapterPosition());
                    //notifyDataSetChanged();
                } else {
                    animation_like.setMinAndMaxProgress(0.5f, 1.0f);
                    animation_like.playAnimation();
                    dataArrayList.get(getAbsoluteAdapterPosition()).setIsLikeSymbol("Unlike");
                    toggleLike(getAbsoluteAdapterPosition());
                    //notifyDataSetChanged();
                }
            }
        }
        private void showOptionMenu(View v) {

            Context wrapper = new ContextThemeWrapper(mContext, R.style.popupMenuStylenew);
            PopupMenu popupMenu = new PopupMenu(wrapper, v);

            popupMenu.inflate(R.menu.menu_uploader_items);
            popupMenu.setOnMenuItemClickListener(this);
            //popupMenu.show();

            try {
                Method method = popupMenu.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                method.setAccessible(true);
                method.invoke(popupMenu.getMenu(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            popupMenu.show();
        }

        @SuppressLint("NotifyDataSetChanged")
        private void toggleLike(int absoluteAdapterPosition) {
            journalingMain.onClickedLike(dataArrayList.get(absoluteAdapterPosition));
            int likeCount = Integer.parseInt(dataArrayList.get(absoluteAdapterPosition).getLikeCount());
            if (dataArrayList.get(absoluteAdapterPosition).getIsLikeSymbol().equalsIgnoreCase("Like")) {
                if (likeCount != 0) {
                    likeCount = likeCount - 1;
                }
                dataArrayList.get(absoluteAdapterPosition).setLikeCount("" + likeCount);


            } else {
                likeCount = likeCount + 1;
                dataArrayList.get(absoluteAdapterPosition).setLikeCount("" + likeCount);
            }
            notifyDataSetChanged();
        }

        private void deleteItem(ModelGratitudeListingResponseData modelGratitudeListingResponseData) {
            //journalingMain.deleteGratitude(modelGratitudeListingResponseData);
        }

    }

    private void editItemDetails(ModelGratitudeListingResponseData modelGratitudeListingResponseData) {
        //GratitudeJournalingMain journalingMain = (GratitudeJournalingMain) fragment;
        journalingMain.editGratitude(modelGratitudeListingResponseData);

    }


}
