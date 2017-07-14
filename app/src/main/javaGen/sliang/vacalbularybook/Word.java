package sliang.vacalbularybook;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.ArrayList;
import java.util.List;

import sliang.vocalbularybook.DaoSession;
import sliang.vocalbularybook.ExplainDao;
import sliang.vocalbularybook.WordDao;
import sliang.vocalbularybook.WordVoiceDao;

/**
 * Created by Administrator on 2017/7/12.
 */
@Entity
public class Word {
    @Id(autoincrement = true)
    public long id;
    @ToMany(referencedJoinProperty = "wordId")
    private List<Explain> translations;
    private String name;
    private String usPhonetic;
    private String phonetic;
    private String ukPhonetic;
    private long wordVoiceId;
    @ToOne(joinProperty = "wordVoiceId")
    private WordVoice wordVoice;
    @ToMany(referencedJoinProperty = "wordId")
    private List<Explain> explains;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 768131649)
    private transient WordDao myDao;
    @Generated(hash = 807109601)
    private transient Long wordVoice__resolvedKey;
    @Generated(hash = 11403810)
    public Word(long id, String name, String usPhonetic, String phonetic, String ukPhonetic,
            long wordVoiceId) {
        this.id = id;
        this.name = name;
        this.usPhonetic = usPhonetic;
        this.phonetic = phonetic;
        this.ukPhonetic = ukPhonetic;
        this.wordVoiceId = wordVoiceId;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsPhonetic() {
        return this.usPhonetic;
    }
    public void setUsPhonetic(String usPhonetic) {
        this.usPhonetic = usPhonetic;
    }
    public String getPhonetic() {
        return this.phonetic;
    }
    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
    public String getUkPhonetic() {
        return this.ukPhonetic;
    }
    public void setUkPhonetic(String ukPhonetic) {
        this.ukPhonetic = ukPhonetic;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1184152990)
    public synchronized void resetTranslations() {
        translations = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1797529998)
    public synchronized void resetExplains() {
        explains = null;
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
    @Generated(hash = 2107838493)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWordDao() : null;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 171668154)
    public List<Explain> getTranslations() {
        if (translations == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExplainDao targetDao = daoSession.getExplainDao();
            List<Explain> translationsNew = targetDao._queryWord_Translations(id);
            synchronized (this) {
                if (translations == null) {
                    translations = translationsNew;
                }
            }
        }
        return translations;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 21557820)
    public List<Explain> getExplains() {
        if (explains == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExplainDao targetDao = daoSession.getExplainDao();
            List<Explain> explainsNew = targetDao._queryWord_Explains(id);
            synchronized (this) {
                if (explains == null) {
                    explains = explainsNew;
                }
            }
        }
        return explains;
    }

    public List<String> getExplainStrings(){
        List<Explain> explains = getExplains();
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < this.explains.size(); i++) {
            strings.add(this.explains.get(i).getName());
        }
        return strings;
    }
    public long getWordVoiceId() {
        return this.wordVoiceId;
    }
    public void setWordVoiceId(long wordVoiceId) {
        this.wordVoiceId = wordVoiceId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1576958091)
    public WordVoice getWordVoice() {
        long __key = this.wordVoiceId;
        if (wordVoice__resolvedKey == null || !wordVoice__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WordVoiceDao targetDao = daoSession.getWordVoiceDao();
            WordVoice wordVoiceNew = targetDao.load(__key);
            synchronized (this) {
                wordVoice = wordVoiceNew;
                wordVoice__resolvedKey = __key;
            }
        }
        return wordVoice;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1107496766)
    public void setWordVoice(@NotNull WordVoice wordVoice) {
        if (wordVoice == null) {
            throw new DaoException(
                    "To-one property 'wordVoiceId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.wordVoice = wordVoice;
            wordVoiceId = wordVoice.getWordVoiceId();
            wordVoice__resolvedKey = wordVoiceId;
        }
    }
}
