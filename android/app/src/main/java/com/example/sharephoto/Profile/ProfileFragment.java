package com.example.sharephoto.Profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.Login.LoginActivity;
import com.example.sharephoto.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "label";
    public static final int EDIT_INFO = 1;
    private String[] tabTitle = {"动态", "收藏", "赞过"};
    private ViewHolder vh;
    private View profileView;

    private List<ProfileContentFragment> fragmentList;
    private ProfileContentFragmentAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (vh == null) {
            vh = new ViewHolder();
            fragmentList = new ArrayList<>();

            profileView = inflater.inflate(R.layout.fragment_profile, container, false);
            vh.profileTab = profileView.findViewById(R.id.profile_tab);
            vh.viewPager = profileView.findViewById(R.id.profile_view_pager);

            for (String t : tabTitle) {
                vh.profileTab.addTab(vh.profileTab.newTab().setText(t));
                fragmentList.add(ProfileContentFragment.newInstance(t));
            }
            adapter = new ProfileContentFragmentAdapter(requireActivity().getSupportFragmentManager(), fragmentList, tabTitle);
            vh.viewPager.setAdapter(adapter);

//            个人资料绑定
            vh.profile_user_name = profileView.findViewById(R.id.profile_user_name);
            vh.profile_user_id = profileView.findViewById(R.id.profile_user_id);
            vh.profile_sex = profileView.findViewById(R.id.profile_sex);
            vh.profile_avatar_icon = profileView.findViewById(R.id.avatar_icon);
            vh.profile_personal_description = profileView.findViewById(R.id.personal_description);

//            关注，点赞和粉丝
            vh.profile_subscription_number = profileView.findViewById(R.id.subscription_number);
            vh.profile_fan_number = profileView.findViewById(R.id.fan_number);
            vh.profile_thumb_sub_number = profileView.findViewById(R.id.thumbsup_number);

//            编辑资料
            vh.profile_edit = profileView.findViewById(R.id.profile_edit);
            vh.profile_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProfileEditActivity.class);
                    startActivityForResult(intent, EDIT_INFO);
                }
            });
        }
        vh.profileTab.setupWithViewPager(vh.viewPager, false);
        return profileView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case EDIT_INFO:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    int icon = data.getIntExtra(ProfileEditActivity.ICON, R.drawable.icon);
                    String username = data.getStringExtra(ProfileEditActivity.USERNAME);
                    String sex = data.getStringExtra(ProfileEditActivity.SEX);
                    String info = data.getStringExtra(ProfileEditActivity.INFO);

                    vh.profile_avatar_icon.setImageResource(icon);
                    vh.profile_user_name.setText(username);
                    vh.profile_personal_description.setText(info);
                    Toast.makeText(getContext(), sex, Toast.LENGTH_SHORT).show();
                } else if (resultCode == ProfileEditActivity.LOGOUT) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

    }

    static class ViewHolder {
        TabLayout profileTab;
        ViewPager viewPager;

        //    用户信息
        CircleImageView profile_avatar_icon;
        TextView profile_user_name;
        TextView profile_user_id;
        ImageView profile_sex;
        TextView profile_personal_description;

        //        关注点赞粉丝
        TextView profile_subscription_number;
        TextView profile_fan_number;
        TextView profile_thumb_sub_number;

        //        资料编辑
        Button profile_edit;
    }
}