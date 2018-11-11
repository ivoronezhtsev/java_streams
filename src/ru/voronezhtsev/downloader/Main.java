package ru.voronezhtsev.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Map<URL, String> links = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 5; i++) {
            System.out.print("Ссылка на файл для скачивания > ");
            URL url = new URL(reader.readLine());
            System.out.print("Имя файла для сохранения на диск > ");
            String fileName = reader.readLine();
            links.put(url, fileName);
        }
        System.out.print("Количество потоков на скачивание > ");
        int threadsCount = Integer.parseInt(reader.readLine());
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        for (URL url : links.keySet()) {
            executorService.submit(new DownloadTask(url, links.get(url)));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("Загрузка завершена");
    }
}