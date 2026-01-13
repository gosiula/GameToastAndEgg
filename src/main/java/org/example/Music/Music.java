//package org.example.Music;
//import java.io.File;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.FloatControl;
//import javax.sound.sampled.LineEvent;
//
//// LOADING AND PLAYING MUSIC
//public class Music {
//    // separate clips for each sound (so they don't interfere)
//    private static Clip bgMusicClip;
//    private static Clip menuSoundClip;
//    private static Clip choosingSoundClip;
//
//    // default background music volume
//    private static final float bgMusicVolume = -20.0f;
//
//    // a boolean to determine if the background music should loop
//    private static boolean shouldLoop = true;
//
//    // music and sounds paths
//    public static void backgroundMusic() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\chipichipi.wav";
//        PlayBgMusic(filepath);
//    }
//
//    public static void punchingSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\punching.wav";
//        playPunchingSound(filepath);
//    }
//
//    public static void fireballSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\fireball.wav";
//        playFireballSound(filepath);
//    }
//
//    public static void congratulationsSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\congratulations.wav";
//        playCongratulationSound(filepath);
//    }
//
//    public static void gameOverSounds() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\game_over.wav";
//        playGameOverSound(filepath);
//    }
//
//    public static void menuSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\menu.wav";
//        playMenuSound(filepath);
//    }
//
//    public static void scoreSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\score.wav";
//        playScoreSound(filepath);
//    }
//
//    public static void choosingSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\choosing.wav";
//        playChoosingSound(filepath);
//    }
//
//    public static void ouchSound() {
//        String filepath = "C:\\Users\\Gosia\\Desktop\\jezyki_programowania\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\ouch.wav";
//        playOuchSound(filepath);
//    }
//
//    // playing music and sounds
//    public static void PlayBgMusic(String location) {
//        try {
//            File musicPath = new File(location);
//
//            if (musicPath.exists()) {
//                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//                bgMusicClip = AudioSystem.getClip();
//                bgMusicClip.open(audioInput);
//
//                // set volume to the default level
//                setVolume(bgMusicClip);
//
//                // adding Listener to handle LineEvent
//                bgMusicClip.addLineListener(event -> {
//                    if (event.getType() == LineEvent.Type.STOP && shouldLoop) {
//                        // if shouldLoop is true, rewind and restart the music (when the user is in level 1 state)
//                        bgMusicClip.setMicrosecondPosition(0);
//                        bgMusicClip.start();
//                    }
//                });
//                bgMusicClip.start();
//            } else {
//                System.out.println("file not found: " + location);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void resetShouldLoop() { shouldLoop = true; }
//
//    public static void playFireballSound(String location) {
//        try {
//            // setting volume to the default level for fireball sound
//            Clip fireballSoundClip = createClip(location);
//            setVolume(fireballSoundClip);
//
//            // starting the fireball sound
//            assert fireballSoundClip != null;
//            fireballSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void playCongratulationSound(String location) {
//        try {
//            // setting volume to the default level for congratulations sound
//            Clip congratulationSoundClip = createClip(location);
//            setVolume(congratulationSoundClip);
//
//            // starting the congratulations sound
//            assert congratulationSoundClip != null;
//            congratulationSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void playGameOverSound(String location) {
//        try {
//            // setting volume to the default level for game over sound
//            Clip gameoverSoundClip = createClip(location);
//            setVolume(gameoverSoundClip);
//
//            // starting the game over sound
//            assert gameoverSoundClip != null;
//            gameoverSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void playMenuSound(String location) {
//        try {
//            // setting volume to the default level for menu sound
//            menuSoundClip = createClip(location);
//            setVolume(menuSoundClip);
//
//            // starting the menu sound
//            menuSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void playScoreSound(String location) {
//        try {
//            // setting volume to the default level for score sound
//            Clip scoreSoundClip = createClip(location);
//            setVolume(scoreSoundClip);
//
//            // starting the score sound
//            assert scoreSoundClip != null;
//            scoreSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void playChoosingSound(String location) {
//        try {
//            // setting volume to the default level for choosing sound
//            choosingSoundClip = createClip(location);
//            setVolume(choosingSoundClip);
//
//            // starting the choosing sound
//            choosingSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void playPunchingSound(String location) {
//        try {
//            // setting volume to the default level for punching sound
//            Clip punchingSoundClip = createClip(location);
//            setVolume(punchingSoundClip);
//
//            // starting the punching sound
//            assert punchingSoundClip != null;
//            punchingSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void playOuchSound(String location) {
//        try {
//            // setting volume to the default level for other sounds
//            Clip ouchSoundClip = createClip(location);
//            setVolume(ouchSoundClip);
//
//            // starting the ouch sound
//            assert ouchSoundClip != null;
//            ouchSoundClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // stopping music and sounds
//    public static void stopBgMusic() {
//        if (bgMusicClip != null) {
//            shouldLoop = false;
//            bgMusicClip.stop();
//        }
//    }
//
//    public static void stopChoosingSound() { if(choosingSoundClip != null) choosingSoundClip.stop(); }
//    public static void stopMenuSound() { if(menuSoundClip != null) menuSoundClip.stop(); }
//
//    // creating a clip
//    private static Clip createClip(String location) {
//        try {
//            File soundPath = new File(location);
//
//            if (soundPath.exists()) {
//                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInput);
//                return clip;
//            } else {
//                System.out.println("file not found: " + location);
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // setting volume
//    private static void setVolume(Clip clip) {
//        if (clip != null) {
//            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            gainControl.setValue(Music.bgMusicVolume);
//        }
//    }
//}

