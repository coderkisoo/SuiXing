package com.vehicle.suixing.suixing.util.musicUtil;

import android.net.Uri;

import com.vehicle.suixing.suixing.bean.musicInfo.BmobMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/5/14.
 */
public class MusicUtils {
    public static List<BmobMusic> getMusicList(){
        List<BmobMusic> musics = new ArrayList<>();
        String[] singerList ={"Daniel Powter","Ina","LenKa","Locebugs","Lydia","Rosette","Young Steff"};
        String[] nameList ={"Free Loop","suddenly","The Show","Everybody Knows I Love You","Tommai mai rub suk tee","Crushed","Distergrate"};

        String[] urlList ={"http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/dff9db3c4003a0d1809c11ac5ff75ba3.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/40075d64405b208f803ca736909e692b.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/1b1f23d34093ec11809954ef701a00f1.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/82693bd340b6c6a58021a0c12badac63.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/397e79ad4019ef86808b825c0070afa6.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/96506c5440f24a2d803d020206b6191c.mp3",
        "http://bmob-cdn-193.b0.upaiyun.com/2016/05/14/91a6b15740402c9a80acfe17204309f0.mp3"};
        for (int i = 0;i < 7;i++){
            BmobMusic music = new BmobMusic();
            music.setFilename(nameList[i]);
            music.setSingername(singerList[i]);
            music.setUrl(Uri.parse(urlList[i]));
            musics.add(music);
        }
        return musics;
    }
}
