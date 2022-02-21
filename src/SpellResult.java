import java.util.Arrays;

public class SpellResult {
    private int code; //код ошибки, см. Коды ошибок(https://yandex.ru/dev/speller/doc/dg/reference/error-codes.htmlhttps://yandex.ru/dev/speller/doc/dg/reference/error-codes.html)
    private int pos; //позиция слова с ошибкой (отсчет от 0)
    private int row; //номер строки (отсчет от 0)
    private int col; //номер столбца (отсчет от 0)
    private int len; //длина слова с ошибкой.
    private String word; //исходное слово
    private String[] s; //подсказка (может быть несколько или могут отсутствовать).

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getS() {
        return s;
    }

    public void setS(String[] s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "SpellResult{" +
                "code=" + code +
                ", pos=" + pos +
                ", row=" + row +
                ", col=" + col +
                ", len=" + len +
                ", word='" + word + '\'' +
                ", s=" + Arrays.toString(s) +
                '}';
    }
}
