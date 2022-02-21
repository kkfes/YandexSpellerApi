import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class СheckTexts {
    private String[] text;  //Текст для проверки.
    private String[] lang;  //Языки проверки.
    private int options = 0;  //Опции Яндекс.Спеллера. Значением параметра является сумма значений требуемых опций, см.
    //Например, options=6 — это сумма опций IGNORE_DIGITS и IGNORE_URLS. По умолчанию options=0.
    private String format = "plain";
//    Формат проверяемого текста.
//
//    Возможные значения:
//
//    plain — текст без разметки (значение по умолчанию);
//    html — HTML-текст.

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public String[] getLang() {
        return lang;
    }

    public void setLang(String[] lang) {
        this.lang = lang;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public String getFormat() {
        return format;
    }

    public void setHtmlFormat() {
        this.format = "html";
    }
    public void setPlainFormat() {
        this.format = "plain";
    }

    public СheckTexts() {
    }

    public СheckTexts(String[] text, String[] lang) {
        this.text = text;
        this.lang = lang;
    }

    public ArrayList<CallBack> execute() throws IOException {
        if(this.text!=null){
            if(this.text[0].length()!=0){
                if(this.lang!=null){
                    if(this.lang.length!=0){
                        String lang = "";
                        for (int i = 0;i<this.lang.length;i++){
                            lang+=this.lang[i]+",";
                        }
                        lang=lang.substring(0,lang.length()-1);
                        String txt = "";
                        for (String s:this.text){
                            txt+="text="+s.trim().replace(" ","+")+"&";
                        }
                        txt=txt.substring(0,txt.length()-1);
                        String url = "https://speller.yandex.net/services/spellservice.json/checkTexts?"+txt+"&lan="+lang+"&options="+this.options+"&format="+this.format;
                        URL urlObject = new URL(url);

                        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
                        connection.setRequestMethod("GET");

                        int resultCode = connection.getResponseCode();
                        if (resultCode == 404) {
                            throw new IllegalArgumentException();
                        }
                        ArrayList<CallBack> callBack = new ArrayList<>();
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            String inLine;
                            StringBuffer result = new StringBuffer();
                            while ((inLine = in.readLine()) != null) {
                                result.append(inLine);
                            }
                            JSONObject jsonObject = new JSONObject("{\"words\":"+result+"}");
                            System.out.println(url);
                            for (int j = 0;j<jsonObject.getJSONArray("words").length();j++){
                                CallBack back = new CallBack();
                                for (int v = 0; v < jsonObject.getJSONArray("words").getJSONArray(j).length(); v++) {
                                    SpellResult spellResult = new SpellResult();
                                    spellResult.setCode(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getInt("code"));
                                    spellResult.setPos(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getInt("pos"));
                                    spellResult.setRow(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getInt("row"));
                                    spellResult.setCol(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getInt("col"));
                                    spellResult.setLen(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getInt("len"));
                                    spellResult.setWord(jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getString("word"));
                                    String str = "";
                                    for (int i = 0; i < jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getJSONArray("s").length(); i++) {
                                        str+=jsonObject.getJSONArray("words").getJSONArray(j).getJSONObject(v).getJSONArray("s").getString(i)+"|";
                                    }
                                    str=str.substring(0,str.length()-1);
                                    String[] strings = str.split("\\|");
                                    spellResult.setS(strings);
                                    back.getSpellResults().add(spellResult);
                                }
                                callBack.add(back);
                            }
                        }
                        return callBack;
                    }else {
                        System.err.println("Lang is null");
                        return null;
                    }
                }else {
                    System.err.println("Lang is null");
                    return null;
                }
            }else {
                System.err.println("Text is null");
                return null;
            }
        }else {
            System.err.println("Text is null");
            return null;
        }
    }

    @Override
    public String toString() {
        return "CheckText{" +
                "text='" + text + '\'' +
                ", lang=" + Arrays.toString(lang) +
                ", options=" + options +
                ", format='" + format + '\'' +
                '}';
    }
}
