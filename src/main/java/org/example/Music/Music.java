package org.example.Music;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class Music {
    private static Clip bgMusicClip; // Separate Clip for background music
    private static Clip fireballSoundClip;
    private static Clip congratulationSoundClip;
    private static Clip gameoverSoundClip;
    private static Clip menuSoundClip;
    private static Clip scoreSoundClip;
    private static Clip choosingSoundClip;
    private static Clip scratchingSoundClip;
    private static Clip ouchSoundClip;

    private static float bgMusicVolume = -20.0f; // Default background music volume
    private static boolean shouldLoop = true;

    public static void backgroundMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\chipichipi.wav";
        PlayBgMusic(filepath);
    }


    public static void scratchingMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\punching.wav";
        playScratchingSound(filepath);
    }


    public static void fireballMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\fireball.wav";
        playFireBallSound(filepath);
    }

    public static void congratulationsMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\congratulations.wav";
        playCongratulationSound(filepath);
    }

    public static void gameOverMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\game_over.wav";
        playGameOverSound(filepath);
    }

    public static void menuMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\menu.wav";
        playMenuSound(filepath);
    }

    public static void scoreMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\score.wav";
        playScoreSound(filepath);
    }

    public static void choosingMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\choosing.wav";
        playChoosingSound(filepath);
    }

    public static void ouchMusic() {
        String filepath = "C:\\Users\\Gosia\\Desktop\\GameToastAndEgg\\src\\main\\resources\\BgMusic\\ouch.wav";
        playChoosingSound(filepath);
    }

    public static void PlayBgMusic(String location) {
        try {
            File musicPath = new File(location);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                bgMusicClip = AudioSystem.getClip();
                bgMusicClip.open(audioInput);

                // Set volume to the default level
                setVolume(bgMusicClip, bgMusicVolume);

                // Add Listener to handle LineEvent
                bgMusicClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP && shouldLoop) {
                        // If shouldLoop is true, rewind and restart the music
                        bgMusicClip.setMicrosecondPosition(0);
                        bgMusicClip.start();
                    }
                });

                bgMusicClip.start();
            } else {
                System.out.println("File not found: " + location);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void stopBgMusic() {
        if (bgMusicClip != null) {
            shouldLoop = false;
            bgMusicClip.stop();
        }
    }

    public static void resetShouldLoop() {
        shouldLoop = true;
    }

    public static void playFireBallSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            fireballSoundClip = createClip(location);
            setVolume(fireballSoundClip, currentVolume);

            fireballSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playCongratulationSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            congratulationSoundClip = createClip(location);
            setVolume(congratulationSoundClip, currentVolume);

            congratulationSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playGameOverSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            gameoverSoundClip = createClip(location);
            setVolume(gameoverSoundClip, currentVolume);

            gameoverSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playMenuSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            menuSoundClip = createClip(location);
            setVolume(menuSoundClip, currentVolume);

            menuSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playScoreSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            scoreSoundClip = createClip(location);
            setVolume(scoreSoundClip, currentVolume);

            scoreSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playChoosingSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            choosingSoundClip = createClip(location);
            setVolume(choosingSoundClip, currentVolume);

            choosingSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void playScratchingSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            scratchingSoundClip = createClip(location);
            setVolume(scratchingSoundClip, currentVolume);

            scratchingSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playOuchSound(String location) {
        try {
            // Save current background music volume
            float currentVolume = bgMusicVolume;

            // Set volume to the default level for other sounds
            scratchingSoundClip = createClip(location);
            setVolume(scratchingSoundClip, currentVolume);

            scratchingSoundClip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public static void stopChoosingMusic() { if(choosingSoundClip != null) choosingSoundClip.stop(); }
    //public static void stopBgMusic() { if(bgMusicClip != null) bgMusicClip.stop(); }
    public static void stopScratchingMusic() { if(scratchingSoundClip != null) scratchingSoundClip.stop(); }
    public static void stopFireballMusic() { if(fireballSoundClip != null) fireballSoundClip.stop(); }
    public static void stopCongratulationMusic() { if(congratulationSoundClip != null) congratulationSoundClip.stop(); }
    public static void stopGameOverMusic() { if(gameoverSoundClip != null) gameoverSoundClip.stop(); }
    public static void stopMenuMusic() { if(menuSoundClip != null) menuSoundClip.stop(); }
    public static void stopScoreMusic() { if(scoreSoundClip != null) scoreSoundClip.stop(); }


    private static Clip createClip(String location) {
        try {
            File soundPath = new File(location);

            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                return clip;
            } else {
                System.out.println("File not found: " + location);
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static void setVolume(Clip clip, float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }
}
