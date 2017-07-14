package sliang.vacalbularybook;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/7/12.
 */
@Entity
public class Explain {
    public long  wordId;
    public String name;

    @Generated(hash = 1975133587)
    public Explain(long wordId, String name) {
        this.wordId = wordId;
        this.name = name;
    }

    @Generated(hash = 624230213)
    public Explain() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public long getWordId() {
        return this.wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
}
