package com.example.jithin.easyapp;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

public class IconPicker extends DialogFragment {
    GridView grid,grid_alpha,grid_num;
    String[] web = {"App"},web_alpha={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"},web_num={"0","1","2","3","4","5","6","7","8","9"};
    int[] imageId = {
            R.mipmap.ic_launcher},
            imageId_alpha={
            R.mipmap.ic_alpha_a,
            R.mipmap.ic_alpha_b,
            R.mipmap.ic_alpha_c,
            R.mipmap.ic_alpha_d,
                    R.mipmap.ic_alpha_e,
                    R.mipmap.ic_alpha_f,
                    R.mipmap.ic_alpha_g,
                    R.mipmap.ic_alpha_h,
                    R.mipmap.ic_alpha_i,
                    R.mipmap.ic_alpha_j,
                    R.mipmap.ic_alpha_k,
    R.mipmap.ic_alpha_l,
    R.mipmap.ic_alpha_m,
    R.mipmap.ic_alpha_n,
    R.mipmap.ic_alpha_o,
    R.mipmap.ic_alpha_p,
    R.mipmap.ic_alpha_q,
    R.mipmap.ic_alpha_r,
    R.mipmap.ic_alpha_s,
    R.mipmap.ic_alpha_t,
    R.mipmap.ic_alpha_u,
    R.mipmap.ic_alpha_v,
                    R.mipmap.ic_alpha_w,
                    R.mipmap.ic_alpha_x,
                    R.mipmap.ic_alpha_y,
                    R.mipmap.ic_alpha_z},
            imageId_num={
            R.mipmap.ic_num_0,
            R.mipmap.ic_num_1,
            R.mipmap.ic_num_2,
                    R.mipmap.ic_num_3,
                    R.mipmap.ic_num_4,
                    R.mipmap.ic_num_5,
                    R.mipmap.ic_num_6,
                    R.mipmap.ic_num_7,
                    R.mipmap.ic_num_8,
                    R.mipmap.ic_num_9

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.iconpicker, container,
                false);
        //getDialog().setTitle("Preview");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        CustomGrid adapter = new CustomGrid(IconPicker.this.getActivity(), web, imageId);
        grid=(GridView)rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);

        //Toast.makeText(IconPicker.this.getActivity(),""+grid., Toast.LENGTH_SHORT).show();


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(IconPicker.this.getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.iconselect(position);
                getDialog().dismiss();


            }
        });

        CustomGrid adapter_alpha = new CustomGrid(IconPicker.this.getActivity(), web_alpha, imageId_alpha);
        grid_alpha=(GridView)rootView.findViewById(R.id.grid_alpha);
        grid_alpha.setAdapter(adapter_alpha);

        //Toast.makeText(IconPicker.this.getActivity(),""+grid_alpha.getNumColumns(), Toast.LENGTH_SHORT).show();

        grid_alpha.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(IconPicker.this.getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.iconselect(1+position);
                getDialog().dismiss();


            }
        });

        CustomGrid adapter_num = new CustomGrid(IconPicker.this.getActivity(), web_num, imageId_num);
        grid_num=(GridView)rootView.findViewById(R.id.grid_num);
        grid_num.setAdapter(adapter_num);
        grid_num.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(IconPicker.this.getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.iconselect(27+position);
                getDialog().dismiss();


            }
        });


        final LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.ll);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = layout.getMeasuredWidth();
                int height = layout.getMeasuredHeight();
                //Toast.makeText(IconPicker.this.getActivity(),""+grid_alpha.getMeasuredWidth()+"  "+grid_alpha.getChildAt(0).getMeasuredWidth()+"  "+grid_alpha.getChildAt(0).getMeasuredHeight(), Toast.LENGTH_SHORT).show();

                int no_rows_alpha=26 / (grid_alpha.getMeasuredWidth() / grid_alpha.getChildAt(0).getMeasuredWidth());
                if(26%(grid_alpha.getMeasuredWidth() / grid_alpha.getChildAt(0).getMeasuredWidth())!=0){
                    ++no_rows_alpha;
                }
                ViewGroup.LayoutParams params = grid_alpha.getLayoutParams();
                params.height = grid_alpha.getChildAt(0).getMeasuredHeight() * no_rows_alpha;
                grid_alpha.setLayoutParams(params);

                int no_rows_num=10 / (grid_num.getMeasuredWidth() / grid_num.getChildAt(0).getMeasuredWidth());
                if(10%(grid_num.getMeasuredWidth() / grid_num.getChildAt(0).getMeasuredWidth())!=0){
                    ++no_rows_num;
                }
                ViewGroup.LayoutParams params1 = grid_num.getLayoutParams();
                params1.height = grid_num.getChildAt(0).getMeasuredHeight() * no_rows_num;
                grid_num.setLayoutParams(params1);

            }
        });



        return rootView;

    }

}