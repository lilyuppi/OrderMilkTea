package com.example.ordermilktea.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ordermilktea.R;
import com.example.ordermilktea.SharedPreferences.InformationLogin;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CaNhanFragment extends Fragment {
    private static final int MY_RESQUEST_CODE = 1111;
    List<AuthUI.IdpConfig> providers;
    private CircleImageView imvAvatar;
    private View view, viewLogin;
    private Button btnLogout;
    private TextView tvName;
    private boolean mIsLogined;
    private InformationLogin informationLogin;

    private ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        map(view);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity().getApplicationContext());
        informationLogin = new InformationLogin(getActivity());
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
        );


        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        listView = (ListView)getActivity().findViewById(R.id.lv_listview);
        final String[] cacthoi = new String[]{"Hiện tại đơn",
                                                "Hiện tại tiếp diễn",
                                                "Hiện tại hoàn thành" };

        ListView listView = (ListView) view.findViewById(R.id.lv_listview);

        ArrayAdapter<String> listviewAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                cacthoi);
        listView.setAdapter(listviewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0){
//                    Hientaidon hientaidon = new Hientaidon();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_nguphap, hientaidon);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
//                if(position == 1){
//                    Hientaitiepdien hientaitiepdien = new Hientaitiepdien();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_nguphap, hientaitiepdien);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
//                if(position == 2){
//                    Hientaihoanthanh hientaihoanthanh = new Hientaihoanthanh();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_nguphap, hientaihoanthanh);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
            }
        });


        return view;
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(), MY_RESQUEST_CODE
        );
    }

    private void map(View view) {
        mIsLogined = false;
        btnLogout = view.findViewById(R.id.btn_logout);
        imvAvatar = view.findViewById(R.id.imv_avatar);
        tvName = view.findViewById(R.id.tv_name_person);
        viewLogin = view.findViewById(R.id.linearlayout_login);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity().getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viewLogin.setEnabled(true);
                                btnLogout.setVisibility(View.GONE);
                                imvAvatar.setImageResource(R.drawable.ic_account);
                                tvName.setText("Đăng nhập");
                                mIsLogined = false;
                                informationLogin.setSharedPre(mIsLogined, "");
//                                showSignInOptions();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInOptions();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_RESQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                updateAfterLogin();
            }
            if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void updateAfterLogin() {
        // get user
        mIsLogined = true;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvName.setText(user.getDisplayName() + "");
        String facebookUserId = "";

        // find the Facebook profile and get the user's id
        for (UserInfo profile : user.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if (FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                facebookUserId = profile.getUid();
            }
        }

        // construct the URL to the profile picture, with a custom height
        // alternatively, use '?type=small|medium|large' instead of ?height=
        String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=1000";
        if (photoUrl == null) {
            photoUrl = user.getPhotoUrl() + "";
        }
        // (optional) use Picasso to download and show to image
        Glide.with(getActivity().getApplicationContext()).load(photoUrl).into(imvAvatar);
        btnLogout.setVisibility(View.VISIBLE);
        viewLogin.setEnabled(false);
        informationLogin.setSharedPre(mIsLogined, user.getUid());
    }


    @Override
    public void onResume() {
        super.onResume();
        mIsLogined = informationLogin.getIsLogined();
        if (mIsLogined) {
//            btnLogout.setVisibility(View.VISIBLE);
            updateAfterLogin();
        }
    }

}
