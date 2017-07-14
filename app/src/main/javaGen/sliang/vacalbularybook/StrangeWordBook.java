package sliang.vacalbularybook;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import sliang.vocalbularybook.DaoSession;
import sliang.vocalbularybook.StrangeWordBookDao;
import sliang.vocalbularybook.WordDao;

/**
 * Created by Administrator on 2017/7/14.
 */

@Entity
public class StrangeWordBook {
    @Id
    private long strangeWordBookId;
    private long dataTime;
    private int clickNum;
    private long wordId;
    @ToOne(joinProperty = "wordId")
    private Word word;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1557966568)
    private transient StrangeWordBookDao myDao;
    @Generated(hash = 312958015)
    public StrangeWordBook(long strangeWordBookId, long dataTime, int clickNum,
            long wordId) {
        this.strangeWordBookId = strangeWordBookId;
        this.dataTime = dataTime;
        this.clickNum = clickNum;
        this.wordId = wordId;
    }
    @Generated(hash = 1914332291)
    public StrangeWordBook() {
    }
    public long getStrangeWordBookId() {
        return this.strangeWordBookId;
    }
    public void setStrangeWordBookId(long strangeWordBookId) {
        this.strangeWordBookId = strangeWordBookId;
    }
    public long getDataTime() {
        return this.dataTime;
    }
    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }
    public int getClickNum() {
        return this.clickNum;
    }
    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }
    public long getWordId() {
        return this.wordId;
    }
    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
    @Generated(hash = 1683684945)
    private transient Long word__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1935261469)
    public Word getWord() {
        long __key = this.wordId;
        if (word__resolvedKey == null || !word__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WordDao targetDao = daoSession.getWordDao();
            Word wordNew = targetDao.load(__key);
            synchronized (this) {
                word = wordNew;
                word__resolvedKey = __key;
            }
        }
        return word;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 964586955)
    public void setWord(@NotNull Word word) {
        if (word == null) {
            throw new DaoException(
                    "To-one property 'wordId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.word = word;
            wordId = word.getId();
            word__resolvedKey = wordId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 461202600)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStrangeWordBookDao() : null;
    }

}
