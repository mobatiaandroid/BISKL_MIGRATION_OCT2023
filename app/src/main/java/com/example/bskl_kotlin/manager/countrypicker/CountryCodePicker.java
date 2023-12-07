package com.example.bskl_kotlin.manager.countrypicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bskl_kotlin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;




public class CountryCodePicker extends RelativeLayout {

    static final int DEFAULT_UNSET = -99;
    static String TAG = "CCP";
    static String BUNDLE_SELECTED_CODE = "selectedCode";
    static int LIB_DEFAULT_COUNTRY_CODE = 91;
    private static int TEXT_GRAVITY_LEFT = -1, TEXT_GRAVITY_RIGHT = 1, TEXT_GRAVITY_CENTER = 0;
    private static String ANDROID_NAME_SPACE = "http://schemas.android.com/apk/res/android";
    String CCP_PREF_FILE = "CCP_PREF_FILE";
    int defaultCountryCode;
    String defaultCountryNameCode;
    Context context;
    View holderView;
    LayoutInflater mInflater;
    TextView textView_selectedCountry;
    EditText editText_registeredCarrierNumber;
    RelativeLayout holder;
    ImageView imageViewArrow;
    ImageView imageViewFlag;
    LinearLayout linearFlagBorder;
    LinearLayout linearFlagHolder;
    CCPCountry selectedCCPCountry;
    CCPCountry defaultCCPCountry;
    RelativeLayout relativeClickConsumer;
    CountryCodePicker codePicker;
    TextGravity currentTextGravity;
    // see attr.xml to see corresponding values for pref
    AutoDetectionPref selectedAutoDetectionPref;
    PhoneNumberUtil phoneUtil;
    boolean showNameCode = false;
    boolean showPhoneCode = true;
    boolean ccpDialogShowPhoneCode = true;
    boolean showFlag = true;
    boolean showFullName = false;
    boolean showFastScroller;
    boolean ccpDialogShowTitle = true;
    boolean ccpDialogShowFlag = true;
    boolean searchAllowed = true;
    boolean showArrow = true;
    boolean showCloseIcon = false;
    boolean rememberLastSelection = false;
    boolean detectCountryWithAreaCode = true;
    boolean ccpDialogShowNameCode = true;
    PhoneNumberType hintExampleNumberType = PhoneNumberType.MOBILE;
    String selectionMemoryTag = "ccp_last_selection";
    int contentColor;
    int borderFlagColor;
    Typeface dialogTypeFace;
    int dialogTypeFaceStyle;
    List<CCPCountry> preferredCountries;
    int ccpTextgGravity = TEXT_GRAVITY_CENTER;
    //this will be "AU,IN,US"
    String countryPreference;
    int fastScrollerBubbleColor = 0;
    List<CCPCountry> customMasterCountriesList;
    //this will be "AU,IN,US"
    String customMasterCountriesParam, excludedCountriesParam;
    Language customDefaultLanguage = Language.ENGLISH;
    Language languageToApply = Language.ENGLISH;

