package com.walmart.gridimagesearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.interfaces.EditSearchFilterDialogListener;
import com.walmart.gridimagesearch.models.SearchFilterParcelable;

/**
 * Created by sgovind on 10/19/15.
 */
public class EditSearchFilterDialog extends DialogFragment implements  View.OnClickListener {

    //Declare layout fields (views)
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private EditText etSiteFilter;
    public SearchFilterParcelable filter;
    public ArrayAdapter<String> aImageSize;
    public ArrayAdapter<String> aColor;
    public ArrayAdapter<String> aType;


    public String imageSizeFilter;
    public String imageColorFilter;
    public String imageTypeFilter;
    public String imageSiteFilter;

    //Empty Constructor
    public EditSearchFilterDialog() {

    }
    //Create new instance of Fragment
    public static EditSearchFilterDialog newInstance (String title, SearchFilterParcelable searchFilter ) {
        EditSearchFilterDialog frag = new EditSearchFilterDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelable("filter", searchFilter);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = 0;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_options, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        //set the title
        String title = getArguments().getString("title");
        getDialog().setTitle(title);

        //Data adapter
        ArrayAdapter<CharSequence> aCustSize = ArrayAdapter.createFromResource(getContext(), R.array.imageSize_arrays, android.R.layout.simple_spinner_item);
        aCustSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(aCustSize);


        ArrayAdapter<CharSequence> aCustColor = ArrayAdapter.createFromResource(getContext(), R.array.imageColor_arrays, android.R.layout.simple_spinner_item);
        aCustColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(aCustColor);


        ArrayAdapter<CharSequence> aCustType = ArrayAdapter.createFromResource(getContext(), R.array.imageType_arrays, android.R.layout.simple_spinner_item);
        aCustType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(aCustType);


        //Set Previously selected values
        aImageSize = (ArrayAdapter<String>) spImageSize.getAdapter();
        aColor = (ArrayAdapter<String>) spColorFilter.getAdapter();
        aType = (ArrayAdapter<String>) spImageType.getAdapter();

        filter = getArguments().getParcelable("filter");
        if ( filter != null) {

            int pSize = aImageSize.getPosition(filter.imageSizeFilter);
            spImageSize.setSelection(pSize);


            int pColor = aColor.getPosition(filter.imageColorFilter);
            spColorFilter.setSelection(pColor);


            int pType = aType.getPosition(filter.imageTypeFilter);
            spImageType.setSelection(pType);

            etSiteFilter.setText(filter.imageSiteFilter);


        }


        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        Button btnReset = (Button) view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditSearchFilterDialogListener listner = (EditSearchFilterDialogListener)getActivity();

        //Get value from spinner
        //Get value from site
        //create parcelable object


        if ( v.getId() == R.id.btnSave) {
            filter = new SearchFilterParcelable();
            filter.imageSizeFilter = spImageSize.getSelectedItem().toString();
            filter.imageColorFilter = spColorFilter.getSelectedItem().toString();
            filter.imageTypeFilter = spImageType.getSelectedItem().toString();
            filter.imageSiteFilter = etSiteFilter.getText().toString();

            listner.onEditSearchFilterDialogDone(this.getTag(), false, filter);
            dismiss();
            return;

        }


        if (v.getId() == R.id.btnReset) {
            filter = new SearchFilterParcelable();
            listner.onEditSearchFilterDialogDone(this.getTag(), false, filter);
            spImageSize.setSelection(0);
            spColorFilter.setSelection(0);
            spImageType.setSelection(0);
            etSiteFilter.setText("");
        }

        if (v.getId() == R.id.btnCancel) {
            filter = new SearchFilterParcelable();
            filter.imageSizeFilter = spImageSize.getSelectedItem().toString();
            filter.imageColorFilter = spColorFilter.getSelectedItem().toString();
            filter.imageTypeFilter = spImageType.getSelectedItem().toString();
            filter.imageSiteFilter = etSiteFilter.getText().toString();
            listner.onEditSearchFilterDialogDone(this.getTag(), true, filter);
            dismiss();
            return;

        }


    }



    public Spinner getSpImageSize() {
        return spImageSize;
    }

    public void setSpImageSize(Spinner spImageSize) {
        this.spImageSize = spImageSize;
    }

    public Spinner getSpColorFilter() {
        return spColorFilter;
    }

    public void setSpColorFilter(Spinner spColorFilter) {
        this.spColorFilter = spColorFilter;
    }

    public Spinner getSpImageType() {
        return spImageType;
    }

    public void setSpImageType(Spinner spImageType) {
        this.spImageType = spImageType;
    }

    public EditText getEtSiteFilter() {
        return etSiteFilter;
    }

    public void setEtSiteFilter(EditText etSiteFilter) {
        this.etSiteFilter = etSiteFilter;
    }
}
