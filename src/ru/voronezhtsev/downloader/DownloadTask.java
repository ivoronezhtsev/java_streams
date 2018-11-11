package ru.voronezhtsev.downloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Задача на скачивание одной ссылки из сети и записи на диск
 *
 * @author Воронежцев Игорь on 11.11.2018
 */
public class DownloadTask implements Runnable {
    private URL mURL;
    private String mFileName;

    /**
     * @param url      ссылка на ресурс для скачивания
     * @param fileName имя файла или абсолютный путь для записи на диск
     */
    public DownloadTask(URL url, String fileName) {
        mURL = url;
        mFileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try (InputStream is = new BufferedInputStream(mURL.openStream())) {
            System.out.println("Загрузка " + mURL + " началась");
            try (FileOutputStream fos = new FileOutputStream(mFileName)) {
                int oneStep = is.available() / 10;
                int bytesDownload = 0;
                for (int c = is.read(); c != -1; c = is.read()) {
                    fos.write(c);
                    bytesDownload++;
                    if (bytesDownload >= oneStep) {
                        bytesDownload = 0;
                    }
                }
            } catch (IOException e) {
                System.err.println("Не удалось произвести запись в файл " + mFileName);
            }
        } catch (IOException e) {
            System.err.println("Не удалось открыть файл по ссылке " + mURL.toString());
        }
        System.out.println("Успешно загрузили " + mURL);
    }
}
