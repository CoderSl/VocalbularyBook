package sliang.vacalbularybook;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/7/14.
 */

@Entity
public class WordVoice {

    @Id(autoincrement = true)
    private  long  wordVoiceId;
    private String speakUrl;
    private String UKSpeakUrl;
    private String USSpeakUrl;
    private String resultSpeakUrl;
    @Generated(hash = 1767977619)
    public WordVoice(long wordVoiceId, String speakUrl, String UKSpeakUrl,
            String USSpeakUrl, String resultSpeakUrl) {
        this.wordVoiceId = wordVoiceId;
        this.speakUrl = speakUrl;
        this.UKSpeakUrl = UKSpeakUrl;
        this.USSpeakUrl = USSpeakUrl;
        this.resultSpeakUrl = resultSpeakUrl;
    }
    @Generated(hash = 314498357)
    public WordVoice() {
    }
    public long getWordVoiceId() {
        return this.wordVoiceId;
    }
    public void setWordVoiceId(long wordVoiceId) {
        this.wordVoiceId = wordVoiceId;
    }
    public String getSpeakUrl() {
        return this.speakUrl;
    }
    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }
    public String getUKSpeakUrl() {
        return this.UKSpeakUrl;
    }
    public void setUKSpeakUrl(String UKSpeakUrl) {
        this.UKSpeakUrl = UKSpeakUrl;
    }
    public String getUSSpeakUrl() {
        return this.USSpeakUrl;
    }
    public void setUSSpeakUrl(String USSpeakUrl) {
        this.USSpeakUrl = USSpeakUrl;
    }
    public String getResultSpeakUrl() {
        return this.resultSpeakUrl;
    }
    public void setResultSpeakUrl(String resultSpeakUrl) {
        this.resultSpeakUrl = resultSpeakUrl;
    }
}