package org.example.Music;

import javax.sound.sampled.*;
import java.net.URL;

// LOADING AND PLAYING MUSIC
public class Music {
    // separate clips for each sound (so they don't interfere)
    private static Clip bgMusicClip;
    private static Clip menuSoundClip;
    private static Clip choosingSoundClip;

    // default background music volume
    private static final float bgMusicVolume = -20.0f;

    // a boolean to determine if the background music should loop
    private static boolean shouldLoop = true;

    // === PUBLIC API (no absolute paths) ===
    public static void backgroundMusic() {
        PlayBgMusic("chipichipi.wav");
    }

    public static void punchingSound() {
        playPunchingSound("punching.wav");
    }

    public static void fireballSound() {
        playFireballSound("fireball.wav");
    }

    public static void congratulationsSound() {
        playCongratulationSound("congratulations.wav");
    }

    public static void gameOverSounds() {
        playGameOverSound("game_over.wav");
    }

    public static void menuSound() {
        playMenuSound("menu.wav");
    }

    public static void scoreSound() {
        playScoreSound("score.wav");
    }

    public static void choosingSound() {
        playChoosingSound("choosing.wav");
    }

    public static void ouchSound() {
        playOuchSound("ouch.wav");
    }

    // === PLAYERS ===
    public static void PlayBgMusic(String filename) {
        try {
            bgMusicClip = createClipFromResources(filename);
            if (bgMusicClip == null) return;

            setVolume(bgMusicClip);

            bgMusicClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && shouldLoop && bgMusicClip != null) {
                    bgMusicClip.setMicrosecondPosition(0);
                    bgMusicClip.start();
                }
            });

            bgMusicClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetShouldLoop() { shouldLoop = true; }

    public static void playFireballSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    public static void playCongratulationSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    public static void playGameOverSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    public static void playMenuSound(String filename) {
        menuSoundClip = createClipFromResources(filename);
        if (menuSoundClip == null) return;
        setVolume(menuSoundClip);
        menuSoundClip.start();
    }

    public static void playScoreSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    public static void playChoosingSound(String filename) {
        choosingSoundClip = createClipFromResources(filename);
        if (choosingSoundClip == null) return;
        setVolume(choosingSoundClip);
        choosingSoundClip.start();
    }

    public static void playPunchingSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    public static void playOuchSound(String filename) {
        Clip clip = createClipFromResources(filename);
        if (clip == null) return;
        setVolume(clip);
        clip.start();
    }

    // === STOPPING ===
    public static void stopBgMusic() {
        if (bgMusicClip != null) {
            shouldLoop = false;
            bgMusicClip.stop();
        }
    }

    public static void stopChoosingSound() {
        if (choosingSoundClip != null) choosingSoundClip.stop();
    }

    public static void stopMenuSound() {
        if (menuSoundClip != null) menuSoundClip.stop();
    }

    // === RESOURCE LOADING ===
    private static Clip createClipFromResources(String filename) {
        try {
            // Plik musi być w: src/main/resources/BgMusic/<filename>
            URL url = Music.class.getResource("/BgMusic/" + filename);

            if (url == null) {
                System.out.println("file not found in resources: /BgMusic/" + filename);
                return null;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // setting volume
    private static void setVolume(Clip clip) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(Music.bgMusicVolume);
        }
    }
}

