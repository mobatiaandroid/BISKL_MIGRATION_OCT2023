package com.example.bskl_kotlin.manager.countrypicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bskl_kotlin.R;



import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by hbb20 on 11/1/16.
 */
class CountryCodeDialog {
    public static void openCountryCodeDialog(final CountryCodePicker codePicker) {
        final Context context = codePicker.getContext();
        final Dialog dialog = new Dialog(context);
        codePicker.refreshCustomMasterList();
        codePicker.refreshPreferredCountries();
        List<CCPCountry> masterCountries = CCPCountry.getCustomMasterCountryList(context, codePicker);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setContentView(R.layout.layout_picker_dialog);

        //keyboard
        if (codePicker.isSearchAllowed() && codePicker.isDialogKeyboardAutoPopup()) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }


        //dialog views
        RecyclerView recyclerView_countryDialog = dialog.findViewById(R.id.recycler_countryDialog);
        final TextView textViewTitle = dialog.findViewById(R.id.textView_title);
        RelativeLayout rlQueryHolder = dialog.findViewById(R.id.rl_query_holder);
        ImageView imgClearQuery = dialog.findViewById(R.id.img_clear_query);
        final EditText editText_search = dialog.findViewById(R.id.editText_search);
        TextView textView_noResult = dialog.findViewById(R.id.textView_noresult);
        RelativeLayout rlHolder = dialog.findViewById(R.id.rl_holder);
        ImageView imgDismiss = dialog.findViewById(R.id.img_dismiss);

        // type faces
        //set type faces
        try {
            if (codePicker.getDialogTypeFace() != null) {
                if (codePicker.getDialogTypeFaceStyle() != CountryCodePicker.DEFAULT_UNSET) {
                    textView_noResult.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle());
                    editText_search.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle());
                    textViewTitle.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle());
                } else {
                    textView_noResult.setTypeface(codePicker.getDialogTypeFace());
                    editText_search.setTypeface(codePicker.getDialogTypeFace());
                    textViewTitle.setTypeface(codePicker.getDialogTypeFace());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //dialog background color
        if (codePicker.getDialogBackgroundColor() != 0) {
            rlHolder.setBackgroundColor(codePicker.getDialogBackgroundColor());
        }

        //close button visibility
        if (codePicker.isShowCloseIcon()) {
            imgDismiss.setVisibility(View.VISIBLE);
            imgDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            imgDismiss.setVisibility(View.GONE);
        }

        //title
        if (!codePicker.getCcpDialogShowTitle()) {
            textViewTitle.setVisibility(View.GONE);
        }

        //clear button color and title color
        if (codePicker.getDialogTextColor() != 0) {
            int textColor = codePicker.getDialogTextColor();
            imgClearQuery.setColorFilter(textColor);
            imgDismiss.setColorFilter(textColor);
            textViewTitle.setTextColor(textColor);
            textView_noResult.setTextColor(textColor);
            editText_search.setTextColor(textColor);
            editText_search.setHintTextColor(Color.argb(100, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }


        //editText tint
        if (codePicker.getDialogSearchEditTextTintColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.setBackgroundTintList(ColorStateList.valueOf(codePicker.getDialogSearchEditTextTintColor()));
                setCursorColor(editText_search, codePicker.getDialogSearchEditTextTintColor());
            }
        }


        //add messages to views
        textViewTitle.setText(codePicker.getDialogTitle());
        editText_search.setHint(codePicker.getSearchHintText());
        textView_noResult.setText(codePicker.getNoResultFoundText());

        //this will make dialog compact
        if (!codePicker.isSearchAllowed()) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView_countryDialog.getLayoutParams();
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            recyclerView_countryDialog.setLayoutParams(params);
        }

        final CountryCodeAdapter cca = new CountryCodeAdapter(context, masterCountries, codePicker, rlQueryHolder, editText_search, textView_noResult, dialog, imgClearQuery);
        recyclerView_countryDialog.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_countryDialog.setAdapter(cca);

        //fast scroller
       /* FastScroller fastScroller = dialog.findViewById(R.id.fastscroll);
        fastScroller.setRecyclerView(recyclerView_countryDialog);
        if (codePicker.isShowFastScroller()) {
            if (codePicker.getFastScrollerBubbleColor() != 0) {
                fastScroller.setBubbleColor(codePicker.getFastScrollerBubbleColor());
            }

            if (codePicker.getFastScrollerHandleColor() != 0) {
                fastScroller.setHandleColor(codePicker.getFastScrollerHandleColor());
            }

            if (codePicker.getFastScrollerBubbleTextAppearance() != 0) {
                try {
                    fastScroller.setBubbleTextAppearance(codePicker.getFastScrollerBubbleTextAppearance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            fastScroller.setVisibility(View.GONE);
        }*/

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hideKeyboard(context);
                if (codePicker.getDialogEventsListener() != null) {
                    codePicker.getDialogEventsListener().onCcpDialogDismiss(dialogInterface);
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                hideKeyboard(context);
                if (codePicker.getDialogEventsListener() != null) {
                    codePicker.getDialogEventsListener().onCcpDialogCancel(dialogInterface);
                }
            }
        });

        dialog.show();
        if (codePicker.getDialogEventsListener() != null) {
            codePicker.getDialogEventsListener().onCcpDialogOpen(dialog);
        }
    }

    private static void hideKeyboard(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static final Field
            sEditorField,
            sCursorDrawableField,
            sCursorDrawableResourceField;

    static {
        Field editorField = null;
        Field cursorDrawableField = null;
        Field cursorDrawableResourceField = null;
        boolean exceptionThrown = false;
        try {
            cursorDrawableResourceField = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableResourceField.setAccessible(true);
            final Class<?> drawableFieldClass;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldClass = TextView.class;
            } else {
                editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                drawableFieldClass = editorField.getType();
            }
            cursorDrawableField = drawableFieldClass.getDeclaredField("mCursorDrawable");
            cursorDrawableField.setAccessible(true);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            sEditorField = null;
            sCursorDrawableField = null;
            sCursorDrawableResourceField = null;
        } else {
            sEditorField = editorField;
            sCursorDrawableField = cursorDrawableField;
            sCursorDrawableResourceField = cursorDrawableResourceField;
        }
    }

    static void setCursorColor(EditText editText, int color) {
        if (sCursorDrawableField == null) {
            return;
        }
        try {
            final Drawable drawable = getDrawable(editText.getContext(),
                    sCursorDrawableResourceField.getInt(editText));
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            sCursorDrawableField.set(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN
                    ? editText : sEditorField.get(editText), new Drawable[]{drawable, drawable});
        } catch (Exception ignored) {
        }
    }

    private static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id);
        } else {
            return context.getDrawable(id);
        }
    }
}