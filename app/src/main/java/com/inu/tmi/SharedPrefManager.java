package com.inu.tmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;

//import com.yap.inu.data.UserInfo;

/**
 * @author woosang.cho@yap.net
 * @since 2017-03-27.
 */
public class SharedPrefManager {
  private static final String PREF_NAME = "my_app_config";

  private Context mContext;
  private SharedPreferences mPref;
  private final String mPrefName = PREF_NAME;

  public static final String KEY_CREATED_DB  = "create_database";
  public static final String KEY_APP_VERSION = "app_version";
  public static final String KEY_USER_ID     = "user_id";
  public static final String KEY_USER_INFO   = "user_info";

  public SharedPrefManager(Context context) {
    Log.i("shared","preference");
    mContext = context;
    mPref = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE);
  }

  /**
   * "/data/data/your_application_package/shared_prefs/my_app_config.xml"
   *
   * @return preferences 파일 존재 여부
   */
  public boolean isPrefExist() {
    StringBuilder sb = new StringBuilder();
    sb.append("/data/data/");
    sb.append(mContext.getPackageName());
    sb.append("/shared_prefs/");
    sb.append(mPrefName);
    sb.append(".xml");
    File f = new File(sb.toString());
    return f.exists();
  }

  public boolean isKeyContains(String key) {
    return mPref.contains(key);
  }

  public String getStrPreferences(String key) {
    try {
      String str = mPref.getString(key, null);
      return str;
    } catch (ClassCastException e) {
      return "";
    }
  }

  public int getIntPreferences(String key) {
    try {
      int num = mPref.getInt(key, -1);
      return num;
    } catch (ClassCastException e) {
      return 0;
    }
  }

  public boolean getBoolPreferences(String key) {
    return mPref.getBoolean(key, false);
  }

  public void putStrPreferences(String key, String value) {
    SharedPreferences.Editor editor = mPref.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public void putIntPreferences(String key, int value) {
    SharedPreferences.Editor editor = mPref.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  public void putBoolPreferences(String key, boolean bool) {
    SharedPreferences.Editor editor = mPref.edit();
    editor.putBoolean(key, bool);
    editor.commit();
  }

  /**
   * 값 삭제
   *
   * @param key 삭제하고자 하는 값에 해당하는 키
   */
  public void removePreferences(String key) {
    SharedPreferences.Editor editor = mPref.edit();
    editor.remove(key);
    editor.commit();
  }

  /**
   * 모든 값 삭제
   */
  public void removeAllPreferences() {
    SharedPreferences.Editor editor = mPref.edit();
    editor.clear();
    editor.commit();
  }


  /**
   * (G) 앱 설정값을 저장한다.
   *
   * @param context Context
   * @param key     저장할 값의 키.
   * @param value   저장할 값 (Integer, Long, Boolean, Float, String)
   * @return 설정값의 저장 여부.
   */
  public static <T> boolean preferenceSave(Context context, String key, T value) {
    Log.i("info : ","preferenceSave");
    boolean result = false;

    if (value != null && context != null) {
      SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = pref.edit();

      if (value instanceof Integer) {
        editor.putInt(key, (Integer) value);
      }
      else if (value instanceof Long) {
        editor.putLong(key, (Long) value);
      }
      else if (value instanceof Boolean) {
        editor.putBoolean(key, (Boolean) value);
      }
      else if (value instanceof Float) {
        editor.putFloat(key, (Float) value);
      }
      else if (value instanceof String) {
        editor.putString(key, (String) value);
      }
      else {
        Log.e("Utils", ">> Save preferences ERROR : 데이터 타입이 유효하지 않습니다. 데이터 타입은"
            + " Integer, Long, Float, Boolean, String 만 유효합니다.");
      }
      editor.apply();
      result = true;
    }

    return result;
  }

  /**
   * key에 해당하는 저장된 Preference의 value를 삭제 한다.
   *
   * @param context Context
   * @param key     삭제할 value의 key값.
   */
  private static void preferencesRemove(Context context, String key) {
    SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    editor.remove(key);
    editor.apply();
  }

  /**
   * SharedPreferences 를 생성한다.
   *
   * @param context Context
   * @return SharedPreferences or null.
   */
  private static SharedPreferences preferencesLoad(Context context) {
    return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  /**
   * 저장된 앱 설정값중 문자열을 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 문자열이나 기본값(-1, "", false)
   */
  public static String preferencesLoadString(Context context, String key) {
    String result = null;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getString(key, "");
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Integer를 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 int 정수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static int preferencesLoadInt(Context context, String key) {
    int result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      try {
        result = pref.getInt(key, -1);
      } catch (ClassCastException e) {
      }
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Long를 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 long 정수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static long preferencesLoadLong(Context context, String key) {
    long result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getLong(key, -1);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Float을 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 float 실수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static float preferencesLoadFloat(Context context, String key) {
    float result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getFloat(key, -1);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Boolean 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 boolean. 오류혹은 저장한 값이 없을시 false.
   */
  public static boolean preferencesLoadBoolean(Context context, String key) {
    boolean result = false;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getBoolean(key, false);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Boolean 불러온다.
   *
   * @param context  Context
   * @param key      저장한 값의 키
   * @param defValue 저장한 값이 없거나 할 때의 기본 값.
   * @return 저장한 boolean, 오류 혹은 저장한 값이 없을시 defValue를 반환
   */
  private static boolean preferencesLoadBoolean(Context context, String key, boolean defValue) {
    boolean result = false;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getBoolean(key, defValue);
    }
    return result;
  }

  /**
   * 저장된 사용자 ID(e-mail)을 획득
   *
   * @param context
   * @return
   */
  public static String loadUserId(Context context) {
    return preferencesLoadString(context, KEY_USER_ID);
  }

  /**
   * 사용자 ID(e-mail)을 저장
   *
   * @param context
   * @param userId
   * @return
   */
  public static boolean saveUserId(Context context, String userId) {
    return preferenceSave(context, KEY_USER_ID, userId);
  }

  /**
   * 저장된 UserInfo 데이터를 획득
   *
   * @param context
   * @return
   */
  /*public static UserInfo loadUserInfo(Context context) {
    Gson gson = new Gson();
    String json = preferencesLoadString(context, KEY_USER_INFO);
    try {
      UserInfo obj = gson.fromJson(json, UserInfo.class);
      return obj;
    } catch (JsonSyntaxException e) {
      return null;
    }
  }*/

  /**
   * UserInfo 데이터를 저장
   *
   * @param context
   * @param userInfo
   * @return
   */
  /*public static boolean saveUserInfo(Context context, UserInfo userInfo) {
    Gson gson = new Gson();
    String json = gson.toJson(userInfo);
    return preferenceSave(context, KEY_USER_INFO, json);
  }*/

  /**
   * 저장된 UserInfo 데이터를 삭제
   *
   * @param context
   */
  public static void deleteUserInfo(Context context) {
    preferencesRemove(context, KEY_USER_INFO);
  }

  //자동로그인 해제
  public static void NotAutoLogin(Context context){preferencesRemove(context,"AutoLogin");}


  public static void Logout(Context context)
  {
    SharedPreferences pref = preferencesLoad(context);

    SharedPreferences.Editor editor = pref.edit();
    editor.clear();
    editor.commit();

  }

  public static boolean AutoCheck(Context context) {
    if(preferencesLoadBoolean(context,"AutoLogin"))
    {  return true;}
    return false;

  }

  public static void UserLogout(Context context)
  {
    preferencesRemove(context,"name");
    preferencesRemove(context,"email");
    preferencesRemove(context,"image");
    preferencesRemove(context,"id");
    preferencesRemove(context,"pass");
    preferencesRemove(context,"name");
    preferencesRemove(context,"AutoLogin");
    preferencesRemove(context,"roomnum");
    preferencesRemove(context,"maker");

  }
  public static final String Location="outside" ;//처음 장소는 밖에
  public static final String Time = "0";          //처음 시간은 0으로 초기화

  public static void SettingRemove(Context context){
    preferencesRemove(context,"Brightness");
    preferencesRemove(context,"AudioSize");
    preferencesRemove(context,"AudioMode");
  }

}
