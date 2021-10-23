package com.example.sharephoto.Profile;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sharephoto.Login.LoginActivity;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;
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

            for (int i = 0 ; i < tabTitle.length; i++) {
                fragmentList.add(ProfileContentFragment.newInstance(tabTitle[i]));
                vh.profileTab.addTab(vh.profileTab.newTab());
                vh.profileTab.getTabAt(i).setText(tabTitle[i]);
            }

            adapter = new ProfileContentFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, tabTitle);
            adapter.notifyDataSetChanged();
            vh.viewPager.setAdapter(adapter);
            vh.viewPager.setOffscreenPageLimit(3);

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
        initData(vh);
        vh.profileTab.setupWithViewPager(vh.viewPager, true);
        return profileView;
    }

    private void initData(ViewHolder vh) {
        String id = getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", "");
        if (!id.equals("")) {
            vh.profile_user_id.setText(id);
            new GetInfoAsyncTask(getContext(), vh).execute(id);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case EDIT_INFO:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    String icon = data.getStringExtra(ProfileEditActivity.ICON);
                    String username = data.getStringExtra(ProfileEditActivity.USERNAME);
                    String sex = data.getStringExtra(ProfileEditActivity.SEX);
                    String info = data.getStringExtra(ProfileEditActivity.INFO);

//                    vh.profile_avatar_icon.setImageResource(icon);
                    Glide.with(getContext()).load(RequestConfig.URL + icon).into(vh.profile_avatar_icon);
                    vh.profile_user_name.setText(username);
                    vh.profile_personal_description.setText(info);
                    vh.profile_sex.setSelected(sex.equals("女"));
//                    Toast.makeText(getContext(), sex, Toast.LENGTH_SHORT).show();
                } else if (resultCode == ProfileEditActivity.LOGOUT) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("profilefragement", "onResume: " + adapter.fragmentList.get(0).getLabel());
        Log.d("profilefragement", "onResume: " + adapter.fragmentList.get(1).getLabel());
        Log.d("profilefragement", "onResume: " + adapter.fragmentList.get(2).getLabel());
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