    boolean dialogKeyboardAutoPopup = true;
    boolean ccpClickable = true;
    boolean autoDetectLanguageEnabled, autoDetectCountryEnabled, numberAutoFormattingEnabled, hintExampleNumberEnabled;
    String xmlWidth = "notSet";
    TextWatcher validityTextWatcher;
    InternationalPhoneTextWatcher formattingTextWatcher;
    boolean reportedValidity;
    TextWatcher areaCodeCountryDetectorTextWatcher;
    boolean countryDetectionBasedOnAreaAllowed;
    String lastCheckedAreaCode = null;
    private OnCountryChangeListener onCountryChangeListener;
    private PhoneNumberValidityChangeListener phoneNumberValidityChangeListener;
    private FailureListener failureListener;
    private DialogEventsListener dialogEventsListener;
    private int fastScrollerHandleColor;
    private int dialogBackgroundColor, dialogTextColor, dialogSearchEditTextTintColor;
    private int fastScrollerBubbleTextAppearance;
    private CCPCountryGroup currentCountryGroup;
    OnClickListener countryCodeHolderClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isCcpClickable()) {
                launchCountrySelectionDialog();
            }
        }
    };

    public CountryCodePicker(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public CountryCodePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CountryCodePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private boolean isNumberAutoFormattingEnabled() {
        return numberAutoFormattingEnabled;
    }




    public void setNumberAutoFormattingEnabled(boolean numberAutoFormattingEnabled) {
        this.numberAutoFormattingEnabled = numberAutoFormattingEnabled;
        if (editText_registeredCarrierNumber != null) {
            updateFormattingTextWatcher();
        }
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(context);

        xmlWidth = attrs.getAttributeValue(ANDROID_NAME_SPACE, "layout_width");
        Log.d(TAG, "init:xmlWidth " + xmlWidth);
        removeAllViewsInLayout();
        //at run time, match parent value returns LayoutParams.MATCH_PARENT ("-1"), for some android xml preview it returns "fill_parent"
        if (xmlWidth != null && (xmlWidth.equals(LayoutParams.MATCH_PARENT + "") || xmlWidth.equals(LayoutParams.FILL_PARENT + "") || xmlWidth.equals("fill_parent") || xmlWidth.equals("match_parent"))) {
            holderView = mInflater.inflate(R.layout.layout_full_width_code_picker, this, true);
        } else {
            holderView = mInflater.inflate(R.layout.layout_code_picker, this, true);
        }

        textView_selectedCountry = holderView.findViewById(R.id.textView_selectedCountry);
        holder = holderView.findViewById(R.id.countryCodeHolder);
        imageViewArrow = holderView.findViewById(R.id.imageView_arrow);
        imageViewFlag = holderView.findViewById(R.id.image_flag);
        linearFlagHolder = holderView.findViewById(R.id.linear_flag_holder);
        linearFlagBorder = holderView.findViewById(R.id.linear_flag_border);
        relativeClickConsumer = holderView.findViewById(R.id.rlClickConsumer);
        codePicker = this;
        applyCustomProperty(attrs);
        relativeClickConsumer.setOnClickListener(countryCodeHolderClickListener);
    }

    private void applyCustomProperty(AttributeSet attrs) {
        //        Log.d(TAG, "Applying custom property");
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0);
        //default country code
        try {
            //hide nameCode. If someone wants only phone code to avoid name collision for same country phone code.
            showNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showNameCode, true);

            //number auto formatting
            numberAutoFormattingEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoFormatNumber, true);

            //show phone code.
            showPhoneCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showPhoneCode, true);

            //show phone code on dialog
            ccpDialogShowPhoneCode = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showPhoneCode, showPhoneCode);

            //show name code on dialog
            ccpDialogShowNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showNameCode, true);

            //show title on dialog
            ccpDialogShowTitle = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showTitle, true);

            //show flag on dialog
            ccpDialogShowFlag = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showFlag, true);

            //show full name
            showFullName = a.getBoolean(R.styleable.CountryCodePicker_ccp_showFullName, false);

            //show fast scroller
            showFastScroller = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showFastScroller, true);

            //bubble color
            fastScrollerBubbleColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_fastScroller_bubbleColor, 0);

            //scroller handle color
            fastScrollerHandleColor = a.getColor(R.styleable.CountryCodePicker_ccpDialog_fastScroller_handleColor, 0);

            //scroller text appearance
            fastScrollerBubbleTextAppearance = a.getResourceId(R.styleable.CountryCodePicker_ccpDialog_fastScroller_bubbleTextAppearance, 0);

            //auto detect language
            autoDetectLanguageEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoDetectLanguage, false);

            //detect country from area code
            detectCountryWithAreaCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_areaCodeDetectedCountry, true);

            //remember last selection
            rememberLastSelection = a.getBoolean(R.styleable.CountryCodePicker_ccp_rememberLastSelection, false);

            //example number hint enabled?
            hintExampleNumberEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_hintExampleNumber, false);

            //example number hint type
            int hintNumberTypeIndex = a.getInt(R.styleable.CountryCodePicker_ccp_hintExampleNumberType, 0);
            hintExampleNumberType = PhoneNumberType.values()[hintNumberTypeIndex];

            //memory tag name for selection
            selectionMemoryTag = a.getString(R.styleable.CountryCodePicker_ccp_selectionMemoryTag);
            if (selectionMemoryTag == null) {
                selectionMemoryTag = "CCP_last_selection";
            }

            //country auto detection pref
            int autoDetectionPrefValue = a.getInt(R.styleable.CountryCodePicker_ccp_countryAutoDetectionPref, 123);
            selectedAutoDetectionPref = AutoDetectionPref.getPrefForValue(String.valueOf(autoDetectionPrefValue));

            //auto detect county
            autoDetectCountryEnabled = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoDetectCountry, false);

            //show arrow
            showArrow = a.getBoolean(R.styleable.CountryCodePicker_ccp_showArrow, true);
            refreshArrowViewVisibility();

            //show close icon
            showCloseIcon = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_showCloseIcon, false);

            //show flag
            showFlag(a.getBoolean(R.styleable.CountryCodePicker_ccp_showFlag, true));

            //autopop keyboard
            setDialogKeyboardAutoPopup(a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_keyboardAutoPopup, true));

            //if custom default language is specified, then set it as custom else sets english as custom
            int attrLanguage;
            attrLanguage = a.getInt(R.styleable.CountryCodePicker_ccp_defaultLanguage, Language.ENGLISH.ordinal());
            customDefaultLanguage = getLanguageEnum(attrLanguage);
            updateLanguageToApply();

            //custom master list
            customMasterCountriesParam = a.getString(R.styleable.CountryCodePicker_ccp_customMasterCountries);
            excludedCountriesParam = a.getString(R.styleable.CountryCodePicker_ccp_excludedCountries);
            if (!isInEditMode()) {
                refreshCustomMasterList();
            }

            //preference
            countryPreference = a.getString(R.styleable.CountryCodePicker_ccp_countryPreference);
            //as3 is raising problem while rendering preview. to avoid such issue, it will update preferred list only on run time.
            if (!isInEditMode()) {
                refreshPreferredCountries();
            }

            //text gravity
            if (a.hasValue(R.styleable.CountryCodePicker_ccp_textGravity)) {
                ccpTextgGravity = a.getInt(R.styleable.CountryCodePicker_ccp_textGravity, TEXT_GRAVITY_RIGHT);
            }
            applyTextGravity(ccpTextgGravity);

            //default country
            //AS 3 has some problem with reading list so this is to make CCP preview work
            defaultCountryNameCode = a.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode);
            boolean setUsingNameCode = false;
            if (defaultCountryNameCode != null && defaultCountryNameCode.length() != 0) {
                if (!isInEditMode()) {
                    if (CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), defaultCountryNameCode) != null) {
                        setUsingNameCode = true;
                        setDefaultCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), defaultCountryNameCode));
                        setSelectedCountry(defaultCCPCountry);
                    }
                } else {
                    if (CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode) != null) {
                        setUsingNameCode = true;
                        setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList(defaultCountryNameCode));
                        setSelectedCountry(defaultCCPCountry);
                    }
                }

                //when it was not set means something was wrong with name code
                if (!setUsingNameCode) {
                    setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList("IN"));
                    setSelectedCountry(defaultCCPCountry);
                    setUsingNameCode = true;
                }
            }

            //if default country is not set using name code.
            int defaultCountryCode = a.getInteger(R.styleable.CountryCodePicker_ccp_defaultPhoneCode, -1);
            if (!setUsingNameCode && defaultCountryCode != -1) {
                if (!isInEditMode()) {
                    //if invalid country is set using xml, it will be replaced with LIB_DEFAULT_COUNTRY_CODE
                    if (defaultCountryCode != -1 && CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, defaultCountryCode) == null) {
                        defaultCountryCode = LIB_DEFAULT_COUNTRY_CODE;
                    }
                    setDefaultCountryUsingPhoneCode(defaultCountryCode);
                    setSelectedCountry(defaultCCPCountry);
                } else {
                    //when it is in edit mode, we will check in english list only.
                    CCPCountry defaultCountry = CCPCountry.getCountryForCodeFromEnglishList(defaultCountryCode + "");
                    if (defaultCountry == null) {
                        defaultCountry = CCPCountry.getCountryForCodeFromEnglishList(LIB_DEFAULT_COUNTRY_CODE + "");
                    }
                    setDefaultCountry(defaultCountry);
                    setSelectedCountry(defaultCountry);
                }
            }

            //if default country is not set using nameCode or phone code, let's set library default as default
            if (getDefaultCountry() == null) {
                setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList("IN"));
                if (getSelectedCountry() == null) {
                    setSelectedCountry(defaultCCPCountry);
                }
            }


            //set auto detected country
            if (isAutoDetectCountryEnabled() && !isInEditMode()) {
                setAutoDetectedCountry(true);
            }

            //set last selection
            if (rememberLastSelection && !isInEditMode()) {
                loadLastSelectedCountryInCCP();
            }

            //content color
            int contentColor;
            if (isInEditMode()) {
                contentColor = a.getColor(R.styleable.CountryCodePicker_ccp_contentColor, 0);
            } else {
                contentColor = a.getColor(R.styleable.CountryCodePicker_ccp_contentColor, context.getResources().getColor(R.color.defaultContentColor));
            }
            if (contentColor != 0) {
                setContentColor(contentColor);
            }

            // flag border color
            int borderFlagColor;
            if (isInEditMode()) {
                borderFlagColor = a.getColor(R.styleable.CountryCodePicker_ccp_flagBorderColor, 0);
            } else {
                borderFlagColor = a.getColor(R.styleable.CountryCodePicker_ccp_flagBorderColor, context.getResources().getColor(R.color.defaultBorderFlagColor));
            }
            if (borderFlagColor != 0) {
                setFlagBorderColor(borderFlagColor);
            }

            //dialog colors
            setDialogBackgroundColor(a.getColor(R.styleable.CountryCodePicker_ccpDialog_backgroundColor, 0));
            setDialogTextColor(a.getColor(R.styleable.CountryCodePicker_ccpDialog_textColor, 0));
            setDialogSearchEditTextTintColor(a.getColor(R.styleable.CountryCodePicker_ccpDialog_searchEditTextTint, 0));

            //text size
            int textSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_textSize, 0);
            if (textSize > 0) {
                textView_selectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                setFlagSize(textSize);
                setArrowSize(textSize);
            }

            //if arrow size is explicitly defined
            int arrowSize = a.getDimensionPixelSize(R.styleable.CountryCodePicker_ccp_arrowSize, 0);
            if (arrowSize > 0) {
                setArrowSize(arrowSize);
            }

            searchAllowed = a.getBoolean(R.styleable.CountryCodePicker_ccpDialog_allowSearch, true);
            setCcpClickable(a.getBoolean(R.styleable.CountryCodePicker_ccp_clickable, true));

        } catch (Exception e) {
            textView_selectedCountry.setTextSize(10);
            textView_selectedCountry.setText(e.toString());
        } finally {
            a.recycle();
        }
        Log.d(TAG, "end:xmlWidth " + xmlWidth);
    }

    private void refreshArrowViewVisibility() {
        if (showArrow) {
            imageViewArrow.setVisibility(VISIBLE);
        } else {
            imageViewArrow.setVisibility(GONE);
        }
    }




    private void loadLastSelectedCountryInCCP() {
        //get the shared pref
        SharedPreferences sharedPref = context.getSharedPreferences(
                CCP_PREF_FILE, Context.MODE_PRIVATE);

        // read last selection value
        String lastSelectedCountryNameCode = sharedPref.getString(selectionMemoryTag, null);

        //if last selection value is not null, load it into the CCP
        if (lastSelectedCountryNameCode != null) {
            setCountryForNameCode(lastSelectedCountryNameCode);
        }
    }


    void storeSelectedCountryNameCode(String selectedCountryNameCode) {
        //get the shared pref
        SharedPreferences sharedPref = context.getSharedPreferences(
                CCP_PREF_FILE, Context.MODE_PRIVATE);

        //we want to write in shared pref, so lets get editor for it
        SharedPreferences.Editor editor = sharedPref.edit();

        // add our last selection country name code in pref
        editor.putString(selectionMemoryTag, selectedCountryNameCode);

        //finally save it...
        editor.apply();
    }

    boolean isCcpDialogShowPhoneCode() {
        return ccpDialogShowPhoneCode;
    }



    public void setCcpDialogShowPhoneCode(boolean ccpDialogShowPhoneCode) {
        this.ccpDialogShowPhoneCode = ccpDialogShowPhoneCode;
    }



    public boolean getCcpDialogShowNameCode() {
        return this.ccpDialogShowNameCode;
    }



    public void setCcpDialogShowTitle(boolean ccpDialogShowTitle) {
        this.ccpDialogShowTitle = ccpDialogShowTitle;
    }



    public boolean getCcpDialogShowTitle() {
        return this.ccpDialogShowTitle;
    }



    public boolean getCcpDialogShowFlag() {
        return this.ccpDialogShowFlag;
    }


    public void setCcpDialogShowFlag(boolean ccpDialogShowFlag) {
        this.ccpDialogShowFlag = ccpDialogShowFlag;
    }


    public void setCcpDialogShowNameCode(boolean ccpDialogShowNameCode) {
        this.ccpDialogShowNameCode = ccpDialogShowNameCode;
    }

    boolean isShowPhoneCode() {
        return showPhoneCode;
    }


    public void setShowPhoneCode(boolean showPhoneCode) {
        this.showPhoneCode = showPhoneCode;
        setSelectedCountry(selectedCCPCountry);
    }



    protected DialogEventsListener getDialogEventsListener() {
        return dialogEventsListener;
    }



    public void setDialogEventsListener(DialogEventsListener dialogEventsListener) {
        this.dialogEventsListener = dialogEventsListener;
    }

    int getFastScrollerBubbleTextAppearance() {
        return fastScrollerBubbleTextAppearance;
    }



    public void setFastScrollerBubbleTextAppearance(int fastScrollerBubbleTextAppearance) {
        this.fastScrollerBubbleTextAppearance = fastScrollerBubbleTextAppearance;
    }

    int getFastScrollerHandleColor() {
        return fastScrollerHandleColor;
    }



    public void setFastScrollerHandleColor(int fastScrollerHandleColor) {
        this.fastScrollerHandleColor = fastScrollerHandleColor;
    }

    int getFastScrollerBubbleColor() {
        return fastScrollerBubbleColor;
    }


    public void setFastScrollerBubbleColor(int fastScrollerBubbleColor) {
        this.fastScrollerBubbleColor = fastScrollerBubbleColor;
    }

    TextGravity getCurrentTextGravity() {
        return currentTextGravity;
    }



    public void setCurrentTextGravity(TextGravity textGravity) {
        this.currentTextGravity = textGravity;
        applyTextGravity(textGravity.enumIndex);
    }

    private void applyTextGravity(int enumIndex) {
        if (enumIndex == TextGravity.LEFT.enumIndex) {
            textView_selectedCountry.setGravity(Gravity.LEFT);
        } else if (enumIndex == TextGravity.CENTER.enumIndex) {
            textView_selectedCountry.setGravity(Gravity.CENTER);
        } else {
            textView_selectedCountry.setGravity(Gravity.RIGHT);
        }
    }


    private void updateLanguageToApply() {
        //when in edit mode, it will return default language only
        if (isInEditMode()) {
            if (customDefaultLanguage != null) {
                languageToApply = customDefaultLanguage;
            } else {
                languageToApply = Language.ENGLISH;
            }
        } else {
            if (isAutoDetectLanguageEnabled()) {
                Language localeBasedLanguage = getCCPLanguageFromLocale();
                if (localeBasedLanguage == null) { //if no language is found from locale
                    if (getCustomDefaultLanguage() != null) { //and custom language is defined
                        languageToApply = getCustomDefaultLanguage();
                    } else {
                        languageToApply = Language.ENGLISH;
                    }
                } else {
                    languageToApply = localeBasedLanguage;
                }
            } else {
                if (getCustomDefaultLanguage() != null) {
                    languageToApply = customDefaultLanguage;
                } else {
                    languageToApply = Language.ENGLISH;  //library default
                }
            }
        }
        Log.d(TAG, "updateLanguageToApply: " + languageToApply);
    }

    private Language getCCPLanguageFromLocale() {
        Locale currentLocale = context.getResources().getConfiguration().locale;
        Log.d(TAG, "getCCPLanguageFromLocale: current locale language" + currentLocale.getLanguage());
        for (Language language : Language.values()) {
            if (language.getCode().equalsIgnoreCase(currentLocale.getLanguage())) {
                return language;
            }
        }
        return null;
    }

    private CCPCountry getDefaultCountry() {
        return defaultCCPCountry;
    }

    private void setDefaultCountry(CCPCountry defaultCCPCountry) {
        this.defaultCCPCountry = defaultCCPCountry;
        //        Log.d(TAG, "Setting default country:" + defaultCountry.logString());
    }

    public TextView getTextView_selectedCountry() {
        return textView_selectedCountry;
    }

    public void setTextView_selectedCountry(TextView textView_selectedCountry) {
        this.textView_selectedCountry = textView_selectedCountry;
    }

    public ImageView getImageViewFlag() {
        return imageViewFlag;
    }

    public void setImageViewFlag(ImageView imageViewFlag) {
        this.imageViewFlag = imageViewFlag;
    }

    private CCPCountry getSelectedCountry() {
        if (selectedCCPCountry == null) {
            setSelectedCountry(getDefaultCountry());
        }
        return selectedCCPCountry;
    }

    void setSelectedCountry(CCPCountry selectedCCPCountry) {

        //force disable area code country detection
        countryDetectionBasedOnAreaAllowed = false;
        lastCheckedAreaCode = "";

        //as soon as country is selected, textView should be updated
        if (selectedCCPCountry == null) {
            selectedCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, defaultCountryCode);
        }

        this.selectedCCPCountry = selectedCCPCountry;

        String displayText = "";

        // add full name to if required
        if (showFullName) {
            displayText = displayText + selectedCCPCountry.getName();
        }

        // adds name code if required
        if (showNameCode) {
            if (showFullName) {
                displayText += " (" + selectedCCPCountry.getNameCode().toUpperCase() + ")";
            } else {
                displayText += " " + selectedCCPCountry.getNameCode().toUpperCase();
            }
        }

        // hide phone code if required
        if (showPhoneCode) {
            if (displayText.length() > 0) {
                displayText += "  ";
            }
            displayText += "+" + selectedCCPCountry.getPhoneCode();
        }

        textView_selectedCountry.setText(displayText);

        //avoid blank state of ccp
        if (showFlag == false && displayText.length() == 0) {
            displayText += "+" + selectedCCPCountry.getPhoneCode();
            textView_selectedCountry.setText(displayText);
        }

        if (onCountryChangeListener != null) {
            onCountryChangeListener.onCountrySelected();
        }

        imageViewFlag.setImageResource(selectedCCPCountry.getFlagID());
        //        Log.d(TAG, "Setting selected country:" + selectedCountry.logString());

        updateFormattingTextWatcher();

        updateHint();

        //notify to registered validity listener
        if (editText_registeredCarrierNumber != null && phoneNumberValidityChangeListener != null) {
            reportedValidity = isValidFullNumber();
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity);
        }

        //once updates are done, this will release lock
        countryDetectionBasedOnAreaAllowed = true;

        //if the country was auto detected based on area code, this will correct the cursor position.
        if (countryChangedDueToAreaCode) {
            try {
                editText_registeredCarrierNumber.setSelection(lastCursorPosition);
                countryChangedDueToAreaCode = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //update country group
        updateCountryGroup();
    }


    private void updateCountryGroup() {
        currentCountryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(getSelectedCountryCodeAsInt());
    }


    private void updateHint() {
        if (editText_registeredCarrierNumber != null && hintExampleNumberEnabled) {
            String formattedNumber = "";
            Phonenumber.PhoneNumber exampleNumber = getPhoneUtil().getExampleNumberForType(getSelectedCountryNameCode(), getSelectedHintNumberType());
            if (exampleNumber != null) {
                    formattedNumber = exampleNumber.getNationalNumber() + "";
                    Log.d(TAG, "updateHint: " + formattedNumber);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        formattedNumber = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + formattedNumber, getSelectedCountryNameCode());
                    } else {
                        formattedNumber = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + formattedNumber + formattedNumber);
                    }
                formattedNumber = formattedNumber.substring(getSelectedCountryCodeWithPlus().length()).trim();
                Log.d(TAG, "updateHint: after format " + formattedNumber + " " + selectionMemoryTag);
            } else {
                Log.w(TAG, "updateHint: No example number found for this country (" + getSelectedCountryNameCode() + ") or this type (" + hintExampleNumberType.name() + ").");
            }
            editText_registeredCarrierNumber.setHint(formattedNumber);
        }
    }



    private PhoneNumberUtil.PhoneNumberType getSelectedHintNumberType() {
        switch (hintExampleNumberType) {
            case MOBILE:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
            case FIXED_LINE:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
            case FIXED_LINE_OR_MOBILE:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;
            case TOLL_FREE:
                return PhoneNumberUtil.PhoneNumberType.TOLL_FREE;
            case PREMIUM_RATE:
                return PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE;
            case SHARED_COST:
                return PhoneNumberUtil.PhoneNumberType.SHARED_COST;
            case VOIP:
                return PhoneNumberUtil.PhoneNumberType.VOIP;
            case PERSONAL_NUMBER:
                return PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;
            case PAGER:
                return PhoneNumberUtil.PhoneNumberType.PAGER;
            case UAN:
                return PhoneNumberUtil.PhoneNumberType.UAN;
            case VOICEMAIL:
                return PhoneNumberUtil.PhoneNumberType.VOICEMAIL;
            case UNKNOWN:

                return PhoneNumberUtil.PhoneNumberType.UNKNOWN;
            default:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
        }
    }

    Language getLanguageToApply() {
        if (languageToApply == null) {
            updateLanguageToApply();
        }
        return languageToApply;
    }

    void setLanguageToApply(Language languageToApply) {
        this.languageToApply = languageToApply;
    }

    private void updateFormattingTextWatcher() {
        if (editText_registeredCarrierNumber != null && selectedCCPCountry != null) {
            Log.d(TAG, "updateFormattingTextWatcher: " + selectionMemoryTag);
            String enteredValue = getEditText_registeredCarrierNumber().getText().toString();
            String digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue);

            if (formattingTextWatcher != null) {
                editText_registeredCarrierNumber.removeTextChangedListener(formattingTextWatcher);
            }

            if (areaCodeCountryDetectorTextWatcher != null) {
                editText_registeredCarrierNumber.removeTextChangedListener(areaCodeCountryDetectorTextWatcher);
            }

            if (numberAutoFormattingEnabled) {
                formattingTextWatcher = new InternationalPhoneTextWatcher(context, getSelectedCountryNameCode(), getSelectedCountryCodeAsInt());
                editText_registeredCarrierNumber.addTextChangedListener(formattingTextWatcher);
            }

            //if country detection from area code is enabled, then it will add areaCodeCountryDetectorTextWatcher
            if (detectCountryWithAreaCode) {
                areaCodeCountryDetectorTextWatcher = getCountryDetectorTextWatcher();
                editText_registeredCarrierNumber.addTextChangedListener(areaCodeCountryDetectorTextWatcher);
            }

            //text watcher stops working when it finds non digit character in previous phone code. This will reset its function
            editText_registeredCarrierNumber.setText("");
            editText_registeredCarrierNumber.setText(digitsValue);
            editText_registeredCarrierNumber.setSelection(editText_registeredCarrierNumber.getText().length());
        } else {
            if (editText_registeredCarrierNumber == null) {
                Log.d(TAG, "updateFormattingTextWatcher: EditText not registered " + selectionMemoryTag);
            } else {
                Log.d(TAG, "updateFormattingTextWatcher: selected country is null " + selectionMemoryTag);
            }
        }
    }

    int lastCursorPosition = 0;
    boolean countryChangedDueToAreaCode = false;


    private TextWatcher getCountryDetectorTextWatcher() {

        if (editText_registeredCarrierNumber != null) {
            if (areaCodeCountryDetectorTextWatcher == null) {
                areaCodeCountryDetectorTextWatcher = new TextWatcher() {
                    String lastCheckedNumber = null;


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CCPCountry selectedCountry = getSelectedCountry();
                        if (selectedCountry != null && (lastCheckedNumber == null || !lastCheckedNumber.equals(s.toString())) && countryDetectionBasedOnAreaAllowed) {
                            //possible countries
                            if (currentCountryGroup != null) {
                                String enteredValue = getEditText_registeredCarrierNumber().getText().toString();
                                if (enteredValue.length() >= currentCountryGroup.areaCodeLength) {
                                    String digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue);
                                    if (digitsValue.length() >= currentCountryGroup.areaCodeLength) {
                                        String currentAreaCode = digitsValue.substring(0, currentCountryGroup.areaCodeLength);
                                        if (!currentAreaCode.equals(lastCheckedAreaCode)) {
                                            CCPCountry detectedCountry = currentCountryGroup.getCountryForAreaCode(context, getLanguageToApply(), currentAreaCode);
                                            if (!detectedCountry.equals(selectedCountry)) {
                                                countryChangedDueToAreaCode = true;
                                                lastCursorPosition = Selection.getSelectionEnd(s);
                                                setSelectedCountry(detectedCountry);
                                            }
                                            lastCheckedAreaCode = currentAreaCode;
                                        }
                                    }
                                }
                            }
                            lastCheckedNumber = s.toString();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                };
            }
        }
        return areaCodeCountryDetectorTextWatcher;
    }

    Language getCustomDefaultLanguage() {
        return customDefaultLanguage;
    }

    private void setCustomDefaultLanguage(Language customDefaultLanguage) {
        this.customDefaultLanguage = customDefaultLanguage;
        updateLanguageToApply();
        setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(context, getLanguageToApply(), selectedCCPCountry.getNameCode()));
    }

    private View getHolderView() {
        return holderView;
    }

    private void setHolderView(View holderView) {
        this.holderView = holderView;
    }

    private RelativeLayout getHolder() {
        return holder;
    }

    private void setHolder(RelativeLayout holder) {
        this.holder = holder;
    }

    boolean isAutoDetectLanguageEnabled() {
        return autoDetectLanguageEnabled;
    }

    boolean isAutoDetectCountryEnabled() {
        return autoDetectCountryEnabled;
    }

    boolean isDialogKeyboardAutoPopup() {
        return dialogKeyboardAutoPopup;
    }

    public void setDialogKeyboardAutoPopup(boolean dialogKeyboardAutoPopup) {
        this.dialogKeyboardAutoPopup = dialogKeyboardAutoPopup;
    }

    boolean isShowFastScroller() {
        return showFastScroller;
    }


    public void setShowFastScroller(boolean showFastScroller) {
        this.showFastScroller = showFastScroller;
    }

    protected boolean isShowCloseIcon() {
        return showCloseIcon;
    }


    public void showCloseIcon(boolean showCloseIcon) {
        this.showCloseIcon = showCloseIcon;
    }

    EditText getEditText_registeredCarrierNumber() {
        Log.d(TAG, "getEditText_registeredCarrierNumber");
        return editText_registeredCarrierNumber;
    }



    void setEditText_registeredCarrierNumber(EditText editText_registeredCarrierNumber) {
        this.editText_registeredCarrierNumber = editText_registeredCarrierNumber;
        Log.d(TAG, "setEditText_registeredCarrierNumber: carrierEditTextAttached " + selectionMemoryTag);
        updateValidityTextWatcher();
        updateFormattingTextWatcher();
        updateHint();
    }


    private void updateValidityTextWatcher() {
        try {
            editText_registeredCarrierNumber.removeTextChangedListener(validityTextWatcher);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //initial REPORTING
        reportedValidity = isValidFullNumber();
        if (phoneNumberValidityChangeListener != null) {
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity);
        }

        validityTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phoneNumberValidityChangeListener != null) {
                    boolean currentValidity;
                    currentValidity = isValidFullNumber();
                    if (currentValidity != reportedValidity) {
                        reportedValidity = currentValidity;
                        phoneNumberValidityChangeListener.onValidityChanged(reportedValidity);
                    }
                }
            }
        };

        editText_registeredCarrierNumber.addTextChangedListener(validityTextWatcher);
    }

    private LayoutInflater getmInflater() {
        return mInflater;
    }

    private OnClickListener getCountryCodeHolderClickListener() {
        return countryCodeHolderClickListener;
    }

    int getDialogBackgroundColor() {
        return dialogBackgroundColor;
    }



    public void setDialogBackgroundColor(int dialogBackgroundColor) {
        this.dialogBackgroundColor = dialogBackgroundColor;
    }

    int getDialogSearchEditTextTintColor() {
        return dialogSearchEditTextTintColor;
    }


    public void setDialogSearchEditTextTintColor(int dialogSearchEditTextTintColor) {
        this.dialogSearchEditTextTintColor = dialogSearchEditTextTintColor;
    }

    int getDialogTextColor() {
        return dialogTextColor;
    }



    public void setDialogTextColor(int dialogTextColor) {
        this.dialogTextColor = dialogTextColor;
    }

    int getDialogTypeFaceStyle() {
        return dialogTypeFaceStyle;
    }


    Typeface getDialogTypeFace() {
        return dialogTypeFace;
    }



    public void setDialogTypeFace(Typeface typeFace) {
        try {
            dialogTypeFace = typeFace;
            dialogTypeFaceStyle = DEFAULT_UNSET;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void refreshPreferredCountries() {
        if (countryPreference == null || countryPreference.length() == 0) {
            preferredCountries = null;
        } else {
            List<CCPCountry> localCCPCountryList = new ArrayList<>();
            for (String nameCode : countryPreference.split(",")) {
                CCPCountry ccpCountry = CCPCountry.getCountryForNameCodeFromCustomMasterList(getContext(), customMasterCountriesList, getLanguageToApply(), nameCode);
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry);
                    }
                }
            }

            if (localCCPCountryList.size() == 0) {
                preferredCountries = null;
            } else {
                preferredCountries = localCCPCountryList;
            }
        }
        if (preferredCountries != null) {
            //            Log.d("preference list", preferredCountries.size() + " countries");
            for (CCPCountry CCPCountry : preferredCountries) {
                CCPCountry.log();
            }
        } else {
            //            Log.d("preference list", " has no country");
        }
    }

    void refreshCustomMasterList() {
        //if no custom list specified then check for exclude list
        if (customMasterCountriesParam == null || customMasterCountriesParam.length() == 0) {
            //if excluded param is also blank, then do nothing
            if (excludedCountriesParam != null && excludedCountriesParam.length() != 0) {
                excludedCountriesParam = excludedCountriesParam.toLowerCase();
                List<CCPCountry> libraryMasterList = CCPCountry.getLibraryMasterCountryList(context, getLanguageToApply());
                List<CCPCountry> localCCPCountryList = new ArrayList<>();
                for (CCPCountry ccpCountry : libraryMasterList) {
                    //if the country name code is in the excluded list, avoid it.
                    if (!excludedCountriesParam.contains(ccpCountry.getNameCode().toLowerCase())) {
                        localCCPCountryList.add(ccpCountry);
                    }
                }

                if (localCCPCountryList.size() > 0) {
                    customMasterCountriesList = localCCPCountryList;
                } else {
                    customMasterCountriesList = null;
                }

            } else {
                customMasterCountriesList = null;
            }
        } else {
            //else add custom list
            List<CCPCountry> localCCPCountryList = new ArrayList<>();
            for (String nameCode : customMasterCountriesParam.split(",")) {
                CCPCountry ccpCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), nameCode);
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry);
                    }
                }
            }

            if (localCCPCountryList.size() == 0) {
                customMasterCountriesList = null;
            } else {
                customMasterCountriesList = localCCPCountryList;
            }
        }

        if (customMasterCountriesList != null) {
            //            Log.d("custom master list:", customMasterCountriesList.size() + " countries");
            for (CCPCountry ccpCountry : customMasterCountriesList) {
                ccpCountry.log();
            }
        } else {
            //            Log.d("custom master list", " has no country");
        }
    }

    List<CCPCountry> getCustomMasterCountriesList() {
        return customMasterCountriesList;
    }

    void setCustomMasterCountriesList(List<CCPCountry> customMasterCountriesList) {
        this.customMasterCountriesList = customMasterCountriesList;
    }



    String getCustomMasterCountriesParam() {
        return customMasterCountriesParam;
    }



    public void setCustomMasterCountries(String customMasterCountriesParam) {
        this.customMasterCountriesParam = customMasterCountriesParam;
    }



    public void setExcludedCountries(String excludedCountries) {
        this.excludedCountriesParam = excludedCountries;
        refreshCustomMasterList();
    }


    boolean isCcpClickable() {
        return ccpClickable;
    }

    public void setCcpClickable(boolean ccpClickable) {
        this.ccpClickable = ccpClickable;
        if (!ccpClickable) {
            relativeClickConsumer.setOnClickListener(null);
            relativeClickConsumer.setClickable(false);
            relativeClickConsumer.setEnabled(false);
        } else {
            relativeClickConsumer.setOnClickListener(countryCodeHolderClickListener);
            relativeClickConsumer.setClickable(true);
            relativeClickConsumer.setEnabled(true);
        }
    }


    private boolean isAlreadyInList(CCPCountry CCPCountry, List<CCPCountry> CCPCountryList) {
        if (CCPCountry != null && CCPCountryList != null) {
            for (CCPCountry iterationCCPCountry : CCPCountryList) {
                if (iterationCCPCountry.getNameCode().equalsIgnoreCase(CCPCountry.getNameCode())) {
                    return true;
                }
            }
        }
        return false;
    }



    private String detectCarrierNumber(String fullNumber, CCPCountry CCPCountry) {
        String carrierNumber;
        if (CCPCountry == null || fullNumber == null || fullNumber.isEmpty()) {
            carrierNumber = fullNumber;
        } else {
            int indexOfCode = fullNumber.indexOf(CCPCountry.getPhoneCode());
            if (indexOfCode == -1) {
                carrierNumber = fullNumber;
            } else {
                carrierNumber = fullNumber.substring(indexOfCode + CCPCountry.getPhoneCode().length());
            }
        }
        return carrierNumber;
    }



    //add entry here
    private Language getLanguageEnum(int index) {
        if (index < Language.values().length) {
            return Language.values()[index];
        } else {
            return Language.ENGLISH;
        }
    }

    String getDialogTitle() {
        return CCPCountry.getDialogTitle(context, getLanguageToApply());
    }

    String getSearchHintText() {
        return CCPCountry.getSearchHintMessage(context, getLanguageToApply());
    }

    String getNoResultFoundText() {
        return CCPCountry.getNoResultFoundAckMessage(context, getLanguageToApply());
    }


    @Deprecated
    public void setDefaultCountryUsingPhoneCode(int defaultCountryCode) {
        CCPCountry defaultCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, defaultCountryCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            //            Log.d(TAG, "No country for code " + defaultCountryCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryCode = defaultCountryCode;
            setDefaultCountry(defaultCCPCountry);
        }
    }


    public void setDefaultCountryUsingNameCode(String defaultCountryNameCode) {
        CCPCountry defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), defaultCountryNameCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            //            Log.d(TAG, "No country for nameCode " + defaultCountryNameCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryNameCode = defaultCCPCountry.getNameCode();
            setDefaultCountry(defaultCCPCountry);
        }
    }



    public String getDefaultCountryCode() {
        return defaultCCPCountry.phoneCode;
    }



    public int getDefaultCountryCodeAsInt() {
        int code = 0;
        try {
            code = Integer.parseInt(getDefaultCountryCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    public String getDefaultCountryCodeWithPlus() {
        return "+" + getDefaultCountryCode();
    }


    public String getDefaultCountryName() {
        return getDefaultCountry().name;
    }


    public String getDefaultCountryNameCode() {
        return getDefaultCountry().nameCode.toUpperCase();
    }


    public void resetToDefaultCountry() {
        defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), getDefaultCountryNameCode());
        setSelectedCountry(defaultCCPCountry);
    }


    public String getSelectedCountryCode() {
        return getSelectedCountry().phoneCode;
    }


    public String getSelectedCountryCodeWithPlus() {
        return "+" + getSelectedCountryCode();
    }

    public int getSelectedCountryCodeAsInt() {
        int code = 0;
        try {
            code = Integer.parseInt(getSelectedCountryCode());
                    /*Integer.parseInt(getSelectedCountryCode());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    public String getSelectedCountryName() {
        return getSelectedCountry().name;
    }



    public String getSelectedCountryEnglishName() {
        return getSelectedCountry().getEnglishName();
    }


    public String getSelectedCountryNameCode() {
        return getSelectedCountry().nameCode.toUpperCase();
    }


    public void setCountryForPhoneCode(int countryCode) {
        CCPCountry ccpCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, countryCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (ccpCountry == null) {
            if (defaultCCPCountry == null) {
                defaultCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, defaultCountryCode);
            }
            setSelectedCountry(defaultCCPCountry);
        } else {
            setSelectedCountry(ccpCountry);
        }
    }



    public void setCountryForNameCode(String countryNameCode) {
        CCPCountry country = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), countryNameCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (country == null) {
            if (defaultCCPCountry == null) {
                defaultCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), preferredCountries, defaultCountryCode);
            }
            setSelectedCountry(defaultCCPCountry);
        } else {
            setSelectedCountry(country);
        }
    }


    public void registerCarrierNumberEditText(EditText editTextCarrierNumber) {
        setEditText_registeredCarrierNumber(editTextCarrierNumber);
    }



    public String getFullNumber() {
        String fullNumber = getSelectedCountryCode();
        if (editText_registeredCarrierNumber != null) {
            PhoneNumberUtil phoneNumberUtil = getPhoneUtil();
            try {
                String phoneCode = getSelectedCountryCode();
                String nameCode = getSelectedCountryNameCode();
                String nationalPhone = PhoneNumberUtil.normalizeDigitsOnly(editText_registeredCarrierNumber.getText().toString());
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(nationalPhone, nameCode);
                fullNumber = "" + phoneNumber.getCountryCode() + phoneNumber.getNationalNumber();
            } catch (NumberParseException e) {
                e.printStackTrace();
            }
        }
        return fullNumber;
    }


    public void setFullNumber(String fullNumber) {
        CCPCountry country = CCPCountry.getCountryForNumber(getContext(), getLanguageToApply(), preferredCountries, fullNumber);
        if (country == null)
            country = getDefaultCountry();
        setSelectedCountry(country);
        String carrierNumber = detectCarrierNumber(fullNumber, country);
        if (getEditText_registeredCarrierNumber() != null) {
            getEditText_registeredCarrierNumber().setText(carrierNumber);
            updateFormattingTextWatcher();
        } else {
            Log.w(TAG, "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber().");
        }
    }

    public String getFormattedFullNumber() {
        String formattedFullNumber;
        Phonenumber.PhoneNumber phoneNumber;
        if (editText_registeredCarrierNumber != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                formattedFullNumber = PhoneNumberUtils.formatNumber(getFullNumberWithPlus(), getSelectedCountryCode());
            } else {
                formattedFullNumber = PhoneNumberUtils.formatNumber(getFullNumberWithPlus());
            }
        } else {
            formattedFullNumber = getSelectedCountry().getPhoneCode();
            Log.w(TAG, "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber().");
        }
        return formattedFullNumber;
    }


    public String getFullNumberWithPlus() {
        String fullNumber = "+" + getFullNumber();
        return fullNumber;
    }



    public int getContentColor() {
        return contentColor;
    }

    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
        textView_selectedCountry.setTextColor(this.contentColor);
        imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN);
    }



    public void setFlagBorderColor(int borderFlagColor) {
        this.borderFlagColor = borderFlagColor;
        linearFlagBorder.setBackgroundColor(this.borderFlagColor);
    }



    public void setTextSize(int textSize) {
        if (textSize > 0) {
            textView_selectedCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            setArrowSize(textSize);
            setFlagSize(textSize);
        }
    }


    public void setArrowSize(int arrowSize) {
        if (arrowSize > 0) {
            LayoutParams params = (LayoutParams) imageViewArrow.getLayoutParams();
            params.width = arrowSize;
            params.height = arrowSize;
            imageViewArrow.setLayoutParams(params);
        }
    }


    public void showNameCode(boolean showNameCode) {
        this.showNameCode = showNameCode;
        setSelectedCountry(selectedCCPCountry);
    }


    public void showArrow(boolean showArrow) {
        this.showArrow = showArrow;
        refreshArrowViewVisibility();
    }



    public void setCountryPreference(String countryPreference) {
        this.countryPreference = countryPreference;
    }



    public void changeDefaultLanguage(Language language) {
        setCustomDefaultLanguage(language);
    }

    public void setTypeFace(Typeface typeFace) {
        try {
            textView_selectedCountry.setTypeface(typeFace);
            setDialogTypeFace(typeFace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setDialogTypeFace(Typeface typeFace, int style) {
        try {
            dialogTypeFace = typeFace;
            if (dialogTypeFace == null) {
                style = DEFAULT_UNSET;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setTypeFace(Typeface typeFace, int style) {
        try {
            textView_selectedCountry.setTypeface(typeFace, style);
            setDialogTypeFace(typeFace, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOnCountryChangeListener(OnCountryChangeListener onCountryChangeListener) {
        this.onCountryChangeListener = onCountryChangeListener;
    }



    public void setFlagSize(int flagSize) {
        imageViewFlag.getLayoutParams().height = flagSize;
        imageViewFlag.requestLayout();
    }

    public void showFlag(boolean showFlag) {
        this.showFlag = showFlag;
        if (showFlag) {
            linearFlagHolder.setVisibility(VISIBLE);
        } else {
            linearFlagHolder.setVisibility(GONE);
        }
    }

    public void showFullName(boolean showFullName) {
        this.showFullName = showFullName;
        setSelectedCountry(selectedCCPCountry);
    }



    public boolean isSearchAllowed() {
        return searchAllowed;
    }


    public void setSearchAllowed(boolean searchAllowed) {
        this.searchAllowed = searchAllowed;
    }



    public void setPhoneNumberValidityChangeListener(PhoneNumberValidityChangeListener phoneNumberValidityChangeListener) {
        this.phoneNumberValidityChangeListener = phoneNumberValidityChangeListener;
        if (editText_registeredCarrierNumber != null) {
            reportedValidity = isValidFullNumber();
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity);
        }
    }


    public void setPhoneNumberValidityChangeListener(FailureListener failureListener) {
        this.failureListener = failureListener;
    }



    public void launchCountrySelectionDialog() {
        CountryCodeDialog.openCountryCodeDialog(codePicker);
    }


    public boolean isValidFullNumber() {
        try {
            if (getEditText_registeredCarrierNumber() != null && getEditText_registeredCarrierNumber().getText().length() != 0) {
                Phonenumber.PhoneNumber phoneNumber = getPhoneUtil().parse("+" + selectedCCPCountry.getPhoneCode() + getEditText_registeredCarrierNumber().getText().toString(), selectedCCPCountry.getNameCode());
                return getPhoneUtil().isValidNumber(phoneNumber);
            } else if (getEditText_registeredCarrierNumber() == null) {
                Toast.makeText(context, "No editText for Carrier number found.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return false;
            }
        } catch (NumberParseException e) {
            //            when number could not be parsed, its not valid
            return false;
        }
    }

    private PhoneNumberUtil getPhoneUtil() {
        if (phoneUtil == null) {
            phoneUtil = PhoneNumberUtil.createInstance(context);
        }
        return phoneUtil;
    }


    public void setAutoDetectedCountry(boolean loadDefaultWhenFails) {
        try {
            boolean successfullyDetected = false;
            for (int i = 0; i < selectedAutoDetectionPref.representation.length(); i++) {
                switch (selectedAutoDetectionPref.representation.charAt(i)) {
                    case '1':
                        Log.d(TAG, "setAutoDetectedCountry: Setting using SIM");
                        successfullyDetected = detectSIMCountry(false);
                        Log.d(TAG, "setAutoDetectedCountry: Result of sim country detection:" + successfullyDetected + " current country:" + getSelectedCountryNameCode());
                        break;
                    case '2':
                        Log.d(TAG, "setAutoDetectedCountry: Setting using NETWORK");
                        successfullyDetected = detectNetworkCountry(false);
                        Log.d(TAG, "setAutoDetectedCountry: Result of network country detection:" + successfullyDetected + " current country:" + getSelectedCountryNameCode());
                        break;
                    case '3':
                        Log.d(TAG, "setAutoDetectedCountry: Setting using LOCALE");
                        successfullyDetected = detectLocaleCountry(false);
                        Log.d(TAG, "setAutoDetectedCountry: Result of LOCALE country detection:" + successfullyDetected + " current country:" + getSelectedCountryNameCode());
                        break;
                }
                if (successfullyDetected) {
                    break;
                } else {
                    if (failureListener != null) {
                        failureListener.onCountryAutoDetectionFailed();
                    }
                }
            }

            if (!successfullyDetected && loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "setAutoDetectCountry: Exception" + e.getMessage());
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
        }
    }



    public boolean detectSIMCountry(boolean loadDefaultWhenFails) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simCountryISO = telephonyManager.getSimCountryIso();
            if (simCountryISO == null || simCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), simCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }


    public boolean detectNetworkCountry(boolean loadDefaultWhenFails) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkCountryISO = telephonyManager.getNetworkCountryIso();
            if (networkCountryISO == null || networkCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), networkCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }



    public boolean detectLocaleCountry(boolean loadDefaultWhenFails) {
        try {
            String localeCountryISO = context.getResources().getConfiguration().locale.getCountry();
            if (localeCountryISO == null || localeCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), localeCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }



    public void setCountryAutoDetectionPref(AutoDetectionPref selectedAutoDetectionPref) {
        this.selectedAutoDetectionPref = selectedAutoDetectionPref;
    }

    protected void onUserTappedCountry(CCPCountry CCPCountry) {
        if (codePicker.rememberLastSelection) {
            codePicker.storeSelectedCountryNameCode(CCPCountry.getNameCode());
        }
        setSelectedCountry(CCPCountry);
    }

    public void setDetectCountryWithAreaCode(boolean detectCountryWithAreaCode) {
        this.detectCountryWithAreaCode = detectCountryWithAreaCode;
        updateFormattingTextWatcher();
    }

    public void setHintExampleNumberEnabled(boolean hintExampleNumberEnabled) {
        this.hintExampleNumberEnabled = hintExampleNumberEnabled;
        updateHint();
    }

    public void setHintExampleNumberType(PhoneNumberType hintExampleNumberType) {
        this.hintExampleNumberType = hintExampleNumberType;
        updateHint();
    }

    //add an entry for your language in attrs.xml's <attr name="language" format="enum"> enum.
    //add here so that language can be set programmatically
    public enum Language {
        ARABIC("ar"),
        BENGALI("bn"),
        CHINESE_SIMPLIFIED("zh"),
        CHINESE_TRADITIONAL("zh"),
        DUTCH("nl"),
        ENGLISH("en"),
        FARSI("fa"),
        FRENCH("fr"),
        GERMAN("de"),
        GUJARATI("gu"),
        HEBREW("iw"),
        HINDI("hi"),
        INDONESIA("in"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KOREAN("ko"),
        POLISH("pl"),
        PORTUGUESE("pt"),
        PUNJABI("pa"),
        RUSSIAN("ru"),
        SLOVAK("sk"),
        SPANISH("es"),
        SWEDISH("sv"),
        TURKISH("tr"),
        UKRAINIAN("uk");

        String code;

        Language(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public enum PhoneNumberType {
        MOBILE,
        FIXED_LINE,
        // In some regions (e.g. the USA), it is impossible to distinguish between fixed-line and
        // mobile numbers by looking at the phone number itself.
        FIXED_LINE_OR_MOBILE,
        // Freephone lines
        TOLL_FREE,
        PREMIUM_RATE,
        // The cost of this call is shared between the caller and the recipient, and is hence typically
        // less than PREMIUM_RATE calls. See // http://en.wikipedia.org/wiki/Shared_Cost_Service for
        // more information.
        SHARED_COST,
        // Voice over IP numbers. This includes TSoIP (Telephony Service over IP).
        VOIP,
        // A personal number is associated with a particular person, and may be routed to either a
        // MOBILE or FIXED_LINE number. Some more information can be found here:
        // http://en.wikipedia.org/wiki/Personal_Numbers
        PERSONAL_NUMBER,
        PAGER,
        // Used for "Universal Access Numbers" or "Company Numbers". They may be further routed to
        // specific offices, but allow one number to be used for a company.
        UAN,
        // Used for "Voice Mail Access Numbers".
        VOICEMAIL,
        // A phone number is of type UNKNOWN when it does not fit any of the known patterns for a
        // specific region.
        UNKNOWN
    }

    public enum AutoDetectionPref {
        SIM_ONLY("1"),
        NETWORK_ONLY("2"),
        LOCALE_ONLY("3"),
        SIM_NETWORK("12"),
        NETWORK_SIM("21"),
        SIM_LOCALE("13"),
        LOCALE_SIM("31"),
        NETWORK_LOCALE("23"),
        LOCALE_NETWORK("32"),
        SIM_NETWORK_LOCALE("123"),
        SIM_LOCALE_NETWORK("132"),
        NETWORK_SIM_LOCALE("213"),
        NETWORK_LOCALE_SIM("231"),
        LOCALE_SIM_NETWORK("312"),
        LOCALE_NETWORK_SIM("321");

        String representation;

        AutoDetectionPref(String representation) {
            this.representation = representation;
        }

        public static AutoDetectionPref getPrefForValue(String value) {
            for (AutoDetectionPref autoDetectionPref : AutoDetectionPref.values()) {
                if (autoDetectionPref.representation.equals(value)) {
                    return autoDetectionPref;
                }
            }
            return SIM_NETWORK_LOCALE;
        }
    }

    public enum TextGravity {
        LEFT(-1), CENTER(0), RIGHT(1);

        int enumIndex;

        TextGravity(int i) {
            enumIndex = i;
        }
    }



    public interface OnCountryChangeListener {
        void onCountrySelected();
    }



    public interface FailureListener {
        //when country auto detection failed.
        void onCountryAutoDetectionFailed();
    }


    public interface PhoneNumberValidityChangeListener {
        void onValidityChanged(boolean isValidNumber);
    }

    public interface DialogEventsListener {
        void onCcpDialogOpen(Dialog dialog);

        void onCcpDialogDismiss(DialogInterface dialogInterface);

        void onCcpDialogCancel(DialogInterface dialogInterface);
    }


}
