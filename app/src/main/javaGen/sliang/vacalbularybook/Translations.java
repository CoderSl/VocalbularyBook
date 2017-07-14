package sliang.vacalbularybook;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/7/13.
 */
@Entity
public class Translations {
    public long  wordId;
    public String name;
    @Generated(hash = 37517959)
    public Translations(long wordId, String name) {
        this.wordId = wordId;
        this.name = name;
    }
    @Generated(hash = 428531616)
    public Translations() {
    }
    public long getWordId() {
        return this.wordId;
    }
    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
